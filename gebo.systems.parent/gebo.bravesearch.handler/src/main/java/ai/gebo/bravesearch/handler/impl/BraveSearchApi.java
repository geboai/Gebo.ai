/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.bravesearch.handler.impl;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.bravesearch.handler.model.BraveApiResponse;
import ai.gebo.bravesearch.handler.model.BraveApiResponse.BraveResult;
import ai.gebo.bravesearch.handler.model.BraveSearchRequest;
import ai.gebo.bravesearch.handler.model.BraveSearchResultItem;
import ai.gebo.bravesearch.handler.model.BraveSearchResults;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.architecture.search.config.SearchCallsConfig;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;

/**
 * Thin REST client for the Brave Web Search API + the LLM tool factory. Auth is
 * the {@code X-Subscription-Token} header; the query is properly percent-encoded
 * via {@code encode().build()} so multi-word queries are handled correctly.
 */
@Service
public class BraveSearchApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(BraveSearchApi.class);
	public static final String BRAVE_SEARCH_URL = "https://api.search.brave.com/res/v1/web/search";
	private static final int DEFAULT_COUNT = 5;

	private final RestTemplateWrapperService restTemplateService;

	/**
	 * The provider is called with an HTTP client of its own, whose connect and read
	 * timeouts are the search calls' ones (see {@link SearchCallsConfig}): a provider not
	 * answering on a sloppy network does not hold the search forever.
	 */
	@Autowired
	public BraveSearchApi(SearchCallsConfig searchCalls) {
		this(RestTemplateWrapperService.withTimeouts(searchCalls.httpConnectTimeout(), searchCalls.httpReadTimeout()));
	}

	BraveSearchApi(RestTemplateWrapperService restTemplateService) {
		this.restTemplateService = restTemplateService;
	}

	BraveApiResponse callApi(String apiKey, String query, Integer topN) throws GeboRestIntegrationException {
		return callApi(apiKey, query, topN, null, null, null);
	}

	BraveApiResponse callApi(String apiKey, String query, Integer topN, String freshness, String country,
			String safesearch) throws GeboRestIntegrationException {
		if (query == null || query.isBlank()) {
			throw new IllegalArgumentException("query must be provided");
		}
		if (apiKey == null || apiKey.isBlank()) {
			throw new IllegalArgumentException("apiKey must be provided");
		}
		UriComponentsBuilder b = UriComponentsBuilder.fromUriString(BRAVE_SEARCH_URL).queryParam("q", query)
				.queryParam("count", topN != null && topN > 0 ? topN : DEFAULT_COUNT);
		if (StringUtils.hasText(freshness)) {
			b.queryParam("freshness", freshness);
		}
		if (StringUtils.hasText(country)) {
			b.queryParam("country", country);
		}
		if (StringUtils.hasText(safesearch)) {
			b.queryParam("safesearch", safesearch);
		}
		URI uri = b.encode().build().toUri();

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Subscription-Token", apiKey);
		headers.set("Accept-Encoding", "gzip");
		headers.setAccept(List.of(MediaType.APPLICATION_JSON));

		HttpEntity<Void> entity = new HttpEntity<Void>(headers);
		ResponseEntity<BraveApiResponse> resp = restTemplateService.exchange(uri.toString(), HttpMethod.GET, entity,
				BraveApiResponse.class);
		return resp.getBody();
	}

	BraveSearchResults search(String apiKey, BraveSearchRequest request) throws GeboRestIntegrationException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Calling brave search with:" + request.getQuery());
		}
		BraveApiResponse response = callApi(apiKey, request.getQuery(), request.getTopN(), request.getFreshness(),
				request.getCountry(), request.getSafesearch());
		BraveSearchResults out = new BraveSearchResults();
		if (response != null && response.getWeb() != null && response.getWeb().getResults() != null) {
			for (BraveResult r : response.getWeb().getResults()) {
				BraveSearchResultItem item = new BraveSearchResultItem();
				item.setTitle(r.getTitle());
				item.setUrl(r.getUrl());
				item.setContent(r.getDescription());
				out.getItems().add(item);
			}
		}
		return out;
	}

}
