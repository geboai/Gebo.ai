package ai.gebo.architecture.graphrag.persistence.repositories;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import ai.gebo.architecture.graphrag.persistence.model.GraphDocumentReference;
@ConditionalOnProperty(prefix = "ai.gebo.neo4j", name = "enabled", havingValue = "true")
public interface GraphDocumentReferenceRepository extends Neo4jRepository<GraphDocumentReference, String> {

	/** Document references extracted from a given knowledge base. */
	List<GraphDocumentReference> findByKnowledgeBaseCode(String knowledgeBaseCode);

	/** Document references extracted from a given project. */
	List<GraphDocumentReference> findByProjectCode(String projectCode);

	/** Document references extracted from a given concrete data-source endpoint. */
	List<GraphDocumentReference> findByProjectEndpointClassAndProjectEndpointCode(String projectEndpointClass,
			String projectEndpointCode);

}
