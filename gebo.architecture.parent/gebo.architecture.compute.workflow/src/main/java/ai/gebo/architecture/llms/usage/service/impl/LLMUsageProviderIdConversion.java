/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.llms.usage.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.llms.usage.model.LLMDailyUsageDetail;
import ai.gebo.architecture.llms.usage.model.LLMUsageDetail;
import ai.gebo.core.messages.ILLMModelTypeProvidersReader;
import lombok.RequiredArgsConstructor;

/**
 * Converts, once per process, the usage records written before the real provider
 * was recorded: their providerId held the model type code, which moves to
 * modelTypeCode. Their providerId is then filled from the model types known in this
 * process; where the LLM modules do not run (tyr), no model type is known and the
 * converted records keep no provider.
 * <p>
 * It runs before the first consolidation: the consolidation groups the records by
 * model type code, and an unconverted record would rebuild a duplicate daily
 * document.
 */
@Component
@RequiredArgsConstructor
public class LLMUsageProviderIdConversion {
	private static final Logger LOGGER = LoggerFactory.getLogger(LLMUsageProviderIdConversion.class);
	static final String PROVIDER_ID = "providerId";
	static final String MODEL_TYPE_CODE = "modelTypeCode";
	private static final List<Class<?>> COLLECTIONS = List.of(LLMUsageDetail.class, LLMDailyUsageDetail.class);
	private final MongoTemplate mongoTemplate;
	private final ObjectProvider<ILLMModelTypeProvidersReader> providersReader;
	private volatile boolean converted = false;

	/**
	 * @return true when the records are converted, now or by a previous call; false
	 *         when the conversion failed, and the consolidation must wait for it
	 */
	public synchronized boolean convertOnce() {
		if (converted) {
			return true;
		}
		try {
			convert();
			converted = true;
		} catch (Throwable e) {
			LOGGER.error("Cannot convert the usage records to the model type code, retrying at the next consolidation",
					e);
		}
		return converted;
	}

	/**
	 * Runs the conversion now, whether it already ran or not: it only touches the
	 * records still to convert.
	 */
	public void convert() {
		for (Class<?> collection : COLLECTIONS) {
			long moved = mongoTemplate.updateMulti(
					Query.query(Criteria.where(MODEL_TYPE_CODE).exists(false).and(PROVIDER_ID).exists(true)),
					new Update().rename(PROVIDER_ID, MODEL_TYPE_CODE), collection).getModifiedCount();
			if (moved > 0) {
				LOGGER.info("Moved the model type code of " + moved + " " + collection.getSimpleName()
						+ " records from providerId to modelTypeCode");
			}
		}
		ILLMModelTypeProvidersReader reader = providersReader.getIfAvailable();
		Map<String, String> providers = reader != null ? reader.providersOfModelTypes() : Map.of();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Filling the provider of the converted usage records from " + providers.size()
					+ " known model types");
		}
		providers.forEach((modelTypeCode, providerId) -> {
			for (Class<?> collection : COLLECTIONS) {
				long filled = mongoTemplate.updateMulti(
						Query.query(Criteria.where(MODEL_TYPE_CODE).is(modelTypeCode).and(PROVIDER_ID).exists(false)),
						new Update().set(PROVIDER_ID, providerId), collection).getModifiedCount();
				if (filled > 0) {
					LOGGER.info("Attributed " + filled + " " + collection.getSimpleName() + " records of model type "
							+ modelTypeCode + " to provider " + providerId);
				}
			}
		});
	}
}
