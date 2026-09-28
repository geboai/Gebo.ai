package ai.gebo.llms.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.model.GEmbeddingModelType;
import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;
import ai.gebo.llms.abstraction.layer.model.GProviderDeal;
import ai.gebo.llms.abstraction.layer.model.GProviderModelPrice;
import ai.gebo.llms.abstraction.layer.model.GProviderModelPriceInfo;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableModel;
import ai.gebo.llms.abstraction.layer.services.IGProviderDealPricedModel;
import ai.gebo.llms.abstraction.layer.services.IGProviderDealService;
import ai.gebo.llms.abstraction.layer.services.IGRuntimeModelConfigurationDao;
import ai.gebo.model.ModelType;

class GProviderModelPricesServiceImplTest {

	private static GModelPricingConditions pricing(double in) {
		GModelPricingConditions pricing = new GModelPricingConditions();
		pricing.setCurrencyCode("USD");
		pricing.setInputMtokenPrice(in);
		return pricing;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static IGConfigurableChatModel chat(String providerId, String configCode, String secretCode,
			String modelCode, GModelPricingConditions configured) {
		IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
		GChatModelType type = new GChatModelType();
		type.setProviderId(providerId);
		GBaseChatModelConfig config = new GBaseChatModelConfig();
		config.setCode(configCode);
		config.setApiSecretCode(secretCode);
		when(model.getType()).thenReturn(type);
		when(model.getConfig()).thenReturn(config);
		when(model.safeGetModelCode()).thenReturn(modelCode);
		when(model.getConfiguredPricingConditions()).thenReturn(configured);
		return model;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static IGConfigurableEmbeddingModel embedding(String providerId, String configCode, String secretCode,
			String modelCode) {
		IGConfigurableEmbeddingModel model = mock(IGConfigurableEmbeddingModel.class);
		GEmbeddingModelType type = new GEmbeddingModelType();
		type.setProviderId(providerId);
		GBaseEmbeddingModelConfig config = new GBaseEmbeddingModelConfig();
		config.setCode(configCode);
		config.setApiSecretCode(secretCode);
		when(model.getType()).thenReturn(type);
		when(model.getConfig()).thenReturn(config);
		when(model.safeGetModelCode()).thenReturn(modelCode);
		return model;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ObjectProvider<IGRuntimeModelConfigurationDao<?, ?>> daos(List... models) {
		ObjectProvider<IGRuntimeModelConfigurationDao<?, ?>> provider = mock(ObjectProvider.class);
		IGRuntimeModelConfigurationDao[] daos = Arrays.stream(models).map(list -> {
			IGRuntimeModelConfigurationDao dao = mock(IGRuntimeModelConfigurationDao.class);
			when(dao.getConfigurations()).thenReturn(list);
			return dao;
		}).toArray(IGRuntimeModelConfigurationDao[]::new);
		when(provider.orderedStream()).thenAnswer(x -> Arrays.stream(daos));
		return provider;
	}

	@Test
	void theProviderModelsAreListedPerDealAndModelCodeWithBothPrices() {
		GProviderDeal deal = new GProviderDeal();
		deal.setId("openai-1");
		deal.setProviderId("openai");
		deal.setSecretCodes(new java.util.ArrayList<>(List.of("k1")));
		deal.getModelPrices().add(new GProviderModelPrice("gpt-4.1", pricing(9d), null));
		deal.getModelPrices().add(new GProviderModelPrice("retired-model", pricing(7d), null));
		IGProviderDealService deals = mock(IGProviderDealService.class);
		when(deals.findDeals("openai")).thenReturn(List.of(deal));

		List<GProviderModelPriceInfo> rows = new GProviderModelPricesServiceImpl(daos(
				List.of(chat("openai", "chat-a", "k1", "gpt-4.1", pricing(2d)),
						chat("openai", "chat-b", "k1", "gpt-4.1", null),
						chat("openai", "chat-c", "k-uncovered", "gpt-4.1", null),
						chat("mistralai", "chat-m", "k1", "mistral-large", null)),
				List.of(embedding("openai", "emb-a", "k1", "text-embedding-3-small"))), deals)
				.getProviderModelPrices("openai");

		assertEquals(4, rows.size());
		GProviderModelPriceInfo gpt = rows.get(0);
		assertEquals("gpt-4.1", gpt.getModelCode());
		assertEquals("openai-1", gpt.getDealId());
		assertEquals(List.of("chat-a", "chat-b"), gpt.getModelConfigCodes());
		assertEquals(List.of(ModelType.CHAT), gpt.getModelTypes());
		assertEquals(2d, gpt.getConfiguredPricing().getInputMtokenPrice());
		assertEquals(9d, gpt.getDealPricing().getInputMtokenPrice());
		// A key no deal covers: listed, with no deal to price it.
		GProviderModelPriceInfo uncovered = rows.get(1);
		assertEquals("gpt-4.1", uncovered.getModelCode());
		assertNull(uncovered.getDealId());
		assertEquals(List.of("k-uncovered"), uncovered.getSecretCodes());
		// A deal price of a model no longer configured, for the admin to drop.
		GProviderModelPriceInfo retired = rows.get(2);
		assertEquals("retired-model", retired.getModelCode());
		assertEquals(List.of(), retired.getModelConfigCodes());
		assertEquals(7d, retired.getDealPricing().getInputMtokenPrice());
		assertEquals(List.of(ModelType.EMBEDDING), rows.get(3).getModelTypes());
	}

	@Test
	void aModelIsPricedByItsDealElseByItsConfiguration() {
		IGConfigurableChatModel model = chat("openai", "chat-a", "k1", "gpt-4.1", pricing(2d));
		IGProviderDealService deals = mock(IGProviderDealService.class);
		GModelPricingConditions dealPricing = pricing(9d);
		when(deals.findModelPricing((IGConfigurableModel<?, ?>) model)).thenReturn(dealPricing, (GModelPricingConditions) null);

		assertSame(dealPricing, IGProviderDealPricedModel.dealOrConfiguredPricing(deals, model));
		assertEquals(2d, IGProviderDealPricedModel.dealOrConfiguredPricing(deals, model).getInputMtokenPrice());
		assertEquals(2d, IGProviderDealPricedModel.dealOrConfiguredPricing(null, model).getInputMtokenPrice());
	}
}
