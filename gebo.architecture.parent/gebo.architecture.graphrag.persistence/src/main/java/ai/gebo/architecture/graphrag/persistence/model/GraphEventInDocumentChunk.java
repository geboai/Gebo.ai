package ai.gebo.architecture.graphrag.persistence.model;

import org.springframework.data.neo4j.core.schema.CompositeProperty;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.Data;

@Node("event_in_chunk")
@Data
public class GraphEventInDocumentChunk extends AbstractInDocumentChunkObject {
	@Relationship(type = "discovered_event", direction = Relationship.Direction.OUTGOING)
	private GraphEventObject discoveredEvent;
	private TimeSegment eventTime = null;
}
