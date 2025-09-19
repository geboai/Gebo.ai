/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.abstraction.layer.tests;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import lombok.Data;

/**
 * AI generated comments
 * 
 * A test implementation of GBaseChatModelConfig used for testing purposes. This
 * class allows configuring test response logic to simulate chat model behavior
 * in test environments without connecting to actual LLM services.
 */
@Data
public class TestKnowledgeExtractionModelConfiguration extends GBaseChatModelConfig {
	
	Function<String, String> testResponseLogic = (s) -> s;
	public Map<Class, Object> responseObjects = new HashMap<Class, Object>();


}