package ai.gebo.architecture.agents.model;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * What an agent waits for when it asks to suspend the run: the value it needs
 * ({@code T}), stated for whoever resolves it and explained as the model put
 * it, and what the model chose when it decided to suspend ({@code O}), the
 * options it put to whoever resolves the condition. The condition is matched to
 * the one saved in the block of the suspended run by its id, so a resolved one
 * built from a loaded block carries the saved id.
 *
 * @param <T> the type of the value waited for
 * @param <O> the type of the options the model chose for the suspension
 */
@Data
@NoArgsConstructor
public class AgentSuspendedWaitingCondition<T, O> {
	private String suspendedConditionId = UUID.randomUUID().toString();
	private Class<T> type;
	private String waitingConditionStatement;
	private String waitedConditionInstanceLLMExplanation;
	private T instanceValue = null;
	/** The type of what the model chose for the suspension; null when it chose nothing. */
	private Class<O> optionsType = null;
	/** What the model chose when it decided to suspend: the options it put to whoever resolves the condition. */
	private O llmChosenOptions = null;

	/** A condition on a value, with nothing chosen by the model. */
	public AgentSuspendedWaitingCondition(Class<T> type, String waitingConditionStatement,
			String waitedConditionInstanceLLMExplanation, T instanceValue) {
		this(type, waitingConditionStatement, waitedConditionInstanceLLMExplanation, instanceValue, null, null);
	}

	/** A condition on a value, with what the model chose for the suspension. */
	public AgentSuspendedWaitingCondition(Class<T> type, String waitingConditionStatement,
			String waitedConditionInstanceLLMExplanation, T instanceValue, Class<O> optionsType,
			O llmChosenOptions) {
		this.type = type;
		this.waitingConditionStatement = waitingConditionStatement;
		this.waitedConditionInstanceLLMExplanation = waitedConditionInstanceLLMExplanation;
		this.instanceValue = instanceValue;
		this.optionsType = optionsType;
		this.llmChosenOptions = llmChosenOptions;
	}
}
