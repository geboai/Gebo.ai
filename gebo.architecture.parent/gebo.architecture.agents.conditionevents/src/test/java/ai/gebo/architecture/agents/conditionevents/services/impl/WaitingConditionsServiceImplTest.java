/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import ai.gebo.architecture.agents.conditionevents.config.ConditionEventsConfig;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEvent;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventAck;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventAck.Outcome;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventCaller;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventStatus;
import ai.gebo.architecture.agents.conditionevents.model.GConditionDispatch;
import ai.gebo.architecture.agents.conditionevents.model.GConditionEvent;
import ai.gebo.architecture.agents.conditionevents.model.WaitingBlockView;
import ai.gebo.architecture.agents.conditionevents.model.WaitingConditionsActionAck;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.ClarificationAnswer;
import ai.gebo.architecture.agents.model.ClarificationOption;
import ai.gebo.architecture.agents.model.ClarificationOptions;
import ai.gebo.architecture.agents.model.ConditionExpiry;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.SuspendedAgentState;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.WaitingCondition;
import ai.gebo.architecture.agents.model.UnansweredBecause;
import ai.gebo.architecture.agents.model.WaitingConditionDropReason;
import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedStateReaction;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.chat.pipelines.service.IAgentSuspendedWaitingConditionTransportTranslatorRepositoryPattern;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineService;
import ai.gebo.llms.chat.pipelines.service.RecordedConditionReaction;
import ai.gebo.llms.chat.pipelines.service.impl.ClarificationConditionTransportTranslator;
import ai.gebo.llms.chat.pipelines.service.impl.GAbstractConditionTransportTranslator;
import ai.gebo.llms.chat.pipelines.service.impl.NetworkOfAgentsSuspensionTransport;

/**
 * Pins the waiting conditions panel and the sweep on the events layer as it is:
 * going on without a condition and an expiry that unblocks are events of the
 * platform's own that respawn the block as any event does, as the owner;
 * abandoning drops the block; the owner is told once of an unwatched run and of
 * an expiry that keeps waiting, and the messages are taken back once the block
 * closes; and nothing of it can be asked through a delivered event.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
class WaitingConditionsServiceImplTest {
	private static final String SESSION = "session-1";
	private static final String OWNER = "owner";
	private static final long MINUTE = 60_000L;

	private final Map<String, GNetworkOfAgentsSuspension> blocks = new LinkedHashMap<String, GNetworkOfAgentsSuspension>();
	private final List<GeboChatRequest> respawns = new ArrayList<GeboChatRequest>();
	private final MutableClock clock = new MutableClock();
	private final ConditionEventsConfig config = new ConditionEventsConfig();
	private final WaitingConditionsNotifier notifier = mock(WaitingConditionsNotifier.class);
	private final GUserChatSession session = new GUserChatSession();
	private InMemoryConditionEventsStore store;
	private WaitingConditionsServiceImpl waiting;
	private ConditionEventsServiceImpl events;
	private ConditionEventsReactionRecorder recorder;

	@BeforeEach
	void setUp() throws Exception {
		final GNetworkOfAgentsSuspensionRepository repository = mock(GNetworkOfAgentsSuspensionRepository.class);
		when(repository.findOpenByConversationCode(anyString())).thenAnswer(i -> blocks.values().stream()
				.filter(b -> i.getArgument(0).equals(b.getConversationCode()) && b.isOpen())
				.max((a, b) -> a.getSuspendedAt().compareTo(b.getSuspendedAt())));
		when(repository.findById(anyString())).thenAnswer(i -> Optional.ofNullable(blocks.get(i.getArgument(0))));
		when(repository.findByRespawnedAtIsNullAndDroppedAtIsNull())
				.thenAnswer(i -> blocks.values().stream().filter(GNetworkOfAgentsSuspension::isOpen).toList());
		when(repository.findByEarliestExpiryAtLessThanEqualAndRespawnedAtIsNullAndDroppedAtIsNull(any()))
				.thenAnswer(i -> blocks.values().stream().filter(b -> b.isOpen() && b.getEarliestExpiryAt() != null
						&& !b.getEarliestExpiryAt().after((Date) i.getArgument(0))).toList());
		when(repository.findByUnwatchedTrueAndMessagesRetractedAtIsNull()).thenAnswer(i -> blocks.values().stream()
				.filter(b -> b.isUnwatched() && b.getMessagesRetractedAt() == null).toList());
		when(repository.findByEarliestExpiryAtIsNotNullAndMessagesRetractedAtIsNull()).thenAnswer(i -> blocks.values()
				.stream().filter(b -> b.getEarliestExpiryAt() != null && b.getMessagesRetractedAt() == null).toList());
		when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

		session.setCode(SESSION);
		session.setUsername(OWNER);
		final GUserChatSessionRepository sessions = mock(GUserChatSessionRepository.class);
		when(sessions.findById(anyString()))
				.thenAnswer(i -> SESSION.equals(i.getArgument(0)) ? Optional.of(session) : Optional.empty());

		final ClarificationConditionTransportTranslator clarification = new ClarificationConditionTransportTranslator();
		final IAgentSuspendedWaitingConditionTransportTranslatorRepositoryPattern translators = mock(
				IAgentSuspendedWaitingConditionTransportTranslatorRepositoryPattern.class);
		when(translators.findTranslator(any()))
				.thenAnswer(i -> clarification.canTranslate(i.getArgument(0)) ? clarification : null);
		final NetworkOfAgentsSuspensionTransport transport = new NetworkOfAgentsSuspensionTransport(translators);

		final IChatPipelineService pipelines = mock(IChatPipelineService.class);
		when(pipelines.respawnAgenticChat(any(), any(GeboChatRequest.class), any())).thenAnswer(i -> {
			final GeboChatRequest request = i.getArgument(1);
			respawns.add(request);
			blocks.get(request.getNetworkOfAgentsSuspendedStateReaction().getNetworkOfAgentsBlockId())
					.setRespawnedAt(new Date(clock.millis()));
			return null;
		});
		final UserDetailsService users = mock(UserDetailsService.class);
		when(users.loadUserByUsername(OWNER)).thenReturn(User.withUsername(OWNER).password("x").authorities("USER").build());
		final ObjectProvider<UserDetailsService> accounts = mock(ObjectProvider.class);
		when(accounts.getIfAvailable()).thenReturn(users);
		final ObjectProvider<org.springframework.data.mongodb.core.MongoOperations> noMongo = mock(ObjectProvider.class);

		store = new InMemoryConditionEventsStore(blocks::values);
		final ConditionEventMatcher matcher = new ConditionEventMatcher(store, transport, config);
		final ConditionEventTexts texts = new ConditionEventTexts(
				List.of(new DefaultConditionEventTextRenderer(), new ClarificationConditionEventTextRenderer()));
		final ConditionEventDispatcher dispatcher = new ConditionEventDispatcher(store, matcher, repository, sessions,
				transport, texts, pipelines, accounts, config, clock);
		waiting = new WaitingConditionsServiceImpl(repository, sessions, store, matcher, dispatcher, transport, notifier,
				noMongo, Runnable::run, clock);
		final ObjectProvider<ai.gebo.architecture.agents.conditionevents.services.IWaitingConditionsService> waitingProvider = mock(
				ObjectProvider.class);
		when(waitingProvider.getIfAvailable()).thenReturn(waiting);
		events = new ConditionEventsServiceImpl(store, matcher, dispatcher, sessions, repository,
				new ConditionEventsAuthorizationImpl(config), config, Runnable::run, clock, waitingProvider);
		recorder = new ConditionEventsReactionRecorder(events, store, dispatcher, transport, Runnable::run);
	}

	// ---- going on without -------------------------------------------------------

	@Test
	void theOwnerGoesOnWithoutAMandatoryConditionAndTheRunResumesToldSo() {
		final GNetworkOfAgentsSuspension block = block(question("year", true), question("country", true));

		final WaitingConditionsActionAck ack = waiting.goOnWithout(block.getCode(), List.of(), OWNER, false);

		assertEquals(WaitingConditionsActionAck.Outcome.DONE, ack.getOutcome());
		assertEquals(1, respawns.size(), "nothing mandatory is left: the block is respawned");
		final List<AgentSuspendedWaitingConditionReactionDto> reactions = respawns.get(0)
				.getNetworkOfAgentsSuspendedStateReaction().getReactions();
		assertEquals(2, reactions.size());
		for (AgentSuspendedWaitingConditionReactionDto reaction : reactions) {
			assertEquals(UnansweredBecause.GONE_ON_WITHOUT, reaction.getUnanswered());
			assertTrue(reaction.getSource().isConversationUser(), "the owner chose: the agent reads 'the user'");
			assertFalse(reaction.getAttributes().containsKey(GAbstractConditionTransportTranslator.GO_ON_WITHOUT));
		}
		assertEquals(ConditionEventStatus.APPLIED, store.events.get(SESSION + ":year").getStatus());
	}

	@Test
	void namedConditionsOnlyAndTheRunWaitsWhileAMandatoryOneIsLeft() {
		final GNetworkOfAgentsSuspension block = block(question("year", true), question("country", true));

		assertEquals(WaitingConditionsActionAck.Outcome.DONE,
				waiting.goOnWithout(block.getCode(), List.of("year"), OWNER, false).getOutcome());

		assertTrue(respawns.isEmpty(), "the country is still mandatory and waiting");
		assertEquals(ConditionEventStatus.ACCEPTED, store.events.get(SESSION + ":year").getStatus());
		assertNull(store.events.get(SESSION + ":country"));
		final WaitingBlockView view = waiting.waitingFor(OWNER).get(0);
		assertTrue(view.getConditions().get(0).isSolved());
		assertTrue(view.getConditions().get(0).getSolvedBy().startsWith(OWNER),
				"solved by the conversation's user, named for whoever reads the panel: "
						+ view.getConditions().get(0).getSolvedBy());
		assertFalse(view.getConditions().get(1).isSolved());
		assertEquals(WaitingConditionsActionAck.Outcome.REFUSED,
				waiting.goOnWithout(block.getCode(), List.of("year"), OWNER, false).getOutcome(), "nothing left of it");
		assertEquals(WaitingConditionsActionAck.Outcome.REFUSED,
				waiting.goOnWithout(block.getCode(), List.of("nope"), OWNER, false).getOutcome(), "not waited for");
	}

	@Test
	void anAdministratorGoesOnWithoutItAndIsNamedToTheAgentAnyoneElseIsRefused() {
		final GNetworkOfAgentsSuspension block = block(question("year", true));

		assertEquals(WaitingConditionsActionAck.Outcome.REFUSED,
				waiting.goOnWithout(block.getCode(), null, "someone", false).getOutcome());
		assertEquals(WaitingConditionsActionAck.Outcome.NOT_FOUND,
				waiting.goOnWithout("no-such-block", null, OWNER, false).getOutcome());
		assertTrue(respawns.isEmpty());

		assertEquals(WaitingConditionsActionAck.Outcome.DONE,
				waiting.goOnWithout(block.getCode(), null, "admin", true).getOutcome());

		assertEquals(1, respawns.size());
		final AgentSuspendedWaitingConditionReactionDto reaction = respawns.get(0)
				.getNetworkOfAgentsSuspendedStateReaction().getReactions().get(0);
		assertEquals(UnansweredBecause.GONE_ON_WITHOUT, reaction.getUnanswered());
		assertEquals("The administrator admin (GEBO)", reaction.getSource().label());
		assertFalse(reaction.getSource().isConversationUser());
	}

	@Test
	void fromTheChatTheOwnerAsksItAsAnAttributeTheRecorderReadsAndNobodyElseMay() {
		final GNetworkOfAgentsSuspension block = block(question("year", true));
		final ConditionEvent delivered = choice(SESSION + ":year", "2024");
		delivered.getAttributes().put(GAbstractConditionTransportTranslator.GO_ON_WITHOUT, true);
		delivered.setUnanswered(UnansweredBecause.GONE_ON_WITHOUT);

		final ConditionEventAck refused = events.deliver(delivered, application());

		assertEquals(Outcome.REFUSED, refused.getOutcome());
		assertTrue(refused.getReason().startsWith(GAbstractConditionTransportTranslator.GO_ON_WITHOUT_NOT_HERE),
				refused.getReason());
		assertNull(store.events.get(SESSION + ":year").getUnanswered(), "a delivered event cannot say it");
		assertTrue(respawns.isEmpty());

		final RecordedConditionReaction recorded = recorder.record(block, chatGoingOnWithout(block, "year"), OWNER,
				"req-1");

		assertTrue(recorded.resumes(), recorded.toString());
		assertEquals(UnansweredBecause.GONE_ON_WITHOUT, store.events.get(SESSION + ":year").getUnanswered());
		assertEquals(UnansweredBecause.GONE_ON_WITHOUT, recorded.reaction().getReactions().get(0).getUnanswered());
		assertFalse(recorded.reaction().getReactions().get(0).getAttributes()
				.containsKey(GAbstractConditionTransportTranslator.GO_ON_WITHOUT));
	}

	// ---- abandoning -------------------------------------------------------------

	@Test
	void abandoningDropsTheBlockSayingWhoAndTakesTheMessagesBack() {
		final GNetworkOfAgentsSuspension block = block(question("year", true));
		block.setUnwatched(true);

		final WaitingConditionsActionAck ack = waiting.abandon(block.getCode(), OWNER, false);

		assertEquals(WaitingConditionsActionAck.Outcome.DONE, ack.getOutcome());
		assertFalse(block.isOpen());
		assertEquals(WaitingConditionDropReason.ABANDONED, block.getDroppedReason());
		assertEquals(OWNER, block.getDroppedBy());
		assertNotNull(block.getMessagesRetractedAt());
		verify(notifier).settled(block.getCode());
		assertEquals(WaitingConditionsActionAck.Outcome.NOT_FOUND, waiting.abandon(block.getCode(), OWNER, false).getOutcome(),
				"no longer open");
		assertTrue(waiting.waitingFor(OWNER).isEmpty());
	}

	@Test
	void aBlockBeingRespawnedCannotBeAbandonedNorCanAnotherUsers() {
		final GNetworkOfAgentsSuspension block = block(question("year", true));
		assertEquals(WaitingConditionsActionAck.Outcome.REFUSED, waiting.abandon(block.getCode(), "someone", false).getOutcome());
		store.claim(block.getCode(), SESSION, new Date(clock.millis()), config.getDispatchLeaseMillis());

		final WaitingConditionsActionAck ack = waiting.abandon(block.getCode(), "admin", true);

		assertEquals(WaitingConditionsActionAck.Outcome.REFUSED, ack.getOutcome());
		assertEquals(WaitingConditionsServiceImpl.RESPAWNING, ack.getReason());
		assertTrue(block.isOpen());
	}

	// ---- expiries ---------------------------------------------------------------

	@Test
	void anExpiryThatUnblocksResolvesTheConditionAndTheRunResumesAnotherTellsTheOwnerOnce() {
		final AgentSuspendedWaitingCondition<?, ?> unblocking = question("year", true);
		unblocking.setExpiry(ConditionExpiry.afterMinutes(new Date(clock.millis()), 10L, true));
		final AgentSuspendedWaitingCondition<?, ?> stuck = question("country", true);
		stuck.setExpiry(ConditionExpiry.afterMinutes(new Date(clock.millis()), 20L, false));
		final GNetworkOfAgentsSuspension block = block(unblocking, stuck);

		events.sweep();
		assertTrue(respawns.isEmpty(), "nothing is due yet");
		verify(notifier, never()).expired(any(), any(), any(), any());

		clock.advance(10 * MINUTE);
		events.sweep();

		assertTrue(respawns.isEmpty(), "the year expired and goes on, the country is still mandatory");
		final GConditionEvent expired = store.events.get(SESSION + ":year");
		assertEquals(ConditionEventStatus.ACCEPTED, expired.getStatus());
		assertEquals(UnansweredBecause.EXPIRED, expired.getUnanswered());
		assertEquals(WaitingConditionsServiceImpl.PLATFORM, expired.getSourceSystem());
		final WaitingBlockView view = waiting.allWaiting().get(0);
		assertTrue(view.getConditions().get(0).isSolved());
		assertFalse(view.isExpired(), "the country is not due yet");

		clock.advance(10 * MINUTE);
		events.sweep();
		events.sweep();

		assertTrue(respawns.isEmpty());
		verify(notifier, times(1)).expired(eq(block.getCode()), eq(OWNER), eq(SESSION), eq(List.of("Which year?")));
		assertTrue(waiting.allWaiting().get(0).isExpired());
		assertTrue(waiting.allWaiting().get(0).getConditions().get(1).isExpired());

		assertEquals(WaitingConditionsActionAck.Outcome.DONE,
				waiting.goOnWithout(block.getCode(), null, OWNER, false).getOutcome());
		assertEquals(1, respawns.size());
		final List<AgentSuspendedWaitingConditionReactionDto> reactions = respawns.get(0)
				.getNetworkOfAgentsSuspendedStateReaction().getReactions();
		assertEquals(UnansweredBecause.EXPIRED, reactions.get(0).getUnanswered());
		assertEquals(UnansweredBecause.GONE_ON_WITHOUT, reactions.get(1).getUnanswered());
		events.sweep();
		verify(notifier).settled(block.getCode());
		assertNotNull(block.getMessagesRetractedAt());
	}

	// ---- unwatched runs ---------------------------------------------------------

	@Test
	void theOwnerOfAnUnwatchedRunIsToldOnceAndTheMessageIsTakenBackWhenItCloses() {
		final GNetworkOfAgentsSuspension block = block(question("year", true));
		block.setUnwatched(true);

		waiting.tellOwners();
		waiting.tellOwners();

		verify(notifier, times(1)).unwatched(block.getCode(), OWNER, SESSION, List.of("Which year?"));
		verify(notifier, never()).settled(any());
		assertTrue(waiting.waitingFor(OWNER).get(0).isUnwatched());

		block.setRespawnedAt(new Date(clock.millis()));
		waiting.tellOwners();
		waiting.tellOwners();

		verify(notifier, times(1)).settled(block.getCode());
	}

	@Test
	void aWatchedRunWithNoExpiryTellsNobody() {
		block(question("year", true));

		waiting.tellOwners();
		waiting.applyExpiries();

		verify(notifier, never()).unwatched(any(), any(), any(), any());
		verify(notifier, never()).expired(any(), any(), any(), any());
		assertTrue(waiting.waitingFor("someone").isEmpty());
		assertEquals(1, waiting.waitingFor(OWNER).size());
		assertEquals(OWNER, waiting.allWaiting().get(0).getOwner());
	}

	// -----------------------------------------------------------------------------

	private GNetworkOfAgentsSuspension block(AgentSuspendedWaitingCondition<?, ?>... conditions) {
		final GNetworkOfAgentsSuspension block = new GNetworkOfAgentsSuspension();
		block.setCode(UUID.randomUUID().toString());
		block.setConversationCode(SESSION);
		block.setSuspendedAt(new Date(clock.millis()));
		final SuspendedAgentState agent = new SuspendedAgentState();
		agent.setAgentConfigCode("the-agent");
		for (AgentSuspendedWaitingCondition<?, ?> condition : conditions) {
			agent.getWaitingConditions().add(WaitingCondition.of(condition));
		}
		block.getSuspendedAgents().add(agent);
		block.setEarliestExpiryAt(block.earliestExpiryOfConditions());
		blocks.put(block.getCode(), block);
		return block;
	}

	private static AgentSuspendedWaitingCondition<?, ?> question(String id, boolean mandatory) {
		final ClarificationOptions options = new ClarificationOptions();
		options.setQuestion("Which year?");
		options.getOptions().add(ClarificationOption.of("2023"));
		options.getOptions().add(ClarificationOption.of("2024"));
		final AgentSuspendedWaitingCondition condition = new AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions>(
				ClarificationAnswer.class, "Which year?", "it matters", null, mandatory, ClarificationOptions.class,
				options);
		condition.setSuspendedConditionId(id);
		return condition;
	}

	private static ConditionEvent choice(String correlationId, String option) {
		final ConditionEvent event = new ConditionEvent();
		event.setCorrelationId(correlationId);
		event.setConditionType(ClarificationConditionTransportTranslator.ID);
		event.setAttributes(new LinkedHashMap<String, Object>(
				Map.of(ClarificationConditionTransportTranslator.CHOSEN_OPTION, option)));
		event.setSourceSystem("ERP");
		return event;
	}

	private static NetworkOfAgentsSuspendedStateReaction chatGoingOnWithout(GNetworkOfAgentsSuspension block,
			String conditionId) {
		final AgentSuspendedWaitingConditionReactionDto answer = new AgentSuspendedWaitingConditionReactionDto();
		answer.setConditionId(conditionId);
		answer.setConditionType(ClarificationConditionTransportTranslator.ID);
		answer.setAttributes(new LinkedHashMap<String, Object>(
				Map.of(GAbstractConditionTransportTranslator.GO_ON_WITHOUT, Boolean.TRUE)));
		final NetworkOfAgentsSuspendedStateReaction reaction = new NetworkOfAgentsSuspendedStateReaction();
		reaction.setNetworkOfAgentsBlockId(block.getCode());
		reaction.setReactions(new ArrayList<AgentSuspendedWaitingConditionReactionDto>(List.of(answer)));
		return reaction;
	}

	private static ConditionEventCaller application() {
		return ConditionEventCaller.of("erp-integration", Set.of("APPLICATION"), ConditionEventCaller.REST);
	}

	private static final class MutableClock extends Clock {
		private long millis = 1_700_000_000_000L;

		void advance(long by) {
			millis += by;
		}

		@Override
		public long millis() {
			return millis;
		}

		@Override
		public Instant instant() {
			return Instant.ofEpochMilli(millis);
		}

		@Override
		public ZoneId getZone() {
			return ZoneId.of("UTC");
		}

		@Override
		public Clock withZone(ZoneId zone) {
			return this;
		}
	}
}
