/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Tunable limits applied when an erogated MCP server exposes the
 * knowledge-base/project/endpoint/virtual-folder hierarchy and serves document
 * content as resources.
 * <p>
 * Every field carries a sensible default, so the configuration is entirely
 * optional; values may be overridden in {@code application.yml} under the
 * {@code ai.gebo.mcp.server.resources} prefix, e.g.:
 *
 * <pre>
 * ai:
 *   gebo:
 *     mcp:
 *       server:
 *         resources:
 *           endpoint-max-folder-depth: 2
 *           max-document-bytes: 16777216
 *           max-document-text-chars: 8388608
 * </pre>
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.mcp.server.resources")
@Data
public class GeboMcpResourcesConfig {

	/**
	 * Deepest level of nested virtual folders listed when an endpoint resource is
	 * read. Documents are never expanded at the endpoint level.
	 */
	private int endpointMaxFolderDepth = 2;

	/**
	 * Maximum raw document size, in bytes, served inline through MCP. Larger
	 * documents are rejected (truncating arbitrary bytes would corrupt the file).
	 * Default: 16 MiB.
	 */
	private int maxDocumentBytes = 16 * 1024 * 1024;

	/**
	 * Maximum extracted-text length, in characters, served inline through MCP.
	 * Longer text is truncated with a marker. Default: 8M characters.
	 */
	private int maxDocumentTextChars = 8 * 1024 * 1024;
	private boolean usersCanLookupMcpServers = true;
}
