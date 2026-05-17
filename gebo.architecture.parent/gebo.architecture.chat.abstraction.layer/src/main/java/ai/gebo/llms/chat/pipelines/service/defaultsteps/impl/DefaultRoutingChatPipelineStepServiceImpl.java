package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.ToolCategoriesTree;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ChatNotificationContent.NotificationType;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.services.CommonChatPromptParamsUtil;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptsParametersCacheService;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IChatPipelineStepRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IStepContribution;
import ai.gebo.llms.chat.pipelines.model.RoutingDecision;
import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineStepServiceRepositoryPattern;
import ai.gebo.llms.chat.pipelines.service.IDataSourcesCatalogsService;
import ai.gebo.llms.chat.pipelines.service.IRoutingChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.model.RespondingWith;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceMetaInfos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Component
@AllArgsConstructor
public class DefaultRoutingChatPipelineStepServiceImpl extends BaseLLMSInvokingService
		implements IRoutingChatPipelineStepService {
	private static final String SCANNING_HUGE_FILE_WITH_LLMS = "Scanning huge file with llms";
	private static final String RUNNING_HEAVY_CHAT_WITH_DOCUMENTS = "RUNNING_HEAVY_CHAT_WITH_DOCUMENTS";
	private static final String EXECUTING_YOUR_CHOOSED_AGENT = "Executing your choosed agent";
	private static final String USER_CHOOSED_AGENT = "USER_CHOOSED_AGENT";
	private static final String CHOOSED_AGENTIC_FLOW_ANSWERING_AGENT_RUNNING = "Choosed agentic flow, answering agent running";
	private static final String CHOOSED_AGENTIC_FLOW = "CHOOSED_AGENTIC_FLOW";
	private static final String CHOOSING_AGENTIC_FLOW_DESCRIPTION = "Choosing agentic flow";
	private static final String CHOOSING_AGENTIC_FLOW = "CHOOSING_AGENTIC_FLOW";
	private static final String I_M_ANALYZING_YOUR_REQUEST = "I'm analyzing your request";
	private static final String DOING_USER_INTENT_ANALISYS = "DOING_USER_INTENT_ANALISYS";
	private static final String DELIVERABLE_EXPLANATION_TEMPLATE_PARAM = "deliverableExplanation";
	static final String DELIVERABLE_TEMPLATE_PARAM = "deliverable";
	static final String REWRITTEN_QUERY_TEMPLATE_PARAM = "rewrittenQuery";
	private static final String REWRITTEN_QUERY_FIELD = REWRITTEN_QUERY_TEMPLATE_PARAM;
	static final String DEEP_SEARCHED_SYSTEMS = "deepSearchedSystems";
	private static final String DELIVERABLE_FIELD = DELIVERABLE_TEMPLATE_PARAM;
	private static final String INTENT_SELECTION_CRITERIA = "selection-criteria: ";
	private static final String INTENT_TYPE = "intent-type: ";
	private static final String END_DELIVERABLE_TYPES_CATALOG = "END_DELIVERABLE_TYPES_CATALOG";
	private static final String DELIVERABLE_TYPES_CATALOG = "DELIVERABLE_TYPES_CATALOG";

	private static final String ROUTING_DECISION = "routingDecision";
	private static final String TOPICS = "topics: ";
	private static final String END_KNOWLEDGE_BASE = "END_KNOWLEDGE_BASE";
	private static final String KNOWLEDGE_BASE = "KNOWLEDGE_BASE";
	private static final String END_INTERNAL_KNOWLEDGEBASE_CATALOG = "END_INTERNAL_KNOWLEDGEBASE_CATALOG";
	private static final String KNOWLEDGE_BASE_TITLE = "knowledge base title: ";
	private static final String NEWLINE = "\r\n";
	private static final String DOCUMENTS = "documents";
	private final static Logger LOGGER = LoggerFactory.getLogger(DefaultRoutingChatPipelineStepServiceImpl.class);
	private final ChatPipelinesConfiguration chatPipelinesConfig;

	private final IGToolCallbackSourceRepositoryPattern toolCallbackSourceRepo;
	private final IDataSourcesCatalogsService deepSearchDataSourcesCatalogsService;
	private final IGRuntimeBinder runtimeBinder;
	private final IGPersistentObjectManager persistentManager;
	private final IGPromptConfigDao promptsDao;
	private final IGPromptsParametersCacheService promptsParamsCacheService;
	private final IGChatSessionLifeCycleService chatSessionLifecycleService;
	public static final String START_INTERNAL_KNOWLEDGEBASE_CATALOG = "INTERNAL_KNOWLEDGEBASE_CATALOG";
	public static final String DEFAULT_ROUTING_STEP = "default-routing-step";
	public static final String INTERNAL_KNOWLEDGE_BASE_SYSTEM_ID = "IKB_SYSTEM";

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return DefaultRoutingChatPipelineStepServiceImpl.DEFAULT_ROUTING_STEP;
	}

	private void notifyUser(ISinkUIEmitter emitter, String code, String message, String icon, Long duration,
			NotificationType notificationType) {

		emitter.notifyUser(code, message, icon, duration, notificationType);
	}

	@AllArgsConstructor
	@Getter
	static class RewriteAndUserIntent {
		private final String rewrited_query;
		private final DeliverableIntent userIntent;
	}

	private RewriteAndUserIntent doRequestRewriteAndUserIntent(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter emitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel,
			String latestInteractions) throws GeboChatSessionLifecycleException, IOException, LLMConfigException {
		notifyUser(emitter, DOING_USER_INTENT_ANALISYS, I_M_ANALYZING_YOUR_REQUEST, null, 2000l, NotificationType.INFO);
		String query = runtimeData.getRequestResources().getCurrentRequest().getQuery();
		Map<String, Object> params = CommonChatPromptParamsUtil
				.preparePromptParameters(runtimeData.getMinimalChatContext());
		params.put(DefaultPipelineSharedPromptPlaceholders.DELIVERABLE_TYPES_LIST_TEMPLATE_PARAM,
				createDeliverableTypesList());
		GPromptTemplateConfig rewritePrompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_QUERY_REWRITING_PROMPT);
		IChatRequestContext context = runtimeData.getRequestResources().createChatRequestContext();
		Map<String, List<String>> data = callLLMRepeatableFieldEntryOutput(serviceModel, rewritePrompt, context, params,
				List.of(DELIVERABLE_FIELD, REWRITTEN_QUERY_FIELD));
		List<String> rewrittenQuery = data.get(REWRITTEN_QUERY_FIELD);
		List<String> deliverable = data.get(DELIVERABLE_FIELD);
		String rewrited_query = rewrittenQuery != null && !rewrittenQuery.isEmpty() ? rewrittenQuery.get(0) : null;
		runtimeData.getRequestResources().getCurrentRequest().setRewrittenQuery(rewrited_query);
		DeliverableIntent userIntent = DeliverableIntent.QA;
		if (deliverable != null && !deliverable.isEmpty()) {

			String toSearchInto = deliverable.get(0);
			if (toSearchInto != null) {
				toSearchInto = toSearchInto.toLowerCase();
			} else
				toSearchInto = "";
			for (DeliverableIntent di : DeliverableIntent.values()) {
				if (toSearchInto.indexOf(di.name().toLowerCase()) >= 0) {
					userIntent = di;
					break;
				}
			}
		}
		notifyUser(emitter, "SERVICE_LEVEL_DETECTED", "Service level is: " + userIntent.name(), null, 1000l,
				NotificationType.DEBUG);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("User intent:" + userIntent.name());
			LOGGER.debug("Rewritten query:" + rewrited_query);
		}
		runtimeData.getRequestResources().getCurrentRequest().setUserIntent(userIntent);
		return new RewriteAndUserIntent(rewrited_query, userIntent);
	}

	private RoutingDecision doDecideRoute(ISinkUIEmitter emitter, ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, String latestInteractions,
			String rewrited_query) throws GeboChatSessionLifecycleException, LLMConfigException {
		notifyUser(emitter, CHOOSING_AGENTIC_FLOW, CHOOSING_AGENTIC_FLOW_DESCRIPTION, null, 2000l,
				NotificationType.INFO);
		GPromptTemplateConfig _prompt = this.promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_ROUTING_DECISION_PROMPT);

		Supplier<Map<String, Object>> paramsProvider = () -> {
			try {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Begin Calculating router params to be cached");
				}
				List<GKnowledgeBase> knowledgeBases = this.chatSessionLifecycleService
						.getSessionAvailableKnowledgeBases(runtimeData.getRequestResources().getCurrentRequest());
				Map<String, Object> templateParams = new HashMap<String, Object>();

				String toolsList = toolsListPromptPart(chatModel);
				String internalKnowledgeBaseCatalog = ragInternalKnowledgeBasePromptPart(knowledgeBases);
				String deepSearchDataSources = deepSearchDataSourcesListPromptPart(knowledgeBases);
				templateParams.put(
						DefaultPipelineSharedPromptPlaceholders.INTERNAL_KNOWLEDGE_BASE_CATALOG_TEMPLATE_PARAM,
						internalKnowledgeBaseCatalog);
				templateParams.put(DefaultPipelineSharedPromptPlaceholders.DEEP_SEARCH_DATA_SOURCES_TEMPLATE_PARAM,
						deepSearchDataSources);
				templateParams.put(DefaultPipelineSharedPromptPlaceholders.TOOLS_LIST_TEMPLATE_PARAM, toolsList);

				templateParams.put(DefaultPipelineSharedPromptPlaceholders.DELIVERABLE_TYPES_LIST_TEMPLATE_PARAM,
						createDeliverableTypesList());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("End Calculating router params to be cached");
				}
				return templateParams;
			} catch (Throwable th) {
				LOGGER.error("Error in template params loader", th);
				throw new RuntimeException("Error in template params loader", th);
			}
		};
		Map<String, Object> params = CommonChatPromptParamsUtil
				.preparePromptParameters(runtimeData.getMinimalChatContext());
		params.put(REWRITTEN_QUERY_TEMPLATE_PARAM, rewrited_query);
		params.put(DELIVERABLE_TEMPLATE_PARAM,
				runtimeData.getRequestResources().getCurrentRequest().getUserIntent().name());
		params.put(DELIVERABLE_EXPLANATION_TEMPLATE_PARAM,
				runtimeData.getRequestResources().getCurrentRequest().getUserIntent().getExplanation());
		final Map<String, Object> cachedParams = this.promptsParamsCacheService.lookupCache(
				GeboPromptsLibrary.DEFAULT_PIPELINE_ROUTING_DECISION_PROMPT,
				runtimeData.getRequestResources().getCurrentRequest().getUserChatContextCode(),
				DefaultRoutingChatPipelineStepServiceImpl.DEFAULT_ROUTING_STEP, 120000, paramsProvider);
		params.putAll(cachedParams);
		int usedTokens = tokensLength(latestInteractions, params.toString(), rewrited_query) + _prompt.getTokensSize();
		int remainingContext = (int) (((double) (serviceModel.getContextLength() - usedTokens)) * 0.8d);
		final int documentsTokenBudget = Math.min(remainingContext,
				this.chatPipelinesConfig.getMaxRoutingDecisionDocumentsTokenBudget());
		IChatRequestContext context = runtimeData.getRequestResources().createChatRequestContext();
		Map<String, List<String>> decisionMap = callLLMRepeatableFieldEntryOutput(serviceModel, _prompt, context,
				params, List.of(ROUTING_DECISION, DEEP_SEARCHED_SYSTEMS));
		sanitizeSearchedSystems(decisionMap);
		// extracting user intent

		RespondingWith decision = decisionMap.containsKey(ROUTING_DECISION)
				? parseDecision(decisionMap.get(ROUTING_DECISION).toString())
				: RespondingWith.PURE_LLM_RESPONSE;
		final Map<String, Object> environmentMap = new HashMap<String, Object>();
		environmentMap.putAll(params);
		environmentMap.putAll(decisionMap);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Routing decision object:" + decision + " decisionMap:" + decisionMap);
		}
		notifyUser(emitter, CHOOSED_AGENTIC_FLOW, CHOOSED_AGENTIC_FLOW_ANSWERING_AGENT_RUNNING, null, 2000l,
				NotificationType.INFO);
		List<String> routes = futureRoutes(decision, RespondingWith.PURE_LLM_RESPONSE, runtimeData.isStreamingOutput());
		final IChatPipelineStepRuntimeData routingEntry = new IChatPipelineStepRuntimeData() {

			@Override
			public String getStepId() {

				return DefaultRoutingChatPipelineStepServiceImpl.this.getStepId();
			}

			@Override
			public List<IStepContribution> getContextEnrichingContribution() {

				return List.of();
			}

			@Override
			public Map<String, Object> getEnvironmentContributions() {

				return environmentMap;
			}
		};

		return new RoutingDecision(routes, routingEntry, decision.name(), new HashMap<>(decisionMap));
	}

	private void sanitizeSearchedSystems(Map<String, List<String>> decisionMap) {
		List<DeepSearchDataSourceMetaInfos> dataSources = deepSearchDataSourcesCatalogsService
				.getActiveDeepSearchDataSourceMetaInfos();
		final Map<String, Boolean> validSystems = new HashMap<>();
		validSystems.put(INTERNAL_KNOWLEDGE_BASE_SYSTEM_ID, true);
		dataSources.forEach(ds -> {
			validSystems.put(ds.getHandlerId(), true);
		});
		final List<String> defaultDataSource = List.of(INTERNAL_KNOWLEDGE_BASE_SYSTEM_ID);
		if (!decisionMap.containsKey(DEEP_SEARCHED_SYSTEMS)) {
			decisionMap.put(DEEP_SEARCHED_SYSTEMS, defaultDataSource);
		} else {

			List<String> cleanedList = new ArrayList<>();
			List<String> choosen = decisionMap.get(DEEP_SEARCHED_SYSTEMS);
			for (String ds : choosen) {
				if (validSystems.containsValue(ds)) {
					cleanedList.add(ds);
				}
			}
			if (cleanedList.isEmpty()) {
				decisionMap.put(DEEP_SEARCHED_SYSTEMS, defaultDataSource);
			} else {
				decisionMap.put(DEEP_SEARCHED_SYSTEMS, cleanedList);
			}
		}

	}

	private String createDeliverableTypesList() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(DELIVERABLE_TYPES_CATALOG);
		buffer.append(NEWLINE);
		for (DeliverableIntent intent : DeliverableIntent.values()) {
			buffer.append(INTENT_TYPE);
			buffer.append(intent.name());
			buffer.append(NEWLINE);
			buffer.append(INTENT_SELECTION_CRITERIA);
			buffer.append(intent.getExplanation());
			buffer.append(NEWLINE);
		}
		buffer.append(END_DELIVERABLE_TYPES_CATALOG);
		buffer.append(NEWLINE);
		return buffer.toString();
	}

	@Override
	public RoutingDecision execute(ChatPipelineExecutionRuntimeData runtimeData, ISinkUIEmitter emitter,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel) throws ChatPipelineException {
		RoutingDecision rd = null;
		try {
			String latestInteractions = RoutingPromptUtil.latestInteractionsPromptPart(
					runtimeData.getRequestResources().getChathistory().getLatestEntries().getInteractions());
			// Doing a query rewrite
			RewriteAndUserIntent firstDecision = doRequestRewriteAndUserIntent(runtimeData, emitter, chatModel,
					serviceModel, latestInteractions);
			String rewrited_query = firstDecision.getRewrited_query();

			// if actual resource has chat with documents or uploads with more than actual
			// tokens budget than doing a deep search ONLY on
			// Documents being in requests
			int forcedDocumentsTotal = runtimeData.getRequestResources().getChatWithDocuments().getTokensSize()
					+ runtimeData.getRequestResources().getUploadedDocuments().getTokensSize();
			int threasholdForForcedDeepSearch = getChatWithDocsAndUploadedSizeTriggersDeepSearchThreashold(chatModel);
			if (forcedDocumentsTotal >= threasholdForForcedDeepSearch) {
				rd = createKnowledgeBaseSearchHeavyDocumentsFixedRoute(emitter, runtimeData);
			} else {
				if (runtimeData.getRequestResources().getCurrentRequest() != null
						&& runtimeData.getRequestResources().getCurrentRequest().getChatPipelineProcessId() != null
						&& runtimeData.getRequestResources().getCurrentRequest().getChatPipelineProcessId().trim()
								.length() > 0) {
					rd = doHandleUserRequestedRouting(emitter, runtimeData);
				} else {
					DeliverableIntent serviceType = firstDecision.getUserIntent();
					switch (serviceType) {
					case PURE_SEARCH: {
						rd = createPureSearchPipelineRoute(runtimeData);
					}
						break;
					default: {
						rd = doDecideRoute(emitter, runtimeData, chatModel, serviceModel, latestInteractions,
								rewrited_query);
					}
						break;
					}
				}
			}
			this.chatSessionLifecycleService.updateRequest(runtimeData.getRequestResources().getCurrentRequest());
		} catch (Throwable th) {
			LOGGER.error("Exception in chat pipeline routing falling back to PURE_LLM_RESPONSE", th);
			rd = new RoutingDecision(List.of(DefaultStreamingOutputChatPipelineServiceImpl.DEFAULT_STREAMING_OUTPUT),
					IChatPipelineStepRuntimeData
							.VoidRetun(DefaultRoutingChatPipelineStepServiceImpl.DEFAULT_ROUTING_STEP),
					RespondingWith.PURE_LLM_RESPONSE.name(), Map.of());
		}
		// Setting the decided routing code
		runtimeData.getChatResponse().setPipelineRouterDecisionCode(rd.getPipelineRouterDecisionCode());
		if (rd.getPipelineParams() != null) {
			runtimeData.getChatResponse().getPipelineParams().putAll(rd.getPipelineParams());
		}
		return rd;

	}

	private RoutingDecision createPureSearchPipelineRoute(ChatPipelineExecutionRuntimeData runtimeData) {

		return new RoutingDecision(futureRoutes(RespondingWith.PURE_SEARCH, RespondingWith.PURE_SEARCH, true),
				IChatPipelineStepRuntimeData.VoidRetun(DefaultRoutingChatPipelineStepServiceImpl.DEFAULT_ROUTING_STEP),
				DefaultPipelineStreamingPureSearchPipelineStepServiceImpl.PURE_SEARCH_STREAMING_SERVICE,
				new HashMap<>());
	}

	private RoutingDecision doHandleUserRequestedRouting(ISinkUIEmitter emitter,
			ChatPipelineExecutionRuntimeData runtimeData) throws ChatPipelineException {
		String routingDecisionId = runtimeData.getRequestResources().getCurrentRequest().getChatPipelineProcessId();
		RespondingWith respondingWith = RespondingWith.valueOf(routingDecisionId);
		RoutingDecision decision = new RoutingDecision(
				futureRoutes(respondingWith, RespondingWith.PURE_LLM_RESPONSE, true),
				IChatPipelineStepRuntimeData.VoidRetun(DefaultRoutingChatPipelineStepServiceImpl.DEFAULT_ROUTING_STEP),
				runtimeData.getRequestResources().getCurrentRequest().getChatPipelineProcessId(), new HashMap<>());
		notifyUser(emitter, USER_CHOOSED_AGENT, EXECUTING_YOUR_CHOOSED_AGENT, null, 3000l, NotificationType.INFO);
		if (decision.getFutureRoute() != null && !decision.getFutureRoute().isEmpty()) {
			String outputStepId = decision.getFutureRoute().get(decision.getFutureRoute().size() - 1);
			IChatPipelineStepServiceRepositoryPattern pipelineStepsRepoPattern = runtimeBinder
					.getImplementationOf(IChatPipelineStepServiceRepositoryPattern.class);
			IChatPipelineStepService handler = pipelineStepsRepoPattern.findByCode(outputStepId);
			if (handler instanceof IStreamingOutputChatPipelineService commonOutputService) {
				List<StepEnvironmentParameter> requireds = commonOutputService.getRequiredParameters();
				for (StepEnvironmentParameter par : requireds) {
					if (runtimeData.getSharedEnvironment().containsKey(par.getParamName())) {
						Object paramValue = runtimeData.getSharedEnvironment().get(par.getParamName());
						switch (par.getParamType()) {
						case STRING: {
							if (!(paramValue instanceof String sValue && sValue.trim().length() > 0)) {
								throw new ChatPipelineException(
										"The request parameter: " + par.getParamName() + " must be a non empty string");
							}
						}
							break;
						case STRING_LIST: {
							if (!(paramValue instanceof List lValue && lValue.size() > 0)) {
								throw new ChatPipelineException("The request parameter: " + par.getParamName()
										+ " must be a non empty List<String>");
							}
						}
							break;
						}
						decision.getPipelineParams().put(par.getParamName(), paramValue);
					} else {
						throw new ChatPipelineException(
								"The request does not contain parameter: " + par.getParamName());
					}
				}
			}
		}
		return decision;
	}

	@AllArgsConstructor
	@Getter
	@ToString
	static class SimpleDecision {
		private final RespondingWith decision;
		private final String motivation;
	}

	private RespondingWith parseDecision(String decision) {
		TreeMap<Integer, RespondingWith> ordered = new TreeMap<Integer, RespondingWith>();
		String tolower = decision.toLowerCase();
		for (RespondingWith rw : RespondingWith.values()) {
			int index = tolower.indexOf(rw.name().toLowerCase());
			if (index >= 0) {
				ordered.put(index, rw);
			}
		}
		RespondingWith _decision = ordered.isEmpty() ? RespondingWith.PURE_LLM_RESPONSE
				: ordered.firstEntry().getValue();
		return _decision;
	}

	private RoutingDecision createKnowledgeBaseSearchHeavyDocumentsFixedRoute(ISinkUIEmitter emitter,
			ChatPipelineExecutionRuntimeData runtimeData) {
		notifyUser(emitter, RUNNING_HEAVY_CHAT_WITH_DOCUMENTS, SCANNING_HUGE_FILE_WITH_LLMS, null, 3000l,
				NotificationType.INFO);
		RoutingDecision rd = new RoutingDecision(
				List.of(DefaultChatWithFilesStreamingOutputPipelineServiceImpl.DEFAULT_CHAT_WITH_DOCS_STREAMING),
				new IChatPipelineStepRuntimeData() {

					@Override
					public String getStepId() {

						return DefaultRoutingChatPipelineStepServiceImpl.this.getStepId();
					}

					@Override
					public List<IStepContribution> getContextEnrichingContribution() {
						return List.of();
					}

					@Override
					public Map<String, Object> getEnvironmentContributions() {

						return Map.of();
					}
				}, RespondingWith.CHAT_WITH_FILES.name(), Map.of());
		return rd;
	}

	private String ragInternalKnowledgeBasePromptPart(List<GKnowledgeBase> knowledgeBases) {
		StringBuffer buffer = new StringBuffer();
		if (!knowledgeBases.isEmpty()) {
			buffer.append(START_INTERNAL_KNOWLEDGEBASE_CATALOG);
			buffer.append(NEWLINE);
			for (GKnowledgeBase kb : knowledgeBases) {
				buffer.append(KNOWLEDGE_BASE);
				buffer.append(NEWLINE);
				buffer.append(KNOWLEDGE_BASE_TITLE + kb.getDescription());
				buffer.append(NEWLINE);
				GProject example = new GProject();
				example.setRootKnowledgeBaseCode(kb.getCode());
				try {
					List<GProject> projects = persistentManager.findByQbe(example);
					if (!projects.isEmpty()) {
						buffer.append(TOPICS);
						buffer.append(NEWLINE);
						for (GProject pj : projects) {

							buffer.append("- ");
							buffer.append(pj.getDescription());
							buffer.append(NEWLINE);
							List<GProjectEndpoint> items = persistentManager
									.findAllByQbeSettingFunction(GProjectEndpoint.class, (t) -> {
										t.setParentProjectCode(pj.getCode());
									});
							for (GProjectEndpoint endp : items) {
								buffer.append("- ");
								buffer.append(endp.getDescription());
								buffer.append(NEWLINE);
							}
						}
					}
				} catch (GeboPersistenceException e) {
					LOGGER.error("Exception accessing mongo", e);
				}
			}
			buffer.append(END_KNOWLEDGE_BASE);
			buffer.append(NEWLINE);
			buffer.append(END_INTERNAL_KNOWLEDGEBASE_CATALOG);
			buffer.append(NEWLINE);
		}
		return buffer.toString();

	}

	private String toolsListPromptPart(IGConfigurableChatModel chatModel) {
		StringBuffer buffer = new StringBuffer();
		if (chatModel != null && chatModel.getConfig() != null
				&& chatModel.getConfig() instanceof GBaseChatModelConfig chatModelConfig) {
			if (chatModelConfig.getEnabledFunctions() != null && !chatModelConfig.getEnabledFunctions().isEmpty()) {

				List<ToolCategoriesTree> tools = toolCallbackSourceRepo
						.getEnabledToolsTree(chatModelConfig.getEnabledFunctions());
				buffer.append(RoutingPromptUtil.toolsListPromptPart(tools));
			}
		}
		return buffer.toString();
	}

	private String deepSearchDataSourcesListPromptPart(List<GKnowledgeBase> knowledgeBases) {
		StringBuffer buffer = new StringBuffer();
		List<DeepSearchDataSourceMetaInfos> dataSources = deepSearchDataSourcesCatalogsService
				.getActiveDeepSearchDataSourceMetaInfos();
		buffer.append(RoutingPromptUtil.dataSourcesListPromptPart(dataSources, knowledgeBases));
		return buffer.toString();
	}

	protected List<String> futureRoutes(RespondingWith llmRoutingDecision, RespondingWith defaultRoute,
			boolean streaming) {
		RespondingWith considered = llmRoutingDecision != null ? llmRoutingDecision : defaultRoute;
		switch (considered) {
		case RAG_LLM_RESPONSE: {
			return List.of(DefaultRagStreamingOutputChatPipelineStepServiceImpl.DEFAULT_RAG_STEP);
		}
		case DEEP_SEARCH_RESPONSE: {
			return List.of(DefaultDeepSearchStreamingOutputChatPipelineStepServiceImpl.DEFAULT_DEEPSEARCH_STREAMING);
		}
		case TOOLS_USE_RESPONSE: {
			return List.of(DefaultToolUsingStreamingOutputChatPipelineServiceImpl.DEFAULT_TOOL_USING_STREAMING);
		}

		case CHAT_WITH_FILES: {
			return List.of(DefaultChatWithFilesStreamingOutputPipelineServiceImpl.DEFAULT_CHAT_WITH_DOCS_STREAMING);
		}
		case PURE_SEARCH: {
			return List.of(DefaultPipelineStreamingPureSearchPipelineStepServiceImpl.PURE_SEARCH_STREAMING_SERVICE);
		}
		case PURE_LLM_RESPONSE:
		default:
			return List.of(DefaultStreamingOutputChatPipelineServiceImpl.DEFAULT_STREAMING_OUTPUT);
		}
	}

	protected int getChatWithDocsAndUploadedSizeTriggersDeepSearchThreashold(IGConfigurableChatModel chatModel) {
		double contextWindow = chatModel.getContextLength();
		double limit = this.chatPipelinesConfig != null
				&& this.chatPipelinesConfig.getFixedDocumentsRequestRoutesDeepSearchTokenThreashold() != null
						? this.chatPipelinesConfig.getFixedDocumentsRequestRoutesDeepSearchTokenThreashold()
								.doubleValue()
						: 0.0;
		if (this.chatPipelinesConfig.getFixedDocumentsRequestRoutesDeepSearchTreasholdContextWindowCoeff() != null) {
			double threashold = this.chatPipelinesConfig
					.getFixedDocumentsRequestRoutesDeepSearchTreasholdContextWindowCoeff() * contextWindow;
			if (limit > 0.0) {
				limit = Math.min(threashold, limit);
			} else {
				limit = threashold;
			}
		}
		return (int) limit;

	}

}
