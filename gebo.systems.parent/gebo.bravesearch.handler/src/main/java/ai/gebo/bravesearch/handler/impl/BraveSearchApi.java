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
import ai.gebo.bravesearch.handler.model.BraveApiResponse;
import ai.gebo.bravesearch.handler.model.BraveApiResponse.BraveResult;
import ai.gebo.bravesearch.handler.model.BraveSearchConfig;
import ai.gebo.bravesearch.handler.model.BraveSearchRequest;
import ai.gebo.bravesearch.handler.model.BraveSearchResultItem;
import ai.gebo.bravesearch.handler.model.BraveSearchResults;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import lombok.AllArgsConstructor;

/**
 * Thin REST client for the Brave Web Search API + the LLM tool factory. Auth is
 * the {@code X-Subscription-Token} header; the query is properly percent-encoded
 * via {@code encode().build()} (unlike the retired Bing handler's build(true),
 * which threw on any multi-word query).
 */
@Service
@AllArgsConstructor
public class BraveSearchApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(BraveSearchApi.class);
	public static final String BRAVE_SEARCH_URL = "https://api.search.brave.com/res/v1/web/search";
	static final String SEARCH_WEB_WITH_BRAVE = "searchWebWithBrave";
	static final String RUNNING_A_BRAVE_SEARCH = "Running a Brave web search";
	private static final int DEFAULT_COUNT = 5;

	private final RestTemplateWrapperService restTemplateService;

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

	ToolCallback create(BraveSearchConfig config) {
		BiFunction<BraveSearchRequest, ToolContext, BraveSearchResults> thisFunction = (request, toolContext) -> {
			BraveSearchResults results = null;
			LOGGER.info("Begin running brave search");
			KBContext context = LLMtInteractionContextThreadLocal.Context.get();
			LLMtInteractionContextThreadLocal.CalledFunction calledFunction = new LLMtInteractionContextThreadLocal.CalledFunction();
			calledFunction.setFunctionName(SEARCH_WEB_WITH_BRAVE);
			calledFunction.setFunctionDescription(RUNNING_A_BRAVE_SEARCH);
			if (request.getQuery() != null) {
				calledFunction.setParamsDescription(List.of(request.getQuery()));
			}
			if (context != null) {
				context.getCalledFunctions().add(calledFunction);
			}
			ToolCallbackDeclarationUtil.addCallToContext(toolContext, calledFunction);
			try {
				results = search(config.getApiKey(), request);
			} catch (Throwable th) {
				LOGGER.error("Error running brave search", th);
				results = new BraveSearchResults();
			}
			LOGGER.info("End running brave search");
			return results;
		};
		return ToolCallbackDeclarationUtil.declare(thisFunction, SEARCH_WEB_WITH_BRAVE, RUNNING_A_BRAVE_SEARCH,
				BraveSearchRequest.class, BraveSearchResults.class);
	}
}
