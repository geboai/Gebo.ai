package ai.gebo.architecture.agents.model;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentsExchangeMessage<PayloadType> {
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentsExchangeMessage.class);

	public enum MessageSemantic {
		EXECUTE_AND_SHARE_RESULT, RESPONSE
	}
	@NotNull
	private final String id = UUID.randomUUID().toString();
	@NotNull
	private String collaborationContextId;
	@NotNull
	private MessageSemantic messageSemantic;
	@NotNull
	private String fromAgent;
	@NotNull
	private GAgentRole fromAgentRole;
	@NotNull
	private String toAgent;
	@NotNull
	private PayloadType payload;
	private int executionOrder = 0;
	public static <PayloadType> AgentsExchangeMessage<PayloadType> of(AgentsCollaborationSessionContext context,
			String targetAgent, PayloadType data, MessageSemantic messageSemantic) {
		AgentsExchangeMessage<PayloadType> m = new AgentsExchangeMessage<PayloadType>();
		m.setCollaborationContextId(context.getId());
		m.setMessageSemantic(messageSemantic);
		m.setPayload(data);
		m.setExecutionOrder(1);
		m.setToAgent(targetAgent);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Built an agents exchange message id:" + m.getId() + " to:" + targetAgent + " semantic:"
					+ messageSemantic + " session:" + context.getId());
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<EXCHANGE_MESSAGE_PAYLOAD to=" + targetAgent + " semantic=" + messageSemantic + ">");
			LOGGER.trace(String.valueOf(data));
			LOGGER.trace("</EXCHANGE_MESSAGE_PAYLOAD>");
		}
		return m;
	}
}
