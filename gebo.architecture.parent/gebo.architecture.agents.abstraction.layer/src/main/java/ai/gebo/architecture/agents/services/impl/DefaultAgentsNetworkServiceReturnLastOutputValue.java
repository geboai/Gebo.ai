package ai.gebo.architecture.agents.services.impl;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.GAbstractAgentsNetworkService;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.security.services.ReactiveIdentityUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultAgentsNetworkServiceReturnLastOutputValue<InputType, OutputType>
		extends GAbstractAgentsNetworkService<InputType, OutputType> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DefaultAgentsNetworkServiceReturnLastOutputValue.class);

	public DefaultAgentsNetworkServiceReturnLastOutputValue(IGAgentServiceRuntimeDao agentsServicesRepository,
			IAgentRoleDao rolesDao, IGeboThreadManager threadManager, GAgentsNetwork network,
			INotificationSink notificationSink, Class<InputType> inputType, Class<OutputType> outputType,
			ReactiveIdentityUtil runAs, IGAgentsNetworkRuntimeDao agentsDao) {
		super(agentsServicesRepository, rolesDao, threadManager, network, notificationSink, inputType, outputType,
				runAs, agentsDao);

	}

	@Override
	protected <OutputType> OutputType compose(OutputType actualOutput, OutputType incremental) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("compose(...) keeping the " + (incremental != null ? "incremental" : "already composed")
					+ " output value");
		}
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

	@Override
	public void dispose() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("dispose() nothing to release for " + getId());
		}
	}

}
