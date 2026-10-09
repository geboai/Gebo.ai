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

import ai.gebo.architecture.search.service.INativeQueryObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A search expressed in the searched system's own query structure, with the
 * objective its results must serve. The JSON schema of {@link #query} is the one
 * of the native query type, composed at tool declaration time.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NativeSearchParam<NativeQueryObject extends INativeQueryObject> extends AbstractSearchToolParam {
	public static final String QUERY_DESCRIPTION = "The search, in the searched system's own query structure.";

	private NativeQueryObject query = null;

	@Override
	public String queryText() {
		if (query == null) {
			return null;
		}
		List<String> keywords = query.relevantKeywords();
		return keywords != null && !keywords.isEmpty() ? String.join(" ", keywords) : String.valueOf(query);
	}
}
