package ai.gebo.architecture.graphrag.persistence.repositories;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import ai.gebo.architecture.graphrag.persistence.model.GraphEventAliasObject;
@ConditionalOnProperty(prefix = "ai.gebo.neo4j", name = "enabled", havingValue = "true")
public interface GraphEventAliasObjectRepository extends AbstractGraphObjectRepository<GraphEventAliasObject> {

	

}
