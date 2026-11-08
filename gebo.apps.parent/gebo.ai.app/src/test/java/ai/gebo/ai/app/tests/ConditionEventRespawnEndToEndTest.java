/*
 * Copyright (c) 2025+ Gebo.ai
 * Licensed under the Mozilla Public License 2.0 (MPL 2.0) with Data Protection Clauses.
 * See LICENSE file in the project root for full license information.
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 */
package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.Duration;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.agents.conditionevents.ConditionCorrelationId;
import ai.gebo.architecture.agents.conditionevents.messaging.ConditionEventsPublisher;
import ai.gebo.architecture.agents.conditionevents.messaging.ConditionEventsReceiverFactory;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEvent;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventCaller;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventStatus;
import ai.gebo.architecture.agents.conditionevents.model.GConditionDispatch;
import ai.gebo.architecture.agents.conditionevents.model.GConditionEvent;
import ai.gebo.architecture.agents.conditionevents.repository.GConditionDispatchRepository;
import ai.gebo.architecture.agents.conditionevents.repository.GConditionEventRepository;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventsPublisher;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.architecture.agents.tools.AskUserSingleChoiceQuestionTool;
import ai.gebo.llms.abstraction.layer.tests.TestChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedState;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineService;
import ai.gebo.security.services.impl.LocalJwtTokenProvider;

/**
 * An external system solves a suspended network of agents, through each of the
 * channels the events layer takes: the whole cycle, with only the model scripted.
 * <p>
 * The chat runs through the pipeline service as the client does, on the agentic
 * loop network, whose agent mounts the user questions library. The scripted model
 * calls {@code askUserSingleChoiceQuestion}: the platform's tool calling runs the
 * tool, the agent suspends and the block is written. The answer then comes, naming
 * the condition by session code and condition id:
 * <ul>
 * <li>through REST, with a personal API key generated over HTTP;</li>
 * <li>through the message broker, sent by the platform's publisher
 * ({@link IConditionEventsPublisher}) to the events layer's receiver.</li>
 * </ul>
 * The events layer matches the answer to the open block, respawns the network as
 * the conversation's user, and the agent, now told the answer, goes on. The event
 * ends APPLIED, its dispatch DONE, the block respawned, the chat has the answer;
 * the same event sent again is recognised as a duplicate.
 */
@TestPropertySource(properties = { "ai.gebo.agents.standard.enabled=true",
		"ai.gebo.agents.standard.default-chat-network-of-agents=AGENTIC_LOOP_AGENTS_NETWORK",
		// Test-only signing key: the test context configures none, and the tokens below must be verifiable.
		"ai.gebo.security.auth.token-secret=dGVzdC1vbmx5LXNpZ25pbmcta2V5LWZvci10aGUtY29uZGl0aW9uLWV2ZW50cy10ZXN0LW5ldmVyLWEtcmVhbC1zZWNyZXQtMDEyMzQ1Njc4OQ==" })
public class ConditionEventRespawnEndToEndTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final String GENERATE_KEY = "/api/users/GeneratedUserApiKeyController/generateUserGeneratedApiKey";
	private static final String DELETE_KEY = "/api/users/GeneratedUserApiKeyController/deleteUserGeneratedApiKey";
	private static final String DELIVER = "/api/users/NetworkOfAgentsConditionEventsController/deliverConditionEvent";
	private static final String STATUS = "/api/users/NetworkOfAgentsConditionEventsController/conditionEventStatus";

	private static final String ANSWER_MARKER = "GEBO_TEST_CONDITION_EVENT_ANSWER";
	private static final String USER_QUESTION = "Please answer in English, write me a short holiday note about the country I prefer.";
	private static final String THE_QUESTION = "Which country do you prefer for your holiday note: Italy or France?";
	/** How the respawned agent is told the answer to its question ({@code ResolvedConditionText}). */
	private static final String TOLD_THE_ANSWER = "You asked the user:";
	private static final String TOLD_THE_CHOICE = "The user chose: ";
	/** The marker of the router's request understanding prompt (its system prompt's first line). */
	private static final String REWRITE_PROMPT_MARKER = "Minimal Question Rewriter";
	private static final String TITLE_PROMPT_MARKER = "summarization assistant";
	private static final Duration RESPAWN_TIMEOUT = Duration.ofSeconds(120);

	/** A condition the network waits for, as the client reads it. */
	private record Suspended(String sessionCode, String conditionId, String blockCode) {
		String correlationId() {
			return new ConditionCorrelationId(sessionCode, conditionId).composed();
		}
	}

	@Autowired
	private IChatPipelineService chatPipelineService;
	@Autowired
	private IGChatSessionLifeCycleService lifeCycleService;
	@Autowired
	private GUserChatSessionRepository sessionsRepo;
	@Autowired
	private GNetworkOfAgentsSuspensionRepository blocks;
	@Autowired
	private GConditionEventRepository events;
	@Autowired
	private GConditionDispatchRepository dispatches;
	@Autowired
	private IGMessageBroker broker;
	@Autowired
	private IConditionEventsPublisher publisher;
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private FilterChainProxy securityFilters;
	@Autowired
	private LocalJwtTokenProvider tokens;
	/** A mapper of its own: the test context has no ObjectMapper bean (dates go as epoch millis). */
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	protected void beforeEachCallback() throws Exception {
		// the agent, not yet told the answer, asks the question through its tool
		TestChatModel.setGlobalToolCallLogic(prompt -> {
			if (prompt == null || !prompt.contains(USER_QUESTION) || prompt.contains(TOLD_THE_ANSWER)
					|| prompt.contains(REWRITE_PROMPT_MARKER) || prompt.contains(TITLE_PROMPT_MARKER)) {
				return null;
			}
			return List.of(new AssistantMessage.ToolCall("call-condition-events-1", "function",
					AskUserSingleChoiceQuestionTool.TOOL_NAME, questionArguments()));
		});
		TestChatModel.setGlobalResponseLogic(prompt -> {
			if (prompt == null) {
				return "";
			}
			if (prompt.contains(TITLE_PROMPT_MARKER)) {
				return "Holiday note";
			}
			if (prompt.contains(REWRITE_PROMPT_MARKER)) {
				return "deliverable=QA\nrewrittenQuery=" + USER_QUESTION + "\nuserRequiredLanguage=English";
			}
			if (prompt.contains(TOLD_THE_ANSWER)) {
				final String chosen = prompt.contains(TOLD_THE_CHOICE + "Italy") ? "Italy"
						: prompt.contains(TOLD_THE_CHOICE + "France") ? "France" : "an unknown country";
				return ANSWER_MARKER + ": a short holiday note about " + chosen + ".";
			}
			return "";
		});
	}

	@Override
	protected void afterEachCallback() throws Exception {
		TestChatModel.clearGlobalToolCallLogic();
		TestChatModel.clearGlobalResponseLogic();
	}

	private String questionArguments() {
		try {
			return objectMapper.writeValueAsString(Map.of("question", THE_QUESTION, //
					"whyNeeded", "The note is about one country only.", //
					"options", List.of(Map.of("label", "Italy", "description", "The note is about Italy."),
							Map.of("label", "France", "description", "The note is about France.")),
					"customAnswerAllowed", false, //
					"mandatory", true));
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Requests go through the security filters on the test's thread, which clear its
	 * security context when they end: the test's identity is put back after each one.
	 */
	private MockMvc mvc() {
		return MockMvcBuilders.webAppContextSetup(context).addFilters(securityFilters)
				.alwaysDo(result -> impersonateAdminUser()).build();
	}

	/** Waits for what runs apart (the broker's delivery, the respawn) to come about. */
	private <T> T await(String what, Supplier<T> probe) throws InterruptedException {
		final long deadline = System.currentTimeMillis() + RESPAWN_TIMEOUT.toMillis();
		T value = probe.get();
		while (value == null && System.currentTimeMillis() < deadline) {
			Thread.sleep(500);
			value = probe.get();
		}
		assertNotNull(value, "timed out waiting for: " + what);
		return value;
	}

	/** The chat: the agent asks its question and the network suspends. */
	private Suspended aSuspendedChat() throws Exception {
		final GeboChatRequest request = new GeboChatRequest();
		request.setQuery(USER_QUESTION);
		lifeCycleService.createChatSession(request);
		final String sessionCode = request.getUserChatContextCode();
		assertNotNull(sessionCode, "the lifecycle service assigns the session");
		final List<GeboChatMessageEnvelope> streamed = chatPipelineService
				.streamingChat(null, request, new LinkedHashMap<String, Object>()).collectList()
				.block(Duration.ofSeconds(120));
		assertNotNull(streamed, "the pipeline answers the first request");
		final Optional<NetworkOfAgentsSuspendedState> pending = chatPipelineService
				.pendingNetworkOfAgentsSuspension(sessionCode);
		assertTrue(pending.isPresent(), "the agent's question suspended the network and the block was written");
		final NetworkOfAgentsSuspendedState state = pending.get();
		assertEquals(1, state.getConditions().size(), "one condition is waited for");
		assertEquals("clarification", state.getConditions().get(0).getConditionType());
		final Suspended suspended = new Suspended(sessionCode, state.getConditions().get(0).getConditionId(),
				state.getNetworkOfAgentsBlockId());
		assertNotNull(suspended.conditionId());
		return suspended;
	}

	private ConditionEvent answer(Suspended suspended, String chosen, String sourceSystem, String sourceEventId) {
		final ConditionEvent event = new ConditionEvent();
		event.setSessionCode(suspended.sessionCode());
		event.setConditionId(suspended.conditionId());
		event.setConditionType("clarification");
		event.setAttributes(new LinkedHashMap<String, Object>(Map.of("chosenOption", chosen)));
		event.setSourceSystem(sourceSystem);
		event.setSourceEventId(sourceEventId);
		event.setSourceTimestamp(new Date());
		return event;
	}

	/** The respawn the event brought about: event APPLIED, dispatch DONE, block respawned, the answer in the chat. */
	private GConditionEvent verifyApplied(Suspended suspended, String chosen) throws InterruptedException {
		final GConditionEvent applied = await("the event applied",
				() -> events.findById(suspended.correlationId())
						.filter(e -> e.getStatus() == ConditionEventStatus.APPLIED
								|| e.getStatus() == ConditionEventStatus.FAILED
								|| e.getStatus() == ConditionEventStatus.STALE)
						.orElse(null));
		assertEquals(ConditionEventStatus.APPLIED, applied.getStatus(),
				"the respawn solved the block: " + applied.getReason());
		final GConditionDispatch dispatch = dispatches.findById(suspended.blockCode()).orElse(null);
		assertNotNull(dispatch, "the respawn was claimed once, by block");
		assertEquals(GConditionDispatch.State.DONE, dispatch.getState(),
				"the dispatch is done: " + dispatch.getLastFailure());
		final GNetworkOfAgentsSuspension block = blocks.findById(suspended.blockCode()).orElseThrow();
		assertNotNull(block.getRespawnedAt(), "the block is marked respawned");
		assertFalse(chatPipelineService.pendingNetworkOfAgentsSuspension(suspended.sessionCode()).isPresent(),
				"no block waits any more");
		final GUserChatSession chat = await("the respawn's answer in the chat", () -> {
			final GUserChatSession saved = sessionsRepo.findById(suspended.sessionCode()).orElse(null);
			return saved != null && saved.getInteractions() != null
					&& saved.getInteractions().stream().anyMatch(this::answeredByTheRespawn) ? saved : null;
		});
		final ChatInteractions answered = chat.getInteractions().stream().filter(this::answeredByTheRespawn)
				.findFirst().orElseThrow();
		LOGGER.info("The respawned network answered: " + answered.getResponse().getQueryResponse());
		assertTrue(String.valueOf(answered.getResponse().getQueryResponse()).contains(chosen),
				"the agent was told the answer the event carried");
		return applied;
	}

	private boolean answeredByTheRespawn(ChatInteractions interaction) {
		return interaction.getResponse() != null
				&& String.valueOf(interaction.getResponse().getQueryResponse()).contains(ANSWER_MARKER);
	}

	@Test
	public void anExternalSystemSolvesTheConditionThroughRest() throws Exception {
		final MockMvc mvc = mvc();
		final String session = tokens.createToken(DEFAULT_ALL_ROLES_USER,
				new Date(System.currentTimeMillis() + 600_000));
		// the external system's credential: a personal API key, generated over HTTP
		final MockHttpServletResponse generated = mvc.perform(post(GENERATE_KEY)
				.header("Authorization", "Bearer " + session).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(Map.of("description", "Condition events test key",
						"expiration", System.currentTimeMillis() + 3_600_000))))
				.andReturn().getResponse();
		assertEquals(200, generated.getStatus(), "the user generates a personal API key");
		final JsonNode key = objectMapper.readTree(generated.getContentAsString());
		final String apiKey = key.path("apiKey").asText(null);
		assertNotNull(apiKey, "the key is returned");
		Suspended suspended = null;
		try {
			suspended = aSuspendedChat();
			final String body = objectMapper
					.writeValueAsString(answer(suspended, "Italy", "condition-events-rest-test", "evt-rest-1"));
			final MockHttpServletResponse delivered = mvc.perform(post(DELIVER)
					.header("Authorization", "Bearer " + apiKey).contentType(MediaType.APPLICATION_JSON).content(body))
					.andReturn().getResponse();
			assertEquals(200, delivered.getStatus(), "the event is taken: " + delivered.getContentAsString());
			final JsonNode ack = objectMapper.readTree(delivered.getContentAsString());
			LOGGER.info("Condition event acknowledged: " + ack);
			assertEquals("ACCEPTED", ack.path("outcome").asText(), "the event matches the open block: " + ack);
			assertEquals(suspended.correlationId(), ack.path("correlationId").asText(),
					"both forms of the id are kept");

			final GConditionEvent applied = verifyApplied(suspended, "Italy");
			assertEquals(ConditionEventCaller.REST, applied.getChannel(), "it came through REST");
			assertEquals(DEFAULT_ALL_ROLES_USER, applied.getActor(), "sent by the key's user");

			// the status is readable through REST
			final JsonNode view = objectMapper.readTree(mvc
					.perform(get(STATUS).param("sessionCode", suspended.sessionCode())
							.param("conditionId", suspended.conditionId()).header("Authorization", "Bearer " + apiKey))
					.andReturn().getResponse().getContentAsString());
			assertEquals("APPLIED", view.path("status").asText(), "the status reads APPLIED: " + view);
			assertEquals(suspended.correlationId(), view.path("correlationId").asText());

			// the same event sent again changes nothing
			final JsonNode again = objectMapper.readTree(mvc.perform(post(DELIVER)
					.header("Authorization", "Bearer " + apiKey).contentType(MediaType.APPLICATION_JSON).content(body))
					.andReturn().getResponse().getContentAsString());
			assertEquals("DUPLICATE", again.path("outcome").asText(), "a redelivery is recognised: " + again);
		} finally {
			if (suspended != null) {
				lifeCycleService.removeChatSession(suspended.sessionCode());
			}
			mvc.perform(post(DELETE_KEY).param("code", key.path("code").asText())
					.header("Authorization", "Bearer " + session));
		}
	}

	@Test
	public void aPlatformComponentSolvesTheConditionThroughTheBroker() throws Exception {
		assertTrue(broker.isRegistered((ConditionEventsPublisher) publisher),
				"the platform's publisher is registered with the broker");
		assertTrue(broker.checkReceivingComponentPresent(ConditionEventsReceiverFactory.MODULE_ID,
				ConditionEventsReceiverFactory.SYSTEM_ID), "the events layer's receiver is registered with the broker");
		Suspended suspended = null;
		try {
			suspended = aSuspendedChat();
			final ConditionEvent event = answer(suspended, "France", null, "evt-broker-1");
			assertNotNull(publisher.publish(event, DEFAULT_ALL_ROLES_USER), "the message sent is named");

			final GConditionEvent applied = verifyApplied(suspended, "France");
			assertEquals(ConditionEventCaller.MESSAGE, applied.getChannel(), "it came through the broker");
			assertEquals(DEFAULT_ALL_ROLES_USER, applied.getActor(), "sent on behalf of the envelope's user");
			assertEquals(ConditionEventsReceiverFactory.MODULE_ID + "." + ConditionEventsPublisher.SYSTEM_ID,
					applied.getSourceSystem(), "with no source of its own, the sending component is the source");

			// the same event sent again changes nothing
			publisher.publish(event, DEFAULT_ALL_ROLES_USER);
			final String correlationId = suspended.correlationId();
			await("the redelivery counted", () -> events.findById(correlationId)
					.filter(e -> e.getDeliveries() >= 2).orElse(null));
			assertEquals(ConditionEventStatus.APPLIED, events.findById(correlationId).orElseThrow().getStatus(),
					"a redelivery changes nothing");
		} finally {
			if (suspended != null) {
				lifeCycleService.removeChatSession(suspended.sessionCode());
			}
		}
	}
}
