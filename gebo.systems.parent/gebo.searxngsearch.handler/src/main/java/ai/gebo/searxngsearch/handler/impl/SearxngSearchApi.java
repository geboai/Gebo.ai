/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.searxngsearch.handler.impl;

import java.net.URI;
import ai.gebo.architecture.search.service.AbstractWebSearchServiceImpl;
import java.util.List;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.searxngsearch.handler.model.SearxngApiResponse;
import ai.gebo.searxngsearch.handler.model.SearxngApiResponse.SearxngApiResult;
import ai.gebo.searxngsearch.handler.model.SearxngSearchConfig;
import ai.gebo.searxngsearch.handler.model.SearxngSearchRequest;
import ai.gebo.searxngsearch.handler.model.SearxngSearchResultItem;
import ai.gebo.searxngsearch.handler.model.SearxngSearchResults;
import lombok.AllArgsConstructor;

/**
 * Thin REST client for a self-hosted SearXNG instance ({@code format=json}) and
 * the LLM tool factory. Base URL comes from configuration; auth is an optional
 * bearer token. Query is properly percent-encoded.
 */
@Service
@AllArgsConstructor
public class SearxngSearchApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearxngSearchApi.class);
	static final String SEARCH_WEB_WITH_SEARXNG = AbstractWebSearchServiceImpl.WEB_SEARCH_TOOL_NAME;
	static final String RUNNING_A_SEARXNG_SEARCH = AbstractWebSearchServiceImpl.WEB_SEARCH_TOOL_DESCRIPTION;
	private static final int DEFAULT_LIMIT = 5;

	private final RestTemplateWrapperService restTemplateService;

	private static String searchEndpoint(String baseUrl) {
		String base = baseUrl.trim();
		while (base.endsWith("/")) {
			base = base.substring(0, base.length() - 1);
		}
		return base + "/search";
	}

	SearxngApiResponse callApi(String baseUrl, String apiKey, String query, Integer topN)
			throws GeboRestIntegrationException {
		return callApi(baseUrl, apiKey, query, topN, null, null, null);
	}

	SearxngApiResponse callApi(String baseUrl, String apiKey, String query, Integer topN, String categories,
			String timeRange, String language) throws GeboRestIntegrationException {
		if (query == null || query.isBlank()) {
			throw new IllegalArgumentException("query must be provided");
		}
		if (baseUrl == null || baseUrl.isBlank()) {
			throw new IllegalArgumentException("SearXNG base URL must be provided");
		}
		UriComponentsBuilder b = UriComponentsBuilder.fromUriString(searchEndpoint(baseUrl)).queryParam("q", query)
				.queryParam("format", "json");
		if (StringUtils.hasText(categories)) {
			b.queryParam("categories", categories);
		}
		if (StringUtils.hasText(timeRange)) {
			b.queryParam("time_range", timeRange);
		}
		if (StringUtils.hasText(language)) {
			b.queryParam("language", language);
		}
		URI uri = b.encode().build().toUri();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(List.of(MediaType.APPLICATION_JSON));
		if (StringUtils.hasText(apiKey)) {
			headers.setBearerAuth(apiKey);
		}

		HttpEntity<Void> entity = new HttpEntity<Void>(headers);
		ResponseEntity<SearxngApiResponse> resp = restTemplateService.exchange(uri.toString(), HttpMethod.GET, entity,
				SearxngApiResponse.class);
		return resp.getBody();
	}

	SearxngSearchResults search(String baseUrl, String apiKey, SearxngSearchRequest request)
			throws GeboRestIntegrationException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Calling searxng search with:" + request.getQuery());
		}
		SearxngApiResponse response = callApi(baseUrl, apiKey, request.getQuery(), request.getTopN(),
				request.getCategories(), request.getTimeRange(), request.getLanguage());
		SearxngSearchResults out = new SearxngSearchResults();
		int limit = request.getTopN() != null && request.getTopN() > 0 ? request.getTopN() : DEFAULT_LIMIT;
		if (response != null && response.getResults() != null) {
			for (SearxngApiResult r : response.getResults()) {
				if (out.getItems().size() >= limit) {
					break;
				}
				SearxngSearchResultItem item = new SearxngSearchResultItem();
				item.setTitle(r.getTitle());
				item.setUrl(r.getUrl());
				item.setContent(r.getContent());
				out.getItems().add(item);
			}
		}
		return out;
	}

	ToolCallback create(SearxngSearchConfig config) {
		BiFunction<SearxngSearchRequest, ToolContext, SearxngSearchResults> thisFunction = (request, toolContext) -> {
			SearxngSearchResults results = null;
			LOGGER.info("Begin running searxng search");
			KBContext context = LLMtInteractionContextThreadLocal.Context.get();
			LLMtInteractionContextThreadLocal.CalledFunction calledFunction = new LLMtInteractionContextThreadLocal.CalledFunction();
			calledFunction.setFunctionName(SEARCH_WEB_WITH_SEARXNG);
			calledFunction.setFunctionDescription(RUNNING_A_SEARXNG_SEARCH);
			if (request.getQuery() != null) {
				calledFunction.setParamsDescription(List.of(request.getQuery()));
			}
			if (context != null) {
				context.getCalledFunctions().add(calledFunction);
			}
			ToolCallbackDeclarationUtil.addCallToContext(toolContext, calledFunction);
			try {
				results = search(config.getBaseUrl(), config.getApiKey(), request);
			} catch (Throwable th) {
				LOGGER.error("Error running searxng search", th);
				results = new SearxngSearchResults();
			}
			LOGGER.info("End running searxng search");
			return results;
		};
		return ToolCallbackDeclarationUtil.declare(thisFunction, SEARCH_WEB_WITH_SEARXNG, RUNNING_A_SEARXNG_SEARCH,
				SearxngSearchRequest.class, SearxngSearchResults.class);
	}
}
