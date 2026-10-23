package ai.gebo.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.security.model.GeneratedApiKey;
import ai.gebo.security.model.GeneratedApiKeyInfo;

public interface GeneratedApiKeyRepository extends IGBaseMongoDBRepository<GeneratedApiKey> {

	Page<GeneratedApiKeyInfo> findAllProjectedBy(Pageable pageable);

	@Override
	default Class<GeneratedApiKey> getManagedType() {

		return GeneratedApiKey.class;
	}

	Page<GeneratedApiKeyInfo> findProjectedByUserCreated(String username, Pageable pageable);

	/** Whether a key whose token carries this {@code jti} still exists: deleted keys are revoked. */
	boolean existsByTokenId(String tokenId);

}
