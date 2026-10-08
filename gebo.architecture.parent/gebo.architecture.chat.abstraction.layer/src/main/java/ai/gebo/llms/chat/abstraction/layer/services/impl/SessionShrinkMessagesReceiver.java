package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.GAbstractTimedOutMessageReceiverFactory;
import ai.gebo.application.messaging.IGBatchMessagesReceiver;
import ai.gebo.application.messaging.IGTimedOutMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GMessagesBatchPayload;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatSessionLifeCycleConfig;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionStateShrinkerService;

@Component
@Scope("singleton")
public class SessionShrinkMessagesReceiver extends GAbstractTimedOutMessageReceiverFactory {
	static final String SESSION_SHRINKER = "session-shrinker";
	private final IGChatSessionStateShrinkerService shrinker;
	private final GeboChatSessionLifeCycleConfig config;

	class BatchSessionShrinkMessagesReceiver extends GNestedBatchAggregatorMessageReceiver {

		public BatchSessionShrinkMessagesReceiver(IGBatchMessagesReceiver nested, int flushThreshold) {
			super(nested, flushThreshold);

		}

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
						shrinker.shrink(entry.getUserChatSessionCode(), entry.getTokensBudget());
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
						shrinker.prepareMinimalContext(entry.getUserChatSessionCode(), entry.getTokensBudget());
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
			IGChatSessionStateShrinkerService shrinker) {
		super(config.getSessionShrinkerReceiverConfig());
		this.shrinker = shrinker;
		this.config = config;

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
