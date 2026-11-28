/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.PathInfoMetaType;
import ai.gebo.zammad.client.model.Ticket;
import ai.gebo.zammad.client.model.TicketArticle;
import ai.gebo.zammad.handler.ZammadResourceReference;
import ai.gebo.zammad.handler.model.ZammadNativePositionObject;
import ai.gebo.zammad.handler.model.ZammadPathComponent;
import ai.gebo.zammad.handler.model.ZammadPathNodeType;

class GZammadRemoteVirtualFilesystemConsumingServiceImplTest {
	private static final String BASE = "http://zammad";

	@Test
	void ticketReferencesFromNodeMetas() {
		Ticket ticket = ZammadTestData.ticket();
		String root = ZammadNodes.rootCode(ZammadPathNodeType.GROUP, 2);
		ZammadResourceReference reference = GZammadRemoteVirtualFilesystemConsumingServiceImpl
				.toReference(ZammadNodes.ticketContent(BASE, root, ticket).getResourceReferenceMetaInfos());
		assertEquals(ZammadPathNodeType.TICKET_CONTENT, reference.type);
		assertEquals("GROUP:2", reference.rootCode);
		assertEquals(2L, reference.ticketId);
		assertEquals("text/html", reference.contentType);
		TicketArticle article = ZammadTestData.customerMessage();
		ZammadNativePositionObject attachment = ZammadNodes.ticketAttachment(BASE, root, ticket, article.getId(),
				article.getAttachments().get(0), new Date());
		assertEquals("5-9", attachment.getId());
		assertEquals(58L, attachment.getResourceFileSize());
		reference = GZammadRemoteVirtualFilesystemConsumingServiceImpl
				.toReference(attachment.getResourceReferenceMetaInfos());
		assertEquals(ZammadPathNodeType.ATTACHMENT, reference.type);
		assertEquals(2L, reference.ticketId);
		assertEquals(5L, reference.articleId);
		assertEquals(9L, reference.attachmentId);
		assertEquals("text/plain", reference.contentType);
	}

	@Test
	void answerReferencesFromNodeMetas() {
		String root = ZammadNodes.rootCode(ZammadPathNodeType.KNOWLEDGE_BASE, 1);
		ZammadNativePositionObject content = ZammadNodes.answerContent(BASE, root, 1L, "en-us", 3L, "Install guide",
				new Date());
		assertEquals("http://zammad/#knowledge_base/1/locale/en-us/answer/3", content.getUrl());
		ZammadResourceReference reference = GZammadRemoteVirtualFilesystemConsumingServiceImpl
				.toReference(content.getResourceReferenceMetaInfos());
		assertEquals(ZammadPathNodeType.ANSWER_CONTENT, reference.type);
		assertEquals("KNOWLEDGE_BASE:1", reference.rootCode);
		assertEquals(1L, reference.knowledgeBaseId);
		assertEquals(3L, reference.answerId);
		ZammadNativePositionObject attachment = ZammadNodes.answerAttachment(BASE, root, 1L, null, 3L,
				Map.of("id", 11, "filename", "install-checklist.txt", "size", "41", "preferences",
						Map.of("Mime-Type", "text/plain")),
				new Date());
		assertEquals("http://zammad/#knowledge_base/1", attachment.getUrl());
		reference = GZammadRemoteVirtualFilesystemConsumingServiceImpl
				.toReference(attachment.getResourceReferenceMetaInfos());
		assertEquals(ZammadPathNodeType.ATTACHMENT, reference.type);
		assertEquals(3L, reference.answerId);
		assertEquals(11L, reference.attachmentId);
		assertNull(reference.ticketId);
		assertEquals("text/plain", reference.contentType);
	}

	/** The tree of one ticket with the given articles, without a Zammad behind it. */
	static class OneTicketTree extends ZammadRemoteTree {
		private final Ticket ticket = ZammadTestData.ticket();
		private final List<TicketArticle> articles;

		OneTicketTree(TicketArticle... articles) {
			super(null);
			this.articles = List.of(articles);
		}

		@Override
		public String getBaseUri() {
			return BASE;
		}

		@Override
		public synchronized void requireIdentity() {
		}

		@Override
		public Ticket ticket(Long ticketId) {
			if (!ticket.getId().equals(ticketId)) {
				throw new IllegalArgumentException("No ticket " + ticketId);
			}
			return ticket;
		}

		@Override
		public synchronized List<TicketArticle> articles(Long ticketId) {
			return articles;
		}
	}

	private static final ZammadPathComponent SUPPORT = new ZammadPathComponent(ZammadPathNodeType.GROUP, "2");

	private static OneTicketTree fullTicket() {
		return new OneTicketTree(ZammadTestData.customerMessage(), ZammadTestData.internalNote(),
				ZammadTestData.emptyInternalArticle(), ZammadTestData.agentReply());
	}

	private static ZammadNativePositionObject childOf(List<ZammadNativePositionObject> children, String code) {
		return children.stream().filter(c -> code.equals(c.getCode())).findFirst()
				.orElseThrow(() -> new AssertionError("no child " + code + " in " + children));
	}

	@Test
	void aTicketHoldsItsInternalNotesAsDocumentsOfTheirOwn() {
		List<ZammadNativePositionObject> children = fullTicket().children(SUPPORT, ZammadPathNodeType.TICKET, "2");
		assertEquals(List.of("ticket-2.html", "attachment-5-9", "internal-note-6", "attachment-6-10",
				"attachment-7-11"), children.stream().map(ZammadNativePositionObject::getCode).toList(),
				"the blank internal article has no note, the public ones are in the ticket's content");
		ZammadNativePositionObject note = childOf(children, "internal-note-6");
		assertEquals(ZammadPathNodeType.INTERNAL_NOTE, note.getType());
		assertEquals("6", note.getId());
		assertEquals("#97002 internal note 6.html", note.getName());
		assertTrue(note.isResource());
		assertEquals("text/html", note.getResourceContentType());
		assertEquals(Date.from(java.time.Instant.parse("2026-11-27T10:40:00Z")), note.getResourceModificationTime());
		assertEquals("http://zammad/#ticket/zoom/2", note.getUrl());
		Map<String, Object> metas = note.getResourceReferenceMetaInfos();
		assertEquals("true", metas.get(ZammadNativePositionObject.ZAMMAD_INTERNAL_NOTE));
		assertEquals("3", metas.get(ZammadNativePositionObject.ZAMMAD_NOTE_AUTHOR_ID));
		assertEquals("6", metas.get(ZammadNativePositionObject.ZAMMAD_ARTICLE_ID));
		assertEquals("2", metas.get(ZammadNativePositionObject.ZAMMAD_TICKET_ID));
		assertEquals("2", metas.get(ZammadNativePositionObject.ZAMMAD_GROUP_ID));
		assertEquals("GROUP:2", metas.get(ZammadNativePositionObject.ZAMMAD_ROOT));
		PathInfo path = ZammadNavigationUtil.toPathInfo("TICKET:2", note);
		assertEquals("TICKET:2|INTERNAL_NOTE:6", path.absolutePath);
		assertFalse(path.folder);
		assertEquals(PathInfoMetaType.WEB_PAGE, path.metaType);
		// the attachments of an internal article are flagged as their article, the others are not
		for (String code : List.of("attachment-6-10", "attachment-7-11")) {
			Map<String, Object> attachment = childOf(children, code).getResourceReferenceMetaInfos();
			assertEquals("true", attachment.get(ZammadNativePositionObject.ZAMMAD_INTERNAL_NOTE), code);
			assertEquals("3", attachment.get(ZammadNativePositionObject.ZAMMAD_NOTE_AUTHOR_ID), code);
		}
		for (String code : List.of("ticket-2.html", "attachment-5-9")) {
			assertNull(childOf(children, code).getResourceReferenceMetaInfos()
					.get(ZammadNativePositionObject.ZAMMAD_INTERNAL_NOTE), code);
		}
	}

	@Test
	void anInternalNoteStepResolvesOnlyToAnInternalNote() {
		OneTicketTree tree = fullTicket();
		ZammadNativePositionObject note = tree.node(SUPPORT,
				new ZammadPathComponent(ZammadPathNodeType.INTERNAL_NOTE, "6"), "2");
		assertEquals("internal-note-6", note.getCode());
		assertEquals("true", note.getResourceReferenceMetaInfos().get(ZammadNativePositionObject.ZAMMAD_INTERNAL_NOTE));
		ZammadNativePositionObject attachment = tree.node(SUPPORT,
				new ZammadPathComponent(ZammadPathNodeType.ATTACHMENT, "6-10"), "2");
		assertEquals("true",
				attachment.getResourceReferenceMetaInfos().get(ZammadNativePositionObject.ZAMMAD_INTERNAL_NOTE));
		for (String id : List.of("7", "8", "99")) {
			assertThrows(IllegalArgumentException.class,
					() -> tree.node(SUPPORT, new ZammadPathComponent(ZammadPathNodeType.INTERNAL_NOTE, id), "2"),
					"the article " + id + " is blank, public or gone");
		}
	}

	private static String read(InputStream stream) throws Exception {
		try (stream) {
			return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
		}
	}

	@Test
	void theNoteIsStreamedAloneAndTheTicketWithoutIt() throws Exception {
		OneTicketTree tree = fullTicket();
		String root = ZammadNodes.rootCode(ZammadPathNodeType.GROUP, 2);
		ZammadResourceReference note = GZammadRemoteVirtualFilesystemConsumingServiceImpl.toReference(
				ZammadNodes.internalNote(BASE, root, tree.ticket(2L), ZammadTestData.internalNote())
						.getResourceReferenceMetaInfos());
		assertEquals(ZammadPathNodeType.INTERNAL_NOTE, note.type);
		assertEquals(2L, note.ticketId);
		assertEquals(6L, note.articleId);
		String html = read(GZammadRemoteVirtualFilesystemConsumingServiceImpl.open(note, tree));
		assertTrue(html.contains("Confirmed on production too"), html);
		assertFalse(html.contains("The relay rejects my mails"), html);
		ZammadResourceReference ticket = GZammadRemoteVirtualFilesystemConsumingServiceImpl
				.toReference(ZammadNodes.ticketContent(BASE, root, tree.ticket(2L)).getResourceReferenceMetaInfos());
		html = read(GZammadRemoteVirtualFilesystemConsumingServiceImpl.open(ticket, tree));
		assertTrue(html.contains("The relay rejects my mails"), html);
		assertTrue(html.contains("A fix is planned for Friday."), html);
		assertFalse(html.contains("Confirmed on production too"), html);
		// an article no longer internal is not streamed as a note
		note.articleId = 8L;
		ZammadResourceReference notANote = note;
		assertThrows(GeboContentHandlerSystemException.class,
				() -> GZammadRemoteVirtualFilesystemConsumingServiceImpl.open(notANote, tree));
	}

	@Test
	void aNoteNoLongerInternalOrGoneIsDeleted() throws Exception {
		String root = ZammadNodes.rootCode(ZammadPathNodeType.GROUP, 2);
		ZammadResourceReference note = GZammadRemoteVirtualFilesystemConsumingServiceImpl.toReference(ZammadNodes
				.internalNote(BASE, root, ZammadTestData.ticket(), ZammadTestData.internalNote())
				.getResourceReferenceMetaInfos());
		GZammadRemoteVirtualFilesystemConsumingServiceImpl service = new GZammadRemoteVirtualFilesystemConsumingServiceImpl(
				null, null);
		Map<String, Object> environment = new HashMap<String, Object>();
		environment.put(GZammadRemoteVirtualFilesystemConsumingServiceImpl.ZAMMAD_TREE, fullTicket());
		GDocumentReference doc = new GDocumentReference();
		assertNull(service.verifyRemoteObjectExistence(null, null, doc, note, environment), "still there");
		environment.put(GZammadRemoteVirtualFilesystemConsumingServiceImpl.ZAMMAD_TREE,
				new OneTicketTree(ZammadTestData.customerMessage(), ZammadTestData.agentReply()));
		assertSame(doc, service.verifyRemoteObjectExistence(null, null, doc, note, environment));
		assertTrue(doc.getDeleted());
	}

	@Test
	void referenceNeverFails() {
		assertNotNull(GZammadRemoteVirtualFilesystemConsumingServiceImpl.toReference(null));
		ZammadResourceReference reference = GZammadRemoteVirtualFilesystemConsumingServiceImpl
				.toReference(Map.of(ZammadNativePositionObject.ZAMMAD_OBJECT_TYPE, "SOMETHING_ELSE",
						ZammadNativePositionObject.ZAMMAD_TICKET_ID, "x"));
		assertNull(reference.type);
		assertNull(reference.ticketId);
	}
}
