/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientResponseException;

import ai.gebo.zammad.client.model.Attachment;
import ai.gebo.zammad.client.model.Group;
import ai.gebo.zammad.client.model.KnowledgeBaseAnswer;
import ai.gebo.zammad.client.model.KnowledgeBaseAssetResult;
import ai.gebo.zammad.client.model.KnowledgeBaseCategory;
import ai.gebo.zammad.client.model.KnowledgeBaseInitResult;
import ai.gebo.zammad.client.model.KnowledgeBaseSearchDetail;
import ai.gebo.zammad.client.model.KnowledgeBaseSearchRequest;
import ai.gebo.zammad.client.model.KnowledgeBaseSearchResult;
import ai.gebo.zammad.client.model.Ticket;
import ai.gebo.zammad.client.model.TicketArticle;
import ai.gebo.zammad.client.model.TicketPriority;
import ai.gebo.zammad.client.model.User;
import ai.gebo.zammad.handler.model.ZammadNativePositionObject;
import ai.gebo.zammad.handler.model.ZammadPathComponent;
import ai.gebo.zammad.handler.model.ZammadPathNodeType;

/**
 * The Zammad contents of one connection seen as a tree: a group holds its
 * tickets, a ticket its rendered conversation (the public articles), each of
 * its internal notes and the attachments of its articles; a knowledge base
 * holds its top categories, a category its
 * sub-categories and its answers, an answer its rendered body and its
 * attachments. Only the published and the internal answers are part of the
 * tree: drafts and archived answers are not. One tree lives for one job or one
 * browsing call and caches what it read (the knowledge bases, the tickets of a
 * group, the articles of a ticket).
 */
public class ZammadRemoteTree {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZammadRemoteTree.class);
	/** The locale of a knowledge base URL: "/help/en-us/1-install". */
	private static final Pattern HELP_LOCALE = Pattern.compile("^/help/([^/]+)(/|$)");
	private final ZammadConnection connection;
	private final Map<Long, List<Ticket>> ticketsByGroup = new HashMap<Long, List<Ticket>>();
	private final Map<Long, List<TicketArticle>> articlesByTicket = new HashMap<Long, List<TicketArticle>>();
	private final Map<Long, String> localeCodes = new HashMap<Long, String>();
	private ZammadKnowledgeBaseIndex knowledgeBaseIndex = null;
	private boolean identityChecked = false;

	public ZammadRemoteTree(ZammadConnection connection) {
		this.connection = connection;
	}

	public ZammadConnection getConnection() {
		return connection;
	}

	public String getBaseUri() {
		return connection.getBaseUri();
	}

	/**
	 * Asks Zammad who the account is, once per tree: refused credentials fail
	 * here with their 401 before any list is read.
	 */
	public synchronized void requireIdentity() {
		if (identityChecked) {
			return;
		}
		LOGGER.debug("Checking the Zammad identity on {}", connection.getBaseUri());
		User me = connection.users().getCurrentUser(null, null);
		identityChecked = true;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Zammad identity on {} accepted: {}", connection.getBaseUri(), me != null ? me.getLogin() : null);
		}
	}

	public static boolean isNotFound(Throwable th) {
		return th instanceof RestClientResponseException e && e.getStatusCode().value() == 404;
	}

	/** Not found or not allowed: what Zammad answers for a knowledge base the account cannot read. */
	static boolean isNotAvailable(Throwable th) {
		return th instanceof RestClientResponseException e
				&& (e.getStatusCode().value() == 404 || e.getStatusCode().value() == 403);
	}

	/**
	 * Every active group the account can read tickets of.
	 */
	public List<Group> listGroups() {
		requireIdentity();
		List<Group> groups = new ArrayList<Group>();
		for (int page = 1;; page++) {
			List<Group> read = connection.groups().listGroups(null, false, page, ZammadConnection.PAGE_SIZE);
			if (read == null) {
				break;
			}
			for (Group group : read) {
				if (!Boolean.FALSE.equals(group.getActive())) {
					groups.add(group);
				}
			}
			if (read.size() < ZammadConnection.PAGE_SIZE) {
				break;
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Zammad {} has {} active groups", connection.getBaseUri(), groups.size());
		}
		return groups;
	}

	public Group group(Long groupId) {
		return connection.groups().getGroup(groupId, null, false);
	}

	/**
	 * The knowledge bases, categories and answers, read once per tree. An account
	 * without access to the knowledge base reads an empty index.
	 */
	public synchronized ZammadKnowledgeBaseIndex knowledgeBases() {
		if (knowledgeBaseIndex == null) {
			requireIdentity();
			try {
				KnowledgeBaseInitResult init = connection.knowledgeBases().initializeKnowledgeBaseOverview(null,
						Map.of());
				knowledgeBaseIndex = new ZammadKnowledgeBaseIndex(init);
			} catch (RestClientResponseException e) {
				if (!isNotAvailable(e)) {
					throw e;
				}
				LOGGER.debug("The Zammad knowledge base of {} is not available to the account: {}",
						connection.getBaseUri(), e.getStatusCode());
				knowledgeBaseIndex = new ZammadKnowledgeBaseIndex(null);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Zammad {} knowledge bases read: {}", connection.getBaseUri(), knowledgeBaseIndex);
			}
		}
		return knowledgeBaseIndex;
	}

	/**
	 * The locale code of a knowledge base ("en-us"), for the links to its
	 * categories and answers. The REST API has no list of the system locales:
	 * the code is read from the URL of one of its categories, null when it has
	 * none or the search fails.
	 */
	public synchronized String localeCode(Long knowledgeBaseId) {
		if (localeCodes.containsKey(knowledgeBaseId)) {
			return localeCodes.get(knowledgeBaseId);
		}
		String code = null;
		try {
			KnowledgeBaseSearchRequest request = new KnowledgeBaseSearchRequest().query("")
					.knowledgeBaseId(knowledgeBaseId).flavor("agent").index("KnowledgeBase::Category::Translation")
					.limit(1);
			KnowledgeBaseSearchResult result = connection.knowledgeBases().searchKnowledgeBase(request, null);
			if (result != null && result.getDetails() != null) {
				for (KnowledgeBaseSearchDetail detail : result.getDetails()) {
					Matcher m = detail.getUrl() != null ? HELP_LOCALE.matcher(detail.getUrl()) : null;
					if (m != null && m.find()) {
						code = m.group(1);
						break;
					}
				}
			}
		} catch (RuntimeException e) {
			LOGGER.debug("No locale code for the Zammad knowledge base {}: {}", knowledgeBaseId, e.getMessage());
		}
		LOGGER.debug("Zammad knowledge base {} locale code: {}", knowledgeBaseId, code);
		localeCodes.put(knowledgeBaseId, code);
		return code;
	}

	/**
	 * Every ticket of a group, oldest first, cached for the tree.
	 */
	public synchronized List<Ticket> ticketsOf(Long groupId) {
		List<Ticket> tickets = ticketsByGroup.get(groupId);
		if (tickets == null) {
			requireIdentity();
			Map<String, Object> condition = new LinkedHashMap<String, Object>();
			condition.put("ticket.group_id", Map.of("operator", "is", "value", List.of(String.valueOf(groupId))));
			tickets = connection.allTickets(condition);
			ticketsByGroup.put(groupId, tickets);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Zammad group {} has {} tickets", groupId, tickets.size());
			}
		}
		return tickets;
	}

	public Ticket ticket(Long ticketId) {
		return connection.ticket(ticketId);
	}

	/**
	 * Every article of a ticket, internal notes included, oldest first.
	 */
	public synchronized List<TicketArticle> articles(Long ticketId) {
		List<TicketArticle> articles = articlesByTicket.get(ticketId);
		if (articles == null) {
			articles = connection.articles().listTicketArticles(ticketId, null, true, null, null);
			if (articles == null) {
				articles = List.of();
			}
			articles = new ArrayList<TicketArticle>(articles);
			articles.sort(Comparator.comparing(TicketArticle::getId, Comparator.nullsLast(Comparator.naturalOrder())));
			articlesByTicket.put(ticketId, articles);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Zammad ticket {} has {} articles", ticketId, articles.size());
			}
		}
		return articles;
	}

	/**
	 * An answer with its translations and their contents.
	 */
	public ZammadAnswer answer(Long knowledgeBaseId, Long answerId) {
		ZammadKnowledgeBaseIndex index = knowledgeBases();
		List<Long> translationIds = index.answerTranslationIds(answerId);
		String include = translationIds.isEmpty() ? null
				: String.join(",", translationIds.stream().map(String::valueOf).toList());
		KnowledgeBaseAssetResult result = connection.answers().getKnowledgeBaseAnswer(knowledgeBaseId, answerId, null,
				include);
		ZammadAnswer answer = new ZammadAnswer(answerId, result != null ? result.getAssets() : null);
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Zammad answer {} of the knowledge base {}: {}", answerId, knowledgeBaseId, answer);
		}
		return answer;
	}

	/** The groups by id and name, for the search filters. */
	public List<ZammadTicketsQueryTranslator.Named> namedGroups() {
		return listGroups().stream().map(g -> new ZammadTicketsQueryTranslator.Named(g.getId(), g.getName(), null))
				.toList();
	}

	/** The ticket states by id, name and state type, for the search filters. */
	public List<ZammadTicketsQueryTranslator.Named> namedStates() {
		return connection.ticketStatesWithTypes().stream().filter(s -> s.get("id") != null)
				.map(s -> new ZammadTicketsQueryTranslator.Named(ZammadKnowledgeBaseIndex.longValue(s.get("id")),
						s.get("name") != null ? s.get("name").toString() : null,
						s.get("state_type") != null ? s.get("state_type").toString() : null))
				.toList();
	}

	/** The ticket priorities by id and name, for the search filters. */
	public List<ZammadTicketsQueryTranslator.Named> namedPriorities() {
		List<TicketPriority> priorities = connection.ticketPriorities().listTicketPriorities(null, false, null, null);
		return priorities == null ? List.of()
				: priorities.stream().map(p -> new ZammadTicketsQueryTranslator.Named(p.getId(), p.getName(), null))
						.toList();
	}

	/**
	 * The answers matching a text in every knowledge base, as the agents see them:
	 * the answer ids, best match first, of the published and internal answers
	 * only.
	 */
	public List<Long> searchAnswers(String text, int limit) {
		KnowledgeBaseSearchRequest request = new KnowledgeBaseSearchRequest().query(text).flavor("agent")
				.index("KnowledgeBase::Answer::Translation").limit(limit);
		KnowledgeBaseSearchResult result = connection.knowledgeBases().searchKnowledgeBase(request, null);
		ZammadKnowledgeBaseIndex index = knowledgeBases();
		java.util.Set<Long> ids = new java.util.LinkedHashSet<Long>();
		if (result != null && result.getDetails() != null) {
			for (KnowledgeBaseSearchDetail detail : result.getDetails()) {
				Long answerId = index.answerIdOfTranslation(detail.getId());
				if (answerId != null && ZammadKnowledgeBaseIndex.isListed(index.answerOf(answerId))) {
					ids.add(answerId);
				}
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Zammad knowledge base search \"{}\": {} answers", text, ids.size());
		}
		return new ArrayList<Long>(ids);
	}

	public InputStream downloadTicketAttachment(Long ticketId, Long articleId, Long attachmentId) throws IOException {
		return connection.downloadTicketAttachment(ticketId, articleId, attachmentId);
	}

	public InputStream downloadAttachment(Long attachmentId) throws IOException {
		return connection.downloadAttachment(attachmentId);
	}

	static Long id(String value) {
		try {
			return Long.valueOf(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Not a Zammad id: " + value);
		}
	}

	/** The article id and the attachment id of a ticket attachment step. */
	static Long[] articleAttachment(String value) {
		int idx = value != null ? value.indexOf(ZammadNavigationUtil.ARTICLE_ATTACHMENT_SEPARATOR) : -1;
		if (idx <= 0) {
			throw new IllegalArgumentException("Not a Zammad ticket attachment: " + value);
		}
		return new Long[] { id(value.substring(0, idx)), id(value.substring(idx + 1)) };
	}

	/**
	 * The root node a data source root stands for.
	 */
	public ZammadNativePositionObject root(ZammadPathComponent root) {
		String base = getBaseUri();
		switch (root.type) {
		case GROUP:
			return ZammadNodes.group(base, group(id(root.id)));
		case KNOWLEDGE_BASE: {
			Long kbId = id(root.id);
			ZammadKnowledgeBaseIndex index = knowledgeBases();
			if (!index.hasKnowledgeBase(kbId)) {
				throw new IllegalArgumentException("No Zammad knowledge base " + kbId + " readable on " + base);
			}
			return ZammadNodes.knowledgeBase(base, kbId, index.knowledgeBaseTitle(kbId));
		}
		default:
			throw new IllegalArgumentException("Not a Zammad root: " + root);
		}
	}

	/**
	 * The node a path step addresses, read from Zammad. {@code parentId} is the
	 * id of the step before (the ticket of an internal note or an attachment, the
	 * answer of an attachment).
	 */
	public ZammadNativePositionObject node(ZammadPathComponent root, ZammadPathComponent step, String parentId) {
		String base = getBaseUri();
		String rootCode = ZammadNodes.rootCode(root.type, root.id);
		switch (step.type) {
		case GROUP:
		case KNOWLEDGE_BASE:
			return root(step);
		case TICKET:
			return ZammadNodes.ticket(base, rootCode, ticket(id(step.id)));
		case TICKET_CONTENT:
			return ZammadNodes.ticketContent(base, rootCode, ticket(id(step.id)));
		case INTERNAL_NOTE: {
			Ticket ticket = ticket(id(parentId));
			TicketArticle article = internalNote(ticket.getId(), id(step.id));
			if (article == null) {
				throw notFound("internal note " + step.id + " of the ticket " + parentId);
			}
			return ZammadNodes.internalNote(base, rootCode, ticket, article);
		}
		case CATEGORY: {
			Long kbId = id(root.id);
			Long categoryId = id(step.id);
			return ZammadNodes.category(base, rootCode, kbId, localeCode(kbId), categoryId,
					knowledgeBases().categoryTitle(categoryId));
		}
		case ANSWER:
		case ANSWER_CONTENT: {
			Long kbId = id(root.id);
			Long answerId = id(step.id);
			ZammadKnowledgeBaseIndex index = knowledgeBases();
			return step.type == ZammadPathNodeType.ANSWER
					? ZammadNodes.answer(base, rootCode, kbId, localeCode(kbId), answerId, index.answerTitle(answerId))
					: ZammadNodes.answerContent(base, rootCode, kbId, localeCode(kbId), answerId,
							index.answerTitle(answerId), ZammadNodes.date(index.answerUpdatedAt(answerId)));
		}
		case ATTACHMENT:
			if (root.type == ZammadPathNodeType.GROUP) {
				Long[] ids = articleAttachment(step.id);
				Ticket ticket = ticket(id(parentId));
				for (TicketArticle article : articles(ticket.getId())) {
					if (ids[0].equals(article.getId()) && article.getAttachments() != null) {
						for (Attachment attachment : article.getAttachments()) {
							if (ids[1].equals(attachment.getId())) {
								return ZammadNodes.ticketAttachment(base, rootCode, ticket, article, attachment);
							}
						}
					}
				}
				throw notFound("ticket attachment " + step.id + " of the ticket " + parentId);
			} else {
				Long kbId = id(root.id);
				Long answerId = id(parentId);
				ZammadAnswer answer = answer(kbId, answerId);
				for (Map<String, Object> attachment : answer.attachments()) {
					if (step.id.equals(String.valueOf(attachment.get("id")))) {
						return ZammadNodes.answerAttachment(base, rootCode, kbId, localeCode(kbId), answerId,
								attachment, ZammadNodes.date(answer.updatedAt()));
					}
				}
				throw notFound("attachment " + step.id + " of the answer " + parentId);
			}
		default:
			throw new IllegalArgumentException("Unknown Zammad path step " + step);
		}
	}

	private static IllegalArgumentException notFound(String what) {
		return new IllegalArgumentException("No Zammad " + what);
	}

	/**
	 * The article of a ticket that is an internal note (internal, with a body),
	 * null when the ticket has no such article any more or it is not internal.
	 */
	public TicketArticle internalNote(Long ticketId, Long articleId) {
		for (TicketArticle article : articles(ticketId)) {
			if (articleId.equals(article.getId())) {
				return ZammadNodes.isInternalNote(article) ? article : null;
			}
		}
		return null;
	}

	/**
	 * The children of a node: a group holds its tickets; a ticket its content, its
	 * internal notes and the attachments of its articles (those of an internal
	 * article flagged internal); a knowledge base its top categories; a
	 * category its sub-categories and its answers; an answer its content and its
	 * attachments. Contents and attachments have no children.
	 */
	public List<ZammadNativePositionObject> children(ZammadPathComponent root, ZammadPathNodeType type, String id) {
		requireIdentity();
		String base = getBaseUri();
		String rootCode = ZammadNodes.rootCode(root.type, root.id);
		List<ZammadNativePositionObject> children = new ArrayList<ZammadNativePositionObject>();
		switch (type) {
		case GROUP:
			for (Ticket ticket : ticketsOf(id(id))) {
				children.add(ZammadNodes.ticket(base, rootCode, ticket));
			}
			break;
		case TICKET: {
			Ticket ticket = ticket(id(id));
			children.add(ZammadNodes.ticketContent(base, rootCode, ticket));
			for (TicketArticle article : articles(ticket.getId())) {
				if (ZammadNodes.isInternalNote(article)) {
					children.add(ZammadNodes.internalNote(base, rootCode, ticket, article));
				}
				if (article.getAttachments() != null) {
					for (Attachment attachment : article.getAttachments()) {
						children.add(ZammadNodes.ticketAttachment(base, rootCode, ticket, article, attachment));
					}
				}
			}
		}
			break;
		case KNOWLEDGE_BASE:
		case CATEGORY: {
			Long kbId = id(root.id);
			ZammadKnowledgeBaseIndex index = knowledgeBases();
			String locale = localeCode(kbId);
			Long categoryId = type == ZammadPathNodeType.CATEGORY ? id(id) : null;
			for (KnowledgeBaseCategory category : index.categoriesOf(kbId, categoryId)) {
				children.add(ZammadNodes.category(base, rootCode, kbId, locale, category.getId(),
						index.categoryTitle(category.getId())));
			}
			if (categoryId != null) {
				for (KnowledgeBaseAnswer answer : index.answersOf(categoryId)) {
					children.add(ZammadNodes.answer(base, rootCode, kbId, locale, answer.getId(),
							index.answerTitle(answer.getId())));
				}
			}
		}
			break;
		case ANSWER: {
			Long kbId = id(root.id);
			Long answerId = id(id);
			ZammadKnowledgeBaseIndex index = knowledgeBases();
			String locale = localeCode(kbId);
			OffsetDateTime updated = index.answerUpdatedAt(answerId);
			children.add(ZammadNodes.answerContent(base, rootCode, kbId, locale, answerId, index.answerTitle(answerId),
					ZammadNodes.date(updated)));
			for (Map<String, Object> attachment : index.answerAttachments(answerId)) {
				children.add(ZammadNodes.answerAttachment(base, rootCode, kbId, locale, answerId, attachment,
						ZammadNodes.date(updated)));
			}
		}
			break;
		default:
			break;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Zammad {} {} under {} has {} children", type, id, rootCode, children.size());
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Zammad {} {} children: {}", type, id, children);
		}
		return children;
	}
}
