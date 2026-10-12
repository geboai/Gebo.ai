/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ragsystem.vectorstores.mongoatlas;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoCollection;

import ai.gebo.model.DocumentMetaInfos;
import lombok.experimental.UtilityClass;

/**
 * Keeps the MongoDB Atlas vector search index of a vector store in step with the
 * metadata the platform filters on ({@link DocumentMetaInfos#ALL_ATTRIBUTES}):
 * creates the index when it is missing, as Spring AI defines it, and adds to an
 * existing one the {@code filter} fields it lacks - the keys added after it was
 * created. Spring AI never alters an index, and a filter on a path the index does not
 * declare is an error. Only Atlas (or a {@code mongot}) serves search indexes: on a
 * plain {@code mongod} this logs a warning and changes nothing.
 */
@UtilityClass
public class MongoDBAtlasSearchIndexReconciler {
	private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBAtlasSearchIndexReconciler.class);
	static final String METADATA_PREFIX = "metadata.";
	static final String FIELDS = "fields";
	static final String FILTER = "filter";

	/**
	 * Creates or completes the index; a failure is logged, the store is used as it is.
	 *
	 * @return the metadata fields added to an existing index
	 */
	public static List<String> reconcile(MongoTemplate mongoTemplate, EmbeddingModel embeddingModel,
			String collectionName, String indexName, String embeddingPath, List<String> fields) {
		try {
			if (!mongoTemplate.collectionExists(collectionName)) {
				mongoTemplate.createCollection(collectionName);
			}
			final MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
			final Document index = collection.listSearchIndexes().name(indexName).first();
			if (index == null) {
				final List<Document> definition = new ArrayList<>();
				definition.add(new Document("type", "vector").append("path", embeddingPath)
						.append("numDimensions", embeddingModel.dimensions()).append("similarity", "cosine"));
				for (String field : fields) {
					definition.add(filter(field));
				}
				mongoTemplate.executeCommand(new Document("createSearchIndexes", collectionName).append("indexes",
						List.of(new Document("name", indexName).append("type", "vectorSearch").append("definition",
								new Document(FIELDS, definition)))));
				LOGGER.info("MongoDB Atlas vector search index " + indexName + " created with " + fields.size()
						+ " metadata filter field(s)");
				return List.of();
			}
			final Document latest = index.get("latestDefinition", Document.class);
			final List<Document> existing = latest != null ? latest.getList(FIELDS, Document.class, List.of())
					: List.of();
			final Set<String> declared = new LinkedHashSet<>();
			for (Document field : existing) {
				if (FILTER.equals(field.getString("type")) && field.getString("path") != null) {
					declared.add(field.getString("path"));
				}
			}
			final List<String> missing = new ArrayList<>();
			for (String field : fields) {
				if (!declared.contains(METADATA_PREFIX + field)) {
					missing.add(field);
				}
			}
			if (missing.isEmpty()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("MongoDB Atlas vector search index " + indexName + " declares every metadata field");
				}
				return List.of();
			}
			final List<Document> completed = new ArrayList<>(existing);
			for (String field : missing) {
				completed.add(filter(field));
			}
			collection.updateSearchIndex(indexName, new Document(FIELDS, completed));
			LOGGER.info("MongoDB Atlas vector search index " + indexName + ": added the metadata filter field(s) "
					+ missing);
			return missing;
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot create or complete the MongoDB Atlas vector search index " + indexName
					+ ": a metadata field it lacks has to be added by hand as a filter field on metadata.<KEY>: "
					+ e.getMessage());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Atlas index reconciliation failure", e);
			}
			return List.of();
		}
	}

	private static Document filter(String field) {
		return new Document("type", FILTER).append("path", METADATA_PREFIX + field);
	}
}
