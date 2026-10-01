/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools.model;

import java.util.List;

import org.springframework.ai.tool.annotation.ToolParam;

import com.fasterxml.jackson.annotation.JsonClassDescription;

import lombok.Data;

/**
 * A deep search: the searches to run, and the question the many documents they find
 * are analysed against.
 */
@Data
@JsonClassDescription("A deep search: the searches to run and the question their documents are analysed against")
public class DeepSearchToolParam {
	/** How thorough the analysis must be, i.e. how many satisfying partial analyses end it. */
	public enum Depth {
		FOCUSED, BROAD, EXHAUSTIVE
	}

	public static final String QUERIES_DESCRIPTION = "1 to 5 searches to run, each one keywords or a short phrase "
			+ "covering a different angle of the question: every document they find is read.";
	public static final String QUESTION_DESCRIPTION = "The question the documents found must answer, complete and "
			+ "self-contained: every document found is analysed against it.";
	public static final String SEARCH_OBJECTIVE_DESCRIPTION = "In 1-3 sentences, what the analysis is for and what it "
			+ "must cover (aspects, comparisons, figures, time span), so that nothing needed is left out.";
	public static final String DEPTH_DESCRIPTION = "How thorough the analysis must be: FOCUSED for a precise answer, "
			+ "BROAD (when not given) for a synthesis, EXHAUSTIVE for a detailed report, a decision or a comparison.";

	@ToolParam(required = true, description = QUERIES_DESCRIPTION)
	private List<String> queries = null;
	@ToolParam(required = true, description = QUESTION_DESCRIPTION)
	private String question = null;
	@ToolParam(required = false, description = SEARCH_OBJECTIVE_DESCRIPTION)
	private String searchObjective = null;
	@ToolParam(required = false, description = DEPTH_DESCRIPTION)
	private Depth depth = null;
}
