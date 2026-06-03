package ai.gebo.architecture.agents.repository;

import java.util.List;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;

public interface GAgentConfigRepository extends IGBaseMongoDBRepository<GAgentConfig> {
	@Override
	default Class<GAgentConfig> getManagedType() {

		return GAgentConfig.class;
	}

	List<GAgentConfig> findByAgentServiceId(String id);
}
