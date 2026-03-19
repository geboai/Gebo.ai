package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.ai.model.GPromptConfig;
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
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
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
	private static final String DELIVERABLE_EXPLANATION_TEMPLATE_PARAM = "deliverableExplanation";
	private static final String DELIVERABLE_TEMPLATE_PARAM = "deliverable";
	private static final String REWRITTEN_QUERY_TEMPLATE_PARAM = "rewrittenQuery";
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

	private String doRequestRewriteAndUserIntent(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, String latestInteractions)
			throws GeboChatSessionLifecycleException, IOException {
		String query = runtimeData.getRequestResources().getCurrentRequest().getQuery();
		Map<String, Object> params = CommonChatPromptParamsUtil
				.preparePromptParameters(runtimeData.getMinimalChatContext());
		params.put(DefaultPipelineSharedPromptPlaceholders.DELIVERABLE_TYPES_LIST_TEMPLATE_PARAM,
				createDeliverableTypesList());
		GPromptConfig rewritePrompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_QUERY_REWRITING_PROMPT);
		Map<String, List<String>> data = callLLMRepeatableFieldEntryOutput(serviceModel, rewritePrompt.getPrompt(),
				query, params, List.of(DELIVERABLE_FIELD, REWRITTEN_QUERY_FIELD));
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
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("User intent:" + userIntent.name());
			LOGGER.debug("Rewritten query:" + rewrited_query);
		}
		runtimeData.getRequestResources().getCurrentRequest().setUserIntent(userIntent);
		return rewrited_query;
	}

	private RoutingDecision doDecideRoute(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, String latestInteractions,
			String rewrited_query) throws GeboChatSessionLifecycleException {
		GPromptConfig _prompt = this.promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_ROUTING_DECISION_PROMPT);
		final String prompt = _prompt.getPrompt();
		Supplier<Map<String, Object>> paramsProvider = () -> {
			try {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Begin Calculating router params to be cached");
				}
				Map<String, Object> templateParams = new HashMap<String, Object>();
				String deepSearchDataSources = deepSearchDataSourcesListPromptPart();
				String toolsList = toolsListPromptPart(chatModel);
				String internalKnowledgeBaseCatalog = deepSearchInternalKnowledgeBasePromptPart(runtimeData);
				String shallowSystemsCatalog = shallowSearchSystemsCatalog(runtimeData);
				templateParams.put(
						DefaultPipelineSharedPromptPlaceholders.INTERNAL_KNOWLEDGE_BASE_CATALOG_TEMPLATE_PARAM,
						internalKnowledgeBaseCatalog);
				templateParams.put(DefaultPipelineSharedPromptPlaceholders.DEEP_SEARCH_DATA_SOURCES_TEMPLATE_PARAM,
						deepSearchDataSources);
				templateParams.put(DefaultPipelineSharedPromptPlaceholders.TOOLS_LIST_TEMPLATE_PARAM, toolsList);
				templateParams.put(DefaultPipelineSharedPromptPlaceholders.SHALLOW_SEARCH_SYSTEMS_TEMPLATE_PARAM,
						shallowSystemsCatalog);
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
		int usedTokens = tokensLength(prompt, latestInteractions, params.toString(), rewrited_query);
		int remainingContext = (int) (((double) (serviceModel.getContextLength() - usedTokens)) * 0.8d);
		final int documentsTokenBudget = Math.min(remainingContext,
				this.chatPipelinesConfig.getMaxRoutingDecisionDocumentsTokenBudget());
		String documents = RoutingPromptUtil.documentsPromptPart(runtimeData.getRequestResources(),
				documentsTokenBudget);
		params.put(DOCUMENTS, documents);
		Map<String, List<String>> decisionMap = callLLMRepeatableFieldEntryOutput(serviceModel, prompt, rewrited_query,
				params, List.of(ROUTING_DECISION,  DELIVERABLE_FIELD, DEEP_SEARCHED_SYSTEMS));

		
		// extracting user intent

		RespondingWith decision = decisionMap.containsKey(ROUTING_DECISION)
				? parseDecision(decisionMap.get(ROUTING_DECISION).toString())
				: RespondingWith.PURE_LLM_RESPONSE;
		final Map<String, Object> environmentMap = new HashMap<String, Object>();
		environmentMap.putAll(params);
		environmentMap.putAll(decisionMap);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Routing decision object:" + decision);
		}
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

		return new RoutingDecision(routes, routingEntry, decision.name());
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

	private DeliverableIntent readUserIntent(Map<String, List<String>> extracted) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin readUserIntent(" + extracted + ")");
		}
		List<String> values = extracted.get(DELIVERABLE_FIELD);
		DeliverableIntent intent = DeliverableIntent.QA;
		if (values != null && !values.isEmpty()) {
			String _intent = values.get(0);
			if (_intent != null) {
				for (DeliverableIntent i : DeliverableIntent.values()) {
					if (_intent.toLowerCase().indexOf(i.name().toLowerCase()) >= 0) {
						intent = i;
						break;
					}
				}
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End readUserIntent(...) output: " + intent.name());
		}
		return intent;

	}

	@Override
	public RoutingDecision execute(ChatPipelineExecutionRuntimeData runtimeData, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel) throws ChatPipelineException {

		RoutingDecision rd = null;

		try {
			String latestInteractions = RoutingPromptUtil.latestInteractionsPromptPart(
					runtimeData.getRequestResources().getChathistory().getLatestEntries().getInteractions());
			// Doing a query rewrite
			String rewrited_query = doRequestRewriteAndUserIntent(runtimeData, chatModel, serviceModel,
					latestInteractions);
			// if actual resource has chat with documents or uploads with more than actual
			// tokens budget than doing a deep search ONLY on
			// Documents being in request
			int forcedDocumentsTotal = runtimeData.getRequestResources().getChatWithDocuments().getTokensSize()
					+ runtimeData.getRequestResources().getUploadedDocuments().getTokensSize();
			int threasholdForForcedDeepSearch = getChatWithDocsAndUploadedSizeTriggersDeepSearchThreashold(chatModel);
			if (forcedDocumentsTotal >= threasholdForForcedDeepSearch) {
				rd = createKnowledgeBaseSearchHeavyDocumentsFixedRoute(runtimeData);
			} else {
				if (runtimeData.getRequestResources().getCurrentRequest() != null
						&& runtimeData.getRequestResources().getCurrentRequest().getChatPipelineProcessId() != null
						&& runtimeData.getRequestResources().getCurrentRequest().getChatPipelineProcessId().trim()
								.length() > 0) {
					rd = doHandleUserRequestedRouting(runtimeData);
				} else {
					rd = doDecideRoute(runtimeData, chatModel, serviceModel, latestInteractions, rewrited_query);
				}
			}
			this.chatSessionLifecycleService.updateRequest(runtimeData.getRequestResources().getCurrentRequest());
		} catch (Throwable th) {
			LOGGER.error("Exception in chat pipeline routing falling back to PURE_LLM_RESPONSE", th);
			rd = new RoutingDecision(List.of(DefaultStreamingOutputChatPipelineServiceImpl.DEFAULT_STREAMING_OUTPUT),
					IChatPipelineStepRuntimeData
							.VoidRetun(DefaultRoutingChatPipelineStepServiceImpl.DEFAULT_ROUTING_STEP),
					RespondingWith.PURE_LLM_RESPONSE.name());
		}
		// Setting the decided routing code
		runtimeData.getChatResponse().setPipelineRouterDecisionCode(rd.getPipelineRouterDecisionCode());
		return rd;

	}

	private RoutingDecision doHandleUserRequestedRouting(ChatPipelineExecutionRuntimeData runtimeData)
			throws ChatPipelineException {
		String routingDecisionId = runtimeData.getRequestResources().getCurrentRequest().getChatPipelineProcessId();
		RespondingWith respondingWith = RespondingWith.valueOf(routingDecisionId);
		RoutingDecision decision = new RoutingDecision(
				futureRoutes(respondingWith, RespondingWith.PURE_LLM_RESPONSE, true),
				IChatPipelineStepRuntimeData.VoidRetun(DefaultRoutingChatPipelineStepServiceImpl.DEFAULT_ROUTING_STEP),
				runtimeData.getRequestResources().getCurrentRequest().getChatPipelineProcessId());
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
					} else {
						throw new ChatPipelineException(
								"The request does not contain parameter: " + par.getParamName());
					}
				}
			}
		}
		return decision;
	}

	private String shallowSearchSystemsCatalog(ChatPipelineExecutionRuntimeData runtimeData) {
		List<DeepSearchDataSourceMetaInfos> systems = this.deepSearchDataSourcesCatalogsService
				.getActiveDeepSearchDataSourceMetaInfos();
		return RoutingPromptUtil.shallowSearchSystemsCatalog(systems);
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

	private String secondRow(String decision) {
		StringTokenizer tokenizer = new StringTokenizer(decision, "\r");
		String _out = "";
		if (tokenizer.hasMoreTokens()) {
			tokenizer.nextToken();
			if (tokenizer.hasMoreTokens()) {
				_out = tokenizer.nextToken();
			}
		}
		return _out;
	}

	private RoutingDecision createKnowledgeBaseSearchHeavyDocumentsFixedRoute(
			ChatPipelineExecutionRuntimeData runtimeData) {
		runtimeData.getRequestResources().getCurrentRequest().setUserIntent(DeliverableIntent.REPORT);
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
				}, RespondingWith.CHAT_WITH_FILES.name());
		return rd;
	}

	private String deepSearchInternalKnowledgeBasePromptPart(ChatPipelineExecutionRuntimeData runtimeData)
			throws GeboChatSessionLifecycleException {
		StringBuffer buffer = new StringBuffer();
		List<GKnowledgeBase> knowledgeBases = this.chatSessionLifecycleService
				.getSessionAvailableKnowledgeBases(runtimeData.getRequestResources().getCurrentRequest());

		return deepSearchInternalKnowledgeBasePromptPart(knowledgeBases);
	}

	private String deepSearchInternalKnowledgeBasePromptPart(List<GKnowledgeBase> knowledgeBases) {
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

	private String deepSearchDataSourcesListPromptPart() {
		StringBuffer buffer = new StringBuffer();
		List<DeepSearchDataSourceMetaInfos> dataSources = deepSearchDataSourcesCatalogsService
				.getActiveDeepSearchDataSourceMetaInfos();
		buffer.append(RoutingPromptUtil.dataSourcesListPromptPart(dataSources));
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
