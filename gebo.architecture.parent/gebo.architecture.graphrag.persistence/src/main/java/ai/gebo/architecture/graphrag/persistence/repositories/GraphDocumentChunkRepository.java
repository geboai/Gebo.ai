package ai.gebo.architecture.graphrag.persistence.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import ai.gebo.architecture.graphrag.persistence.model.ChunkMeta;
import ai.gebo.architecture.graphrag.persistence.model.ChunkNeighborRow;
import ai.gebo.architecture.graphrag.persistence.model.GraphDocumentChunk;
import jakarta.annotation.Nullable;
@ConditionalOnProperty(prefix = "ai.gebo.neo4j", name = "enabled", havingValue = "true")
public interface GraphDocumentChunkRepository extends Neo4jRepository<GraphDocumentChunk, String> {
	public void deleteByDocumentCode(String code);

	// trova i chunk che contengono entity_alias o event_alias connessi in 1-hop
	// ai canonical anchor (entity_object / event_object) richiesti
	@Query("""
			WITH $entityIds AS eids, $eventIds AS evIds
			MATCH (c:document_chunk)<-[:contained_in]-(aic)-[:discovered_entity_alias|:discovered_event_alias]->(a)
			WHERE (
			  size(eids) > 0 AND EXISTS {
			    MATCH (a)-[:alias_of|:referred_on]->(canon:entity_object)
			    WHERE canon.id IN eids
			  }
			) OR (
			  size(evIds) > 0 AND EXISTS {
			    MATCH (a)-[:alias_of|:referred_on]->(canon:event_object)
			    WHERE canon.id IN evIds
			  }
			)
			MATCH (c)-[:chunk_of]->(dr:document_reference)
			WHERE $kbCodes IS NULL OR dr.knowledgeBaseCode IN $kbCodes
			RETURN c.id AS chunkId,
       dr.code AS documentReferenceId,
       count(*) AS neighborCount
			""")
	List<ChunkNeighborRow> findChunksNearAnchors1Hop(@Param("entityIds") Collection<String> entityIds,
			@Param("eventIds") Collection<String> eventIds, @Param("kbCodes") @Nullable Collection<String> kbCodes);

	@Query("""
			MATCH (c:document_chunk)-[:chunk_of]->(dr:document_reference)
			WHERE c.id IN $chunkIds
			RETURN c.id AS chunkId,
		       dr.code AS documentReferenceId,
		       dr.knowledgeBaseCode AS knowledgebaseCode
			""")
	List<ChunkMeta> fetchChunkMetas(@Param("chunkIds") Collection<String> chunkIds);
}
