package ai.gebo.architecture.agents.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
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
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class GAbstractAgentsNetworkService implements IGAgentsNetworkService {
	private final IGAgentServiceRepositoryPattern agentsServicesRepository;
	private final IAgentRoleDao rolesDao;
	private final GAgentConfigRepository agentConfigRepo;
	private final IGeboThreadManager threadManager;

	@Override
	public <InputType, OutputType> OutputType executeNetwork(IChatRequestContext chatRequestContext, InputType input,
			AgentsNetwork network, Class<OutputType> outputType, ReactiveIdentityUtil runAs) throws AgentException, LLMConfigException {
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
				MessageSemantic.EXECUTE_AND_SHARE_RESULT);
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
		try {
			return executeNetworkLoops(chatRequestContext, network, agentsDao, session, inputRuntime, inputMessage, outputType, runAs);
		} catch (LLMConfigException | AgentException | InterruptedException | ExecutionException e) {
			throw new AgentException("Exception in agents network execution", e);
		}
	}

	protected <InputType, OutputType> OutputType executeNetworkLoops(IChatRequestContext chatRequestContext,
			AgentsNetwork network, IGAgentsNetworkRuntimeDao agentsDao,
			AgentsCollaborationSessionContext session, RuntimeAgentInfos inputRuntime, AgentsExchangeMessage<?> inputMessage,
			Class<OutputType> outputType, ReactiveIdentityUtil runAs)
			throws LLMConfigException, AgentException, InterruptedException, ExecutionException {
		OutputType output = null;
		if (checkContinueLoop(network, agentsDao)) {
			List<AgentsExchangeMessage<?>> messages = inputRuntime.getService().onMessage(chatRequestContext,
					inputRuntime.getConfig(), inputMessage, network, inputRuntime.getNetworkParticipantConfig(), session,
					inputRuntime.getAgentContext(), runAs, agentsDao);
			inputRuntime.setTurnOfExecution(inputRuntime.getTurnOfExecution() + 1);
			TreeMap<Integer, List<AgentsExchangeMessage<?>>> deliveryOrder = new TreeMap<>();
			for (AgentsExchangeMessage<?> msg : messages) {
				addTo(msg, session);
				addTo(msg, inputMessage, inputRuntime.getAgentContext());
				if (inputRuntime.getNetworkParticipantConfig().isOutputNode()
						&& msg.getMessageSemantic() == MessageSemantic.RESPONSE) {
					output = compose(output, (OutputType) msg.getPayload());
				}
				MessageSemantic msgSemantic = msg.getMessageSemantic();
				if (msgSemantic != null) {
					switch (msgSemantic) {
					case EXECUTE_AND_SHARE_RESULT: {
						if (!deliveryOrder.containsKey(msg.getExecutionOrder())) {
							deliveryOrder.put(msg.getExecutionOrder(), new ArrayList<>());
						}
						deliveryOrder.get(msg.getExecutionOrder()).add(msg);
					}
						break;
					}

				}
			}
			for (List<AgentsExchangeMessage<?>> parallelExecs : deliveryOrder.values()) {
				OutputType loopOutput = executeNetworkLoopsGroup(chatRequestContext, network, agentsDao, session, parallelExecs,
						outputType, runAs);
				if (loopOutput != null) {
					output = compose(output, loopOutput);
				}
			}

		}

		return output;

	}

	protected <OutputType> OutputType executeNetworkLoopsGroup(IChatRequestContext chatRequestContext,
			AgentsNetwork network, IGAgentsNetworkRuntimeDao agentsDao,
			AgentsCollaborationSessionContext session, List<AgentsExchangeMessage<?>> executionGroup, Class<OutputType> outputType, ReactiveIdentityUtil runAs)
			throws AgentException, LLMConfigException, InterruptedException, ExecutionException {
		OutputType out = null;
		if (executionGroup.isEmpty())
			return null;
		if (executionGroup.size() == 1) {
			AgentsExchangeMessage<?> msg = executionGroup.get(0);
			RuntimeAgentInfos agentRuntime = agentsDao.findAgentByCode(msg.getToAgent());
			out = executeNetworkLoops(chatRequestContext, network, agentsDao, session, agentRuntime, msg, outputType, runAs);
		} else {
			List<CompletableFuture<OutputType>> completables = new ArrayList<>();
			for (AgentsExchangeMessage<?> msg : executionGroup) {
				RuntimeAgentInfos agent = agentsDao.findAgentByCode(msg.getToAgent());
				Supplier<OutputType> supplier = () -> {
					try {
						if (runAs != null)
							return runAs.doRunAsWithReturnAndException(() -> {
								return executeNetworkLoops(chatRequestContext, network, agentsDao, session, agent, msg, outputType, runAs);
							});
						else
							return executeNetworkLoops(chatRequestContext, network, agentsDao, session, agent, msg, outputType, runAs);
					} catch (Throwable e) {

						return null;
					}
				};
				Executor executor = threadManager.getExecutorService();
				CompletableFuture<OutputType> completable = CompletableFuture.supplyAsync(supplier, executor);
				completables.add(completable);
			}
			for (CompletableFuture<OutputType> completableFuture : completables) {
				OutputType iterationOut = completableFuture.get();
				out = compose(out, iterationOut);
			}
		}
		return out;
	}

	private void addTo(AgentsExchangeMessage<?> msg, AgentsExchangeMessage<?> inputMessage,
			AgentPrivateSessionContext agentContext) {
		agentContext.addInteraction(inputMessage,msg.getPayload());
	}

	private void addTo(AgentsExchangeMessage<?> msg, AgentsCollaborationSessionContext session) {
		if (msg.getMessageSemantic()==MessageSemantic.RESPONSE) {
			session.addContribution(msg);
		}
	}

	private boolean checkContinueLoop(AgentsNetwork network, IGAgentsNetworkRuntimeDao agentsDao)
			throws AgentException {
		int loopDone = Integer.MIN_VALUE;
		for (AgentNetworkParticipant agent : network.getAgents()) {
			RuntimeAgentInfos agentSituation = agentsDao.findAgentByCode(agent.getNetworkAgentName());
			loopDone = Math.max(agentSituation.getTurnOfExecution(), loopDone);
		}
		return network.getMaxLoopIteration() <= loopDone;
	}

	protected abstract <OutputType> OutputType compose(OutputType actualOutput, OutputType incremental);

	private Map<String, RuntimeAgentInfos> allocateAgents(AgentsNetwork network) {
		// TODO Auto-generated method stub
		return null;
	}

}
