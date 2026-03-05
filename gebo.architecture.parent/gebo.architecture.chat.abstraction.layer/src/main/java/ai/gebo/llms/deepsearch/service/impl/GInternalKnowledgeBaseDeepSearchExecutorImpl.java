package ai.gebo.llms.deepsearch.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.services.CommonChatPromptParamsUtil;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchResponse;
import ai.gebo.llms.deepsearch.model.DeepSearchState;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchChatResponseEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchKnowledgeBasesProcessedEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchProcessedEvent;
import ai.gebo.llms.deepsearch.repository.DeepSearchRequestRepository;
import ai.gebo.llms.deepsearch.service.IGDeepSearchConfigProvider;
import ai.gebo.llms.deepsearch.service.IGInternalKnlowledgeBaseRagDeepSearchService;
import ai.gebo.llms.deepsearch.service.IGInternalKnowledgeBaseDeepSearchExecutor;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@AllArgsConstructor
public class GInternalKnowledgeBaseDeepSearchExecutorImpl extends BaseLLMSInvokingService
		implements IGInternalKnowledgeBaseDeepSearchExecutor {
	private final static Logger LOGGER = LoggerFactory.getLogger(GInternalKnowledgeBaseDeepSearchExecutorImpl.class);
	private final IGInternalKnlowledgeBaseRagDeepSearchService internalKnowledgeBaseRagStepDeepSearchService;
	private final IGDeepSearchConfigProvider deepSearchConfigProvider;
	private final IGChatSessionLifeCycleService sessionLifecycleService;
	private final IGSecurityService securityService;
	private final IDocumentsChunkService chunkingService;
	private final IGeboThreadManager threadManager;
	private final DeepSearchServiceImpl deepSearchServiceImpl;
	private final IGPromptConfigDao promptsDao;
	private final DeepSearchRequestRepository deepSearchRequestRepository;

	@Override
	public Flux<GeboChatMessageEnvelope> execute(LLMChatRequestResources requestResources,
			MinimalChatContext minimalChatContext, GeboChatResponse response, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel) throws LLMConfigException, IOException, GeboIngestionException,
			GeboContentHandlerSystemException, SearchServiceException, GeboChatSessionLifecycleException {
		GeboChatRequest request = requestResources.getCurrentRequest();
		DeepSearchRequest deepSearchRequest = new DeepSearchRequest();
		deepSearchRequest.setQuery(GeboChatRequest.actualQuery(request));
		deepSearchRequest.setDeepSearchDataSources(List.of());
		deepSearchRequest.setUserChatContextCode(request.getUserChatContextCode());
		deepSearchRequest.setUserIntent(request.getUserIntent());
		deepSearchRequest.setKnowledgeBases(request.getChoosedKnowledgeBases());
		deepSearchRequest.setChatRequestCode(request.getId());
		response.setDeepSearchRequestId(deepSearchRequest.getCode());
		this.deepSearchRequestRepository.save(deepSearchRequest);
		final String chunkSessionId = chunkingService
				.createChunkingSession("deepsearch:" + deepSearchRequest.getCode());
		AIDocumentsSet forcedDocuments = AIDocumentsSet.join(requestResources.getChatWithDocuments(),
				requestResources.getUploadedDocuments(), requestResources.getRetrievedDocuments());
		List<IGConfigurableEmbeddingModel> embeddingModels = sessionLifecycleService.getSessionEmbeddingModels(request);
		UserInfos userInfo = securityService.getCurrentUser();
		final ReactiveIdentityUtil doAs = ReactiveIdentityUtil.create();
		Flux<AbstractDeepSearchEvent> flux = internalKnowledgeBaseRagStepDeepSearchService.knowledgeBaseDeepSearch(
				deepSearchRequest, new DeepSearchState(), minimalChatContext, forcedDocuments,
				deepSearchConfigProvider.get(), userInfo, chatModel, serviceModel, chunkSessionId, embeddingModels);
		Vector<DeepSearchKnowledgeBasesProcessedEvent> resultFiltered = new Vector<DeepSearchKnowledgeBasesProcessedEvent>();
		flux = flux.map(x -> {
			if (x instanceof DeepSearchKnowledgeBasesProcessedEvent processed) {
				resultFiltered.add(processed);
			}
			return x;
		});
		flux = Flux.concat(flux, trailProcess(resultFiltered, deepSearchRequest, chatModel, minimalChatContext));
		flux = deepSearchServiceImpl.manageTrailingChatSessionEvents(doAs, flux, request, response);
		flux = flux.onErrorResume(Common.commonFallBack(deepSearchRequest));
		flux.doOnComplete(() -> {
			try {
				doAs.<GeboChatSessionLifecycleException, LLMConfigException, IOException>doAsWith3Exceptions(() -> {
					this.sessionLifecycleService.chatRequestCompleted(request, chatModel);
					this.chunkingService.disposeChunkingSession(chunkSessionId);
				});
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
