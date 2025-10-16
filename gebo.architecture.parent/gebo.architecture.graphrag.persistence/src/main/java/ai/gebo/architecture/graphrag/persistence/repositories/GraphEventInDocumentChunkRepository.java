package ai.gebo.architecture.graphrag.persistence.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import ai.gebo.architecture.graphrag.persistence.model.ChunkHitRow;
import ai.gebo.architecture.graphrag.persistence.model.GraphEventInDocumentChunk;
import jakarta.annotation.Nullable;

public interface GraphEventInDocumentChunkRepository
		extends AbstractInDocumentChunkObjectRepository<GraphEventInDocumentChunk> {
	@Query("""
			MATCH (c:document_chunk)<-[:contained_in]-(eaic:event_in_chunk)-[:discovered_event]->(ea:event_object)
			MATCH (c)-[:chunk_of]->(dr:document_reference)
			WHERE ea.id IN $eventIds
			AND ($kbCodes IS NULL OR dr.knowledgeBaseCode IN $kbCodes)
			RETURN c.id AS chunkId, dr.code AS documentReferenceId,
			 collect(DISTINCT ea.id) AS matchedIds, count(*) AS occurrences
						""")
	List<ChunkHitRow> findChunksByEventIds(@Param("eventIds") Collection<String> aliasIds,
			@Param("kbCodes") @Nullable Collection<String> kbCodes);
}
