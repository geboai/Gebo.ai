/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.a2aserver.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.a2aproject.sdk.spec.DataPart;
import org.a2aproject.sdk.spec.Message;
import org.a2aproject.sdk.spec.Part;
import org.a2aproject.sdk.spec.TextPart;
import org.junit.jupiter.api.Test;

import ai.gebo.architecture.a2aserver.config.A2AExportKindsConfig;
import ai.gebo.architecture.a2aserver.model.A2AExportedAgent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;

/**
 * Pins what an A2A caller is given back: the answer, the documents it rests on
 * and the account of the run, and which frame of a streamed run ends it.
 */
class A2AResultAdapterTest {

	private final A2AResultAdapter adapter = new A2AResultAdapter();

	private static GeboChatResponse answered() {
		GeboChatResponse response = new GeboChatResponse();
		response.setQueryResponse("The Secret Doctrine is in the knowledge base.");
		response.setUserChatContextCode("a-conversation");
		response.setUsedChatModelCode("gpt-4.1");
		response.setUsedChatModelProvider("openai");
		GResponseDocumentRef document = new GResponseDocumentRef();
		document.setUuid("doc-uuid");
		document.setDocumentCode("doc-code");
		document.setDescription("The Secret Doctrine, Vol. 1");
		document.setContentType("application/pdf");
		document.setExtension("pdf");
		document.setKnowledgeBaseCode("biblioteca");
		response.setDocumentsRef(List.of(document));
		return response;
	}

	private static Map<String, Object> dataOf(Part<?> part) {
		return (Map<String, Object>) ((DataPart) part).data();
	}

	@Test
	void theAnswerTravelsWithTheDocumentsItRestsOn() {
		Message message = adapter.messageOf(answered(), null, "a-conversation");

		assertEquals(Message.Role.ROLE_AGENT, message.role());
		assertNotNull(message.messageId());
		List<Part<?>> parts = message.parts();
		assertTrue(parts.size() >= 2, "the text, and what the answer rests on");
		assertTrue(parts.get(0) instanceof TextPart);
		assertEquals("The Secret Doctrine is in the knowledge base.", ((TextPart) parts.get(0)).text());

		Map<String, Object> citations = parts.stream().filter(p -> p instanceof DataPart)
				.map(A2AResultAdapterTest::dataOf)
				.filter(d -> A2AResultAdapter.CITATIONS_KIND.equals(d.get("kind"))).findFirst().orElse(null);
		assertNotNull(citations, "the documents are sent back, not dropped at the boundary");
		List<Map<String, Object>> documents = (List<Map<String, Object>>) citations.get("documents");
		assertEquals(1, documents.size());
		assertEquals("doc-uuid", documents.get(0).get("uuid"));
		assertEquals("biblioteca", documents.get(0).get("knowledgeBaseCode"));
		assertEquals("application/pdf", documents.get(0).get("contentType"));
	}

	@Test
	void theAccountOfTheRunTravelsTooSoACallerKnowsHowItWasAnswered() {
		Message message = adapter.messageOf(answered(), null, null);

		Map<String, Object> runInfo = message.parts().stream().filter(p -> p instanceof DataPart)
				.map(A2AResultAdapterTest::dataOf)
				.filter(d -> A2AResultAdapter.RUN_INFO_KIND.equals(d.get("kind"))).findFirst().orElse(null);
		assertNotNull(runInfo);
		assertEquals("gpt-4.1", runInfo.get("chatModelCode"));
		assertEquals("openai", runInfo.get("chatModelProvider"));
		assertEquals("a-conversation", runInfo.get("chatContextCode"));
	}

	@Test
	void anAnswerWithoutDocumentsSaysSoByCarryingNone() {
		GeboChatResponse bare = new GeboChatResponse();
		bare.setQueryResponse("Seventeen times twenty-three is 391.");

		Message message = adapter.messageOf(bare, null, null);

		boolean citations = message.parts().stream().filter(p -> p instanceof DataPart)
				.map(A2AResultAdapterTest::dataOf)
				.anyMatch(d -> A2AResultAdapter.CITATIONS_KIND.equals(d.get("kind")));
		assertFalse(citations, "no documents means no citations part, not an empty one");
	}

	@Test
	void aFrameCarryingTextIsAChunkAndDoesNotEndTheAnswer() {
		GeboChatMessageEnvelope<String> chunk = new GeboChatMessageEnvelope<>("a piece of the answer");

		assertEquals("a piece of the answer", adapter.chunkOf(chunk));
		assertFalse(adapter.isLast(chunk));
		assertNull(adapter.responseOf(chunk));
	}

	@Test
	void theFrameMarkedAsTheLastOneEndsTheAnswer() {
		GeboChatMessageEnvelope<String> marked = new GeboChatMessageEnvelope<>("the end", true);
		assertTrue(adapter.isLast(marked), "a caller that waits reads this one");
	}

	@Test
	void aFrameCarryingTheResponseEndsTheAnswerAndCarriesIt() {
		GeboChatResponse response = answered();
		GeboChatMessageEnvelope<GeboChatResponse> envelope = new GeboChatMessageEnvelope<>(response);

		assertTrue(adapter.isLast(envelope), "the response is the answer, so its frame ends it");
		assertEquals(response, adapter.responseOf(envelope));
		assertNull(adapter.chunkOf(envelope), "it is not a chunk of text");
		assertEquals("The Secret Doctrine is in the knowledge base.", adapter.answerTextOf(response));
	}

	@Test
	void networksAreExportedByDefaultAndSingleAgentsAreNot() {
		A2AExportKindsConfig kinds = new A2AExportKindsConfig();

		assertTrue(kinds.isNetwokOfAgentsExported(), "a network of agents is what is worth exporting");
		assertFalse(kinds.isAgentsExported(), "an agent alone is not offered unless asked for");
		assertTrue(kinds.isAllowed(A2AExportedAgent.Kind.NETWORK));
		assertFalse(kinds.isAllowed(A2AExportedAgent.Kind.AGENT));
	}

	@Test
	void anInstallationCanTurnEitherKindAround() {
		A2AExportKindsConfig kinds = new A2AExportKindsConfig();
		kinds.setNetwokOfAgentsExported(false);
		kinds.setAgentsExported(true);

		assertFalse(kinds.isAllowed(A2AExportedAgent.Kind.NETWORK));
		assertTrue(kinds.isAllowed(A2AExportedAgent.Kind.AGENT));
	}
}
