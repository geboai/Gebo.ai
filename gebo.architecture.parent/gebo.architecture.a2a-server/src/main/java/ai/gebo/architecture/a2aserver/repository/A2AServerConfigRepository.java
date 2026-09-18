package ai.gebo.architecture.a2aserver.repository;

import java.util.Optional;

import ai.gebo.architecture.a2aserver.model.A2AServerConfig;
import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;

/**
 * MongoDB repository for {@link A2AServerConfig}, mirroring
 * {@code GeboMCPServerConfigRepository}.
 */
public interface A2AServerConfigRepository extends IGBaseMongoDBRepository<A2AServerConfig> {

	@Override
	default Class<A2AServerConfig> getManagedType() {
		return A2AServerConfig.class;
	}

	/**
	 * Looks up a published server by its unique relative URL, used to enforce
	 * uniqueness on insert/update.
	 *
	 * @param exportedRelativeUrl the relative URL segment
	 * @return the matching configuration, if any
	 */
	Optional<A2AServerConfig> findByExportedRelativeUrl(String exportedRelativeUrl);
}
