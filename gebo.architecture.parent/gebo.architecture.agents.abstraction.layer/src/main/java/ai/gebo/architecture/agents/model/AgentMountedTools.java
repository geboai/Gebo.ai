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

	/** One tool, with the category of the source that exports it. */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MountedTool {
		private String name;
		private String description;
		private String categoryCode;
		private String categoryDescription;
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
