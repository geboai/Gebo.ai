package ai.gebo.architecture.graphrag.persistence.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.Data;

@Node("relation_in_chunk")
@Data
public class GraphRelationInDocumentChunk extends AbstractInDocumentChunkObject {
	@Relationship(type = "discovered_relation", direction = Relationship.Direction.OUTGOING)
	private GraphRelationObject discoveredRelation;

}
