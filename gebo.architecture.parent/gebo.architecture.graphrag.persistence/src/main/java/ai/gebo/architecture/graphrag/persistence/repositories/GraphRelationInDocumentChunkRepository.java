package ai.gebo.architecture.graphrag.persistence.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import ai.gebo.architecture.graphrag.persistence.model.ChunkHitRow;
import ai.gebo.architecture.graphrag.persistence.model.GraphRelationInDocumentChunk;
import jakarta.annotation.Nullable;

public interface GraphRelationInDocumentChunkRepository
		extends AbstractInDocumentChunkObjectRepository<GraphRelationInDocumentChunk> {
	@Query("""
			MATCH (c:document_chunk)<-[:in_chunk]-(ric:relation_chunk)-[:discovered_relation]->(ro:relation_object)
			MATCH (c)-[:of_document]->(dr:document_reference)
			WHERE ro.id IN $relationIds
			  AND ($kbCodes IS NULL OR dr.knowledgebase_code IN $kbCodes)
			RETURN c.id AS chunkId,
			       dr.id AS documentReferenceId,
			       collect(DISTINCT ro.id) AS matchedIds,
			       count(*) AS occurrences
			""")
	List<ChunkHitRow> findChunksByRelationIds(@Param("relationIds") Collection<String> relationIds,
			@Param("kbCodes") @Nullable Collection<String> kbCodes);
}
