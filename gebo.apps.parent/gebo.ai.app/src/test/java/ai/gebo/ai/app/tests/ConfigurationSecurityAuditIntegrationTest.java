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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import ai.gebo.architecture.a2aclients.controllers.A2AClientConfigController;
import ai.gebo.architecture.a2aclients.model.A2ARemoteAgentConfig;
import ai.gebo.architecture.a2aserver.controllers.GeboA2AServerAdminController;
import ai.gebo.architecture.a2aserver.model.A2AServerConfig;
import ai.gebo.architecture.agents.controllers.AgenticChatDefaultNetworksAdminController;
import ai.gebo.architecture.agents.controllers.AgenticChatDefaultNetworksAdminController.AgenticChatDefaultNetworkRequest;
import ai.gebo.architecture.agents.controllers.GeboAgentAdminController;
import ai.gebo.architecture.agents.controllers.GeboAgentsNetworkAdminController;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.PipelineType;
import ai.gebo.architecture.agents.services.IGAgenticChatDefaultNetworkOfAgentsService.AgenticChatDefaultNetworkInfo;
import ai.gebo.architecture.mcpclients.controllers.McpClientConfigController;
import ai.gebo.architecture.mcpclients.model.MCPClientConfig;
import ai.gebo.architecture.mcpclients.model.MCPTransportType;
import ai.gebo.architecture.mcpclients.model.McpAuthMode;
import ai.gebo.architecture.mcpserver.controllers.GeboMCPServerAdminController;
import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.core.controllers.KnowledgeBaseController;
import ai.gebo.core.controllers.ProjectsController;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.client.rest.controllers.GeboAdminChatProfilesConfigurationController;
import ai.gebo.llms.chat.client.rest.controllers.GeboDeepSearchAdminController;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.controller.UsersAdminController;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.services.ConfigurationAuditSupport;
import ai.gebo.security.services.SecurityAuditConstraints;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import ai.gebo.tavilysearch.handler.controllers.TavilySearchConfigurationController;
import ai.gebo.tavilysearch.handler.model.GTavilySearchApiCredentials;

/**
 * The configuration changes that decide what the AI may do and for whom - networks
 * of agents and agents, MCP and A2A, chat profiles and knowledge bases, external
 * search - reach the security audit trail through their admin endpoints, once per
 * change and with the outcome they really had.
 *
 * <p>
 * The endpoints are called on the controller beans, as {@link SecurityAuditLoggingIntegrationTest}
 * calls the services: what is checked is the event each endpoint writes on the
 * dedicated "security-log" logger, the one wired to the Wazuh-compatible
 * security-log.jsonl ({@link WazuhSecurityLogCompatibilityTest} then decodes these
 * very events with a real Wazuh manager).
 * </p>
 */
public class ConfigurationSecurityAuditIntegrationTest extends AbstractBaseIntegrationTest {

	private static final ObjectMapper mapper = new ObjectMapper();

	/** A system network, read-only, present in every installation with the agents. */
	private static final String SYSTEM_NETWORK = "AGENTIC_LOOP_AGENTS_NETWORK";

	@Autowired
	private GeboAgentsNetworkAdminController networkController;
	@Autowired
	private GeboAgentAdminController agentController;
	@Autowired
	private AgenticChatDefaultNetworksAdminController defaultNetworksController;
	@Autowired
	private McpClientConfigController mcpClientController;
	@Autowired
	private GeboMCPServerAdminController mcpServerController;
	@Autowired
	private A2AClientConfigController a2aClientController;
	@Autowired
	private GeboA2AServerAdminController a2aServerController;
	@Autowired
	private GeboAdminChatProfilesConfigurationController chatProfilesController;
	@Autowired
	private GeboDeepSearchAdminController deepSearchController;
	@Autowired
	private KnowledgeBaseController knowledgeBaseController;
	@Autowired
	private ProjectsController projectsController;
	@Autowired
	private TavilySearchConfigurationController tavilyController;
	@Autowired
	private UsersAdminController usersAdminController;

	private Logger securityLogger;
	private ListAppender<ILoggingEvent> listAppender;

	@BeforeEach
	public void attachSecurityLogAppender() {
		securityLogger = (Logger) LoggerFactory.getLogger(SecurityAuditConstraints.SECURITY_LOG);
		listAppender = new ListAppender<>();
		listAppender.start();
		securityLogger.addAppender(listAppender);
	}

	@AfterEach
	public void detachSecurityLogAppender() {
		if (securityLogger != null && listAppender != null) {
			securityLogger.detachAppender(listAppender);
		}
	}

	private static String unique(String prefix) {
		return prefix + "-" + UUID.randomUUID().toString().substring(0, 8);
	}

	/**
	 * The logged events carrying one action: background work (schedulers, session
	 * renewals) may write other events meanwhile, so the assertions name theirs.
	 */
	private List<JsonNode> loggedEventsWithAction(String action) {
		List<JsonNode> out = new ArrayList<>();
		for (ILoggingEvent logged : new ArrayList<>(listAppender.list)) {
			JsonNode node = mapper.readTree(logged.getFormattedMessage());
			if (node.has("action") && action.equals(node.get("action").asText())) {
				out.add(node);
			}
		}
		return out;
	}

	/**
	 * Exactly one event of the action, with the taxonomy of a configuration change, then
	 * forgets the events logged so far so the next step checks only its own.
	 *
	 * @param resourceId the expected code, null when the change never had one
	 */
	private JsonNode singleConfigurationEvent(String eventType, String action, String resourceType,
			String resourceId, String outcome) {
		List<JsonNode> found = loggedEventsWithAction(action);
		assertEquals(1, found.size(), "Exactly one \"" + action + "\" event should have been logged, found: " + found);
		JsonNode event = found.get(0);
		assertEquals(eventType, event.get("eventType").asText(), "eventType of " + action);
		assertEquals(eventType, event.get("category").asText(), "category of " + action);
		assertEquals(resourceType, event.get("resourceType").asText(), "resourceType of " + action);
		// null: a refused change of a configuration that never got a code
		if (resourceId != null) {
			assertEquals(resourceId, event.get("resourceId").asText(), "resourceId of " + action);
		}
		assertEquals(outcome, event.get("outcome").asText(), "outcome of " + action);
		assertNotNull(event.get("stackPoint").asText(), "stackPoint of " + action);
		listAppender.list.clear();
		return event;
	}

	// ------------------------------------------------------------------------------
	// Agents configuration
	// ------------------------------------------------------------------------------

	@Test
	public void networksOfAgentsAreAuditedOnCopyUpdateAndDelete() {
		// a copy of a system network: an editable, valid network to change and delete
		OperationStatus<GAgentsNetwork> copied = networkController.copyAgentsNetwork(SYSTEM_NETWORK);
		assertFalse(copied.isHasErrorMessages(), "The system network must be copyable: " + copied.getMessages());
		GAgentsNetwork copy = copied.getResult();
		JsonNode copyEvent = singleConfigurationEvent(SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION,
				SecurityAuditTaxonomy.Action.AGENTS_NETWORK_COPY, SecurityAuditTaxonomy.ResourceType.AGENTS_NETWORK,
				SYSTEM_NETWORK, SecurityAuditTaxonomy.Outcome.SUCCESS);
		assertEquals(copy.getCode(), copyEvent.get("details").get("copyCode").asText());

		copy.setDescription("Copy changed by the configuration audit test");
		copy.setAccessibleToAll(false);
		copy.setAccessibleGroups(new ArrayList<>(List.of("SALES_TEAM")));
		copy.setAccessibleUsers(new ArrayList<>(List.of("someone@gebo.ai")));
		OperationStatus<GAgentsNetwork> updated = networkController.updateAgentsNetwork(copy);
		assertFalse(updated.isHasErrorMessages(), "The copy must be updatable: " + updated.getMessages());
		JsonNode updateEvent = singleConfigurationEvent(SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION,
				SecurityAuditTaxonomy.Action.AGENTS_NETWORK_UPDATE, SecurityAuditTaxonomy.ResourceType.AGENTS_NETWORK,
				copy.getCode(), SecurityAuditTaxonomy.Outcome.SUCCESS);
		JsonNode details = updateEvent.get("details");
		assertFalse(details.get(ConfigurationAuditSupport.DETAIL_ACCESSIBLE_TO_ALL).asBoolean());
		assertEquals("SALES_TEAM", details.get(ConfigurationAuditSupport.DETAIL_ACCESSIBLE_GROUPS).get(0).asText());
		assertEquals(1, details.get(ConfigurationAuditSupport.DETAIL_ACCESSIBLE_USERS_COUNT).asInt());
		// the user ids are e-mail addresses: counted, never written
		assertFalse(updateEvent.toString().contains("someone@gebo.ai"), "A user id leaked into " + updateEvent);

		OperationStatus<GAgentsNetwork> deleted = networkController.deleteAgentsNetwork(copy);
		assertFalse(deleted.isHasErrorMessages(), "The copy must be deletable: " + deleted.getMessages());
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION,
				SecurityAuditTaxonomy.Action.AGENTS_NETWORK_DELETE, SecurityAuditTaxonomy.ResourceType.AGENTS_NETWORK,
				copy.getCode(), SecurityAuditTaxonomy.Outcome.SUCCESS);
	}

	@Test
	public void refusedNetworkChangesAreAuditedAsFailures() {
		// a network with no agents is refused by the validation
		GAgentsNetwork empty = new GAgentsNetwork();
		empty.setDescription("A network with no agents");
		OperationStatus<GAgentsNetwork> inserted = networkController.insertAgentsNetwork(empty);
		assertTrue(inserted.isHasErrorMessages(), "A network with no agents must be refused");
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION,
				SecurityAuditTaxonomy.Action.AGENTS_NETWORK_INSERT, SecurityAuditTaxonomy.ResourceType.AGENTS_NETWORK,
				null, SecurityAuditTaxonomy.Outcome.FAILURE);

		// a system network is read-only: deleting it is refused and nothing changes
		GAgentsNetwork system = networkController.getAgentsNetworkByCode(SYSTEM_NETWORK);
		assertNotNull(system, "The system network must exist");
		OperationStatus<GAgentsNetwork> deleted = networkController.deleteAgentsNetwork(system);
		assertTrue(deleted.isHasErrorMessages(), "A read-only network must not be deletable");
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION,
				SecurityAuditTaxonomy.Action.AGENTS_NETWORK_DELETE, SecurityAuditTaxonomy.ResourceType.AGENTS_NETWORK,
				SYSTEM_NETWORK, SecurityAuditTaxonomy.Outcome.FAILURE);
		assertNotNull(networkController.getAgentsNetworkByCode(SYSTEM_NETWORK), "The system network must survive");
	}

	@Test
	public void agentsAreAuditedOnInsertUpdateAndDeleteAndAFailedInsertKeepsItsException()
			throws GeboPersistenceException {
		// no code: generated on insert (a preset one is refused), and taken from what was saved
		GAgentConfig agent = new GAgentConfig();
		agent.setDescription("Agent of the configuration audit test");
		agent.setUseDefaultChatModel(true);
		String code = agentController.insertAgent(agent).getCode();
		assertNotNull(code);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION,
				SecurityAuditTaxonomy.Action.AGENT_INSERT, SecurityAuditTaxonomy.ResourceType.AGENT, code,
				SecurityAuditTaxonomy.Outcome.SUCCESS);

		GAgentConfig stored = agentController.getAgentByCode(code);
		stored.setDescription("Agent changed by the configuration audit test");
		agentController.updateAgent(stored);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION,
				SecurityAuditTaxonomy.Action.AGENT_UPDATE, SecurityAuditTaxonomy.ResourceType.AGENT, code,
				SecurityAuditTaxonomy.Outcome.SUCCESS);

		agentController.deleteAgent(stored);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION,
				SecurityAuditTaxonomy.Action.AGENT_DELETE, SecurityAuditTaxonomy.ResourceType.AGENT, code,
				SecurityAuditTaxonomy.Outcome.SUCCESS);

		// a read-only agent cannot be inserted: the caller gets the same exception, the
		// security log a failure with its message
		GAgentConfig readOnly = new GAgentConfig();
		readOnly.setDescription("Read-only agent");
		readOnly.setReadOnly(true);
		assertThrows(GeboPersistenceException.class, () -> agentController.insertAgent(readOnly));
		JsonNode failure = singleConfigurationEvent(SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION,
				SecurityAuditTaxonomy.Action.AGENT_INSERT, SecurityAuditTaxonomy.ResourceType.AGENT, null,
				SecurityAuditTaxonomy.Outcome.FAILURE);
		assertTrue(failure.get("details").has(ConfigurationAuditSupport.DETAIL_ERROR));
	}

	@Test
	public void theDefaultChatNetworksAreAuditedOnSetAndReset() {
		// a network that does not exist cannot be the default: refused, audited, unchanged
		AgenticChatDefaultNetworkRequest missing = new AgenticChatDefaultNetworkRequest();
		missing.setPipelineType(PipelineType.RAG_PIPELINE);
		missing.setDefaultChatNetworkOfAgents("NO_SUCH_NETWORK_" + UUID.randomUUID());
		OperationStatus<AgenticChatDefaultNetworkInfo> refused = defaultNetworksController
				.setAgenticChatDefaultNetwork(missing);
		assertTrue(refused.isHasErrorMessages(), "A network that does not exist must be refused");
		JsonNode refusedEvent = singleConfigurationEvent(SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION,
				SecurityAuditTaxonomy.Action.AGENTIC_CHAT_DEFAULT_NETWORK_SET,
				SecurityAuditTaxonomy.ResourceType.AGENTIC_CHAT_DEFAULT_NETWORK, PipelineType.RAG_PIPELINE.name(),
				SecurityAuditTaxonomy.Outcome.FAILURE);
		assertEquals(missing.getDefaultChatNetworkOfAgents(), refusedEvent.get("details").get("network").asText());

		// a reset succeeds when the agents are on (refused otherwise); the administrator's
		// previous choice, if any, is put back so no other test sees a different default
		boolean enabled = defaultNetworksController.isAgenticChatNetworksEnabled();
		String previous = defaultNetworksController.getAgenticChatDefaultNetworks().stream()
				.filter(x -> x.getPipelineType() == PipelineType.RAG_PIPELINE).findFirst()
				.map(AgenticChatDefaultNetworkInfo::getDefaultChatNetworkOfAgents).orElse(null);
		AgenticChatDefaultNetworkRequest reset = new AgenticChatDefaultNetworkRequest();
		reset.setPipelineType(PipelineType.RAG_PIPELINE);
		defaultNetworksController.resetAgenticChatDefaultNetwork(reset);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION,
				SecurityAuditTaxonomy.Action.AGENTIC_CHAT_DEFAULT_NETWORK_RESET,
				SecurityAuditTaxonomy.ResourceType.AGENTIC_CHAT_DEFAULT_NETWORK, PipelineType.RAG_PIPELINE.name(),
				enabled ? SecurityAuditTaxonomy.Outcome.SUCCESS : SecurityAuditTaxonomy.Outcome.FAILURE);
		if (enabled && previous != null) {
			AgenticChatDefaultNetworkRequest restore = new AgenticChatDefaultNetworkRequest();
			restore.setPipelineType(PipelineType.RAG_PIPELINE);
			restore.setDefaultChatNetworkOfAgents(previous);
			defaultNetworksController.setAgenticChatDefaultNetwork(restore);
			singleConfigurationEvent(SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION,
					SecurityAuditTaxonomy.Action.AGENTIC_CHAT_DEFAULT_NETWORK_SET,
					SecurityAuditTaxonomy.ResourceType.AGENTIC_CHAT_DEFAULT_NETWORK, PipelineType.RAG_PIPELINE.name(),
					SecurityAuditTaxonomy.Outcome.SUCCESS);
		}
	}

	// ------------------------------------------------------------------------------
	// Interoperability configuration
	// ------------------------------------------------------------------------------

	@Test
	public void externalMcpServersAreAuditedWithWhoMayUseThem() {
		String code = unique("audit-mcp");
		MCPClientConfig config = new MCPClientConfig();
		config.setCode(code);
		config.setDescription("MCP server of the configuration audit test");
		// never contacted: inserting, updating and deleting the configuration does not connect
		config.setBaseUrl("http://localhost:9/mcp");
		config.setTransportType(MCPTransportType.STREAMABLE_HTTP);
		config.setAuthMode(McpAuthMode.NONE);
		config.setExportingPrefix("auditmcp");
		config.setAccessibleToAll(false);
		config.setAccessibleGroups(new ArrayList<>(List.of("HR", "LEGAL")));
		config.setAccessibleUsers(new ArrayList<>(List.of("someone@gebo.ai", "someone.else@gebo.ai")));

		OperationStatus<MCPClientConfig> inserted = mcpClientController.insertMCPClientConfig(config);
		assertFalse(inserted.isHasErrorMessages(), "The MCP configuration must be inserted: " + inserted.getMessages());
		JsonNode insertEvent = singleConfigurationEvent(SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION,
				SecurityAuditTaxonomy.Action.MCP_SERVER_INSERT, SecurityAuditTaxonomy.ResourceType.MCP_SERVER, code,
				SecurityAuditTaxonomy.Outcome.SUCCESS);
		assertEquals(2, insertEvent.get("details").get(ConfigurationAuditSupport.DETAIL_ACCESSIBLE_GROUPS).size());
		assertEquals(2, insertEvent.get("details").get(ConfigurationAuditSupport.DETAIL_ACCESSIBLE_USERS_COUNT).asInt());
		assertFalse(insertEvent.toString().contains("someone"), "A user id leaked into " + insertEvent);

		MCPClientConfig stored = inserted.getResult();
		stored.setDescription("MCP server changed by the configuration audit test");
		mcpClientController.updateMCPClientConfig(stored);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION,
				SecurityAuditTaxonomy.Action.MCP_SERVER_UPDATE, SecurityAuditTaxonomy.ResourceType.MCP_SERVER, code,
				SecurityAuditTaxonomy.Outcome.SUCCESS);

		mcpClientController.deleteMCPClientConfig(stored);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION,
				SecurityAuditTaxonomy.Action.MCP_SERVER_DELETE, SecurityAuditTaxonomy.ResourceType.MCP_SERVER, code,
				SecurityAuditTaxonomy.Outcome.SUCCESS);

		// deleting it again is refused: a failure in the security log
		OperationStatus<Boolean> again = mcpClientController.deleteMCPClientConfig(stored);
		assertTrue(again.isHasErrorMessages());
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION,
				SecurityAuditTaxonomy.Action.MCP_SERVER_DELETE, SecurityAuditTaxonomy.ResourceType.MCP_SERVER, code,
				SecurityAuditTaxonomy.Outcome.FAILURE);
	}

	@Test
	public void exposedMcpServersAreAuditedAndAnInvalidOneKeepsItsException() throws GeboPersistenceException {
		// no relative url: refused with a checked exception, which the caller still gets
		GeboMCPServerConfig invalid = new GeboMCPServerConfig();
		invalid.setDescription("Exposed MCP server with no url");
		assertThrows(GeboPersistenceException.class, () -> mcpServerController.insertMcpServer(invalid));
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION,
				SecurityAuditTaxonomy.Action.MCP_EXPOSED_SERVER_INSERT,
				SecurityAuditTaxonomy.ResourceType.MCP_EXPOSED_SERVER, null, SecurityAuditTaxonomy.Outcome.FAILURE);

		// kept disabled: no endpoint is published by the test. No code: generated on insert
		GeboMCPServerConfig config = new GeboMCPServerConfig();
		config.setDescription("Exposed MCP server of the configuration audit test");
		config.setExportedUniqueRelativeUrl(unique("audit-mcp-exposed"));
		config.setEnabled(false);
		GeboMCPServerConfig saved = mcpServerController.insertMcpServer(config);
		assertNotNull(saved.getCode());
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION,
				SecurityAuditTaxonomy.Action.MCP_EXPOSED_SERVER_INSERT,
				SecurityAuditTaxonomy.ResourceType.MCP_EXPOSED_SERVER, saved.getCode(),
				SecurityAuditTaxonomy.Outcome.SUCCESS);

		saved.setDescription("Exposed MCP server changed by the configuration audit test");
		mcpServerController.updateMcpServer(saved);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION,
				SecurityAuditTaxonomy.Action.MCP_EXPOSED_SERVER_UPDATE,
				SecurityAuditTaxonomy.ResourceType.MCP_EXPOSED_SERVER, saved.getCode(),
				SecurityAuditTaxonomy.Outcome.SUCCESS);

		mcpServerController.deleteMcpServer(saved.getCode());
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION,
				SecurityAuditTaxonomy.Action.MCP_EXPOSED_SERVER_DELETE,
				SecurityAuditTaxonomy.ResourceType.MCP_EXPOSED_SERVER, saved.getCode(),
				SecurityAuditTaxonomy.Outcome.SUCCESS);
	}

	@Test
	public void a2aRemoteAgentsAreAuditedOnInsertUpdateAndDelete() {
		// no agent card url: refused by the validation
		A2ARemoteAgentConfig invalid = new A2ARemoteAgentConfig();
		invalid.setCode(unique("audit-a2a-invalid"));
		invalid.setExportingPrefix("auditinvalid");
		OperationStatus<A2ARemoteAgentConfig> refused = a2aClientController.insertA2AClientConfig(invalid);
		assertTrue(refused.isHasErrorMessages());
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION,
				SecurityAuditTaxonomy.Action.A2A_REMOTE_AGENT_INSERT, SecurityAuditTaxonomy.ResourceType.A2A_REMOTE_AGENT,
				invalid.getCode(), SecurityAuditTaxonomy.Outcome.FAILURE);

		String code = unique("audit-a2a");
		A2ARemoteAgentConfig config = new A2ARemoteAgentConfig();
		config.setCode(code);
		config.setDescription("A2A agent of the configuration audit test");
		// never contacted: inserting, updating and deleting the configuration does not connect
		config.setAgentCardUrl("http://localhost:9/.well-known/agent-card.json");
		config.setExportingPrefix("auditagent");
		OperationStatus<A2ARemoteAgentConfig> inserted = a2aClientController.insertA2AClientConfig(config);
		assertFalse(inserted.isHasErrorMessages(), "The A2A agent must be inserted: " + inserted.getMessages());
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION,
				SecurityAuditTaxonomy.Action.A2A_REMOTE_AGENT_INSERT, SecurityAuditTaxonomy.ResourceType.A2A_REMOTE_AGENT,
				code, SecurityAuditTaxonomy.Outcome.SUCCESS);

		A2ARemoteAgentConfig stored = inserted.getResult();
		stored.setDescription("A2A agent changed by the configuration audit test");
		a2aClientController.updateA2AClientConfig(stored);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION,
				SecurityAuditTaxonomy.Action.A2A_REMOTE_AGENT_UPDATE, SecurityAuditTaxonomy.ResourceType.A2A_REMOTE_AGENT,
				code, SecurityAuditTaxonomy.Outcome.SUCCESS);

		a2aClientController.deleteA2AClientConfig(stored);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION,
				SecurityAuditTaxonomy.Action.A2A_REMOTE_AGENT_DELETE, SecurityAuditTaxonomy.ResourceType.A2A_REMOTE_AGENT,
				code, SecurityAuditTaxonomy.Outcome.SUCCESS);
	}

	@Test
	public void a2aExportsAreAuditedWithTheCodeGeneratedOnInsert() {
		// no code: the one generated on insert is the resource of the event
		A2AServerConfig config = new A2AServerConfig();
		config.setDescription("A2A export of the configuration audit test");
		config.setExportedRelativeUrl(unique("audit-a2a-export"));
		config.setEnabled(false);
		OperationStatus<A2AServerConfig> inserted = a2aServerController.insertA2AServer(config);
		assertFalse(inserted.isHasErrorMessages(), "The A2A export must be inserted: " + inserted.getMessages());
		String code = inserted.getResult().getCode();
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION,
				SecurityAuditTaxonomy.Action.A2A_EXPORT_INSERT, SecurityAuditTaxonomy.ResourceType.A2A_EXPORT, code,
				SecurityAuditTaxonomy.Outcome.SUCCESS);

		A2AServerConfig stored = inserted.getResult();
		stored.setDescription("A2A export changed by the configuration audit test");
		a2aServerController.updateA2AServer(stored);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION,
				SecurityAuditTaxonomy.Action.A2A_EXPORT_UPDATE, SecurityAuditTaxonomy.ResourceType.A2A_EXPORT, code,
				SecurityAuditTaxonomy.Outcome.SUCCESS);

		a2aServerController.deleteA2AServer(code);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION,
				SecurityAuditTaxonomy.Action.A2A_EXPORT_DELETE, SecurityAuditTaxonomy.ResourceType.A2A_EXPORT, code,
				SecurityAuditTaxonomy.Outcome.SUCCESS);
	}

	// ------------------------------------------------------------------------------
	// Knowledge configuration
	// ------------------------------------------------------------------------------

	@Test
	public void chatProfilesAreAuditedOnInsertUpdateAndDelete() throws GeboPersistenceException {
		// no code: generated on insert, and taken from what was saved
		GChatProfileConfiguration profile = new GChatProfileConfiguration();
		profile.setDescription("Chat profile of the configuration audit test");
		profile.setAccessibleToAll(false);
		profile.setAccessibleGroups(new ArrayList<>(List.of("SUPPORT")));
		GChatProfileConfiguration saved = chatProfilesController.insertChatProfile(profile);
		String code = saved.getCode();
		assertNotNull(code);
		JsonNode insertEvent = singleConfigurationEvent(SecurityAuditTaxonomy.EventType.KNOWLEDGE_CONFIGURATION,
				SecurityAuditTaxonomy.Action.CHAT_PROFILE_INSERT, SecurityAuditTaxonomy.ResourceType.CHAT_PROFILE, code,
				SecurityAuditTaxonomy.Outcome.SUCCESS);
		assertEquals("SUPPORT",
				insertEvent.get("details").get(ConfigurationAuditSupport.DETAIL_ACCESSIBLE_GROUPS).get(0).asText());

		saved.setDescription("Chat profile changed by the configuration audit test");
		chatProfilesController.updateChatProfile(saved);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.KNOWLEDGE_CONFIGURATION,
				SecurityAuditTaxonomy.Action.CHAT_PROFILE_UPDATE, SecurityAuditTaxonomy.ResourceType.CHAT_PROFILE, code,
				SecurityAuditTaxonomy.Outcome.SUCCESS);

		chatProfilesController.deleteChatProfile(saved);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.KNOWLEDGE_CONFIGURATION,
				SecurityAuditTaxonomy.Action.CHAT_PROFILE_DELETE, SecurityAuditTaxonomy.ResourceType.CHAT_PROFILE, code,
				SecurityAuditTaxonomy.Outcome.SUCCESS);
	}

	@Test
	public void knowledgeBasesAndProjectsAreAuditedOnInsertUpdateAndDelete() throws GeboPersistenceException {
		// no code: generated on insert, and taken from what was saved
		GKnowledgeBase kb = new GKnowledgeBase();
		kb.setDescription("Knowledge base of the configuration audit test " + UUID.randomUUID());
		kb.setAccessibleToAll(false);
		kb.setAccessibleGroups(new ArrayList<>(List.of("HR")));
		GKnowledgeBase savedKb = knowledgeBaseController.insertKnowledgeBase(kb);
		assertNotNull(savedKb.getCode());
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.KNOWLEDGE_CONFIGURATION,
				SecurityAuditTaxonomy.Action.KNOWLEDGE_BASE_INSERT, SecurityAuditTaxonomy.ResourceType.KNOWLEDGE_BASE,
				savedKb.getCode(), SecurityAuditTaxonomy.Outcome.SUCCESS);

		GProject project = new GProject();
		project.setRootKnowledgeBaseCode(savedKb.getCode());
		project.setDescription("Project of the configuration audit test");
		GProject savedProject = projectsController.insertProject(project);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.KNOWLEDGE_CONFIGURATION,
				SecurityAuditTaxonomy.Action.PROJECT_INSERT, SecurityAuditTaxonomy.ResourceType.PROJECT,
				savedProject.getCode(), SecurityAuditTaxonomy.Outcome.SUCCESS);

		savedProject.setDescription("Project changed by the configuration audit test");
		savedProject = projectsController.updateProject(savedProject);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.KNOWLEDGE_CONFIGURATION,
				SecurityAuditTaxonomy.Action.PROJECT_UPDATE, SecurityAuditTaxonomy.ResourceType.PROJECT,
				savedProject.getCode(), SecurityAuditTaxonomy.Outcome.SUCCESS);

		savedKb.setAccessibleGroups(new ArrayList<>(List.of("HR", "MANAGEMENT")));
		savedKb = knowledgeBaseController.updateKnowledgeBase(savedKb);
		JsonNode kbUpdate = singleConfigurationEvent(SecurityAuditTaxonomy.EventType.KNOWLEDGE_CONFIGURATION,
				SecurityAuditTaxonomy.Action.KNOWLEDGE_BASE_UPDATE, SecurityAuditTaxonomy.ResourceType.KNOWLEDGE_BASE,
				savedKb.getCode(), SecurityAuditTaxonomy.Outcome.SUCCESS);
		assertEquals(2, kbUpdate.get("details").get(ConfigurationAuditSupport.DETAIL_ACCESSIBLE_GROUPS).size());

		projectsController.deleteProject(savedProject);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.KNOWLEDGE_CONFIGURATION,
				SecurityAuditTaxonomy.Action.PROJECT_DELETE, SecurityAuditTaxonomy.ResourceType.PROJECT,
				savedProject.getCode(), SecurityAuditTaxonomy.Outcome.SUCCESS);

		knowledgeBaseController.deleteKnowledgeBase(savedKb);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.KNOWLEDGE_CONFIGURATION,
				SecurityAuditTaxonomy.Action.KNOWLEDGE_BASE_DELETE, SecurityAuditTaxonomy.ResourceType.KNOWLEDGE_BASE,
				savedKb.getCode(), SecurityAuditTaxonomy.Outcome.SUCCESS);
	}

	// ------------------------------------------------------------------------------
	// Search configuration
	// ------------------------------------------------------------------------------

	@Test
	public void deepSearchConfigurationsAreAuditedWithTheirExternalSourcesAccess() throws GeboPersistenceException {
		// not the default configuration: the external sources other tests search stay as they are
		DeepSearchConfig config = new DeepSearchConfig();
		config.setDescription("Deep search configuration of the configuration audit test");
		config.setDefaultConfig(false);
		config.setPerDataSourceConfigured(false);
		config.setAccessibleToAll(false);
		config.setAccessibleGroups(new ArrayList<>(List.of("MANAGEMENT")));
		DeepSearchConfig saved = deepSearchController.insertDeepSearchConfig(config);
		String code = saved.getCode();
		assertNotNull(code, "The code is generated on insert");
		JsonNode insertEvent = singleConfigurationEvent(SecurityAuditTaxonomy.EventType.SEARCH_CONFIGURATION,
				SecurityAuditTaxonomy.Action.DEEP_SEARCH_CONFIG_INSERT, SecurityAuditTaxonomy.ResourceType.DEEP_SEARCH_CONFIG,
				code, SecurityAuditTaxonomy.Outcome.SUCCESS);
		assertFalse(insertEvent.get("details").get("perDataSourceConfigured").asBoolean());

		saved.setDescription("Deep search configuration changed by the configuration audit test");
		deepSearchController.updateDeepSearchConfig(saved);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.SEARCH_CONFIGURATION,
				SecurityAuditTaxonomy.Action.DEEP_SEARCH_CONFIG_UPDATE, SecurityAuditTaxonomy.ResourceType.DEEP_SEARCH_CONFIG,
				code, SecurityAuditTaxonomy.Outcome.SUCCESS);

		deepSearchController.deleteDeepSearchConfig(saved);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.SEARCH_CONFIGURATION,
				SecurityAuditTaxonomy.Action.DEEP_SEARCH_CONFIG_DELETE, SecurityAuditTaxonomy.ResourceType.DEEP_SEARCH_CONFIG,
				code, SecurityAuditTaxonomy.Outcome.SUCCESS);
	}

	/**
	 * One web search provider stands for the five (Google, Tavily, Brave, SearXNG,
	 * SerpApi): their configuration controllers are instrumented the same way. The
	 * fast setup, which also writes a secret, is not exercised here.
	 */
	@Test
	public void webSearchCredentialsAreAuditedWithTheirProvider() {
		String code = unique("audit-tavily");
		GTavilySearchApiCredentials credentials = new GTavilySearchApiCredentials();
		credentials.setCode(code);
		credentials.setDescription("Tavily credentials of the configuration audit test");
		GTavilySearchApiCredentials saved = tavilyController.insertGTavilySearchApiCredentials(credentials);
		JsonNode insertEvent = singleConfigurationEvent(SecurityAuditTaxonomy.EventType.SEARCH_CONFIGURATION,
				SecurityAuditTaxonomy.Action.WEB_SEARCH_CONFIG_INSERT, SecurityAuditTaxonomy.ResourceType.WEB_SEARCH_CONFIG,
				code, SecurityAuditTaxonomy.Outcome.SUCCESS);
		assertEquals("tavily", insertEvent.get("details").get("provider").asText());

		saved.setDescription("Tavily credentials changed by the configuration audit test");
		tavilyController.updateGTavilySearchApiCredentials(saved);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.SEARCH_CONFIGURATION,
				SecurityAuditTaxonomy.Action.WEB_SEARCH_CONFIG_UPDATE, SecurityAuditTaxonomy.ResourceType.WEB_SEARCH_CONFIG,
				code, SecurityAuditTaxonomy.Outcome.SUCCESS);

		tavilyController.deleteGTavilySearchApiCredentials(saved);
		singleConfigurationEvent(SecurityAuditTaxonomy.EventType.SEARCH_CONFIGURATION,
				SecurityAuditTaxonomy.Action.WEB_SEARCH_CONFIG_DELETE, SecurityAuditTaxonomy.ResourceType.WEB_SEARCH_CONFIG,
				code, SecurityAuditTaxonomy.Outcome.SUCCESS);
	}

	// ------------------------------------------------------------------------------
	// User administration: one event per change
	// ------------------------------------------------------------------------------

	/**
	 * The users admin endpoint used to audit the group changes on top of the users
	 * admin service, so each one was written twice: now only the service's event,
	 * which names the members, reaches the security log.
	 */
	@Test
	public void aGroupInsertedThroughTheAdminEndpointIsAuditedOnce() {
		String code = unique("AUDIT_GROUP");
		UsersGroup group = new UsersGroup();
		group.setCode(code);
		group.setDescription("Group of the configuration audit test");
		group.setUserIds(new ArrayList<>(List.of(DEFAULT_ALL_ROLES_USER)));
		usersAdminController.insertGroup(group);

		List<JsonNode> inserted = loggedEventsWithAction(SecurityAuditTaxonomy.Action.GROUP_INSERT);
		assertEquals(1, inserted.size(), "A group insert must be audited exactly once: " + inserted);
		assertEquals(code, inserted.get(0).get("resourceId").asText());
		assertEquals(SecurityAuditTaxonomy.Outcome.SUCCESS, inserted.get(0).get("outcome").asText());
		listAppender.list.clear();

		usersAdminController.deleteGroup(group);
		List<JsonNode> deleted = loggedEventsWithAction(SecurityAuditTaxonomy.Action.GROUP_DELETE);
		assertEquals(1, deleted.size(), "A group delete must be audited exactly once: " + deleted);
	}
}
