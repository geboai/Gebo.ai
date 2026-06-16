package ai.gebo.architecture.agents.model;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
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
		return m;
	}
}
