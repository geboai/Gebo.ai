/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpserver.runtime;

import java.util.List;
import java.util.Optional;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.metadata.ToolMetadata;

import ai.gebo.llms.agent.standard.services.UserKnowledgeBasesExecutionEnvironment;
import io.modelcontextprotocol.common.McpTransportContext;
import io.modelcontextprotocol.server.McpSyncServerExchange;

/**
 * Decorates a {@link ToolCallback} so that its execution runs under the identity of
 * the MCP caller. When invoked with a {@link ToolContext}, the originating
 * {@link McpSyncServerExchange} is recovered via {@link McpToolUtils#getMcpExchange},
 * its {@link McpTransportContext} is read, and the delegate is executed through
 * {@link GeboMcpSecurityContextSupport#runAs} so the platform tool sees the correct
 * authenticated user (and ACLs) even though the MCP SDK may run it off the request thread.
 * Outside a chat, the tool works on all the knowledge bases the caller can see (see
 * {@link UserKnowledgeBasesExecutionEnvironment}), read under the caller's identity and
 * given in its tools context as a chat gives its own.
 */
public class GeboMcpSecurityAwareToolCallback implements ToolCallback {

	private final ToolCallback delegate;
	private final GeboMcpSecurityContextSupport securitySupport;
	private final UserKnowledgeBasesExecutionEnvironment userEnvironment;

	public GeboMcpSecurityAwareToolCallback(ToolCallback delegate, GeboMcpSecurityContextSupport securitySupport,
			UserKnowledgeBasesExecutionEnvironment userEnvironment) {
		this.delegate = delegate;
		this.securitySupport = securitySupport;
		this.userEnvironment = userEnvironment;
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
		return delegate.call(toolInput, withUserKnowledgeBases(null));
	}

	@Override
	public String call(String toolInput, ToolContext toolContext) {
		Optional<McpSyncServerExchange> exchange = McpToolUtils.getMcpExchange(toolContext);
		McpTransportContext transportContext = exchange.map(McpSyncServerExchange::transportContext).orElse(null);
		return securitySupport.runAs(transportContext,
				() -> delegate.call(toolInput, withUserKnowledgeBases(toolContext)));
	}

	/** The tools context with the knowledge bases of the caller, read as the caller. */
	ToolContext withUserKnowledgeBases(ToolContext toolContext) {
		final List<String> knowledgeBases = userEnvironment.knowledgeBaseCodes();
		return new ToolContext(userEnvironment.toolsContext(toolContext != null ? toolContext.getContext() : null,
				knowledgeBases));
	}
}
