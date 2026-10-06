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

import ai.gebo.llms.agent.standardtools.model.SearchToolResult.Status;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * What a deep search tool answers to the model: the analysis of the documents
 * found against the question, with the documents it relies on, plus the outcome of
 * the deep search so the model can tell a failure from an empty result.
 * <p>
 * The order is the one the model reads (tool results are otherwise serialized
 * alphabetically): the outcome and the coverage before the analysis, so a result
 * cut to the room keeps them.
 */
@Data
@JsonPropertyOrder({ "status", "message", "coverage", "fragmentsAnalysed", "tokens", "analysis", "sources",
		"unavailableSources", "documentsNotRead" })
public class DeepSearchToolResult {
	/** A document the analysis relies on. */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Source {
		private String title;
		/** Url or location of the document, when known. */
		private String source;
		/** The code identifying the document. */
		private String documentCode;
	}

	private Status status = Status.OK;
	private String message = null;
	/**
	 * How much of what was found the analysis covers (see {@link DeepSearchCoverage}):
	 * before the analysis, so a result cut to the room keeps it. Null when nothing was
	 * analysed.
	 */
	private DeepSearchCoverage coverage = null;
	/** Document fragments found and analysed. */
	private int fragmentsAnalysed = 0;
	/** Size of the returned analysis, in tokens. */
	private int tokens = 0;
	private String analysis = null;
	private List<Source> sources = new ArrayList<>();
	/**
	 * The sources that could not be searched, and why (not responding within the
	 * timeout, out of service, access refused, failed): what they hold is missing from
	 * the analysis. Null when every source was searched.
	 */
	private List<String> unavailableSources = null;
	/**
	 * The documents found that give nothing to the analysis, with why (not loaded, not
	 * read by the analysis, judged not relevant): never among its sources. Null when
	 * there is none.
	 */
	private List<DocumentNotRead> documentsNotRead = null;

	public static DeepSearchToolResult of(Status status, String message) {
		DeepSearchToolResult result = new DeepSearchToolResult();
		result.setStatus(status);
		result.setMessage(message);
		return result;
	}
}
