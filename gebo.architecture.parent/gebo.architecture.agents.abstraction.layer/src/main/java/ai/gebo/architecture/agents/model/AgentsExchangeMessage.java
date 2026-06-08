package ai.gebo.architecture.agents.model;

import java.util.List;

import ai.gebo.application.messaging.model.GBaseMessagePayload;
import ai.gebo.security.services.ReactiveIdentityUtil;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentsExchangeMessage<PayloadType> {
	public enum MessageSemantic {
		EXECUTE_AND_SHARE_RESULT, RESPONSE

	}

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
		return m;
	}
}
