/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpserver.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ai.gebo.architecture.mcpserver.config.GeboMcpResourcesConfig;
import ai.gebo.architecture.mcpserver.model.GeboMCPAgentTool;
import ai.gebo.architecture.mcpserver.model.GeboMCPAgentsNetworkTool;
import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.architecture.mcpserver.model.UserAccessibleMcpServerView;
import ai.gebo.architecture.mcpserver.runtime.GeboMcpAccessChecker;
import ai.gebo.architecture.mcpserver.service.IGMCPServerConfigManagerService;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.model.base.GObjectRef;
import lombok.AllArgsConstructor;

/**
 * User-level REST controller that lets an authenticated user discover the
 * erogated MCP servers they are entitled to call with their own rights.
 * <p>
 * Unlike {@link GeboMCPServerAdminController} (which manages every
 * configuration and is reserved to administrators), this controller never
 * mutates anything and never exposes the underlying
 * {@link GeboMCPServerConfig}. Each server is filtered through
 * {@link GeboMcpAccessChecker#canAccessServer(GeboMCPServerConfig)} — the very
 * same check the runtime applies on the {@code /mcp/<url>} endpoint — so the
 * caller only ever sees servers their identity (or an administrator's, via
 * {@code adminCanDoAll}) is granted on. For each visible server it returns a
 * compact {@link UserAccessibleMcpServerView}: the connectivity/endpoint
 * coordinates plus a short list of the exported tools, resources and prompts.
 */
@RestController
@PreAuthorize("hasAnyRole('USER','ADMIN')")
@RequestMapping("api/user/GeboMCPServerUserController")
@AllArgsConstructor
public class GeboMCPServerUserController {

	/**
	 * Endpoint prefix every erogated MCP server is published under (mirrors
	 * GeboMcpServerBuilder).
	 */
	private static final String ENDPOINT_PREFIX = "/mcp/";

	/**
	 * Erogated MCP servers are served over streamable HTTP by the WebMvc transport.
	 */
	private static final String TRANSPORT_TYPE = "STREAMABLE_HTTP";

	/** Resource URI schemes (mirror GeboMcpResourcesProvider). */
	private static final String KB_SCHEME = "gebo-kb://";
	private static final String PROJECT_SCHEME = "gebo-project://";
	private static final String ENDPOINT_SCHEME = "gebo-endpoint://";

	private final IGMCPServerConfigManagerService managerService;
	private final GeboMcpAccessChecker accessChecker;
	private final GeboMcpResourcesConfig mcpResourcesConfig;

	/**
	 * Lists every erogated MCP server the current user may call, as a compact view
	 * reporting its endpoint/connectivity configuration and exported
	 * tools/resources/prompts.
	 *
	 * @return the accessible servers (empty if the user is granted on none)
	 */
	@GetMapping(value = "listAccessibleMcpServers", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserAccessibleMcpServerView> listAccessibleMcpServers() throws GeboPersistenceException {
		if (!mcpResourcesConfig.isUsersCanLookupMcpServers())
			throw new IllegalStateException("Users cannot list mcp servers");
		List<UserAccessibleMcpServerView> result = new ArrayList<>();
		for (GeboMCPServerConfig config : managerService.findAll()) {
			if (accessChecker.canAccessServer(config)) {
				result.add(toView(config));
			}
		}
		return result;
	}

	/**
	 * Returns the compact view of a single accessible MCP server by its code.
	 *
	 * @param code the configuration code
	 * @return the server view
	 * @throws ResponseStatusException 404 if no such server exists, 403 if the
	 *                                 current user is not allowed to call it
	 */
	@GetMapping(value = "findAccessibleMcpServerByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserAccessibleMcpServerView findAccessibleMcpServerByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		if (!mcpResourcesConfig.isUsersCanLookupMcpServers())
			throw new IllegalStateException("Users cannot list mcp servers");
		GeboMCPServerConfig config = managerService.findByCode(code);
		if (config == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "MCP server '" + code + "' does not exist");
		}
		if (!accessChecker.canAccessServer(config)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					"You are not allowed to call MCP server '" + code + "'");
		}
		return toView(config);
	}

	@GetMapping(value = "getUsersCanAccessMcpServersList", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean getUsersCanAccessMcpServersList() {
		return (mcpResourcesConfig.isUsersCanLookupMcpServers());

	}

	/**
	 * Maps a configuration to its user-facing view, deriving the short capability
	 * report from the configured names without resolving the underlying objects.
	 */
	private UserAccessibleMcpServerView toView(GeboMCPServerConfig config) {
		UserAccessibleMcpServerView view = new UserAccessibleMcpServerView();
		view.setCode(config.getCode());
		view.setDescription(config.getDescription());
		view.setName(displayName(config));
		view.setEnabled(config.getEnabled());

		view.setExportedUniqueRelativeUrl(config.getExportedUniqueRelativeUrl());
		view.setEndpointPath(
				config.getExportedUniqueRelativeUrl() != null ? ENDPOINT_PREFIX + config.getExportedUniqueRelativeUrl()
						: null);
		view.setTransportType(TRANSPORT_TYPE);

		List<String> tools = collectTools(config);
		List<String> resources = collectResources(config);
		List<String> prompts = config.getExportedPrompts() != null ? new ArrayList<>(config.getExportedPrompts())
				: new ArrayList<>();

		view.setTools(tools);
		view.setResources(resources);
		view.setPrompts(prompts);
		view.setToolsCount(tools.size());
		view.setResourcesCount(resources.size());
		view.setPromptsCount(prompts.size());
		return view;
	}

	private static String displayName(GeboMCPServerConfig config) {
		if (config.getDescription() != null && !config.getDescription().isBlank()) {
			return config.getDescription();
		}
		return config.getExportedUniqueRelativeUrl();
	}

	/**
	 * Plain enabled tools plus the agent-as-tool and agent-network-as-tool exports.
	 */
	private static List<String> collectTools(GeboMCPServerConfig config) {
		List<String> tools = new ArrayList<>();
		if (config.getEnabledTools() != null) {
			tools.addAll(config.getEnabledTools());
		}
		if (config.getAgentAsTools() != null) {
			for (GeboMCPAgentTool agentTool : config.getAgentAsTools()) {
				if (agentTool != null && agentTool.getToolName() != null) {
					tools.add(agentTool.getToolName());
				}
			}
		}
		if (config.getAgentNetworkAsTools() != null) {
			for (GeboMCPAgentsNetworkTool networkTool : config.getAgentNetworkAsTools()) {
				if (networkTool != null && networkTool.getToolName() != null) {
					tools.add(networkTool.getToolName());
				}
			}
		}
		return tools;
	}

	/**
	 * Exported knowledge bases, projects and project endpoints as their resource
	 * URIs.
	 */
	private static List<String> collectResources(GeboMCPServerConfig config) {
		List<String> resources = new ArrayList<>();
		if (config.getExportedKnowledgeBasesAsResources() != null) {
			for (String kbCode : config.getExportedKnowledgeBasesAsResources()) {
				resources.add(KB_SCHEME + kbCode);
			}
		}
		if (config.getExportedProjectsAsResources() != null) {
			for (String projectCode : config.getExportedProjectsAsResources()) {
				resources.add(PROJECT_SCHEME + projectCode);
			}
		}
		if (config.getExportedProjectEndpoints() != null) {
			for (GObjectRef<?> ref : config.getExportedProjectEndpoints()) {
				if (ref != null && ref.getCode() != null) {
					resources.add(ENDPOINT_SCHEME + ref.getCode());
				}
			}
		}
		return resources;
	}
}
