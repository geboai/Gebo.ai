package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import java.util.LinkedHashMap;

import ai.gebo.architecture.agents.model.ConditionTarget;

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
	/**
	 * Whether the condition is a human in the loop request handled as a process,
	 * shown and answerable in the agents processes area as well as in the chat.
	 */
	private boolean humanInTheLoopProcessTreatment = false;
	/** Who or what the condition is waited from: only they may answer it, or an administrator; null for the usual answerers. */
	private ConditionTarget target = null;
	@NotEmpty
	@NotNull
	private LinkedHashMap<String, Object> attributes = null;
	/** The attributes typed, for a condition of the {@code clarification} type; null for any other. */
	private ClarificationConditionAttributes clarificationAttributes = null;
	/** The attributes typed, for a condition of the {@code default} type; null for any other. */
	private DefaultConditionAttributes defaultAttributes = null;
}
