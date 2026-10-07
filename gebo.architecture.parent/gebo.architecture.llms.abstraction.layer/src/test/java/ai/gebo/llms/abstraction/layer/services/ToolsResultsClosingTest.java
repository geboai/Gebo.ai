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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.ai.service.ToolsTokenBudget;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.security.services.ReactiveIdentityUtil;
import ai.gebo.security.services.RunAsWithReturn;

/**
 * Pins the prompt's text closing the tools' results: the tool calling loop puts the
 * results after the user message, so when tools run they are the last text the model
 * reads before answering, and what must hold after them goes at their end.
 */
class ToolsResultsClosingTest {

	private static final String CLOSING = "Answer in the language of the user's request.";

	private static ToolCallback tool(String name, String result) {
		ToolCallback tool = mock(ToolCallback.class);
		when(tool.getToolDefinition())
				.thenReturn(ToolDefinition.builder().name(name).description(name + " tool").inputSchema("{}").build());
		when(tool.call(anyString(), any(ToolContext.class))).thenReturn(result);
		when(tool.call(anyString())).thenReturn(result);
		return tool;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ReactiveIdentityUtil runningInPlace() {
		ReactiveIdentityUtil runAs = mock(ReactiveIdentityUtil.class);
		when(runAs.doRunAsWithReturn(any()))
				.thenAnswer(invocation -> ((RunAsWithReturn) invocation.getArgument(0)).apply());
		return runAs;
	}

	@Test
	void theResultEndsWithTheClosingTheListenerRecordsItWithout() {
		ToolCallsListener listener = new ToolCallsListener();
		RunAsToolCallback wrapped = new RunAsToolCallback(tool("searchKnowledgeBase", "the fragments"),
				runningInPlace(), listener, "  " + CLOSING + "\n");

		String seen = wrapped.call("{}", new ToolContext(Map.of()));

		assertEquals("the fragments" + RunAsToolCallback.TOOLS_RESULTS_CLOSING_SEPARATOR + CLOSING, seen,
				"the model reads the result, then the closing, trimmed");
		assertEquals("the fragments", listener.getCalls().get(0).getResult(),
				"the recorded call (Found docs, the user's progress) is the tool's own result");
		assertEquals("the fragments" + RunAsToolCallback.TOOLS_RESULTS_CLOSING_SEPARATOR + CLOSING,
				wrapped.call("{}"), "also without a tools context");
	}

	@Test
	void withoutAClosingTheResultIsUnchanged() {
		assertEquals("the fragments",
				new RunAsToolCallback(tool("searchWeb", "the fragments"), runningInPlace(), null).call("{}",
						new ToolContext(Map.of())));
		assertEquals("the fragments", new RunAsToolCallback(tool("searchWeb", "the fragments"), runningInPlace(), null,
				"   ").call("{}", new ToolContext(Map.of())), "a blank closing is no closing");
	}

	@Test
	void theClosingIsTakenOutOfTheRoomLeftToTheTools() {
		ToolsTokenBudget budget = new ToolsTokenBudget(5000);
		ToolContext context = new ToolContext(Map.of(ToolsTokenBudget.TOOLS_CONTEXT_KEY, budget));
		ToolsTokenBudget alone = new ToolsTokenBudget(5000);
		ToolContext aloneContext = new ToolContext(Map.of(ToolsTokenBudget.TOOLS_CONTEXT_KEY, alone));

		new RunAsToolCallback(tool("readDocument", "a page"), runningInPlace(), null, CLOSING).call("{}", context);
		new RunAsToolCallback(tool("readDocument", "a page"), runningInPlace(), null).call("{}", aloneContext);

		assertEquals(alone.left() - ITokensCountable.stringsTokensSize(CLOSING), budget.left());
	}

	@Test
	void theCallRendersTheClosingFromThePromptWithTheUserRequest() {
		AdditionalToolsDeclarationTest.DeclaringModel model = new AdditionalToolsDeclarationTest.DeclaringModel(
				mock(IGToolCallbackSourceRepositoryPattern.class), mock(IChatModelUsageAdvisorFactory.class));
		IChatRequestContext context = mock(IChatRequestContext.class);
		when(context.getActualUserRequest()).thenReturn("Who is Fohat?");
		GPromptTemplateConfig prompt = GPromptTemplateConfig.of("system", "user {question}", "a-use");

		assertNull(model.createToolsResultsClosing(prompt, Map.of(), context), "no template, no closing");
		prompt.setToolsResultsPromptTemplate("  ");
		assertNull(model.createToolsResultsClosing(prompt, Map.of(), context), "a blank template is none");

		prompt.setToolsResultsPromptTemplate("Answer in the language of: {question}");
		assertEquals("Answer in the language of: Who is Fohat?",
				model.createToolsResultsClosing(prompt, Map.of(IChatRequestContext.DOCUMENTS_PROMPT_PARAM, "docs"),
						context));
		assertTrue(prompt.getPlaceholders().containsKey("question"),
				"the closing's placeholders are the prompt's ones");
	}

	@Test
	void everyToolOfTheCallClosesItsResults() {
		IGToolCallbackSourceRepositoryPattern repository = mock(IGToolCallbackSourceRepositoryPattern.class);
		ToolCallback searchWeb = tool("searchWeb", "pages");
		when(repository.getTools(anyList())).thenReturn(List.of(searchWeb));
		AdditionalToolsDeclarationTest.DeclaringModel model = new AdditionalToolsDeclarationTest.DeclaringModel(
				repository, mock(IChatModelUsageAdvisorFactory.class));
		model.config = new GBaseChatModelConfig();
		model.config.setEnabledFunctions(List.of("searchWeb"));

		List<ToolCallback> closed = model.wrapToolsClosingResults(runningInPlace(), null, CLOSING);
		List<ToolCallback> open = model.wrapTools(runningInPlace(), null);

		assertTrue(closed.get(0).call("{}", new ToolContext(Map.of())).endsWith(CLOSING));
		assertEquals("pages", open.get(0).call("{}", new ToolContext(Map.of())),
				"the interface's wrapping, used out of a prompt's call, adds nothing");
	}
}
