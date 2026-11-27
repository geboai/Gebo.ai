/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.util.HtmlUtils;

import ai.gebo.glpi.client.model.ApplianceUser;
import ai.gebo.glpi.client.model.Followup;
import ai.gebo.glpi.client.model.KBArticle;
import ai.gebo.glpi.client.model.Solution;
import ai.gebo.glpi.client.model.Ticket;
import ai.gebo.glpi.client.model.TicketTask;

/**
 * Renders the GLPI text items (ticket body, follow-ups, tasks, solutions,
 * knowledge base articles) as small UTF-8 html documents: a title, the
 * attributes that give them context, then the GLPI rich text itself.
 */
public final class GlpiContentRenderer {

	private GlpiContentRenderer() {
	}

	static String typeName(Ticket ticket) {
		if (ticket.getType() == null) {
			return null;
		}
		return ticket.getType().getValue() == 1 ? "Incident" : ticket.getType().getValue() == 2 ? "Request"
				: String.valueOf(ticket.getType().getValue());
	}

	private static String user(ApplianceUser user) {
		return user != null ? user.getName() : null;
	}

	private static String date(OffsetDateTime date) {
		return date != null ? date.toString() : null;
	}

	/**
	 * GLPI rich text is html. Older GLPI versions stored it html-encoded: such a
	 * text (entities and no tag) is decoded
	 */
	static String richText(String content) {
		if (content == null) {
			return "";
		}
		if (content.indexOf('<') < 0 && (content.contains("&lt;") || content.contains("&#60;"))) {
			return HtmlUtils.htmlUnescape(content);
		}
		return content;
	}

	private static InputStream document(String title, Map<String, Object> attributes, String content) {
		StringBuilder html = new StringBuilder();
		html.append("<!DOCTYPE html>\n<html>\n<head>\n<meta charset=\"utf-8\">\n<title>");
		html.append(HtmlUtils.htmlEscape(title));
		html.append("</title>\n</head>\n<body>\n<h1>");
		html.append(HtmlUtils.htmlEscape(title));
		html.append("</h1>\n");
		boolean first = true;
		for (Map.Entry<String, Object> attribute : attributes.entrySet()) {
			if (attribute.getValue() == null || attribute.getValue().toString().isBlank()) {
				continue;
			}
			if (first) {
				html.append("<ul>\n");
				first = false;
			}
			html.append("<li><b>").append(HtmlUtils.htmlEscape(attribute.getKey())).append(":</b> ")
					.append(HtmlUtils.htmlEscape(attribute.getValue().toString())).append("</li>\n");
		}
		if (!first) {
			html.append("</ul>\n");
		}
		html.append("<div>\n").append(richText(content)).append("\n</div>\n</body>\n</html>\n");
		return new ByteArrayInputStream(html.toString().getBytes(StandardCharsets.UTF_8));
	}

	public static InputStream ticket(Ticket ticket) {
		Map<String, Object> attributes = new LinkedHashMap<String, Object>();
		attributes.put("Ticket", "#" + ticket.getId());
		attributes.put("Entity", ticket.getEntity() != null
				? (ticket.getEntity().getCompletename() != null ? ticket.getEntity().getCompletename()
						: ticket.getEntity().getName())
				: null);
		attributes.put("Type", typeName(ticket));
		attributes.put("Status", ticket.getStatus() != null ? ticket.getStatus().getName() : null);
		attributes.put("Category", ticket.getCategory() != null ? ticket.getCategory().getName() : null);
		attributes.put("Priority", ticket.getPriority() != null ? ticket.getPriority().getValue() : null);
		attributes.put("Urgency", ticket.getUrgency() != null ? ticket.getUrgency().getValue() : null);
		attributes.put("Impact", ticket.getImpact() != null ? ticket.getImpact().getValue() : null);
		attributes.put("Location", ticket.getLocation() != null ? ticket.getLocation().getName() : null);
		attributes.put("Request source", ticket.getRequestType() != null ? ticket.getRequestType().getName() : null);
		attributes.put("Requester", user(ticket.getUserRecipient()));
		attributes.put("Opening date", date(ticket.getDate()));
		attributes.put("Last update", date(ticket.getDateMod()));
		attributes.put("Solved on", date(ticket.getDateSolve()));
		attributes.put("Closed on", date(ticket.getDateClose()));
		return document("Ticket #" + ticket.getId() + " " + (ticket.getName() != null ? ticket.getName() : ""),
				attributes, ticket.getContent());
	}

	private static Map<String, Object> ticketContext(String ticketName, long ticketId) {
		Map<String, Object> attributes = new LinkedHashMap<String, Object>();
		attributes.put("Ticket", "#" + ticketId + (ticketName != null ? " " + ticketName : ""));
		return attributes;
	}

	public static InputStream followup(String ticketName, long ticketId, Followup followup) {
		Map<String, Object> attributes = ticketContext(ticketName, ticketId);
		attributes.put("Author", user(followup.getUser()));
		attributes.put("Date", date(followup.getDate() != null ? followup.getDate() : followup.getDateCreation()));
		attributes.put("Request source", followup.getRequestType() != null ? followup.getRequestType().getName() : null);
		attributes.put("Private", Boolean.TRUE.equals(followup.getIsPrivate()) ? "yes" : null);
		return document("Ticket #" + ticketId + " follow-up " + followup.getId(), attributes, followup.getContent());
	}

	public static InputStream task(String ticketName, long ticketId, TicketTask task) {
		Map<String, Object> attributes = ticketContext(ticketName, ticketId);
		attributes.put("Author", user(task.getUser()));
		attributes.put("Technician", user(task.getUserTech()));
		attributes.put("Date", date(task.getDate() != null ? task.getDate() : task.getDateCreation()));
		attributes.put("Category", task.getCategory() != null ? task.getCategory().getName() : null);
		attributes.put("Duration (seconds)", task.getDuration());
		attributes.put("Private", Boolean.TRUE.equals(task.getIsPrivate()) ? "yes" : null);
		return document("Ticket #" + ticketId + " task " + task.getId(), attributes, task.getContent());
	}

	public static InputStream solution(String ticketName, long ticketId, Solution solution) {
		Map<String, Object> attributes = ticketContext(ticketName, ticketId);
		attributes.put("Author", user(solution.getUser()));
		attributes.put("Solution type", solution.getType() != null ? solution.getType().getName() : null);
		attributes.put("Date", date(solution.getDateCreation()));
		attributes.put("Approved by", user(solution.getApprover()));
		return document("Ticket #" + ticketId + " solution " + solution.getId(), attributes, solution.getContent());
	}

	public static InputStream kbArticle(KBArticle article) {
		Map<String, Object> attributes = new LinkedHashMap<String, Object>();
		attributes.put("Knowledge base article", "#" + article.getId());
		attributes.put("Categories", GlpiNativeObjects.categoriesOf(article));
		attributes.put("Entity", article.getEntity() != null ? article.getEntity().getName() : null);
		attributes.put("FAQ", Boolean.TRUE.equals(article.getIsFaq()) ? "yes" : null);
		attributes.put("Author", user(article.getUser()));
		attributes.put("Summary", article.getDescription());
		attributes.put("Last update", date(article.getDateMod()));
		return document(article.getName() != null ? article.getName() : "Knowledge base article " + article.getId(),
				attributes, article.getContent());
	}
}
