package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import java.util.LinkedHashMap;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

import ai.gebo.architecture.agents.model.ConditionSource;
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
	/**
	 * Who or what gave the answer, as the events layer kept and verified it: set
	 * only on reactions built from stored events, never read from a client, so a
	 * caller cannot say it answers as someone else.
	 */
	@JsonIgnore
	private ConditionSource source = null;
}
