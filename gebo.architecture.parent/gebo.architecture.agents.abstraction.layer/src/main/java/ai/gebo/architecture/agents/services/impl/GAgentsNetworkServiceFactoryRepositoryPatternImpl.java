package ai.gebo.architecture.agents.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.services.IGAgentsNetworkService;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactoryRepositoryPattern;
import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;

@Service
public class GAgentsNetworkServiceFactoryRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IGAgentsNetworkServiceFactory>
		implements IGAgentsNetworkServiceFactoryRepositoryPattern {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GAgentsNetworkServiceFactoryRepositoryPatternImpl.class);

	public GAgentsNetworkServiceFactoryRepositoryPatternImpl(
			@Autowired(required = false) List<IGAgentsNetworkServiceFactory> implementations) {
		super(implementations);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agents network service factory repository initialized with "
					+ (implementations != null ? implementations.size() : 0) + " implementation(s)");
		}
	}

	@Override
	public String getCodeValue(IGAgentsNetworkServiceFactory x) {

		return x.getId();
	}

	@Override
	public <InputType, OutputType, NetworkService extends IGAgentsNetworkService<InputType, OutputType>> IGAgentsNetworkServiceFactory<InputType, OutputType, NetworkService> getFactory(
			Class<NetworkService> type) {
		IGAgentsNetworkServiceFactory<InputType, OutputType, NetworkService> factory = findImplementation(
				x -> x.canHandle(type));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getFactory(" + (type != null ? type.getName() : null) + ") resolved:"
					+ (factory != null ? factory.getId() : null));
		}
		return factory;
	}
}
