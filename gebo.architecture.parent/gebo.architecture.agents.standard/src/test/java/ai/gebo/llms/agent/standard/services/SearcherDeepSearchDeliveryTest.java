/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standard.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.SearchAgentCommand;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.agent.standardtools.DeepSearchToolAnalysis;
import ai.gebo.llms.agent.standardtools.DeepSearchToolsSupport;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.services.IGRankerService;
import ai.gebo.llms.deepsearch.service.DeepSearchAnalysisOutcome;
import ai.gebo.model.DocumentMetaInfos;
import reactor.core.publisher.Flux;

/**
 * A search agent of the networks hands over one analysis instead of the fragments
 * when the command asks a deep search: the reduce of the deep search tools, run on
 * what its searches found. The analysis document names the documents it rests on,
 * whose refs are published for the answer. Without the request, or without the deep
 * search deployed, the fragments are handed over as retrieved.
 */
class SearcherDeepSearchDeliveryTest {

	private final DeepSearchToolsSupport support = mock(DeepSearchToolsSupport.class);
	private final DeepSearchToolAnalysis analysis = mock(DeepSearchToolAnalysis.class);
	private final IGRankerService ranker = mock(IGRankerService.class);
	private final IGRuntimeBinder binder = mock(IGRuntimeBinder.class);

	private static Document fragment(String id, String code, String name) {
		return new Document(id, "text of " + id,
				Map.of(DocumentMetaInfos.CONTENT_CODE, code, DocumentMetaInfos.GEBO_FILE_NAME, name));
	}

	private static SearchAgentCommand command(Boolean deepSearch, SearchAgentCommand.DeepSearchDepth depth) {
		SearchAgentCommand command = new SearchAgentCommand();
		command.setCommand("what the policy says about remote work");
		command.setTopK(10);
		command.setExecuteRanking(true);
		command.setDeepSearch(deepSearch);
		command.setDeepSearchDepth(depth);
		return command;
	}

	private InternalKnowledgeBaseSearchNetworkAgentService searcher(boolean deepSearchDeployed) {
		when(binder.getImplementationOf(DeepSearchToolsSupport.class)).thenReturn(deepSearchDeployed ? support : null);
		when(ranker.isRankerConfigured()).thenReturn(true);
		IGChatModelRuntimeConfigurationDao chatModelsDao = mock(IGChatModelRuntimeConfigurationDao.class);
		when(chatModelsDao.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES))
				.thenReturn(mock(IGConfigurableChatModel.class));
		when(support.chatModelsDao()).thenReturn(chatModelsDao);
		when(support.analysis()).thenReturn(analysis);
		when(support.maxAnalysisTokens()).thenReturn(16000);
		when(support.searchTopK()).thenReturn(30);
		return new InternalKnowledgeBaseSearchNetworkAgentService(null, null, null, null, null, binder, null, ranker,
				null, null);
	}

	private void analysisAnswers(String text, String... unreadFragmentIds) {
		when(analysis.analyze(any(), any(), any(), any(), anyString(), any(), any(), any(), any(), any()))
				.thenAnswer(invocation -> {
					DeepSearchAnalysisOutcome outcome = invocation.getArgument(9);
					for (String id : unreadFragmentIds) {
						outcome.getUnreadFragmentIds().add(id);
					}
					return Flux.just(text);
				});
	}

	@Test
	void theDeepSearchHandsOverOneAnalysisRestingOnTheDocumentsRead() throws Exception {
		InternalKnowledgeBaseSearchNetworkAgentService searcher = searcher(true);
		analysisAnswers("The policy allows remote work three days a week.", "f3");
		AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext();
		List<Document> fragments = List.of(fragment("f1", "docA", "policy.pdf"), fragment("f2", "docA", "policy.pdf"),
				fragment("f3", "docB", "faq.docx"));
		List<String> notices = new ArrayList<>();
		AgentNetworkParticipant persona = new AgentNetworkParticipant();
		persona.setAgentConfigCode("searcher");

		List<Document> delivered = searcher.deliverDocuments(fragments, command(true, SearchAgentCommand.DeepSearchDepth.BROAD),
				null, mock(IGConfigurableChatModel.class), session, persona, null, null, notices);

		assertEquals(1, delivered.size(), "one analysis instead of the fragments");
		Document analysisDocument = delivered.get(0);
		assertEquals("The policy allows remote work three days a week.", analysisDocument.getText());
		assertEquals(Boolean.TRUE, analysisDocument.getMetadata()
				.get(GAbstractStandardDocumentsSearchAgentService.DEEP_SEARCH_ANALYSIS));
		assertEquals(List.of("docA"), analysisDocument.getMetadata()
				.get(GAbstractStandardDocumentsSearchAgentService.DEEP_SEARCH_SOURCE_CODES), "docB was left unread");
		assertFalse(analysisDocument.getMetadata().containsKey(DocumentMetaInfos.CONTENT_CODE),
				"the analysis is no document of the answer by itself");
		assertTrue(String.valueOf(analysisDocument.getMetadata().get(DocumentMetaInfos.TITLE)).contains("Deep search analysis"));
		// the readers are told what was analysed
		assertEquals(1, notices.size());
		assertTrue(notices.get(0).contains("2 of 3 fragments"), notices.get(0));
		assertTrue(notices.get(0).contains("rests on 1 document(s)"), notices.get(0));
		assertTrue(notices.get(0).contains("1 fragment(s) were left unread"), notices.get(0));
		// the refs of the sources are published for the answer's documents
		@SuppressWarnings("unchecked")
		Map<String, GResponseDocumentRef> refs = (Map<String, GResponseDocumentRef>) session.getEnvironment()
				.get(StandardAgentsNetworkEnvironmentEntries.CHAT_WITH_DOC_REFS_BY_CODE);
		assertEquals(List.of("docA"), new ArrayList<>(refs.keySet()));
	}

	@Test
	void withoutTheRequestTheFragmentsAreHandedOverAsRetrieved() throws Exception {
		InternalKnowledgeBaseSearchNetworkAgentService searcher = searcher(true);
		List<Document> fragments = List.of(fragment("f1", "docA", "policy.pdf"));
		List<String> notices = new ArrayList<>();

		List<Document> plain = searcher.deliverDocuments(fragments, command(null, null), null, null,
				new AgentsCollaborationSessionContext(), new AgentNetworkParticipant(), null, null, notices);
		List<Document> refused = searcher.deliverDocuments(fragments, command(false, null), null, null,
				new AgentsCollaborationSessionContext(), new AgentNetworkParticipant(), null, null, notices);

		assertSame(fragments, plain);
		assertSame(fragments, refused);
		assertTrue(notices.isEmpty());
	}

	@Test
	void withoutTheDeepSearchDeployedTheSearcherOnlySearches() throws Exception {
		InternalKnowledgeBaseSearchNetworkAgentService searcher = searcher(false);
		SearchAgentCommand command = command(true, SearchAgentCommand.DeepSearchDepth.EXHAUSTIVE);
		List<Document> fragments = List.of(fragment("f1", "docA", "policy.pdf"));

		assertFalse(searcher.deepSearchRequested(command));
		assertSame(fragments, searcher.deliverDocuments(fragments, command, null, null,
				new AgentsCollaborationSessionContext(), new AgentNetworkParticipant(), null, null, new ArrayList<>()));
		// a plain search: ranking as asked, retrieval widened for it
		assertTrue(searcher.rankingRequested(command));
		assertEquals(20, searcher.retrievalTopK(command));
		assertFalse(searcher.getAgentCapabilities(new GAgentConfig()).getCapabilities().stream()
				.anyMatch(x -> x.contains("Deep search")));
	}

	@Test
	void aDeepSearchRetrievesWideAndLeavesTheJudgementToTheAnalysis() {
		InternalKnowledgeBaseSearchNetworkAgentService searcher = searcher(true);
		SearchAgentCommand command = command(true, null);

		assertTrue(searcher.deepSearchRequested(command));
		assertEquals(30, searcher.retrievalTopK(command), "at least as many documents as the deep search tools");
		assertFalse(searcher.rankingRequested(command), "the analysis judges every fragment found");
		assertTrue(searcher.getAgentCapabilities(new GAgentConfig()).getCapabilities().stream()
				.anyMatch(x -> x.contains("Deep search")));
	}

	@Test
	void whenTheAnalysisFailsTheFragmentsAreHandedOverAndTheReadersTold() throws Exception {
		InternalKnowledgeBaseSearchNetworkAgentService searcher = searcher(true);
		when(analysis.analyze(any(), any(), any(), any(), anyString(), any(), any(), any(), any(), any()))
				.thenThrow(new IllegalStateException("provider down"));
		List<Document> fragments = List.of(fragment("f1", "docA", "policy.pdf"));
		List<String> notices = new ArrayList<>();

		List<Document> delivered = searcher.deliverDocuments(fragments, command(true, null), null,
				mock(IGConfigurableChatModel.class), new AgentsCollaborationSessionContext(),
				new AgentNetworkParticipant(), null, null, notices);

		assertSame(fragments, delivered);
		assertEquals(1, notices.size());
		assertTrue(notices.get(0).contains("could not be run"), notices.get(0));
	}
}
