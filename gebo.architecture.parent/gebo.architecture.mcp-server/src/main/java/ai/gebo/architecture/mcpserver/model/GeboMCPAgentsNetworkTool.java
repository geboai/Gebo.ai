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
	 * tool, or null for none. With one, every call runs in a conversation of its own,
	 * as a chat with that profile would: the files the network's models generate and
	 * its tools write have a place to be kept, and the tool answers with them beside
	 * its text. Without one the network runs outside any conversation and answers
	 * with text only.
	 */
	private String chatProfileCode = null;
}
