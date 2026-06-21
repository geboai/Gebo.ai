package ai.gebo.architecture.agents.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

public final class AgentsCollaborationSessionContext {
	@Getter
	@NotNull
	private final String id = UUID.randomUUID().toString();

	@AllArgsConstructor
	@Getter
	public static class SessionContribution {
		private final int contributionUniqueNr;
		private final String agentName;
		private final Object data;
	}

	@Getter
	private int contributionCounter = 0;
	private final List<SessionContribution> contributions = new ArrayList<>();

	public synchronized void addContribution(AgentsExchangeMessage<?> msg) {
		SessionContribution contribution = new SessionContribution(++contributionCounter, msg.getFromAgent(),
				msg.getPayload());
		contributions.add(contribution);
	}

	public synchronized List<SessionContribution> getSampledContributions() {
		return new ArrayList<>(contributions);
	}
	public synchronized List<SessionContribution> getSampledContributionsAfter(int index) { 
		return getSampledContributions().stream().filter(x->x.getContributionUniqueNr()>=index).toList();
	}
	

}
