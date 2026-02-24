package ai.gebo.atlassian.jira.handler.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ai.gebo.atlassian.jira.handler.search.model.JiraIssueAttributeFilter;
import ai.gebo.atlassian.jira.handler.search.model.JiraIssuesSearchFilter;
import ai.gebo.atlassian.jira.handler.search.model.JiraPeopleFilter;

public class JiraJsqlUtil {

	/**
	 * Deterministic JQL builder from JiraIssuesSearchFilter. - Across different
	 * filter groups: AND - Inside lists: ANY => OR, ALL => AND (with parentheses) -
	 * Uses IN (...) where possible. - Escapes quoted strings for JQL.
	 *
	 * Note: user identifiers in Jira Cloud are tricky (accountId vs displayName).
	 * This code treats values as raw JQL user operands: - if it looks like a
	 * function call (ends with ')'), it is NOT quoted (e.g., currentUser()). -
	 * otherwise it's quoted as a string (e.g., "Alex Doe") unless it already looks
	 * quoted.
	 */
	static String  createJqlString(JiraIssuesSearchFilter query) {
		if (query == null)
			return "";

		List<String> clauses = new ArrayList();

		JiraIssueAttributeFilter a = query.getIssuesAttributesFilter();
		if (a != null) {
			// project, issuetype, key
			addInClause(clauses, "project", a.getProjectCodes(), true);
			addInClause(clauses, "issuetype", a.getIssuetypeCodes(), true);
			addInClause(clauses, "key", a.getIssueKeys(), true);

			// summary / description text search
			addTextTermsClause(clauses, "summary", a.getSummaryTerms(), a.getSummaryTermsMatchMode());

			addTextTermsClause(clauses, "description", a.getDescriptionTerms(), a.getDescriptionTermsMatchMode());

			// labels (exact match semantics in JQL is labels in (...))
			addLabelsClause(clauses, a.getLabels(), a.getLabelsMatchMode());

			// priority, status
			addInClause(clauses, "priority", a.getPriorityCodes(), true);
			addInClause(clauses, "status", a.getStatusCodes(), true);

			// versions
			addInClause(clauses, "affectedVersion", a.getAffectedVersions(), true);
			addInClause(clauses, "fixVersion", a.getFixVersions(), true);
		}

		JiraPeopleFilter p = query.getPeopleFilter();
		if (p != null) {
			addUserInClause(clauses, "assignee", p.getAssigneesList());
			addUserInClause(clauses, "reporter", p.getReportersList());
			addUserInClause(clauses, "creator", p.getCreatorsList());
		}

		if (clauses.isEmpty())
			return "";

		// Default ordering (optional; remove if you don't want it)
		return String.join("\nAND ", clauses) + "\nORDER BY updated DESC";
	}

	/* ---------------- helpers ---------------- */

	private static void addInClause(List<String> clauses, String field, List<String> values, boolean quoteValues) {
		List<String> v = normalize(values);
		if (v.isEmpty())
			return;

		String joined = v.stream().map(x -> quoteValues ? jqlQuote(x) : x).collect(Collectors.joining(","));

		clauses.add(field + " in (" + joined + ")");
	}

	private static void addTextTermsClause(List<String> clauses, String field, List<String> terms,
			JiraIssueAttributeFilter.TextMatchMode mode) {
		List<String> t = normalize(terms);
		if (t.isEmpty())
			return;

		String op = (mode == JiraIssueAttributeFilter.TextMatchMode.ALL) ? " AND " : " OR ";

		// JQL text search: field ~ "term"
		String inner = t.stream().map(term -> field + " ~ " + jqlQuote(term)).collect(Collectors.joining(op));

		// Always parenthesize multi-term groups
		clauses.add(t.size() > 1 ? "(" + inner + ")" : inner);
	}

	private static void addLabelsClause(List<String> clauses, List<String> labels,
			JiraIssueAttributeFilter.TextMatchMode mode) {
		List<String> l = normalize(labels);
		if (l.isEmpty())
			return;

		// For ANY, best is labels in ("a","b")
		if (mode != JiraIssueAttributeFilter.TextMatchMode.ALL) {
			String joined = l.stream().map(JqlBuilder::jqlQuote).collect(Collectors.joining(","));
			clauses.add("labels in (" + joined + ")");
			return;
		}

		// For ALL labels: labels = "a" AND labels = "b"
		String inner = l.stream().map(x -> "labels = " + jqlQuote(x)).collect(Collectors.joining(" AND "));

		clauses.add(l.size() > 1 ? "(" + inner + ")" : inner);
	}

	private static void addUserInClause(List<String> clauses, String field, List<String> users) {
		List<String> u = normalize(users);
		if (u.isEmpty())
			return;

		String joined = u.stream().map(JqlBuilder::jqlUserOperand).collect(Collectors.joining(","));

		clauses.add(field + " in (" + joined + ")");
	}

	private static List<String> normalize(List<String> list) {
		if (list == null)
			return List.of();
		return list.stream().filter(Objects::nonNull).map(String::trim).filter(s -> !s.isEmpty()).distinct()
				.collect(Collectors.toList());
	}

	/**
	 * Quote a literal for JQL (double quotes) and escape backslashes and quotes.
	 */
	private static String jqlQuote(String raw) {
		String s = raw;
		// Strip surrounding quotes to avoid double quoting
		if (s.length() >= 2 && ((s.startsWith("\"") && s.endsWith("\"")) || (s.startsWith("'") && s.endsWith("'")))) {
			s = s.substring(1, s.length() - 1);
		}
		s = s.replace("\\", "\\\\").replace("\"", "\\\"");
		return "\"" + s + "\"";
	}

	/**
	 * Convert a user token into a safe JQL operand: - keep JQL functions as-is
	 * (e.g., currentUser()) - keep already-quoted strings as-is - otherwise quote
	 * as a string
	 *
	 * If you standardize on accountId, consider emitting accountId() operands
	 * instead after resolving via REST (recommended in Cloud).
	 */
	private static String jqlUserOperand(String token) {
		String t = token.trim();
		if (t.isEmpty())
			return "\"\"";

		// Already quoted
		if ((t.startsWith("\"") && t.endsWith("\"")) || (t.startsWith("'") && t.endsWith("'"))) {
			return t.startsWith("'") ? jqlQuote(t) : t; // normalize single quotes into double quotes
		}

		// Looks like a function call: currentUser(), membersOf("group"), etc.
		if (t.endsWith(")") && t.contains("(")) {
			return t;
		}

		// Otherwise treat as literal (display name or something you mapped)
		return jqlQuote(t);
	}

	/* Optional: if you want helpers in a dedicated utility class name */
	private static final class JqlBuilder {
		private static String jqlQuote(String raw) {
			return JiraJsqlUtil.jqlQuote(raw);
		}

		private static String jqlUserOperand(String token) {
			return JiraJsqlUtil.jqlUserOperand(token);
		}
	}
}
