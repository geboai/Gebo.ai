package ai.gebo.architecture.graphrag.persistence.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.Data;

@Node("entity_alias_chunk")
@Data
public class GraphEntityAliasInDocumentChunk extends AbstractInDocumentChunkObject {
	@Relationship(type = "discovered_entity_alias", direction = Relationship.Direction.OUTGOING)
	private GraphEntityAliasObject discoveredEntityAlias;
}
