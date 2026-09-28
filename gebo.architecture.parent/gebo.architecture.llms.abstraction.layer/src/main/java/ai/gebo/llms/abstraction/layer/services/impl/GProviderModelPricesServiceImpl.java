package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
		Map<String, GProviderModelPriceInfo> rows = new LinkedHashMap<>();
		runtimeDaos.orderedStream().forEach(dao -> {
			for (IGConfigurableModel<?, ?> model : dao.getConfigurations()) {
				GModelType type = model.getType();
				if (type == null || !providerId.equals(type.getProviderId())) {
					continue;
				}
				GBaseModelConfig<?> config = model.getConfig();
				String secretCode = config != null ? config.getApiSecretCode() : null;
				GProviderDeal deal = secretCode != null ? dealOfKey.get(secretCode) : null;
				GProviderModelPriceInfo row = row(rows, providerId, deal, model.safeGetModelCode());
				ModelType family = familyOf(model);
				if (family != null && !row.getModelTypes().contains(family)) {
					row.getModelTypes().add(family);
				}
				if (config != null && config.getCode() != null) {
					row.getModelConfigCodes().add(config.getCode());
				}
				if (secretCode != null && !row.getSecretCodes().contains(secretCode)) {
					row.getSecretCodes().add(secretCode);
				}
				if (row.getConfiguredPricing() == null) {
					row.setConfiguredPricing(model.getConfiguredPricingConditions());
				}
			}
		});
		// The deal prices of models no longer configured, for the admin to see and drop.
		for (GProviderDeal deal : deals) {
			if (deal.getModelPrices() != null) {
				for (GProviderModelPrice price : deal.getModelPrices()) {
					row(rows, providerId, deal, price.getModelCode());
				}
			}
		}
		List<GProviderModelPriceInfo> result = new ArrayList<>(rows.values());
		result.sort(Comparator.comparing(GProviderModelPriceInfo::getModelCode, Comparator.nullsLast(String::compareTo))
				.thenComparing(GProviderModelPriceInfo::getDealId, Comparator.nullsLast(String::compareTo)));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getProviderModelPrices(providerId=" + providerId + ") found " + result.size()
					+ " models over " + deals.size() + " deals, "
					+ result.stream().filter(x -> x.getDealPricing() != null).count() + " priced by their deal");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<PROVIDER_MODEL_PRICES>" + result + "</PROVIDER_MODEL_PRICES>");
		}
		return result;
	}

	private static GProviderModelPriceInfo row(Map<String, GProviderModelPriceInfo> rows, String providerId,
			GProviderDeal deal, String modelCode) {
		String dealId = deal != null ? deal.getId() : null;
		return rows.computeIfAbsent(dealId + "|" + modelCode, x -> {
			GProviderModelPriceInfo row = new GProviderModelPriceInfo();
			row.setProviderId(providerId);
			row.setDealId(dealId);
			row.setDealDescription(deal != null ? deal.getDescription() : null);
			row.setModelCode(modelCode);
			row.setDealPricing(deal != null ? deal.modelPricing(modelCode) : null);
			return row;
		});
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
