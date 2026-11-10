package ai.gebo.architecture.agents.conditionevents.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventView;
import ai.gebo.architecture.agents.conditionevents.model.GConditionDispatch;
import ai.gebo.architecture.agents.conditionevents.model.GConditionEvent;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.ClarificationAnswer;
import ai.gebo.architecture.agents.model.ClarificationOption;
import ai.gebo.architecture.agents.model.ClarificationOptions;
import ai.gebo.architecture.agents.model.ConditionSource;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.SuspendedAgentState;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.WaitingCondition;
import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedStateReaction;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.chat.pipelines.service.IAgentSuspendedWaitingConditionTransportTranslatorRepositoryPattern;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineService;
import ai.gebo.llms.chat.pipelines.service.RecordedConditionReaction;
import ai.gebo.llms.chat.pipelines.service.SolvedCondition;
import ai.gebo.llms.chat.pipelines.service.impl.GAbstractConditionTransportTranslator;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ClarificationReactionAttributes;
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
	private ConditionEventsReactionRecorder recorder;
	private NetworkOfAgentsSuspensionTransport transport;
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
		// the conversation was opened by the ERP with its own key of the dialogue
		session.setExternalSystem("ERP");
		session.setExternalSessionKey("order-7");
		when(sessions.findByExternalSystemAndExternalSessionKey(anyString(), anyString())).thenAnswer(
				i -> "ERP".equals(i.getArgument(0)) && "order-7".equals(i.getArgument(1)) ? Optional.of(session)
						: Optional.empty());

		final ClarificationConditionTransportTranslator clarification = new ClarificationConditionTransportTranslator();
		final IAgentSuspendedWaitingConditionTransportTranslatorRepositoryPattern translators = mock(
				IAgentSuspendedWaitingConditionTransportTranslatorRepositoryPattern.class);
		when(translators.findTranslator(any()))
				.thenAnswer(i -> clarification.canTranslate(i.getArgument(0)) ? clarification : null);
		transport = new NetworkOfAgentsSuspensionTransport(translators);

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
		recorder = new ConditionEventsReactionRecorder(service, store, dispatcher, transport, Runnable::run);
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

	// ---- the chat answering one condition at a time ---------------------------

	@Test
	void aChatAnswerJoinsTheAnswerAnotherSourceGaveAndTheBlockResumes() {
		final GNetworkOfAgentsSuspension block = block(question("year", true, "2023", "2024"), approval("approval", true));
		final ConditionEvent approved = value(SESSION + ":approval", "approved");
		approved.setSourceSystem("WORKFLOW");
		assertEquals(Outcome.ACCEPTED, service.deliver(approved, application()).getOutcome());
		assertEquals(block.getCode(), store.events.get(SESSION + ":approval").getBlockCode(),
				"another source already answered the block");

		final RecordedConditionReaction recorded = recorder.record(block, chatChoosing(block, "year", "2024"), OWNER,
				"request-1");

		assertEquals(List.of(new RecordedConditionReaction.Answer("year", "ACCEPTED", null)), recorded.answers());
		assertTrue(recorded.waitingFor().isEmpty());
		assertTrue(recorded.resumes(), "the chat request runs the respawn itself");
		assertEquals(List.of("year", "approval"), recorded.reaction().getReactions().stream()
				.map(r -> r.getConditionId()).toList(), "both answers, in the order the block asked them");
		assertEquals(block.getCode(), recorded.reaction().getNetworkOfAgentsBlockId());
		assertEquals(ConditionEventStatus.DISPATCHED, store.events.get(SESSION + ":year").getStatus());
		assertEquals("request-1", store.events.get(SESSION + ":approval").getDispatchedRequestId());
		assertEquals(ConditionEventCaller.CHAT, store.events.get(SESSION + ":year").getChannel());
		assertEquals(ConditionEventsReactionRecorder.SOURCE_SYSTEM, store.events.get(SESSION + ":year").getSourceSystem());

		service.sweep();
		assertTrue(respawns.isEmpty(), "the dispatcher stands off while the chat holds the respawn");

		// the chat's run respawns the block, then says it ended
		block.setRespawnedAt(new Date(clock.millis()));
		recorder.concluded(block, "request-1");

		assertEquals(ConditionEventStatus.APPLIED, store.events.get(SESSION + ":year").getStatus());
		assertEquals(ConditionEventStatus.APPLIED, store.events.get(SESSION + ":approval").getStatus());
		assertEquals(GConditionDispatch.State.DONE, store.dispatches.get(block.getCode()).getState());
		assertTrue(respawns.isEmpty(), "respawned once, by the chat");
	}

	@Test
	void theChatCompletingTheBlockDeclinesTheOptionalConditionsNoneAnswered() {
		final GNetworkOfAgentsSuspension block = block(question("year", true, "2023", "2024"), approval("approval", false));

		final RecordedConditionReaction recorded = recorder.record(block, chatChoosing(block, "year", "2024"), OWNER,
				"request-1");

		assertTrue(recorded.resumes());
		final AgentSuspendedWaitingConditionReactionDto declined = recorded.reaction().getReactions().get(1);
		assertEquals("approval", declined.getConditionId());
		assertEquals(DefaultConditionTransportTranslator.ID, declined.getConditionType());
		assertEquals(Boolean.TRUE, declined.getAttributes().get(GAbstractConditionTransportTranslator.DECLINED));
		assertNull(store.events.get(SESSION + ":approval"), "a declined condition is no event of any source");
	}

	@Test
	void aRespawnAnotherHoldsLeavesTheChatToTellTheUser() {
		final GNetworkOfAgentsSuspension block = block(question("year", true, "2023", "2024"));
		store.claim(block.getCode(), SESSION, new Date(clock.millis()), config.getDispatchLeaseMillis());

		final RecordedConditionReaction recorded = recorder.record(block, chatChoosing(block, "year", "2024"), OWNER,
				"request-1");

		assertEquals(RecordedConditionReaction.Next.RESPAWNING_APART, recorded.next());
		assertNull(recorded.reaction());
		assertEquals(ConditionEventStatus.ACCEPTED, store.events.get(SESSION + ":year").getStatus(),
				"kept for whoever holds the respawn");
	}

	@Test
	void aChatRespawnLeavingTheBlockOpenIsTriedAgainByTheDispatcherAfterItsBackoff() {
		final GNetworkOfAgentsSuspension block = block(question("year", true, "2023", "2024"));
		assertTrue(recorder.record(block, chatChoosing(block, "year", "2024"), OWNER, "request-1").resumes());

		// the chat's run failed: the block is still open
		recorder.concluded(block, "request-1");

		assertEquals(GConditionDispatch.State.FAILED, store.dispatches.get(block.getCode()).getState());
		assertEquals(ConditionEventStatus.ACCEPTED, store.events.get(SESSION + ":year").getStatus());
		clock.advance(config.getRetryBackoffMillis());
		service.sweep();
		assertEquals(1, respawns.size(), "tried again unattended, as the session's owner");
		assertEquals(ConditionEventStatus.APPLIED, store.events.get(SESSION + ":year").getStatus());
	}

	@Test
	void aRequestThatNoLongerHoldsTheRespawnConcludesNothing() {
		final GNetworkOfAgentsSuspension block = block(question("year", true, "2023", "2024"));
		recorder.record(block, chatChoosing(block, "year", "2024"), OWNER, "request-1");

		recorder.concluded(block, "another-request");

		assertEquals(GConditionDispatch.State.RUNNING, store.dispatches.get(block.getCode()).getState());
		assertEquals(ConditionEventStatus.DISPATCHED, store.events.get(SESSION + ":year").getStatus());
	}

	@Test
	void theConditionsAnotherSourceSolvedAreMarkedWithItsSource() {
		final GNetworkOfAgentsSuspension block = block(question("year", true, "2023", "2024"), approval("approval", true));
		final ConditionEvent approved = value(SESSION + ":approval", "approved");
		approved.setSourceSystem("WORKFLOW");
		service.deliver(approved, application());

		final List<SolvedCondition> solved = recorder.solvedConditionsOf(block);

		assertEquals(1, solved.size());
		assertEquals("approval", solved.get(0).conditionId());
		assertEquals("WORKFLOW", solved.get(0).sourceSystem());
		assertEquals("erp-integration", solved.get(0).actor());
		assertNotNull(solved.get(0).solvedAt());
	}

	@Test
	void aChatAnswerLeavingAMandatoryConditionIsKeptAndTheBlockWaitsForIt() {
		final GNetworkOfAgentsSuspension block = block(question("year", true, "2023", "2024"), approval("approval", true));
		assertTrue(store.liveEventsOf(SESSION).isEmpty(), "no source answered the block yet");

		final RecordedConditionReaction recorded = recorder.record(block, chatChoosing(block, "year", "2024"), OWNER,
				"request-1");

		assertEquals(List.of("Manager approval of the offer"), recorded.waitingFor());
		assertFalse(recorded.resumes());
		assertTrue(respawns.isEmpty(), "no run before the approval");
		assertEquals(ConditionEventStatus.ACCEPTED, store.events.get(SESSION + ":year").getStatus(),
				"the chat's answer is kept");

		assertEquals(Outcome.ACCEPTED, service.deliver(value(SESSION + ":approval", "approved"), application()).getOutcome());
		assertEquals(1, respawns.size(), "the approval completes the block");
	}

	@Test
	void theSameChatRequestSentAgainChangesNothing() {
		final GNetworkOfAgentsSuspension block = block(question("year", true, "2023", "2024"), approval("approval", true));
		recorder.record(block, chatChoosing(block, "year", "2024"), OWNER, "request-1");

		final RecordedConditionReaction again = recorder.record(block, chatChoosing(block, "year", "2024"), OWNER,
				"request-1");

		assertEquals("DUPLICATE", again.answers().get(0).outcome());
		assertEquals(2, store.events.get(SESSION + ":year").getDeliveries(), "delivered twice, kept once");
		assertEquals(ConditionEventStatus.ACCEPTED, store.events.get(SESSION + ":year").getStatus());
	}

	@Test
	void anAnswerTheConditionDoesNotTakeIsRefusedWithItsReason() {
		final GNetworkOfAgentsSuspension block = block(question("year", true, "2023", "2024"));
		final NetworkOfAgentsSuspendedStateReaction reaction = chatChoosing(block, "year", "1999");
		reaction.getReactions().add(reaction.getReactions().get(0));
		reaction.getReactions().add(new AgentSuspendedWaitingConditionReactionDto());

		final RecordedConditionReaction recorded = recorder.record(block, reaction, OWNER, "request-1");

		assertTrue(recorded.anyRefused());
		assertEquals(List.of(RecordedConditionReaction.REFUSED, RecordedConditionReaction.REFUSED,
				RecordedConditionReaction.REFUSED), recorded.answers().stream().map(a -> a.outcome()).toList());
		assertTrue(recorded.answers().get(0).reason().contains("1999"), recorded.answers().get(0).reason());
		assertEquals(ConditionEventsReactionRecorder.ANSWERED_TWICE, recorded.answers().get(1).reason());
		assertEquals(ConditionEventsReactionRecorder.NOT_NAMED, recorded.answers().get(2).reason());
		assertEquals(List.of("Which year?"), recorded.waitingFor());
		assertTrue(respawns.isEmpty());
	}

	@Test
	void onlyTheConversationsUserAnswersFromItsChat() {
		final GNetworkOfAgentsSuspension block = block(question("year", true, "2023", "2024"));

		final RecordedConditionReaction recorded = recorder.record(block, chatChoosing(block, "year", "2024"),
				"someone-else", "request-1");

		assertTrue(recorded.answers().get(0).isRefused());
		assertFalse(recorded.resumes());
		assertTrue(respawns.isEmpty());
	}

	private static NetworkOfAgentsSuspendedStateReaction chatChoosing(GNetworkOfAgentsSuspension block,
			String conditionId, String option) {
		final AgentSuspendedWaitingConditionReactionDto answer = new AgentSuspendedWaitingConditionReactionDto();
		answer.setConditionId(conditionId);
		answer.setConditionType(ClarificationConditionTransportTranslator.ID);
		answer.setAttributes(new LinkedHashMap<String, Object>(
				Map.of(ClarificationConditionTransportTranslator.CHOSEN_OPTION, option)));
		final NetworkOfAgentsSuspendedStateReaction reaction = new NetworkOfAgentsSuspendedStateReaction();
		reaction.setNetworkOfAgentsBlockId(block.getCode());
		reaction.setReactions(new ArrayList<AgentSuspendedWaitingConditionReactionDto>(List.of(answer)));
		return reaction;
	}

	// ----------------------------------------------------------------------------

	@Test
	void anAnswerGivenTypedIsKeptAsItsAttributesAndRespawnsTheBlock() {
		block(question("year", true, "2023", "2024"));
		final ConditionEvent typed = choice(SESSION + ":year", "2023");
		typed.setAttributes(null);
		final ClarificationReactionAttributes answer = new ClarificationReactionAttributes();
		answer.setChosenOption("2024");
		typed.setClarificationAttributes(answer);

		final ConditionEventAck ack = service.deliver(typed, application());

		assertEquals(Outcome.ACCEPTED, ack.getOutcome());
		assertEquals("2024", store.events.get(SESSION + ":year").getAttributes()
				.get(ClarificationConditionTransportTranslator.CHOSEN_OPTION));
		assertEquals(1, respawns.size());
		assertEquals("2024", respawns.get(0).getNetworkOfAgentsSuspendedStateReaction().getReactions().get(0)
				.getAttributes().get(ClarificationConditionTransportTranslator.CHOSEN_OPTION));
	}

	@Test
	void anAnswerGivenTypedStandsForTheChoiceOfTheMapAndIsCheckedAsIt() {
		block(question("year", true, "2023", "2024"));
		final ConditionEvent typed = choice(SESSION + ":year", "2023");
		typed.getAttributes().put("ticket", "T-12");
		final ClarificationReactionAttributes answer = new ClarificationReactionAttributes();
		answer.setChosenOption("1999");
		typed.setClarificationAttributes(answer);

		final ConditionEventAck ack = service.deliver(typed, application());

		assertEquals(Outcome.REFUSED, ack.getOutcome(), "the typed choice is the one checked, not the map's");
		assertTrue(ack.getReason().contains("1999"), ack.getReason());
		assertEquals("T-12", store.events.get(SESSION + ":year").getAttributes().get("ticket"),
				"the other attributes are kept");
		assertTrue(respawns.isEmpty());
	}

	@Test
	void anEventMayNameTheSessionByTheKeyTheOtherSystemOpenedItWith() {
		block(question("year", true, "2023", "2024"));
		final ConditionEvent byKey = choice(null, "2024");
		byKey.setExternalSystem(" ERP ");
		byKey.setExternalSessionKey("order-7");
		byKey.setConditionId("year");

		final ConditionEventAck ack = service.deliver(byKey, application());

		assertEquals(Outcome.ACCEPTED, ack.getOutcome(), ack.getReason());
		assertEquals(SESSION + ":year", ack.getCorrelationId(), "resolved to Gebo's own session");
		final GConditionEvent kept = store.events.get(SESSION + ":year");
		assertEquals("ERP", kept.getExternalSystem());
		assertEquals("order-7", kept.getExternalSessionKey());
		final ConditionEventView view = service.status(new ConditionCorrelationId(SESSION, "year"), owner()).orElseThrow();
		assertEquals("ERP", view.getExternalSystem());
		assertEquals("order-7", view.getExternalSessionKey());
		assertEquals(1, respawns.size());
		assertEquals(Optional.of(SESSION), service.sessionCodeOf("ERP", "order-7"));
		assertEquals(Optional.empty(), service.sessionCodeOf("ERP", null), "half a key names nothing");

		final ConditionEvent unknown = choice(null, "2024");
		unknown.setExternalSystem("ERP");
		unknown.setExternalSessionKey("order-8");
		unknown.setConditionId("year");
		final ConditionEventAck refused = service.deliver(unknown, application());
		assertEquals(Outcome.REFUSED, refused.getOutcome());
		assertTrue(refused.getReason().startsWith(ConditionEventsServiceImpl.NO_SUCH_EXTERNAL_SESSION), refused.getReason());

		final ConditionEvent both = choice(SESSION + ":year", "2024");
		both.setExternalSystem("ERP");
		both.setExternalSessionKey("order-8");
		assertEquals(Outcome.STALE, service.deliver(both, application()).getOutcome(),
				"Gebo's own name wins over the key: the condition is the one already applied");
	}

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

	@Test
	void aSourceSayingWhoItIsIsKeptAndTheRespawnTellsIt() {
		final GNetworkOfAgentsSuspension block = block(question("year", true, "2023", "2024"));
		final ConditionEvent event = choice(SESSION + ":year", "2024");
		event.setSource(anna());

		assertEquals(Outcome.ACCEPTED, service.deliver(event, application()).getOutcome());

		final GConditionEvent kept = store.events.get(SESSION + ":year");
		assertEquals("arossi", kept.getSource().getId());
		assertEquals("erp-session-7", kept.getSource().getSessionCode());
		assertTrue(kept.getSource().isVerified(), "an application account delivers for others");
		assertEquals(ConditionEventCaller.REST, kept.getSource().getChannel());
		assertEquals("ERP", kept.getSourceSystem());
		assertEquals("erp-integration", kept.getActor(), "who delivered it is kept apart");
		assertEquals("arossi", ConditionEventView.of(kept).getSource().getId());
		assertEquals(1, respawns.size());
		final ConditionSource told = respawns.get(0).getNetworkOfAgentsSuspendedStateReaction().getReactions().get(0)
				.getSource();
		assertEquals("Anna Rossi (ERP)", told.label(), "the respawn says who answered");
		assertEquals("Which year?\n→ 2024 (from Anna Rossi (ERP))", respawns.get(0).getQuery());
		assertEquals(block.getCode(), kept.getDispatchCode());
	}

	@Test
	void aSourceClaimedByAnotherAccountIsKeptUnverified() {
		block(question("year", true, "2023", "2024"));
		final ConditionEvent event = choice(SESSION + ":year", "2024");
		final ConditionSource claimed = anna();
		claimed.setVerified(true);
		event.setSource(claimed);

		service.deliver(event, owner());

		final ConditionSource kept = store.events.get(SESSION + ":year").getSource();
		assertFalse(kept.isVerified(), "the owner is not Anna");
		assertEquals("Anna Rossi (ERP), unverified", kept.label());
		assertEquals("Anna Rossi (ERP), unverified", respawns.get(0).getNetworkOfAgentsSuspendedStateReaction()
				.getReactions().get(0).getSource().label());
	}

	@Test
	void anEventClaimingNoSourceIsToldByItsDelivererAndTheHistoryAsAlways() {
		block(question("year", true, "2023", "2024"));

		service.deliver(choice(SESSION + ":year", "2024"), application());

		final ConditionSource source = respawns.get(0).getNetworkOfAgentsSuspendedStateReaction().getReactions().get(0)
				.getSource();
		assertEquals("erp-integration (ERP)", source.label());
		assertEquals("Which year?\n→ 2024 (from ERP)", respawns.get(0).getQuery());
	}

	@Test
	void theChatsAnswersAreTheConversationsUserTheOthersKeepTheirSource() {
		final GNetworkOfAgentsSuspension block = block(question("year", true, "2023", "2024"), approval("approval", true));
		final ConditionEvent approved = value(SESSION + ":approval", "approved");
		approved.setSourceSystem("WORKFLOW");
		service.deliver(approved, application());

		final RecordedConditionReaction recorded = recorder.record(block, chatChoosing(block, "year", "2024"), OWNER,
				"request-1");

		final ConditionSource year = recorded.reaction().getReactions().get(0).getSource();
		assertTrue(year.isConversationUser(), "the chat's answer is the conversation's user");
		assertEquals(OWNER, year.getId());
		assertEquals(SESSION, year.getSessionCode());
		final ConditionSource approval = recorded.reaction().getReactions().get(1).getSource();
		assertFalse(approval.isConversationUser());
		assertEquals("erp-integration (WORKFLOW)", approval.label());
	}

	@Test
	void theFormShowsYouForTheChatsAnswersAndTheSourceForOthers() {
		final GNetworkOfAgentsSuspension block = block(question("year", true, "2023", "2024"), approval("approval", true),
				approval("budget", true));
		final ConditionEvent approved = value(SESSION + ":approval", "approved");
		approved.setSource(anna());
		service.deliver(approved, application());
		recorder.record(block, chatChoosing(block, "year", "2024"), OWNER, "request-1");

		final List<SolvedCondition> solved = recorder.solvedConditionsOf(block);

		assertEquals(List.of("year", "approval"), solved.stream().map(SolvedCondition::conditionId).toList());
		assertEquals(SolvedCondition.YOU, solved.get(0).solvedByLabel());
		assertEquals("Anna Rossi (ERP)", solved.get(1).solvedByLabel());
	}

	private static ConditionSource anna() {
		final ConditionSource source = ConditionSource.of(ConditionSource.Kind.PERSON, "ERP", "arossi", "Anna Rossi");
		source.setSessionCode("erp-session-7");
		return source;
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
