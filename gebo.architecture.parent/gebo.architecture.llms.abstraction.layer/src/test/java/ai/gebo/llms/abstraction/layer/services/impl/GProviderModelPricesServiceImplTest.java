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

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
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

	/** A chat model whose chosen model carries the pricing of the provider's API, if any. */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static IGConfigurableChatModel chat(String providerId, String configCode, String secretCode,
			String modelCode, GModelPricingConditions fromProviderApi) {
		IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
		GChatModelType type = new GChatModelType();
		type.setProviderId(providerId);
		GBaseChatModelConfig config = new GBaseChatModelConfig();
		config.setCode(configCode);
		config.setDescription("config " + configCode);
		config.setApiSecretCode(secretCode);
		GBaseChatModelChoice choice = new GBaseChatModelChoice();
		choice.setCode(modelCode);
		choice.setPricingConditions(fromProviderApi);
		config.setChoosedModel(choice);
		when(model.getType()).thenReturn(type);
		when(model.getConfig()).thenReturn(config);
		when(model.safeGetModelCode()).thenReturn(modelCode);
		when(model.getProviderApiPricingConditions()).thenReturn(fromProviderApi);
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
	void theProviderModelsAreListedPerDealWithTheirConfigurationsAndPrices() {
		GProviderDeal deal = new GProviderDeal();
		deal.setId("openai-1");
		deal.setProviderId("openai");
		deal.setSecretCodes(new java.util.ArrayList<>(List.of("k1", GProviderDeal.NO_API_KEY)));
		deal.getModelPrices().add(new GProviderModelPrice("gpt-4.1", pricing(9d), null, Boolean.TRUE));
		deal.getModelPrices().add(new GProviderModelPrice("priced-ahead", pricing(7d), null, Boolean.FALSE));
		IGProviderDealService deals = mock(IGProviderDealService.class);
		when(deals.findDeals("openai")).thenReturn(List.of(deal));

		List<GProviderModelPriceInfo> rows = new GProviderModelPricesServiceImpl(daos(
				List.of(chat("openai", "chat-a", "k1", "gpt-4.1", pricing(2d)),
						chat("openai", "chat-b", "k1", "gpt-4.1", null),
						chat("openai", "chat-local", null, "gpt-4.1", null),
						chat("openai", "chat-c", "k-uncovered", "gpt-4.1", null),
						chat("mistralai", "chat-m", "k1", "mistral-large", null)),
				List.of(embedding("openai", "emb-a", "k1", "text-embedding-3-small"))), deals)
				.getProviderModelPrices("openai");

		// One row per deal and model, then the models whose key no deal covers.
		assertEquals(List.of("gpt-4.1", "priced-ahead", "text-embedding-3-small", "gpt-4.1"),
				rows.stream().map(GProviderModelPriceInfo::getModelCode).toList());
		GProviderModelPriceInfo gpt = rows.get(0);
		assertEquals("openai-1", gpt.getDealId());
		// The configurations running it with the deal's keys, the keyless one included.
		assertEquals(List.of("chat-a", "chat-b", "chat-local"),
				gpt.getConfigurations().stream().map(x -> x.getConfigCode()).toList());
		assertEquals(ModelType.CHAT, gpt.getConfigurations().get(0).getModelType());
		assertEquals("config chat-a", gpt.getConfigurations().get(0).getDescription());
		assertEquals(2d, gpt.getProviderApiPricing().getInputMtokenPrice());
		assertEquals(9d, gpt.getDealPricing().getInputMtokenPrice());
		assertEquals(Boolean.TRUE, gpt.getDealPricingAutoImported());
		// A model priced ahead, no configuration runs it yet.
		assertEquals(List.of(), rows.get(1).getConfigurations());
		assertEquals(Boolean.FALSE, rows.get(1).getDealPricingAutoImported());
		assertEquals(ModelType.EMBEDDING, rows.get(2).getConfigurations().get(0).getModelType());
		assertNull(rows.get(2).getDealPricing());
		// A key no deal covers: listed, with no deal to price it.
		assertNull(rows.get(3).getDealId());
		assertEquals("k-uncovered", rows.get(3).getConfigurations().get(0).getSecretCode());
	}

	@Test
	void aModelIsPricedByItsDealElseByTheProviderApi() {
		IGConfigurableChatModel model = chat("openai", "chat-a", "k1", "gpt-4.1", pricing(2d));
		IGProviderDealService deals = mock(IGProviderDealService.class);
		GModelPricingConditions dealPricing = pricing(9d);
		when(deals.findModelPricing((IGConfigurableModel<?, ?>) model)).thenReturn(dealPricing,
				(GModelPricingConditions) null);

		assertSame(dealPricing, IGProviderDealPricedModel.dealOrProviderApiPricing(deals, model));
		assertEquals(2d, IGProviderDealPricedModel.dealOrProviderApiPricing(deals, model).getInputMtokenPrice());
		assertEquals(2d, IGProviderDealPricedModel.dealOrProviderApiPricing(null, model).getInputMtokenPrice());
	}
}
