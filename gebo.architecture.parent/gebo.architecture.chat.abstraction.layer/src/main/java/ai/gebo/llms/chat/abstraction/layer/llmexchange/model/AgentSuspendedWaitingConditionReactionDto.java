package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import java.util.LinkedHashMap;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AgentSuspendedWaitingConditionReactionDto {
	@NotNull
	private String conditionType = null;
	@NotNull
	private String conditionId = null;
	@NotEmpty
	@NotNull
	private LinkedHashMap<String, Object> attributes = null;
}
