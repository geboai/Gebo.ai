/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.impl;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.zammad.client.api.GroupsApi;
import ai.gebo.zammad.client.api.KnowledgeBaseAnswersApi;
import ai.gebo.zammad.client.api.KnowledgeBaseApi;
import ai.gebo.zammad.client.api.TicketArticlesApi;
import ai.gebo.zammad.client.api.TicketPrioritiesApi;
import ai.gebo.zammad.client.api.TicketStatesApi;
import ai.gebo.zammad.client.api.TicketsApi;
import ai.gebo.zammad.client.api.UsersApi;
import ai.gebo.zammad.client.invoker.ApiClient;
import ai.gebo.zammad.client.model.Ticket;

/**
 * An authenticated connection to the REST API of one Zammad server, through the
 * generated client. Three calls are made here instead:
 * <ul>
 * <li>the ticket search: the specification describes its answer as an object
 * ({@code records}, {@code total_count}...) while Zammad 7 answers the plain
 * list of tickets, which the client cannot read; the list is read here, as the
 * client's own {@link Ticket} model</li>
 * <li>the downloads of the ticket and knowledge base attachments, streamed
 * instead of written to a temporary file (the client has no download of the
 * knowledge base attachments at all)</li>
 * </ul>
 * Every URL is built from the configured base URI, never taken from an answer,
 * so the credentials only go to the configured server.
 */
public class ZammadConnection {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZammadConnection.class);
	/** The page size of every list read. */
	public static final int PAGE_SIZE = 100;
	private static final ParameterizedTypeReference<List<Ticket>> TICKETS = new ParameterizedTypeReference<List<Ticket>>() {
	};
	private final ApiClient apiClient;
	private static final ParameterizedTypeReference<List<Map<String, Object>>> MAPS = new ParameterizedTypeReference<List<Map<String, Object>>>() {
	};
	private final RestClient restClient;
	private final String baseUri;

	public ZammadConnection(ApiClient apiClient, RestClient restClient, String baseUri) {
		this.apiClient = apiClient;
		this.restClient = restClient;
		this.baseUri = baseUri;
	}

	public String getBaseUri() {
		return baseUri;
	}

	public UsersApi users() {
		return new UsersApi(apiClient);
	}

	public GroupsApi groups() {
		return new GroupsApi(apiClient);
	}

	public TicketsApi tickets() {
		return new TicketsApi(apiClient);
	}

	public TicketArticlesApi articles() {
		return new TicketArticlesApi(apiClient);
	}

	public TicketStatesApi ticketStates() {
		return new TicketStatesApi(apiClient);
	}

	public TicketPrioritiesApi ticketPriorities() {
		return new TicketPrioritiesApi(apiClient);
	}

	public KnowledgeBaseApi knowledgeBases() {
		return new KnowledgeBaseApi(apiClient);
	}

	public KnowledgeBaseAnswersApi answers() {
		return new KnowledgeBaseAnswersApi(apiClient);
	}

	URI uri(String apiPath, Map<String, String> query) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUri + "/api/v1" + apiPath);
		Map<String, String> values = new LinkedHashMap<String, String>();
		if (query != null) {
			query.forEach((k, v) -> {
				if (v != null) {
					builder.queryParam(k, "{" + k + "}");
					values.put(k, v);
				}
			});
		}
		return builder.encode().buildAndExpand(values).toUri();
	}

	/**
	 * One page of a ticket search, expanded (the names of the group, state,
	 * priority, owner and customer are additional properties of each ticket). A
	 * {@code condition} searches the database, so it works without Elasticsearch;
	 * a {@code query} is a full text search, served by Elasticsearch when Zammad
	 * has it and by a simpler database search when it has not.
	 */
	public List<Ticket> searchTickets(Map<String, Object> condition, String query, String sortBy, String orderBy,
			int page, int perPage) {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("expand", "true");
		params.put("page", String.valueOf(page));
		params.put("per_page", String.valueOf(perPage));
		Map<String, Object> body = new LinkedHashMap<String, Object>();
		if (condition != null && !condition.isEmpty()) {
			body.put("condition", condition);
		}
		if (query != null && !query.isBlank()) {
			body.put("query", query);
		}
		if (sortBy != null) {
			body.put("sort_by", sortBy);
			body.put("order_by", orderBy != null ? orderBy : "asc");
		}
		URI uri = uri("/tickets/search", params);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("POST {} with {}", uri, body);
		}
		List<Ticket> tickets = restClient.post().uri(uri).contentType(MediaType.APPLICATION_JSON).body(body).retrieve()
				.body(TICKETS);
		if (tickets == null) {
			tickets = List.of();
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("POST {} answered {}", uri, tickets);
		}
		return tickets;
	}

	/**
	 * Every ticket matching a condition, paged to the end, oldest first.
	 */
	public List<Ticket> allTickets(Map<String, Object> condition) {
		List<Ticket> all = new ArrayList<Ticket>();
		for (int page = 1;; page++) {
			List<Ticket> tickets = searchTickets(condition, null, "id", "asc", page, PAGE_SIZE);
			all.addAll(tickets);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Zammad tickets page {} read: {} tickets, {} so far", page, tickets.size(), all.size());
			}
			if (tickets.size() < PAGE_SIZE) {
				return all;
			}
		}
	}

	/**
	 * A ticket, expanded like the search results.
	 */
	/**
	 * The ticket states, expanded: each with the name of its state type
	 * ({@code state_type}), which the typed client model does not keep.
	 */
	public List<Map<String, Object>> ticketStatesWithTypes() {
		URI uri = uri("/ticket_states", Map.of("expand", "true"));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GET {}", uri);
		}
		List<Map<String, Object>> states = restClient.get().uri(uri).retrieve().body(MAPS);
		return states != null ? states : List.of();
	}

	public Ticket ticket(Long ticketId) {
		URI uri = uri("/tickets/" + ticketId, Map.of("expand", "true"));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GET {}", uri);
		}
		return restClient.get().uri(uri).retrieve().body(Ticket.class);
	}

	/**
	 * Streams the attachment of a ticket article. The response stays open until
	 * the stream is closed.
	 */
	public InputStream downloadTicketAttachment(Long ticketId, Long articleId, Long attachmentId) throws IOException {
		return download(uri("/ticket_attachment/" + ticketId + "/" + articleId + "/" + attachmentId, null),
				"the attachment " + attachmentId + " of the article " + articleId + " of the ticket " + ticketId);
	}

	/**
	 * Streams an attachment by id: the attachments of the knowledge base answers.
	 */
	public InputStream downloadAttachment(Long attachmentId) throws IOException {
		return download(uri("/attachments/" + attachmentId, null), "the attachment " + attachmentId);
	}

	private InputStream download(URI uri, String what) throws IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Downloading {} from {}", what, uri);
		}
		return restClient.get().uri(uri).exchange((request, response) -> {
			if (response.getStatusCode().isError()) {
				int status = response.getStatusCode().value();
				response.close();
				throw new IOException("Zammad answered " + status + " downloading " + what);
			}
			InputStream body = response.getBody();
			return new FilterInputStream(body) {
				@Override
				public void close() throws IOException {
					try {
						super.close();
					} finally {
						response.close();
					}
				}
			};
		}, false);
	}
}
