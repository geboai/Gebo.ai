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
						uniqueMap.put(shrinkPayload.getUserChatSessionCode(), shrinkPayload);
					}
				}
			}

			for (SessionShrinkRequestPayload entry : uniqueMap.values()) {
				try {
					shrinker.shrink(entry.getUserChatSessionCode(), entry.getTokensBudget());
				} catch (Throwable e) {
					LOGGER.error("Error shrinking " + entry.getUserChatSessionCode(), e);
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
