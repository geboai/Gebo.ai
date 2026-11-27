/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.impl;

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
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * An authenticated connection to the API v3 of one OpenProject server, read as
 * untyped HAL: the generated client follows a specification that leaves some
 * collections untyped (activities, documents, news) and some parameters out
 * (paging of projects, filters of documents), so every read goes through the
 * same map based access ({@link OpenProjectHal}). Every URL is built from the
 * configured base URI, so the credentials only go to the configured server.
 */
public class OpenProjectConnection {
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenProjectConnection.class);
	/** The page size of every collection read. */
	public static final int PAGE_SIZE = 100;
	private static final ParameterizedTypeReference<Map<String, Object>> MAP = new ParameterizedTypeReference<Map<String, Object>>() {
	};
	private final RestClient restClient;
	private final String baseUri;

	public OpenProjectConnection(RestClient restClient, String baseUri) {
		this.restClient = restClient;
		this.baseUri = baseUri;
	}

	public String getBaseUri() {
		return baseUri;
	}

	URI uri(String apiPath, Map<String, String> query) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUri + "/api/v3" + apiPath);
		Map<String, String> values = new LinkedHashMap<String, String>();
		if (query != null) {
			query.forEach((k, v) -> {
				if (v != null) {
					builder.queryParam(k, "{" + k + "}");
					values.put(k, v);
				}
			});
		}
		// values are expanded as URI variables: the JSON of the filters is encoded
		return builder.encode().buildAndExpand(values).toUri();
	}

	/**
	 * One resource of the API v3, e.g. "/work_packages/12".
	 */
	public Map<String, Object> get(String apiPath, Map<String, String> query) {
		URI uri = uri(apiPath, query);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GET {}", uri);
		}
		Map<String, Object> body = restClient.get().uri(uri).retrieve().body(MAP);
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("GET {} answered {}", uri, body);
		}
		return body;
	}

	public Map<String, Object> get(String apiPath) {
		return get(apiPath, null);
	}

	/**
	 * Every element of a collection of the API v3, paged to the end (OpenProject
	 * counts the offset in pages, from 1).
	 */
	public List<Map<String, Object>> all(String apiPath, Map<String, String> query) {
		List<Map<String, Object>> elements = new ArrayList<Map<String, Object>>();
		Map<String, String> params = new LinkedHashMap<String, String>();
		if (query != null) {
			params.putAll(query);
		}
		params.put("pageSize", String.valueOf(PAGE_SIZE));
		int page = 1;
		while (true) {
			params.put("offset", String.valueOf(page));
			Map<String, Object> collection = get(apiPath, params);
			List<Map<String, Object>> items = OpenProjectHal.elements(collection);
			elements.addAll(items);
			Integer total = OpenProjectHal.integer(collection, "total");
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("{} page {} read: {} items, {} of {}", apiPath, page, items.size(), elements.size(), total);
			}
			if (items.isEmpty() || total == null || elements.size() >= total) {
				break;
			}
			page++;
		}
		return elements;
	}

	/**
	 * At most the first page of a collection, for searches with a result limit.
	 */
	public List<Map<String, Object>> first(String apiPath, Map<String, String> query, int pageSize) {
		Map<String, String> params = new LinkedHashMap<String, String>();
		if (query != null) {
			params.putAll(query);
		}
		params.put("pageSize", String.valueOf(pageSize));
		params.put("offset", "1");
		return OpenProjectHal.elements(get(apiPath, params));
	}

	/**
	 * Streams the content of an attachment. The response stays open until the
	 * stream is closed.
	 */
	public InputStream downloadAttachment(String attachmentId) throws IOException {
		URI uri = uri("/attachments/" + attachmentId + "/content", null);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Downloading the OpenProject attachment {} from {}", attachmentId, uri);
		}
		return restClient.get().uri(uri).exchange((request, response) -> {
			if (response.getStatusCode().isError()) {
				int status = response.getStatusCode().value();
				response.close();
				throw new IOException("OpenProject answered " + status + " downloading the attachment " + attachmentId);
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
