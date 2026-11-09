/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standardlibraries;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.agents.model.ClarificationOptions;
import ai.gebo.architecture.agents.services.GWaitExternalConditionSink;
import ai.gebo.architecture.agents.services.IGDynamicAgentConfigDataSource;
import ai.gebo.architecture.agents.services.IGWaitExternalCondition;
import ai.gebo.architecture.agents.tools.AskUserMultipleChoiceQuestionTool;
import ai.gebo.architecture.agents.tools.AskUserSingleChoiceQuestionTool;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.agent.standard.config.AgenticLoopAgentsInitialization;

/**
 * The library of the tools asking the user: the two question tools and no
 * resources, with an instance that keeps nothing and is rebuilt from its id.
 */
class UserQuestionsLibrarySourceTest {
	private static final String QUESTION = "{\"question\":\"Which year?\",\"options\":[{\"label\":\"2023\"},{\"label\":\"2024\"}]}";
	private final UserQuestionsLibrarySource source = new UserQuestionsLibrarySource();
	private final AIToolsAndResourcesLibraryType type = source.getManagedTypes().get(0);

	private static ToolReference named(List<ToolReference> references, String name) {
		return references.stream().filter(r -> name.equals(r.getName())).findFirst().orElseThrow();
	}

	private static ToolCallback tool(List<ToolCallback> tools, String name) {
		return tools.stream().filter(t -> name.equals(t.getToolDefinition().name())).findFirst().orElseThrow();
	}

	@Test
	void theTypeBundlesTheTwoQuestionToolsAndNoResources() {
		assertEquals(UserQuestionsLibrarySource.TYPE_ID, type.getId());
		assertEquals("User questions and clarifications tool", type.getDescription());
		assertTrue(type.getResourcesTypes().isEmpty());
		assertEquals(List.of(AskUserSingleChoiceQuestionTool.TOOL_NAME, AskUserMultipleChoiceQuestionTool.TOOL_NAME),
				type.getTools().stream().map(ToolReference::getName).toList());
	}

	@Test
	void theReferencesOfferWhatTheToolsTake() {
		AIToolsAndResourcesLibraryInstance instance = source.createInstance(type, List.of("a-conversation"));

		for (ToolCallback tool : source.getToolCallbacks(instance)) {
			ToolReference reference = named(type.getTools(), tool.getToolDefinition().name());
			assertEquals(tool.getToolDefinition().inputSchema(), reference.getInputSchema());
			assertEquals(tool.getToolDefinition().description(), reference.getDescription());
			assertTrue(reference.getUserUIfunctionDescription() != null);
		}
	}

	@Test
	void theToolsOfAnInstanceEndTheCallTheyArePartOf() {
		AIToolsAndResourcesLibraryInstance instance = source.createInstance(type, List.of("a-conversation"));

		List<ToolCallback> tools = source.getToolCallbacks(instance);

		assertEquals(2, tools.size());
		assertTrue(tools.stream().allMatch(tool -> tool.getToolMetadata().returnDirect()));
	}

	@Test
	void anInstanceBelongsToItsOwnersAndHasNoResources() {
		AIToolsAndResourcesLibraryInstance instance = source.createInstance(type, List.of("a-conversation", "a-user"));

		assertEquals(type, instance.getLibraryType());
		assertEquals(List.of("a-conversation", "a-user"), instance.getOwnersId());
		assertTrue(instance.getResources().isEmpty());
		assertEquals(type.getTools(), instance.getTools());
		assertNotEquals(instance.getId(), source.createInstance(type, null).getId());
	}

	@Test
	void anInstanceOfAnotherTypeIsRefused() {
		AIToolsAndResourcesLibraryType other = new AIToolsAndResourcesLibraryType();
		other.setId("working-directory");

		assertThrows(IllegalArgumentException.class, () -> source.createInstance(other, List.of("a-conversation")));
		assertThrows(IllegalArgumentException.class, () -> source.createInstance(null, List.of("a-conversation")));
	}

	@Test
	void anInstanceIsRebuiltFromItsIdWithNothingKept() {
		String id = source.createInstance(type, List.of("a-conversation")).getId();

		AIToolsAndResourcesLibraryInstance rebuilt = new UserQuestionsLibrarySource().getInstanceById(id,
				"a-conversation");

		assertEquals(id, rebuilt.getId());
		assertEquals(UserQuestionsLibrarySource.TYPE_ID, rebuilt.getLibraryType().getId());
		assertEquals(List.of("a-conversation"), rebuilt.getOwnersId());
	}

	@Test
	void whatIsNotTheIdOfAnInstanceFindsNothing() {
		assertNull(source.getInstanceById(null, "a-conversation"));
		assertNull(source.getInstanceById(" ", "a-conversation"));
		assertNull(source.getInstanceById("../somewhere", "a-conversation"));
	}

	@Test
	void droppingAnInstanceReleasesNothing() {
		assertDoesNotThrow(() -> source.dropInstance(source.createInstance(type, List.of("a-conversation"))));
		assertDoesNotThrow(() -> source.dropInstance(null));
	}

	@Test
	void aToolCalledThroughTheLibraryDeclaresTheQuestionToTheRunsWait() {
		GWaitExternalConditionSink wait = new GWaitExternalConditionSink();
		AIToolsAndResourcesLibraryInstance instance = source.createInstance(type, List.of("a-conversation"));
		GeboToolCalling toolCalling = GeboToolCalling.builder().provide(IGWaitExternalCondition.class, wait).build();
		ToolContext context = new ToolContext(toolCalling.toolsContext(Map.of()));
		ToolCallback question = tool(source.getToolCallbacks(instance), AskUserSingleChoiceQuestionTool.TOOL_NAME);

		question.call(QUESTION, context);

		assertEquals(1, wait.waited().size());
		assertEquals("Which year?", wait.waited().get(0).getWaitingConditionStatement());
		ClarificationOptions options = (ClarificationOptions) wait.waited().get(0).getLlmChosenOptions();
		assertEquals(2, options.getOptions().size());
	}

	@Test
	void theToolsOfAnInstanceEndTheCall() {
		List<ToolCallback> tools = source.getToolCallbacks(source.createInstance(type, List.of("a-conversation")));

		assertEquals(2, tools.size());
		assertTrue(tools.stream().allMatch(tool -> tool.getToolMetadata().returnDirect()),
				"the loop stops at the question: the work waits");
	}

	@Test
	void theAgenticLoopAgentsNameTheLibrary() {
		AgenticLoopAgentsInitialization agents = new AgenticLoopAgentsInitialization();

		for (IGDynamicAgentConfigDataSource configs : List.of(agents.agenticLoopAgentConfigDataSource(),
				agents.agenticLoopPureChatAgentConfigDataSource())) {
			assertEquals(List.of(UserQuestionsLibrarySource.TYPE_ID),
					configs.getConfigurations().get(0).getLibraryTypeIds());
		}
	}

	@Test
	void whetherAToolEndsTheCallStaysOutOfTheJson() throws Exception {
		String json = new ObjectMapper().writeValueAsString(type.getTools().get(0));

		assertTrue(type.getTools().get(0).isReturnDirect());
		assertFalse(json.contains("returnDirect"), json);
	}

	@Test
	void theLibraryTellsTheAgentsThatAQuestionComesBeforeTheirUsualOutput() {
		String rules = new UserQuestionsLibrarySource().getManagedTypes().get(0).getAgentRules();

		assertTrue(rules.startsWith("## Asking the user"), "a section of its own is read as one: " + rules);
		assertTrue(rules.contains("do NOT produce your usual output"), rules);
		assertTrue(rules.contains("yourself"), "an agent left to delegate leaves the question to the writer: " + rules);
		new UserQuestionsLibrarySource().getManagedTypes().get(0).getTools()
				.forEach(tool -> assertTrue(rules.contains(tool.getName()), tool.getName() + " in: " + rules));
		assertFalse(rules.contains("{"), "the rules go in a template as a value: " + rules);
	}
}
