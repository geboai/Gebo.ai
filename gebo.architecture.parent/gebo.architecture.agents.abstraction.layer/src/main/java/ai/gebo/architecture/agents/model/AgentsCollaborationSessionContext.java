package ai.gebo.architecture.agents.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public final class AgentsCollaborationSessionContext {
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentsCollaborationSessionContext.class);
	@Getter
	@NotNull
	private final String id = UUID.randomUUID().toString();

	private int contributionCounter = 0;
	private final TreeMap<Integer, List<AgentProducedSessionContribution>> contributions = new TreeMap<Integer, List<AgentProducedSessionContribution>>();
	@Getter
	private final Map<String, Object> environment = new HashMap<String, Object>();

	public synchronized void addContribution(AgentsExchangeMessage<?> msg, int contributionNr) {
		AgentProducedSessionContribution contribution = new AgentProducedSessionContribution(contributionNr,
				msg.getFromAgent(), msg.getPayload());
		contributions.computeIfAbsent(contributionNr, (c) -> new ArrayList<AgentProducedSessionContribution>());
		contributions.get(contributionNr).add(contribution);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addContribution(...) session:" + id + " contributionNr:" + contributionNr + " fromAgent:"
					+ msg.getFromAgent() + " the shared context now spans " + contributions.size() + " turn(s)");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<SHARED_CONTRIBUTION nr=" + contributionNr + " agent=" + msg.getFromAgent() + ">");
			LOGGER.trace(String.valueOf(msg.getPayload()));
			LOGGER.trace("</SHARED_CONTRIBUTION>");
		}
	}

	public synchronized List<AgentProducedSessionContribution> getSampledContributions() {
		List<AgentProducedSessionContribution> listified = new ArrayList<AgentProducedSessionContribution>();
		for (List<AgentProducedSessionContribution> c : contributions.values()) {
			listified.addAll(c);
		}
		return List.copyOf(listified);
	}

	public List<AgentProducedSessionContribution> getSampledContributionsAfter(int index) {
		List<AgentProducedSessionContribution> sampled = getSampledContributions().stream()
				.filter(x -> x.getContributionUniqueNr() >= index).toList();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getSampledContributionsAfter(" + index + ") session:" + id + " returned " + sampled.size()
					+ " contribution(s)");
		}
		return sampled;
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
		final int allocated = contributionCounter++;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getAndIncrementContributionNr() session:" + id + " allocated contributionNr:" + allocated);
		}
		return allocated;
	}
}
