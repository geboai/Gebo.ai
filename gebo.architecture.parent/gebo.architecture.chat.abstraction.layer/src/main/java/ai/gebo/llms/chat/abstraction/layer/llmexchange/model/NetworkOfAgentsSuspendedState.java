package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NetworkOfAgentsSuspendedState {
	@NotNull
	private String networkOfAgentsBlockId = null;
	@NotNull
	@NotEmpty
	private List<AgentSuspendedWaitingConditionDto> conditions = null;
}
