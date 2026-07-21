package ai.gebo.atlassian.confluence.handler.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ai.gebo.atlassian.confluence.search.api.ConfluenceContentAttributeFilter;
import ai.gebo.atlassian.confluence.search.api.ConfluenceContentSearchFilter;
import ai.gebo.atlassian.confluence.search.api.ConfluencePeopleFilter;

class ConfluenceCqlTranslator {

	static String createCqlString(ConfluenceContentSearchFilter query) {
		if (query == null)
			return "";

		List<String> clauses = new ArrayList<>();
		List<String> restrictingClauses = new ArrayList<String>();
		ConfluenceContentAttributeFilter a = query.getContentAttributesFilter();
		if (a != null) {
			// space
			addInClauseQuoted(clauses, "space", normalize(a.getSpaceKeys()));

			// type (page, blogpost, attachment, comment, etc.)
			addInClauseBareOrQuoted(clauses, "type", normalize(a.getContentTypes()));

			// id (numeric)
			addInClauseNumbers(clauses, "id", normalizeLongs(a.getContentIds()));
			
			// ancestor (numeric)
			addInClauseNumbers(clauses, "ancestor", normalizeLongs(a.getAncestorIds()));

			// title/text terms (text fields => use ~)
			addTextTermsClause(clauses, "title", normalize(a.getTitleTerms()), a.getTitleTermsMatchMode());
			addTextTermsClause(clauses, "text", normalize(a.getTextTerms()), a.getTextTermsMatchMode());

			// labels
			addLabelsClause(clauses, normalize(a.getLabels()), a.getLabelsMatchMode());
		}

		ConfluencePeopleFilter p = query.getPeopleFilter();
		if (p != null) {
			addUserAnyAllClause(clauses, "creator", normalize(p.getCreatorsList())); // creator = ...
			addUserAnyAllClause(clauses, "contributor", normalize(p.getContributorsList())); // contributor = ...
			addUserAnyAllClause(clauses, "mention", normalize(p.getMentionsList())); // mention = ...
			addUserAnyAllClause(clauses, "owner", normalize(p.getOwnersList())); // owner = ... (supports currentUser())
		}

		if (clauses.isEmpty())
			return "";

		// Confluence CQL supports ORDER BY (SQL-like).
		// :contentReference[oaicite:3]{index=3}
		return String.join(" OR ", clauses) + " ORDER BY lastmodified DESC";
	}

	/* ---------------- helpers ---------------- */

	private static void addInClauseQuoted(List<String> clauses, String field, List<String> values) {
		if (values.isEmpty())
			return;
		String joined = values.stream().map(ConfluenceCqlTranslator::cqlQuote).collect(Collectors.joining(","));
		clauses.add(field + " in (" + joined + ")");
	}

	/**
	 * For some fields like type/label, Confluence examples often show unquoted
	 * tokens. This helper emits bare tokens when safe, otherwise quotes.
	 */
	private static void addInClauseBareOrQuoted(List<String> clauses, String field, List<String> values) {
		if (values.isEmpty())
			return;
		String joined = values.stream().map(ConfluenceCqlTranslator::bareOrQuote).collect(Collectors.joining(","));
		clauses.add(field + " in (" + joined + ")");
	}

	private static void addInClauseNumbers(List<String> clauses, String field, List<Long> values) {
		if (values.isEmpty())
			return;
		String joined = values.stream().map(String::valueOf).collect(Collectors.joining(","));
		clauses.add(field + " in (" + joined + ")");
	}

	private static void addTextTermsClause(List<String> clauses, String field, List<String> terms,
			ConfluenceContentAttributeFilter.TextMatchMode mode) {
		if (terms.isEmpty())
			return;

		String joinOp = (mode == ConfluenceContentAttributeFilter.TextMatchMode.ALL) ? " AND " : " OR ";

		// text fields: use ~ (CONTAINS). :contentReference[oaicite:4]{index=4}
		String inner = terms.stream().map(t -> field + " ~ " + cqlQuote(t)).collect(Collectors.joining(joinOp));

		clauses.add(terms.size() > 1 ? "(" + inner + ")" : inner);
	}

	private static void addLabelsClause(List<String> clauses, List<String> labels,
			ConfluenceContentAttributeFilter.TextMatchMode mode) {
		if (labels.isEmpty())
			return;

		if (mode != ConfluenceContentAttributeFilter.TextMatchMode.ALL) {
			// ANY: label in (...)
			String joined = labels.stream().map(ConfluenceCqlTranslator::bareOrQuote).collect(Collectors.joining(","));
			clauses.add("label in (" + joined + ")");
			return;
		}

		// ALL: (label = a AND label = b)
		String inner = labels.stream().map(l -> "label = " + bareOrQuote(l)).collect(Collectors.joining(" AND "));

		clauses.add(labels.size() > 1 ? "(" + inner + ")" : inner);
	}

	/**
	 * People fields: treat tokens as either a function (e.g., currentUser()) or a
	 * quoted string. For Cloud privacy, accountId is safest if you can resolve it
	 * beforehand.
	 */
	private static void addUserAnyAllClause(List<String> clauses, String field, List<String> users) {
		if (users.isEmpty())
			return;

		// default: ANY (OR). If you need ALL semantics, add an explicit match mode like
		// Jira.
		String inner = users.stream().map(u -> field + " = " + userOperand(u)).collect(Collectors.joining(" OR "));

		clauses.add(users.size() > 1 ? "(" + inner + ")" : inner);
	}

	private static List<String> normalize(List<String> list) {
		if (list == null)
			return List.of();
		return list.stream().filter(Objects::nonNull).map(String::trim).filter(s -> !s.isEmpty()).distinct()
				.collect(Collectors.toList());
	}

	private static List<Long> normalizeLongs(List<Long> list) {
		if (list == null)
			return List.of();
		return list.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
	}

	private static String cqlQuote(String raw) {
		String s = raw.trim();
		if (s.length() >= 2 && ((s.startsWith("\"") && s.endsWith("\"")) || (s.startsWith("'") && s.endsWith("'")))) {
			s = s.substring(1, s.length() - 1);
		}
		s = s.replace("\\", "\\\\").replace("\"", "\\\"");
		return "\"" + s + "\"";
	}

	private static String bareOrQuote(String token) {
		String t = token.trim();
		// Safe bare token: letters/numbers/_/-
		if (t.matches("^[A-Za-z0-9_-]+$"))
			return t;
		return cqlQuote(t);
	}

	private static String userOperand(String token) {
		String t = token.trim();
		if (t.isEmpty())
			return "\"\"";

		// already quoted
		if ((t.startsWith("\"") && t.endsWith("\"")) || (t.startsWith("'") && t.endsWith("'"))) {
			return t.startsWith("'") ? cqlQuote(t) : t;
		}

		// function-like (currentUser())
		if (t.endsWith(")") && t.contains("("))
			return t;

		return cqlQuote(t);
	}

}
