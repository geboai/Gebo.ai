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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionException;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.util.MimeTypeUtils;

import reactor.core.publisher.Flux;

/**
 * A file the provider refuses does not sink the answer: the call is made once more
 * without the files and the model is told which were left out. Found live: a broken
 * PDF a tool returned made OpenAI refuse the request and the whole answer came back empty.
 */
class RefusedFilesRetryingChatModelTest {
	/** Named as the provider clients name a 400 answer. */
	static class BadRequestException extends RuntimeException {
		BadRequestException(String message) {
			super(message);
		}
	}

	/** A provider refusing any prompt that carries a file, recording the prompts. */
	static class Refusing implements ChatModel {
		final List<Prompt> prompts = new ArrayList<>();
		RuntimeException failure = new BadRequestException("400: The file you uploaded is badly formatted or corrupted.");

		@Override
		public ChatResponse call(Prompt prompt) {
			prompts.add(prompt);
			if (RefusedFilesRetryingChatModel.carriesFiles(prompt)) {
				throw failure;
			}
			return answer("answered without the file");
		}

		@Override
		public Flux<ChatResponse> stream(Prompt prompt) {
			prompts.add(prompt);
			if (RefusedFilesRetryingChatModel.carriesFiles(prompt)) {
				return Flux.error(new CompletionException(failure));
			}
			return Flux.just(answer("streamed without the file"));
		}
	}

	static ChatResponse answer(String text) {
		return new ChatResponse(List.of(new Generation(AssistantMessage.builder().content(text).build())));
	}

	static Prompt withFile() {
		return new Prompt(List.of(UserMessage.builder().text("What does the report say?")
				.media(List.of(Media.builder().mimeType(MimeTypeUtils.parseMimeType("application/pdf"))
						.data(new byte[] { 1, 2, 3 }).name("heron-q3").build()))
				.build()));
	}

	@Test
	void aRefusedFileIsLeftOutAndTheModelIsToldWhich() {
		final Refusing provider = new Refusing();
		final ChatResponse response = RefusedFilesRetryingChatModel.wrap(provider).call(withFile());

		assertEquals("answered without the file", response.getResult().getOutput().getText());
		assertEquals(2, provider.prompts.size(), "once with the file, once without");
		final UserMessage retried = (UserMessage) provider.prompts.get(1).getInstructions().get(0);
		assertTrue(retried.getMedia().isEmpty());
		assertTrue(retried.getText().startsWith("What does the report say?"));
		assertTrue(retried.getText().contains(RefusedFilesRetryingChatModel.LEFT_OUT_NOTE + "heron-q3 (application/pdf)"),
				retried.getText());
	}

	@Test
	void withoutFilesOrForAnotherFailureNothingIsRetried() {
		final Refusing provider = new Refusing();
		provider.failure = new IllegalStateException("read timed out");
		assertThrows(IllegalStateException.class, () -> RefusedFilesRetryingChatModel.wrap(provider).call(withFile()));
		assertEquals(1, provider.prompts.size(), "not a refusal of the request: not made again");

		final Refusing plain = new Refusing();
		assertEquals("answered without the file",
				RefusedFilesRetryingChatModel.wrap(plain).call(new Prompt("hello")).getResult().getOutput().getText());
		assertEquals(1, plain.prompts.size());
	}

	@Test
	void aStreamRefusedAtOnceIsMadeAgainWithoutTheFiles() {
		final Refusing provider = new Refusing();
		final List<ChatResponse> streamed = RefusedFilesRetryingChatModel.wrap(provider).stream(withFile())
				.collectList().block();
		assertEquals(1, streamed.size());
		assertEquals("streamed without the file", streamed.get(0).getResult().getOutput().getText());
		assertTrue(((UserMessage) provider.prompts.get(1).getInstructions().get(0)).getMedia().isEmpty());
	}

	@Test
	void aStreamThatAlreadyAnsweredIsNotMadeAgain() {
		final ChatModel halfway = new ChatModel() {
			@Override
			public ChatResponse call(Prompt prompt) {
				throw new UnsupportedOperationException();
			}

			@Override
			public Flux<ChatResponse> stream(Prompt prompt) {
				return Flux.concat(Flux.just(answer("part")),
						Flux.error(new BadRequestException("400 after the first part")));
			}
		};
		final List<String> received = new ArrayList<>();
		assertThrows(RuntimeException.class, () -> RefusedFilesRetryingChatModel.wrap(halfway).stream(withFile())
				.doOnNext(r -> received.add(r.getResult().getOutput().getText())).blockLast());
		assertEquals(List.of("part"), received, "what was received is not repeated");
	}
}
