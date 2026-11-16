/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import org.springframework.ai.decision.DecisionModel;

import ai.gebo.llms.abstraction.layer.model.GBaseDecisionModelConfig;
import ai.gebo.llms.abstraction.layer.model.GDecisionModelType;

/**
 * A decision model configured at runtime from its persisted configuration: hands out
 * the Spring AI {@link DecisionModel} that answers yes/no, choice and score questions.
 *
 * @param <ModelConfig> the configuration type of the provider's decision model
 */
public interface IGConfigurableDecisionModel<ModelConfig extends GBaseDecisionModelConfig>
		extends IGConfigurableModel<ModelConfig, GDecisionModelType> {

	/** The Spring AI decision model, null until the configuration is initialized. */
	public DecisionModel getDecisionModel();
}
