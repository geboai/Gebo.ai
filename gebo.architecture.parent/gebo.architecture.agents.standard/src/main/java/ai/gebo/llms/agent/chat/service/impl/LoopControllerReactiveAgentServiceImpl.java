/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.chat.service.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.agent.chat.service.ILoopControllerReactiveAgentService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;

/**
 * The agent with tools working in a loop that is also the controller of a network of
 * other agents: beside its own tools it is given one tool for each agent of its
 * communication list (see {@code AgentCommandTools}), and hands them the work they
 * are made for - writing a document of a kind, for one - while it answers the user.
 * <p>
 * An iteration in which it hands work over stops the loop before its answer: its
 * adapter ({@link LoopControllerNetworkAdapter}) gives the network the commands in
 * place of an answer, the network delivers them and executes the agent again once
 * the agents commanded answered. The loop then goes on from the iteration after the
 * one it stopped at - its story, its documents and the cycle of its answer kept in
 * the {@link LoopControllerRun} of the run - told what each agent answered. Its last
 * iteration hands no work over: it writes the answer.
 * <p>
 * Executed with no run loaded, by anything but its adapter, or in a network where
 * it reaches no agent, it is the plain loop.
 */
@Service
public class LoopControllerReactiveAgentServiceImpl extends AgenticLoopReactiveAgentServiceImpl
		implements ILoopControllerReactiveAgentService {
	public static final String LOOP_CONTROLLER_NETWORK_AGENT_SERVICE = "LoopControllerNetworkAgentService";
	private static final String DESCRIPTION = "Agent that operates its tools in a loop, hands work to the agents it coordinates and decides by itself when the answer is complete";
	/** Why an iteration handing work over stops the loop, as the iterations after it are told. */
	static final String HANDED_WORK_OVER = "you handed work to other agents here and waited for them; their answers"
			+ " are given before the user's request.";
	/** What a command is refused with in the last iteration, which no execution follows. */
	static final String LAST_STEP = "This is your last step: no work can be handed to another agent any more."
			+ " Write the answer with what you have, saying what could not be done.";

	public LoopControllerReactiveAgentServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGRuntimeBinder runtimeBinder, IGSecurityService securityService, IAgentRoleDao agentRoleDao,
			IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, runtimeBinder, securityService, agentRoleDao,
				rendererFactory);
	}

	@Override
	public String getId() {
		return LOOP_CONTROLLER_NETWORK_AGENT_SERVICE;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	public AgentCapabilities getAgentCapabilities(GAgentConfig agentConfig) {
		AgentCapabilities capabilities = super.getAgentCapabilities(agentConfig);
		capabilities.addCapability("Hand work to the agents it coordinates and answer the user with what they did");
		return capabilities;
	}

	/** What the adapter loaded in the tool calling of the execution: null when it is not the adapter executing it. */
	private static LoopControllerRun.Execution loaded(GeboToolCalling toolCalling) {
		return toolCalling != null && toolCalling.getProvider() != null
				? toolCalling.getProvider().get(LoopControllerRun.Execution.class)
				: null;
	}

	/**
	 * The loop over the run its adapter keeps: the first execution of a run is the loop
	 * from its start, an execution after one that handed work over goes on from there,
	 * with the recorder of the tool calls of the run in place of its own.
	 */
	@Override
	protected Flux<IGPartialOperation<GeboChatMessageEnvelope>> createResponse(IChatRequestContext chatRequestContext,
			GAgentConfig agentConfig, String request, GAgentsNetwork network,
			AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<String, GeboChatMessageEnvelope> mySessionContext,
			IGConfigurableChatModel agentModel, GAgentRole agentRole, GPromptTemplateConfig agentPrompt,
			ReactiveIdentityUtil runAs, ToolCallsListener callBacksListener, GeboToolCalling toolCalling)
			throws LLMConfigException, AgentException {
		final LoopControllerRun.Execution execution = loaded(toolCalling);
		if (execution == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Loop controller agent id:" + getId() + " executed with no run loaded: the plain loop");
			}
			return super.createResponse(chatRequestContext, agentConfig, request, network, contextAgentPersona,
					notificationSink, session, mySessionContext, agentModel, agentRole, agentPrompt, runAs,
					callBacksListener, toolCalling);
		}
		final LoopControllerRun run = execution.run();
		final ToolCallsListener runCalls = run.callsOf(callBacksListener);
		final IChatRequestContext runContext = runCalls == callBacksListener ? chatRequestContext
				: IChatRequestContext.forAgent(chatRequestContext, runCalls);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Loop controller agent id:" + getId() + " executed over its run: " + run.loop().iterations()
					+ " iteration(s) and " + runCalls.getCalls().size() + " tool call(s) before this execution");
		}
		return loopResponse(runContext, agentConfig, request, network, contextAgentPersona, notificationSink, session,
				mySessionContext, agentModel, agentRole, agentPrompt, runAs, runCalls, toolCalling, run.loop());
	}

	/** The last iteration hands no work over: no execution follows it to read the answers. */
	@Override
	protected void iterationStarts(int number, int maxIterations, GeboToolCalling toolCalling) {
		final LoopControllerRun.Execution execution = loaded(toolCalling);
		if (execution != null) {
			execution.commands().refuseWith(number >= maxIterations ? LAST_STEP : null);
		}
	}

	/** An iteration that handed work to other agents stops the loop until they answered. */
	@Override
	protected String stopsTheLoop(int number, GeboToolCalling toolCalling) {
		final LoopControllerRun.Execution execution = loaded(toolCalling);
		return execution != null && execution.commands().isCommanding() ? HANDED_WORK_OVER : null;
	}
}
