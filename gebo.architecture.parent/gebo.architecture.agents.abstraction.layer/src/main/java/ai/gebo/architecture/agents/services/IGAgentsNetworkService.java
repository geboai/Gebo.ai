package ai.gebo.architecture.agents.services;

import java.util.Map;

import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;

public interface IGAgentsNetworkService<InputType, OutputType> {
	String getId();

	String getDescription();

	OutputType executeNetwork(IChatRequestContext chatRequestContext, InputType input, Map<String, Object> environment)
			throws AgentException, LLMConfigException;

	/**
	 * The same run, with what the software controlling it adds to every model call of
	 * it (see {@link GeboToolCalling}): the network passes it to each of its agents,
	 * which pass it to their model and to whatever they call that takes one - a
	 * nested network, a reactive agent, a tool's own model call. Null is the plain
	 * run. An implementation that does not take it runs the plain run.
	 */
	default OutputType executeNetwork(IChatRequestContext chatRequestContext, InputType input,
			Map<String, Object> environment, GeboToolCalling toolCalling) throws AgentException, LLMConfigException {
		return executeNetwork(chatRequestContext, input, environment);
	}
	
	Class<OutputType> getOutputType();

	Class<InputType> getInputType();

	void dispose();
}
