/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.knowledgebase.repositories.uniqueid;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.persistence.IGMongoSequenceService;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;

/**
 * Numbers, once the application is up, the document references and virtual folders
 * saved before the uniqueId existed (their uniqueId null or missing in Mongo), from
 * the same sequence as {@link VirtualFilesystemUniqueIds}. Runs in the
 * background so the startup does not wait for it; an object is numbered only while
 * it still has no uniqueId, so several nodes doing it at once never number one
 * object twice.
 */
@Component
public class VirtualFilesystemUniqueIdBackfill {
	private static final Logger LOGGER = LoggerFactory.getLogger(VirtualFilesystemUniqueIdBackfill.class);
	static final String UNIQUE_ID = VirtualFilesystemUniqueIds.UNIQUE_ID;
	/** Objects numbered between two progress lines in the log. */
	static final int PROGRESS_EVERY = 10000;

	private final MongoOperations mongoOperations;
	private final IGMongoSequenceService sequenceService;

	public VirtualFilesystemUniqueIdBackfill(MongoOperations mongoOperations,
			IGMongoSequenceService sequenceService) {
		this.mongoOperations = mongoOperations;
		this.sequenceService = sequenceService;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		final Thread worker = new Thread(this::backfill, "virtual-filesystem-unique-id-backfill");
		worker.setDaemon(true);
		worker.start();
	}

	/** Numbers every document reference and virtual folder without a uniqueId. */
	public void backfill() {
		for (Class<? extends GAbstractVirtualFilesystemObject> type : List.of(GVirtualFolder.class,
				GDocumentReference.class)) {
			try {
				backfill(type);
			} catch (RuntimeException e) {
				LOGGER.error("Cannot give a uniqueId to the " + type.getSimpleName() + " objects without one", e);
			}
		}
	}

	/** Numbers the objects of a type without a uniqueId; the number of objects numbered. */
	long backfill(Class<? extends GAbstractVirtualFilesystemObject> type) {
		final long start = System.currentTimeMillis();
		final Query withoutUniqueId = new Query(Criteria.where(UNIQUE_ID).is(null));
		withoutUniqueId.fields().include("_id");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin backfill(" + type.getSimpleName() + ") numbering the objects without a uniqueId");
		}
		long numbered = 0;
		try (Stream<? extends GAbstractVirtualFilesystemObject> objects = mongoOperations.stream(withoutUniqueId,
				type)) {
			final Iterator<? extends GAbstractVirtualFilesystemObject> iterator = objects.iterator();
			while (iterator.hasNext()) {
				final GAbstractVirtualFilesystemObject object = iterator.next();
				final long uniqueId = sequenceService.nextSequence(VirtualFilesystemUniqueIds.UNIQUE_ID_SEQUENCE);
				// numbered only if still without one (another node may have numbered it)
				final Query stillWithout = new Query(
						Criteria.where("_id").is(object.getCode()).and(UNIQUE_ID).is(null));
				if (mongoOperations.updateFirst(stillWithout, new Update().set(UNIQUE_ID, uniqueId), type)
						.getModifiedCount() > 0) {
					numbered++;
					if (LOGGER.isTraceEnabled()) {
						LOGGER.trace(type.getSimpleName() + " code:" + object.getCode() + " numbered uniqueId:" + uniqueId);
					}
					if (numbered % PROGRESS_EVERY == 0) {
						LOGGER.info("backfill(" + type.getSimpleName() + ") " + numbered + " object(s) numbered so far");
					}
				}
			}
		}
		if (numbered > 0 || LOGGER.isDebugEnabled()) {
			LOGGER.info("End backfill(" + type.getSimpleName() + ") " + numbered + " object(s) without a uniqueId numbered in "
					+ (System.currentTimeMillis() - start) + " ms");
		}
		return numbered;
	}
}
