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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.services.AgentAnswersText;
import ai.gebo.architecture.agents.services.AgentAnswersText.AgentAnswer;
import ai.gebo.architecture.agents.services.GAgentCommandsSink;
import ai.gebo.architecture.agents.services.IGAgentCommands;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;

/**
 * What a loop controller keeps over one run of its network, from an execution to the
 * next: the run of its loop (the story of its iterations, its documents, its cycle),
 * the recorder of its tool calls, the commands it gave and is waiting the answers
 * of, the answers it got so far. Made by its adapter, one for each run, and loaded
 * in the tool calling of each execution with the sink of that execution's commands
 * ({@link Execution}), where the agent finds it.
 * <p>
 * It lives as long as the run: a run suspended for a person's answer and respawned
 * is another run, whose controller starts its loop over, told what it waited for.
 */
public final class LoopControllerRun {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoopControllerRun.class);
	private final AgenticLoopReactiveAgentServiceImpl.LoopRun loop = new AgenticLoopReactiveAgentServiceImpl.LoopRun();
	private ToolCallsListener calls = null;
	private final List<AgentAnswer> answers = new ArrayList<AgentAnswer>();
	private List<IGAgentCommands.Command> pending = List.of();
	private int pendingSince = -1;

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

	/** The commands the agent gave in the turn of the given number: their answers are waited. */
	synchronized void commanded(List<IGAgentCommands.Command> commands, int atTurn) {
		pending = commands != null ? List.copyOf(commands) : List.of();
		pendingSince = atTurn;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("The loop controller waits the answers to " + pending.size() + " command(s) given at turn "
					+ atTurn);
		}
	}

	/**
	 * The answers the agent got so far in the run, the ones to the commands it is
	 * waiting on read in the session first: every execution going on is told them all.
	 */
	synchronized List<AgentAnswer> answersSoFar(AgentsCollaborationSessionContext session) {
		if (!pending.isEmpty()) {
			final List<AgentAnswer> got = AgentAnswersText.answersTo(pending, session, pendingSince);
			answers.addAll(got);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The loop controller got " + got.stream().filter(x -> x.answer() != null).count()
						+ " answer(s) to the " + pending.size() + " command(s) given at turn " + pendingSince + ", "
						+ answers.size() + " so far in the run");
			}
			pending = List.of();
		}
		return List.copyOf(answers);
	}
}
