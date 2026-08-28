/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.serpapisearch.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.serpapisearch.handler.model.SerpapiApiResponse;
import ai.gebo.serpapisearch.handler.model.SerpapiApiResponse.SerpapiOrganicResult;
import ai.gebo.serpapisearch.handler.model.SerpapiSearchRequest;
import ai.gebo.serpapisearch.handler.model.SerpapiSearchResults;

/**
 * No-network unit test for the SerpApi client: the query, the api_key and the
 * native options land in the request URL, and organic_results are mapped.
 */
class SerpapiSearchApiTest {

	private static final class CapturingRest extends RestTemplateWrapperService {
		private String capturedUrl;
		private final SerpapiApiResponse toReturn;

		private CapturingRest(SerpapiApiResponse toReturn) {
			this.toReturn = toReturn;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
				Class<T> responseType) {
			this.capturedUrl = url;
			return (ResponseEntity<T>) ResponseEntity.ok(toReturn);
		}
	}

	private static SerpapiApiResponse oneResult() {
		SerpapiApiResponse resp = new SerpapiApiResponse();
		SerpapiOrganicResult r = new SerpapiOrganicResult();
		r.setTitle("Example title");
		r.setLink("https://example.org/page");
		r.setSnippet("Example snippet");
		resp.setOrganic_results(List.of(r));
		return resp;
	}

	@Test
	void searchBuildsUrlWithKeyAndMapsOrganicResults() throws Exception {
		CapturingRest rest = new CapturingRest(oneResult());
		SerpapiSearchApi api = new SerpapiSearchApi(rest);

		SerpapiSearchRequest request = new SerpapiSearchRequest();
		request.setQuery("climate change");
		request.setTopN(4);

		SerpapiSearchResults out = api.search("SECRET_KEY", request);

		assertTrue(rest.capturedUrl.startsWith("https://serpapi.com/search.json"), "unexpected URL: " + rest.capturedUrl);
		assertTrue(rest.capturedUrl.contains("engine=google"), "default engine missing: " + rest.capturedUrl);
		assertTrue(rest.capturedUrl.contains("api_key=SECRET_KEY"), "api_key missing: " + rest.capturedUrl);
		assertTrue(rest.capturedUrl.contains("q=climate%20change") || rest.capturedUrl.contains("q=climate+change"),
				"query not encoded: " + rest.capturedUrl);

		assertEquals(1, out.getItems().size());
		assertEquals("https://example.org/page", out.getItems().get(0).getUrl());
		assertEquals("Example title", out.getItems().get(0).getTitle());
		assertEquals("Example snippet", out.getItems().get(0).getContent());
	}

	@Test
	void nativeOptionsAreAddedToTheUrl() throws Exception {
		CapturingRest rest = new CapturingRest(oneResult());
		SerpapiSearchApi api = new SerpapiSearchApi(rest);

		api.callApi("SECRET_KEY", "quantum computing", 5, "bing", "us", "en", "qdr:m");

		assertTrue(rest.capturedUrl.contains("engine=bing"), "engine missing: " + rest.capturedUrl);
		assertTrue(rest.capturedUrl.contains("gl=us"), "gl missing: " + rest.capturedUrl);
		assertTrue(rest.capturedUrl.contains("hl=en"), "hl missing: " + rest.capturedUrl);
		assertTrue(rest.capturedUrl.contains("tbs=qdr%3Am") || rest.capturedUrl.contains("tbs=qdr:m"),
				"tbs missing: " + rest.capturedUrl);
	}
}
