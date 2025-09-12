package ai.gebo.architecture.graphrag.persistence.model;

import org.springframework.data.neo4j.core.schema.Node;

import lombok.Data;

@Node("event_alias")
@Data
public class GraphEventAliasObject extends AbstractGraphAliasObject<GraphEventObject> {

}
