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
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.conditionevents.ConditionCorrelationId;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEvent;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventAck;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventCaller;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventStatus;
import ai.gebo.architecture.agents.conditionevents.model.GConditionEvent;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventsService;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventsStore;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.WaitingCondition;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedStateReaction;
import ai.gebo.llms.chat.pipelines.service.IConditionReactionRecorder;
import ai.gebo.llms.chat.pipelines.service.RecordedConditionReaction;
import ai.gebo.llms.chat.pipelines.service.RecordedConditionReaction.Answer;

/**
 * The chat's answers as condition events: each one is delivered as any source
 * delivers its event, by the conversation's user on the {@code CHAT} channel,
 * so it is checked by the condition's translator, kept next to the answers of
 * the other sources, and the block is respawned by the dispatcher once solved.
 */
@Service
public class ConditionEventsReactionRecorder implements IConditionReactionRecorder {
	/** The system the chat's answers come from. */
	public static final String SOURCE_SYSTEM = "CHAT";
	static final String NOT_NAMED = "The answer names no condition";
	static final String ANSWERED_TWICE = "The reaction answers this condition more than once: the first answer counts";
	/** The states of an event that solves its condition in the block it was matched to. */
	private static final Set<ConditionEventStatus> SOLVING = EnumSet.of(ConditionEventStatus.ACCEPTED,
			ConditionEventStatus.DISPATCHED, ConditionEventStatus.APPLIED);
	private final IConditionEventsService events;
	private final IConditionEventsStore store;

	public ConditionEventsReactionRecorder(IConditionEventsService events, IConditionEventsStore store) {
		this.events = events;
		this.store = store;
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
					final ConditionEventAck ack = events.deliver(eventOf(block, answer, requestId), caller);
					answers.add(new Answer(conditionId,
							ack.getOutcome() != null ? ack.getOutcome().name() : RecordedConditionReaction.REFUSED,
							ack.getReason()));
				}
			}
		}
		return outcomeOf(block, answers);
	}

	private static ConditionEvent eventOf(GNetworkOfAgentsSuspension block, AgentSuspendedWaitingConditionReactionDto answer,
			String requestId) {
		final ConditionEvent event = new ConditionEvent();
		event.setSessionCode(block.getConversationCode());
		event.setConditionId(answer.getConditionId());
		event.setConditionType(answer.getConditionType());
		event.setAttributes(answer.getAttributes() != null ? new LinkedHashMap<String, Object>(answer.getAttributes())
				: new LinkedHashMap<String, Object>());
		event.setSourceSystem(SOURCE_SYSTEM);
		// the same chat request sent again is the same event of its source
		event.setSourceEventId(requestId != null ? requestId + ":" + answer.getConditionId() : null);
		return event;
	}

	/**
	 * What the block still waits for once the answers are in: its mandatory
	 * conditions with no event solving them in this block. It resumes when none is
	 * left and something solved it; a block of optional conditions only resumes
	 * on its first answer.
	 */
	private RecordedConditionReaction outcomeOf(GNetworkOfAgentsSuspension block, List<Answer> answers) {
		final List<String> waitingFor = new ArrayList<String>();
		boolean anySolved = false;
		for (WaitingCondition condition : block.conditions()) {
			final boolean solved = isSolvedIn(block, condition.getSuspendedConditionId());
			anySolved |= solved;
			if (condition.isMandatory() && !solved) {
				waitingFor.add(condition.getWaitingConditionStatement());
			}
		}
		return new RecordedConditionReaction(answers, waitingFor, waitingFor.isEmpty() && anySolved);
	}

	private boolean isSolvedIn(GNetworkOfAgentsSuspension block, String conditionId) {
		if (conditionId == null) {
			return false;
		}
		final GConditionEvent event = store
				.findEvent(ConditionCorrelationId.of(null, block.getConversationCode(), conditionId).composed())
				.orElse(null);
		return event != null && SOLVING.contains(event.getStatus()) && (Objects.equals(block.getCode(), event.getBlockCode())
				|| Objects.equals(block.getCode(), event.getDispatchCode()));
	}
}
