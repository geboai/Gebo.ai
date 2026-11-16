/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.responses.OpenAiResponsesChatOptions;

import com.openai.core.JsonValue;
import com.openai.errors.BadRequestException;
import com.openai.models.ErrorObject;

import reactor.core.publisher.Flux;

/**
 * Pins the answer to an organization refused the reasoning summaries: the request again
 * without them, then never asking them; any other failure as it is; a stream asked again
 * only while it emitted nothing.
 */
class ReasoningSummariesFallbackChatModelTest {

	static final OpenAiResponsesChatOptions OPTIONS = OpenAiResponsesChatOptions.builder().model("gpt-6.1-sol")
			.reasoningSummary("auto").build();

	static BadRequestException badRequest(String message) {
		return BadRequestException.builder().headers(com.openai.core.http.Headers.builder().build())
				.error(ErrorObject.builder().message(message).type("invalid_request_error").code(JsonValue.from(null))
						.param(JsonValue.from(null)).build())
				.build();
	}

	static ChatResponse answer(String text) {
		return new ChatResponse(List.of(new Generation(new AssistantMessage(text))));
	}

	/** A model refusing the summaries as OpenAI does, recording the summary each request asked. */
	static final class Refusing implements ChatModel {
		final List<String> asked = new CopyOnWriteArrayList<>();
		final RuntimeException refusal;

		Refusing(RuntimeException refusal) {
			this.refusal = refusal;
		}

		String summaryOf(Prompt prompt) {
			return ((OpenAiResponsesChatOptions) prompt.getOptions()).getReasoningSummary();
		}

		@Override
		public ChatResponse call(Prompt prompt) {
			asked.add(String.valueOf(summaryOf(prompt)));
			if (summaryOf(prompt) != null) {
				throw refusal;
			}
			return answer("the answer");
		}

		@Override
		public Flux<ChatResponse> stream(Prompt prompt) {
			asked.add(String.valueOf(summaryOf(prompt)));
			if (summaryOf(prompt) != null) {
				return Flux.error(new CompletionException(refusal));
			}
			return Flux.just(answer("the "), answer("answer"));
		}

		@Override
		public ChatOptions getOptions() {
			return OPTIONS;
		}
	}

	@Test
	void aRefusedOrganizationIsAskedAgainWithoutSummariesThenNeverAskedThem() {
		Refusing openai = new Refusing(
				badRequest("Your organization must be verified to generate reasoning summaries."));
		ReasoningSummariesFallbackChatModel model = new ReasoningSummariesFallbackChatModel(openai);

		assertEquals("the answer", model.call(new Prompt("q", OPTIONS)).getResult().getOutput().getText());
		assertEquals("the answer", model.call(new Prompt("q", OPTIONS)).getResult().getOutput().getText());

		assertEquals(List.of("auto", "null", "null"), openai.asked, "once refused, never asked again");
		assertTrue(model.summariesRefused());
		assertSame(OPTIONS, model.getOptions(), "the ChatClient builds its requests on the model's options");
	}

	@Test
	void aStreamRefusedBeforeItsFirstEventIsAskedAgainWithoutSummaries() {
		Refusing openai = new Refusing(badRequest("reasoning.summary is not supported for this organization"));
		ReasoningSummariesFallbackChatModel model = new ReasoningSummariesFallbackChatModel(openai);

		List<ChatResponse> chunks = model.stream(new Prompt("q", OPTIONS)).collectList().block();

		assertEquals(2, chunks.size());
		assertEquals(List.of("auto", "null"), openai.asked, "the refusal travels wrapped by the async client");
	}

	@Test
	void anyOtherBadRequestIsTheCallersAndSummariesStayAsked() {
		Refusing openai = new Refusing(badRequest("Invalid value for 'temperature'"));
		ReasoningSummariesFallbackChatModel model = new ReasoningSummariesFallbackChatModel(openai);

		assertThrows(BadRequestException.class, () -> model.call(new Prompt("q", OPTIONS)));
		assertFalse(model.summariesRefused());
		assertEquals(List.of("auto"), openai.asked);
	}

	@Test
	void aStreamThatAlreadyAnsweredIsNotAskedAgain() {
		ChatModel failingMidway = new ChatModel() {
			@Override
			public ChatResponse call(Prompt prompt) {
				throw new UnsupportedOperationException();
			}

			@Override
			public Flux<ChatResponse> stream(Prompt prompt) {
				return Flux.concat(Flux.just(answer("half")),
						Flux.error(badRequest("reasoning summary refused")));
			}

			@Override
			public ChatOptions getOptions() {
				return OPTIONS;
			}
		};

		assertThrows(BadRequestException.class, () -> new ReasoningSummariesFallbackChatModel(failingMidway)
				.stream(new Prompt("q", OPTIONS)).collectList().block());
	}

	@Test
	void aPromptWithoutOptionsTakesTheModelsWithoutSummaries() {
		ReasoningSummariesFallbackChatModel model = new ReasoningSummariesFallbackChatModel(
				new Refusing(badRequest("summary")));

		Prompt without = model.withoutSummaries(new Prompt("q"));

		OpenAiResponsesChatOptions options = (OpenAiResponsesChatOptions) without.getOptions();
		assertNull(options.getReasoningSummary());
		assertEquals("gpt-6.1-sol", options.getModel(), "the rest of the model's options kept");
	}
}
