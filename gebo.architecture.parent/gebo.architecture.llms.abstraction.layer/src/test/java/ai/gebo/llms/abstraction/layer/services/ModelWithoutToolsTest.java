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
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.services.AdditionalToolsDeclarationTest.DeclaringModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel.ChatModelConfigOptions;
import ai.gebo.security.services.ReactiveIdentityUtil;

@SuppressWarnings({ "rawtypes", "unchecked" })
class ModelWithoutToolsTest {
	private IGToolCallbackSourceRepositoryPattern repository;
	private DeclaringModel model;

	private static ToolCallback tool(String name) {
		final ToolCallback tool = mock(ToolCallback.class);
		when(tool.getToolDefinition())
				.thenReturn(ToolDefinition.builder().name(name).description(name).inputSchema("{}").build());
		return tool;
	}

	@BeforeEach
	void setUp() {
		final ToolCallback searchWeb = tool("searchWeb");
		repository = mock(IGToolCallbackSourceRepositoryPattern.class);
		when(repository.getTools(anyList())).thenAnswer(call -> {
			final List<String> names = call.getArgument(0);
			return names.contains("searchWeb") ? List.of(searchWeb) : List.of();
		});
		final IChatModelUsageAdvisorFactory usage = mock(IChatModelUsageAdvisorFactory.class);
		when(usage.create(any(), any(), any())).thenReturn(mock(IChatModelUsageAdvisor.class));
		model = new DeclaringModel(repository, usage);
		model.config = new GBaseChatModelConfig();
		model.config.setCode("the-model");
	}

	@Test
	void aModelWithNoToolsIsReturnedItself() throws Exception {
		assertSame(model, model.withoutTools());
	}

	@Test
	void aModelNamingOnlyUnknownToolsIsReturnedItself() throws Exception {
		model.toolNames = List.of("gone");

		assertSame(model, model.withoutTools());
	}

	@Test
	void aModelWithToolsGivesACopyOfferingNone() throws Exception {
		model.toolNames = List.of("searchWeb");

		final IGConfigurableChatModel copy = model.withoutTools();

		assertNotSame(model, copy);
		assertTrue(copy.wrapTools(mock(ReactiveIdentityUtil.class), null).isEmpty());
		assertEquals(1, model.wrapTools(mock(ReactiveIdentityUtil.class), null).size(), "the model keeps its tools");
	}

	@Test
	void theCopyKeepsTheCodeOfTheModel() throws Exception {
		model.toolNames = List.of("searchWeb");

		assertEquals("the-model", model.withoutTools().getCode());
	}

	@Test
	void theCopyIsMadeOnce() throws Exception {
		model.toolNames = List.of("searchWeb");

		assertSame(model.withoutTools(), model.withoutTools());
	}

	@Test
	void aToolMadeForThisUseOfTheModelCountsAsATool() throws Exception {
		final IGConfigurableChatModel agentModel = model.cloneWithOptions("agent-",
				new ChatModelConfigOptions(null, null, null, List.of("notifyUser"), List.of(tool("notifyUser"))));

		final IGConfigurableChatModel copy = agentModel.withoutTools();

		assertNotSame(agentModel, copy);
		assertTrue(copy.wrapTools(mock(ReactiveIdentityUtil.class), null).isEmpty());
	}

	@Test
	void aCopyHasNoToolsToLeaveOut() throws Exception {
		model.toolNames = List.of("searchWeb");
		final IGConfigurableChatModel copy = model.withoutTools();

		assertSame(copy, copy.withoutTools());
	}
}
