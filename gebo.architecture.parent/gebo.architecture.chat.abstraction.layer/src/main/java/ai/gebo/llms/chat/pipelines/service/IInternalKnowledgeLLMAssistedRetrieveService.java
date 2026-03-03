package ai.gebo.llms.chat.pipelines.service;

import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.DocumentsEnrichDecision;
import reactor.core.publisher.Flux;

public interface IInternalKnowledgeLLMAssistedRetrieveService {
	public Flux<DocumentsEnrichDecision> doDocumentsRetrieve(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException, FullTextException, LLMConfigException;
}
