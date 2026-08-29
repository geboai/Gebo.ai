package ai.gebo.architecture.a2aclients.service.impl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.a2aclients.model.A2AAuthMode;
import ai.gebo.architecture.a2aclients.model.A2ARemoteAgentConfig;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboCustomSecretContent;
import ai.gebo.secrets.model.GeboOauth2TokenSecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGOauth2AccessTokenService;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.services.IGOauth2RuntimeConfigurationDao;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.impl.LocalJwtTokenProvider;
import ai.gebo.security.config.GeboSecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import ai.gebo.security.model.UserInfos;
import lombok.AllArgsConstructor;
import org.a2aproject.sdk.jsonrpc.common.json.JsonUtil;
import org.a2aproject.sdk.jsonrpc.common.wrappers.SendMessageRequest;
import org.a2aproject.sdk.jsonrpc.common.wrappers.SendMessageResponse;
import org.a2aproject.sdk.jsonrpc.common.wrappers.SendStreamingMessageRequest;
import org.a2aproject.sdk.jsonrpc.common.wrappers.SendStreamingMessageResponse;
import org.a2aproject.sdk.spec.AgentCard;
import org.a2aproject.sdk.spec.EventKind;
import org.a2aproject.sdk.spec.Message;
import org.a2aproject.sdk.spec.MessageSendParams;
import org.a2aproject.sdk.spec.StreamingEventKind;
import org.a2aproject.sdk.spec.TextPart;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Transport + authentication for calling a remote A2A agent. Mirrors the role of
 * {@code McpClientConnector}: the A2A protocol (JSON-RPC 2.0 over HTTP, SSE for
 * {@code message/stream}) is spoken over the JDK {@link HttpClient} using the
 * official A2A SDK wire types and {@link JsonUtil} (Gson) for spec-correct
 * serialization. The JDK client is used deliberately — it avoids pulling the SDK's
 * Vert.x/gRPC client transports and Reactor-Netty into the platform.
 * <p>
 * The {@code Authorization} header is resolved per call for the configured
 * {@link A2AAuthMode}, reusing the platform credential services exactly as the MCP
 * client connector does.
 */
@Service
@AllArgsConstructor
public class A2AClientConnector {

	private static final Logger LOGGER = LoggerFactory.getLogger(A2AClientConnector.class);

	private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(60);
	private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(20);
	private static final String DEFAULT_CARD_PATH = "/.well-known/agent-card.json";
	private static final String DEFAULT_RPC_ENDPOINT = "/";

	/** TTL of the short-lived token minted when relaying the caller's own identity. */
	private static final Duration USER_TOKEN_EXCHANGE_TTL = Duration.ofMinutes(5);

	private final IGeboSecretsAccessService secretsAccessService;
	private final IGOauth2AccessTokenService oauth2AccessTokenService;
	private final IGOauth2RuntimeConfigurationDao oauth2ConfigDao;
	private final IGSecurityService securityService;
	private final LocalJwtTokenProvider jwtTokenProvider;
	private final GeboSecurityConfig securityConfig;

	private HttpClient newHttpClient() {
		// Pin HTTP/1.1: A2A servers are plain HTTP(S)/SSE endpoints, and forcing 1.1
		// avoids the JDK client's h2c-upgrade handshake against servers that only speak
		// HTTP/1.1 (which surfaces as "header parser received no bytes").
		return HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).connectTimeout(CONNECT_TIMEOUT).build();
	}

	// ---------------------------------------------------------------------
	// Agent Card
	// ---------------------------------------------------------------------

	/**
	 * Fetches and parses the remote Agent Card (a plain HTTP GET of the well-known
	 * card document — not a JSON-RPC call). Blocking: used during discovery.
	 *
	 * @throws Exception if the card cannot be fetched or parsed
	 */
	public AgentCard fetchAgentCard(A2ARemoteAgentConfig config) throws Exception {
		requireBaseUrl(config);
		String cardPath = config.getAgentCardPath() != null ? config.getAgentCardPath() : DEFAULT_CARD_PATH;
		String authorization = resolveAuthorizationHeader(config);
		HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(config.getBaseUrl() + cardPath))
				.timeout(REQUEST_TIMEOUT).header("Accept", "application/json").GET();
		applyAuth(builder, authorization);
		HttpResponse<String> response = newHttpClient().send(builder.build(), BodyHandlers.ofString());
		if (response.statusCode() / 100 != 2) {
			throw new IllegalStateException("Agent Card fetch failed with HTTP " + response.statusCode());
		}
		return JsonUtil.fromJson(response.body(), AgentCard.class);
	}

	// ---------------------------------------------------------------------
	// message/send (blocking) and message/stream (SSE)
	// ---------------------------------------------------------------------

	/**
	 * Sends a single text message via {@code message/send} and returns the result
	 * event (a {@code Message} or a terminal {@code Task}). Blocking.
	 *
	 * @throws Exception on transport failure or a JSON-RPC error response
	 */
	public EventKind sendMessage(A2ARemoteAgentConfig config, String text, String contextId) throws Exception {
		requireBaseUrl(config);
		String authorization = resolveAuthorizationHeader(config);
		MessageSendParams params = buildParams(text, contextId);
		SendMessageRequest request = new SendMessageRequest(UUID.randomUUID().toString(), params);
		String requestBody = JsonUtil.toJson(request);
		HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(config.getBaseUrl() + rpcEndpoint(config)))
				.timeout(REQUEST_TIMEOUT).header("Content-Type", "application/json").header("Accept", "application/json")
				.POST(BodyPublishers.ofString(requestBody));
		applyAuth(builder, authorization);
		HttpResponse<String> response = newHttpClient().send(builder.build(), BodyHandlers.ofString());
		if (response.statusCode() / 100 != 2) {
			throw new IllegalStateException("message/send failed with HTTP " + response.statusCode());
		}
		SendMessageResponse parsed = JsonUtil.fromJson(response.body(), SendMessageResponse.class);
		if (parsed.getError() != null) {
			throw new IllegalStateException("Remote A2A agent returned an error: code=" + parsed.getError().getCode()
					+ " message=" + parsed.getError().getMessage());
		}
		return parsed.getResult();
	}

	/**
	 * Opens a {@code message/stream} SSE subscription for a text message and emits
	 * each streaming event as it arrives. The blocking JDK stream is consumed on a
	 * bounded-elastic scheduler so callers get a non-blocking {@link Flux}.
	 */
	public Flux<StreamingEventKind> streamMessage(A2ARemoteAgentConfig config, String text, String contextId) {
		final HttpRequest request;
		try {
			requireBaseUrl(config);
			String authorization = resolveAuthorizationHeader(config);
			MessageSendParams params = buildParams(text, contextId);
			SendStreamingMessageRequest streamRequest = new SendStreamingMessageRequest(UUID.randomUUID().toString(),
					params);
			HttpRequest.Builder builder = HttpRequest.newBuilder()
					.uri(URI.create(config.getBaseUrl() + rpcEndpoint(config))).timeout(REQUEST_TIMEOUT)
					.header("Content-Type", "application/json").header("Accept", "text/event-stream")
					.POST(BodyPublishers.ofString(JsonUtil.toJson(streamRequest)));
			applyAuth(builder, authorization);
			request = builder.build();
		} catch (Exception e) {
			return Flux.error(e);
		}
		return Flux.defer(() -> {
			try {
				HttpResponse<Stream<String>> response = newHttpClient().send(request, BodyHandlers.ofLines());
				return Flux.fromStream(response.body()).handle((line, sink) -> {
					String data = extractSseData(line);
					if (data == null || data.isBlank()) {
						return;
					}
					try {
						SendStreamingMessageResponse parsed = JsonUtil.fromJson(data,
								SendStreamingMessageResponse.class);
						if (parsed.getError() != null) {
							sink.error(new IllegalStateException("A2A stream error: code="
									+ parsed.getError().getCode() + " message=" + parsed.getError().getMessage()));
							return;
						}
						if (parsed.getResult() != null) {
							sink.next(parsed.getResult());
						}
					} catch (Exception ex) {
						sink.error(ex);
					}
				});
			} catch (Exception e) {
				return Flux.<StreamingEventKind>error(e);
			}
		}).subscribeOn(Schedulers.boundedElastic());
	}

	// ---------------------------------------------------------------------
	// helpers
	// ---------------------------------------------------------------------

	/** Extracts the JSON payload from an SSE {@code data:} line (or returns null). */
	private String extractSseData(String line) {
		if (line == null) {
			return null;
		}
		String trimmed = line.stripLeading();
		if (trimmed.startsWith("data:")) {
			return trimmed.substring("data:".length()).trim();
		}
		return null;
	}

	private MessageSendParams buildParams(String text, String contextId) {
		Message.Builder messageBuilder = Message.builder().role(Message.Role.ROLE_USER)
				.messageId(UUID.randomUUID().toString()).parts(new TextPart(text != null ? text : ""));
		if (contextId != null && !contextId.isBlank()) {
			messageBuilder.contextId(contextId);
		}
		return MessageSendParams.builder().message(messageBuilder.build()).build();
	}

	private void applyAuth(HttpRequest.Builder builder, String authorization) {
		if (authorization != null) {
			builder.header("Authorization", authorization);
		}
	}

	private String rpcEndpoint(A2ARemoteAgentConfig config) {
		return config.getRpcEndpoint() != null ? config.getRpcEndpoint() : DEFAULT_RPC_ENDPOINT;
	}

	/**
	 * Resolves the {@code Authorization} header value for the configured auth mode,
	 * or {@code null} when no authorization is required. Ported from
	 * {@code McpClientConnector} so both protocols resolve credentials identically.
	 */
	private String resolveAuthorizationHeader(A2ARemoteAgentConfig config) throws Exception {
		A2AAuthMode authMode = config.getAuthMode();
		if (authMode == null || authMode == A2AAuthMode.NONE) {
			return null;
		}
		switch (authMode) {
		case API_KEY:
		case STATIC_BEARER_TOKEN:
			return "Bearer " + resolveStaticSecretToken(config, authMode);
		case OAUTH2_CLIENT_CREDENTIALS:
			return "Bearer " + resolveClientCredentialsToken(config);
		case OAUTH2_AUTHORIZATION_CODE_PER_USER:
		case USER_TOKEN_RELAY:
			// Per-user delegation: relay the stored authorized-client token when a
			// secretCode is configured; otherwise fall back to token exchange and relay
			// the caller's own live identity.
			if (isBlank(config.getSecretCode())) {
				return "Bearer " + exchangeCurrentUserToken();
			}
			return "Bearer " + resolveStoredOauthAccessToken(config, authMode);
		case TOKEN_EXCHANGE:
			// Always relay the caller's live identity.
			return "Bearer " + exchangeCurrentUserToken();
		default:
			throw new UnsupportedOperationException("Unsupported A2A auth mode: " + authMode);
		}
	}

	private String resolveStaticSecretToken(A2ARemoteAgentConfig config, A2AAuthMode authMode) throws Exception {
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

	private String resolveStoredOauthAccessToken(A2ARemoteAgentConfig config, A2AAuthMode authMode) throws Exception {
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

	private String resolveClientCredentialsToken(A2ARemoteAgentConfig config) throws Exception {
		if (isBlank(config.getOauth2AuthenticatorCode())) {
			throw new IllegalStateException("authMode OAUTH2_CLIENT_CREDENTIALS requires an oauth2AuthenticatorCode");
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
		return oauth2AccessTokenService.getAccessToken(tokenUri, null, oauthConfig.getClientSecretId());
	}

	/**
	 * Token exchange: relays the caller's own identity to the remote A2A agent instead
	 * of a service credential. The mechanism follows the platform's active auth mode:
	 * <ul>
	 * <li><b>OAuth2 resource-server mode</b> ({@code ai.gebo.security.oauth2ResourceServerEnabled}):
	 * the platform is not the token issuer, so the actual inbound OAuth2 bearer token
	 * (the one the user authenticated with) is relayed as-is.</li>
	 * <li><b>Self-issued JWT / API-key mode</b>: the platform is the issuer, so a fresh
	 * short-lived JWT is minted for the current user via {@link LocalJwtTokenProvider}
	 * (the same primitive the API-key mechanism uses).</li>
	 * </ul>
	 * The network runs under the invoking user's {@code runAs}, so the current security
	 * context here is that user; a Gebo A2A server validates the token through the same
	 * chain and runs the exported network impersonating that same user.
	 */
	private String exchangeCurrentUserToken() {
		if (isOauth2ResourceServerMode()) {
			String relayed = currentResourceServerToken();
			if (isBlank(relayed)) {
				throw new IllegalStateException(
						"OAuth2 resource-server mode: no bearer token in the current context to relay to the remote A2A agent");
			}
			return relayed;
		}
		UserInfos user = securityService.getCurrentUser();
		if (user == null || isBlank(user.getUsername())) {
			throw new IllegalStateException(
					"Cannot relay user authentication: no authenticated user in the current context");
		}
		Date expiration = new Date(System.currentTimeMillis() + USER_TOKEN_EXCHANGE_TTL.toMillis());
		return jwtTokenProvider.createToken(user.getUsername(), expiration);
	}

	private boolean isOauth2ResourceServerMode() {
		return securityConfig != null && Boolean.TRUE.equals(securityConfig.getOauth2ResourceServerEnabled());
	}

	/**
	 * Extracts the raw inbound OAuth2 token value from the current authentication.
	 * Covers both JWT and opaque-token resource-server authentications (both extend
	 * {@code AbstractOAuth2TokenAuthenticationToken}).
	 */
	private String currentResourceServerToken() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth instanceof AbstractOAuth2TokenAuthenticationToken<?> tokenAuth && tokenAuth.getToken() != null) {
			return tokenAuth.getToken().getTokenValue();
		}
		return null;
	}

	private void requireBaseUrl(A2ARemoteAgentConfig config) {
		if (isBlank(config.getBaseUrl())) {
			throw new IllegalArgumentException("baseUrl is required to reach an A2A agent");
		}
	}

	private static boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
}
