package ai.gebo.architecture.mcpserver.model;

import java.util.List;

import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GeboMCPServerConfig extends GBaseObject implements IGObjectWithSecurity, IAclGrantedResource {
	@NotNull
	private String exportedUniqueRelativeUrl = null;
	// List of group IDs or names that have access to this project.
	private List<String> accessibleGroups = null;

	// List of user IDs or usernames that have access to this project.
	private List<String> accessibleUsers = null;

	// Indicates whether the project is accessible to all users.
	private Boolean accessibleToAll = null;

	private List<Integer> aclAliases = null;

}
