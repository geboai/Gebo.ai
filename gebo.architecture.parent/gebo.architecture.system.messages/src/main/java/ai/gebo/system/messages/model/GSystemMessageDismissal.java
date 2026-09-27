/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.system.messages.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import lombok.Data;

/**
 * A user having hidden one revision of a system message. A later revision of the
 * same message is shown again.
 */
@Data
@Document
public class GSystemMessageDismissal {
	/** {@code <username>|<messageId>}, see {@link #idOf(String, String)}. */
	@Id
	private String id = null;
	@Indexed
	private String username = null;
	@Indexed
	private String messageId = null;
	/** The revision of the message that was dismissed. */
	private long revision = 0;
	private Date dismissedAt = null;

	/**
	 * The identifier of a user's dismissal of a message.
	 *
	 * @param username  the user
	 * @param messageId the message
	 * @return the dismissal identifier
	 */
	public static String idOf(String username, String messageId) {
		return username + "|" + messageId;
	}
}
