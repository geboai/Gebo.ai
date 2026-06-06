package ai.gebo.architecture.agents.services;

import ai.gebo.architecture.agents.model.AgentsNetwork;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.security.services.ReactiveIdentityUtil;

public interface IGAgentsNetworkService {
	String getId();
	String getDescription();
	<InputType, OutputType> OutputType executeNetwork(InputType input, AgentsNetwork network,Class<OutputType> outputType, ReactiveIdentityUtil runAs) throws AgentException, LLMConfigException;
}
