/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.search.controller;

import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import lombok.Data;

/**
 * Wire body for the {@code aggregate} search endpoint: carries the two extraction
 * results to consolidate. Generic so a concrete controller/client parameterizes it
 * with the connector's extraction data type.
 */
@Data
public class AggregateRequestBody<C extends BaseSearchResultsExtractionDataType> {
	private C oldConsolidated;
	private C consolidated;
}
