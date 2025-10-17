package ai.gebo.architecture.graphrag.persistence.repositories;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import ai.gebo.architecture.graphrag.persistence.model.GraphEventObject;
@ConditionalOnProperty(prefix = "ai.gebo.neo4j", name = "enabled", havingValue = "true")
public interface GraphEventObjectRepository extends AbstractGraphObjectRepository<GraphEventObject>{

	

}
