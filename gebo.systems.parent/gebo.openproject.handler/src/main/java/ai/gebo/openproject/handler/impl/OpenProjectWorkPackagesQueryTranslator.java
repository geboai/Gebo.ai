/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.impl;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.openproject.search.api.OpenProjectWorkPackagesSearchFilter;
import ai.gebo.openproject.search.api.TextMatchMode;
import tools.jackson.databind.json.JsonMapper;

/**
 * Translates an {@link OpenProjectWorkPackagesSearchFilter} into the "filters"
 * parameter of a work packages query of the API v3, where every condition holds.
 * Type, status and priority names are matched, ignoring case, to the ids
 * OpenProject knows; a name OpenProject does not know is left out rather than
 * guessed, and so is a project the account cannot see. Text terms that any may
 * match give one query each.
 */
public class OpenProjectWorkPackagesQueryTranslator {
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenProjectWorkPackagesQueryTranslator.class);
	private static final JsonMapper JSON = JsonMapper.builder().build();
	/** The sort of every search: the most recently updated first. */
	public static final String SORT_BY = "[[\"updatedAt\",\"desc\"]]";
	private final Supplier<Map<String, String>> types;
	private final Supplier<Map<String, String>> statuses;
	private final Supplier<Map<String, String>> priorities;
	private final Function<String, String> projectIds;

	/**
	 * @param types      type ids by lower case name, read when needed
	 * @param statuses   status ids by lower case name, read when needed
	 * @param priorities priority ids by lower case name, read when needed
	 * @param projectIds the numeric id of a project identifier, null when the
	 *                   account cannot see it
	 */
	public OpenProjectWorkPackagesQueryTranslator(Supplier<Map<String, String>> types,
			Supplier<Map<String, String>> statuses, Supplier<Map<String, String>> priorities,
			Function<String, String> projectIds) {
		this.types = types;
		this.statuses = statuses;
		this.priorities = priorities;
		this.projectIds = projectIds;
	}

	/**
	 * The project identifier of a project the model named: a catalog code
	 * ({@code PROJECT:<identifier>}) or the identifier itself.
	 */
	public static String projectIdOf(String project) {
		String value = project.trim();
		return value.startsWith(OpenProjectNavigationUtil.PROJECT_PREFIX)
				? value.substring(OpenProjectNavigationUtil.PROJECT_PREFIX.length())
				: value;
	}

	private static boolean present(List<?> list) {
		return list != null && list.stream().anyMatch(x -> x != null && !x.toString().isBlank());
	}

	/**
	 * Whether the filter has anything to search with.
	 */
	public static boolean hasFilter(OpenProjectWorkPackagesSearchFilter filter) {
		return present(filter.getProjects()) || present(filter.getTextTerms()) || present(filter.getWorkPackageIds())
				|| present(filter.getTypes()) || present(filter.getStatuses()) || filter.getOpen() != null
				|| present(filter.getPriorities()) || Boolean.TRUE.equals(filter.getAssignedToMe())
				|| filter.getCreatedFrom() != null || filter.getCreatedTo() != null || filter.getUpdatedFrom() != null
				|| filter.getUpdatedTo() != null;
	}

	private List<String> ids(List<String> names, Supplier<Map<String, String>> known, String what) {
		if (!present(names)) {
			return null;
		}
		Map<String, String> byName = known.get();
		List<String> ids = new ArrayList<String>();
		for (String name : names) {
			if (name == null || name.isBlank()) {
				continue;
			}
			String id = byName.get(name.trim().toLowerCase(Locale.ROOT));
			if (id != null) {
				ids.add(id);
			} else if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The OpenProject {} \"{}\" is unknown, not used as a filter", what, name);
			}
		}
		return ids;
	}

	private static String date(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		try {
			return LocalDate.parse(value.trim()).toString();
		} catch (DateTimeParseException e) {
			LOGGER.debug("Not a yyyy-MM-dd date, not used as a filter: {}", value);
			return null;
		}
	}

	/** A "between dates" condition, open on the side without a date. */
	static Map<String, Object> dateRange(String from, String to) {
		String f = date(from);
		String t = date(to);
		if (f == null && t == null) {
			return null;
		}
		return condition("<>d", f != null ? f : "", t != null ? t : "");
	}

	private static Map<String, Object> condition(String operator, String... values) {
		return condition(operator, List.of(values));
	}

	private static Map<String, Object> condition(String operator, List<String> values) {
		Map<String, Object> condition = new LinkedHashMap<String, Object>();
		condition.put("operator", operator);
		condition.put("values", values);
		return condition;
	}

	private static void add(List<Map<String, Object>> filters, String name, Map<String, Object> condition) {
		if (condition != null) {
			filters.add(Map.of(name, condition));
		}
	}

	/**
	 * The text conditions: one with every term when all of them must match, one
	 * per term when any is enough; a single null when there are no terms.
	 */
	static List<String> textValues(OpenProjectWorkPackagesSearchFilter filter) {
		if (!present(filter.getTextTerms())) {
			return Collections.singletonList(null);
		}
		List<String> terms = filter.getTextTerms().stream().filter(x -> x != null && !x.isBlank()).map(String::trim)
				.distinct().toList();
		if (filter.getTextTermsMatchMode() == TextMatchMode.ANY) {
			return terms;
		}
		return List.of(String.join(" ", terms));
	}

	/**
	 * The filters of the queries of a search, as the JSON the "filters"
	 * parameter takes; none when the filter names only projects the account
	 * cannot see.
	 */
	public List<String> translate(OpenProjectWorkPackagesSearchFilter filter) {
		List<Map<String, Object>> common = new ArrayList<Map<String, Object>>();
		if (present(filter.getProjects())) {
			List<String> ids = new ArrayList<String>();
			for (String project : filter.getProjects()) {
				if (project == null || project.isBlank()) {
					continue;
				}
				String id = projectIds.apply(projectIdOf(project));
				if (id != null) {
					ids.add(id);
				} else {
					LOGGER.debug("The OpenProject project \"{}\" is not visible, not searched", project);
				}
			}
			if (ids.isEmpty()) {
				LOGGER.debug("No visible OpenProject project among {}, nothing searched", filter.getProjects());
				return List.of();
			}
			add(common, "project", condition("=", ids.stream().distinct().toList()));
		}
		List<String> status = ids(filter.getStatuses(), statuses, "status");
		if (status != null && !status.isEmpty()) {
			add(common, "status", condition("=", status));
		} else {
			add(common, "status", condition(
					Boolean.TRUE.equals(filter.getOpen()) ? "o" : Boolean.FALSE.equals(filter.getOpen()) ? "c" : "*",
					List.of()));
		}
		List<String> type = ids(filter.getTypes(), types, "type");
		if (type != null && !type.isEmpty()) {
			add(common, "type", condition("=", type));
		}
		List<String> priority = ids(filter.getPriorities(), priorities, "priority");
		if (priority != null && !priority.isEmpty()) {
			add(common, "priority", condition("=", priority));
		}
		if (present(filter.getWorkPackageIds())) {
			add(common, "id", condition("=",
					filter.getWorkPackageIds().stream().filter(x -> x != null).map(String::valueOf).distinct().toList()));
		}
		if (Boolean.TRUE.equals(filter.getAssignedToMe())) {
			add(common, "assignee", condition("=", "me"));
		}
		add(common, "createdAt", dateRange(filter.getCreatedFrom(), filter.getCreatedTo()));
		add(common, "updatedAt", dateRange(filter.getUpdatedFrom(), filter.getUpdatedTo()));
		List<String> queries = new ArrayList<String>();
		for (String text : textValues(filter)) {
			List<Map<String, Object>> filters = new ArrayList<Map<String, Object>>(common);
			if (text != null) {
				add(filters, "search", condition("**", text));
			}
			queries.add(JSON.writeValueAsString(filters));
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("OpenProject filter {} translated into {}", filter, queries);
		}
		return queries;
	}
}
