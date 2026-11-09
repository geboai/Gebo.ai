package ai.gebo.architecture.agents.model;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * What an agent waits for when it asks to suspend the run: the value it needs
 * ({@code T}), stated for whoever resolves it and explained as the model put
 * it, and what the model chose when it decided to suspend ({@code O}), the
 * options it put to whoever resolves the condition. A mandatory condition holds
 * the conversation until it is resolved: nothing else happens there, and it is
 * shown again whenever the conversation is opened; one that is not leaves the
 * conversation free to go on, the run being dropped when it does. The condition
 * is matched to the one saved in the block of the suspended run by its id, so a
 * resolved one built from a loaded block carries the saved id.
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
	/**
	 * Whether the conversation waits for this before anything else can happen in
	 * it, or may go on without it. Required: an agent that asks says which.
	 */
	private boolean mandatory = true;
	/** The type of what the model chose for the suspension; null when it chose nothing. */
	private Class<O> optionsType = null;
	/** What the model chose when it decided to suspend: the options it put to whoever resolves the condition. */
	private O llmChosenOptions = null;
	/**
	 * What the agent wrote to itself when it stopped: given back to it with the
	 * resolved condition, never shown to whoever resolves it. Null when it wrote nothing.
	 */
	private String notesForLater = null;
	/**
	 * Who or what solved it, when the respawn was told: null when it was not, or
	 * while it waits. The agent is told it, unless it is the conversation's own user.
	 */
	private ConditionSource solvedBy = null;
	/**
	 * The type of the condition: the id of the translator that shows it to the
	 * client and reads the answers to it. Kept when the run is suspended, so that an
	 * answer is read by the translator the question was shown with; null until then,
	 * and in the blocks kept before it was, which find their translator by kind.
	 */
	private String conditionType = null;

	/** A condition on a value, with nothing chosen by the model. */
	public AgentSuspendedWaitingCondition(Class<T> type, String waitingConditionStatement,
			String waitedConditionInstanceLLMExplanation, T instanceValue, boolean mandatory) {
		this(type, waitingConditionStatement, waitedConditionInstanceLLMExplanation, instanceValue, mandatory, null,
				null);
	}

	/** A condition on a value, with what the model chose for the suspension. */
	public AgentSuspendedWaitingCondition(Class<T> type, String waitingConditionStatement,
			String waitedConditionInstanceLLMExplanation, T instanceValue, boolean mandatory, Class<O> optionsType,
			O llmChosenOptions) {
		this.type = type;
		this.waitingConditionStatement = waitingConditionStatement;
		this.waitedConditionInstanceLLMExplanation = waitedConditionInstanceLLMExplanation;
		this.instanceValue = instanceValue;
		this.mandatory = mandatory;
		this.optionsType = optionsType;
		this.llmChosenOptions = llmChosenOptions;
	}
}
