package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

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

import ai.gebo.architecture.ai.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.ai.model.ToolCategoriesTree;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingAndProvidingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptConfigDao;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptsParametersCacheService;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IChatPipelineStepRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IStepContribution;
import ai.gebo.llms.chat.pipelines.model.RoutingDecision;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IRoutingChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.model.RespondingWith;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceMetaInfos;
import ai.gebo.llms.deepsearch.service.IDeepSearchDataSourcesCatalogsService;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Component
public class DefaultRoutingChatPipelineStepServiceImpl extends BaseLLMSInvokingAndProvidingService
		implements IRoutingChatPipelineStepService {
	static final String SEARCHED_SYSTEM = "searchedSystem";
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
	private final IGDeepSearchService deepSearchService;
	private final IGToolCallbackSourceRepositoryPattern toolCallbackSourceRepo;
	private final IDeepSearchDataSourcesCatalogsService deepSearchDataSourcesCatalogsService;
	private final IGKnowledgebaseVisibilityService visibleKnowledgeBasesService;
	private final IGPersistentObjectManager persistentManager;
	private final IGPromptConfigDao promptsDao;
	private final IGPromptsParametersCacheService promptsParamsCacheService;
	private final IGChatSessionLifeCycleService chatSessionLifecycleService;
	public static final String START_INTERNAL_KNOWLEDGEBASE_CATALOG = "INTERNAL_KNOWLEDGEBASE_CATALOG";
	public static final String DEFAULT_ROUTING_STEP = "default-routing-step";

	public DefaultRoutingChatPipelineStepServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao,
			DefaultRagStreamingOutputChatPipelineStepServiceImpl defaultRagStreamingOutputChatPipelineStepServiceImpl,
			ChatPipelinesConfiguration chatPipelinesConfig, IGDeepSearchService deepSearchService,
			IGToolCallbackSourceRepositoryPattern toolCallbackSourceRepo,
			IDeepSearchDataSourcesCatalogsService deepSearchDataSourcesCatalogsService, IGPromptConfigDao promptsDao,
			IGKnowledgebaseVisibilityService visibleKnowledgeBasesService, IGPersistentObjectManager persistentManager,
			IGPromptsParametersCacheService promptsParamsCacheService,
			IGChatSessionLifeCycleService chatSessionLifecycleService) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
		this.chatPipelinesConfig = chatPipelinesConfig;
		this.deepSearchService = deepSearchService;
		this.toolCallbackSourceRepo = toolCallbackSourceRepo;
		this.deepSearchDataSourcesCatalogsService = deepSearchDataSourcesCatalogsService;
		this.promptsDao = promptsDao;
		this.visibleKnowledgeBasesService = visibleKnowledgeBasesService;
		this.persistentManager = persistentManager;
		this.promptsParamsCacheService = promptsParamsCacheService;
		this.chatSessionLifecycleService = chatSessionLifecycleService;
	}

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return DefaultRoutingChatPipelineStepServiceImpl.DEFAULT_ROUTING_STEP;
	}

	private String doRequestRewrite(ChatPipelineExecutionRuntimeData runtimeData, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, String latestInteractions)
			throws GeboChatSessionLifecycleException, IOException {
		String query = runtimeData.getRequestResources().getLastRequest().getQuery();

		GPromptConfig rewritePrompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_QUERY_REWRITING_PROMPT);
		String rewrited_query = callLLM(serviceModel, rewritePrompt.getPrompt(), query,
				Map.of(DefaultPipelineSharedPromptPlaceholders.LATEST_INTERACTIONS_TEMPLATE_PARAM, latestInteractions));
		runtimeData.getRequestResources().getLastRequest().setRewrittenQuery(rewrited_query);
		this.chatSessionLifecycleService.updateRequest(runtimeData.getRequestResources().getLastRequest());
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
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("End Calculating router params to be cached");
				}
				return templateParams;
			} catch (Throwable th) {
				LOGGER.error("Error in template params loader", th);
				throw new RuntimeException("Error in template params loader", th);
			}
		};
		final Map<String, Object> templateParams = this.promptsParamsCacheService.lookupCache(
				GeboPromptsLibrary.DEFAULT_PIPELINE_ROUTING_DECISION_PROMPT,
				runtimeData.getRequestResources().getLastRequest().getUserChatContextCode(),
				DefaultRoutingChatPipelineStepServiceImpl.DEFAULT_ROUTING_STEP, 120000, paramsProvider);
		templateParams.put(DefaultPipelineSharedPromptPlaceholders.LATEST_INTERACTIONS_TEMPLATE_PARAM,
				latestInteractions);
		int usedTokens = tokensLength(prompt, latestInteractions, templateParams.toString(), rewrited_query);
		int remainingContext = (int) (((double) (serviceModel.getContextLength() - usedTokens)) * 0.8d);
		final int documentsTokenBudget = Math.min(remainingContext,
				this.chatPipelinesConfig.getMaxRoutingDecisionDocumentsTokenBudget());
		String documents = RoutingPromptUtil.documentsPromptPart(runtimeData.getRequestResources(),
				documentsTokenBudget);
		templateParams.put(DOCUMENTS, documents);
		Map<String, List<String>> decisionMap = callLLMRepeatableFieldEntryOutput(serviceModel, prompt, rewrited_query,
				templateParams, List.of(ROUTING_DECISION, SEARCHED_SYSTEM));
		if (decisionMap.containsKey(SEARCHED_SYSTEM)) {
			List<String> systems = decisionMap.get(SEARCHED_SYSTEM);
			List<String> realCodes = new ArrayList<String>();
			systems.forEach(x -> {
				if (x.startsWith(RoutingPromptUtil.SHALLOW_SYSTEM_PREFIX)) {
					realCodes.add(x.substring(RoutingPromptUtil.SHALLOW_SYSTEM_PREFIX.length()));
				}
			});
			decisionMap.put(SEARCHED_SYSTEM, realCodes);
		}
		RespondingWith decision = decisionMap.containsKey(ROUTING_DECISION)
				? parseDecision(decisionMap.get(ROUTING_DECISION).toString())
				: RespondingWith.PURE_LLM_RESPONSE;
		final Map<String, Object> environmentMap = new HashMap<String, Object>();
		environmentMap.putAll(templateParams);
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

	@Override
	public RoutingDecision execute(ChatPipelineExecutionRuntimeData runtimeData, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel) throws ChatPipelineException {

		RoutingDecision rd = null;
		if (runtimeData.getRequestResources().getLastRequest() != null
				&& runtimeData.getRequestResources().getLastRequest().getChatPipelineProcessId() != null) {
			rd = new RoutingDecision(
					List.of(runtimeData.getRequestResources().getLastRequest().getChatPipelineProcessId()),
					IChatPipelineStepRuntimeData
							.VoidRetun(DefaultRoutingChatPipelineStepServiceImpl.DEFAULT_ROUTING_STEP),
					runtimeData.getRequestResources().getLastRequest().getChatPipelineProcessId());

		} else {
			try {
				String latestInteractions = RoutingPromptUtil
						.latestInteractionsPromptPart(runtimeData.getRequestResources().getLastInteractions());
				// Doing a query rewrite
				String rewrited_query = doRequestRewrite(runtimeData, chatModel, serviceModel, latestInteractions);
				// if actual resource has chat with documents or uploads with more than actual
				// tokens budget than doing a deep search ONLY on
				// Documents being in request
				int forcedDocumentsTotal = runtimeData.getRequestResources().getChatWithDocuments().getTokensSize()
						+ runtimeData.getRequestResources().getUploadedDocuments().getTokensSize();
				int threasholdForForcedDeepSearch = getChatWithDocsAndUploadedSizeTriggersDeepSearchThreashold(
						chatModel);
				if (forcedDocumentsTotal >= threasholdForForcedDeepSearch) {
					rd = createDeepSearchHeavyDocumentsFixedRoute(runtimeData);
				} else {
					rd = doDecideRoute(runtimeData, chatModel, serviceModel, latestInteractions, rewrited_query);
				}
			} catch (Throwable th) {
				LOGGER.error("Exception in chat pipeline routing falling back to PURE_LLM_RESPONSE", th);
				rd = new RoutingDecision(
						List.of(DefaultStreamingOutputChatPipelineServiceImpl.DEFAULT_STREAMING_OUTPUT),
						IChatPipelineStepRuntimeData
								.VoidRetun(DefaultRoutingChatPipelineStepServiceImpl.DEFAULT_ROUTING_STEP),
						RespondingWith.PURE_LLM_RESPONSE.name());
			}
		}
		return rd;

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

	private RoutingDecision createDeepSearchHeavyDocumentsFixedRoute(ChatPipelineExecutionRuntimeData runtimeData) {
		RoutingDecision rd = new RoutingDecision(
				List.of(DefaultDeepSearchStreamingOutputChatPipelineStepServiceImpl.DEFAULT_DEEPSEARCH_STREAMING),
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
				}, RespondingWith.DEEP_SEARCH_RESPONSE.name());
		return rd;
	}

	private String deepSearchInternalKnowledgeBasePromptPart(ChatPipelineExecutionRuntimeData runtimeData)
			throws GeboChatSessionLifecycleException {
		StringBuffer buffer = new StringBuffer();
		List<GKnowledgeBase> knowledgeBases = this.chatSessionLifecycleService
				.getSessionAvailableKnowledgeBases(runtimeData.getRequestResources().getLastRequest());

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
		case SHALLOW_SEARCH_RESPONSE: {
			return List.of(DefaultShallowSearchOutputPipelineServiceImpl.DEFAULT_SHALLOW_SEARCH_STREAMING_OUTPUT);
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
