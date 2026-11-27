/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.impl;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Map;

import ai.gebo.zammad.client.model.Attachment;
import ai.gebo.zammad.client.model.Group;
import ai.gebo.zammad.client.model.Ticket;
import ai.gebo.zammad.handler.model.ZammadNativePositionObject;
import ai.gebo.zammad.handler.model.ZammadPathNodeType;

/**
 * Factories of the Zammad virtual filesystem nodes from the client models.
 * Codes are stable and unique among siblings, names carry the extension of the
 * content, URLs point at the Zammad agent web pages, and every node carries its
 * root in its meta infos.
 */
final class ZammadNodes {

	private ZammadNodes() {
	}

	private static ZammadNativePositionObject node(ZammadPathNodeType type, String id, String code, String name,
			String url, String rootCode) {
		ZammadNativePositionObject node = new ZammadNativePositionObject();
		node.setType(type);
		node.setId(id);
		node.setCode(code);
		node.setName(name);
		node.setUrl(url);
		node.meta(ZammadNativePositionObject.ZAMMAD_ROOT, rootCode);
		return node;
	}

	static Date date(OffsetDateTime value) {
		return value != null ? Date.from(value.toInstant()) : null;
	}

	/** A rendered content: a small html document (see {@link ZammadContentRenderer}). */
	private static ZammadNativePositionObject rendered(ZammadNativePositionObject node, Date modified) {
		node.setResourceContentType(ZammadContentRenderer.HTML);
		node.setResourceModificationTime(modified);
		return node;
	}

	static String rootCode(ZammadPathNodeType type, Object id) {
		return ZammadNavigationUtil.segment(type, String.valueOf(id));
	}

	static ZammadNativePositionObject group(String baseUri, Group group) {
		String id = String.valueOf(group.getId());
		String name = group.getName() != null ? group.getName() : "Group " + id;
		return node(ZammadPathNodeType.GROUP, id, "group-" + id, ZammadNavigationUtil.sanitizeName("Tickets of " + name),
				baseUri + "/#manage/groups/" + id, rootCode(ZammadPathNodeType.GROUP, id))
				.meta(ZammadNativePositionObject.ZAMMAD_GROUP_ID, id);
	}

	static ZammadNativePositionObject knowledgeBase(String baseUri, Long knowledgeBaseId, String title) {
		String id = String.valueOf(knowledgeBaseId);
		return node(ZammadPathNodeType.KNOWLEDGE_BASE, id, "knowledge-base-" + id,
				ZammadNavigationUtil.sanitizeName(title != null ? title : "Knowledge base " + id),
				knowledgeBaseUrl(baseUri, knowledgeBaseId), rootCode(ZammadPathNodeType.KNOWLEDGE_BASE, id))
				.meta(ZammadNativePositionObject.ZAMMAD_KNOWLEDGE_BASE_ID, id);
	}

	static String knowledgeBaseUrl(String baseUri, Long knowledgeBaseId) {
		return baseUri + "/#knowledge_base/" + knowledgeBaseId;
	}

	/**
	 * The agent page of a category or an answer: it needs the locale code of the
	 * knowledge base, without it the knowledge base page.
	 */
	static String knowledgeBaseItemUrl(String baseUri, Long knowledgeBaseId, String localeCode, String kind,
			Object id) {
		return localeCode == null ? knowledgeBaseUrl(baseUri, knowledgeBaseId)
				: knowledgeBaseUrl(baseUri, knowledgeBaseId) + "/locale/" + localeCode + "/" + kind + "/" + id;
	}

	static String ticketUrl(String baseUri, Object ticketId) {
		return baseUri + "/#ticket/zoom/" + ticketId;
	}

	/** "#97002 SMTP relay rejects large mails". */
	static String ticketLabel(Ticket ticket) {
		String number = ticket.getNumber() != null ? ticket.getNumber() : String.valueOf(ticket.getId());
		return ZammadNavigationUtil.sanitizeName("#" + number + (ticket.getTitle() != null ? " " + ticket.getTitle() : ""));
	}

	private static ZammadNativePositionObject ticketMeta(ZammadNativePositionObject node, Ticket ticket) {
		return node.meta(ZammadNativePositionObject.ZAMMAD_TICKET_ID, ticket.getId())
				.meta(ZammadNativePositionObject.ZAMMAD_TICKET_NUMBER, ticket.getNumber())
				.meta(ZammadNativePositionObject.ZAMMAD_GROUP_ID, ticket.getGroupId());
	}

	static ZammadNativePositionObject ticket(String baseUri, String rootCode, Ticket ticket) {
		String id = String.valueOf(ticket.getId());
		return ticketMeta(node(ZammadPathNodeType.TICKET, id, "ticket-" + id, ticketLabel(ticket),
				ticketUrl(baseUri, id), rootCode), ticket);
	}

	static ZammadNativePositionObject ticketContent(String baseUri, String rootCode, Ticket ticket) {
		String id = String.valueOf(ticket.getId());
		Date updated = date(ticket.getUpdatedAt());
		return rendered(ticketMeta(node(ZammadPathNodeType.TICKET_CONTENT, id, "ticket-" + id + ".html",
				ticketLabel(ticket) + ".html", ticketUrl(baseUri, id), rootCode), ticket),
				updated != null ? updated : date(ticket.getCreatedAt()));
	}

	static ZammadNativePositionObject category(String baseUri, String rootCode, Long knowledgeBaseId,
			String localeCode, Long categoryId, String title) {
		String id = String.valueOf(categoryId);
		return node(ZammadPathNodeType.CATEGORY, id, "category-" + id,
				ZammadNavigationUtil.sanitizeName(title != null ? title : "Category " + id),
				knowledgeBaseItemUrl(baseUri, knowledgeBaseId, localeCode, "category", id), rootCode)
				.meta(ZammadNativePositionObject.ZAMMAD_KNOWLEDGE_BASE_ID, knowledgeBaseId)
				.meta(ZammadNativePositionObject.ZAMMAD_CATEGORY_ID, id);
	}

	static ZammadNativePositionObject answer(String baseUri, String rootCode, Long knowledgeBaseId, String localeCode,
			Long answerId, String title) {
		String id = String.valueOf(answerId);
		return node(ZammadPathNodeType.ANSWER, id, "answer-" + id,
				ZammadNavigationUtil.sanitizeName(title != null ? title : "Answer " + id),
				knowledgeBaseItemUrl(baseUri, knowledgeBaseId, localeCode, "answer", id), rootCode)
				.meta(ZammadNativePositionObject.ZAMMAD_KNOWLEDGE_BASE_ID, knowledgeBaseId)
				.meta(ZammadNativePositionObject.ZAMMAD_ANSWER_ID, id);
	}

	static ZammadNativePositionObject answerContent(String baseUri, String rootCode, Long knowledgeBaseId,
			String localeCode, Long answerId, String title, Date modified) {
		String id = String.valueOf(answerId);
		return rendered(node(ZammadPathNodeType.ANSWER_CONTENT, id, "answer-" + id + ".html",
				ZammadNavigationUtil.sanitizeName(title != null ? title : "Answer " + id) + ".html",
				knowledgeBaseItemUrl(baseUri, knowledgeBaseId, localeCode, "answer", id), rootCode)
				.meta(ZammadNativePositionObject.ZAMMAD_KNOWLEDGE_BASE_ID, knowledgeBaseId)
				.meta(ZammadNativePositionObject.ZAMMAD_ANSWER_ID, id), modified);
	}

	private static ZammadNativePositionObject attachment(String id, String code, String url, String rootCode,
			String fileName, Object contentType, String size, Date created) {
		ZammadNativePositionObject node = node(ZammadPathNodeType.ATTACHMENT, id, code,
				ZammadNavigationUtil.sanitizeName(fileName != null ? fileName : code), url, rootCode)
				.meta(ZammadNativePositionObject.ZAMMAD_FILE_NAME, fileName);
		node.setResourceContentType(contentType != null ? contentType.toString() : null);
		node.setResourceFileSize(parseSize(size));
		node.setResourceModificationTime(created);
		return node;
	}

	/** The attachment of a ticket article: its id is "article id-attachment id". */
	static ZammadNativePositionObject ticketAttachment(String baseUri, String rootCode, Ticket ticket, Long articleId,
			Attachment attachment, Date created) {
		String id = articleId + ZammadNavigationUtil.ARTICLE_ATTACHMENT_SEPARATOR + attachment.getId();
		return ticketMeta(attachment(id, "attachment-" + id, ticketUrl(baseUri, ticket.getId()), rootCode,
				attachment.getFilename(), contentTypeOf(attachment.getPreferences()), attachment.getSize(), created),
				ticket).meta(ZammadNativePositionObject.ZAMMAD_ARTICLE_ID, articleId)
				.meta(ZammadNativePositionObject.ZAMMAD_ATTACHMENT_ID, attachment.getId());
	}

	/**
	 * The attachment of an answer, from the untyped answer asset: {@code id},
	 * {@code filename}, {@code size}, {@code preferences}.
	 */
	static ZammadNativePositionObject answerAttachment(String baseUri, String rootCode, Long knowledgeBaseId,
			String localeCode, Long answerId, Map<String, Object> attachment, Date created) {
		String id = String.valueOf(attachment.get("id"));
		Object preferences = attachment.get("preferences");
		return attachment(id, "attachment-" + id,
				knowledgeBaseItemUrl(baseUri, knowledgeBaseId, localeCode, "answer", answerId), rootCode,
				attachment.get("filename") != null ? attachment.get("filename").toString() : null,
				preferences instanceof Map<?, ?> map ? contentTypeOf(map) : null,
				attachment.get("size") != null ? attachment.get("size").toString() : null, created)
				.meta(ZammadNativePositionObject.ZAMMAD_KNOWLEDGE_BASE_ID, knowledgeBaseId)
				.meta(ZammadNativePositionObject.ZAMMAD_ANSWER_ID, answerId)
				.meta(ZammadNativePositionObject.ZAMMAD_ATTACHMENT_ID, id);
	}

	/** The content type an attachment's preferences declare ("Content-Type" or "Mime-Type"). */
	static String contentTypeOf(Map<?, ?> preferences) {
		if (preferences == null) {
			return null;
		}
		for (String key : new String[] { "Content-Type", "Mime-Type", "content_type" }) {
			Object value = preferences.get(key);
			if (value != null && !value.toString().isBlank()) {
				return value.toString();
			}
		}
		return null;
	}

	static Long parseSize(String size) {
		if (size == null || size.isBlank()) {
			return null;
		}
		try {
			return Long.valueOf(size.trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
