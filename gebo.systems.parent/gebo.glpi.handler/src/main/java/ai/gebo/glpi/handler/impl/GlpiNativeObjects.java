/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.impl;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ai.gebo.glpi.client.model.Document;
import ai.gebo.glpi.client.model.Entity;
import ai.gebo.glpi.client.model.Followup;
import ai.gebo.glpi.client.model.KBArticle;
import ai.gebo.glpi.client.model.Solution;
import ai.gebo.glpi.client.model.Ticket;
import ai.gebo.glpi.client.model.TicketTask;
import ai.gebo.glpi.handler.model.GlpiNativePositionObject;
import ai.gebo.glpi.handler.model.GlpiPathNodeType;

/**
 * Builds the native nodes of the GLPI tree from the GLPI objects. Codes are
 * built from GLPI ids only (stable and unique among siblings), names from the
 * GLPI titles; the synthesised text items get an .html name, the content type
 * they are streamed with.
 */
public final class GlpiNativeObjects {
	public static final String HTML_CONTENT_TYPE = "text/html";
	static final String HTML_EXTENSION = ".html";

	private GlpiNativeObjects() {
	}

	static Date toDate(OffsetDateTime dateTime) {
		return dateTime != null ? Date.from(dateTime.toInstant()) : null;
	}

	static Date latest(OffsetDateTime... dates) {
		OffsetDateTime latest = null;
		for (OffsetDateTime date : dates) {
			if (date != null && (latest == null || date.isAfter(latest))) {
				latest = date;
			}
		}
		return toDate(latest);
	}

	/** The name of an entity: its full path in the entities tree when GLPI gives it */
	public static String entityName(Entity entity) {
		String name = entity.getCompletename() != null && !entity.getCompletename().isBlank()
				? entity.getCompletename()
				: entity.getName();
		return name != null ? name : "Entity " + entity.getId();
	}

	public static String ticketTitle(Ticket ticket) {
		String title = GlpiNavigationUtil.sanitizeName(ticket.getName());
		return "#" + ticket.getId() + (title != null ? " " + title : "");
	}

	public static String kbArticleTitle(KBArticle article) {
		String title = GlpiNavigationUtil.sanitizeName(article.getName());
		return title != null ? title : "Knowledge base article " + article.getId();
	}

	public static String documentName(Document document) {
		String name = GlpiNavigationUtil.sanitizeName(document.getFilename());
		if (name == null) {
			name = GlpiNavigationUtil.sanitizeName(document.getName());
		}
		return name != null ? name : "document-" + document.getId();
	}

	public static GlpiNativePositionObject entity(long entityId, String entityName, String rootUrl) {
		GlpiNativePositionObject node = new GlpiNativePositionObject(GlpiPathNodeType.ENTITY, entityId,
				"entity-" + entityId, entityName, GlpiWebLinks.entity(rootUrl, entityId));
		node.meta(GlpiNativePositionObject.GLPI_ENTITY_ID, entityId);
		node.meta(GlpiNativePositionObject.GLPI_ENTITY_NAME, entityName);
		return node;
	}

	public static GlpiNativePositionObject entity(Entity entity, String rootUrl) {
		return entity(entity.getId(), entityName(entity), rootUrl);
	}

	public static GlpiNativePositionObject ticketsSection(long entityId) {
		return new GlpiNativePositionObject(GlpiPathNodeType.TICKETS_SECTION, null, "tickets",
				GlpiNavigationUtil.TICKETS_SECTION_NAME, null).meta(GlpiNativePositionObject.GLPI_ENTITY_ID,
						entityId);
	}

	public static GlpiNativePositionObject kbSection(long entityId) {
		return new GlpiNativePositionObject(GlpiPathNodeType.KB_SECTION, null, "knowledge-base",
				GlpiNavigationUtil.KB_SECTION_NAME, null).meta(GlpiNativePositionObject.GLPI_ENTITY_ID, entityId);
	}

	private static GlpiNativePositionObject withTicketInfos(GlpiNativePositionObject node, Ticket ticket) {
		node.meta(GlpiNativePositionObject.GLPI_TICKET_ID, ticket.getId());
		node.meta(GlpiNativePositionObject.GLPI_TICKET_NAME, ticket.getName());
		if (ticket.getEntity() != null) {
			node.meta(GlpiNativePositionObject.GLPI_ENTITY_ID, ticket.getEntity().getId());
		}
		if (ticket.getStatus() != null) {
			node.meta(GlpiNativePositionObject.GLPI_TICKET_STATUS, ticket.getStatus().getName());
		}
		if (ticket.getType() != null) {
			node.meta(GlpiNativePositionObject.GLPI_TICKET_TYPE, GlpiContentRenderer.typeName(ticket));
		}
		if (ticket.getPriority() != null) {
			node.meta(GlpiNativePositionObject.GLPI_TICKET_PRIORITY, ticket.getPriority().getValue());
		}
		if (ticket.getCategory() != null) {
			node.meta(GlpiNativePositionObject.GLPI_TICKET_CATEGORY, ticket.getCategory().getName());
		}
		return node;
	}

	public static GlpiNativePositionObject ticketFolder(Ticket ticket, String rootUrl) {
		GlpiNativePositionObject node = new GlpiNativePositionObject(GlpiPathNodeType.TICKET, ticket.getId(),
				"ticket-" + ticket.getId(), ticketTitle(ticket), GlpiWebLinks.ticket(rootUrl, ticket.getId()));
		return withTicketInfos(node, ticket);
	}

	public static GlpiNativePositionObject ticketBody(Ticket ticket, String rootUrl) {
		GlpiNativePositionObject node = new GlpiNativePositionObject(GlpiPathNodeType.TICKET_BODY, ticket.getId(),
				"ticket-" + ticket.getId() + "-body", "Ticket " + ticketTitle(ticket) + HTML_EXTENSION,
				GlpiWebLinks.ticket(rootUrl, ticket.getId()));
		node.setResourceContentType(HTML_CONTENT_TYPE);
		node.setResourceModificationTime(latest(ticket.getDateMod(), ticket.getDateCreation()));
		return withTicketInfos(node, ticket);
	}

	private static GlpiNativePositionObject ticketChild(GlpiPathNodeType type, String kind, long ticketId,
			Long itemId, String rootUrl, Boolean isPrivate, OffsetDateTime dateMod, OffsetDateTime dateCreation) {
		GlpiNativePositionObject node = new GlpiNativePositionObject(type, itemId, kind + "-" + itemId,
				"Ticket #" + ticketId + " " + kind + " " + itemId + HTML_EXTENSION,
				GlpiWebLinks.ticket(rootUrl, ticketId));
		node.setResourceContentType(HTML_CONTENT_TYPE);
		node.setResourceModificationTime(latest(dateMod, dateCreation));
		node.meta(GlpiNativePositionObject.GLPI_TICKET_ID, ticketId);
		node.meta(GlpiNativePositionObject.GLPI_PRIVATE, isPrivate);
		return node;
	}

	public static GlpiNativePositionObject followup(long ticketId, Followup followup, String rootUrl) {
		return ticketChild(GlpiPathNodeType.FOLLOWUP, "followup", ticketId, followup.getId(), rootUrl,
				followup.getIsPrivate(), followup.getDateMod(), followup.getDateCreation());
	}

	public static GlpiNativePositionObject task(long ticketId, TicketTask task, String rootUrl) {
		return ticketChild(GlpiPathNodeType.TASK, "task", ticketId, task.getId(), rootUrl, task.getIsPrivate(),
				task.getDateMod(), task.getDateCreation());
	}

	public static GlpiNativePositionObject solution(long ticketId, Solution solution, String rootUrl) {
		return ticketChild(GlpiPathNodeType.SOLUTION, "solution", ticketId, solution.getId(), rootUrl, null,
				solution.getDateMod(), solution.getDateCreation());
	}

	public static GlpiNativePositionObject document(long ticketId, Document document, String rootUrl) {
		String name = documentName(document);
		GlpiNativePositionObject node = new GlpiNativePositionObject(GlpiPathNodeType.DOCUMENT, document.getId(),
				"document-" + document.getId(), name, GlpiWebLinks.document(rootUrl, document.getId()));
		node.setResourceContentType(document.getMime());
		node.setResourceModificationTime(latest(document.getDateMod(), document.getDateCreation()));
		node.meta(GlpiNativePositionObject.GLPI_TICKET_ID, ticketId);
		node.meta(GlpiNativePositionObject.GLPI_DOCUMENT_FILENAME, name);
		node.meta(GlpiNativePositionObject.GLPI_DOCUMENT_MIME, document.getMime());
		return node;
	}

	public static GlpiNativePositionObject kbArticle(KBArticle article, String rootUrl) {
		GlpiNativePositionObject node = new GlpiNativePositionObject(GlpiPathNodeType.KB_ARTICLE, article.getId(),
				"kbarticle-" + article.getId(), kbArticleTitle(article) + HTML_EXTENSION,
				GlpiWebLinks.kbArticle(rootUrl, article.getId()));
		node.setResourceContentType(HTML_CONTENT_TYPE);
		node.setResourceModificationTime(latest(article.getDateMod(), article.getDateCreation()));
		if (article.getEntity() != null) {
			node.meta(GlpiNativePositionObject.GLPI_ENTITY_ID, article.getEntity().getId());
		}
		node.meta(GlpiNativePositionObject.GLPI_KB_CATEGORIES, categoriesOf(article));
		return node;
	}

	static String categoriesOf(KBArticle article) {
		List<String> names = article.getCategories() == null ? List.of()
				: article.getCategories().stream().filter(c -> c != null && c.getName() != null)
						.map(c -> c.getName()).collect(Collectors.toList());
		return names.isEmpty() ? null : String.join(", ", names);
	}
}
