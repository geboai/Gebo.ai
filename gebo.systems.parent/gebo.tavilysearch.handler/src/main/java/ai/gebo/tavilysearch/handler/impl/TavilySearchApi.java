/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.tavilysearch.handler.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.architecture.search.config.SearchCallsConfig;
import ai.gebo.architecture.search.model.SearchCallParameters;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.tavilysearch.handler.model.TavilyApiResponse;
import ai.gebo.tavilysearch.handler.model.TavilyApiResponse.TavilyApiResult;
import ai.gebo.tavilysearch.handler.model.TavilySearchRequest;
import ai.gebo.tavilysearch.handler.model.TavilySearchResultItem;
import ai.gebo.tavilysearch.handler.model.TavilySearchResults;

/**
 * Thin REST client for the Tavily Search API, and the factory for the LLM
 * web-search tool callback. Tavily is a POST/JSON API, so the body carries the
 * query (no URL-encoding pitfalls); auth is a bearer token.
 */
@Service
public class TavilySearchApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(TavilySearchApi.class);
	public static final String TAVILY_SEARCH_URL = "https://api.tavily.com/search";
	private static final int DEFAULT_MAX_RESULTS = 5;

	private final RestTemplateWrapperService restTemplateService;

	/**
	 * The provider is called with an HTTP client of its own, whose connect and read
	 * timeouts are the search calls' ones (see {@link SearchCallsConfig}): a provider not
	 * answering on a sloppy network does not hold the search forever.
	 */
	@Autowired
	public TavilySearchApi(SearchCallsConfig searchCalls) {
		this(RestTemplateWrapperService.withTimeouts(searchCalls.httpConnectTimeout(), searchCalls.httpReadTimeout()));
	}

	TavilySearchApi(RestTemplateWrapperService restTemplateService) {
		this.restTemplateService = restTemplateService;
	}

	/** This API called with an HTTP client of its own whose timeouts are the call parameters'. */
	TavilySearchApi using(SearchCallParameters parameters) {
		return new TavilySearchApi(
				RestTemplateWrapperService.withTimeouts(parameters.connectTimeout(), parameters.readTimeout()));
	}

	TavilyApiResponse callApi(String apiKey, String query, Integer topN) throws GeboRestIntegrationException {
		return callApi(apiKey, query, topN, null, null, null);
	}

	TavilyApiResponse callApi(String apiKey, String query, Integer topN, String searchDepth, String topic,
			String timeRange) throws GeboRestIntegrationException {
		if (query == null || query.isBlank()) {
			throw new IllegalArgumentException("query must be provided");
		}
		if (apiKey == null || apiKey.isBlank()) {
			throw new IllegalArgumentException("apiKey must be provided");
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(List.of(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(apiKey);

		Map<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("query", query);
		body.put("max_results", topN != null && topN > 0 ? topN : DEFAULT_MAX_RESULTS);
		body.put("search_depth", StringUtils.hasText(searchDepth) ? searchDepth : "basic");
		if (StringUtils.hasText(topic)) {
			body.put("topic", topic);
		}
		if (StringUtils.hasText(timeRange)) {
			body.put("time_range", timeRange);
		}
		body.put("include_answer", false);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<Map<String, Object>>(body, headers);
		ResponseEntity<TavilyApiResponse> resp = restTemplateService.exchange(TAVILY_SEARCH_URL, HttpMethod.POST,
				entity, TavilyApiResponse.class);
		return resp.getBody();
	}

	TavilySearchResults search(String apiKey, TavilySearchRequest request) throws GeboRestIntegrationException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Calling tavily search with:" + request.getQuery());
		}
		TavilyApiResponse response = callApi(apiKey, request.getQuery(), request.getTopN(), request.getSearchDepth(),
				request.getTopic(), request.getTimeRange());
		TavilySearchResults out = new TavilySearchResults();
		if (response != null) {
			out.setAnswer(response.getAnswer());
			if (response.getResults() != null) {
				for (TavilyApiResult r : response.getResults()) {
					TavilySearchResultItem item = new TavilySearchResultItem();
					item.setTitle(r.getTitle());
					item.setUrl(r.getUrl());
					item.setContent(r.getContent());
					out.getItems().add(item);
				}
			}
		}
		return out;
	}

}
