package ai.gebo.llms.deepsearch.service;

import java.util.List;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchState;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.security.repository.UserRepository.UserInfos;
import reactor.core.publisher.Flux;

public interface IGInternalKnlowledgeBaseRagDeepSearchService {

	public Flux<AbstractDeepSearchEvent> knowledgeBaseDeepSearch(DeepSearchRequest request, boolean runSearches,
			DeepSearchState state, MinimalChatContext minimalChatContext, AIDocumentsSet sessionDocuments,
			DeepSearchConfig configuration, UserInfos userInfos, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, String chunkingSessionId, List<IGConfigurableEmbeddingModel> embeddingModels)
			throws GeboChatSessionLifecycleException, LLMConfigException;

}