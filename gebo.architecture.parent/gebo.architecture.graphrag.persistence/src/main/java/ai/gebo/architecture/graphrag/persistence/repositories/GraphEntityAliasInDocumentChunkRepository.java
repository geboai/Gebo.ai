package ai.gebo.architecture.graphrag.persistence.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import ai.gebo.architecture.graphrag.persistence.model.ChunkHitRow;
import ai.gebo.architecture.graphrag.persistence.model.GraphEntityAliasInDocumentChunk;
import jakarta.annotation.Nullable;

public interface GraphEntityAliasInDocumentChunkRepository
		extends AbstractInDocumentChunkObjectRepository<GraphEntityAliasInDocumentChunk> {
	@Query("""
			MATCH (c:document_chunk)<-[:contained_in]-(eaic:entity_alias_chunk)-[:discovered_entity_alias]->(ea:entity_alias)
			MATCH (c)-[:chunk_of]->(dr:document_reference)
			WHERE ea.id IN $aliasIds
			  AND ($kbCodes IS NULL OR dr.knowledgebase_code IN $kbCodes)
			RETURN c.id AS chunkId,
			       dr.id AS documentReferenceId,
			       collect(DISTINCT ea.id) AS matchedIds,
			       count(*) AS occurrences
			""")
	List<ChunkHitRow> findChunksByEntityAliasIds(@Param("aliasIds") Collection<String> aliasIds,
			@Param("kbCodes") @Nullable Collection<String> kbCodes);
}
