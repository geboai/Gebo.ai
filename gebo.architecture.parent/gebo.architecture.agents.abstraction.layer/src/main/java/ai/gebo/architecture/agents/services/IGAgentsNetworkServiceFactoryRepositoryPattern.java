package ai.gebo.architecture.agents.services;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;

public interface IGAgentsNetworkServiceFactoryRepositoryPattern
		extends IGImplementationsRepositoryPattern<IGAgentsNetworkServiceFactory> {

	<NetworkService extends IGAgentsNetworkService> IGAgentsNetworkServiceFactory<?, ?, NetworkService> getFactory(
			Class<NetworkService> type);
}
