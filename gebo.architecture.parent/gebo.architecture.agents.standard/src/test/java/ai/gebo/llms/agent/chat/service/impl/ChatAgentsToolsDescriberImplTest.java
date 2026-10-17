/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.chat.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.AgentMountedTools;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.PipelineType;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGAgenticChatDefaultNetworkOfAgentsService;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
import ai.gebo.architecture.ai.model.ToolCategoriesTree;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.chat.abstraction.layer.model.AgentToolsInfo;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;

/**
 * The chat infos tell the agents of the network a chat is handed to, with the tools
 * each really mounts: the network is the profile's one for a knowledge base chat,
 * the pure chat default for a free chat, nothing when the agents are off.
 */
class ChatAgentsToolsDescriberImplTest {
	private final IGAgenticChatDefaultNetworkOfAgentsService defaults = mock(
			IGAgenticChatDefaultNetworkOfAgentsService.class);
	private final IAgentConfigDao configs = mock(IAgentConfigDao.class);
	private final IGAgentServiceRuntimeDao services = mock(IGAgentServiceRuntimeDao.class);
	private final IGToolCallbackSourceRepositoryPattern tools = mock(IGToolCallbackSourceRepositoryPattern.class);
	private final ChatAgentsToolsDescriberImpl describer = new ChatAgentsToolsDescriberImpl(defaults, configs,
			services, tools);

	@Test
	void nothingWhenTheAgenticChatsAreOff() {
		when(defaults.isAgenticChatNetworksEnabled()).thenReturn(false);
		assertTrue(describer.describeAgentsTools(new GChatProfileConfiguration()).isEmpty());
		verify(defaults, never()).resolveChatNetwork(any(), any());
	}

	@Test
	void theProfileNetworkAgentsWithTheirMountedTools() {
		when(defaults.isAgenticChatNetworksEnabled()).thenReturn(true);
		GChatProfileConfiguration profile = new GChatProfileConfiguration();
		profile.setCode("kb-profile");
		profile.setDefaultChatNetworkOfAgents("MY_NETWORK");
		GAgentsNetwork network = new GAgentsNetwork();
		network.setCode("MY_NETWORK");
		AgentNetworkParticipant participant = new AgentNetworkParticipant();
		participant.setAgentConfigCode("searcherAgent");
		network.setAgents(List.of(participant));
		when(defaults.resolveChatNetwork(PipelineType.RAG_PIPELINE, "MY_NETWORK")).thenReturn(network);
		GAgentConfig config = new GAgentConfig();
		config.setCode("searcherAgent");
		config.setDescription("Searches the knowledge base");
		config.setAgentServiceId("searcherService");
		when(configs.findByCode("searcherAgent")).thenReturn(config);
		IGGenericAgentService service = mock(IGGenericAgentService.class);
		when(services.findByCode("searcherService")).thenReturn(service);
		AgentMountedTools mounted = new AgentMountedTools();
		mounted.setMountMode(AgentMountedTools.MountMode.SELECTED);
		mounted.getTools().add(new AgentMountedTools.MountedTool("searchKnowledgeBase", "...", "kb", "Knowledge base"));
		when(service.getMountedTools(config)).thenReturn(mounted);
		ToolCategoriesTree tree = new ToolCategoriesTree();
		when(tools.getEnabledToolsTree(eq(List.of("searchKnowledgeBase")))).thenReturn(List.of(tree));

		List<AgentToolsInfo> infos = describer.describeAgentsTools(profile);

		assertEquals(1, infos.size());
		AgentToolsInfo info = infos.get(0);
		assertEquals("MY_NETWORK", info.getNetworkCode());
		assertEquals("searcherAgent", info.getAgentName());
		assertEquals("Searches the knowledge base", info.getAgentDescription());
		assertEquals("searcherService", info.getAgentServiceId());
		assertEquals("SELECTED", info.getMountMode());
		assertEquals(List.of(tree), info.getTools());
	}

	@Test
	void aFreeChatIsDescribedOnThePureChatNetwork() {
		when(defaults.isAgenticChatNetworksEnabled()).thenReturn(true);
		when(defaults.resolveChatNetwork(PipelineType.PURE_CHAT_PIPELINE, null)).thenReturn(null);
		assertTrue(describer.describeAgentsTools(null).isEmpty());
		verify(defaults).resolveChatNetwork(PipelineType.PURE_CHAT_PIPELINE, null);
	}

	@Test
	void aParticipantWithoutConfigurationIsNamedButHasNoTools() {
		when(defaults.isAgenticChatNetworksEnabled()).thenReturn(true);
		GAgentsNetwork network = new GAgentsNetwork();
		network.setCode("NET");
		AgentNetworkParticipant participant = new AgentNetworkParticipant();
		participant.setAgentConfigCode("ghost");
		network.setAgents(List.of(participant));
		when(defaults.resolveChatNetwork(PipelineType.RAG_PIPELINE, null)).thenReturn(network);
		when(configs.findByCode("ghost")).thenReturn(null);

		List<AgentToolsInfo> infos = describer.describeAgentsTools(new GChatProfileConfiguration());

		assertEquals(1, infos.size());
		assertEquals("ghost", infos.get(0).getAgentName());
		assertNull(infos.get(0).getAgentServiceId());
		assertTrue(infos.get(0).getTools().isEmpty());
	}
}
