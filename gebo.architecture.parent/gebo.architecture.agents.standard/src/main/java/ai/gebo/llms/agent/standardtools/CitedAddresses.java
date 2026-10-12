/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import reactor.core.publisher.Flux;

/**
 * The web addresses a text cites, checked against the addresses known for a request
 * (the ones its tools returned, the user's request and the chat): an address no one
 * gave is made up, and is removed so it never reaches the user as a link. The check
 * is done by code, whatever the model.
 * <p>
 * Addresses are compared normalized: scheme and host in lower case, without the
 * fragment, the query and a trailing slash, decoded.
 */
public final class CitedAddresses {
	/**
	 * A web address in a text. It ends at a space, at the ASCII delimiters and at the
	 * CJK and full-width punctuation (U+3000-U+303F, U+FF01-U+FF0F, U+FF1A-U+FF20,
	 * U+FF3B-U+FF40, U+FF5B-U+FF65): some models close a citation with one of them right
	 * after the address.
	 */
	static final Pattern ADDRESS = Pattern.compile(
			"https?://[^\\s<>\"'`()\\[\\]{}|\\\\^\\u3000-\\u303F\\uFF01-\\uFF0F\\uFF1A-\\uFF20\\uFF3B-\\uFF40\\uFF5B-\\uFF65]+",
			Pattern.CASE_INSENSITIVE);
	/** A markdown link to a web address: its text and its address. */
	static final Pattern MARKDOWN_LINK = Pattern.compile("\\[([^\\]\\n]{0,500})\\]\\(\\s*(https?://[^\\s)]+)\\s*\\)",
			Pattern.CASE_INSENSITIVE);
	/** An address between angle brackets (an autolink). */
	static final Pattern AUTOLINK = Pattern.compile("<(https?://[^\\s<>]+)>", Pattern.CASE_INSENSITIVE);
	/** Punctuation that ends a sentence, not the address it follows. */
	private static final String TRAILING_PUNCTUATION = ".,;:!?*_'\"";
	/** Longest markdown link text held back while the answer streams. */
	static final int MAX_HELD_LINK_CHARACTERS = 600;
	private static final String HTTP_PREFIX = "http";

	private CitedAddresses() {
	}

	/** The addresses a text cites, normalized, added to {@code into}. */
	public static Set<String> addressesIn(String text, Set<String> into) {
		if (text == null || text.isEmpty()) {
			return into;
		}
		final Matcher matcher = ADDRESS.matcher(text);
		while (matcher.find()) {
			final String address = trimmed(matcher.group());
			if (!address.isEmpty()) {
				into.add(normalized(address));
			}
		}
		return into;
	}

	/** The addresses of a text, normalized. */
	public static Set<String> addressesIn(String text) {
		return addressesIn(text, new LinkedHashSet<>());
	}

	/** The given addresses, normalized, added to {@code into}. */
	public static Set<String> addresses(Collection<String> addresses, Set<String> into) {
		if (addresses != null) {
			for (String address : addresses) {
				if (address != null && !address.isBlank()) {
					into.add(normalized(trimmed(address.trim())));
				}
			}
		}
		return into;
	}

	/** Whether an address is among the known ones (normalized, see {@link #normalized}). */
	public static boolean isKnown(String address, Set<String> known) {
		return address != null && known != null && known.contains(normalized(trimmed(address.trim())));
	}

	/**
	 * An address as compared: scheme and host in lower case, decoded, without fragment,
	 * query and trailing slash.
	 */
	public static String normalized(String address) {
		String value = address.trim();
		final int fragment = value.indexOf('#');
		if (fragment >= 0) {
			value = value.substring(0, fragment);
		}
		final int query = value.indexOf('?');
		if (query >= 0) {
			value = value.substring(0, query);
		}
		try {
			value = URLDecoder.decode(value, StandardCharsets.UTF_8);
		} catch (IllegalArgumentException e) {
			// not an encoded address: compared as it is
		}
		final int schemeEnd = value.indexOf("://");
		if (schemeEnd > 0) {
			final int hostEnd = value.indexOf('/', schemeEnd + 3);
			final String origin = (hostEnd < 0 ? value : value.substring(0, hostEnd)).toLowerCase(Locale.ROOT);
			value = origin + (hostEnd < 0 ? "" : value.substring(hostEnd));
		}
		while (value.endsWith("/")) {
			value = value.substring(0, value.length() - 1);
		}
		return value;
	}

	/** The address without the punctuation of the sentence it ends. */
	static String trimmed(String address) {
		int end = address.length();
		while (end > 0 && TRAILING_PUNCTUATION.indexOf(address.charAt(end - 1)) >= 0) {
			end--;
		}
		return address.substring(0, end);
	}

	/**
	 * The text without the addresses {@code known} rejects: a markdown link keeps its
	 * text, an address alone is removed; each address removed is told to
	 * {@code removed}.
	 */
	public static String withoutUnknown(String text, Predicate<String> known, Consumer<String> removed) {
		if (text == null || text.isEmpty() || !text.toLowerCase(Locale.ROOT).contains(HTTP_PREFIX)) {
			return text;
		}
		// links first: their text stays
		final StringBuilder links = new StringBuilder();
		Matcher matcher = MARKDOWN_LINK.matcher(text);
		int last = 0;
		while (matcher.find()) {
			links.append(text, last, matcher.start());
			final String address = trimmed(matcher.group(2));
			if (known.test(address)) {
				links.append(matcher.group());
			} else {
				removed.accept(address);
				links.append(matcher.group(1));
			}
			last = matcher.end();
		}
		links.append(text.substring(last));
		// then the autolinks and the addresses alone
		final String withoutAutolinks = replaceUnknown(links.toString(), AUTOLINK, 1, known, removed);
		return replaceUnknown(withoutAutolinks, ADDRESS, 0, known, removed);
	}

	private static String replaceUnknown(String text, Pattern pattern, int group, Predicate<String> known,
			Consumer<String> removed) {
		final StringBuilder out = new StringBuilder();
		final Matcher matcher = pattern.matcher(text);
		int last = 0;
		while (matcher.find()) {
			final String found = matcher.group(group);
			final String address = trimmed(found);
			out.append(text, last, matcher.start());
			if (known.test(address)) {
				out.append(matcher.group());
			} else {
				removed.accept(address);
				// the sentence's punctuation stays
				if (group == 0) {
					out.append(found.substring(address.length()));
				}
			}
			last = matcher.end();
		}
		out.append(text.substring(last));
		return out.toString();
	}

	/**
	 * A tool result without its list of documents not read (see
	 * {@code documentsNotRead}): their addresses are not known as cited.
	 */
	public static String withoutDocumentsNotRead(String toolResult) {
		if (toolResult == null || !toolResult.contains("documentsNotRead")) {
			return toolResult;
		}
		return toolResult.replaceAll("(?s)\"documentsNotRead\"\\s*:\\s*\\[.*?\\]", "\"documentsNotRead\":[]");
	}

	/**
	 * The streamed text without the addresses {@code known} rejects (see
	 * {@link #withoutUnknown}): the text is let through as it comes, an address being
	 * held back until it ends, a markdown link until it closes.
	 */
	public static Flux<String> guarded(Flux<String> text, Predicate<String> known, Consumer<String> removed) {
		return Flux.defer(() -> {
			final StringBuilder pending = new StringBuilder();
			return text.concatMap(chunk -> {
				pending.append(chunk);
				final int safe = safeEnd(pending);
				if (safe <= 0) {
					return Flux.<String>empty();
				}
				final String out = withoutUnknown(pending.substring(0, safe), known, removed);
				pending.delete(0, safe);
				return out.isEmpty() ? Flux.<String>empty() : Flux.just(out);
			}).concatWith(Flux.defer(() -> {
				final String out = withoutUnknown(pending.toString(), known, removed);
				pending.setLength(0);
				return out.isEmpty() ? Flux.<String>empty() : Flux.just(out);
			}));
		});
	}

	/**
	 * How much of the streamed text can be let through: up to an address not ended yet,
	 * or a markdown link not closed yet (within {@link #MAX_HELD_LINK_CHARACTERS}).
	 */
	static int safeEnd(CharSequence text) {
		final int length = text.length();
		int safe = length;
		// the start of an address cut at the end of the text
		final String lower = text.toString().toLowerCase(Locale.ROOT);
		for (int start = Math.max(0, length - "https://".length()); start < length; start++) {
			final String tail = lower.substring(start);
			if ("https://".startsWith(tail) || "http://".startsWith(tail)) {
				safe = Math.min(safe, start);
				break;
			}
		}
		// an address not ended yet
		final int address = lower.lastIndexOf(HTTP_PREFIX);
		if (address >= 0 && !hasDelimiterAfter(lower, address)) {
			final int start = address > 0 && lower.charAt(address - 1) == '<' ? address - 1 : address;
			safe = Math.min(safe, start);
		}
		// a markdown link not closed yet
		final int open = lower.lastIndexOf('[');
		if (open >= 0 && length - open <= MAX_HELD_LINK_CHARACTERS && lower.indexOf('\n', open) < 0
				&& linkOpen(lower, open)) {
			safe = Math.min(safe, open);
		}
		return safe;
	}

	private static boolean hasDelimiterAfter(String text, int from) {
		for (int i = from; i < text.length(); i++) {
			final char c = text.charAt(i);
			if (Character.isWhitespace(c) || "<>\"'`()[]{}|\\^".indexOf(c) >= 0) {
				return true;
			}
		}
		return false;
	}

	/** Whether the text from the given '[' may still become a markdown link. */
	private static boolean linkOpen(String text, int open) {
		final int close = text.indexOf(']', open);
		if (close < 0 || close == text.length() - 1) {
			return true;
		}
		if (text.charAt(close + 1) != '(') {
			return false;
		}
		return text.indexOf(')', close + 1) < 0;
	}
}
