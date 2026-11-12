/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai_compat.modeltypes;

import ai.gebo.llms.abstraction.layer.model.GDecisionModelType;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIDecisionModelConfig;
import lombok.Data;

/**
 * A decision model provider declared in providers.yml (decisionModelProviders): an
 * OpenAI compatible server exposing a Decisions API at its base url.
 */
@Data
public class GenericOpenAIDecisionModelTypeConfig extends GDecisionModelType {
	private String baseUrl = null;
	private String modelsListProvider = null;
	// providerId is inherited from GModelType and bound from providers.yml.
	private boolean optionalAuthentication = false;
	/** The model offered when the provider lists none, or none at all when null. */
	private String defaultModel = null;

	public GenericOpenAIDecisionModelTypeConfig() {
		setModelConfigurationClass(GenericOpenAIAPIDecisionModelConfig.class.getName());
	}
}
