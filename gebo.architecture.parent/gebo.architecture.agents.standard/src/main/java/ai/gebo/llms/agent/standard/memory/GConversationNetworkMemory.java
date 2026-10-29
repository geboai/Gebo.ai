/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standard.memory;

import java.util.Date;

import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.model.base.GBaseObject;
import lombok.Data;

/**
 * What a network of agents remembers of a conversation between two of its
 * runs in it: a digest of what its agents contributed in the last run, read
 * into the shared context of the next (see
 * {@link ai.gebo.llms.agent.standard.services.GConversationAgentsSessionFactory}).
 * One per conversation and network; dropped with the conversation.
 */
@Document
@Data
public class GConversationNetworkMemory extends GBaseObject {
	@HashIndexed
	private String conversationCode = null;
	@HashIndexed
	private String networkCode = null;
	/** The digest the next run reads. */
	private String digest = null;
	/** How many runs it was built over. */
	private int runs = 0;
	private Date updatedAt = null;
}
