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

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.acl.AclGrantType;
import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.architecture.mcpserver.service.IGMCPServerConfigManagerService;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.utils.DataPage;
import ai.gebo.security.services.IGSecurityService.AclOwnerInfo;
import ai.gebo.security.services.IGSecurityService.AclOwnerInfo.OwnerType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * AI generated comments Admin REST controller to manage the erogated MCP servers.
 * All operations require the 'ADMIN' role. Through this controller an administrator
 * defines a {@link GeboMCPServerConfig}: the {@code exportedUniqueRelativeUrl} of its
 * {@code mcp/<url>} endpoint, the {@code enabledTools}, the exported knowledge bases /
 * projects / project endpoints / prompts, and the access grants (accessible* flags or
 * ACL aliases).
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/GeboMCPServerAdminController")
public class GeboMCPServerAdminController {

	private final IGMCPServerConfigManagerService managerService;

	public GeboMCPServerAdminController(IGMCPServerConfigManagerService managerService) {
		this.managerService = managerService;
	}

	/**
	 * Creates a new MCP server configuration and publishes its endpoint.
	 *
	 * @param config the configuration to create
	 * @return the persisted configuration
	 */
	@PostMapping(value = "insertMcpServer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeboMCPServerConfig insertMcpServer(@Valid @RequestBody GeboMCPServerConfig config)
			throws GeboPersistenceException {
		return managerService.insert(config);
	}

	/**
	 * Updates an existing MCP server configuration and refreshes its endpoint.
	 *
	 * @param config the configuration to update
	 * @return the persisted configuration
	 */
	@PostMapping(value = "updateMcpServer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeboMCPServerConfig updateMcpServer(@Valid @RequestBody GeboMCPServerConfig config)
			throws GeboPersistenceException {
		return managerService.update(config);
	}

	/**
	 * Deletes the MCP server configuration with the given code.
	 *
	 * @param code the configuration code
	 */
	@PostMapping(value = "deleteMcpServer", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteMcpServer(@RequestParam("code") String code) throws GeboPersistenceException {
		managerService.delete(code);
	}

	/**
	 * Finds the MCP server configuration with the given code.
	 *
	 * @param code the configuration code
	 * @return the configuration
	 */
	@GetMapping(value = "findMcpServerByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GeboMCPServerConfig findMcpServerByCode(@RequestParam("code") String code) throws GeboPersistenceException {
		return managerService.findByCode(code);
	}

	/**
	 * Lists all MCP server configurations.
	 *
	 * @return the configurations
	 */
	@GetMapping(value = "getAllMcpServers", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GeboMCPServerConfig> getAllMcpServers() throws GeboPersistenceException {
		return managerService.findAll();
	}

	/**
	 * Returns a page of MCP server configurations.
	 *
	 * @param page the pagination information
	 * @return a page of configurations
	 */
	@PostMapping(value = "getMcpServerPagedList", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Page<GeboMCPServerConfig> getMcpServerPagedList(@Valid @RequestBody DataPage page)
			throws GeboPersistenceException {
		return managerService.getPagedList(page.toPageable());
	}

	/**
	 * DTO describing a single ACL grant (a user or group with its grant types).
	 */
	@Data
	public static class AclOwnerParam {
		@NotNull
		public OwnerType ownerType = null;
		@NotNull
		public String ownerCode = null;
		@NotNull
		public AclGrantType[] grants = null;
	}

	/**
	 * DTO for setting the ACL grants of an MCP server configuration.
	 */
	@Data
	public static class SetMcpServerAclsParam {
		@NotNull
		public String code = null;
		@NotNull
		public List<AclOwnerParam> owners = null;
	}

	/**
	 * Sets the ACL grants of an MCP server configuration and refreshes its endpoint.
	 *
	 * @param param the code and the list of owner grants to apply
	 * @return the persisted configuration
	 */
	@PostMapping(value = "setMcpServerAccessAcls", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeboMCPServerConfig setMcpServerAccessAcls(@Valid @RequestBody SetMcpServerAclsParam param)
			throws GeboPersistenceException {
		List<AclOwnerInfo> owners = new ArrayList<>();
		for (AclOwnerParam owner : param.owners) {
			owners.add(new AclOwnerInfo(owner.ownerType, owner.ownerCode, owner.grants));
		}
		return managerService.setAccessAcls(param.code, owners);
	}
}
