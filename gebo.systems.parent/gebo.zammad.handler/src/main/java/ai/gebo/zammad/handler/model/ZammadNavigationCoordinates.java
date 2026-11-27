/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.model;

import ai.gebo.systems.abstraction.layer.model.AbstractNavigationCoordinates;

public class ZammadNavigationCoordinates extends AbstractNavigationCoordinates<ZammadPathComponent> {
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "{root=" + (getRoot() != null ? getRoot().getCode() : null) + ", steps=" + getBrowsingStepsCustom()
				+ "}";
	}

	/**
	 * The position as an administrator reads it in the job messages, e.g. "ticket
	 * 12 of the Zammad group 3".
	 */
	public String describe() {
		String rootCode = getRoot() != null ? getRoot().getCode() : null;
		String where = rootCode == null ? "an unknown Zammad root"
				: rootCode.startsWith("GROUP:") ? "the Zammad group " + rootCode.substring("GROUP:".length())
				: rootCode.startsWith("KNOWLEDGE_BASE:")
						? "the Zammad knowledge base " + rootCode.substring("KNOWLEDGE_BASE:".length())
						: rootCode;
		java.util.List<ZammadPathComponent> steps = getBrowsingStepsCustom();
		if (steps == null || steps.isEmpty()) {
			return where;
		}
		ZammadPathComponent last = steps.get(steps.size() - 1);
		String what = last.type == null ? String.valueOf(last.id) : switch (last.type) {
		case TICKET, TICKET_CONTENT -> "ticket " + last.id;
		case CATEGORY -> "category " + last.id;
		case ANSWER, ANSWER_CONTENT -> "answer " + last.id;
		case ATTACHMENT -> "attachment " + last.id;
		default -> last.toString();
		};
		return what + " of " + where;
	}
}
