/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.system.messages.services;

import java.util.List;

import ai.gebo.system.messages.model.GSystemMessage;

/**
 * Publishes system messages for the platform's modules and serves them to the
 * logged-in users, routed by role: administrators see the messages for
 * {@code ADMINS} and {@code ALL}, the other users those for {@code USERS} and
 * {@code ALL}.
 */
public interface IGSystemMessagesService {

	/**
	 * Publishes a message, replacing the one previously published under the same
	 * source and key. Publishing unchanged content keeps the message's revision,
	 * so users who dismissed it do not see it again.
	 *
	 * @param publication the message
	 * @return the stored message
	 */
	GSystemMessage publish(SystemMessagePublication publication);

	/**
	 * Removes a message, and the users' dismissals of it.
	 *
	 * @param source the publishing module
	 * @param key    the publisher's key
	 */
	void retract(String source, String key);

	/**
	 * The messages to show to a user: routed to the user's role, not expired, and
	 * not dismissed at their current revision.
	 *
	 * @param username      the user
	 * @param administrator whether the user has the administrator role
	 * @return the messages, most severe first, then the most recent first
	 */
	List<GSystemMessage> findVisibleFor(String username, boolean administrator);

	/**
	 * Hides a message for a user at its current revision.
	 *
	 * @param username      the user
	 * @param administrator whether the user has the administrator role
	 * @param messageId     the message
	 * @return false when the message does not exist, is not routed to the user or
	 *         is not dismissible
	 */
	boolean dismiss(String username, boolean administrator, String messageId);

	/**
	 * @return every stored message, whatever its audience or expiry
	 */
	List<GSystemMessage> findAll();
}
