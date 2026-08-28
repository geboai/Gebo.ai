package ai.gebo.architecture.a2aclients.model;

import java.util.List;

import lombok.Data;

/**
 * A skill discovered on a remote A2A agent's Agent Card, cached on the owning
 * {@link A2ARemoteAgentConfig}. Mirrors the discovery/diff bookkeeping of the MCP
 * client's {@code BaseMCPObject}/{@code MCPTool}: {@code testAndDiscovery} matches
 * a freshly fetched skill against the stored one by {@link #id} and flags whether
 * it was {@link #addedOnRemote added}, {@link #deletedOnRemote removed}, or
 * unchanged.
 */
@Data
public class A2ARemoteSkill {
	/** Stable skill id as advertised in the remote Agent Card. Match key for discovery. */
	private String id = null;
	/** Human readable skill name. */
	private String name = null;
	/** Longer description of what the skill does. */
	private String description = null;
	/** Free-form tags advertised for the skill. */
	private List<String> tags = null;
	/** Advertised input MIME/mode hints (e.g. {@code text/plain}). */
	private List<String> inputModes = null;
	/** Advertised output MIME/mode hints. */
	private List<String> outputModes = null;

	/** Set during discovery when the skill is present remotely but was not stored before. */
	private Boolean addedOnRemote = null;
	/** Set during discovery when the skill was stored but is no longer present remotely. */
	private Boolean deletedOnRemote = null;
}
