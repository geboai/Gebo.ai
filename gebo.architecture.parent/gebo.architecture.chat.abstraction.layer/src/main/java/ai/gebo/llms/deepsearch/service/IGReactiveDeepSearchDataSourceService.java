package ai.gebo.llms.deepsearch.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchState;
import ai.gebo.llms.deepsearch.model.IDeepSearchResult;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.system.ingestion.GeboIngestionException;
import reactor.core.publisher.Flux;

public interface IGReactiveDeepSearchDataSourceService< InputType, OutputType, StepEventType extends AbstractDeepSearchEvent<InputType, OutputType>> {
	

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
	public boolean isEnabled(IGConfigurableChatModel chatModel, DeepSearchConfig deepSearchConfig,
			DeepSearchRequest request) throws SearchServiceException;

	/******************************************************************
	 * Returns a description of the data source
	 * 
	 * @return
	 */
	public String getDescription(IGConfigurableChatModel chatModel, DeepSearchConfig deepSearchConfig,
			DeepSearchRequest request);

	

	/******************************************************************
	 * Processes next step, will be iterated untill return null or returns a
	 * DeepSearchDataSourceResponse
	 * @param request
	 * @param minimalChatContext TODO
	 * @param totalSteps TODO
	 * @param doneSteps TODO
	 * @param satisfactoryDocuments TODO
	 * @param completed TODO
	 * @param satisfactoryDocumentsThreashold TODO
	 * @param serviceModel TODO
	 * @param pastSystemsResponses
	 * @param chunkingSessionId TODO
	 * @param deepSearchState TODO
	 * @param history
	 * @return
	 * @throws LLMConfigException
	 * @throws IOException
	 * @throws GeboContentHandlerSystemException
	 * @throws GeboIngestionException
	 * @throws SearchServiceException
	 */
	public Flux<AbstractDeepSearchEvent> streamSearch(DeepSearchRequest request, MinimalChatContext minimalChatContext,
			AtomicInteger totalSteps, AtomicInteger doneSteps, AtomicInteger satisfactoryDocuments, AtomicBoolean completed, int satisfactoryDocumentsThreashold, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, DeepSearchConfig deepSearchConfig, List<IDeepSearchResult> pastSystemsResponses, String chunkingSessionId, DeepSearchState deepSearchState) throws LLMConfigException, IOException, GeboIngestionException,
			GeboContentHandlerSystemException, SearchServiceException;

}
