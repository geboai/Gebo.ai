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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class ZammadContentRendererTest {

	@Test
	void ticketWithItsPublicConversation() {
		String html = ZammadContentRenderer.renderTicket(ZammadTestData.ticket(),
				List.of(ZammadTestData.customerMessage(), ZammadTestData.internalNote(),
						ZammadTestData.emptyInternalArticle(), ZammadTestData.agentReply()),
				"http://zammad");
		assertTrue(html.contains("<title>#97002 SMTP relay rejects large mails</title>"), html);
		assertTrue(html.contains("Gebo Support"), html);
		assertTrue(html.contains("3 high"), html);
		assertTrue(html.contains("Acme Mail"), html);
		assertTrue(html.contains("http://zammad/#ticket/zoom/2"), html);
		assertTrue(html.contains("1. Message (web) from Carla &lt;carla@acme.test&gt;"), html);
		assertTrue(html.contains("The relay rejects my mails:<br>\nmessage too large (12MB)"), html);
		assertTrue(html.contains("relay.log (58 bytes)"), html);
		assertTrue(html.contains("2. Message (phone) from Gebo Support"), html);
		assertTrue(html.contains("A fix is planned for Friday."), html);
		assertFalse(html.contains("<b>Subject:</b> SMTP relay"), "a subject equal to the title is not repeated");
	}

	@Test
	void theTicketDocumentLeavesTheInternalArticlesOut() {
		String html = ZammadContentRenderer.renderTicket(ZammadTestData.ticket(),
				List.of(ZammadTestData.customerMessage(), ZammadTestData.internalNote(),
						ZammadTestData.emptyInternalArticle(), ZammadTestData.agentReply()),
				"http://zammad");
		assertFalse(html.contains("Internal note"), html);
		assertFalse(html.contains("Confirmed on production too"), html);
		assertFalse(html.contains("relay-config.txt"), "the attachments of an internal article are not named");
		assertFalse(html.contains("screenshot.png"), "the attachments of an internal article are not named");
	}

	@Test
	void anInternalNoteAlone() {
		String html = ZammadContentRenderer.renderInternalNote(ZammadTestData.ticket(), ZammadTestData.internalNote(),
				"http://zammad");
		assertTrue(html.contains("<title>Internal note 6 of #97002 SMTP relay rejects large mails</title>"), html);
		assertTrue(html.contains("<li><b>Ticket:</b> 97002</li>"), html);
		assertTrue(html.contains("<li><b>Visibility:</b> internal</li>"), html);
		assertTrue(html.contains("<li><b>Author:</b> Admin Gebo</li>"), html);
		assertTrue(html.contains("<li><b>Created:</b> 2026-11-27T10:30Z</li>"), html);
		assertTrue(html.contains("http://zammad/#ticket/zoom/2"), html);
		assertTrue(html.contains("Internal note (note) from Admin Gebo, 2026-11-27T10:30Z"), html);
		assertTrue(html.contains("the relay limit is <b>10MB</b>"), html);
		assertTrue(html.contains("relay-config.txt (36 bytes)"), html);
		assertFalse(html.contains("<script"), "html bodies are cleaned");
		assertFalse(html.contains("The relay rejects my mails"), "only the note");
	}

	@Test
	void plainBodiesAreEscaped() {
		assertEquals("<p>a &lt;b&gt; c<br>\nd</p>", ZammadContentRenderer.body("a <b> c\r\nd\n", "text/plain"));
		assertEquals("", ZammadContentRenderer.body(" ", "text/html"));
	}

	@Test
	void answerWithItsBodyAndAttachments() {
		Map<String, Map<String, Object>> assets = Map.of("KnowledgeBaseAnswer",
				Map.of("3", Map.of("id", 3, "category_id", 1, "published_at", "2026-11-27T10:00:00Z",
						"updated_at", "2026-11-27T10:00:00Z", "attachments",
						List.of(Map.of("id", 11, "filename", "install-checklist.txt")))),
				"KnowledgeBaseAnswerTranslation",
				Map.of("4", Map.of("id", 4, "answer_id", 3, "kb_locale_id", 1, "title", "Install guide",
						"content_id", 5)),
				"KnowledgeBaseAnswerTranslationContent", Map.of("5", Map.of("id", 5, "body",
						"<p>To install, open the port 8080.</p><img src=x onerror=alert(1)>")));
		String html = ZammadContentRenderer.renderAnswer(new ZammadAnswer(3L, assets), "Gebo help center",
				"Install / Network", "http://zammad/#knowledge_base/1/locale/en-us/answer/3");
		assertTrue(html.contains("<title>Install guide</title>"), html);
		assertTrue(html.contains("Gebo help center"), html);
		assertTrue(html.contains("Install / Network"), html);
		assertTrue(html.contains("public"), html);
		assertTrue(html.contains("open the port 8080"), html);
		assertTrue(html.contains("install-checklist.txt"), html);
		assertFalse(html.contains("onerror"), "html bodies are cleaned");
	}
}
