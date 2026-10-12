/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ragsystem.vectorstores.redis;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;

import ai.gebo.model.DocumentMetaInfos;
import lombok.experimental.UtilityClass;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.search.schemafields.SchemaField;
import redis.clients.jedis.search.schemafields.TextField;

/**
 * Keeps the Redis search index of a vector store in step with the metadata the
 * platform filters on ({@link DocumentMetaInfos#ALL_ATTRIBUTES}): creates the index
 * when it is missing, and adds to an existing one the metadata fields it lacks - the
 * keys added after it was created - as the store declares them (TEXT on
 * {@code $.<key>}). Spring AI creates a missing index only and never alters one, and a
 * filter on a field the index lacks is an error.
 */
@UtilityClass
public class RedisMetadataFieldsReconciler {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisMetadataFieldsReconciler.class);
	private static final String ATTRIBUTES = "attributes";
	private static final String ATTRIBUTE = "attribute";
	private static final String JSON_PATH_PREFIX = "$.";

	/**
	 * Creates or completes the index; a failure is logged, the store is used as it is.
	 *
	 * @return the fields added to an existing index
	 */
	public static List<String> reconcile(UnifiedJedis jedis, RedisVectorStore store, String indexName,
			List<String> fields) {
		try {
			if (!jedis.ftList().contains(indexName)) {
				store.afterPropertiesSet();
				LOGGER.info("Redis vector store index " + indexName + " created with " + fields.size()
						+ " metadata field(s)");
				return List.of();
			}
			final Set<String> declared = declaredAttributes(jedis.ftInfo(indexName));
			final List<String> missing = new ArrayList<>();
			for (String field : fields) {
				if (!declared.contains(field)) {
					missing.add(field);
				}
			}
			if (missing.isEmpty()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Redis vector store index " + indexName + " declares every metadata field");
				}
				return List.of();
			}
			final List<SchemaField> added = new ArrayList<>();
			for (String field : missing) {
				added.add(TextField.of(JSON_PATH_PREFIX + field).as(field));
			}
			jedis.ftAlter(indexName, added);
			LOGGER.info("Redis vector store index " + indexName + ": added the metadata field(s) " + missing);
			return missing;
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot create or complete the Redis vector store index " + indexName
					+ ": a metadata field it lacks has to be added by hand (FT.ALTER " + indexName
					+ " SCHEMA ADD $.<KEY> AS <KEY> TEXT): " + e.getMessage());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Redis index reconciliation failure", e);
			}
			return List.of();
		}
	}

	/** The names of the attributes an FT.INFO answer declares, whatever its protocol shape. */
	static Set<String> declaredAttributes(Map<String, Object> info) {
		final Set<String> names = new LinkedHashSet<>();
		final Object attributes = info != null ? info.get(ATTRIBUTES) : null;
		if (!(attributes instanceof List<?> list)) {
			return names;
		}
		for (Object attribute : list) {
			if (attribute instanceof Map<?, ?> map) {
				final Object name = map.get(ATTRIBUTE);
				if (name != null) {
					names.add(text(name));
				}
			} else if (attribute instanceof List<?> pairs) {
				for (int i = 0; i + 1 < pairs.size(); i++) {
					if (ATTRIBUTE.equalsIgnoreCase(text(pairs.get(i)))) {
						names.add(text(pairs.get(i + 1)));
						break;
					}
				}
			}
		}
		return names;
	}

	private static String text(Object value) {
		if (value instanceof byte[] bytes) {
			return new String(bytes, StandardCharsets.UTF_8);
		}
		return String.valueOf(value);
	}
}
