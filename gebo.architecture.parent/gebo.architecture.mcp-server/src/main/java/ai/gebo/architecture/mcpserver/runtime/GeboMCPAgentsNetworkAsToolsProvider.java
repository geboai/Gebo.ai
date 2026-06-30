package ai.gebo.architecture.mcpserver.runtime;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import io.modelcontextprotocol.server.McpServerFeatures.SyncToolSpecification;
@Service
public class GeboMCPAgentsNetworkAsToolsProvider {
	public List<SyncToolSpecification> buildTools(GeboMCPServerConfig config) {
		return List.of();
	}
}
