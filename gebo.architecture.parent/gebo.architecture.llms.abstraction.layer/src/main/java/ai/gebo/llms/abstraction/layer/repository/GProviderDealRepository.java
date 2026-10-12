package ai.gebo.llms.abstraction.layer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ai.gebo.llms.abstraction.layer.model.GProviderDeal;

public interface GProviderDealRepository extends MongoRepository<GProviderDeal, String> {

	/** The deals made with a provider. */
	public List<GProviderDeal> findByProviderId(String providerId);

	/**
	 * The deal of a provider covering an API key. The equality on the secretCodes
	 * array matches any of its elements.
	 */
	@Query("{ 'providerId' : ?0, 'secretCodes' : ?1 }")
	public Optional<GProviderDeal> findByProviderIdAndSecretCode(String providerId, String secretCode);

	/** The deals covering an API key, whatever their provider. */
	@Query("{ 'secretCodes' : ?0 }")
	public List<GProviderDeal> findBySecretCode(String secretCode);
}
