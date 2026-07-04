/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.mcpclient.content.handler;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GVirtualFilesystemProjectEndpoint;
import ai.gebo.model.annotations.EntityDescription;
import lombok.Data;

/**
 * A connection endpoint for a Model Context Protocol (MCP) server.
 * <p>
 * Semantically an endpoint behaves like a "virtual drive": it references a
 * configured {@link GMCPClientSystem} (which in turn points to the underlying
 * {@link ai.gebo.architecture.mcpclients.model.MCPClientConfig} holding the MCP
 * client connection), and it encodes, in the inherited
 * {@link GVirtualFilesystemProjectEndpoint#getPaths() paths} list, the MCP server
 * resources that should be integrated as documents. Each selected resource is
 * stored as a {@link ai.gebo.model.virtualfs.VFilesystemReference} whose root is
 * the MCP server and whose path carries the canonical MCP resource URI (see
 * {@link ai.gebo.mcpclient.content.handler.impl.MCPClientNavigationUtil}). An
 * endpoint with an empty path list — or a path that points at the server root —
 * is treated as "integrate every resource the MCP server exposes".
 */
@Data
@EntityDescription(description = "MCP server documents source", entityCategory = GProjectEndpoint.class)
@Document
public class MCPClientProjectEndpoint extends GVirtualFilesystemProjectEndpoint {

	/**
	 * Code of the {@link ai.gebo.architecture.mcpclients.model.MCPClientConfig}
	 * (managed by the {@code gebo.architecture.mcp-clients} module) that provides
	 * the transport, endpoint and credentials used to reach the MCP server this
	 * virtual drive integrates. Reusing that configuration means content ingestion
	 * and tool-calling share the same authenticated MCP connection.
	 */
	private String mcpClientConfigCode = null;

}
