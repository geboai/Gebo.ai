package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.ai.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.ai.model.ToolCategoriesTree;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.services.BaseLlmsInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IChatPipelineStepRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IStepContribution;
import ai.gebo.llms.chat.pipelines.model.RoutingDecision;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IRoutingChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.model.RespondingWith;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.model.RoutingDecisionResponse;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.base.GBaseObject;

@Component
public class DefaultRoutingChatPipelineStepServiceImpl extends BaseLlmsInvokingService
		implements IRoutingChatPipelineStepService {
	private static final String NEWLINE = "\r\n";
	private static final String DEEP_SEARCH_DATA_SOURCES = "deepSearchDataSources";
	private static final String LATEST_INTERACTIONS = "latestInteractions";
	private static final String DOCUMENTS = "documents";
	private static final String TOOLS_LIST = "toolsList";
	private final static Logger LOGGER = LoggerFactory.getLogger(DefaultRoutingChatPipelineStepServiceImpl.class);
	private final ChatPipelinesConfiguration chatPipelinesConfig;
	private final IGDeepSearchService deepSearchService;
	private final IGToolCallbackSourceRepositoryPattern toolCallbackSourceRepo;

	public DefaultRoutingChatPipelineStepServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao,
			DefaultRagStreamingOutputChatPipelineStepServiceImpl defaultRagStreamingOutputChatPipelineStepServiceImpl,
			ChatPipelinesConfiguration chatPipelinesConfig, IGDeepSearchService deepSearchService,
			IGToolCallbackSourceRepositoryPattern toolCallbackSourceRepo) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
		this.chatPipelinesConfig = chatPipelinesConfig;
		this.deepSearchService = deepSearchService;
		this.toolCallbackSourceRepo = toolCallbackSourceRepo;
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
				Map<String, Object> templateParams = new HashMap<String, Object>();

				final String prompt = chatPipelinesConfig.getDefaultPipelineRoutingDecisionPrompt().getPrompt();

				String latestInteractions = RoutingPromptUtil
						.latestInteractionsPromptPart(runtimeData.getRequestResources().getLastInteractions());
				String deepSearchDataSources = deepSearchDataSourcesListPromptPart();
				String toolsList = toolsListPromptPart(chatModel);

				templateParams.put(LATEST_INTERACTIONS, latestInteractions);
				templateParams.put(DEEP_SEARCH_DATA_SOURCES, deepSearchDataSources);
				templateParams.put(TOOLS_LIST, toolsList);
				int usedTokens = tokensLength(prompt, latestInteractions, deepSearchDataSources, toolsList,
						runtimeData.getRequestResources().getLastRequest().getQuery());
				int remainingContext =(int) (((double) (serviceModel.getContextLength() - usedTokens))*0.8d);
				final int documentsTokenBudget = Math.min(remainingContext,
						this.chatPipelinesConfig.getMaxRoutingDecisionDocumentsTokenBudget());
				String documents = RoutingPromptUtil.documentsPromptPart(runtimeData.getRequestResources(),
						documentsTokenBudget);
				templateParams.put(DOCUMENTS, documents);
				RoutingDecisionResponse llmRoutingDecision = callLLMStructuredReturn(serviceModel, prompt,
						runtimeData.getRequestResources().getLastRequest().getQuery(), templateParams,
						RoutingDecisionResponse.class);
				LOGGER.info("Routing decision object:" + llmRoutingDecision);
				List<String> routes = futureRoutes(llmRoutingDecision.getResponseRoutingDecision(),
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

						return getEnvironmentContribution(llmRoutingDecision, RespondingWith.PURE_LLM_RESPONSE);
					}
				};
				RespondingWith routingDecisionCode = llmRoutingDecision != null
						&& llmRoutingDecision.getResponseRoutingDecision() != null
								? llmRoutingDecision.getResponseRoutingDecision()
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
		if (routingDecision != null && routingDecision.getQueryRewritings() != null
				&& ((routingDecision.getQueryRewritings().getRewrittenFullTextSearchSentences() != null
						&& !routingDecision.getQueryRewritings().getRewrittenFullTextSearchSentences().isEmpty())
						|| (routingDecision.getQueryRewritings().getRewrittenSemanticSearchSentences() != null
								&& !routingDecision.getQueryRewritings().getRewrittenSemanticSearchSentences()
										.isEmpty()))) {
			data.put(DefaultPipelineSharedEnvironmentUtil.AI_SELECTED_QUERY_REWRITE_SUGGESTIONS,
					routingDecision.getQueryRewritings());
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
		List<GBaseObject> dataSources = deepSearchService.getDeepSearchActiveHandlers();
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
