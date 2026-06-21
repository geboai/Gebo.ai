package ai.gebo.architecture.agents.services;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;

public interface IGAgentsNetworkServiceFactoryRepositoryPattern
		extends IGImplementationsRepositoryPattern<IGAgentsNetworkServiceFactory> {

	<InputType,OutputType,NetworkService extends IGAgentsNetworkService<InputType,OutputType>> IGAgentsNetworkServiceFactory<InputType,OutputType, NetworkService> getFactory(
			Class<NetworkService> type);
}
