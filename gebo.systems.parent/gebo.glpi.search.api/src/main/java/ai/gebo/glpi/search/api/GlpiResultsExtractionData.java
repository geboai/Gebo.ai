/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.glpi.search.api;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GlpiResultsExtractionData extends BaseSearchResultsExtractionDataType {
	@JsonPropertyDescription("Fill this data structure to suggest additional searches enriching the actual GLPI tickets and articles analysis")
	private GlpiAdditionalSearchFilter additionalGlpiSearchIdeas = null;
}
