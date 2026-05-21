package ai.gebo.llms.deepsearch.service;

import java.io.IOException;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.deepsearch.datasources.model.AbstractPureSearchDocumentResultEntry;
import ai.gebo.system.ingestion.GeboIngestionException;
import reactor.core.publisher.Flux;

public interface IGInternalKnlowledgeBaseRagDeepSearchService {

	

	public Flux<AbstractPureSearchDocumentResultEntry> streamPureSearch(MinimalChatContext minimalChatContext,
			ISinkUIEmitter emitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel,
			String chunkingSessionId, int topK, int sampleTextTokensSize)
			throws LLMConfigException, GeboChatSessionLifecycleException;

	public Flux<Document> streamSearchResults(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel,
			String chunkingSessionId, int topK) throws LLMConfigException, IOException, GeboIngestionException,
			GeboContentHandlerSystemException, SearchServiceException, GeboChatSessionLifecycleException;

}