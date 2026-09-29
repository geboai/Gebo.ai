package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.llms.abstraction.layer.model.GProviderDeal;
import ai.gebo.llms.abstraction.layer.model.GProviderModelPrice;
import ai.gebo.llms.abstraction.layer.model.GProviderModelPriceInfo;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableImageModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableRankerModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTextToSpeechModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTranscriptModel;
import ai.gebo.llms.abstraction.layer.services.IGProviderDealService;
import ai.gebo.llms.abstraction.layer.services.IGProviderModelPricesService;
import ai.gebo.llms.abstraction.layer.services.IGRuntimeModelConfigurationDao;
import ai.gebo.model.ModelType;
import lombok.AllArgsConstructor;

/**
 * {@link IGProviderModelPricesService} over every runtime model DAO: the models
 * are read as they run, so the listing covers every model family.
 */
@Service
@AllArgsConstructor
public class GProviderModelPricesServiceImpl implements IGProviderModelPricesService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GProviderModelPricesServiceImpl.class);
	private final ObjectProvider<IGRuntimeModelConfigurationDao<?, ?>> runtimeDaos;
	private final IGProviderDealService dealService;

	@Override
	public List<GProviderModelPriceInfo> getProviderModelPrices(String providerId) {
		if (providerId == null || providerId.isBlank()) {
			throw new IllegalArgumentException("Missing providerId");
		}
		List<GProviderDeal> deals = dealService.findDeals(providerId);
		Map<String, GProviderDeal> dealOfKey = new HashMap<>();
		for (GProviderDeal deal : deals) {
			for (String code : deal.getSecretCodes()) {
				dealOfKey.put(code, deal);
			}
		}
		List<GProviderModelPriceInfo> rows = new ArrayList<>();
		// The deal prices applied: "dealId|configCode".
		Set<String> applied = new HashSet<>();
		runtimeDaos.orderedStream().forEach(dao -> {
			for (IGConfigurableModel<?, ?> model : dao.getConfigurations()) {
				GModelType type = model.getType();
				if (type == null || !providerId.equals(type.getProviderId())) {
					continue;
				}
				GBaseModelConfig<?> config = model.getConfig();
				String configCode = config != null && config.getCode() != null ? config.getCode() : model.getCode();
				String secretCode = config != null ? config.getApiSecretCode() : null;
				GProviderDeal deal = secretCode != null ? dealOfKey.get(secretCode) : null;
				GProviderModelPriceInfo row = row(providerId, deal, configCode);
				row.setConfigDescription(config != null ? config.getDescription() : model.getDescription());
				row.setModelCode(model.safeGetModelCode());
				row.setModelType(familyOf(model));
				row.setSecretCode(secretCode);
				if (config != null && config.getPricingConditions() != null) {
					row.setConfiguredPricing(config.getPricingConditions());
					row.setConfiguredPricingSource(GProviderModelPriceInfo.PricingSource.CONFIGURATION);
				} else if (config != null && config.getChoosedModel() != null
						&& config.getChoosedModel().getPricingConditions() != null) {
					row.setConfiguredPricing(config.getChoosedModel().getPricingConditions());
					row.setConfiguredPricingSource(GProviderModelPriceInfo.PricingSource.PROVIDER_API);
				}
				if (deal != null) {
					applied.add(deal.getId() + "|" + configCode);
				}
				rows.add(row);
			}
		});
		// The deal prices no longer applied, for the admin to see and drop.
		for (GProviderDeal deal : deals) {
			if (deal.getModelPrices() == null) {
				continue;
			}
			for (GProviderModelPrice price : deal.getModelPrices()) {
				if (price.getConfigCode() != null && !applied.contains(deal.getId() + "|" + price.getConfigCode())) {
					GProviderModelPriceInfo row = row(providerId, deal, price.getConfigCode());
					row.setModelCode(price.getModelCode());
					row.setStale(true);
					rows.add(row);
				}
			}
		}
		rows.sort(Comparator.comparing(GProviderModelPriceInfo::getDealId, Comparator.nullsLast(String::compareTo))
				.thenComparing(GProviderModelPriceInfo::isStale)
				.thenComparing(GProviderModelPriceInfo::getConfigCode, Comparator.nullsLast(String::compareTo)));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getProviderModelPrices(providerId=" + providerId + ") found " + rows.size()
					+ " configurations over " + deals.size() + " deals, "
					+ rows.stream().filter(x -> x.getDealPricing() != null && !x.isStale()).count()
					+ " priced by their deal, " + rows.stream().filter(GProviderModelPriceInfo::isStale).count()
					+ " stale deal prices");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<PROVIDER_MODEL_PRICES>" + rows + "</PROVIDER_MODEL_PRICES>");
		}
		return rows;
	}

	private static GProviderModelPriceInfo row(String providerId, GProviderDeal deal, String configCode) {
		GProviderModelPriceInfo row = new GProviderModelPriceInfo();
		row.setProviderId(providerId);
		row.setConfigCode(configCode);
		if (deal != null) {
			row.setDealId(deal.getId());
			row.setDealDescription(deal.getDescription());
			row.setDealPricing(deal.configPricing(configCode));
		}
		return row;
	}

	private static ModelType familyOf(IGConfigurableModel<?, ?> model) {
		if (model instanceof IGConfigurableChatModel) {
			return ModelType.CHAT;
		} else if (model instanceof IGConfigurableEmbeddingModel) {
			return ModelType.EMBEDDING;
		} else if (model instanceof IGConfigurableRankerModel) {
			return ModelType.RANKER;
		} else if (model instanceof IGConfigurableImageModel) {
			return ModelType.IMAGE;
		} else if (model instanceof IGConfigurableTextToSpeechModel) {
			return ModelType.TTS;
		} else if (model instanceof IGConfigurableTranscriptModel) {
			return ModelType.TRANSCRIPT;
		}
		return null;
	}
}
