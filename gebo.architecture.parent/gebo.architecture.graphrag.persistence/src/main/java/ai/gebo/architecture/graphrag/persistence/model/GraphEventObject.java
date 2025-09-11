package ai.gebo.architecture.graphrag.persistence.model;

import org.springframework.data.neo4j.core.schema.Node;

import lombok.Data;
@Node("event_object")
@Data
public class GraphEventObject extends AbstractGraphObject {
	private String title = null;	
}
