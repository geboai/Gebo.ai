package ai.gebo.architecture.agents.services;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.security.services.ReactiveIdentityUtil;

public interface IGAgentsNetworkService<InputType, OutputType> {
	String getId();

	String getDescription();

	OutputType executeNetwork(IChatRequestContext chatRequestContext, InputType input)
			throws AgentException, LLMConfigException;

	void dispose();
}
