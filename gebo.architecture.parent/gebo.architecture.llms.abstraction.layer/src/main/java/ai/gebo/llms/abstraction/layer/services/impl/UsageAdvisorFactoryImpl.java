package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.concurrent.atomic.AtomicLong;
import reactor.core.publisher.SignalType;
import ai.gebo.core.messages.LLMCallOutcome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;
import ai.gebo.llms.abstraction.layer.services.IChatModelUsageAdvisor;
import ai.gebo.llms.abstraction.layer.services.IChatModelUsageAdvisorFactory;
import ai.gebo.model.ModelType;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class UsageAdvisorFactoryImpl implements IChatModelUsageAdvisorFactory {
	private final LLMUsageRecorder usageRecorder;

	@AllArgsConstructor
	public static final class GeboChatModelUsageAdvisor implements IChatModelUsageAdvisor {
		private static final Logger LOGGER = LoggerFactory.getLogger(GeboChatModelUsageAdvisor.class);

		private final GBaseChatModelConfig config;
		private final LLMUsageRecorder usageRecorder;
		/**
		 * The chat model's {@code IGConfigurableModel.getPricingConditions()}, read when
		 * a call ends; null for an unpriced model.
		 */
		private final Supplier<GModelPricingConditions> pricing;
		/**
		 * The chat model's {@code IGConfigurableModel.getProviderId()}, read when a call
		 * ends; null records the provider as unknown.
		 */
		private final Supplier<String> providerId;

		public GeboChatModelUsageAdvisor(GBaseChatModelConfig config, LLMUsageRecorder usageRecorder,
				Supplier<GModelPricingConditions> pricing) {
			this(config, usageRecorder, pricing, null);
		}

		@Override
		public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
			// Captured per invocation: this advisor instance is installed once per
			// ChatClient and shared by every request through it, so instance state would race.
			final String username = LLMUsageRecorder.safeCurrentUsername();
			final String stack = LLMUsageRecorder.safeSampleCaller();
			final long start = System.nanoTime();
			final ChatClientResponse response;
			try {
				response = chain.nextCall(request);
			} catch (RuntimeException e) {
				// A failed call still consumed time and is the interesting part of the tail.
				LLMUsageRecorder.bestEffort("account a failed chat call", () -> recordUsage(username, stack, start, null,
						new TokenCounters(), new AnswerShape(), LLMCallOutcome.ERROR));
				throw e;
			}
			// Accounted best effort, outside the call: it must never fail a successful call.
			// A blocking call returns the final response only, whose usage already covers
			// every tool calling round trip the model made (see adviseStream).
			// No time to first token: a blocking call gives no signal before it is complete.
			LLMUsageRecorder.bestEffort("account a chat call", () -> recordUsage(username, stack, start, null,
					TokenCounters.of(usageOf(response)), AnswerShape.of(response), LLMCallOutcome.SUCCESS));
			return response;
		}

		@Override
		public Flux<ChatClientResponse> adviseStream(ChatClientRequest request, StreamAdvisorChain chain) {
			// The user is read here, on the subscribing thread: the stream completes on a
			// Reactor thread whose security context is usually empty.
			final String username = LLMUsageRecorder.safeCurrentUsername();
			final String stack = LLMUsageRecorder.safeSampleCaller();
			final long start = System.nanoTime();
			// The provider emits a Usage object on EVERY streamed chunk, carrying zero counts
			// until the last one, so a null check cannot tell a chunk from the completion.
			// Recording per chunk wrote thousands of rows for one call; write exactly one
			// record when the stream terminates instead.
			// The call's usage is the LARGEST seen, never the sum. When the request drives a
			// tool calling loop the model performs several round trips inside this one
			// subscription, and Spring AI reports each round's usage as a running total
			// (UsageCalculator.getCumulativeUsage, Ollama's and Bedrock's own accumulation).
			// Worse, a chunk with no usage of its own carries the previous rounds' total, so
			// every chunk of the second round repeats the first round's tokens. Summing
			// counted those once per chunk; the maximum is the total of all the rounds.
			final TokenCounters counters = new TokenCounters();
			final AnswerShape shape = new AnswerShape();
			final FirstTokenTimer firstToken = new FirstTokenTimer();
			// Per chunk accounting is best effort: a throw here would error the whole stream.
			return chain.nextStream(request)
					.doOnNext(response -> LLMUsageRecorder.bestEffort("account a chat chunk", () -> {
						firstToken.onChunk(response != null ? response.chatResponse() : null);
						Usage usage = usageOf(response);
						if (isMeaningful(usage)) {
							counters.max(usage);
						}
						shape.add(response);
					})).doFinally(signal -> LLMUsageRecorder.bestEffort("account a chat stream",
							() -> recordUsage(username, stack, start, firstToken.firstTokenNanos(), counters, shape,
									outcomeOf(signal))));
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
		 * Token counts of one call: the largest of each count reported over however many
		 * chunks and model round trips the chain produced inside a single advisor
		 * invocation.
		 */
		static final class TokenCounters {
			private final AtomicLong input = new AtomicLong();
			private final AtomicLong output = new AtomicLong();
			private final AtomicLong total = new AtomicLong();

			void max(Usage usage) {
				if (usage == null) {
					return;
				}
				Integer in = usage.getPromptTokens();
				Integer out = usage.getCompletionTokens();
				Integer tot = usage.getTotalTokens();
				if (in != null) {
					input.accumulateAndGet(in.longValue(), Math::max);
				}
				if (out != null) {
					output.accumulateAndGet(out.longValue(), Math::max);
				}
				if (tot != null) {
					total.accumulateAndGet(tot.longValue(), Math::max);
				}
			}

			long input() {
				return input.get();
			}

			long output() {
				return output.get();
			}

			long total() {
				return total.get();
			}

			static TokenCounters of(Usage usage) {
				TokenCounters counters = new TokenCounters();
				counters.max(usage);
				return counters;
			}
		}

		/**
		 * How a call ended, for the DEBUG log: the last finish reason the provider gave and
		 * the characters of text and of reasoning it streamed. A call cut by the output limit
		 * (finish reason LENGTH) with no text spent it reasoning. The reasoning comes in the
		 * {@value #REASONING_CONTENT_METADATA} metadata of the answer, piece by piece or
		 * grown piece after piece.
		 */
		static final class AnswerShape {
			/** The metadata of the answer holding its reasoning (as the reasoning stream reads it). */
			static final String REASONING_CONTENT_METADATA = "reasoningContent";
			private String finishReason = null;
			private long textChars = 0;
			private long reasoningChars = 0;
			private String reasoningSoFar = "";

			synchronized void add(ChatClientResponse response) {
				final org.springframework.ai.chat.model.ChatResponse chat = response != null ? response.chatResponse()
						: null;
				final org.springframework.ai.chat.model.Generation result = chat != null ? chat.getResult() : null;
				if (result == null) {
					return;
				}
				if (result.getMetadata() != null && result.getMetadata().getFinishReason() != null
						&& !result.getMetadata().getFinishReason().isBlank()) {
					finishReason = result.getMetadata().getFinishReason();
				}
				if (result.getOutput() == null) {
					return;
				}
				final String text = result.getOutput().getText();
				if (text != null) {
					textChars += text.length();
				}
				final Object reasoning = result.getOutput().getMetadata() != null
						? result.getOutput().getMetadata().get(REASONING_CONTENT_METADATA)
						: null;
				if (reasoning instanceof String piece && !piece.isEmpty()) {
					if (piece.startsWith(reasoningSoFar)) {
						// the same reasoning grown
						reasoningChars += piece.length() - reasoningSoFar.length();
					} else {
						reasoningChars += piece.length();
					}
					reasoningSoFar = piece;
				}
			}

			static AnswerShape of(ChatClientResponse response) {
				final AnswerShape shape = new AnswerShape();
				shape.add(response);
				return shape;
			}

			@Override
			public synchronized String toString() {
				return "finishReason=" + finishReason + " text=" + textChars + " reasoning=" + reasoningChars;
			}
		}

		/**
		 * Writes the single usage record of one call. Always writes, even when the provider
		 * never returned usage metadata: the response time is worth recording on its own, and a
		 * call that produced no usage used to vanish from the audit entirely.
		 */
		private void recordUsage(String username, String callerStack, long startNanos, Long firstTokenNanos,
				TokenCounters counters, AnswerShape shape, LLMCallOutcome outcome) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Chat call ended outcome=" + outcome + " model=" + config.getCode() + " tokens="
						+ counters.input() + "/" + counters.output() + "/" + counters.total() + " firstToken="
						+ (firstTokenNanos != null ? "timed" : "n/a") + " " + shape);
			}
			usageRecorder.record(config, LLMUsageRecorder.safeProviderId(providerId), ModelType.CHAT, pricing,
					username, callerStack, startNanos, firstTokenNanos, counters.input(), counters.output(),
					counters.total(), outcome);
		}
	}

	@Override
	public IChatModelUsageAdvisor create(GBaseChatModelConfig config) {
		return create(config, null);
	}

	@Override
	public IChatModelUsageAdvisor create(GBaseChatModelConfig config, Supplier<GModelPricingConditions> pricing) {
		return create(config, pricing, null);
	}

	@Override
	public IChatModelUsageAdvisor create(GBaseChatModelConfig config, Supplier<GModelPricingConditions> pricing,
			Supplier<String> providerId) {
		return new GeboChatModelUsageAdvisor(config, usageRecorder, pricing, providerId);
	}

	@Override
	public ChatModel recording(ChatModel model, GBaseChatModelConfig config) {
		return recording(model, config, null);
	}

	@Override
	public ChatModel recording(ChatModel model, GBaseChatModelConfig config,
			Supplier<GModelPricingConditions> pricing) {
		return recording(model, config, pricing, null);
	}

	@Override
	public ChatModel recording(ChatModel model, GBaseChatModelConfig config,
			Supplier<GModelPricingConditions> pricing, Supplier<String> providerId) {
		if (model == null || model instanceof UsageRecordingChatModel) {
			return model;
		}
		return new UsageRecordingChatModel(model, config, usageRecorder, pricing, providerId);
	}

}
