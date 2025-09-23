package ai.gebo.architecture.graphrag.persistence.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.Data;

@Node("event_alias_chunk")
@Data
public class GraphEventAliasInDocumentChunk extends AbstractInDocumentChunkObject {
	@Relationship(type = "discovered_event_alias", direction = Relationship.Direction.OUTGOING)
	private GraphEventAliasObject discoveredEventAlias;
}
