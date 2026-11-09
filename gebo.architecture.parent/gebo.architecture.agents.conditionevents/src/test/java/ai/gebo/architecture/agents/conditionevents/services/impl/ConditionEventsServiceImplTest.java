package ai.gebo.architecture.agents.conditionevents.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import ai.gebo.architecture.agents.conditionevents.ConditionCorrelationId;
import ai.gebo.architecture.agents.conditionevents.config.ConditionEventsConfig;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEvent;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventAck;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventAck.Outcome;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventCaller;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventStatus;
import ai.gebo.architecture.agents.conditionevents.model.GConditionDispatch;
import ai.gebo.architecture.agents.conditionevents.model.GConditionEvent;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.ClarificationAnswer;
import ai.gebo.architecture.agents.model.ClarificationOption;
import ai.gebo.architecture.agents.model.ClarificationOptions;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.SuspendedAgentState;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.WaitingCondition;
import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.chat.pipelines.service.IAgentSuspendedWaitingConditionTransportTranslatorRepositoryPattern;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineService;
import ai.gebo.llms.chat.pipelines.service.impl.ClarificationConditionTransportTranslator;
import ai.gebo.llms.chat.pipelines.service.impl.DefaultConditionTransportTranslator;
import ai.gebo.llms.chat.pipelines.service.impl.NetworkOfAgentsSuspensionTransport;

/**
 * Pins the events layer end to end on what it uses as it is: the blocks, read
 * only, the translators of the conditions, the pipelines' respawn. Each event is
 * kept and matched to its session's open block, the block is respawned once
 * solved, as its session's owner, unattended, and the block tells how it went.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
class ConditionEventsServiceImplTest {
	private static final String SESSION = "session-1";
	private static final String OWNER = "owner";

	private final Map<String, GNetworkOfAgentsSuspension> blocks = new LinkedHashMap<String, GNetworkOfAgentsSuspension>();
	private final List<GeboChatRequest> respawns = new ArrayList<GeboChatRequest>();
	private final List<String> respawnedAs = new ArrayList<String>();
	private final MutableClock clock = new MutableClock();
	private final ConditionEventsConfig config = new ConditionEventsConfig();
	private InMemoryConditionEventsStore store;
	private ConditionEventsServiceImpl service;
	/** What a respawn does to the block it respawns: by default it consumes it. */
	private Consumer<GNetworkOfAgentsSuspension> respawn = block -> block.setRespawnedAt(new Date(clock.millis()));

	@BeforeEach
	void setUp() throws Exception {
		final GNetworkOfAgentsSuspensionRepository blocksRepository = mock(GNetworkOfAgentsSuspensionRepository.class);
		when(blocksRepository.findOpenByConversationCode(anyString())).thenAnswer(i -> blocks.values().stream()
				.filter(b -> i.getArgument(0).equals(b.getConversationCode()) && b.isOpen())
				.max((a, b) -> a.getSuspendedAt().compareTo(b.getSuspendedAt())));
		when(blocksRepository.findById(anyString())).thenAnswer(i -> Optional.ofNullable(blocks.get(i.getArgument(0))));

		final GUserChatSession session = new GUserChatSession();
		session.setCode(SESSION);
		session.setUsername(OWNER);
		session.setChatProfileCode("profile-1");
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
			respawnedAs.add(SecurityContextHolder.getContext().getAuthentication().getName());
			respawn.accept(blocks.get(request.getNetworkOfAgentsSuspendedStateReaction().getNetworkOfAgentsBlockId()));
			return null;
		});

		final UserDetailsService users = mock(UserDetailsService.class);
		when(users.loadUserByUsername(OWNER)).thenReturn(User.withUsername(OWNER).password("x").authorities("USER").build());
		final ObjectProvider<UserDetailsService> accounts = mock(ObjectProvider.class);
		when(accounts.getIfAvailable()).thenReturn(users);

		store = new InMemoryConditionEventsStore(blocks::values);
		final ConditionEventMatcher matcher = new ConditionEventMatcher(store, transport, config);
		final ConditionEventTexts texts = new ConditionEventTexts(
				List.of(new DefaultConditionEventTextRenderer(), new ClarificationConditionEventTextRenderer()));
		final ConditionEventDispatcher dispatcher = new ConditionEventDispatcher(store, matcher, blocksRepository,
				sessions, transport, texts, pipelines, accounts, config, clock);
		service = new ConditionEventsServiceImpl(store, matcher, dispatcher, sessions, blocksRepository,
				new ConditionEventsAuthorizationImpl(config), config, Runnable::run, clock);
	}

	@Test
	void anEventSolvingTheBlockRespawnsItAsTheSessionOwnerUnattended() {
		final GNetworkOfAgentsSuspension block = block(question("year", true, "2023", "2024"));

		final ConditionEventAck ack = service.deliver(choice(SESSION + ":year", "2024"), application());

		assertEquals(Outcome.ACCEPTED, ack.getOutcome());
		assertEquals(1, respawns.size(), "the block is solved: it is respawned");
		final GeboChatRequest request = respawns.get(0);
		assertEquals(List.of(OWNER), respawnedAs, "the respawn runs as the session's owner, whoever delivered");
		assertEquals(SESSION, request.getUserChatContextCode());
		assertEquals("profile-1", request.getChatProfileCode());
		assertEquals(ConditionEventDispatcher.RUN_ORIGIN, request.getRunOrigin());
		assertFalse(request.getAttended());
		assertEquals(block.getCode(), request.getNetworkOfAgentsSuspendedStateReaction().getNetworkOfAgentsBlockId());
		final AgentSuspendedWaitingConditionReactionDto reaction = request.getNetworkOfAgentsSuspendedStateReaction()
				.getReactions().get(0);
		assertEquals("year", reaction.getConditionId());
		assertEquals(ClarificationConditionTransportTranslator.ID, reaction.getConditionType());
		assertEquals("2024", reaction.getAttributes().get(ClarificationConditionTransportTranslator.CHOSEN_OPTION));
		assertEquals("Which year?\n→ 2024 (from ERP)", request.getQuery(),
				"the text is made from the solved condition when the event carries none");
		final GConditionEvent kept = store.events.get(SESSION + ":year");
		assertEquals(ConditionEventStatus.APPLIED, kept.getStatus());
		assertEquals(GConditionDispatch.State.DONE, store.dispatches.get(block.getCode()).getState());
	}

	@Test
	void anOptionNotOfferedIsRefusedWhenItArrivesAndNothingIsRespawned() {
		block(question("year", true, "2023", "2024"));

		final ConditionEventAck ack = service.deliver(choice(SESSION + ":year", "1999"), application());

		assertEquals(Outcome.REFUSED, ack.getOutcome());
		assertTrue(ack.getReason().contains("1999"), ack.getReason());
		assertTrue(respawns.isEmpty());
		assertEquals(ConditionEventStatus.REFUSED, store.events.get(SESSION + ":year").getStatus());
	}

	@Test
	void aBlockWaitsForEveryMandatoryConditionAndTheEventsComeFromDifferentSourcesInEitherForm() {
		block(question("year", true, "2023", "2024"), approval("approval", true));

		assertEquals(Outcome.ACCEPTED, service.deliver(choice(SESSION + ":year", "2024"), application()).getOutcome());
		assertTrue(respawns.isEmpty(), "the approval is still waited for");

		final ConditionEvent approved = value(null, "approved");
		approved.setSessionCode(SESSION);
		approved.setConditionId("approval");
		approved.setSourceSystem("WORKFLOW");
		approved.setText("The manager approved the offer.");
		assertEquals(Outcome.ACCEPTED, service.deliver(approved, owner()).getOutcome());

		assertEquals(1, respawns.size());
		final List<AgentSuspendedWaitingConditionReactionDto> reactions = respawns.get(0)
				.getNetworkOfAgentsSuspendedStateReaction().getReactions();
		assertEquals(List.of("year", "approval"), reactions.stream().map(r -> r.getConditionId()).toList(),
				"in the order the block asked them");
		assertEquals("Which year?\n→ 2024 (from ERP)\n\n[from WORKFLOW] The manager approved the offer.",
				respawns.get(0).getQuery(), "what a source says stands for its condition, marked with the source");
	}

	@Test
	void anEventBeforeItsBlockIsPendingAndIsMatchedOnceTheBlockIsWritten() {
		final ConditionEventAck early = service.deliver(choice(SESSION + ":year", "2024"), application());
		assertEquals(Outcome.PENDING, early.getOutcome());
		assertTrue(respawns.isEmpty());

		// the run that asked ends and its block is written
		block(question("year", true, "2023", "2024"));
		clock.advance(30_000L);
		service.sweep();

		assertEquals(1, respawns.size());
		assertEquals(ConditionEventStatus.APPLIED, store.events.get(SESSION + ":year").getStatus());
	}

	@Test
	void aPendingEventExpiresPastItsTimeToLive() {
		service.deliver(choice(SESSION + ":year", "2024"), application());

		clock.advance(config.getPendingTimeToLiveMillis() + 1);
		service.sweep();

		assertEquals(ConditionEventStatus.EXPIRED, store.events.get(SESSION + ":year").getStatus());
	}

	@Test
	void aConditionOfAClosedBlockIsStale() {
		final GNetworkOfAgentsSuspension closed = block(question("year", true, "2023", "2024"));
		closed.setRespawnedAt(new Date(clock.millis()));

		final ConditionEventAck ack = service.deliver(choice(SESSION + ":year", "2024"), application());

		assertEquals(Outcome.STALE, ack.getOutcome());
		assertTrue(ack.getReason().startsWith(ConditionEventMatcher.BLOCK_RESPAWNED));
		assertTrue(respawns.isEmpty());
	}

	@Test
	void aRepeatedEventIsADuplicateAnOlderOneIsSupersededAndOneAfterTheRespawnIsStale() {
		block(question("year", true, "2023", "2024"), approval("approval", true));
		final ConditionEvent first = choice(SESSION + ":year", "2023");
		first.setSourceEventId("evt-1");
		first.setSourceTimestamp(new Date(1_000L));
		service.deliver(first, application());

		assertEquals(Outcome.DUPLICATE, service.deliver(first, application()).getOutcome());
		final ConditionEvent older = choice(SESSION + ":year", "2024");
		older.setSourceEventId("evt-0");
		older.setSourceTimestamp(new Date(500L));
		assertEquals(Outcome.SUPERSEDED, service.deliver(older, application()).getOutcome());
		final ConditionEvent newer = choice(SESSION + ":year", "2024");
		newer.setSourceEventId("evt-2");
		newer.setSourceTimestamp(new Date(2_000L));
		assertEquals(Outcome.ACCEPTED, service.deliver(newer, application()).getOutcome(),
				"the condition changed again before the respawn: the later value is kept");

		service.deliver(value(SESSION + ":approval", "approved"), application());
		assertEquals("2024", respawns.get(0).getNetworkOfAgentsSuspendedStateReaction().getReactions().get(0)
				.getAttributes().get(ClarificationConditionTransportTranslator.CHOSEN_OPTION));
		assertEquals(Outcome.STALE, service.deliver(choice(SESSION + ":year", "2023"), application()).getOutcome());
		assertEquals(5, store.events.get(SESSION + ":year").getDeliveries(), "every delivery is counted");
	}

	@Test
	void aCallerNeitherOwnerNorApplicationAccountIsRefusedAndNothingIsKept() {
		block(question("year", true, "2023", "2024"));

		final ConditionEventAck ack = service.deliver(choice(SESSION + ":year", "2024"),
				ConditionEventCaller.of("somebody-else", Set.of("USER"), ConditionEventCaller.REST));

		assertEquals(Outcome.REFUSED, ack.getOutcome());
		assertEquals(ConditionEventsAuthorizationImpl.NOT_ALLOWED, ack.getReason());
		assertTrue(store.events.isEmpty());
		assertTrue(service.status(new ConditionCorrelationId(SESSION, "year"),
				ConditionEventCaller.of("somebody-else", Set.of("USER"), ConditionEventCaller.REST)).isEmpty());
	}

	@Test
	void theBrokerIsATrustedChannel() {
		block(question("year", true, "2023", "2024"));
		final ConditionEvent event = choice(SESSION + ":year", "2024");
		event.setSourceSystem(null);

		final ConditionEventAck ack = service.deliver(event,
				ConditionEventCaller.trustedChannel(null, ConditionEventCaller.MESSAGE, "CRM.bridge"));

		assertEquals(Outcome.ACCEPTED, ack.getOutcome());
		assertEquals("CRM.bridge", store.events.get(SESSION + ":year").getSourceSystem());
	}

	@Test
	void theTwoFormsOfTheCorrelationMustAgree() {
		final ConditionEvent event = choice(SESSION + ":year", "2024");
		event.setSessionCode(SESSION);
		event.setConditionId("another");

		final ConditionEventAck ack = service.deliver(event, application());

		assertEquals(Outcome.REFUSED, ack.getOutcome());
		assertTrue(ack.getReason().startsWith(ConditionCorrelationId.FORMS_DISAGREE));
	}

	@Test
	void theSessionNamedWhenARequestHasNoneAddressesNothing() {
		final ConditionEventAck ack = service.deliver(
				choice(ConditionEventsServiceImpl.NO_CONTEXT_SESSION + ":year", "2024"), application());

		assertEquals(Outcome.REFUSED, ack.getOutcome());
	}

	@Test
	void aFailedRespawnIsTriedAgainAfterItsBackoffThenGivesUp() {
		config.setMaxAttempts(2);
		final GNetworkOfAgentsSuspension block = block(question("year", true, "2023", "2024"));
		respawn = b -> {
			throw new IllegalStateException("The chat runs another request");
		};

		service.deliver(choice(SESSION + ":year", "2024"), application());

		assertEquals(1, respawns.size());
		final GConditionDispatch dispatch = store.dispatches.get(block.getCode());
		assertEquals(GConditionDispatch.State.FAILED, dispatch.getState());
		assertEquals(ConditionEventStatus.ACCEPTED, store.events.get(SESSION + ":year").getStatus(),
				"accepted again, for the next attempt");

		clock.advance(config.getRetryBackoffMillis() / 2);
		service.sweep();
		assertEquals(1, respawns.size(), "the backoff is not over");

		clock.advance(config.getRetryBackoffMillis());
		service.sweep();
		assertEquals(2, respawns.size());
		assertEquals(GConditionDispatch.State.GAVE_UP, dispatch.getState());
		assertEquals(ConditionEventStatus.FAILED, store.events.get(SESSION + ":year").getStatus());
		assertTrue(block.isOpen(), "the block is left as the failed respawns left it");

		// a new delivery of the condition tries the block afresh
		respawn = b -> b.setRespawnedAt(new Date(clock.millis()));
		assertEquals(Outcome.ACCEPTED, service.deliver(choice(SESSION + ":year", "2023"), application()).getOutcome());
		assertEquals(3, respawns.size());
		assertEquals(ConditionEventStatus.APPLIED, store.events.get(SESSION + ":year").getStatus());
	}

	@Test
	void aBlockOfOptionalConditionsIsRespawnedOnceItSettled() {
		block(question("year", false, "2023", "2024"), approval("approval", false));

		service.deliver(choice(SESSION + ":year", "2024"), application());
		assertTrue(respawns.isEmpty(), "the other condition may still come");

		clock.advance(config.getOptionalSettleMillis());
		service.sweep();

		assertEquals(1, respawns.size());
		assertEquals(1, respawns.get(0).getNetworkOfAgentsSuspendedStateReaction().getReactions().size());
	}

	@Test
	void aRespawnSuspendingAgainLetsTheEventsOfTheNewBlockRespawnIt() {
		final GNetworkOfAgentsSuspension first = block(question("year", true, "2023", "2024"));
		// the approval is asked by the respawned run: its event comes before its block
		service.deliver(value(SESSION + ":approval", "approved"), application());
		respawn = b -> {
			if (b == first) {
				clock.advance(1L);
				final GNetworkOfAgentsSuspension next = block(approval("approval", true));
				b.setRespawnedAt(new Date(clock.millis()));
				b.setRespawnedToSuspensionCode(next.getCode());
			} else {
				b.setRespawnedAt(new Date(clock.millis()));
			}
		};

		service.deliver(choice(SESSION + ":year", "2024"), application());

		assertEquals(2, respawns.size(), "the second block was solved by the event that came early");
		final GConditionEvent year = store.events.get(SESSION + ":year");
		assertEquals(ConditionEventStatus.APPLIED, year.getStatus());
		assertEquals(respawns.get(1).getNetworkOfAgentsSuspendedStateReaction().getNetworkOfAgentsBlockId(),
				year.getRespawnedToBlockCode());
		assertEquals(ConditionEventStatus.APPLIED, store.events.get(SESSION + ":approval").getStatus());
	}

	@Test
	void theOwnerSeesWhatBecameOfItsEvent() {
		block(question("year", true, "2023", "2024"));
		service.deliver(choice(SESSION + ":year", "2024"), owner());

		assertEquals(ConditionEventStatus.APPLIED,
				service.status(ConditionCorrelationId.parse(SESSION + ":year"), owner()).get().getStatus());
		assertNull(store.events.get(SESSION + ":year").getReason());
	}

	// ----------------------------------------------------------------------------

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
		blocks.put(block.getCode(), block);
		return block;
	}

	private static AgentSuspendedWaitingCondition<?, ?> question(String id, boolean mandatory, String... labels) {
		final ClarificationOptions options = new ClarificationOptions();
		options.setQuestion("Which year?");
		for (String label : labels) {
			options.getOptions().add(ClarificationOption.of(label));
		}
		final AgentSuspendedWaitingCondition condition = new AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions>(
				ClarificationAnswer.class, "Which year?", "it matters", null, mandatory, ClarificationOptions.class,
				options);
		condition.setSuspendedConditionId(id);
		return condition;
	}

	private static AgentSuspendedWaitingCondition<?, ?> approval(String id, boolean mandatory) {
		final AgentSuspendedWaitingCondition condition = new AgentSuspendedWaitingCondition<String, Object>(String.class,
				"Manager approval of the offer", "the offer cannot go out without it", null, mandatory);
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

	private static ConditionEvent value(String correlationId, Object value) {
		final ConditionEvent event = new ConditionEvent();
		event.setCorrelationId(correlationId);
		event.setConditionType(DefaultConditionTransportTranslator.ID);
		event.setAttributes(new LinkedHashMap<String, Object>(Map.of(DefaultConditionTransportTranslator.VALUE, value)));
		return event;
	}

	private static ConditionEventCaller application() {
		return ConditionEventCaller.of("erp-integration", Set.of("APPLICATION"), ConditionEventCaller.REST);
	}

	private static ConditionEventCaller owner() {
		return ConditionEventCaller.of(OWNER, Set.of("USER"), ConditionEventCaller.REST);
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
			return ZoneOffset.UTC;
		}

		@Override
		public Clock withZone(ZoneId zone) {
			return this;
		}
	}
}
