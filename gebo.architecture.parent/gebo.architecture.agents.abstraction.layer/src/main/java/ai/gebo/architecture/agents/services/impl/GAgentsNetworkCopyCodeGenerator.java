package ai.gebo.architecture.agents.services.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;

/**
 * The code of a network of agents copy, made from its original's code (e.g.
 * {@code DEFAULT_AGENTS_NETWORK-1}).
 */
@Service
public class GAgentsNetworkCopyCodeGenerator extends GAbstractCopyCodeGenerator<GAgentsNetwork> {

	public GAgentsNetworkCopyCodeGenerator(IGRuntimeBinder runtimeBinder) {
		super(runtimeBinder);
	}

	@Override
	public Class<GAgentsNetwork> getAffectedClass() {
		return GAgentsNetwork.class;
	}

	@Override
	protected IGRuntimeConfigurationDao<GAgentsNetwork> dao() {
		return runtimeBinder.getImplementationOf(IAgentsNetworkDao.class);
	}
}
