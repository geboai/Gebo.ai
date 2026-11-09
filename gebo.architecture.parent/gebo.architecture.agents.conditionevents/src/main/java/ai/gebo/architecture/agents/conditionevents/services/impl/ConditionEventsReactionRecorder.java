/*
 * Copyright (c) 2025+ Gebo.ai
 * Licensed under the Mozilla Public License 2.0 (MPL 2.0) with Data Protection Clauses.
 * See LICENSE file in the project root for full license information.
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 */
package ai.gebo.architecture.agents.conditionevents.services.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.conditionevents.ConditionCorrelationId;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEvent;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventAck;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventCaller;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventStatus;
import ai.gebo.architecture.agents.conditionevents.model.ConditionSources;
import ai.gebo.architecture.agents.conditionevents.model.GConditionEvent;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventsService;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventsStore;
import ai.gebo.architecture.agents.conditionevents.services.impl.ConditionEventDispatcher.RequestClaim;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.WaitingCondition;
import ai.gebo.architecture.agents.model.UnansweredBecause;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedState;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedStateReaction;
import ai.gebo.llms.chat.pipelines.service.IConditionReactionRecorder;
import ai.gebo.llms.chat.pipelines.service.RecordedConditionReaction;
import ai.gebo.llms.chat.pipelines.service.RecordedConditionReaction.Answer;
import ai.gebo.llms.chat.pipelines.service.RecordedConditionReaction.Next;
import ai.gebo.llms.chat.pipelines.service.SolvedCondition;
import ai.gebo.llms.chat.pipelines.service.impl.GAbstractConditionTransportTranslator;
import ai.gebo.llms.chat.pipelines.service.impl.NetworkOfAgentsSuspensionTransport;

/**
 * The chat's answers as condition events: each one is delivered as any source
 * delivers its event, by the conversation's user on the {@code CHAT} channel,
 * so it is checked by the condition's translator and kept next to the answers of
 * the other sources. Once every mandatory condition of the block has an answer
 * the chat request claims the respawn and runs it, with the answers of every
 * source and the optional conditions none answered declined; the dispatcher
 * stands off while it holds the claim, and reads how it went once it ends.
 */
@Service
public class ConditionEventsReactionRecorder implements IConditionReactionRecorder {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConditionEventsReactionRecorder.class);
	/** The system the chat's answers come from. */
	public static final String SOURCE_SYSTEM = ConditionSources.CHAT_SYSTEM;
	static final String NOT_NAMED = "The answer names no condition";
	static final String ANSWERED_TWICE = "The reaction answers this condition more than once: the first answer counts";
	/** The states of an event that solves its condition in the block it was matched to. */
	private static final Set<ConditionEventStatus> SOLVING = EnumSet.of(ConditionEventStatus.ACCEPTED,
			ConditionEventStatus.DISPATCHED, ConditionEventStatus.APPLIED);
	private final IConditionEventsService events;
	private final IConditionEventsStore store;
	private final ConditionEventDispatcher dispatcher;
	private final NetworkOfAgentsSuspensionTransport transport;
	private final Executor conclusions;

	@Autowired
	public ConditionEventsReactionRecorder(IConditionEventsService events, IConditionEventsStore store,
			ConditionEventDispatcher dispatcher, NetworkOfAgentsSuspensionTransport transport,
			ObjectProvider<IGeboThreadManager> threads) {
		this(events, store, dispatcher, transport, ConditionEventsServiceImpl.lazyExecutorOf(threads));
	}

	ConditionEventsReactionRecorder(IConditionEventsService events, IConditionEventsStore store,
			ConditionEventDispatcher dispatcher, NetworkOfAgentsSuspensionTransport transport, Executor conclusions) {
		this.events = events;
		this.store = store;
		this.dispatcher = dispatcher;
		this.transport = transport;
		this.conclusions = conclusions;
	}

	@Override
	public boolean holdsAnswersFor(GNetworkOfAgentsSuspension block) {
		if (block == null || block.getConversationCode() == null) {
			return false;
		}
		return store.liveEventsOf(block.getConversationCode()).stream()
				.anyMatch(event -> event.getStatus() == ConditionEventStatus.ACCEPTED
						&& Objects.equals(block.getCode(), event.getBlockCode()));
	}

	@Override
	public RecordedConditionReaction record(GNetworkOfAgentsSuspension block,
			NetworkOfAgentsSuspendedStateReaction reaction, String username, String requestId) {
		final List<Answer> answers = new ArrayList<Answer>();
		final Set<String> answered = new HashSet<String>();
		final ConditionEventCaller caller = ConditionEventCaller.of(username, Set.of(), ConditionEventCaller.CHAT);
		if (reaction != null && reaction.getReactions() != null) {
			for (AgentSuspendedWaitingConditionReactionDto answer : reaction.getReactions()) {
				final String conditionId = answer != null ? answer.getConditionId() : null;
				if (conditionId == null || conditionId.isBlank()) {
					answers.add(new Answer(null, RecordedConditionReaction.REFUSED, NOT_NAMED));
				} else if (!answered.add(conditionId)) {
					answers.add(new Answer(conditionId, RecordedConditionReaction.REFUSED, ANSWERED_TWICE));
				} else {
					// held: the request takes the block further itself, below
					final ConditionEventAck ack = events.deliverHeld(eventOf(block, answer, username, requestId),
							caller);
					answers.add(new Answer(conditionId,
							ack.getOutcome() != null ? ack.getOutcome().name() : RecordedConditionReaction.REFUSED,
							ack.getReason()));
				}
			}
		}
		return outcomeOf(block, answers, requestId);
	}

	@Override
	public void concluded(GNetworkOfAgentsSuspension block, String requestId) {
		if (block == null) {
			return;
		}
		try {
			conclusions.execute(() -> {
				try {
					dispatcher.concludeRequest(block.getConversationCode(), block.getCode(), requestId);
				} catch (RuntimeException e) {
					LOGGER.warn("Cannot conclude the respawn of block:" + block.getCode() + " by request:" + requestId
							+ " (the sweeper takes it further once its lease is over): " + e.getMessage(), e);
				}
			});
		} catch (RuntimeException notTaken) {
			LOGGER.warn("Cannot conclude the respawn of block:" + block.getCode() + " by request:" + requestId
					+ " now (the sweeper takes it further once its lease is over): " + notTaken.getMessage());
		}
	}

	@Override
	public List<SolvedCondition> solvedConditionsOf(GNetworkOfAgentsSuspension block) {
		final List<SolvedCondition> solved = new ArrayList<SolvedCondition>();
		if (block == null) {
			return solved;
		}
		for (WaitingCondition condition : block.conditions()) {
			final GConditionEvent event = solvingEventIn(block, condition.getSuspendedConditionId());
			if (event != null) {
				solved.add(new SolvedCondition(event.getConditionId(), event.getSourceSystem(), event.getActor(),
						event.getAcceptedAt() != null ? event.getAcceptedAt() : event.getLastReceivedAt(),
						event.sourceOrKept()));
			}
		}
		return solved;
	}

	private static ConditionEvent eventOf(GNetworkOfAgentsSuspension block, AgentSuspendedWaitingConditionReactionDto answer,
			String username, String requestId) {
		final ConditionEvent event = new ConditionEvent();
		event.setSessionCode(block.getConversationCode());
		event.setConditionId(answer.getConditionId());
		event.setConditionType(answer.getConditionType());
		final LinkedHashMap<String, Object> attributes = answer.getAttributes() != null
				? new LinkedHashMap<String, Object>(answer.getAttributes())
				: new LinkedHashMap<String, Object>();
		// the conversation's own user may go on without the condition, mandatory or not:
		// asked as an attribute, kept as the platform's own mark, which no attribute may set
		if (GAbstractConditionTransportTranslator.isTrue(attributes.remove(GAbstractConditionTransportTranslator.GO_ON_WITHOUT))) {
			event.setUnanswered(UnansweredBecause.GONE_ON_WITHOUT);
		}
		event.setAttributes(attributes);
		event.setSourceSystem(SOURCE_SYSTEM);
		// the conversation's own user, verified on delivery as the account answering
		event.setSource(ConditionSources.forChatUser(username, null, block.getConversationCode()));
		// the same chat request sent again is the same event of its source
		event.setSourceEventId(requestId != null ? requestId + ":" + answer.getConditionId() : null);
		return event;
	}

	/**
	 * What the block still waits for once the answers are in: its mandatory
	 * conditions with no event solving them in this block. With none left and
	 * something solved, the respawn is claimed for the request.
	 */
	private RecordedConditionReaction outcomeOf(GNetworkOfAgentsSuspension block, List<Answer> answers,
			String requestId) {
		final List<String> waitingFor = new ArrayList<String>();
		boolean anySolved = false;
		for (WaitingCondition condition : block.conditions()) {
			final boolean solved = solvingEventIn(block, condition.getSuspendedConditionId()) != null;
			anySolved |= solved;
			if (condition.isMandatory() && !solved) {
				waitingFor.add(condition.getWaitingConditionStatement());
			}
		}
		if (!waitingFor.isEmpty() || !anySolved) {
			return new RecordedConditionReaction(answers, waitingFor, Next.WAIT, null);
		}
		final RequestClaim claim = dispatcher.claimForRequest(block.getConversationCode(), requestId);
		return switch (claim.kind()) {
		case CLAIMED -> new RecordedConditionReaction(answers, waitingFor, Next.RESPAWN,
				reactionOf(claim.block(), claim.used()));
		case HELD -> new RecordedConditionReaction(answers, waitingFor, Next.RESPAWNING_APART, null);
		default -> new RecordedConditionReaction(answers, waitingFor, Next.WAIT, null);
		};
	}

	/**
	 * The reaction the request respawns the block with: the answer of each
	 * condition, from whichever source, in the order the block asked them; an
	 * optional condition none answered is declined, as the chat declines one it
	 * leaves.
	 */
	private NetworkOfAgentsSuspendedStateReaction reactionOf(GNetworkOfAgentsSuspension block,
			List<GConditionEvent> used) {
		final Map<String, GConditionEvent> byCondition = new LinkedHashMap<String, GConditionEvent>();
		for (GConditionEvent event : used) {
			byCondition.put(event.getConditionId(), event);
		}
		final Map<String, String> typeOf = new LinkedHashMap<String, String>();
		final NetworkOfAgentsSuspendedState state = transport.stateOf(block);
		if (state != null && state.getConditions() != null) {
			for (AgentSuspendedWaitingConditionDto dto : state.getConditions()) {
				typeOf.put(dto.getConditionId(), dto.getConditionType());
			}
		}
		final List<AgentSuspendedWaitingConditionReactionDto> reactions = new ArrayList<AgentSuspendedWaitingConditionReactionDto>();
		for (WaitingCondition condition : block.conditions()) {
			final String conditionId = condition.getSuspendedConditionId();
			final GConditionEvent event = byCondition.get(conditionId);
			if (event != null) {
				reactions.add(event.toReaction());
			} else if (!condition.isMandatory() && conditionId != null) {
				final AgentSuspendedWaitingConditionReactionDto declined = new AgentSuspendedWaitingConditionReactionDto();
				declined.setConditionId(conditionId);
				declined.setConditionType(typeOf.get(conditionId));
				final LinkedHashMap<String, Object> attributes = new LinkedHashMap<String, Object>();
				attributes.put(GAbstractConditionTransportTranslator.DECLINED, Boolean.TRUE);
				declined.setAttributes(attributes);
				reactions.add(declined);
			}
		}
		final NetworkOfAgentsSuspendedStateReaction reaction = new NetworkOfAgentsSuspendedStateReaction();
		reaction.setNetworkOfAgentsBlockId(block.getCode());
		reaction.setReactions(reactions);
		return reaction;
	}

	/** The event that solves the condition in this block, from whichever source; null when none does. */
	private GConditionEvent solvingEventIn(GNetworkOfAgentsSuspension block, String conditionId) {
		if (conditionId == null) {
			return null;
		}
		final GConditionEvent event = store
				.findEvent(ConditionCorrelationId.of(null, block.getConversationCode(), conditionId).composed())
				.orElse(null);
		return event != null && SOLVING.contains(event.getStatus()) && (Objects.equals(block.getCode(), event.getBlockCode())
				|| Objects.equals(block.getCode(), event.getDispatchCode())) ? event : null;
	}
}
