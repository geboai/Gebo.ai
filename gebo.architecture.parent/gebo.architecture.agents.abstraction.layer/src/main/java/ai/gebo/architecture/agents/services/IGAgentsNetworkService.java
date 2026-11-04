package ai.gebo.architecture.agents.services;

import java.util.Map;

import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;

public interface IGAgentsNetworkService<InputType, OutputType> {
	String getId();

	String getDescription();

	/**
	 * Runs the network on the input, in the environment, with what the software
	 * controlling the run adds to every model call of it (see
	 * {@link GeboToolCalling}): the entry point of the run builds it and every
	 * agent of the network receives it. Required: a run with no tool calling is
	 * refused with an {@link AgentException}.
	 */
	OutputType executeNetwork(IChatRequestContext chatRequestContext, InputType input,
			Map<String, Object> environment, GeboToolCalling toolCalling) throws AgentException, LLMConfigException;

	Class<OutputType> getOutputType();

	Class<InputType> getInputType();

	void dispose();
}
