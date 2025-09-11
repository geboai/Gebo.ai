package ai.gebo.architecture.graphrag.persistence.model;

import java.util.List;

import org.springframework.data.neo4j.core.schema.CompositeProperty;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.Data;

@Node("event_in_chunk")
@Data
public class GraphEventInDocumentChunk extends AbstractInDocumentChunkObject {
	@Relationship(type = "discovered_event", direction = Relationship.Direction.OUTGOING)
	private GraphEventObject discoveredEvent;
	@Relationship(type = "participants", direction = Relationship.Direction.OUTGOING)
	private List<GraphEntityObject> participants;
	private String startDateTime = null;
	private String endDateTime = null;
}
