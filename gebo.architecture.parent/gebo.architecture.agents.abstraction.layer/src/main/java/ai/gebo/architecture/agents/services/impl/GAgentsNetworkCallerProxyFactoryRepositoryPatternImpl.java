package ai.gebo.architecture.agents.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.services.IGAgentsNetworkCallerProxyFactory;
import ai.gebo.architecture.agents.services.IGAgentsNetworkCallerProxyFactoryRepositoryPattern;
import ai.gebo.architecture.agents.services.IGAgentsNetworkService;
import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;

@Service
public class GAgentsNetworkCallerProxyFactoryRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IGAgentsNetworkCallerProxyFactory>
		implements IGAgentsNetworkCallerProxyFactoryRepositoryPattern {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GAgentsNetworkCallerProxyFactoryRepositoryPatternImpl.class);

	public GAgentsNetworkCallerProxyFactoryRepositoryPatternImpl(
			@Autowired(required = false) List<IGAgentsNetworkCallerProxyFactory> implementations) {
		super(implementations);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agents network caller proxy factory repository initialized with "
					+ (implementations != null ? implementations.size() : 0) + " implementation(s)");
		}
	}

	@Override
	public String getCodeValue(IGAgentsNetworkCallerProxyFactory x) {

		return x.getId();
	}

	@Override
	public IGAgentsNetworkCallerProxyFactory getByAdaptedNetworkServiceId(String id) {
		IGAgentsNetworkCallerProxyFactory factory = findImplementation(
				x -> x.getAdaptedNetworkServiceId() != null && id != null && x.getAdaptedNetworkServiceId().equals(id));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getByAdaptedNetworkServiceId(" + id + ") resolved:"
					+ (factory != null ? factory.getClass().getName() : null));
		}
		return factory;
	}

	@Override
	public <InputType, OutputType> IGAgentsNetworkCallerProxyFactory<InputType, OutputType> getByAgentsNetworkService(
			IGAgentsNetworkService<InputType, OutputType> service) {
		IGAgentsNetworkCallerProxyFactory<InputType, OutputType> factory = findImplementation(
				x -> x.createdServiceCanAdapt(service));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getByAgentsNetworkService(" + (service != null ? service.getId() : null) + ") resolved:"
					+ (factory != null ? factory.getClass().getName() : null));
		}
		return factory;
	}

}
