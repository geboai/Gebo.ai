package ai.gebo.llms.deepsearch.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
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
	 * @param serviceModel TODO
	 * @param request
	 * @param pastSystemsResponses
	 * @param chunkingSessionId TODO
	 * @param totalSteps TODO
	 * @param doneSteps TODO
	 * @param deepSearchState TODO
	 * @param history
	 * @return
	 * @throws LLMConfigException
	 * @throws IOException
	 * @throws GeboContentHandlerSystemException
	 * @throws GeboIngestionException
	 * @throws SearchServiceException
	 */
	public Flux<AbstractDeepSearchEvent> streamSearch(IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel,
			DeepSearchConfig deepSearchConfig, DeepSearchRequest request, List<IDeepSearchResult> pastSystemsResponses, String chunkingSessionId, AtomicInteger totalSteps, AtomicInteger doneSteps, DeepSearchState deepSearchState) throws LLMConfigException, IOException, GeboIngestionException,
			GeboContentHandlerSystemException, SearchServiceException;

}
