/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

/**
 * How a streamed chat request ends, the same way whatever route answered it.
 * <p>
 * Whoever streams a request (the chat pipelines executor, or the direct
 * {@code streamChat(request)} entry points of the chat services) wraps its answer
 * stream here once, after {@link IGChatSessionLifeCycleService#startRequest}. The
 * route that answers fills the request's response and emits it as the content of the
 * envelope flagged as the last message; it never ends the request itself.
 * <ul>
 * <li>As that last message passes, before the client gets it, the interaction is saved
 * ({@link IGChatSessionLifeCycleService#endRequest}): the client asks for the chat
 * title as soon as it receives the message, and a title written from a session saved
 * without the answer overwrote it.</li>
 * <li>A stream ending without that message is saved however it ended, but cancelled
 * before any answer text came: a cancelled answer the user saw part of is kept, an
 * empty turn is not.</li>
 * <li>The session maintenance of a completed request
 * ({@link IGChatSessionLifeCycleService#chatRequestCompleted}) runs unless the request
 * was cancelled; the request is released in every case.</li>
 * </ul>
 */
public final class ChatRequestEnding {
	private static final Logger LOGGER = LoggerFactory.getLogger(ChatRequestEnding.class);

	private ChatRequestEnding() {
	}

	/**
	 * The answer stream of the request, ending it as described by this class.
	 *
	 * @param stream    the answer stream, whose last message carries the response
	 * @param lifeCycle the lifecycle service the request was started with
	 * @param request   the request being answered
	 * @param response  its response, the one the stream's last message carries
	 * @param chatModel the chat model answering, the session maintenance's reference
	 * @param runAs     the identity of the user the request belongs to
	 */
	public static Flux<GeboChatMessageEnvelope> ending(Flux<GeboChatMessageEnvelope> stream,
			IGChatSessionLifeCycleService lifeCycle, GeboChatRequest request, GeboChatResponse response,
			IGConfigurableChatModel chatModel, ReactiveIdentityUtil runAs) {
		final AtomicBoolean ended = new AtomicBoolean(false);
		return stream.doOnNext(envelope -> {
			if (envelope != null && envelope.isLastMessage() && envelope.getContent() instanceof GeboChatResponse
					&& ended.compareAndSet(false, true)) {
				runAs.doAs(() -> endRequest(lifeCycle, request, response));
			}
		}).doFinally(signal -> runAs.doAs(() -> {
			try {
				final boolean cancelled = signal == SignalType.CANCEL;
				if (!ended.get()) {
					final boolean hasContent = response != null && response.getQueryResponse() != null
							&& !response.getQueryResponse().isBlank();
					if (cancelled && !hasContent) {
						LOGGER.debug("Request {} of chat {} cancelled before any answer: nothing saved",
								request.getId(), request.getUserChatContextCode());
					} else if (ended.compareAndSet(false, true)) {
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Request {} of chat {} ended on {} without its last message: saved as it is",
									request.getId(), request.getUserChatContextCode(), signal);
						}
						endRequest(lifeCycle, request, response);
					}
				}
				if (!cancelled) {
					try {
						lifeCycle.chatRequestCompleted(request, chatModel);
					} catch (Throwable e) {
						LOGGER.error("Error completing request " + request.getId(), e);
					}
				}
			} finally {
				lifeCycle.releaseRequest(request);
			}
		}));
	}

	private static void endRequest(IGChatSessionLifeCycleService lifeCycle, GeboChatRequest request,
			GeboChatResponse response) {
		try {
			lifeCycle.endRequest(request, response);
		} catch (Throwable e) {
			LOGGER.error("Error ending request " + request.getId(), e);
		}
	}
}
