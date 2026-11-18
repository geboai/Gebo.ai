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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import ai.gebo.architecture.agents.controllers.GeboAgentAdminController;
import ai.gebo.architecture.agents.controllers.GeboAgentsNetworkAdminController;
import ai.gebo.architecture.agents.model.AgentMountedTools;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.tools.AskUserSingleChoiceQuestionTool;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySource;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySourceRepositoryPattern;
import ai.gebo.llms.agent.standard.config.AgenticLoopAgentsInitialization;
import ai.gebo.llms.agent.standardlibraries.UserQuestionsLibrarySource;
import ai.gebo.llms.agent.standardlibraries.WorkingDirectoryLibrarySource;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatAgentsToolsDescriber;

/**
 * The agentic loop agents mount every library the installation has: a library added
 * to it reaches them with no change to their configuration, the ones mounted only on
 * the agents naming them do not, and a copy may leave some out. The chats on their
 * network accept the files those libraries work on. The libraries standing for
 * added ones are this class's own beans, so no other test context gets them.
 */
public class AgentsMountEveryLibraryTest extends AbstractBaseIntegrationTest {
	private static final String ADDED = "test-added-library";
	private static final String ASKED_ONLY = "test-asked-only-library";

	@MockitoSpyBean
	private IGAIToolsAndResourcesLibrarySourceRepositoryPattern librarySources;
	@Autowired
	private GeboAgentsNetworkAdminController networkController;
	@Autowired
	private GeboAgentAdminController agentController;
	@Autowired
	private IGChatAgentsToolsDescriber describer;

	@Override
	protected void beforeEachCallback() throws Exception {
		doAnswer(invocation -> {
			@SuppressWarnings("unchecked")
			final List<IGAIToolsAndResourcesLibrarySource> real = (List<IGAIToolsAndResourcesLibrarySource>) invocation
					.callRealMethod();
			final List<IGAIToolsAndResourcesLibrarySource> all = new ArrayList<>(real);
			all.add(new TestLibraries());
			return all;
		}).when(librarySources).getImplementations();
	}

	private List<String> mountedToolsOf(String agentConfigCode) {
		final List<AgentMountedTools> mounted = networkController.getAgentsMountedTools(List.of(agentConfigCode));
		assertEquals(1, mounted.size(), agentConfigCode);
		return mounted.get(0).getTools().stream().map(AgentMountedTools.MountedTool::getName).toList();
	}

	@Test
	public void theLoopAgentsMountTheAddedLibraryButTheOneAskedForOnly() {
		for (String code : List.of(AgenticLoopAgentsInitialization.AGENTIC_LOOP_AGENT_CONFIG,
				AgenticLoopAgentsInitialization.AGENTIC_LOOP_PURE_CHAT_AGENT_CONFIG)) {
			final List<String> tools = mountedToolsOf(code);
			assertTrue(tools.contains("drawPicture"), code + " mounts the library added: " + tools);
			assertTrue(tools.contains(WorkingDirectoryLibrarySource.LIST_FILES_TOOL),
					code + " mounts the working directory: " + tools);
			assertFalse(tools.contains("openAProcess"), code + " does not mount a library asked for only: " + tools);
		}
	}

	@Test
	public void aCopyLeavesLibrariesOutAndStillNamesItsOwn() throws Exception {
		final GAgentConfig copy = agentController.copyAgent(AgenticLoopAgentsInitialization.AGENTIC_LOOP_AGENT_CONFIG)
				.getResult();
		assertTrue(Boolean.TRUE.equals(copy.getSubscribeAllLibraries()), "the copy mounts every library too");
		copy.setExcludedLibraryTypeIds(List.of(ADDED, WorkingDirectoryLibrarySource.TYPE_ID,
				UserQuestionsLibrarySource.TYPE_ID));
		copy.setLibraryTypeIds(List.of(UserQuestionsLibrarySource.TYPE_ID, ASKED_ONLY));
		agentController.updateAgent(copy);
		try {
			final List<String> tools = mountedToolsOf(copy.getCode());
			assertFalse(tools.contains("drawPicture"), "left out: " + tools);
			assertFalse(tools.contains(WorkingDirectoryLibrarySource.LIST_FILES_TOOL), "left out: " + tools);
			assertTrue(tools.contains("openAProcess"), "named, so mounted: " + tools);
			assertEquals(List.of(ADDED, WorkingDirectoryLibrarySource.TYPE_ID, UserQuestionsLibrarySource.TYPE_ID),
					agentController.getAgentByCode(copy.getCode()).getExcludedLibraryTypeIds(), "saved as set");
			assertTrue(tools.contains(AskUserSingleChoiceQuestionTool.TOOL_NAME),
					"user questions named, so mounted even if left out: " + tools);
		} finally {
			agentController.deleteAgent(copy);
		}
	}

	@Test
	public void aChatOnTheLoopNetworkAcceptsTheFilesOfTheAddedLibrary() {
		final GChatProfileConfiguration profile = new GChatProfileConfiguration();
		profile.setCode("a-profile-on-the-loop-network");
		profile.setDefaultChatNetworkOfAgents(AgenticLoopAgentsInitialization.AGENTIC_LOOP_AGENTS_NETWORK);
		final List<String> libraries = describer.libraryTypeIds(profile);
		assertTrue(libraries.contains(ADDED), "added: " + libraries);
		assertTrue(libraries.contains(WorkingDirectoryLibrarySource.TYPE_ID), "working directory: " + libraries);
		assertTrue(libraries.contains(UserQuestionsLibrarySource.TYPE_ID), "user questions: " + libraries);
		assertFalse(libraries.contains(ASKED_ONLY), "asked for only: " + libraries);
	}

	private static AIToolsAndResourcesLibraryType type(String id, String toolName, boolean onlyWhenAsked) {
		final AIToolsAndResourcesLibraryType type = new AIToolsAndResourcesLibraryType();
		type.setId(id);
		type.setDescription("Test library " + id);
		type.setResourcesTypes(List.of());
		final ToolReference tool = new ToolReference();
		tool.setName(toolName);
		tool.setDescription("Does " + toolName);
		type.setTools(List.of(tool));
		type.setUploadFileTypes(List.of("PNG"));
		type.setMountedOnlyWhenAsked(onlyWhenAsked);
		return type;
	}

	/** A library added to the installation, and one mounted only on the agents naming it; they never run here. */
	private static final class TestLibraries implements IGAIToolsAndResourcesLibrarySource {
		@Override
		public String getId() {
			return "test-libraries";
		}

		@Override
		public String getDescription() {
			return "Test libraries";
		}

		@Override
		public List<AIToolsAndResourcesLibraryType> getManagedTypes() {
			return List.of(type(ADDED, "drawPicture", false), type(ASKED_ONLY, "openAProcess", true));
		}

		@Override
		public AIToolsAndResourcesLibraryInstance getInstanceById(String id, String owner) {
			return null;
		}

		@Override
		public AIToolsAndResourcesLibraryInstance createInstance(AIToolsAndResourcesLibraryType type,
				List<String> owners) {
			return null;
		}

		@Override
		public void dropInstance(AIToolsAndResourcesLibraryInstance instance) {
		}

		@Override
		public List<ToolCallback> getToolCallbacks(AIToolsAndResourcesLibraryInstance instance) {
			return List.of();
		}
	}
}
