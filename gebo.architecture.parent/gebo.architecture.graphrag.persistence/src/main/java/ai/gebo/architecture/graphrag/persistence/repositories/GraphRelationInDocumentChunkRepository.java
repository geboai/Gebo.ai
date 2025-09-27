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
			MATCH (c:document_chunk)<-[:contained_in]-(ric:relation_in_chunk)-[:discovered_relation]->(ro:relation_object)
			MATCH (c)-[:chunk_of]->(dr:document_reference)
			WHERE ro.id IN $relationIds
			AND ($kbCodes IS NULL OR dr.knowledgeBaseCode IN $kbCodes)
			RETURN c.id AS chunkId, dr.code AS documentReferenceId,
			    collect(DISTINCT ro.id) AS matchedIds, count(*) AS occurrences

			""")
	List<ChunkHitRow> findChunksByRelationIds(@Param("relationIds") Collection<String> relationIds,
			@Param("kbCodes") @Nullable Collection<String> kbCodes);
}
