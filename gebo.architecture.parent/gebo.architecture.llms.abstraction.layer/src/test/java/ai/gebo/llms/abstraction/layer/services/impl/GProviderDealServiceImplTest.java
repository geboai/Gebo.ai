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
import ai.gebo.llms.abstraction.layer.repository.GProviderDealRepository;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

class GProviderDealServiceImplTest {

	@SuppressWarnings("unchecked")
	private static <T> ObjectProvider<T> provider(T value) {
		ObjectProvider<T> provider = mock(ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(value);
		return provider;
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

		GProviderDeal result = new GProviderDealServiceImpl(provider(repository), provider(mongo), provider(null)).ensureDeal("openai",
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

		GProviderDeal result = new GProviderDealServiceImpl(provider(repository), provider(mongo), provider(null))
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
		assertNull(new GProviderDealServiceImpl(provider(null), provider(null), provider(null)).ensureDeal("openai", "key-1"));
	}

	@Test
	void aDealNeedsBothProviderAndKey() {
		GProviderDealServiceImpl service = new GProviderDealServiceImpl(provider(mock(GProviderDealRepository.class)),
				provider(mock(MongoTemplate.class)), provider(null));
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
		when(mongo.updateMulti(any(Query.class), any(Update.class), eq(GProviderDeal.class)))
				.thenReturn(UpdateResult.acknowledged(1, 1L, null));
		GProviderDealServiceImpl service = new GProviderDealServiceImpl(provider(repository), provider(mongo),
				provider(secrets("openai", "key-1", "key-2")));

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
				provider(secrets("openai", "key-1")));

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
				provider(mock(MongoTemplate.class)), provider(null));

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
				provider(mock(MongoTemplate.class)), provider(secrets("regolo.ai", "key-1", "key-2")));

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
				provider(null));

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
				provider(null));

		assertThrows(IllegalArgumentException.class,
				() -> service.updateFlatConditions("regolo.ai-1", flat(null, 50d, null, null)));
		assertThrows(IllegalArgumentException.class,
				() -> service.updateFlatConditions("regolo.ai-1", flat("EUR", -1d, null, null)));
		assertThrows(IllegalArgumentException.class,
				() -> service.updateFlatConditions("regolo.ai-1", flat("EUR", 50d, 0d, null)));
		verify(mongo, never()).updateFirst(any(Query.class), any(Update.class), eq(GProviderDeal.class));
	}
}
