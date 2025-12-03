package ai.gebo.architecture.graphrag.extraction.model;

import java.util.List;

import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.knlowledgebase.model.contents.GContentSelectionFilter;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Document
@Data

public class GraphRagExtractionConfig extends GBaseObject implements Cloneable{
	public GraphRagExtractionConfig() {
	}

	@HashIndexed
	private String knowledgeBaseCode = null;
	@HashIndexed
	private String projectCode = null;
	@HashIndexed
	private Boolean defaultConfiguration = null;
	private Boolean graphRagAllSources = null;
	@HashIndexed
	private GObjectRef<GProjectEndpoint> endpoint = null;
	private String extractionPrompt = null;
	private List<GraphObjectType> customEntityTypes = null;
	private List<GraphObjectType> customEventTypes = null;
	private List<GraphObjectType> customRelationTypes = null;
	@GObjectReference(referencedType = GBaseChatModelConfig.class, referencesExtensions = true)
	private GObjectRef<GBaseChatModelConfig> usedModelConfiguration = null;
	private GContentSelectionFilter contentSelectionFilter = null;
	private Boolean processEveryDocument = null;
	@NotNull
	private GraphRagExtractionFormat extractionFormat = null;
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
