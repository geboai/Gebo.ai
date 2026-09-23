/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpclients.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.mcpclients.model.MCPClientConfig;
import ai.gebo.architecture.mcpclients.repository.McpClientConfigRepository;
import ai.gebo.architecture.mcpclients.service.McpClientManagementService;
import ai.gebo.architecture.utils.DataPage;
import ai.gebo.model.OperationStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

/**
 * Admin REST controller for managing MCP client configurations: full CRUD,
 * connectivity test &amp; feature discovery, a paged list (served by the
 * management service) and a paged Query-by-Example search (served by the
 * repository). All operations require the {@code ADMIN} role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/McpClientConfigController")
@AllArgsConstructor
public class McpClientConfigController {

	private final McpClientManagementService managementService;
	private final McpClientConfigRepository repository;

	/**
	 * Tests connectivity to the configured MCP server and discovers its
	 * tools/resources/prompts, returning the diffed configuration.
	 */
	@PostMapping(value = "testAndDiscovery", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<MCPClientConfig> testAndDiscovery(@RequestBody @Valid @NotNull MCPClientConfig config) {
		return managementService.testAndDiscovery(config);
	}

	/**
	 * Creates a new MCP client configuration.
	 */
	@PostMapping(value = "insertMCPClientConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<MCPClientConfig> insertMCPClientConfig(@RequestBody @Valid @NotNull MCPClientConfig config) {
		return managementService.insert(config);
	}

	/**
	 * Updates an existing MCP client configuration.
	 */
	@PostMapping(value = "updateMCPClientConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<MCPClientConfig> updateMCPClientConfig(@RequestBody @Valid @NotNull MCPClientConfig config) {
		return managementService.update(config);
	}

	/**
	 * Deletes the given MCP client configuration.
	 */
	@DeleteMapping(value = "deleteMCPClientConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteMCPClientConfig(@RequestBody @Valid @NotNull MCPClientConfig config) {
		return managementService.delete(config);
	}

	/**
	 * Retrieves an MCP client configuration by its code.
	 */
	@GetMapping(value = "findMCPClientConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<MCPClientConfig> findMCPClientConfigByCode(@RequestParam("code") String code) {
		return managementService.findByCode(code);
	}

	/**
	 * Returns a page of MCP client configurations via the management service.
	 */
	@PostMapping(value = "listMCPClientConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<MCPClientConfig> listMCPClientConfig(@RequestBody @Valid @NotNull DataPage page) {
		return managementService.list(page.toPageable());
	}

	/**
	 * Parameters for a paged Query-by-Example search.
	 */
	public static class FindByMCPClientConfigQbeParam {
		@NotNull
		public MCPClientConfig qbe = null;
		@NotNull
		public DataPage page = null;
	}

	/**
	 * Paged Query-by-Example search served directly by the repository.
	 */
	@PostMapping(value = "findMCPClientConfigByQbe", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<MCPClientConfig> findMCPClientConfigByQbe(
			@RequestBody @Valid @NotNull FindByMCPClientConfigQbeParam param) {
		return repository.findByQbe(param.qbe, param.page.toPageable());
	}
}
