package ai.gebo.architecture.graphrag.persistence.model;

import org.springframework.data.neo4j.core.schema.Relationship;

public class AbstractInDocumentChunkObject extends AbstractGraphObject {
	@Relationship(type = "contained_in", direction = Relationship.Direction.OUTGOING)
	private GraphDocumentChunk documentChunk;
	private Double confidence=null;
	

}
