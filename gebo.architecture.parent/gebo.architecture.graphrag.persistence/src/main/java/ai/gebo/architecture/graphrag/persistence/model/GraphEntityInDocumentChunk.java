package ai.gebo.architecture.graphrag.persistence.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.Data;

@Node("entity_in_chunk")
@Data
public class GraphEntityInDocumentChunk extends AbstractInDocumentChunkObject {
	@Relationship(type = "discovered_entity", direction = Relationship.Direction.OUTGOING)
	private GraphEntityObject discoveredEntity;
}
