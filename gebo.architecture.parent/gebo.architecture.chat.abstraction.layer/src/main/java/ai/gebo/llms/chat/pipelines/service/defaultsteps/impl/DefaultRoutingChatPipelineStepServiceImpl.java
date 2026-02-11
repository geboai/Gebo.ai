package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

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
	public static final String START_INTERNAL_KNOWLEDGEBASE_CATALOG = "INTERNAL_KNOWLEDGEBASE_CATALOG";
	public static final String DEFAULT_ROUTING_STEP = "default-routing-step";

	public DefaultRoutingChatPipelineStepServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao,
			DefaultRagStreamingOutputChatPipelineStepServiceImpl defaultRagStreamingOutputChatPipelineStepServiceImpl,
			ChatPipelinesConfiguration chatPipelinesConfig, IGDeepSearchService deepSearchService,
			IGToolCallbackSourceRepositoryPattern toolCallbackSourceRepo,
			IDeepSearchDataSourcesCatalogsService deepSearchDataSourcesCatalogsService, IGPromptConfigDao promptsDao,
			IGKnowledgebaseVisibilityService visibleKnowledgeBasesService, IGPersistentObjectManager persistentManager,
			IGPromptsParametersCacheService promptsParamsCacheService) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
		this.chatPipelinesConfig = chatPipelinesConfig;
		this.deepSearchService = deepSearchService;
		this.toolCallbackSourceRepo = toolCallbackSourceRepo;
		this.deepSearchDataSourcesCatalogsService = deepSearchDataSourcesCatalogsService;
		this.promptsDao = promptsDao;
		this.visibleKnowledgeBasesService = visibleKnowledgeBasesService;
		this.persistentManager = persistentManager;
		this.promptsParamsCacheService = promptsParamsCacheService;
	}

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return DefaultRoutingChatPipelineStepServiceImpl.DEFAULT_ROUTING_STEP;
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
				// Doing a query rewrite
				String query = runtimeData.getRequestResources().getLastRequest().getQuery();

				String latestInteractions = RoutingPromptUtil
						.latestInteractionsPromptPart(runtimeData.getRequestResources().getLastInteractions());
				GPromptConfig rewritePrompt = promptsDao
						.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_QUERY_REWRITING_PROMPT);
				String rewrited_query = callLLM(serviceModel, rewritePrompt.getPrompt(), query,
						Map.of(DefaultPipelineSharedPromptPlaceholders.LATEST_INTERACTIONS_TEMPLATE_PARAM,
								latestInteractions));
				runtimeData.getRequestResources().getLastRequest().setRewrittenQuery(rewrited_query);
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

					GPromptConfig _prompt = this.promptsDao
							.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_ROUTING_DECISION_PROMPT);
					final String prompt = _prompt.getPrompt();
					Supplier<Map<String, Object>> paramsProvider = () -> {
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Begin Calculating router params to be cached");
						}
						Map<String, Object> templateParams = new HashMap<String, Object>();
						String deepSearchDataSources = deepSearchDataSourcesListPromptPart();
						String toolsList = toolsListPromptPart(chatModel);
						String internalKnowledgeBaseCatalog = deepSearchInternalKnowledgeBasePromptPart(runtimeData);
						templateParams.put(
								DefaultPipelineSharedPromptPlaceholders.INTERNAL_KNOWLEDGE_BASE_CATALOG_TEMPLATE_PARAM,
								internalKnowledgeBaseCatalog);
						templateParams.put(
								DefaultPipelineSharedPromptPlaceholders.DEEP_SEARCH_DATA_SOURCES_TEMPLATE_PARAM,
								deepSearchDataSources);
						templateParams.put(DefaultPipelineSharedPromptPlaceholders.TOOLS_LIST_TEMPLATE_PARAM, toolsList);
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("End Calculating router params to be cached");
						}
						return templateParams;
					};
					final Map<String, Object> templateParams = this.promptsParamsCacheService.lookupCache(
							GeboPromptsLibrary.DEFAULT_PIPELINE_ROUTING_DECISION_PROMPT,
							runtimeData.getUserChatContext().getCode(),
							DefaultRoutingChatPipelineStepServiceImpl.DEFAULT_ROUTING_STEP, 120000, paramsProvider);
					templateParams.put(DefaultPipelineSharedPromptPlaceholders.LATEST_INTERACTIONS_TEMPLATE_PARAM,
							latestInteractions);
					int usedTokens = tokensLength(prompt, latestInteractions, templateParams.toString(),
							rewrited_query);
					int remainingContext = (int) (((double) (serviceModel.getContextLength() - usedTokens)) * 0.8d);
					final int documentsTokenBudget = Math.min(remainingContext,
							this.chatPipelinesConfig.getMaxRoutingDecisionDocumentsTokenBudget());
					String documents = RoutingPromptUtil.documentsPromptPart(runtimeData.getRequestResources(),
							documentsTokenBudget);
					templateParams.put(DOCUMENTS, documents);
					String decisionText = callLLM(serviceModel, prompt, rewrited_query, templateParams);

					SimpleDecision decision = parseDecision(decisionText);
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Routing decision object:" + decision);
					}
					List<String> routes = futureRoutes(decision.getDecision(), RespondingWith.PURE_LLM_RESPONSE,
							runtimeData.isStreamingOutput());
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

							return templateParams;
						}
					};

					rd = new RoutingDecision(routes, routingEntry, decision.getDecision().name());
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

	@AllArgsConstructor
	@Getter
	@ToString
	static class SimpleDecision {
		private final RespondingWith decision;
		private final String motivation;
	}

	private SimpleDecision parseDecision(String decision) {
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
		return new SimpleDecision(_decision, secondRow(decision));
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

	private String deepSearchInternalKnowledgeBasePromptPart(ChatPipelineExecutionRuntimeData runtimeData) {
		StringBuffer buffer = new StringBuffer();
		List<GKnowledgeBase> knowledgeBases = new ArrayList<GKnowledgeBase>();
		if (runtimeData.getChatProfile() != null) {
			if (runtimeData.getChatProfile().getUserChoosesKnowledgeBases() != null
					&& runtimeData.getChatProfile().getUserChoosesKnowledgeBases()) {
				knowledgeBases.addAll(visibleKnowledgeBasesService.allVisibleKnowledgebases());
			} else {
				List<String> list = runtimeData.getChatProfile().getKnowledgeBaseCodes();
				if (list != null && !list.isEmpty()) {
					knowledgeBases.addAll(visibleKnowledgeBasesService.visiblesAndChildKnowledgebases(list));
				}
			}
		}
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
