package ai.gebo.architecture.a2aclients.model;

import java.util.List;

import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A registered external A2A agent that Gebo can call. Persisted in MongoDB and
 * shaped like the MCP client's {@code MCPClientConfig}: it carries the remote
 * endpoint, the transport, the outbound authentication mode, the credential
 * references, and the skills discovered from the remote Agent Card.
 * <p>
 * <b>Admin-gated / secure by default:</b> a remote agent is only mounted as a
 * network participant (through the dynamic supplier that exposes it as an
 * {@code IGNetworkAgentService}) when an admin has registered it and set
 * {@link #enabled} to {@code true}; disabled configs are skipped.
 *
 * @see A2AAuthMode
 * @see A2ATransportType
 * @see A2ARemoteSkill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class A2ARemoteAgentConfig extends GBaseObject implements IAclGrantedResource, IGObjectWithSecurity {

	/**
	 * Full URL of the remote Agent Card, e.g.
	 * {@code https://host:port/.well-known/agent-card.json}. This is the single A2A
	 * discovery input: the card is fetched from here, and the JSON-RPC service endpoint
	 * is taken from the card body ({@code url} / interfaces).
	 */
	@NotNull
	private String agentCardUrl;

	/**
	 * Optional override of the JSON-RPC endpoint for {@code message/send} /
	 * {@code message/stream}. Per the A2A standard the endpoint is normally taken from
	 * the remote Agent Card ({@code url} / interfaces), so this is left null by default
	 * and only set to force a specific endpoint (absolute URL, or a path relative to the
	 * Agent Card URL's origin).
	 */
	private String rpcEndpoint;

	@NotNull
	private A2ATransportType transportType = A2ATransportType.JSONRPC;

	@NotNull
	private A2AAuthMode authMode = A2AAuthMode.NONE;

	/**
	 * Points to a {@code GeboSecret} (API key / static bearer / stored OAuth2 token)
	 * resolved through {@code IGeboSecretsAccessService}. Used by the credential
	 * modes that read a stored secret.
	 */
	private String secretCode;

	/**
	 * Points to an OAuth2 runtime configuration ({@code IGOauth2RuntimeConfigurationDao})
	 * used by the client-credentials / per-user OAuth2 modes.
	 */
	private String oauth2AuthenticatorCode;

	/**
	 * Prefix applied to the remote agent's id when it is surfaced as a local network
	 * agent / capability, keeping remote ids from colliding with local ones.
	 */
	@NotNull
	private String exportingPrefix;

	/**
	 * When false (default) the remote agent is NOT mounted as a runnable network
	 * participant. An admin must explicitly enable it.
	 */
	private Boolean enabled = null;

	/** Skills discovered from the remote Agent Card (populated by testAndDiscovery). */
	private List<A2ARemoteSkill> skills = null;

	// --- security (ACL) fields, same contract as MCPClientConfig ---
	private List<String> accessibleGroups = null;
	private List<String> accessibleUsers = null;
	private Boolean accessibleToAll = null;
	private List<Integer> aclAliases = null;

	@Override
	public String owner() {
		return getUserCreated();
	}
}
