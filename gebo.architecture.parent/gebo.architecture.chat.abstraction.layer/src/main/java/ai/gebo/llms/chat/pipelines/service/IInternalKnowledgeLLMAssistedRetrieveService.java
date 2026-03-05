package ai.gebo.llms.chat.pipelines.service;

import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import reactor.core.publisher.Flux;

public interface IInternalKnowledgeLLMAssistedRetrieveService {
	public Flux<AIDocumentsSet> doDocumentsRetrieve(MinimalChatContext minimalChatContext,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy, int topK)
			throws GeboChatSessionLifecycleException, LLMConfigException;
}
