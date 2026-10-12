/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * How much of what a deep search found its analysis covers, measured by the tool,
 * not judged by a model: the documents found and how much of each was read and used,
 * the yield of each search when the source can tell it, the documents of the scope no
 * search reached, what the analysis itself reports as missing. When the coverage is
 * thin for an analysis, {@link #isCompletionRequired()} asks the agent to complete it
 * before answering, and the agentic loop holds an answer that does not.
 * <p>
 * The verdict and its note come first, the per document detail last.
 */
@Data
@JsonPropertyOrder({ "completionRequired", "note", "documentsFound", "documentsUsed", "documentsUnread", "notReached",
		"notCovered", "searches", "documents" })
public class DeepSearchCoverage {
	/**
	 * A document found: the fragments of it the analysis read and left unread, its
	 * length in fragments when known, and whether it is one of its sources.
	 */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class DocumentCoverage {
		private String name;
		private int fragmentsAnalysed;
		private int fragmentsUnread;
		/** The fragments the whole document has; null when not known. */
		private Integer fragmentsInDocument;
		private boolean usedAsSource;
	}

	/** A search of the deep search: the results it gave and the documents it was the first to find. */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SearchCoverage {
		private String query;
		private int results;
		private int newDocuments;
	}

	/**
	 * The coverage is thin for an analysis and has to be completed before answering
	 * (searches of what is missing, documents read whole, a deep search aimed at it).
	 */
	private boolean completionRequired = false;
	/** Why the coverage is thin and how to complete it; null when it is not thin. */
	private String note = null;
	private int documentsFound = 0;
	private int documentsUsed = 0;
	/** The documents found the analysis left unread (it stopped before their fragments). */
	private int documentsUnread = 0;
	/**
	 * The documents of the scope (the chat's knowledge bases) no search reached; null
	 * when not known. Told, not judged: their number says nothing of the question.
	 */
	private Integer notReached = null;
	/** What the analysis reports as missing, when it reports it; null otherwise. */
	private String notCovered = null;
	private List<DocumentCoverage> documents = new ArrayList<>();
	/** The yield of each search, when the source runs them one by one; null otherwise. */
	private List<SearchCoverage> searches = null;
}
