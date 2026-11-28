/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.zammad.handler.impl.ZammadTicketsQueryTranslator.Named;
import ai.gebo.zammad.search.api.TextMatchMode;
import ai.gebo.zammad.search.api.ZammadTicketsSearchFilter;

class ZammadTicketsQueryTranslatorTest {
	private static final List<Named> GROUPS = List.of(new Named(2L, "Gebo Support", null),
			new Named(3L, "Gebo Secret", null));
	private static final List<Named> STATES = List.of(new Named(1L, "new", "new"), new Named(2L, "open", "open"),
			new Named(3L, "pending reminder", "pending reminder"), new Named(4L, "closed", "closed"),
			new Named(5L, "merged", "merged"), new Named(6L, "pending close", "pending action"));
	private static final List<Named> PRIORITIES = List.of(new Named(1L, "1 low", null),
			new Named(2L, "2 normal", null), new Named(3L, "3 high", null));

	private final ZammadTicketsQueryTranslator translator = new ZammadTicketsQueryTranslator(() -> GROUPS,
			() -> STATES, () -> PRIORITIES);

	@SuppressWarnings("unchecked")
	private static List<Map<String, Object>> conditions(Map<String, Object> condition) {
		assertEquals("AND", condition.get("operator"));
		return (List<Map<String, Object>>) condition.get("conditions");
	}

	private static Map<String, Object> named(List<Map<String, Object>> conditions, String name) {
		return conditions.stream().filter(x -> name.equals(x.get("name"))).findFirst().orElse(null);
	}

	@Test
	void emptyFilterHasNoCriteria() {
		assertFalse(ZammadTicketsQueryTranslator.hasFilter(null));
		ZammadTicketsSearchFilter filter = new ZammadTicketsSearchFilter();
		assertFalse(ZammadTicketsQueryTranslator.hasFilter(filter));
		filter.setTextTerms(List.of(" ", ""));
		filter.setOwnedByMe(false);
		filter.setSearchKnowledgeBase(true);
		assertFalse(ZammadTicketsQueryTranslator.hasFilter(filter));
		filter.setOpen(false);
		assertTrue(ZammadTicketsQueryTranslator.hasFilter(filter));
	}

	@Test
	void groupsByCodeNameOrCatalogLabel() {
		ZammadTicketsSearchFilter filter = new ZammadTicketsSearchFilter();
		filter.setGroups(List.of("GROUP:3", "gebo support", "Tickets of Gebo Secret"));
		Map<String, Object> group = named(conditions(translator.translate(filter)), "ticket.group_id");
		assertEquals("is", group.get("operator"));
		assertEquals(List.of("3", "2"), group.get("value"));
		filter.setGroups(List.of("Nowhere"));
		assertNull(translator.translate(filter), "an unknown group matches no ticket");
	}

	@Test
	void textTermsSearchTitlesAndArticles() {
		ZammadTicketsSearchFilter filter = new ZammadTicketsSearchFilter();
		filter.setTextTerms(List.of("relay", " relay ", "Friday"));
		filter.setTextTermsMatchMode(TextMatchMode.ALL);
		Map<String, Object> text = conditions(translator.translate(filter)).get(0);
		assertEquals("AND", text.get("operator"));
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> byTerm = (List<Map<String, Object>>) text.get("conditions");
		assertEquals(2, byTerm.size(), "duplicated terms are searched once");
		assertEquals("OR", byTerm.get(0).get("operator"));
		assertEquals(List.of(Map.of("name", "ticket.title", "operator", "contains", "value", "relay"),
				Map.of("name", "article.body", "operator", "contains", "value", "relay")), byTerm.get(0).get("conditions"));
		filter.setTextTermsMatchMode(null);
		assertEquals("OR", conditions(translator.translate(filter)).get(0).get("operator"));
	}

	@Test
	void openIsEveryStateNotClosedNorMerged() {
		ZammadTicketsSearchFilter filter = new ZammadTicketsSearchFilter();
		filter.setOpen(true);
		assertEquals(List.of("1", "2", "3", "6"),
				named(conditions(translator.translate(filter)), "ticket.state_id").get("value"));
		filter.setOpen(false);
		assertEquals(List.of("4", "5"), named(conditions(translator.translate(filter)), "ticket.state_id").get("value"));
		filter.setStates(List.of("Closed", "open"));
		assertEquals(List.of("4"), named(conditions(translator.translate(filter)), "ticket.state_id").get("value"));
		filter.setStates(List.of("open"));
		assertNull(translator.translate(filter), "open tickets that are closed: none");
	}

	@Test
	void prioritiesByTheirNameEnd() {
		ZammadTicketsSearchFilter filter = new ZammadTicketsSearchFilter();
		filter.setPriorities(List.of("High", "2 normal"));
		assertEquals(List.of("3", "2"),
				named(conditions(translator.translate(filter)), "ticket.priority_id").get("value"));
		filter.setPriorities(List.of("urgent"));
		assertNull(translator.translate(filter));
	}

	@Test
	void numbersOwnerAndDates() {
		ZammadTicketsSearchFilter filter = new ZammadTicketsSearchFilter();
		filter.setTicketNumbers(List.of("#97002", "97003"));
		filter.setOwnedByMe(true);
		filter.setCreatedFrom("2026-11-01");
		filter.setUpdatedTo("2026-11-27");
		List<Map<String, Object>> conditions = conditions(translator.translate(filter));
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> numbers = (List<Map<String, Object>>) conditions.get(0).get("conditions");
		assertEquals("OR", conditions.get(0).get("operator"));
		assertEquals("97002", numbers.get(0).get("value"));
		assertEquals("97003", numbers.get(1).get("value"));
		Map<String, Object> owner = named(conditions, "ticket.owner_id");
		assertEquals("current_user.id", owner.get("pre_condition"));
		Map<String, Object> created = named(conditions, "ticket.created_at");
		assertEquals("after (absolute)", created.get("operator"));
		assertEquals("2026-11-01T00:00:00Z", created.get("value"));
		Map<String, Object> updated = named(conditions, "ticket.updated_at");
		assertEquals("before (absolute)", updated.get("operator"));
		assertEquals("2026-11-28T00:00:00Z", updated.get("value"), "on or before a day includes that day");
		filter.setCreatedFrom("27/11/2026");
		assertThrows(IllegalArgumentException.class, () -> translator.translate(filter));
	}
}
