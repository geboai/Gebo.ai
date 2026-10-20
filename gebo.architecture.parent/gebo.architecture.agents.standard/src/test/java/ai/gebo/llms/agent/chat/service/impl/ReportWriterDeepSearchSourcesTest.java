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

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.llms.agent.standard.services.GAbstractStandardDocumentsSearchAgentService;
import ai.gebo.llms.agent.standard.services.StandardAgentsNetworkEnvironmentEntries;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.model.DocumentMetaInfos;

/**
 * The documents of the answer: the fragments the searchers handed over, and the
 * documents a deep search analysis rests on, through the refs its searcher
 * published; a source with no published ref is left out, never made up.
 */
class ReportWriterDeepSearchSourcesTest {

	private static Document fragment(String id, String code) {
		return new Document(id, "text of " + id, Map.of(DocumentMetaInfos.CONTENT_CODE, code));
	}

	private static GResponseDocumentRef ref(String code) {
		GResponseDocumentRef ref = new GResponseDocumentRef();
		ref.setDocumentCode(code);
		return ref;
	}

	@Test
	void theAnswerListsTheDocumentsTheAnalysisRestsOn() {
		ReportWriterReactiveAgentServiceImpl writer = new ReportWriterReactiveAgentServiceImpl(null, null, null, null,
				null, null, null);
		AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext();
		Map<String, GResponseDocumentRef> published = new ConcurrentHashMap<>();
		published.put("docA", ref("docA"));
		published.put("docC", ref("docC"));
		session.getEnvironment().put(StandardAgentsNetworkEnvironmentEntries.CHAT_WITH_DOC_REFS_BY_CODE, published);
		Document analysis = new Document("The analysis",
				Map.of(GAbstractStandardDocumentsSearchAgentService.DEEP_SEARCH_ANALYSIS, Boolean.TRUE,
						GAbstractStandardDocumentsSearchAgentService.DEEP_SEARCH_SOURCE_CODES,
						List.of("docA", "docB", "docC")));
		session.addContribution(
				AgentsExchangeMessage.of(session, "deepSearcher", "writer", List.of(analysis), MessageSemantic.RESPONSE),
				1);
		session.addContribution(AgentsExchangeMessage.of(session, "searcher", "writer",
				List.of(fragment("f1", "docC"), fragment("f2", "docD")), MessageSemantic.RESPONSE), 2);

		List<String> codes = writer.documentsList(session).stream().map(GResponseDocumentRef::getDocumentCode)
				.toList();

		// the fragments' documents, then the analysis' sources not among them; docB has no ref
		assertEquals(List.of("docC", "docD", "docA"), codes);
	}
}
