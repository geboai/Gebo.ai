package ai.gebo.architecture.ai.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class AIToolsAndResourcesLibraryInstance {
	private AIToolsAndResourcesLibraryType libraryType = null;
	private String id, description;
	private Map<String, Object> metaData = null;
	private List<ToolReference> tools = null;
	private List<AIResource> resources = null;
	private List<String> ownersId = new ArrayList<>();
}
