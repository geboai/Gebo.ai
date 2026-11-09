package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import java.util.LinkedHashMap;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AgentSuspendedWaitingConditionDto {
	@NotNull
	private String conditionType = null;
	@NotNull
	private String conditionId = null;
	@NotEmpty
	@NotNull
	private LinkedHashMap<String, Object> attributes = null;
	/** The attributes typed, for a condition of the {@code clarification} type; null for any other. */
	private ClarificationConditionAttributes clarificationAttributes = null;
	/** The attributes typed, for a condition of the {@code default} type; null for any other. */
	private DefaultConditionAttributes defaultAttributes = null;
}
