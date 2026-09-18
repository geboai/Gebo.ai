package ai.gebo.architecture.a2aserver.model;

import java.util.List;

import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A published A2A server: exposes selected Gebo agents / networks as one A2A
 * endpoint (an Agent Card at {@code <url>/.well-known/agent-card.json} plus a
 * JSON-RPC endpoint). Persisted in MongoDB and shaped like the MCP server's
 * {@code GeboMCPServerConfig}.
 * <p>
 * <b>Secure by default:</b> nothing is externally visible or callable unless an
 * admin creates a config, adds agents/networks to {@link #exportedAgents}, and
 * sets {@link #enabled} to {@code true}. The ACL fields additionally gate which
 * (locally resolved) caller may invoke the published endpoint; the inbound
 * credential is authenticated through the platform's existing Spring Security
 * chain and the exported network runs impersonating that local principal.
 *
 * @see A2AExportedAgent
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class A2AServerConfig extends GBaseObject implements IGObjectWithSecurity, IAclGrantedResource {

	/**
	 * Unique relative URL segment the endpoint is served under (e.g. {@code sales});
	 * the Agent Card and JSON-RPC routes are published beneath it.
	 */
	@NotNull
	private String exportedRelativeUrl = null;

	/** When false/null the endpoint is not published. */
	private Boolean enabled = null;

	/** The agents and/or networks exported through this endpoint. */
	private List<A2AExportedAgent> exportedAgents = null;

	/**
	 * The A2A security scheme advertised on the Agent Card. Reflects the platform's
	 * active auth mode (bearer JWT / API key, or OAuth2 resource server); the actual
	 * validation is performed by the existing Spring Security chain.
	 */
	private String securitySchemeName = null;

	// --- security (ACL) fields, same contract as GeboMCPServerConfig ---
	private List<String> accessibleGroups = null;
	private List<String> accessibleUsers = null;
	private Boolean accessibleToAll = null;
	private List<Integer> aclAliases = null;
}
