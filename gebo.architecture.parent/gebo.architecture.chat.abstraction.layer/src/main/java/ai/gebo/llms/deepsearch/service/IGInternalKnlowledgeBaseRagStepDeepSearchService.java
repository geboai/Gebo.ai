package ai.gebo.llms.deepsearch.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchState;
import ai.gebo.llms.deepsearch.model.IDeepSearchResult;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.security.repository.UserRepository.UserInfos;
import reactor.core.publisher.Flux;

public interface IGInternalKnlowledgeBaseRagStepDeepSearchService {

	public Flux<AbstractDeepSearchEvent> knowledgeBaseDeepSearch(DeepSearchRequest request,
			MinimalChatContext minimalChatContext, AtomicInteger totalSteps,
			AtomicInteger doneSteps, AtomicInteger satisfactoryDocuments, AtomicBoolean completed,
			final int satisfactoryDocumentsThreashold, AIDocumentsSet sessionDocuments, List<IDeepSearchResult> dataSourcesResults,
			List<AbstractDeepSearchEvent> history, DeepSearchState state, DeepSearchConfig configuration, UserInfos userInfos, IGConfigurableChatModel chatModel, String chunkingSessionId, List<IGConfigurableEmbeddingModel> embeddingModels);

}