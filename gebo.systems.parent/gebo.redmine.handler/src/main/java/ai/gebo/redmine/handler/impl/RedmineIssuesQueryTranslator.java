/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.impl;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.redmine.search.api.RedmineIssuesSearchFilter;
import ai.gebo.redmine.search.api.TextMatchMode;

/**
 * Translates the structured part of a {@link RedmineIssuesSearchFilter} into
 * Redmine issues queries, one per requested project (or one over every
 * project). Tracker, status and priority names are matched, ignoring case, to
 * the ids Redmine knows; a name Redmine does not know is left out rather than
 * guessed.
 */
public class RedmineIssuesQueryTranslator {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedmineIssuesQueryTranslator.class);
	private final Supplier<Map<String, Integer>> trackers;
	private final Supplier<Map<String, Integer>> statuses;
	private final Supplier<Map<String, Integer>> priorities;

	/**
	 * @param trackers   tracker ids by lower case name, read when needed
	 * @param statuses   status ids by lower case name, read when needed
	 * @param priorities priority ids by lower case name, read when needed
	 */
	public RedmineIssuesQueryTranslator(Supplier<Map<String, Integer>> trackers,
			Supplier<Map<String, Integer>> statuses, Supplier<Map<String, Integer>> priorities) {
		this.trackers = trackers;
		this.statuses = statuses;
		this.priorities = priorities;
	}

	/**
	 * The project identifier of a project the model named: a catalog code
	 * ({@code PROJECT:<identifier>}) or the identifier itself.
	 */
	public static String projectIdOf(String project) {
		String value = project.trim();
		return value.startsWith(RedmineNavigationUtil.PROJECT_PREFIX)
				? value.substring(RedmineNavigationUtil.PROJECT_PREFIX.length())
				: value;
	}

	private static boolean present(List<?> list) {
		return list != null && list.stream().anyMatch(x -> x != null && !x.toString().isBlank());
	}

	/**
	 * Whether the filter has anything an issues query can use.
	 */
	public static boolean hasStructuredFilter(RedmineIssuesSearchFilter filter) {
		return present(filter.getProjects()) || hasIssueAttributesFilter(filter);
	}

	/**
	 * Whether the filter tells something about the issues themselves (numbers,
	 * trackers, statuses, priorities, assignee, dates), beyond the projects to
	 * look in.
	 */
	public static boolean hasIssueAttributesFilter(RedmineIssuesSearchFilter filter) {
		return present(filter.getIssueIds()) || present(filter.getTrackers())
				|| present(filter.getStatuses()) || filter.getOpen() != null || present(filter.getPriorities())
				|| Boolean.TRUE.equals(filter.getAssignedToMe()) || filter.getCreatedFrom() != null
				|| filter.getCreatedTo() != null || filter.getUpdatedFrom() != null || filter.getUpdatedTo() != null;
	}

	public static boolean hasTextTerms(RedmineIssuesSearchFilter filter) {
		return present(filter.getTextTerms());
	}

	private String ids(List<String> names, Supplier<Map<String, Integer>> known, String what) {
		if (!present(names)) {
			return null;
		}
		Map<String, Integer> byName = known.get();
		List<String> ids = new ArrayList<String>();
		for (String name : names) {
			if (name == null || name.isBlank()) {
				continue;
			}
			Integer id = byName.get(name.trim().toLowerCase(Locale.ROOT));
			if (id != null) {
				ids.add(String.valueOf(id));
			} else if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The Redmine {} \"{}\" is unknown, not used as a filter", what, name);
			}
		}
		return ids.isEmpty() ? null : String.join("|", ids);
	}

	static String dateRange(String from, String to) {
		String f = date(from);
		String t = date(to);
		if (f != null && t != null) {
			return "><" + f + "|" + t;
		}
		if (f != null) {
			return ">=" + f;
		}
		if (t != null) {
			return "<=" + t;
		}
		return null;
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

	/**
	 * The any_searchable values the text terms become: one with every term when
	 * all of them must match (Redmine requires every word of the value), one per
	 * term when any is enough; a single null when there are no terms.
	 */
	static List<String> textValues(RedmineIssuesSearchFilter filter) {
		if (!present(filter.getTextTerms())) {
			return java.util.Collections.singletonList(null);
		}
		List<String> terms = filter.getTextTerms().stream().filter(x -> x != null && !x.isBlank())
				.map(String::trim).distinct().toList();
		if (filter.getTextTermsMatchMode() == TextMatchMode.ANY) {
			return terms.stream().map(x -> "~" + x).toList();
		}
		return List.of("~" + String.join(" ", terms));
	}

	/**
	 * The issues queries of a filter: one per project (or one over every project),
	 * times one per text value when the terms are an issues filter too, so that
	 * every condition of the filter holds on the issues found.
	 */
	public List<RedmineIssuesQuery> translate(RedmineIssuesSearchFilter filter) {
		String status = ids(filter.getStatuses(), statuses, "status");
		if (status == null) {
			status = Boolean.TRUE.equals(filter.getOpen()) ? "o" : Boolean.FALSE.equals(filter.getOpen()) ? "c" : "*";
		}
		String tracker = ids(filter.getTrackers(), trackers, "tracker");
		String priority = ids(filter.getPriorities(), priorities, "priority");
		String issueIds = present(filter.getIssueIds()) ? filter.getIssueIds().stream().filter(x -> x != null)
				.map(String::valueOf).collect(Collectors.joining(",")) : null;
		String assignedTo = Boolean.TRUE.equals(filter.getAssignedToMe()) ? "me" : null;
		String created = dateRange(filter.getCreatedFrom(), filter.getCreatedTo());
		String updated = dateRange(filter.getUpdatedFrom(), filter.getUpdatedTo());
		List<RedmineIssuesQuery> queries = new ArrayList<RedmineIssuesQuery>();
		List<String> projects = present(filter.getProjects())
				? filter.getProjects().stream().filter(x -> x != null && !x.isBlank())
						.map(RedmineIssuesQueryTranslator::projectIdOf).distinct().toList()
				: List.of();
		List<String> scopes = projects.isEmpty() ? java.util.Collections.singletonList(null) : projects;
		for (String project : scopes) {
			for (String text : textValues(filter)) {
				RedmineIssuesQuery query = project == null ? RedmineIssuesQuery.anyProject()
						: RedmineIssuesQuery.forProject(project);
				query.status(status).tracker(tracker).priority(priority).issues(issueIds).assignedTo(assignedTo)
						.createdOn(created).updatedOn(updated).anySearchable(text).sort("updated_on:desc");
				queries.add(query);
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Redmine filter translated into {}", queries);
		}
		return queries;
	}
}
