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
import java.util.List;

import ai.gebo.architecture.agents.model.AgentProducedSessionContribution;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;

/**
 * What an agent that handed work to other agents is told when it is executed again:
 * what it asked each of them and what each answered, before the message it is
 * executing, as an agent respawned on a person's answer is told the question and
 * the answer (see {@link ResolvedConditionText}).
 */
public final class AgentAnswersText {
	static final String NO_ANSWER = "(no answer: the agent failed, or is still waiting for something)";

	private AgentAnswersText() {
	}

	/** What an agent was asked and what it answered: a null answer is none. */
	public static record AgentAnswer(String agent, String request, String answer) {
	}

	/**
	 * The answers to the commands given, read in the session: what each agent
	 * commanded contributed after the turn the commands were given in, the commands to
	 * one agent taking its contributions in order. A command nothing answers has a
	 * null answer.
	 *
	 * @param commandedAt the number of the turn the commands were given in
	 */
	public static List<AgentAnswer> answersTo(List<IGAgentCommands.Command> commands,
			AgentsCollaborationSessionContext session, int commandedAt) {
		final List<AgentAnswer> answers = new ArrayList<AgentAnswer>();
		if (commands == null || session == null) {
			return answers;
		}
		final java.util.Map<String, Integer> taken = new java.util.HashMap<String, Integer>();
		for (IGAgentCommands.Command command : commands) {
			final List<AgentProducedSessionContribution> said = session.getSampledContributionOfAfter(command.toAgent(),
					commandedAt + 1);
			final int index = taken.merge(command.toAgent(), 1, Integer::sum) - 1;
			final Object data = index < said.size() ? said.get(index).getData() : null;
			final String answer = data == null || String.valueOf(data).isBlank() ? null : String.valueOf(data).strip();
			answers.add(new AgentAnswer(command.toAgent(), String.valueOf(command.commandData()), answer));
		}
		return answers;
	}

	/** One answer as the agent is told of it. */
	public static String render(AgentAnswer answer) {
		return "You asked the agent " + answer.agent() + ": " + answer.request() + "\nThe agent " + answer.agent()
				+ " answered: " + (answer.answer() != null ? answer.answer() : NO_ANSWER);
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
