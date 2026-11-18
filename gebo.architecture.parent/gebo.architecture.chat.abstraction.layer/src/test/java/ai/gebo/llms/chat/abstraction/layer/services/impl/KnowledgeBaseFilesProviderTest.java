package ai.gebo.llms.chat.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import ai.gebo.architecture.ai.files.GLibraryFileContent;
import ai.gebo.architecture.ai.files.IGFilesLocation;
import ai.gebo.architecture.documents.access.StreamingPurpose;
import ai.gebo.architecture.documents.cache.service.IDocumentsCacheService;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.core.contents.security.services.VirtualFilesystemQuery;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.model.base.TypedInputStream;

class KnowledgeBaseFilesProviderTest {

	@Test
	void aDocumentOfTheConversationKnowledgeBasesIsReadByItsUniqueId() throws Exception {
		final IGChatSessionLifeCycleService sessions = mock(IGChatSessionLifeCycleService.class);
		final GKnowledgeBase base = new GKnowledgeBase();
		base.setCode("kb-1");
		when(sessions.getSessionAvailableKnowledgeBases(argThat(r -> r != null && "chat-1".equals(r.getUserChatContextCode()))))
				.thenReturn(List.of(base));
		final GDocumentReference document = new GDocumentReference();
		document.setUniqueId(42L);
		document.setName("budget.xlsx");
		document.setExtension(".xlsx");
		document.setFileSize(5L);
		document.setDateModified(new Date(1000));
		final IGKnowledgebaseVisibilityService visibility = mock(IGKnowledgebaseVisibilityService.class);
		when(visibility.browseVisibleDocuments(any(VirtualFilesystemQuery.class), any(Pageable.class)))
				.thenAnswer(call -> {
					final VirtualFilesystemQuery query = call.getArgument(0);
					final boolean visible = List.of("kb-1").equals(query.getKnowledgeBaseCodes())
							&& List.of(42L).equals(query.getUniqueIds());
					return (Page<GDocumentReference>) new PageImpl<>(visible ? List.of(document) : List.of());
				});
		final IDocumentsCacheService cache = mock(IDocumentsCacheService.class);
		when(cache.streamDocument(eq(StreamingPurpose.SERVING), eq(document))).thenAnswer(any -> TypedInputStream
				.of(new ByteArrayInputStream("bytes".getBytes(StandardCharsets.UTF_8)), "application/x-test", ".xlsx"));
		final StaticListableBeanFactory beans = new StaticListableBeanFactory();
		beans.addBean("sessions", sessions);
		beans.addBean("visibility", visibility);
		beans.addBean("cache", cache);
		final KnowledgeBaseFilesProvider provider = new KnowledgeBaseFilesProvider(
				beans.getBeanProvider(IGChatSessionLifeCycleService.class),
				beans.getBeanProvider(IGKnowledgebaseVisibilityService.class),
				beans.getBeanProvider(IDocumentsCacheService.class));

		final IGFilesLocation location = provider.filesOf("chat-1");
		assertFalse(location.isWritable());
		assertEquals("42/budget.xlsx", location.describe("42").path());
		final GLibraryFileContent read = location.read("42/budget.xlsx");
		assertEquals("bytes", new String(read.content().getInputStream().readAllBytes(), StandardCharsets.UTF_8));
		assertEquals(".xlsx", read.content().getExtension());
		assertEquals(5, read.file().size());
		assertNull(location.describe("43"));
		assertThrows(IllegalArgumentException.class, () -> location.read("43/other.xlsx"));
		assertThrows(IllegalArgumentException.class, () -> location.read("budget.xlsx"));
		assertThrows(UnsupportedOperationException.class, () -> location.list(null, false));
		assertThrows(UnsupportedOperationException.class, () -> location.write("42", null, null));
		assertNull(provider.filesOf("chat-without-knowledge-bases"), "no knowledge base, no location");
	}
}
