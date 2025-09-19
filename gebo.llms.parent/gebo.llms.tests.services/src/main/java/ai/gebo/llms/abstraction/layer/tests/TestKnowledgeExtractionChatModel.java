/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.tests;

import java.util.List;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;

import ai.gebo.architecture.testing.AbstractTestingBusinessLogic;

/**
 * AI generated comments
 * A test implementation of the ChatModel interface used for testing LLM interactions.
 * This class allows for configuring test responses without requiring a real LLM connection.
 */
public class TestKnowledgeExtractionChatModel extends AbstractTestingBusinessLogic implements ChatModel {
	// Configuration object that contains the test response logic
	TestKnowledgeExtractionModelConfiguration configuration = null;

	/**
	 * Default constructor for TestChatModel
	 */
	public TestKnowledgeExtractionChatModel() {

	}

	/**
	 * Processes a prompt and returns a chat response based on configured test logic.
	 * If no configuration is provided, returns an empty response.
	 * 
	 * @param prompt The input prompt to process
	 * @return A ChatResponse containing the generated response
	 */
	@Override
	public ChatResponse call(Prompt prompt) {
		String content = prompt.getContents();
		String response = configuration != null && configuration.getTestResponseLogic() != null
				? configuration.getTestResponseLogic().apply(content)
				: "";
		AssistantMessage am = new AssistantMessage(response);
		Generation generation = new Generation(am);
		return new ChatResponse(List.of(generation));
	}
	
	/**
	 * Gets the current configuration for this test model
	 * 
	 * @return The current TestChatModelConfiguration
	 */
	public TestKnowledgeExtractionModelConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * Sets the configuration for this test model
	 * 
	 * @param configuration The TestChatModelConfiguration to use
	 */
	public void setConfiguration(TestKnowledgeExtractionModelConfiguration configuration) {
		this.configuration = configuration;
	}

}