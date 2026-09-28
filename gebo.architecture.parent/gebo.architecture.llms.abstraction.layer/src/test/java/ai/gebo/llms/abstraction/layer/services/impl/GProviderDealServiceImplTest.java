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

		GProviderDeal result = new GProviderDealServiceImpl(provider(repository), provider(mongo)).ensureDeal("openai",
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

		GProviderDeal result = new GProviderDealServiceImpl(provider(repository), provider(mongo))
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
	}

	@Test
	void withoutMongoTheDealsAreNotMaintained() {
		assertNull(new GProviderDealServiceImpl(provider(null), provider(null)).ensureDeal("openai", "key-1"));
	}

	@Test
	void aDealNeedsBothProviderAndKey() {
		GProviderDealServiceImpl service = new GProviderDealServiceImpl(provider(mock(GProviderDealRepository.class)),
				provider(mock(MongoTemplate.class)));
		assertThrows(IllegalArgumentException.class, () -> service.ensureDeal(null, "key-1"));
		assertThrows(IllegalArgumentException.class, () -> service.ensureDeal("openai", " "));
	}
}
