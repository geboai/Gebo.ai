package ai.gebo.architecture.agents.services;

import ai.gebo.architecture.agents.model.GAgentsNetwork;

public interface IGAgentsNetworkCallerProxyFactory<InputType, OutputType> {
	public String getAdaptedNetworkServiceId();

	public String getId();

	public Class<InputType> getInputType();

	public Class<OutputType> getOutputType();

	public IGAgentsNetworkCallerProxy<InputType, OutputType> create(
			IGAgentsNetworkService<InputType, OutputType> service);

	public boolean createdServiceCanAdapt(IGAgentsNetworkService service);

}
