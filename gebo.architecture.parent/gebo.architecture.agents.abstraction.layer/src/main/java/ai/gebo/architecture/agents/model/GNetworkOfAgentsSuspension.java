/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.model.base.GBaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The cut of a suspended run of a network of agents, as the suspension handler
 * was told it (see
 * {@link ai.gebo.architecture.agents.services.IGNetworkOfAgentsSuspensionHandler}):
 * everything that passed through its parameters, kept so that the run can be
 * respawned as it was. One document per suspended run, identified by its code,
 * the suspension's own id, consolidated while the cut is taken and written as
 * one block when the network declares the run finished. It holds:
 * <ul>
 * <li>the network as it was run, a copy and not a reference, since the
 * configuration may change before the respawn, and the input it was given,
 * which is all there is of the run when its first agent asks to suspend it;</li>
 * <li>the agents that asked to suspend, each with its configuration, its place
 * in the network, its private state, the condition it waits for and the turn it
 * was executing, which the respawn re-executes;</li>
 * <li>the runtime of every agent of the network, its counters and its private
 * state, restored by the respawn before anything is delivered;</li>
 * <li>the messages the run had not delivered yet, each group with the agent
 * that produced them, its place in the network and its private state;</li>
 * <li>the shared state of the collaboration: every contribution made so far,
 * the next contribution number and the environment of the run.</li>
 * </ul>
 * The payloads of the messages, the outputs of the agents' turns and the
 * contributions are kept as they are, with the name of their class beside them,
 * since the network is generic over them.
 */
@Document
@Data
@EqualsAndHashCode(callSuper = true)
public class GNetworkOfAgentsSuspension extends GBaseObject {
	private static final Logger LOGGER = LoggerFactory.getLogger(GNetworkOfAgentsSuspension.class);
	/** The code of the network that was run. */
	@HashIndexed
	private String networkCode = null;
	/** The id of the collaboration session the run collaborated in. */
	@HashIndexed
	private String collaborationSessionId = null;
	/**
	 * The conversation the run served and the request it answered, from the chat
	 * request context of the run; null for a run outside a chat. The open block of a
	 * conversation is the one with its code that was neither respawned nor dropped.
	 */
	@HashIndexed
	private String conversationCode = null;
	private String requestId = null;
	/** When the suspension began: the first agent asked for it. */
	private Date suspendedAt = null;
	/** When the network declared the run finished, the cut complete and written. */
	private Date finishedAt = null;
	/** The network as it was run. */
	private GAgentsNetwork network = null;
	/** The input the network was given (the input of executeNetwork), as it is. */
	private Object networkInput = null;
	private String networkInputClass = null;
	/** The agents that asked to suspend the run, one entry each, in the order they asked. */
	private List<SuspendedAgentState> suspendedAgents = new ArrayList<SuspendedAgentState>();
	/** The messages the run had not delivered, by the agent that produced them. */
	private List<FrozenDeliveries> frozenDeliveries = new ArrayList<FrozenDeliveries>();
	/** The runtime of every agent of the network when the run was suspended. */
	private List<AgentRuntimeState> agentsRuntime = new ArrayList<AgentRuntimeState>();
	/** The shared state of the collaboration, saved last. */
	private SharedState sharedState = null;
	/** The block this run was respawned from, when it is a suspension of a respawned run. */
	private String respawnedFromSuspensionCode = null;
	/** When this block was respawned, and the block the respawned run suspended into, if it did. */
	private Date respawnedAt = null;
	private String respawnedToSuspensionCode = null;
	/**
	 * When this block was dropped unanswered: the conversation went on with a normal
	 * interaction, which it may when no condition is mandatory, or another run of it
	 * suspended, a conversation holding one open block at most.
	 */
	private Date droppedAt = null;

	/** Whether the block is still to be answered: neither respawned nor dropped. */
	public boolean isOpen() {
		return respawnedAt == null && droppedAt == null;
	}

	/** Whether a condition of the block is mandatory: the conversation waits for the block. */
	public boolean isMandatory() {
		for (WaitingCondition condition : conditions()) {
			if (condition.isMandatory()) {
				return true;
			}
		}
		return false;
	}

	/** Every condition of the block, agent by agent, in the order they were asked. */
	public List<WaitingCondition> conditions() {
		final List<WaitingCondition> conditions = new ArrayList<WaitingCondition>();
		for (SuspendedAgentState suspended : suspendedAgents) {
			conditions.addAll(suspended.getWaitingConditions());
		}
		return conditions;
	}

	/**
	 * An agent that asked to suspend the run, and everything it waits for: one entry
	 * however many conditions it declared, since it is re-executed once, on the
	 * message it was executing, told every answer.
	 */
	@Data
	@NoArgsConstructor
	public static class SuspendedAgentState {
		private String agentConfigCode = null;
		/** The agent's configuration as it was run. */
		private GAgentConfig agentConfig = null;
		/** The agent's place in the network: its name, who it talks to. */
		private AgentNetworkParticipant participant = null;
		private AgentPrivateState privateState = null;
		private List<WaitingCondition> waitingConditions = new ArrayList<WaitingCondition>();
		/** The message the agent was executing when it asked, and the number of that turn. */
		private ExchangeMessageRecord inputMessage = null;
		private Integer contributionNr = null;

		public static SuspendedAgentState of(GAgentConfig agentConfig, AgentNetworkParticipant participant,
				AgentPrivateSessionContext<?, ?> privateContext, AgentSuspendedWaitingCondition<?, ?> waitForCondition) {
			final SuspendedAgentState state = new SuspendedAgentState();
			state.setAgentConfigCode(agentConfig != null ? agentConfig.getCode() : null);
			state.setAgentConfig(agentConfig);
			state.setParticipant(participant);
			state.setPrivateState(AgentPrivateState.of(privateContext));
			if (waitForCondition != null) {
				state.getWaitingConditions().add(WaitingCondition.of(waitForCondition));
			}
			return state;
		}
	}

	/** The messages an agent produced that the run had not delivered. */
	@Data
	@NoArgsConstructor
	public static class FrozenDeliveries {
		private String agentConfigCode = null;
		/** The producing agent's configuration as it was run. */
		private GAgentConfig agentConfig = null;
		/** The producing agent's place in the network. */
		private AgentNetworkParticipant participant = null;
		/** The producing agent's private state when the run was suspended. */
		private AgentPrivateState privateState = null;
		private List<ExchangeMessageRecord> messages = new ArrayList<ExchangeMessageRecord>();

		public static FrozenDeliveries of(GAgentConfig agentConfig, AgentNetworkParticipant participant,
				AgentPrivateSessionContext<?, ?> privateContext, List<? extends AgentsExchangeMessage<?>> messages) {
			final FrozenDeliveries frozen = new FrozenDeliveries();
			frozen.setAgentConfigCode(agentConfig != null ? agentConfig.getCode() : null);
			frozen.setAgentConfig(agentConfig);
			frozen.setParticipant(participant);
			frozen.setPrivateState(AgentPrivateState.of(privateContext));
			if (messages != null) {
				for (AgentsExchangeMessage<?> message : messages) {
					if (message != null) {
						frozen.getMessages().add(ExchangeMessageRecord.of(message));
					}
				}
			}
			return frozen;
		}
	}

	/** The runtime of an agent of the network (see {@link RuntimeAgentInfos}). */
	@Data
	@NoArgsConstructor
	public static class AgentRuntimeState {
		private String networkAgentName = null;
		private String agentConfigCode = null;
		/** How many times the network dispatched a message to the agent. */
		private int invocations = 0;
		private int turnOfExecution = 0;
		private int sessionSharedContributionsReadIndex = 0;
		private AgentPrivateState privateState = null;

		public static AgentRuntimeState of(RuntimeAgentInfos runtime) {
			if (runtime == null) {
				return null;
			}
			final AgentRuntimeState state = new AgentRuntimeState();
			state.setNetworkAgentName(runtime.getNetworkParticipantConfig() != null
					? runtime.getNetworkParticipantConfig().getNetworkAgentName()
					: null);
			state.setAgentConfigCode(runtime.getConfig() != null ? runtime.getConfig().getCode() : null);
			state.setInvocations(runtime.getInvocations() != null ? runtime.getInvocations().get() : 0);
			state.setTurnOfExecution(runtime.getTurnOfExecution());
			state.setSessionSharedContributionsReadIndex(runtime.getSessionSharedContributionsReadIndex());
			state.setPrivateState(AgentPrivateState.of(runtime.getAgentContext()));
			return state;
		}
	}

	/**
	 * The private state of an agent (see {@link AgentPrivateSessionContext}): its
	 * turns so far, each with the message it was given and what it produced.
	 */
	@Data
	@NoArgsConstructor
	public static class AgentPrivateState {
		private String privateContextId = null;
		private String collaborationContextId = null;
		private List<PrivateInteraction> interactions = new ArrayList<PrivateInteraction>();

		public static AgentPrivateState of(AgentPrivateSessionContext<?, ?> privateContext) {
			if (privateContext == null) {
				return null;
			}
			final AgentPrivateState state = new AgentPrivateState();
			state.setPrivateContextId(privateContext.getId());
			state.setCollaborationContextId(privateContext.getCollaborationContextId());
			final List<?> interactions = privateContext.getInteractions();
			if (interactions != null) {
				for (Object interaction : List.copyOf(interactions)) {
					if (interaction instanceof AgentPrivateSessionContext<?, ?>.AgentInteraction turn) {
						state.getInteractions().add(PrivateInteraction.of(turn));
					}
				}
			}
			return state;
		}
	}

	/** One turn of an agent: the message it was given and what it produced. */
	@Data
	@NoArgsConstructor
	public static class PrivateInteraction {
		private ExchangeMessageRecord inputMessage = null;
		private int contributionCounter = 0;
		private Object output = null;
		private String outputClass = null;

		public static PrivateInteraction of(AgentPrivateSessionContext<?, ?>.AgentInteraction turn) {
			final PrivateInteraction interaction = new PrivateInteraction();
			interaction.setInputMessage(ExchangeMessageRecord.of(turn.getInputMessage()));
			interaction.setContributionCounter(turn.getContributionCounter());
			interaction.setOutput(turn.getOutput());
			interaction.setOutputClass(classNameOf(turn.getOutput()));
			return interaction;
		}
	}

	/** A message of the exchange (see {@link AgentsExchangeMessage}), its payload as it is. */
	@Data
	@NoArgsConstructor
	public static class ExchangeMessageRecord {
		private String messageId = null;
		private String collaborationContextId = null;
		private MessageSemantic messageSemantic = null;
		private String fromAgent = null;
		private GAgentRole fromAgentRole = null;
		private String toAgent = null;
		private Object payload = null;
		private String payloadClass = null;
		private int executionOrder = 0;
		private List<String> statusNotices = null;

		public static ExchangeMessageRecord of(AgentsExchangeMessage<?> message) {
			if (message == null) {
				return null;
			}
			final ExchangeMessageRecord record = new ExchangeMessageRecord();
			record.setMessageId(message.getId());
			record.setCollaborationContextId(message.getCollaborationContextId());
			record.setMessageSemantic(message.getMessageSemantic());
			record.setFromAgent(message.getFromAgent());
			record.setFromAgentRole(message.getFromAgentRole());
			record.setToAgent(message.getToAgent());
			record.setPayload(message.getPayload());
			record.setPayloadClass(classNameOf(message.getPayload()));
			record.setExecutionOrder(message.getExecutionOrder());
			record.setStatusNotices(message.getStatusNotices() != null ? List.copyOf(message.getStatusNotices()) : null);
			return record;
		}

		/**
		 * The message this record was taken from, as a new message of the exchange: the
		 * same but for its id, which a message gives itself.
		 */
		public AgentsExchangeMessage<Object> toMessage() {
			return new AgentsExchangeMessage<Object>(collaborationContextId, messageSemantic, fromAgent, fromAgentRole,
					toAgent, payload, executionOrder, statusNotices != null ? List.copyOf(statusNotices) : null);
		}
	}

	/**
	 * What a suspended agent waits for (see {@link AgentSuspendedWaitingCondition}):
	 * the type of the value it waits for and the type of what the model chose are
	 * kept by name.
	 */
	@Data
	@NoArgsConstructor
	public static class WaitingCondition {
		private String suspendedConditionId = null;
		private String typeName = null;
		private String waitingConditionStatement = null;
		private String waitedConditionInstanceLLMExplanation = null;
		private Object instanceValue = null;
		private boolean mandatory = true;
		/** What the model chose when it decided to suspend, and its type by name. */
		private String optionsTypeName = null;
		private Object llmChosenOptions = null;
		/** What the agent wrote to itself when it stopped. */
		private String notesForLater = null;

		public static WaitingCondition of(AgentSuspendedWaitingCondition<?, ?> condition) {
			if (condition == null) {
				return null;
			}
			final WaitingCondition waiting = new WaitingCondition();
			waiting.setSuspendedConditionId(condition.getSuspendedConditionId());
			waiting.setTypeName(condition.getType() != null ? condition.getType().getName() : null);
			waiting.setWaitingConditionStatement(condition.getWaitingConditionStatement());
			waiting.setWaitedConditionInstanceLLMExplanation(condition.getWaitedConditionInstanceLLMExplanation());
			waiting.setInstanceValue(condition.getInstanceValue());
			waiting.setMandatory(condition.isMandatory());
			waiting.setOptionsTypeName(condition.getOptionsType() != null ? condition.getOptionsType().getName() : null);
			waiting.setLlmChosenOptions(condition.getLlmChosenOptions());
			waiting.setNotesForLater(condition.getNotesForLater());
			return waiting;
		}

		/**
		 * The condition this record was taken from, with the saved id, so that it is
		 * matched to the one of the block when resolved: the same but for its types,
		 * {@code Object} when a class named is not on the classpath.
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public AgentSuspendedWaitingCondition<?, ?> toCondition() {
			final AgentSuspendedWaitingCondition condition = new AgentSuspendedWaitingCondition(classNamed(typeName),
					waitingConditionStatement, waitedConditionInstanceLLMExplanation, instanceValue, mandatory,
					optionsTypeName != null ? classNamed(optionsTypeName) : null, llmChosenOptions);
			condition.setNotesForLater(notesForLater);
			if (suspendedConditionId != null) {
				condition.setSuspendedConditionId(suspendedConditionId);
			}
			return condition;
		}

		private static Class<?> classNamed(String name) {
			if (name == null) {
				return Object.class;
			}
			try {
				return Class.forName(name);
			} catch (ClassNotFoundException notOnTheClasspath) {
				return Object.class;
			}
		}
	}

	/**
	 * The shared state of the collaboration (see
	 * {@link AgentsCollaborationSessionContext}): what every agent reads. Of the
	 * environment only the entries that can be kept are kept: texts, numbers,
	 * booleans, dates, enums and collections or maps of them. The rest, the runtime
	 * objects a caller put there for its agents, is what the caller gives again when
	 * it respawns the run.
	 */
	@Data
	@NoArgsConstructor
	public static class SharedState {
		private String sessionId = null;
		/** The next contribution number the session would hand out. */
		private int contributionCounter = 0;
		private List<SharedContribution> contributions = new ArrayList<SharedContribution>();
		private Map<String, Object> environment = new HashMap<String, Object>();

		public static SharedState of(AgentsCollaborationSessionContext session) {
			if (session == null) {
				return null;
			}
			final SharedState state = new SharedState();
			state.setSessionId(session.getId());
			state.setContributionCounter(session.getContributionCounter());
			for (AgentProducedSessionContribution<?> contribution : session.getSampledContributions()) {
				if (contribution != null) {
					state.getContributions().add(SharedContribution.of(contribution));
				}
			}
			if (session.getEnvironment() != null) {
				for (Map.Entry<String, Object> entry : session.getEnvironment().entrySet()) {
					if (isPersistable(entry.getValue(), 0)) {
						state.getEnvironment().put(entry.getKey(), entry.getValue());
					} else if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Environment entry:" + entry.getKey() + " of class "
								+ (entry.getValue() != null ? entry.getValue().getClass().getName() : null)
								+ " is not kept in the block: the caller gives it again on the respawn");
					}
				}
			}
			return state;
		}

		/** Whether a value is a text, a number, a boolean, a date, an enum, or a collection or map of them. */
		static boolean isPersistable(Object value, int depth) {
			if (value == null || value instanceof String || value instanceof Number || value instanceof Boolean
					|| value instanceof Character || value instanceof Date || value instanceof Enum<?>) {
				return true;
			}
			if (depth > 8) {
				return false;
			}
			if (value instanceof Collection<?> collection) {
				for (Object item : collection) {
					if (!isPersistable(item, depth + 1)) {
						return false;
					}
				}
				return true;
			}
			if (value instanceof Map<?, ?> map) {
				for (Map.Entry<?, ?> item : map.entrySet()) {
					if (!(item.getKey() instanceof String) || !isPersistable(item.getValue(), depth + 1)) {
						return false;
					}
				}
				return true;
			}
			if (value.getClass().isArray()) {
				return value instanceof Object[] items ? isPersistable(java.util.Arrays.asList(items), depth + 1) : true;
			}
			return false;
		}
	}

	/** A contribution of an agent to the shared state (see {@link AgentProducedSessionContribution}). */
	@Data
	@NoArgsConstructor
	public static class SharedContribution {
		private int contributionUniqueNr = 0;
		private String agentName = null;
		private Object data = null;
		private String dataClass = null;
		private List<String> statusNotices = null;

		public static SharedContribution of(AgentProducedSessionContribution<?> contribution) {
			final SharedContribution shared = new SharedContribution();
			shared.setContributionUniqueNr(contribution.getContributionUniqueNr());
			shared.setAgentName(contribution.getAgentName());
			shared.setData(contribution.getData());
			shared.setDataClass(classNameOf(contribution.getData()));
			shared.setStatusNotices(
					contribution.getStatusNotices() != null ? List.copyOf(contribution.getStatusNotices()) : null);
			return shared;
		}
	}

	static String classNameOf(Object value) {
		return value != null ? value.getClass().getName() : null;
	}
}
