/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.mcpclient.content.handler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.mcpclient.content.handler.impl.MCPClientBrowsingContext;
import ai.gebo.mcpclient.content.handler.impl.MCPClientBrowsingService;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.model.virtualfs.VirtualFilesystemNavigationTreeStatus;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Admin REST controller exposing the MCP server as a browsable virtual
 * filesystem: it lists server roots (drives) and the resources under a root, so
 * an operator can pick which MCP resources an endpoint should integrate.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/MCPClientBrowsingController")
public class MCPClientBrowsingController {

	@Autowired
	MCPClientBrowsingService browsingService;

	/**
	 * Lists the virtual filesystem roots (the MCP server drive) for a configuration.
	 */
	@GetMapping(value = "getMCPClientRoots", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GVirtualFilesystemRoot>> getMCPClientRoots(
			@RequestParam("mcpClientConfigCode") String mcpClientConfigCode) throws VirtualFilesystemBrowsingException {
		return browsingService.getRoots(MCPClientBrowsingContext.of(mcpClientConfigCode));
	}

	/**
	 * Browses a path under the MCP server root, returning its resources as leaves.
	 */
	@PostMapping(value = "browseMCPClientPath", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<PathInfo>> browseMCPClientPath(@RequestBody BrowseParam param,
			@RequestParam("mcpClientConfigCode") String mcpClientConfigCode) throws VirtualFilesystemBrowsingException {
		return browsingService.browse(param, MCPClientBrowsingContext.of(mcpClientConfigCode));
	}

	/**
	 * Rebuilds the navigation status for a set of stored references.
	 */
	@PostMapping(value = "getMCPClientNavigationStatus", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<VirtualFilesystemNavigationTreeStatus>> getMCPClientNavigationStatus(
			@RequestParam("mcpClientConfigCode") String mcpClientConfigCode,
			@NotNull @Valid @RequestBody List<VFilesystemReference> references)
			throws VirtualFilesystemBrowsingException {
		return browsingService.navigationStatus(references, MCPClientBrowsingContext.of(mcpClientConfigCode));
	}
}
