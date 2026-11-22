/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;

import reactor.core.publisher.Flux;

/**
 * A provider's chat model that does not let a file sink the answer. When a call whose
 * prompt carries files - the files of a request, the files a tool showed - is refused by
 * the provider as a bad request (a file it cannot read, too large, of a type it does not
 * take), the call is made once more without the files, and the model is told which
 * files were left out. A prompt without files, and any other failure, are as before; a
 * streamed answer is retried only when nothing of it was received yet.
 */
public final class RefusedFilesRetryingChatModel implements ChatModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(RefusedFilesRetryingChatModel.class);
	/** The note the model reads in place of the files left out. */
	static final String LEFT_OUT_NOTE = "These files could not be read by the model provider and were left out: ";
	private final ChatModel delegate;

	private RefusedFilesRetryingChatModel(ChatModel delegate) {
		this.delegate = delegate;
	}

	/** The model wrapped, or the model itself when null or already wrapped. */
	public static ChatModel wrap(ChatModel model) {
		return model == null || model instanceof RefusedFilesRetryingChatModel ? model
				: new RefusedFilesRetryingChatModel(model);
	}

	@Override
	public ChatResponse call(Prompt prompt) {
		try {
			return delegate.call(prompt);
		} catch (RuntimeException failure) {
			if (!carriesFiles(prompt) || !refusedAsBadRequest(failure)) {
				throw failure;
			}
			LOGGER.warn("The model provider refused a request carrying files (" + causeOf(failure)
					+ "): it is made again without " + namesOf(prompt));
			return delegate.call(withoutFiles(prompt));
		}
	}

	@Override
	public Flux<ChatResponse> stream(Prompt prompt) {
		if (!carriesFiles(prompt)) {
			return delegate.stream(prompt);
		}
		final AtomicBoolean received = new AtomicBoolean(false);
		return Flux.defer(() -> delegate.stream(prompt)).doOnNext(x -> received.set(true)).onErrorResume(failure -> {
			if (received.get() || !refusedAsBadRequest(failure)) {
				return Flux.error(failure);
			}
			LOGGER.warn("The model provider refused a streamed request carrying files (" + causeOf(failure)
					+ "): it is made again without " + namesOf(prompt));
			return delegate.stream(withoutFiles(prompt));
		});
	}

	@Override
	public ChatOptions getDefaultOptions() {
		return delegate.getDefaultOptions();
	}

	@Override
	public ChatOptions getOptions() {
		return delegate.getOptions();
	}

	static boolean carriesFiles(Prompt prompt) {
		if (prompt == null || prompt.getInstructions() == null) {
			return false;
		}
		for (Message message : prompt.getInstructions()) {
			if (message instanceof UserMessage user && user.getMedia() != null && !user.getMedia().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Whether the provider refused the request as a bad one: a 400, 413, 415 or 422
	 * answer, whatever the provider's client calls it. A request refused for another
	 * reason fails again without the files, after one more call.
	 */
	static boolean refusedAsBadRequest(Throwable failure) {
		for (Throwable t = failure; t != null; t = t.getCause() == t ? null : t.getCause()) {
			final String name = t.getClass().getSimpleName().toLowerCase(Locale.ROOT);
			if (name.contains("badrequest") || name.contains("unprocessable") || name.contains("payloadtoolarge")
					|| name.contains("unsupportedmediatype")) {
				return true;
			}
			if (t instanceof org.springframework.web.client.HttpClientErrorException http) {
				final int status = http.getStatusCode().value();
				if (status == 400 || status == 413 || status == 415 || status == 422) {
					return true;
				}
			}
			if (t instanceof org.springframework.web.reactive.function.client.WebClientResponseException http) {
				final int status = http.getStatusCode().value();
				if (status == 400 || status == 413 || status == 415 || status == 422) {
					return true;
				}
			}
		}
		return false;
	}

	/** The prompt with no file, the user messages that carried some telling which. */
	static Prompt withoutFiles(Prompt prompt) {
		final List<Message> messages = new ArrayList<>();
		for (Message message : prompt.getInstructions()) {
			if (message instanceof UserMessage user && user.getMedia() != null && !user.getMedia().isEmpty()) {
				final String text = user.getText() != null ? user.getText() : "";
				messages.add(UserMessage.builder().text(text + "\n\n" + LEFT_OUT_NOTE + names(user.getMedia()) + ".")
						.metadata(user.getMetadata()).build());
			} else {
				messages.add(message);
			}
		}
		return new Prompt(messages, prompt.getOptions());
	}

	private static String namesOf(Prompt prompt) {
		final List<Media> media = new ArrayList<>();
		for (Message message : prompt.getInstructions()) {
			if (message instanceof UserMessage user && user.getMedia() != null) {
				media.addAll(user.getMedia());
			}
		}
		return names(media);
	}

	private static String names(List<Media> media) {
		final List<String> names = new ArrayList<>();
		for (Media m : media) {
			names.add((m.getName() != null ? m.getName() : "file") + " ("
					+ (m.getMimeType() != null ? m.getMimeType().toString() : "unknown type") + ")");
		}
		return String.join(", ", names);
	}

	private static String causeOf(Throwable failure) {
		Throwable root = failure;
		while (root.getCause() != null && root.getCause() != root) {
			root = root.getCause();
		}
		return root.getClass().getSimpleName() + ": " + root.getMessage();
	}
}
