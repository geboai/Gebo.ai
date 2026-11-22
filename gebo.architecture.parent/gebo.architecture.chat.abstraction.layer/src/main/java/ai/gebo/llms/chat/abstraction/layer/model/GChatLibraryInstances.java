/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * The instances of the libraries of tools and resources a conversation holds,
 * by library type id: a working directory, a sandbox, created the first time an
 * agent working in the conversation needs one and reclaimed by every agent
 * entering its execution there, so that at every step every agent's tools work
 * on the same pool of resources. Dropped with the conversation.
 * <p>
 * One record per conversation, keyed by the conversation's code and kept apart
 * from the conversation document: that document is loaded when a request starts
 * and saved whole when it ends, by the chat lifecycle and by whatever else
 * touches a conversation, and what the agents record about their libraries in
 * the middle of a request would be written over by such a save.
 */
@Document
@Data
public class GChatLibraryInstances {
	/** The code of the conversation. */
	@Id
	private String userChatContextCode = null;
	/**
	 * The instance held of each library type, by the type's id as it is stored: a
	 * type id may have dots (sandbox:python:3.12-slim) and a key of a Mongo map
	 * may not, so the dot is kept escaped here, and the percent sign that escapes
	 * it with it. Read and written through {@link #getInstanceIds()} and
	 * {@link #setInstanceIds(Map)}, which give and take the real type ids; a
	 * record saved before had neither in its keys and reads the same.
	 */
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Map<String, String> instanceIds = new LinkedHashMap<>();
	private Date dateCreated = null;
	private Date dateModified = null;

	private static final Pattern ESCAPED = Pattern.compile("%(25|2E)");

	/** The instance held of each library type, by the type's id; null when none is recorded. */
	public Map<String, String> getInstanceIds() {
		if (instanceIds == null) {
			return null;
		}
		final Map<String, String> ids = new LinkedHashMap<>();
		instanceIds.forEach((key, id) -> ids.put(unescape(key), id));
		return ids;
	}

	/** Records the instance held of each library type, by the type's id. */
	public void setInstanceIds(Map<String, String> ids) {
		if (ids == null) {
			this.instanceIds = null;
			return;
		}
		final Map<String, String> stored = new LinkedHashMap<>();
		ids.forEach((typeId, id) -> stored.put(escape(typeId), id));
		this.instanceIds = stored;
	}

	/** A library type id as a key of a Mongo map: with no dot. */
	static String escape(String typeId) {
		return typeId == null ? null : typeId.replace("%", "%25").replace(".", "%2E");
	}

	/** The library type id a key of the stored map stands for. */
	static String unescape(String key) {
		if (key == null || key.indexOf('%') < 0) {
			return key;
		}
		final Matcher escaped = ESCAPED.matcher(key);
		final StringBuilder typeId = new StringBuilder();
		while (escaped.find()) {
			escaped.appendReplacement(typeId, "25".equals(escaped.group(1)) ? "%" : ".");
		}
		escaped.appendTail(typeId);
		return typeId.toString();
	}
}
