/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * AI generated comments
 * This class provides an API interface for using Google's Custom Search API.
 * It handles the construction of search requests, executing them, and processing results.
 */
package ai.gebo.googlesearch.handler.impl;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import ai.gebo.architecture.ai.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;
import ai.gebo.googlesearch.handler.model.GoogleSearchConfig;
import ai.gebo.googlesearch.handler.model.GoogleSearchRequest;
import ai.gebo.googlesearch.handler.model.GoogleSearchResults;

@Service
class GoogleSearchApi {
	/** Logger for this class */
	static Logger LOGGER = LoggerFactory.getLogger(GoogleSearchApi.class);
	/** Base URL for Google Custom Search API */
	private static final String googleSearch = "https://www.googleapis.com/customsearch/v1?key=";
	/** Descriptive text for logging search operations */
	static final String RUNNING_A_GOOGLE_SEARCH = "Running a google search";
	/** Function name for the search web tool */
	static final String SEARCH_WEB_WITH_GOOGLE = "searchWebWithGoogle";

	/**
	 * search with google api documentation:
	 * https://developers.google.com/custom-search/v1/reference/rest/v1/cse/list?hl=it
	 * 
	 * @param apiKey                Google API key for authorization
	 * @param customSearchEngineId  Custom Search Engine ID to specify which search engine to use
	 * @param request               The search request containing query and other parameters
	 * @return                      Results from the Google search
	 * @throws MalformedURLException           If the constructed URL is invalid
	 * @throws UnsupportedEncodingException    If encoding parameters fails
	 * @throws RestClientException             If the REST call fails
	 * @throws URISyntaxException              If the URL can't be converted to a URI
	 */
	public GoogleSearchResults search(String apiKey, String customSearchEngineId, GoogleSearchRequest request)
			throws MalformedURLException, UnsupportedEncodingException, RestClientException, URISyntaxException {
		LOGGER.info("Calling google search with:" + request.getQuery());
		String charset = "UTF-8";
		String language = request.getLanguage();
		language = "lang_en";
		String search = request.getQuery();
		URL url = new URL(googleSearch + URLEncoder.encode(apiKey, charset) + "&cx="
				+ URLEncoder.encode(customSearchEngineId, charset) + "&q=" + URLEncoder.encode(search, charset) + "&lr"
				+ URLEncoder.encode(language, charset));
		System.out.println(url);
		RestTemplate restTemplate = new RestTemplate();
		URI uri = url.toURI();
		ResponseEntity<GoogleSearchResults> returned = restTemplate.getForEntity(uri, GoogleSearchResults.class);
		if (returned.hasBody())
			return returned.getBody();
		return new GoogleSearchResults();
	}

	/**
	 * Creates a ToolCallback that can be used to perform Google searches.
	 * 
	 * @param config    Configuration containing API key and Custom Search Engine ID
	 * @return          A ToolCallback that can execute Google searches
	 */
	ToolCallback create(GoogleSearchConfig config) {
		BiFunction<GoogleSearchRequest, ToolContext, GoogleSearchResults> thisFunction = (GoogleSearchRequest request,
				ToolContext toolContext) -> {
			GoogleSearchResults results = null;

			LOGGER.info("Begin running google search");
			KBContext context = LLMtInteractionContextThreadLocal.Context.get();
			LLMtInteractionContextThreadLocal.CalledFunction calledFunction = new LLMtInteractionContextThreadLocal.CalledFunction();
			calledFunction.setFunctionName(SEARCH_WEB_WITH_GOOGLE);
			calledFunction.setFunctionDescription(RUNNING_A_GOOGLE_SEARCH);
			if (request.getQuery() != null) {
				calledFunction.setParamsDescription(List.of(request.getQuery()));
			}
			if (context != null) {
				// Add the function call to the current context for tracking
				context.getCalledFunctions().add(calledFunction);
			}
			ToolCallbackDeclarationUtil.addCallToContext(toolContext, calledFunction);
			try {
				results = search(config.getApiKey(), config.getCustomSearchEngineId(), request);
			} catch (Throwable th) {
				LOGGER.error("Error running google search", th);
				results = new GoogleSearchResults();
			}
			LOGGER.info("End running google search");
			return results;
		};
		return ToolCallbackDeclarationUtil.declare(thisFunction, SEARCH_WEB_WITH_GOOGLE, RUNNING_A_GOOGLE_SEARCH,
				GoogleSearchRequest.class, GoogleSearchResults.class);
	}

}