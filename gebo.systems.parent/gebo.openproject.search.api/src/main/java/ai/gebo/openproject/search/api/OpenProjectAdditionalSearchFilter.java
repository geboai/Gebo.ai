/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.openproject.search.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

/**
 * Follow-up searches a model suggests while reading OpenProject results.
 */
@Data
@JsonClassDescription("Additional OpenProject searches related to the analyzed text")
public class OpenProjectAdditionalSearchFilter {
	@JsonPropertyDescription("OpenProject work package numbers referenced by the analyzed text (for example 1234 for #1234)")
	private List<Integer> workPackageIds = null;
	@JsonPropertyDescription("Plain text keywords or short phrases worth searching in other OpenProject work packages")
	private List<String> textTerms = null;
	@JsonPropertyDescription("ANY when any text term is enough, ALL when every text term must be present")
	private TextMatchMode textTermsMatchMode = null;
}
