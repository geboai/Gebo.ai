/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.system.messages.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.system.messages.model.GSystemMessageDismissal;

/**
 * Users' dismissals of system messages.
 */
public interface SystemMessageDismissalRepository extends MongoRepository<GSystemMessageDismissal, String> {

	/**
	 * @param username the user
	 * @return every dismissal recorded for the user
	 */
	List<GSystemMessageDismissal> findByUsername(String username);

	/**
	 * Removes the dismissals of a message, when the message itself is retracted.
	 *
	 * @param messageId the message
	 */
	void deleteByMessageId(String messageId);
}
