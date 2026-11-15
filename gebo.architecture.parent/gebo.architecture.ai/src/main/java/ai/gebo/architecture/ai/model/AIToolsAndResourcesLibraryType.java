package ai.gebo.architecture.ai.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AIToolsAndResourcesLibraryType {
	@NotNull
	private String id, description;
	private Map<String, Object> metaData = new HashMap<>();
	@NotNull
	private List<AIResourceType> resourcesTypes = null;
	@NotNull
	private List<ToolReference> tools = null;
	/**
	 * What an agent's model must be told when a run holds this library, for the rules
	 * of its prompt that the tools change; null when there is nothing to tell. It is
	 * for the model: the API describing the library to the admin leaves it out.
	 */
	@JsonIgnore
	private String agentRules = null;
}
