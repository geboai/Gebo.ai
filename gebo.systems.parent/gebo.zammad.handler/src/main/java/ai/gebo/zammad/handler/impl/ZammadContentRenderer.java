/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.web.util.HtmlUtils;

import ai.gebo.zammad.client.model.Attachment;
import ai.gebo.zammad.client.model.Ticket;
import ai.gebo.zammad.client.model.TicketArticle;

/**
 * Renders the Zammad tickets and knowledge base answers as small UTF-8 html
 * documents, read by the html reader of the ingestion: a title, the attributes
 * of the item as a list, then the conversation of a ticket (its public
 * articles), one internal note of a ticket, or the body of an answer.
 * <p>
 * The bodies are the html Zammad stores (e-mails, notes, answers) cleaned with
 * the relaxed jsoup safelist, so neither scripts nor event handlers of an
 * incoming e-mail reach the stored document; plain text bodies are escaped
 * and keep their line breaks.
 */
final class ZammadContentRenderer {
	static final String HTML = "text/html";
	private static final Safelist SAFELIST = Safelist.relaxed();

	private ZammadContentRenderer() {
	}

	static String escape(Object value) {
		return value == null ? "" : HtmlUtils.htmlEscape(value.toString());
	}

	/** The html of a body, cleaned, or the escaped text with its line breaks. */
	static String body(String body, String contentType) {
		if (body == null || body.isBlank()) {
			return "";
		}
		if (contentType != null && contentType.toLowerCase(java.util.Locale.ROOT).contains("html")) {
			return Jsoup.clean(body, SAFELIST);
		}
		return "<p>" + escape(body.strip()).replace("\r\n", "\n").replace("\n", "<br>\n") + "</p>";
	}

	private static StringBuilder open(String title) {
		StringBuilder html = new StringBuilder();
		html.append("<!DOCTYPE html>\n<html>\n<head>\n<meta charset=\"utf-8\">\n<title>").append(escape(title))
				.append("</title>\n</head>\n<body>\n<h1>").append(escape(title)).append("</h1>\n");
		return html;
	}

	private static void attributes(StringBuilder html, Map<String, Object> attributes) {
		StringBuilder items = new StringBuilder();
		attributes.forEach((label, value) -> {
			if (value != null && !value.toString().isBlank()) {
				items.append("<li><b>").append(escape(label)).append(":</b> ").append(escape(value)).append("</li>\n");
			}
		});
		if (!items.isEmpty()) {
			html.append("<ul>\n").append(items).append("</ul>\n");
		}
	}

	private static String close(StringBuilder html) {
		return html.append("</body>\n</html>\n").toString();
	}

	private static Object extra(Ticket ticket, String key) {
		return ticket.getAdditionalProperties() != null ? ticket.getAdditionalProperty(key) : null;
	}

	/** Who wrote an article: its from header, else its author, else its sender kind. */
	static String author(TicketArticle article) {
		for (String candidate : new String[] { article.getFrom(), article.getCreatedBy() }) {
			if (candidate != null && !candidate.isBlank() && !"-".equals(candidate.trim())) {
				return candidate.trim();
			}
		}
		return article.getSender() != null ? article.getSender().getValue() : null;
	}

	/**
	 * One article: a heading with its kind, type, author and date, then its
	 * subject (when it is not the ticket's title), its recipients, its body and
	 * the names of its attachments.
	 */
	private static void article(StringBuilder html, Ticket ticket, TicketArticle article, String prefix) {
		StringBuilder heading = new StringBuilder().append(prefix);
		heading.append(ZammadNodes.isInternal(article) ? "Internal note" : "Message");
		if (article.getType() != null) {
			heading.append(" (").append(article.getType()).append(")");
		}
		String author = author(article);
		if (author != null) {
			heading.append(" from ").append(author);
		}
		if (article.getCreatedAt() != null) {
			heading.append(", ").append(article.getCreatedAt());
		}
		html.append("<h2>").append(escape(heading)).append("</h2>\n");
		if (article.getSubject() != null && !article.getSubject().isBlank()
				&& !article.getSubject().equals(ticket.getTitle())) {
			html.append("<p><b>Subject:</b> ").append(escape(article.getSubject())).append("</p>\n");
		}
		if (article.getTo() != null && !article.getTo().isBlank()) {
			html.append("<p><b>To:</b> ").append(escape(article.getTo())).append("</p>\n");
		}
		html.append("<div>\n").append(body(article.getBody(), article.getContentType() != null ? article.getContentType().getValue() : null)).append("\n</div>\n");
		if (article.getAttachments() != null && !article.getAttachments().isEmpty()) {
			html.append("<p><b>Attachments:</b></p>\n<ul>\n");
			for (Attachment attachment : article.getAttachments()) {
				html.append("<li>").append(escape(attachment.getFilename()));
				Long size = ZammadNodes.parseSize(attachment.getSize());
				if (size != null) {
					html.append(" (").append(size).append(" bytes)");
				}
				html.append("</li>\n");
			}
			html.append("</ul>\n");
		}
	}

	/**
	 * The ticket's document: its attributes and its public articles. The internal
	 * articles are left out, as Zammad keeps them from the customer: each one
	 * with a body is a document of its own ({@link #renderInternalNote}).
	 */
	static String renderTicket(Ticket ticket, List<TicketArticle> articles, String baseUri) {
		String title = ZammadNodes.ticketLabel(ticket);
		StringBuilder html = open(title);
		Map<String, Object> attributes = new LinkedHashMap<String, Object>();
		attributes.put("Ticket", ticket.getNumber());
		attributes.put("Group", extra(ticket, "group"));
		attributes.put("State", extra(ticket, "state"));
		attributes.put("Priority", extra(ticket, "priority"));
		attributes.put("Owner", extra(ticket, "owner"));
		attributes.put("Customer", extra(ticket, "customer"));
		attributes.put("Organization", extra(ticket, "organization"));
		attributes.put("Created", ticket.getCreatedAt());
		attributes.put("Updated", ticket.getUpdatedAt());
		attributes.put("Closed", ticket.getCloseAt());
		attributes.put("URL", ZammadNodes.ticketUrl(baseUri, ticket.getId()));
		attributes(html, attributes);
		int n = 0;
		for (TicketArticle article : articles) {
			if (ZammadNodes.isInternal(article)) {
				continue;
			}
			n++;
			article(html, ticket, article, n + ". ");
		}
		return close(html);
	}

	/**
	 * An internal note alone: the ticket it belongs to, who wrote it and when,
	 * then the note itself.
	 */
	static String renderInternalNote(Ticket ticket, TicketArticle article, String baseUri) {
		String title = "Internal note " + article.getId() + " of " + ZammadNodes.ticketLabel(ticket);
		StringBuilder html = open(title);
		Map<String, Object> attributes = new LinkedHashMap<String, Object>();
		attributes.put("Ticket", ticket.getNumber());
		attributes.put("Ticket title", ticket.getTitle());
		attributes.put("Group", extra(ticket, "group"));
		attributes.put("Visibility", "internal");
		attributes.put("Author", author(article));
		attributes.put("Created", article.getCreatedAt());
		attributes.put("Updated", article.getUpdatedAt());
		attributes.put("URL", ZammadNodes.ticketUrl(baseUri, ticket.getId()));
		attributes(html, attributes);
		article(html, ticket, article, "");
		return close(html);
	}

	static String renderAnswer(ZammadAnswer answer, String knowledgeBaseTitle, String categoryPath, String url) {
		List<ZammadAnswer.Translation> translations = answer.translations();
		String title = !translations.isEmpty() && translations.get(0).title() != null ? translations.get(0).title()
				: "Answer " + answer.getId();
		StringBuilder html = open(title);
		Map<String, Object> attributes = new LinkedHashMap<String, Object>();
		attributes.put("Knowledge base", knowledgeBaseTitle);
		attributes.put("Category", categoryPath);
		attributes.put("Visibility", answer.archivedAt() != null ? "archived"
				: answer.publishedAt() != null ? "public" : answer.internalAt() != null ? "internal" : "draft");
		attributes.put("Updated", answer.updatedAt());
		attributes.put("URL", url);
		attributes(html, attributes);
		for (ZammadAnswer.Translation translation : translations) {
			if (translations.size() > 1) {
				html.append("<h2>").append(escape(translation.title())).append("</h2>\n");
			}
			html.append("<div>\n").append(body(translation.body(), HTML)).append("\n</div>\n");
		}
		List<Map<String, Object>> attachments = answer.attachments();
		if (!attachments.isEmpty()) {
			html.append("<p><b>Attachments:</b></p>\n<ul>\n");
			for (Map<String, Object> attachment : attachments) {
				html.append("<li>").append(escape(attachment.get("filename"))).append("</li>\n");
			}
			html.append("</ul>\n");
		}
		return close(html);
	}
}
