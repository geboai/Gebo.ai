/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.redmine.search.api.RedmineIssuesSearchFilter;
import ai.gebo.redmine.search.api.TextMatchMode;

class RedmineIssuesQueryTranslatorTest {
	private final RedmineIssuesQueryTranslator translator = new RedmineIssuesQueryTranslator(
			() -> Map.of("bug", 1, "feature", 2), () -> Map.of("new", 1, "resolved", 3),
			() -> Map.of("high", 4));

	@Test
	void oneQueryPerProjectWithTheShortFilters() {
		RedmineIssuesSearchFilter filter = new RedmineIssuesSearchFilter();
		filter.setProjects(List.of("PROJECT:gebo-core", "gebo-ui", "gebo-ui"));
		filter.setTrackers(List.of("Bug", "Unknown tracker"));
		filter.setPriorities(List.of("HIGH"));
		filter.setOpen(true);
		filter.setAssignedToMe(true);
		filter.setUpdatedFrom("2026-01-01");
		filter.setUpdatedTo("2026-03-31");
		List<RedmineIssuesQuery> queries = translator.translate(filter);
		assertEquals(2, queries.size());
		String query = queries.get(0).toString();
		assertTrue(query.contains("project_id=gebo-core"), query);
		assertTrue(query.contains("tracker_id=1,"), query);
		assertTrue(query.contains("priority_id=4"), query);
		assertTrue(query.contains("status_id=o"), query);
		assertTrue(query.contains("assigned_to_id=me"), query);
		assertTrue(query.contains("updated_on=><2026-01-01|2026-03-31"), query);
		assertTrue(queries.get(1).toString().contains("project_id=gebo-ui"));
	}

	@Test
	void statusNamesWinOverOpen() {
		RedmineIssuesSearchFilter filter = new RedmineIssuesSearchFilter();
		filter.setStatuses(List.of("New", "Resolved"));
		filter.setOpen(false);
		String query = translator.translate(filter).get(0).toString();
		assertTrue(query.contains("status_id=1|3"), query);
		assertTrue(query.contains("project_id=null"), query);
	}

	@Test
	void anyStatusByDefaultAndBadDatesIgnored() {
		RedmineIssuesSearchFilter filter = new RedmineIssuesSearchFilter();
		filter.setIssueIds(List.of(12, 13));
		filter.setCreatedFrom("last week");
		String query = translator.translate(filter).get(0).toString();
		assertTrue(query.contains("status_id=*"), query);
		assertTrue(query.contains("issue_id=12,13"), query);
		assertTrue(query.contains("created_on=null"), query);
	}

	@Test
	void dateRanges() {
		assertEquals(">=2026-01-01", RedmineIssuesQueryTranslator.dateRange("2026-01-01", null));
		assertEquals("<=2026-01-01", RedmineIssuesQueryTranslator.dateRange(null, "2026-01-01"));
		assertNull(RedmineIssuesQueryTranslator.dateRange(null, " "));
	}

	@Test
	void emptyFilterHasNothingToSearch() {
		RedmineIssuesSearchFilter filter = new RedmineIssuesSearchFilter();
		assertFalse(RedmineIssuesQueryTranslator.hasStructuredFilter(filter));
		assertFalse(RedmineIssuesQueryTranslator.hasTextTerms(filter));
		filter.setTextTerms(List.of(" "));
		assertFalse(RedmineIssuesQueryTranslator.hasTextTerms(filter));
		filter.setTextTerms(List.of("timeout"));
		assertTrue(RedmineIssuesQueryTranslator.hasTextTerms(filter));
		assertFalse(RedmineIssuesQueryTranslator.hasStructuredFilter(filter));
	}

	@Test
	void projectsAloneAreNoIssueAttributes() {
		RedmineIssuesSearchFilter filter = new RedmineIssuesSearchFilter();
		filter.setProjects(List.of("gebo-core"));
		assertTrue(RedmineIssuesQueryTranslator.hasStructuredFilter(filter));
		assertFalse(RedmineIssuesQueryTranslator.hasIssueAttributesFilter(filter));
		filter.setOpen(true);
		assertTrue(RedmineIssuesQueryTranslator.hasIssueAttributesFilter(filter));
	}

	@Test
	void textTermsFilterTheIssuesAllInOneValueAnyOnePerTerm() {
		RedmineIssuesSearchFilter filter = new RedmineIssuesSearchFilter();
		filter.setProjects(List.of("gebo-core", "gebo-ui"));
		filter.setTrackers(List.of("Bug"));
		filter.setTextTerms(List.of("timeout", " proxy ", "timeout"));
		List<RedmineIssuesQuery> all = translator.translate(filter);
		assertEquals(2, all.size());
		assertTrue(all.get(0).toString().contains("any_searchable=~timeout proxy,"), all.get(0).toString());
		filter.setTextTermsMatchMode(TextMatchMode.ANY);
		List<RedmineIssuesQuery> any = translator.translate(filter);
		assertEquals(4, any.size());
		assertTrue(any.get(0).toString().contains("project_id=gebo-core"), any.get(0).toString());
		assertTrue(any.get(0).toString().contains("any_searchable=~timeout,"), any.get(0).toString());
		assertTrue(any.get(1).toString().contains("any_searchable=~proxy,"), any.get(1).toString());
		assertTrue(any.get(2).toString().contains("project_id=gebo-ui"), any.get(2).toString());
	}

	@Test
	void noTextTermsNoTextFilter() {
		RedmineIssuesSearchFilter filter = new RedmineIssuesSearchFilter();
		filter.setOpen(true);
		List<RedmineIssuesQuery> queries = translator.translate(filter);
		assertEquals(1, queries.size());
		assertTrue(queries.get(0).toString().contains("any_searchable=null"), queries.get(0).toString());
	}
}
