/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.PartialIndexFilter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import jakarta.annotation.PostConstruct;

/**
 * The index that finds a conversation by the key another system opened it by,
 * and keeps one conversation per system and key: unique on the pair, over the
 * conversations that carry a key only.
 */
@Component
public class ExternalSessionsIndexes {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExternalSessionsIndexes.class);
	static final String EXTERNAL_SESSION_UNIQUE = "external_session_unique";
	private final ObjectProvider<MongoOperations> mongo;

	public ExternalSessionsIndexes(ObjectProvider<MongoOperations> mongo) {
		this.mongo = mongo;
	}

	@PostConstruct
	public void createIndexes() {
		final MongoOperations operations = mongo.getIfAvailable();
		if (operations == null) {
			LOGGER.warn("No MongoDB operations: the external sessions index is not created");
			return;
		}
		try {
			operations.indexOps(GUserChatSession.class)
					.createIndex(new Index().on("externalSystem", Direction.ASC).on("externalSessionKey", Direction.ASC)
							.unique().partial(PartialIndexFilter.of(Criteria.where("externalSessionKey").exists(true)))
							.named(EXTERNAL_SESSION_UNIQUE));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Conversations indexed by the external system and key that opened them");
			}
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot make the conversations unique by external system and key: " + e.getMessage());
		}
	}
}
