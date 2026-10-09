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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel.ChatModelConfigOptions;
import ai.gebo.security.services.ReactiveIdentityUtil;
import io.micrometer.observation.ObservationRegistry;

/**
 * Pins the declaration of the tools made for one use of a model (an agent's
 * notifyUser): they are not in the tools repository, the only source the model
 * declared its tools from, so the model was never told they exist.
 */
class AdditionalToolsDeclarationTest {

	/** A model with nothing to call: only its tool declaration is looked at. */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static class DeclaringModel extends GAbstractConfigurableChatModel<GBaseChatModelConfig, ChatModel> {
		final IGToolCallbackSourceRepositoryPattern repository;
		final IChatModelUsageAdvisorFactory usage;

		DeclaringModel(IGToolCallbackSourceRepositoryPattern repository, IChatModelUsageAdvisorFactory usage) {
			super(null, repository, usage, ObservationRegistry.NOOP);
			this.repository = repository;
			this.usage = usage;
		}

		@Override
		protected IGConfigurableChatModel cloneMeWithInjection() {
			return new DeclaringModel(repository, usage);
		}

		@Override
		protected ChatModel configureModel(GBaseChatModelConfig config, GChatModelType type,
				ToolCallingManager toolsCallsManager) {
			return mock(ChatModel.class);
		}
	}

	private static ToolCallback tool(String name) {
		ToolCallback tool = mock(ToolCallback.class);
		when(tool.getToolDefinition())
				.thenReturn(ToolDefinition.builder().name(name).description(name).inputSchema("{}").build());
		return tool;
	}

	private static List<String> names(List<ToolCallback> tools) {
		return tools.stream().map(x -> x.getToolDefinition().name()).toList();
	}

	@Test
	void theToolsMadeForAnAgentAreDeclaredWithTheRepositoryOnes() throws Exception {
		IGToolCallbackSourceRepositoryPattern repository = mock(IGToolCallbackSourceRepositoryPattern.class);
		ToolCallback search = tool("searchKnowledgeBase");
		// the repository only knows its own tools
		when(repository.getTools(anyList())).thenReturn(List.of(search));
		IChatModelUsageAdvisorFactory usage = mock(IChatModelUsageAdvisorFactory.class);
		when(usage.create(any(), any(), any())).thenReturn(mock(IChatModelUsageAdvisor.class));
		DeclaringModel model = new DeclaringModel(repository, usage);
		model.config = new GBaseChatModelConfig();

		ToolCallback notifyUser = tool("notifyUser");
		ToolCallback notEnabled = tool("notEnabled");
		IGConfigurableChatModel agentModel = model.cloneWithOptions("agent-",
				new ChatModelConfigOptions(null, null, null, List.of("searchKnowledgeBase", "notifyUser"),
						mock(ToolCallingManager.class), List.of(notifyUser, notEnabled)));

		List<ToolCallback> declared = agentModel.wrapTools(mock(ReactiveIdentityUtil.class), null);

		assertEquals(List.of("searchKnowledgeBase", "notifyUser"), names(declared),
				"the agent's own tool is declared once, a tool its configuration does not enable is not");
	}

	@Test
	void aModelWithoutToolsOfItsOwnDeclaresTheRepositoryOnes() {
		IGToolCallbackSourceRepositoryPattern repository = mock(IGToolCallbackSourceRepositoryPattern.class);
		ToolCallback searchWeb = tool("searchWeb");
		when(repository.getTools(anyList())).thenReturn(List.of(searchWeb));
		DeclaringModel model = new DeclaringModel(repository, mock(IChatModelUsageAdvisorFactory.class));
		model.config = new GBaseChatModelConfig();
		model.config.setEnabledFunctions(List.of("searchWeb"));

		assertEquals(List.of("searchWeb"), names(model.wrapTools(mock(ReactiveIdentityUtil.class), null)));
	}
}
