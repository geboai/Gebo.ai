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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.DefaultChatClient.DefaultChatClientRequestSpec;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.model.tool.DefaultToolCallingChatOptions;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

import ai.gebo.architecture.ai.model.ContextContentRequired;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel.RequestSpec;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.security.services.ReactiveIdentityUtil;
import io.micrometer.observation.ObservationRegistry;

/**
 * A prompt not allowing tools keeps them from the model: a provider's model holds the
 * enabled tools in its default options, where nothing on the request can take them
 * away, so such a call must go through a copy of the model enabling none.
 */
class PromptNotAllowingToolsTest {
	private final IGToolCallbackSourceRepositoryPattern repository = mock(IGToolCallbackSourceRepositoryPattern.class);
	private final IChatModelUsageAdvisorFactory usage = mock(IChatModelUsageAdvisorFactory.class);
	private final ToolCallback search = tool("searchKnowledgeBase");
	/** The tool names of every provider model made; a chat model makes two of them. */
	private final List<List<String>> configured = new ArrayList<>();

	/** Configures its model as the providers do: the enabled tools in the default options. */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	class ProviderLikeModel extends GAbstractConfigurableChatModel<GBaseChatModelConfig, ChatModel> {
		ProviderLikeModel() {
			super(null, repository, usage, ObservationRegistry.NOOP, null);
		}

		@Override
		protected IGConfigurableChatModel cloneMeWithInjection() {
			return new ProviderLikeModel();
		}

		@Override
		protected ChatModel configureModel(GBaseChatModelConfig config, GChatModelType type) {
			final List<String> names = config.getEnabledFunctions() != null ? config.getEnabledFunctions() : List.of();
			configured.add(names);
			final ToolCallingChatOptions options = DefaultToolCallingChatOptions.builder()
					.toolCallbacks(names.isEmpty() ? List.of() : repository.getTools(names)).build();
			final ChatModel chatModel = mock(ChatModel.class);
			when(chatModel.getOptions()).thenReturn(options);
			return chatModel;
		}
	}

	@BeforeEach
	void aRepositoryKnowingOneTool() {
		when(repository.getTools(anyList())).thenAnswer(
				call -> ((List<?>) call.getArgument(0)).contains("searchKnowledgeBase") ? List.of(search) : List.of());
		when(usage.create(any(), any(), any())).thenReturn(mock(IChatModelUsageAdvisor.class));
	}

	private static ToolCallback tool(String name) {
		ToolCallback tool = mock(ToolCallback.class);
		when(tool.getToolDefinition())
				.thenReturn(ToolDefinition.builder().name(name).description(name).inputSchema("{}").build());
		return tool;
	}

	private ProviderLikeModel modelEnabling(String... tools) throws Exception {
		GBaseChatModelConfig config = new GBaseChatModelConfig();
		config.setCode("a-model");
		config.setEnabledFunctions(List.of(tools));
		ProviderLikeModel model = new ProviderLikeModel();
		model.initialize(config, null);
		return model;
	}

	private static GPromptTemplateConfig prompt(ContextContentRequired toolsCalling) {
		GPromptTemplateConfig prompt = new GPromptTemplateConfig();
		prompt.setPromptUse("test");
		prompt.setSystemPromptTemplate("You answer.");
		prompt.setUserPromptTemplate("{question}");
		prompt.setChatHistory(ContextContentRequired.NOT_REQUIRED);
		prompt.setContextDocuments(ContextContentRequired.NOT_REQUIRED);
		prompt.setToolsCalling(toolsCalling);
		return prompt;
	}

	private static DefaultChatClientRequestSpec prepared(ProviderLikeModel model, ContextContentRequired toolsCalling,
			GeboToolCalling toolCalling) {
		IChatRequestContext context = IChatRequestContext.builder().actualUserRequest("the question")
				.consolidatedHistory("").interactions(List.of()).documents(List.of()).toolsContext(Map.of())
				.pipelineInfos(Map.of()).build();
		RequestSpec spec = model.prepareCall(prompt(toolsCalling), Map.of(), context, mock(ReactiveIdentityUtil.class),
				toolCalling);
		return (DefaultChatClientRequestSpec) spec.getRequestSpec();
	}

	/** The model the request goes to: the chat client does not expose it. */
	private static ChatModel chatModelOf(DefaultChatClientRequestSpec request) throws Exception {
		Method getter = DefaultChatClientRequestSpec.class.getDeclaredMethod("getChatModel");
		getter.setAccessible(true);
		return (ChatModel) getter.invoke(request);
	}

	private static List<ToolCallback> defaultToolsOf(DefaultChatClientRequestSpec request) throws Exception {
		return ((ToolCallingChatOptions) chatModelOf(request).getOptions()).getToolCallbacks();
	}

	private static List<String> names(List<ToolCallback> tools) {
		return tools.stream().map(x -> x.getToolDefinition().name()).toList();
	}

	@Test
	void aPromptNotAllowingToolsOffersNoneOfTheEnabledOnes() throws Exception {
		DefaultChatClientRequestSpec request = prepared(modelEnabling("searchKnowledgeBase"),
				ContextContentRequired.NOT_REQUIRED, null);

		assertTrue(defaultToolsOf(request).isEmpty(), "the provider's default options declare nothing");
		assertTrue(request.getToolCallbacks().isEmpty(), "the request adds nothing");
	}

	@Test
	void aPromptAllowingToolsOffersThemWrapped() throws Exception {
		ProviderLikeModel model = modelEnabling("searchKnowledgeBase");

		for (ContextContentRequired allowing : new ContextContentRequired[] { ContextContentRequired.REQUIRED, null }) {
			DefaultChatClientRequestSpec request = prepared(model, allowing, null);

			assertEquals(List.of("searchKnowledgeBase"), names(request.getToolCallbacks()));
			assertTrue(request.getToolCallbacks().get(0) instanceof RunAsToolCallback);
		}
		assertFalse(configured.contains(List.of()), "no copy of the model is made");
	}

	@Test
	void theCopyIsMadeOnceAndTheModelKeepsItsTools() throws Exception {
		ProviderLikeModel model = modelEnabling("searchKnowledgeBase");

		DefaultChatClientRequestSpec first = prepared(model, ContextContentRequired.NOT_REQUIRED, null);
		DefaultChatClientRequestSpec second = prepared(model, ContextContentRequired.NOT_REQUIRED, null);
		DefaultChatClientRequestSpec allowing = prepared(model, ContextContentRequired.REQUIRED, null);

		assertEquals(2, Collections.frequency(configured, List.of()), "one copy, with no tools");
		assertSame(chatModelOf(first), chatModelOf(second));
		assertNotSame(chatModelOf(first), chatModelOf(allowing));
		assertEquals(List.of("searchKnowledgeBase"), names(defaultToolsOf(allowing)));
	}

	@Test
	void aModelWithNoToolsNeedsNoCopy() throws Exception {
		prepared(modelEnabling(), ContextContentRequired.NOT_REQUIRED, null);
		// an agent's notifyUser is made for its model and is not in its default options
		prepared(modelEnabling("notifyUser"), ContextContentRequired.NOT_REQUIRED, null);

		assertEquals(4, configured.size(), "the two models, no copy of them");
	}

	@Test
	void theControllingSoftwaresMandatoryToolsStayOffered() throws Exception {
		GeboToolCalling toolCalling = GeboToolCalling.builder().mandatoryTool(tool("mandatory")).caller("agent-1")
				.build();

		DefaultChatClientRequestSpec request = prepared(modelEnabling("searchKnowledgeBase"),
				ContextContentRequired.NOT_REQUIRED, toolCalling);

		assertTrue(defaultToolsOf(request).isEmpty());
		assertEquals(List.of("mandatory"), names(request.getToolCallbacks()));
	}
}
