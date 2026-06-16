package ai.gebo.architecture.agents.repository;

import java.util.List;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;

public interface AgentConfigRepository extends IGBaseMongoDBRepository<GAgentConfig> {
	@Override
	default Class<GAgentConfig> getManagedType() {

		return GAgentConfig.class;
	}

	List<GAgentConfig> findByAgentServiceId(String id);
}
