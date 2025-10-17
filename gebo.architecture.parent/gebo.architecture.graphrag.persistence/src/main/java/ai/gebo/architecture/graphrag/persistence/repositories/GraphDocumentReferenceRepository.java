package ai.gebo.architecture.graphrag.persistence.repositories;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import ai.gebo.architecture.graphrag.persistence.model.GraphDocumentReference;
@ConditionalOnProperty(prefix = "ai.gebo.neo4j", name = "enabled", havingValue = "true")
public interface GraphDocumentReferenceRepository extends Neo4jRepository<GraphDocumentReference, String> {

}
