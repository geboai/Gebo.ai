package ai.gebo.architecture.mcpclients.model;

import java.util.List;

import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.model.IGObjectWithSecurity;
import lombok.Data;

@Data
public class BaseMCPObject implements IAclGrantedResource, IGObjectWithSecurity {
	// List of group IDs or names that have access to this project.
	private List<String> accessibleGroups = null;

	// List of user IDs or usernames that have access to this project.
	private List<String> accessibleUsers = null;

	// Indicates whether the project is accessible to all users.
	private Boolean accessibleToAll = null;

	private List<Integer> aclAliases = null;

	// Logical identity of the discovered MCP feature (tool/prompt name, resource
	// name). This is the key used by McpClientManagementService.testAndDiscovery to
	// match a remotely discovered feature against the one previously stored on the
	// MCPClientConfig. Richer metadata (description, schema, ...) is intentionally
	// left to be modelled on the concrete subclasses.
	private String name = null;

	private Boolean deletedOnMCPServer = null;
	private Boolean addedOnMCPServer = null;

	@Override
	public String owner() {

		return "system";
	}
}
