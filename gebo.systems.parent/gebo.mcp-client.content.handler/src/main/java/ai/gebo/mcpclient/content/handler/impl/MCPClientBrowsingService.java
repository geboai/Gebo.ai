/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.mcpclient.content.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.mcpclients.model.MCPClientConfig;
import ai.gebo.architecture.mcpclients.service.McpClientManagementService;
import ai.gebo.architecture.mcpclients.service.impl.McpClientPool;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.IGVirtualFilesystemBrowsingService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import io.modelcontextprotocol.spec.McpSchema;

/**
 * Provides the "virtual filesystem logical browsing" of an MCP server: the
 * server (identified by an MCP client configuration) is presented as a single
 * root (a virtual drive) whose children are the resources it exposes. As the MCP
 * resource model is flat, browsing is one-level: the resources under the server
 * root are returned as leaf files.
 */
@Service
public class MCPClientBrowsingService implements IGVirtualFilesystemBrowsingService<MCPClientBrowsingContext> {

	@Autowired
	McpClientManagementService managementService;

	@Autowired
	McpClientPool clientPool;

	@Override
	public OperationStatus<List<GVirtualFilesystemRoot>> getRoots(MCPClientBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		List<GVirtualFilesystemRoot> roots = new ArrayList<>();
		try {
			MCPClientConfig config = resolveConfig(context);
			roots.add(MCPClientNavigationUtil.toRoot(config.getCode(), describe(config), config.getBaseUrl()));
		} catch (Throwable exc) {
			return OperationStatus.of(roots, GUserMessage.errorMessage("Error accessing MCP server", exc));
		}
		return OperationStatus.of(roots);
	}

	@Override
	public OperationStatus<List<PathInfo>> browse(BrowseParam param, MCPClientBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		List<PathInfo> paths = new ArrayList<>();
		try {
			if (!MCPClientNavigationUtil.isServerRoot(param.root)) {
				return OperationStatus.of(paths);
			}
			// The MCP resource model is flat: only the server root has children, and
			// they are all leaves; there is nothing to list below a resource.
			if (param.path != null) {
				return OperationStatus.of(paths);
			}
			MCPClientConfig config = resolveConfig(context);
			for (McpSchema.Resource resource : listResources(config)) {
				paths.add(MCPClientNavigationUtil.toResourcePathInfo(resource.uri(), resource.name()));
			}
		} catch (Throwable exc) {
			return OperationStatus.of(paths, GUserMessage.errorMessage("Error browsing MCP server", exc));
		}
		return OperationStatus.of(paths);
	}

	@Override
	public boolean isSupportsNavigationStatus() {
		return false;
	}

	@Override
	public VFilesystemReference getParent(VFilesystemReference reference, MCPClientBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		return null;
	}

	private List<McpSchema.Resource> listResources(MCPClientConfig config) throws VirtualFilesystemBrowsingException {
		try {
			return clientPool.execute(config, client -> {
				McpSchema.ListResourcesResult result = client.listResources();
				return result != null && result.resources() != null ? result.resources() : List.<McpSchema.Resource>of();
			});
		} catch (Exception e) {
			throw new VirtualFilesystemBrowsingException("Cannot list resources of MCP server " + config.getCode(), e);
		}
	}

	private MCPClientConfig resolveConfig(MCPClientBrowsingContext context) throws VirtualFilesystemBrowsingException {
		if (context == null || context.getMcpClientConfigCode() == null) {
			throw new VirtualFilesystemBrowsingException("No MCP client configuration code provided");
		}
		OperationStatus<MCPClientConfig> status = managementService.findByCode(context.getMcpClientConfigCode());
		MCPClientConfig config = status != null ? status.getResult() : null;
		if (config == null) {
			throw new VirtualFilesystemBrowsingException(
					"MCP client configuration '" + context.getMcpClientConfigCode() + "' cannot be resolved");
		}
		return config;
	}

	private static String describe(MCPClientConfig config) {
		if (config.getExportingPrefix() != null && !config.getExportingPrefix().isBlank()) {
			return config.getExportingPrefix();
		}
		return config.getCode();
	}
}
