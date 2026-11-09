package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import java.util.LinkedHashMap;

import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

import ai.gebo.architecture.agents.model.ConditionSource;
import ai.gebo.architecture.agents.model.UnansweredBecause;
import lombok.Data;

@Data
public class AgentSuspendedWaitingConditionReactionDto {
	@NotNull
	private String conditionType = null;
	@NotNull
	private String conditionId = null;
	/**
	 * What answers the condition, as its translator reads it. May be left out when
	 * the answer is given typed; custom types answer here only.
	 */
	private LinkedHashMap<String, Object> attributes = null;
	/**
	 * The answer typed, to a condition of the {@code clarification} type: it stands
	 * for the attributes it covers (see {@link ClarificationReactionAttributes#COVERED}).
	 */
	private ClarificationReactionAttributes clarificationAttributes = null;
	/**
	 * The answer typed, to a condition of the {@code default} type: it stands for the
	 * attributes it covers (see {@link DefaultReactionAttributes#COVERED}).
	 */
	private DefaultReactionAttributes defaultAttributes = null;
	/**
	 * Who or what gave the answer, as the events layer kept and verified it: set
	 * only on reactions built from stored events, never read from a client, so a
	 * caller cannot say it answers as someone else.
	 */
	@JsonIgnore
	private ConditionSource source = null;
	/**
	 * Why the condition is resolved with no value although it may be mandatory: it
	 * expired, or its owner or an administrator chose to go on without it. Set only
	 * by the platform, on reactions built from stored events; never read from a
	 * client, which may only decline a condition that is not mandatory.
	 */
	@JsonIgnore
	private UnansweredBecause unanswered = null;

	/**
	 * Puts the answer given typed in the attributes, in place of what they said of
	 * the attributes it covers: the translators, the recorder and the texts read the
	 * attributes. Doing it again changes nothing.
	 */
	public AgentSuspendedWaitingConditionReactionDto mergeTypedAttributes() {
		if (clarificationAttributes != null) {
			attributes = clarificationAttributes
					.mergeInto(attributes != null ? new LinkedHashMap<String, Object>(attributes) : null);
		}
		if (defaultAttributes != null) {
			attributes = defaultAttributes
					.mergeInto(attributes != null ? new LinkedHashMap<String, Object>(attributes) : null);
		}
		return this;
	}
}
