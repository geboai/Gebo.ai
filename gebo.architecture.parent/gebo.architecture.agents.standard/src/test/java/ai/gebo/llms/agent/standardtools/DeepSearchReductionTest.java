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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ai.document.Document;

import ai.gebo.architecture.agents.model.SearchAgentCommand;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolParam.Depth;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.deepsearch.service.DeepSearchAnalysisOutcome;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;

/**
 * The reduce step of a search agent's deep search is the deep search tools' analysis:
 * the fragments found are analysed against the command, sized by the depth, and the
 * analysis rests on the documents whose fragments were read.
 */
class DeepSearchReductionTest {

	private static Document fragment(String id, String code) {
		return new Document(id, "text of " + id, Map.of(DocumentMetaInfos.CONTENT_CODE, code));
	}

	private final DeepSearchToolAnalysis analysis = mock(DeepSearchToolAnalysis.class);
	private final IGChatModelRuntimeConfigurationDao chatModelsDao = mock(IGChatModelRuntimeConfigurationDao.class);
	private final IGConfigurableChatModel serviceModel = mock(IGConfigurableChatModel.class);
	private final IGConfigurableChatModel agentModel = mock(IGConfigurableChatModel.class);
	private final DeepSearchToolsSupport support = mock(DeepSearchToolsSupport.class);

	private DeepSearchToolsSupport support() {
		when(support.analysis()).thenReturn(analysis);
		when(support.chatModelsDao()).thenReturn(chatModelsDao);
		when(support.maxAnalysisTokens()).thenReturn(16000);
		when(chatModelsDao.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES)).thenReturn(serviceModel);
		when(serviceModel.getCode()).thenReturn("service-model");
		when(agentModel.getCode()).thenReturn("agent-model");
		return support;
	}

	/** The analysis answers the given text, leaving the given fragments unread. */
	private void analysisAnswers(String text, String notCovered, String... unreadFragmentIds) {
		when(analysis.analyze(any(), any(), any(), any(), anyString(), any(), any(), any(), any(), any()))
				.thenAnswer(invocation -> {
					DeepSearchAnalysisOutcome outcome = invocation.getArgument(9);
					for (String id : unreadFragmentIds) {
						outcome.getUnreadFragmentIds().add(id);
					}
					outcome.setNotCovered(notCovered);
					return Flux.just(text);
				});
	}

	@Test
	void theDepthOfTheCommandMapsToTheToolsOneBroadByDefault() {
		assertEquals(Depth.BROAD, DeepSearchReduction.depthOf(null));
		assertEquals(Depth.FOCUSED, DeepSearchReduction.depthOf(SearchAgentCommand.DeepSearchDepth.FOCUSED));
		assertEquals(Depth.BROAD, DeepSearchReduction.depthOf(SearchAgentCommand.DeepSearchDepth.BROAD));
		assertEquals(Depth.EXHAUSTIVE, DeepSearchReduction.depthOf(SearchAgentCommand.DeepSearchDepth.EXHAUSTIVE));
		// the sizes are the deep search tools' ones
		assertEquals(AbstractDeepSearchTool.fragmentsPerDocument(Depth.EXHAUSTIVE),
				DeepSearchReduction.fragmentsPerDocument(Depth.EXHAUSTIVE));
		assertEquals(KnowledgeBaseDeepSearchTool.FRAGMENTS_PER_DOCUMENT,
				DeepSearchReduction.knowledgeBaseFragmentsPerDocument());
		assertEquals(SearchServiceDeepSearchTool.MAX_FRAGMENTS_PER_DOCUMENT,
				DeepSearchReduction.externalFragmentsPerDocument());
	}

	@Test
	void theFragmentsAreAnalysedAgainstTheCommandAndTheAnalysisRestsOnTheDocumentsRead() {
		support();
		analysisAnswers("The analysis", "the 2026 figures", "f2");
		IChatRequestContext context = mock(IChatRequestContext.class);
		when(context.getRequestID()).thenReturn("req-1");
		when(context.getUserLanguage()).thenReturn("it");
		List<Document> fragments = List.of(fragment("f1", "docA"), fragment("f2", "docB"), fragment("f3", "docA"),
				fragment("f4", "docC"));

		DeepSearchReduction.Outcome outcome = DeepSearchReduction.reduce(support, "searcher", "the command",
				Depth.EXHAUSTIVE, fragments, context, agentModel, mock(ReactiveIdentityUtil.class), null);

		assertTrue(outcome.hasAnalysis());
		assertEquals("The analysis", outcome.analysis());
		assertEquals(4, outcome.fragmentsFound());
		assertEquals(3, outcome.fragmentsRead());
		assertEquals(List.of("docA", "docC"), outcome.sourceCodes(), "docB was left unread: not a source");
		assertEquals("the 2026 figures", outcome.notCovered());

		// the analysis is the tools' one: the command is its question, the depth its deliverable
		ArgumentCaptor<IChatRequestContext> analysed = ArgumentCaptor.forClass(IChatRequestContext.class);
		ArgumentCaptor<String> note = ArgumentCaptor.forClass(String.class);
		verify(analysis).analyze(any(), analysed.capture(), any(), eq(DeliverableIntent.ANALISYS), note.capture(),
				eq(agentModel), eq(serviceModel), any(), any(), any());
		assertEquals("the command", analysed.getValue().getActualUserRequest());
		assertEquals("req-1", analysed.getValue().getRequestID());
		assertEquals("it", analysed.getValue().getUserLanguage());
		assertTrue(note.getValue().contains(AbstractDeepSearchTool.TOOL_COMPLETENESS_NOTE));
		assertTrue(note.getValue().contains("about " + AbstractDeepSearchTool.depthWords(Depth.EXHAUSTIVE) + " words"));
	}

	@Test
	void noAnalysisWhenNothingWasReadOrFound() {
		support();
		analysisAnswers("A text nobody read", null, "f1", "f2");
		List<Document> fragments = List.of(fragment("f1", "docA"), fragment("f2", "docB"));

		DeepSearchReduction.Outcome nothingRead = DeepSearchReduction.reduce(support, "searcher", "the command",
				null, fragments, null, agentModel, null, null);
		assertFalse(nothingRead.hasAnalysis());
		assertEquals(2, nothingRead.fragmentsFound());
		assertEquals(0, nothingRead.fragmentsRead());

		DeepSearchReduction.Outcome nothingFound = DeepSearchReduction.reduce(support, "searcher", "the command",
				Depth.BROAD, List.of(), null, agentModel, null, null);
		assertFalse(nothingFound.hasAnalysis());
		assertNull(nothingFound.analysis());
	}

	@Test
	void noAnalysisWithoutAServiceModel() {
		support();
		when(chatModelsDao.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES)).thenReturn(null);

		DeepSearchReduction.Outcome outcome = DeepSearchReduction.reduce(support, "searcher", "the command",
				Depth.BROAD, List.of(fragment("f1", "docA")), null, agentModel, null, null);

		assertFalse(outcome.hasAnalysis());
	}
}
