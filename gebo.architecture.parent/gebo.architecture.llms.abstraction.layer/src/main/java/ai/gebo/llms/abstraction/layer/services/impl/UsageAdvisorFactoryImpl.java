package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.concurrent.atomic.AtomicLong;
import reactor.core.publisher.SignalType;
import ai.gebo.core.messages.LLMCallOutcome;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.dto.LLMUsageDetailDto;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.services.IChatModelUsageAdvisor;
import ai.gebo.llms.abstraction.layer.services.IChatModelUsageAdvisorFactory;
import ai.gebo.llms.abstraction.layer.services.ILLMSUsageCrudService;
import ai.gebo.llms.abstraction.layer.services.StackSamplingUtils;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class UsageAdvisorFactoryImpl implements IChatModelUsageAdvisorFactory {
	private final ILLMSUsageCrudService usageCrudService;

	@AllArgsConstructor
	public static final class GeboChatModelUsageAdvisor implements IChatModelUsageAdvisor {

		private final GBaseChatModelConfig config;
		private final ILLMSUsageCrudService usageCrudService;

		@Override
		public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
			// Sampled and kept per invocation: this advisor instance is installed once per
			// ChatClient and shared by every request through it, so instance state would race.
			final String stack = StackSamplingUtils.sampleCallerPackages(3);
			final long start = System.nanoTime();
			try {
				ChatClientResponse response = chain.nextCall(request);
				recordUsage(stack, start, TokenCounters.of(usageOf(response)), LLMCallOutcome.SUCCESS);
				return response;
			} catch (RuntimeException e) {
				// A failed call still consumed time and is the interesting part of the tail.
				recordUsage(stack, start, new TokenCounters(), LLMCallOutcome.ERROR);
				throw e;
			}
		}

		@Override
		public Flux<ChatClientResponse> adviseStream(ChatClientRequest request, StreamAdvisorChain chain) {
			final String stack = StackSamplingUtils.sampleCallerPackages(3);
			final long start = System.nanoTime();
			// The provider emits a Usage object on EVERY streamed chunk, carrying zero counts
			// until the last one, so a null check cannot tell a chunk from the completion.
			// Recording per chunk wrote thousands of rows for one call, each holding elapsed
			// time rather than a duration, which turned nrRequests into a chunk count and
			// latencyAvg into roughly half the token weighted mean. Keep the last meaningful
			// usage and write exactly one record when the stream terminates.
			// Accumulated rather than kept as the last one seen: when the request drives a
			// tool calling loop the chain performs several model round trips inside this one
			// subscription, each ending with its own usage, and keeping only the last would
			// under count the call. Summing is safe because the provider reports usage once
			// per round trip, not cumulatively on every chunk.
			final TokenCounters counters = new TokenCounters();
			return chain.nextStream(request).doOnNext(response -> {
				Usage usage = usageOf(response);
				if (isMeaningful(usage)) {
					counters.add(usage);
				}
			}).doFinally(signal -> recordUsage(stack, start, counters, outcomeOf(signal)));
		}

		@Override
		public String getName() {
			return "gebo-chatmodel-usage-advisor";
		}

		@Override
		public int getOrder() {
			return Ordered.LOWEST_PRECEDENCE - 100;
		}

		private static LLMCallOutcome outcomeOf(SignalType signal) {
			if (signal == SignalType.ON_ERROR) {
				return LLMCallOutcome.ERROR;
			}
			if (signal == SignalType.CANCEL) {
				return LLMCallOutcome.CANCELLED;
			}
			return LLMCallOutcome.SUCCESS;
		}

		private static Usage usageOf(ChatClientResponse response) {
			if (response == null || response.chatResponse() == null
					|| response.chatResponse().getMetadata() == null) {
				return null;
			}
			return response.chatResponse().getMetadata().getUsage();
		}

		/**
		 * Whether a {@link Usage} carries real counts. A streamed chunk carries a non null
		 * Usage whose counts are zero or null, so identity with null is not the test.
		 */
		private static boolean isMeaningful(Usage usage) {
			if (usage == null) {
				return false;
			}
			Integer total = usage.getTotalTokens();
			Integer input = usage.getPromptTokens();
			Integer output = usage.getCompletionTokens();
			return (total != null && total.intValue() > 0) || (input != null && input.intValue() > 0)
					|| (output != null && output.intValue() > 0);
		}

		/**
		 * Token counts of one call, summed over however many model round trips the chain
		 * performed inside a single advisor invocation.
		 */
		private static final class TokenCounters {
			private final AtomicLong input = new AtomicLong();
			private final AtomicLong output = new AtomicLong();
			private final AtomicLong total = new AtomicLong();

			private void add(Usage usage) {
				if (usage == null) {
					return;
				}
				Integer in = usage.getPromptTokens();
				Integer out = usage.getCompletionTokens();
				Integer tot = usage.getTotalTokens();
				if (in != null) {
					input.addAndGet(in.longValue());
				}
				if (out != null) {
					output.addAndGet(out.longValue());
				}
				if (tot != null) {
					total.addAndGet(tot.longValue());
				}
			}

			private static TokenCounters of(Usage usage) {
				TokenCounters counters = new TokenCounters();
				counters.add(usage);
				return counters;
			}
		}

		/**
		 * Writes the single usage record of one call. Always writes, even when the provider
		 * never returned usage metadata: the latency is worth recording on its own, and a
		 * call that produced no usage used to vanish from the audit entirely.
		 */
		private void recordUsage(String callerStack, long startNanos, TokenCounters counters, LLMCallOutcome outcome) {
			String username = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
					.filter(Authentication::isAuthenticated).map(Authentication::getName).orElse("anonymous");
			long latencyMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos);
			LLMUsageDetailDto detail = LLMUsageDetailDto.of(config);
			detail.setInputToken(counters.input.get());
			detail.setOutputToken(counters.output.get());
			detail.setTotalToken(counters.total.get());
			detail.setUsername(username);
			detail.setCallerStack(callerStack);
			detail.setLatency(latencyMs);
			detail.setOutcome(outcome);
			this.usageCrudService.enqueueUsage(detail);
		}
	}

	@Override
	public IChatModelUsageAdvisor create(GBaseChatModelConfig config) {

		return new GeboChatModelUsageAdvisor(config, usageCrudService);
	}

}
