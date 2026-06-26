package ai.gebo.llms.agent.chat.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentProducedSessionContribution;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.AgentPromptTemplateParams;
import ai.gebo.architecture.agents.services.GAbstractReactiveAgentService;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.CalledFunction;
import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
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
import ai.gebo.llms.agent.chat.service.IReportWriterReactiveAgentService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ChatNotificationContent.NotificationType;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetCalculator;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetFluxCoordinator;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetFluxCoordinator.GenerativeFunction;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetFluxCoordinator.LastWork;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetFluxCoordinator.TokensLimitCompute;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;

@Service
public class ReportWriterReactiveAgentServiceImpl extends
		GAbstractReactiveAgentService<String, GeboChatMessageEnvelope, GeboChatMessageEnvelope, GeboChatResponse>
		implements IReportWriterReactiveAgentService {

	private static final String END_AGENT_LOOP = "END_AGENT-LOOP-";
	private static final String TOOL_CALLED = "TOOL-CALLED-";
	private static final String RESPONSE = "RESPONSE: ";
	private static final String BEGIN_AGENT_LOOP = "BEGIN_AGENT-LOOP-";
	private static final String NEWLINE = "\r\n";
	public static final String AGENT_CONTROL_CONTINUE_PROMPT_PARAM = "AGENT_CONTROL_CONTINUE_PROMPT_PARAM";
	public static final String AGENT_CONTROL_FINISHED_PROMPT_PARAM = "AGENT_CONTROL_FINISHED_PROMPT_PARAM";
	private static final String MAX_ITERATIONS_PROMPT_PARAM = "MAX_ITERATIONS";
	public static final String CURRENT_ITERATION_PROMPT_PARAM = "CURRENT_ITERATION";
	public static final String AGENT_SESSION_STORY_PROMPT_PARAM = "AGENT_SESSION_STORY";
	private static final String REPORT_WRITER_NETWORK_AGENT_SERVICE_DESC = "Report writer network agent service";
	public static final String REPORT_WRITER_NETWORK_AGENT_SERVICE = "ReportWriterNetworkAgentService";
	public static final String AGENT_CONTROL_FINISHED = "<AGENT-CONTROL-STOP/>";
	public static final String AGENT_CONTROL_MORE_TOOLS = "<AGENT-CONTROL-MORE-TOOLS/>";

	public ReportWriterReactiveAgentServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGRuntimeBinder runtimeBinder, IGSecurityService securityService, IAgentRoleDao agentRoleDao,
			IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, runtimeBinder, securityService, agentRoleDao,
				rendererFactory);

	}

	@Override
	public String getId() {

		return REPORT_WRITER_NETWORK_AGENT_SERVICE;
	}

	@Override
	public String getDescription() {

		return REPORT_WRITER_NETWORK_AGENT_SERVICE_DESC;
	}

	@Override
	public AgentCapabilities getAgentCapabilities(GAgentConfig agentConfig) {
		AgentCapabilities capabilities = super.getAgentCapabilities(agentConfig);
		capabilities.addCapability(
				"Read the user question and the evidence gathered by the other agents and write the final, user-facing answer/report");
		return capabilities;
	}

	@Override
	protected Flux<IGPartialOperation<GeboChatMessageEnvelope>> createResponse(IChatRequestContext chatRequestContext,
			GAgentConfig agentConfig, String request, GAgentsNetwork network,
			AgentNetworkParticipant contextAgentPersona, INotificationSink<GeboChatMessageEnvelope> notificationSink,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<String, GeboChatMessageEnvelope> mySessionContext,
			IGConfigurableChatModel agentModel, GAgentRole agentRole, GPromptTemplateConfig agentPrompt,
			ReactiveIdentityUtil runAs, ToolCallsListener callBacksListener) throws LLMConfigException, AgentException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin createResponse(...) report writer agent id:" + getId() + " persona:"
					+ (contextAgentPersona != null ? contextAgentPersona.getAgentContextualName() : null));
		}
		final GeboChatResponse response = new GeboChatResponse();
		final int tokenBudget = (agentModel.getContextLength() - agentPrompt.getTokensSize()) * 2 / 3;
		// The routed query (the coordinator's writing command) is the agent input that
		// feeds the {INPUT} placeholder; pass the raw payload as the other agents do,
		// so
		// it is rendered by the standard String renderer. SHARED_CONTEXT, identity and
		// scenario placeholders are built from the network/session. agentsDao is not
		// available at this layer, hence null (peer descriptions are then omitted).
		final List<Map<String, Object>> params = createAgentTemplateParams(agentPrompt, network, agentRole,
				contextAgentPersona, session, mySessionContext, request, null, 0, tokenBudget, true);
		Flux<String> textStream = null;
		if (params.size() == 1) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Report writer using single-window reactive LLM call (tokenBudget:" + tokenBudget + ")");
			}
			textStream = callLLMReactive(agentModel, agentPrompt, chatRequestContext, params.get(0));
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Report writer using token-budget coordinator over " + params.size()
						+ " shared-context window(s)");
			}
			textStream = streamWithTokenBudgetCoordinator(agentModel, agentPrompt, chatRequestContext, params, runAs,
					notificationSink);
		}

		final StringBuffer cumulatedContent = new StringBuffer();
		Flux<IGPartialOperation<GeboChatMessageEnvelope>> bodyStream = textStream.map(content -> {
			cumulatedContent.append(content);
			return IGPartialOperation.of(new GeboChatMessageEnvelope(content), false);
		});
		Flux<IGPartialOperation<GeboChatMessageEnvelope>> lastItem = Flux.defer(() -> {
			String queryResponse = cumulatedContent.toString();
			boolean lastMessage = false;
			response.setQueryResponse(queryResponse);
			response.setCalledFunctions(renderFunctions(callBacksListener.getCalls()));
			GeboChatMessageEnvelope envelope = new GeboChatMessageEnvelope(response);
			envelope.setLastMessage(lastMessage);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End createResponse(...) report writer agent id:" + getId() + " final response length:"
						+ queryResponse.length() + " calledFunctions:"
						+ (response.getCalledFunctions() != null ? response.getCalledFunctions().size() : 0));
			}
			return Flux.just(IGPartialOperation.of(envelope, lastMessage));
		});
		return Flux.concat(bodyStream, lastItem);
	}

	protected Flux<String> streamWithTokenBudgetCoordinator(IGConfigurableChatModel agentModel,
			GPromptTemplateConfig agentPrompt, IChatRequestContext chatRequestContext, List<Map<String, Object>> params,
			ReactiveIdentityUtil runAs, INotificationSink<GeboChatMessageEnvelope> notificationSink) {
		final Map<String, Object> cleanedParams = params.get(0);
		cleanedParams.put(AgentPromptTemplateParams.SHARED_CONTEXT_TEMPLATE_PARAM, "");
		cleanedParams.put(CONSOLIDATED_TEMPLATE_VARIABLE, "");
		Flux<Map<String, Object>> inputFlux = Flux.fromIterable(params);
		GenerativeFunction<Map<String, Object>, String> intermediateProcess = (initialValue, ignoredEmitter,
				documentsBatch) -> runAs.doRunAsWithReturnAndException(() -> {
					Map<String, Object> iterationParams = documentsBatch.get(0);

					return agentModel.textResponse(agentPrompt, iterationParams, chatRequestContext);
				});
		LastWork<String, String> finalWork = (consolidations, ignoredEmitter) -> runAs
				.doRunAsWithReturnAndException(() -> {
					Map<String, Object> finalParams = new HashMap<>(cleanedParams);
					finalParams.put(AgentPromptTemplateParams.SHARED_CONTEXT_TEMPLATE_PARAM,
							String.join("\r\n", consolidations));
					finalParams.put(CONSOLIDATED_TEMPLATE_VARIABLE, "");
					return callLLMReactive(agentModel, agentPrompt, chatRequestContext, finalParams);
				});

		Predicate<Map<String, Object>> isValidDocument = (document) -> document != null
				&& document.containsKey(AgentPromptTemplateParams.SHARED_CONTEXT_TEMPLATE_PARAM)
				&& document.get(AgentPromptTemplateParams.SHARED_CONTEXT_TEMPLATE_PARAM).toString().trim().length() > 0;

		Predicate<String> isOutOfBand = (value) -> value == null || value.equals(LLM_PROCESSING_ERROR);
		Predicate<String> noEndCondition = (value) -> false;
		Function<String, String> identityCleaning = (value) -> value;
		Consumer<Map<String, Object>> unprocessedCumulator = (document) -> {
		};
		ISinkUIEmitter emitter = toSinkUIEmitter(notificationSink);
		return TokensBudgetFluxCoordinator.tokenBudgetCoordinateAlreadySplitted(inputFlux, emitter, isValidDocument,
				intermediateProcess, finalWork, "", LLM_PROCESSING_ERROR, isOutOfBand, LLM_PROCESSING_ERROR,
				isOutOfBand, noEndCondition, identityCleaning, REPORT_STRING_STREAMER, runAs, 4, unprocessedCumulator);

	}

	private static final String LLM_PROCESSING_ERROR = "REPORT_WRITER_LLM_ERROR";

	/** Streams a string in small chunks (mirrors the deep-search streamer). */
	private static final Function<String, Flux<String>> REPORT_STRING_STREAMER = (data) -> {
		String inputString = data != null ? data : "";
		List<String> chunks = new ArrayList<>();
		for (int index = 0; index < inputString.length(); index += 4) {
			int stopChar = Math.min(index + 4, inputString.length());
			chunks.add(inputString.substring(index, stopChar));
		}
		return Flux.fromIterable(chunks);
	};

	private ISinkUIEmitter toSinkUIEmitter(INotificationSink<GeboChatMessageEnvelope> notificationSink) {
		return new ISinkUIEmitter() {
			@Override
			public void notifyUser(String code, String message, String icon, Long duration,
					NotificationType notificationType) {
				// The agent only carries a notification sink for streamed envelopes; UI
				// notifications are not propagated.
			}

			@Override
			public void next(GeboChatMessageEnvelope event) {
				if (notificationSink != null) {
					notificationSink.next(event);
				}
			}

			@Override
			public void error(Throwable error) {
				LOGGER.error("Error in report writer token-budget coordination", error);
			}

			@Override
			public void complete() {
			}
		};
	}

	protected String createCycleHistoryVariable(
			AgentPrivateSessionContext<String, GeboChatMessageEnvelope> privateMemory, String request) {
		List<GeboChatResponse> pastResponses = new ArrayList<GeboChatResponse>();
		for (AgentPrivateSessionContext<String, GeboChatMessageEnvelope>.AgentInteraction interaction : privateMemory
				.getInteractions()) {
			pastResponses.add((GeboChatResponse) interaction.getOutput().getContent());
		}
		StringBuffer buffer = new StringBuffer();
		int index = 0;
		for (GeboChatResponse geboChatResponse : pastResponses) {
			buffer.append(BEGIN_AGENT_LOOP + index);
			buffer.append(NEWLINE);

			if (geboChatResponse.getCalledFunctions() != null && !geboChatResponse.getCalledFunctions().isEmpty()) {
				int tcIndex = 0;
				for (CalledFunction callF : geboChatResponse.getCalledFunctions()) {
					buffer.append(
							TOOL_CALLED + tcIndex + ": " + callF.getFunctionName() + " params:" + callF.getParams());
					buffer.append(NEWLINE);
					tcIndex++;
				}
			}
			buffer.append(RESPONSE + geboChatResponse.getQueryResponse());
			buffer.append(NEWLINE);
			buffer.append(END_AGENT_LOOP + index);
			buffer.append(NEWLINE);
			index++;
		}
		return buffer.toString();
	}

	protected List<CalledFunction> renderFunctions(List<ToolCallExecuted> calls) {

		return calls != null ? calls.stream().map(x -> new CalledFunction(x.getName(), x.getToolDescription(),
				List.of(), x.getToolInput() != null ? List.of(x.getToolInput()) : List.of())).toList() : List.of();
	}

}
