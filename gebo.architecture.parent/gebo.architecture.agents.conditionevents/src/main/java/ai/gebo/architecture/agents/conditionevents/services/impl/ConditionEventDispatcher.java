/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.services.impl;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.conditionevents.config.ConditionEventsConfig;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventStatus;
import ai.gebo.architecture.agents.conditionevents.model.GConditionDispatch;
import ai.gebo.architecture.agents.conditionevents.model.GConditionEvent;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventsStore;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.WaitingCondition;
import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedStateReaction;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineService;
import ai.gebo.llms.chat.pipelines.service.IConditionReactionRecorder;
import ai.gebo.llms.chat.pipelines.service.impl.NetworkOfAgentsSuspensionTransport;
import ai.gebo.security.services.IdentityUtil;

/**
 * Turns the events of a session into the respawn of its open block, once they
 * solve it:
 * <ol>
 * <li>the session's events not used yet are matched again to its open block;</li>
 * <li>the block is solved when every mandatory condition has an accepted event;
 * a block with none is solved when every condition has one, or once the first
 * accepted event waited {@code optionalSettleMillis} for the others;</li>
 * <li>the respawn is claimed (see {@link IConditionEventsStore#claim}), so that
 * one instance runs it;</li>
 * <li>it runs through the session's pipeline (its respawner step), as the
 * session's owner, unattended, with the events as the reaction and their text as
 * the user's message;</li>
 * <li>the block tells how it went: respawned, the events are applied; dropped,
 * they are stale; still open, the respawn failed and is tried again with a
 * doubling backoff, until it gives up.</li>
 * </ol>
 * The blocks are read, never written: the respawn writes them as it always does.
 * A request that runs the respawn itself (the chat answering the last condition)
 * claims it here instead (see {@link #claimForRequest}) and says when it ended
 * (see {@link #concludeRequest}).
 */
@Service
public class ConditionEventDispatcher {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConditionEventDispatcher.class);
	/** The run origin of a respawn started by events. */
	public static final String RUN_ORIGIN = IConditionReactionRecorder.RESPAWN_ORIGIN;
	static final String STILL_OPEN = "The block is still open after the respawn";
	static final String BLOCK_GONE = "The block was dropped or removed before the respawn consumed it";
	static final String NO_SESSION = "The session of the block no longer exists";
	static final String NO_ACCOUNTS = "Accounts cannot be resolved in this installation";
	static final String NO_OWNER = "The session has no owner to run the respawn as";
	static final String OWNER_UNRESOLVED = "The session's owner cannot be resolved: ";
	static final String OWNER_DISABLED = "The session's owner's account is disabled: ";
	static final String GAVE_UP = "The respawn was tried %d time(s) and gave up: %s";
	static final String RETRYING = "The respawn failed (attempt %d of %d), tried again after %s: %s";
	private final IConditionEventsStore store;
	private final ConditionEventMatcher matcher;
	private final GNetworkOfAgentsSuspensionRepository blocks;
	private final GUserChatSessionRepository sessions;
	private final NetworkOfAgentsSuspensionTransport transport;
	private final ConditionEventTexts texts;
	private final IChatPipelineService pipelines;
	private final ObjectProvider<UserDetailsService> accounts;
	private final ConditionEventsConfig config;
	private final Clock clock;

	@Autowired
	public ConditionEventDispatcher(IConditionEventsStore store, ConditionEventMatcher matcher,
			GNetworkOfAgentsSuspensionRepository blocks, GUserChatSessionRepository sessions,
			NetworkOfAgentsSuspensionTransport transport, ConditionEventTexts texts, IChatPipelineService pipelines,
			ObjectProvider<UserDetailsService> accounts, ConditionEventsConfig config) {
		this(store, matcher, blocks, sessions, transport, texts, pipelines, accounts, config, Clock.systemUTC());
	}

	ConditionEventDispatcher(IConditionEventsStore store, ConditionEventMatcher matcher,
			GNetworkOfAgentsSuspensionRepository blocks, GUserChatSessionRepository sessions,
			NetworkOfAgentsSuspensionTransport transport, ConditionEventTexts texts, IChatPipelineService pipelines,
			ObjectProvider<UserDetailsService> accounts, ConditionEventsConfig config, Clock clock) {
		this.store = store;
		this.matcher = matcher;
		this.blocks = blocks;
		this.sessions = sessions;
		this.transport = transport;
		this.texts = texts;
		this.pipelines = pipelines;
		this.accounts = accounts;
		this.config = config;
		this.clock = clock;
	}

	/**
	 * What a request that runs the respawn itself got from its claim.
	 *
	 * @param kind  whether it was claimed
	 * @param block the open block, when there is one
	 * @param used  with {@link Kind#CLAIMED}, the events the request respawns it
	 *              with, in the order the block asked their conditions
	 */
	public record RequestClaim(Kind kind, GNetworkOfAgentsSuspension block, List<GConditionEvent> used) {
		public enum Kind {
			/** The session has no open block, or no accepted event for it. */
			NOTHING,
			/** Another runs its respawn, it waits for its backoff, or it is over. */
			HELD,
			/** Claimed for the request, which runs the respawn. */
			CLAIMED
		}

		public RequestClaim {
			used = used != null ? List.copyOf(used) : List.of();
		}
	}

	/** Matches the session's live events to its open block and respawns it once they solve it. */
	public void evaluate(String sessionCode) {
		final Date now = new Date(clock.millis());
		final GNetworkOfAgentsSuspension open = blocks.findOpenByConversationCode(sessionCode).orElse(null);
		final Map<String, GConditionEvent> accepted = acceptedFor(sessionCode, open, now);
		if (open == null || !isSolved(open, accepted, now)) {
			return;
		}
		dispatch(sessionCode, open, accepted, now);
	}

	/**
	 * Claims the respawn of the session's open block for a request that runs it
	 * itself, with the events accepted for the block: the request decided the
	 * block is solved, so the optional ones do not wait to settle. The events are
	 * marked dispatched to the request; nothing runs here.
	 */
	public RequestClaim claimForRequest(String sessionCode, String requestId) {
		final Date now = new Date(clock.millis());
		final GNetworkOfAgentsSuspension open = blocks.findOpenByConversationCode(sessionCode).orElse(null);
		final Map<String, GConditionEvent> accepted = acceptedFor(sessionCode, open, now);
		if (open == null || accepted.isEmpty()) {
			return new RequestClaim(RequestClaim.Kind.NOTHING, open, null);
		}
		final Optional<GConditionDispatch> claimed = store.claim(open.getCode(), sessionCode, now,
				config.getDispatchLeaseMillis());
		if (claimed.isEmpty()) {
			return new RequestClaim(RequestClaim.Kind.HELD, open, null);
		}
		final List<GConditionEvent> used = inBlockOrder(open, accepted);
		if (claimed.get().getAttempts() > config.getMaxAttempts()) {
			giveUp(claimed.get(), used, claimed.get().getLastFailure());
			return new RequestClaim(RequestClaim.Kind.HELD, open, null);
		}
		markDispatched(claimed.get(), open, used, requestId, now);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Respawn of block:" + open.getCode() + " of session:" + sessionCode + " claimed by request:"
					+ requestId + " with " + used.size() + " solved condition(s)");
		}
		return new RequestClaim(RequestClaim.Kind.CLAIMED, open, used);
	}

	/**
	 * The respawn a request claimed (see {@link #claimForRequest}) ended, however
	 * it went: read from the block as the dispatcher's own; nothing when the
	 * request no longer holds it.
	 */
	public void concludeRequest(String sessionCode, String blockCode, String requestId) {
		if (blockCode == null || requestId == null) {
			return;
		}
		final GConditionDispatch dispatch = store.findDispatch(blockCode).orElse(null);
		if (dispatch == null || !requestId.equals(dispatch.getRequestId())
				|| dispatch.getState() != GConditionDispatch.State.RUNNING) {
			return;
		}
		final List<GConditionEvent> used = new ArrayList<GConditionEvent>();
		for (GConditionEvent event : store.eventsOfDispatch(blockCode)) {
			if (event.getStatus() == ConditionEventStatus.DISPATCHED && requestId.equals(event.getDispatchedRequestId())) {
				used.add(event);
			}
		}
		conclude(sessionCode, dispatch, blockCode, used, null);
	}

	/** The session's live events matched again to its open block: those it accepted, by condition. */
	private Map<String, GConditionEvent> acceptedFor(String sessionCode, GNetworkOfAgentsSuspension open, Date now) {
		final Map<String, GConditionEvent> accepted = new LinkedHashMap<String, GConditionEvent>();
		for (GConditionEvent event : store.liveEventsOf(sessionCode)) {
			if (!isAcceptedFor(event, open)) {
				final ConditionEventStatus status = event.getStatus();
				final String block = event.getBlockCode();
				final String reason = event.getReason();
				matcher.match(event, open, now);
				if (status != event.getStatus() || !Objects.equals(block, event.getBlockCode())
						|| !Objects.equals(reason, event.getReason())) {
					event.setDateModified(now);
					store.saveEvent(event);
				}
			}
			if (isAcceptedFor(event, open)) {
				accepted.put(event.getConditionId(), event);
			}
		}
		return accepted;
	}

	private static boolean isAcceptedFor(GConditionEvent event, GNetworkOfAgentsSuspension open) {
		return open != null && event.getStatus() == ConditionEventStatus.ACCEPTED
				&& Objects.equals(open.getCode(), event.getBlockCode());
	}

	/**
	 * Whether the accepted events solve the block: every mandatory condition, or,
	 * with none mandatory, every condition, or the first one once it settled.
	 */
	boolean isSolved(GNetworkOfAgentsSuspension open, Map<String, GConditionEvent> accepted, Date now) {
		if (accepted.isEmpty()) {
			return false;
		}
		boolean anyMandatory = false;
		boolean all = true;
		for (WaitingCondition condition : open.conditions()) {
			final boolean solved = accepted.containsKey(condition.getSuspendedConditionId());
			if (condition.isMandatory()) {
				anyMandatory = true;
				if (!solved) {
					return false;
				}
			}
			all &= solved;
		}
		if (anyMandatory || all) {
			return true;
		}
		long first = Long.MAX_VALUE;
		for (GConditionEvent event : accepted.values()) {
			if (event.getAcceptedAt() != null) {
				first = Math.min(first, event.getAcceptedAt().getTime());
			}
		}
		return first != Long.MAX_VALUE && first + config.getOptionalSettleMillis() <= now.getTime();
	}

	private void dispatch(String sessionCode, GNetworkOfAgentsSuspension open, Map<String, GConditionEvent> accepted,
			Date now) {
		final Optional<GConditionDispatch> claimed = store.claim(open.getCode(), sessionCode, now,
				config.getDispatchLeaseMillis());
		if (claimed.isEmpty()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The respawn of block:" + open.getCode() + " is held by another, waits for its backoff,"
						+ " or is over");
			}
			return;
		}
		final GConditionDispatch dispatch = claimed.get();
		final List<GConditionEvent> used = inBlockOrder(open, accepted);
		if (dispatch.getAttempts() > config.getMaxAttempts()) {
			giveUp(dispatch, used, dispatch.getLastFailure());
			return;
		}
		final GeboChatRequest request = new GeboChatRequest();
		markDispatched(dispatch, open, used, request.getId(), now);
		final GUserChatSession session = sessions.findById(sessionCode).orElse(null);
		if (session == null) {
			giveUp(dispatch, used, NO_SESSION);
			return;
		}
		final UserDetails owner;
		try {
			owner = ownerOf(session);
		} catch (IllegalStateException cannotRun) {
			giveUp(dispatch, used, cannotRun.getMessage());
			return;
		}
		fill(request, session, open, used);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Respawning block:" + open.getCode() + " of session:" + sessionCode + " as:"
					+ owner.getUsername() + " with " + used.size() + " solved condition(s), attempt "
					+ dispatch.getAttempts() + ", request:" + request.getId());
		}
		String failure = null;
		try {
			IdentityUtil.create(owner).<GeboChatResponse, Exception>doRunAsWithReturnAndException(
					() -> pipelines.respawnAgenticChat(session.getPipelineCode(), request,
							new LinkedHashMap<String, Object>()));
		} catch (Exception | LinkageError respawnFailure) {
			failure = respawnFailure.toString();
			LOGGER.warn("The respawn of block:" + open.getCode() + " by condition events failed: " + failure);
		}
		conclude(sessionCode, dispatch, open.getCode(), used, failure);
	}

	/** The claimed respawn runs the request with the events: they are dispatched to it. */
	private void markDispatched(GConditionDispatch dispatch, GNetworkOfAgentsSuspension open,
			List<GConditionEvent> used, String requestId, Date now) {
		dispatch.setRequestId(requestId);
		dispatch.setConditionIds(used.stream().map(GConditionEvent::getConditionId).toList());
		dispatch.setDateModified(now);
		store.saveDispatch(dispatch);
		for (GConditionEvent event : used) {
			event.setStatus(ConditionEventStatus.DISPATCHED);
			event.setDispatchCode(open.getCode());
			event.setDispatchedRequestId(requestId);
			event.setReason(null);
			event.setDateModified(now);
			store.saveEvent(event);
		}
	}

	/** The request of the respawn, as the chat sends it, the events being the reaction. */
	private void fill(GeboChatRequest request, GUserChatSession session, GNetworkOfAgentsSuspension open,
			List<GConditionEvent> used) {
		request.setUserChatContextCode(session.getCode());
		request.setChatProfileCode(session.getChatProfileCode());
		request.setChatModelCode(session.getChatModelCode());
		request.setRunOrigin(RUN_ORIGIN);
		request.setAttended(Boolean.FALSE);
		request.setQuery(texts.queryOf(transport.stateOf(open), used));
		final NetworkOfAgentsSuspendedStateReaction reaction = new NetworkOfAgentsSuspendedStateReaction();
		reaction.setNetworkOfAgentsBlockId(open.getCode());
		final List<AgentSuspendedWaitingConditionReactionDto> reactions = new ArrayList<AgentSuspendedWaitingConditionReactionDto>();
		for (GConditionEvent event : used) {
			reactions.add(event.toReaction());
		}
		reaction.setReactions(reactions);
		request.setNetworkOfAgentsSuspendedStateReaction(reaction);
	}

	/** The account the respawn runs as: the session's owner, enabled. */
	private UserDetails ownerOf(GUserChatSession session) {
		final UserDetailsService resolver = accounts != null ? accounts.getIfAvailable() : null;
		if (resolver == null) {
			throw new IllegalStateException(NO_ACCOUNTS);
		}
		if (session.getUsername() == null || session.getUsername().isBlank()) {
			throw new IllegalStateException(NO_OWNER);
		}
		final UserDetails owner;
		try {
			owner = resolver.loadUserByUsername(session.getUsername());
		} catch (RuntimeException notResolved) {
			throw new IllegalStateException(OWNER_UNRESOLVED + session.getUsername() + ": " + notResolved.getMessage());
		}
		if (owner == null) {
			throw new IllegalStateException(OWNER_UNRESOLVED + session.getUsername());
		}
		if (!owner.isEnabled()) {
			throw new IllegalStateException(OWNER_DISABLED + session.getUsername());
		}
		return owner;
	}

	/** Reads how the respawn went from the block, whatever the run answered. */
	private void conclude(String sessionCode, GConditionDispatch dispatch, String blockCode,
			List<GConditionEvent> used, String failure) {
		final Date now = new Date(clock.millis());
		final GNetworkOfAgentsSuspension after = blocks.findById(blockCode).orElse(null);
		if (after != null && after.getRespawnedAt() != null) {
			dispatch.setState(GConditionDispatch.State.DONE);
			dispatch.setEndedAt(now);
			dispatch.setLastFailure(null);
			dispatch.setDateModified(now);
			store.saveDispatch(dispatch);
			for (GConditionEvent event : used) {
				event.setStatus(ConditionEventStatus.APPLIED);
				event.setAppliedAt(after.getRespawnedAt());
				event.setRespawnedToBlockCode(after.getRespawnedToSuspensionCode());
				event.setReason(null);
				event.setDateModified(now);
				store.saveEvent(event);
			}
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Block:" + blockCode + " respawned by condition events"
						+ (after.getRespawnedToSuspensionCode() != null
								? ", suspended again into block:" + after.getRespawnedToSuspensionCode()
								: ""));
			}
			if (after.getRespawnedToSuspensionCode() != null) {
				// the events of the conditions still waited for go to the new block
				evaluate(sessionCode);
			}
			return;
		}
		if (after == null || after.getDroppedAt() != null) {
			dispatch.setState(GConditionDispatch.State.DONE);
			dispatch.setEndedAt(now);
			dispatch.setLastFailure(BLOCK_GONE);
			dispatch.setDateModified(now);
			store.saveDispatch(dispatch);
			for (GConditionEvent event : used) {
				event.setStatus(ConditionEventStatus.STALE);
				event.setReason(BLOCK_GONE);
				event.setDateModified(now);
				store.saveEvent(event);
			}
			return;
		}
		failed(dispatch, used, failure != null ? failure : STILL_OPEN, now);
	}

	private void failed(GConditionDispatch dispatch, List<GConditionEvent> used, String failure, Date now) {
		if (dispatch.getAttempts() >= config.getMaxAttempts()) {
			giveUp(dispatch, used, failure);
			return;
		}
		final long backoff = config.getRetryBackoffMillis() * (1L << Math.min(20, Math.max(0, dispatch.getAttempts() - 1)));
		dispatch.setState(GConditionDispatch.State.FAILED);
		dispatch.setLastFailure(failure);
		dispatch.setNextAttemptAt(new Date(now.getTime() + backoff));
		dispatch.setDateModified(now);
		store.saveDispatch(dispatch);
		final String reason = String.format(RETRYING, dispatch.getAttempts(), config.getMaxAttempts(),
				(backoff / 1000L) + "s", failure);
		for (GConditionEvent event : used) {
			// accepted again: the next claim, once the backoff is over, takes them
			event.setStatus(ConditionEventStatus.ACCEPTED);
			event.setReason(reason);
			event.setDateModified(now);
			store.saveEvent(event);
		}
	}

	private void giveUp(GConditionDispatch dispatch, List<GConditionEvent> used, String failure) {
		final Date now = new Date(clock.millis());
		final String reason = String.format(GAVE_UP, Math.min(dispatch.getAttempts(), config.getMaxAttempts()),
				failure);
		LOGGER.error("Block:" + dispatch.getCode() + " of session:" + dispatch.getSessionCode() + ": " + reason);
		dispatch.setState(GConditionDispatch.State.GAVE_UP);
		dispatch.setLastFailure(failure);
		dispatch.setEndedAt(now);
		dispatch.setDateModified(now);
		store.saveDispatch(dispatch);
		for (GConditionEvent event : used) {
			event.setStatus(ConditionEventStatus.FAILED);
			event.setDispatchCode(dispatch.getCode());
			event.setReason(reason);
			event.setDateModified(now);
			store.saveEvent(event);
		}
	}

	/** The accepted events, in the order the block asked their conditions. */
	private static List<GConditionEvent> inBlockOrder(GNetworkOfAgentsSuspension open,
			Map<String, GConditionEvent> accepted) {
		final List<GConditionEvent> ordered = new ArrayList<GConditionEvent>();
		for (WaitingCondition condition : open.conditions()) {
			final GConditionEvent event = accepted.get(condition.getSuspendedConditionId());
			if (event != null) {
				ordered.add(event);
			}
		}
		return ordered;
	}
}
