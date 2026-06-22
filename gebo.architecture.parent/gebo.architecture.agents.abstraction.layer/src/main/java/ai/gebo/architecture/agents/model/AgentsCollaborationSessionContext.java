package ai.gebo.architecture.agents.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public final class AgentsCollaborationSessionContext {
	@Getter
	@NotNull
	private final String id = UUID.randomUUID().toString();

	private int contributionCounter = 0;
	private final List<AgentProducedSessionContribution> contributions = new ArrayList<>();

	public synchronized void addContribution(AgentsExchangeMessage<?> msg, int contributionNr) {
		AgentProducedSessionContribution contribution = new AgentProducedSessionContribution(contributionNr,
				msg.getFromAgent(), msg.getPayload());
		contributions.add(contribution);
	}

	public synchronized List<AgentProducedSessionContribution> getSampledContributions() {
		return new ArrayList<>(contributions);
	}

	public List<AgentProducedSessionContribution> getSampledContributionsAfter(int index) {
		return getSampledContributions().stream().filter(x -> x.getContributionUniqueNr() >= index).toList();
	}

	public List<AgentProducedSessionContribution> getSampledContributionOf(String agentName) {
		return getSampledContributions().stream()
				.filter(x -> x.getAgentName() != null && agentName != null && x.getAgentName().equals(agentName))
				.toList();
	}

	public List<AgentProducedSessionContribution> getSampledContributionOfAfter(String agentName, int index) {
		return getSampledContributions().stream().filter(x -> x.getContributionUniqueNr() >= index
				&& x.getAgentName() != null && agentName != null && x.getAgentName().equals(agentName)).toList();
	}

	public synchronized int getContributionCounter() {
		return contributionCounter;
	}

	public synchronized int getAndIncrementContributionNr() {
		return contributionCounter++;
	}
}
