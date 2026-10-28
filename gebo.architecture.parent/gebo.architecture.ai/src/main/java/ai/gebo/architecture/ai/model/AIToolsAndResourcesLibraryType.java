package ai.gebo.architecture.ai.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
