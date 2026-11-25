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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.architecture.ai.model.ContextContentRequired;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.llms.usage.model.LLMUsageDetail;
import ai.gebo.architecture.llms.usage.repository.LLMUsageDetailRepository;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel.ChatModelConfigOptions;
import ai.gebo.llms.abstraction.layer.tests.TestChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;

/**
 * Certifies that every model call of a streamed request is made with the identity of
 * the person asking and recorded as usage of that person, the ones made after a tool
 * ran too, over the real path: a streamed request makes the model call a tool, the
 * tool runs, the model is called again; both calls run with the identity of the
 * person, and the usage recorded for both names them.
 */
public class StreamedToolRoundsUsageIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final String ANSWER = "GEBO_TEST_STREAMED_USAGE_ANSWER: done.";
	private static final String TOOL = "streamed_usage_test_tool";

	@Autowired
	private IGChatSessionLifeCycleService lifeCycleService;
	@Autowired
	private LLMUsageDetailRepository usage;

	private final AtomicBoolean toolAsked = new AtomicBoolean(true);
	/** Who the thread of each model call works for, in the order of the calls. */
	private final List<String> callers = new java.util.concurrent.CopyOnWriteArrayList<>();

	@Override
	protected void beforeEachCallback() throws Exception {
		TestChatModel.setGlobalToolCallLogic(content -> toolAsked.compareAndSet(false, true)
				? List.of(new AssistantMessage.ToolCall("call-streamed-usage-1", "function", TOOL, "{}"))
				: null);
		TestChatModel.setGlobalResponseLogic(content -> ANSWER);
		callers.clear();
		TestChatModel.setGlobalPromptObserver(prompt -> {
			final org.springframework.security.core.Authentication authentication = org.springframework.security.core.context.SecurityContextHolder
					.getContext().getAuthentication();
			callers.add(authentication != null ? authentication.getName() : "nobody");
		});
	}

	@Override
	protected void afterEachCallback() throws Exception {
		TestChatModel.clearGlobalToolCallLogic();
		TestChatModel.clearGlobalResponseLogic();
		TestChatModel.clearGlobalPromptObserver();
	}

	@Test
	public void theCallsAfterAToolRanAreRecordedAsUsageOfThePersonAsking() throws Exception {
		final AtomicBoolean toolRan = new AtomicBoolean(false);
		final ToolCallback tool = FunctionToolCallback.builder(TOOL, (Map<String, Object> input) -> {
			toolRan.set(true);
			return "GEBO_TEST_STREAMED_USAGE_TOOL_RESULT";
		}).description("A tool of the test of the usage of streamed calls.").inputType(Map.class).build();
		@SuppressWarnings("rawtypes")
		final IGConfigurableChatModel model = chatModelRuntimeDao.defaultHandler().cloneWithOptions(
				"streamed-usage-test-", new ChatModelConfigOptions(null, null, null, List.of(TOOL), List.of(tool)));
		final GeboChatRequest first = new GeboChatRequest();
		first.setQuery("a chat whose model calls a tool");
		lifeCycleService.createChatSession(first);
		final GeboChatRequest request = new GeboChatRequest();
		request.setUserChatContextCode(first.getUserChatContextCode());
		request.setQuery("Use your tool and answer.");
		@SuppressWarnings("unchecked")
		final LLMChatRequestResources resources = lifeCycleService.startRequest(request, model,
				LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		final long since = System.currentTimeMillis();
		try {
			final IChatRequestContext context = resources.createChatRequestContext();
			final GPromptTemplateConfig prompt = new GPromptTemplateConfig();
			prompt.setSystemPromptTemplate("You answer with the help of your tool.");
			prompt.setUserPromptTemplate("{question}");
			prompt.setContextDocuments(ContextContentRequired.NOT_REQUIRED);
			toolAsked.set(false);

			@SuppressWarnings("unchecked")
			final List<String> streamed = (List<String>) model.streamStringResponse(prompt, Map.of(), context)
					.collectList().block(Duration.ofSeconds(60));
			assertNotNull(streamed);
			assertTrue(toolRan.get(), "the model called its tool");
			assertTrue(String.join("", streamed).contains(ANSWER), "and answered: " + streamed);
			assertEquals(List.of(DEFAULT_ALL_ROLES_USER, DEFAULT_ALL_ROLES_USER), List.copyOf(callers),
					"the model is called with the identity of the person asking, after the tool ran too");
		} finally {
			lifeCycleService.releaseRequest(request);
		}

		// the usage is recorded after the calls: both calls of the request are waited for
		List<LLMUsageDetail> recorded = List.of();
		final long until = System.currentTimeMillis() + 30000;
		while (System.currentTimeMillis() < until) {
			recorded = usage.findAll().stream()
					.filter(row -> row.getTimestamp() >= since && row.getModel() != null).toList();
			if (recorded.size() >= 2) {
				break;
			}
			Thread.sleep(1000);
		}
		final List<String> people = recorded.stream().map(LLMUsageDetail::getUsername).toList();
		LOGGER.info("Usage recorded for the streamed request: " + recorded.stream()
				.map(row -> row.getUsername() + " model=" + row.getModel() + " stack=" + row.getCallerStack()).toList());
		assertTrue(recorded.size() >= 2, "both calls of the request are recorded, found " + recorded.size());
		for (String person : people) {
			assertEquals(DEFAULT_ALL_ROLES_USER, person, "every call is recorded as usage of the person asking: " + people);
		}
	}
}
