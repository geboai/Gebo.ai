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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.CalledFunction;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener.ToolCallExecuted;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;

/**
 * A single agent with tools, working in the classic agentic loop over the network
 * of agents: at each iteration the model uses its tools as it sees fit, then either
 * answers and ends the loop, or asks for another iteration by ending its text with
 * a control marker. Every iteration is streamed to the user without the markers,
 * and the next one receives the history of the previous ones (their text and the
 * tools they called). The loop ends when the model says so, or after
 * {@link #maxIterations(AgentNetworkParticipant)} iterations.
 * <p>
 * It runs as the output node of its own network, reusing the report writer's
 * streaming output and final response (documents and called functions).
 */
@Service
public class AgenticLoopReactiveAgentServiceImpl extends ReportWriterReactiveAgentServiceImpl {
	public static final String AGENTIC_LOOP_NETWORK_AGENT_SERVICE = "AgenticLoopNetworkAgentService";
	private static final String DESCRIPTION = "Single agent that operates every available tool in a loop and decides by itself when the answer is complete";
	/** Iterations of the loop when the network does not set them. */
	static final int DEFAULT_MAX_ITERATIONS = 5;
	static final String MAX_ITERATIONS_PARAM = "MAX_ITERATIONS";
	private static final String NEWLINE = "\r\n";

	public AgenticLoopReactiveAgentServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGRuntimeBinder runtimeBinder, IGSecurityService securityService, IAgentRoleDao agentRoleDao,
			IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, runtimeBinder, securityService, agentRoleDao,
				rendererFactory);
	}

	@Override
	public String getId() {
		return AGENTIC_LOOP_NETWORK_AGENT_SERVICE;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	public AgentCapabilities getAgentCapabilities(GAgentConfig agentConfig) {
		AgentCapabilities capabilities = super.getAgentCapabilities(agentConfig);
		capabilities.addCapability("Operate the available tools in a loop until the user request is fully answered");
		return capabilities;
	}

	/**
	 * This agent is the one that operates the tools, so it mounts all of them, the
	 * ones kept out of the other agents' automatic mounting included.
	 */
	@Override
	protected List<String> filterAutoMountedTools(List<String> toolNames) {
		return toolNames;
	}

	/** One iteration of the loop: what the model wrote and the tools it called. */
	record LoopIteration(int number, String text, List<ToolCallExecuted> calls) {
	}

	@Override
	protected Flux<IGPartialOperation<GeboChatMessageEnvelope>> createResponse(IChatRequestContext chatRequestContext,
			GAgentConfig agentConfig, String request, GAgentsNetwork network,
			AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<String, GeboChatMessageEnvelope> mySessionContext,
			IGConfigurableChatModel agentModel, GAgentRole agentRole, GPromptTemplateConfig agentPrompt,
			ReactiveIdentityUtil runAs, ToolCallsListener callBacksListener) throws LLMConfigException, AgentException {
		final int maxIterations = maxIterations(contextAgentPersona);
		final int budget = agentTokenBudget(agentModel, agentPrompt, chatRequestContext);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin createResponse(...) agentic loop agent id:" + getId() + " maxIterations:"
					+ maxIterations + " budget:" + budget + " (tok)");
		}
		// The kind of deliverable the user asked for (a direct answer, an analysis...)
		// shapes every iteration, as it shapes the report writer's answer.
		final DeliverableIntent userIntent = sessionUserIntent(session);
		final Map<String, Object> deliverableParams = deliverableTemplateParams(userIntent);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agentic loop agent id:" + getId() + " shapes its answer for the deliverable:"
					+ userIntent.name());
		}
		final List<LoopIteration> history = new ArrayList<>();
		Flux<String> text = iteration(1, maxIterations, budget, history, agentModel, agentPrompt, chatRequestContext,
				contextAgentPersona, notificationSink, callBacksListener, deliverableParams);
		return renderOutputStream(text, new GeboChatResponse(), session, contextAgentPersona, notificationSink,
				callBacksListener);
	}

	/**
	 * The iterations of the loop, from the given one: the model's streamed text
	 * without the control markers, followed by the next iteration when the model
	 * asked for one and the limit is not reached.
	 */
	protected Flux<String> iteration(int number, int maxIterations, int budget, List<LoopIteration> history,
			IGConfigurableChatModel agentModel, GPromptTemplateConfig agentPrompt,
			IChatRequestContext chatRequestContext, AgentNetworkParticipant contextAgentPersona,
			INotificationSink notificationSink, ToolCallsListener callBacksListener) {
		return iteration(number, maxIterations, budget, history, agentModel, agentPrompt, chatRequestContext,
				contextAgentPersona, notificationSink, callBacksListener,
				deliverableTemplateParams(DeliverableIntent.SUMMARY));
	}

	/**
	 * The iterations of the loop, from the given one, with the prompt parameters
	 * shaping the deliverable the user asked for (see
	 * {@link #deliverableTemplateParams(DeliverableIntent)}).
	 */
	protected Flux<String> iteration(int number, int maxIterations, int budget, List<LoopIteration> history,
			IGConfigurableChatModel agentModel, GPromptTemplateConfig agentPrompt,
			IChatRequestContext chatRequestContext, AgentNetworkParticipant contextAgentPersona,
			INotificationSink notificationSink, ToolCallsListener callBacksListener,
			Map<String, Object> deliverableParams) {
		return Flux.defer(() -> {
			final Map<String, Object> params = new HashMap<>(deliverableParams);
			params.put(CURRENT_ITERATION_PROMPT_PARAM, number);
			params.put(MAX_ITERATIONS_PARAM, maxIterations);
			params.put(AGENT_CONTROL_FINISHED_PROMPT_PARAM, AGENT_CONTROL_FINISHED);
			params.put(AGENT_CONTROL_CONTINUE_PROMPT_PARAM, AGENT_CONTROL_MORE_TOOLS);
			params.put(AGENT_SESSION_STORY_PROMPT_PARAM, loopStory(history, budget));
			final int callsBefore = callBacksListener.getCalls().size();
			final ControlMarkerStripper stripper = new ControlMarkerStripper();
			final StringBuilder text = new StringBuilder();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Agentic loop agent id:" + getId() + " iteration " + number + " of " + maxIterations);
			}
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("<AGENTIC_LOOP_STORY iteration=" + number + ">");
				LOGGER.trace(String.valueOf(params.get(AGENT_SESSION_STORY_PROMPT_PARAM)));
				LOGGER.trace("</AGENTIC_LOOP_STORY>");
			}
			Flux<String> modelText;
			try {
				modelText = callLLMReactive(agentModel, agentPrompt, chatRequestContext, params);
			} catch (LLMConfigException e) {
				return Flux.error(e);
			}
			Flux<String> visible = modelText.map(chunk -> {
				String out = stripper.accept(chunk);
				text.append(out);
				return out;
			}).concatWith(Flux.defer(() -> {
				String tail = stripper.complete();
				text.append(tail);
				return Flux.just(tail);
			})).filter(chunk -> !chunk.isEmpty());
			Flux<String> next = Flux.defer(() -> {
				List<ToolCallExecuted> calls = callBacksListener.getCalls();
				history.add(new LoopIteration(number, text.toString(),
						new ArrayList<>(calls.subList(Math.min(callsBefore, calls.size()), calls.size()))));
				if (LOGGER.isTraceEnabled()) {
					LOGGER.trace("<AGENTIC_LOOP_ITERATION number=" + number + ">");
					for (ToolCallExecuted call : history.get(history.size() - 1).calls()) {
						LOGGER.trace("tool called: " + call.getName());
					}
					LOGGER.trace(text.toString());
					LOGGER.trace("</AGENTIC_LOOP_ITERATION>");
				}
				boolean another = stripper.isContinueRequested() && number < maxIterations;
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Agentic loop agent id:" + getId() + " iteration " + number + " ended, tools called:"
							+ (calls.size() - Math.min(callsBefore, calls.size())) + " continue requested:"
							+ stripper.isContinueRequested() + " finish requested:" + stripper.isFinishRequested()
							+ " next iteration:" + another);
				}
				if (!another) {
					return Flux.<String>empty();
				}
				notificationSink.next(
						"Agent: " + contextAgentPersona.getNetworkAgentName() + " goes on working (step " + (number + 1)
								+ " of " + maxIterations + ")..",
						ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType.INFO);
				return Flux.just(NEWLINE + NEWLINE).concatWith(iteration(number + 1, maxIterations, budget, history,
						agentModel, agentPrompt, chatRequestContext, contextAgentPersona, notificationSink,
						callBacksListener, deliverableParams));
			});
			return visible.concatWith(next);
		});
	}

	/**
	 * The history of the previous iterations for the next one: the tools each called
	 * and what it wrote, within half of the budget, shared equally among them; the
	 * other half is left to the chat history and the tool results of the iteration.
	 */
	protected String loopStory(List<LoopIteration> history, int budget) {
		if (history.isEmpty()) {
			return "No previous iteration: this is the first one.";
		}
		List<String> pieces = new ArrayList<>();
		for (LoopIteration iteration : history) {
			StringBuilder piece = new StringBuilder();
			piece.append("BEGIN_AGENT-LOOP-").append(iteration.number()).append(NEWLINE);
			List<CalledFunction> functions = renderFunctions(iteration.calls());
			for (int i = 0; functions != null && i < functions.size(); i++) {
				piece.append("TOOL-CALLED-").append(i + 1).append(": ").append(functions.get(i).getFunctionName())
						.append(" params:").append(functions.get(i).getParams()).append(NEWLINE);
			}
			piece.append("RESPONSE: ").append(iteration.text()).append(NEWLINE);
			piece.append("END_AGENT-LOOP-").append(iteration.number()).append(NEWLINE);
			pieces.add(piece.toString());
		}
		return String.join("", fitEqually(pieces, Math.max(budget / 2, MIN_SHARED_CONTEXT_TOKENS)));
	}

	/** The iterations the loop may run. */
	protected int maxIterations(AgentNetworkParticipant contextAgentPersona) {
		return DEFAULT_MAX_ITERATIONS;
	}

	/**
	 * Removes the loop control markers from the streamed text, remembering which one
	 * the model wrote. The markers can be split across chunks: the end of a chunk
	 * that could be the beginning of a marker is held back until the next one.
	 */
	static final class ControlMarkerStripper {
		private static final List<String> MARKERS = List.of(AGENT_CONTROL_FINISHED, AGENT_CONTROL_MORE_TOOLS);
		private static final int LONGEST_MARKER = MARKERS.stream().mapToInt(String::length).max().orElse(0);
		private final StringBuilder pending = new StringBuilder();
		private boolean continueRequested = false;
		private boolean finishRequested = false;

		/** The text of the chunk that can be shown now. */
		String accept(String chunk) {
			if (chunk == null || chunk.isEmpty()) {
				return "";
			}
			pending.append(chunk);
			removeMarkers();
			int held = heldBack();
			String out = pending.substring(0, pending.length() - held);
			pending.delete(0, pending.length() - held);
			return out;
		}

		/** The text still held back, once the stream is over. */
		String complete() {
			removeMarkers();
			String out = pending.toString();
			pending.setLength(0);
			return out;
		}

		boolean isContinueRequested() {
			// The last word wins: a model that asks to go on and then says it is done has
			// finished.
			return continueRequested && !finishRequested;
		}

		boolean isFinishRequested() {
			return finishRequested;
		}

		private void removeMarkers() {
			for (String marker : MARKERS) {
				int index;
				while ((index = pending.indexOf(marker)) >= 0) {
					pending.delete(index, index + marker.length());
					if (marker.equals(AGENT_CONTROL_FINISHED)) {
						finishRequested = true;
					} else {
						continueRequested = true;
					}
				}
			}
		}

		private int heldBack() {
			for (int length = Math.min(pending.length(), LONGEST_MARKER - 1); length > 0; length--) {
				String suffix = pending.substring(pending.length() - length);
				for (String marker : MARKERS) {
					if (marker.startsWith(suffix)) {
						return length;
					}
				}
			}
			return 0;
		}
	}
}
