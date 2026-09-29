package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
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
import lombok.RequiredArgsConstructor;

/**
 * Mongo backed {@link IGProviderDealService}. The repository and template are
 * resolved lazily: an application loading the LLM layer without MongoDB still
 * starts, its deals simply not being maintained.
 */
@Service
@RequiredArgsConstructor
public class GProviderDealServiceImpl implements IGProviderDealService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GProviderDealServiceImpl.class);
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

	/** A deal price of a configuration, with the coordinates it applies at. */
	private record PricedConfig(String dealId, String providerId, Set<String> secretCodes,
			GModelPricingConditions pricing) {
	}

	/**
	 * The deals' prices by configuration code, read on every model call without
	 * touching the store: rebuilt after each change made here and every
	 * {@code ai.gebo.llms.providerDeals.pricesRefreshMillis} for the changes made by
	 * other cluster instances. Null until first built.
	 */
	private volatile Map<String, List<PricedConfig>> pricesByConfig = null;

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
			KeyMove move = pullFromOtherDeals(providerId, deal.getId(), code);
			sources.addAll(move.sourceDealIds());
			deal.setModelPrices(mergePrices(deal.getModelPrices(), move.prices()));
			deal.getSecretCodes().add(code);
		}
		deal = requireRepository().insert(deal);
		dealsChanged();
		LOGGER.info("Created the deal id=" + deal.getId() + " of provider=" + providerId + " covering " + codes
				+ " with the prices of the configurations " + deal.getModelPrices().stream()
						.map(GProviderModelPrice::getConfigCode).toList());
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
		KeyMove move = pullFromOtherDeals(deal.getProviderId(), dealId, secretCode);
		List<String> sources = move.sourceDealIds();
		Update assignment = new Update().addToSet("secretCodes", secretCode).set("dateModified", new Date());
		if (!move.prices().isEmpty()) {
			assignment.set("modelPrices", mergePrices(deal.getModelPrices(), move.prices()));
		}
		requireTemplate().updateFirst(new Query(Criteria.where("_id").is(dealId)), assignment, GProviderDeal.class);
		dealsChanged();
		LOGGER.info((sources.isEmpty() ? "Assigned" : "Transferred") + " secretCode=" + secretCode
				+ " to the deal id=" + dealId + " of provider=" + deal.getProviderId()
				+ (sources.isEmpty() ? "" : " from " + sources) + (move.prices().isEmpty() ? ""
						: " with the prices of the configurations "
								+ move.prices().stream().map(GProviderModelPrice::getConfigCode).toList()));
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
	public GProviderDeal updateModelPricing(String dealId, String configCode, GModelPricingConditions pricing) {
		requireText(configCode, "configCode");
		GProviderDeal deal = requireDeal(dealId);
		IGConfigurableModel<?, ?> runtime = findRuntime(configCode);
		String modelCode = runtime != null ? runtime.safeGetModelCode() : null;
		if (pricing != null) {
			if (runtime == null) {
				throw new IllegalArgumentException("No model configuration with code=" + configCode + " is running");
			}
			GModelType type = runtime.getType();
			if (type == null || !deal.getProviderId().equals(type.getProviderId())) {
				throw new IllegalArgumentException("The model configuration " + configCode
						+ " does not run a model of provider=" + deal.getProviderId());
			}
			String secretCode = runtime.getConfig() != null ? runtime.getConfig().getApiSecretCode() : null;
			if (secretCode == null || !deal.getSecretCodes().contains(secretCode)) {
				throw new IllegalArgumentException("The model configuration " + configCode + " runs with the API key "
						+ secretCode + ", which the deal " + dealId + " does not cover: move the key into the deal first");
			}
			requireValidPricing(pricing);
		}
		Date now = new Date();
		List<GProviderModelPrice> prices = new ArrayList<>();
		if (deal.getModelPrices() != null) {
			deal.getModelPrices().stream().filter(x -> !configCode.equals(x.getConfigCode())).forEach(prices::add);
		}
		if (pricing != null) {
			prices.add(new GProviderModelPrice(configCode, modelCode, pricing, now));
		}
		requireTemplate().updateFirst(new Query(Criteria.where("_id").is(dealId)),
				new Update().set("modelPrices", prices).set("dateModified", now), GProviderDeal.class);
		dealsChanged();
		LOGGER.info((pricing != null ? "Set the price " + pricing + " of" : "Removed the price of")
				+ " configuration=" + configCode + " (model=" + modelCode + ") in the deal id=" + dealId
				+ " of provider=" + deal.getProviderId());
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
			return findConfigPricing(type != null ? type.getProviderId() : null,
					config != null ? config.getApiSecretCode() : null,
					config != null && config.getCode() != null ? config.getCode() : model.getCode());
		} catch (Throwable e) {
			LOGGER.error("Cannot read the deal pricing of model code=" + model.getCode(), e);
			return null;
		}
	}

	@Override
	public GModelPricingConditions findConfigPricing(String providerId, String secretCode, String configCode) {
		if (providerId == null || secretCode == null || configCode == null) {
			return null;
		}
		try {
			Map<String, List<PricedConfig>> snapshot = pricesByConfig;
			if (snapshot == null) {
				// Only before the first build, normally done at startup.
				refreshPricesSnapshotQuietly();
				snapshot = pricesByConfig;
			}
			List<PricedConfig> prices = snapshot != null ? snapshot.get(configCode) : null;
			PricedConfig found = null;
			if (prices != null) {
				for (PricedConfig price : prices) {
					if (providerId.equals(price.providerId()) && price.secretCodes().contains(secretCode)) {
						found = price;
						break;
					}
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Deal pricing of configuration=" + configCode + " provider=" + providerId + " secretCode="
						+ secretCode + ": " + (found != null ? "found in the deal id=" + found.dealId()
								: "none, the configured pricing applies"));
			}
			if (found != null && LOGGER.isTraceEnabled()) {
				LOGGER.trace("<DEAL_CONFIG_PRICING>" + found.pricing() + "</DEAL_CONFIG_PRICING>");
			}
			return found != null ? found.pricing() : null;
		} catch (Throwable e) {
			LOGGER.error("Cannot read the deal pricing of configuration=" + configCode + ", the configured pricing applies",
					e);
			return null;
		}
	}

	@Override
	public void refreshPricesSnapshot() {
		GProviderDealRepository repository = repositoryProvider.getIfAvailable();
		Map<String, List<PricedConfig>> snapshot = new HashMap<>();
		if (repository != null) {
			for (GProviderDeal deal : repository.findAll()) {
				if (deal.getModelPrices() == null) {
					continue;
				}
				Set<String> keys = deal.getSecretCodes() != null ? Set.copyOf(deal.getSecretCodes()) : Set.of();
				for (GProviderModelPrice price : deal.getModelPrices()) {
					if (price.getConfigCode() != null && price.getPricingConditions() != null) {
						snapshot.computeIfAbsent(price.getConfigCode(), x -> new ArrayList<>()).add(
								new PricedConfig(deal.getId(), deal.getProviderId(), keys, price.getPricingConditions()));
					}
				}
			}
		}
		Map<String, List<PricedConfig>> frozen = new HashMap<>();
		snapshot.forEach((code, prices) -> frozen.put(code, List.copyOf(prices)));
		pricesByConfig = Map.copyOf(frozen);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Rebuilt the deal prices snapshot: " + frozen.size() + " priced configurations");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<DEAL_PRICES_SNAPSHOT>" + frozen + "</DEAL_PRICES_SNAPSHOT>");
		}
	}

	/** {@link #refreshPricesSnapshot()}, never failing the operation it follows. */
	private void refreshPricesSnapshotQuietly() {
		try {
			refreshPricesSnapshot();
		} catch (Throwable e) {
			LOGGER.error("Cannot rebuild the deal prices snapshot, the previous one is kept", e);
		}
	}

	/** Builds the snapshot as soon as the application is up, before the first call. */
	@EventListener(ApplicationReadyEvent.class)
	public void buildPricesSnapshotAtStartup() {
		refreshPricesSnapshotQuietly();
	}

	/** Picks up the deal changes made by other cluster instances. */
	@Scheduled(initialDelayString = "${ai.gebo.llms.providerDeals.pricesRefreshMillis:60000}", fixedDelayString = "${ai.gebo.llms.providerDeals.pricesRefreshMillis:60000}")
	public void refreshPricesSnapshotPeriodically() {
		refreshPricesSnapshotQuietly();
	}

	/** The running model whose configuration has the given code, or null. */
	private IGConfigurableModel<?, ?> findRuntime(String configCode) {
		return runtimeDaos.orderedStream().flatMap(dao -> dao.getConfigurations().stream())
				.filter(x -> configCode.equals(x.getConfig() != null ? x.getConfig().getCode() : x.getCode()))
				.findFirst().orElse(null);
	}

	/** Codes of the model configurations of the provider running with the given API key. */
	private Set<String> configsRunningWith(String providerId, String secretCode) {
		Set<String> codes = new HashSet<>();
		runtimeDaos.orderedStream().flatMap(dao -> dao.getConfigurations().stream()).forEach(model -> {
			GModelType type = model.getType();
			GBaseModelConfig<?> config = model.getConfig();
			if (type != null && providerId.equals(type.getProviderId()) && config != null
					&& secretCode.equals(config.getApiSecretCode())) {
				codes.add(config.getCode() != null ? config.getCode() : model.getCode());
			}
		});
		return codes;
	}

	/** The prices of a deal with the moved ones added, replacing those of the same configurations. */
	private static List<GProviderModelPrice> mergePrices(List<GProviderModelPrice> current,
			List<GProviderModelPrice> moved) {
		Set<String> movedCodes = new HashSet<>();
		moved.forEach(x -> movedCodes.add(x.getConfigCode()));
		List<GProviderModelPrice> merged = new ArrayList<>();
		if (current != null) {
			current.stream().filter(x -> !movedCodes.contains(x.getConfigCode())).forEach(merged::add);
		}
		merged.addAll(moved);
		return merged;
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

	/** Rebuilds the prices snapshot after a change of the deals' keys or prices. */
	private void dealsChanged() {
		refreshPricesSnapshotQuietly();
	}

	private static void requirePositiveOrUnset(Double value, String name) {
		if (value != null && !(value > 0)) {
			throw new IllegalArgumentException(name + " must be positive, or unset for no limit");
		}
	}

	/** An API key moved away from other deals, with the prices of its configurations. */
	private record KeyMove(List<String> sourceDealIds, List<GProviderModelPrice> prices) {
	}

	/**
	 * Removes a key from every other deal of the provider, together with the prices
	 * those deals give to the model configurations running with it.
	 */
	private KeyMove pullFromOtherDeals(String providerId, String dealId, String secretCode) {
		Query holders = new Query(Criteria.where("providerId").is(providerId).and("_id").ne(dealId)
				.and("secretCodes").is(secretCode));
		List<GProviderDeal> sources = requireTemplate().find(holders, GProviderDeal.class);
		if (sources.isEmpty()) {
			return new KeyMove(List.of(), List.of());
		}
		Set<String> configs = configsRunningWith(providerId, secretCode);
		List<String> ids = new ArrayList<>();
		List<GProviderModelPrice> moved = new ArrayList<>();
		for (GProviderDeal source : sources) {
			ids.add(source.getId());
			List<GProviderModelPrice> kept = new ArrayList<>();
			if (source.getModelPrices() != null) {
				for (GProviderModelPrice price : source.getModelPrices()) {
					(configs.contains(price.getConfigCode()) ? moved : kept).add(price);
				}
			}
			requireTemplate().updateFirst(new Query(Criteria.where("_id").is(source.getId())),
					new Update().pull("secretCodes", secretCode).set("modelPrices", kept).set("dateModified", new Date()),
					GProviderDeal.class);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("secretCode=" + secretCode + " moved away from the deals " + ids + " of provider=" + providerId
					+ " with the prices of the configurations " + moved.stream().map(GProviderModelPrice::getConfigCode).toList());
		}
		return new KeyMove(ids, moved);
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
