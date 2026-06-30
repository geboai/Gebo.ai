package ai.gebo.architecture.mcpserver.model;

import java.util.List;

import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GeboMCPServerConfig extends GBaseObject implements IGObjectWithSecurity, IAclGrantedResource {
	@NotNull
	private String exportedUniqueRelativeUrl = null;

	// Whether this MCP server is published/served. When false the endpoint is not
	// exposed.
	private Boolean enabled = null;

	// List of group IDs or names that have access to this project.
	private List<String> accessibleGroups = null;

	// List of user IDs or usernames that have access to this project.
	private List<String> accessibleUsers = null;

	// Indicates whether the project is accessible to all users.
	private Boolean accessibleToAll = null;

	private List<Integer> aclAliases = null;

	private List<String> enabledTools = null;
	private List<String> exportedKnowledgeBasesAsResources = null;
	private List<String> exportedProjectsAsResources = null;
	private List<GObjectRef<GProjectEndpoint>> exportedProjectEndpoints;
	// Codes of the internal prompt templates (GPromptTemplateConfig) exported as
	// MCP prompts.
	private List<String> exportedPrompts = null;
	private List<GeboMCPAgentTool> agentAsTools = null;
	private List<GeboMCPAgentsNetworkTool> agentNetworkAsTools = null;
}
