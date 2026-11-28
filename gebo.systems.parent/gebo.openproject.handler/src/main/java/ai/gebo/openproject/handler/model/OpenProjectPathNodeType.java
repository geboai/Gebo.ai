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
 * The kinds of nodes of the OpenProject virtual filesystem: a project holds its
 * sections, work packages hold their rendered content, their attachments, their
 * internal comments and their child work packages, documents and meetings hold
 * their rendered content and their attachments, news are single documents.
 * <p>
 * An internal comment (an activity OpenProject flags {@code internal}, shown only
 * to the roles with {@code view_internal_comments}) is its own document, never
 * part of the work package's rendered content, so that whoever may read the work
 * package does not read it too.
 * </p>
 */
public enum OpenProjectPathNodeType {
	PROJECT, SECTION, WORK_PACKAGE, WORK_PACKAGE_CONTENT, DOCUMENT, DOCUMENT_CONTENT, MEETING, MEETING_CONTENT,
	NEWS, ATTACHMENT, INTERNAL_COMMENT;

	public boolean isFolder() {
		return this == PROJECT || this == SECTION || this == WORK_PACKAGE || this == DOCUMENT || this == MEETING;
	}
}
