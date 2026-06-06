package ai.gebo.architecture.agents.services.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.repository.GAgentConfigRepository;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRepositoryPattern;

@Service
public class DefaultAgentsNetworkServiceReturnLastOutputValue extends GAbstractAgentsNetworkService {

	public DefaultAgentsNetworkServiceReturnLastOutputValue(IGAgentServiceRepositoryPattern agentsServicesRepository,
			IAgentRoleDao rolesDao, GAgentConfigRepository agentConfigRepo) {
		super(agentsServicesRepository, rolesDao, agentConfigRepo);
	}

	@Override
	protected <OutputType> OutputType compose(OutputType actualOutput, OutputType incremental) {

		return incremental != null ? incremental : actualOutput;
	}

	@Override
	public String getId() {

		return "DefaultReturnLastOutputValueNetworkService";
	}

	@Override
	public String getDescription() {

		return "Agent network executor returning last valid output value";
	}

}
