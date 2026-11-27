/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.model;

/**
 * The sections a project shows, one per OpenProject module the handler reads.
 * A section of a module the project does not enable, or the account cannot see,
 * is simply empty.
 */
public enum OpenProjectSection {
	WORK_PACKAGES("Work packages", "work_packages"), DOCUMENTS("Documents", "documents"),
	MEETINGS("Meetings", "meetings"), NEWS("News", "news");

	private final String label;
	private final String webPath;

	OpenProjectSection(String label, String webPath) {
		this.label = label;
		this.webPath = webPath;
	}

	public String getLabel() {
		return label;
	}

	/** The path of the section's page in the project, on the OpenProject web UI. */
	public String getWebPath() {
		return webPath;
	}
}
