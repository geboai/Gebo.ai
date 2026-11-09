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
import org.mockito.ArgumentCaptor;
import org.springframework.ai.document.Document;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentCapabilityResource;
import ai.gebo.architecture.agents.model.AgentMountedTools;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.model.LibraryToolCollision;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.architecture.ai.service.IGAIToolsLibraryInstancesService;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel.ChatModelConfigOptions;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.security.services.IGSecurityService;

/**
 * An agent configuration naming a library type gets the library in block: every
 * tool the type declares where a configuration is shown, the tools of the instance
 * the conversation holds where it runs. Nothing of a library is in the tool registry,
 * so a run with no instance has no tool of it, and a library's tool takes the place
 * of a mounted tool of its name.
 */
class AgentLibraryTypesMountingTest {
	private static final String WORKING_DIRECTORY = "working-directory";

	/** The least of an agent service: enough to resolve what it mounts. */
	private static final class AnAgentService extends GAbstractGenericalAgentService {
		AnAgentService(IGToolCallbackSourceRepositoryPattern tools, IGRuntimeBinder binder) {
			this(tools, binder, mock(IGChatModelRuntimeConfigurationDao.class));
		}

		AnAgentService(IGToolCallbackSourceRepositoryPattern tools, IGRuntimeBinder binder,
				IGChatModelRuntimeConfigurationDao models) {
			super(models, tools, mock(IGPromptConfigDao.class), binder, mock(IGSecurityService.class),
					mock(IAgentRoleDao.class), mock(IGDocumentContentRendererProvider.class));
		}

		@Override
		public String getId() {
			return "an-agent-service";
		}

		@Override
		public String getDescription() {
			return "An agent service";
		}
	}

	private static ToolCallback tool(String name) {
		ToolCallback tool = mock(ToolCallback.class);
		when(tool.getToolDefinition())
				.thenReturn(ToolDefinition.builder().name(name).description("Does " + name).inputSchema("{}").build());
		return tool;
	}

	private static AIToolsAndResourcesLibraryType aLibraryType(String typeId, String... toolNames) {
		AIToolsAndResourcesLibraryType type = new AIToolsAndResourcesLibraryType();
		type.setId(typeId);
		type.setDescription("Library " + typeId);
		type.setTools(List.of(toolNames).stream().map(name -> {
			ToolReference reference = new ToolReference();
			reference.setName(name);
			reference.setDescription("Does " + name);
			return reference;
		}).toList());
		return type;
	}

	/** A tool source exporting the tools of the given names. */
	private static IGToolCallbackSource aSource(String id, String... toolNames) {
		List<ToolCallback> callbacks = List.of(toolNames).stream().map(x -> tool(x)).toList();
		IGToolCallbackSource source = mock(IGToolCallbackSource.class);
		when(source.getId()).thenReturn(id);
		when(source.getDescription()).thenReturn("Source " + id);
		when(source.getToolCategory()).thenReturn(new ToolsCategory(id, "Category " + id, false));
		when(source.getToolCallbacks()).thenReturn(callbacks);
		return source;
	}

	private static IGToolCallbackSourceRepositoryPattern registryOf(IGToolCallbackSource... sources) {
		IGToolCallbackSourceRepositoryPattern registry = mock(IGToolCallbackSourceRepositoryPattern.class);
		when(registry.getImplementations()).thenReturn(List.of(sources));
		for (IGToolCallbackSource source : sources) {
			when(registry.findByCode(source.getId())).thenReturn(source);
		}
		return registry;
	}

	private static IGRuntimeBinder binderOf(IGAIToolsLibraryInstancesService instances) {
		IGRuntimeBinder binder = mock(IGRuntimeBinder.class);
		when(binder.getImplementationOf(IGAIToolsLibraryInstancesService.class)).thenReturn(instances);
		return binder;
	}

	/** The instances service of an installation having the given library types. */
	private static IGAIToolsLibraryInstancesService anInstallationWith(AIToolsAndResourcesLibraryType... types) {
		IGAIToolsLibraryInstancesService instances = mock(IGAIToolsLibraryInstancesService.class);
		when(instances.typesOf(any())).thenReturn(List.of(types));
		return instances;
	}

	private static GAgentConfig aCoder(String... enabledFunctions) {
		GAgentConfig config = new GAgentConfig();
		config.setCode("a-coder");
		config.setEnabledFunctions(List.of(enabledFunctions));
		config.setLibraryTypeIds(List.of(WORKING_DIRECTORY));
		return config;
	}

	private static List<String> names(AgentMountedTools mounted) {
		return mounted.getTools().stream().map(AgentMountedTools.MountedTool::getName).toList();
	}

	@Test
	void aConfigurationIsShownWithEveryToolOfItsLibraryInBlock() {
		AnAgentService service = new AnAgentService(registryOf(aSource("kb", "searchKnowledgeBase")), binderOf(
				anInstallationWith(aLibraryType(WORKING_DIRECTORY, "readFile", "writeFile", "listFiles"))));

		AgentMountedTools mounted = service.getMountedTools(aCoder("searchKnowledgeBase"));

		assertEquals(List.of("searchKnowledgeBase", "readFile", "writeFile", "listFiles"), names(mounted));
		AgentMountedTools.MountedTool readFile = mounted.getTools().get(1);
		assertEquals("Does readFile", readFile.getDescription());
		assertEquals(IGAIToolsLibraryInstancesService.LIBRARY_CATEGORY.getCode(), readFile.getCategoryCode());
		assertEquals(WORKING_DIRECTORY, readFile.getSourceId());
		assertEquals("Library: Library working-directory", readFile.getSourceDescription());
		assertTrue(mounted.getCollisions().isEmpty());
	}

	@Test
	void theCapabilitiesTellTheToolsOfTheLibraries() {
		AnAgentService service = new AnAgentService(registryOf(),
				binderOf(anInstallationWith(aLibraryType(WORKING_DIRECTORY, "readFile"))));
		AgentCapabilities capabilities = new AgentCapabilities();

		service.appendConfiguredTools(capabilities, aCoder("actualDateTime"));

		assertEquals(List.of("actualDateTime", "readFile"),
				capabilities.getTools().stream().map(AgentCapabilityResource::getCode).toList());
		assertEquals("Does readFile", capabilities.getTools().get(1).getDescription());
	}

	@Test
	void aLibraryTypeNobodyManagesMountsNothing() {
		AnAgentService service = new AnAgentService(registryOf(), binderOf(anInstallationWith()));

		assertEquals(List.of(), names(service.getMountedTools(aCoder())));
	}

	@Test
	void anAgentNamingNoLibraryIsUnchanged() {
		IGRuntimeBinder binder = mock(IGRuntimeBinder.class);
		AnAgentService service = new AnAgentService(registryOf(aSource("kb", "searchKnowledgeBase")), binder);
		GAgentConfig config = new GAgentConfig();
		config.setCode("a-plain-agent");
		config.setEnabledFunctions(List.of("searchKnowledgeBase"));

		assertEquals(List.of("searchKnowledgeBase"), names(service.getMountedTools(config)));
		verify(binder, never()).getImplementationOf(IGAIToolsLibraryInstancesService.class);
	}

	@Test
	void theToolsOfALibraryAreNotAmongTheNamesResolvedFromTheRegistry() {
		AnAgentService service = new AnAgentService(registryOf(),
				binderOf(anInstallationWith(aLibraryType(WORKING_DIRECTORY, "readFile"))));

		assertEquals(List.of("searchKnowledgeBase"), service.resolveMountedToolNames(aCoder("searchKnowledgeBase"), null));
	}

	@Test
	void aMountedToolWithTheNameOfALibraryToolCollidesAndTheLibraryOneIsShown() {
		AnAgentService service = new AnAgentService(registryOf(aSource("mcp-files", "readFile")),
				binderOf(anInstallationWith(aLibraryType(WORKING_DIRECTORY, "readFile"))));

		AgentMountedTools mounted = service.getMountedTools(aCoder("readFile"));

		assertEquals(List.of("readFile"), names(mounted));
		assertEquals(WORKING_DIRECTORY, mounted.getTools().get(0).getSourceId());
		assertEquals(1, mounted.getCollisions().size());
		LibraryToolCollision collision = mounted.getCollisions().get(0);
		assertEquals("readFile", collision.getToolName());
		assertEquals(WORKING_DIRECTORY, collision.getLibraryTypeId());
		assertEquals("mcp-files", collision.getShadowedSourceId());
		assertEquals("Source mcp-files", collision.getShadowedDescription());
	}

	@Test
	void aRegisteredToolTheAgentDoesNotMountIsNoCollision() {
		AnAgentService service = new AnAgentService(registryOf(aSource("mcp-files", "readFile")),
				binderOf(anInstallationWith(aLibraryType(WORKING_DIRECTORY, "readFile"))));

		assertTrue(service.getMountedTools(aCoder()).getCollisions().isEmpty());
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
	void anAgentEnteringItsExecutionHasTheToolsOfTheInstancesOfTheConversation() {
		IGAIToolsLibraryInstancesService instances = mock(IGAIToolsLibraryInstancesService.class);
		AIToolsAndResourcesLibraryInstance held = new AIToolsAndResourcesLibraryInstance();
		held.setId("dir-of-the-conversation");
		Map<String, AIToolsAndResourcesLibraryInstance> reclaimed = Map.of(WORKING_DIRECTORY, held);
		when(instances.reclaim("the-conversation", List.of(WORKING_DIRECTORY))).thenReturn(reclaimed);
		when(instances.describe(any())).thenReturn(List.of("The libraries at your disposal: a working directory"));
		ToolCallback readFile = tool("readFile");
		when(instances.toolsOf(reclaimed)).thenReturn(List.of(readFile));
		AnAgentService service = new AnAgentService(registryOf(), binderOf(instances));

		GAbstractGenericalAgentService.AgentLibraries entered = service.reclaimTheLibrariesOfTheConversation(
				aContextOf("the-conversation", List.of("Answer in English")), aCoder());

		assertEquals(List.of(readFile), entered.tools());
		assertEquals("there", entered.context().getToolsContext().get("already"), "what the context carried stays");
		assertEquals(List.of("Answer in English", "The libraries at your disposal: a working directory"),
				entered.context().getRulesToFollow(), "the conversation's rules first, then what the libraries hold");
		assertEquals("the-conversation", entered.context().getSessionID());
	}

	@Test
	void aRunWithNoConversationHoldingTheLibraryHasNoToolOfIt() {
		IGAIToolsLibraryInstancesService instances = mock(IGAIToolsLibraryInstancesService.class);
		when(instances.reclaim(any(), any())).thenReturn(Map.of());
		AnAgentService service = new AnAgentService(registryOf(), binderOf(instances));
		IChatRequestContext context = aContextOf(null, List.of());

		GAbstractGenericalAgentService.AgentLibraries entered = service.reclaimTheLibrariesOfTheConversation(context,
				aCoder());

		assertSame(context, entered.context());
		assertTrue(entered.tools().isEmpty());
		verify(instances, never()).toolsOf(any());
	}

	@Test
	void anAgentNamingNoLibraryEntersWithItsContextAsItIs() {
		IGRuntimeBinder binder = mock(IGRuntimeBinder.class);
		AnAgentService service = new AnAgentService(registryOf(), binder);
		GAgentConfig config = new GAgentConfig();
		config.setCode("a-plain-agent");
		IChatRequestContext context = aContextOf("the-conversation", List.of());

		GAbstractGenericalAgentService.AgentLibraries entered = service.reclaimTheLibrariesOfTheConversation(context,
				config);

		assertSame(context, entered.context());
		assertTrue(entered.tools().isEmpty());
		verify(binder, never()).getImplementationOf(IGAIToolsLibraryInstancesService.class);
	}

	@Test
	void anInstallationKeepingNoInstancesLeavesTheContextAsItIs() {
		IGRuntimeBinder binder = mock(IGRuntimeBinder.class);
		when(binder.getImplementationOf(IGAIToolsLibraryInstancesService.class))
				.thenThrow(new IllegalStateException("no such bean"));
		AnAgentService service = new AnAgentService(registryOf(), binder);
		IChatRequestContext context = aContextOf("the-conversation", List.of());

		GAbstractGenericalAgentService.AgentLibraries entered = service.reclaimTheLibrariesOfTheConversation(context,
				aCoder());

		assertSame(context, entered.context());
		assertTrue(entered.tools().isEmpty());
	}

	@Test
	@SuppressWarnings("unchecked")
	void theModelOfARunDeclaresTheBoundToolsOnceBesideTheConfiguredOnes() throws Exception {
		IGChatModelRuntimeConfigurationDao models = mock(IGChatModelRuntimeConfigurationDao.class);
		IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
		when(models.defaultHandler()).thenReturn(model);
		AnAgentService service = new AnAgentService(registryOf(), mock(IGRuntimeBinder.class), models);
		ToolCallback readFile = tool("readFile");
		ToolCallback writeFile = tool("writeFile");
		GAgentConfig config = aCoder("searchKnowledgeBase", "readFile");
		config.setUseDefaultChatModel(true);

		service.getAgentModel(config, null, null, null, List.of(readFile, writeFile));

		ArgumentCaptor<ChatModelConfigOptions> options = ArgumentCaptor.forClass(ChatModelConfigOptions.class);
		verify(model).cloneWithOptions(any(), options.capture());
		assertEquals(List.of("searchKnowledgeBase", "readFile", "writeFile"), options.getValue().getToolsName());
		assertEquals(List.of(readFile, writeFile), options.getValue().getAdditionalTools());
	}

	@Test
	void whatAPromptGetsFromTheLibrariesIsTheRulesOfTheTypesReallyHeld() {
		IGAIToolsLibraryInstancesService instances = mock(IGAIToolsLibraryInstancesService.class);
		Map<String, AIToolsAndResourcesLibraryInstance> reclaimed = Map.of(WORKING_DIRECTORY,
				new AIToolsAndResourcesLibraryInstance());
		when(instances.reclaim("the-conversation", List.of(WORKING_DIRECTORY))).thenReturn(reclaimed);
		when(instances.describe(any())).thenReturn(List.of());
		when(instances.toolsOf(reclaimed)).thenReturn(List.of());
		AIToolsAndResourcesLibraryType held = new AIToolsAndResourcesLibraryType();
		held.setId(WORKING_DIRECTORY);
		held.setAgentRules("Ask before going on.");
		when(instances.typesOf(reclaimed.keySet())).thenReturn(List.of(held));
		AnAgentService service = new AnAgentService(registryOf(), binderOf(instances));

		assertEquals("Ask before going on.", service
				.reclaimTheLibrariesOfTheConversation(aContextOf("the-conversation", List.of()), aCoder()).rules());
	}

	@Test
	void holdingNoLibraryNothingIsAddedToThePrompt() {
		IGAIToolsLibraryInstancesService instances = mock(IGAIToolsLibraryInstancesService.class);
		when(instances.reclaim(any(), any())).thenReturn(Map.of());
		AnAgentService service = new AnAgentService(registryOf(), binderOf(instances));

		assertEquals("", service.reclaimTheLibrariesOfTheConversation(aContextOf(null, List.of()), aCoder()).rules());
	}
}
