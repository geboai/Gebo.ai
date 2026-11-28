/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.model;

/**
 * The kinds of nodes of the Zammad virtual filesystem. The roots are the groups
 * (holding their tickets) and the knowledge bases (holding their categories); a
 * ticket holds its rendered conversation (its public articles), each of its
 * internal notes as a document of its own ({@link #INTERNAL_NOTE}) and the
 * attachments of its articles, a category its sub-categories and its answers,
 * an answer its rendered body and its attachments. The names are stored in the
 * paths and the meta infos of the documents: values are only added.
 */
public enum ZammadPathNodeType {
	GROUP, KNOWLEDGE_BASE, TICKET, TICKET_CONTENT, CATEGORY, ANSWER, ANSWER_CONTENT, ATTACHMENT,
	/**
	 * An internal article of a ticket with a body, rendered alone: Zammad shows it
	 * to the agents of the ticket's group, never to its customer. Its id is the
	 * article id.
	 */
	INTERNAL_NOTE;

	public boolean isFolder() {
		return this == GROUP || this == KNOWLEDGE_BASE || this == TICKET || this == CATEGORY || this == ANSWER;
	}

	/** The node types a data source root can be. */
	public boolean isRoot() {
		return this == GROUP || this == KNOWLEDGE_BASE;
	}
}
