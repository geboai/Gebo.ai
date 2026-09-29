package ai.gebo.llms.abstraction.layer.services.impl;

import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;

import ai.gebo.core.messages.LLMCallOutcome;
import java.util.function.Supplier;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;
import ai.gebo.llms.abstraction.layer.services.impl.UsageAdvisorFactoryImpl.GeboChatModelUsageAdvisor.TokenCounters;
import ai.gebo.model.ModelType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

/**
 * A raw {@link ChatModel} that records its calls as usage, the way the usage advisor
 * records the calls made through a ChatClient. It is what
 * {@code IGConfigurableChatModel.doWithChatModel(...)} hands out, so code driving the
 * model directly, instead of through its ChatClient, is accounted too.
 * <p>
 * Only {@link #call(Prompt)} and {@link #stream(Prompt)} are recorded: every other
 * call and stream method of the interface defaults to one of them, on this wrapper.
 * Token counts follow the advisor's rule: the largest usage reported, because Spring
 * AI reports the usage of a tool calling loop as a running total.
 */
public class UsageRecordingChatModel implements ChatModel {
	private final ChatModel delegate;
	private final GBaseChatModelConfig config;
	private final LLMUsageRecorder recorder;
	/** The chat model's pricing conditions, read when a call ends; null if unpriced. */
	private final Supplier<GModelPricingConditions> pricing;

	public UsageRecordingChatModel(ChatModel delegate, GBaseChatModelConfig config, LLMUsageRecorder recorder,
			Supplier<GModelPricingConditions> pricing) {
		this.delegate = delegate;
		this.config = config;
		this.recorder = recorder;
		this.pricing = pricing;
	}

	/** The provider model this wrapper forwards to. */
	public ChatModel getDelegate() {
		return delegate;
	}

	@Override
	public ChatResponse call(Prompt prompt) {
		final String username = LLMUsageRecorder.safeCurrentUsername();
		final String stack = LLMUsageRecorder.safeSampleCaller();
		final long start = System.nanoTime();
		final ChatResponse response;
		try {
			response = delegate.call(prompt);
		} catch (RuntimeException e) {
			recorder.record(config, ModelType.CHAT, pricing, username, stack, start, null, 0, 0, 0,
					LLMCallOutcome.ERROR);
			throw e;
		}
		// Accounted best effort, outside the call: it must never fail a successful call.
		// No time to first token: a blocking call gives no signal before it is complete.
		LLMUsageRecorder.bestEffort("account a chat call", () -> {
			TokenCounters counters = TokenCounters.of(usageOf(response));
			recorder.record(config, ModelType.CHAT, pricing, username, stack, start, null, counters.input(),
					counters.output(), counters.total(), LLMCallOutcome.SUCCESS);
		});
		return response;
	}

	@Override
	public Flux<ChatResponse> stream(Prompt prompt) {
		// Captured on the calling thread: the stream completes on a Reactor thread.
		final String username = LLMUsageRecorder.safeCurrentUsername();
		final String stack = LLMUsageRecorder.safeSampleCaller();
		final long start = System.nanoTime();
		final TokenCounters counters = new TokenCounters();
		final FirstTokenTimer firstToken = new FirstTokenTimer();
		// Per chunk accounting is best effort: a throw here would error the whole stream.
		return delegate.stream(prompt).doOnNext(response -> LLMUsageRecorder.bestEffort("account a chat chunk", () -> {
			firstToken.onChunk(response);
			counters.max(usageOf(response));
		})).doFinally(signal -> recorder.record(config, ModelType.CHAT, pricing, username, stack, start,
				firstToken.firstTokenNanos(), counters.input(), counters.output(), counters.total(),
				outcomeOf(signal)));
	}

	@Override
	public ChatOptions getOptions() {
		return delegate.getOptions();
	}

	@Override
	public ChatOptions getDefaultOptions() {
		return delegate.getDefaultOptions();
	}

	private static Usage usageOf(ChatResponse response) {
		return response != null && response.getMetadata() != null ? response.getMetadata().getUsage() : null;
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
}
