/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.impl;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import ai.gebo.glpi.search.api.GlpiSearchFilter;
import ai.gebo.glpi.search.api.GlpiSearchFilter.GlpiItemKind;
import ai.gebo.glpi.search.api.GlpiSearchFilter.GlpiTicketStatus;
import ai.gebo.glpi.search.api.GlpiSearchFilter.GlpiTicketType;
import ai.gebo.glpi.search.api.GlpiSearchFilter.TextMatchMode;

/**
 * Builds the RSQL filters of the GLPI high-level API: {@code ;} is AND,
 * {@code ,} is OR, {@code =ilike=} a case insensitive like where {@code *} is
 * the wildcard, {@code =in=(a,b)} a list. Search words are reduced to letters
 * and digits, so a filter value never needs quoting.
 */
public final class GlpiRsqlUtil {
	static final String AND = ";";
	static final String OR = ",";
	static final String NAME = "name";
	static final String CONTENT = "content";
	static final String NOT_DELETED = "is_deleted==false";
	private static final Pattern WORD = Pattern.compile("[\\p{L}\\p{N}]+");

	private GlpiRsqlUtil() {
	}

	public static String ofEntity(long entityId) {
		return "entity.id==" + entityId;
	}

	public static String ticketsOfEntity(long entityId) {
		return ofEntity(entityId) + AND + NOT_DELETED;
	}

	/** The words of a text usable in a filter value */
	static List<String> words(String text) {
		List<String> words = new ArrayList<String>();
		if (text != null) {
			Matcher matcher = WORD.matcher(text);
			while (matcher.find()) {
				words.add(matcher.group());
			}
		}
		return words;
	}

	/** Every word of the text in the property: null when the text has no word */
	static String allWordsIn(String property, String text) {
		List<String> words = words(text);
		if (words.isEmpty()) {
			return null;
		}
		return group(words.stream().map(w -> property + "=ilike=*" + w + "*").toList(), AND);
	}

	/** Every word of the text in the title or in the content */
	static String allWordsInTitleOrContent(String text) {
		List<String> words = words(text);
		if (words.isEmpty()) {
			return null;
		}
		return group(words.stream()
				.map(w -> "(" + NAME + "=ilike=*" + w + "*" + OR + CONTENT + "=ilike=*" + w + "*)").toList(), AND);
	}

	private static String group(List<String> parts, String operator) {
		List<String> present = parts.stream().filter(p -> p != null && !p.isBlank()).toList();
		if (present.isEmpty()) {
			return null;
		}
		if (present.size() == 1) {
			return present.get(0);
		}
		return "(" + String.join(operator, present) + ")";
	}

	private static String terms(String property, Collection<String> terms, TextMatchMode mode) {
		if (terms == null || terms.isEmpty()) {
			return null;
		}
		List<String> parts = terms.stream().map(t -> allWordsIn(property, t)).toList();
		return group(parts, mode == TextMatchMode.ALL ? AND : OR);
	}

	private static String in(String property, Collection<?> values) {
		if (values == null || values.isEmpty()) {
			return null;
		}
		List<String> present = values.stream().filter(v -> v != null).map(Object::toString).toList();
		if (present.isEmpty()) {
			return null;
		}
		return property + "=in=(" + String.join(OR, present) + ")";
	}

	private static String dates(String property, String from, String to) {
		List<String> parts = new ArrayList<String>();
		LocalDate fromDate = parseDate(from);
		if (fromDate != null) {
			parts.add(property + "=ge=" + fromDate);
		}
		LocalDate toDate = parseDate(to);
		if (toDate != null) {
			parts.add(property + "=lt=" + toDate.plusDays(1));
		}
		return parts.isEmpty() ? null : String.join(AND, parts);
	}

	static LocalDate parseDate(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		String trimmed = value.trim();
		try {
			return LocalDate.parse(trimmed.length() > 10 ? trimmed.substring(0, 10) : trimmed);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	static long statusId(GlpiTicketStatus status) {
		switch (status) {
		case NEW:
			return 1;
		case PROCESSING_ASSIGNED:
			return 2;
		case PROCESSING_PLANNED:
			return 3;
		case PENDING:
			return 4;
		case SOLVED:
			return 5;
		case CLOSED:
			return 6;
		case APPROVAL:
			return 10;
		default:
			throw new IllegalArgumentException("Unknown status " + status);
		}
	}

	static long typeId(GlpiTicketType type) {
		return type == GlpiTicketType.INCIDENT ? 1 : 2;
	}

	/** True when the filter asks for tickets */
	public static boolean searchesTickets(GlpiSearchFilter filter) {
		return filter.getItemKinds() == null || filter.getItemKinds().isEmpty()
				|| filter.getItemKinds().contains(GlpiItemKind.TICKET);
	}

	/**
	 * True when the filter asks for knowledge base articles: when no kind is
	 * given, only if no ticket-only criterion is set
	 */
	public static boolean searchesKbArticles(GlpiSearchFilter filter) {
		if (filter.getItemKinds() != null && !filter.getItemKinds().isEmpty()) {
			return filter.getItemKinds().contains(GlpiItemKind.KB_ARTICLE);
		}
		return isEmpty(filter.getTicketIds()) && isEmpty(filter.getTicketStatuses())
				&& isEmpty(filter.getTicketTypes()) && isEmpty(filter.getPriorities())
				&& isEmpty(filter.getCategoryNames());
	}

	private static boolean isEmpty(Collection<?> c) {
		return c == null || c.isEmpty();
	}

	/** The tickets filter of a native search, the ones in the trash excluded */
	public static String ticketsFilter(GlpiSearchFilter filter) {
		List<String> parts = new ArrayList<String>();
		parts.add(NOT_DELETED);
		parts.add(in("entity.id", filter.getEntityIds()));
		parts.add(in("id", filter.getTicketIds()));
		parts.add(terms(NAME, filter.getTitleTerms(), filter.getTitleTermsMatchMode()));
		parts.add(terms(CONTENT, filter.getContentTerms(), filter.getContentTermsMatchMode()));
		if (filter.getTicketStatuses() != null) {
			parts.add(in("status.id", filter.getTicketStatuses().stream().filter(s -> s != null)
					.map(GlpiRsqlUtil::statusId).collect(Collectors.toList())));
		}
		if (filter.getTicketTypes() != null) {
			parts.add(in("type", filter.getTicketTypes().stream().filter(t -> t != null).map(GlpiRsqlUtil::typeId)
					.collect(Collectors.toList())));
		}
		parts.add(in("priority", filter.getPriorities()));
		if (filter.getCategoryNames() != null) {
			parts.add(group(filter.getCategoryNames().stream().map(c -> allWordsIn("category.name", c)).toList(), OR));
		}
		parts.add(dates("date_creation", filter.getCreatedFrom(), filter.getCreatedTo()));
		return join(parts);
	}

	/** The knowledge base articles filter of a native search */
	public static String kbArticlesFilter(GlpiSearchFilter filter) {
		List<String> parts = new ArrayList<String>();
		parts.add(in("entity.id", filter.getEntityIds()));
		parts.add(terms(NAME, filter.getTitleTerms(), filter.getTitleTermsMatchMode()));
		parts.add(terms(CONTENT, filter.getContentTerms(), filter.getContentTermsMatchMode()));
		parts.add(dates("date_creation", filter.getCreatedFrom(), filter.getCreatedTo()));
		return join(parts);
	}

	/** The tickets filter of a plain text search */
	public static String ticketsTextFilter(String text) {
		return join(List.of(NOT_DELETED, nullToEmpty(allWordsInTitleOrContent(text))));
	}

	/** The knowledge base articles filter of a plain text search, null when the text has no word */
	public static String kbArticlesTextFilter(String text) {
		return allWordsInTitleOrContent(text);
	}

	private static String nullToEmpty(String s) {
		return s != null ? s : "";
	}

	private static String join(List<String> parts) {
		List<String> present = parts.stream().filter(p -> p != null && !p.isBlank()).toList();
		return present.isEmpty() ? null : String.join(AND, present);
	}
}
