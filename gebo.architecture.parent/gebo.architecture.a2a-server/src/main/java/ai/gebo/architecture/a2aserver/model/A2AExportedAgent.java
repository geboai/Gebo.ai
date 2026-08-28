package ai.gebo.architecture.a2aserver.model;

import lombok.Data;

/**
 * One entry in an {@link A2AServerConfig}'s export list: an agent or a whole
 * network-of-agents published as a single opaque A2A skill.
 * <p>
 * Both kinds run through the same proven path
 * ({@code IGAgentsNetworkServiceFactory.create(...).executeNetwork()}): a
 * {@link Kind#NETWORK} runs the referenced network directly, while a
 * {@link Kind#AGENT} is wrapped in an ephemeral single-node network (the agent
 * as both input and output node). A single agent has no standalone run path, so
 * the single-node network is how it is executed.
 */
@Data
public class A2AExportedAgent {

	public enum Kind {
		/** Export a single agent config, run via a synthesized single-node network. */
		AGENT,
		/** Export an entire network-of-agents, resolved by code (static or code-generated). */
		NETWORK
	}

	private Kind kind = Kind.NETWORK;

	/**
	 * For {@link Kind#NETWORK}: the network code, resolved via {@code IAgentsNetworkDao}
	 * regardless of whether it is a persisted (Mongo) or code-generated network. The
	 * factory and effective I/O types come from the resolved network, not restated here.
	 */
	private String networkCode;

	/** For {@link Kind#AGENT}: the agent configuration code wrapped in a single-node network. */
	private String agentConfigCode;

	/** The A2A skill id/name this export is advertised as on the Agent Card. */
	private String skillName;

	/**
	 * When true, aggregate the member agents' capabilities into the advertised skill
	 * for richer discovery. Default false keeps the network opaque (member agents are
	 * never individually reachable).
	 */
	private Boolean exposeMemberCapabilities = null;
}
