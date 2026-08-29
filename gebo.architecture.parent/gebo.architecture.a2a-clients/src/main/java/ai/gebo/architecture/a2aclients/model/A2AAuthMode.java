package ai.gebo.architecture.a2aclients.model;

/**
 * How the a2a-clients connector authenticates outbound calls to a remote A2A
 * agent. Mirrors the MCP client's {@code McpAuthMode} so the same credential
 * services ({@code IGeboSecretsAccessService}, {@code IGOauth2AccessTokenService},
 * {@code IGOauth2RuntimeConfigurationDao}) resolve the {@code Authorization}
 * header the same way.
 * <p>
 * The modes split into two identity semantics:
 * <ul>
 * <li><b>Service identity</b> ({@link #API_KEY}, {@link #STATIC_BEARER_TOKEN},
 * {@link #OAUTH2_CLIENT_CREDENTIALS}): the remote authenticates Gebo as a single
 * service principal; the local user is not propagated.</li>
 * <li><b>Per-user delegation</b> ({@link #OAUTH2_AUTHORIZATION_CODE_PER_USER},
 * {@link #USER_TOKEN_RELAY}, {@link #TOKEN_EXCHANGE}): the local user's own (or an
 * exchanged) token is relayed, so the remote sees the actual local user.</li>
 * </ul>
 * For the OAuth2/user-relay modes a stored authorized-client token is used when a
 * {@code secretCode} is configured; when {@code secretCode} is left null the connector
 * falls back to <b>token exchange</b>, and {@link #TOKEN_EXCHANGE} always does. Token
 * exchange follows the platform's active auth mode: in <b>OAuth2 resource-server</b>
 * mode the actual inbound OAuth2 bearer is relayed as-is (the platform is not the
 * issuer); in <b>self-issued JWT / API-key</b> mode a fresh short-lived JWT is minted
 * for the current user. Either way the remote sees the caller's actual identity.
 */
public enum A2AAuthMode {
	NONE, API_KEY, STATIC_BEARER_TOKEN, OAUTH2_CLIENT_CREDENTIALS, OAUTH2_AUTHORIZATION_CODE_PER_USER, USER_TOKEN_RELAY,
	TOKEN_EXCHANGE
}
