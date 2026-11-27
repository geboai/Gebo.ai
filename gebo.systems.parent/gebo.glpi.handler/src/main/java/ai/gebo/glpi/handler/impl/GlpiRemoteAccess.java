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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;

import ai.gebo.glpi.client.api.AdministrationApi;
import ai.gebo.glpi.client.api.AssistanceApi;
import ai.gebo.glpi.client.api.KnowledgebaseApi;
import ai.gebo.glpi.client.api.ManagementApi;
import ai.gebo.glpi.client.api.SessionApi;
import ai.gebo.glpi.client.invoker.ApiClient;
import ai.gebo.glpi.client.model.Document;
import ai.gebo.glpi.client.model.Entity;
import ai.gebo.glpi.client.model.Followup;
import ai.gebo.glpi.client.model.KBArticle;
import ai.gebo.glpi.client.model.Session;
import ai.gebo.glpi.client.model.Solution;
import ai.gebo.glpi.client.model.Ticket;
import ai.gebo.glpi.client.model.TicketTask;

/**
 * The calls the GLPI handler makes on the generated client, with the paging of
 * the list endpoints. Every list is read to its end: a page is asked from the
 * first item not yet read until GLPI answers an empty one, so a server page
 * size smaller than the one asked never truncates a listing.
 */
public class GlpiRemoteAccess {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlpiRemoteAccess.class);
	/** Items asked per page (GLPI documents no default nor maximum) */
	static final long PAGE_SIZE = 100L;
	/**
	 * The session's active entity is read with its sub-entities, so that every
	 * entity under the GLPI user's one is listed and its items found
	 */
	static final String ENTITY_RECURSIVE = "true";
	static final String SORT_BY_ID = "id:asc";

	private final AssistanceApi assistance;
	private final KnowledgebaseApi knowledgebase;
	private final AdministrationApi administration;
	private final ManagementApi management;
	private final SessionApi session;

	public GlpiRemoteAccess(ApiClient apiClient) {
		this(new AssistanceApi(apiClient), new KnowledgebaseApi(apiClient), new AdministrationApi(apiClient),
				new ManagementApi(apiClient), new SessionApi(apiClient));
	}

	GlpiRemoteAccess(AssistanceApi assistance, KnowledgebaseApi knowledgebase, AdministrationApi administration,
			ManagementApi management, SessionApi session) {
		this.assistance = assistance;
		this.knowledgebase = knowledgebase;
		this.administration = administration;
		this.management = management;
		this.session = session;
	}

	/** One page of a list endpoint */
	@FunctionalInterface
	interface PageReader<T> {
		List<T> read(long start, long limit);
	}

	/** Reads every page of a list endpoint */
	static <T> List<T> readAllPages(String what, PageReader<T> reader, Function<T, Long> idOf) {
		List<T> all = new ArrayList<T>();
		long start = 0;
		Long previousFirstId = null;
		while (true) {
			List<T> page = reader.read(start, PAGE_SIZE);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("GLPI " + what + " page start=" + start + " limit=" + PAGE_SIZE + " returned "
						+ (page != null ? page.size() : 0));
			}
			if (page == null || page.isEmpty()) {
				break;
			}
			Long firstId = idOf.apply(page.get(0));
			if (previousFirstId != null && Objects.equals(previousFirstId, firstId)) {
				// The server ignored the start parameter: reading on would loop forever
				LOGGER.warn("GLPI " + what + " paging returned again the page starting with id " + firstId
						+ ", stopping the listing at " + all.size() + " items");
				break;
			}
			previousFirstId = firstId;
			all.addAll(page);
			start += page.size();
		}
		return all;
	}

	private static int id(long value) {
		return Math.toIntExact(value);
	}

	public Session session() {
		return session.sessionGet(null, null, null, null);
	}

	public List<Entity> entities() {
		return readAllPages("entities",
				(start, limit) -> administration.administrationEntityGet(null, start, limit, SORT_BY_ID, null, null,
						ENTITY_RECURSIVE, null),
				Entity::getId);
	}

	/** The entity, null when GLPI does not know it (or no longer lets the user see it) */
	public Entity entity(long entityId) {
		return orNullWhenMissing(
				() -> administration.administrationEntityIdGet(id(entityId), null, null, ENTITY_RECURSIVE, null));
	}

	/** The tickets of one entity (not of its sub-entities), the ones in the trash excluded */
	public List<Ticket> tickets(long entityId) {
		final String filter = GlpiRsqlUtil.ticketsOfEntity(entityId);
		return readAllPages("tickets of entity " + entityId,
				(start, limit) -> assistance.assistanceTicketGet(filter, start, limit, SORT_BY_ID, null, null,
						ENTITY_RECURSIVE, null),
				Ticket::getId);
	}

	/** One page of tickets matching an RSQL filter */
	public List<Ticket> searchTickets(String filter, long limit, String sort) {
		return assistance.assistanceTicketGet(filter, 0L, limit, sort, null, null, ENTITY_RECURSIVE, null);
	}

	public Ticket ticket(long ticketId) {
		return orNullWhenMissing(() -> assistance.assistanceTicketIdGet(id(ticketId), null, null, ENTITY_RECURSIVE, null));
	}

	public List<Followup> followups(long ticketId) {
		return orEmpty(assistance.assistanceTicketIdTimelineFollowupGet(id(ticketId), null, null, ENTITY_RECURSIVE,
				null));
	}

	public Followup followup(long ticketId, long followupId) {
		return orNullWhenMissing(() -> assistance.assistanceTicketIdTimelineFollowupSubitemIdGet(id(ticketId),
				id(followupId), null, null, ENTITY_RECURSIVE, null));
	}

	public List<TicketTask> tasks(long ticketId) {
		return orEmpty(assistance.assistanceTicketIdTimelineTaskGet(id(ticketId), null, null, ENTITY_RECURSIVE, null));
	}

	public TicketTask task(long ticketId, long taskId) {
		return orNullWhenMissing(() -> assistance.assistanceTicketIdTimelineTaskSubitemIdGet(id(ticketId), id(taskId),
				null, null, ENTITY_RECURSIVE, null));
	}

	public List<Solution> solutions(long ticketId) {
		return orEmpty(assistance.assistanceTicketIdTimelineSolutionGet(id(ticketId), null, null, ENTITY_RECURSIVE,
				null));
	}

	public Solution solution(long ticketId, long solutionId) {
		return orNullWhenMissing(() -> assistance.assistanceTicketIdTimelineSolutionSubitemIdGet(id(ticketId),
				id(solutionId), null, null, ENTITY_RECURSIVE, null));
	}

	/** The documents attached to a ticket (to the ticket itself or to its timeline items) */
	public List<Document> ticketDocuments(long ticketId) {
		return orEmpty(assistance.assistanceTicketIdTimelineDocumentGet(id(ticketId), null, null, ENTITY_RECURSIVE,
				null));
	}

	public Document ticketDocument(long ticketId, long documentId) {
		return orNullWhenMissing(() -> assistance.assistanceTicketIdTimelineDocumentSubitemIdGet(id(ticketId),
				id(documentId), null, null, ENTITY_RECURSIVE, null));
	}

	public Document document(long documentId) {
		return orNullWhenMissing(
				() -> management.managementDocumentIdGet(id(documentId), null, null, ENTITY_RECURSIVE, null));
	}

	/** The contents of a GLPI document */
	public InputStream download(long documentId) {
		byte[] data = management.managementDocumentIdDownloadGetWithResponseSpec(id(documentId), null, null, null,
				null, ENTITY_RECURSIVE, null).body(byte[].class);
		return data != null ? new ByteArrayInputStream(data) : InputStream.nullInputStream();
	}

	/** The knowledge base articles of one entity */
	public List<KBArticle> kbArticles(long entityId) {
		final String filter = GlpiRsqlUtil.ofEntity(entityId);
		return readAllPages("knowledge base articles of entity " + entityId,
				(start, limit) -> knowledgebase.knowledgebaseArticleGet(null, filter, start, limit, SORT_BY_ID, null,
						null, ENTITY_RECURSIVE, null),
				KBArticle::getId);
	}

	/** One page of knowledge base articles matching an RSQL filter */
	public List<KBArticle> searchKbArticles(String filter, long limit, String sort) {
		return knowledgebase.knowledgebaseArticleGet(null, filter, 0L, limit, sort, null, null, ENTITY_RECURSIVE, null);
	}

	public KBArticle kbArticle(long articleId) {
		return orNullWhenMissing(
				() -> knowledgebase.knowledgebaseArticleArticleIdGet(id(articleId), null, null, null, ENTITY_RECURSIVE, null));
	}

	private static <T> List<T> orEmpty(List<T> list) {
		return list != null ? list : List.of();
	}

	@FunctionalInterface
	private interface Call<T> {
		T call();
	}

	private static <T> T orNullWhenMissing(Call<T> call) {
		try {
			return call.call();
		} catch (HttpClientErrorException.NotFound e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("GLPI answered 404: " + e.getMessage());
			}
			return null;
		}
	}
}
