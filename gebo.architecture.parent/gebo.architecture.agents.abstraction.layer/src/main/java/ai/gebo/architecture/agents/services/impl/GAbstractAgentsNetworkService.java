package ai.gebo.architecture.agents.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsNetwork;
import ai.gebo.architecture.agents.model.AgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.repository.GAgentConfigRepository;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRepositoryPattern;
import ai.gebo.architecture.agents.services.IGAgentsNetworkService;
import ai.gebo.architecture.agents.services.IGNetworkAgentService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class GAbstractAgentsNetworkService implements IGAgentsNetworkService {
	private final IGAgentServiceRepositoryPattern agentsServicesRepository;
	private final IAgentRoleDao rolesDao;
	private final GAgentConfigRepository agentConfigRepo;

	static class ServiceWithConfig {
		IGNetworkAgentService service;
		GAgentConfig config;
		int turnOfExecution = 0;
		AgentNetworkParticipant networkParticipantConfig;
		AgentPrivateSessionContext agentContext = new AgentPrivateSessionContext();
	}

	@Override
	public <InputType, OutputType> OutputType executeNetwork(InputType input, AgentsNetwork network,
			Class<OutputType> outputType, ReactiveIdentityUtil runAs) throws AgentException, LLMConfigException {
		Map<String, ServiceWithConfig> agents = allocateAgents(network);
		if (network.getAgents() != null || network.getAgents().isEmpty())
			throw new AgentException("No agents configured");
		Optional<AgentNetworkParticipant> inputNode = network.getAgents().stream().filter(x -> x.isInputNode())
				.findFirst();
		if (inputNode.isEmpty())
			throw new AgentException("No input node configured in agents network");
		AgentNetworkParticipant inputNodeAgentConfig = inputNode.get();
		String inputNodeName = inputNodeAgentConfig.getNetworkAgentName();
		AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext();
		AgentsExchangeMessage<InputType> inputMessage = AgentsExchangeMessage.of(session, inputNodeName, input,
				MessageSemantic.AS_FUNCTION_CALL);
		ServiceWithConfig inputRuntime = agents.get(inputNodeName);
		if (!inputRuntime.service.getInputType().isAssignableFrom(input.getClass()))
			throw new AgentException(
					"Network input node does not support a matching type " + input.getClass().getName());

		return executeNetworkLoops(network, session, inputRuntime, inputMessage, agents, outputType, runAs);
	}

	protected <InputType, OutputType> OutputType executeNetworkLoops(AgentsNetwork network,
			AgentsCollaborationSessionContext session, ServiceWithConfig inputRuntime,
			AgentsExchangeMessage<InputType> inputMessage, Map<String, ServiceWithConfig> agents,
			Class<OutputType> outputType, ReactiveIdentityUtil runAs) throws LLMConfigException, AgentException {
		OutputType output = null;
		if (checkContinueLoop(network, agents)) {
			List<AgentsExchangeMessage<?>> messages = inputRuntime.service.onMessage(inputRuntime.config, inputMessage,
					network, inputRuntime.networkParticipantConfig, session, inputRuntime.agentContext);
			inputRuntime.turnOfExecution++;
			for (AgentsExchangeMessage<?> msg : messages) {
				if (inputRuntime.networkParticipantConfig.isOutputNode()) {
					output = compose(output, (OutputType) msg.getPayload());
				}
				for (String agentName : msg.getToAgent()) {
					ServiceWithConfig svc = agents.get(agentName);
					OutputType iterationOutput = executeNetworkLoops(network, session, svc, msg, agents, outputType,
							runAs);
					output = compose(output, iterationOutput);
				}
			}
		}
		return output;
	}

	private boolean checkContinueLoop(AgentsNetwork network, Map<String, ServiceWithConfig> agents) {
		int loopDone = Integer.MAX_VALUE;
		for (Map.Entry<String, ServiceWithConfig> entry : agents.entrySet()) {
			loopDone = Math.min(loopDone, entry.getValue().turnOfExecution);
		}
		return network.getMaxLoopIteration() <= loopDone;
	}

	protected abstract <OutputType> OutputType compose(OutputType actualOutput, OutputType incremental);

	private Map<String, ServiceWithConfig> allocateAgents(AgentsNetwork network) {
		// TODO Auto-generated method stub
		return null;
	}

}
