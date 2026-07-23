package ai.gebo.architecture.agents.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AgentProducedSessionContribution<OutputType> {
	private final int contributionUniqueNr;
	private final String agentName;
	private final OutputType data;
}