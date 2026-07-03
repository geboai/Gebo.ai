package ai.gebo.architecture.mcpserver.runtime;

import java.awt.Taskbar.State;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IGAgentsNetworkCallerProxy;
import ai.gebo.architecture.agents.services.IGAgentsNetworkCallerProxyFactory;
import ai.gebo.architecture.agents.services.IGAgentsNetworkCallerProxyFactoryRepositoryPattern;
import ai.gebo.architecture.agents.services.IGAgentsNetworkService;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactoryRepositoryPattern;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.agents.services.NetworkOfAgentsException;
import ai.gebo.architecture.agents.services.INotificationSink.NotificationObject;
import ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AgentNetworkToolCallbackAdapter {
	private static final String EXECUTION_OF_NETWORK_OF_AGENTS = "Execution of Network of agents: ";
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentNetworkToolCallbackAdapter.class);
	private static final INotificationSink staticLoggingSink = new INotificationSink() {

		@Override
		public void next(NotificationObject state) {
			NotificationType nottype = NotificationType.INFO;
			if (state.getNotificationType() != null) {
				nottype = state.getNotificationType();
			}
			switch (nottype) {
			case DEBUG: {
				LOGGER.debug("INotificationSink=>" + state);
			}
				break;
			case INFO: {
				LOGGER.info("INotificationSink=>" + state);
			}
				break;
			case ERROR: {
				LOGGER.error("INotificationSink=>" + state);
			}
				break;
			}

		}
	};
	private final IGAgentsNetworkCallerProxyFactoryRepositoryPattern factoryRepoPattern;
	private final IGAgentsNetworkServiceFactoryRepositoryPattern agentNetworkServiceFactory;

	public ToolCallback createTool(String toolName, GAgentsNetwork network, ReactiveIdentityUtil identityUtil) {
		IGAgentsNetworkServiceFactory factory = agentNetworkServiceFactory
				.findByCode(network.getAgentsNetworkServiceFactoryId());
		return ToolCallbackDeclarationUtil.declare((input, ctx) -> {
			try {
				return this.call(input, factory, network, identityUtil);
			} catch (NetworkOfAgentsException | AgentException e) {
				LOGGER.error("Error in calling network of agents", e);
			}
			return null;
		}, toolName, EXECUTION_OF_NETWORK_OF_AGENTS + network.getDescription(), factory.getInputType(),
				factory.getOutputType());
	}

	private <InputType, OutputType> OutputType call(Object input,
			IGAgentsNetworkServiceFactory<InputType, OutputType, ?> factory, GAgentsNetwork network,
			ReactiveIdentityUtil runAs) throws NetworkOfAgentsException, AgentException {
		IGAgentsNetworkService<InputType, OutputType> networkService = factory.create(network, staticLoggingSink,
				factory.getInputType(), factory.getOutputType(), runAs);
		IGAgentsNetworkCallerProxyFactory<InputType, OutputType> networkCallerProxyFactory = factoryRepoPattern
				.getByAgentsNetworkService(networkService);
		IGAgentsNetworkCallerProxy<InputType, OutputType> callerProxy = networkCallerProxyFactory
				.create(networkService);
		return callerProxy.call((InputType) input);
	}
}
