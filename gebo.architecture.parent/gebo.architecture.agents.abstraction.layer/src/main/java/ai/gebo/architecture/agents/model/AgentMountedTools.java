package ai.gebo.architecture.agents.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The tools an agent configuration mounts on its model, as the agent mounts them
 * when it runs: either every registered tool but the ones kept out of automatic
 * mounting ({@link MountMode#AUTO}), or exactly the tools the configuration
 * selects ({@link MountMode#SELECTED}). The tools made for a single execution
 * (such as {@code notifyUser}) are not part of it. Read by the network of agents
 * editor to show the tools of each agent.
 */
@Data
@NoArgsConstructor
public class AgentMountedTools {
	public static enum MountMode {
		/** every registered tool, but the ones kept out of automatic mounting */
		AUTO,
		/** exactly the tools the configuration selects */
		SELECTED
	}

	/** Why automatic mounting leaves a tool out for an agent. */
	public static enum Exclusion {
		/** the agent configuration leaves out the tool's source ({@code GAgentConfig.excludedToolSources}) */
		AGENT_CONFIGURATION,
		/** the system settings keep it out ({@code ai.gebo.agents.tools.auto-mounting}) */
		SYSTEM_SETTINGS,
		/** the agent service's own rule leaves it out (e.g. a free chat without the knowledge base tools) */
		AGENT_SERVICE
	}

	/** One tool, with the source that exports it and the category it is listed under. */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MountedTool {
		private String name;
		private String description;
		private String categoryCode;
		private String categoryDescription;
		/** The id of the source exporting the tool. */
		private String sourceId;
		/** What the source is, for the administrator. */
		private String sourceDescription;
		/** Why the tool is left out, for the excluded tools; null for the mounted ones. */
		private Exclusion exclusion;

		public MountedTool(String name, String description, String categoryCode, String categoryDescription) {
			this(name, description, categoryCode, categoryDescription, null, null, null);
		}
	}

	private String agentConfigCode;
	private String agentServiceId;
	private MountMode mountMode;
	/** the tools mounted on the agent model */
	private List<MountedTool> tools = new ArrayList<>();
	/**
	 * with {@link MountMode#AUTO}, the registered tools automatic mounting leaves
	 * out for this agent
	 */
	private List<MountedTool> excludedTools = new ArrayList<>();
}
