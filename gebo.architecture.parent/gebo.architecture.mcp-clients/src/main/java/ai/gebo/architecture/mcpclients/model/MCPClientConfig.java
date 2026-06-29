package ai.gebo.architecture.mcpclients.model;

import java.util.List;
import java.util.Map;

import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MCPClientConfig extends GBaseObject implements IAclGrantedResource, IGObjectWithSecurity {
	// Base MCP Server url for example http://localhost:8081 .
	// Required for the HTTP based transports (STREAMABLE_HTTP / SSE_LEGACY); it is
	// not used by the STDIO transport, which spawns a local process instead, so it
	// is no longer globally @NotNull. Per-transport requirements are enforced by
	// McpClientManagementService.
	private String baseUrl;
	// Main endpoint relative url for example /mcp
	private String mcpEndpoint;
	// SSE Version
	private String sseEndpoint;
	// Points to a secret like api key or oauth2 token
	// This can be null and points to a GeboSecret by IGGeboSecretService that can
	// be an API_KEY or other type of credentials
	private String secretCode;
	// Points to an oauth2 domain client the actual user can be autenticated to
	// Points to a oauth2 configuration that can be reached with:
	// IGOauth2RuntimeConfigurationDao
	private String oauth2AuthenticatorCode = null;
	// STDIO transport only: the executable/command to spawn the local MCP server
	// process (for example "npx" or an absolute path to a binary).
	private String stdioCommand = null;
	// STDIO transport only: arguments passed to the stdioCommand.
	private List<String> stdioArgs = null;
	// STDIO transport only: extra environment variables for the spawned process.
	private Map<String, String> stdioEnvironment = null;

	@NotNull
	private MCPTransportType transportType;
	@NotNull
	private McpAuthMode authMode;
	@NotNull
	private String exportingPrefix = null;
	// List of group IDs or names that have access to this project.
	private List<String> accessibleGroups = null;

	// List of user IDs or usernames that have access to this project.
	private List<String> accessibleUsers = null;

	// Indicates whether the project is accessible to all users.
	private Boolean accessibleToAll = null;

	private List<Integer> aclAliases = null;
	private List<MCPTool> tools = null;
	private List<MCPResource> resources = null;
	private List<MCPPrompt> prompts = null;

	@Override
	public String owner() {

		return getUserCreated();
	}
}
