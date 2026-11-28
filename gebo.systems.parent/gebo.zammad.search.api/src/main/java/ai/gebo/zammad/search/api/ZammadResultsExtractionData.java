/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.zammad.search.api;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import lombok.Data;

/**
 * What a model extracts from one Zammad search result, with the follow-up
 * searches it suggests.
 */
@Data
public class ZammadResultsExtractionData extends BaseSearchResultsExtractionDataType {
	@JsonPropertyDescription("Fill this data structure to suggest additional searches enriching the actual Zammad tickets and answers analysis")
	private ZammadAdditionalSearchFilter additionalZammadSearchIdeas = null;
}
