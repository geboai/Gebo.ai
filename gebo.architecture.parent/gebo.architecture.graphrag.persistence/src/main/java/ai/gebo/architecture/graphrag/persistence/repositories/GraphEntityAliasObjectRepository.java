package ai.gebo.architecture.graphrag.persistence.repositories;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import ai.gebo.architecture.graphrag.persistence.model.GraphEntityAliasObject;
@ConditionalOnProperty(prefix = "ai.gebo.neo4j", name = "enabled", havingValue = "true")
public interface GraphEntityAliasObjectRepository extends AbstractGraphObjectRepository<GraphEntityAliasObject> {

	

}
