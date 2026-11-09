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
	/**
	 * Whether the conditions are answered one at a time, by the chat and by other
	 * sources alike: an answer may leave some open, and those another source solved
	 * say so (see the {@code solved} attributes of each condition).
	 */
	private boolean answersOneByOne = false;
}
