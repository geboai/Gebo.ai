package ai.gebo.architecture.agents.model;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AgentPrivateSessionContext  {
	@NotNull
	String id=UUID.randomUUID().toString();
	@NotNull
	String collaborationContextId;
	

}
