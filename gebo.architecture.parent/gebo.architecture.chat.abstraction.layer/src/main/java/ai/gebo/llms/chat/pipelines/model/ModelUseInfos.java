/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Whether a pipeline step uses one of its two models and what for: it tells the
 * admin configuring the step's tools which selectors apply and what they affect.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelUseInfos {
	private boolean modelUsed = false;
	private String modelUseDescription = null;

	/** The step does not use this model. */
	public static ModelUseInfos notUsed() {
		return new ModelUseInfos(false, null);
	}

	/** The step uses this model, for what is described. */
	public static ModelUseInfos usedFor(String description) {
		return new ModelUseInfos(true, description);
	}
}
