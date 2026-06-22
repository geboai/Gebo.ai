package ai.gebo.architecture.agents.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Vector;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public final class AgentPrivateSessionContext<InputType, OutputType> {
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
		if (numbers == null || numbers.isEmpty())
			return null;
		return numbers.get(numbers.size() - 1);

	}
}
