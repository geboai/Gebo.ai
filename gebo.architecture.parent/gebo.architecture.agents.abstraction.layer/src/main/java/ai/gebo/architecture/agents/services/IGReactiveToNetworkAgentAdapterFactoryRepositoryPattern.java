package ai.gebo.architecture.agents.services;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;

public interface IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern
		extends IGImplementationsRepositoryPattern<IGReactiveToNetworkAgentAdapterFactory> {
	public <RequestType, ResponseType, NotificationObject> IGReactiveToNetworkAgentAdapterFactory<RequestType, ResponseType> getFactory(
			IGReactiveAgentService<RequestType, ResponseType> service);
}
