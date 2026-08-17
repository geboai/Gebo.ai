/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.searxngsearch.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.searxngsearch.handler.model.SearxngApiResponse;
import ai.gebo.searxngsearch.handler.model.SearxngApiResponse.SearxngApiResult;
import ai.gebo.searxngsearch.handler.model.SearxngSearchRequest;
import ai.gebo.searxngsearch.handler.model.SearxngSearchResults;

/**
 * SearXNG is the one shipped web-search provider that can run WITHOUT an API key
 * (a self-hosted / public instance). This unit test pins that keyless path: no
 * network, a captured request, asserting the URL is built correctly, that NO
 * Authorization header is sent when no key is configured, and that the JSON
 * response is mapped into results.
 */
class SearxngSearchApiTest {

	/** Captures the outgoing call and returns a canned response - no network. */
	private static final class CapturingRest extends RestTemplateWrapperService {
		private String capturedUrl;
		private HttpEntity<?> capturedEntity;
		private final SearxngApiResponse toReturn;

		private CapturingRest(SearxngApiResponse toReturn) {
			this.toReturn = toReturn;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
				Class<T> responseType) {
			this.capturedUrl = url;
			this.capturedEntity = requestEntity;
			return (ResponseEntity<T>) ResponseEntity.ok(toReturn);
		}
	}

	private static SearxngApiResponse oneResult() {
		SearxngApiResponse resp = new SearxngApiResponse();
		SearxngApiResult r = new SearxngApiResult();
		r.setTitle("Example title");
		r.setUrl("https://example.org/page");
		r.setContent("Example snippet");
		resp.setResults(List.of(r));
		return resp;
	}

	@Test
	void keylessSearchBuildsUrlWithoutAuthHeaderAndMapsResults() throws Exception {
		CapturingRest rest = new CapturingRest(oneResult());
		SearxngSearchApi api = new SearxngSearchApi(rest);

		SearxngSearchRequest request = new SearxngSearchRequest();
		request.setQuery("climate change");
		request.setTopN(3);

		// note the trailing slash on the base URL - must be normalized away
		SearxngSearchResults out = api.search("https://searx.example.org/", null, request);

		// URL: <base>/search with format=json and the encoded query
		assertTrue(rest.capturedUrl.startsWith("https://searx.example.org/search"),
				"unexpected URL: " + rest.capturedUrl);
		assertTrue(rest.capturedUrl.contains("format=json"), "missing format=json: " + rest.capturedUrl);
		assertTrue(rest.capturedUrl.contains("q=climate%20change") || rest.capturedUrl.contains("q=climate+change"),
				"query not encoded: " + rest.capturedUrl);

		// keyless: no Authorization header must be sent
		HttpHeaders headers = rest.capturedEntity.getHeaders();
		assertFalse(headers.containsHeader(HttpHeaders.AUTHORIZATION),
				"no Authorization header expected when no API key is configured");

		// response mapped into results
		assertEquals(1, out.getItems().size());
		assertEquals("https://example.org/page", out.getItems().get(0).getUrl());
		assertEquals("Example title", out.getItems().get(0).getTitle());
		assertEquals("Example snippet", out.getItems().get(0).getContent());
	}

	@Test
	void nativeOptionsAreAddedToTheUrl() throws Exception {
		CapturingRest rest = new CapturingRest(oneResult());
		SearxngSearchApi api = new SearxngSearchApi(rest);

		api.callApi("https://searx.example.org", null, "quantum computing", 5, "science,news", "month", "en");

		assertTrue(rest.capturedUrl.contains("categories=science%2Cnews")
				|| rest.capturedUrl.contains("categories=science,news"), "categories missing: " + rest.capturedUrl);
		assertTrue(rest.capturedUrl.contains("time_range=month"), "time_range missing: " + rest.capturedUrl);
		assertTrue(rest.capturedUrl.contains("language=en"), "language missing: " + rest.capturedUrl);
	}
}
