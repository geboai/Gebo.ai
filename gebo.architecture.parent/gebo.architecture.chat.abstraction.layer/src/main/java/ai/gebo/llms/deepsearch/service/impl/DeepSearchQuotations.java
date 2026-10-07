/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.deepsearch.service.impl;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;

import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.ExtractedDocumentMetaData;
import reactor.core.publisher.Flux;

/**
 * The quotations of one deep search run, best effort: a model's analysis may put any
 * sentence between quotation marks, its own paraphrase included, and whoever reads the
 * analysis cannot tell. The partial analyses write their quotations as
 * {@code ⟦q:<fragmentId>|<exact words>⟧}; each is checked here against the text of its
 * fragment, while its batch is at hand: a verified one is kept, carried by the
 * consolidation as {@code ⟦Q<n>|...⟧}, an unverified one becomes plain text. The final
 * text renders each kept quotation the standard way, {@code “exact words” (Document
 * title)}, with no fragment id; any other quotation marks are kept only around words
 * one of the run's fragments has. Nothing here ever fails a delivery: a broken pattern
 * gives fewer quotations, never an invented one.
 */
public class DeepSearchQuotations {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeepSearchQuotations.class);
	/** The pattern the partial analyses write their quotations in. */
	static final Pattern PARTIAL_QUOTE = Pattern.compile("⟦\\s*q\\s*:\\s*([^|⟧\\s]+)\\s*\\|(.*?)⟧",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	/** A verified quotation, as the consolidation carries it. */
	static final Pattern KEPT_QUOTE = Pattern.compile("⟦\\s*(Q\\d+)\\s*\\|(.*?)⟧", Pattern.DOTALL);
	/** Any other bracketed pattern: its words, without the brackets. */
	static final Pattern OTHER_PATTERN = Pattern.compile("⟦([^⟧]*)⟧", Pattern.DOTALL);
	/** Quotation marks around a span: “…”, «…», "…". */
	static final Pattern PLAIN_QUOTE = Pattern.compile("“([^”\\n]+)”|«([^»\\n]+)»|\"([^\"\\n]+)\"");
	/** Below this many normalized characters a quoted span is a word, not a quotation: left as it is. */
	static final int MIN_QUOTE_CHARS = 25;
	/** How much the streamed rendering holds back for a quotation still open. */
	static final int MAX_HELD_CHARS = 4000;

	/** A verified quotation. */
	public record Quote(String key, String text, String fragmentId, String documentCode, String title) {
	}

	private final Map<String, Quote> quotes = Collections.synchronizedMap(new LinkedHashMap<>());
	/** The normalized text of every fragment the run's partial analyses were given. */
	private final List<String> fragmentTexts = Collections.synchronizedList(new ArrayList<>());
	private final AtomicInteger next = new AtomicInteger(1);

	/**
	 * A partial analysis with its quotations checked against its batch: each
	 * {@code ⟦q:<number>|words⟧} whose words its fragment has is kept as
	 * {@code ⟦Q<n>|words⟧}, the others become their words; quotation marks around words
	 * no fragment of the batch has are taken away.
	 */
	public String keepVerified(String partialAnalysis, DeepSearchBatchTrace.NumberedBatch batch) {
		if (partialAnalysis == null || partialAnalysis.isEmpty()) {
			return partialAnalysis;
		}
		final Map<String, Document> byNumber = new LinkedHashMap<>();
		final List<String> batchTexts = new ArrayList<>();
		if (batch != null) {
			for (Document numbered : batch.documents()) {
				byNumber.put(numbered.getId(), numbered);
				final String text = normalized(numbered.getText());
				batchTexts.add(text);
				fragmentTexts.add(text);
			}
		}
		int kept = 0;
		int dropped = 0;
		final StringBuilder out = new StringBuilder();
		final Matcher matcher = PARTIAL_QUOTE.matcher(partialAnalysis);
		int last = 0;
		while (matcher.find()) {
			out.append(partialAnalysis, last, matcher.start());
			final String number = matcher.group(1).trim();
			final String words = matcher.group(2).trim();
			final Document fragment = byNumber.get(number);
			if (fragment != null && !words.isEmpty() && contains(normalized(fragment.getText()), words)) {
				final String key = "Q" + next.getAndIncrement();
				final Map<String, Object> metadata = fragment.getMetadata();
				quotes.put(key, new Quote(key, words, batch.idOf(number), stringOf(metadata.get(DocumentMetaInfos.CONTENT_CODE)),
						titleOf(metadata)));
				out.append("⟦").append(key).append('|').append(words).append('⟧');
				kept++;
			} else {
				out.append(words);
				dropped++;
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Deep search quotation not found in fragment:" + number + ", kept as plain text: "
							+ abbreviated(words));
				}
			}
			last = matcher.end();
		}
		out.append(partialAnalysis.substring(last));
		final String checked = unquoteUnverified(out.toString(), batchTexts);
		if (LOGGER.isDebugEnabled() && (kept > 0 || dropped > 0)) {
			LOGGER.debug("Deep search partial analysis quotations: " + kept + " verified, " + dropped
					+ " not found in their fragment");
		}
		return checked;
	}

	/**
	 * The final text as its reader gets it: each kept quotation the standard way,
	 * {@code “exact words” (Document title)}, with no fragment id; any other bracketed
	 * pattern its words only; quotation marks around words none of the run's fragments has
	 * taken away.
	 */
	public String render(String text) {
		if (text == null || text.isEmpty()) {
			return text;
		}
		final StringBuilder out = new StringBuilder();
		final Matcher matcher = KEPT_QUOTE.matcher(text);
		int last = 0;
		while (matcher.find()) {
			out.append(text, last, matcher.start());
			final Quote quote = quotes.get(matcher.group(1));
			if (quote != null) {
				// the words checked against the fragment, whatever the consolidation copied
				out.append('“').append(quote.text()).append('”');
				if (quote.title() != null) {
					out.append(" (").append(quote.title()).append(')');
				}
			} else {
				out.append(matcher.group(2).trim());
			}
			last = matcher.end();
		}
		out.append(text.substring(last));
		String rendered = OTHER_PATTERN.matcher(out.toString()).replaceAll(matchResult -> {
			final String inner = matchResult.group(1);
			final int bar = inner.indexOf('|');
			return Matcher.quoteReplacement(bar >= 0 ? inner.substring(bar + 1).trim() : "");
		});
		rendered = rendered.replace("⟦", "").replace("⟧", "");
		return unquoteUnverified(rendered, fragmentTexts);
	}

	/**
	 * The text rendered while it streams (see {@link #render(String)}): what follows a
	 * quotation or a pattern still open is held until it closes, at most
	 * {@value #MAX_HELD_CHARS} characters, then given as it is rendered.
	 */
	public Flux<String> render(Flux<String> text) {
		final StringBuilder held = new StringBuilder();
		return text.concatMap(chunk -> {
			held.append(chunk);
			final int open = openAt(held);
			if (open < 0 || held.length() - open > MAX_HELD_CHARS) {
				final String out = render(held.toString());
				held.setLength(0);
				return Flux.just(out);
			}
			if (open == 0) {
				return Flux.empty();
			}
			final String before = held.substring(0, open);
			held.delete(0, open);
			return Flux.just(render(before));
		}).concatWith(Flux.defer(() -> {
			final String rest = render(held.toString());
			held.setLength(0);
			return Flux.just(rest);
		})).filter(chunk -> !chunk.isEmpty());
	}

	/**
	 * Adds documents an analysis reads directly, with no partial analysis: the final
	 * text may quote them.
	 */
	public void addSources(List<Document> documents) {
		if (documents == null) {
			return;
		}
		for (Document document : documents) {
			if (document != null && document.getText() != null) {
				fragmentTexts.add(normalized(document.getText()));
			}
		}
	}

	/**
	 * The text with the quotation marks taken away around the quoted words
	 * {@code keepQuoted} does not keep (quotations of at least {@value #MIN_QUOTE_CHARS}
	 * normalized characters only).
	 */
	public static String unquoteWhere(String text, java.util.function.Predicate<String> keepQuoted) {
		if (text == null || text.isEmpty()) {
			return text;
		}
		return PLAIN_QUOTE.matcher(text).replaceAll(matchResult -> {
			String words = null;
			for (int group = 1; group <= 3 && words == null; group++) {
				words = matchResult.group(group);
			}
			if (words == null || normalized(words).length() < MIN_QUOTE_CHARS || keepQuoted.test(words)) {
				return Matcher.quoteReplacement(matchResult.group());
			}
			return Matcher.quoteReplacement(words);
		});
	}

	/**
	 * The same while the text streams: what follows a quotation still open is held
	 * until it closes, at most {@value #MAX_HELD_CHARS} characters.
	 */
	public static Flux<String> unquotingWhere(Flux<String> text, java.util.function.Predicate<String> keepQuoted) {
		return Flux.defer(() -> {
			final StringBuilder held = new StringBuilder();
			return text.concatMap(chunk -> {
				held.append(chunk);
				final int open = openAt(held);
				if (open < 0 || held.length() - open > MAX_HELD_CHARS) {
					final String out = unquoteWhere(held.toString(), keepQuoted);
					held.setLength(0);
					return Flux.just(out);
				}
				if (open == 0) {
					return Flux.<String>empty();
				}
				final String before = held.substring(0, open);
				held.delete(0, open);
				return Flux.just(unquoteWhere(before, keepQuoted));
			}).concatWith(Flux.defer(() -> {
				final String rest = unquoteWhere(held.toString(), keepQuoted);
				held.setLength(0);
				return Flux.just(rest);
			})).filter(chunk -> !chunk.isEmpty());
		});
	}

	/** The quotations kept, in the order they were found. */
	public List<Quote> quotes() {
		synchronized (quotes) {
			return new ArrayList<>(quotes.values());
		}
	}

	/** The quotation kept under {@code key}, null when none. */
	public Quote quote(String key) {
		return quotes.get(key);
	}

	/** Where a quotation or a pattern still open starts in the text, -1 when none is. */
	static int openAt(CharSequence text) {
		int pattern = -1;
		int typographic = -1;
		int guillemet = -1;
		int straight = -1;
		boolean straightOpen = false;
		for (int i = 0; i < text.length(); i++) {
			switch (text.charAt(i)) {
			case '⟦' -> pattern = pattern < 0 ? i : pattern;
			case '⟧' -> pattern = -1;
			case '“' -> typographic = typographic < 0 ? i : typographic;
			case '”' -> typographic = -1;
			case '«' -> guillemet = guillemet < 0 ? i : guillemet;
			case '»' -> guillemet = -1;
			case '"' -> {
				straightOpen = !straightOpen;
				straight = straightOpen ? i : -1;
			}
			case '\n' -> {
				// a quotation never spans a line (see PLAIN_QUOTE)
				typographic = -1;
				guillemet = -1;
				straightOpen = false;
				straight = -1;
			}
			default -> {
			}
			}
		}
		int first = -1;
		for (int position : new int[] { pattern, typographic, guillemet, straight }) {
			if (position >= 0 && (first < 0 || position < first)) {
				first = position;
			}
		}
		return first;
	}

	/** The text with the quotation marks taken away around the words none of the texts has. */
	static String unquoteUnverified(String text, List<String> texts) {
		final List<String> sources;
		synchronized (texts) {
			sources = new ArrayList<>(texts);
		}
		return PLAIN_QUOTE.matcher(text).replaceAll(matchResult -> {
			String words = null;
			for (int group = 1; group <= 3 && words == null; group++) {
				words = matchResult.group(group);
			}
			if (words == null || normalized(words).length() < MIN_QUOTE_CHARS) {
				return Matcher.quoteReplacement(matchResult.group());
			}
			for (String source : sources) {
				if (contains(source, words)) {
					return Matcher.quoteReplacement(matchResult.group());
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Deep search quotation marks taken away, no fragment has: " + abbreviated(words));
			}
			return Matcher.quoteReplacement(words);
		});
	}

	/**
	 * Whether the normalized source has the words: whole, or each of their pieces joined
	 * by an ellipsis, in order.
	 */
	public static boolean contains(String normalizedSource, String words) {
		if (normalizedSource == null || words == null) {
			return false;
		}
		int from = 0;
		boolean any = false;
		for (String piece : words.split("…|\\.\\.\\.|\\[\\s*\\.\\.\\.\\s*\\]|\\(\\s*\\.\\.\\.\\s*\\)")) {
			final String normalizedPiece = normalized(piece);
			if (normalizedPiece.isEmpty()) {
				continue;
			}
			final int at = normalizedSource.indexOf(normalizedPiece, from);
			if (at < 0) {
				return false;
			}
			from = at + normalizedPiece.length();
			any = true;
		}
		return any;
	}

	/** Letters and digits only, lower case, without accents, single spaced. */
	public static String normalized(String text) {
		if (text == null) {
			return "";
		}
		final String decomposed = Normalizer.normalize(text, Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
		return decomposed.toLowerCase().replaceAll("[^\\p{L}\\p{N}]+", " ").trim();
	}

	/** The title of the document a fragment belongs to: its extracted title, else its file name. */
	static String titleOf(Map<String, Object> metadata) {
		if (metadata == null) {
			return null;
		}
		final String title = ExtractedDocumentMetaData.of(metadata).getTitle();
		if (title != null && !title.isBlank()) {
			return title.trim();
		}
		final String fileName = stringOf(metadata.get(DocumentMetaInfos.GEBO_FILE_NAME));
		return fileName != null && !fileName.isBlank() ? fileName.trim() : null;
	}

	private static String stringOf(Object value) {
		return value != null ? value.toString() : null;
	}

	private static String abbreviated(String text) {
		return text.length() <= 80 ? text : text.substring(0, 80) + "…";
	}
}
