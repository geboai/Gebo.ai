package ai.gebo.architecture.agents.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public final class AgentPrivateSessionContext<InputType, OutputType> {
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentPrivateSessionContext.class);
	@NotNull
	String id = UUID.randomUUID().toString();
	@NotNull
	String collaborationContextId;
	private final Vector<AgentInteraction> interactions = new Vector<>();
	/**
	 * The conditions this agent waited for and that were solved, with its notes: they
	 * are not turns, a turn being what counts the cycles of a coordinator, but they
	 * stay known in every turn after the one they were told in.
	 */
	private final Vector<AgentSuspendedWaitingCondition<?, ?>> solvedConditions = new Vector<>();
	/**
	 * What the libraries held in the agent's present turn add to its prompt: set as
	 * the turn begins, it is not memory and no suspension keeps it, a respawned agent
	 * reclaims its libraries and gets it again.
	 */
	private volatile String additionalRules = "";

	@AllArgsConstructor
	@Getter
	public class AgentInteraction {
		@NotNull
		final AgentsExchangeMessage<InputType> inputMessage;
		final int contributionCounter;
		@NotNull
		final OutputType output;
	}

	public synchronized void addInteraction(AgentsExchangeMessage<InputType> inputMessage, int contributionCounter,
			OutputType payload) {
		interactions.add(new AgentInteraction(inputMessage, contributionCounter, payload));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addInteraction(...) privateContext:" + id + " contributionCounter:" + contributionCounter
					+ " outputProduced:" + (payload != null) + " total turns:" + interactions.size());
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<PRIVATE_TURN_INPUT contributionCounter=" + contributionCounter + ">");
			LOGGER.trace(String.valueOf(inputMessage != null ? inputMessage.getPayload() : null));
			LOGGER.trace("</PRIVATE_TURN_INPUT>");
			LOGGER.trace("<PRIVATE_TURN_OUTPUT contributionCounter=" + contributionCounter + ">");
			LOGGER.trace(String.valueOf(payload));
			LOGGER.trace("</PRIVATE_TURN_OUTPUT>");
		}
	}

	/**
	 * Forgets the turn of the given number when the agent said nothing in it: the
	 * record of a turn the agent asked to suspend the run on instead of answering,
	 * which is re-executed on the same message and recorded then. A turn in which
	 * the agent also produced something stays. Returns how many records went.
	 */
	public synchronized int dropEmptyTurn(int contributionCounter) {
		int dropped = 0;
		for (int index = interactions.size() - 1; index >= 0; index--) {
			final AgentInteraction interaction = interactions.get(index);
			if (interaction.getContributionCounter() == contributionCounter && isEmpty(interaction.getOutput())) {
				interactions.remove(index);
				dropped++;
			}
		}
		if (dropped > 0 && LOGGER.isDebugEnabled()) {
			LOGGER.debug("dropEmptyTurn(...) privateContext:" + id + " contributionCounter:" + contributionCounter
					+ " dropped:" + dropped + " empty record(s), total turns:" + interactions.size());
		}
		return dropped;
	}

	private static boolean isEmpty(Object output) {
		return output == null || (output instanceof CharSequence text && text.toString().isBlank());
	}

	public String getAdditionalRules() {
		return additionalRules;
	}

	public void setAdditionalRules(String additionalRules) {
		this.additionalRules = additionalRules != null ? additionalRules : "";
	}

	/** Keeps the conditions given as solved; none given changes nothing. */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public synchronized void addSolvedConditions(Collection<? extends AgentSuspendedWaitingCondition> conditions) {
		if (conditions == null) {
			return;
		}
		for (AgentSuspendedWaitingCondition condition : conditions) {
			if (condition != null) {
				solvedConditions.add(condition);
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addSolvedConditions(...) privateContext:" + id + " now holds " + solvedConditions.size()
					+ " solved condition(s)");
		}
	}

	public synchronized List<Integer> getContributionTurnNumbers() {
		TreeMap<Integer, Boolean> contribs = new TreeMap<Integer, Boolean>();
		for (AgentInteraction agentInteraction : interactions) {
			contribs.put(agentInteraction.getContributionCounter(), true);
		}
		return new ArrayList(contribs.keySet());
	}

	public Integer getLastContributionTurn() {
		List<Integer> numbers = getContributionTurnNumbers();
		if (numbers == null || numbers.isEmpty()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getLastContributionTurn() privateContext:" + id + " has no recorded turn yet");
			}
			return null;
		}
		final Integer last = numbers.get(numbers.size() - 1);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getLastContributionTurn() privateContext:" + id + " lastTurn:" + last + " over "
					+ numbers.size() + " recorded turn(s)");
		}
		return last;

	}
}
