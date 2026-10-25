/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standard.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

import ai.gebo.architecture.agents.config.AgentsToolsAutoMountingConfig;
import ai.gebo.architecture.agents.model.AgentMountedTools;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.agent.standard.services.StringToStringToolCallingNetworkAgent;

/**
 * A family of tools only some agents are meant to operate: the installation keeps
 * the source out of automatic mounting, and the agents that must reach it name it
 * among the sources they mount on top.
 * <p>
 * There is no other way back. An agent that mounts every registered tool cannot
 * reach an excluded one by selecting it by name, because the automatic mounting
 * replaces the tools selected by name outright; and leaving the source mounted for
 * everyone puts those tools in front of every chat.
 */
class AgentAdditionalToolSourcesTest {
	private static final String OUTCOME_SOURCE = "harness-outcome-tool-source";
	private static final String GOAL_REACHED = "goalReached";
	private static final String NEEDS_A_PERSON = "needsAPerson";

	private static ToolCallback tool(String name) {
		ToolCallback callback = mock(ToolCallback.class);
		when(callback.getToolDefinition())
				.thenReturn(ToolDefinition.builder().name(name).description(name).inputSchema("{}").build());
		return callback;
	}

	private static IGToolCallbackSource source(String id, ToolCallback... tools) {
		IGToolCallbackSource source = mock(IGToolCallbackSource.class);
		when(source.getId()).thenReturn(id);
		when(source.getDescription()).thenReturn("source " + id);
		ToolsCategory category = new ToolsCategory();
		category.setCode(id);
		category.setDescription(id);
		when(source.getToolCategory()).thenReturn(category);
		when(source.getToolCallbacks()).thenReturn(List.of(tools));
		return source;
	}

	/** A source whose tools belong to the agents that ask for it, by its own nature. */
	private static IGToolCallbackSource sourceMountedOnlyWhenAsked(String id, ToolCallback... tools) {
		IGToolCallbackSource source = source(id, tools);
		when(source.isMountedOnlyWhenAsked()).thenReturn(true);
		return source;
	}

	private static IGToolCallbackSourceRepositoryPattern toolsRepository() {
		return toolsRepository(source(OUTCOME_SOURCE, tool(GOAL_REACHED), tool(NEEDS_A_PERSON)));
	}

	private static IGToolCallbackSourceRepositoryPattern toolsRepository(IGToolCallbackSource outcomeSource) {
		List<IGToolCallbackSource> sources = List.of(source("ActualDateFunctions", tool("getActualDate")),
				source("mcp-tools-exporter", tool("mcpCreateTicket")), outcomeSource);
		List<ToolCallback> all = new ArrayList<>();
		sources.forEach(x -> all.addAll(x.getToolCallbacks()));
		IGToolCallbackSourceRepositoryPattern repository = mock(IGToolCallbackSourceRepositoryPattern.class);
		when(repository.getImplementations()).thenReturn(sources);
		when(repository.getTools()).thenReturn(all);
		return repository;
	}

	/** The installation keeping a whole source out of automatic mounting. */
	private static IGRuntimeBinder binderExcluding(String... sourceIds) {
		AgentsToolsAutoMountingConfig config = new AgentsToolsAutoMountingConfig();
		config.setExcludedToolSources(List.of(sourceIds));
		IGRuntimeBinder binder = mock(IGRuntimeBinder.class);
		when(binder.getImplementationOf(AgentsToolsAutoMountingConfig.class)).thenReturn(config);
		return binder;
	}

	private static StringToStringToolCallingNetworkAgent agent(IGToolCallbackSourceRepositoryPattern repository,
			IGRuntimeBinder binder) {
		return new StringToStringToolCallingNetworkAgent(null, repository, null, null, null, binder, null);
	}

	private static GAgentConfig anAgentMountingEveryTool() {
		GAgentConfig config = new GAgentConfig();
		config.setCode("an-agent-mounting-every-tool");
		config.setAgentServiceId(StringToStringToolCallingNetworkAgent.STRING_TO_STRING_TOOL_CALLING_AGENT_SERVICE);
		config.setSubscribeAllTools(true);
		return config;
	}

	private static Set<String> mounted(GAgentConfig config, IGRuntimeBinder binder) {
		return mounted(config, binder, toolsRepository());
	}

	private static Set<String> mounted(GAgentConfig config, IGRuntimeBinder binder,
			IGToolCallbackSourceRepositoryPattern repository) {
		AgentMountedTools tools = agent(repository, binder).getMountedTools(config);
		return tools.getTools().stream().map(AgentMountedTools.MountedTool::getName).collect(Collectors.toSet());
	}

	@Test
	void anAgentMountingEveryToolDoesNotReachAnExcludedSource() {
		Set<String> tools = mounted(anAgentMountingEveryTool(), binderExcluding(OUTCOME_SOURCE));

		assertTrue(tools.contains("getActualDate"), "it mounts what is not excluded");
		assertFalse(tools.contains(GOAL_REACHED), "the installation keeps these out of every chat");
		assertFalse(tools.contains(NEEDS_A_PERSON));
	}

	@Test
	void selectingAnExcludedToolByNameIsNotEnough() {
		GAgentConfig config = anAgentMountingEveryTool();
		config.setEnabledFunctions(List.of(GOAL_REACHED, NEEDS_A_PERSON));

		assertFalse(mounted(config, binderExcluding(OUTCOME_SOURCE)).contains(GOAL_REACHED),
				"the automatic mounting replaces the tools selected by name");
	}

	@Test
	void theAgentThatMustReachThemMountsTheSourceOnTop() {
		GAgentConfig config = anAgentMountingEveryTool();
		config.setAdditionalToolSources(List.of(OUTCOME_SOURCE));

		Set<String> tools = mounted(config, binderExcluding(OUTCOME_SOURCE));

		assertTrue(tools.contains(GOAL_REACHED));
		assertTrue(tools.contains(NEEDS_A_PERSON));
		assertTrue(tools.contains("getActualDate"), "without losing the tools it already had");
		assertTrue(tools.contains("mcpCreateTicket"));
	}

	@Test
	void anAgentDoesNotContradictItself() {
		GAgentConfig config = anAgentMountingEveryTool();
		config.setAdditionalToolSources(List.of(OUTCOME_SOURCE));
		config.setExcludedToolSources(List.of(OUTCOME_SOURCE));

		assertFalse(mounted(config, binderExcluding(OUTCOME_SOURCE)).contains(GOAL_REACHED),
				"what the configuration itself leaves out stays out");
	}

	@Test
	void anAgentOnToolsSelectedByNameKeepsThemAndAddsTheSource() {
		GAgentConfig config = new GAgentConfig();
		config.setCode("an-agent-on-one-tool");
		config.setAgentServiceId(StringToStringToolCallingNetworkAgent.STRING_TO_STRING_TOOL_CALLING_AGENT_SERVICE);
		config.setEnabledFunctions(List.of("getActualDate"));
		config.setAdditionalToolSources(List.of(OUTCOME_SOURCE));

		Set<String> tools = mounted(config, binderExcluding(OUTCOME_SOURCE));

		assertEquals(Set.of("getActualDate", GOAL_REACHED, NEEDS_A_PERSON), tools,
				"it was never mounting every tool");
	}

	@Test
	void aSourceMountedOnlyWhenAskedStaysOutWithNoInstallationSettingAtAll() {
		IGToolCallbackSourceRepositoryPattern repository = toolsRepository(
				sourceMountedOnlyWhenAsked(OUTCOME_SOURCE, tool(GOAL_REACHED), tool(NEEDS_A_PERSON)));

		Set<String> tools = mounted(anAgentMountingEveryTool(), binderExcluding(), repository);

		assertTrue(tools.contains("getActualDate"), "it mounts the tools that are mounted on everyone");
		assertFalse(tools.contains(GOAL_REACHED),
				"no setting is needed: the source itself says who its tools are for");
	}

	@Test
	void theAgentAskingForItReachesASourceMountedOnlyWhenAsked() {
		IGToolCallbackSourceRepositoryPattern repository = toolsRepository(
				sourceMountedOnlyWhenAsked(OUTCOME_SOURCE, tool(GOAL_REACHED), tool(NEEDS_A_PERSON)));
		GAgentConfig config = anAgentMountingEveryTool();
		config.setAdditionalToolSources(List.of(OUTCOME_SOURCE));

		Set<String> tools = mounted(config, binderExcluding(), repository);

		assertEquals(Set.of("getActualDate", "mcpCreateTicket", GOAL_REACHED, NEEDS_A_PERSON), tools);
	}

	@Test
	void mountingASourceNothingExcludesChangesNothing() {
		GAgentConfig config = anAgentMountingEveryTool();
		config.setAdditionalToolSources(List.of("ActualDateFunctions"));

		Set<String> tools = mounted(config, binderExcluding());

		assertEquals(Set.of("getActualDate", "mcpCreateTicket", GOAL_REACHED, NEEDS_A_PERSON), tools,
				"each tool mounted once");
	}
}
