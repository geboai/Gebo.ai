package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import ai.gebo.llms.abstraction.layer.services.BaseLlmsInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptConfigDao;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IChatPipelineStepRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IStepContribution;
import ai.gebo.llms.chat.pipelines.model.RoutingDecision;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IRoutingChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.model.RespondingWith;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.model.RoutingDecisionResponse;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceMetaInfos;
import ai.gebo.llms.deepsearch.service.IDeepSearchDataSourcesCatalogsService;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import ai.gebo.model.base.GBaseObject;

@Component
public class DefaultRoutingChatPipelineStepServiceImpl extends BaseLlmsInvokingService
		implements IRoutingChatPipelineStepService {
	private static final String TOPICS = "topics: ";
	private static final String END_KNOWLEDGE_BASE = "END_KNOWLEDGE_BASE";
	private static final String KNOWLEDGE_BASE = "KNOWLEDGE_BASE";
	private static final String END_INTERNAL_KNOWLEDGEBASE_CATALOG = "END_INTERNAL_KNOWLEDGEBASE_CATALOG";
	private static final String INTERNAL_KNOWLEDGEBASE_CATALOG = "INTERNAL_KNOWLEDGEBASE_CATALOG";
	private static final String KNOWLEDGE_BASE_TITLE = "knowledge base title: ";
	private static final String INTERNAL_KNOWLEDGE_BASE_CATALOG = "internalKnowledgeBaseCatalog";
	private static final String REWRITE_THREASHOLD = "rewriteThreshold";
	private static final String NEWLINE = "\r\n";
	private static final String DEEP_SEARCH_DATA_SOURCES = "deepSearchDataSources";
	private static final String LATEST_INTERACTIONS = "latestInteractions";
	private static final String DOCUMENTS = "documents";
	private static final String TOOLS_LIST = "toolsList";
	private final static Logger LOGGER = LoggerFactory.getLogger(DefaultRoutingChatPipelineStepServiceImpl.class);
	private final ChatPipelinesConfiguration chatPipelinesConfig;
	private final IGDeepSearchService deepSearchService;
	private final IGToolCallbackSourceRepositoryPattern toolCallbackSourceRepo;
	private final IDeepSearchDataSourcesCatalogsService deepSearchDataSourcesCatalogsService;
	private final IGKnowledgebaseVisibilityService visibleKnowledgeBasesService;
	private final IGPersistentObjectManager persistentManager;
	private final IGPromptConfigDao promptsDao;

	public DefaultRoutingChatPipelineStepServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao,
			DefaultRagStreamingOutputChatPipelineStepServiceImpl defaultRagStreamingOutputChatPipelineStepServiceImpl,
			ChatPipelinesConfiguration chatPipelinesConfig, IGDeepSearchService deepSearchService,
			IGToolCallbackSourceRepositoryPattern toolCallbackSourceRepo,
			IDeepSearchDataSourcesCatalogsService deepSearchDataSourcesCatalogsService, IGPromptConfigDao promptsDao,
			IGKnowledgebaseVisibilityService visibleKnowledgeBasesService,
			IGPersistentObjectManager persistentManager) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
		this.chatPipelinesConfig = chatPipelinesConfig;
		this.deepSearchService = deepSearchService;
		this.toolCallbackSourceRepo = toolCallbackSourceRepo;
		this.deepSearchDataSourcesCatalogsService = deepSearchDataSourcesCatalogsService;
		this.promptsDao = promptsDao;
		this.visibleKnowledgeBasesService = visibleKnowledgeBasesService;
		this.persistentManager = persistentManager;
	}

	public static final String DEFAULT_ROUTING_STEP = "default-routing-step";

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return DEFAULT_ROUTING_STEP;
	}

	@Override
	public RoutingDecision execute(ChatPipelineExecutionRuntimeData runtimeData, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel) throws ChatPipelineException {

		RoutingDecision rd = null;
		if (runtimeData.getRequestResources().getLastRequest() != null
				&& runtimeData.getRequestResources().getLastRequest().getChatPipelineProcessId() != null) {
			rd = new RoutingDecision(
					List.of(runtimeData.getRequestResources().getLastRequest().getChatPipelineProcessId()),
					IChatPipelineStepRuntimeData.VoidRetun(DEFAULT_ROUTING_STEP),
					runtimeData.getRequestResources().getLastRequest().getChatPipelineProcessId());

		} else {
			try {

				RoutingDecisionResponse llmRoutingDecision = null;
				String query = runtimeData.getRequestResources().getLastRequest().getQuery();

				String latestInteractions = RoutingPromptUtil
						.latestInteractionsPromptPart(runtimeData.getRequestResources().getLastInteractions());
				GPromptConfig rewritePrompt = promptsDao
						.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_QUERY_REWRITING_PROMPT);
				query = callLLM(serviceModel, rewritePrompt.getPrompt(), query,
						Map.of(LATEST_INTERACTIONS, latestInteractions));
				runtimeData.getRequestResources().getLastRequest().setRewrittenQuery(query);
				Map<String, Object> templateParams = new HashMap<String, Object>();
				GPromptConfig _prompt = this.promptsDao
						.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_ROUTING_DECISION_PROMPT);
				final String prompt = _prompt.getPrompt();

				String deepSearchDataSources = deepSearchDataSourcesListPromptPart();
				String toolsList = toolsListPromptPart(chatModel);
				String internalKnowledgeBaseCatalog = deepSearchInternalKnowledgeBasePromptPart(runtimeData);
				templateParams.put(INTERNAL_KNOWLEDGE_BASE_CATALOG, internalKnowledgeBaseCatalog);
				templateParams.put(LATEST_INTERACTIONS, latestInteractions);
				templateParams.put(DEEP_SEARCH_DATA_SOURCES, deepSearchDataSources);
				templateParams.put(TOOLS_LIST, toolsList);
				templateParams.put(REWRITE_THREASHOLD, "" + chatPipelinesConfig.getRewriteThreashold());
				int usedTokens = tokensLength(prompt, latestInteractions, deepSearchDataSources, toolsList, query);
				int remainingContext = (int) (((double) (serviceModel.getContextLength() - usedTokens)) * 0.8d);
				final int documentsTokenBudget = Math.min(remainingContext,
						this.chatPipelinesConfig.getMaxRoutingDecisionDocumentsTokenBudget());
				String documents = RoutingPromptUtil.documentsPromptPart(runtimeData.getRequestResources(),
						documentsTokenBudget);
				templateParams.put(DOCUMENTS, documents);
				llmRoutingDecision = callLLMStructuredReturn(serviceModel, prompt, query, templateParams,
						RoutingDecisionResponse.class);

				final RoutingDecisionResponse finalDecision = llmRoutingDecision;
				LOGGER.info("Routing decision object:" + finalDecision);
				List<String> routes = futureRoutes(finalDecision.getResponseRoutingDecision(),
						RespondingWith.PURE_LLM_RESPONSE, runtimeData.isStreamingOutput());
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

						return getEnvironmentContribution(finalDecision, RespondingWith.PURE_LLM_RESPONSE);
					}
				};
				RespondingWith routingDecisionCode = finalDecision != null
						&& finalDecision.getResponseRoutingDecision() != null
								? finalDecision.getResponseRoutingDecision()
								: RespondingWith.PURE_LLM_RESPONSE;
				rd = new RoutingDecision(routes, routingEntry, routingDecisionCode.name());
			} catch (Throwable th) {
				LOGGER.error("Exception in chat pipeline routing falling back to PURE_LLM_RESPONSE", th);
				rd = new RoutingDecision(
						List.of(DefaultStreamingOutputChatPipelineServiceImpl.DEFAULT_STREAMING_OUTPUT),
						IChatPipelineStepRuntimeData.VoidRetun(DEFAULT_ROUTING_STEP),
						RespondingWith.PURE_LLM_RESPONSE.name());
			}
		}
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
			buffer.append(INTERNAL_KNOWLEDGEBASE_CATALOG);
			buffer.append(NEWLINE);
			for (GKnowledgeBase kb : knowledgeBases) {
				buffer.append(KNOWLEDGE_BASE);
				buffer.append(NEWLINE);
				buffer.append(KNOWLEDGE_BASE_TITLE+kb.getDescription());
				buffer.append(NEWLINE);
				GProject example=new GProject();
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
							List<GProjectEndpoint> items = persistentManager.findAllByQbeSettingFunction(GProjectEndpoint.class, (t)->{
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
					LOGGER.error("Exception accessing mongo",e);
				}
			}
			buffer.append(END_KNOWLEDGE_BASE);
			buffer.append(NEWLINE);
			buffer.append(END_INTERNAL_KNOWLEDGEBASE_CATALOG);
			buffer.append(NEWLINE);
		}
		return buffer.toString();
	}

	private void shareExpandedDocuments(RoutingDecisionResponse routingDecision, Map<String, Object> data) {
		if (routingDecision != null && routingDecision.getExpandDocuments() != null
				&& !routingDecision.getExpandDocuments().isEmpty()) {
			data.put(DefaultPipelineSharedEnvironmentUtil.AI_SELECTED_DOCUMENTS, routingDecision.getExpandDocuments());
		}
	}

	protected Map<String, Object> getEnvironmentContribution(RoutingDecisionResponse routingDecision,
			RespondingWith defaultResponse) {
		Map<String, Object> data = new HashMap<String, Object>();
		RespondingWith route = routingDecision != null && routingDecision.getResponseRoutingDecision() != null
				? routingDecision.getResponseRoutingDecision()
				: defaultResponse;
		switch (route) {
		case TOOLS_USE_RESPONSE: {
			shareToolsToCallList(routingDecision, data);
		}
			break;
		case DEEP_SEARCH_RESPONSE: {
			shareDeepSearchSources(routingDecision, data);
		}
		case RAG_LLM_RESPONSE: {
			shareQueriesRewritings(routingDecision, data);
		}
		case RespondingWith.PURE_LLM_RESPONSE: {
			shareExpandedDocuments(routingDecision, data);
		}
			break;

		}
		return data;
	}

	private void shareToolsToCallList(RoutingDecisionResponse routingDecision, Map<String, Object> data) {
		if (routingDecision != null && routingDecision.getToolsToUse() != null
				&& !routingDecision.getToolsToUse().isEmpty()) {
			data.put(DefaultPipelineSharedEnvironmentUtil.AI_SELECTED_TOOLS_LIST, routingDecision.getToolsToUse());
		}
	}

	private void shareQueriesRewritings(RoutingDecisionResponse routingDecision, Map<String, Object> data) {
		if (routingDecision != null && routingDecision.getSuggestedSearches() != null
				&& ((routingDecision.getSuggestedSearches().getRewrittenFullTextSearchSentences() != null
						&& !routingDecision.getSuggestedSearches().getRewrittenFullTextSearchSentences().isEmpty())
						|| (routingDecision.getSuggestedSearches().getRewrittenSemanticSearchSentences() != null
								&& !routingDecision.getSuggestedSearches().getRewrittenSemanticSearchSentences()
										.isEmpty()))) {
			data.put(DefaultPipelineSharedEnvironmentUtil.AI_SELECTED_QUERY_REWRITE_SUGGESTIONS,
					routingDecision.getSuggestedSearches());
		}

	}

	private void shareDeepSearchSources(RoutingDecisionResponse routingDecision, Map<String, Object> data) {
		if (routingDecision != null && routingDecision.getDeepSearchDataSourceCodesToAnalyze() != null
				&& !routingDecision.getDeepSearchDataSourceCodesToAnalyze().isEmpty()) {
			data.put(DefaultPipelineSharedEnvironmentUtil.AI_SELECTED_DEEP_SEARCH_DATA_SOURCES,
					routingDecision.getDeepSearchDataSourceCodesToAnalyze());
		}

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
}
