package ai.gebo.llms.abstraction.layer.services.impl;

import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;

import ai.gebo.core.messages.LLMCallOutcome;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
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

	public UsageRecordingChatModel(ChatModel delegate, GBaseChatModelConfig config, LLMUsageRecorder recorder) {
		this.delegate = delegate;
		this.config = config;
		this.recorder = recorder;
	}

	/** The provider model this wrapper forwards to. */
	public ChatModel getDelegate() {
		return delegate;
	}

	@Override
	public ChatResponse call(Prompt prompt) {
		final String username = LLMUsageRecorder.currentUsername();
		final String stack = LLMUsageRecorder.sampleCaller();
		final long start = System.nanoTime();
		try {
			ChatResponse response = delegate.call(prompt);
			TokenCounters counters = TokenCounters.of(usageOf(response));
			// No time to first token: a blocking call gives no signal before it is complete.
			recorder.record(config, ModelType.CHAT, username, stack, start, null, counters.input(),
					counters.output(), counters.total(), LLMCallOutcome.SUCCESS);
			return response;
		} catch (RuntimeException e) {
			recorder.record(config, ModelType.CHAT, username, stack, start, null, 0, 0, 0, LLMCallOutcome.ERROR);
			throw e;
		}
	}

	@Override
	public Flux<ChatResponse> stream(Prompt prompt) {
		// Captured on the calling thread: the stream completes on a Reactor thread.
		final String username = LLMUsageRecorder.currentUsername();
		final String stack = LLMUsageRecorder.sampleCaller();
		final long start = System.nanoTime();
		final TokenCounters counters = new TokenCounters();
		final FirstTokenTimer firstToken = new FirstTokenTimer();
		return delegate.stream(prompt).doOnNext(response -> {
			firstToken.onChunk(response);
			counters.max(usageOf(response));
		}).doFinally(signal -> recorder.record(config, ModelType.CHAT, username, stack, start,
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
