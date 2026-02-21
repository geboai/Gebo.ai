package ai.gebo.bingsearch.handler.repository;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.bingsearch.handler.model.GBingSearchApiCredentials;

public interface GBingSearchApiCredentialsRepository extends IGBaseMongoDBRepository<GBingSearchApiCredentials> {
	@Override
	default Class<GBingSearchApiCredentials> getManagedType() {
		return GBingSearchApiCredentials.class;
	}
}
