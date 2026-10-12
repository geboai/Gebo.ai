/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

import ai.gebo.model.DocumentMetaInfos;

/**
 * The best documents of a ranking of fragments: a search returns documents, not
 * loose fragments. The documents are rated by their best fragment, the first
 * {@code topK} of them kept with all their fragments, the others left.
 */
public final class RankedDocuments {
	private RankedDocuments() {
	}

	/**
	 * The fragments of the {@code topK} best documents, the documents in the order of
	 * their best fragment; each document's fragments in reading order when
	 * {@code readingOrder} and their positions are known, in ranking order otherwise.
	 * A fragment without a document code is a document of its own.
	 */
	public static List<Document> top(List<Document> ranked, int topK, boolean readingOrder) {
		final Map<String, List<Document>> byDocument = new LinkedHashMap<>();
		for (Document fragment : ranked) {
			if (fragment == null) {
				continue;
			}
			final String code = documentOf(fragment);
			if (!byDocument.containsKey(code) && byDocument.size() >= topK) {
				// a document rated below the topK best
				continue;
			}
			byDocument.computeIfAbsent(code, key -> new ArrayList<>()).add(fragment);
		}
		final List<Document> kept = new ArrayList<>();
		for (List<Document> fragments : byDocument.values()) {
			if (readingOrder && fragments.stream().allMatch(fragment -> positionOf(fragment) != null)) {
				fragments.sort((a, b) -> Long.compare(positionOf(a), positionOf(b)));
			}
			kept.addAll(fragments);
		}
		return kept;
	}

	/** The documents a list of fragments comes from. */
	public static int documentsIn(List<Document> fragments) {
		return (int) fragments.stream().filter(fragment -> fragment != null).map(RankedDocuments::documentOf)
				.distinct().count();
	}

	static String documentOf(Document fragment) {
		final Object code = fragment.getMetadata() != null ? fragment.getMetadata().get(DocumentMetaInfos.CONTENT_CODE)
				: null;
		return code != null ? code.toString() : "fragment:" + fragment.getId();
	}

	static Long positionOf(Document fragment) {
		final Object position = fragment.getMetadata() != null
				? fragment.getMetadata().get(DocumentMetaInfos.GEBO_CHUNK_POSITION)
				: null;
		if (position instanceof Number number) {
			return number.longValue();
		}
		if (position != null) {
			try {
				return Long.parseLong(position.toString().trim());
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}
}
