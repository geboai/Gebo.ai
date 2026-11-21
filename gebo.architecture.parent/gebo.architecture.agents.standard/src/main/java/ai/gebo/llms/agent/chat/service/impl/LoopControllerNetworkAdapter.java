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
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.services.AgentAnswersText;
import ai.gebo.architecture.agents.services.AgentAnswersText.AgentAnswer;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.GAgentCommandsSink;
import ai.gebo.architecture.agents.services.IGAgentCommands;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGReactiveAgentService;
import ai.gebo.architecture.agents.tools.AgentCommandTools;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import reactor.core.publisher.Sinks;

/**
 * The network adapter of a loop controller: the report writer's one, streaming the
 * agent's text to the user and giving the network its answer, that also lets the
 * agent command the agents of its communication list.
 * <p>
 * At each turn of an agent that is the controller of its network and reaches some
 * agent, it loads in the turn's tool calling one tool for each agent reached (see
 * {@link AgentCommandTools}), the sink their commands go to and the
 * {@link LoopControllerRun} of the run, for the agent to find. When the turn ended on commands, it gives the
 * network the commands, in place of the agent's answer, and a message executing the
 * agent again after them, on the message of the turn: the agent is then told what
 * each agent answered, before that message.
 * <p>
 * An agent that is not the controller, or reaches no agent taking a text, is
 * adapted as the report writer is.
 */
public class LoopControllerNetworkAdapter extends ReportWriterReactiveAgentServiceNetworkAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoopControllerNetworkAdapter.class);
	/** The run of the agent: one adapter is made for each run of the network. */
	private final LoopControllerRun run = new LoopControllerRun();

	public LoopControllerNetworkAdapter(IGReactiveAgentService<String, GeboChatMessageEnvelope> service,
			Sinks.Many<IGPartialOperation<GeboChatMessageEnvelope>> sink) {
		super(service, sink);
	}

	@Override
	protected TurnAdditions<String, GeboChatMessageEnvelope> turnAdditions(AgentsExchangeMessage<String> msg,
			int actualContributionNr, GAgentsNetwork network, AgentNetworkParticipant contextAgentPersona,
			AgentsCollaborationSessionContext session, IGAgentsNetworkRuntimeDao agentsDao) throws AgentException {
		if (contextAgentPersona == null || !contextAgentPersona.isController()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Loop controller adapter id:" + getId() + " agent:"
						+ (contextAgentPersona != null ? contextAgentPersona.getNetworkAgentName() : null)
						+ " is not the controller of the network: it commands no agent");
			}
			return super.turnAdditions(msg, actualContributionNr, network, contextAgentPersona, session, agentsDao);
		}
		final List<String> reachable = AgentCommandTools.reachable(contextAgentPersona, agentsDao);
		if (reachable.isEmpty()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Loop controller adapter id:" + getId() + " agent:"
						+ contextAgentPersona.getNetworkAgentName() + " reaches no agent taking a text: it commands none");
			}
			return super.turnAdditions(msg, actualContributionNr, network, contextAgentPersona, session, agentsDao);
		}
		final String self = contextAgentPersona.getNetworkAgentName();
		final List<AgentAnswer> answers = run.answersSoFar(session);
		final GAgentCommandsSink commands = new GAgentCommandsSink(reachable);
		final List<ToolCallback> tools = AgentCommandTools.of(contextAgentPersona, agentsDao);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Loop controller adapter id:" + getId() + " agent:" + self + " turn " + actualContributionNr
					+ " may command " + reachable + ", told " + answers.size() + " answer(s) of the run so far");
		}
		return new TurnAdditions<String, GeboChatMessageEnvelope>() {
			@Override
			public GeboToolCalling toolCalling(GeboToolCalling ofTheTurn) {
				return GeboToolCalling.merged(GeboToolCalling.builder().provide(IGAgentCommands.class, commands)
						.provide(LoopControllerRun.Execution.class, run.execution(commands))
						.mandatoryTools(tools).build(), ofTheTurn);
			}

			@Override
			public String request(String ofTheTurn) {
				return AgentAnswersText.prepend(answers, ofTheTurn);
			}

			/** The loop's model reads the request from the context: the answers are told there too. */
			@Override
			public IChatRequestContext context(IChatRequestContext ofTheTurn, String request) {
				return answers.isEmpty() || ofTheTurn == null ? ofTheTurn : ofTheTurn.withActualUserRequest(request);
			}

			@Override
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public List<AgentsExchangeMessage<GeboChatMessageEnvelope>> messages(GeboChatMessageEnvelope response) {
				if (!commands.isCommanding()) {
					return null;
				}
				final List<IGAgentCommands.Command> given = commands.commanded();
				final List out = new ArrayList();
				for (IGAgentCommands.Command command : given) {
					// delivered first, together: the agents commanded work at the same time
					out.add(AgentsExchangeMessage.of(session, self, command.toAgent(), command.commandData(),
							MessageSemantic.EXECUTE_AND_SHARE_RESULT));
				}
				// then the agent is executed again, on the message of this turn
				final AgentsExchangeMessage again = AgentsExchangeMessage.of(session, self, self, msg.getPayload(),
						MessageSemantic.EXECUTE_AND_SHARE_RESULT);
				again.setExecutionOrder(2);
				out.add(again);
				run.commanded(given, actualContributionNr);
				LOGGER.info("Loop controller adapter id:" + getId() + " agent:" + self + " turn " + actualContributionNr
						+ " hands work to " + given.stream().map(IGAgentCommands.Command::toAgent).toList()
						+ " and is executed again after them");
				return out;
			}
		};
	}
}
