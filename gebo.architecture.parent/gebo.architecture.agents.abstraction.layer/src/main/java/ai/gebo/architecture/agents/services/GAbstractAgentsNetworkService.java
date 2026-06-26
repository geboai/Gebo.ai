package ai.gebo.architecture.agents.services;

import java.util.ArrayList;
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
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class GAbstractAgentsNetworkService<InputType, OutputType>
		implements IGAgentsNetworkService<InputType, OutputType> {
	private static final String EXCEPTION_IN_AGENTS_NETWORK_EXECUTION = "Exception in agents network execution";
	private static final String NETWORK_INPUT_NODE_DOES_NOT_SUPPORT_A_MATCHING_TYPE = "Network input node does not support a matching type ";
	private static final String NO_INPUT_NODE_CONFIGURED_IN_AGENTS_NETWORK = "No input node configured in agents network";
	private static final String NO_AGENTS_CONFIGURED = "No agents configured";
	private static final String NO_RUNTIME_ALLOCATED_FOR_INPUT_NODE = "No runtime agent allocated for input node: ";
	private final IGAgentServiceRuntimeDao agentsServicesRepository;
	private final IAgentRoleDao rolesDao;
	private final IGeboThreadManager threadManager;
	private final GAgentsNetwork network;
	private final INotificationSink notificationSink;
	private final Class<InputType> inputType;
	private final Class<OutputType> outputType;	
	private final ReactiveIdentityUtil runAs;
	private final IGAgentsNetworkRuntimeDao agentsDao;

	@Override
	public OutputType executeNetwork(IChatRequestContext chatRequestContext, InputType input)
			throws AgentException, LLMConfigException {
		if (network.getAgents() == null || network.getAgents().isEmpty())
			throw new AgentException(NO_AGENTS_CONFIGURED);
		Optional<AgentNetworkParticipant> inputNode = network.getAgents().stream().filter(x -> x.isInputNode())
				.findFirst();
		if (inputNode.isEmpty())
			throw new AgentException(NO_INPUT_NODE_CONFIGURED_IN_AGENTS_NETWORK);
		final AgentNetworkParticipant inputNodeAgentConfig = inputNode.get();
		final String inputNodeName = inputNodeAgentConfig.getNetworkAgentName();
		final AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext();
		final AgentsExchangeMessage<InputType> inputMessage = AgentsExchangeMessage.of(session, inputNodeName, input,
				MessageSemantic.EXECUTE_AND_SHARE_RESULT);
		final RuntimeAgentInfos inputRuntime = agentsDao.findAgentByCode(inputNodeName);
		if (inputRuntime == null)
			throw new AgentException(NO_RUNTIME_ALLOCATED_FOR_INPUT_NODE + inputNodeName);
		if (!inputRuntime.getService().getInputType().isAssignableFrom(input.getClass()))
			throw new AgentException(NETWORK_INPUT_NODE_DOES_NOT_SUPPORT_A_MATCHING_TYPE + input.getClass().getName());

		CallsResult<OutputType> iterationResult = null;
		try {
			iterationResult = executeNetworkLoops(chatRequestContext, notificationSink, agentsDao, session,
					inputRuntime, inputMessage, outputType, runAs);
			while (iterationResult != null && iterationResult.getDeliveryOrder() != null
					&& !iterationResult.getDeliveryOrder().isEmpty()) {
				CallsResult<OutputType> levelResult = new CallsResult<OutputType>(new TreeMap<>(),
						iterationResult.getOutput());
				for (List<AgentsExchangeMessage<?>> group : iterationResult.getDeliveryOrder().values()) {
					CallsResult<OutputType> rowResult = executeNetworkLoopsGroup(chatRequestContext, notificationSink,
							agentsDao, session, group, outputType, runAs);
					levelResult = join(levelResult, rowResult);
				}
				iterationResult = levelResult;
			}
		} catch (LLMConfigException | AgentException | InterruptedException | ExecutionException e) {
			throw new AgentException(EXCEPTION_IN_AGENTS_NETWORK_EXECUTION, e);
		}
		return iterationResult != null ? iterationResult.getOutput() : null;
	}

	protected <OutputType> CallsResult<OutputType> join(CallsResult<OutputType> levelResult,
			CallsResult<OutputType> rowResult) {
		if (levelResult == null)
			return rowResult;
		if (rowResult == null)
			return levelResult;
		TreeMap<Integer, List<AgentsExchangeMessage<?>>> mergedDeliveryOrder = new TreeMap<>();
		mergeDeliveryOrder(mergedDeliveryOrder, levelResult.getDeliveryOrder());
		mergeDeliveryOrder(mergedDeliveryOrder, rowResult.getDeliveryOrder());
		OutputType composedOutput = compose(levelResult.getOutput(), rowResult.getOutput());
		return new CallsResult<OutputType>(mergedDeliveryOrder, composedOutput);
	}

	private void mergeDeliveryOrder(TreeMap<Integer, List<AgentsExchangeMessage<?>>> target,
			TreeMap<Integer, List<AgentsExchangeMessage<?>>> source) {
		if (source == null)
			return;
		for (Map.Entry<Integer, List<AgentsExchangeMessage<?>>> entry : source.entrySet()) {
			target.computeIfAbsent(entry.getKey(), k -> new ArrayList<>()).addAll(entry.getValue());
		}
	}

	@AllArgsConstructor
	@Getter
	class CallsResult<OutputType> {
		private final TreeMap<Integer, List<AgentsExchangeMessage<?>>> deliveryOrder;
		private final OutputType output;
	}

	protected <InputType, OutputType> CallsResult<OutputType> executeNetworkLoops(
			IChatRequestContext chatRequestContext, INotificationSink notificationSink,
			IGAgentsNetworkRuntimeDao agentsDao, AgentsCollaborationSessionContext session,
			RuntimeAgentInfos inputRuntime, AgentsExchangeMessage<?> inputMessage, Class<OutputType> outputType,
			ReactiveIdentityUtil runAs)
			throws LLMConfigException, AgentException, InterruptedException, ExecutionException {
		OutputType output = null;
		final TreeMap<Integer, List<AgentsExchangeMessage<?>>> deliveryOrder = new TreeMap<>();
		if (checkContinueLoop(network, agentsDao)) {
			final int contributionNr = session.getAndIncrementContributionNr();
			inputRuntime.setTurnOfExecution(contributionNr);
			List<AgentsExchangeMessage<?>> messages = inputRuntime.getService().onMessage(chatRequestContext,
					inputRuntime.getConfig(), inputMessage, contributionNr, network,
					inputRuntime.getNetworkParticipantConfig(), notificationSink, session,
					inputRuntime.getAgentContext(), runAs, agentsDao);

			if (messages == null || messages.isEmpty()) {
				addToEmptyReturn(inputMessage, contributionNr, inputRuntime.getAgentContext());
			} else {
				for (AgentsExchangeMessage<?> msg : messages) {
					addTo(msg, contributionNr, session);
					addTo(msg, inputMessage, contributionNr, inputRuntime.getAgentContext());
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
			}

		}

		return new CallsResult<OutputType>(deliveryOrder, output);

	}

	private void addToEmptyReturn(AgentsExchangeMessage<?> inputMessage, int contributionNr,
			AgentPrivateSessionContext agentContext) {
		agentContext.addInteraction(inputMessage, contributionNr, "");

	}

	protected <OutputType> CallsResult<OutputType> executeNetworkLoopsGroup(IChatRequestContext chatRequestContext,
			INotificationSink notificationSink, IGAgentsNetworkRuntimeDao agentsDao,
			AgentsCollaborationSessionContext session, List<AgentsExchangeMessage<?>> executionGroup,
			Class<OutputType> outputType, ReactiveIdentityUtil runAs)
			throws AgentException, LLMConfigException, InterruptedException, ExecutionException {
		CallsResult<OutputType> out = null;
		if (executionGroup.isEmpty())
			return null;
		if (executionGroup.size() == 1) {
			AgentsExchangeMessage<?> msg = executionGroup.get(0);
			RuntimeAgentInfos agentRuntime = agentsDao.findAgentByCode(msg.getToAgent());
			out = executeNetworkLoops(chatRequestContext, notificationSink, agentsDao, session, agentRuntime, msg,
					outputType, runAs);
		} else {
			List<CompletableFuture<CallsResult<OutputType>>> completables = new ArrayList<>();
			for (AgentsExchangeMessage<?> msg : executionGroup) {
				RuntimeAgentInfos agent = agentsDao.findAgentByCode(msg.getToAgent());
				Supplier<CallsResult<OutputType>> supplier = () -> {
					try {
						if (runAs != null)
							return runAs.doRunAsWithReturnAndException(() -> {
								return executeNetworkLoops(chatRequestContext, notificationSink, agentsDao, session,
										agent, msg, outputType, runAs);
							});
						else
							return executeNetworkLoops(chatRequestContext, notificationSink, agentsDao, session, agent,
									msg, outputType, runAs);
					} catch (Throwable e) {

						return null;
					}
				};
				Executor executor = threadManager.getExecutorService();
				CompletableFuture<CallsResult<OutputType>> completable = CompletableFuture.supplyAsync(supplier,
						executor);
				completables.add(completable);
			}
			for (CompletableFuture<CallsResult<OutputType>> completableFuture : completables) {
				CallsResult<OutputType> iterationOut = completableFuture.get();
				out = join(out, iterationOut);
			}
		}
		return out;
	}

	private void addTo(AgentsExchangeMessage<?> msg, AgentsExchangeMessage<?> inputMessage, int contributionCounter,
			AgentPrivateSessionContext agentContext) {
		agentContext.addInteraction(inputMessage, contributionCounter, msg.getPayload());
	}

	private void addTo(AgentsExchangeMessage<?> msg, int contributionCounter,
			AgentsCollaborationSessionContext session) {
		if (msg.getMessageSemantic() == MessageSemantic.RESPONSE) {
			session.addContribution(msg, contributionCounter);
		}
	}

	private boolean checkContinueLoop(GAgentsNetwork network, IGAgentsNetworkRuntimeDao agentsDao)
			throws AgentException {
		int loopDone = Integer.MIN_VALUE;
		for (AgentNetworkParticipant agent : network.getAgents()) {
			RuntimeAgentInfos agentSituation = agentsDao.findAgentByCode(agent.getNetworkAgentName());
			loopDone = Math.max(agentSituation.getTurnOfExecution(), loopDone);
		}
		return loopDone < network.getMaxLoopIteration();
	}

	protected abstract <OutputType> OutputType compose(OutputType actualOutput, OutputType incremental);

	public Class<OutputType> getOutputType() {
		return outputType;
	}

	public Class<InputType> getInputType() {
		return inputType;
	}

}
