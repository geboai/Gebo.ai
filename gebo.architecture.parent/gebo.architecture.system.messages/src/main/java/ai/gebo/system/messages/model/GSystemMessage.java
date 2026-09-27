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

import ai.gebo.model.GUserMessage.MsgServerity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * A notification published by a platform module and shown to the logged-in users
 * its {@link #audience} routes it to.
 * <p>
 * A message is identified by its publishing {@link #source} and a {@link #key}
 * chosen by the publisher: publishing again under the same pair replaces the
 * previous content (a status that changes day by day stays one message), and
 * bumps {@link #revision}, so users who dismissed an earlier revision see the new
 * one.
 */
@Data
@Document
public class GSystemMessage {
	/** {@code <source>:<key>}, see {@link #idOf(String, String)}. */
	@Id
	private String id = null;
	/** The publishing module, e.g. "backup" or "ingestion". */
	@NotNull
	private String source = null;
	/** The publisher's own identifier for this message within its source. */
	@NotNull
	private String key = null;
	@NotNull
	private MsgServerity severity = null;
	@NotNull
	private String summary = null;
	private String detail = null;
	@NotNull
	private SystemMessageAudience audience = null;
	/**
	 * Whether a user can hide it. A message that must stay visible until its
	 * publisher retracts it is not dismissible.
	 */
	private boolean dismissible = true;
	/** Incremented at every publication under the same source and key. */
	private long revision = 0;
	private Date createdAt = null;
	private Date updatedAt = null;
	/** When set, the message is no longer shown after this instant. */
	private Date expiresAt = null;

	/**
	 * The identifier of the message published under the given source and key.
	 *
	 * @param source the publishing module
	 * @param key    the publisher's key
	 * @return the message identifier
	 */
	public static String idOf(String source, String key) {
		return source + ":" + key;
	}
}
