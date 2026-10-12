package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.architecture.agents.controllers.GeboAgentsNetworkAdminController;
import ai.gebo.architecture.agents.model.AgentCapabilityResource;
import ai.gebo.architecture.agents.model.AgentMountedTools;
import ai.gebo.architecture.agents.model.AgentMountedTools.MountMode;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.repository.AgentConfigRepository;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
import ai.gebo.llms.abstraction.layer.functions.ActualDateFunctions;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.agent.chat.service.impl.AgenticLoopPureChatReactiveAgentServiceImpl;
import ai.gebo.llms.agent.chat.service.impl.AgenticLoopReactiveAgentServiceImpl;
import ai.gebo.llms.agent.standardtools.DeepSearchToolSource;
import ai.gebo.llms.agent.standardtools.InternalKnowledgeBaseSearchToolSource;
import ai.gebo.llms.agent.standardtools.StandardSearchesToolsImpl;
import ai.gebo.llms.agent.standardtools.WebSearchToolSource;
import ai.gebo.llms.agent.standard.services.StringToStringToolCallingNetworkAgent;

/**
 * The tools the network of agents editor shows on each agent are the ones the
 * agent mounts when it runs: auto mounted with the configured exclusions, or the
 * selected ones. The date tools are kept out of automatic mounting here; the
 * agentic loop, which operates every tool, mounts them anyway.
 */
@TestPropertySource(properties = { "ai.gebo.agents.standard.enabled=true",
		"ai.gebo.agents.tools.auto-mounting.excluded-tool-sources=ActualDateFunctions" })
public class AgentsMountedToolsTest extends AbstractBaseTestLLmsIntegrationTests {

	@Autowired
	private GeboAgentsNetworkAdminController networkController;
	@Autowired
	private AgentConfigRepository agentConfigRepository;
	@Autowired
	private IGAgentServiceRuntimeDao agentServicesDao;
	@Autowired
	private ActualDateFunctions dateFunctions;
	@Autowired
	private IGToolCallbackSourceRepositoryPattern toolSources;

	/** The tool sources the standard agents keep out of automatic mounting. */
	private static final Set<String> DEFAULT_EXCLUDED_SOURCES = Set.of(
			InternalKnowledgeBaseSearchToolSource.INTERNAL_KNOWLEDGE_BASE_SEARCH_TOOL_SOURCE,
			StandardSearchesToolsImpl.STANDARD_SEARCHES_TOOLS_SOURCE, WebSearchToolSource.WEB_SEARCH_TOOL_SOURCE,
			DeepSearchToolSource.DEEP_SEARCH_TOOL_SOURCE);

	/** The names of the tools the given sources export. */
	private Set<String> toolNamesOfSources(Set<String> sourceIds) {
		Set<String> names = new HashSet<>();
		for (IGToolCallbackSource source : toolSources.getImplementations()) {
			if (sourceIds.contains(source.getId())) {
				source.getToolCallbacks().forEach(x -> names.add(x.getToolDefinition().name()));
			}
		}
		return names;
	}

	private Set<String> registeredToolNames() {
		return toolSources.getTools().stream().map(x -> x.getToolDefinition().name()).collect(Collectors.toSet());
	}

	private Set<String> dateToolNames() {
		Set<String> names = dateFunctions.getToolCallbacks().stream().map(x -> x.getToolDefinition().name())
				.collect(Collectors.toSet());
		assertFalse(names.isEmpty(), "The date tool source must export tools");
		return names;
	}

	private static Set<String> names(List<AgentMountedTools.MountedTool> tools) {
		return tools.stream().map(AgentMountedTools.MountedTool::getName).collect(Collectors.toSet());
	}

	private GAgentConfig saveConfig(String code, String serviceId, boolean auto, List<String> enabledFunctions) {
		GAgentConfig config = new GAgentConfig();
		config.setCode(code);
		config.setDescription(code);
		config.setAgentServiceId(serviceId);
		config.setSubscribeAllTools(auto);
		config.setEnabledFunctions(enabledFunctions);
		config.setUseDefaultChatModel(true);
		return agentConfigRepository.save(config);
	}

	private AgentMountedTools mountedOf(String code) {
		List<AgentMountedTools> result = networkController.getAgentsMountedTools(List.of(code));
		assertEquals(1, result.size());
		assertEquals(code, result.get(0).getAgentConfigCode());
		return result.get(0);
	}

	@Test
	public void testAutoMountingLeavesOutTheExcludedTools() {
		GAgentConfig config = saveConfig("mounted-tools-auto",
				StringToStringToolCallingNetworkAgent.STRING_TO_STRING_TOOL_CALLING_AGENT_SERVICE, true, null);
		AgentMountedTools mounted = mountedOf(config.getCode());

		assertEquals(MountMode.AUTO, mounted.getMountMode());
		assertFalse(mounted.getTools().isEmpty(), "Auto mounting must mount the registered tools");
		Set<String> dateTools = dateToolNames();
		assertTrue(names(mounted.getTools()).stream().noneMatch(dateTools::contains),
				"The excluded tools must not be mounted: " + names(mounted.getTools()));
		assertTrue(names(mounted.getExcludedTools()).containsAll(dateTools),
				"The excluded tools must be reported as excluded: " + names(mounted.getExcludedTools()));
		mounted.getTools().forEach(tool -> assertNotNull(tool.getCategoryCode(),
				"Every registered tool reports its source category: " + tool.getName()));

		// the capabilities the network advertises name the very same tools
		IGGenericAgentService service = agentServicesDao.findByCode(config.getAgentServiceId());
		Set<String> advertised = service.getAgentCapabilities(config).getTools().stream()
				.map(AgentCapabilityResource::getName).collect(Collectors.toSet());
		assertEquals(names(mounted.getTools()), advertised);
	}

	@Test
	public void testTheAgenticLoopMountsTheExcludedToolsToo() {
		GAgentConfig config = saveConfig("mounted-tools-loop",
				AgenticLoopReactiveAgentServiceImpl.AGENTIC_LOOP_NETWORK_AGENT_SERVICE, true, null);
		AgentMountedTools mounted = mountedOf(config.getCode());

		assertEquals(MountMode.AUTO, mounted.getMountMode());
		assertTrue(names(mounted.getTools()).containsAll(dateToolNames()),
				"The agentic loop operates every tool: " + names(mounted.getTools()));
		assertTrue(mounted.getExcludedTools().isEmpty(), "Nothing is left out of the agentic loop");

		// its advertised capabilities no longer hide the tools it mounts
		IGGenericAgentService service = agentServicesDao.findByCode(config.getAgentServiceId());
		Set<String> advertised = service.getAgentCapabilities(config).getTools().stream()
				.map(AgentCapabilityResource::getName).collect(Collectors.toSet());
		assertEquals(names(mounted.getTools()), advertised);
	}

	@Test
	public void testTheStandardSearchSourcesAreKeptOutOfAutoMounting() {
		Set<String> searchTools = toolNamesOfSources(DEFAULT_EXCLUDED_SOURCES);
		assertFalse(searchTools.isEmpty(), "The standard agents register their search tools");
		GAgentConfig config = saveConfig("mounted-tools-default-exclusions",
				StringToStringToolCallingNetworkAgent.STRING_TO_STRING_TOOL_CALLING_AGENT_SERVICE, true, null);
		AgentMountedTools mounted = mountedOf(config.getCode());

		assertTrue(names(mounted.getTools()).stream().noneMatch(searchTools::contains),
				"The search tools are the job of the search agents: " + names(mounted.getTools()));
		assertTrue(names(mounted.getExcludedTools()).containsAll(searchTools),
				"The search tools are reported as excluded: " + names(mounted.getExcludedTools()));
	}

	@Test
	public void testTheFreeChatLoopLeavesOutOnlyTheKnowledgeBaseTools() {
		GAgentConfig config = saveConfig("mounted-tools-free-chat-loop",
				AgenticLoopPureChatReactiveAgentServiceImpl.AGENTIC_LOOP_PURE_CHAT_NETWORK_AGENT_SERVICE, true, null);
		AgentMountedTools mounted = mountedOf(config.getCode());

		Set<String> registered = registeredToolNames();
		Set<String> knowledgeBaseTools = registered.stream()
				.filter(AgenticLoopPureChatReactiveAgentServiceImpl.KNOWLEDGE_BASE_TOOLS::contains)
				.collect(Collectors.toSet());
		assertFalse(knowledgeBaseTools.isEmpty(), "The knowledge base tools are registered");
		assertEquals(knowledgeBaseTools, names(mounted.getExcludedTools()),
				"A free chat leaves out the knowledge base tools, and nothing else");
		Set<String> expectedMounted = new HashSet<>(registered);
		expectedMounted.removeAll(knowledgeBaseTools);
		assertEquals(expectedMounted, names(mounted.getTools()),
				"A free chat mounts the configured exclusions too, the web and deep search included");

		IGGenericAgentService service = agentServicesDao.findByCode(config.getAgentServiceId());
		Set<String> advertised = service.getAgentCapabilities(config).getTools().stream()
				.map(AgentCapabilityResource::getName).collect(Collectors.toSet());
		assertEquals(names(mounted.getTools()), advertised);
	}

	@Test
	public void testSelectedToolsAreMountedAsSelected() {
		String dateTool = dateToolNames().iterator().next();
		GAgentConfig config = saveConfig("mounted-tools-selected",
				StringToStringToolCallingNetworkAgent.STRING_TO_STRING_TOOL_CALLING_AGENT_SERVICE, false,
				List.of(dateTool));
		AgentMountedTools mounted = mountedOf(config.getCode());

		assertEquals(MountMode.SELECTED, mounted.getMountMode());
		assertEquals(Set.of(dateTool), names(mounted.getTools()),
				"A selected tool is mounted even when automatic mounting excludes it");
		assertNotNull(mounted.getTools().get(0).getDescription());
		assertTrue(mounted.getExcludedTools().isEmpty());
	}

	@Test
	public void testAnUnknownConfigurationMountsNothing() {
		AgentMountedTools mounted = mountedOf("no-such-agent-config");
		assertEquals(MountMode.SELECTED, mounted.getMountMode());
		assertTrue(mounted.getTools().isEmpty());
	}
}
