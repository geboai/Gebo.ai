/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.openproject.search.api.OpenProjectWorkPackagesSearchFilter;
import ai.gebo.openproject.search.api.TextMatchMode;

/**
 * The work packages filters of the API v3 a search filter becomes.
 */
public class OpenProjectWorkPackagesQueryTranslatorTest {
	final OpenProjectWorkPackagesQueryTranslator translator = new OpenProjectWorkPackagesQueryTranslator(
			() -> Map.of("bug", "7", "task", "1"), () -> Map.of("new", "1", "closed", "12"),
			() -> Map.of("high", "9"), project -> Map.of("gebo-demo", "3", "gebo-demo-sub", "4").get(project));

	@Test
	public void everyStatusByDefault() {
		OpenProjectWorkPackagesSearchFilter filter = new OpenProjectWorkPackagesSearchFilter();
		filter.setProjects(List.of("PROJECT:gebo-demo"));
		assertEquals(List.of("[{\"project\":{\"operator\":\"=\",\"values\":[\"3\"]}},"
				+ "{\"status\":{\"operator\":\"*\",\"values\":[]}}]"), translator.translate(filter));
	}

	@Test
	public void namesBecomeIdsAndUnknownNamesAreLeftOut() {
		OpenProjectWorkPackagesSearchFilter filter = new OpenProjectWorkPackagesSearchFilter();
		filter.setTypes(List.of("Bug", "Spike"));
		filter.setStatuses(List.of("closed"));
		filter.setPriorities(List.of("Urgent"));
		filter.setWorkPackageIds(List.of(38, 39));
		filter.setAssignedToMe(true);
		String json = translator.translate(filter).get(0);
		assertTrue(json.contains("{\"status\":{\"operator\":\"=\",\"values\":[\"12\"]}}"), json);
		assertTrue(json.contains("{\"type\":{\"operator\":\"=\",\"values\":[\"7\"]}}"), json);
		assertFalse(json.contains("priority"), json);
		assertTrue(json.contains("{\"id\":{\"operator\":\"=\",\"values\":[\"38\",\"39\"]}}"), json);
		assertTrue(json.contains("{\"assignee\":{\"operator\":\"=\",\"values\":[\"me\"]}}"), json);
	}

	@Test
	public void openAndDatesAndTextTerms() {
		OpenProjectWorkPackagesSearchFilter filter = new OpenProjectWorkPackagesSearchFilter();
		filter.setOpen(true);
		filter.setCreatedFrom("2026-11-01");
		filter.setUpdatedTo("2026-12-31");
		filter.setTextTerms(List.of("relay", "limit"));
		List<String> all = translator.translate(filter);
		assertEquals(1, all.size());
		assertTrue(all.get(0).contains("{\"status\":{\"operator\":\"o\",\"values\":[]}}"), all.get(0));
		assertTrue(all.get(0).contains("{\"createdAt\":{\"operator\":\"<>d\",\"values\":[\"2026-11-01\",\"\"]}}"),
				all.get(0));
		assertTrue(all.get(0).contains("{\"updatedAt\":{\"operator\":\"<>d\",\"values\":[\"\",\"2026-12-31\"]}}"),
				all.get(0));
		// every word must be there: one condition with all of them
		assertTrue(all.get(0).contains("{\"search\":{\"operator\":\"**\",\"values\":[\"relay limit\"]}}"), all.get(0));
		filter.setTextTermsMatchMode(TextMatchMode.ANY);
		List<String> any = translator.translate(filter);
		assertEquals(2, any.size());
		assertTrue(any.get(1).contains("{\"search\":{\"operator\":\"**\",\"values\":[\"limit\"]}}"), any.get(1));
	}

	@Test
	public void projectsNobodyCanSeeAreNotSearched() {
		OpenProjectWorkPackagesSearchFilter filter = new OpenProjectWorkPackagesSearchFilter();
		filter.setProjects(List.of("gebo-secret"));
		assertTrue(translator.translate(filter).isEmpty());
		filter.setProjects(List.of("gebo-secret", "gebo-demo-sub"));
		assertTrue(translator.translate(filter).get(0).contains("\"values\":[\"4\"]"));
		filter.setProjects(List.of("   "));
		assertFalse(OpenProjectWorkPackagesQueryTranslator.hasFilter(new OpenProjectWorkPackagesSearchFilter()));
	}
}
