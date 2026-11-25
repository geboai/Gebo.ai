/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Keeps the identity of the person a streamed model call is made for on every round of
 * its tool calling loop.
 * <p>
 * A streamed call is subscribed with the identity of who asks
 * ({@link ReactiveIdentityUtil}), and its tools run with it. The loop that calls the
 * model again after a tool ran is Spring AI's own (ToolCallingAdvisor): it subscribes
 * each further round on a thread of its own, which knows nobody, so what runs inside
 * the round - the advisors of the call, the model, whatever they record - worked for
 * nobody: the usage of those calls was recorded as anonymous.
 * <p>
 * Two advisors of the chat client close the gap. The {@link Carrier}, outside the
 * loop, samples the identity on the thread the call starts on and carries it down the
 * call in its Reactor context. The {@link Restorer}, the first advisor inside the loop,
 * builds and subscribes every round with that identity. A call made for nobody is left
 * as it is.
 */
public final class StreamedCallIdentity {
	private static final Logger LOGGER = LoggerFactory.getLogger(StreamedCallIdentity.class);
	/** Where a streamed call carries the identity it is made with. */
	static final String KEY = StreamedCallIdentity.class.getName() + ".runAs";
	/** Spring AI's ToolCallingAdvisor.DEFAULT_ORDER: the place of the tool calling loop among the advisors. */
	private static final int TOOL_CALLING_LOOP_ORDER = Ordered.HIGHEST_PRECEDENCE + 300;

	private StreamedCallIdentity() {
	}

	/** The identity of the thread, null when it works for nobody. */
	private static ReactiveIdentityUtil sample() {
		try {
			final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			return authentication != null && authentication.isAuthenticated() ? ReactiveIdentityUtil.create() : null;
		} catch (Throwable e) {
			LOGGER.error("Cannot sample the identity a streamed model call is made with", e);
			return null;
		}
	}

	/** Outside the tool calling loop: samples the identity once, where the call starts. */
	public static final class Carrier implements StreamAdvisor {
		static final int ORDER = TOOL_CALLING_LOOP_ORDER - 150;

		@Override
		public Flux<ChatClientResponse> adviseStream(ChatClientRequest request, StreamAdvisorChain chain) {
			final ReactiveIdentityUtil runAs = sample();
			final Flux<ChatClientResponse> stream = chain.nextStream(request);
			return runAs == null ? stream : stream.contextWrite(context -> context.put(KEY, runAs));
		}

		@Override
		public String getName() {
			return "Gebo Streamed Call Identity Carrier";
		}

		@Override
		public int getOrder() {
			return ORDER;
		}
	}

	/** The first advisor inside the tool calling loop: every round is built and subscribed with the identity. */
	public static final class Restorer implements StreamAdvisor {
		static final int ORDER = TOOL_CALLING_LOOP_ORDER + 1;

		@Override
		public Flux<ChatClientResponse> adviseStream(ChatClientRequest request, StreamAdvisorChain chain) {
			return Flux.deferContextual(context -> {
				final ReactiveIdentityUtil runAs = context.getOrDefault(KEY, null);
				if (runAs == null) {
					return chain.nextStream(request);
				}
				return runAs.doRunAsWithReturn(() -> chain.nextStream(request))
						.subscribeOn(runAs.wrap(Schedulers.boundedElastic()));
			});
		}

		@Override
		public String getName() {
			return "Gebo Streamed Call Identity Restorer";
		}

		@Override
		public int getOrder() {
			return ORDER;
		}
	}
}
