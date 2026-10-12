package ai.gebo.architecture.agents.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AgentProducedSessionContribution<OutputType> {
	private final int contributionUniqueNr;
	private final String agentName;
	private final OutputType data;
	/** How the data was produced, shown with it (see {@link AgentsExchangeMessage#getStatusNotices()}). */
	private final List<String> statusNotices;

	public AgentProducedSessionContribution(int contributionUniqueNr, String agentName, OutputType data) {
		this(contributionUniqueNr, agentName, data, null);
	}
}