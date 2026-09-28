package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GProviderApiKey;
import ai.gebo.llms.abstraction.layer.model.GProviderDeal;
import ai.gebo.llms.abstraction.layer.model.GProviderFlatConditions;
import ai.gebo.llms.abstraction.layer.repository.GProviderDealRepository;
import ai.gebo.llms.abstraction.layer.services.IGProviderDealService;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
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
	private final ObjectProvider<IGeboSecretsAccessService> secretsProvider;

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
				.setOnInsert("_id", GProviderDeal.newId(providerId)).setOnInsert("dateCreated", now)
				.setOnInsert("description", GProviderDeal.defaultDescription(providerId, true));
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

	@Override
	public List<GProviderDeal> findDeals(String providerId) {
		GProviderDealRepository repository = requireRepository();
		List<GProviderDeal> deals = providerId != null ? repository.findByProviderId(providerId)
				: repository.findAll();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("findDeals(providerId=" + providerId + ") found " + deals.size() + " deals");
		}
		return deals;
	}

	@Override
	public GProviderDeal findDeal(String dealId) {
		return dealId != null ? requireRepository().findById(dealId).orElse(null) : null;
	}

	@Override
	public List<GProviderApiKey> getProviderApiKeys(String providerId) {
		requireText(providerId, "providerId");
		Map<String, String> dealOfKey = new HashMap<>();
		for (GProviderDeal deal : requireRepository().findByProviderId(providerId)) {
			for (String code : deal.getSecretCodes()) {
				dealOfKey.put(code, deal.getId());
			}
		}
		List<GProviderApiKey> keys = new ArrayList<>();
		for (SecretInfo secret : providerSecrets(providerId)) {
			GProviderApiKey key = new GProviderApiKey();
			key.setSecretCode(secret.getCode());
			key.setDescription(secret.getDescription());
			key.setSecretType(secret.getSecretType() != null ? secret.getSecretType().name() : null);
			key.setReadOnly(secret.getReadOnly());
			key.setDealId(dealOfKey.get(secret.getCode()));
			keys.add(key);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getProviderApiKeys(providerId=" + providerId + ") found " + keys.size() + " keys, "
					+ keys.stream().filter(x -> x.getDealId() != null).count() + " covered by a deal");
		}
		return keys;
	}

	@Override
	public GProviderDeal createDeal(String providerId, List<String> secretCodes, String description) {
		requireText(providerId, "providerId");
		List<String> codes = secretCodes != null ? secretCodes.stream().distinct().toList() : List.of();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin createDeal(providerId=" + providerId + ", secretCodes=" + codes + ")");
		}
		for (String code : codes) {
			requireProviderKey(providerId, code);
		}
		Date now = new Date();
		GProviderDeal deal = new GProviderDeal();
		deal.setId(GProviderDeal.newId(providerId));
		deal.setProviderId(providerId);
		deal.setDescription(description != null && !description.isBlank() ? description.trim()
				: GProviderDeal.defaultDescription(providerId, false));
		deal.setDateCreated(now);
		deal.setDateModified(now);
		for (String code : codes) {
			pullFromOtherDeals(providerId, deal.getId(), code);
			deal.getSecretCodes().add(code);
		}
		deal = requireRepository().insert(deal);
		LOGGER.info("Created the deal id=" + deal.getId() + " of provider=" + providerId + " covering " + codes);
		return deal;
	}

	@Override
	public GProviderDeal assignApiKey(String dealId, String secretCode) {
		GProviderDeal deal = requireDeal(dealId);
		requireProviderKey(deal.getProviderId(), secretCode);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin assignApiKey(dealId=" + dealId + ", secretCode=" + secretCode + ")");
		}
		long moved = pullFromOtherDeals(deal.getProviderId(), dealId, secretCode);
		requireTemplate().updateFirst(new Query(Criteria.where("_id").is(dealId)),
				new Update().addToSet("secretCodes", secretCode).set("dateModified", new Date()),
				GProviderDeal.class);
		LOGGER.info((moved > 0 ? "Transferred" : "Assigned") + " secretCode=" + secretCode + " to the deal id="
				+ dealId + " of provider=" + deal.getProviderId());
		return requireDeal(dealId);
	}

	@Override
	public GProviderDeal removeApiKey(String dealId, String secretCode) {
		requireText(secretCode, "secretCode");
		GProviderDeal deal = requireDeal(dealId);
		requireTemplate().updateFirst(new Query(Criteria.where("_id").is(dealId)),
				new Update().pull("secretCodes", secretCode).set("dateModified", new Date()), GProviderDeal.class);
		LOGGER.info("Removed secretCode=" + secretCode + " from the deal id=" + dealId + " of provider="
				+ deal.getProviderId());
		return requireDeal(dealId);
	}

	@Override
	public void deleteDeal(String dealId) {
		GProviderDeal deal = requireDeal(dealId);
		if (deal.getSecretCodes() != null && !deal.getSecretCodes().isEmpty()) {
			throw new IllegalStateException("The deal " + dealId + " still covers the API keys "
					+ deal.getSecretCodes() + ": transfer or remove them before deleting it");
		}
		requireRepository().deleteById(dealId);
		LOGGER.info("Deleted the deal id=" + dealId + " of provider=" + deal.getProviderId());
	}

	@Override
	public GProviderDeal updateDescription(String dealId, String description) {
		requireText(description, "description");
		GProviderDeal deal = requireDeal(dealId);
		requireTemplate().updateFirst(new Query(Criteria.where("_id").is(dealId)),
				new Update().set("description", description.trim()).set("dateModified", new Date()),
				GProviderDeal.class);
		LOGGER.info("Renamed the deal id=" + dealId + " of provider=" + deal.getProviderId() + " to \""
				+ description.trim() + "\"");
		return requireDeal(dealId);
	}

	@Override
	public GProviderDeal updateFlatConditions(String dealId, GProviderFlatConditions flatConditions) {
		GProviderDeal deal = requireDeal(dealId);
		if (flatConditions != null) {
			requireText(flatConditions.getCurrencyCode(), "currencyCode of the flat conditions");
			if (flatConditions.getMonthlyFlatCost() == null || flatConditions.getMonthlyFlatCost() < 0) {
				throw new IllegalArgumentException("The flat conditions need a monthly cost of zero or more");
			}
			requirePositiveOrUnset(flatConditions.getMonthlyTrafficLimits(), "monthlyTrafficLimits");
			requirePositiveOrUnset(flatConditions.getDailyTrafficLimits(), "dailyTrafficLimits");
		}
		requireTemplate().updateFirst(new Query(Criteria.where("_id").is(dealId)),
				new Update().set("flatConditions", flatConditions).set("dateModified", new Date()),
				GProviderDeal.class);
		LOGGER.info((flatConditions != null ? "Set the flat conditions " + flatConditions : "Cleared the flat conditions")
				+ " of the deal id=" + dealId + " of provider=" + deal.getProviderId());
		return requireDeal(dealId);
	}

	private static void requirePositiveOrUnset(Double value, String name) {
		if (value != null && !(value > 0)) {
			throw new IllegalArgumentException(name + " must be positive, or unset for no limit");
		}
	}

	/** Removes a key from every other deal of the provider; returns how many held it. */
	private long pullFromOtherDeals(String providerId, String dealId, String secretCode) {
		UpdateResult result = requireTemplate().updateMulti(
				new Query(Criteria.where("providerId").is(providerId).and("_id").ne(dealId).and("secretCodes")
						.is(secretCode)),
				new Update().pull("secretCodes", secretCode).set("dateModified", new Date()), GProviderDeal.class);
		if (result.getModifiedCount() > 0 && LOGGER.isDebugEnabled()) {
			LOGGER.debug("secretCode=" + secretCode + " moved away from " + result.getModifiedCount()
					+ " other deal(s) of provider=" + providerId);
		}
		return result.getModifiedCount();
	}

	/** The provider's API keys: the secrets listed under its context code. */
	private List<SecretInfo> providerSecrets(String providerId) {
		IGeboSecretsAccessService secrets = secretsProvider.getIfAvailable();
		if (secrets == null) {
			throw new IllegalStateException("No secrets store in this application");
		}
		try {
			List<SecretInfo> list = secrets.getSecretInfoByContextCode(providerId);
			return list != null ? list : List.of();
		} catch (GeboCryptSecretException e) {
			throw new IllegalStateException("Cannot read the API keys of provider=" + providerId, e);
		}
	}

	private void requireProviderKey(String providerId, String secretCode) {
		requireText(secretCode, "secretCode");
		boolean known = providerSecrets(providerId).stream().anyMatch(x -> secretCode.equals(x.getCode()));
		if (!known) {
			throw new IllegalArgumentException("The API key " + secretCode + " is not a key of provider="
					+ providerId + " (no secret with that code under the context code " + providerId + ")");
		}
	}

	private GProviderDeal requireDeal(String dealId) {
		requireText(dealId, "dealId");
		return requireRepository().findById(dealId)
				.orElseThrow(() -> new IllegalArgumentException("No provider deal with id=" + dealId));
	}

	private static void requireText(String value, String name) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException("Missing " + name);
		}
	}

	private GProviderDealRepository requireRepository() {
		GProviderDealRepository repository = repositoryProvider.getIfAvailable();
		if (repository == null) {
			throw new IllegalStateException("No MongoDB in this application, provider deals are not available");
		}
		return repository;
	}

	private MongoTemplate requireTemplate() {
		MongoTemplate template = mongoTemplateProvider.getIfAvailable();
		if (template == null) {
			throw new IllegalStateException("No MongoDB in this application, provider deals are not available");
		}
		return template;
	}
}
