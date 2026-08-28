/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.serpapisearch.handler.impl;

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
import ai.gebo.serpapisearch.handler.model.SerpapiApiResponse;
import ai.gebo.serpapisearch.handler.model.SerpapiApiResponse.SerpapiOrganicResult;
import ai.gebo.serpapisearch.handler.model.SerpapiSearchConfig;
import ai.gebo.serpapisearch.handler.model.SerpapiSearchRequest;
import ai.gebo.serpapisearch.handler.model.SerpapiSearchResultItem;
import ai.gebo.serpapisearch.handler.model.SerpapiSearchResults;
import lombok.AllArgsConstructor;

/**
 * Thin REST client for SerpApi (search.json) + the LLM tool factory. SerpApi
 * returns real search-engine results; auth is the {@code api_key} query param.
 * The query is percent-encoded via {@code encode().build()}.
 */
@Service
@AllArgsConstructor
public class SerpapiSearchApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(SerpapiSearchApi.class);
	public static final String SERPAPI_SEARCH_URL = "https://serpapi.com/search.json";
	static final String SEARCH_WEB_WITH_SERPAPI = AbstractWebSearchServiceImpl.WEB_SEARCH_TOOL_NAME;
	static final String RUNNING_A_SERPAPI_SEARCH = AbstractWebSearchServiceImpl.WEB_SEARCH_TOOL_DESCRIPTION;
	private static final int DEFAULT_NUM = 5;
	private static final String DEFAULT_ENGINE = "google";

	private final RestTemplateWrapperService restTemplateService;

	SerpapiApiResponse callApi(String apiKey, String query, Integer topN, String engine, String gl, String hl,
			String tbs) throws GeboRestIntegrationException {
		if (query == null || query.isBlank()) {
			throw new IllegalArgumentException("query must be provided");
		}
		if (apiKey == null || apiKey.isBlank()) {
			throw new IllegalArgumentException("apiKey must be provided");
		}
		UriComponentsBuilder b = UriComponentsBuilder.fromUriString(SERPAPI_SEARCH_URL)
				.queryParam("engine", StringUtils.hasText(engine) ? engine : DEFAULT_ENGINE)
				.queryParam("q", query)
				.queryParam("num", topN != null && topN > 0 ? topN : DEFAULT_NUM)
				.queryParam("api_key", apiKey);
		if (StringUtils.hasText(gl)) {
			b.queryParam("gl", gl);
		}
		if (StringUtils.hasText(hl)) {
			b.queryParam("hl", hl);
		}
		if (StringUtils.hasText(tbs)) {
			b.queryParam("tbs", tbs);
		}
		URI uri = b.encode().build().toUri();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(List.of(MediaType.APPLICATION_JSON));
		HttpEntity<Void> entity = new HttpEntity<Void>(headers);
		ResponseEntity<SerpapiApiResponse> resp = restTemplateService.exchange(uri.toString(), HttpMethod.GET, entity,
				SerpapiApiResponse.class);
		return resp.getBody();
	}

	SerpapiSearchResults search(String apiKey, SerpapiSearchRequest request) throws GeboRestIntegrationException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Calling serpapi search with:" + request.getQuery());
		}
		SerpapiApiResponse response = callApi(apiKey, request.getQuery(), request.getTopN(), request.getEngine(),
				request.getGl(), request.getHl(), request.getTbs());
		SerpapiSearchResults out = new SerpapiSearchResults();
		if (response != null && response.getOrganic_results() != null) {
			for (SerpapiOrganicResult r : response.getOrganic_results()) {
				SerpapiSearchResultItem item = new SerpapiSearchResultItem();
				item.setTitle(r.getTitle());
				item.setUrl(r.getLink());
				item.setContent(r.getSnippet());
				out.getItems().add(item);
			}
		}
		return out;
	}

	ToolCallback create(SerpapiSearchConfig config) {
		BiFunction<SerpapiSearchRequest, ToolContext, SerpapiSearchResults> thisFunction = (request, toolContext) -> {
			SerpapiSearchResults results = null;
			LOGGER.info("Begin running serpapi search");
			KBContext context = LLMtInteractionContextThreadLocal.Context.get();
			LLMtInteractionContextThreadLocal.CalledFunction calledFunction = new LLMtInteractionContextThreadLocal.CalledFunction();
			calledFunction.setFunctionName(SEARCH_WEB_WITH_SERPAPI);
			calledFunction.setFunctionDescription(RUNNING_A_SERPAPI_SEARCH);
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
				LOGGER.error("Error running serpapi search", th);
				results = new SerpapiSearchResults();
			}
			LOGGER.info("End running serpapi search");
			return results;
		};
		return ToolCallbackDeclarationUtil.declare(thisFunction, SEARCH_WEB_WITH_SERPAPI, RUNNING_A_SERPAPI_SEARCH,
				SerpapiSearchRequest.class, SerpapiSearchResults.class);
	}
}
