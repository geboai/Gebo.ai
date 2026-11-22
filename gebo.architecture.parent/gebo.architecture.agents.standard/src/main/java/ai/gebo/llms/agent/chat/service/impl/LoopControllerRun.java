/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.chat.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.services.AgentAnswersText;
import ai.gebo.architecture.agents.services.AgentAnswersText.AgentAnswer;
import ai.gebo.architecture.agents.services.GAgentCommandsSink;
import ai.gebo.architecture.agents.services.IGAgentCommands;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;

/**
 * What a loop controller keeps in memory from an execution to the next, while the
 * service running its network lives: the run of its loop (the story of its
 * iterations, its documents, its cycle), the recorder of its tool calls, what it
 * asked each agent. Made by its adapter, one for each service, and loaded in the
 * tool calling of each execution with the sink of the commands of that execution
 * ({@link Execution}), where the agent finds it.
 * <p>
 * What the agents answered is not kept here: it is read, at each execution, in the
 * shared session of the network, from the first turn the controller made, which its
 * private session keeps. A run suspended for a person's answer is respawned in
 * another service, with those two sessions given back whole: the controller is then
 * told the same answers, and its loop, whose story was in memory, starts again after
 * as many iterations as the turns its private session says it made.
 */
public final class LoopControllerRun {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoopControllerRun.class);
	private final AgenticLoopReactiveAgentServiceImpl.LoopRun loop = new AgenticLoopReactiveAgentServiceImpl.LoopRun();
	private ToolCallsListener calls = null;
	/** What the controller asked each agent, by its name, in the order it did: to tell which requests got no answer. */
	private final Map<String, List<String>> asked = new HashMap<String, List<String>>();

	/**
	 * What the adapter loads in the tool calling of one execution of the agent, for the
	 * agent to find: the run, the same at every execution, and the sink the commands of
	 * this execution go to.
	 */
	public interface Execution {
		LoopControllerRun run();

		GAgentCommandsSink commands();
	}

	/** This run in one execution of the agent, whose commands go to the given sink. */
	Execution execution(GAgentCommandsSink commands) {
		final LoopControllerRun run = this;
		return new Execution() {
			@Override
			public LoopControllerRun run() {
				return run;
			}

			@Override
			public GAgentCommandsSink commands() {
				return commands;
			}
		};
	}

	/** The run of the agent's loop, the same at every execution. */
	AgenticLoopReactiveAgentServiceImpl.LoopRun loop() {
		return loop;
	}

	/**
	 * The recorder of the agent's tool calls over the whole run: the one its first
	 * execution was given, kept, so that an execution going on knows the calls of the
	 * ones before it.
	 */
	synchronized ToolCallsListener callsOf(ToolCallsListener ofTheExecution) {
		if (calls == null) {
			calls = ofTheExecution;
		}
		return calls;
	}

	/** The commands the agent gave in an execution: remembered, to tell which of them got no answer. */
	synchronized void commanded(List<IGAgentCommands.Command> commands) {
		if (commands == null) {
			return;
		}
		for (IGAgentCommands.Command command : commands) {
			asked.computeIfAbsent(command.toAgent(), k -> new ArrayList<String>())
					.add(String.valueOf(command.commandData()));
		}
	}

	/**
	 * What the agents the controller reaches answered so far, as an execution going on
	 * is told: read in the shared session from the first turn the controller made in the
	 * run, which its private session keeps. What it asked is not told again: the story
	 * of its loop already gives it, with the call that asked. The loop is also made to
	 * go on after as many iterations as the turns
	 * the private session says the controller made, when it ran fewer here: what a
	 * respawned run finds.
	 */
	synchronized List<AgentAnswer> answersSoFar(AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<?, ?> privateSession, Collection<String> reachable) {
		return answersSoFar(session, privateSession, reachable, null);
	}

	/**
	 * The same, with what the controller itself settled with the user in the run: after
	 * a respawn it goes on from its request, which may still say to ask.
	 *
	 * @param self the network name of the controller
	 */
	synchronized List<AgentAnswer> answersSoFar(AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<?, ?> privateSession, Collection<String> reachable, String self) {
		final int turns = AgentAnswersText.turnsOf(privateSession);
		if (loop.iterations() < turns) {
			LOGGER.info("The loop controller made " + turns + " turn(s) in the run its private session keeps and "
					+ loop.iterations() + " iteration(s) in this service: its loop goes on after " + turns);
			loop.startAfter(turns);
		}
		final List<AgentAnswer> answers = AgentAnswersText
				.answersIn(session, AgentAnswersText.firstTurnOf(privateSession), reachable, asked, self).stream()
				.map(x -> new AgentAnswer(x.agent(), null, x.answer())).toList();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("The loop controller is told " + answers.stream().filter(x -> x.answer() != null).count()
					+ " answer(s) of the agents " + reachable + " read in the session, "
					+ answers.stream().filter(x -> x.answer() == null).count() + " request(s) not answered");
		}
		return answers;
	}
}
