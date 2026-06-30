/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpserver.model;

import java.util.List;

import lombok.Data;

/**
 * Read-only, user-facing summary of an erogated MCP server that the current
 * (authenticated) user is entitled to call with their own rights.
 * <p>
 * It deliberately exposes only what a caller needs to connect and to understand
 * what the server offers: the connectivity/endpoint coordinates and a short
 * report of the exported tools, resources and prompts. Security grants
 * ({@code accessible*} flags, ACL aliases) and any secret material are
 * intentionally omitted: this is the projection returned by the user-level
 * controller, not the administrative {@link GeboMCPServerConfig}.
 */
@Data
public class UserAccessibleMcpServerView {

	/** Configuration code (stable identifier of the erogated server). */
	private String code;

	/** Human-readable name: the configuration description, or the relative URL as a fallback. */
	private String name;

	/** Free-text description of the server, if any. */
	private String description;

	/** Whether the server is currently published/served. */
	private Boolean enabled;

	// --- Connectivity / endpoint configuration -----------------------------------

	/** The unique relative URL the server is published under, e.g. {@code my-server}. */
	private String exportedUniqueRelativeUrl;

	/** The full endpoint path the client connects to, e.g. {@code /mcp/my-server}. */
	private String endpointPath;

	/** Transport used to reach the endpoint. Erogated servers are served over streamable HTTP. */
	private String transportType;

	// --- Short report of the exported capabilities -------------------------------

	/** Names of the exported tools (plain tools, agent-as-tools and agent-network-as-tools). */
	private List<String> tools;

	/** URIs of the exported resources (knowledge bases, projects, project endpoints). */
	private List<String> resources;

	/** Codes of the exported prompts. */
	private List<String> prompts;

	/** Number of exported tools. */
	private int toolsCount;

	/** Number of exported resources. */
	private int resourcesCount;

	/** Number of exported prompts. */
	private int promptsCount;
}
