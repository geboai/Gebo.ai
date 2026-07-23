/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpserver.runtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import io.modelcontextprotocol.server.McpSyncServer;
import lombok.Getter;

/**
 * A single live erogated MCP server: the {@link GeboMCPServerConfig} snapshot it
 * was built from, the running {@link McpSyncServer}, and the access-filtered
 * {@link RouterFunction} that serves its {@code mcp/<url>} endpoint. Instances are
 * created by {@link GeboMcpServerBuilder} and owned by {@link GeboMcpServerRegistry}.
 */
@Getter
public class GeboMcpServerInstance {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeboMcpServerInstance.class);

	private final GeboMCPServerConfig config;
	private final McpSyncServer server;
	private final RouterFunction<ServerResponse> routerFunction;

	public GeboMcpServerInstance(GeboMCPServerConfig config, McpSyncServer server,
			RouterFunction<ServerResponse> routerFunction) {
		this.config = config;
		this.server = server;
		this.routerFunction = routerFunction;
	}

	/**
	 * Gracefully stops the underlying MCP server, swallowing errors so that a
	 * failure to close one server never blocks the registry refresh.
	 */
	public void close() {
		if (server == null) {
			return;
		}
		try {
			server.closeGracefully();
		} catch (Throwable t) {
			LOGGER.warn("Error closing MCP server '{}'", config != null ? config.getExportedUniqueRelativeUrl() : null,
					t);
			try {
				server.close();
			} catch (Throwable t2) {
				LOGGER.warn("Error force-closing MCP server", t2);
			}
		}
	}
}
