package ai.gebo.architecture.graphrag.persistence.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import ai.gebo.architecture.graphrag.persistence.model.ChunkHitRow;
import ai.gebo.architecture.graphrag.persistence.model.GraphEntityInDocumentChunk;
import jakarta.annotation.Nullable;
@ConditionalOnProperty(prefix = "ai.gebo.neo4j", name = "enabled", havingValue = "true")
public interface GraphEntityInDocumentChunkRepository extends AbstractInDocumentChunkObjectRepository<GraphEntityInDocumentChunk> {
	@Query("""
			MATCH (c:document_chunk)<-[:contained_in]-(eaic:entity_in_chunk)-[:discovered_entity]->(ea:entity_object)
			MATCH (c)-[:chunk_of]->(dr:document_reference)
			WHERE ea.id IN $entityIds
			AND ($kbCodes IS NULL OR dr.knowledgeBaseCode IN $kbCodes)
			RETURN c.id AS chunkId, dr.code AS documentReferenceId,
			 collect(DISTINCT ea.id) AS matchedIds, count(*) AS occurrences
						""")
	List<ChunkHitRow> findChunksByEntityIds(@Param("entityIds") Collection<String> aliasIds,
			@Param("kbCodes") @Nullable Collection<String> kbCodes);
}
