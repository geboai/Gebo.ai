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

/**
 * AI generated comments
 * 
 * A test implementation of GBaseChatModelConfig used for testing purposes.
 * This class allows configuring test response logic to simulate chat model behavior
 * in test environments without connecting to actual LLM services.
 */
public class TestChatModelConfiguration extends GBaseChatModelConfig {
	/** Function that defines how test responses are generated from input strings */
	Function<String, String> testResponseLogic = (s) -> s;

	/**
	 * Default constructor that initializes a test chat model configuration
	 * with default response logic (identity function).
	 */
	public TestChatModelConfiguration() {

	}

	/**
	 * Returns the current test response logic function.
	 * 
	 * @return the function that transforms input strings to response strings
	 */
	public Function<String, String> getTestResponseLogic() {
		return testResponseLogic;
	}

	/**
	 * Sets the test response logic to be used for generating responses.
	 * 
	 * @param responseLogic the function to transform input strings to response strings
	 */
	public void setTestResponseLogic(Function<String, String> responseLogic) {
		this.testResponseLogic = responseLogic;
	}

}