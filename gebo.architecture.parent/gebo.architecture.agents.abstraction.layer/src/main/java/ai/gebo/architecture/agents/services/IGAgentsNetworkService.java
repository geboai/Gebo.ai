package ai.gebo.architecture.agents.services;

import java.util.Map;

import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

public interface IGAgentsNetworkService<InputType, OutputType> {
	String getId();

	String getDescription();

	OutputType executeNetwork(IChatRequestContext chatRequestContext, InputType input, Map<String, Object> environment)
			throws AgentException, LLMConfigException;

	Class<OutputType> getOutputType();

	Class<InputType> getInputType();

	void dispose();
}
