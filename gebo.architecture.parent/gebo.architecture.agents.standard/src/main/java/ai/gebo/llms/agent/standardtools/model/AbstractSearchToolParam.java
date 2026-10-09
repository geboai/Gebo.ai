/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools.model;

import org.springframework.ai.tool.annotation.ToolParam;

import lombok.Data;

/**
 * The parameters every search tool shares, whatever the shape of its query: the
 * objective the results must serve - used to rank the contents found and to
 * throw away the ones that do not serve it - and the size of the answer.
 */
@Data
public abstract class AbstractSearchToolParam {
	public static final String SEARCH_OBJECTIVE_DESCRIPTION = "In 1-3 sentences, what information you need and what you "
			+ "need it for. The contents found are ranked against this objective and the ones that do not serve it are "
			+ "discarded, so state it precisely.";
	public static final String TOP_K_DESCRIPTION = "Maximum number of documents to return, with their passages found, "
			+ "8 when not given, at most 30.";

	@ToolParam(required = true, description = SEARCH_OBJECTIVE_DESCRIPTION)
	private String searchObjective = null;
	@ToolParam(required = false, description = TOP_K_DESCRIPTION)
	private Integer topK = null;

	/**
	 * The query as plain text, used when the model gave no objective and for the
	 * logs and the called functions report.
	 */
	public abstract String queryText();
}
