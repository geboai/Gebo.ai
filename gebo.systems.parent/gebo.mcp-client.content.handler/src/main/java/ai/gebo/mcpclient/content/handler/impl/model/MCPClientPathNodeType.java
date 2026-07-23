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
 * The kinds of node that make up the (deliberately shallow) MCP virtual
 * filesystem hierarchy.
 * <p>
 * The base MCP resource model is a flat list, so the tree is only two levels
 * deep: the {@link #SERVER} folder — the virtual drive — directly containing the
 * exposed {@link #RESOURCE} leaves.
 */
public enum MCPClientPathNodeType {
	/** The MCP server itself, rendered as the root folder / virtual drive. */
	SERVER,

	/** A single MCP resource, rendered as a streamable leaf document. */
	RESOURCE
}
