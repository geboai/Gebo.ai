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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;
import ai.gebo.architecture.ai.service.ToolsTokenBudget;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.core.contents.security.services.VirtualFilesystemQuery;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.llms.agent.standardtools.KnowledgeBaseBrowsingToolSource.BrowseProjectEndpointsParam;
import ai.gebo.llms.agent.standardtools.KnowledgeBaseBrowsingToolSource.BrowseVirtualFilesystemParam;
import ai.gebo.llms.agent.standardtools.KnowledgeBaseBrowsingToolSource.CountDocumentsParam;
import ai.gebo.llms.agent.standardtools.KnowledgeBaseBrowsingToolSource.DocumentContent;
import ai.gebo.llms.agent.standardtools.KnowledgeBaseBrowsingToolSource.DocumentContentsParam;
import ai.gebo.llms.agent.standardtools.KnowledgeBaseBrowsingToolSource.DocumentsCount;
import ai.gebo.llms.agent.standardtools.KnowledgeBaseBrowsingToolSource.ListPage;
import ai.gebo.llms.agent.standardtools.KnowledgeBaseBrowsingToolSource.PageParam;
import ai.gebo.llms.agent.standardtools.KnowledgeBaseBrowsingToolSource.VirtualFilesystemItem;
import ai.gebo.llms.agent.standardtools.KnowledgeBaseDocumentChunksReader.DocumentChunks;
import ai.gebo.model.DocumentMetaInfos;

/**
 * Pins the knowledge base browsing tools: limited to the chat's visible knowledge
 * bases, paged (50 by default, 200 at most), the folders and documents with their
 * uniqueId, the documents read whole from their chunks in the vector store within
 * the room.
 */
class KnowledgeBaseBrowsingToolSourceTest {

	private IGKnowledgebaseVisibilityService visibility;
	private KnowledgeBaseDocumentChunksReader reader;

	@SuppressWarnings("unchecked")
	private static <T> ObjectProvider<T> provider(T value) {
		ObjectProvider<T> provider = mock(ObjectProvider.class);
		when(provider.getObject()).thenReturn(value);
		when(provider.getIfAvailable()).thenReturn(value);
		return provider;
	}

	private static GKnowledgeBase kb(String code) {
		GKnowledgeBase kb = new GKnowledgeBase();
		kb.setCode(code);
		return kb;
	}

	private static KBContext chat(String... codes) {
		KBContext context = new KBContext();
		context.setKnowledgeBasesCodes(List.of(codes));
		return context;
	}

	private static GDocumentReference document(long uniqueId, String code, String name) {
		GDocumentReference document = new GDocumentReference();
		document.setUniqueId(uniqueId);
		document.setCode(code);
		document.setName(name);
		return document;
	}

	private static Document chunk(String content) {
		return new Document(content);
	}

	@BeforeEach
	void setUp() {
		visibility = mock(IGKnowledgebaseVisibilityService.class);
		reader = mock(KnowledgeBaseDocumentChunksReader.class);
		// the chat's kb1 and its visible child kb1-child
		when(visibility.visiblesAndChildKnowledgebases(List.of("kb1"))).thenReturn(List.of(kb("kb1"), kb("kb1-child")));
		when(visibility.allVisibleKnowledgebases()).thenReturn(List.of(kb("kb1"), kb("kb1-child"), kb("other")));
	}

	private KnowledgeBaseBrowsingToolSource tools() {
		return new KnowledgeBaseBrowsingToolSource(provider(visibility), provider(reader));
	}

	@Test
	void theScopeIsTheChatsVisibleKnowledgeBasesWithTheirChildren() {
		KnowledgeBaseBrowsingToolSource tools = tools();

		assertEquals(List.of("kb1", "kb1-child"), tools.scope(chat("kb1"), null));
		assertEquals(List.of("kb1-child"), tools.scope(chat("kb1"), "kb1-child"));
		assertEquals(List.of(), tools.scope(chat("kb1"), "other"));
		assertEquals(List.of("kb1", "kb1-child", "other"), tools.scope(null, null));
	}

	@Test
	void pagesAre50ByDefaultAndAtMost200() {
		PageParam param = new PageParam();
		assertEquals(50, KnowledgeBaseBrowsingToolSource.pageSizeOf(param));
		param.setPageSize(500);
		assertEquals(200, KnowledgeBaseBrowsingToolSource.pageSizeOf(param));
		param.setPageSize(20);
		param.setPage(-3);
		assertEquals(20, KnowledgeBaseBrowsingToolSource.pageSizeOf(param));
		assertEquals(0, KnowledgeBaseBrowsingToolSource.pageOf(param));
	}

	@SuppressWarnings("unchecked")
	@Test
	void documentsAreListedWithTheirUniqueIdInTheChatScope() {
		GDocumentReference document = document(12L, "doc-12", "senses.pdf");
		document.setDescription("A book");
		document.setParentVirtualFolderCode("folder-1");
		document.setCustomMetaInfos(Map.of(DocumentMetaInfos.TITLE, "The twelve senses", "author", "Archiati"));
		when(visibility.browseVisibleDocuments(any(VirtualFilesystemQuery.class), any(Pageable.class)))
				.thenReturn(new PageImpl<>(List.of(document), PageRequest.of(1, 50), 120));
		BrowseVirtualFilesystemParam param = new BrowseVirtualFilesystemParam();
		param.setProjectEndpointCode("ep");
		param.setNameContains("sens");
		param.setPage(1);

		ListPage<VirtualFilesystemItem> page = tools().browseDocuments(param, chat("kb1"), null);

		assertEquals(120, page.total());
		assertTrue(page.hasMorePages());
		VirtualFilesystemItem item = page.items().get(0);
		assertEquals(12L, item.uniqueId());
		assertEquals("senses.pdf", item.name());
		assertEquals("The twelve senses", item.title());
		assertEquals("A book", item.description());
		assertEquals("doc-12", item.code());
		assertEquals("folder-1", item.parentCode());
		assertEquals("Archiati", item.customMetaData().get("author"));
		ArgumentCaptor<VirtualFilesystemQuery> query = ArgumentCaptor.forClass(VirtualFilesystemQuery.class);
		ArgumentCaptor<Pageable> pageable = ArgumentCaptor.forClass(Pageable.class);
		verify(visibility).browseVisibleDocuments(query.capture(), pageable.capture());
		assertEquals(List.of("kb1", "kb1-child"), query.getValue().getKnowledgeBaseCodes());
		assertEquals("ep", query.getValue().getProjectEndpointCode());
		assertEquals("sens", query.getValue().getNameContains());
		assertEquals(1, pageable.getValue().getPageNumber());
		assertEquals(50, pageable.getValue().getPageSize());
	}

	@Test
	void aKnowledgeBaseOutsideTheChatIsNotBrowsed() {
		BrowseVirtualFilesystemParam param = new BrowseVirtualFilesystemParam();
		param.setKnowledgeBaseCode("other");

		ListPage<VirtualFilesystemItem> page = tools().browseFolders(param, chat("kb1"), null);

		assertTrue(page.items().isEmpty());
		assertTrue(page.message().contains("not among this chat's knowledge bases"), page.message());
		verify(visibility, never()).browseVisibleVirtualFolders(any(), any());
	}

	@Test
	void documentsAreCountedByKnowledgeBase() {
		when(visibility.countVisibleDocuments(any(VirtualFilesystemQuery.class))).thenReturn(7L, 3L);
		CountDocumentsParam param = new CountDocumentsParam();

		DocumentsCount count = tools().countDocuments(param, chat("kb1"));

		assertEquals(10L, count.total());
		assertEquals(Map.of("kb1", 7L, "kb1-child", 3L), count.byKnowledgeBase());
	}

	@Test
	void theEndpointsOfAProjectOutsideTheChatAreNotListed() {
		GProject project = new GProject();
		project.setCode("p1");
		when(visibility.getVisibleProjectsByKnowledgeBaseCode(any())).thenReturn(List.of(project));
		BrowseProjectEndpointsParam param = new BrowseProjectEndpointsParam();
		param.setProjectCode("p-elsewhere");

		ListPage<?> page = tools().browseProjectEndpoints(param, chat("kb1"), null);

		assertTrue(page.items().isEmpty());
		verify(visibility, never()).getVisibleProjectsEndpointByParentProjectCode(any());
	}

	@Test
	void documentsAreReadWholeFromTheirChunksInOrder() throws Exception {
		when(visibility.browseVisibleDocuments(any(VirtualFilesystemQuery.class), any(Pageable.class)))
				.thenReturn(new PageImpl<>(List.of(document(5L, "doc-5", "a.pdf"))));
		when(reader.read(any(GDocumentReference.class)))
				.thenReturn(new DocumentChunks(List.of(chunk("first part"), chunk("second part")), "embedding-1"));
		DocumentContentsParam param = new DocumentContentsParam();
		param.setUniqueIds(List.of(5L, 99L));

		List<DocumentContent> contents = tools().documentContents(param, chat("kb1"), null);

		assertEquals(2, contents.size());
		assertEquals("first part\nsecond part\n", contents.get(0).content());
		assertTrue(contents.get(0).complete());
		assertNull(contents.get(1).content());
		assertTrue(contents.get(1).message().contains("No document with this uniqueId"), contents.get(1).message());
	}

	@Test
	void aDocumentLargerThanTheRoomIsCut() throws Exception {
		when(visibility.browseVisibleDocuments(any(VirtualFilesystemQuery.class), any(Pageable.class)))
				.thenReturn(new PageImpl<>(List.of(document(5L, "doc-5", "big.pdf"))));
		List<Document> chunks = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			chunks.add(chunk(("word" + i + " ").repeat(100)));
		}
		when(reader.read(any(GDocumentReference.class))).thenReturn(new DocumentChunks(chunks, "embedding-1"));
		DocumentContentsParam param = new DocumentContentsParam();
		param.setUniqueIds(List.of(5L));

		DocumentContent content = tools().documentContents(param, chat("kb1"), new ToolsTokenBudget(2000)).get(0);

		assertFalse(content.complete());
		assertTrue(content.content().length() < 50 * 700, String.valueOf(content.content().length()));
		assertTrue(content.message().contains("Only the beginning"), content.message());
	}

	@Test
	void aDocumentNotVectorizedYetHasNoText() {
		when(visibility.browseVisibleDocuments(any(VirtualFilesystemQuery.class), any(Pageable.class)))
				.thenReturn(new PageImpl<>(List.of(document(5L, "doc-5", "a.pdf"))));
		when(reader.read(any(GDocumentReference.class))).thenReturn(new DocumentChunks(List.of(), null));
		DocumentContentsParam param = new DocumentContentsParam();
		param.setUniqueIds(List.of(5L));

		DocumentContent content = tools().documentContents(param, chat("kb1"), null).get(0);

		assertNull(content.content());
		assertTrue(content.message().contains("not vectorized"), content.message());
	}

	@Test
	void aFailingReadIsReportedForThatDocumentOnly() {
		when(visibility.browseVisibleDocuments(any(VirtualFilesystemQuery.class), any(Pageable.class)))
				.thenReturn(new PageImpl<>(List.of(document(5L, "doc-5", "a.pdf"), document(6L, "doc-6", "b.pdf"))));
		when(reader.read(any(GDocumentReference.class))).thenThrow(new IllegalStateException("store down"))
				.thenReturn(new DocumentChunks(List.of(chunk("text of b")), "embedding-1"));
		DocumentContentsParam param = new DocumentContentsParam();
		param.setUniqueIds(List.of(5L, 6L));

		List<DocumentContent> contents = tools().documentContents(param, chat("kb1"), null);

		assertTrue(contents.get(0).message().contains("could not be read"), contents.get(0).message());
		assertEquals("text of b\n", contents.get(1).content());
	}

	@Test
	void theSevenToolsAreDeclaredWithTheirDataFlows() {
		KnowledgeBaseBrowsingToolSource tools = tools();

		assertEquals(KnowledgeBaseBrowsingToolSource.TOOLS,
				new java.util.HashSet<>(tools.getToolCallbacks().stream().map(x -> x.getToolDefinition().name()).toList()));
		String schema = tools.getToolCallbacks().stream()
				.filter(x -> x.getToolDefinition().name().equals(KnowledgeBaseBrowsingToolSource.BROWSE_DOCUMENTS_TOOL))
				.findFirst().orElseThrow().getToolDefinition().inputSchema();
		assertTrue(schema.contains("\"pageSize\"") && schema.contains("\"parentFolderCode\""), schema);
		assertEquals(
				List.of(ai.gebo.architecture.ai.model.ToolDataFlowTarget.Kind.KNOWLEDGE_BASE_VECTOR_STORE,
						ai.gebo.architecture.ai.model.ToolDataFlowTarget.Kind.EMBEDDING_MODEL),
				tools.getDataFlowTargets(KnowledgeBaseBrowsingToolSource.DOCUMENT_CONTENTS_TOOL).stream()
						.map(x -> x.kind()).toList());
		assertEquals(ai.gebo.architecture.ai.model.ToolDataFlowTarget.Kind.PLATFORM_DATA,
				tools.getDataFlowTargets(KnowledgeBaseBrowsingToolSource.COUNT_DOCUMENTS_TOOL).get(0).kind());
	}
}
