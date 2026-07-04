/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.mcpclient.content.handler.impl;

import ai.gebo.systems.abstraction.layer.IGVirtualFileSystemContext;

/**
 * Browsing context for the MCP virtual filesystem: identifies the
 * {@link ai.gebo.architecture.mcpclients.model.MCPClientConfig} (by code) whose
 * MCP server is being navigated while an operator configures a virtual drive.
 */
public class MCPClientBrowsingContext implements IGVirtualFileSystemContext {

	private String mcpClientConfigCode = null;

	public String getMcpClientConfigCode() {
		return mcpClientConfigCode;
	}

	public void setMcpClientConfigCode(String mcpClientConfigCode) {
		this.mcpClientConfigCode = mcpClientConfigCode;
	}

	/**
	 * @param mcpClientConfigCode the MCP client configuration code
	 * @return a context bound to the given MCP client configuration
	 */
	public static MCPClientBrowsingContext of(String mcpClientConfigCode) {
		MCPClientBrowsingContext context = new MCPClientBrowsingContext();
		context.mcpClientConfigCode = mcpClientConfigCode;
		return context;
	}
}
