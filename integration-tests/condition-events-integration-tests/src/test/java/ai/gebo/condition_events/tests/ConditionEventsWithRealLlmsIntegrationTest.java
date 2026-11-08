/*
 * Copyright (c) 2025+ Gebo.ai
 * Licensed under the Mozilla Public License 2.0 (MPL 2.0) with Data Protection Clauses.
 * See LICENSE file in the project root for full license information.
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 */
package ai.gebo.condition_events.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.architecture.agents.conditionevents.ConditionCorrelationId;
import ai.gebo.architecture.agents.conditionevents.messaging.ConditionEventPayload;
import ai.gebo.architecture.agents.conditionevents.messaging.ConditionEventsReceiverFactory;
import ai.gebo.architecture.integration.tests.AbstractVendorSetupAndUseTest;
import ai.gebo.architecture.integration.tests.model.TestGeboSystemInfo;
import ai.gebo.architecture.integration.tests.preconditions.RequiresConfig;
import ai.gebo.monolithic.api.client.api.GeboChatPipelinesControllerApi;
import ai.gebo.monolithic.api.client.api.GeboRagChatControllerApi;
import ai.gebo.monolithic.api.client.api.GeboUserChatsControllerApi;
import ai.gebo.monolithic.api.client.api.NetworkOfAgentsConditionEventsControllerApi;
import ai.gebo.monolithic.api.client.invoker.ApiClient;
import ai.gebo.monolithic.api.client.model.AgentSuspendedWaitingConditionDto;
import ai.gebo.monolithic.api.client.model.ChatInteractions;
import ai.gebo.monolithic.api.client.model.ConditionEvent;
import ai.gebo.monolithic.api.client.model.ConditionEventAck;
import ai.gebo.monolithic.api.client.model.ConditionEventView;
import ai.gebo.monolithic.api.client.model.GChatProfileConfiguration;
import ai.gebo.monolithic.api.client.model.GeboChatRequest;
import ai.gebo.monolithic.api.client.model.GeboChatResponse;
import ai.gebo.monolithic.api.client.model.NetworkOfAgentsSuspendedState;
import ai.gebo.monolithic.api.client.model.PipelineRequestBody;
import ai.gebo.monolithic.api.client.model.UserChatHistory;
import ai.gebo.monolithic.app.Main;

/**
 * External conditions solved for a real model, through each channel of the
 * condition events layer.
 * <p>
 * The installation is set up from {@code FullSetupSecret} as in the full-setup
 * suite (admin account, LLM vendor and its models); the default chat network is
 * the agentic loop network, whose agent mounts the user questions library. A chat
 * through the REST API asks for a holiday note on a country the model cannot know
 * and tells it to ask: when the model calls its question tool the network suspends
 * and the pending block names the conditions. Each condition is then answered with
 * the first option offered:
 * <ul>
 * <li>through REST, with the generated Java client
 * ({@link NetworkOfAgentsConditionEventsControllerApi});</li>
 * <li>through the message broker, from a component registered as an emitter of
 * {@link ConditionEventPayload} (the test's own: no platform component emits it
 * yet), to the events layer's receiver.</li>
 * </ul>
 * What the platform must do fails the test: the event is accepted, the network is
 * respawned as the conversation's user, the event ends APPLIED and the chat gets an
 * answer after the respawn. What only the model decides is reported: whether it
 * calls the question tool at all (tried with a few wordings), whether its answer
 * names the option chosen, whether it asks again after the answer. Set
 * {@code ai.gebo.tests.conditionevents.strictModelBehaviour=true} to fail on those
 * too; otherwise a model that never asks skips the test instead of failing it.
 */
@SpringBootTest(classes = Main.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "ai.gebo.agents.standard.enabled=true",
		"ai.gebo.agents.standard.default-chat-network-of-agents=AGENTIC_LOOP_AGENTS_NETWORK" })
@Import(ConditionEventsWithRealLlmsIntegrationTest.EmitterConfiguration.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiresConfig(value = AbstractVendorSetupAndUseTest.FULL_SETUP_ENVIRONMENT_JSON_STRING,
		description = "JSON with the admin account, the LLM vendor and its API key")
public class ConditionEventsWithRealLlmsIntegrationTest extends AbstractVendorSetupAndUseTest {
	private static final String STRICT_MODEL_BEHAVIOUR = "ai.gebo.tests.conditionevents.strictModelBehaviour";
	private static final String CLARIFICATION = "clarification";
	/** A real model's respawn: a few model calls, well within this. */
	private static final long RESPAWN_TIMEOUT_MILLIS = 5 * 60 * 1000L;
	private static final Set<String> SETTLED = Set.of("APPLIED", "FAILED", "STALE", "EXPIRED", "REFUSED");

	/**
	 * Requests a model cannot fulfil without asking, from plain to imperative: models
	 * differ in how readily they ask.
	 */
	private static final List<String> ASKING_REQUESTS = List.of(
			"Write me a short holiday note about the country I prefer between Italy and France. You do not know"
					+ " which one I prefer: ask me with your question tool before writing anything, do not choose for me.",
			"I need a short holiday note about my preferred country, Italy or France. Before writing anything,"
					+ " use the askUserSingleChoiceQuestion tool to ask me which of the two I prefer, with the two"
					+ " countries as options. Do not guess and do not write the note yet.",
			"Your very first action must be your own function call to askUserSingleChoiceQuestion, asking me"
					+ " whether my holiday note should be about Italy or France, with exactly those two options."
					+ " Do not write anything else before I answer.");

	/**
	 * A component of the platform sending condition events through the broker: the
	 * broker routes a message only from a registered emitter of its payload type.
	 */
	public static class TestConditionEventsEmitter implements IGMessageEmitter {
		public static final String MODULE_ID = "TEST-CONDITION-EVENTS-SOURCE";
		public static final String SYSTEM_ID = "TEST-CONDITION-EVENTS-EMITTER";

		@Override
		public String getMessagingModuleId() {
			return MODULE_ID;
		}

		@Override
		public String getMessagingSystemId() {
			return SYSTEM_ID;
		}

		@Override
		public SystemComponentType getComponentType() {
			return SystemComponentType.APPLICATION_COMPONENT;
		}

		@Override
		public List<String> getEmittedPayloadTypes() {
			return List.of(ConditionEventPayload.class.getName());
		}
	}

	@TestConfiguration
	public static class EmitterConfiguration {
		@Bean
		public TestConditionEventsEmitter testConditionEventsEmitter() {
			return new TestConditionEventsEmitter();
		}
	}

	/** A condition the network waits for, with the option the test answers it with. */
	private record Waiting(String sessionCode, String conditionId, String chosen) {
		String correlationId() {
			return new ConditionCorrelationId(sessionCode, conditionId).composed();
		}
	}

	@Autowired
	private IGMessageBroker broker;
	@Autowired
	private IMessageEnvelopeFactory envelopes;
	@Autowired
	private TestConditionEventsEmitter emitter;

	/** The installation, set up once for the class. */
	private static TestGeboSystemInfo systemInfo = null;
	private static String chatProfileCode = null;

	private boolean strict() {
		return Boolean.parseBoolean(environment.getProperty(STRICT_MODEL_BEHAVIOUR, "false"));
	}

	/** A model's own doing, which varies between runs: reported unless this run means to pin it. */
	private void reportModelBehaviour(String what, boolean asExpected) {
		if (asExpected) {
			LOGGER.info("The model did what the test hopes for: " + what);
			return;
		}
		if (strict()) {
			fail("The model did not: " + what);
		}
		LOGGER.warn("The model did not do what the test hopes for, which is its own decision: " + what);
	}

	private ApiClient installation() throws Exception {
		if (systemInfo == null) {
			systemInfo = executeSystemSetupBySecret();
			assertNotNull(systemInfo, "the setup of the installation under test");
			// as the full-setup suite: the models' autoconfiguration settles
			Thread.sleep(30000);
		}
		final ApiClient apiClient = createApiClient(systemInfo.getHost(), systemInfo.getPort(),
				systemInfo.getSecurityHeader());
		renew(apiClient);
		if (chatProfileCode == null) {
			final List<GChatProfileConfiguration> profiles = new GeboRagChatControllerApi(apiClient).getChatProfiles();
			assertFalse(profiles == null || profiles.isEmpty(), "the installation must have a chat profile");
			chatProfileCode = profiles.get(0).getCode();
		}
		return apiClient;
	}

	/**
	 * A chat whose model asked: each wording is tried in a chat of its own until the
	 * network suspends on a clarification. Skips (or fails, when strict) when the
	 * model never asks.
	 */
	private List<Waiting> aChatTheModelSuspends(ApiClient apiClient) {
		final GeboChatPipelinesControllerApi pipelines = new GeboChatPipelinesControllerApi(apiClient);
		for (String query : ASKING_REQUESTS) {
			final PipelineRequestBody body = new PipelineRequestBody();
			final GeboChatRequest request = new GeboChatRequest();
			request.setQuery(query);
			request.setChatProfileCode(chatProfileCode);
			body.setRequest(request);
			final GeboChatResponse response = pipelines.executeDefaultChatPipeline(body);
			assertNotNull(response, "the pipeline answers");
			final String sessionCode = response.getUserChatContextCode();
			assertNotNull(sessionCode, "the chat has a context");
			LOGGER.info("Asked: " + query + "\nRouting: " + response.getPipelineRouterDecisionCode() + "\nAnswered: "
					+ response.getQueryResponse());
			final NetworkOfAgentsSuspendedState state = pipelines.pendingNetworkOfAgentsSuspension(sessionCode);
			if (state == null || state.getConditions() == null || state.getConditions().isEmpty()) {
				reportModelBehaviour("ask with its question tool on: " + query, false);
				continue;
			}
			final List<Waiting> waiting = new ArrayList<>();
			for (AgentSuspendedWaitingConditionDto condition : state.getConditions()) {
				LOGGER.info("The network waits for condition:" + condition.getConditionId() + " of type:"
						+ condition.getConditionType() + " " + condition.getAttributes());
				assertEquals(CLARIFICATION, condition.getConditionType(),
						"the question tool suspends on a clarification");
				waiting.add(new Waiting(sessionCode, condition.getConditionId(), firstOptionOf(condition)));
			}
			reportModelBehaviour("ask with its question tool, " + waiting.size() + " condition(s)", true);
			return waiting;
		}
		if (strict()) {
			fail("The model never called its question tool, in " + ASKING_REQUESTS.size() + " wordings");
		}
		Assumptions.abort("The model never called its question tool, in " + ASKING_REQUESTS.size()
				+ " wordings: nothing to solve (set " + STRICT_MODEL_BEHAVIOUR + "=true to fail instead)");
		return List.of();
	}

	@SuppressWarnings("unchecked")
	private static String firstOptionOf(AgentSuspendedWaitingConditionDto condition) {
		final Object options = condition.getAttributes() != null ? condition.getAttributes().get("options") : null;
		assertTrue(options instanceof List<?> list && !list.isEmpty(),
				"the clarification offers options: " + condition.getAttributes());
		final Object first = ((List<Object>) options).get(0);
		final Object label = first instanceof Map<?, ?> map ? map.get("label") : null;
		assertNotNull(label, "each option has a label: " + first);
		return String.valueOf(label);
	}

	private ConditionEvent answerTo(Waiting waiting, String sourceSystem, String sourceEventId) {
		final ConditionEvent event = new ConditionEvent();
		event.setSessionCode(waiting.sessionCode());
		event.setConditionId(waiting.conditionId());
		event.setConditionType(CLARIFICATION);
		final Map<String, Object> attributes = new LinkedHashMap<>();
		attributes.put("chosenOption", waiting.chosen());
		event.setAttributes(attributes);
		event.setSourceSystem(sourceSystem);
		event.setSourceEventId(sourceEventId);
		event.setSourceTimestamp(new Date());
		return event;
	}

	/** The event's status, read through REST until settled. */
	private ConditionEventView settled(ApiClient apiClient, Waiting waiting) throws Exception {
		final NetworkOfAgentsConditionEventsControllerApi conditionEvents = new NetworkOfAgentsConditionEventsControllerApi(
				apiClient);
		final long deadline = System.currentTimeMillis() + RESPAWN_TIMEOUT_MILLIS;
		ConditionEventView view = conditionEvents.conditionEventStatus(waiting.correlationId(), null, null);
		while ((view == null || view.getStatus() == null || !SETTLED.contains(view.getStatus().getValue()))
				&& System.currentTimeMillis() < deadline) {
			Thread.sleep(2000);
			view = conditionEvents.conditionEventStatus(waiting.correlationId(), null, null);
		}
		assertNotNull(view, "the event is known by its correlation id");
		LOGGER.info("Condition event " + waiting.correlationId() + " settled as: " + view.getStatus() + " "
				+ (view.getReason() != null ? view.getReason() : ""));
		return view;
	}

	/**
	 * Everything the platform must do once the conditions are answered: each event
	 * applied, the chat answered after the respawn, and what the model did with the
	 * answer reported.
	 */
	private void verifyRespawned(ApiClient apiClient, List<Waiting> waiting, String expectedChannelReport)
			throws Exception {
		for (Waiting each : waiting) {
			final ConditionEventView view = settled(apiClient, each);
			assertEquals("APPLIED", String.valueOf(view.getStatus()),
					"the respawn solved the block (" + expectedChannelReport + "): " + view.getReason());
			assertEquals(each.correlationId(), view.getCorrelationId(), "both forms of the id are kept");
		}
		final String sessionCode = waiting.get(0).sessionCode();
		final UserChatHistory history = new GeboUserChatsControllerApi(apiClient).getChatHistory(sessionCode);
		assertNotNull(history, "the chat is readable");
		final List<ChatInteractions> interactions = history.getInteractions();
		assertTrue(interactions != null && interactions.size() >= 2,
				"the respawn added its interaction to the chat: " + (interactions != null ? interactions.size() : 0));
		final ChatInteractions last = interactions.get(interactions.size() - 1);
		final String answer = last.getResponse() != null ? String.valueOf(last.getResponse().getQueryResponse())
				: null;
		assertNotNull(answer, "the respawned network answered");
		assertFalse(answer.isBlank(), "with some text");
		LOGGER.info("The respawned network answered (" + expectedChannelReport + "): " + answer);
		final String chosen = waiting.get(0).chosen();
		reportModelBehaviour("name the option chosen (" + chosen + ") in its answer",
				answer.toLowerCase().contains(chosen.toLowerCase()));
		final NetworkOfAgentsSuspendedState again = new GeboChatPipelinesControllerApi(apiClient)
				.pendingNetworkOfAgentsSuspension(sessionCode);
		reportModelBehaviour("go on without asking again",
				again == null || again.getConditions() == null || again.getConditions().isEmpty());
	}

	private void inTheChat(ApiClient apiClient, String sessionCode, Consumer<String> work) {
		try {
			work.accept(sessionCode);
		} finally {
			try {
				new GeboUserChatsControllerApi(apiClient).deleteChat(sessionCode);
			} catch (Exception e) {
				LOGGER.warn("The test chat " + sessionCode + " could not be removed", e);
			}
		}
	}

	@Test
	@Order(1)
	public void aRealModelsQuestionIsAnsweredThroughRest() throws Exception {
		final ApiClient apiClient = installation();
		final List<Waiting> waiting = aChatTheModelSuspends(apiClient);
		inTheChat(apiClient, waiting.get(0).sessionCode(), sessionCode -> {
			try {
				final NetworkOfAgentsConditionEventsControllerApi conditionEvents = new NetworkOfAgentsConditionEventsControllerApi(
						apiClient);
				int n = 0;
				for (Waiting each : waiting) {
					final ConditionEventAck ack = conditionEvents
							.deliverConditionEvent(answerTo(each, "condition-events-real-llm-rest", "evt-rest-" + (++n)));
					LOGGER.info("Condition event acknowledged: " + ack);
					assertNotNull(ack, "the event is acknowledged");
					assertEquals("ACCEPTED", String.valueOf(ack.getOutcome()),
							"the event matches the open block: " + ack.getReason());
					assertEquals(each.correlationId(), ack.getCorrelationId(), "both forms of the id are kept");
				}
				verifyRespawned(apiClient, waiting, "through REST");
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		});
	}

	@Test
	@Order(2)
	public void aRealModelsQuestionIsAnsweredThroughTheBroker() throws Exception {
		final ApiClient apiClient = installation();
		assertTrue(broker.isRegistered(emitter), "the test's emitter is registered with the broker");
		assertTrue(broker.checkReceivingComponentPresent(ConditionEventsReceiverFactory.MODULE_ID,
				ConditionEventsReceiverFactory.SYSTEM_ID), "the events layer's receiver is registered with the broker");
		final List<Waiting> waiting = aChatTheModelSuspends(apiClient);
		inTheChat(apiClient, waiting.get(0).sessionCode(), sessionCode -> {
			try {
				int n = 0;
				for (Waiting each : waiting) {
					final ai.gebo.architecture.agents.conditionevents.model.ConditionEvent event = new ai.gebo.architecture.agents.conditionevents.model.ConditionEvent();
					event.setSessionCode(each.sessionCode());
					event.setConditionId(each.conditionId());
					event.setConditionType(CLARIFICATION);
					event.setAttributes(new LinkedHashMap<String, Object>(Map.of("chosenOption", each.chosen())));
					event.setSourceEventId("evt-broker-" + (++n));
					event.setSourceTimestamp(new Date());
					final GMessageEnvelope<ConditionEventPayload> envelope = envelopes.newMessageFrom(emitter,
							new ConditionEventPayload(event), systemInfo.getUsername());
					envelope.setTargetModule(ConditionEventsReceiverFactory.MODULE_ID);
					envelope.setTargetComponent(ConditionEventsReceiverFactory.SYSTEM_ID);
					envelope.setTargetType(SystemComponentType.APPLICATION_COMPONENT);
					broker.accept(envelope);
				}
				verifyRespawned(apiClient, waiting, "through the broker");
				final ConditionEventView view = new NetworkOfAgentsConditionEventsControllerApi(apiClient)
						.conditionEventStatus(waiting.get(0).correlationId(), null, null);
				assertEquals(TestConditionEventsEmitter.MODULE_ID + "." + TestConditionEventsEmitter.SYSTEM_ID,
						view.getSourceSystem(), "with no source of its own, the sending component is the source");
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		});
	}
}
