package ai.gebo.architecture.agents.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentProducedSessionContribution;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.AgentsSessionCreationContext;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.AgentPrivateState;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.AgentRuntimeState;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.ExchangeMessageRecord;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.FrozenDeliveries;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.PrivateInteraction;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.SharedContribution;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.SharedState;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.SuspendedAgentState;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.WaitingCondition;
import ai.gebo.architecture.agents.model.NetworkOfAgentsExecutionResult;
import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;

public abstract class GAbstractAgentsNetworkService<InputType, OutputType>
		implements IGAgentsNetworkService<InputType, OutputType>, IGSessionContextFactoryAware {
	private static final Logger LOGGER = LoggerFactory.getLogger(GAbstractAgentsNetworkService.class);
	private static final String EXCEPTION_IN_AGENTS_NETWORK_EXECUTION = "Exception in agents network execution";
	private static final String NETWORK_INPUT_NODE_DOES_NOT_SUPPORT_A_MATCHING_TYPE = "Network input node does not support a matching type ";
	private static final String NO_INPUT_NODE_CONFIGURED_IN_AGENTS_NETWORK = "No input node configured in agents network";
	private static final String NO_AGENTS_CONFIGURED = "No agents configured";
	private static final String NO_TOOL_CALLING_FOR_THE_RUN = "The run has no tool calling:"
			+ " the entry point of a run builds one for every call of it";
	private static final String NO_RUNTIME_ALLOCATED_FOR_INPUT_NODE = "No runtime agent allocated for input node: ";
	private static final String NO_RUNTIME_ALLOCATED_FOR_AGENT = "No runtime agent allocated for agent: ";
	private static final String NO_SUSPENSION_TO_RESPAWN = "There is no suspension to respawn the network from";
	private static final String SUSPENSION_OF_ANOTHER_NETWORK = "The suspension is of another network: ";
	private static final String SUSPENSION_WITHOUT_SHARED_STATE = "The suspension has no shared state to respawn from: ";
	private static final String SUSPENDED_AGENT_WITHOUT_TURN = "The suspension does not say which message the agent was executing: ";
	private static final String SERVICE_ALREADY_RUN = "A respawn needs a network service created for the block,"
			+ " this one already ran: ";
	private final IGAgentServiceRuntimeDao agentsServicesRepository;
	private final IAgentRoleDao rolesDao;
	private final IGeboThreadManager threadManager;
	private final GAgentsNetwork network;
	private final INotificationSink notificationSink;
	private final Class<InputType> inputType;
	private final Class<OutputType> outputType;
	private final ReactiveIdentityUtil runAs;
	private final IGAgentsNetworkRuntimeDao agentsDao;
	/**
	 * Where the suspended runs are kept: the handler of a run keeps the cut there
	 * and a respawned block is marked there. Null means the runs of this service
	 * cannot be suspended.
	 */
	private final GNetworkOfAgentsSuspensionRepository suspensions;
	/**
	 * Who builds the session of a run, when someone outside owns it; null means
	 * this service builds its own, which is what a plain run does.
	 */
	private volatile IAgentsCollaborationSessionContextFactory sessionContextFactory;
	/** Tells the type of the conditions of a suspended run; null keeps none. */
	private volatile IWaitingConditionTypeResolver conditionTypeResolver;
	/** Tells when the conditions of a suspended run stop waiting; null gives them no expiry. */
	private volatile IWaitingConditionExpiryPolicy conditionExpiryPolicy;

	/**
	 * A network service that owns the sessions of its runs: every run collaborates
	 * in a session of its own, with a fresh identity and no past. The repository is
	 * where its suspended runs are kept; null when they cannot be suspended.
	 */
	public GAbstractAgentsNetworkService(IGAgentServiceRuntimeDao agentsServicesRepository, IAgentRoleDao rolesDao,
			IGeboThreadManager threadManager, GAgentsNetwork network, INotificationSink notificationSink,
			Class<InputType> inputType, Class<OutputType> outputType, ReactiveIdentityUtil runAs,
			IGAgentsNetworkRuntimeDao agentsDao, GNetworkOfAgentsSuspensionRepository suspensions) {
		this(agentsServicesRepository, rolesDao, threadManager, network, notificationSink, inputType, outputType, runAs,
				agentsDao, suspensions, null);
	}

	/**
	 * A network service whose sessions are built by the given factory (see
	 * {@link IAgentsCollaborationSessionContextFactory}); a null factory is the
	 * same as not having one. The repository is where its suspended runs are kept;
	 * null when they cannot be suspended.
	 */
	public GAbstractAgentsNetworkService(IGAgentServiceRuntimeDao agentsServicesRepository, IAgentRoleDao rolesDao,
			IGeboThreadManager threadManager, GAgentsNetwork network, INotificationSink notificationSink,
			Class<InputType> inputType, Class<OutputType> outputType, ReactiveIdentityUtil runAs,
			IGAgentsNetworkRuntimeDao agentsDao, GNetworkOfAgentsSuspensionRepository suspensions,
			IAgentsCollaborationSessionContextFactory sessionContextFactory) {
		this.agentsServicesRepository = agentsServicesRepository;
		this.rolesDao = rolesDao;
		this.threadManager = threadManager;
		this.network = network;
		this.notificationSink = notificationSink;
		this.inputType = inputType;
		this.outputType = outputType;
		this.runAs = runAs;
		this.agentsDao = agentsDao;
		this.suspensions = suspensions;
		this.sessionContextFactory = sessionContextFactory;
	}

	/**
	 * The handler of a run's suspension (see {@link IGNetworkOfAgentsSuspensionHandler}):
	 * the agents of the run ask it to suspend the run, in their own control code, and
	 * this service asks it whether one did, to freeze what the run has not delivered
	 * yet. One per run, since the suspension state is the run's: one that keeps the
	 * cut in the repository of suspended runs (see
	 * {@link GMongoNetworkOfAgentsSuspensionHandler}), or, for a service without
	 * one, whose runs cannot be suspended, one that refuses, which is what a plain
	 * run does.
	 */
	protected IGNetworkOfAgentsSuspensionHandler createSuspensionHandler() {
		return suspensions != null
				? new GMongoNetworkOfAgentsSuspensionHandler(suspensions, conditionTypeResolver, conditionExpiryPolicy)
				: new NeverSuspendingHandler();
	}

	/**
	 * The handler of a run that cannot be suspended: it is never in suspension state,
	 * has nothing to freeze, and refuses an agent asking to suspend the run, so that
	 * the agent is told instead of waiting for a respawn that never comes.
	 */
	static final class NeverSuspendingHandler implements IGNetworkOfAgentsSuspensionHandler {
		private static final String CANNOT_BE_SUSPENDED = "The run of this network cannot be suspended";
		private volatile GAgentsNetwork network;

		@Override
		public void initialize(GAgentsNetwork agentsNetwork, Object networkInput,
				IChatRequestContext chatRequestContext) throws AgentException {
			this.network = agentsNetwork;
		}

		@Override
		public void enterSuspensionState(GAgentsNetwork agentsNetwork, GAgentConfig agentConfig,
				AgentNetworkParticipant participant, AgentPrivateSessionContext privateContext,
				AgentSuspendedWaitingCondition waitForCondition) throws AgentException {
			final GAgentsNetwork of = agentsNetwork != null ? agentsNetwork : network;
			throw new AgentException(CANNOT_BE_SUSPENDED + (of != null ? ": " + of.getCode() : "")
					+ (participant != null ? ", asked by agent: " + participant.getNetworkAgentName() : ""));
		}

		@Override
		public void setSuspendingMessagesNotYetDelivered(GAgentsNetwork agentsNetwork, GAgentConfig agentConfig,
				AgentNetworkParticipant participant, AgentPrivateSessionContext privateContext,
				List<AgentsExchangeMessage> messages) throws AgentException {
			// never in suspension state: nothing is ever frozen
		}

		@Override
		public boolean isSuspensionState() {
			return false;
		}

		@Override
		public void setSuspendingNetworkOfAgentsSharedState(AgentsCollaborationSessionContext sharedContext)
				throws AgentException {
			// never in suspension state: no shared state to save
		}

		@Override
		public void networkExecutionFinished() {
			// nothing was kept for the run
		}

		@Override
		public List<AgentSuspendedWaitingCondition> suspensionConditions() {
			return List.of();
		}

		@Override
		public void setSuspendedAgentTurn(AgentNetworkParticipant participant, AgentsExchangeMessage inputMessage,
				int contributionNr) throws AgentException {
			// never in suspension state: no turn to keep
		}

		@Override
		public void setSuspendingAgentsRuntimeState(List<RuntimeAgentInfos> agentsRuntime) throws AgentException {
			// never in suspension state: no runtime to keep
		}

		@Override
		public void respawnFrom(GNetworkOfAgentsSuspension suspension,
				Map<String, List<AgentSuspendedWaitingCondition>> resolvedConditionsByAgent) throws AgentException {
			throw new AgentException(CANNOT_BE_SUSPENDED + (network != null ? ": " + network.getCode() : "")
					+ ", so it cannot be respawned");
		}

		@Override
		public List<AgentSuspendedWaitingCondition> takeResolvedConditionsOf(AgentNetworkParticipant participant) {
			return List.of();
		}

		@Override
		public String suspensionId() {
			return null;
		}
	}

	/**
	 * Who tells the type of a condition an agent of a run waits for, kept with it
	 * when the run is suspended; null keeps none.
	 */
	public void setConditionTypeResolver(IWaitingConditionTypeResolver conditionTypeResolver) {
		this.conditionTypeResolver = conditionTypeResolver;
	}

	/**
	 * Who tells when a condition an agent of a run waits for stops waiting, kept
	 * with it when the run is suspended; null gives the conditions no expiry.
	 */
	public void setConditionExpiryPolicy(IWaitingConditionExpiryPolicy conditionExpiryPolicy) {
		this.conditionExpiryPolicy = conditionExpiryPolicy;
	}

	@Override
	public void setSessionContextFactory(IAgentsCollaborationSessionContextFactory sessionContextFactory) {
		this.sessionContextFactory = sessionContextFactory;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setSessionContextFactory(...) network code:" + (network != null ? network.getCode() : null)
					+ " sessions built from outside:" + (sessionContextFactory != null));
		}
	}

	/**
	 * What the run is about, for whoever builds its session.
	 */
	protected AgentsSessionCreationContext sessionCreationContext(IChatRequestContext chatRequestContext,
			Map<String, Object> environment) {
		return AgentsSessionCreationContext.builder().network(network).chatRequestContext(chatRequestContext)
				.environment(environment).inputType(inputType).outputType(outputType).runAs(runAs)
				.networkServiceId(getId()).build();
	}

	/**
	 * The session this run collaborates in: the one the factory builds when there
	 * is one that builds a usable session, else a session of this run's own.
	 */
	protected AgentsCollaborationSessionContext createSessionContext(AgentsSessionCreationContext creation) {
		final IAgentsCollaborationSessionContextFactory factory = sessionContextFactory;
		if (factory == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("No session factory supplied: the run opens a session of its own");
			}
			return new AgentsCollaborationSessionContext();
		}
		AgentsCollaborationSessionContext created = null;
		try {
			created = factory.createSessionContext(creation);
		} catch (Throwable factoryFailure) {
			// A session builder that cannot build one must not take the run down with it:
			// the run goes on in a session of its own, and the failure is reported.
			LOGGER.error(
					"The session factory failed to build a session for network:"
							+ (network != null ? network.getCode() : null) + "; the run opens one of its own",
					factoryFailure);
		}
		if (created == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The session factory built no session: the run opens one of its own");
			}
			return new AgentsCollaborationSessionContext();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("The run collaborates in session:" + created.getId() + " built from outside, already holding "
					+ created.getSampledContributions().size() + " contribution(s), next contributionNr:"
					+ created.getContributionCounter());
		}
		return created;
	}

	/**
	 * Tells whoever built the session that the run is over. Only the factory that
	 * built a session is told of it, and what it throws never changes the outcome
	 * of the run.
	 */
	private void sessionCompleted(AgentsCollaborationSessionContext session, AgentsSessionCreationContext creation,
			OutputType output, Throwable failure) {
		final IAgentsCollaborationSessionContextFactory factory = sessionContextFactory;
		if (factory == null || session == null) {
			return;
		}
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Closing session:" + session.getId() + " of network:"
						+ (network != null ? network.getCode() : null) + " produced output:" + (output != null)
						+ " failed:" + (failure != null) + " contributions:"
						+ session.getSampledContributions().size());
			}
			factory.sessionCompleted(session, creation, output, failure);
		} catch (Throwable completionFailure) {
			LOGGER.error("The session factory failed while closing session:" + session.getId()
					+ "; the outcome of the run is unchanged", completionFailure);
		}
	}

	@Override
	public NetworkOfAgentsExecutionResult<OutputType> executeNetwork(IChatRequestContext chatRequestContext,
			InputType input, Map<String, Object> environment, GeboToolCalling toolCalling)
			throws AgentException, LLMConfigException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin executeNetwork(...) network code:" + (network != null ? network.getCode() : null)
					+ " inputType:" + (input != null ? input.getClass().getName() : null) + " maxLoopIteration:"
					+ (network != null ? network.getMaxLoopIteration() : null) + " with " + toolCalling);
		}
		if (toolCalling == null)
			throw new AgentException(NO_TOOL_CALLING_FOR_THE_RUN);
		if (toolCalling.getListener() != null && LOGGER.isDebugEnabled()) {
			// each agent records its own tool calls, forwarded to the request's recorder; a
			// listener on the run's container records beside them, every call of the run
			LOGGER.debug("The tool calling of network:" + (network != null ? network.getCode() : null)
					+ " carries a listener: it records every call of the run beside the agents' own");
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
		final AgentsSessionCreationContext sessionCreation = sessionCreationContext(chatRequestContext, environment);
		final AgentsCollaborationSessionContext session = createSessionContext(sessionCreation);
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
		OutputType producedOutput = null;
		Throwable failure = null;
		// The handler of this run's suspension, given to every agent of the run: an
		// agent asks it to suspend the run, this service asks it whether one did.
		final IGNetworkOfAgentsSuspensionHandler suspensionHandler = createSuspensionHandler();
		try {
			suspensionHandler.initialize(network, input, chatRequestContext);
			producedOutput = executeNetworkInSession(chatRequestContext, input, session, inputNodeName, toolCalling,
					suspensionHandler);
			if (suspensionHandler.isSuspensionState()) {
				// The cut is complete: the agents that were running have ended, what they did
				// not deliver is frozen (see executeNetworkInSession), and the runtime of every
				// agent and the shared state of the run, with every contribution made so far,
				// are saved last.
				return suspendedResult(session, suspensionHandler);
			}
			return NetworkOfAgentsExecutionResult.of(producedOutput);
		} catch (AgentException | LLMConfigException | RuntimeException | Error runFailure) {
			failure = runFailure;
			throw runFailure;
		} finally {
			runOver(suspensionHandler, session, sessionCreation, producedOutput, failure);
		}
	}

	/**
	 * The result of a run suspended in the given session: the runtime of every agent
	 * and the shared state of the run are kept by the handler, last, and the result
	 * names the block the run is saved as, with the conditions it waits for.
	 */
	private NetworkOfAgentsExecutionResult<OutputType> suspendedResult(AgentsCollaborationSessionContext session,
			IGNetworkOfAgentsSuspensionHandler suspensionHandler) throws AgentException {
		suspensionHandler.setSuspendingAgentsRuntimeState(agentsRuntime());
		suspensionHandler.setSuspendingNetworkOfAgentsSharedState(session);
		final List<AgentSuspendedWaitingCondition> conditions = suspensionHandler.suspensionConditions();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Network:" + (network != null ? network.getCode() : null) + " suspended in session:"
					+ session.getId() + " waiting for " + (conditions != null ? conditions.size() : 0)
					+ " condition(s), suspension:" + suspensionHandler.suspensionId());
		}
		return NetworkOfAgentsExecutionResult.suspended(suspensionHandler.suspensionId(), conditions);
	}

	/**
	 * The handler is told the run is over in every case, executed, suspended or
	 * failed: a suspended run is written then, and a cut that cannot be written is
	 * the failure of the run. Whoever built this session is told the same in every
	 * case: with what the run produced, or with what made it fail.
	 */
	private void runOver(IGNetworkOfAgentsSuspensionHandler suspensionHandler,
			AgentsCollaborationSessionContext session, AgentsSessionCreationContext sessionCreation,
			OutputType producedOutput, Throwable failure) {
		try {
			suspensionHandler.networkExecutionFinished();
		} catch (RuntimeException | Error finishFailure) {
			sessionCompleted(session, sessionCreation, null, failure != null ? failure : finishFailure);
			throw finishFailure;
		}
		sessionCompleted(session, sessionCreation, producedOutput, failure);
	}

	/** The runtime of every agent of the network that has one. */
	private List<RuntimeAgentInfos> agentsRuntime() throws AgentException {
		final List<RuntimeAgentInfos> runtimes = new ArrayList<RuntimeAgentInfos>();
		if (network != null && network.getAgents() != null) {
			for (AgentNetworkParticipant participant : network.getAgents()) {
				final RuntimeAgentInfos runtime = agentsDao.findAgentByCode(participant.getNetworkAgentName());
				if (runtime != null) {
					runtimes.add(runtime);
				}
			}
		}
		return runtimes;
	}

	@Override
	public NetworkOfAgentsExecutionResult<OutputType> respawnNetworkExecution(IChatRequestContext chatRequestContext,
			GNetworkOfAgentsSuspension suspension, List<AgentSuspendedWaitingCondition<?, ?>> resolvedConditions,
			Map<String, Object> environment, GeboToolCalling toolCalling) throws AgentException, LLMConfigException {
		if (toolCalling == null)
			throw new AgentException(NO_TOOL_CALLING_FOR_THE_RUN);
		if (suspension == null)
			throw new AgentException(NO_SUSPENSION_TO_RESPAWN);
		if (network == null || network.getCode() == null || !network.getCode().equals(suspension.getNetworkCode()))
			throw new AgentException(SUSPENSION_OF_ANOTHER_NETWORK + suspension.getNetworkCode()
					+ ", this service runs: " + (network != null ? network.getCode() : null));
		if (suspension.getSharedState() == null)
			throw new AgentException(SUSPENSION_WITHOUT_SHARED_STATE + suspension.getCode());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin respawnNetworkExecution(...) network code:" + network.getCode() + " suspension:"
					+ suspension.getCode() + " suspended agents:" + suspension.getSuspendedAgents().size()
					+ " frozen deliveries:" + suspension.getFrozenDeliveries().size() + " resolved conditions:"
					+ (resolvedConditions != null ? resolvedConditions.size() : 0) + " with " + toolCalling);
		}
		// The collaboration goes on in the session of the cut, with every contribution
		// made so far and what the block kept of the environment, the caller's entries
		// on top; whoever builds sessions is not asked for this one, since it exists,
		// but is told when the run is over.
		final AgentsCollaborationSessionContext session = sessionOf(suspension.getSharedState());
		if (environment != null) {
			session.getEnvironment().putAll(environment);
		}
		final AgentsSessionCreationContext sessionCreation = sessionCreationContext(chatRequestContext,
				session.getEnvironment());
		restoreAgentsRuntime(suspension);
		final Map<String, List<AgentSuspendedWaitingCondition>> resolvedByAgent = resolvedByAgent(suspension,
				resolvedConditions);
		OutputType producedOutput = null;
		Throwable failure = null;
		final IGNetworkOfAgentsSuspensionHandler suspensionHandler = createSuspensionHandler();
		try {
			suspensionHandler.initialize(network, suspension.getNetworkInput(), chatRequestContext);
			suspensionHandler.respawnFrom(suspension, resolvedByAgent);
			producedOutput = respawnInSession(chatRequestContext, session, suspension, resolvedByAgent, toolCalling,
					suspensionHandler);
			if (suspensionHandler.isSuspensionState()) {
				return suspendedResult(session, suspensionHandler);
			}
			return NetworkOfAgentsExecutionResult.of(producedOutput);
		} catch (AgentException | LLMConfigException | RuntimeException | Error runFailure) {
			failure = runFailure;
			throw runFailure;
		} finally {
			runOver(suspensionHandler, session, sessionCreation, producedOutput, failure);
			if (failure == null) {
				// the block is consumed by a respawn that ended or suspended again; one that
				// failed leaves it as it was, to be respawned again
				markRespawned(suspension, suspensionHandler.suspensionId());
			}
		}
	}

	/**
	 * The respawned run itself, in the session of the cut. The suspended agents
	 * whose conditions are resolved are re-executed first, each once, with the message it
	 * was executing when it asked (the re-execution is the same act of the agent,
	 * not one more of its count); what they produce joins what the run had not
	 * delivered, in its delivery order, and the delivery goes on as in a run. The
	 * suspended agents whose conditions are not resolved are then suspended again in
	 * this run's handler, with the turn each was executing, so the run suspends
	 * again for them, however far the others went.
	 */
	private OutputType respawnInSession(IChatRequestContext chatRequestContext,
			AgentsCollaborationSessionContext session, GNetworkOfAgentsSuspension suspension,
			Map<String, List<AgentSuspendedWaitingCondition>> resolvedByAgent, GeboToolCalling toolCalling,
			IGNetworkOfAgentsSuspensionHandler suspensionHandler) throws AgentException, LLMConfigException {
		final List<AgentsExchangeMessage<?>> reExecuted = new ArrayList<AgentsExchangeMessage<?>>();
		final List<SuspendedAgentState> stillWaiting = new ArrayList<SuspendedAgentState>();
		for (SuspendedAgentState suspended : suspension.getSuspendedAgents()) {
			final String name = nameOf(suspended);
			if (name != null && resolvedByAgent.containsKey(name)) {
				if (suspended.getInputMessage() == null)
					throw new AgentException(SUSPENDED_AGENT_WITHOUT_TURN + name);
				final RuntimeAgentInfos runtime = agentsDao.findAgentByCode(name);
				if (runtime == null)
					throw new AgentException(NO_RUNTIME_ALLOCATED_FOR_AGENT + name);
				if (runtime.getInvocations().get() > 0)
					runtime.getInvocations().decrementAndGet();
				keepSolvedConditions(session, runtime, name, resolvedByAgent.get(name));
				reExecuted.add(suspended.getInputMessage().toMessage());
			} else {
				stillWaiting.add(suspended);
			}
		}
		final TreeMap<Integer, List<AgentsExchangeMessage<?>>> frozenOrder = new TreeMap<Integer, List<AgentsExchangeMessage<?>>>();
		int frozen = 0;
		for (FrozenDeliveries deliveries : suspension.getFrozenDeliveries()) {
			for (ExchangeMessageRecord record : deliveries.getMessages()) {
				final AgentsExchangeMessage<Object> message = record.toMessage();
				frozenOrder.computeIfAbsent(message.getExecutionOrder(), k -> new ArrayList<>()).add(message);
				frozen++;
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Respawning network:" + network.getCode() + " in session:" + session.getId() + ": "
					+ reExecuted.size() + " agent(s) re-executed, " + frozen + " frozen message(s) delivered, "
					+ stillWaiting.size() + " agent(s) still waiting");
		}
		CallsResult<OutputType> iterationResult = new CallsResult<OutputType>(frozenOrder, null);
		try {
			if (!reExecuted.isEmpty()) {
				final CallsResult<OutputType> reExecution = executeNetworkLoopsGroup(chatRequestContext,
						notificationSink, agentsDao, session, reExecuted, outputType, runAs, toolCalling,
						suspensionHandler);
				iterationResult = join(iterationResult, reExecution);
			}
			iterationResult = deliver(chatRequestContext, session, iterationResult, toolCalling, suspensionHandler);
		} catch (LLMConfigException | AgentException | InterruptedException | ExecutionException e) {
			LOGGER.error(EXCEPTION_IN_AGENTS_NETWORK_EXECUTION, e);
			throw new AgentException(EXCEPTION_IN_AGENTS_NETWORK_EXECUTION, e);
		}
		for (SuspendedAgentState waiting : stillWaiting) {
			final String name = nameOf(waiting);
			final RuntimeAgentInfos runtime = name != null ? agentsDao.findAgentByCode(name) : null;
			final AgentNetworkParticipant participant = runtime != null ? runtime.getNetworkParticipantConfig()
					: waiting.getParticipant();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Agent:" + name + " is still waiting: the respawned run suspends again for it");
			}
			// an agent can ask with no condition: it suspends again all the same
			final List<AgentSuspendedWaitingCondition<?, ?>> waitedFor = new ArrayList<AgentSuspendedWaitingCondition<?, ?>>();
			for (WaitingCondition condition : waiting.getWaitingConditions()) {
				waitedFor.add(condition.toCondition());
			}
			if (waitedFor.isEmpty()) {
				waitedFor.add(null);
			}
			for (AgentSuspendedWaitingCondition<?, ?> condition : waitedFor) {
				suspensionHandler.enterSuspensionState(network,
						runtime != null ? runtime.getConfig() : waiting.getAgentConfig(), participant,
						runtime != null ? runtime.getAgentContext() : null, condition);
			}
			if (waiting.getInputMessage() != null) {
				suspensionHandler.setSuspendedAgentTurn(participant, waiting.getInputMessage().toMessage(),
						waiting.getContributionNr() != null ? waiting.getContributionNr() : 0);
			}
		}
		final boolean suspended = suspensionHandler.isSuspensionState();
		final OutputType producedOutput = !suspended && iterationResult != null ? iterationResult.getOutput()
				: null;
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<NETWORK_OUTPUT network=" + network.getCode() + " respawned=true>");
			LOGGER.trace(String.valueOf(producedOutput));
			LOGGER.trace("</NETWORK_OUTPUT>");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End respawnNetworkExecution(...) network code:" + network.getCode() + " produced output:"
					+ (producedOutput != null) + " suspended:" + suspended);
		}
		return producedOutput;
	}

	/**
	 * What an agent waited for outlives the turn it is told in: the agent keeps it in
	 * its private session, notes included, for its next turns, and the other agents
	 * read it in the shared session, without the notes, so that none asks for it again.
	 * The session of a respawn is rebuilt from the block, so one that fails adds
	 * nothing to the block it is tried again from.
	 */
	@SuppressWarnings("rawtypes")
	private void keepSolvedConditions(AgentsCollaborationSessionContext session, RuntimeAgentInfos runtime, String name,
			List<AgentSuspendedWaitingCondition> solved) {
		if (runtime.getAgentContext() != null) {
			runtime.getAgentContext().addSolvedConditions(solved);
		}
		for (AgentSuspendedWaitingCondition<?, ?> condition : solved) {
			final String told = ResolvedConditionText.renderForPeers(name, condition);
			if (told != null) {
				session.addContribution(AgentsExchangeMessage.of(session, name, null, told, MessageSemantic.RESPONSE),
						session.getAndIncrementContributionNr());
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agent:" + name + " keeps " + solved.size()
					+ " solved condition(s) in its private session, shared with the other agents");
		}
	}

	private static String nameOf(SuspendedAgentState suspended) {
		return suspended != null && suspended.getParticipant() != null
				? suspended.getParticipant().getNetworkAgentName()
				: null;
	}

	/**
	 * The resolved conditions by the name of the agent that waited for them, for the
	 * agents to re-execute: a resolved condition is matched to a saved one by its id,
	 * and one matching no saved condition is ignored, with a warning. An agent is
	 * re-executed once, so it goes on when at least one of its conditions is resolved
	 * and no mandatory one is left: those left without a value count as declined. With
	 * a mandatory one left it keeps waiting for all of them.
	 */
	private Map<String, List<AgentSuspendedWaitingCondition>> resolvedByAgent(GNetworkOfAgentsSuspension suspension,
			List<AgentSuspendedWaitingCondition<?, ?>> resolvedConditions) {
		final Map<String, AgentSuspendedWaitingCondition> resolvedById = new HashMap<String, AgentSuspendedWaitingCondition>();
		final Set<String> savedIds = new HashSet<String>();
		for (WaitingCondition saved : suspension.conditions()) {
			savedIds.add(saved.getSuspendedConditionId());
		}
		if (resolvedConditions != null) {
			for (AgentSuspendedWaitingCondition<?, ?> resolved : resolvedConditions) {
				if (resolved == null || !savedIds.contains(resolved.getSuspendedConditionId())) {
					LOGGER.warn("A resolved condition matches none the suspension:" + suspension.getCode()
							+ " waits for, ignored: " + (resolved != null ? resolved.getSuspendedConditionId() : null));
					continue;
				}
				resolvedById.put(resolved.getSuspendedConditionId(), resolved);
			}
		}
		final Map<String, List<AgentSuspendedWaitingCondition>> resolvedByAgent = new HashMap<String, List<AgentSuspendedWaitingCondition>>();
		for (SuspendedAgentState suspended : suspension.getSuspendedAgents()) {
			final List<AgentSuspendedWaitingCondition> ofTheAgent = new ArrayList<AgentSuspendedWaitingCondition>();
			boolean anyResolved = false;
			boolean mandatoryLeft = false;
			for (WaitingCondition saved : suspended.getWaitingConditions()) {
				final AgentSuspendedWaitingCondition resolved = resolvedById.get(saved.getSuspendedConditionId());
				anyResolved |= resolved != null;
				mandatoryLeft |= resolved == null && saved.isMandatory();
				ofTheAgent.add(resolved != null ? resolved : saved.toCondition());
			}
			if (nameOf(suspended) != null && anyResolved && !mandatoryLeft) {
				resolvedByAgent.put(nameOf(suspended), ofTheAgent);
			} else if (anyResolved && LOGGER.isDebugEnabled()) {
				LOGGER.debug("Agent:" + nameOf(suspended) + " keeps waiting: a mandatory condition of its is not resolved");
			}
		}
		return resolvedByAgent;
	}

	/** The session of the cut: its identity, every contribution made so far, its counter and its environment. */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private AgentsCollaborationSessionContext sessionOf(SharedState sharedState) {
		final List<AgentProducedSessionContribution> prior = new ArrayList<AgentProducedSessionContribution>();
		for (SharedContribution contribution : sharedState.getContributions()) {
			prior.add(new AgentProducedSessionContribution(contribution.getContributionUniqueNr(),
					contribution.getAgentName(), contribution.getData(), contribution.getStatusNotices()));
		}
		final AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext(
				sharedState.getSessionId(), prior, sharedState.getContributionCounter());
		if (sharedState.getEnvironment() != null) {
			session.getEnvironment().putAll(sharedState.getEnvironment());
		}
		return session;
	}

	/**
	 * Restores the runtime of every agent of the cut into the agents of this
	 * service: its counters, and its private state replayed turn by turn. The service
	 * must be created for the block, by the factory of its network: one whose agents
	 * already ran is refused, since restoring over their state would mix two runs,
	 * and a service streaming its output completed its sink with its run anyway.
	 * An agent of the cut this service has no runtime for is skipped, with a warning.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void restoreAgentsRuntime(GNetworkOfAgentsSuspension suspension) throws AgentException {
		for (RuntimeAgentInfos runtime : agentsRuntime()) {
			final boolean ran = runtime.getInvocations().get() > 0 || runtime.getTurnOfExecution() > 0
					|| (runtime.getAgentContext() != null && !runtime.getAgentContext().getInteractions().isEmpty());
			if (ran)
				throw new AgentException(
						SERVICE_ALREADY_RUN + runtime.getNetworkParticipantConfig().getNetworkAgentName());
		}
		for (AgentRuntimeState state : suspension.getAgentsRuntime()) {
			final RuntimeAgentInfos runtime = state.getNetworkAgentName() != null
					? agentsDao.findAgentByCode(state.getNetworkAgentName())
					: null;
			if (runtime == null) {
				LOGGER.warn("The suspension:" + suspension.getCode() + " keeps the runtime of agent:"
						+ state.getNetworkAgentName() + " which network:" + network.getCode() + " has no runtime for");
				continue;
			}
			runtime.getInvocations().set(state.getInvocations());
			runtime.setTurnOfExecution(state.getTurnOfExecution());
			runtime.setSessionSharedContributionsReadIndex(state.getSessionSharedContributionsReadIndex());
			final AgentPrivateState privateState = state.getPrivateState();
			final AgentPrivateSessionContext privateContext = runtime.getAgentContext();
			if (privateState != null && privateContext != null) {
				if (privateState.getPrivateContextId() != null) {
					privateContext.setId(privateState.getPrivateContextId());
				}
				privateContext.setCollaborationContextId(privateState.getCollaborationContextId());
				for (PrivateInteraction turn : privateState.getInteractions()) {
					privateContext.addInteraction(turn.getInputMessage() != null ? turn.getInputMessage().toMessage() : null,
							turn.getContributionCounter(), turn.getOutput());
				}
				final List<AgentSuspendedWaitingCondition> solved = new ArrayList<AgentSuspendedWaitingCondition>();
				for (WaitingCondition condition : privateState.getSolvedConditions()) {
					solved.add(condition.toCondition());
				}
				privateContext.addSolvedConditions(solved);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Restored agent:" + state.getNetworkAgentName() + " invocations:" + state.getInvocations()
						+ " turnOfExecution:" + state.getTurnOfExecution() + " private turns:"
						+ (privateState != null ? privateState.getInteractions().size() : 0));
			}
		}
	}

	/**
	 * Marks the block consumed by a respawn that ended or suspended again, with the
	 * block it suspended into when it did; what the repository throws is logged and
	 * leaves the outcome of the run unchanged.
	 */
	private void markRespawned(GNetworkOfAgentsSuspension suspension, String respawnedToSuspensionCode) {
		if (suspensions == null) {
			return;
		}
		suspension.setRespawnedAt(new Date());
		suspension.setRespawnedToSuspensionCode(respawnedToSuspensionCode);
		suspension.setDateModified(suspension.getRespawnedAt());
		try {
			suspensions.save(suspension);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Suspension:" + suspension.getCode() + " marked respawned"
						+ (respawnedToSuspensionCode != null ? " into suspension:" + respawnedToSuspensionCode : ""));
			}
		} catch (RuntimeException e) {
			LOGGER.error("The suspension:" + suspension.getCode() + " could not be marked respawned; the outcome of"
					+ " the run is unchanged", e);
		}
	}

	/**
	 * The run itself, in the session it collaborates in. The messages of the run are
	 * delivered by this loop alone, a level of the exchange at a time and a group of
	 * a level at a time: when an agent asked the handler to suspend the run, the loop
	 * sees it before the next delivery. The agents that were running have ended by
	 * then (a group is waited for whole) and their contributions are in the shared
	 * state; what they produced for other agents, and whatever else was still to be
	 * delivered, is frozen with the handler instead of delivered, and the run ends
	 * with no output. That is the cut of the Chandy-Lamport algorithm the handler
	 * describes, taken on the one thread that delivers.
	 */
	private OutputType executeNetworkInSession(IChatRequestContext chatRequestContext, InputType input,
			AgentsCollaborationSessionContext session, String inputNodeName, GeboToolCalling toolCalling,
			IGNetworkOfAgentsSuspensionHandler suspensionHandler) throws AgentException, LLMConfigException {
		final AgentsExchangeMessage<InputType> inputMessage = AgentsExchangeMessage.of(session, inputNodeName, input,
				MessageSemantic.EXECUTE_AND_SHARE_RESULT);
		final RuntimeAgentInfos inputRuntime = agentsDao.findAgentByCode(inputNodeName);
		if (inputRuntime == null)
			throw new AgentException(NO_RUNTIME_ALLOCATED_FOR_INPUT_NODE + inputNodeName);
		if (!inputRuntime.getService().getInputType().isAssignableFrom(input.getClass()))
			throw new AgentException(NETWORK_INPUT_NODE_DOES_NOT_SUPPORT_A_MATCHING_TYPE + input.getClass().getName());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(
					"Input node:" + inputNodeName + " bound to agent service id:" + inputRuntime.getService().getId()
							+ " accepting inputType:" + inputRuntime.getService().getInputType().getName());
		}

		CallsResult<OutputType> iterationResult = null;
		boolean dynamicExchange = false;
		try {
			iterationResult = executeNetworkLoops(chatRequestContext, notificationSink, agentsDao, session,
					inputRuntime, inputMessage, outputType, runAs, toolCalling, suspensionHandler);
			// A "dynamic exchange" means the input node actually dispatched at least one
			// message onward into the network. Captured before the delivery loop consumes
			// iterationResult, so a network that seeds but never propagates can be flagged.
			dynamicExchange = iterationResult != null && iterationResult.getDeliveryOrder() != null
					&& !iterationResult.getDeliveryOrder().isEmpty();
			iterationResult = deliver(chatRequestContext, session, iterationResult, toolCalling, suspensionHandler);
		} catch (LLMConfigException | AgentException | InterruptedException | ExecutionException e) {
			LOGGER.error(EXCEPTION_IN_AGENTS_NETWORK_EXECUTION, e);
			throw new AgentException(EXCEPTION_IN_AGENTS_NETWORK_EXECUTION, e);
		}
		final boolean suspended = suspensionHandler.isSuspensionState();
		// A suspended run has no output: what its output node produced so far, if
		// anything, is in the shared state the handler saves, and the respawn completes it.
		final OutputType producedOutput = !suspended && iterationResult != null ? iterationResult.getOutput()
				: null;
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<NETWORK_OUTPUT network=" + (network != null ? network.getCode() : null) + ">");
			LOGGER.trace(String.valueOf(producedOutput));
			LOGGER.trace("</NETWORK_OUTPUT>");
		}
		// If the network stopped without any dynamic message exchange between agents
		// and
		// produced no output, surface it with ERROR severity: the run terminated dead
		// (e.g. the input node emitted nothing to route), which the user must be told.
		if (!dynamicExchange && producedOutput == null && !suspended) {
			notificationSink.next("Agents network '" + (network != null ? network.getCode() : null)
					+ "' stopped without any dynamic message exchange between agents", NotificationType.ERROR);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End executeNetwork(...) network code:" + (network != null ? network.getCode() : null)
					+ " produced output:" + (producedOutput != null) + " dynamicExchange:" + dynamicExchange
					+ " suspended:" + suspended);
		}
		return producedOutput;
	}

	/**
	 * Freezes the messages the run was still to deliver when it was suspended, each
	 * with the agent that produced it, for the respawn to deliver them.
	 */
	private void freezeUndelivered(List<List<AgentsExchangeMessage<?>>> groups,
			IGNetworkOfAgentsSuspensionHandler suspensionHandler) throws AgentException {
		final Map<String, List<AgentsExchangeMessage>> byProducer = new LinkedHashMap<>();
		for (List<AgentsExchangeMessage<?>> group : groups) {
			for (AgentsExchangeMessage<?> msg : group) {
				byProducer.computeIfAbsent(msg.getFromAgent(), k -> new ArrayList<>()).add(msg);
			}
		}
		for (Map.Entry<String, List<AgentsExchangeMessage>> entry : byProducer.entrySet()) {
			final RuntimeAgentInfos producer = agentsDao.findAgentByCode(entry.getKey());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Freezing " + entry.getValue().size() + " undelivered message(s) produced by agent:"
						+ entry.getKey() + " of the suspended network:" + (network != null ? network.getCode() : null));
			}
			suspensionHandler.setSuspendingMessagesNotYetDelivered(network, producer != null ? producer.getConfig() : null,
					producer != null ? producer.getNetworkParticipantConfig() : null,
					producer != null ? producer.getAgentContext() : null, entry.getValue());
		}
	}

	/**
	 * Delivers what the agents produce, a level of the exchange at a time and a
	 * group of a level at a time, from the given result until nothing is left to
	 * deliver or an agent asked to suspend the run, in which case what is left is
	 * frozen with the handler (see {@link #executeNetworkInSession}).
	 */
	private CallsResult<OutputType> deliver(IChatRequestContext chatRequestContext,
			AgentsCollaborationSessionContext session, CallsResult<OutputType> iterationResult,
			GeboToolCalling toolCalling, IGNetworkOfAgentsSuspensionHandler suspensionHandler)
			throws LLMConfigException, AgentException, InterruptedException, ExecutionException {
		int level = 0;
		while (iterationResult != null && iterationResult.getDeliveryOrder() != null
				&& !iterationResult.getDeliveryOrder().isEmpty()) {
			level++;
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Processing delivery level " + level + " with " + iterationResult.getDeliveryOrder().size()
						+ " group(s)");
			}
			CallsResult<OutputType> levelResult = new CallsResult<OutputType>(new TreeMap<>(),
					iterationResult.getOutput());
			final List<List<AgentsExchangeMessage<?>>> groups = new ArrayList<>(
					iterationResult.getDeliveryOrder().values());
			for (int g = 0; g < groups.size(); g++) {
				if (suspensionHandler.isSuspensionState()) {
					// An agent asked to suspend the run: this group and the ones after it are
					// frozen undelivered. What the groups already run in this level produced
					// is in levelResult, the next level: the next turn of this loop freezes it.
					freezeUndelivered(groups.subList(g, groups.size()), suspensionHandler);
					break;
				}
				CallsResult<OutputType> rowResult = executeNetworkLoopsGroup(chatRequestContext, notificationSink,
						agentsDao, session, groups.get(g), outputType, runAs, toolCalling, suspensionHandler);
				levelResult = join(levelResult, rowResult);
			}
			iterationResult = levelResult;
		}
		return iterationResult;
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
			ReactiveIdentityUtil runAs, GeboToolCalling toolCalling,
			IGNetworkOfAgentsSuspensionHandler suspensionHandler)
			throws LLMConfigException, AgentException, InterruptedException, ExecutionException {
		OutputType output = null;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin executeNetworkLoops(...) targetAgent:"
					+ (inputRuntime != null ? inputRuntime.getService().getId() : null) + " messageSemantic:"
					+ (inputMessage != null ? inputMessage.getMessageSemantic() : null));
		}
		final TreeMap<Integer, List<AgentsExchangeMessage<?>>> deliveryOrder = new TreeMap<>();
		if (checkContinueLoop(network, agentsDao, inputRuntime)) {
			final int contributionNr = session.getAndIncrementContributionNr();
			inputRuntime.setTurnOfExecution(contributionNr);
			final int invocation = inputRuntime.getInvocations().incrementAndGet();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Agent:" + inputRuntime.getNetworkParticipantConfig().getNetworkAgentName()
						+ " dispatched for the " + invocation + ". time");
			}
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
				messages = inputRuntime.getService().onMessage(chatRequestContext, inputRuntime.getConfig(),
						inputMessage, contributionNr, network, inputRuntime.getNetworkParticipantConfig(),
						notificationSink, session, inputRuntime.getAgentContext(), runAs, agentsDao, toolCalling,
						suspensionHandler);
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
				// A downstream agent failure is isolated so the network stays resilient:
				// notify,
				// record an empty turn so the private session context stays consistent, and let
				// the rest of the network proceed. The failing agent's turn has already been
				// advanced above, so loop termination still progresses.
				//
				// Reported as ERROR, not DEBUG. Resilient does not mean silent: the caller
				// still
				// gets an answer built without whatever this agent was supposed to contribute,
				// and when the agent that failed is the router that answer is empty. Sending it
				// at DEBUG put the one account of the failure in the same channel as "is
				// coordinating...", so a provider rejecting every request looked exactly like a
				// model with nothing to say.
				notificationSink.notifyFailure(
						"Agent: " + participantName + " failed, skipped: " + rootCauseMessage(failure), failure);
				LOGGER.error("Agent " + participantName + " failed; continuing network", failure);
				addToEmptyReturn(inputMessage, contributionNr, inputRuntime.getAgentContext());
				return new CallsResult<OutputType>(new TreeMap<>(), output);
			}
			notificationSink.next("Agent: " + participantName + " has finished", NotificationType.DEBUG);
			if (suspensionHandler.isSuspensionState()) {
				// the handler keeps the turn of the agents that asked to suspend the run, to
				// re-execute them with it; a turn of any other agent is ignored
				suspensionHandler.setSuspendedAgentTurn(inputRuntime.getNetworkParticipantConfig(), inputMessage,
						contributionNr);
			}
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
						LOGGER.debug(
								"Agent:" + participantName + " emitted message to:" + msg.getToAgent() + " semantic:"
										+ msg.getMessageSemantic() + " executionOrder:" + msg.getExecutionOrder());
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
			Class<OutputType> outputType, ReactiveIdentityUtil runAs, GeboToolCalling toolCalling,
			IGNetworkOfAgentsSuspensionHandler suspensionHandler)
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
					outputType, runAs, toolCalling, suspensionHandler);
		} else {
			List<CompletableFuture<CallsResult<OutputType>>> completables = new ArrayList<>();
			for (AgentsExchangeMessage<?> msg : executionGroup) {
				RuntimeAgentInfos agent = agentsDao.findAgentByCode(msg.getToAgent());
				Supplier<CallsResult<OutputType>> supplier = () -> {
					try {
						if (runAs != null)
							return runAs.doRunAsWithReturnAndException(() -> {
								return executeNetworkLoops(chatRequestContext, notificationSink, agentsDao, session,
										agent, msg, outputType, runAs, toolCalling, suspensionHandler);
							});
						else
							return executeNetworkLoops(chatRequestContext, notificationSink, agentsDao, session, agent,
									msg, outputType, runAs, toolCalling, suspensionHandler);
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

	/**
	 * Whether the network goes on dispatching to the given agent. When the network
	 * flags a controller, its {@link GAgentsNetwork#getMaxLoopIteration() limit} is
	 * the number of times the controller may act: the controller is dispatched that
	 * many times, plus once more to receive what its last cycle produced (the
	 * answer of the output node comes back to it); the other participants act only
	 * on what the controller sends, so they need no count of their own. A network
	 * without a controller (saved before the controller existed) keeps the former
	 * counting: the limit bounds the dispatches of the whole exchange.
	 */
	private boolean checkContinueLoop(GAgentsNetwork network, IGAgentsNetworkRuntimeDao agentsDao,
			RuntimeAgentInfos target) throws AgentException {
		final boolean hasController = network.getAgents().stream().anyMatch(AgentNetworkParticipant::isController);
		if (hasController) {
			final AgentNetworkParticipant persona = target.getNetworkParticipantConfig();
			if (!persona.isController()) {
				if (LOGGER.isTraceEnabled()) {
					LOGGER.trace("checkContinueLoop(...) agent:" + persona.getNetworkAgentName()
							+ " is not the controller: dispatched");
				}
				return true;
			}
			final int done = target.getInvocations().get();
			final boolean continueLoop = done <= network.getMaxLoopIteration();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkContinueLoop(...) controller:" + persona.getNetworkAgentName() + " dispatched "
						+ done + " time(s), maxLoopIteration:" + network.getMaxLoopIteration() + " continue:"
						+ continueLoop);
			}
			return continueLoop;
		}
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
