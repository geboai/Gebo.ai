/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.mcpclient.content.handler.impl.model;

/**
 * A single step in an MCP navigation path.
 * <p>
 * For MCP the only navigable step below the server root is a resource, so a
 * component carries the resource {@link #uri} together with its
 * {@link #type}.
 */
public class MCPClientPathComponent {

	/** The kind of node this component addresses. */
	public MCPClientPathNodeType type = null;

	/** Canonical MCP resource URI addressed by this component. */
	public String uri = null;
}
