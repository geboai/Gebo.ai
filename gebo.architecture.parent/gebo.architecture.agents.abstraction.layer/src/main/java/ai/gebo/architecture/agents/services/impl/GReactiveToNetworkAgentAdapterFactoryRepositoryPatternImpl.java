package ai.gebo.architecture.agents.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GReactiveToNetworkAgentAdapterFactoryRepositoryPatternImpl.class);

	public GReactiveToNetworkAgentAdapterFactoryRepositoryPatternImpl(
			@Autowired(required = false) List<IGReactiveToNetworkAgentAdapterFactory> implementations) {
		super(implementations);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Reactive to network agent adapter factory repository initialized with "
					+ (implementations != null ? implementations.size() : 0) + " implementation(s)");
		}
	}

	@Override
	public String getCodeValue(IGReactiveToNetworkAgentAdapterFactory x) {
		return x.getClass().getName();
	}

	@Override
	public <RequestType, ResponseType, NotificationObject> IGReactiveToNetworkAgentAdapterFactory<RequestType, ResponseType> getFactory(
			IGReactiveAgentService<RequestType, ResponseType> service) {
		IGReactiveToNetworkAgentAdapterFactory<RequestType, ResponseType> factory = findImplementation(
				x -> x.canBeAdapted(service));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getFactory(" + (service != null ? service.getId() : null) + ") resolved:"
					+ (factory != null ? factory.getClass().getName() : null));
		}
		return factory;
	}

}
