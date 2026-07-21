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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;

import ai.gebo.architecture.graphrag.extraction.model.LLMExtractionResult;
import ai.gebo.architecture.testing.AbstractTestingBusinessLogic;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

/**
 * AI generated comments
 * A test implementation of the ChatModel interface used for testing LLM interactions.
 * This class allows for configuring test responses without requiring a real LLM connection.
 */
public class TestKnowledgeExtractionChatModel extends AbstractTestingBusinessLogic implements ChatModel {
	// Configuration object that contains the test response logic
	TestKnowledgeExtractionModelConfiguration configuration = null;

	private static final Logger LOGGER = LoggerFactory.getLogger(TestKnowledgeExtractionChatModel.class);

	private static final ObjectMapper JSON = new ObjectMapper();

	/**
	 * Default constructor for TestChatModel
	 */
	public TestKnowledgeExtractionChatModel() {

	}

	/**
	 * Processes a prompt and returns a chat response based on configured test logic.
	 *
	 * <p>
	 * When the graphrag JSON extraction path is exercised, Spring AI's
	 * {@code DefaultChatClient$DefaultCallResponseSpec.entity(Class<T>)} drives the
	 * call through {@link #call(Prompt)} and then deserializes the assistant content
	 * with {@code BeanOutputConverter<LLMExtractionResult>}. To keep that real
	 * structured-output path working under the fake LLM, this method first consults
	 * the configuration's {@code responseObjects} / {@code responseCallbacks} for a
	 * canned {@link LLMExtractionResult} and, when present, serializes it to JSON so
	 * the converter can parse it. Only when no canned extraction result is wired does
	 * it fall back to the plain {@code testResponseLogic}.
	 * </p>
	 *
	 * @param prompt The input prompt to process
	 * @return A ChatResponse containing the generated response
	 */
	@Override
	public ChatResponse call(Prompt prompt) {
		String content = prompt.getContents();
		String response = produceExtractionJsonResponse(prompt);
		if (response == null) {
			response = configuration != null && configuration.getTestResponseLogic() != null
					? configuration.getTestResponseLogic().apply(content)
					: "";
		}
		AssistantMessage am = new AssistantMessage(response != null ? response : "");
		Generation generation = new Generation(am);
		return new ChatResponse(List.of(generation));
	}

	/**
	 * Resolves a canned {@link LLMExtractionResult} from the configuration (either a
	 * static object in {@code responseObjects} or produced by a
	 * {@code responseCallbacks} entry fed with the prompt's messages) and serializes
	 * it to JSON for the structured-output converter. Returns {@code null} when no
	 * extraction result is wired, so the caller can fall back to the plain test
	 * response logic.
	 */
	private String produceExtractionJsonResponse(Prompt prompt) {
		if (configuration == null) {
			return null;
		}
		Object canned = configuration.getResponseObjects().get(LLMExtractionResult.class);
		if (!(canned instanceof LLMExtractionResult) && configuration.getResponseCallbacks()
				.containsKey(LLMExtractionResult.class)) {
			KnowledgeExtractionCallEvent event = new KnowledgeExtractionCallEvent(prompt, prompt.getInstructions(),
					List.of());
			canned = configuration.getResponseCallbacks().get(LLMExtractionResult.class).apply(event);
		}
		if (canned instanceof LLMExtractionResult result) {
			try {
				return JSON.writeValueAsString(result);
			} catch (JacksonException e) {
				LOGGER.error("Failed to serialize canned LLMExtractionResult to JSON", e);
				return "";
			}
		}
		return null;
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