/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.knowledgebase.repositories.uniqueid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.IGMongoSequenceService;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;

/**
 * Gives the document references and the virtual folders their uniqueId: the one
 * already stored for their code when they were saved before (a synchronization
 * rebuilds them from their source, without it), a new number from the sequence
 * otherwise. One sequence for both kinds, so a uniqueId identifies a folder or a
 * document among all of them.
 */
@Service
public class VirtualFilesystemUniqueIds {
	private static final Logger LOGGER = LoggerFactory.getLogger(VirtualFilesystemUniqueIds.class);
	/** The Mongo sequence the uniqueIds are taken from. */
	public static final String UNIQUE_ID_SEQUENCE = "virtual-filesystem-object-unique-id";
	static final String UNIQUE_ID = "uniqueId";

	// resolved at the first use: both need the MongoTemplate whose save callback uses
	// this service (see VirtualFilesystemUniqueIdCallback)
	private final ObjectProvider<MongoOperations> mongoOperations;
	private final ObjectProvider<IGMongoSequenceService> sequenceService;

	public VirtualFilesystemUniqueIds(ObjectProvider<MongoOperations> mongoOperations,
			ObjectProvider<IGMongoSequenceService> sequenceService) {
		this.mongoOperations = mongoOperations;
		this.sequenceService = sequenceService;
	}

	/** Whether the object is one of the numbered kinds: a document reference or a folder. */
	public static boolean isNumbered(Object object) {
		return object instanceof GDocumentReference || object instanceof GVirtualFolder;
	}

	/**
	 * Sets the object's uniqueId when it has none: the stored one for its code, a new
	 * one when it was never saved. Objects of the other kinds are left as they are.
	 *
	 * @return the object's uniqueId, null for the other kinds
	 */
	public Long ensureUniqueId(GAbstractVirtualFilesystemObject object) {
		if (!isNumbered(object)) {
			return null;
		}
		if (object.getUniqueId() != null) {
			return object.getUniqueId();
		}
		Long uniqueId = storedUniqueId(object);
		final boolean reused = uniqueId != null;
		if (uniqueId == null) {
			uniqueId = sequenceService.getObject().nextSequence(UNIQUE_ID_SEQUENCE);
		}
		object.setUniqueId(uniqueId);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("ensureUniqueId(...) " + object.getClass().getSimpleName() + " code:" + object.getCode()
					+ (reused ? " keeps its stored uniqueId:" : " numbered uniqueId:") + uniqueId);
		}
		return uniqueId;
	}

	/**
	 * The uniqueId of the document reference with the code, null when there is none
	 * or it has none yet (the contents ingested before the uniqueId existed carry only
	 * the document's code).
	 */
	public Long documentUniqueId(String documentCode) {
		if (documentCode == null || documentCode.isBlank()) {
			return null;
		}
		final Query byCode = new Query(Criteria.where("_id").is(documentCode));
		byCode.fields().include(UNIQUE_ID);
		final GDocumentReference stored = mongoOperations.getObject().findOne(byCode, GDocumentReference.class);
		final Long uniqueId = stored != null ? stored.getUniqueId() : null;
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("documentUniqueId(" + documentCode + ") -> " + uniqueId);
		}
		return uniqueId;
	}

	/** The uniqueId stored for the object's code, null when none is stored. */
	Long storedUniqueId(GAbstractVirtualFilesystemObject object) {
		if (object.getCode() == null) {
			return null;
		}
		final Query byCode = new Query(Criteria.where("_id").is(object.getCode()));
		byCode.fields().include(UNIQUE_ID);
		final GAbstractVirtualFilesystemObject stored = mongoOperations.getObject().findOne(byCode,
				object.getClass());
		return stored != null ? stored.getUniqueId() : null;
	}
}
