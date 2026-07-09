/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.cluster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tools.jackson.databind.ObjectMapper;

import ai.gebo.architecture.hazelcast.GClusterMessage;
import ai.gebo.architecture.hazelcast.IGClusterMessageBus;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.cluster.GLlmModelClusterEvent.Operation;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGImageModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGRuntimeModelConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

/**
 * Keeps the memory-resident (live remote connection) LLM model clients of every
 * {@link IGRuntimeModelConfigurationDao} implementation in sync across a
 * Gebo.ai cluster.
 * <p>
 * When an admin operation adds, updates or deletes a model configuration on one
 * instance, the owning DAO calls one of the {@code broadcast*} methods below;
 * this component publishes a {@link GLlmModelClusterEvent} onto the shared
 * cluster message bus. Every other instance receives the event through
 * {@link #onClusterMessage(GClusterMessage)} and re-applies the same change to
 * its own live clients so the whole cluster converges to the same LLM runtime
 * state.
 * <p>
 * <strong>No echo loop:</strong> remote events are applied through the DAO's
 * plain (non-clustered) operations, which never broadcast. Only the
 * {@code *Clustered} DAO operations (invoked by the local admin flow) broadcast,
 * so a change is emitted exactly once by its originating node. The bus also
 * flags self-published messages ({@link GClusterMessage#isLocalOrigin()}), which
 * are ignored defensively.
 * <p>
 * When clustering is disabled the injected bus is the standalone no-op
 * implementation, so all broadcasts are silently dropped and this component adds
 * no overhead to a single instance.
 */
@Component
public class GLlmModelClusterSynchronizer {

	private static final Logger LOGGER = LoggerFactory.getLogger(GLlmModelClusterSynchronizer.class);

	/** Logical channel on the cluster bus dedicated to LLM runtime config changes. */
	public static final String CHANNEL = "gebo.llms.model.runtime-config";

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private final IGClusterMessageBus bus;
	private final IGRuntimeBinder runtimeBinder;

	private AutoCloseable subscription;

	@Autowired
	public GLlmModelClusterSynchronizer(IGClusterMessageBus bus, IGRuntimeBinder runtimeBinder) {
		this.bus = bus;
		this.runtimeBinder = runtimeBinder;
	}

	@PostConstruct
	public void start() {
		this.subscription = bus.subscribe(CHANNEL, this::onClusterMessage);
		if (bus.isClustered()) {
			LOGGER.info("LLM model cluster synchronizer active on member {} (channel {})", bus.localMemberId(),
					CHANNEL);
		} else {
			LOGGER.info("LLM model cluster synchronizer running standalone (clustering disabled)");
		}
	}

	@PreDestroy
	public void stop() {
		if (subscription != null) {
			try {
				subscription.close();
			} catch (Exception e) {
				LOGGER.warn("Error closing LLM model cluster subscription", e);
			}
		}
	}

	// ---------------------------------------------------------------------
	// Broadcasting (local change -> cluster)
	// ---------------------------------------------------------------------

	/** Propagates the creation of a live model client built from {@code config}. */
	public void broadcastAdd(GLlmModelClusterCategory category, GBaseModelConfig config) {
		publishConfigEvent(category, Operation.ADD, config);
	}

	/** Propagates the reconfiguration of an existing live model client. */
	public void broadcastUpdate(GLlmModelClusterCategory category, GBaseModelConfig config) {
		publishConfigEvent(category, Operation.UPDATE, config);
	}

	/** Propagates the deletion of the live model client identified by {@code code}. */
	public void broadcastDelete(GLlmModelClusterCategory category, String code) {
		if (!bus.isClustered()) {
			return;
		}
		GLlmModelClusterEvent event = new GLlmModelClusterEvent(category, Operation.DELETE, null, code, null);
		bus.publish(CHANNEL, event);
		LOGGER.debug("Broadcast DELETE {} code={}", category, code);
	}

	private void publishConfigEvent(GLlmModelClusterCategory category, Operation operation, GBaseModelConfig config) {
		if (!bus.isClustered() || config == null) {
			return;
		}
		try {
			String json = MAPPER.writeValueAsString(config);
			GLlmModelClusterEvent event = new GLlmModelClusterEvent(category, operation, config.getClass().getName(),
					config.getCode(), json);
			bus.publish(CHANNEL, event);
			LOGGER.debug("Broadcast {} {} code={}", operation, category, config.getCode());
		} catch (Throwable t) {
			LOGGER.error("Failed to broadcast " + operation + " for LLM model " + category + " code="
					+ (config != null ? config.getCode() : null), t);
		}
	}

	// ---------------------------------------------------------------------
	// Receiving (cluster -> local change)
	// ---------------------------------------------------------------------

	void onClusterMessage(GClusterMessage<GLlmModelClusterEvent> message) {
		if (message.isLocalOrigin()) {
			// This node already applied the change before broadcasting it.
			return;
		}
		GLlmModelClusterEvent event = message.getPayload();
		if (event == null) {
			return;
		}
		try {
			applyRemote(event);
			LOGGER.info("Applied remote LLM model {} {} code={} from member {}", event.getOperation(),
					event.getCategory(), event.getCode(), message.getOriginMemberId());
		} catch (Throwable t) {
			LOGGER.error("Failed to apply remote LLM model event " + event.getOperation() + " " + event.getCategory()
					+ " code=" + event.getCode(), t);
		}
	}

	private void applyRemote(GLlmModelClusterEvent event) throws Exception {
		IGRuntimeModelConfigurationDao<?, ?> dao = resolveDao(event.getCategory());
		if (dao == null) {
			LOGGER.warn("No DAO resolved for LLM model category {}; skipping remote event", event.getCategory());
			return;
		}
		apply(dao, event);
	}

	private IGRuntimeModelConfigurationDao<?, ?> resolveDao(GLlmModelClusterCategory category) {
		switch (category) {
		case CHAT:
			return runtimeBinder.getImplementationOf(IGChatModelRuntimeConfigurationDao.class);
		case EMBEDDING:
			return runtimeBinder.getImplementationOf(IGEmbeddingModelRuntimeConfigurationDao.class);
		case RANKER:
			return runtimeBinder.getImplementationOf(IGRankerModelRuntimeConfigurationDao.class);
		case TEXT_TO_SPEECH:
			return runtimeBinder.getImplementationOf(IGTextToSpeechModelRuntimeConfigurationDao.class);
		case TRANSCRIPT:
			return runtimeBinder.getImplementationOf(IGTranscriptModelRuntimeConfigurationDao.class);
		case IMAGE:
			return runtimeBinder.getImplementationOf(IGImageModelRuntimeConfigurationDao.class);
		default:
			return null;
		}
	}

	/**
	 * Applies a remote event through the DAO's <em>plain</em> operations only, so
	 * re-applying never re-broadcasts. Raw types are used deliberately: the concrete
	 * configuration type is recovered from the payload and is guaranteed assignable
	 * to the DAO's declared configuration type for the resolved category.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void apply(IGRuntimeModelConfigurationDao dao, GLlmModelClusterEvent event) throws Exception {
		switch (event.getOperation()) {
		case ADD:
			dao.addRuntimeByConfig(deserializeConfig(event));
			break;
		case UPDATE:
			IGConfigurableModel handler = (IGConfigurableModel) dao.findByCode(event.getCode());
			if (handler != null) {
				handler.reconfigure(deserializeConfig(event));
			} else {
				// Not present locally yet: create it so this node converges.
				dao.addRuntimeByConfig(deserializeConfig(event));
			}
			break;
		case DELETE:
			dao.deleteByCode(event.getCode());
			break;
		default:
			break;
		}
	}

	private GBaseModelConfig deserializeConfig(GLlmModelClusterEvent event) throws ClassNotFoundException {
		if (event.getConfigJson() == null || event.getConfigClassName() == null) {
			throw new IllegalArgumentException("Missing config payload for " + event.getOperation() + " event");
		}
		Class<?> concreteType = Class.forName(event.getConfigClassName());
		return (GBaseModelConfig) MAPPER.readValue(event.getConfigJson(), concreteType);
	}
}
