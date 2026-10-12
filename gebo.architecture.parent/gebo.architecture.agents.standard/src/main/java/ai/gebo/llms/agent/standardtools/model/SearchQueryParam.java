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

import com.fasterxml.jackson.annotation.JsonClassDescription;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/** A plain text search, with the objective its results must serve. */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonClassDescription("A search and the objective its results must serve")
public class SearchQueryParam extends AbstractSearchToolParam {
	public static final String QUERY_DESCRIPTION = "What to type into the search engine: keywords or a short phrase.";

	@ToolParam(required = true, description = QUERY_DESCRIPTION)
	private String query = null;

	@Override
	public String queryText() {
		return query;
	}
}
