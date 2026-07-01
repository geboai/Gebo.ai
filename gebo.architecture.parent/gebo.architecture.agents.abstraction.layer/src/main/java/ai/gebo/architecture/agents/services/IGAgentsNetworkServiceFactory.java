package ai.gebo.architecture.agents.services;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.security.services.ReactiveIdentityUtil;

public interface IGAgentsNetworkServiceFactory<InputType, OutputType, ServiceType extends IGAgentsNetworkService> {
	public String getId();

	public String getDescription();

	public boolean canHandle(Class<IGAgentsNetworkService> agentNetworkService);

	Class<OutputType> getOutputType();

	Class<InputType> getInputType();

	public ServiceType create(GAgentsNetwork network, INotificationSink notificationSink, Class<InputType> inputType,
			Class<OutputType> outputType, ReactiveIdentityUtil runAs) throws NetworkOfAgentsException;
}
