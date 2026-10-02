/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import java.util.List;
import java.util.function.UnaryOperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.advisor.ToolCallingAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.model.tool.ToolCallingManager;

import io.micrometer.observation.ObservationRegistry;

/**
 * The tool calling loop of a chat client call whose first model round must call a
 * tool: that round is sent with the vendor's "call a tool" choice, every next round
 * (the model reading the tools' results) with the call's own choice, so the model is
 * free to answer. A choice kept on every round would never let it answer.
 * <p>
 * It replaces the loop the chat client registers by itself (one tool loop only is
 * allowed) and is built as that one is: the same tool calling manager, order and
 * conversation history.
 */
class FirstRoundToolChoiceAdvisor extends ToolCallingAdvisor {
	private static final Logger LOGGER = LoggerFactory.getLogger(FirstRoundToolChoiceAdvisor.class);

	/** The options asking the vendor to call a tool, null when it cannot be asked. */
	private final UnaryOperator<ToolCallingChatOptions> requiringToolCall;
	private final String modelCode;

	FirstRoundToolChoiceAdvisor(UnaryOperator<ToolCallingChatOptions> requiringToolCall, String modelCode) {
		super(ToolCallingManager.builder().observationRegistry(ObservationRegistry.NOOP).build(),
				DEFAULT_TOOL_EXECUTION_ELIGIBILITY_CHECKER, DEFAULT_ORDER, true);
		this.requiringToolCall = requiringToolCall;
		this.modelCode = modelCode;
	}

	@Override
	protected ChatClientRequest doBeforeCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
		return requireToolOnFirstRound(chatClientRequest);
	}

	@Override
	protected ChatClientRequest doBeforeStream(ChatClientRequest chatClientRequest,
			StreamAdvisorChain streamAdvisorChain) {
		return requireToolOnFirstRound(chatClientRequest);
	}

	/** The first round is the one whose messages carry no tool result yet. */
	static boolean isFirstRound(List<Message> instructions) {
		return instructions.stream().noneMatch(ToolResponseMessage.class::isInstance);
	}

	ChatClientRequest requireToolOnFirstRound(ChatClientRequest request) {
		final List<Message> instructions = request.prompt().getInstructions();
		if (!isFirstRound(instructions)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Model:" + modelCode + " reads the tools' results, free to answer");
			}
			return request;
		}
		if (!(request.prompt().getOptions() instanceof ToolCallingChatOptions options)
				|| options.getToolCallbacks() == null || options.getToolCallbacks().isEmpty()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Model:" + modelCode + " has no tool declared, no tool call can be required");
			}
			return request;
		}
		final ToolCallingChatOptions requiring = requiringToolCall.apply(options);
		if (requiring == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Model:" + modelCode + " cannot be asked to call a tool, its first round is free");
			}
			return request;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Model:" + modelCode + " must call one of its " + options.getToolCallbacks().size()
					+ " tool(s) on its first round");
		}
		return request.mutate().prompt(new Prompt(instructions, requiring)).build();
	}
}
