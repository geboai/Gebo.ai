package ai.gebo.architecture.graphrag.extraction.repositories;

import java.util.List;

import ai.gebo.architecture.graphrag.extraction.model.GraphRagExtractionConfig;
import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;

public interface GraphRagExtractionConfigRepository extends IGBaseMongoDBRepository<GraphRagExtractionConfig> {
	@Override
	default Class<GraphRagExtractionConfig> getManagedType() {
		return GraphRagExtractionConfig.class;
	}

	public List<GraphRagExtractionConfig> findByEndpointClassNameAndEndpointCode(String className, String code);

	public List<GraphRagExtractionConfig> findByKnowledgeBaseCode(String knowledgeBaseCode);

	public List<GraphRagExtractionConfig> findByDefaultConfiguration(Boolean trueValue);

	public long countByDefaultConfiguration(Boolean trueValue);
}
