/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.chat.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.security.services.ReactiveIdentityUtil;

/**
 * Pins how the report writer fits its input: its own last output whole, and the
 * evidence of the cycle reduced by the extraction prompt until it fits the final
 * writing call.
 */
class ReportWriterEvidenceBudgetTest {

	private static final IGDocumentContentRendererProvider NO_RENDERER = new IGDocumentContentRendererProvider() {
		@Override
		public <T> IGDocumentContentRenderer<T> get(T doc) {
			return null;
		}
	};

	private static String words(int count, String prefix) {
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < count; i++) {
			text.append(prefix).append(i % 97).append(' ');
		}
		return text.toString();
	}

	private static int tokens(String text) {
		return ITokensCountable.stringsTokensSize(text);
	}

	private static ReportWriterReactiveAgentServiceImpl writer() {
		return new ReportWriterReactiveAgentServiceImpl(null, null, null, null, null, null, NO_RENDERER);
	}

	private static AgentsExchangeMessage<String> instruction(String text) {
		AgentsExchangeMessage<String> message = new AgentsExchangeMessage<String>();
		message.setPayload(text);
		return message;
	}

	@Test
	void theLastOutputGoesInWholeAndTheOlderTurnsAsALine() {
		AgentPrivateSessionContext<String, String> memory = new AgentPrivateSessionContext<String, String>();
		memory.addInteraction(instruction("first instruction " + words(300, "old")), 1, words(2000, "firstdraft"));
		memory.addInteraction(instruction("second instruction"), 5, words(2000, "lastdraft"));

		String rendered = writer().render(memory, 6, 100_000);

		assertTrue(rendered.contains(words(2000, "lastdraft").strip()), "the last output is whole");
		assertFalse(rendered.contains("firstdraft"), "a superseded draft is not repeated");
		assertTrue(rendered.contains("first instruction"), "a superseded turn is recalled by its instruction");
		assertTrue(rendered.contains("second instruction"));
		assertTrue(tokens(rendered) < tokens(words(2000, "lastdraft")) + 200, "only the last draft weighs");
	}

	@Test
	void extractionsAreGroupedInOrderWithinTheBudget() {
		List<String> extractions = List.of(words(100, "a"), words(100, "b"), words(100, "c"), words(100, "d"));
		int one = tokens(words(100, "a"));

		List<String> groups = ReportWriterReactiveAgentServiceImpl.groupToBudget(extractions, one * 2 + 10);

		assertEquals(2, groups.size());
		assertTrue(groups.get(0).startsWith("a0") && groups.get(0).contains("b0"));
		assertTrue(groups.get(1).startsWith("c0") && groups.get(1).contains("d0"));
	}

	@Test
	void anExtractionWithNothingRelevantIsDropped() {
		assertFalse(ReportWriterReactiveAgentServiceImpl.isRelevantExtraction(" NO RELEVANT FACTS \n"));
		assertFalse(ReportWriterReactiveAgentServiceImpl.isRelevantExtraction(""));
		assertTrue(ReportWriterReactiveAgentServiceImpl.isRelevantExtraction("- a fact [source]"));
	}

	@Test
	void extractionsOverTheFinalBudgetAreReducedAgainWithTheExtractionPrompt() throws Exception {
		IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
		GPromptTemplateConfig extractorPrompt = new GPromptTemplateConfig();
		AtomicInteger calls = new AtomicInteger();
		when(model.textResponse(any(), any(), any())).thenAnswer(invocation -> {
			calls.incrementAndGet();
			Map<String, Object> params = invocation.getArgument(1);
			assertNotNull(params.get("SHARED_CONTEXT"), "each group is the shared context of its call");
			return "- merged fact " + calls.get() + " [source " + calls.get() + "]";
		});
		List<String> extractions = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			extractions.add(words(200, "fact" + i));
		}
		int one = tokens(words(200, "fact0"));
		Map<String, Object> extractorParams = new HashMap<>();
		extractorParams.put("INPUT", "write the report");
		AgentNetworkParticipant persona = mock(AgentNetworkParticipant.class);
		when(persona.getNetworkAgentName()).thenReturn("writer");

		List<String> reduced = writer().reduceExtractions(extractions, one * 3, one * 2 + 20, extractorParams, model,
				extractorPrompt, null, ReactiveIdentityUtil.create(), new AtomicInteger(), mock(ISinkUIEmitter.class),
				persona, mock(INotificationSink.class));

		assertTrue(tokens(String.join("\r\n", reduced)) <= one * 3, "the extractions fit the final call");
		assertTrue(reduced.stream().allMatch(x -> x.startsWith("- merged fact")), "reduced by the extraction prompt");
		verify(model, atLeastOnce()).textResponse(any(), any(), any());
	}

	@Test
	void extractionsWithinTheFinalBudgetAreNotReduced() throws Exception {
		IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
		List<String> extractions = List.of("- fact one [a]", "- fact two [b]");

		List<String> reduced = writer().reduceExtractions(extractions, 10_000, 5_000, new HashMap<>(), model,
				new GPromptTemplateConfig(), null, ReactiveIdentityUtil.create(), new AtomicInteger(),
				mock(ISinkUIEmitter.class), mock(AgentNetworkParticipant.class), mock(INotificationSink.class));

		assertEquals(extractions, reduced);
		verify(model, org.mockito.Mockito.never()).textResponse(any(), any(), any());
	}

	@Test
	void theExtractionPromptIsInTheLibraryWithItsPlaceholders() throws Exception {
		String library = resource("/agents-prompt-library/agents-prompt-library.yml");
		assertTrue(library.contains("promptUse: report-evidence-extractor-prompt"));
		assertTrue(library.contains("code: report-evidence-extractor-prompt"));
		String user = resource("/agents-prompt-library/en/report-evidence-extractor-user-prompt.txt");
		for (String placeholder : List.of("{question}", "{INPUT}", "{REQUIRED_AGENT_COMPLETENESS}", "{SHARED_CONTEXT}")) {
			assertTrue(user.contains(placeholder), placeholder);
		}
		String system = resource("/agents-prompt-library/en/report-evidence-extractor-system-prompt.txt");
		assertTrue(system.contains(ReportWriterReactiveAgentServiceImpl.NO_RELEVANT_FACTS));
		assertFalse(system.contains("{"), "the system prompt declares no placeholder");
	}

	private static String resource(String path) throws Exception {
		try (InputStream in = ReportWriterEvidenceBudgetTest.class.getResourceAsStream(path)) {
			assertNotNull(in, path);
			return new String(in.readAllBytes(), StandardCharsets.UTF_8);
		}
	}
}
