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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.zammad.client.model.Ticket;
import ai.gebo.zammad.client.model.TicketArticle;
import ai.gebo.zammad.handler.ZammadResourceReference;
import ai.gebo.zammad.handler.model.ZammadNativePositionObject;
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
