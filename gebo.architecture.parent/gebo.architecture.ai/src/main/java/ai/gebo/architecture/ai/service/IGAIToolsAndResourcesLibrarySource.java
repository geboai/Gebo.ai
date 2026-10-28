package ai.gebo.architecture.ai.service;

import java.util.List;

import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;

public interface IGAIToolsAndResourcesLibrarySource {
	public String getId();

	public String getDescription();
	public List<AIToolsAndResourcesLibraryType> getManagedTypes();
	public AIToolsAndResourcesLibraryInstance getInstanceById(String id, String owner);
	public AIToolsAndResourcesLibraryInstance createInstance(AIToolsAndResourcesLibraryType type, List<String> owners);
	public void dropInstance(AIToolsAndResourcesLibraryInstance instance);

}
