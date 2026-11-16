/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai.services;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.responses.OpenAiResponsesChatOptions;

import com.openai.errors.BadRequestException;

import reactor.core.publisher.Flux;

/**
 * A Responses chat model asking the reasoning summaries while the organization is given
 * them. Summaries "may require organization verification first" (OpenAI's reasoning
 * guide): an organization refused them gets a 400 for every request asking them. The
 * first such refusal is answered here by the same request without summaries, and from
 * then on they are no longer asked; the answer goes on without its thinking shown, the
 * reasoning itself unchanged.
 * <p>
 * Spring AI's {@code OpenAiResponsesChatModel} is final and has no such fallback, so it is
 * wrapped. A streamed request is asked again only while it has emitted nothing: a
 * refusal comes before the first event.
 */
public class ReasoningSummariesFallbackChatModel implements ChatModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReasoningSummariesFallbackChatModel.class);

	private final ChatModel delegate;
	private final AtomicBoolean summariesRefused = new AtomicBoolean(false);

	public ReasoningSummariesFallbackChatModel(ChatModel delegate) {
		this.delegate = delegate;
	}

	@Override
	public ChatOptions getOptions() {
		return delegate.getOptions();
	}

	/** Whether the organization was refused the summaries: they are no longer asked. */
	boolean summariesRefused() {
		return summariesRefused.get();
	}

	@Override
	public ChatResponse call(Prompt prompt) {
		if (summariesRefused.get()) {
			return delegate.call(withoutSummaries(prompt));
		}
		try {
			return delegate.call(prompt);
		} catch (RuntimeException e) {
			if (!refusesSummaries(e)) {
				throw e;
			}
			refused(e);
			return delegate.call(withoutSummaries(prompt));
		}
	}

	@Override
	public Flux<ChatResponse> stream(Prompt prompt) {
		return Flux.defer(() -> {
			if (summariesRefused.get()) {
				return delegate.stream(withoutSummaries(prompt));
			}
			final AtomicBoolean emitted = new AtomicBoolean(false);
			return delegate.stream(prompt).doOnNext(response -> emitted.set(true)).onErrorResume(e -> {
				if (emitted.get() || !refusesSummaries(e)) {
					return Flux.error(e);
				}
				refused(e);
				return delegate.stream(withoutSummaries(prompt));
			});
		});
	}

	private void refused(Throwable e) {
		if (summariesRefused.compareAndSet(false, true)) {
			LOGGER.warn("OpenAI refused the reasoning summaries ({}): the requests no longer ask them",
					String.valueOf(e.getMessage()));
		}
	}

	/** The prompt asking no summaries: its options, or the model's, without them. */
	Prompt withoutSummaries(Prompt prompt) {
		final ChatOptions options = prompt.getOptions() != null ? prompt.getOptions() : delegate.getOptions();
		if (!(options instanceof OpenAiResponsesChatOptions responses)) {
			return prompt;
		}
		return prompt.mutate().chatOptions(responses.mutate().reasoningSummary(null).build()).build();
	}

	/**
	 * Whether a failure is OpenAI refusing the summaries: a 400 naming them or the
	 * verification they need, wherever it is wrapped (the async client completes
	 * exceptionally).
	 */
	static boolean refusesSummaries(Throwable failure) {
		for (Throwable cause = failure; cause != null; cause = cause.getCause() != cause ? cause.getCause() : null) {
			if (cause instanceof BadRequestException) {
				final String message = String.valueOf(cause.getMessage()).toLowerCase(Locale.ROOT);
				return message.contains("summary") || message.contains("verif");
			}
		}
		return false;
	}
}
