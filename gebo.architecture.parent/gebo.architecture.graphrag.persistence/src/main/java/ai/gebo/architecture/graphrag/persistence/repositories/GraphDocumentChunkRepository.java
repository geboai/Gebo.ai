package ai.gebo.architecture.graphrag.persistence.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import ai.gebo.architecture.graphrag.persistence.model.GraphDocumentChunk;

public interface GraphDocumentChunkRepository extends Neo4jRepository<GraphDocumentChunk, String> {

}
