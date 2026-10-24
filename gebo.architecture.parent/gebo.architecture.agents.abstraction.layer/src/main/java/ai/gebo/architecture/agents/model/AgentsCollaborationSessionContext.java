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
	private final String id;

	private int contributionCounter = 0;
	private final TreeMap<Integer, List<AgentProducedSessionContribution>> contributions = new TreeMap<Integer, List<AgentProducedSessionContribution>>();
	@Getter
	private final Map<String, Object> environment = new HashMap<String, Object>();
	/**
	 * Told of every contribution as it is recorded, when someone outside asked to
	 * be (see {@link #setContributionListener}). Volatile because the agents of a
	 * network run on several threads.
	 */
	private volatile IAgentsSessionContributionListener contributionListener = null;

	/**
	 * Told of each contribution of a session as it is recorded, so that a caller
	 * living longer than the run can follow the collaboration while it happens
	 * instead of only seeing what the network finally returned.
	 * <p>
	 * Called outside the session's own lock, so an implementation is free to take
	 * its time; what it throws is logged and swallowed, because recording what the
	 * agents said must never be able to break the run that is saying it.
	 */
	public static interface IAgentsSessionContributionListener {
		void contributionAdded(AgentsCollaborationSessionContext session, AgentProducedSessionContribution contribution);
	}

	/** A session of its own, with a fresh identity and no past: what a plain run uses. */
	public AgentsCollaborationSessionContext() {
		this(null, null, 0);
	}

	/**
	 * A session whose identity is given by the caller, so the run is traceable to
	 * something that caller knows. A blank id is no identity at all and a fresh one
	 * is used instead.
	 */
	public AgentsCollaborationSessionContext(String id) {
		this(id, null, 0);
	}

	/**
	 * A session that continues earlier work: the contributions given are replayed
	 * into it, so every agent reading its shared context sees them and the
	 * collaboration goes on rather than starting over.
	 * <p>
	 * {@code contributionCounterStart} is where this session's own turns begin. It
	 * is raised past the highest replayed turn when it was left behind it, because
	 * two contributions sharing a turn number would make the shared context read as
	 * if two agents had answered the same question.
	 *
	 * @param id                       the session's identity, or {@code null} for a
	 *                                 fresh one
	 * @param priorContributions       what was contributed before, or {@code null}
	 *                                 for no past
	 * @param contributionCounterStart the first turn number this session hands out
	 */
	public AgentsCollaborationSessionContext(String id, List<AgentProducedSessionContribution> priorContributions,
			int contributionCounterStart) {
		this.id = id != null && !id.isBlank() ? id : UUID.randomUUID().toString();
		int highestReplayed = -1;
		int replayed = 0;
		if (priorContributions != null) {
			for (AgentProducedSessionContribution contribution : priorContributions) {
				if (contribution == null) {
					continue;
				}
				final int turn = contribution.getContributionUniqueNr();
				contributions.computeIfAbsent(turn, (c) -> new ArrayList<AgentProducedSessionContribution>());
				contributions.get(turn).add(contribution);
				highestReplayed = Math.max(highestReplayed, turn);
				replayed++;
			}
		}
		this.contributionCounter = Math.max(contributionCounterStart, highestReplayed + 1);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Opened session:" + this.id + " with " + replayed + " replayed contribution(s) over "
					+ contributions.size() + " turn(s), next contributionNr:" + this.contributionCounter
					+ (id != null && !id.isBlank() ? " (identity given by the caller)" : " (fresh identity)"));
		}
		if (this.contributionCounter != contributionCounterStart && LOGGER.isWarnEnabled()) {
			LOGGER.warn("Session:" + this.id + " was asked to start its turns at " + contributionCounterStart
					+ " but the contributions replayed into it already reach turn " + highestReplayed
					+ ": its turns start at " + this.contributionCounter + " so that no two of them collide");
		}
	}

	/**
	 * The listener told of every contribution recorded from now on, or {@code null}
	 * to stop telling anyone. Set before the run starts.
	 */
	public void setContributionListener(IAgentsSessionContributionListener contributionListener) {
		this.contributionListener = contributionListener;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setContributionListener(...) session:" + id + " listening:" + (contributionListener != null));
		}
	}

	public void addContribution(AgentsExchangeMessage<?> msg, int contributionNr) {
		final AgentProducedSessionContribution contribution = recordContribution(msg, contributionNr);
		// Outside the lock on purpose: a listener that persists what it is told must
		// not hold up the agents still contributing to the same session.
		notifyContributionAdded(contribution);
	}

	private synchronized AgentProducedSessionContribution recordContribution(AgentsExchangeMessage<?> msg,
			int contributionNr) {
		AgentProducedSessionContribution contribution = new AgentProducedSessionContribution(contributionNr,
				msg.getFromAgent(), msg.getPayload(), msg.getStatusNotices());
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
		return contribution;
	}

	private void notifyContributionAdded(AgentProducedSessionContribution contribution) {
		final IAgentsSessionContributionListener listener = contributionListener;
		if (listener == null || contribution == null) {
			return;
		}
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Telling the contribution listener of session:" + id + " of contributionNr:"
						+ contribution.getContributionUniqueNr() + " fromAgent:" + contribution.getAgentName());
			}
			listener.contributionAdded(this, contribution);
		} catch (Throwable listenerFailure) {
			LOGGER.error("The contribution listener of session:" + id + " failed on contributionNr:"
					+ contribution.getContributionUniqueNr() + "; the run goes on", listenerFailure);
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
