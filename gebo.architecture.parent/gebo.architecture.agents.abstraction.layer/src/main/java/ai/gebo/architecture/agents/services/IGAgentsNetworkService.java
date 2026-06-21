package ai.gebo.architecture.agents.services;

import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

public interface IGAgentsNetworkService<InputType, OutputType> {
	String getId();

	String getDescription();

	OutputType executeNetwork(IChatRequestContext chatRequestContext, InputType input)
			throws AgentException, LLMConfigException;

	void dispose();
}
