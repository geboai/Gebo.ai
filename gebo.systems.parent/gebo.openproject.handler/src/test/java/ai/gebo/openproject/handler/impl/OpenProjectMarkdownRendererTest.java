/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * The markdown of the OpenProject items, from HAL resources shaped as the API v3
 * answers them.
 */
public class OpenProjectMarkdownRendererTest {
	static final String BASE = "http://op.test";
	static final OpenProjectMarkdownRenderer.UserNames USERS = (resource, link) -> {
		String title = OpenProjectHal.linkTitle(resource, link);
		return title != null ? title : "user #" + OpenProjectHal.linkId(resource, link);
	};

	static Map<String, Object> link(String href, String title) {
		return title != null ? Map.of("href", href, "title", title) : Map.of("href", href);
	}

	/** A link without a target, as the API v3 writes a missing parent. */
	static Map<String, Object> nullHref() {
		Map<String, Object> link = new HashMap<String, Object>();
		link.put("href", null);
		return link;
	}

	static Map<String, Object> workPackage() {
		return Map.of("id", 38, "subject", "SMTP relay drops attachments", "description",
				Map.of("format", "markdown", "raw", "Mails with **large attachments** are dropped."), "createdAt",
				"2026-11-27T19:06:47Z", "percentageDone", 20, "_links",
				Map.of("type", link("/api/v3/types/7", "Bug"), "status", link("/api/v3/statuses/7", "In progress"),
						"project", link("/api/v3/projects/3", "Gebo demo"), "author", link("/api/v3/users/4", null),
						"parent", nullHref()));
	}

	@Test
	public void workPackageCarriesFieldsRelationsChildrenAttachmentsAndActivity() {
		List<Map<String, Object>> relations = List.of(Map.of("type", "relates", "reverseType", "relates", "_links",
				Map.of("from", link("/api/v3/work_packages/41", "PDF export"), "to",
						link("/api/v3/work_packages/38", "SMTP relay drops attachments"))),
				Map.of("type", "blocks", "reverseType", "blocked", "_links",
						Map.of("from", link("/api/v3/work_packages/38", "SMTP"), "to",
								link("/api/v3/work_packages/50", "Release 1.1"))));
		List<Map<String, Object>> children = List.of(Map.of("id", 39, "subject", "Raise the relay size limit", "_links",
				Map.of("status", link("/api/v3/statuses/1", "New"))));
		List<Map<String, Object>> attachments = List.of(Map.of("id", 3, "fileName", "relay.log", "fileSize", 58));
		List<Map<String, Object>> activities = List.of(
				Map.of("comment", Map.of("raw", ""), "details", List.of(Map.of("raw", "Type set to Bug")), "createdAt",
						"2026-11-27T19:06:47Z", "_links", Map.of("user", link("/api/v3/users/4", null))),
				Map.of("comment", Map.of("raw", "Confirmed on production too."), "details", List.of(), "internal",
						true, "createdAt", "2026-11-27T19:07:00Z", "_links",
						Map.of("user", link("/api/v3/users/5", "Read Only"))));
		String md = OpenProjectMarkdownRenderer.renderWorkPackage(workPackage(), relations, children, attachments,
				activities, USERS, BASE);
		assertTrue(md.startsWith("# Bug #38: SMTP relay drops attachments"), md);
		assertTrue(md.contains("- Status: In progress"), md);
		assertTrue(md.contains("- Project: Gebo demo"), md);
		assertTrue(md.contains("- Author: user #4"), md);
		assertTrue(md.contains("- % Complete: 20%"), md);
		assertTrue(md.contains("- URL: http://op.test/work_packages/38"), md);
		assertTrue(md.contains("Mails with **large attachments** are dropped."), md);
		assertFalse(md.contains("Parent work package"), md);
		assertTrue(md.contains("- #39 Raise the relay size limit (New)"), md);
		// the relation is read from this work package's side
		assertTrue(md.contains("- relates #41 PDF export"), md);
		assertTrue(md.contains("- blocks #50 Release 1.1"), md);
		assertTrue(md.contains("- relay.log (58 bytes)"), md);
		assertTrue(md.contains("- Type set to Bug"), md);
		assertTrue(md.contains("### Read Only, 2026-11-27T19:07:00Z (internal comment)"), md);
		assertTrue(md.contains("Confirmed on production too."), md);
	}

	@Test
	public void meetingListsItsAgendaAndDocumentAndNewsTheirText() {
		Map<String, Object> meeting = Map.of("id", 7, "title", "Weekly sync", "startTime", "2026-11-24T10:00:00Z",
				"location", "Room 1", "_links", Map.of("project", link("/api/v3/projects/3", "Gebo demo")));
		List<Map<String, Object>> agenda = List.of(Map.of("title", "SMTP relay incident", "durationInMinutes", 15,
				"notes", Map.of("raw", "Decide the new size limit."), "_links",
				Map.of("workPackage", link("/api/v3/work_packages/38", "SMTP relay"))));
		String md = OpenProjectMarkdownRenderer.renderMeeting(meeting, agenda, List.of(), USERS, BASE);
		assertTrue(md.startsWith("# Meeting: Weekly sync"), md);
		assertTrue(md.contains("- Location: Room 1"), md);
		assertTrue(md.contains("### SMTP relay incident (work package #38 SMTP relay)"), md);
		assertTrue(md.contains("- Duration (minutes): 15"), md);
		assertTrue(md.contains("Decide the new size limit."), md);

		String doc = OpenProjectMarkdownRenderer.renderDocument(Map.of("id", 1, "title", "Install guide",
				"description", Map.of("raw", "Run `docker compose up`."), "_links", Map.of()), List.of(), BASE);
		assertTrue(doc.contains("# Install guide") && doc.contains("Run `docker compose up`."), doc);

		String news = OpenProjectMarkdownRenderer.renderNews(Map.of("id", 3, "title", "Demo goes live", "summary",
				"Launch notice", "description", Map.of("raw", "The demo is **live**."), "_links",
				Map.of("author", link("/api/v3/users/4", "OpenProject Admin"))), USERS, BASE);
		assertTrue(news.contains("# News: Demo goes live") && news.contains("Launch notice")
				&& news.contains("The demo is **live**.") && news.contains("- Author: OpenProject Admin"), news);
	}
}
