package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.llms.abstraction.layer.model.GProviderApiKey;
import ai.gebo.llms.abstraction.layer.model.GProviderModelPrice;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableModel;
import ai.gebo.llms.abstraction.layer.model.GProviderDeal;
import ai.gebo.llms.abstraction.layer.model.GProviderFlatConditions;
import ai.gebo.llms.abstraction.layer.model.GProviderKeyLimit;
import ai.gebo.llms.abstraction.layer.model.GProviderSpendingLimits;
import ai.gebo.llms.abstraction.layer.repository.GProviderDealRepository;
import ai.gebo.llms.abstraction.layer.services.IGProviderDealService;
import ai.gebo.llms.abstraction.layer.services.IGProviderKeyLimitsReader;
import ai.gebo.llms.abstraction.layer.services.IGRuntimeModelConfigurationDao;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
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
	/** What {@code IGConfigurableModel.safeGetModelCode()} returns for a model without code. */
	private static final String UNKNOWN_MODEL_CODE = "unknown";
	private final ObjectProvider<GProviderDealRepository> repositoryProvider;
	private final ObjectProvider<MongoTemplate> mongoTemplateProvider;
	private final ObjectProvider<IGeboSecretsAccessService> secretsProvider;
	/**
	 * The runtime models, to tell which providers have configured models; lazy, the
	 * DAOs themselves using this service.
	 */
	private final ObjectProvider<IGRuntimeModelConfigurationDao<?, ?>> runtimeDaos;
	/** The per provider readers of the API keys' spending limits. */
	private final ObjectProvider<IGProviderKeyLimitsReader> limitsReaders;

	/**
	 * How long the deal covering an API key is served from {@link #dealsByKey}: the
	 * model prices are read on every model call. A change made on this instance
	 * empties the cache at once; one made on another cluster instance shows here
	 * within this delay.
	 */
	private static final long DEALS_CACHE_TTL_MILLIS = 60_000L;

	private record CachedDeal(GProviderDeal deal, long expiresAt) {
	}

	/** The deal covering each "providerId|secretCode", null when none does. */
	private final Map<String, CachedDeal> dealsByKey = new ConcurrentHashMap<>();

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
		dealsChanged();
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
		// The deal gained a key: its imported limits change.
		return refreshQuietly(deal);
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
		List<String> sources = new ArrayList<>();
		for (String code : codes) {
			sources.addAll(pullFromOtherDeals(providerId, deal.getId(), code));
			deal.getSecretCodes().add(code);
		}
		deal = requireRepository().insert(deal);
		dealsChanged();
		LOGGER.info("Created the deal id=" + deal.getId() + " of provider=" + providerId + " covering " + codes);
		refreshSourcesQuietly(sources);
		return refreshQuietly(deal);
	}

	@Override
	public GProviderDeal assignApiKey(String dealId, String secretCode) {
		GProviderDeal deal = requireDeal(dealId);
		requireProviderKey(deal.getProviderId(), secretCode);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin assignApiKey(dealId=" + dealId + ", secretCode=" + secretCode + ")");
		}
		List<String> sources = pullFromOtherDeals(deal.getProviderId(), dealId, secretCode);
		requireTemplate().updateFirst(new Query(Criteria.where("_id").is(dealId)),
				new Update().addToSet("secretCodes", secretCode).set("dateModified", new Date()),
				GProviderDeal.class);
		dealsChanged();
		LOGGER.info((sources.isEmpty() ? "Assigned" : "Transferred") + " secretCode=" + secretCode
				+ " to the deal id=" + dealId + " of provider=" + deal.getProviderId()
				+ (sources.isEmpty() ? "" : " from " + sources));
		refreshSourcesQuietly(sources);
		return refreshQuietly(requireDeal(dealId));
	}

	@Override
	public GProviderDeal removeApiKey(String dealId, String secretCode) {
		requireText(secretCode, "secretCode");
		GProviderDeal deal = requireDeal(dealId);
		requireTemplate().updateFirst(new Query(Criteria.where("_id").is(dealId)),
				new Update().pull("secretCodes", secretCode).set("dateModified", new Date()), GProviderDeal.class);
		dealsChanged();
		LOGGER.info("Removed secretCode=" + secretCode + " from the deal id=" + dealId + " of provider="
				+ deal.getProviderId());
		return refreshQuietly(requireDeal(dealId));
	}

	@Override
	public void deleteDeal(String dealId) {
		GProviderDeal deal = requireDeal(dealId);
		if (deal.getSecretCodes() != null && !deal.getSecretCodes().isEmpty()) {
			throw new IllegalStateException("The deal " + dealId + " still covers the API keys "
					+ deal.getSecretCodes() + ": transfer or remove them before deleting it");
		}
		if (requireRepository().findByProviderId(deal.getProviderId()).size() <= 1) {
			List<String> models = configuredModelsOf(deal.getProviderId());
			if (!models.isEmpty()) {
				throw new IllegalStateException("The deal " + dealId + " is the last deal of provider="
						+ deal.getProviderId() + ", which still has the configured models " + models
						+ ": it cannot be deleted");
			}
		}
		requireRepository().deleteById(dealId);
		dealsChanged();
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

	@Override
	public GProviderDeal updateModelPricing(String dealId, String modelCode, GModelPricingConditions pricing) {
		requireText(modelCode, "modelCode");
		GProviderDeal deal = requireDeal(dealId);
		if (pricing != null) {
			requireValidPricing(pricing);
		}
		Date now = new Date();
		List<GProviderModelPrice> prices = new ArrayList<>();
		if (deal.getModelPrices() != null) {
			deal.getModelPrices().stream().filter(x -> !modelCode.equals(x.getModelCode())).forEach(prices::add);
		}
		if (pricing != null) {
			prices.add(new GProviderModelPrice(modelCode, pricing, now));
		}
		requireTemplate().updateFirst(new Query(Criteria.where("_id").is(dealId)),
				new Update().set("modelPrices", prices).set("dateModified", now), GProviderDeal.class);
		dealsChanged();
		LOGGER.info((pricing != null ? "Set the price " + pricing + " of" : "Removed the price of") + " model="
				+ modelCode + " in the deal id=" + dealId + " of provider=" + deal.getProviderId());
		return requireDeal(dealId);
	}

	private static void requireValidPricing(GModelPricingConditions pricing) {
		requireText(pricing.getCurrencyCode(), "currencyCode of the model pricing");
		requireNonNegativeOrUnset(pricing.getInputMtokenPrice(), "inputMtokenPrice");
		requireNonNegativeOrUnset(pricing.getOutputMtokenPrice(), "outputMtokenPrice");
		requireNonNegativeOrUnset(pricing.getRequestPrice(), "requestPrice");
		requireNonNegativeOrUnset(pricing.getMonthlyFlatCost(), "monthlyFlatCost");
		requirePositiveOrUnset(pricing.getMonthlyTrafficLimits(), "monthlyTrafficLimits");
		requirePositiveOrUnset(pricing.getDailyTrafficLimits(), "dailyTrafficLimits");
		if (pricing.getInputMtokenPrice() == null && pricing.getOutputMtokenPrice() == null
				&& pricing.getRequestPrice() == null && pricing.getMonthlyFlatCost() == null) {
			throw new IllegalArgumentException(
					"The model pricing sets no price: remove it to go back to the configured price");
		}
	}

	private static void requireNonNegativeOrUnset(Double value, String name) {
		if (value != null && !(value >= 0)) {
			throw new IllegalArgumentException(name + " must be zero or more, or unset");
		}
	}

	@Override
	public GModelPricingConditions findModelPricing(IGConfigurableModel<?, ?> model) {
		if (model == null) {
			return null;
		}
		try {
			GModelType type = model.getType();
			GBaseModelConfig<?> config = model.getConfig();
			return findModelPricing(type != null ? type.getProviderId() : null,
					config != null ? config.getApiSecretCode() : null, model.safeGetModelCode());
		} catch (Throwable e) {
			LOGGER.error("Cannot read the deal pricing of model code=" + model.getCode(), e);
			return null;
		}
	}

	@Override
	public GModelPricingConditions findModelPricing(String providerId, String secretCode, String modelCode) {
		if (providerId == null || providerId.isBlank() || secretCode == null || secretCode.isBlank()
				|| modelCode == null || modelCode.isBlank() || UNKNOWN_MODEL_CODE.equals(modelCode)) {
			return null;
		}
		try {
			GProviderDeal deal = coveringDeal(providerId, secretCode);
			GModelPricingConditions pricing = deal != null ? deal.modelPricing(modelCode) : null;
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Deal pricing of provider=" + providerId + " secretCode=" + secretCode + " model="
						+ modelCode + ": " + (deal == null ? "no deal covers the key"
								: pricing == null ? "none in the deal id=" + deal.getId()
										: "found in the deal id=" + deal.getId()));
			}
			if (pricing != null && LOGGER.isTraceEnabled()) {
				LOGGER.trace("<DEAL_MODEL_PRICING>" + pricing + "</DEAL_MODEL_PRICING>");
			}
			return pricing;
		} catch (Throwable e) {
			LOGGER.error("Cannot read the deal pricing of provider=" + providerId + " secretCode=" + secretCode
					+ " model=" + modelCode + ", the configured pricing applies", e);
			return null;
		}
	}

	/** The deal covering an API key, through {@link #dealsByKey}; null when none. */
	private GProviderDeal coveringDeal(String providerId, String secretCode) {
		String key = providerId + "|" + secretCode;
		long now = System.currentTimeMillis();
		CachedDeal cached = dealsByKey.get(key);
		if (cached != null && cached.expiresAt() > now) {
			return cached.deal();
		}
		GProviderDealRepository repository = repositoryProvider.getIfAvailable();
		GProviderDeal deal = repository != null
				? repository.findByProviderIdAndSecretCode(providerId, secretCode).orElse(null)
				: null;
		dealsByKey.put(key, new CachedDeal(deal, now + DEALS_CACHE_TTL_MILLIS));
		return deal;
	}

	/** Codes of the model configurations running a model of the provider. */
	private List<String> configuredModelsOf(String providerId) {
		List<String> codes = new ArrayList<>();
		runtimeDaos.orderedStream().forEach(dao -> {
			for (IGConfigurableModel<?, ?> model : dao.getConfigurations()) {
				GModelType type = model.getType();
				if (type != null && providerId.equals(type.getProviderId())) {
					codes.add(model.getConfig() != null ? model.getConfig().getCode() : model.getCode());
				}
			}
		});
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Provider=" + providerId + " has the configured models " + codes);
		}
		return codes;
	}

	/** Forgets the cached deals after a change of the deals' keys or prices. */
	private void dealsChanged() {
		dealsByKey.clear();
	}

	private static void requirePositiveOrUnset(Double value, String name) {
		if (value != null && !(value > 0)) {
			throw new IllegalArgumentException(name + " must be positive, or unset for no limit");
		}
	}

	/** Removes a key from every other deal of the provider; returns the ids of those that held it. */
	private List<String> pullFromOtherDeals(String providerId, String dealId, String secretCode) {
		Query holders = new Query(Criteria.where("providerId").is(providerId).and("_id").ne(dealId)
				.and("secretCodes").is(secretCode));
		List<String> ids = requireTemplate().find(holders, GProviderDeal.class).stream().map(GProviderDeal::getId)
				.toList();
		if (!ids.isEmpty()) {
			requireTemplate().updateMulti(holders,
					new Update().pull("secretCodes", secretCode).set("dateModified", new Date()),
					GProviderDeal.class);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("secretCode=" + secretCode + " moved away from the deals " + ids + " of provider="
						+ providerId);
			}
		}
		return ids;
	}

	@Override
	public GProviderDeal refreshImportedLimits(String dealId) {
		GProviderDeal deal = requireDeal(dealId);
		GProviderSpendingLimits current = deal.getSpendingLimits();
		if (current != null && !Boolean.TRUE.equals(current.getAutoImported())) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Deal id=" + dealId + " has spending limits set by an admin, not importing them");
			}
			return deal;
		}
		IGProviderKeyLimitsReader reader = limitsReaders.orderedStream()
				.filter(x -> deal.getProviderId().equals(x.getProviderId())).findFirst().orElse(null);
		if (reader == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("No spending limits reader for provider=" + deal.getProviderId()
						+ ", the limits of the deal id=" + dealId + " are not imported");
			}
			return deal;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin refreshImportedLimits(dealId=" + dealId + ") with " + reader.getClass().getName()
					+ " on " + deal.getSecretCodes().size() + " keys");
		}
		GProviderSpendingLimits imported = importLimits(deal, reader);
		if (imported == null) {
			return deal;
		}
		requireTemplate().updateFirst(new Query(Criteria.where("_id").is(dealId)),
				new Update().set("spendingLimits", imported).set("dateModified", new Date()), GProviderDeal.class);
		LOGGER.info("Imported the spending limits " + imported + " of the deal id=" + dealId + " of provider="
				+ deal.getProviderId());
		return requireDeal(dealId);
	}

	/**
	 * Reads the limit of every API key of the deal and sums them per period. A key
	 * without a limit leaves the deal unlimited: its imported limits are then all
	 * unset. Null, leaving the stored limits unchanged, when a key cannot be read or
	 * the keys are limited in different currencies.
	 */
	private GProviderSpendingLimits importLimits(GProviderDeal deal, IGProviderKeyLimitsReader reader) {
		GProviderSpendingLimits limits = new GProviderSpendingLimits();
		limits.setAutoImported(Boolean.TRUE);
		limits.setImportDate(new Date());
		boolean unlimited = false;
		for (String code : deal.getSecretCodes()) {
			GProviderKeyLimit limit;
			try {
				limit = reader.readLimit(clearApiKey(code));
			} catch (Throwable e) {
				LOGGER.error("Cannot read the spending limit of secretCode=" + code + " from provider="
						+ deal.getProviderId() + ", the limits of the deal id=" + deal.getId()
						+ " are left unchanged", e);
				return null;
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("secretCode=" + code + " of provider=" + deal.getProviderId() + " has limit=" + limit);
			}
			if (limit == null || limit.getAmount() == null) {
				unlimited = true;
				continue;
			}
			if (limits.getCurrencyCode() == null) {
				limits.setCurrencyCode(limit.getCurrencyCode());
			} else if (limit.getCurrencyCode() != null && !limits.getCurrencyCode().equals(limit.getCurrencyCode())) {
				LOGGER.warn("The API keys of the deal id=" + deal.getId() + " are limited in different currencies ("
						+ limits.getCurrencyCode() + ", " + limit.getCurrencyCode() + "), its limits are left unchanged");
				return null;
			}
			GProviderKeyLimit.Period period = limit.getPeriod() != null ? limit.getPeriod()
					: GProviderKeyLimit.Period.TOTAL;
			switch (period) {
			case DAILY -> limits.setDailySpendingLimit(add(limits.getDailySpendingLimit(), limit.getAmount()));
			case WEEKLY -> limits.setWeeklySpendingLimit(add(limits.getWeeklySpendingLimit(), limit.getAmount()));
			case MONTHLY -> limits.setMonthlySpendingLimit(add(limits.getMonthlySpendingLimit(), limit.getAmount()));
			case TOTAL -> limits.setTotalSpendingLimit(add(limits.getTotalSpendingLimit(), limit.getAmount()));
			}
		}
		if (unlimited) {
			GProviderSpendingLimits none = new GProviderSpendingLimits();
			none.setAutoImported(Boolean.TRUE);
			none.setImportDate(limits.getImportDate());
			return none;
		}
		return limits;
	}

	private static Double add(Double sum, Double amount) {
		return sum != null ? sum + amount : amount;
	}

	/** The clear value of an API key secret, which must be a token. */
	private String clearApiKey(String secretCode) throws GeboCryptSecretException {
		IGeboSecretsAccessService secrets = secretsProvider.getIfAvailable();
		if (secrets == null) {
			throw new IllegalStateException("No secrets store in this application");
		}
		AbstractGeboSecretContent secret = secrets.getSecretContentById(secretCode);
		if (secret == null || secret.type() != GeboSecretType.TOKEN) {
			throw new IllegalStateException("The secret " + secretCode + " is not an API key (TOKEN) secret");
		}
		return ((GeboTokenContent) secret).getToken();
	}

	/** {@link #refreshImportedLimits(String)}, never failing the operation it follows. */
	private GProviderDeal refreshQuietly(GProviderDeal deal) {
		try {
			return refreshImportedLimits(deal.getId());
		} catch (Throwable e) {
			LOGGER.error("Cannot import the spending limits of the deal id=" + deal.getId(), e);
			return deal;
		}
	}

	/** Refreshes the deals an API key was moved away from, which lost its limit. */
	private void refreshSourcesQuietly(List<String> dealIds) {
		for (String id : dealIds.stream().distinct().toList()) {
			GProviderDeal source = findDeal(id);
			if (source != null) {
				refreshQuietly(source);
			}
		}
	}

	@Override
	public GProviderDeal updateSpendingLimits(String dealId, GProviderSpendingLimits spendingLimits) {
		GProviderDeal deal = requireDeal(dealId);
		if (spendingLimits == null) {
			requireTemplate().updateFirst(new Query(Criteria.where("_id").is(dealId)),
					new Update().unset("spendingLimits").set("dateModified", new Date()), GProviderDeal.class);
			LOGGER.info("Cleared the spending limits of the deal id=" + dealId + " of provider="
					+ deal.getProviderId() + ", importing them again");
			return refreshQuietly(requireDeal(dealId));
		}
		requireText(spendingLimits.getCurrencyCode(), "currencyCode of the spending limits");
		requirePositiveOrUnset(spendingLimits.getDailySpendingLimit(), "dailySpendingLimit");
		requirePositiveOrUnset(spendingLimits.getWeeklySpendingLimit(), "weeklySpendingLimit");
		requirePositiveOrUnset(spendingLimits.getMonthlySpendingLimit(), "monthlySpendingLimit");
		requirePositiveOrUnset(spendingLimits.getTotalSpendingLimit(), "totalSpendingLimit");
		spendingLimits.setAutoImported(Boolean.FALSE);
		spendingLimits.setImportDate(null);
		requireTemplate().updateFirst(new Query(Criteria.where("_id").is(dealId)),
				new Update().set("spendingLimits", spendingLimits).set("dateModified", new Date()),
				GProviderDeal.class);
		LOGGER.info("Set by hand the spending limits " + spendingLimits + " of the deal id=" + dealId
				+ " of provider=" + deal.getProviderId());
		return requireDeal(dealId);
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
