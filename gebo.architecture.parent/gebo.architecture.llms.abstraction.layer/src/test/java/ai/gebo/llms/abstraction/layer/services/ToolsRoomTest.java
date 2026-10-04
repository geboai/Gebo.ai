/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.ToolsTokenBudget;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.security.services.ReactiveIdentityUtil;
import ai.gebo.security.services.RunAsWithReturn;

/**
 * Pins the room a model call leaves to its tools' results: sized on the context by
 * the model call itself, capping the one an agent shares, and taken by every tool
 * result through the tool wrapper, which cuts a result to what is left.
 */
class ToolsRoomTest {

	private static String words(int count) {
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < count; i++) {
			text.append("word").append(i % 97).append(' ');
		}
		return text.toString();
	}

	private static ToolCallback tool(String name, String result) {
		ToolCallback tool = mock(ToolCallback.class);
		when(tool.getToolDefinition())
				.thenReturn(ToolDefinition.builder().name(name).description(name + " tool").inputSchema("{}").build());
		when(tool.call(anyString(), any(ToolContext.class))).thenReturn(result);
		return tool;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ReactiveIdentityUtil runningInPlace() {
		ReactiveIdentityUtil runAs = mock(ReactiveIdentityUtil.class);
		when(runAs.doRunAsWithReturn(any())).thenAnswer(invocation -> ((RunAsWithReturn) invocation.getArgument(0)).apply());
		return runAs;
	}

	private static AdditionalToolsDeclarationTest.DeclaringModel model(Integer contextLength) {
		AdditionalToolsDeclarationTest.DeclaringModel model = new AdditionalToolsDeclarationTest.DeclaringModel(
				mock(ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern.class),
				mock(IChatModelUsageAdvisorFactory.class));
		model.config = new GBaseChatModelConfig();
		model.config.setCode("room-test-model");
		model.config.setContextLength(contextLength);
		return model;
	}

	@Test
	void aResultInTheRoomIsReturnedWholeAndTakesItsInputAndItself() {
		ToolsTokenBudget budget = new ToolsTokenBudget(1000);
		String result = words(100);

		assertSame(result, budget.admit("t", "{\"q\":\"x\"}", result));
		assertEquals(1000 - ITokensCountable.stringsTokensSize("{\"q\":\"x\"}") - ITokensCountable.stringsTokensSize(result),
				budget.left());
	}

	@Test
	void aResultOverTheRoomIsCutToWhatIsLeftAndMarked() {
		ToolsTokenBudget budget = new ToolsTokenBudget(500);
		String result = words(3000);

		String admitted = budget.admit("t", "{}", result);

		assertTrue(admitted.contains("truncated: about"), admitted.substring(Math.max(0, admitted.length() - 160)));
		assertTrue(ITokensCountable.stringsTokensSize(admitted) <= 500 - ITokensCountable.stringsTokensSize("{}"),
				"admitted " + ITokensCountable.stringsTokensSize(admitted));
		assertTrue(budget.left() >= 0 && budget.left() < 50, "the room is used, " + budget.left() + " left");
	}

	@Test
	void tooLittleRoomReplacesTheResultWithAMessage() {
		ToolsTokenBudget budget = new ToolsTokenBudget(20);

		String admitted = budget.admit("deepSearchWeb", null, words(500));

		assertTrue(admitted.startsWith("The result of the tool deepSearchWeb"), admitted);
		assertEquals(0, budget.left());
	}

	@Test
	void concurrentResultsNeverTakeMoreThanTheRoom() throws Exception {
		ToolsTokenBudget budget = new ToolsTokenBudget(2000);
		ExecutorService pool = Executors.newFixedThreadPool(8);
		for (int i = 0; i < 40; i++) {
			pool.submit(() -> budget.admit("t", "{}", words(200)));
		}
		pool.shutdown();
		assertTrue(pool.awaitTermination(30, TimeUnit.SECONDS));

		assertEquals(0, budget.left());
	}

	@Test
	void aToolSizesItsWorkOnTheRoomOrOnItsOwnLimitsWithoutOne() {
		ToolContext withRoom = new ToolContext(Map.of(ToolsTokenBudget.TOOLS_CONTEXT_KEY, new ToolsTokenBudget(1200)));
		ToolContext withoutRoom = new ToolContext(Map.of("other", "value"));

		assertEquals(1200, ToolsTokenBudget.grantFor(withRoom, 4000));
		assertEquals(300, ToolsTokenBudget.grantFor(withRoom, 300));
		assertEquals(4000, ToolsTokenBudget.grantFor(withoutRoom, 4000), "no room set: the tool's own limit");
		assertEquals(4000, ToolsTokenBudget.grantFor(null, 4000));
		assertTrue(!ToolsTokenBudget.noUsefulRoom(withRoom) && !ToolsTokenBudget.noUsefulRoom(withoutRoom));
		assertTrue(ToolsTokenBudget.noUsefulRoom(new ToolContext(Map.of(ToolsTokenBudget.TOOLS_CONTEXT_KEY,
				new ToolsTokenBudget(ToolsTokenBudget.MIN_USEFUL_TOKENS - 1)))));
	}

	@Test
	void aTextIsFittedWholeOrCutAndMarked() {
		assertEquals("short", ToolsTokenBudget.fitText("short", 100));
		String cut = ToolsTokenBudget.fitText(words(2000), 300);
		assertTrue(ITokensCountable.stringsTokensSize(cut) <= 300 && cut.contains("truncated: about"), cut);
		assertEquals("", ToolsTokenBudget.fitText(words(2000), 10), "too small to hold a cut");
	}

	@Test
	void aListIsCutBetweenWholeItems() {
		List<String> items = List.of(words(100), words(100), words(100), words(100));
		int one = ITokensCountable.stringsTokensSize(words(100));

		assertEquals(2, ToolsTokenBudget.fitItems(items, one * 2 + one / 2, item -> item).size());
		assertEquals(4, ToolsTokenBudget.fitItems(items, one * 10, item -> item).size());
		assertEquals(0, ToolsTokenBudget.fitItems(items, one - 1, item -> item).size());
	}

	@Test
	void theToolWrapperAdmitsTheResultAndRecordsWhatTheModelGets() {
		ToolsTokenBudget budget = new ToolsTokenBudget(300);
		ToolCallsListener listener = new ToolCallsListener();
		RunAsToolCallback wrapped = new RunAsToolCallback(tool("readUrl", words(2000)), runningInPlace(), listener);

		String returned = wrapped.call("{}", new ToolContext(Map.of(ToolsTokenBudget.TOOLS_CONTEXT_KEY, budget)));

		assertTrue(ITokensCountable.stringsTokensSize(returned) <= 300, "returned " + ITokensCountable.stringsTokensSize(returned));
		assertEquals(returned, listener.getCalls().get(0).getResult(), "the call is recorded as the model got it");

		// a model call without a room leaves the result as the tool made it
		String whole = words(2000);
		RunAsToolCallback unbounded = new RunAsToolCallback(tool("readUrl", whole), runningInPlace(), null);
		assertEquals(whole, unbounded.call("{}", new ToolContext(Map.of("other", "value"))));
	}

	@Test
	void theModelCallSizesTheRoomOnWhatItsMessagesAndToolsLeaveOfTheContext() {
		AdditionalToolsDeclarationTest.DeclaringModel model = model(20000);
		List<Message> messages = List.of(new SystemMessage(words(1000)), new UserMessage(words(200)));
		List<ToolCallback> tools = List.of(tool("searchWeb", ""));
		Map<String, Object> callerContext = new HashMap<>(Map.of("requestId", "r1"));

		Map<String, Object> toolsContext = model.withToolsRoom(callerContext, messages, tools, -1);

		long used = ITokensCountable.stringsTokensSize(words(1000)) + ITokensCountable.stringsTokensSize(words(200));
		long definitions = ITokensCountable.stringsTokensSize("searchWeb", "searchWeb tool", "{}");
		assertEquals((int) ((20000 - used - definitions) * GAbstractConfigurableChatModel.TOOLS_ROOM_SHARE),
				ToolsTokenBudget.from(toolsContext).left());
		assertEquals("r1", toolsContext.get("requestId"), "the caller's values are kept");
		assertNull(callerContext.get(ToolsTokenBudget.TOOLS_CONTEXT_KEY), "the caller's map is never changed");
	}

	@Test
	void anAgentsSmallerRoomIsCappedNeverReplaced() {
		AdditionalToolsDeclarationTest.DeclaringModel model = model(20000);
		ToolsTokenBudget agents = new ToolsTokenBudget(500);
		List<Message> messages = List.of(new UserMessage(words(10)));
		List<ToolCallback> tools = List.of(tool("searchWeb", ""));

		Map<String, Object> toolsContext = model.withToolsRoom(Map.of(ToolsTokenBudget.TOOLS_CONTEXT_KEY, agents),
				messages, tools, -1);
		assertSame(agents, ToolsTokenBudget.from(toolsContext));
		assertEquals(500, agents.left(), "the agent's smaller room applies");

		ToolsTokenBudget generous = new ToolsTokenBudget(1_000_000);
		model.withToolsRoom(Map.of(ToolsTokenBudget.TOOLS_CONTEXT_KEY, generous), messages, tools, -1);
		assertTrue(generous.left() < 20000, "an agent's room larger than the context is capped to it: " + generous.left());
	}

	@Test
	void aModelWithNoKnownContextLengthSetsNoRoom() {
		AdditionalToolsDeclarationTest.DeclaringModel model = model(null);
		Map<String, Object> callerContext = Map.of("requestId", "r1");

		Map<String, Object> toolsContext = model.withToolsRoom(callerContext, new ArrayList<>(List.of(new UserMessage("q"))),
				List.of(tool("searchWeb", "")), -1);

		assertSame(callerContext, toolsContext);
		assertNull(ToolsTokenBudget.from(toolsContext));
		assertEquals(8192, model.getContextLength(), "the context length a model without one is given is unchanged");
	}
}
