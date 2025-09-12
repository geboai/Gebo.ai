package ai.gebo.architecture.graphrag.persistence.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Node("docreference")
@Data
public class GraphDocumentReference {
	@Id
	@NotNull
	private String code = null;
	@NotNull
	private String projectCode = null;
	@NotNull
	private String knowledgeBaseCode = null;
	@NotNull
	private String projectEndpointClass = null;
	@NotNull
	private String projectEndpointCode = null;
}
