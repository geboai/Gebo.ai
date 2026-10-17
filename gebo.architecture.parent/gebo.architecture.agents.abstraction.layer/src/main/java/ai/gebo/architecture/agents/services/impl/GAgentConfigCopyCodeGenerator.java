package ai.gebo.architecture.agents.services.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;

/**
 * The code of an agent configuration copy, made from its original's code (e.g.
 * {@code controllerAgent-1}): it names the copy where the networks address it.
 */
@Service
public class GAgentConfigCopyCodeGenerator extends GAbstractCopyCodeGenerator<GAgentConfig> {

	public GAgentConfigCopyCodeGenerator(IGRuntimeBinder runtimeBinder) {
		super(runtimeBinder);
	}

	@Override
	public Class<GAgentConfig> getAffectedClass() {
		return GAgentConfig.class;
	}

	@Override
	protected IGRuntimeConfigurationDao<GAgentConfig> dao() {
		return runtimeBinder.getImplementationOf(IAgentConfigDao.class);
	}
}
