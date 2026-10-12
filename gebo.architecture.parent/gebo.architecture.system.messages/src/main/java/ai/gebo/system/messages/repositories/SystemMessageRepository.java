/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.system.messages.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.system.messages.model.GSystemMessage;
import ai.gebo.system.messages.model.SystemMessageAudience;

/**
 * Stored system messages.
 */
public interface SystemMessageRepository extends MongoRepository<GSystemMessage, String> {

	/**
	 * @param audiences the audiences to include
	 * @return the messages routed to any of them, expired ones included
	 */
	List<GSystemMessage> findByAudienceIn(Collection<SystemMessageAudience> audiences);
}
