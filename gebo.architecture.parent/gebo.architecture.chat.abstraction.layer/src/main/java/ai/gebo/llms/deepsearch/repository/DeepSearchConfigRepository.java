package ai.gebo.llms.deepsearch.repository;

import java.util.List;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;

public interface DeepSearchConfigRepository extends IGBaseMongoDBRepository<DeepSearchConfig> {
	@Override
	default Class<DeepSearchConfig> getManagedType() {
		return DeepSearchConfig.class;
	}

	public DeepSearchConfig findByDefaultConfig(Boolean b);
	
}
