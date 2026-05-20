package ai.gebo.llms.deepsearch.service;

import java.io.IOException;
import java.util.List;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.deepsearch.datasources.model.AbstractPureSearchDocumentResultEntry;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchState;
import ai.gebo.llms.deepsearch.model.IDeepSearchResult;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceService.DocumentWithSearchResult;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.system.ingestion.GeboIngestionException;
import reactor.core.publisher.Flux;

public interface IGInternalKnlowledgeBaseRagDeepSearchService {

	public Flux<AbstractDeepSearchEvent> knowledgeBaseDeepSearch(DeepSearchRequest request, boolean runSearches,
			DeepSearchState state, MinimalChatContext minimalChatContext, AIDocumentsSet sessionDocuments,
			DeepSearchConfig configuration, UserInfos userInfos, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, String chunkingSessionId,
			List<IGConfigurableEmbeddingModel> embeddingModels)
			throws GeboChatSessionLifecycleException, LLMConfigException;

	public Flux<AbstractPureSearchDocumentResultEntry> streamPureSearch(MinimalChatContext minimalChatContext,
			ISinkUIEmitter emitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel,
			String chunkingSessionId, int topK, int sampleTextTokensSize)
			throws LLMConfigException, GeboChatSessionLifecycleException;

	public Flux<Document> streamSearchResults(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel,
			String chunkingSessionId, int topK) throws LLMConfigException, IOException, GeboIngestionException,
			GeboContentHandlerSystemException, SearchServiceException, GeboChatSessionLifecycleException;

}