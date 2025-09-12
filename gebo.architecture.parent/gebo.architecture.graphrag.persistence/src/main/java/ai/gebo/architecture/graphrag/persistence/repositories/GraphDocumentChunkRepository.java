package ai.gebo.architecture.graphrag.persistence.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import ai.gebo.architecture.graphrag.persistence.model.ChunkMeta;
import ai.gebo.architecture.graphrag.persistence.model.ChunkNeighborRow;
import ai.gebo.architecture.graphrag.persistence.model.GraphDocumentChunk;
import jakarta.annotation.Nullable;

public interface GraphDocumentChunkRepository extends Neo4jRepository<GraphDocumentChunk, String> {
	public void deleteByDocumentCode(String code);

	@Query("""
			// trova i chunk che contengono entity_alias o event_alias connessi in 1-hop
			// ai canonical anchor (entity_object / event_object) richiesti
			WITH $entityIds AS eids, $eventIds AS evIds
			MATCH (c:document_chunk)<-[:in_chunk]-(aic)-[:discovered_entity_alias|:discovered_event_alias]->(a)
			WHERE
			  (
			    size(eids) > 0 AND
			    EXISTS {
			      MATCH (a)-[:alias_of|:referred_on]->(canon)
			      WHERE (canon:entity_object AND canon.id IN eids)
			    }
			  ) OR
			  (
			    size(evIds) > 0 AND
			    EXISTS {
			      MATCH (a)-[:alias_of|:referred_on]->(canon)
			      WHERE (canon:event_object AND canon.id IN evIds)
			    }
			  )
			MATCH (c)-[:of_document]->(dr:document_reference)
			WHERE ($kbCodes IS NULL OR dr.knowledgebase_code IN $kbCodes)
			RETURN c.id AS chunkId,
			       dr.id AS documentReferenceId,
			       count(*) AS neighborCount
			""")
	List<ChunkNeighborRow> findChunksNearAnchors1Hop(@Param("entityIds") Collection<String> entityIds,
			@Param("eventIds") Collection<String> eventIds, @Param("kbCodes") @Nullable Collection<String> kbCodes);

	@Query("""
			MATCH (c:document_chunk)-[:of_document]->(dr:document_reference)
			WHERE c.id IN $chunkIds
			RETURN c.id AS chunkId,
			       dr.id AS documentReferenceId,
			       dr.knowledgebase_code AS knowledgebaseCode,
			       c.token_count AS tokenCount,
			       dr.epoch_millis AS documentEpochMillis
			""")
	List<ChunkMeta> fetchChunkMetas(@Param("chunkIds") Collection<String> chunkIds);
}
