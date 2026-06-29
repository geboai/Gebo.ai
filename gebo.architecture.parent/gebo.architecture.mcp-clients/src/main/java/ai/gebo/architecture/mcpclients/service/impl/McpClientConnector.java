/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpclients.service.impl;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.mcpclients.model.MCPClientConfig;
import ai.gebo.architecture.mcpclients.model.MCPTransportType;
import ai.gebo.architecture.mcpclients.model.McpAuthMode;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboCustomSecretContent;
import ai.gebo.secrets.model.GeboOauth2TokenSecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGOauth2AccessTokenService;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.services.IGOauth2RuntimeConfigurationDao;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.client.transport.HttpClientStreamableHttpTransport;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import io.modelcontextprotocol.client.transport.customizer.McpSyncHttpClientRequestCustomizer;
import io.modelcontextprotocol.json.McpJsonDefaults;
import io.modelcontextprotocol.spec.McpClientTransport;
import lombok.AllArgsConstructor;

/**
 * Builds and connects a {@link McpSyncClient} from a {@link MCPClientConfig}.
 * <p>
 * This component encapsulates everything that depends on the MCP SDK and on the
 * platform credential services: it selects the right client transport for the
 * configured {@link MCPTransportType} and resolves the {@code Authorization}
 * header (or process credentials) for the configured {@link McpAuthMode}, reusing
 * the existing {@link IGeboSecretsAccessService}, {@link IGOauth2AccessTokenService}
 * and {@link IGOauth2RuntimeConfigurationDao}. Keeping it separate lets the
 * management service stay focused on discovery/CRUD orchestration and lets the
 * {@code MCPToolsExporter} reuse the same connection logic later on.
 */
@Service
@AllArgsConstructor
public class McpClientConnector {

	private static final Logger LOGGER = LoggerFactory.getLogger(McpClientConnector.class);

	private static final Duration DEFAULT_REQUEST_TIMEOUT = Duration.ofSeconds(30);
	private static final Duration DEFAULT_INITIALIZATION_TIMEOUT = Duration.ofSeconds(30);
	private static final String DEFAULT_MCP_ENDPOINT = "/mcp";

	private final IGeboSecretsAccessService secretsAccessService;
	private final IGOauth2AccessTokenService oauth2AccessTokenService;
	private final IGOauth2RuntimeConfigurationDao oauth2ConfigDao;

	/**
	 * Builds a fully initialized synchronous MCP client for the given configuration.
	 * The caller owns the returned client and is responsible for closing it (see
	 * {@link #closeQuietly(McpSyncClient)}).
	 *
	 * @param config the MCP client configuration
	 * @return an initialized {@link McpSyncClient}
	 * @throws Exception if the transport cannot be built, credentials cannot be
	 *                   resolved, or the handshake fails
	 */
	public McpSyncClient connectAndInitialize(MCPClientConfig config) throws Exception {
		McpClientTransport transport = buildTransport(config);
		McpSyncClient client = McpClient.sync(transport).requestTimeout(DEFAULT_REQUEST_TIMEOUT)
				.initializationTimeout(DEFAULT_INITIALIZATION_TIMEOUT).build();
		client.initialize();
		return client;
	}

	/**
	 * Closes the given client, swallowing and logging any error so that cleanup
	 * never masks the original outcome.
	 */
	public void closeQuietly(McpSyncClient client) {
		if (client == null) {
			return;
		}
		try {
			client.closeGracefully();
		} catch (Throwable t) {
			LOGGER.warn("Error while closing MCP client gracefully, forcing close", t);
			try {
				client.close();
			} catch (Throwable t2) {
				LOGGER.warn("Error while force-closing MCP client", t2);
			}
		}
	}

	private McpClientTransport buildTransport(MCPClientConfig config) throws Exception {
		MCPTransportType transportType = config.getTransportType();
		if (transportType == null) {
			throw new IllegalArgumentException("transportType is required to connect to an MCP server");
		}
		switch (transportType) {
		case STREAMABLE_HTTP:
			return buildStreamableHttpTransport(config);
		case SSE_LEGACY:
			return buildSseTransport(config);
		case STDIO:
			return buildStdioTransport(config);
		default:
			throw new UnsupportedOperationException("Unsupported MCP transport type: " + transportType);
		}
	}

	private McpClientTransport buildStreamableHttpTransport(MCPClientConfig config) throws Exception {
		requireBaseUrl(config);
		HttpClientStreamableHttpTransport.Builder builder = HttpClientStreamableHttpTransport
				.builder(config.getBaseUrl());
		String endpoint = config.getMcpEndpoint() != null ? config.getMcpEndpoint() : DEFAULT_MCP_ENDPOINT;
		builder.endpoint(endpoint);
		McpSyncHttpClientRequestCustomizer customizer = buildAuthorizationCustomizer(config);
		if (customizer != null) {
			builder.httpRequestCustomizer(customizer);
		}
		return builder.build();
	}

	private McpClientTransport buildSseTransport(MCPClientConfig config) throws Exception {
		requireBaseUrl(config);
		HttpClientSseClientTransport.Builder builder = HttpClientSseClientTransport.builder(config.getBaseUrl());
		if (config.getSseEndpoint() != null) {
			builder.sseEndpoint(config.getSseEndpoint());
		}
		McpSyncHttpClientRequestCustomizer customizer = buildAuthorizationCustomizer(config);
		if (customizer != null) {
			builder.httpRequestCustomizer(customizer);
		}
		return builder.build();
	}

	private McpClientTransport buildStdioTransport(MCPClientConfig config) {
		if (isBlank(config.getStdioCommand())) {
			throw new IllegalArgumentException("stdioCommand is required for the STDIO transport");
		}
		ServerParameters.Builder paramsBuilder = ServerParameters.builder(config.getStdioCommand());
		if (config.getStdioArgs() != null && !config.getStdioArgs().isEmpty()) {
			paramsBuilder.args(config.getStdioArgs());
		}
		if (config.getStdioEnvironment() != null && !config.getStdioEnvironment().isEmpty()) {
			paramsBuilder.env(config.getStdioEnvironment());
		}
		return new StdioClientTransport(paramsBuilder.build(), McpJsonDefaults.getMapper());
	}

	/**
	 * Resolves the value of the {@code Authorization} header for the configured auth
	 * mode and returns a request customizer that adds it to every HTTP request, or
	 * {@code null} when no authorization is required ({@link McpAuthMode#NONE}).
	 */
	private McpSyncHttpClientRequestCustomizer buildAuthorizationCustomizer(MCPClientConfig config) throws Exception {
		final String authorizationHeader = resolveAuthorizationHeader(config);
		if (authorizationHeader == null) {
			return null;
		}
		return (requestBuilder, method, endpoint, body, context) -> requestBuilder.header("Authorization",
				authorizationHeader);
	}

	/**
	 * Resolves the {@code Authorization} header value for every supported auth mode.
	 */
	private String resolveAuthorizationHeader(MCPClientConfig config) throws Exception {
		McpAuthMode authMode = config.getAuthMode();
		if (authMode == null || authMode == McpAuthMode.NONE) {
			return null;
		}
		switch (authMode) {
		case API_KEY:
		case STATIC_BEARER_TOKEN:
			// A statically stored credential (API key or long lived bearer token)
			// referenced by secretCode is sent verbatim as a bearer token.
			return "Bearer " + resolveStaticSecretToken(config, authMode);
		case OAUTH2_CLIENT_CREDENTIALS:
			// Machine-to-machine: obtain a fresh access token from the OAuth2
			// configuration referenced by oauth2AuthenticatorCode.
			return "Bearer " + resolveClientCredentialsToken(config);
		case OAUTH2_AUTHORIZATION_CODE_PER_USER:
		case USER_TOKEN_RELAY:
			// The user has already authorized the downstream provider; the resulting
			// authorized-client token is stored as an OAuth2 token secret and relayed.
			return "Bearer " + resolveStoredOauthAccessToken(config, authMode);
		case TOKEN_EXCHANGE:
			// RFC 8693 token exchange has no platform endpoint wired yet; fail with a
			// precise, actionable message rather than silently sending no credentials.
			throw new UnsupportedOperationException(
					"authMode TOKEN_EXCHANGE is not yet supported for MCP clients (no token-exchange endpoint is "
							+ "configured on the platform)");
		default:
			throw new UnsupportedOperationException("Unsupported MCP auth mode: " + authMode);
		}
	}

	private String resolveStaticSecretToken(MCPClientConfig config, McpAuthMode authMode) throws Exception {
		if (isBlank(config.getSecretCode())) {
			throw new IllegalStateException("authMode " + authMode + " requires a secretCode pointing to a GeboSecret");
		}
		AbstractGeboSecretContent content = secretsAccessService.getSecretContentById(config.getSecretCode());
		if (content == null) {
			throw new IllegalStateException("Secret '" + config.getSecretCode() + "' could not be resolved");
		}
		if (content instanceof GeboTokenContent tokenContent) {
			return tokenContent.getToken();
		}
		if (content instanceof GeboCustomSecretContent customContent) {
			return customContent.getContent();
		}
		if (content instanceof GeboOauth2TokenSecretContent oauthToken && oauthToken.getAccessToken() != null) {
			return oauthToken.getAccessToken().getTokenValue();
		}
		throw new IllegalStateException("Secret '" + config.getSecretCode() + "' of type " + content.type()
				+ " cannot be used as an API key / bearer token");
	}

	private String resolveStoredOauthAccessToken(MCPClientConfig config, McpAuthMode authMode) throws Exception {
		if (isBlank(config.getSecretCode())) {
			throw new IllegalStateException("authMode " + authMode
					+ " requires a secretCode pointing to a stored OAuth2 authorized-client token");
		}
		AbstractGeboSecretContent content = secretsAccessService.getSecretContentById(config.getSecretCode());
		if (content instanceof GeboOauth2TokenSecretContent oauthToken && oauthToken.getAccessToken() != null) {
			return oauthToken.getAccessToken().getTokenValue();
		}
		throw new IllegalStateException("authMode " + authMode + " requires secretCode '" + config.getSecretCode()
				+ "' to reference an OAuth2 authorized-client token (GeboOauth2TokenSecretContent) with an access token");
	}

	private String resolveClientCredentialsToken(MCPClientConfig config) throws Exception {
		if (isBlank(config.getOauth2AuthenticatorCode())) {
			throw new IllegalStateException(
					"authMode OAUTH2_CLIENT_CREDENTIALS requires an oauth2AuthenticatorCode");
		}
		Oauth2RuntimeConfiguration oauthConfig = oauth2ConfigDao.findByCode(config.getOauth2AuthenticatorCode());
		if (oauthConfig == null) {
			throw new IllegalStateException(
					"OAuth2 configuration '" + config.getOauth2AuthenticatorCode() + "' does not exist");
		}
		String tokenUri = oauthConfig.getProviderConfig() != null ? oauthConfig.getProviderConfig().getTokenUri() : null;
		if (isBlank(tokenUri)) {
			throw new IllegalStateException("OAuth2 configuration '" + config.getOauth2AuthenticatorCode()
					+ "' has no token URI (providerConfig.tokenUri)");
		}
		if (isBlank(oauthConfig.getClientSecretId())) {
			throw new IllegalStateException(
					"OAuth2 configuration '" + config.getOauth2AuthenticatorCode() + "' has no clientSecretId");
		}
		// No scope is modelled on MCPClientConfig yet; the token endpoint is expected
		// to apply a sensible default scope for the client-credentials grant.
		return oauth2AccessTokenService.getAccessToken(tokenUri, null, oauthConfig.getClientSecretId());
	}

	private void requireBaseUrl(MCPClientConfig config) {
		if (isBlank(config.getBaseUrl())) {
			throw new IllegalArgumentException(
					"baseUrl is required for the " + config.getTransportType() + " transport");
		}
	}

	private static boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
}
