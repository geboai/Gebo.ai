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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.PipelineType;
import ai.gebo.architecture.agents.repository.AgentsNetworkRepository;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.agents.services.IGAgenticChatDefaultNetworkOfAgentsService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.tests.TestChatModel;
import ai.gebo.llms.agent.chat.service.impl.ReactiveChatAgentsNetworkStreamingOutputChatPipelineService;
import ai.gebo.llms.agent.chat.service.impl.ReportWriterReactiveAgentServiceImpl;
import ai.gebo.llms.agent.standard.memory.GConversationNetworkMemory;
import ai.gebo.llms.agent.standard.memory.GConversationNetworkMemoryRepository;
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
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;

/**
 * A network that asks to remember across the turns of a conversation
 * ({@link GAgentsNetwork#getRememberAcrossTurns()}), run by the chat pipeline on
 * two turns of the same chat: what its agents worked out in the first turn is
 * kept for the chat and the network, its agents read it in the second turn, and
 * it goes with the chat when the chat is removed. The same two turns on the
 * shipped network, which does not ask to remember, keep nothing and show
 * nothing: the chats run as they always did.
 * <p>
 * The network remembering is a copy of the controller network (the controller
 * and the report writer render the shared context their prompts name), made the
 * system default of the RAG pipeline for the test, as an administrator would.
 */
@TestPropertySource(properties = { "ai.gebo.agents.standard.enabled=true",
		"ai.gebo.agents.standard.default-chat-network-of-agents=DEFAULT_AGENTS_NETWORK" })
public class NetworkMemoryAcrossTurnsTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final GeboToolCalling TOOL_CALLING = GeboToolCalling.builder()
			.caller("test:NetworkMemoryAcrossTurnsTest").build();
	private static final String SHIPPED_NETWORK = "DEFAULT_AGENTS_NETWORK";
	private static final String REMEMBERING_NETWORK = "NETWORK_MEMORY_TEST_NETWORK";
	private static final String FIRST_QUESTION = "What is the capital of France?";
	private static final String SECOND_QUESTION = "And how many people live there?";
	private static final String FIRST_ANSWER = "FIRST_TURN_ANSWER_MARKER the capital of France is Paris.";
	private static final String SECOND_ANSWER = "SECOND_TURN_ANSWER_MARKER about two million people.";
	private static final String WRITING_INSTRUCTION = "Write a concise direct answer to the user question.";
	/** How the memory opens in the shared context (GConversationAgentsSessionFactory). */
	private static final String MEMORY_OPENING = "What this network of agents worked out earlier in this conversation";

	@Autowired
	private List<IStreamingOutputChatPipelineService> pipelineServices;
	@Autowired
	private IGChatSessionLifeCycleService lifeCycleService;
	@Autowired
	private IAgentsNetworkDao networksDao;
	@Autowired
	private AgentsNetworkRepository networksRepository;
	@Autowired
	private IGAgenticChatDefaultNetworkOfAgentsService defaultNetworksService;
	@Autowired
	private GConversationNetworkMemoryRepository memories;

	/** The controller's prompts of the turn being run. */
	private final List<String> controllerPrompts = Collections.synchronizedList(new ArrayList<>());

	@Override
	protected void beforeEachCallback() throws Exception {
		controllerPrompts.clear();
		TestChatModel.setGlobalResponseLogic(prompt -> {
			if (prompt == null) {
				return "";
			}
			if (prompt.contains("coordinator-controller")) {
				controllerPrompts.add(prompt);
				return "{\n" //
						+ "  \"" + ReportWriterReactiveAgentServiceImpl.REPORT_WRITER_NETWORK_AGENT_SERVICE + "\": {\n" //
						+ "    \"deliveryOrder\": 1,\n" //
						+ "    \"concurrency\": \"SERIAL\",\n" //
						+ "    \"commandData\": \"" + WRITING_INSTRUCTION + "\"\n" //
						+ "  }\n" //
						+ "}";
			}
			if (prompt.contains("Writer/Reporter")) {
				return prompt.contains(SECOND_QUESTION) ? SECOND_ANSWER : FIRST_ANSWER;
			}
			return "";
		});
	}

	@Override
	protected void afterEachCallback() throws Exception {
		TestChatModel.clearGlobalResponseLogic();
	}

	/** Runs one turn of the chat through the agents pipeline, returning the controller's prompts. */
	private List<String> runTurn(String sessionCode, String question) throws Exception {
		controllerPrompts.clear();
		IStreamingOutputChatPipelineService agentsPipeline = pipelineServices.stream()
				.filter(s -> s instanceof ReactiveChatAgentsNetworkStreamingOutputChatPipelineService).findFirst()
				.orElseThrow();
		GeboChatRequest request = new GeboChatRequest();
		request.setQuery(question);
		request.setUserChatContextCode(sessionCode);
		MinimalChatContext minimalChatContext = new MinimalChatContext();
		minimalChatContext.setCurrentRequest(request);
		LLMChatRequestResources resources = new LLMChatRequestResources();
		resources.setCurrentRequest(request);
		resources.setChathistory(new CSSConsolidatedChatHistory());
		ChatPipelineExecutionRuntimeData runtimeData = new ChatPipelineExecutionRuntimeData(null, 8192, resources,
				new GeboChatResponse(), minimalChatContext, true, TOOL_CALLING);
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
				LOGGER.error("Error emitted by the agents network", error);
			}

			@Override
			public void complete() {
			}
		};
		List<GeboChatMessageEnvelope> streamed = agentsPipeline.execute(runtimeData, emitter, model, model)
				.collectList().block(Duration.ofSeconds(90));
		assertNotNull(streamed);
		assertFalse(controllerPrompts.isEmpty(), "The controller must have called its model");
		return new ArrayList<>(controllerPrompts);
	}

	private String newChat() throws Exception {
		GeboChatRequest request = new GeboChatRequest();
		request.setQuery(FIRST_QUESTION);
		lifeCycleService.createChatSession(request);
		assertNotNull(request.getUserChatContextCode());
		return request.getUserChatContextCode();
	}

	/**
	 * The memory of the chat and the network, waited for: the network closes its
	 * session when its run ends, which may come after the last envelope streamed.
	 */
	private Optional<GConversationNetworkMemory> memoryOf(String sessionCode, String networkCode, int runs)
			throws InterruptedException {
		Optional<GConversationNetworkMemory> memory = Optional.empty();
		for (int i = 0; i < 100; i++) {
			memory = memories.findByConversationCodeAndNetworkCode(sessionCode, networkCode);
			if (memory.isPresent() && memory.get().getRuns() >= runs) {
				return memory;
			}
			Thread.sleep(100);
		}
		return memory;
	}

	@Test
	public void aNetworkThatAsksToRememberReadsInTheNextTurnWhatItWorkedOut() throws Exception {
		GAgentsNetwork remembering = networksDao.findByCode(SHIPPED_NETWORK);
		assertNotNull(remembering, "the shipped controller network is the model of the remembering one");
		remembering.setCode(REMEMBERING_NETWORK);
		remembering.setReadOnly(false);
		remembering.setRememberAcrossTurns(Boolean.TRUE);
		networksRepository.save(remembering);
		String sessionCode = null;
		try {
			defaultNetworksService.setAgenticChatDefaultNetwork(PipelineType.RAG_PIPELINE, REMEMBERING_NETWORK);
			sessionCode = newChat();

			List<String> first = runTurn(sessionCode, FIRST_QUESTION);
			assertTrue(first.stream().noneMatch(p -> p.contains(MEMORY_OPENING)),
					"nothing is remembered before the first turn: " + first.get(0));
			Optional<GConversationNetworkMemory> afterFirst = memoryOf(sessionCode, REMEMBERING_NETWORK, 1);
			assertTrue(afterFirst.isPresent(), "the first turn must be remembered for the chat and the network");
			assertEquals(1, afterFirst.get().getRuns());
			assertTrue(afterFirst.get().getDigest().contains(FIRST_ANSWER),
					"what the writer worked out is in the memory: " + afterFirst.get().getDigest());

			List<String> second = runTurn(sessionCode, SECOND_QUESTION);
			assertTrue(second.stream().anyMatch(p -> p.contains(MEMORY_OPENING) && p.contains(FIRST_ANSWER)),
					"the controller of the second turn reads what the first turn worked out: " + second.get(0));
			Optional<GConversationNetworkMemory> afterSecond = memoryOf(sessionCode, REMEMBERING_NETWORK, 2);
			assertEquals(2, afterSecond.get().getRuns());
			assertTrue(afterSecond.get().getDigest().contains(SECOND_ANSWER),
					"the memory now holds the second turn: " + afterSecond.get().getDigest());
			assertFalse(afterSecond.get().getDigest().contains(MEMORY_OPENING),
					"the memory itself is not digested again: " + afterSecond.get().getDigest());

			lifeCycleService.removeChatSession(sessionCode);
			sessionCode = null;
			assertTrue(memories.findByConversationCodeAndNetworkCode(afterSecond.get().getConversationCode(),
					REMEMBERING_NETWORK).isEmpty(), "what was remembered goes with the chat");
		} finally {
			if (sessionCode != null) {
				lifeCycleService.removeChatSession(sessionCode);
			}
			defaultNetworksService.resetAgenticChatDefaultNetwork(PipelineType.RAG_PIPELINE);
			networksRepository.deleteById(REMEMBERING_NETWORK);
		}
	}

	@Test
	public void theShippedNetworkRemembersNothingAcrossTurns() throws Exception {
		String sessionCode = newChat();
		try {
			runTurn(sessionCode, FIRST_QUESTION);
			List<String> second = runTurn(sessionCode, SECOND_QUESTION);
			assertTrue(second.stream().noneMatch(p -> p.contains(MEMORY_OPENING) || p.contains(FIRST_ANSWER)),
					"a network that did not ask to remember reads nothing of the earlier turn: " + second.get(0));
			Thread.sleep(500);
			assertTrue(memories.findByConversationCodeAndNetworkCode(sessionCode, SHIPPED_NETWORK).isEmpty(),
					"a network that did not ask to remember keeps nothing");
		} finally {
			lifeCycleService.removeChatSession(sessionCode);
		}
	}
}
