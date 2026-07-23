package ai.gebo.architecture.mcpclients.model;

import java.util.List;

import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.model.IGObjectWithSecurity;
import lombok.Data;

@Data
public class MCPTool  extends BaseMCPObject {

	// Human/LLM readable description of what the tool does, as advertised by the
	// MCP server. Surfaced verbatim in the exported ToolCallback definition.
	private String description = null;

	// JSON Schema (serialized) describing the tool's input arguments, as advertised
	// by the MCP server. Used as the inputSchema of the exported ToolCallback so the
	// LLM knows how to call it.
	private String inputSchema = null;
}