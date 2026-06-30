/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpserver.runtime;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.mcp.server.webmvc.transport.WebMvcStreamableServerTransportProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.json.jackson3.JacksonMcpJsonMapperSupplier;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures.SyncPromptSpecification;
import io.modelcontextprotocol.server.McpServerFeatures.SyncResourceSpecification;
import io.modelcontextprotocol.server.McpServerFeatures.SyncResourceTemplateSpecification;
import io.modelcontextprotocol.server.McpServerFeatures.SyncToolSpecification;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.spec.McpSchema.ServerCapabilities;
import lombok.AllArgsConstructor;

/**
 * Builds a live {@link GeboMcpServerInstance} from a
 * {@link GeboMCPServerConfig}.
 * <p>
 * For each configuration this wires a
 * {@link WebMvcStreamableServerTransportProvider} bound to
 * {@code /mcp/<exportedUniqueRelativeUrl>}, builds a synchronous MCP server
 * populated with the tools/resources/prompts produced by the capability
 * providers, and wraps the provider's {@link RouterFunction} in an
 * access-control filter that rejects callers who are not granted on the
 * configuration (HTTP 403).
 */
@Service
@AllArgsConstructor
public class GeboMcpServerBuilder {

	private static final String SERVER_VERSION = "1.0.0";
	private static final String ENDPOINT_PREFIX = "/mcp/";

	private final GeboMcpToolsProvider toolsProvider;
	private final GeboMCPAgentAsToolProvider agentToolsProvider;
	private final GeboMCPAgentsNetworkAsToolsProvider agentNetworkAsToolsProvider;
	private final GeboMcpResourcesProvider resourcesProvider;
	private final GeboMcpPromptsProvider promptsProvider;
	private final GeboMcpAccessChecker accessChecker;
	private final GeboMcpSecurityContextSupport securitySupport;

	/**
	 * Builds and starts the MCP server instance for the given configuration.
	 *
	 * @param config the MCP server configuration
	 * @return the live server instance
	 */
	public GeboMcpServerInstance build(GeboMCPServerConfig config) {
		McpJsonMapper jsonMapper = new JacksonMcpJsonMapperSupplier().get();
		String endpoint = ENDPOINT_PREFIX + config.getExportedUniqueRelativeUrl();

		WebMvcStreamableServerTransportProvider provider = WebMvcStreamableServerTransportProvider.builder()
				.jsonMapper(jsonMapper).mcpEndpoint(endpoint).contextExtractor(request -> securitySupport.capture())
				.build();

		List<SyncToolSpecification> tools = new ArrayList<SyncToolSpecification>(toolsProvider.buildTools(config));
		tools.addAll(agentToolsProvider.buildTools(config));
		tools.addAll(agentNetworkAsToolsProvider.buildTools(config));
		List<SyncResourceSpecification> resources = resourcesProvider.buildResources(config);
		List<SyncResourceTemplateSpecification> resourceTemplates = resourcesProvider.buildResourceTemplates(config);
		List<SyncPromptSpecification> prompts = promptsProvider.buildPrompts(config);

		ServerCapabilities capabilities = ServerCapabilities.builder().tools(true).resources(false, true).prompts(true)
				.build();

		McpSyncServer server = McpServer.sync(provider).serverInfo(serverName(config), SERVER_VERSION)
				.capabilities(capabilities).tools(tools).resources(resources).resourceTemplates(resourceTemplates)
				.prompts(prompts).build();

		RouterFunction<ServerResponse> routerFunction = provider.getRouterFunction()
				.filter((request, next) -> accessChecker.canAccessServer(config) ? next.handle(request)
						: ServerResponse.status(403).build());

		return new GeboMcpServerInstance(config, server, routerFunction);
	}

	private static String serverName(GeboMCPServerConfig config) {
		if (config.getDescription() != null && !config.getDescription().isBlank()) {
			return config.getDescription();
		}
		return config.getExportedUniqueRelativeUrl();
	}
}
