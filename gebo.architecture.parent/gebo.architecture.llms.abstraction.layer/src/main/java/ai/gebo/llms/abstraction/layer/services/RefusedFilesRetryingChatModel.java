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
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import reactor.core.publisher.Flux;

/**
 * A provider's chat model that does not let a file sink the answer. When a call whose
 * prompt carries files - the files of a request, the files a tool showed - is refused by
 * the provider as a bad request (a file it cannot read, too large, of a type it does not
 * take), the call is made once more without the files, and the model is told which
 * files were left out. A prompt without files, and any other failure, are as before; a
 * streamed answer is retried only when nothing of it was received yet. Who listens
 * ({@link Listener}) is told of the files left out and of the person the request was
 * made for.
 */
public final class RefusedFilesRetryingChatModel implements ChatModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(RefusedFilesRetryingChatModel.class);
	/** The note the model reads in place of the files left out. */
	static final String LEFT_OUT_NOTE = "These files could not be read by the model provider and were left out: ";
	/** Where a streamed call carries who it is made for (see {@link AskingAdvisor}). */
	static final String ASKING_KEY = RefusedFilesRetryingChatModel.class.getName() + ".asking";
	/** What the error of a provider's client is asked for the HTTP status of its answer. */
	private static final List<String> STATUS_ACCESSORS = List.of("statusCode", "code");
	private final ChatModel delegate;
	private final Listener listener;

	/** Told of the files a request was made again without. */
	public interface Listener {
		/**
		 * @param files   the files left out
		 * @param refusal what the provider answered the request carrying them
		 * @param asking  the username of the person the request was made for, null when
		 *                it was made for nobody
		 */
		void filesLeftOut(List<Media> files, Throwable refusal, String asking);
	}

	private RefusedFilesRetryingChatModel(ChatModel delegate, Listener listener) {
		this.delegate = delegate;
		this.listener = listener;
	}

	/** The model wrapped, or the model itself when null or already wrapped. */
	public static ChatModel wrap(ChatModel model) {
		return wrap(model, null);
	}

	/**
	 * The model wrapped, or the model itself when null or already wrapped.
	 *
	 * @param listener told of the files left out, may be null
	 */
	public static ChatModel wrap(ChatModel model, Listener listener) {
		return model == null || model instanceof RefusedFilesRetryingChatModel ? model
				: new RefusedFilesRetryingChatModel(model, listener);
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
			tell(prompt, failure, askingOnThisThread());
			return delegate.call(withoutFiles(prompt));
		}
	}

	@Override
	public Flux<ChatResponse> stream(Prompt prompt) {
		if (!carriesFiles(prompt)) {
			return delegate.stream(prompt);
		}
		final AtomicBoolean received = new AtomicBoolean(false);
		return Flux.deferContextual(context -> Flux.defer(() -> delegate.stream(prompt))
				.doOnNext(x -> received.set(true)).onErrorResume(failure -> {
					if (received.get() || !refusedAsBadRequest(failure)) {
						return Flux.error(failure);
					}
					LOGGER.warn("The model provider refused a streamed request carrying files (" + causeOf(failure)
							+ "): it is made again without " + namesOf(prompt));
					// the rounds after a tool ran are made on threads that do not know the person
					final String carried = context.getOrDefault(ASKING_KEY, null);
					tell(prompt, failure, carried != null ? carried : askingOnThisThread());
					return delegate.stream(withoutFiles(prompt));
				}));
	}

	/** Tells who listens, best effort: the answer never depends on it. */
	private void tell(Prompt prompt, Throwable refusal, String asking) {
		if (listener == null) {
			return;
		}
		try {
			listener.filesLeftOut(filesOf(prompt), refusal, asking);
		} catch (Throwable e) {
			LOGGER.error("Cannot tell of the files left out of a request, the request goes on", e);
		}
	}

	/** The username of the person this thread works for, null when it works for nobody. */
	static String askingOnThisThread() {
		try {
			final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			return authentication != null && authentication.isAuthenticated()
					&& !(authentication instanceof AnonymousAuthenticationToken) ? authentication.getName() : null;
		} catch (Throwable e) {
			LOGGER.error("Cannot read the person a model call is made for", e);
			return null;
		}
	}

	/**
	 * Carries who a streamed call is made for down to its model, where
	 * {@link RefusedFilesRetryingChatModel} reads it: the rounds after a tool ran are
	 * made on threads that do not know the person. Outside the tool calling loop
	 * (Spring AI's ToolCallingAdvisor, DEFAULT_ORDER = HIGHEST_PRECEDENCE + 300), so it
	 * is read once, on the thread the call starts on.
	 */
	public static final class AskingAdvisor implements StreamAdvisor {
		static final int ORDER = Ordered.HIGHEST_PRECEDENCE + 150;

		@Override
		public Flux<ChatClientResponse> adviseStream(ChatClientRequest request, StreamAdvisorChain chain) {
			final String asking = askingOnThisThread();
			final Flux<ChatClientResponse> stream = chain.nextStream(request);
			return asking == null ? stream : stream.contextWrite(context -> context.put(ASKING_KEY, asking));
		}

		@Override
		public String getName() {
			return "Gebo Asking Person Advisor";
		}

		@Override
		public int getOrder() {
			return ORDER;
		}
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
	 * answer, whatever the provider's client calls it. The status is read from the error
	 * itself where the client gives it - the clients of OpenAI, Anthropic and Bedrock by
	 * {@code statusCode()}, Google's by {@code code()}, Spring's own HTTP errors - and
	 * from the text of the error Spring AI raises for the providers it calls over its
	 * own HTTP client, which begins with it. A request refused for another reason fails
	 * again without the files, after one more call.
	 */
	static boolean refusedAsBadRequest(Throwable failure) {
		for (Throwable t = failure; t != null; t = t.getCause() == t ? null : t.getCause()) {
			final String name = t.getClass().getSimpleName().toLowerCase(Locale.ROOT);
			if (name.contains("badrequest") || name.contains("unprocessable") || name.contains("payloadtoolarge")
					|| name.contains("unsupportedmediatype")) {
				return true;
			}
			if (t instanceof org.springframework.web.client.HttpClientErrorException http
					&& refusal(http.getStatusCode().value())) {
				return true;
			}
			if (t instanceof org.springframework.web.reactive.function.client.WebClientResponseException http
					&& refusal(http.getStatusCode().value())) {
				return true;
			}
			final Integer status = statusOf(t);
			if (status != null && refusal(status)) {
				return true;
			}
		}
		return false;
	}

	private static boolean refusal(int status) {
		return status == 400 || status == 413 || status == 415 || status == 422;
	}

	/**
	 * The HTTP status the error of a provider's client tells, null when it tells none:
	 * what its {@code statusCode()} or {@code code()} gives, or what the text of Spring
	 * AI's error for a refused request ("400 - ...") begins with.
	 */
	private static Integer statusOf(Throwable t) {
		for (String accessor : STATUS_ACCESSORS) {
			try {
				final java.lang.reflect.Method method = t.getClass().getMethod(accessor);
				if (method.getReturnType() == int.class || method.getReturnType() == Integer.class) {
					return (Integer) method.invoke(t);
				}
			} catch (ReflectiveOperationException | RuntimeException e) {
				// this error does not tell its status this way
			}
		}
		final String text = t.getMessage();
		if ("NonTransientAiException".equals(t.getClass().getSimpleName()) && text != null) {
			final int end = text.indexOf(" - ");
			if (end == 3) {
				try {
					return Integer.valueOf(text.substring(0, end));
				} catch (NumberFormatException e) {
					// the text does not begin with a status
				}
			}
		}
		return null;
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
		return names(filesOf(prompt));
	}

	private static List<Media> filesOf(Prompt prompt) {
		final List<Media> media = new ArrayList<>();
		for (Message message : prompt.getInstructions()) {
			if (message instanceof UserMessage user && user.getMedia() != null) {
				media.addAll(user.getMedia());
			}
		}
		return media;
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
