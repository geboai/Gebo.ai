package ai.gebo.architecture.graphrag.persistence.model;

import org.springframework.data.neo4j.core.schema.Node;

import lombok.Data;
@Node("entity_object")
@Data
public class GraphEntityObject extends AbstractGraphObject  {
	private String name = null;
}
