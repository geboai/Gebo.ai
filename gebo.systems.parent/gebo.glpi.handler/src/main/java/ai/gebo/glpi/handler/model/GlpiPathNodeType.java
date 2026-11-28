/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.model;

/**
 * The kinds of node of the GLPI tree: the folders (an entity, its tickets and
 * knowledge base sections, a ticket) and the resources (the ticket body, its
 * timeline items, its documents and the knowledge base articles).
 */
public enum GlpiPathNodeType {
	ENTITY(true), TICKETS_SECTION(true), KB_SECTION(true), TICKET(true), TICKET_BODY(false), FOLLOWUP(false),
	TASK(false), SOLUTION(false), DOCUMENT(false), KB_ARTICLE(false);

	private final boolean folder;

	private GlpiPathNodeType(boolean folder) {
		this.folder = folder;
	}

	public boolean isFolder() {
		return folder;
	}
}
