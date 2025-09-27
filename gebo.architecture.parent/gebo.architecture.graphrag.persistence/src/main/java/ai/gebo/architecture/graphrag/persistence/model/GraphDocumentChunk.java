package ai.gebo.architecture.graphrag.persistence.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.neo4j.core.schema.CompositeProperty;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Node("document_chunk")
@Data
public class GraphDocumentChunk {
	@Id
	@NotNull
	private String id = null;
	@NotNull
	private String documentCode = null;
	@NotNull
	private String projectCode = null;
	@NotNull
	private String knowledgeBaseCode = null;
	@NotNull
	private String projectEndpointClass = null;
	@NotNull
	private String projectEndpointCode = null;
	@NotNull
	private String text = null;
	@Relationship(type = "chunk_of", direction = Relationship.Direction.OUTGOING)
	private GraphDocumentReference chunkOf = null;
	@CompositeProperty(prefix = "metaData")
	private Map<String, Object> metaData = new HashMap<String, Object>();
}
