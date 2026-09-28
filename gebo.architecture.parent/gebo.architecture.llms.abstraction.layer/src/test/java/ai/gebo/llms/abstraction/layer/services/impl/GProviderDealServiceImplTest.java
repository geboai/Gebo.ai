package ai.gebo.llms.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.bson.BsonString;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.UpdateResult;

import ai.gebo.llms.abstraction.layer.model.GProviderDeal;
import ai.gebo.llms.abstraction.layer.model.GProviderKeyLimit;
import ai.gebo.llms.abstraction.layer.model.GProviderSpendingLimits;
import ai.gebo.llms.abstraction.layer.repository.GProviderDealRepository;
import ai.gebo.llms.abstraction.layer.services.IGProviderKeyLimitsReader;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

class GProviderDealServiceImplTest {

	@SuppressWarnings("unchecked")
	private static <T> ObjectProvider<T> provider(T value) {
		ObjectProvider<T> provider = mock(ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(value);
		return provider;
	}

	@SuppressWarnings("unchecked")
	private static ObjectProvider<IGProviderKeyLimitsReader> readers(IGProviderKeyLimitsReader... readers) {
		ObjectProvider<IGProviderKeyLimitsReader> provider = mock(ObjectProvider.class);
		when(provider.orderedStream()).thenAnswer(x -> java.util.Arrays.stream(readers));
		return provider;
	}

	/** Runtime DAOs running one model per given provider. */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ObjectProvider<ai.gebo.llms.abstraction.layer.services.IGRuntimeModelConfigurationDao<?, ?>> daos(
			String... modelProviders) {
		java.util.List models = new java.util.ArrayList();
		for (String providerId : modelProviders) {
			ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel model = mock(
					ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel.class);
			ai.gebo.llms.abstraction.layer.model.GChatModelType type = new ai.gebo.llms.abstraction.layer.model.GChatModelType();
			type.setProviderId(providerId);
			ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig config = new ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig();
			config.setCode(providerId + "-chat");
			when(model.getType()).thenReturn(type);
			when(model.getConfig()).thenReturn(config);
			models.add(model);
		}
		ai.gebo.llms.abstraction.layer.services.IGRuntimeModelConfigurationDao dao = mock(
				ai.gebo.llms.abstraction.layer.services.IGRuntimeModelConfigurationDao.class);
		when(dao.getConfigurations()).thenReturn(models);
		ObjectProvider provider = mock(ObjectProvider.class);
		when(provider.orderedStream()).thenAnswer(x -> java.util.stream.Stream.of(dao));
		return provider;
	}

	@Test
	void theLastDealOfAProviderWithConfiguredModelsCannotBeDeleted() {
		GProviderDealRepository repository = mock(GProviderDealRepository.class);
		GProviderDeal deal = deal("openai");
		deal.setId("openai-1");
		when(repository.findById("openai-1")).thenReturn(Optional.of(deal));
		when(repository.findByProviderId("openai")).thenReturn(java.util.List.of(deal));
		GProviderDealServiceImpl service = new GProviderDealServiceImpl(provider(repository),
				provider(mock(MongoTemplate.class)), provider(null), daos("openai", "mistralai"), readers());

		assertThrows(IllegalStateException.class, () -> service.deleteDeal("openai-1"));
		verify(repository, never()).deleteById(any());
	}

	@Test
	void anEmptyDealCanBeDeletedWhenAnotherDealRemainsOrNoModelIsConfigured() {
		GProviderDealRepository repository = mock(GProviderDealRepository.class);
		GProviderDeal deal = deal("openai");
		deal.setId("openai-1");
		GProviderDeal other = deal("openai", "k1");
		other.setId("openai-2");
		when(repository.findById("openai-1")).thenReturn(Optional.of(deal));
		when(repository.findByProviderId("openai")).thenReturn(java.util.List.of(deal, other),
				java.util.List.of(deal));

		new GProviderDealServiceImpl(provider(repository), provider(mock(MongoTemplate.class)), provider(null),
				daos("openai"), readers()).deleteDeal("openai-1");
		new GProviderDealServiceImpl(provider(repository), provider(mock(MongoTemplate.class)), provider(null),
				daos("mistralai"), readers()).deleteDeal("openai-1");

		verify(repository, org.mockito.Mockito.times(2)).deleteById("openai-1");
	}

	/** A reader answering with the limit of each clear key; a missing key fails. */
	private static IGProviderKeyLimitsReader reader(String providerId,
			java.util.Map<String, GProviderKeyLimit> limits) {
		return new IGProviderKeyLimitsReader() {
			@Override
			public String getProviderId() {
				return providerId;
			}

			@Override
			public GProviderKeyLimit readLimit(String clearApiKey) {
				if (!limits.containsKey(clearApiKey)) {
					throw new IllegalStateException("provider down");
				}
				return limits.get(clearApiKey);
			}
		};
	}

	/** Secrets whose TOKEN content is "clear-" followed by the code. */
	private static IGeboSecretsAccessService tokens() throws Exception {
		IGeboSecretsAccessService secrets = mock(IGeboSecretsAccessService.class);
		when(secrets.getSecretContentById(any())).thenAnswer(x -> {
			GeboTokenContent token = new GeboTokenContent();
			token.setToken("clear-" + x.getArgument(0));
			return token;
		});
		return secrets;
	}

	private static GProviderSpendingLimits storedLimits(MongoTemplate mongo) {
		ArgumentCaptor<Update> update = ArgumentCaptor.forClass(Update.class);
		verify(mongo).updateFirst(any(Query.class), update.capture(), eq(GProviderDeal.class));
		return (GProviderSpendingLimits) ((org.bson.Document) update.getValue().getUpdateObject().get("$set"))
				.get("spendingLimits");
	}

	private static GProviderDealRepository repositoryWith(GProviderDeal deal) {
		GProviderDealRepository repository = mock(GProviderDealRepository.class);
		when(repository.findById(deal.getId())).thenReturn(Optional.of(deal));
		return repository;
	}

	@Test
	void theKeysLimitsAreSummedPerPeriodAndFlaggedAutoImported() throws Exception {
		GProviderDeal deal = deal("openrouter.ai", "k1", "k2", "k3");
		deal.setId("openrouter.ai-1");
		MongoTemplate mongo = mock(MongoTemplate.class);
		IGProviderKeyLimitsReader reader = reader("openrouter.ai",
				java.util.Map.of("clear-k1", new GProviderKeyLimit("USD", 10d, GProviderKeyLimit.Period.MONTHLY),
						"clear-k2", new GProviderKeyLimit("USD", 5d, GProviderKeyLimit.Period.MONTHLY), "clear-k3",
						new GProviderKeyLimit("USD", 1d, GProviderKeyLimit.Period.DAILY)));

		new GProviderDealServiceImpl(provider(repositoryWith(deal)), provider(mongo), provider(tokens()),
				daos(), readers(reader("other", java.util.Map.of()), reader)).refreshImportedLimits("openrouter.ai-1");

		GProviderSpendingLimits limits = storedLimits(mongo);
		org.junit.jupiter.api.Assertions.assertEquals("USD", limits.getCurrencyCode());
		org.junit.jupiter.api.Assertions.assertEquals(15d, limits.getMonthlySpendingLimit());
		org.junit.jupiter.api.Assertions.assertEquals(1d, limits.getDailySpendingLimit());
		assertNull(limits.getWeeklySpendingLimit());
		assertNull(limits.getTotalSpendingLimit());
		org.junit.jupiter.api.Assertions.assertEquals(Boolean.TRUE, limits.getAutoImported());
		org.junit.jupiter.api.Assertions.assertNotNull(limits.getImportDate());
	}

	@Test
	void aKeyWithoutLimitLeavesTheDealUnlimited() throws Exception {
		GProviderDeal deal = deal("openrouter.ai", "k1", "k2");
		deal.setId("openrouter.ai-1");
		MongoTemplate mongo = mock(MongoTemplate.class);
		IGProviderKeyLimitsReader reader = reader("openrouter.ai",
				java.util.Map.of("clear-k1", new GProviderKeyLimit("USD", 10d, GProviderKeyLimit.Period.MONTHLY),
						"clear-k2", new GProviderKeyLimit("USD", null, null)));

		new GProviderDealServiceImpl(provider(repositoryWith(deal)), provider(mongo), provider(tokens()),
				daos(), readers(reader)).refreshImportedLimits("openrouter.ai-1");

		GProviderSpendingLimits limits = storedLimits(mongo);
		assertNull(limits.getMonthlySpendingLimit());
		org.junit.jupiter.api.Assertions.assertEquals(Boolean.TRUE, limits.getAutoImported());
	}

	@Test
	void limitsSetByAnAdminAreNeverOverwritten() throws Exception {
		GProviderDeal deal = deal("openrouter.ai", "k1");
		deal.setId("openrouter.ai-1");
		GProviderSpendingLimits manual = new GProviderSpendingLimits();
		manual.setAutoImported(Boolean.FALSE);
		deal.setSpendingLimits(manual);
		MongoTemplate mongo = mock(MongoTemplate.class);

		new GProviderDealServiceImpl(provider(repositoryWith(deal)), provider(mongo), provider(tokens()),
				daos(), readers(reader("openrouter.ai", java.util.Map.of("clear-k1",
						new GProviderKeyLimit("USD", 10d, GProviderKeyLimit.Period.MONTHLY)))))
				.refreshImportedLimits("openrouter.ai-1");

		verify(mongo, never()).updateFirst(any(Query.class), any(Update.class), eq(GProviderDeal.class));
	}

	@Test
	void aProviderWithoutReaderOrUnreadableLeavesTheLimitsUnchanged() throws Exception {
		GProviderDeal deal = deal("openrouter.ai", "k1");
		deal.setId("openrouter.ai-1");
		MongoTemplate mongo = mock(MongoTemplate.class);

		new GProviderDealServiceImpl(provider(repositoryWith(deal)), provider(mongo), provider(tokens()), daos(), readers())
				.refreshImportedLimits("openrouter.ai-1");
		// The reader fails on k1: best effort, nothing thrown nor stored.
		new GProviderDealServiceImpl(provider(repositoryWith(deal)), provider(mongo), provider(tokens()),
				daos(), readers(reader("openrouter.ai", java.util.Map.of()))).refreshImportedLimits("openrouter.ai-1");

		verify(mongo, never()).updateFirst(any(Query.class), any(Update.class), eq(GProviderDeal.class));
	}

	@Test
	void manualLimitsAreFlaggedNotAutoImported() throws Exception {
		GProviderDeal deal = deal("openrouter.ai", "k1");
		deal.setId("openrouter.ai-1");
		MongoTemplate mongo = mock(MongoTemplate.class);
		GProviderSpendingLimits manual = new GProviderSpendingLimits();
		manual.setCurrencyCode("USD");
		manual.setMonthlySpendingLimit(100d);
		manual.setAutoImported(Boolean.TRUE);
		GProviderDealServiceImpl service = new GProviderDealServiceImpl(provider(repositoryWith(deal)),
				provider(mongo), provider(tokens()), daos(), readers());

		service.updateSpendingLimits("openrouter.ai-1", manual);

		org.junit.jupiter.api.Assertions.assertEquals(Boolean.FALSE, storedLimits(mongo).getAutoImported());
		GProviderSpendingLimits invalid = new GProviderSpendingLimits();
		invalid.setCurrencyCode("USD");
		invalid.setDailySpendingLimit(0d);
		assertThrows(IllegalArgumentException.class, () -> service.updateSpendingLimits("openrouter.ai-1", invalid));
	}

	private static ai.gebo.llms.abstraction.layer.model.GModelPricingConditions pricing(String currency, Double in,
			Double out) {
		ai.gebo.llms.abstraction.layer.model.GModelPricingConditions pricing = new ai.gebo.llms.abstraction.layer.model.GModelPricingConditions();
		pricing.setCurrencyCode(currency);
		pricing.setInputMtokenPrice(in);
		pricing.setOutputMtokenPrice(out);
		return pricing;
	}

	@SuppressWarnings("unchecked")
	@Test
	void aModelPriceReplacesTheDealsPreviousOneAndNullRemovesIt() throws Exception {
		GProviderDeal deal = deal("openai", "k1");
		deal.setId("openai-1");
		deal.getModelPrices().add(new ai.gebo.llms.abstraction.layer.model.GProviderModelPrice("gpt-4.1",
				pricing("USD", 1d, 2d), null));
		deal.getModelPrices().add(new ai.gebo.llms.abstraction.layer.model.GProviderModelPrice("gpt-4o",
				pricing("USD", 3d, 4d), null));
		MongoTemplate mongo = mock(MongoTemplate.class);
		GProviderDealServiceImpl service = new GProviderDealServiceImpl(provider(repositoryWith(deal)),
				provider(mongo), provider(null), daos(), readers());

		service.updateModelPricing("openai-1", "gpt-4.1", pricing("USD", 5d, 6d));
		service.updateModelPricing("openai-1", "gpt-4o", null);

		ArgumentCaptor<Update> updates = ArgumentCaptor.forClass(Update.class);
		verify(mongo, org.mockito.Mockito.times(2)).updateFirst(any(Query.class), updates.capture(),
				eq(GProviderDeal.class));
		java.util.List<ai.gebo.llms.abstraction.layer.model.GProviderModelPrice> replaced = (java.util.List<ai.gebo.llms.abstraction.layer.model.GProviderModelPrice>) ((org.bson.Document) updates
				.getAllValues().get(0).getUpdateObject().get("$set")).get("modelPrices");
		org.junit.jupiter.api.Assertions.assertEquals(java.util.List.of("gpt-4o", "gpt-4.1"),
				replaced.stream().map(x -> x.getModelCode()).toList());
		org.junit.jupiter.api.Assertions.assertEquals(5d, replaced.get(1).getPricingConditions().getInputMtokenPrice());
		java.util.List<ai.gebo.llms.abstraction.layer.model.GProviderModelPrice> removed = (java.util.List<ai.gebo.llms.abstraction.layer.model.GProviderModelPrice>) ((org.bson.Document) updates
				.getAllValues().get(1).getUpdateObject().get("$set")).get("modelPrices");
		org.junit.jupiter.api.Assertions.assertEquals(java.util.List.of("gpt-4.1"),
				removed.stream().map(x -> x.getModelCode()).toList());
	}

	@Test
	void invalidModelPricesAreRejected() {
		GProviderDeal deal = deal("openai", "k1");
		deal.setId("openai-1");
		MongoTemplate mongo = mock(MongoTemplate.class);
		GProviderDealServiceImpl service = new GProviderDealServiceImpl(provider(repositoryWith(deal)),
				provider(mongo), provider(null), daos(), readers());

		assertThrows(IllegalArgumentException.class,
				() -> service.updateModelPricing("openai-1", "gpt-4.1", pricing(null, 1d, 2d)));
		assertThrows(IllegalArgumentException.class,
				() -> service.updateModelPricing("openai-1", "gpt-4.1", pricing("USD", -1d, 2d)));
		assertThrows(IllegalArgumentException.class,
				() -> service.updateModelPricing("openai-1", "gpt-4.1", pricing("USD", null, null)));
		assertThrows(IllegalArgumentException.class,
				() -> service.updateModelPricing("openai-1", " ", pricing("USD", 1d, 2d)));
		verify(mongo, never()).updateFirst(any(Query.class), any(Update.class), eq(GProviderDeal.class));
	}

	@Test
	void theModelPriceIsFoundAtTheKeyProviderAndModelCoordinatesAndCached() {
		GProviderDeal deal = deal("openai", "k1");
		deal.setId("openai-1");
		deal.getModelPrices().add(new ai.gebo.llms.abstraction.layer.model.GProviderModelPrice("gpt-4.1",
				pricing("USD", 1d, 2d), null));
		GProviderDealRepository repository = repositoryWith(deal);
		when(repository.findByProviderIdAndSecretCode("openai", "k1")).thenReturn(Optional.of(deal));
		GProviderDealServiceImpl service = new GProviderDealServiceImpl(provider(repository),
				provider(mock(MongoTemplate.class)), provider(null), daos(), readers());

		org.junit.jupiter.api.Assertions.assertEquals(1d,
				service.findModelPricing("openai", "k1", "gpt-4.1").getInputMtokenPrice());
		assertNull(service.findModelPricing("openai", "k1", "gpt-4o"));
		assertNull(service.findModelPricing("openai", "k1", "unknown"));
		assertNull(service.findModelPricing("openai", null, "gpt-4.1"));
		// Read on every model call: one repository read serves the following ones.
		verify(repository, org.mockito.Mockito.times(1)).findByProviderIdAndSecretCode("openai", "k1");

		// A change on this instance is seen at once.
		service.updateModelPricing("openai-1", "gpt-4o", pricing("USD", 3d, 4d));
		service.findModelPricing("openai", "k1", "gpt-4o");
		verify(repository, org.mockito.Mockito.times(2)).findByProviderIdAndSecretCode("openai", "k1");
	}

	@Test
	void aFailingDealsStoreNeverFailsTheModelPricing() {
		GProviderDealRepository repository = mock(GProviderDealRepository.class);
		when(repository.findByProviderIdAndSecretCode(any(), any())).thenThrow(new IllegalStateException("down"));
		GProviderDealServiceImpl service = new GProviderDealServiceImpl(provider(repository),
				provider(mock(MongoTemplate.class)), provider(null), daos(), readers());

		assertNull(service.findModelPricing("openai", "k1", "gpt-4.1"));
	}

	private static GProviderDeal deal(String providerId, String... secretCodes) {
		GProviderDeal deal = new GProviderDeal();
		deal.setProviderId(providerId);
		deal.setSecretCodes(new java.util.ArrayList<>(java.util.List.of(secretCodes)));
		return deal;
	}

	@Test
	void aKeyAlreadyCoveredLeavesTheDealsUntouched() {
		GProviderDealRepository repository = mock(GProviderDealRepository.class);
		MongoTemplate mongo = mock(MongoTemplate.class);
		GProviderDeal existing = deal("openai", "key-1");
		when(repository.findByProviderIdAndSecretCode("openai", "key-1")).thenReturn(Optional.of(existing));

		GProviderDeal result = new GProviderDealServiceImpl(provider(repository), provider(mongo), provider(null), daos(), readers()).ensureDeal("openai",
				"key-1");

		assertSame(existing, result);
		verify(mongo, never()).upsert(any(Query.class), any(Update.class), eq(GProviderDeal.class));
	}

	@Test
	void anUncoveredKeyIsAddedToTheProviderDealOrCreatesIt() {
		GProviderDealRepository repository = mock(GProviderDealRepository.class);
		MongoTemplate mongo = mock(MongoTemplate.class);
		GProviderDeal after = deal("regolo.ai", "key-1", "key-2");
		when(repository.findByProviderIdAndSecretCode("regolo.ai", "key-2")).thenReturn(Optional.empty(),
				Optional.of(after));
		when(mongo.upsert(any(Query.class), any(Update.class), eq(GProviderDeal.class)))
				.thenReturn(UpdateResult.acknowledged(1, 1L, new BsonString("new-id")));

		GProviderDeal result = new GProviderDealServiceImpl(provider(repository), provider(mongo), provider(null), daos(), readers())
				.ensureDeal("regolo.ai", "key-2");

		assertSame(after, result);
		ArgumentCaptor<Query> query = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> update = ArgumentCaptor.forClass(Update.class);
		verify(mongo).upsert(query.capture(), update.capture(), eq(GProviderDeal.class));
		// One upsert keyed on the provider: adds the key to its deal, or creates it.
		assertSame("regolo.ai", query.getValue().getQueryObject().get("providerId"));
		// A created deal gets an id made of the provider and a UUID.
		String newId = (String) ((org.bson.Document) update.getValue().getUpdateObject().get("$setOnInsert"))
				.get("_id");
		org.junit.jupiter.api.Assertions.assertTrue(newId.startsWith("regolo.ai-") && newId.length() == "regolo.ai-".length() + 36,
				newId);
		org.junit.jupiter.api.Assertions.assertEquals("key-2",
				((org.bson.Document) update.getValue().getUpdateObject().get("$addToSet")).get("secretCodes"));
		// A deal created automatically gets a default description naming its provider.
		String description = (String) ((org.bson.Document) update.getValue().getUpdateObject().get("$setOnInsert"))
				.get("description");
		org.junit.jupiter.api.Assertions.assertTrue(description.startsWith("regolo.ai deal (created automatically on "),
				description);
	}

	@Test
	void withoutMongoTheDealsAreNotMaintained() {
		assertNull(new GProviderDealServiceImpl(provider(null), provider(null), provider(null), daos(), readers()).ensureDeal("openai", "key-1"));
	}

	@Test
	void aDealNeedsBothProviderAndKey() {
		GProviderDealServiceImpl service = new GProviderDealServiceImpl(provider(mock(GProviderDealRepository.class)),
				provider(mock(MongoTemplate.class)), provider(null), daos(), readers());
		assertThrows(IllegalArgumentException.class, () -> service.ensureDeal(null, "key-1"));
		assertThrows(IllegalArgumentException.class, () -> service.ensureDeal("openai", " "));
	}

	private static SecretInfo secret(String code) {
		SecretInfo info = new SecretInfo();
		info.setCode(code);
		info.setDescription("key " + code);
		return info;
	}

	private static IGeboSecretsAccessService secrets(String contextCode, String... codes) throws Exception {
		IGeboSecretsAccessService secrets = mock(IGeboSecretsAccessService.class);
		when(secrets.getSecretInfoByContextCode(contextCode))
				.thenReturn(java.util.Arrays.stream(codes).map(GProviderDealServiceImplTest::secret).toList());
		return secrets;
	}

	@Test
	void assigningAKeyHeldByAnotherDealOfTheProviderTransfersIt() throws Exception {
		GProviderDealRepository repository = mock(GProviderDealRepository.class);
		MongoTemplate mongo = mock(MongoTemplate.class);
		GProviderDeal target = deal("openai", "key-2");
		target.setId("openai-target");
		when(repository.findById("openai-target")).thenReturn(Optional.of(target));
		GProviderDeal source = deal("openai", "key-1");
		source.setId("openai-source");
		when(mongo.find(any(Query.class), eq(GProviderDeal.class))).thenReturn(java.util.List.of(source));
		when(mongo.updateMulti(any(Query.class), any(Update.class), eq(GProviderDeal.class)))
				.thenReturn(UpdateResult.acknowledged(1, 1L, null));
		GProviderDealServiceImpl service = new GProviderDealServiceImpl(provider(repository), provider(mongo),
				provider(secrets("openai", "key-1", "key-2")), daos(), readers());

		service.assignApiKey("openai-target", "key-1");

		// Pulled from the provider's other deals, then added to the target one.
		ArgumentCaptor<Query> pull = ArgumentCaptor.forClass(Query.class);
		verify(mongo).updateMulti(pull.capture(), any(Update.class), eq(GProviderDeal.class));
		org.junit.jupiter.api.Assertions.assertEquals("openai", pull.getValue().getQueryObject().get("providerId"));
		verify(mongo).updateFirst(any(Query.class), any(Update.class), eq(GProviderDeal.class));
	}

	@Test
	void aKeyThatIsNotTheProvidersIsRejected() throws Exception {
		GProviderDealRepository repository = mock(GProviderDealRepository.class);
		GProviderDeal target = deal("openai");
		target.setId("openai-target");
		when(repository.findById("openai-target")).thenReturn(Optional.of(target));
		MongoTemplate mongo = mock(MongoTemplate.class);
		GProviderDealServiceImpl service = new GProviderDealServiceImpl(provider(repository), provider(mongo),
				provider(secrets("openai", "key-1")), daos(), readers());

		assertThrows(IllegalArgumentException.class, () -> service.assignApiKey("openai-target", "someone-else"));
		verify(mongo, never()).updateFirst(any(Query.class), any(Update.class), eq(GProviderDeal.class));
	}

	@Test
	void aDealStillCoveringKeysCannotBeDeleted() {
		GProviderDealRepository repository = mock(GProviderDealRepository.class);
		GProviderDeal deal = deal("openai", "key-1");
		deal.setId("openai-1");
		when(repository.findById("openai-1")).thenReturn(Optional.of(deal));
		GProviderDealServiceImpl service = new GProviderDealServiceImpl(provider(repository),
				provider(mock(MongoTemplate.class)), provider(null), daos(), readers());

		assertThrows(IllegalStateException.class, () -> service.deleteDeal("openai-1"));
		verify(repository, never()).deleteById(any());
	}

	@Test
	void theProviderKeysListTheDealCoveringThem() throws Exception {
		GProviderDealRepository repository = mock(GProviderDealRepository.class);
		GProviderDeal deal = deal("regolo.ai", "key-1");
		deal.setId("regolo.ai-1");
		when(repository.findByProviderId("regolo.ai")).thenReturn(java.util.List.of(deal));
		GProviderDealServiceImpl service = new GProviderDealServiceImpl(provider(repository),
				provider(mock(MongoTemplate.class)), provider(secrets("regolo.ai", "key-1", "key-2")), daos(), readers());

		java.util.List<ai.gebo.llms.abstraction.layer.model.GProviderApiKey> keys = service
				.getProviderApiKeys("regolo.ai");

		org.junit.jupiter.api.Assertions.assertEquals(2, keys.size());
		org.junit.jupiter.api.Assertions.assertEquals("regolo.ai-1", keys.get(0).getDealId());
		assertNull(keys.get(1).getDealId());
	}

	private static ai.gebo.llms.abstraction.layer.model.GProviderFlatConditions flat(String currency, Double monthly,
			Double monthlyLimit, Double dailyLimit) {
		ai.gebo.llms.abstraction.layer.model.GProviderFlatConditions flat = new ai.gebo.llms.abstraction.layer.model.GProviderFlatConditions();
		flat.setCurrencyCode(currency);
		flat.setMonthlyFlatCost(monthly);
		flat.setMonthlyTrafficLimits(monthlyLimit);
		flat.setDailyTrafficLimits(dailyLimit);
		return flat;
	}

	@Test
	void validFlatConditionsAreStoredOnTheDealAndCanBeCleared() {
		GProviderDealRepository repository = mock(GProviderDealRepository.class);
		MongoTemplate mongo = mock(MongoTemplate.class);
		GProviderDeal deal = deal("regolo.ai", "key-1");
		deal.setId("regolo.ai-1");
		when(repository.findById("regolo.ai-1")).thenReturn(Optional.of(deal));
		GProviderDealServiceImpl service = new GProviderDealServiceImpl(provider(repository), provider(mongo),
				provider(null), daos(), readers());

		service.updateFlatConditions("regolo.ai-1", flat("EUR", 50d, 100d, 5d));
		service.updateFlatConditions("regolo.ai-1", null);

		ArgumentCaptor<Update> updates = ArgumentCaptor.forClass(Update.class);
		verify(mongo, org.mockito.Mockito.times(2)).updateFirst(any(Query.class), updates.capture(),
				eq(GProviderDeal.class));
		org.bson.Document set = (org.bson.Document) updates.getAllValues().get(0).getUpdateObject().get("$set");
		org.junit.jupiter.api.Assertions.assertEquals(flat("EUR", 50d, 100d, 5d), set.get("flatConditions"));
		org.bson.Document cleared = (org.bson.Document) updates.getAllValues().get(1).getUpdateObject().get("$set");
		assertNull(cleared.get("flatConditions"));
	}

	@Test
	void invalidFlatConditionsAreRejected() {
		GProviderDealRepository repository = mock(GProviderDealRepository.class);
		MongoTemplate mongo = mock(MongoTemplate.class);
		GProviderDeal deal = deal("regolo.ai");
		deal.setId("regolo.ai-1");
		when(repository.findById("regolo.ai-1")).thenReturn(Optional.of(deal));
		GProviderDealServiceImpl service = new GProviderDealServiceImpl(provider(repository), provider(mongo),
				provider(null), daos(), readers());

		assertThrows(IllegalArgumentException.class,
				() -> service.updateFlatConditions("regolo.ai-1", flat(null, 50d, null, null)));
		assertThrows(IllegalArgumentException.class,
				() -> service.updateFlatConditions("regolo.ai-1", flat("EUR", -1d, null, null)));
		assertThrows(IllegalArgumentException.class,
				() -> service.updateFlatConditions("regolo.ai-1", flat("EUR", 50d, 0d, null)));
		verify(mongo, never()).updateFirst(any(Query.class), any(Update.class), eq(GProviderDeal.class));
	}
}
