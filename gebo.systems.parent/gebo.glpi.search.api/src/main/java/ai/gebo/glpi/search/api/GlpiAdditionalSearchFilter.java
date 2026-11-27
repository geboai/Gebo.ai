/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.glpi.search.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.glpi.search.api.GlpiSearchFilter.TextMatchMode;
import lombok.Data;

@Data
@JsonClassDescription("Additional GLPI search related to the actual analyzed text")
public class GlpiAdditionalSearchFilter {
	@JsonPropertyDescription("Numeric ids of GLPI tickets mentioned or related to the analyzed text")
	List<Long> ticketIds = null;
	@JsonPropertyDescription("Keywords or short phrases to search in the title of GLPI tickets or knowledge base articles")
	List<String> titleTerms = null;
	TextMatchMode titleTermsMatchMode = null;
	@JsonPropertyDescription("Keywords or short phrases to search in the description of GLPI tickets or in the body of knowledge base articles")
	List<String> contentTerms = null;
	TextMatchMode contentTermsMatchMode = null;
}
