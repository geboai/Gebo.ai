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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import ai.gebo.architecture.agents.controllers.GeboAgentAdminController;
import ai.gebo.architecture.agents.controllers.GeboAgentsNetworkAdminController;
import ai.gebo.architecture.agents.model.AgentServiceDescriptor;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.llms.agent.standard.services.ChatRuntimeDataQueryAdapterAgentService;
import ai.gebo.llms.agent.standard.services.StringToStringToolCallingNetworkAgent;
import ai.gebo.model.OperationStatus;

/**
 * An agent names a role only when its service builds prompts with one. The chat input
 * adapter, a network adapter and a remote A2A agent build none: they are saved without
 * a role, a copy of the shipped input adapter is edited and saved as it is. An agent
 * building prompts is still refused without one, and the editors are told which is which.
 */
public class AgentRoleOnlyWhenNeededTest extends AbstractBaseIntegrationTest {
	private static final String INPUT_ADAPTER = ChatRuntimeDataQueryAdapterAgentService.CHAT_RUNTIME_DATA_QUERY_ADAPTER;
	private static final String NETWORK_ADAPTER = "stringToStringAgentsNetworkToNetworkAgentAdapterService";
	private static final String TOOL_CALLING = StringToStringToolCallingNetworkAgent.STRING_TO_STRING_TOOL_CALLING_AGENT_SERVICE;

	@Autowired
	private GeboAgentAdminController agentController;
	@Autowired
	private GeboAgentsNetworkAdminController networkController;

	@Test
	public void aCopiedInputAdapterIsEditedAndSavedWithoutARole() throws Exception {
		final OperationStatus<GAgentConfig> copied = agentController.copyAgent(INPUT_ADAPTER);
		assertFalse(copied.isHasErrorMessages(), "the shipped input adapter is copyable: " + copied.getMessages());
		final GAgentConfig copy = copied.getResult();
		assertNull(copy.getAgentRoleCode(), "the shipped input adapter has no role, nor its copy");
		copy.setDescription("Input adapter copy edited without a role");
		agentController.updateAgent(copy);
		assertEquals("Input adapter copy edited without a role",
				agentController.getAgentByCode(copy.getCode()).getDescription());
		agentController.deleteAgent(copy);
	}

	@Test
	public void aNetworkAdapterIsInsertedWithoutARole() throws Exception {
		final GAgentConfig adapter = new GAgentConfig();
		adapter.setDescription("Network adapter without a role");
		adapter.setAgentServiceId(NETWORK_ADAPTER);
		adapter.setAgentType(GAgentConfig.AgentType.AGENTS_NETWORK);
		final GAgentConfig inserted = agentController.insertAgent(adapter);
		assertNotNull(inserted.getCode());
		assertNull(agentController.getAgentByCode(inserted.getCode()).getAgentRoleCode());
		agentController.deleteAgent(inserted);
	}

	@Test
	public void anAgentBuildingPromptsStillNeedsARole() throws Exception {
		final GAgentConfig agent = new GAgentConfig();
		agent.setDescription("Tool calling agent");
		agent.setAgentServiceId(TOOL_CALLING);
		agent.setUseDefaultChatModel(true);
		final ResponseStatusException refused = assertThrows(ResponseStatusException.class,
				() -> agentController.insertAgent(agent));
		assertEquals(HttpStatus.BAD_REQUEST, refused.getStatusCode());
		assertTrue(refused.getReason().contains(TOOL_CALLING), refused.getReason());
		agent.setAgentRoleCode("  ");
		assertThrows(ResponseStatusException.class, () -> agentController.insertAgent(agent), "a blank role is none");

		agent.setAgentRoleCode("TOOL_AGENT");
		final GAgentConfig inserted = agentController.insertAgent(agent);
		inserted.setAgentRoleCode(null);
		assertThrows(ResponseStatusException.class, () -> agentController.updateAgent(inserted),
				"an update dropping the role is refused");
		assertEquals("TOOL_AGENT", agentController.getAgentByCode(inserted.getCode()).getAgentRoleCode());
		agentController.deleteAgent(inserted);
	}

	@Test
	public void theEditorsAreToldWhichServicesNeedARole() {
		final Map<String, Boolean> roleRequired = networkController.getAgentServices().stream().collect(
				Collectors.toMap(AgentServiceDescriptor::getServiceId, AgentServiceDescriptor::isRoleRequired));
		assertEquals(Boolean.FALSE, roleRequired.get(INPUT_ADAPTER));
		assertEquals(Boolean.FALSE, roleRequired.get(NETWORK_ADAPTER));
		assertEquals(Boolean.TRUE, roleRequired.get(TOOL_CALLING));
	}
}
