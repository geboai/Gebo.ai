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
