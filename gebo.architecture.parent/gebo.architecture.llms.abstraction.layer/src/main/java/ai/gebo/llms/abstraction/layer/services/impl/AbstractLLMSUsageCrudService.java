package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.List;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.core.messages.LLMUsageDetailPayload;
import ai.gebo.llms.abstraction.layer.dto.LLMUsageDetailDto;
import ai.gebo.llms.abstraction.layer.services.ILLMSUsageCrudService;

/**
 * Emits usage events to the {@code LLMS-USAGE-MONITOR}/{@code USAGE-CONCENTRATOR}
 * receiver hosted in {@code gebo.architecture.compute.workflow} (package
 * {@code ai.gebo.architecture.llms.usage}), rather than persisting directly - the
 * receiver's own dedicated thread does the Mongo write and the periodic
 * consolidation, keeping both off the calling chat-completion thread.
 *
 * <p>
 * {@code messagingSystemId} is fixed ({@link GStandardModulesConstraints#USAGE_CONCENTRATOR})
 * while {@code messagingModuleId} is assignable per concrete subclass -
 * mirroring {@code AbstractJobLaunchManager}: every hosting microservice
 * registers its OWN already-owned module id here, never the shared
 * {@link GStandardModulesConstraints#LLMS_USAGE_MONITOR} target constant
 * itself - that one is exclusively tyr's, declared in
 * {@code GeboStandardMicroservices}. Registering under the target's own
 * identity (the bug this replaced) is harmless while that identity is
 * undeclared in the topology (a same-service self-loop nobody proxies), but
 * collides the moment it becomes topology-routable: the sending
 * microservice's own RabbitMQ bridge then builds a remote-proxy emitter under
 * that exact key too. The monolith is the one legitimate exception - it hosts
 * both this emitter and tyr's receiver in the same JVM, under the identical
 * shared identity, which is safe because {@code GBaseMessageBroker} keeps
 * separate maps for emitters and receivers.
 * </p>
 */
public abstract class AbstractLLMSUsageCrudService implements ILLMSUsageCrudService, IGMessageEmitter {
	private final IGMessageBroker broker;
	private final IMessageEnvelopeFactory envelopeFactory;
	private final String messagingModuleId;

	protected AbstractLLMSUsageCrudService(IGMessageBroker broker, IMessageEnvelopeFactory envelopeFactory,
			String messagingModuleId) {
		this.broker = broker;
		this.envelopeFactory = envelopeFactory;
		this.messagingModuleId = messagingModuleId;
	}

	@Override
	public void enqueueUsage(LLMUsageDetailDto usage) {
		LLMUsageDetailPayload payload = new LLMUsageDetailPayload();
		payload.setProviderId(usage.getProviderId());
		payload.setUsername(usage.getUsername());
		payload.setModel(usage.getModel());
		payload.setCallerStack(usage.getCallerStack());
		payload.setModelType(usage.getModelType());
		payload.setLatency(usage.getLatency());
		payload.setInputToken(usage.getInputToken());
		payload.setOutputToken(usage.getOutputToken());
		payload.setTotalToken(usage.getTotalToken());
		payload.setUsageTimestamp(System.currentTimeMillis());

		GMessageEnvelope<LLMUsageDetailPayload> envelope = envelopeFactory.newMessageFrom(this, payload);
		envelope.setTargetModule(GStandardModulesConstraints.LLMS_USAGE_MONITOR);
		envelope.setTargetComponent(GStandardModulesConstraints.USAGE_CONCENTRATOR);
		broker.accept(envelope);
	}

	@Override
	public String getMessagingModuleId() {
		return messagingModuleId;
	}

	@Override
	public String getMessagingSystemId() {
		return GStandardModulesConstraints.USAGE_CONCENTRATOR;
	}

	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}

	@Override
	public List<String> getEmittedPayloadTypes() {
		return List.of(LLMUsageDetailPayload.class.getName());
	}
}
