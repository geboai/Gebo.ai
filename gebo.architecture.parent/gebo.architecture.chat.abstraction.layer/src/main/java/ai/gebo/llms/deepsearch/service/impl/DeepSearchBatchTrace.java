/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.deepsearch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.model.DocumentMetaInfos;

/**
 * What a deep search partial analysis is given, for its log: the fragments of a
 * batch grouped by the document (web page, file) they come from, with their size,
 * so an analysis that goes wrong can be traced back to the documents it read.
 */
public final class DeepSearchBatchTrace {

	private DeepSearchBatchTrace() {
	}

	/**
	 * A batch as the partial analysis is given it: copies of its fragments numbered
	 * 1..n as their fragmentId, so the model lists short numbers instead of long opaque
	 * ids (fewer tokens to copy, less room for a list that repeats itself or makes ids
	 * up), with the way back to the fragments' own ids.
	 */
	public static final class NumberedBatch {
		private final List<Document> documents;
		private final Map<String, String> idByNumber;

		private NumberedBatch(List<Document> documents, Map<String, String> idByNumber) {
			this.documents = documents;
			this.idByNumber = idByNumber;
		}

		/** The numbered copies, in the batch order. */
		public List<Document> documents() {
			return documents;
		}

		/** The id of the fragment numbered {@code number}; null when no fragment has it (made up). */
		public String idOf(String number) {
			return number != null ? idByNumber.get(number.trim()) : null;
		}
	}

	/** The batch numbered (see {@link NumberedBatch}). */
	public static NumberedBatch numbered(List<Document> batch) {
		final List<Document> documents = new ArrayList<>();
		final Map<String, String> idByNumber = new HashMap<>();
		if (batch != null) {
			for (Document fragment : batch) {
				if (fragment == null) {
					continue;
				}
				final String number = String.valueOf(documents.size() + 1);
				idByNumber.put(number, fragment.getId());
				documents.add(Document.builder().id(number).text(fragment.getText())
						.metadata(fragment.getMetadata() != null ? new HashMap<>(fragment.getMetadata()) : new HashMap<>())
						.score(fragment.getScore()).build());
			}
		}
		return new NumberedBatch(documents, idByNumber);
	}

	/**
	 * Watches a partial analysis while it streams: holds once its irrelevant fragments
	 * lists ({@code marker}=...) have given more entries than the batch has fragments,
	 * a list that repeats itself or makes ids up. Only the list lines count: the text
	 * of the analysis may say anything. To be used on one answer, its text growing.
	 */
	public static Predicate<CharSequence> runawayWatch(String marker, int batchSize) {
		return new Predicate<CharSequence>() {
			/** Where the scan goes on from. */
			private int scanned = 0;
			/** Within a list line (after a marker, before its end of line). */
			private boolean inList = false;
			private boolean inEntry = false;
			private int entries = 0;

			@Override
			public synchronized boolean test(CharSequence text) {
				while (scanned < text.length()) {
					if (!inList) {
						final int at = indexOfIgnoreCase(text, marker, scanned);
						if (at < 0) {
							// a marker may be cut between two pieces
							scanned = Math.max(scanned, text.length() - marker.length() + 1);
							return false;
						}
						inList = true;
						inEntry = false;
						scanned = at + marker.length();
						continue;
					}
					final char ch = text.charAt(scanned++);
					if (ch == '\n' || ch == '\r') {
						inList = false;
					} else if (ch == ',') {
						inEntry = false;
					} else if (!Character.isWhitespace(ch) && ch != '=' && !inEntry) {
						inEntry = true;
						entries++;
						if (entries > batchSize) {
							return true;
						}
					}
				}
				return false;
			}
		};
	}

	private static int indexOfIgnoreCase(CharSequence text, String marker, int from) {
		final int last = text.length() - marker.length();
		for (int i = Math.max(0, from); i <= last; i++) {
			boolean match = true;
			for (int j = 0; j < marker.length() && match; j++) {
				match = Character.toUpperCase(text.charAt(i + j)) == Character.toUpperCase(marker.charAt(j));
			}
			if (match) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * The analysis without its irrelevant fragments lists ({@code marker}=... up to the
	 * end of the line): what is kept of an analysis whose list ran away, the list being
	 * ignored.
	 */
	public static String withoutIrrelevantLists(String analysis, String marker) {
		if (analysis == null) {
			return "";
		}
		final StringBuilder out = new StringBuilder(analysis);
		int at;
		while ((at = indexOfIgnoreCase(out, marker, 0)) >= 0) {
			int end = at;
			while (end < out.length() && out.charAt(end) != '\n' && out.charAt(end) != '\r') {
				end++;
			}
			out.delete(at, end);
		}
		return out.toString();
	}

	/** The source of a fragment: its URL, else its file name, else its content code. */
	static String sourceOf(Document fragment) {
		final Map<String, Object> metadata = fragment.getMetadata();
		if (metadata != null) {
			for (String key : List.of(DocumentMetaInfos.CONTENT_ORIGINAL_URL, DocumentMetaInfos.GEBO_FILE_NAME,
					DocumentMetaInfos.CONTENT_CODE)) {
				final Object value = metadata.get(key);
				if (value != null && !value.toString().isBlank()) {
					return value.toString();
				}
			}
		}
		return "unknown";
	}

	/**
	 * The batch by source document, largest first: "N fragment(s) T (tok) from D
	 * document(s): [source: n fragment(s) t (tok) c char(s), ...]".
	 */
	public static String composition(List<Document> batch) {
		if (batch == null || batch.isEmpty()) {
			return "no fragment";
		}
		final Map<String, long[]> bySource = new LinkedHashMap<>();
		long totalTokens = 0;
		for (Document fragment : batch) {
			if (fragment == null) {
				continue;
			}
			final String text = fragment.getText() != null ? fragment.getText() : "";
			final int tokens = ITokensCountable.stringsTokensSize(text);
			totalTokens += tokens;
			final long[] stats = bySource.computeIfAbsent(sourceOf(fragment), k -> new long[3]);
			stats[0]++;
			stats[1] += tokens;
			stats[2] += text.length();
		}
		final StringBuilder out = new StringBuilder();
		out.append(batch.size()).append(" fragment(s) ").append(totalTokens).append(" (tok) from ")
				.append(bySource.size()).append(" document(s): [");
		bySource.entrySet().stream().sorted((a, b) -> Long.compare(b.getValue()[1], a.getValue()[1]))
				.forEach(entry -> out.append(entry.getKey()).append(": ").append(entry.getValue()[0])
						.append(" fragment(s) ").append(entry.getValue()[1]).append(" (tok) ")
						.append(entry.getValue()[2]).append(" char(s); "));
		out.append("]");
		return out.toString();
	}

	/** Each fragment id of the batch with its source document, for the TRACE log. */
	public static String fragmentSources(List<Document> batch) {
		final StringBuilder out = new StringBuilder();
		if (batch != null) {
			for (Document fragment : batch) {
				if (fragment != null) {
					out.append(fragment.getId()).append(" -> ").append(sourceOf(fragment)).append("; ");
				}
			}
		}
		return out.toString();
	}

	/** The entries of the IRRILEVANT line of an analysis, repetitions included. */
	static List<String> irrelevantEntries(String analysis, String marker) {
		if (analysis == null) {
			return List.of();
		}
		final int start = analysis.toLowerCase().indexOf(marker.toLowerCase());
		if (start < 0) {
			return List.of();
		}
		final int end = analysis.indexOf('\n', start);
		final String line = analysis.substring(start + marker.length(), end < 0 ? analysis.length() : end)
				.replace("=", "");
		final List<String> entries = new ArrayList<>();
		for (String entry : line.split(",")) {
			final String id = entry.trim();
			if (!id.isEmpty()) {
				entries.add(id);
			}
		}
		return entries;
	}

	/**
	 * Null when the IRRILEVANT line of the analysis lists no more entries than the batch
	 * has fragments; else what went wrong, for a WARN: the entries listed, the distinct
	 * ones, those that are no fragment of the batch (made up), and the most repeated
	 * with the document they come from.
	 */
	public static String runawayReport(String analysis, String marker, List<Document> batch) {
		final List<String> entries = irrelevantEntries(analysis, marker);
		final int batchSize = batch != null ? batch.size() : 0;
		if (entries.size() <= batchSize) {
			return null;
		}
		final Map<String, String> sourceById = new HashMap<>();
		if (batch != null) {
			for (Document fragment : batch) {
				if (fragment != null && fragment.getId() != null) {
					sourceById.put(fragment.getId(), sourceOf(fragment));
				}
			}
		}
		final Map<String, Integer> counts = new LinkedHashMap<>();
		for (String entry : entries) {
			counts.merge(entry, 1, Integer::sum);
		}
		final long madeUp = counts.keySet().stream().filter(id -> !sourceById.containsKey(id)).count();
		final StringBuilder out = new StringBuilder();
		out.append(entries.size()).append(" irrelevant entr(ies) for ").append(batchSize).append(" fragment(s), ")
				.append(counts.size()).append(" distinct, ").append(madeUp).append(" not in the batch; most repeated: ");
		counts.entrySet().stream().sorted((a, b) -> Integer.compare(b.getValue(), a.getValue())).limit(5)
				.forEach(entry -> out.append(entry.getKey()).append(" x").append(entry.getValue()).append(" (")
						.append(sourceById.getOrDefault(entry.getKey(), "not in the batch")).append("); "));
		return out.toString();
	}
}
