/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GBaseDecisionModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseDecisionModelConfig;
import ai.gebo.llms.abstraction.layer.model.GDecisionModelType;

/**
 * A provider's support of its decision models: the type it declares, the models it
 * offers, and the configurable model it creates from a configuration. One
 * implementation per provider, found by the type code the configurations carry.
 *
 * @param <ModelChoice> the provider's model choice type
 * @param <ModelConfig> the provider's configuration type
 */
public interface IGDecisionModelConfigurationSupportService<ModelChoice extends GBaseDecisionModelChoice, ModelConfig extends GBaseDecisionModelConfig>
		extends
		IGModelConfigurationSupportService<GDecisionModelType, ModelChoice, ModelConfig, IGConfigurableDecisionModel> {

	@Override
	IGConfigurableDecisionModel create(ModelConfig config) throws LLMConfigException;
}
