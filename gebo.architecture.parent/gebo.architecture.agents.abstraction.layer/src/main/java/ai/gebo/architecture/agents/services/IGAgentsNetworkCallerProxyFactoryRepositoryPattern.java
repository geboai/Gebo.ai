package ai.gebo.architecture.agents.services;

public interface IGAgentsNetworkCallerProxyFactoryRepositoryPattern {
	public IGAgentsNetworkCallerProxyFactory getByAdaptedNetworkServiceId(String id);

	public <InputType, OutputType> IGAgentsNetworkCallerProxyFactory<InputType, OutputType> getByAgentsNetworkService(
			IGAgentsNetworkService<InputType, OutputType> service);
}
