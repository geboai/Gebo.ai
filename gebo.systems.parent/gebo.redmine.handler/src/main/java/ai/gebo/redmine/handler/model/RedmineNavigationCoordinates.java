/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.model;

import ai.gebo.systems.abstraction.layer.model.AbstractNavigationCoordinates;

public class RedmineNavigationCoordinates extends AbstractNavigationCoordinates<RedminePathComponent> {
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "{root=" + (getRoot() != null ? getRoot().getCode() : null) + ", steps=" + getBrowsingStepsCustom()
				+ "}";
	}

	/**
	 * The position as an administrator reads it in the job messages, e.g. "wiki
	 * page Install_guide of the Redmine project gebo-demo".
	 */
	public String describe() {
		String rootCode = getRoot() != null ? getRoot().getCode() : null;
		String project = rootCode != null && rootCode.startsWith("PROJECT:") ? rootCode.substring("PROJECT:".length())
				: rootCode;
		String where = "the Redmine project " + project;
		java.util.List<RedminePathComponent> steps = getBrowsingStepsCustom();
		if (steps == null || steps.isEmpty()) {
			return where;
		}
		RedminePathComponent last = steps.get(steps.size() - 1);
		String what = last.type == null ? String.valueOf(last.id) : switch (last.type) {
		case SECTION -> "the " + String.valueOf(last.id).toLowerCase() + " section";
		case ISSUE, ISSUE_CONTENT -> "issue #" + last.id;
		case WIKI_PAGE, WIKI_CONTENT -> "wiki page " + last.id;
		case ATTACHMENT -> "attachment " + last.id;
		case FILE -> "file " + last.id;
		case PRIVATE_NOTE -> "private note " + last.id;
		default -> last.toString();
		};
		return what + " of " + where;
	}
}
