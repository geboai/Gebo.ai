package ai.gebo.architecture.agents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A single resource exported by an agent through its {@link AgentCapabilities}
 * descriptor: a catalog (e.g. a knowledge base or a searchable system), a generic
 * resource, or a callable tool/function. It is purely descriptive and is meant to
 * be rendered into the shared network-of-agents description.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentCapabilityResource {
	/** Stable identifier of the resource (e.g. the knowledge base code, the tool name). */
	private String code;
	/** Human readable name (falls back to {@link #code} when rendered). */
	private String name;
	/** Optional longer description of the resource. */
	private String description;

	public static AgentCapabilityResource of(String code, String name, String description) {
		return new AgentCapabilityResource(code, name, description);
	}
}
