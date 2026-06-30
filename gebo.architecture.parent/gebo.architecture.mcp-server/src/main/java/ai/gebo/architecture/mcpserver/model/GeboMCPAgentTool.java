package ai.gebo.architecture.mcpserver.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class GeboMCPAgentTool {
	@NotNull
	private String agentConfigCode = null;
	@NotNull
	private String toolName = null;
}
