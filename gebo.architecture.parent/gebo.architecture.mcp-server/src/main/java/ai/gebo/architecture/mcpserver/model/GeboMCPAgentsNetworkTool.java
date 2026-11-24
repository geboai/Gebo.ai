package ai.gebo.architecture.mcpserver.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GeboMCPAgentsNetworkTool {
	@NotNull
	private String agentsNetworkCode = null;
	@NotNull
	private String toolName = null;
	/**
	 * The chat profile whose environment the network runs in when called as this
	 * tool; left empty it is the default chat profile of the installation. Every
	 * call runs in a conversation of its own, as a chat with that profile would: the
	 * files the network's models generate and its tools write have a place to be
	 * kept, and the tool answers with them beside its text.
	 */
	private String chatProfileCode = null;
}
