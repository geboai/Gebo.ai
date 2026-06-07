package ai.gebo.architecture.agents.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsNetwork;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.architecture.agents.model.AgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.repository.GAgentConfigRepository;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRepositoryPattern;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class GAbstractAgentsNetworkService implements IGAgentsNetworkService {
	private final IGAgentServiceRepositoryPattern agentsServicesRepository;
	private final IAgentRoleDao rolesDao;
	private final GAgentConfigRepository agentConfigRepo;

	@Override
	public <InputType, OutputType> OutputType executeNetwork(InputType input, AgentsNetwork network,
			Class<OutputType> outputType, ReactiveIdentityUtil runAs) throws AgentException, LLMConfigException {
		final Map<String, RuntimeAgentInfos> agents = allocateAgents(network);
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
		RuntimeAgentInfos inputRuntime = agents.get(inputNodeName);
		if (!inputRuntime.getService().getInputType().isAssignableFrom(input.getClass()))
			throw new AgentException(
					"Network input node does not support a matching type " + input.getClass().getName());
		IGAgentsNetworkRuntimeDao agentsDao = new IGAgentsNetworkRuntimeDao() {

			@Override
			public RuntimeAgentInfos findAgentByCode(String agentName) {

				return agents.get(agentName);
			}
		};
		return executeNetworkLoops(network, agentsDao, session, inputRuntime, inputMessage, agents, outputType, runAs);
	}

	protected <InputType, OutputType> OutputType executeNetworkLoops(AgentsNetwork network,
			IGAgentsNetworkRuntimeDao agentsDao, AgentsCollaborationSessionContext session,
			RuntimeAgentInfos inputRuntime, AgentsExchangeMessage<InputType> inputMessage,
			Map<String, RuntimeAgentInfos> agents, Class<OutputType> outputType, ReactiveIdentityUtil runAs)
			throws LLMConfigException, AgentException {
		OutputType output = null;
		if (checkContinueLoop(network, agents)) {
			List<AgentsExchangeMessage<?>> messages = inputRuntime.getService().onMessage(inputRuntime.getConfig(),
					inputMessage, network, agentsDao, inputRuntime.getNetworkParticipantConfig(), session,
					inputRuntime.getAgentContext(), runAs);
			inputRuntime.setTurnOfExecution(inputRuntime.getTurnOfExecution() + 1);
			for (AgentsExchangeMessage<?> msg : messages) {
				if (inputRuntime.getNetworkParticipantConfig().isOutputNode()) {
					output = compose(output, (OutputType) msg.getPayload());
				}
				for (String agentName : msg.getToAgent()) {
					RuntimeAgentInfos svc = agents.get(agentName);
					OutputType iterationOutput = executeNetworkLoops(network, agentsDao, session, svc, msg, agents,
							outputType, runAs);
					output = compose(output, iterationOutput);
				}
			}
		}
		return output;
	}

	private boolean checkContinueLoop(AgentsNetwork network, Map<String, RuntimeAgentInfos> agents) {
		int loopDone = Integer.MAX_VALUE;
		for (Map.Entry<String, RuntimeAgentInfos> entry : agents.entrySet()) {
			loopDone = Math.min(loopDone, entry.getValue().getTurnOfExecution());
		}
		return network.getMaxLoopIteration() <= loopDone;
	}

	protected abstract <OutputType> OutputType compose(OutputType actualOutput, OutputType incremental);

	private Map<String, RuntimeAgentInfos> allocateAgents(AgentsNetwork network) {
		// TODO Auto-generated method stub
		return null;
	}

}
