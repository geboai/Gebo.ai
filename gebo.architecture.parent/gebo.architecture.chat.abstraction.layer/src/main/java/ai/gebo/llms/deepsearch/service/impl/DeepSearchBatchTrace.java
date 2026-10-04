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
