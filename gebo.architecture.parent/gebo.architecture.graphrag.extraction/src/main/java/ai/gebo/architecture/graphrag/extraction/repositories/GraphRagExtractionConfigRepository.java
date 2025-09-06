package ai.gebo.architecture.graphrag.extraction.repositories;

import ai.gebo.architecture.graphrag.extraction.model.GraphRagExtractionConfig;
import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;

public interface GraphRagExtractionConfigRepository extends IGBaseMongoDBRepository<GraphRagExtractionConfig> {
	@Override
	default Class<GraphRagExtractionConfig> getManagedType() {
		return GraphRagExtractionConfig.class;
	}
}
