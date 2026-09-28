package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;

import ai.gebo.llms.abstraction.layer.model.GProviderDeal;
import ai.gebo.llms.abstraction.layer.repository.GProviderDealRepository;
import ai.gebo.llms.abstraction.layer.services.IGProviderDealService;
import lombok.AllArgsConstructor;

/**
 * Mongo backed {@link IGProviderDealService}. The repository and template are
 * resolved lazily: an application loading the LLM layer without MongoDB still
 * starts, its deals simply not being maintained.
 */
@Service
@AllArgsConstructor
public class GProviderDealServiceImpl implements IGProviderDealService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GProviderDealServiceImpl.class);
	private final ObjectProvider<GProviderDealRepository> repositoryProvider;
	private final ObjectProvider<MongoTemplate> mongoTemplateProvider;

	@Override
	public GProviderDeal ensureDeal(String providerId, String secretCode) {
		if (providerId == null || providerId.isBlank() || secretCode == null || secretCode.isBlank()) {
			throw new IllegalArgumentException(
					"A provider deal needs both a provider and an API key: providerId=" + providerId
							+ " secretCode=" + secretCode);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin ensureDeal(providerId=" + providerId + ", secretCode=" + secretCode + ")");
		}
		GProviderDealRepository repository = repositoryProvider.getIfAvailable();
		MongoTemplate mongoTemplate = mongoTemplateProvider.getIfAvailable();
		if (repository == null || mongoTemplate == null) {
			LOGGER.debug("No MongoDB in this application, provider deals are not maintained");
			return null;
		}
		Optional<GProviderDeal> covering = repository.findByProviderIdAndSecretCode(providerId, secretCode);
		if (covering.isPresent()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End ensureDeal(): deal id=" + covering.get().getId() + " of provider=" + providerId
						+ " already covers secretCode=" + secretCode);
			}
			return covering.get();
		}
		// One atomic upsert on the provider: adds the key to the provider's existing
		// deal, or creates the deal with the key when the provider has none. $addToSet
		// keeps the key unique even if another instance added it meanwhile.
		Date now = new Date();
		Query providerDeal = new Query(Criteria.where("providerId").is(providerId));
		Update association = new Update().addToSet("secretCodes", secretCode).set("dateModified", now)
				.setOnInsert("_id", GProviderDeal.newId(providerId)).setOnInsert("dateCreated", now);
		UpdateResult result = mongoTemplate.upsert(providerDeal, association, GProviderDeal.class);
		GProviderDeal deal = repository.findByProviderIdAndSecretCode(providerId, secretCode)
				.orElseThrow(() -> new IllegalStateException(
						"The deal of provider=" + providerId + " was not found after associating " + secretCode));
		if (result.getUpsertedId() != null) {
			LOGGER.info("Created the deal id=" + deal.getId() + " of provider=" + providerId
					+ " covering secretCode=" + secretCode);
		} else {
			LOGGER.info("Added secretCode=" + secretCode + " to the deal id=" + deal.getId() + " of provider="
					+ providerId);
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<PROVIDER_DEAL>" + deal + "</PROVIDER_DEAL>");
		}
		return deal;
	}
}
