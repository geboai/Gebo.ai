package ai.gebo.architecture.agents.services.impl;

import java.util.List;

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

	public GAgentsNetworkServiceFactoryRepositoryPatternImpl(
			@Autowired(required = false) List<IGAgentsNetworkServiceFactory> implementations) {
		super(implementations);
	}

	@Override
	public String getCodeValue(IGAgentsNetworkServiceFactory x) {

		return x.getId();
	}

	@Override
	public <InputType, OutputType, NetworkService extends IGAgentsNetworkService<InputType, OutputType>> IGAgentsNetworkServiceFactory<InputType, OutputType, NetworkService> getFactory(
			Class<NetworkService> type) {
		return findImplementation(x -> x.canHandle(type));
	}
}
