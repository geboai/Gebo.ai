package ai.gebo.architecture.agents.services.impl;

import java.util.List;

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

	public GAgentsNetworkCallerProxyFactoryRepositoryPatternImpl(
			@Autowired(required = false) List<IGAgentsNetworkCallerProxyFactory> implementations) {
		super(implementations);

	}

	@Override
	public String getCodeValue(IGAgentsNetworkCallerProxyFactory x) {

		return x.getId();
	}

	@Override
	public IGAgentsNetworkCallerProxyFactory getByAdaptedNetworkServiceId(String id) {

		return findImplementation(
				x -> x.getAdaptedNetworkServiceId() != null && id != null && x.getAdaptedNetworkServiceId().equals(id));
	}

	@Override
	public <InputType, OutputType> IGAgentsNetworkCallerProxyFactory<InputType, OutputType> getByAgentsNetworkService(
			IGAgentsNetworkService<InputType, OutputType> service) {

		return findImplementation(x -> x.createdServiceCanAdapt(service));
	}

}
