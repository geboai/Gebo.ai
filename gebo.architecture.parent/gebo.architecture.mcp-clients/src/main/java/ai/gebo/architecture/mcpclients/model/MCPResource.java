package ai.gebo.architecture.mcpclients.model;

import java.util.List;

import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.model.IGObjectWithSecurity;
import lombok.Data;

@Data
public class MCPResource extends BaseMCPObject {

	// Canonical URI of the MCP resource. Resources are matched/diffed by uri (not
	// by name, which is not guaranteed unique) during testAndDiscovery.
	private String uri = null;
}