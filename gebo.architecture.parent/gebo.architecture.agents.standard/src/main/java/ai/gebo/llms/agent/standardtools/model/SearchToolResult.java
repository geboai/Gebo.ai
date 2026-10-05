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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * What a search tool answers to the model: the content fragments found, ranked
 * against the search objective and fitted to the requested size, each with its
 * source, plus the outcome of the search so the model can tell a failure from an
 * empty result.
 */
@Data
public class SearchToolResult {
	public enum Status {
		/** Contents found and returned. */
		OK,
		/** Contents returned, but some of the searched systems failed. */
		PARTIAL,
		/** Nothing new found: no result, or only contents already returned. */
		NO_RESULTS,
		/** The user is not allowed to search this source. */
		NOT_ALLOWED,
		/** The search failed. */
		FAILED
	}

	/** A content fragment, taken from one of the documents found. */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Fragment {
		/** Position of the fragment in the answer, 1 is the most relevant. */
		private int ref;
		private String title;
		/** Url or location of the document. */
		private String source;
		/** The code identifying the document the fragment comes from. */
		private String documentCode;
		/** Position of the fragment in its document, as "n/total" when known. */
		private String chunk;
		private String content;
	}

	private Status status = Status.OK;
	private String message = null;
	/** Whether the fragments were ranked against the search objective. */
	private boolean ranked = false;
	/** Documents found by the search, before ranking and filtering. */
	private int documentsFound = 0;
	/** Documents skipped because already returned by a previous call for the same request. */
	private int documentsAlreadyReturned = 0;
	/** Size of the returned contents, in tokens. */
	private int tokens = 0;
	/**
	 * The sources that could not be searched, and why (not responding within the
	 * timeout, out of service, access refused, failed): what they hold is missing from
	 * the results. Null when every source was searched.
	 */
	private List<String> unavailableSources = null;
	private List<Fragment> fragments = new ArrayList<>();

	public static SearchToolResult of(Status status, String message) {
		SearchToolResult result = new SearchToolResult();
		result.setStatus(status);
		result.setMessage(message);
		return result;
	}
}
