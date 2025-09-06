package ai.gebo.architecture.graphrag.extraction.model;

import java.util.List;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;
import lombok.Data;

@Data
public class GraphRagExtractionConfig extends GBaseObject {
	private String knowledgeBaseCode = null;
	private String projectCode = null;
	private Boolean defaultConfiguration = null;
	private String extractionPrompt = null;
	private GObjectRef<GProjectEndpoint> endpoint = null;
	private List<GraphObjectType> customEntityTypes = null;
	private List<GraphObjectType> customEventTypes = null;
	private List<GraphObjectType> customRelationTypes = null;
	private GObjectRef<GBaseChatModelConfig> usedModelConfiguration = null;	
}
