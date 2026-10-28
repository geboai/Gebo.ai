/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.service.AIToolsLibraryTypeToolSource;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySource;
import ai.gebo.architecture.ai.service.IGAIToolsLibraryInstancesService;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.security.services.IGSecurityService;

/**
 * An agent configuration naming a library type mounts the library in block:
 * every tool of the type, beside whatever else it mounts, and nothing of a type
 * this installation does not have.
 */
class AgentLibraryTypesMountingTest {

	/** The least of an agent service: enough to resolve what it mounts. */
	private static final class AnAgentService extends GAbstractGenericalAgentService {
		AnAgentService(IGToolCallbackSourceRepositoryPattern tools) {
			this(tools, mock(IGRuntimeBinder.class));
		}

		AnAgentService(IGToolCallbackSourceRepositoryPattern tools, IGRuntimeBinder binder) {
			super(mock(IGChatModelRuntimeConfigurationDao.class), tools, mock(IGPromptConfigDao.class), binder,
					mock(IGSecurityService.class), mock(IAgentRoleDao.class),
					mock(IGDocumentContentRendererProvider.class));
		}

		@Override
		public String getId() {
			return "an-agent-service";
		}

		@Override
		public String getDescription() {
			return "An agent service";
		}

		List<String> mounts(GAgentConfig config) {
			return resolveMountedToolNames(config, null);
		}
	}

	private static AIToolsLibraryTypeToolSource aLibraryType(String typeId, String... toolNames) {
		AIToolsAndResourcesLibraryType type = new AIToolsAndResourcesLibraryType();
		type.setId(typeId);
		type.setDescription("Library " + typeId);
		type.setTools(List.of(toolNames).stream().map(name -> {
			ToolReference reference = new ToolReference();
			reference.setName(name);
			reference.setDescription(name);
			reference.setInputSchema("{}");
			return reference;
		}).toList());
		IGAIToolsAndResourcesLibrarySource library = mock(IGAIToolsAndResourcesLibrarySource.class);
		when(library.getId()).thenReturn("libraries");
		when(library.getManagedTypes()).thenReturn(List.of(type));
		when(library.getToolCallbacks(org.mockito.ArgumentMatchers.any(AIToolsAndResourcesLibraryInstance.class)))
				.thenReturn(List.of());
		return new AIToolsLibraryTypeToolSource(type, library);
	}

	private static IGToolCallbackSourceRepositoryPattern registryOf(IGToolCallbackSource... sources) {
		IGToolCallbackSourceRepositoryPattern registry = mock(IGToolCallbackSourceRepositoryPattern.class);
		when(registry.getImplementations()).thenReturn(List.of(sources));
		for (IGToolCallbackSource source : sources) {
			when(registry.findByCode(source.getId())).thenReturn(source);
		}
		return registry;
	}

	@Test
	void namingALibraryTypeMountsEveryToolOfItInBlock() {
		AnAgentService service = new AnAgentService(
				registryOf(aLibraryType("working-directory", "readFile", "writeFile", "listFiles")));
		GAgentConfig config = new GAgentConfig();
		config.setCode("a-coder");
		config.setEnabledFunctions(List.of("searchKnowledgeBase"));
		config.setLibraryTypeIds(List.of("working-directory"));

		List<String> mounted = service.mounts(config);

		assertEquals(List.of("searchKnowledgeBase"), mounted.subList(0, 1), "what it mounted by name stays first");
		assertTrue(mounted.containsAll(List.of("readFile", "writeFile", "listFiles")), mounted.toString());
		assertEquals(4, mounted.size());
	}

	@Test
	void aLibraryTypeNobodyManagesMountsNothing() {
		AnAgentService service = new AnAgentService(registryOf(aLibraryType("working-directory", "readFile")));
		GAgentConfig config = new GAgentConfig();
		config.setCode("a-dreamer");
		config.setLibraryTypeIds(List.of("a-library-this-installation-lacks"));

		assertEquals(List.of(), service.mounts(config));
	}

	@Test
	void anAgentNamingNoLibraryIsUnchanged() {
		AnAgentService service = new AnAgentService(registryOf(aLibraryType("working-directory", "readFile")));
		GAgentConfig config = new GAgentConfig();
		config.setCode("a-plain-agent");
		config.setEnabledFunctions(List.of("searchKnowledgeBase"));

		assertEquals(List.of("searchKnowledgeBase"), service.mounts(config));
	}

	/** A request context of a conversation, with the rules it already has. */
	private static IChatRequestContext aContextOf(String conversation, List<String> rules) {
		return new IChatRequestContext() {
			@Override
			public String getRequestID() {
				return "request-1";
			}

			@Override
			public String getSessionID() {
				return conversation;
			}

			@Override
			public String getConsolidatedHistory() {
				return "";
			}

			@Override
			public List<IChatSessionEntry> getInteractions() {
				return List.of();
			}

			@Override
			public List<Document> getDocuments() {
				return List.of();
			}

			@Override
			public String getActualUserRequest() {
				return "do the work";
			}

			@Override
			public Map<String, Object> getToolsContext() {
				return Map.of("already", "there");
			}

			@Override
			public Map<String, Object> getPipelineInfos() {
				return Map.of();
			}

			@Override
			public ToolCallsListener getToolCallListener() {
				return null;
			}

			@Override
			public List<String> getRulesToFollow() {
				return rules;
			}
		};
	}

	@Test
	@SuppressWarnings("unchecked")
	void anAgentEnteringItsExecutionReclaimsTheLibrariesOfTheConversation() {
		IGToolCallbackSourceRepositoryPattern registry = registryOf(aLibraryType("working-directory", "readFile"));
		IGRuntimeBinder binder = mock(IGRuntimeBinder.class);
		IGAIToolsLibraryInstancesService instances = mock(IGAIToolsLibraryInstancesService.class);
		when(binder.getImplementationOf(IGAIToolsLibraryInstancesService.class)).thenReturn(instances);
		AIToolsAndResourcesLibraryInstance held = new AIToolsAndResourcesLibraryInstance();
		held.setId("dir-of-the-conversation");
		when(instances.reclaim("the-conversation", List.of("working-directory")))
				.thenReturn(Map.of("working-directory", held));
		when(instances.describe(any())).thenReturn(List.of("The libraries at your disposal: a working directory"));
		AnAgentService service = new AnAgentService(registry, binder);
		GAgentConfig config = new GAgentConfig();
		config.setCode("a-coder");
		config.setLibraryTypeIds(List.of("working-directory"));

		IChatRequestContext entered = service.withTheLibrariesOfTheConversation(
				aContextOf("the-conversation", List.of("Answer in English")), config);

		Map<String, AIToolsAndResourcesLibraryInstance> inContext = (Map<String, AIToolsAndResourcesLibraryInstance>) entered
				.getToolsContext().get(ToolCallbackDeclarationUtil.LIBRARY_INSTANCES_CONTEXT_KEY);
		assertEquals(held, inContext.get("working-directory"), "the tools find the instance in their context");
		assertEquals("there", entered.getToolsContext().get("already"), "what the context carried stays");
		assertEquals(List.of("Answer in English", "The libraries at your disposal: a working directory"),
				entered.getRulesToFollow(), "the conversation's rules first, then what the libraries hold");
		assertEquals("the-conversation", entered.getSessionID());
	}

	@Test
	void anAgentNamingNoLibraryEntersWithItsContextAsItIs() {
		IGRuntimeBinder binder = mock(IGRuntimeBinder.class);
		AnAgentService service = new AnAgentService(registryOf(), binder);
		GAgentConfig config = new GAgentConfig();
		config.setCode("a-plain-agent");
		IChatRequestContext context = aContextOf("the-conversation", List.of());

		assertSame(context, service.withTheLibrariesOfTheConversation(context, config));
		verify(binder, never()).getImplementationOf(IGAIToolsLibraryInstancesService.class);
	}

	@Test
	void anInstallationKeepingNoInstancesLeavesTheContextAsItIs() {
		IGRuntimeBinder binder = mock(IGRuntimeBinder.class);
		when(binder.getImplementationOf(IGAIToolsLibraryInstancesService.class))
				.thenThrow(new IllegalStateException("no such bean"));
		AnAgentService service = new AnAgentService(registryOf(aLibraryType("working-directory", "readFile")), binder);
		GAgentConfig config = new GAgentConfig();
		config.setCode("a-coder");
		config.setLibraryTypeIds(List.of("working-directory"));
		IChatRequestContext context = aContextOf("the-conversation", List.of());

		assertSame(context, service.withTheLibrariesOfTheConversation(context, config));
	}

	@Test
	void theLibraryToolsCountAsAnyOtherToolCallback() {
		AIToolsLibraryTypeToolSource source = aLibraryType("working-directory", "readFile");
		List<ToolCallback> callbacks = source.getToolCallbacks();

		assertEquals(1, callbacks.size());
		assertEquals("readFile", callbacks.get(0).getToolDefinition().name());
	}
}
