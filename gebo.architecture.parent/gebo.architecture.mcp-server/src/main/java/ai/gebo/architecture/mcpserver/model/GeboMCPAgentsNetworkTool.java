package ai.gebo.architecture.mcpserver.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GeboMCPAgentsNetworkTool {
	@NotNull
	private String agentsNetworkCode = null;
	@NotNull
	private String toolName = null;
}
