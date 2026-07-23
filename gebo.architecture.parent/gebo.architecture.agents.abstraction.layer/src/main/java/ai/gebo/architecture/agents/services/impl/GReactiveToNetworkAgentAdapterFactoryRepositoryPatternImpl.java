package ai.gebo.architecture.agents.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.services.IGReactiveAgentService;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactory;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern;
import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;

@Service
public class GReactiveToNetworkAgentAdapterFactoryRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IGReactiveToNetworkAgentAdapterFactory>
		implements IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern {

	public GReactiveToNetworkAgentAdapterFactoryRepositoryPatternImpl(
			@Autowired(required = false) List<IGReactiveToNetworkAgentAdapterFactory> implementations) {
		super(implementations);

	}

	@Override
	public String getCodeValue(IGReactiveToNetworkAgentAdapterFactory x) {
		return x.getClass().getName();
	}

	@Override
	public <RequestType, ResponseType, NotificationObject> IGReactiveToNetworkAgentAdapterFactory<RequestType, ResponseType> getFactory(
			IGReactiveAgentService<RequestType, ResponseType> service) {
		return findImplementation(x -> x.canBeAdapted(service));
	}

}
