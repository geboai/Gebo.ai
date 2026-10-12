/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import ai.gebo.architecture.llms.usage.model.LLMDailyUsageDetail;
import ai.gebo.architecture.llms.usage.model.LLMUsageDetail;
import ai.gebo.architecture.llms.usage.repository.LLMDailyUsageDetailRepository;
import ai.gebo.architecture.llms.usage.repository.LLMUsageDetailRepository;
import ai.gebo.architecture.llms.usage.service.impl.LLMUsageProviderIdConversion;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.tests.TestChatModelSupportServiceImpl;

/**
 * The LLM usage records attribute each call to the real provider of the model, next
 * to its model type code, and the records written before the provider was recorded
 * are converted.
 */
public class LLMUsageProviderIdTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final String TEST_PROVIDER = "test";
	private static final String TEST_CHAT_TYPE = TestChatModelSupportServiceImpl.TEST_CONFIGURABLE_CHAT_MODEL_SERVICE;

	@Autowired
	private LLMUsageDetailRepository usageRepo;
	@Autowired
	private LLMDailyUsageDetailRepository dailyRepo;
	@Autowired
	private LLMUsageProviderIdConversion conversion;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	protected void beforeEachCallback() throws Exception {
		usageRepo.deleteAll();
		dailyRepo.deleteAll();
	}

	@Test
	public void aChatCallIsAttributedToTheProviderOfItsModelType() throws Exception {
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		model.doWithChatClient(client -> client.prompt("What is the capital of France?").call().content());

		LLMUsageDetail detail = awaitUsageOfType(TEST_CHAT_TYPE);
		assertEquals(TEST_PROVIDER, detail.getProviderId());
		assertEquals(TEST_CHAT_TYPE, detail.getModelTypeCode());
	}

	@Test
	public void theRecordsWrittenBeforeTheProviderAreConverted() {
		LocalDate today = LocalDate.now();
		String known = insertOldRecord(LLMUsageDetail.class, TEST_CHAT_TYPE, today);
		String unknown = insertOldRecord(LLMUsageDetail.class, "no-longer-installed-type", today);
		String knownDaily = insertOldRecord(LLMDailyUsageDetail.class, TEST_CHAT_TYPE, today);

		conversion.convert();

		LLMUsageDetail converted = usageRepo.findById(known).orElseThrow();
		assertEquals(TEST_CHAT_TYPE, converted.getModelTypeCode());
		assertEquals(TEST_PROVIDER, converted.getProviderId());
		LLMUsageDetail ofUnknownType = usageRepo.findById(unknown).orElseThrow();
		assertEquals("no-longer-installed-type", ofUnknownType.getModelTypeCode());
		assertNull(ofUnknownType.getProviderId(), "A model type unknown here leaves the provider unrecorded");
		LLMDailyUsageDetail convertedDaily = dailyRepo.findById(knownDaily).orElseThrow();
		assertEquals(TEST_CHAT_TYPE, convertedDaily.getModelTypeCode());
		assertEquals(TEST_PROVIDER, convertedDaily.getProviderId());
	}

	/** A record as written before the provider existed: the type code in providerId. */
	private String insertOldRecord(Class<?> collection, String modelTypeCode, LocalDate day) {
		String id = UUID.randomUUID().toString();
		Document old = new Document("_id", id).append("providerId", modelTypeCode).append("username", "someone")
				.append("model", "a-model").append("modelType", "CHAT").append("timestamp", System.currentTimeMillis())
				.append("year", day.getYear()).append("month", day.getMonthValue()).append("day", day.getDayOfMonth());
		mongoTemplate.insert(old, mongoTemplate.getCollectionName(collection));
		return id;
	}

	/** The usage travels through the internal message broker: it is stored asynchronously. */
	private LLMUsageDetail awaitUsageOfType(String modelTypeCode) throws InterruptedException {
		for (int i = 0; i < 100; i++) {
			List<LLMUsageDetail> found = usageRepo.findAll().stream()
					.filter(x -> modelTypeCode.equals(x.getModelTypeCode())).toList();
			if (!found.isEmpty()) {
				return found.get(0);
			}
			Thread.sleep(100);
		}
		throw new AssertionError("No usage recorded for model type " + modelTypeCode);
	}
}
