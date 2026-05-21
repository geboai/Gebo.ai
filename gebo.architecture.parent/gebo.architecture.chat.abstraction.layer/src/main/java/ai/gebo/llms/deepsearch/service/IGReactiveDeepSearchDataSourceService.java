package ai.gebo.llms.deepsearch.service;

import java.io.IOException;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.deepsearch.datasources.model.AbstractPureSearchDocumentResultEntry;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import reactor.core.publisher.Flux;

public interface IGReactiveDeepSearchDataSourceService {

	/**************************************************************************
	 * Returns a unique identifier for this handler
	 * 
	 * @return
	 */
	public String getHandlerId();

	/******************************************************************
	 * Returns true if this handler is enabled for the actual request
	 * 
	 * @return
	 * @throws SearchServiceException
	 */
	public boolean isEnabled(DeepSearchConfig deepSearchConfig) throws SearchServiceException;

	/******************************************************************
	 * Returns a description of the data source
	 * 
	 * @return
	 */
	public String getDescription(DeepSearchConfig deepSearchConfig);

	public String getProductId();

	@AllArgsConstructor
	@Getter
	public static class DocumentWithSearchResult {
		private final SearchResult searchResult;
		private final Document document;
	}

	public Flux<AbstractPureSearchDocumentResultEntry> streamPureSearch(MinimalChatContext minimalChatContext,
			ISinkUIEmitter emitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, int topK,
			int sampleTextTokensSize, String chunkingSessionId) throws LLMConfigException, IOException,
			GeboIngestionException, GeboContentHandlerSystemException, SearchServiceException;

	public Flux<DocumentWithSearchResult> streamSearchResults(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel,
			String chunkingSessionId, int topK) throws LLMConfigException, IOException, GeboIngestionException,
			GeboContentHandlerSystemException, SearchServiceException, GeboChatSessionLifecycleException;

}
