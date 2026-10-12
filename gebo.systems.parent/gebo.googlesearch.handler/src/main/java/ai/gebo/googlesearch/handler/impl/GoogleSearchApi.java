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
import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import ai.gebo.architecture.search.config.SearchCallsConfig;
import ai.gebo.architecture.search.model.SearchCallParameters;
import ai.gebo.googlesearch.handler.model.GoogleSearchRequest;
import ai.gebo.googlesearch.handler.model.GoogleSearchResults;

@Service
class GoogleSearchApi {
	/** Logger for this class */
	static Logger LOGGER = LoggerFactory.getLogger(GoogleSearchApi.class);
	/** Base URL for Google Custom Search API */
	private static final String googleSearch = "https://www.googleapis.com/customsearch/v1?key=";
	/** Most results the Custom Search API returns in one call. */
	static final int MAX_RESULTS_PER_CALL = 10;

	// the Google API is called with an HTTP client of its own, whose connect and read
	// timeouts are the search calls' ones: it does not hold a search forever
	private final RestTemplate restTemplate;

	@Autowired
	GoogleSearchApi(SearchCallsConfig searchCalls) {
		this(searchCalls.httpConnectTimeout(), searchCalls.httpReadTimeout());
	}

	private GoogleSearchApi(Duration connectTimeout, Duration readTimeout) {
		final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(connectTimeout);
		requestFactory.setReadTimeout(readTimeout);
		this.restTemplate = new RestTemplate(requestFactory);
	}

	/** This API called with an HTTP client of its own whose timeouts are the call parameters'. */
	GoogleSearchApi using(SearchCallParameters parameters) {
		return new GoogleSearchApi(parameters.connectTimeout(), parameters.readTimeout());
	}

	/**
	 * search with google api documentation:
	 * https://developers.google.com/custom-search/v1/reference/rest/v1/cse/list?hl=it
	 * 
	 * @param apiKey               Google API key for authorization
	 * @param customSearchEngineId Custom Search Engine ID to specify which search
	 *                             engine to use
	 * @param request              The search request containing query and other
	 *                             parameters
	 * @return Results from the Google search
	 * @throws MalformedURLException        If the constructed URL is invalid
	 * @throws UnsupportedEncodingException If encoding parameters fails
	 * @throws RestClientException          If the REST call fails
	 * @throws URISyntaxException           If the URL can't be converted to a URI
	 */
	public GoogleSearchResults search(String apiKey, String customSearchEngineId, GoogleSearchRequest request)
			throws MalformedURLException, UnsupportedEncodingException, RestClientException, URISyntaxException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Calling google search with:" + request.getQuery());
		}
		String charset = "UTF-8";
		String language = request.getLanguage();
		String search = request.getQuery();
		StringBuilder address = new StringBuilder(googleSearch).append(URLEncoder.encode(apiKey, charset))
				.append("&cx=").append(URLEncoder.encode(customSearchEngineId, charset)).append("&q=")
				.append(URLEncoder.encode(search, charset));
		// lr restricts the results to a language, as "lang_<code>": only when one is asked
		if (language != null && !language.isBlank()) {
			String languageRestrict = language.startsWith("lang_") ? language : "lang_" + language;
			address.append("&lr=").append(URLEncoder.encode(languageRestrict, charset));
		}
		// num is the number of results, 1 to 10 for the Custom Search API
		if (request.getTopN() != null && request.getTopN() > 0) {
			address.append("&num=").append(Math.min(MAX_RESULTS_PER_CALL, request.getTopN()));
		}
		URL url = new URL(address.toString());
		if (LOGGER.isDebugEnabled()) {
			// Never the API key: the logs are read by far more people than the key should be.
			LOGGER.debug("Google search url:" + url.toString().replace(URLEncoder.encode(apiKey, charset), "***"));
		}
		URI uri = url.toURI();
		ResponseEntity<GoogleSearchResults> returned = restTemplate.getForEntity(uri, GoogleSearchResults.class);
		if (returned.hasBody())
			return returned.getBody();
		return new GoogleSearchResults();
	}

}