package ai.gebo.architecture.agents.repository;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;

public interface AgentsNetworkRepository extends IGBaseMongoDBRepository<GAgentsNetwork> {
	@Override
	default Class<GAgentsNetwork> getManagedType() {
		return GAgentsNetwork.class;
	}
}
