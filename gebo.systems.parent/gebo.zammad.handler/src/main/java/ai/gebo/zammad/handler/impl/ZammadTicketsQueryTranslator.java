/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.impl;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.zammad.search.api.TextMatchMode;
import ai.gebo.zammad.search.api.ZammadTicketsSearchFilter;

/**
 * Translates a {@link ZammadTicketsSearchFilter} into the condition of a
 * Zammad ticket search ({@code POST /tickets/search}): an AND of every given
 * criterion, in the nested ("expert") form, which Zammad evaluates on its
 * database, so the search works with and without Elasticsearch.
 * <ul>
 * <li>groups, states and priorities are named by the user: they are matched,
 * case insensitively, against the names Zammad lists (a priority also by the
 * end of its name: "high" is "3 high"); a list none of whose names is known
 * matches no ticket</li>
 * <li>open is any state whose type is not closed, merged or removed</li>
 * <li>each text term is found in the ticket title or in the body of any of its
 * articles; ANY joins the terms with OR, ALL with AND</li>
 * <li>dates are whole days, in UTC</li>
 * </ul>
 */
public class ZammadTicketsQueryTranslator {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZammadTicketsQueryTranslator.class);
	/** The state types of the tickets that are not open. */
	static final Set<String> NOT_OPEN_STATE_TYPES = Set.of("closed", "merged", "removed");

	/** A group, state or priority as Zammad lists it; type is the state type of a state. */
	public record Named(Long id, String name, String type) {
	}

	private final Supplier<List<Named>> groups;
	private final Supplier<List<Named>> states;
	private final Supplier<List<Named>> priorities;

	public ZammadTicketsQueryTranslator(Supplier<List<Named>> groups, Supplier<List<Named>> states,
			Supplier<List<Named>> priorities) {
		this.groups = groups;
		this.states = states;
		this.priorities = priorities;
	}

	private static boolean given(List<String> values) {
		return values != null && values.stream().anyMatch(x -> x != null && !x.isBlank());
	}

	/** Whether the filter has any criterion at all. */
	public static boolean hasFilter(ZammadTicketsSearchFilter filter) {
		return filter != null && (given(filter.getGroups()) || given(filter.getTicketNumbers())
				|| given(filter.getTextTerms()) || given(filter.getStates()) || filter.getOpen() != null
				|| given(filter.getPriorities()) || Boolean.TRUE.equals(filter.getOwnedByMe())
				|| filter.getCreatedFrom() != null || filter.getCreatedTo() != null || filter.getUpdatedFrom() != null
				|| filter.getUpdatedTo() != null);
	}

	/** The text terms of the filter, trimmed, without blanks and duplicates. */
	public static List<String> textTerms(ZammadTicketsSearchFilter filter) {
		Set<String> terms = new LinkedHashSet<String>();
		if (filter != null && filter.getTextTerms() != null) {
			for (String term : filter.getTextTerms()) {
				if (term != null && !term.isBlank()) {
					terms.add(term.trim());
				}
			}
		}
		return new ArrayList<String>(terms);
	}

	static Map<String, Object> leaf(String name, String operator, Object value) {
		Map<String, Object> condition = new LinkedHashMap<String, Object>();
		condition.put("name", name);
		condition.put("operator", operator);
		condition.put("value", value);
		return condition;
	}

	static Map<String, Object> group(String operator, List<Map<String, Object>> conditions) {
		Map<String, Object> condition = new LinkedHashMap<String, Object>();
		condition.put("operator", operator);
		condition.put("conditions", conditions);
		return condition;
	}

	private static String normalize(String value) {
		return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
	}

	/**
	 * The ids of the named items among the known ones, as strings; an empty list
	 * when no name is known.
	 */
	static List<String> idsOf(List<String> names, List<Named> known, boolean byNameEnd, String what) {
		Set<String> ids = new LinkedHashSet<String>();
		for (String name : names) {
			String wanted = normalize(name);
			if (wanted.isEmpty()) {
				continue;
			}
			boolean found = false;
			for (Named item : known) {
				String itemName = normalize(item.name());
				if (itemName.equals(wanted) || (byNameEnd && itemName.endsWith(" " + wanted))) {
					ids.add(String.valueOf(item.id()));
					found = true;
				}
			}
			if (!found) {
				LOGGER.debug("No Zammad {} named {}", what, name);
			}
		}
		return new ArrayList<String>(ids);
	}

	/** The label of a group root in the catalog: "Tickets of Gebo Support". */
	static final String GROUP_LABEL_PREFIX = "tickets of ";

	/**
	 * The ids of the groups a model named: by the code of the catalog
	 * ({@code GROUP:<id>}), by name, or by the catalog label ("Tickets of
	 * <name>").
	 */
	List<String> groupIds(List<String> values) {
		Set<String> ids = new LinkedHashSet<String>();
		List<String> names = new ArrayList<String>();
		for (String value : values) {
			String v = value != null ? value.trim() : "";
			if (v.regionMatches(true, 0, ZammadNavigationUtil.GROUP_PREFIX, 0, ZammadNavigationUtil.GROUP_PREFIX.length())
					&& v.substring(ZammadNavigationUtil.GROUP_PREFIX.length()).matches("\\d+")) {
				ids.add(v.substring(ZammadNavigationUtil.GROUP_PREFIX.length()));
			} else if (normalize(v).startsWith(GROUP_LABEL_PREFIX)) {
				names.add(v.substring(GROUP_LABEL_PREFIX.length()));
			} else if (!v.isEmpty()) {
				names.add(v);
			}
		}
		if (!names.isEmpty()) {
			ids.addAll(idsOf(names, groups.get(), false, "group"));
		}
		return new ArrayList<String>(ids);
	}

	/** The start of a day in UTC, as Zammad reads an absolute date. */
	static String dayStart(String day, int plusDays) {
		try {
			return LocalDate.parse(day.trim()).plusDays(plusDays).atStartOfDay().toInstant(ZoneOffset.UTC).toString();
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Not a yyyy-MM-dd date: " + day, e);
		}
	}

	private static void dates(List<Map<String, Object>> conditions, String attribute, String from, String to) {
		if (from != null && !from.isBlank()) {
			conditions.add(leaf(attribute, "after (absolute)", dayStart(from, 0)));
		}
		if (to != null && !to.isBlank()) {
			// "on or before" a day is before the start of the next one
			conditions.add(leaf(attribute, "before (absolute)", dayStart(to, 1)));
		}
	}

	/**
	 * The search condition of a filter, or null when one of its criteria matches
	 * no ticket (a group, state or priority list none of whose names is known).
	 */
	public Map<String, Object> translate(ZammadTicketsSearchFilter filter) {
		List<Map<String, Object>> conditions = new ArrayList<Map<String, Object>>();
		if (given(filter.getGroups())) {
			List<String> ids = groupIds(filter.getGroups());
			if (ids.isEmpty()) {
				return null;
			}
			conditions.add(leaf("ticket.group_id", "is", ids));
		}
		if (given(filter.getTicketNumbers())) {
			List<Map<String, Object>> numbers = new ArrayList<Map<String, Object>>();
			for (String number : filter.getTicketNumbers()) {
				if (number != null && !number.isBlank()) {
					numbers.add(leaf("ticket.number", "is", number.trim().replaceFirst("^#", "")));
				}
			}
			conditions.add(group("OR", numbers));
		}
		List<String> terms = textTerms(filter);
		if (!terms.isEmpty()) {
			List<Map<String, Object>> byTerm = new ArrayList<Map<String, Object>>();
			for (String term : terms) {
				byTerm.add(group("OR", List.of(leaf("ticket.title", "contains", term),
						leaf("article.body", "contains", term))));
			}
			conditions.add(group(filter.getTextTermsMatchMode() == TextMatchMode.ALL ? "AND" : "OR", byTerm));
		}
		if (given(filter.getStates()) || filter.getOpen() != null) {
			List<Named> known = states.get();
			List<String> ids = given(filter.getStates()) ? idsOf(filter.getStates(), known, false, "state")
					: known.stream().map(x -> String.valueOf(x.id())).toList();
			if (filter.getOpen() != null) {
				boolean open = filter.getOpen();
				Set<String> openness = new LinkedHashSet<String>();
				for (Named state : known) {
					if (open != NOT_OPEN_STATE_TYPES.contains(normalize(state.type()))) {
						openness.add(String.valueOf(state.id()));
					}
				}
				ids = ids.stream().filter(openness::contains).toList();
			}
			if (ids.isEmpty()) {
				return null;
			}
			conditions.add(leaf("ticket.state_id", "is", ids));
		}
		if (given(filter.getPriorities())) {
			List<String> ids = idsOf(filter.getPriorities(), priorities.get(), true, "priority");
			if (ids.isEmpty()) {
				return null;
			}
			conditions.add(leaf("ticket.priority_id", "is", ids));
		}
		if (Boolean.TRUE.equals(filter.getOwnedByMe())) {
			Map<String, Object> me = leaf("ticket.owner_id", "is", List.of());
			me.put("pre_condition", "current_user.id");
			conditions.add(me);
		}
		dates(conditions, "ticket.created_at", filter.getCreatedFrom(), filter.getCreatedTo());
		dates(conditions, "ticket.updated_at", filter.getUpdatedFrom(), filter.getUpdatedTo());
		Map<String, Object> condition = group("AND", conditions);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Zammad filter {} translated to {}", filter, condition);
		}
		return condition;
	}
}
