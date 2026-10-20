/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.tests.TestChatModel;
import ai.gebo.llms.agent.chat.service.impl.ReactiveChatAgentsNetworkStreamingOutputChatPipelineService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ChatNotificationContent.NotificationType;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSConsolidatedChatHistory;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.security.services.SecurityAuditConstraints;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import reactor.core.publisher.Flux;

/**
 * Every chat model call leaves exactly one llmInvokeChat event in the security audit
 * log, whichever path makes it: a direct call through the model's ChatClient and a
 * chat handed to a network of agents, which never passes through the chat services.
 * The events carry metadata only, never the text of the prompt or of the answer.
 */
@TestPropertySource(properties = { "ai.gebo.agents.standard.enabled=true",
		"ai.gebo.agents.standard.default-chat-network-of-agents=AGENTIC_LOOP_AGENTS_NETWORK" })
public class ModelCallSecurityAuditIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {

	private static final String USER_QUESTION = "What is the capital of France?";
	private static final String ANSWER = "MODEL_AUDIT_TEST_ANSWER: the capital of France is Paris.";
	private static final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private List<IStreamingOutputChatPipelineService> pipelineServices;
	@Autowired
	private IGChatSessionLifeCycleService lifeCycleService;

	private Logger securityLogger;
	private ListAppender<ILoggingEvent> listAppender;

	@BeforeEach
	public void attachSecurityLogAppender() {
		securityLogger = (Logger) LoggerFactory.getLogger(SecurityAuditConstraints.SECURITY_LOG);
		listAppender = new ListAppender<>();
		listAppender.start();
		securityLogger.addAppender(listAppender);
	}

	@AfterEach
	public void detachSecurityLogAppender() {
		if (securityLogger != null && listAppender != null) {
			securityLogger.detachAppender(listAppender);
		}
	}

	@Override
	protected void beforeEachCallback() throws Exception {
		TestChatModel.setGlobalResponseLogic(prompt -> prompt != null && prompt.contains(USER_QUESTION) ? ANSWER : "");
	}

	@Override
	protected void afterEachCallback() throws Exception {
		TestChatModel.clearGlobalResponseLogic();
	}

	/**
	 * The events logged so far. A streamed call writes its record in the stream's
	 * doFinally, right after the completion that releases the caller, so the events are
	 * awaited for a moment instead of read at once.
	 */
	private List<JsonNode> chatInvocationEvents(int atLeast) throws InterruptedException {
		List<JsonNode> out = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			out.clear();
			for (ILoggingEvent logged : new ArrayList<>(listAppender.list)) {
				JsonNode node = mapper.readTree(logged.getFormattedMessage());
				if (SecurityAuditTaxonomy.Action.LLM_INVOKE_CHAT.equals(node.path("action").asText())) {
					out.add(node);
				}
			}
			if (out.size() >= atLeast) {
				break;
			}
			Thread.sleep(50);
		}
		return out;
	}

	private static void assertMetadataOnly(JsonNode event) {
		assertEquals(SecurityAuditTaxonomy.EventType.LLM_INVOCATION, event.get("eventType").asText());
		// the model configuration: the network's agents run a copy of it, named after it
		assertTrue(event.get("resourceId").asText().contains(DEFAULT_TEST_CHAT_MODEL_CODE),
				"resourceId names the model configuration: " + event);
		assertEquals(SecurityAuditTaxonomy.Outcome.SUCCESS, event.get("outcome").asText());
		assertTrue(event.get("details").has("latencyMs"), "the latency is part of the metadata");
		String json = event.toString();
		assertFalse(json.contains(USER_QUESTION), "the prompt must never reach the audit log: " + json);
		assertFalse(json.contains("Paris"), "the answer must never reach the audit log: " + json);
	}

	@Test
	public void aDirectModelCallIsAuditedOnce() throws Exception {
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		Object answer = model.doWithChatClient(client -> client.prompt(USER_QUESTION).call().content());
		assertNotNull(answer);

		List<JsonNode> events = chatInvocationEvents(1);
		// a moment more: a second event would mean the call is audited twice
		Thread.sleep(500);
		events = chatInvocationEvents(1);
		assertEquals(1, events.size(), "One model call, one llmInvokeChat event: " + events);
		assertMetadataOnly(events.get(0));
	}

	@Test
	public void theModelCallsOfAnAgenticChatAreAudited() throws Exception {
		IStreamingOutputChatPipelineService agentsPipeline = pipelineServices.stream()
				.filter(s -> s instanceof ReactiveChatAgentsNetworkStreamingOutputChatPipelineService).findFirst()
				.orElseThrow(() -> new IllegalStateException(
						"Agents-network pipeline service not present; is ai.gebo.agents.standard.enabled=true?"));

		GeboChatRequest request = new GeboChatRequest();
		request.setQuery(USER_QUESTION);
		lifeCycleService.createChatSession(request);
		MinimalChatContext minimalChatContext = new MinimalChatContext();
		minimalChatContext.setCurrentRequest(request);
		LLMChatRequestResources resources = new LLMChatRequestResources();
		resources.setCurrentRequest(request);
		resources.setChathistory(new CSSConsolidatedChatHistory());
		ChatPipelineExecutionRuntimeData runtimeData = new ChatPipelineExecutionRuntimeData(null, 8192, resources,
				new GeboChatResponse(), minimalChatContext, true);
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		ISinkUIEmitter emitter = new ISinkUIEmitter() {
			@Override
			public void notifyUser(String code, String message, String icon, Long duration,
					NotificationType notificationType) {
			}

			@Override
			public void next(GeboChatMessageEnvelope event) {
			}

			@Override
			public void error(Throwable error) {
			}

			@Override
			public void complete() {
			}
		};

		Flux<GeboChatMessageEnvelope> flux = agentsPipeline.execute(runtimeData, emitter, model, model);
		assertNotNull(flux.collectList().block(Duration.ofSeconds(90)));

		List<JsonNode> events = chatInvocationEvents(1);
		assertFalse(events.isEmpty(), "The agentic chat's model calls must be audited");
		for (JsonNode event : events) {
			assertMetadataOnly(event);
		}
	}
}
