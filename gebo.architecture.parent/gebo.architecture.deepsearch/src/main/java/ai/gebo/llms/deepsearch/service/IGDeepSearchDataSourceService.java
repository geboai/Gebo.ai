package ai.gebo.llms.deepsearch.service;

import java.util.List;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.deepsearch.model.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.DataSourceExecutionTime;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.IDeepSearchResult;

public interface IGDeepSearchDataSourceService<StateType, InputType, OutputType, StepEventType extends AbstractDeepSearchEvent<InputType, OutputType>> {
	public DataSourceExecutionTime getExecutionTime();

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
	 */
	public boolean isEnabled(IGConfigurableChatModel chatModel,DeepSearchConfig deepSearchConfig, DeepSearchRequest request);

	/******************************************************************
	 * Returns a description of the data source
	 * 
	 * @return
	 */
	public String getDescription(IGConfigurableChatModel chatModel,DeepSearchConfig deepSearchConfig, DeepSearchRequest request);

	/****************************************************************
	 * Creates an initial state for iterative process steps
	 * 
	 * @param request
	 * @return
	 */
	public StateType createInitialState(IGConfigurableChatModel chatModel,DeepSearchConfig deepSearchConfig, DeepSearchRequest request);

	/******************************************************************
	 * Processes next step, will be iterated untill return null or returns a
	 * DeepSearchDataSourceResponse
	 * @param request
	 * @param pastSystemsResponses
	 * @param state
	 * @param previusConsolidatedResult TODO
	 * @param history
	 * 
	 * @return
	 * @throws LLMConfigException 
	 */
	public AbstractDeepSearchEvent nextStep(IGConfigurableChatModel chatModel,DeepSearchConfig deepSearchConfig, DeepSearchRequest request,
			List<IDeepSearchResult> pastSystemsResponses, StateType state, String previusConsolidatedResult) throws LLMConfigException;

}
