package ai.gebo.architecture.graphrag.persistence.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import ai.gebo.architecture.graphrag.persistence.model.ChunkHitRow;
import ai.gebo.architecture.graphrag.persistence.model.GraphEventAliasInDocumentChunk;
import jakarta.annotation.Nullable;

public interface GraphEventAliasInDocumentChunkRepository
		extends AbstractInDocumentChunkObjectRepository<GraphEventAliasInDocumentChunk> {
	@Query("""
			MATCH (c:document_chunk)<-[:contained_in]-(evic:event_alias_chunk)-[:discovered_event_alias]->(ev:event_alias)
			MATCH (c)-[:chunk_of]->(dr:document_reference)
			WHERE ev.id IN $aliasIds
			  AND ($kbCodes IS NULL OR dr.knowledgebase_code IN $kbCodes)
			RETURN c.id AS chunkId,
			       dr.id AS documentReferenceId,
			       collect(DISTINCT ev.id) AS matchedIds,
			       count(*) AS occurrences
			""")
	List<ChunkHitRow> findChunksByEventAliasIds(@Param("aliasIds") Collection<String> aliasIds,
			@Param("kbCodes") @Nullable Collection<String> kbCodes);
}
