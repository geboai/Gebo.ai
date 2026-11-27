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
		note.setPrivateNotes(true);
		issue.setJournals(List.of(change, note));
		String text = RedmineMarkdownRenderer.renderIssue(issue, "http://redmine");
		assertTrue(text.startsWith("# Bug #12: Crash on save"), text);
		assertTrue(text.contains("- Status: New"), text);
		assertTrue(text.contains("- URL: http://redmine/issues/12"), text);
		assertTrue(text.contains("## Description\n\nIt crashes."), text);
		assertTrue(text.contains("### Ada (private note)\n\nReproduced."), text);
		assertFalse(text.contains("## Related issues"), text);
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
