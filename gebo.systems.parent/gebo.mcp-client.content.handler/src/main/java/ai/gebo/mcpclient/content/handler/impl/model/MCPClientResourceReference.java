/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.mcpclient.content.handler.impl.model;

import ai.gebo.systems.abstraction.layer.IGRemoteVirtualFilesystemResourceReference;

/**
 * A streamable handle to a single MCP server resource.
 * <p>
 * It is reconstructed from the metadata carried by a document reference (or by a
 * native position object) and holds everything needed to fetch the resource
 * content back from the MCP server through a {@code resources/read} call.
 */
public class MCPClientResourceReference implements IGRemoteVirtualFilesystemResourceReference {

	/** Canonical MCP resource URI to read. */
	public String uri;

	/** Human readable name of the resource, when known. */
	public String name;

	/** MIME type advertised for the resource, when known. */
	public String mimeType;

}
