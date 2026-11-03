/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.toolcalling;

import java.util.List;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.ToolCallingAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.model.tool.ToolExecutionResult;

import reactor.core.publisher.Flux;

/**
 * The tool calling loop of a model call made with a {@link GeboToolCalling}: Spring
 * AI's own loop, at its own place in the advisor chain, run by the manager the
 * {@link GeboToolCalling} builds (its limits, the observation registry) and telling it
 * how the call goes - the rounds, the turn's messages, whether a tool ended it. Passed
 * as the call's advisor it takes the place of the one the chat client registers by
 * default (a chat client allows one tool advisor, and registers its own only when the
 * call brings none).
 */
public class GeboToolCallingAdvisor extends ToolCallingAdvisor {
	private final GeboToolCalling toolCalling;

	GeboToolCallingAdvisor(GeboToolCalling toolCalling, ToolCallingManager toolCallingManager) {
		super(toolCallingManager, DEFAULT_TOOL_EXECUTION_ELIGIBILITY_CHECKER, DEFAULT_ORDER, true);
		this.toolCalling = toolCalling;
	}

	public GeboToolCalling getToolCalling() {
		return toolCalling;
	}

	@Override
	public String getName() {
		return "Gebo Tool Calling Advisor";
	}

	// ---- blocking calls

	@Override
	protected ChatClientRequest doInitializeLoop(ChatClientRequest chatClientRequest,
			CallAdvisorChain callAdvisorChain) {
		toolCalling.callStarted();
		return chatClientRequest;
	}

	@Override
	protected ChatClientRequest doBeforeCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
		toolCalling.roundStarted();
		return chatClientRequest;
	}

	@Override
	protected ChatClientResponse doAfterCall(ChatClientResponse chatClientResponse,
			CallAdvisorChain callAdvisorChain) {
		toolCalling.roundAnswered(chatClientResponse != null ? chatClientResponse.chatResponse() : null);
		return chatClientResponse;
	}

	@Override
	protected List<Message> doGetNextInstructionsForToolCall(ChatClientRequest chatClientRequest,
			ChatClientResponse chatClientResponse, ToolExecutionResult toolExecutionResult) {
		toolCalling.turnContinued(toolExecutionResult);
		return super.doGetNextInstructionsForToolCall(chatClientRequest, chatClientResponse, toolExecutionResult);
	}

	@Override
	protected ChatClientResponse doFinalizeLoop(ChatClientResponse chatClientResponse,
			CallAdvisorChain callAdvisorChain) {
		toolCalling.callEnded(chatClientResponse != null ? chatClientResponse.chatResponse() : null);
		return chatClientResponse;
	}

	// ---- streaming calls

	@Override
	protected ChatClientRequest doInitializeLoopStream(ChatClientRequest chatClientRequest,
			StreamAdvisorChain streamAdvisorChain) {
		toolCalling.callStarted();
		return chatClientRequest;
	}

	@Override
	protected ChatClientRequest doBeforeStream(ChatClientRequest chatClientRequest,
			StreamAdvisorChain streamAdvisorChain) {
		toolCalling.roundStarted();
		return chatClientRequest;
	}

	@Override
	protected ChatClientResponse doAfterStream(ChatClientResponse chatClientResponse,
			StreamAdvisorChain streamAdvisorChain) {
		toolCalling.roundAnswered(chatClientResponse != null ? chatClientResponse.chatResponse() : null);
		return chatClientResponse;
	}

	@Override
	protected List<Message> doGetNextInstructionsForToolCallStream(ChatClientRequest chatClientRequest,
			ChatClientResponse chatClientResponse, ToolExecutionResult toolExecutionResult) {
		toolCalling.turnContinued(toolExecutionResult);
		return super.doGetNextInstructionsForToolCallStream(chatClientRequest, chatClientResponse,
				toolExecutionResult);
	}

	@Override
	protected Flux<ChatClientResponse> doFinalizeLoopStream(Flux<ChatClientResponse> chatClientResponseFlux,
			StreamAdvisorChain streamAdvisorChain) {
		// the chunk a tool ended the call with carries the finish reason; the others
		// leave the record as it is
		return chatClientResponseFlux
				.doOnNext(response -> toolCalling.callEnded(response != null ? response.chatResponse() : null));
	}
}
