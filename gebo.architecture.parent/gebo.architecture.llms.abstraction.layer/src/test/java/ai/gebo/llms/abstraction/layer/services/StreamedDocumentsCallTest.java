/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import reactor.core.publisher.Flux;

/**
 * Pins the streamed documents call: the model's answer arrives in pieces and is
 * returned whole, with the same parameters as the blocking call, never through it.
 */
class StreamedDocumentsCallTest {

	static class Service extends BaseLLMSInvokingService {
		String call(IGConfigurableChatModel model) throws Exception {
			return streamLLMWithDocumentsAndConsolidation(model, new GPromptTemplateConfig(),
					mock(IChatRequestContext.class), List.of("doc"), "report so far", Map.of("extra", "value"));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	void theStreamedPiecesAreJoinedWithTheCallParameters() throws Exception {
		IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
		when(model.streamStringResponse(any(), anyMap(), any()))
				.thenReturn((Flux) Flux.just("analysis ", "of the batch", "\nIRRILEVANT=a1"));

		String answer = new Service().call(model);

		assertEquals("analysis of the batch\nIRRILEVANT=a1", answer);
		ArgumentCaptor<Map> params = ArgumentCaptor.forClass(Map.class);
		verify(model).streamStringResponse(any(), params.capture(), any());
		assertEquals(List.of("doc"), params.getValue().get(BaseLLMSInvokingService.DOCUMENTS_TEMPLATE_VARIABLE));
		assertEquals("report so far", params.getValue().get(BaseLLMSInvokingService.CONSOLIDATED_TEMPLATE_VARIABLE));
		assertEquals("value", params.getValue().get("extra"));
		verify(model, never()).textResponse(any(), anyMap(), any());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	void theThinkingIsRemovedWhenTheModelThinks() throws Exception {
		IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
		when(model.isApplyThinkingMarkupHandling()).thenReturn(true);
		when(model.streamStringResponse(any(), anyMap(), any()))
				.thenReturn((Flux) Flux.just("<think>reasoning</think>", "the analysis"));

		assertEquals("the analysis", new Service().call(model).trim());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	void anAnswerGoingAstrayIsStoppedAndWhatCameIsReturned() throws Exception {
		IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
		java.util.concurrent.atomic.AtomicBoolean cancelled = new java.util.concurrent.atomic.AtomicBoolean();
		java.util.concurrent.atomic.AtomicInteger sent = new java.util.concurrent.atomic.AtomicInteger();
		when(model.streamStringResponse(any(), anyMap(), any())).thenReturn((Flux) Flux
				.just("analysis", "\nLIST=", "1,", "1,", "1,", "1,", "1,", "1,").doOnNext(piece -> sent.incrementAndGet())
				.doOnCancel(() -> cancelled.set(true)));
		Service service = new Service() {
			@Override
			String call(IGConfigurableChatModel model) throws Exception {
				return streamLLMWithDocumentsAndConsolidation(model, new GPromptTemplateConfig(),
						mock(IChatRequestContext.class), List.of("doc"), "", Map.of(),
						text -> text.toString().split(",").length > 3);
			}
		};

		assertEquals("analysis\nLIST=1,1,1,1,", service.call(model));
		assertTrue(cancelled.get(), "the stream is cancelled");
		assertEquals(6, sent.get(), "nothing more is read");
	}
}
