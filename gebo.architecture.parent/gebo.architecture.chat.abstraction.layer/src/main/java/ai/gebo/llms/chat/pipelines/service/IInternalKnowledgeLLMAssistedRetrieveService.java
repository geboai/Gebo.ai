package ai.gebo.llms.chat.pipelines.service;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import reactor.core.publisher.Flux;

public interface IInternalKnowledgeLLMAssistedRetrieveService {
	/**
	 * The documents of the session knowledge bases for the current request, searched
	 * with the searches the model suggests; with a ranker configured, twice topK are
	 * searched, ranked and the fragments the ranker service judges irrelevant are
	 * removed (as the RAG chat answers from them directly).
	 */
	public default Flux<AIDocumentsSet> doDocumentsRetrieve(MinimalChatContext minimalChatContext,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy, int topK)
			throws GeboChatSessionLifecycleException, LLMConfigException {
		return doDocumentsRetrieve(minimalChatContext, targetChatModel, policy, topK, true);
	}

	/**
	 * The same, the irrelevant fragments removed only when {@code removeIrrelevant}:
	 * otherwise the ranker keeps the best topK with no irrelevance filter, as the deep
	 * search does (its analysis judges the fragments itself).
	 */
	public Flux<AIDocumentsSet> doDocumentsRetrieve(MinimalChatContext minimalChatContext,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy, int topK,
			boolean removeIrrelevant) throws GeboChatSessionLifecycleException, LLMConfigException;
}
