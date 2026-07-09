/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.mcpclient.content.handler;

import ai.gebo.mcpclient.content.handler.impl.model.MCPClientResourceReference;
import ai.gebo.systems.abstraction.layer.IGRemoteVirtualFilesystemConsumingService;

/**
 * Consuming service for the MCP virtual filesystem: browses the MCP server's
 * resources and streams their content back through the platform's remote virtual
 * filesystem abstraction.
 */
public interface IGMCPClientVirtualFilesystemConsumingService extends
		IGRemoteVirtualFilesystemConsumingService<GMCPClientSystem, MCPClientProjectEndpoint, MCPClientResourceReference> {

}
