/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpserver.runtime;

import java.util.Optional;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.metadata.ToolMetadata;

import io.modelcontextprotocol.common.McpTransportContext;
import io.modelcontextprotocol.server.McpSyncServerExchange;

/**
 * Decorates a {@link ToolCallback} so that its execution runs under the identity of
 * the MCP caller. When invoked with a {@link ToolContext}, the originating
 * {@link McpSyncServerExchange} is recovered via {@link McpToolUtils#getMcpExchange},
 * its {@link McpTransportContext} is read, and the delegate is executed through
 * {@link GeboMcpSecurityContextSupport#runAs} so the platform tool sees the correct
 * authenticated user (and ACLs) even though the MCP SDK may run it off the request thread.
 */
public class GeboMcpSecurityAwareToolCallback implements ToolCallback {

	private final ToolCallback delegate;
	private final GeboMcpSecurityContextSupport securitySupport;

	public GeboMcpSecurityAwareToolCallback(ToolCallback delegate, GeboMcpSecurityContextSupport securitySupport) {
		this.delegate = delegate;
		this.securitySupport = securitySupport;
	}

	@Override
	public ToolDefinition getToolDefinition() {
		return delegate.getToolDefinition();
	}

	@Override
	public ToolMetadata getToolMetadata() {
		return delegate.getToolMetadata();
	}

	@Override
	public String call(String toolInput) {
		return delegate.call(toolInput);
	}

	@Override
	public String call(String toolInput, ToolContext toolContext) {
		Optional<McpSyncServerExchange> exchange = McpToolUtils.getMcpExchange(toolContext);
		McpTransportContext transportContext = exchange.map(McpSyncServerExchange::transportContext).orElse(null);
		return securitySupport.runAs(transportContext, () -> delegate.call(toolInput, toolContext));
	}
}
