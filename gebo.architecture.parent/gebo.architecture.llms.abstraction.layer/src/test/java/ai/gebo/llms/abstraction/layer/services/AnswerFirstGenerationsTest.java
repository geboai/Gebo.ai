/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;

/**
 * A blocking call reads its answer from the first generation; a provider returning its
 * reasoning as generations ahead of the answer (Spring AI's Anthropic model) has the
 * answer put first, so content(), entity() and call(String) read the answer.
 */
class AnswerFirstGenerationsTest {

	private static final Predicate<Generation> REASONING = g -> g.getOutput().getMetadata().containsKey("signature");

	private static Generation thinking(String text) {
		return new Generation(AssistantMessage.builder().content(text).properties(Map.of("signature", "sig")).build());
	}

	private static Generation answer(String text) {
		return new Generation(AssistantMessage.builder().content(text).properties(Map.of()).build());
	}

	private static List<String> texts(ChatResponse response) {
		return response.getResults().stream().map(g -> g.getOutput().getText()).toList();
	}

	/** The shape of a Claude call with thinking: thinking blocks first, the answer last */
	private static ChatModel thinkingFirst(String answerText) {
		return prompt -> new ChatResponse(List.of(thinking("let me think"), thinking("more"), answer(answerText)));
	}

	@Test
	void theAnswerGoesFirstTheReasoningKeepsItsOrder() {
		ChatResponse response = new ChatResponse(List.of(thinking("t1"), thinking("t2"), answer("the answer")));

		ChatResponse reordered = AnswerFirstGenerations.answerFirst(response, REASONING);

		assertEquals(List.of("the answer", "t1", "t2"), texts(reordered));
		assertEquals("the answer", reordered.getResult().getOutput().getText());
	}

	@Test
	void aResponseAlreadyAnswerFirstOrWithoutAnAnswerIsLeftAsItIs() {
		ChatResponse answerFirst = new ChatResponse(List.of(answer("the answer"), thinking("t1")));
		ChatResponse onlyReasoning = new ChatResponse(List.of(thinking("t1"), thinking("t2")));
		ChatResponse single = new ChatResponse(List.of(thinking("t1")));

		assertSame(answerFirst, AnswerFirstGenerations.answerFirst(answerFirst, REASONING));
		assertSame(onlyReasoning, AnswerFirstGenerations.answerFirst(onlyReasoning, REASONING));
		assertSame(single, AnswerFirstGenerations.answerFirst(single, REASONING));
	}

	@Test
	void aProviderWithoutReasoningGenerationsIsLeftAsItIs() {
		ChatResponse response = new ChatResponse(List.of(thinking("t1"), answer("the answer")));

		assertSame(response, AnswerFirstGenerations.answerFirst(response, g -> false));
	}

	@Test
	void theChatClientContentIsTheAnswer() {
		ChatModel model = thinkingFirst("the answer");

		assertEquals("let me think", ChatClient.builder(model).build().prompt("q").call().content(),
				"without the advisor the call reads the first thinking block");
		assertEquals("the answer", ChatClient.builder(model)
				.defaultAdvisors(new AnswerFirstGenerations.Advisor(REASONING)).build().prompt("q").call().content());
	}

	record Fields(String name, int count) {
	}

	@Test
	void theChatClientEntityIsParsedFromTheAnswer() {
		ChatModel model = thinkingFirst("{\"name\":\"rewritten query\",\"count\":2}");

		Fields fields = ChatClient.builder(model).defaultAdvisors(new AnswerFirstGenerations.Advisor(REASONING))
				.build().prompt("q").call().entity(Fields.class);

		assertEquals(new Fields("rewritten query", 2), fields);
	}

	@Test
	void theRawModelCallReadsTheAnswerAndStreamsAreForwarded() {
		ChatModel model = new AnswerFirstGenerations.Model(thinkingFirst("the answer"), REASONING);

		assertEquals("the answer", model.call("q"));
		assertEquals(List.of("the answer", "let me think", "more"), texts(model.call(new Prompt("q"))));
	}
}
