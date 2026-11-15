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
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;

import ai.gebo.architecture.testing.AbstractTestingBusinessLogic;
import reactor.core.publisher.Flux;

/**
 * AI generated comments
 * A test implementation of the ChatModel interface used for testing LLM interactions.
 * This class allows for configuring test responses without requiring a real LLM connection.
 */
public class TestChatModel extends AbstractTestingBusinessLogic implements ChatModel {
	// Configuration object that contains the test response logic
	TestChatModelConfiguration configuration = null;

	/**
	 * Test-global response logic that, when set, takes precedence over the
	 * per-configuration logic. This is needed because agent models are obtained via
	 * {@code cloneWithOptions(...)}, which JSON round-trips the configuration and
	 * therefore drops any {@link Function} held on it. A static hook survives
	 * cloning, so tests that exercise agent networks can program responses here. Set
	 * it in test setup and clear it in teardown.
	 */
	private static volatile Function<String, String> globalResponseLogic = null;

	/**
	 * Sets the test-global response logic used by every {@link TestChatModel}
	 * instance (including clones produced for agents).
	 */
	public static void setGlobalResponseLogic(Function<String, String> logic) {
		globalResponseLogic = logic;
	}

	/**
	 * Clears the test-global response logic.
	 */
	public static void clearGlobalResponseLogic() {
		globalResponseLogic = null;
	}

	/**
	 * Test-global observer of every prompt this model is called with, clones
	 * included: a test reads from the prompt's options what reached the model (the
	 * tools declared, the tool context and what the controlling software loaded in
	 * it). Set it in test setup and clear it in teardown.
	 */
	private static volatile Consumer<Prompt> globalPromptObserver = null;

	/** Sets the test-global prompt observer. */
	public static void setGlobalPromptObserver(Consumer<Prompt> observer) {
		globalPromptObserver = observer;
	}

	/** Clears the test-global prompt observer. */
	public static void clearGlobalPromptObserver() {
		globalPromptObserver = null;
	}

	/**
	 * Test-global tool calls: when it gives tool calls for a prompt's contents, the
	 * model answers with those calls instead of text, as a real model asking for its
	 * tools does; the tool calling advisor of the platform runs them. It is asked
	 * before the response logic; an empty or null list leaves the answer to it. Set it
	 * in test setup and clear it in teardown.
	 */
	private static volatile Function<String, List<AssistantMessage.ToolCall>> globalToolCallLogic = null;

	/** Sets the test-global tool calls. */
	public static void setGlobalToolCallLogic(Function<String, List<AssistantMessage.ToolCall>> logic) {
		globalToolCallLogic = logic;
	}

	/** Clears the test-global tool calls. */
	public static void clearGlobalToolCallLogic() {
		globalToolCallLogic = null;
	}

	/**
	 * Default constructor for TestChatModel
	 */
	public TestChatModel() {

	}

	/**
	 * Tool calling options, as every provider's model has: the chat client copies the
	 * tools declared and the tool context into a prompt only when the model's options
	 * take them, so a test can see what the platform loaded for the tools of a call.
	 */
	@Override
	public ChatOptions getOptions() {
		return ToolCallingChatOptions.builder().build();
	}

	/**
	 * Processes a prompt and returns a chat response based on configured test logic.
	 * The test-global logic (if set) takes precedence over the per-configuration
	 * logic; if neither is present, an empty response is returned.
	 *
	 * @param prompt The input prompt to process
	 * @return A ChatResponse containing the generated response
	 */
	@Override
	public ChatResponse call(Prompt prompt) {
		final Consumer<Prompt> observer = globalPromptObserver;
		if (observer != null) {
			observer.accept(prompt);
		}
		String content = prompt.getContents();
		final Function<String, List<AssistantMessage.ToolCall>> toolCallLogic = globalToolCallLogic;
		final List<AssistantMessage.ToolCall> toolCalls = toolCallLogic != null ? toolCallLogic.apply(content) : null;
		if (toolCalls != null && !toolCalls.isEmpty()) {
			return new ChatResponse(
					List.of(new Generation(AssistantMessage.builder().content("").toolCalls(toolCalls).build())));
		}
		Function<String, String> global = globalResponseLogic;
		String response;
		if (global != null) {
			response = global.apply(content);
		} else if (configuration != null && configuration.getTestResponseLogic() != null) {
			response = configuration.getTestResponseLogic().apply(content);
		} else {
			response = "";
		}
		AssistantMessage am = new AssistantMessage(response != null ? response : "");
		Generation generation = new Generation(am);
		return new ChatResponse(List.of(generation));
	}

	/**
	 * Streaming variant required by reactive agents (e.g. the report-writer node):
	 * emits the single non-streaming response as a one-element flux. Spring AI's
	 * default {@code stream} does not produce content for this test model.
	 *
	 * @param prompt The input prompt to process
	 * @return a one-element flux wrapping {@link #call(Prompt)}
	 */
	@Override
	public Flux<ChatResponse> stream(Prompt prompt) {
		return Flux.just(call(prompt));
	}

	/**
	 * Gets the current configuration for this test model
	 * 
	 * @return The current TestChatModelConfiguration
	 */
	public TestChatModelConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * Sets the configuration for this test model
	 * 
	 * @param configuration The TestChatModelConfiguration to use
	 */
	public void setConfiguration(TestChatModelConfiguration configuration) {
		this.configuration = configuration;
	}

}