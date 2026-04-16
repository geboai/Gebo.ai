package ai.gebo.llms.deepsearch.service;

import java.io.IOException;
import java.util.List;

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

public interface IGReactiveDeepSearchDataSourceService<InputType, OutputType, StepEventType extends AbstractDeepSearchEvent<InputType, OutputType>> {

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

	/******************************************************************
	 * Processes next step, will be iterated untill return null or returns a
	 * DeepSearchDataSourceResponse
	 * 
	 * @param request
	 * @param minimalChatContext   TODO
	 * @param deepSearchState      TODO
	 * @param serviceModel         TODO
	 * @param pastSystemsResponses
	 * @param chunkingSessionId    TODO
	 * @param history
	 * @return
	 * @throws LLMConfigException
	 * @throws IOException
	 * @throws GeboContentHandlerSystemException
	 * @throws GeboIngestionException
	 * @throws SearchServiceException
	 */
	public Flux<AbstractDeepSearchEvent> streamSearch(DeepSearchRequest request, MinimalChatContext minimalChatContext,
			DeepSearchState deepSearchState, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel,
			DeepSearchConfig deepSearchConfig, List<IDeepSearchResult> pastSystemsResponses, String chunkingSessionId)
			throws LLMConfigException, IOException, GeboIngestionException, GeboContentHandlerSystemException,
			SearchServiceException;

}
