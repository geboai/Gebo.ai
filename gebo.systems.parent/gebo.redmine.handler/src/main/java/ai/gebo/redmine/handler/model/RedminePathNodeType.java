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
 * The kinds of node of the Redmine virtual filesystem.
 * <ul>
 * <li>PROJECT: a root, one per Redmine project</li>
 * <li>SECTION: the Issues, Wiki and Files folders of a project</li>
 * <li>ISSUE: an issue as a folder, holding its content, attachments and
 * sub-issues</li>
 * <li>ISSUE_CONTENT: the issue itself (fields, description and public notes)
 * as a markdown document</li>
 * <li>WIKI_PAGE: a wiki page as a folder, holding its content, attachments and
 * child pages</li>
 * <li>WIKI_CONTENT: the wiki page text as a markdown document</li>
 * <li>ATTACHMENT: a file attached to an issue or a wiki page</li>
 * <li>FILE: a file of the project Files section</li>
 * <li>PRIVATE_NOTE: a private note of an issue (a journal whose notes are
 * private) as a markdown document of its own, so that it is not merged into the
 * issue content that whoever sees the issue reads; its id is the journal
 * id</li>
 * </ul>
 * Values are only added: the names are stored in the data sources paths.
 */
public enum RedminePathNodeType {
	PROJECT, SECTION, ISSUE, ISSUE_CONTENT, WIKI_PAGE, WIKI_CONTENT, ATTACHMENT, FILE, PRIVATE_NOTE;

	public boolean isFolder() {
		return this == PROJECT || this == SECTION || this == ISSUE || this == WIKI_PAGE;
	}
}
