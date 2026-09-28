package ai.gebo.llms.abstraction.layer.services.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.DefaultUsage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;

import ai.gebo.core.messages.LLMCallOutcome;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.services.impl.UsageAdvisorFactoryImpl.GeboChatModelUsageAdvisor;
import ai.gebo.model.ModelType;
import reactor.core.publisher.Flux;

/**
 * Pins how a chat call's token usage is derived. Spring AI reports streamed usage as
 * a running total across the round trips of a tool calling loop, and a chunk with no
 * usage of its own repeats the previous rounds' total, so the call's usage is the
 * largest reported, never the sum.
 */
class GeboChatModelUsageAdvisorTest {

	private static ChatClientResponse chunk(int prompt, int completion, int total) {
		ChatResponseMetadata metadata = ChatResponseMetadata.builder()
				.usage(new DefaultUsage(prompt, completion, total)).build();
		ChatResponse response = new ChatResponse(List.of(new Generation(new AssistantMessage("x"))), metadata);
		return new ChatClientResponse(response, Map.of());
	}

	@Test
	void streamedToolLoopRecordsTheRunningTotalOnce() {
		LLMUsageRecorder recorder = mock(LLMUsageRecorder.class);
		GBaseChatModelConfig config = mock(GBaseChatModelConfig.class);
		GeboChatModelUsageAdvisor advisor = new GeboChatModelUsageAdvisor(config, recorder);
		StreamAdvisorChain chain = mock(StreamAdvisorChain.class);
		// Round 1: empty chunks, then its usage. Round 2: every chunk repeats round 1's
		// total, then the running total of both rounds.
		when(chain.nextStream(any())).thenReturn(Flux.just(chunk(0, 0, 0), chunk(0, 0, 0), chunk(100, 10, 110),
				chunk(100, 10, 110), chunk(100, 10, 110), chunk(100, 10, 110), chunk(250, 30, 280)));

		advisor.adviseStream(mock(ChatClientRequest.class), chain).blockLast();

		verify(recorder, times(1)).record(eq(config), eq(ModelType.CHAT), anyString(), anyString(), anyLong(), eq(250L), eq(30L),
				eq(280L), eq(LLMCallOutcome.SUCCESS));
	}

	@Test
	void streamWithoutUsageStillRecordsTheCall() {
		LLMUsageRecorder recorder = mock(LLMUsageRecorder.class);
		GBaseChatModelConfig config = mock(GBaseChatModelConfig.class);
		GeboChatModelUsageAdvisor advisor = new GeboChatModelUsageAdvisor(config, recorder);
		StreamAdvisorChain chain = mock(StreamAdvisorChain.class);
		when(chain.nextStream(any())).thenReturn(Flux.just(chunk(0, 0, 0), chunk(0, 0, 0)));

		advisor.adviseStream(mock(ChatClientRequest.class), chain).blockLast();

		verify(recorder, times(1)).record(eq(config), eq(ModelType.CHAT), anyString(), anyString(), anyLong(), eq(0L), eq(0L), eq(0L),
				eq(LLMCallOutcome.SUCCESS));
	}

	@Test
	void failedStreamIsRecordedAsError() {
		LLMUsageRecorder recorder = mock(LLMUsageRecorder.class);
		GBaseChatModelConfig config = mock(GBaseChatModelConfig.class);
		GeboChatModelUsageAdvisor advisor = new GeboChatModelUsageAdvisor(config, recorder);
		StreamAdvisorChain chain = mock(StreamAdvisorChain.class);
		when(chain.nextStream(any()))
				.thenReturn(Flux.concat(Flux.just(chunk(40, 0, 40)), Flux.error(new IllegalStateException("down"))));

		try {
			advisor.adviseStream(mock(ChatClientRequest.class), chain).blockLast();
		} catch (IllegalStateException expected) {
			// the failure is propagated to the caller
		}

		verify(recorder, times(1)).record(eq(config), eq(ModelType.CHAT), anyString(), anyString(), anyLong(), eq(40L), eq(0L), eq(40L),
				eq(LLMCallOutcome.ERROR));
	}

	@Test
	void blockingCallRecordsTheFinalUsage() {
		LLMUsageRecorder recorder = mock(LLMUsageRecorder.class);
		GBaseChatModelConfig config = mock(GBaseChatModelConfig.class);
		GeboChatModelUsageAdvisor advisor = new GeboChatModelUsageAdvisor(config, recorder);
		CallAdvisorChain chain = mock(CallAdvisorChain.class);
		when(chain.nextCall(any())).thenReturn(chunk(250, 30, 280));

		advisor.adviseCall(mock(ChatClientRequest.class), chain);

		verify(recorder, times(1)).record(eq(config), eq(ModelType.CHAT), anyString(), anyString(), anyLong(), eq(250L), eq(30L),
				eq(280L), eq(LLMCallOutcome.SUCCESS));
	}
}
