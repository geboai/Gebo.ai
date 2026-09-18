package ai.gebo.llms.agent.chat.service.impl;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.GAbstractReactiveOutputAgentsNetworkService;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactory.AdapterWithFlux;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.security.services.ReactiveIdentityUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GReactiveChatAgentsNetworkService
		extends GAbstractReactiveOutputAgentsNetworkService<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope>
		implements IGReactiveChatAgentsNetworkService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GReactiveChatAgentsNetworkService.class);

	private static final String NETWORK_SERVICE_DESCRIPTION = "Reactive Chat agents network service";
	public static final String REACTIVE_CHAT_AGENTS_NETWORK_SERVICE = "ReactiveChatAgentsNetworkService";

	public GReactiveChatAgentsNetworkService(IGAgentServiceRuntimeDao agentsServicesRepository, IAgentRoleDao rolesDao,
			IGeboThreadManager threadManager, GAgentsNetwork network, INotificationSink notificationSink,
			Class<ChatPipelineExecutionRuntimeData> inputType, Class<GeboChatMessageEnvelope> outputType,
			ReactiveIdentityUtil runAs, IGAgentsNetworkRuntimeDao agentsDao,
			AdapterWithFlux<?, GeboChatMessageEnvelope> adapterWithFlux) {
		super(agentsServicesRepository, rolesDao, threadManager, network, notificationSink, inputType, outputType,
				runAs, agentsDao, adapterWithFlux);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Allocated " + REACTIVE_CHAT_AGENTS_NETWORK_SERVICE + " over network code:"
					+ (network != null ? network.getCode() : null) + " participants:"
					+ (network != null && network.getAgents() != null ? network.getAgents().size() : 0));
		}
	}

	@Override
	protected <OutputType> OutputType compose(OutputType actualOutput, OutputType incremental) {
		// Streaming partials are emitted to the reactive flux; the returned value is
		// the
		// final envelope, so the latest non-null output wins.
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("compose(...) keeping the " + (incremental != null ? "incremental" : "already composed")
					+ " chat envelope");
		}
		return incremental != null ? incremental : actualOutput;
	}

	@Override
	public void dispose() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("dispose() nothing to release for " + REACTIVE_CHAT_AGENTS_NETWORK_SERVICE);
		}
	}

	@Override
	public String getId() {

		return REACTIVE_CHAT_AGENTS_NETWORK_SERVICE;
	}

	@Override
	public String getDescription() {

		return NETWORK_SERVICE_DESCRIPTION;
	}

}
