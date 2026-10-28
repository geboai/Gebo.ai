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

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

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
	/** The instance held of each library type, by the type's id. */
	private Map<String, String> instanceIds = new LinkedHashMap<>();
	private Date dateCreated = null;
	private Date dateModified = null;
}
