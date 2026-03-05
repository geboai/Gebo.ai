package ai.gebo.llms.deepsearch.service;

import java.io.IOException;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.system.ingestion.GeboIngestionException;
import reactor.core.publisher.Flux;

public interface IGInternalKnowledgeBaseDeepSearchExecutor {
	public Flux<GeboChatMessageEnvelope> execute(LLMChatRequestResources requestResources , MinimalChatContext minimalChatContext,
			GeboChatResponse response, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws LLMConfigException, IOException, GeboIngestionException, GeboContentHandlerSystemException,
			SearchServiceException, GeboChatSessionLifecycleException;
}
