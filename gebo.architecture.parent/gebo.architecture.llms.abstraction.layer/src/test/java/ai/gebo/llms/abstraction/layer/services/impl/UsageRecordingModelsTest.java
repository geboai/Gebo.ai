package ai.gebo.llms.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
		UsageRecordingEmbeddingModel model = new UsageRecordingEmbeddingModel(provider, () -> config, () -> recorder);

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
	void embeddingWithoutRecorderIsNotRecorded() {
		EmbeddingModel provider = mock(EmbeddingModel.class);
		when(provider.call(any())).thenReturn(embeddingResponse(12));
		UsageRecordingEmbeddingModel model = new UsageRecordingEmbeddingModel(provider,
				() -> mock(GBaseEmbeddingModelConfig.class), () -> null);

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
		ChatModel model = new UsageRecordingChatModel(provider, mock(GBaseChatModelConfig.class), recorder);

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
				() -> mock(GBaseEmbeddingModelConfig.class), () -> new LLMUsageRecorder(crud));

		assertEquals(3, model.dimensions());

		verify(crud, never()).enqueueUsage(any());
	}
}
