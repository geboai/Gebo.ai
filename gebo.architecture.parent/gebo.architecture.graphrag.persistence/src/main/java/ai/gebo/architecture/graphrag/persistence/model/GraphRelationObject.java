package ai.gebo.architecture.graphrag.persistence.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.Data;

@Node("relation_object")
@Data
public class GraphRelationObject extends AbstractGraphObject {
	@Relationship(type = "fromEntity", direction = Relationship.Direction.OUTGOING)
	private GraphEntityObject fromEntity;
	@Relationship(type = "toEntity", direction = Relationship.Direction.OUTGOING)
	private GraphEntityObject toEntity;
}
