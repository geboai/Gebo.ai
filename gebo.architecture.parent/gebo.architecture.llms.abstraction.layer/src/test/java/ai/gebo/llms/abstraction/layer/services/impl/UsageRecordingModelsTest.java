package ai.gebo.llms.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.DefaultUsage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.embedding.EmbeddingResponseMetadata;

import ai.gebo.core.messages.LLMCallOutcome;
import ai.gebo.llms.abstraction.layer.dto.LLMUsageDetailDto;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableRankerModel;
import ai.gebo.llms.abstraction.layer.services.ILLMSUsageCrudService;
import ai.gebo.model.ModelType;
import ai.gebo.ranker.model.RankerModel;

/**
 * Pins that the usage recording wrappers write exactly one record per provider call,
 * with the provider's token usage and the right model type.
 */
class UsageRecordingModelsTest {

	private static EmbeddingResponse embeddingResponse(int tokens) {
		return new EmbeddingResponse(List.of(new Embedding(new float[] { 1f }, 0)),
				new EmbeddingResponseMetadata("m", new DefaultUsage(tokens, 0, tokens)));
	}

	@Test
	void embeddingBulkCallsAreRecordedOncePerProviderCall() {
		ILLMSUsageCrudService crud = mock(ILLMSUsageCrudService.class);
		LLMUsageRecorder recorder = new LLMUsageRecorder(crud);
		EmbeddingModel provider = mock(EmbeddingModel.class);
		when(provider.call(any())).thenReturn(embeddingResponse(12));
		when(provider.dimensions()).thenReturn(1);
		GBaseEmbeddingModelConfig config = mock(GBaseEmbeddingModelConfig.class);
		UsageRecordingEmbeddingModel model = new UsageRecordingEmbeddingModel(provider, () -> config, () -> recorder, null);

		// embed(List<String>) keeps its interface default, which ends in the wrapper's call().
		model.embed(List.of("a"));
		// dimensions() is forwarded, never recorded: the default would embed a probe text.
		assertEquals(1, model.dimensions());

		ArgumentCaptor<LLMUsageDetailDto> captor = ArgumentCaptor.forClass(LLMUsageDetailDto.class);
		verify(crud, times(1)).enqueueUsage(captor.capture());
		LLMUsageDetailDto detail = captor.getValue();
		assertEquals(ModelType.EMBEDDING, detail.getModelType());
		assertEquals(12, detail.getInputToken());
		assertEquals(12, detail.getTotalToken());
		assertEquals(LLMCallOutcome.SUCCESS, detail.getOutcome());
	}

	@Test
	void aFailingUsageStoreNeverFailsASuccessfulCall() {
		ILLMSUsageCrudService crud = mock(ILLMSUsageCrudService.class);
		org.mockito.Mockito.doThrow(new IllegalStateException("usage store down")).when(crud).enqueueUsage(any());
		LLMUsageRecorder recorder = new LLMUsageRecorder(crud);
		EmbeddingModel embeddings = mock(EmbeddingModel.class);
		when(embeddings.call(any())).thenReturn(embeddingResponse(12));
		UsageRecordingEmbeddingModel embeddingModel = new UsageRecordingEmbeddingModel(embeddings,
				() -> mock(GBaseEmbeddingModelConfig.class), () -> recorder, null);
		ChatModel chat = mock(ChatModel.class);
		ChatResponse response = new ChatResponse(List.of(new Generation(new AssistantMessage("ok"))),
				ChatResponseMetadata.builder().usage(new DefaultUsage(50, 5, 55)).build());
		when(chat.call(any(Prompt.class))).thenReturn(response);
		when(chat.stream(any(Prompt.class))).thenReturn(reactor.core.publisher.Flux.just(response));
		ChatModel chatModel = new UsageRecordingChatModel(chat, mock(GBaseChatModelConfig.class), recorder, null);

		// The calls succeed although their accounting fails.
		assertEquals(1, embeddingModel.embed(List.of("a")).size());
		assertEquals("ok", chatModel.call("hello"));
		assertEquals(1, chatModel.stream(new Prompt("hello")).collectList().block().size());
		// One attempt per call: the accounting failure is not recorded as a failed call.
		verify(crud, times(3)).enqueueUsage(any());
	}

	@Test
	void aResponseWhoseUsageCannotBeReadIsRecordedWithoutTokens() {
		ILLMSUsageCrudService crud = mock(ILLMSUsageCrudService.class);
		LLMUsageRecorder recorder = new LLMUsageRecorder(crud);
		EmbeddingModel provider = mock(EmbeddingModel.class);
		EmbeddingResponse broken = mock(EmbeddingResponse.class);
		when(broken.getMetadata()).thenThrow(new IllegalStateException("no metadata"));
		when(provider.call(any())).thenReturn(broken);
		UsageRecordingEmbeddingModel model = new UsageRecordingEmbeddingModel(provider,
				() -> mock(GBaseEmbeddingModelConfig.class), () -> recorder, null);

		assertEquals(broken, model.call(new org.springframework.ai.embedding.EmbeddingRequest(List.of("a"), null)));
		ArgumentCaptor<LLMUsageDetailDto> captor = ArgumentCaptor.forClass(LLMUsageDetailDto.class);
		verify(crud, times(1)).enqueueUsage(captor.capture());
		assertEquals(LLMCallOutcome.SUCCESS, captor.getValue().getOutcome());
		assertEquals(0, captor.getValue().getTotalToken());
	}

	@Test
	void embeddingWithoutRecorderIsNotRecorded() {
		EmbeddingModel provider = mock(EmbeddingModel.class);
		when(provider.call(any())).thenReturn(embeddingResponse(12));
		UsageRecordingEmbeddingModel model = new UsageRecordingEmbeddingModel(provider,
				() -> mock(GBaseEmbeddingModelConfig.class), () -> null, null);

		model.embed(List.of("a"));

		verify(provider, times(1)).call(any());
	}

	@Test
	void rawChatModelCallIsRecorded() {
		ILLMSUsageCrudService crud = mock(ILLMSUsageCrudService.class);
		LLMUsageRecorder recorder = new LLMUsageRecorder(crud);
		ChatModel provider = mock(ChatModel.class);
		ChatResponse response = new ChatResponse(List.of(new Generation(new AssistantMessage("ok"))),
				ChatResponseMetadata.builder().usage(new DefaultUsage(50, 5, 55)).build());
		when(provider.call(any(Prompt.class))).thenReturn(response);
		ChatModel model = new UsageRecordingChatModel(provider, mock(GBaseChatModelConfig.class), recorder, null);

		// call(String) keeps its interface default, which ends in the wrapper's call(Prompt).
		assertEquals("ok", model.call("hello"));

		ArgumentCaptor<LLMUsageDetailDto> captor = ArgumentCaptor.forClass(LLMUsageDetailDto.class);
		verify(crud, times(1)).enqueueUsage(captor.capture());
		assertEquals(ModelType.CHAT, captor.getValue().getModelType());
		assertEquals(50, captor.getValue().getInputToken());
		assertEquals(5, captor.getValue().getOutputToken());
		assertEquals(55, captor.getValue().getTotalToken());
	}

	@Test
	void rawChatModelStreamRecordsTimeToFirstTokenWithinTheResponseTime() {
		ILLMSUsageCrudService crud = mock(ILLMSUsageCrudService.class);
		LLMUsageRecorder recorder = new LLMUsageRecorder(crud);
		ChatModel provider = mock(ChatModel.class);
		ChatResponse chunk = new ChatResponse(List.of(new Generation(new AssistantMessage("hi"))),
				ChatResponseMetadata.builder().usage(new DefaultUsage(10, 2, 12)).build());
		when(provider.stream(any(Prompt.class)))
				.thenReturn(reactor.core.publisher.Flux.just(chunk).delayElements(java.time.Duration.ofMillis(30)));
		ChatModel model = new UsageRecordingChatModel(provider, mock(GBaseChatModelConfig.class), recorder, null);

		model.stream(new Prompt("hello")).blockLast();

		// The stream completes on a Reactor thread, where doFinally records just after
		// blockLast() has returned: wait for the record rather than race it.
		ArgumentCaptor<LLMUsageDetailDto> captor = ArgumentCaptor.forClass(LLMUsageDetailDto.class);
		verify(crud, org.mockito.Mockito.timeout(2000).times(1)).enqueueUsage(captor.capture());
		LLMUsageDetailDto detail = captor.getValue();
		assertNotNull(detail.getTimeToFirstToken());
		assertTrue(detail.getTimeToFirstToken() >= 25, "first token after the delay: " + detail.getTimeToFirstToken());
		assertTrue(detail.getTimeToFirstToken() <= detail.getResponseTime());
	}

	@Test
	void blockingChatCallHasNoTimeToFirstToken() {
		ILLMSUsageCrudService crud = mock(ILLMSUsageCrudService.class);
		LLMUsageRecorder recorder = new LLMUsageRecorder(crud);
		ChatModel provider = mock(ChatModel.class);
		when(provider.call(any(Prompt.class))).thenReturn(new ChatResponse(
				List.of(new Generation(new AssistantMessage("ok"))), ChatResponseMetadata.builder().build()));
		new UsageRecordingChatModel(provider, mock(GBaseChatModelConfig.class), recorder, null).call("hello");

		ArgumentCaptor<LLMUsageDetailDto> captor = ArgumentCaptor.forClass(LLMUsageDetailDto.class);
		verify(crud, times(1)).enqueueUsage(captor.capture());
		assertNull(captor.getValue().getTimeToFirstToken());
	}

	@Test
	void pricedEmbeddingCallRecordsItsCost() {
		ILLMSUsageCrudService crud = mock(ILLMSUsageCrudService.class);
		LLMUsageRecorder recorder = new LLMUsageRecorder(crud);
		EmbeddingModel provider = mock(EmbeddingModel.class);
		when(provider.call(any())).thenReturn(embeddingResponse(12));
		// Priced per request, as regolo.ai prices its embedding model.
		GModelPricingConditions pricing = GModelPricingConditions.fromPerTokenPrices(0d, 0d, 0.001, "USD");
		UsageRecordingEmbeddingModel model = new UsageRecordingEmbeddingModel(provider,
				() -> mock(GBaseEmbeddingModelConfig.class), () -> recorder, () -> pricing);

		model.embed(List.of("a"));

		ArgumentCaptor<LLMUsageDetailDto> captor = ArgumentCaptor.forClass(LLMUsageDetailDto.class);
		verify(crud, times(1)).enqueueUsage(captor.capture());
		assertEquals(0.001, captor.getValue().getCost(), 1e-12);
		assertEquals("USD", captor.getValue().getCurrencyCode());
	}

	@Test
	void pricedChatCallCostsItsTokens() {
		ILLMSUsageCrudService crud = mock(ILLMSUsageCrudService.class);
		LLMUsageRecorder recorder = new LLMUsageRecorder(crud);
		ChatModel provider = mock(ChatModel.class);
		when(provider.call(any(Prompt.class))).thenReturn(new ChatResponse(
				List.of(new Generation(new AssistantMessage("ok"))),
				ChatResponseMetadata.builder().usage(new DefaultUsage(1_000_000, 500_000, 1_500_000)).build()));
		// 1.00 per M input + 4.20 per M output
		GModelPricingConditions pricing = GModelPricingConditions.fromPerTokenPrices(1e-6, 4.2e-6, null, "USD");
		new UsageRecordingChatModel(provider, mock(GBaseChatModelConfig.class), recorder, () -> pricing).call("hi");

		ArgumentCaptor<LLMUsageDetailDto> captor = ArgumentCaptor.forClass(LLMUsageDetailDto.class);
		verify(crud, times(1)).enqueueUsage(captor.capture());
		assertEquals(1.0 + 2.1, captor.getValue().getCost(), 1e-9);
	}

	@Test
	void aFailingPricingLookupStillRecordsTheCall() {
		ILLMSUsageCrudService crud = mock(ILLMSUsageCrudService.class);
		LLMUsageRecorder recorder = new LLMUsageRecorder(crud);
		EmbeddingModel provider = mock(EmbeddingModel.class);
		when(provider.call(any())).thenReturn(embeddingResponse(12));
		UsageRecordingEmbeddingModel model = new UsageRecordingEmbeddingModel(provider,
				() -> mock(GBaseEmbeddingModelConfig.class), () -> recorder, () -> {
					throw new IllegalStateException("pricing broken");
				});

		model.embed(List.of("a"));

		ArgumentCaptor<LLMUsageDetailDto> captor = ArgumentCaptor.forClass(LLMUsageDetailDto.class);
		verify(crud, times(1)).enqueueUsage(captor.capture());
		assertNull(captor.getValue().getCost());
		assertEquals(12, captor.getValue().getInputToken());
	}

	@Test
	void failedRankingIsRecordedAsError() {
		ILLMSUsageCrudService crud = mock(ILLMSUsageCrudService.class);
		LLMUsageRecorder recorder = new LLMUsageRecorder(crud);
		RankerModel failing = input -> {
			throw new IllegalStateException("down");
		};
		@SuppressWarnings("unchecked")
		IGConfigurableRankerModel<GBaseRankerModelConfig> provider = mock(IGConfigurableRankerModel.class);
		when(provider.getRankerModel()).thenReturn(failing);
		when(provider.getConfig()).thenReturn(mock(GBaseRankerModelConfig.class));
		UsageRecordingRankerModel<GBaseRankerModelConfig> model = new UsageRecordingRankerModel<>(provider, recorder);

		assertThrows(IllegalStateException.class, () -> model.getRankerModel().call(null));

		ArgumentCaptor<LLMUsageDetailDto> captor = ArgumentCaptor.forClass(LLMUsageDetailDto.class);
		verify(crud, times(1)).enqueueUsage(captor.capture());
		assertEquals(ModelType.RANKER, captor.getValue().getModelType());
		assertEquals(LLMCallOutcome.ERROR, captor.getValue().getOutcome());
	}

	@Test
	void unrecordedEmbeddingNeverTouchesTheUsageService() {
		ILLMSUsageCrudService crud = mock(ILLMSUsageCrudService.class);
		EmbeddingModel provider = mock(EmbeddingModel.class);
		when(provider.dimensions()).thenReturn(3);
		UsageRecordingEmbeddingModel model = new UsageRecordingEmbeddingModel(provider,
				() -> mock(GBaseEmbeddingModelConfig.class), () -> new LLMUsageRecorder(crud), null);

		assertEquals(3, model.dimensions());

		verify(crud, never()).enqueueUsage(any());
	}
}
