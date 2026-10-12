package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.GAbstractTimedOutMessageReceiverFactory;
import ai.gebo.application.messaging.IGBatchMessagesReceiver;
import ai.gebo.application.messaging.IGTimedOutMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GMessagesBatchPayload;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatSessionLifeCycleConfig;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionStateShrinkerService;
import ai.gebo.security.services.IdentityUtil;
import ai.gebo.security.services.RunAsWith2Exceptions;

@Component
@Scope("singleton")
public class SessionShrinkMessagesReceiver extends GAbstractTimedOutMessageReceiverFactory {
	static final String SESSION_SHRINKER = "session-shrinker";
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionShrinkMessagesReceiver.class);
	private final IGChatSessionStateShrinkerService shrinker;
	private final GeboChatSessionLifeCycleConfig config;
	// resolves the user a chat's job runs as (the user's principal and authorities)
	private final ObjectProvider<UserDetailsService> users;

	class BatchSessionShrinkMessagesReceiver extends GNestedBatchAggregatorMessageReceiver {

		public BatchSessionShrinkMessagesReceiver(IGBatchMessagesReceiver nested, int flushThreshold) {
			super(nested, flushThreshold);

		}

	}

	/**
	 * Runs a job of a chat as the user it belongs to, as the user details service
	 * resolves them (their model calls accounted to them): the receiver's thread carries
	 * no identity. A job queued without its user, or whose user can not be resolved, runs
	 * without one.
	 */
	static void asChatUser(SessionShrinkRequestPayload entry, UserDetailsService users,
			RunAsWith2Exceptions<LLMConfigException, IOException> job) throws LLMConfigException, IOException {
		UserDetails user = null;
		if (entry.getUsername() != null && users != null) {
			try {
				user = users.loadUserByUsername(entry.getUsername());
			} catch (RuntimeException e) {
				LOGGER.warn("Chat " + entry.getUserChatSessionCode() + " job: its user " + entry.getUsername()
						+ " can not be resolved, run without identity: " + e);
			}
		}
		if (user == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Chat {} job run without identity (user {})", entry.getUserChatSessionCode(),
						entry.getUsername());
			}
			job.run();
			return;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Chat {} job run as user {}", entry.getUserChatSessionCode(), user.getUsername());
		}
		IdentityUtil.create(user).doAsWith2Exceptions(job);
	}

	public class BatchSessionShrinkerProcessor implements IGBatchMessagesReceiver {
		static final Logger LOGGER = LoggerFactory.getLogger(BatchSessionShrinkerProcessor.class);

		@Override
		public void acceptMessages(GMessageEnvelope<GMessagesBatchPayload> messages) {
			Map<String, SessionShrinkRequestPayload> uniqueMap = new HashMap<String, SessionShrinkRequestPayload>();
			if (messages.getPayload() instanceof GMessagesBatchPayload batch) {
				for (int i = 0; i < batch.size(); i++) {
					Object msgpayload = batch.get(i);

					if (msgpayload instanceof GMessageEnvelope envelope
							&& envelope.getPayload() instanceof SessionShrinkRequestPayload shrinkPayload) {
						uniqueMap.put(shrinkPayload.getUserChatSessionCode()
								+ (shrinkPayload.isMinimalContextOnly() ? "|minimal" : ""), shrinkPayload);
					}
				}
			}

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Shrink batch of {} requests for {} chats", messages.getPayload() instanceof GMessagesBatchPayload b ? b.size() : 0,
						uniqueMap.size());
			}
			// a chat's state is shrunk before its minimal context is prepared: the shrink
			// replaces the history the minimal context is made of
			for (SessionShrinkRequestPayload entry : uniqueMap.values()) {
				if (!entry.isMinimalContextOnly()) {
					try {
						long start = System.currentTimeMillis();
						asChatUser(entry, users.getIfAvailable(), () -> shrinker.shrink(entry.getUserChatSessionCode(), entry.getTokensBudget()));
						LOGGER.debug("Shrunk chat {} to a {} tokens target in {} ms", entry.getUserChatSessionCode(),
								entry.getTokensBudget(), System.currentTimeMillis() - start);
					} catch (Throwable e) {
						LOGGER.error("Error shrinking " + entry.getUserChatSessionCode(), e);
					}
				}
			}
			for (SessionShrinkRequestPayload entry : uniqueMap.values()) {
				if (entry.isMinimalContextOnly()) {
					try {
						long start = System.currentTimeMillis();
						asChatUser(entry, users.getIfAvailable(), () -> shrinker.prepareMinimalContext(entry.getUserChatSessionCode(),
								entry.getTokensBudget()));
						LOGGER.debug("Prepared the minimal context of chat {} for {} tokens in {} ms",
								entry.getUserChatSessionCode(), entry.getTokensBudget(),
								System.currentTimeMillis() - start);
					} catch (Throwable e) {
						LOGGER.error("Error preparing the minimal context of " + entry.getUserChatSessionCode(), e);
					}
				}
			}
		}

	}

	public SessionShrinkMessagesReceiver(GeboChatSessionLifeCycleConfig config,
			IGChatSessionStateShrinkerService shrinker, ObjectProvider<UserDetailsService> users) {
		super(config.getSessionShrinkerReceiverConfig());
		this.shrinker = shrinker;
		this.config = config;
		this.users = users;

	}

	@Override
	public IGTimedOutMessageReceiver create() {

		return new BatchSessionShrinkMessagesReceiver(new BatchSessionShrinkerProcessor(),
				config.getSessionShrinkerReceiverConfig().getFlushThreshold());
	}

	@Override
	public List<String> getAcceptedPayloadTypes() {

		return List.of(SessionShrinkRequestPayload.class.getName());
	}

	@Override
	public boolean isAcceptEveryPayloadType() {

		return false;
	}

	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.CORE_MODULE;
	}

	@Override
	public String getMessagingSystemId() {

		return SESSION_SHRINKER;
	}

	@Override
	public SystemComponentType getComponentType() {

		return SystemComponentType.APPLICATION_COMPONENT;
	}

}
