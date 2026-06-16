package ai.gebo.architecture.agents.services.impl;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.repository.AgentConfigRepository;
import ai.gebo.architecture.agents.services.GAbstractAgentsNetworkService;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.security.services.ReactiveIdentityUtil;


public class DefaultAgentsNetworkServiceReturnLastOutputValue<InputType, OutputType>
		extends GAbstractAgentsNetworkService<InputType, OutputType> {

	

	public DefaultAgentsNetworkServiceReturnLastOutputValue(IGAgentServiceRuntimeDao agentsServicesRepository,
			IAgentRoleDao rolesDao, AgentConfigRepository agentConfigRepo, IGeboThreadManager threadManager,
			GAgentsNetwork network, INotificationSink notificationSink, Class<OutputType> outputType,
			ReactiveIdentityUtil runAs, IGAgentsNetworkRuntimeDao agentsDao) {
		super(agentsServicesRepository, rolesDao, agentConfigRepo, threadManager, network, notificationSink, outputType, runAs,
				agentsDao);
		// TODO Auto-generated constructor stub
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
