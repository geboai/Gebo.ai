package ai.gebo.architecture.graphrag.persistence.repositories;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import ai.gebo.architecture.graphrag.persistence.model.GraphEntityObject;
@ConditionalOnProperty(prefix = "ai.gebo.neo4j", name = "enabled", havingValue = "true")
public interface GraphEntityObjectRepository extends AbstractGraphObjectRepository<GraphEntityObject> {
	
}
