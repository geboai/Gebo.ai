/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.ollama.model;

import java.util.HashMap;
import java.util.Map;

import ai.gebo.llms.abstraction.layer.model.GBaseDecisionModelChoice;

/**
 * A decision model an Ollama server serves (a model with the "decision" capability,
 * such as the d1-omni family), with the details the server shows for it.
 */
public class GOllamaDecisionModelChoice extends GBaseDecisionModelChoice {

	private Map<String, Object> modelDetails = new HashMap<String, Object>();

	public GOllamaDecisionModelChoice() {
	}

	public Map<String, Object> getModelDetails() {
		return modelDetails;
	}

	public void setModelDetails(Map<String, Object> modelDetails) {
		this.modelDetails = modelDetails;
	}
}
