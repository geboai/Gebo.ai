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
import ai.gebo.llms.agent.standardtools.DeepSearchToolSource;
import ai.gebo.llms.agent.standardtools.InternalKnowledgeBaseSearchToolSource;
import ai.gebo.llms.agent.standardtools.StandardSearchesToolsImpl;
import ai.gebo.llms.agent.standardtools.WebSearchToolSource;

/**
 * The tool-calling agent of the networks with search agents mounts every registered
 * tool but the ones of the search tool sources: in those networks each knowledge
 * base search and each search service is an agent of its own, so the tool-calling
 * agent runs the other tools (MCP tools, the basic tools). The exclusion is the
 * agent configuration's own, not a system-wide one: another tool-calling agent,
 * made by the admin for a network without search agents, mounts the search tools.
 */
class ToolCallingAgentExcludedSearchSourcesTest {

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

	/** A tools repository with one tool per search source, an MCP tool and a date tool. */
	private static IGToolCallbackSourceRepositoryPattern toolsRepository() {
		List<IGToolCallbackSource> sources = List.of(
				source(InternalKnowledgeBaseSearchToolSource.INTERNAL_KNOWLEDGE_BASE_SEARCH_TOOL_SOURCE,
						tool("searchKnowledgeBase")),
				source(StandardSearchesToolsImpl.STANDARD_SEARCHES_TOOLS_SOURCE, tool("searchConfluence"),
						tool("searchJira")),
				source(WebSearchToolSource.WEB_SEARCH_TOOL_SOURCE, tool("searchWeb")),
				source(DeepSearchToolSource.DEEP_SEARCH_TOOL_SOURCE, tool("deepSearchWeb"),
						tool("deepSearchKnowledgeBase")),
				source("mcp-tools-exporter", tool("mcpCreateTicket")),
				source("ActualDateFunctions", tool("getActualDate")));
		List<ToolCallback> all = new ArrayList<>();
		sources.forEach(x -> all.addAll(x.getToolCallbacks()));
		IGToolCallbackSourceRepositoryPattern repository = mock(IGToolCallbackSourceRepositoryPattern.class);
		when(repository.getImplementations()).thenReturn(sources);
		when(repository.getTools()).thenReturn(all);
		return repository;
	}

	private static StandardAgentsInitialization initialization() {
		return new StandardAgentsInitialization(null, null, null, null, null, null, null, null, null, null, null,
				new StandardAgentsConfig());
	}

	private static StringToStringToolCallingNetworkAgent toolCallingAgent(
			IGToolCallbackSourceRepositoryPattern repository) {
		// no system-wide exclusion configured: the runtime binder knows no configuration
		return new StringToStringToolCallingNetworkAgent(null, repository, null, null, null,
				mock(IGRuntimeBinder.class), null);
	}

	private static Set<String> names(List<AgentMountedTools.MountedTool> tools) {
		return tools.stream().map(AgentMountedTools.MountedTool::getName).collect(Collectors.toSet());
	}

	@Test
	void theNetworkToolCallingAgentLeavesTheSearchSourcesToTheSearchAgents() {
		GAgentConfig config = initialization().defaultToolCallingAgentConfigDataSource().getConfigurations().get(0);

		assertEquals(Boolean.TRUE, config.getSubscribeAllTools(), "it operates whatever tool is registered");
		assertEquals(StandardAgentsInitialization.SEARCH_TOOL_SOURCES, config.getExcludedToolSources());
		assertEquals(Set.of(InternalKnowledgeBaseSearchToolSource.INTERNAL_KNOWLEDGE_BASE_SEARCH_TOOL_SOURCE,
				StandardSearchesToolsImpl.STANDARD_SEARCHES_TOOLS_SOURCE, WebSearchToolSource.WEB_SEARCH_TOOL_SOURCE,
				DeepSearchToolSource.DEEP_SEARCH_TOOL_SOURCE), Set.copyOf(config.getExcludedToolSources()));
	}

	@Test
	void theNetworkToolCallingAgentMountsTheOtherToolsOnly() {
		IGToolCallbackSourceRepositoryPattern repository = toolsRepository();
		GAgentConfig config = initialization().defaultToolCallingAgentConfigDataSource().getConfigurations().get(0);

		AgentMountedTools mounted = toolCallingAgent(repository).getMountedTools(config);

		assertEquals(AgentMountedTools.MountMode.AUTO, mounted.getMountMode());
		assertEquals(Set.of("mcpCreateTicket", "getActualDate"), names(mounted.getTools()),
				"the MCP and basic tools are mounted, the searches are the search agents' job");
		assertEquals(Set.of("searchKnowledgeBase", "searchConfluence", "searchJira", "searchWeb", "deepSearchWeb",
				"deepSearchKnowledgeBase"), names(mounted.getExcludedTools()), "the search tools are reported left out");
		// each tool tells its source and why it is left out: the administrator reads it in the editor
		for (AgentMountedTools.MountedTool tool : mounted.getExcludedTools()) {
			assertEquals(AgentMountedTools.Exclusion.AGENT_CONFIGURATION, tool.getExclusion(), tool.getName());
			assertTrue(tool.getSourceId() != null && tool.getSourceDescription() != null, tool.getName());
		}
		AgentMountedTools.MountedTool web = mounted.getExcludedTools().stream()
				.filter(x -> "searchWeb".equals(x.getName())).findFirst().orElseThrow();
		assertEquals(WebSearchToolSource.WEB_SEARCH_TOOL_SOURCE, web.getSourceId());
		assertEquals("source " + WebSearchToolSource.WEB_SEARCH_TOOL_SOURCE, web.getSourceDescription());
		for (AgentMountedTools.MountedTool tool : mounted.getTools()) {
			assertEquals(null, tool.getExclusion(), "a mounted tool has no exclusion");
			assertTrue(tool.getSourceId() != null, tool.getName());
		}
	}

	@Test
	void theSystemSettingsExclusionsAreToldApartFromTheConfigurationOnes() {
		IGToolCallbackSourceRepositoryPattern repository = toolsRepository();
		IGRuntimeBinder binder = mock(IGRuntimeBinder.class);
		AgentsToolsAutoMountingConfig settings = new AgentsToolsAutoMountingConfig();
		settings.setExcludedToolSources(List.of("ActualDateFunctions"));
		when(binder.getImplementationOf(AgentsToolsAutoMountingConfig.class)).thenReturn(settings);
		StringToStringToolCallingNetworkAgent agent = new StringToStringToolCallingNetworkAgent(null, repository, null,
				null, null, binder, null);
		GAgentConfig config = new GAgentConfig();
		config.setCode("webLessAgent");
		config.setSubscribeAllTools(true);
		config.setExcludedToolSources(List.of(WebSearchToolSource.WEB_SEARCH_TOOL_SOURCE));

		AgentMountedTools mounted = agent.getMountedTools(config);

		assertEquals(Set.of("getActualDate", "searchWeb"), names(mounted.getExcludedTools()));
		assertEquals(AgentMountedTools.Exclusion.SYSTEM_SETTINGS, mounted.getExcludedTools().stream()
				.filter(x -> "getActualDate".equals(x.getName())).findFirst().orElseThrow().getExclusion());
		assertEquals(AgentMountedTools.Exclusion.AGENT_CONFIGURATION, mounted.getExcludedTools().stream()
				.filter(x -> "searchWeb".equals(x.getName())).findFirst().orElseThrow().getExclusion());
	}

	@Test
	void anAdminToolCallingAgentWithoutExcludedSourcesMountsTheSearchToolsToo() {
		IGToolCallbackSourceRepositoryPattern repository = toolsRepository();
		GAgentConfig config = new GAgentConfig();
		config.setCode("myToolAgent");
		config.setAgentServiceId(StringToStringToolCallingNetworkAgent.STRING_TO_STRING_TOOL_CALLING_AGENT_SERVICE);
		config.setSubscribeAllTools(true);

		AgentMountedTools mounted = toolCallingAgent(repository).getMountedTools(config);

		assertEquals(8, mounted.getTools().size(), "every registered tool: " + names(mounted.getTools()));
		assertTrue(mounted.getExcludedTools().isEmpty());
	}

	@Test
	void theSelectedToolsAreNeverAffectedByTheExcludedSources() {
		IGToolCallbackSourceRepositoryPattern repository = toolsRepository();
		GAgentConfig config = new GAgentConfig();
		config.setCode("selectedSearcher");
		config.setSubscribeAllTools(false);
		config.setEnabledFunctions(List.of("searchWeb", "mcpCreateTicket"));
		config.setExcludedToolSources(List.of(WebSearchToolSource.WEB_SEARCH_TOOL_SOURCE));

		AgentMountedTools mounted = toolCallingAgent(repository).getMountedTools(config);

		assertEquals(AgentMountedTools.MountMode.SELECTED, mounted.getMountMode());
		assertEquals(Set.of("searchWeb", "mcpCreateTicket"), names(mounted.getTools()));
	}

	@Test
	void anExcludedSourceExportingNothingChangesNothing() {
		IGToolCallbackSourceRepositoryPattern repository = toolsRepository();
		GAgentConfig config = new GAgentConfig();
		config.setCode("allButAnUnknownSource");
		config.setSubscribeAllTools(true);
		config.setExcludedToolSources(List.of("no-such-source"));

		AgentMountedTools mounted = toolCallingAgent(repository).getMountedTools(config);

		assertEquals(8, mounted.getTools().size());
	}
}
