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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;

import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.ExtractedDocumentMetaData;

/**
 * The fragments the analyses of a deep search found relevant, from three signals: the
 * {@value #RELEVANT_FRAGMENTS_MARKER} list a partial analysis gives (by the numbers of
 * its batch), the fragments its verified quotations come from and the fragments of the
 * documents it names, by file name or title. A list that runs away (more entries than
 * the batch has fragments) or lists the whole batch says nothing and is ignored; a
 * missing or empty list says nothing either. Used by the partial analyses running in
 * parallel.
 */
public class DeepSearchRelevance {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeepSearchRelevance.class);
	/** The field of a partial analysis listing its relevant fragments, by number. */
	public static final String RELEVANT_FRAGMENTS_MARKER = "RELEVANT_FRAGMENTS";
	private final Set<String> relevantFragmentIds = ConcurrentHashMap.newKeySet();
	private int listed = 0;
	private int quoted = 0;
	private int named = 0;

	/**
	 * Records what a partial analysis found relevant in its batch: the fragments its list
	 * gives ({@code listRanAway} when the list is not to be read) and the fragments of
	 * the documents its text names. The quotations are recorded at the end (see
	 * {@link #recordQuotations(DeepSearchQuotations)}).
	 *
	 * @param analysis     the partial analysis, its list included
	 * @param batch        the numbered batch it analysed
	 * @param listRanAway  whether its list ran away (see
	 *                     {@link DeepSearchBatchTrace#runawayReport})
	 * @return the number of fragments of the batch it made relevant
	 */
	public int recordAnalysis(String analysis, DeepSearchBatchTrace.NumberedBatch batch, boolean listRanAway) {
		if (analysis == null || batch == null) {
			return 0;
		}
		final Set<String> found = new LinkedHashSet<>();
		if (!listRanAway) {
			final Set<String> fromList = new LinkedHashSet<>();
			for (String number : DeepSearchBatchTrace.irrelevantEntries(analysis, RELEVANT_FRAGMENTS_MARKER)) {
				final String id = batch.idOf(number.replaceAll("[^\\p{Alnum}-]", ""));
				if (id != null) {
					fromList.add(id);
				}
			}
			final int batchSize = batch.documents().size();
			if (batchSize > 1 && fromList.size() == batchSize) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Deep search relevance: a partial analysis listed all " + batchSize
							+ " fragment(s) of its batch as relevant: its list is ignored");
				}
			} else {
				found.addAll(fromList);
				synchronized (this) {
					listed += fromList.size();
				}
			}
		}
		final String text = DeepSearchBatchTrace.withoutIrrelevantLists(analysis, RELEVANT_FRAGMENTS_MARKER);
		final String folded = " " + folded(text) + " ";
		int byName = 0;
		for (Document numbered : batch.documents()) {
			final String id = batch.idOf(numbered.getId());
			if (id != null && !found.contains(id) && mentioned(folded, numbered.getMetadata())) {
				found.add(id);
				byName++;
			}
		}
		synchronized (this) {
			named += byName;
		}
		relevantFragmentIds.addAll(found);
		return found.size();
	}

	/** Records the fragments the verified quotations of the deep search come from. */
	public void recordQuotations(DeepSearchQuotations quotations) {
		if (quotations == null) {
			return;
		}
		int count = 0;
		for (DeepSearchQuotations.Quote quote : quotations.quotes()) {
			if (quote.fragmentId() != null && relevantFragmentIds.add(quote.fragmentId())) {
				count++;
			}
		}
		synchronized (this) {
			quoted += count;
		}
	}

	/** Whether the fragment was found relevant. */
	public boolean isRelevant(String fragmentId) {
		return fragmentId != null && relevantFragmentIds.contains(fragmentId);
	}

	/** Whether no fragment was found relevant: nothing to choose the documents by. */
	public boolean isEmpty() {
		return relevantFragmentIds.isEmpty();
	}

	/** How many fragments each signal made relevant, for the logs. */
	public synchronized String summary() {
		return relevantFragmentIds.size() + " relevant fragment(s): " + listed + " listed, " + quoted
				+ " quoted, " + named + " named";
	}

	/**
	 * Whether the analysis (folded, between spaces) names the document of the fragment:
	 * its file name (with its extension, or without it when made of two words or more)
	 * or its title (two words or more). A one word title or name may be any word of the
	 * text, and does not count.
	 */
	static boolean mentioned(String foldedText, Map<String, Object> metadata) {
		for (String name : namesOf(metadata)) {
			if (foldedText.contains(" " + name + " ")) {
				return true;
			}
		}
		return false;
	}

	/** The names a document can be told by, folded: the ones that count only. */
	static List<String> namesOf(Map<String, Object> metadata) {
		final List<String> names = new ArrayList<>();
		if (metadata == null) {
			return names;
		}
		final Object fileName = metadata.get(DocumentMetaInfos.GEBO_FILE_NAME);
		if (fileName != null && !fileName.toString().isBlank()) {
			final String file = fileName.toString().trim();
			final int dot = file.lastIndexOf('.');
			if (dot > 0 && dot < file.length() - 1) {
				// with its extension it is a file name, whatever its words
				addName(names, folded(file), 1);
				addName(names, folded(file.substring(0, dot)), 2);
			} else {
				addName(names, folded(file), 2);
			}
		}
		final String title = ExtractedDocumentMetaData.of(metadata).getTitle();
		if (title != null) {
			addName(names, folded(title), 2);
		}
		return names;
	}

	private static void addName(List<String> names, String name, int minimumWords) {
		if (!name.isEmpty() && name.split(" ").length >= minimumWords && !names.contains(name)) {
			names.add(name);
		}
	}

	/**
	 * The text lower case, without accents, its words separated by one space (any other
	 * character a separator): "La_Scienza-Occulta.pdf" is "la scienza occulta pdf".
	 */
	static String folded(String text) {
		if (text == null) {
			return "";
		}
		final String noAccents = Normalizer.normalize(text, Normalizer.Form.NFKD).replaceAll("\\p{M}+", "");
		return noAccents.toLowerCase().replaceAll("[^\\p{L}\\p{N}]+", " ").trim();
	}
}
