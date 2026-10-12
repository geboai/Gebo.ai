/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Vector;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.llms.abstraction.layer.services.IGProgressNotifier;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.deepsearch.service.DeepSearchAnalysisOutcome;
import ai.gebo.llms.deepsearch.service.impl.DeepSearchAnalysis;
import ai.gebo.llms.deepsearch.service.impl.DeepSearchQuotations;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;

/**
 * Pins that the deep search tools analyse with the deep search pipelines' analysis
 * ({@link DeepSearchAnalysis}): the tool's outcome given to it, its quotations the
 * outcome's, no pipeline to notify of a model failure.
 */
class DeepSearchToolAnalysisTest {

	@Test
	void theToolsAnalyseWithThePipelinesAnalysisHandingTheirOutcome() {
		final DeepSearchAnalysis shared = mock(DeepSearchAnalysis.class);
		when(shared.analyze(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(),
				anyString())).thenReturn(Flux.just("The ", "analysis."));
		final DeepSearchToolAnalysis tool = new DeepSearchToolAnalysis(shared);
		final DeepSearchAnalysisOutcome outcome = new DeepSearchAnalysisOutcome();

		final String text = String.join("",
				tool.analyze(Flux.empty(), null, ReactiveIdentityUtil.create(), DeliverableIntent.ANALISYS, "note", null,
						null, new Vector<>(), IGProgressNotifier.NONE, outcome).collectList().block());

		assertEquals("The analysis.", text);
		final ArgumentCaptor<DeepSearchQuotations> quotations = ArgumentCaptor.forClass(DeepSearchQuotations.class);
		final ArgumentCaptor<Runnable> onFailure = ArgumentCaptor.forClass(Runnable.class);
		verify(shared).analyze(any(), any(), any(), eq(DeliverableIntent.ANALISYS), eq("note"), any(), any(), any(),
				any(), quotations.capture(), any(), eq(outcome), onFailure.capture(), eq("Deep search tool"));
		assertSame(outcome.getQuotations(), quotations.getValue(), "the tool lists the quotations of its outcome");
		assertNull(onFailure.getValue(), "a tool has no pipeline to notify");
	}
}
