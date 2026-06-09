package ai.gebo.architecture.agents.model;

import java.util.UUID;
import java.util.Vector;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public final class AgentPrivateSessionContext<InputType,OutputType> {
	@NotNull
	String id = UUID.randomUUID().toString();
	@NotNull
	String collaborationContextId;
	private final Vector<AgentInteraction> interactions = new Vector<>();	
	@AllArgsConstructor
	@Getter
	class AgentInteraction {
		@NotNull
		final AgentsExchangeMessage<InputType> inputMessage;
		@NotNull
		final OutputType output;
	}
	public void addInteraction(AgentsExchangeMessage<InputType> inputMessage, OutputType payload) {
		interactions.add(new AgentInteraction(inputMessage, payload));
	}
}
