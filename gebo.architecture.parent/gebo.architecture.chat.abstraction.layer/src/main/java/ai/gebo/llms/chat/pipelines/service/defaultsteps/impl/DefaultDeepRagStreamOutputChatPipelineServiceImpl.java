package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.services.CommonChatPromptParamsUtil;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchResponse;
import ai.gebo.llms.deepsearch.model.DeepSearchState;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchChatResponseEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchKnowledgeBasesProcessedEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchProcessedEvent;
import ai.gebo.llms.deepsearch.service.IGDeepSearchConfigProvider;
import ai.gebo.llms.deepsearch.service.IGInternalKnlowledgeBaseRagStepDeepSearchService;
import ai.gebo.llms.deepsearch.service.impl.Common;
import ai.gebo.llms.deepsearch.service.impl.DeepSearchServiceImpl;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class DefaultDeepRagStreamOutputChatPipelineServiceImpl extends BaseLLMSInvokingService
		implements IStreamingOutputChatPipelineService {
	public static final String DEFAULT_DEEPRAG_STREAMING = "default-deeprag-streaming";
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DefaultDeepRagStreamOutputChatPipelineServiceImpl.class);
	private final IGInternalKnlowledgeBaseRagStepDeepSearchService internalKnowledgeBaseRagStepDeepSearchService;
	private final IGDeepSearchConfigProvider deepSearchConfigProvider;
	private final IGChatSessionLifeCycleService sessionLifecycleService;
	private final IGSecurityService securityService;
	private final IDocumentsChunkService chunkingService;
	private final IGeboThreadManager threadManager;
	private final DeepSearchServiceImpl deepSearchServiceImpl;
	private final IGPromptConfigDao promptsDao;

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return DEFAULT_DEEPRAG_STREAMING;
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws ChatPipelineException, GeboChatSessionLifecycleException, FullTextException, LLMConfigException {
		DeepSearchRequest deepSearchRequest = new DeepSearchRequest();
		GeboChatRequest request = runtimeData.getRequestResources().getCurrentRequest();
		GeboChatResponse response = runtimeData.getChatResponse();
		final String chunkSessionId = chunkingService
				.createChunkingSession("deepsearch:" + deepSearchRequest.getCode());
		AIDocumentsSet forcedDocuments = AIDocumentsSet.join(runtimeData.getRequestResources().getChatWithDocuments(),
				runtimeData.getRequestResources().getUploadedDocuments(),
				runtimeData.getRequestResources().getRetrievedDocuments());
		List<IGConfigurableEmbeddingModel> embeddingModels = sessionLifecycleService
				.getSessionEmbeddingModels(runtimeData.getRequestResources().getCurrentRequest());
		UserInfos userInfo = securityService.getCurrentUser();
		final ReactiveIdentityUtil doAs = ReactiveIdentityUtil.create();
		Flux<AbstractDeepSearchEvent> flux = internalKnowledgeBaseRagStepDeepSearchService.knowledgeBaseDeepSearch(
				deepSearchRequest, new DeepSearchState(), runtimeData.getMinimalChatContext(), forcedDocuments,
				deepSearchConfigProvider.get(), userInfo, chatModel, serviceModel, chunkSessionId, embeddingModels);
		Vector<DeepSearchKnowledgeBasesProcessedEvent> resultFiltered = new Vector<DeepSearchKnowledgeBasesProcessedEvent>();
		flux = deepSearchServiceImpl.manageTrailingChatSessionEvents(doAs, flux, request, response);
		flux = flux.map(x -> {
			if (x instanceof DeepSearchKnowledgeBasesProcessedEvent processed) {
				resultFiltered.add(processed);
			}
			return x;
		});
		flux = Flux.concat(flux,
				trailProcess(resultFiltered, deepSearchRequest, chatModel, runtimeData.getMinimalChatContext()));
		flux = flux.onErrorResume(Common.commonFallBack(deepSearchRequest));
		flux.doOnComplete(() -> {
			try {
				this.sessionLifecycleService.chatRequestCompleted(runtimeData.getRequestResources().getCurrentRequest(),
						chatModel);
			} catch (GeboChatSessionLifecycleException | LLMConfigException | IOException e) {
				LOGGER.error("Exceptinin deep search streaming pipeline handler", e);
			}
		});
		flux = flux.subscribeOn(threadManager.getScheduler())
				.doOnNext(evt -> deepSearchServiceImpl.persistSideEffects(doAs, evt));
		return deepSearchServiceImpl.mapToChatFlux(flux, DeepSearchChatResponseEvent.class);

	}

	private Flux<AbstractDeepSearchEvent> trailProcess(Vector<DeepSearchKnowledgeBasesProcessedEvent> resultFiltered,
			DeepSearchRequest deepSearchRequest, IGConfigurableChatModel usedModel,
			MinimalChatContext minimalChatContext) {

		return Flux.defer(() -> {
			DeepSearchProcessedEvent finalEvent = new DeepSearchProcessedEvent();
			finalEvent.setInputData(deepSearchRequest);
			finalEvent.setOutputData(new DeepSearchResponse());
			finalEvent.getOutputData().setCode(UUID.randomUUID().toString());
			finalEvent.getOutputData().setDeepsearchCode(deepSearchRequest.getCode());
			finalEvent.getOutputData().processedBy(usedModel);
			boolean empty = resultFiltered.isEmpty()
					|| (resultFiltered.get(0).getOutputData().getSearchResultsEmpty() != null
							&& resultFiltered.get(0).getOutputData().getSearchResultsEmpty());
			finalEvent.getOutputData().setSearchResultsEmpty(empty);
			finalEvent.getOutputData().setProcessPercentage(100);
			finalEvent.getOutputData().setDescription("Internal knowledge base");
			if (!empty) {
				String text = resultFiltered.get(0).getOutputData().getResponse();
				finalEvent.getOutputData().setResponse(text);
			} else {
				Map<String, Object> promptParams = CommonChatPromptParamsUtil
						.preparePromptParameters(minimalChatContext);
				String backupText = callLLM(usedModel,
						promptsDao.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_EMPTY_RESULTS_FALLBACK_PROMPT)
								.getPrompt(),
						GeboChatRequest.actualQuery(minimalChatContext.getCurrentRequest()), promptParams);
				finalEvent.getOutputData().setResponse(backupText);

			}
			finalEvent.getOutputData().setProcessPercentage(100);
			return Flux.just(finalEvent);
		});
	}

}
