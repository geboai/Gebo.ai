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

	@Test
	void thePicturesARunGeneratedTravelAsFileParts() throws Exception {
		final ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource picture = new ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource();
		picture.setCode("g1");
		picture.setFileName("drawing.png");
		picture.setContentType("image/png");
		picture.setFileSize(3L);
		final ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService storage = org.mockito.Mockito
				.mock(ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService.class);
		org.mockito.Mockito.when(storage.streamContent(picture))
				.thenReturn(new java.io.ByteArrayInputStream(new byte[] { 1, 2, 3 }));
		final A2AResultAdapter withStorage = new A2AResultAdapter();
		withStorage.setStorageArea(storage);
		final GeboChatResponse response = new GeboChatResponse();
		response.setGeneratedResources(java.util.List.of(picture));

		final java.util.List<org.a2aproject.sdk.spec.FilePart> files = withStorage.messageOf(response, "done", "ctx")
				.parts().stream().filter(p -> p instanceof org.a2aproject.sdk.spec.FilePart)
				.map(p -> (org.a2aproject.sdk.spec.FilePart) p).toList();

		org.junit.jupiter.api.Assertions.assertEquals(1, files.size());
		final org.a2aproject.sdk.spec.FileWithBytes file = (org.a2aproject.sdk.spec.FileWithBytes) files.get(0).file();
		org.junit.jupiter.api.Assertions.assertEquals("image/png", file.mimeType());
		org.junit.jupiter.api.Assertions.assertEquals("drawing.png", file.name());
		org.junit.jupiter.api.Assertions.assertEquals(java.util.Base64.getEncoder().encodeToString(new byte[] { 1, 2, 3 }),
				file.bytes());
		// without the storage, as in a bare adapter, the answer has no file part and still its text
		org.junit.jupiter.api.Assertions.assertTrue(adapter.messageOf(response, "done", "ctx").parts().stream()
				.noneMatch(p -> p instanceof org.a2aproject.sdk.spec.FilePart));
	}

	/** A file the run's tools wrote travels as a file part, read from where it was written. */
	@org.junit.jupiter.api.Test
	void aFileTheToolsWroteTravelsWithTheAnswer() throws Exception {
		final ai.gebo.architecture.ai.files.IGFilesLocation location = org.mockito.Mockito
				.mock(ai.gebo.architecture.ai.files.IGFilesLocation.class);
		final ai.gebo.architecture.ai.files.GLibraryFile described = new ai.gebo.architecture.ai.files.GLibraryFile(
				"workingDirectory", "reports/summary.docx", 4, null, "v1",
				"application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		org.mockito.Mockito.when(location.describe("reports/summary.docx")).thenReturn(described);
		org.mockito.Mockito.when(location.read("reports/summary.docx"))
				.thenReturn(new ai.gebo.architecture.ai.files.GLibraryFileContent(described,
						ai.gebo.model.base.TypedInputStream.of(new java.io.ByteArrayInputStream(new byte[] { 9, 8, 7, 6 }),
								null, "docx")));
		final ai.gebo.architecture.ai.files.IGAILibraryFilesService libraryFiles = org.mockito.Mockito
				.mock(ai.gebo.architecture.ai.files.IGAILibraryFilesService.class);
		org.mockito.Mockito.when(libraryFiles.locationOf("chat-1", "workingDirectory")).thenReturn(location);
		final A2AResultAdapter withFiles = new A2AResultAdapter();
		withFiles.setLibraryFiles(libraryFiles);
		final GeboChatResponse response = new GeboChatResponse();
		response.setUserChatContextCode("chat-1");
		final ai.gebo.architecture.ai.files.GConversationFile written = new ai.gebo.architecture.ai.files.GConversationFile();
		written.setLocation("workingDirectory");
		written.setPath("reports/summary.docx");
		final ai.gebo.architecture.ai.files.GConversationFile gone = new ai.gebo.architecture.ai.files.GConversationFile();
		gone.setLocation("sandbox");
		gone.setPath("lost.txt");
		response.setWrittenFiles(java.util.List.of(written, gone));

		final java.util.List<org.a2aproject.sdk.spec.FilePart> files = withFiles.messageOf(response, "done", "ctx")
				.parts().stream().filter(p -> p instanceof org.a2aproject.sdk.spec.FilePart)
				.map(p -> (org.a2aproject.sdk.spec.FilePart) p).toList();

		org.junit.jupiter.api.Assertions.assertEquals(1, files.size());
		final org.a2aproject.sdk.spec.FileWithBytes file = (org.a2aproject.sdk.spec.FileWithBytes) files.get(0).file();
		org.junit.jupiter.api.Assertions.assertEquals("summary.docx", file.name());
		org.junit.jupiter.api.Assertions.assertEquals(
				"application/vnd.openxmlformats-officedocument.wordprocessingml.document", file.mimeType());
		org.junit.jupiter.api.Assertions.assertEquals(
				java.util.Base64.getEncoder().encodeToString(new byte[] { 9, 8, 7, 6 }), file.bytes());
	}

	/** What the caller sent and was not taken in is told in the run's account. */
	@org.junit.jupiter.api.Test
	void whatWasNotTakenIsToldToTheCaller() {
		final GeboChatResponse response = new GeboChatResponse();
		final java.util.List<String> notTaken = java.util.List.of("the attachment a.png, which names a link");
		final java.util.List<org.a2aproject.sdk.spec.DataPart> withResponse = new A2AResultAdapter()
				.messageOf(response, "done", "ctx", notTaken).parts().stream()
				.filter(p -> p instanceof org.a2aproject.sdk.spec.DataPart).map(p -> (org.a2aproject.sdk.spec.DataPart) p)
				.toList();
		org.junit.jupiter.api.Assertions.assertTrue(withResponse.stream().anyMatch(
				d -> d.data() instanceof java.util.Map<?, ?> m && notTaken.equals(m.get("notTaken"))));
		// and when the run left no response at all
		org.junit.jupiter.api.Assertions.assertTrue(new A2AResultAdapter().messageOf(null, "done", "ctx", notTaken)
				.parts().stream().anyMatch(p -> p instanceof org.a2aproject.sdk.spec.DataPart d
						&& d.data() instanceof java.util.Map<?, ?> m && notTaken.equals(m.get("notTaken"))));
		// nothing is said when everything was taken
		org.junit.jupiter.api.Assertions.assertTrue(new A2AResultAdapter().messageOf(response, "done", "ctx").parts()
				.stream().noneMatch(p -> p instanceof org.a2aproject.sdk.spec.DataPart d
						&& d.data() instanceof java.util.Map<?, ?> m && m.containsKey("notTaken")));
	}
}
