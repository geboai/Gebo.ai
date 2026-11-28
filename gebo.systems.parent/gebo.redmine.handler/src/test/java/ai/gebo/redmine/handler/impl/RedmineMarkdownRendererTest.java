/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.redmine.client.model.IdName;
import ai.gebo.redmine.client.model.Issue;
import ai.gebo.redmine.client.model.IssueJournalsInner;
import ai.gebo.redmine.client.model.IssueStatusSummary;
import ai.gebo.redmine.client.model.WikiPage;

class RedmineMarkdownRendererTest {

	private static IdName idName(int id, String name) {
		IdName value = new IdName();
		value.setId(id);
		value.setName(name);
		return value;
	}

	@Test
	void issueWithNotes() {
		Issue issue = new Issue();
		issue.setId(12);
		issue.setSubject("Crash on save");
		issue.setTracker(idName(1, "Bug"));
		issue.setProject(idName(3, "Gebo core"));
		IssueStatusSummary status = new IssueStatusSummary();
		status.setName("New");
		issue.setStatus(status);
		issue.setDescription("It crashes.");
		IssueJournalsInner change = new IssueJournalsInner();
		change.setNotes("");
		IssueJournalsInner note = new IssueJournalsInner();
		note.setUser(idName(5, "Ada"));
		note.setNotes("Reproduced.");
		note.setPrivateNotes(false);
		IssueJournalsInner privateNote = new IssueJournalsInner();
		privateNote.setUser(idName(6, "Bea"));
		privateNote.setNotes("The customer is Acme.");
		privateNote.setPrivateNotes(true);
		issue.setJournals(List.of(change, note, privateNote));
		String text = RedmineMarkdownRenderer.renderIssue(issue, "http://redmine");
		assertTrue(text.startsWith("# Bug #12: Crash on save"), text);
		assertTrue(text.contains("- Status: New"), text);
		assertTrue(text.contains("- URL: http://redmine/issues/12"), text);
		assertTrue(text.contains("## Description\n\nIt crashes."), text);
		assertTrue(text.contains("### Ada\n\nReproduced."), text);
		assertFalse(text.contains("## Related issues"), text);
		// a private note is a document of its own, never part of the issue
		assertFalse(text.contains("The customer is Acme."), text);
		assertFalse(text.contains("Bea"), text);
		assertFalse(text.contains("private note"), text);
	}

	@Test
	void issueWhoseOnlyNotesArePrivateHasNoNotesSection() {
		Issue issue = new Issue();
		issue.setId(13);
		issue.setSubject("Leak");
		IssueJournalsInner privateNote = new IssueJournalsInner();
		privateNote.setUser(idName(6, "Bea"));
		privateNote.setNotes("Internal only.");
		privateNote.setPrivateNotes(true);
		issue.setJournals(List.of(privateNote));
		String text = RedmineMarkdownRenderer.renderIssue(issue, "http://redmine");
		assertFalse(text.contains("## Notes"), text);
		assertFalse(text.contains("Internal only."), text);
	}

	@Test
	void privateNoteTellsItsIssueAuthorAndDate() {
		Issue issue = new Issue();
		issue.setId(12);
		issue.setSubject("Crash on save");
		issue.setTracker(idName(1, "Bug"));
		issue.setProject(idName(3, "Gebo core"));
		IssueJournalsInner privateNote = new IssueJournalsInner();
		privateNote.setId(56);
		privateNote.setUser(idName(6, "Bea"));
		privateNote.setNotes("  The customer is Acme.\n");
		privateNote.setPrivateNotes(true);
		privateNote.setCreatedOn(OffsetDateTime.parse("2026-01-02T10:00:00Z"));
		privateNote.setUpdatedOn(OffsetDateTime.parse("2026-01-02T10:00:00Z"));
		String text = RedmineMarkdownRenderer.renderPrivateNote(issue, privateNote, "http://redmine");
		assertTrue(text.startsWith("# Private note on Bug #12: Crash on save"), text);
		assertTrue(text.contains("- Project: Gebo core"), text);
		assertTrue(text.contains("- Issue: #12"), text);
		assertTrue(text.contains("- Author: Bea"), text);
		assertTrue(text.contains("- Created on: 2026-01-02T10:00:00Z"), text);
		assertTrue(text.contains("- Visibility: private note"), text);
		assertTrue(text.contains("- URL: http://redmine/issues/12"), text);
		assertTrue(text.endsWith("## Note\n\nThe customer is Acme.\n"), text);
		// not edited: no update date
		assertFalse(text.contains("Updated on"), text);
	}

	@Test
	void wikiPage() {
		WikiPage page = new WikiPage();
		page.setTitle("Install Guide");
		page.setText("h1. Install");
		String text = RedmineMarkdownRenderer.renderWikiPage(page, "gebo-core", "http://redmine");
		assertTrue(text.startsWith("# Install Guide"), text);
		assertTrue(text.contains("http://redmine/projects/gebo-core/wiki/Install%20Guide"), text);
		assertTrue(text.contains("h1. Install"), text);
	}
}
