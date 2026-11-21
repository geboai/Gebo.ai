/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentProducedSessionContribution;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;

/**
 * What an agent that handed work to other agents is told when it is executed again:
 * what each of them answered, and what it asked when that is known, before the
 * message it is executing, as an agent respawned on a person's answer is told the
 * question and the answer (see {@link ResolvedConditionText}).
 * <p>
 * The answers are read where the network keeps them: the shared session, which holds
 * what every agent contributed, from the first turn the agent made in the run, which
 * its private session holds. Both outlive a suspension - a respawned run is given
 * them back whole - so an agent executed again after one is told the same answers.
 */
public final class AgentAnswersText {
	static final String NO_ANSWER = "(no answer: the agent failed, or is still waiting for something)";

	private AgentAnswersText() {
	}

	/** What an agent answered, and what it was asked: a null request is one not known, a null answer is none. */
	public static record AgentAnswer(String agent, String request, String answer) {
	}

	/**
	 * The number of the first turn the agent made in the run, as its private session
	 * keeps it: null when it made none yet. The session may remember earlier runs of
	 * the conversation: what the other agents contributed before this turn answers
	 * nothing the agent asked in this one.
	 */
	public static Integer firstTurnOf(AgentPrivateSessionContext<?, ?> privateSession) {
		if (privateSession == null) {
			return null;
		}
		final List<Integer> turns = privateSession.getContributionTurnNumbers();
		return turns == null || turns.isEmpty() ? null : turns.get(0);
	}

	/** How many turns the agent made in the run, as its private session keeps them. */
	public static int turnsOf(AgentPrivateSessionContext<?, ?> privateSession) {
		if (privateSession == null) {
			return 0;
		}
		final List<Integer> turns = privateSession.getContributionTurnNumbers();
		return turns != null ? turns.size() : 0;
	}

	/**
	 * What the agents given contributed to the session after the given turn, in the
	 * order they did: the answers to what an agent that made that turn handed them.
	 * Each is given with what the agent was asked, when the requests are known: the
	 * requests to an agent, in the order they were given, go with its contributions
	 * in the order it made them, and a request no contribution answers has a null
	 * answer.
	 *
	 * @param afterTurn the number of the first turn of the agent asking; null, an
	 *                  agent that made no turn yet, has no answer
	 * @param agents    the network names of the agents asked
	 * @param asked     what each agent was asked, by its name, when known: null or
	 *                  empty leaves the requests untold
	 */
	@SuppressWarnings("rawtypes")
	public static List<AgentAnswer> answersIn(AgentsCollaborationSessionContext session, Integer afterTurn,
			Collection<String> agents, Map<String, List<String>> asked) {
		final List<AgentAnswer> answers = new ArrayList<AgentAnswer>();
		if (session == null || afterTurn == null || agents == null) {
			return answers;
		}
		final Map<String, Integer> taken = new HashMap<String, Integer>();
		for (AgentProducedSessionContribution said : session.getSampledContributionsAfter(afterTurn + 1)) {
			final String agent = said.getAgentName();
			if (agent == null || !agents.contains(agent)) {
				continue;
			}
			final int index = taken.merge(agent, 1, Integer::sum) - 1;
			answers.add(new AgentAnswer(agent, requestOf(asked, agent, index), textOf(said.getData())));
		}
		if (asked != null) {
			for (String agent : agents) {
				final List<String> requests = asked.get(agent);
				for (int index = taken.getOrDefault(agent, 0); requests != null && index < requests.size(); index++) {
					answers.add(new AgentAnswer(agent, requests.get(index), null));
				}
			}
		}
		return answers;
	}

	private static String requestOf(Map<String, List<String>> asked, String agent, int index) {
		final List<String> requests = asked != null ? asked.get(agent) : null;
		return requests != null && index < requests.size() ? requests.get(index) : null;
	}

	private static String textOf(Object data) {
		return data == null || String.valueOf(data).isBlank() ? null : String.valueOf(data).strip();
	}

	/** One answer as the agent is told of it. */
	public static String render(AgentAnswer answer) {
		return (answer.request() != null ? "You asked the agent " + answer.agent() + ": " + answer.request() + "\n" : "")
				+ "The agent " + answer.agent() + " answered: " + (answer.answer() != null ? answer.answer() : NO_ANSWER);
	}

	/** The message an agent is executed again on, the answers of the agents it asked told first. */
	public static String prepend(List<AgentAnswer> answers, String message) {
		if (answers == null || answers.isEmpty()) {
			return message;
		}
		final String told = String.join("\n\n", answers.stream().map(AgentAnswersText::render).toList());
		return message != null && !message.isBlank() ? told + "\n\n" + message : told;
	}
}
