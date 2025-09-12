package ai.gebo.architecture.graphrag.persistence.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import ai.gebo.architecture.graphrag.persistence.model.GraphDocumentReference;

public interface GraphDocumentReferenceRepository extends Neo4jRepository<GraphDocumentReference, String> {

}
