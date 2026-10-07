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
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;

import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener.ToolCallExecuted;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.ExtractedDocumentMetaData;
import reactor.core.publisher.Flux;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 * The quotations of one answer, best effort: a model may put any sentence between
 * quotation marks, its own paraphrase included, and whoever reads the answer cannot
 * tell. Used by the deep searches and by every answer of the chat pipelines that reads
 * documents.
 * <p>
 * The deep search partial analyses write their quotations as
 * {@code ⟦q:<number>|<exact words>⟧}; each is checked here against the text of its
 * fragment, while its batch is at hand: a verified one is kept, carried by the
 * consolidation as {@code ⟦Q<n>|...⟧}, an unverified one becomes plain text. An answer
 * reading its documents directly (see {@link #addSources(List)}) writes
 * {@code ⟦q:<fragmentId>|<exact words>⟧}, checked when the answer is rendered.
 * <p>
 * The text given to the user renders each verified quotation the standard way,
 * {@code “exact words” (Document title)}, with no fragment id; any other quotation marks
 * are kept only around words one of the answer's sources has (its documents, its tools'
 * results), and are all kept when the answer has no source to check them against. Code
 * (fenced blocks, inline code) is never changed. Nothing here ever fails a delivery: a
 * broken pattern gives fewer quotations, never an invented one.
 */
public class DeepSearchQuotations {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeepSearchQuotations.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();
	/** The pattern the partial analyses write their quotations in. */
	static final Pattern PARTIAL_QUOTE = Pattern.compile("⟦\\s*q\\s*:\\s*([^|⟧\\s]+)\\s*\\|(.*?)⟧",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	/** A quotation of a document read directly: its id may be any fragment id. */
	static final Pattern DIRECT_QUOTE = Pattern.compile("⟦\\s*q\\s*:\\s*([^|⟧]*?)\\s*\\|(.*?)⟧",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	/** A verified quotation, as the consolidation carries it. */
	static final Pattern KEPT_QUOTE = Pattern.compile("⟦\\s*(Q\\d+)\\s*\\|(.*?)⟧", Pattern.DOTALL);
	/** Any other bracketed pattern: its words, without the brackets. */
	static final Pattern OTHER_PATTERN = Pattern.compile("⟦([^⟧]*)⟧", Pattern.DOTALL);
	/** Quotation marks around a span: “…”, «…», "…". */
	static final Pattern PLAIN_QUOTE = Pattern.compile("“([^”\\n]+)”|«([^»\\n]+)»|\"([^\"\\n]+)\"");
	/** Inline code: never changed. */
	static final Pattern INLINE_CODE = Pattern.compile("`[^`\\n]*`");
	/** A fenced code block's fence: what it encloses is never changed. */
	static final String FENCE = "```";
	/** Below this many normalized characters a quoted span is a word, not a quotation: left as it is. */
	static final int MIN_QUOTE_CHARS = 25;
	/** How much the streamed rendering holds back for a quotation still open. */
	static final int MAX_HELD_CHARS = 4000;

	/** A verified quotation. */
	public record Quote(String key, String text, String fragmentId, String documentCode, String title) {
	}

	private final Map<String, Quote> quotes = Collections.synchronizedMap(new LinkedHashMap<>());
	/** The normalized text of every fragment and tool result the answer was given. */
	private final List<String> fragmentTexts = Collections.synchronizedList(new ArrayList<>());
	/** The documents read directly, by their fragment id. */
	private final Map<String, Document> directSources = Collections.synchronizedMap(new LinkedHashMap<>());
	private final AtomicInteger next = new AtomicInteger(1);
	/** The tool calls of the answer, whose results it may quote; null when none. */
	private volatile ToolCallsListener toolCalls = null;
	/** The tool calls whose results were already read. */
	private int toolCallsRead = 0;

	/**
	 * A partial analysis with its quotations checked against its batch: each
	 * {@code ⟦q:<number>|words⟧} whose words its fragment, or another fragment of the
	 * batch, has is kept as
	 * {@code ⟦Q<n>|words⟧}, the others become their words; quotation marks around words
	 * no fragment read so far has are taken away.
	 */
	public String keepVerified(String partialAnalysis, DeepSearchBatchTrace.NumberedBatch batch) {
		if (partialAnalysis == null || partialAnalysis.isEmpty()) {
			return partialAnalysis;
		}
		final Map<String, Document> byNumber = new LinkedHashMap<>();
		if (batch != null) {
			for (Document numbered : batch.documents()) {
				byNumber.put(numbered.getId(), numbered);
				final String text = normalized(numbered.getText());
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
			final String written = matcher.group(1).trim();
			final String words = matcher.group(2).trim();
			// the fragment of the number written when it has the words, else the first of the
			// batch that has them (a model may write the number of another fragment)
			String number = null;
			if (!words.isEmpty()) {
				final Document named = byNumber.get(written);
				if (named != null && contains(normalized(named.getText()), words)) {
					number = written;
				} else {
					for (Map.Entry<String, Document> entry : byNumber.entrySet()) {
						if (contains(normalized(entry.getValue().getText()), words)) {
							number = entry.getKey();
							break;
						}
					}
				}
			}
			if (number != null) {
				final Document fragment = byNumber.get(number);
				final String key = "Q" + next.getAndIncrement();
				final Map<String, Object> metadata = fragment.getMetadata();
				quotes.put(key, new Quote(key, words, batch.idOf(number), stringOf(metadata.get(DocumentMetaInfos.CONTENT_CODE)),
						titleOf(metadata)));
				out.append("⟦").append(key).append('|').append(words).append('⟧');
				kept++;
				if (LOGGER.isDebugEnabled() && !number.equals(written)) {
					LOGGER.debug("Deep search quotation written for fragment:" + written + " found in fragment:" + number
							+ ": " + abbreviated(words));
				}
			} else {
				out.append(words);
				dropped++;
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Deep search quotation not found in fragment:" + written + " nor in its batch, kept as plain text: "
							+ abbreviated(words));
				}
			}
			last = matcher.end();
		}
		out.append(partialAnalysis.substring(last));
		// against every fragment read so far: an analysis carries the consolidation of the ones
		// before it, quoting their fragments
		final String checked = outsideCode(out.toString(), prose -> unquoteUnverified(prose, fragmentTexts));
		if (LOGGER.isDebugEnabled() && (kept > 0 || dropped > 0)) {
			LOGGER.debug("Deep search partial analysis quotations: " + kept + " verified, " + dropped
					+ " not found in their fragment");
		}
		return checked;
	}

	/**
	 * The final text as its reader gets it: each verified quotation the standard way,
	 * {@code “exact words” (Document title)}, with no fragment id; any other bracketed
	 * pattern its words only; quotation marks around words none of the answer's sources
	 * has taken away. Code is left as it is.
	 */
	public String render(String text) {
		if (text == null || text.isEmpty()) {
			return text;
		}
		return outsideCode(text, this::renderProse);
	}

	/**
	 * The text rendered while it streams (see {@link #render(String)}): what follows a
	 * quotation or a pattern still open is held until it closes, at most
	 * {@value #MAX_HELD_CHARS} characters, then given as it is rendered.
	 */
	public Flux<String> render(Flux<String> text) {
		return streamed(text, this::renderProse);
	}

	/** A rendering of a text given piece by piece (see {@link #render(String)}). */
	public Streaming streaming() {
		return new Streaming(this::renderProse);
	}

	/**
	 * Adds documents an answer reads directly, with no partial analysis: the answer may
	 * quote them, as {@code ⟦q:<fragmentId>|words⟧} or between quotation marks.
	 */
	public void addSources(List<Document> documents) {
		if (documents == null) {
			return;
		}
		for (Document document : documents) {
			if (document != null && document.getText() != null) {
				fragmentTexts.add(normalized(document.getText()));
				if (document.getId() != null) {
					directSources.put(document.getId(), document);
				}
			}
		}
	}

	/**
	 * The tool calls of the answer: its quotation marks are kept around words their
	 * results have, read as the tools run.
	 */
	public void addToolResults(ToolCallsListener calls) {
		this.toolCalls = calls;
	}

	/**
	 * The text with the quotation marks taken away around the quoted words
	 * {@code keepQuoted} does not keep (quotations of at least {@value #MIN_QUOTE_CHARS}
	 * normalized characters only). Code is left as it is.
	 */
	public static String unquoteWhere(String text, Predicate<String> keepQuoted) {
		if (text == null || text.isEmpty()) {
			return text;
		}
		return outsideCode(text, prose -> unquoteProse(prose, keepQuoted));
	}

	/**
	 * The same while the text streams: what follows a quotation still open is held
	 * until it closes, at most {@value #MAX_HELD_CHARS} characters.
	 */
	public static Flux<String> unquotingWhere(Flux<String> text, Predicate<String> keepQuoted) {
		return streamed(text, prose -> unquoteProse(prose, keepQuoted));
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

	/**
	 * A text given piece by piece, transformed outside code: what follows a quotation, a
	 * pattern or an inline code still open is held until it closes (at most
	 * {@value #MAX_HELD_CHARS} characters); a fenced code block is given as it is, as it
	 * arrives.
	 */
	public static final class Streaming {
		private final UnaryOperator<String> prose;
		private final StringBuilder held = new StringBuilder();
		private boolean inFence = false;

		Streaming(UnaryOperator<String> prose) {
			this.prose = prose;
		}

		/** What can be given of the text so far, the new piece added. */
		public synchronized String next(String piece) {
			if (piece != null) {
				held.append(piece);
			}
			return drain(false);
		}

		/** What was held, once the text ended. */
		public synchronized String rest() {
			return drain(true);
		}

		private String drain(boolean all) {
			final StringBuilder out = new StringBuilder();
			while (held.length() > 0) {
				if (inFence) {
					final int close = held.indexOf(FENCE);
					if (close < 0) {
						// the code as it is, but a fence that may be closing
						final int keep = all ? 0 : trailingBackticks(held);
						out.append(held, 0, held.length() - keep);
						held.delete(0, held.length() - keep);
						break;
					}
					out.append(held, 0, close + FENCE.length());
					held.delete(0, close + FENCE.length());
					inFence = false;
					continue;
				}
				final int open = held.indexOf(FENCE);
				if (open >= 0) {
					out.append(outsideInlineCode(held.substring(0, open), prose)).append(FENCE);
					held.delete(0, open + FENCE.length());
					inFence = true;
					continue;
				}
				if (all) {
					out.append(outsideInlineCode(held.toString(), prose));
					held.setLength(0);
					break;
				}
				// a fence that may be opening, a quotation, pattern or inline code still open: held
				int cut = held.length() - trailingBackticks(held);
				final int unclosed = openAt(held);
				if (unclosed >= 0 && held.length() - unclosed <= MAX_HELD_CHARS) {
					cut = Math.min(cut, unclosed);
				}
				if (cut > 0) {
					out.append(outsideInlineCode(held.substring(0, cut), prose));
					held.delete(0, cut);
				}
				break;
			}
			return out.toString();
		}
	}

	private static Flux<String> streamed(Flux<String> text, UnaryOperator<String> prose) {
		return Flux.defer(() -> {
			final Streaming streaming = new Streaming(prose);
			return text.concatMap(piece -> Flux.just(streaming.next(piece)))
					.concatWith(Flux.defer(() -> Flux.just(streaming.rest()))).filter(piece -> !piece.isEmpty());
		});
	}

	/** The text with {@code prose} applied outside its fenced code blocks and inline code. */
	static String outsideCode(String text, UnaryOperator<String> prose) {
		final StringBuilder out = new StringBuilder();
		int from = 0;
		while (from < text.length()) {
			final int open = text.indexOf(FENCE, from);
			if (open < 0) {
				out.append(outsideInlineCode(text.substring(from), prose));
				break;
			}
			out.append(outsideInlineCode(text.substring(from, open), prose));
			final int close = text.indexOf(FENCE, open + FENCE.length());
			final int end = close < 0 ? text.length() : close + FENCE.length();
			out.append(text, open, end);
			from = end;
		}
		return out.toString();
	}

	private static String outsideInlineCode(String text, UnaryOperator<String> prose) {
		if (text.isEmpty()) {
			return text;
		}
		final StringBuilder out = new StringBuilder();
		final Matcher matcher = INLINE_CODE.matcher(text);
		int last = 0;
		while (matcher.find()) {
			out.append(prose.apply(text.substring(last, matcher.start())));
			out.append(matcher.group());
			last = matcher.end();
		}
		out.append(prose.apply(text.substring(last)));
		return out.toString();
	}

	/** How many backticks end the text: a fence may be on its way. */
	private static int trailingBackticks(CharSequence text) {
		int count = 0;
		while (count < text.length() && count < FENCE.length() - 1
				&& text.charAt(text.length() - 1 - count) == '`') {
			count++;
		}
		return count;
	}

	/** Prose rendered: quotations of documents read directly, kept quotations, other patterns, plain quotes. */
	private String renderProse(String text) {
		if (text.isEmpty()) {
			return text;
		}
		String rendered = renderDirectQuotes(text);
		final StringBuilder out = new StringBuilder();
		final Matcher matcher = KEPT_QUOTE.matcher(rendered);
		int last = 0;
		while (matcher.find()) {
			out.append(rendered, last, matcher.start());
			final Quote quote = quotes.get(matcher.group(1));
			if (quote != null) {
				// the words checked against the fragment, whatever the consolidation copied
				out.append(standard(quote));
			} else {
				out.append(matcher.group(2).trim());
			}
			last = matcher.end();
		}
		out.append(rendered.substring(last));
		rendered = OTHER_PATTERN.matcher(out.toString()).replaceAll(matchResult -> {
			final String inner = matchResult.group(1);
			final int bar = inner.indexOf('|');
			return Matcher.quoteReplacement(bar >= 0 ? inner.substring(bar + 1).trim() : "");
		});
		rendered = rendered.replace("⟦", "").replace("⟧", "");
		readNewToolResults();
		return unquoteUnverified(rendered, fragmentTexts);
	}

	/**
	 * Each {@code ⟦q:<fragmentId>|words⟧} whose words a document read directly has, the
	 * standard way: the document of that id when it has them, else the first that has
	 * them (a model may miscopy a long id); the others their words only.
	 */
	private String renderDirectQuotes(String text) {
		if (directSources.isEmpty() || text.indexOf('⟦') < 0) {
			return text;
		}
		final List<Document> sources;
		synchronized (directSources) {
			sources = new ArrayList<>(directSources.values());
		}
		final StringBuilder out = new StringBuilder();
		final Matcher matcher = DIRECT_QUOTE.matcher(text);
		int last = 0;
		while (matcher.find()) {
			out.append(text, last, matcher.start());
			final String id = matcher.group(1).trim();
			final String words = matcher.group(2).trim();
			Document source = null;
			if (!words.isEmpty()) {
				final Document named = directSources.get(id);
				if (named != null && contains(normalized(named.getText()), words)) {
					source = named;
				} else {
					for (Document document : sources) {
						if (contains(normalized(document.getText()), words)) {
							source = document;
							break;
						}
					}
				}
			}
			if (source != null) {
				final String key = "Q" + next.getAndIncrement();
				final Map<String, Object> metadata = source.getMetadata();
				final Quote quote = new Quote(key, words, source.getId(),
						stringOf(metadata.get(DocumentMetaInfos.CONTENT_CODE)), titleOf(metadata));
				quotes.put(key, quote);
				out.append(standard(quote));
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Quotation verified in document:" + source.getId()
							+ (id.equals(source.getId()) ? "" : " (written for:" + id + ")") + ": " + abbreviated(words));
				}
			} else {
				out.append(words);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Quotation of document:" + id + " found in no document, kept as plain text: "
							+ abbreviated(words));
				}
			}
			last = matcher.end();
		}
		out.append(text.substring(last));
		return out.toString();
	}

	private static String standard(Quote quote) {
		return "“" + quote.text() + "”" + (quote.title() != null ? " (" + quote.title() + ")" : "");
	}

	/** The results of the tool calls made since the last read, added to the sources. */
	private void readNewToolResults() {
		final ToolCallsListener calls = toolCalls;
		if (calls == null) {
			return;
		}
		synchronized (this) {
			final List<ToolCallExecuted> executed = calls.getCalls();
			for (int i = toolCallsRead; i < executed.size(); i++) {
				final ToolCallExecuted call = executed.get(i);
				if (call != null && call.getResult() != null) {
					fragmentTexts.add(normalized(resultTexts(call.getResult())));
				}
			}
			if (LOGGER.isDebugEnabled() && executed.size() > toolCallsRead) {
				LOGGER.debug("Quotation sources: " + (executed.size() - toolCallsRead) + " tool result(s) added");
			}
			toolCallsRead = Math.max(toolCallsRead, executed.size());
		}
	}

	/** Every text value of a tool result, joined; the result as it is when not JSON. */
	static String resultTexts(String result) {
		final JsonNode node;
		try {
			node = MAPPER.readTree(result);
		} catch (RuntimeException e) {
			// a result cut to the room, or plain text: read as text
			return result;
		}
		if (node == null || !node.isContainer()) {
			return result;
		}
		final StringBuilder out = new StringBuilder();
		collectTexts(node, out);
		return out.toString();
	}

	private static void collectTexts(JsonNode node, StringBuilder out) {
		if (node == null) {
			return;
		}
		if (node.isString()) {
			out.append(node.asString()).append('\n');
		} else if (node.isContainer()) {
			for (JsonNode child : node) {
				collectTexts(child, out);
			}
		}
	}

	/** Where a quotation, a pattern or an inline code still open starts in the text, -1 when none is. */
	static int openAt(CharSequence text) {
		int pattern = -1;
		int typographic = -1;
		int guillemet = -1;
		int straight = -1;
		int code = -1;
		boolean straightOpen = false;
		boolean codeOpen = false;
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
			case '`' -> {
				codeOpen = !codeOpen;
				code = codeOpen ? i : -1;
			}
			case '\n' -> {
				// a quotation or an inline code never spans a line (see PLAIN_QUOTE, INLINE_CODE)
				typographic = -1;
				guillemet = -1;
				straightOpen = false;
				straight = -1;
				codeOpen = false;
				code = -1;
			}
			default -> {
			}
			}
		}
		int first = -1;
		for (int position : new int[] { pattern, typographic, guillemet, straight, code }) {
			if (position >= 0 && (first < 0 || position < first)) {
				first = position;
			}
		}
		return first;
	}

	/**
	 * The text with the quotation marks taken away around the words none of the texts
	 * has; as it is when there is no text to check them against.
	 */
	static String unquoteUnverified(String text, List<String> texts) {
		final List<String> sources;
		synchronized (texts) {
			sources = new ArrayList<>(texts);
		}
		if (sources.isEmpty()) {
			return text;
		}
		return unquoteProse(text, words -> {
			for (String source : sources) {
				if (contains(source, words)) {
					return true;
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Quotation marks taken away, no source has: " + abbreviated(words));
			}
			return false;
		});
	}

	private static String unquoteProse(String text, Predicate<String> keepQuoted) {
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
