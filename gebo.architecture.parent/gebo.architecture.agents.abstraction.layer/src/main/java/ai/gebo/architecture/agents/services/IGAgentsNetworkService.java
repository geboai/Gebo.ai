package ai.gebo.architecture.agents.services;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.security.services.ReactiveIdentityUtil;

public interface IGAgentsNetworkService {
	String getId();
	String getDescription();
	<InputType, OutputType> OutputType executeNetwork(IChatRequestContext chatRequestContext, InputType input,GAgentsNetwork network, INotificationSink notificationSink, Class<OutputType> outputType, ReactiveIdentityUtil runAs) throws AgentException, LLMConfigException;
}
