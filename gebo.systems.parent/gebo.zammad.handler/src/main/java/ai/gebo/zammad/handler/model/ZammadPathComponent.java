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
 * One typed step of a Zammad path: its node type and its id (a group, knowledge
 * base, ticket, category or answer id; an attachment of a ticket is
 * {@code <article id>-<attachment id>}).
 */
public class ZammadPathComponent {
	public ZammadPathNodeType type = null;
	public String id = null;

	public ZammadPathComponent() {
	}

	public ZammadPathComponent(ZammadPathNodeType type, String id) {
		this.type = type;
		this.id = id;
	}

	@Override
	public String toString() {
		return type + ":" + id;
	}
}
