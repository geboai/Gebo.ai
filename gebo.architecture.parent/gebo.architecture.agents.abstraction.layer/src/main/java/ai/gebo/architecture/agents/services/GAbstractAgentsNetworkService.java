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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(GAbstractAgentsNetworkService.class);
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
	public OutputType executeNetwork(IChatRequestContext chatRequestContext, InputType input,
			Map<String, Object> environment) throws AgentException, LLMConfigException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin executeNetwork(...) network code:" + (network != null ? network.getCode() : null)
					+ " inputType:" + (input != null ? input.getClass().getName() : null) + " maxLoopIteration:"
					+ (network != null ? network.getMaxLoopIteration() : null));
		}
		if (network.getAgents() == null || network.getAgents().isEmpty())
			throw new AgentException(NO_AGENTS_CONFIGURED);
		Optional<AgentNetworkParticipant> inputNode = network.getAgents().stream().filter(x -> x.isInputNode())
				.findFirst();
		if (inputNode.isEmpty())
			throw new AgentException(NO_INPUT_NODE_CONFIGURED_IN_AGENTS_NETWORK);
		final AgentNetworkParticipant inputNodeAgentConfig = inputNode.get();
		final String inputNodeName = inputNodeAgentConfig.getNetworkAgentName();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Resolved input node:" + inputNodeName + " among " + network.getAgents().size()
					+ " network participant(s)");
		}
		final AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext();
		if (environment != null)
			session.getEnvironment().putAll(environment);
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<NETWORK_ENVIRONMENT network=" + (network != null ? network.getCode() : null) + ">");
			LOGGER.trace(String.valueOf(environment));
			LOGGER.trace("</NETWORK_ENVIRONMENT>");
			LOGGER.trace("<NETWORK_INPUT node=" + inputNodeName + ">");
			LOGGER.trace(String.valueOf(input));
			LOGGER.trace("</NETWORK_INPUT>");
		}
		final AgentsExchangeMessage<InputType> inputMessage = AgentsExchangeMessage.of(session, inputNodeName, input,
				MessageSemantic.EXECUTE_AND_SHARE_RESULT);
		final RuntimeAgentInfos inputRuntime = agentsDao.findAgentByCode(inputNodeName);
		if (inputRuntime == null)
			throw new AgentException(NO_RUNTIME_ALLOCATED_FOR_INPUT_NODE + inputNodeName);
		if (!inputRuntime.getService().getInputType().isAssignableFrom(input.getClass()))
			throw new AgentException(NETWORK_INPUT_NODE_DOES_NOT_SUPPORT_A_MATCHING_TYPE + input.getClass().getName());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Input node:" + inputNodeName + " bound to agent service id:"
					+ inputRuntime.getService().getId() + " accepting inputType:"
					+ inputRuntime.getService().getInputType().getName());
		}

		CallsResult<OutputType> iterationResult = null;
		boolean dynamicExchange = false;
		try {
			iterationResult = executeNetworkLoops(chatRequestContext, notificationSink, agentsDao, session,
					inputRuntime, inputMessage, outputType, runAs);
			// A "dynamic exchange" means the input node actually dispatched at least one
			// message onward into the network. Captured before the delivery loop consumes
			// iterationResult, so a network that seeds but never propagates can be flagged.
			dynamicExchange = iterationResult != null && iterationResult.getDeliveryOrder() != null
					&& !iterationResult.getDeliveryOrder().isEmpty();
			int level = 0;
			while (iterationResult != null && iterationResult.getDeliveryOrder() != null
					&& !iterationResult.getDeliveryOrder().isEmpty()) {
				level++;
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Processing delivery level " + level + " with "
							+ iterationResult.getDeliveryOrder().size() + " group(s)");
				}
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
			LOGGER.error(EXCEPTION_IN_AGENTS_NETWORK_EXECUTION, e);
			throw new AgentException(EXCEPTION_IN_AGENTS_NETWORK_EXECUTION, e);
		}
		final OutputType producedOutput = iterationResult != null ? iterationResult.getOutput() : null;
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<NETWORK_OUTPUT network=" + (network != null ? network.getCode() : null) + ">");
			LOGGER.trace(String.valueOf(producedOutput));
			LOGGER.trace("</NETWORK_OUTPUT>");
		}
		// If the network stopped without any dynamic message exchange between agents and
		// produced no output, surface it with ERROR severity: the run terminated dead
		// (e.g. the input node emitted nothing to route), which the user must be told.
		if (!dynamicExchange && producedOutput == null) {
			notificationSink.next("Agents network '" + (network != null ? network.getCode() : null)
					+ "' stopped without any dynamic message exchange between agents", NotificationType.ERROR);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End executeNetwork(...) network code:" + (network != null ? network.getCode() : null)
					+ " produced output:" + (producedOutput != null) + " dynamicExchange:" + dynamicExchange);
		}
		return producedOutput;
	}

	protected <OutputType> CallsResult<OutputType> join(CallsResult<OutputType> levelResult,
			CallsResult<OutputType> rowResult) {
		if (levelResult == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("join(...) no level result yet, keeping the row result as is");
			}
			return rowResult;
		}
		if (rowResult == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("join(...) no row result to merge, keeping the level result as is");
			}
			return levelResult;
		}
		TreeMap<Integer, List<AgentsExchangeMessage<?>>> mergedDeliveryOrder = new TreeMap<>();
		mergeDeliveryOrder(mergedDeliveryOrder, levelResult.getDeliveryOrder());
		mergeDeliveryOrder(mergedDeliveryOrder, rowResult.getDeliveryOrder());
		OutputType composedOutput = compose(levelResult.getOutput(), rowResult.getOutput());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("join(...) merged delivery order into " + mergedDeliveryOrder.size()
					+ " execution order slot(s), composed output:" + (composedOutput != null));
		}
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
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin executeNetworkLoops(...) targetAgent:"
					+ (inputRuntime != null ? inputRuntime.getService().getId() : null) + " messageSemantic:"
					+ (inputMessage != null ? inputMessage.getMessageSemantic() : null));
		}
		final TreeMap<Integer, List<AgentsExchangeMessage<?>>> deliveryOrder = new TreeMap<>();
		if (checkContinueLoop(network, agentsDao)) {
			final int contributionNr = session.getAndIncrementContributionNr();
			inputRuntime.setTurnOfExecution(contributionNr);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Dispatching message to agent:" + inputRuntime.getService().getId() + " contributionNr:"
						+ contributionNr);
			}
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("<AGENT_INBOUND_MESSAGE to=" + inputMessage.getToAgent() + " semantic="
						+ inputMessage.getMessageSemantic() + ">");
				LOGGER.trace(String.valueOf(inputMessage.getPayload()));
				LOGGER.trace("</AGENT_INBOUND_MESSAGE>");
			}
			final String participantName = inputRuntime.getNetworkParticipantConfig().getNetworkAgentName();
			final boolean isInputNode = inputRuntime.getNetworkParticipantConfig().isInputNode();
			notificationSink.next("Agent: " + participantName + " is working...", NotificationType.DEBUG);
			List<AgentsExchangeMessage<?>> messages;
			try {
				messages = inputRuntime.getService().onMessage(chatRequestContext, inputRuntime.getConfig(), inputMessage,
						contributionNr, network, inputRuntime.getNetworkParticipantConfig(), notificationSink, session,
						inputRuntime.getAgentContext(), runAs, agentsDao);
			} catch (Throwable failure) {
				if (isInputNode) {
					// The input node seeds the whole network: if it fails there is nothing
					// downstream to run, so notify with ERROR severity and let the failure stop
					// the network (it propagates to executeNetwork and aborts the run).
					notificationSink.next("Input node agent: " + participantName + " failed, stopping network: "
							+ rootCauseMessage(failure), NotificationType.ERROR);
					LOGGER.error("Input node agent " + participantName + " failed; stopping network", failure);
					throw new AgentException("Input node agent failed: " + participantName, failure);
				}
				// A downstream agent failure is isolated so the network stays resilient: notify
				// (debug), record an empty turn so the private session context stays consistent,
				// and let the rest of the network proceed. The failing agent's turn has already
				// been advanced above, so loop termination still progresses.
				notificationSink.next("Agent: " + participantName + " failed, skipped: " + rootCauseMessage(failure),
						NotificationType.DEBUG);
				LOGGER.error("Agent " + participantName + " failed; continuing network", failure);
				addToEmptyReturn(inputMessage, contributionNr, inputRuntime.getAgentContext());
				return new CallsResult<OutputType>(new TreeMap<>(), output);
			}
			notificationSink.next("Agent: " + participantName + " has finished", NotificationType.DEBUG);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Agent:" + inputRuntime.getService().getId() + " returned "
						+ (messages != null ? messages.size() : 0) + " message(s)");
			}
			if (messages == null || messages.isEmpty()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Agent:" + participantName + " produced no message, recording an empty turn");
				}
				addToEmptyReturn(inputMessage, contributionNr, inputRuntime.getAgentContext());
			} else {
				for (AgentsExchangeMessage<?> msg : messages) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Agent:" + participantName + " emitted message to:" + msg.getToAgent()
								+ " semantic:" + msg.getMessageSemantic() + " executionOrder:" + msg.getExecutionOrder());
					}
					if (LOGGER.isTraceEnabled()) {
						LOGGER.trace("<AGENT_OUTBOUND_MESSAGE from=" + participantName + " to=" + msg.getToAgent()
								+ " semantic=" + msg.getMessageSemantic() + ">");
						LOGGER.trace(String.valueOf(msg.getPayload()));
						LOGGER.trace("</AGENT_OUTBOUND_MESSAGE>");
					}
					addTo(msg, contributionNr, session);
					addTo(msg, inputMessage, contributionNr, inputRuntime.getAgentContext());
					if (inputRuntime.getNetworkParticipantConfig().isOutputNode()
							&& msg.getMessageSemantic() == MessageSemantic.RESPONSE) {
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Composing network output from output node:" + participantName);
						}
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

		} else if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("executeNetworkLoops(...) skipped: the network loop budget is exhausted");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End executeNetworkLoops(...) targetAgent:"
					+ (inputRuntime != null ? inputRuntime.getService().getId() : null) + " deliveryOrder slots:"
					+ deliveryOrder.size() + " output:" + (output != null));
		}
		return new CallsResult<OutputType>(deliveryOrder, output);

	}

	private void addToEmptyReturn(AgentsExchangeMessage<?> inputMessage, int contributionNr,
			AgentPrivateSessionContext agentContext) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Recording an empty interaction for contributionNr:" + contributionNr);
		}
		agentContext.addInteraction(inputMessage, contributionNr, "");

	}

	/**
	 * Extracts a concise, human-readable message from the root cause of a failure,
	 * for use in the {@link INotificationSink} failure notifications.
	 */
	private static String rootCauseMessage(Throwable failure) {
		Throwable root = failure;
		while (root.getCause() != null && root.getCause() != root) {
			root = root.getCause();
		}
		String message = root.getMessage();
		return message != null ? message : root.getClass().getSimpleName();
	}

	protected <OutputType> CallsResult<OutputType> executeNetworkLoopsGroup(IChatRequestContext chatRequestContext,
			INotificationSink notificationSink, IGAgentsNetworkRuntimeDao agentsDao,
			AgentsCollaborationSessionContext session, List<AgentsExchangeMessage<?>> executionGroup,
			Class<OutputType> outputType, ReactiveIdentityUtil runAs)
			throws AgentException, LLMConfigException, InterruptedException, ExecutionException {
		CallsResult<OutputType> out = null;
		if (executionGroup.isEmpty()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("executeNetworkLoopsGroup(...) skipped: the execution group is empty");
			}
			return null;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin executeNetworkLoopsGroup(...) with " + executionGroup.size() + " message(s) (parallel:"
					+ (executionGroup.size() > 1) + ")");
		}
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
						LOGGER.error("Exception executing network loop for agent:" + msg.getToAgent(), e);
						return null;
					}
				};
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Scheduling parallel network loop for agent:" + msg.getToAgent() + " executionOrder:"
							+ msg.getExecutionOrder());
				}
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
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End executeNetworkLoopsGroup(...) deliveryOrder slots:"
					+ (out != null && out.getDeliveryOrder() != null ? out.getDeliveryOrder().size() : 0) + " output:"
					+ (out != null && out.getOutput() != null));
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
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Adding a RESPONSE message to the shared session context, contributionNr:"
						+ contributionCounter);
			}
			session.addContribution(msg, contributionCounter);
		}
	}

	private boolean checkContinueLoop(GAgentsNetwork network, IGAgentsNetworkRuntimeDao agentsDao)
			throws AgentException {
		int loopDone = Integer.MIN_VALUE;
		for (AgentNetworkParticipant agent : network.getAgents()) {
			RuntimeAgentInfos agentSituation = agentsDao.findAgentByCode(agent.getNetworkAgentName());
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("Agent:" + agent.getNetworkAgentName() + " turnOfExecution:"
						+ agentSituation.getTurnOfExecution());
			}
			loopDone = Math.max(agentSituation.getTurnOfExecution(), loopDone);
		}
		boolean continueLoop = loopDone < network.getMaxLoopIteration();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("checkContinueLoop(...) maxTurnOfExecution:" + loopDone + " maxLoopIteration:"
					+ network.getMaxLoopIteration() + " continue:" + continueLoop);
		}
		return continueLoop;
	}

	protected abstract <OutputType> OutputType compose(OutputType actualOutput, OutputType incremental);

	public Class<OutputType> getOutputType() {
		return outputType;
	}

	public Class<InputType> getInputType() {
		return inputType;
	}

}
