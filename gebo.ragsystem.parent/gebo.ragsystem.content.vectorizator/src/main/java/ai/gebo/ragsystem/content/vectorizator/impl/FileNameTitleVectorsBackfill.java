/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ragsystem.content.vectorizator.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.vectorstores.EmbedTypeFilters;
import ai.gebo.llms.abstraction.layer.vectorstores.IGExtendedVectorStore;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GVectorizedContent;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorizedFragmentMetadata;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.ragsystem.content.vectorizator.config.GeboVectorizatorConfig;
import ai.gebo.ragsystem.content.vectorizator.impl.DocumentIdentityVectors.IdentityVectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Gives the documents vectorized before the file name, title and author vectors
 * existed those vectors (see {@link DocumentIdentityVectors}), once the application is
 * started, without ingesting them again: the metadata of one vector of a document's
 * contents tells its file name, its title and the metadata the new vectors copy.
 *
 * <p>
 * A document is pending in a vector store while its {@link GVectorizedContent} has
 * no file name vectors ids (null); once done, they are recorded (none when it has
 * no file name) with its title's and its author's. The record is updated only if no vectorization
 * changed it meanwhile, else the vectors just added are deleted: the vectorization
 * gave the document its own. A document whose metadata cannot be read stays
 * pending, for the next start.
 * </p>
 *
 * <p>
 * The title and the author texts are kept beside their vectors' ids. A document
 * whose title or author vectors are recorded without their text (vectorized or
 * backfilled before the texts were kept) is given the texts alone, from the same
 * metadata, adding no vector.
 * </p>
 */
@Component
public class FileNameTitleVectorsBackfill {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileNameTitleVectorsBackfill.class);
	static final String FILE_NAME_VECTORS_ID = "fileNameVectorsId";
	static final String TITLE_VECTORS_ID = "titleVectorsId";
	static final String AUTHOR_VECTORS_ID = "authorVectorsId";
	static final String TITLE = "title";
	static final String AUTHOR = "author";
	static final String VECTORS_ID = "vectorsId";
	static final String VECTOR_STORE_ID = "_id.vectorStoreId";

	/** What a backfill of a vector store did. */
	@Getter
	@AllArgsConstructor
	@ToString
	public static class Outcome {
		private final String vectorStoreId;
		private final int documentsDone;
		private final int vectorsAdded;
		private final int documentsLeftPending;
		private final int documentsChangedMeanwhile;
	}

	private final IGEmbeddingModelRuntimeConfigurationDao embeddingModels;
	private final MongoTemplate mongoTemplate;
	private final GeboVectorizatorConfig config;

	public FileNameTitleVectorsBackfill(IGEmbeddingModelRuntimeConfigurationDao embeddingModels,
			MongoTemplate mongoTemplate, GeboVectorizatorConfig config) {
		this.embeddingModels = embeddingModels;
		this.mongoTemplate = mongoTemplate;
		this.config = config;
	}

	/** Starts the backfill in the background once the application is ready, when enabled. */
	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		if (!config.isFileNameTitleBackfillEnabled()) {
			LOGGER.info("File name and title vectors backfill disabled");
			return;
		}
		final Thread thread = new Thread(this::backfillAll, "file-name-title-vectors-backfill");
		thread.setDaemon(true);
		thread.start();
	}

	/** Backfills the vector store of every embedding model configured. */
	public List<Outcome> backfillAll() {
		final List<Outcome> outcomes = new ArrayList<>();
		try {
			final List<IGConfigurableEmbeddingModel> models = embeddingModels.getConfigurations();
			for (IGConfigurableEmbeddingModel model : models != null ? models : List.<IGConfigurableEmbeddingModel>of()) {
				if (model != null && model.getVectorStore() != null) {
					outcomes.add(backfill(model));
				}
			}
		} catch (RuntimeException e) {
			LOGGER.error("File name and title vectors backfill failed", e);
		}
		return outcomes;
	}

	/** Backfills the documents pending in the vector store of the embedding model. */
	public Outcome backfill(IGConfigurableEmbeddingModel model) {
		final String vectorStoreId = model.getCode();
		final int batchSize = Math.max(1, config.getFileNameTitleBackfillBatchSize());
		int done = 0, added = 0, leftPending = 0, changed = 0;
		final long pendingAtStart = mongoTemplate.count(pending(vectorStoreId), GVectorizedContent.class);
		if (pendingAtStart > 0) {
			LOGGER.info("File name and title vectors backfill of " + vectorStoreId + ": " + pendingAtStart
					+ " document(s) to do");
		} else if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("File name and title vectors backfill of " + vectorStoreId + ": nothing to do");
		}
		while (true) {
			// the documents left pending (unreadable) are skipped, the others are done
			final List<GVectorizedContent> batch = mongoTemplate
					.find(pending(vectorStoreId).skip(leftPending).limit(batchSize), GVectorizedContent.class);
			if (batch.isEmpty()) {
				break;
			}
			final BatchOutcome outcome = backfill(model.getVectorStore(), batch);
			done += outcome.done;
			added += outcome.added;
			leftPending += outcome.leftPending;
			changed += outcome.changed;
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("backfill(" + vectorStoreId + ") batch of " + batch.size() + ": " + outcome);
			}
		}
		final Outcome outcome = new Outcome(vectorStoreId, done, added, leftPending, changed);
		if (pendingAtStart > 0) {
			LOGGER.info("File name and title vectors backfill of " + vectorStoreId + " ended: " + outcome);
		}
		backfillTexts(model);
		return outcome;
	}

	/**
	 * Gives the documents of the vector store whose title or author vectors are
	 * recorded without their text the texts, from the metadata of their contents'
	 * first vector; adds no vector.
	 *
	 * @return the number of documents given their texts
	 */
	int backfillTexts(IGConfigurableEmbeddingModel model) {
		final String vectorStoreId = model.getCode();
		final int batchSize = Math.max(1, config.getFileNameTitleBackfillBatchSize());
		final long pendingAtStart = mongoTemplate.count(textsPending(vectorStoreId), GVectorizedContent.class);
		if (pendingAtStart == 0) {
			return 0;
		}
		LOGGER.info("Title and author texts backfill of " + vectorStoreId + ": " + pendingAtStart + " document(s) to do");
		int done = 0, skipped = 0;
		while (true) {
			// the documents whose texts cannot be read are skipped, the others are done
			final List<GVectorizedContent> batch = mongoTemplate
					.find(textsPending(vectorStoreId).skip(skipped).limit(batchSize), GVectorizedContent.class);
			if (batch.isEmpty()) {
				break;
			}
			final Map<String, Map<String, Object>> metadataByFirstVector = metadata(model.getVectorStore(), batch);
			for (GVectorizedContent record : batch) {
				final Map<String, Object> metadata = record.getVectorsId() != null && !record.getVectorsId().isEmpty()
						? metadataByFirstVector.get(record.getVectorsId().get(0))
						: null;
				final IdentityVectors identity = metadata != null ? DocumentIdentityVectors.ofContentMetadata(metadata)
						: IdentityVectors.NONE;
				final String title = hasIds(record.getTitleVectorsId()) ? text(identity.title()) : null;
				final String author = hasIds(record.getAuthorVectorsId()) ? text(identity.author()) : null;
				if ((hasIds(record.getTitleVectorsId()) && title == null)
						|| (hasIds(record.getAuthorVectorsId()) && author == null)) {
					skipped++;
					continue;
				}
				// only if no vectorization changed the document meanwhile
				final Query unchanged = new Query(
						Criteria.where("_id").is(record.getId()).and(VECTORS_ID).is(record.getVectorsId()));
				if (mongoTemplate.updateFirst(unchanged, new Update().set(TITLE, title).set(AUTHOR, author),
						GVectorizedContent.class).getModifiedCount() > 0) {
					done++;
				} else {
					// vectorized meanwhile: the vectorization gave it its texts, else it is
					// skipped so the loop does not read it again
					skipped++;
				}
			}
		}
		LOGGER.info("Title and author texts backfill of " + vectorStoreId + " ended: " + done + " done, " + skipped
				+ " left without readable texts");
		return done;
	}

	@ToString
	static class BatchOutcome {
		int done, added, leftPending, changed;
	}

	/** Gives a batch of pending documents their file name and title vectors. */
	BatchOutcome backfill(IGExtendedVectorStore store, List<GVectorizedContent> batch) {
		final BatchOutcome outcome = new BatchOutcome();
		final Map<String, Map<String, Object>> metadataByFirstVector = metadata(store, batch);
		final Map<GVectorizedContent, IdentityVectors> vectors = new HashMap<>();
		final List<Document> toAdd = new ArrayList<>();
		for (GVectorizedContent record : batch) {
			final List<String> contents = record.getVectorsId();
			if (Boolean.TRUE.equals(record.getDeleted()) || contents == null || contents.isEmpty()) {
				// nothing to copy the metadata from: done, without vectors
				vectors.put(record, IdentityVectors.NONE);
				continue;
			}
			final Map<String, Object> metadata = metadataByFirstVector.get(contents.get(0));
			if (metadata == null) {
				outcome.leftPending++;
				continue;
			}
			final IdentityVectors identity = DocumentIdentityVectors.ofContentMetadata(metadata);
			vectors.put(record, identity);
			toAdd.addAll(identity.all());
		}
		if (!toAdd.isEmpty()) {
			try {
				store.add(toAdd);
			} catch (RuntimeException e) {
				LOGGER.warn("Cannot add " + toAdd.size() + " file name and title vector(s), the documents stay pending: "
						+ e.getMessage());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("backfill add failure", e);
				}
				outcome.leftPending += vectors.size();
				return outcome;
			}
			outcome.added = toAdd.size();
		}
		final List<String> orphans = new ArrayList<>();
		for (Map.Entry<GVectorizedContent, IdentityVectors> entry : vectors.entrySet()) {
			final GVectorizedContent record = entry.getKey();
			final List<String> fileNameIds = ids(entry.getValue().fileName());
			final List<String> titleIds = ids(entry.getValue().title());
			final List<String> authorIds = ids(entry.getValue().author());
			// only if no vectorization changed the document meanwhile
			final Query unchanged = new Query(Criteria.where("_id").is(record.getId()).and(FILE_NAME_VECTORS_ID).is(null)
					.and(VECTORS_ID).is(record.getVectorsId()));
			final long updated = mongoTemplate
					.updateFirst(unchanged,
							new Update().set(FILE_NAME_VECTORS_ID, fileNameIds).set(TITLE_VECTORS_ID, titleIds)
									.set(AUTHOR_VECTORS_ID, authorIds).set(TITLE, text(entry.getValue().title()))
									.set(AUTHOR, text(entry.getValue().author())),
							GVectorizedContent.class)
					.getModifiedCount();
			if (updated > 0) {
				outcome.done++;
			} else {
				outcome.changed++;
				orphans.addAll(fileNameIds);
				orphans.addAll(titleIds);
				orphans.addAll(authorIds);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("backfill(...) " + record.getId().getDocReferenceCode()
							+ " was vectorized meanwhile, its backfilled vectors are deleted");
				}
			}
		}
		if (!orphans.isEmpty()) {
			try {
				store.delete(orphans);
			} catch (RuntimeException e) {
				LOGGER.error("Cannot delete " + orphans.size() + " backfilled vector(s) of documents vectorized meanwhile",
						e);
			}
		}
		return outcome;
	}

	/**
	 * The metadata of the first vector of the contents of each document, by its id:
	 * read by id when the store can, else found by a filtered search (one per
	 * document); missing for the documents whose metadata cannot be read.
	 */
	Map<String, Map<String, Object>> metadata(IGExtendedVectorStore store, List<GVectorizedContent> batch) {
		final Map<String, Map<String, Object>> metadata = new HashMap<>();
		final List<String> firstIds = new ArrayList<>();
		for (GVectorizedContent record : batch) {
			if (record.getVectorsId() != null && !record.getVectorsId().isEmpty()) {
				firstIds.add(record.getVectorsId().get(0));
			}
		}
		if (firstIds.isEmpty()) {
			return metadata;
		}
		try {
			for (VectorizedFragmentMetadata read : store.readMetadataByIds(firstIds)) {
				if (read != null && read.getId() != null && read.getMetadata() != null) {
					metadata.put(read.getId(), read.getMetadata());
				}
			}
			return metadata;
		} catch (Exception e) {
			if (e instanceof InterruptedException) {
				Thread.currentThread().interrupt();
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("metadata(...) the store cannot read metadata by id (" + e.getMessage()
						+ "), searching each document's contents");
			}
		}
		final FilterExpressionBuilder filters = new FilterExpressionBuilder();
		for (GVectorizedContent record : batch) {
			if (record.getVectorsId() == null || record.getVectorsId().isEmpty()) {
				continue;
			}
			final String code = record.getId().getDocReferenceCode();
			try {
				final List<Document> found = store.similaritySearch(SearchRequest.builder().query(code).topK(1)
						.similarityThresholdAll()
						.filterExpression(
								filters.and(filters.eq(DocumentMetaInfos.CONTENT_CODE, code), EmbedTypeFilters.contentsOnly())
										.build())
						.build());
				if (found != null && !found.isEmpty() && found.get(0).getMetadata() != null) {
					metadata.put(record.getVectorsId().get(0), found.get(0).getMetadata());
				}
			} catch (RuntimeException e) {
				LOGGER.warn("Cannot read the metadata of the contents of " + code + ", it stays pending: " + e.getMessage());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("backfill metadata search failure", e);
				}
			}
		}
		return metadata;
	}

	/** The documents of the vector store without file name vectors (yet). */
	static Query pending(String vectorStoreId) {
		// in a stable order, the documents left pending skipped by their count
		return new Query(Criteria.where(VECTOR_STORE_ID).is(vectorStoreId).and(FILE_NAME_VECTORS_ID).is(null))
				.with(Sort.by("_id"));
	}

	/**
	 * The documents of the vector store whose title or author vectors are recorded
	 * without their text.
	 */
	static Query textsPending(String vectorStoreId) {
		return new Query(Criteria.where(VECTOR_STORE_ID).is(vectorStoreId).and("deleted").ne(true)
				.orOperator(Criteria.where(TITLE_VECTORS_ID + ".0").exists(true).and(TITLE).is(null),
						Criteria.where(AUTHOR_VECTORS_ID + ".0").exists(true).and(AUTHOR).is(null)))
				.with(Sort.by("_id"));
	}

	private static boolean hasIds(List<String> ids) {
		return ids != null && !ids.isEmpty();
	}

	/** The text a vector embeds, null without the vector. */
	private static String text(Document vector) {
		return vector != null ? vector.getText() : null;
	}

	private static List<String> ids(Document vector) {
		final List<String> ids = new ArrayList<>();
		if (vector != null) {
			ids.add(vector.getId());
		}
		return ids;
	}
}
