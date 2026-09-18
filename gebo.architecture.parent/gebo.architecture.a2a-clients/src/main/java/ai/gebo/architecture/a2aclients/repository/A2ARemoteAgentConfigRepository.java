package ai.gebo.architecture.a2aclients.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.gebo.architecture.a2aclients.model.A2ARemoteAgentConfig;
import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;

/**
 * MongoDB repository for {@link A2ARemoteAgentConfig}, mirroring
 * {@code McpClientConfigRepository}.
 */
public interface A2ARemoteAgentConfigRepository extends IGBaseMongoDBRepository<A2ARemoteAgentConfig> {

	@Override
	default Class<A2ARemoteAgentConfig> getManagedType() {
		return A2ARemoteAgentConfig.class;
	}

	/**
	 * Paged Query-by-Example search over the registered remote A2A agents.
	 *
	 * @param example  the probe whose populated fields are used as match criteria
	 * @param pageable the pagination/sorting information
	 * @return the matching page of configurations
	 */
	default Page<A2ARemoteAgentConfig> findByQbe(A2ARemoteAgentConfig example, Pageable pageable) {
		return findAll(Example.of(example), pageable);
	}
}
