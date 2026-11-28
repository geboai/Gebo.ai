/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.model;

/**
 * The sections of a Redmine project shown as folders, each bound to the
 * Redmine project module that has to be enabled for it to exist.
 */
public enum RedmineSection {
	ISSUES("Issues", "issue_tracking"), WIKI("Wiki", "wiki"), FILES("Files", "files");

	private final String label;
	private final String redmineModule;

	RedmineSection(String label, String redmineModule) {
		this.label = label;
		this.redmineModule = redmineModule;
	}

	public String getLabel() {
		return label;
	}

	public String getRedmineModule() {
		return redmineModule;
	}
}
