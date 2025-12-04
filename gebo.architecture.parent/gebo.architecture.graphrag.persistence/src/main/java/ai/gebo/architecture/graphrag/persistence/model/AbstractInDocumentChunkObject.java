package ai.gebo.architecture.graphrag.persistence.model;

import java.util.UUID;

import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.Data;
@Data
public class AbstractInDocumentChunkObject extends AbstractGraphObject {
	@Relationship(type = "contained_in", direction = Relationship.Direction.OUTGOING)
	private GraphDocumentChunk documentChunk;
	private Double confidence=null;
	@Override
	public String computeId() {
		
		return UUID.randomUUID().toString();
	}
	

}
