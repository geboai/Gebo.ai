/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googlesearch.handler.impl;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ai.gebo.architecture.ai.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;
import ai.gebo.googlesearch.handler.model.SimpleWebSearchRequest;
import ai.gebo.googlesearch.handler.model.SimpleWebSearchResponse;
import ai.gebo.googlesearch.handler.model.SimpleWebSearchResponse.WebReferenceItem;

/**
 * AI generated comments
 * 
 * A service class that implements a simplified Bing search API integration.
 * This class allows performing web searches using Bing's search engine and
 * parses the results into a structured format.
 */
@Service
public class SimpleBingSearchApi {
	/** RestTemplate to handle HTTP requests */
	static RestTemplate template = new RestTemplate();
	/** Description constant for the Bing search operation */
	static final String RUNNING_A_SIMPLE_BING_SEARCH = "Running a simple bing web search";
	/** Function name constant for the Bing search operation */
	static final String SIMPLE_WEB_SEARCH_WITH_BING = "simpleWebSearchWithBing";

	/**
	 * Executes a Bing search with the provided request parameters.
	 * This method sends a search query to Bing, parses the HTML response,
	 * and extracts relevant links and their descriptions.
	 *
	 * @param request The search request containing the query and additional parameters
	 * @param toolContext The context for AI tool callbacks
	 * @return A response object containing search results or error information
	 */
	public SimpleWebSearchResponse runSimpleBingSearch(SimpleWebSearchRequest request, ToolContext toolContext) {
		KBContext context = LLMtInteractionContextThreadLocal.Context.get();
		SimpleWebSearchResponse response = new SimpleWebSearchResponse();
		try {
			LLMtInteractionContextThreadLocal.CalledFunction calledFunction = new LLMtInteractionContextThreadLocal.CalledFunction();
			calledFunction.setFunctionName(SIMPLE_WEB_SEARCH_WITH_BING);
			calledFunction.setFunctionDescription(RUNNING_A_SIMPLE_BING_SEARCH);
			if (request.getSearchTextQParam() != null) {
				calledFunction.setParamsDescription(List.of(request.getSearchTextQParam()));
			}
			if (context != null) {

				context.getCalledFunctions().add(calledFunction);
			}
			if (toolContext != null) {
				ToolCallbackDeclarationUtil.addCallToContext(toolContext, calledFunction);
			}
			// Construct the search URL with the query parameter
			String uriRequested = BING_QUERY + URLEncoder.encode(request.getSearchTextQParam());
			// Add any additional query parameters from the request
			for (Map.Entry<String, List<String>> entry : request.getAdditionalQueryRequestParameters().entrySet()) {
				String key = entry.getKey();
				List<String> val = entry.getValue();
				if (val != null && !val.isEmpty()) {
					for (String stringParam : val) {
						uriRequested += "&" + key + "=" + URLEncoder.encode(stringParam);
					}
				}
			}
			URI uri = new URI(uriRequested);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Accept", "text/html");
			HttpEntity<String> rq = new HttpEntity<String>(headers);
			ResponseEntity<String> responseText = template.exchange(uri, HttpMethod.GET, rq, String.class);
			if (responseText.hasBody()) {
				String body = responseText.getBody();
				if (body != null) {
					// Parse the HTML response
					Document document = Jsoup.parse(body, "https://www.bing.com/");
					Elements anchors = document.getElementsByTag("a");

					// Extract all valid links from the search results
					for (int i = 0; i < anchors.size(); i++) {
						Element anchor = anchors.get(i);

						String url = anchor.absUrl("href");
						try {
							URL _url = new URL(url);
							// Filter out Bing internal links and other unwanted URLs
							if (!url.startsWith("https://www.bing.com/") && !url.contains("javascript")
									&& (!url.contains("go.microsoft.com/fwlink"))) {
								String text = anchor.text();
								WebReferenceItem item = new WebReferenceItem();
								item.description = text;
								item.hyperLink = url;
								response.getWebReferences().add(item);
							}
						} catch (Throwable th) {
							// Silently ignore malformed URLs
						}

					}

				}

				response.setStatusOK(true);
			} else {
				response.setStatusOK(false);
				response.setStatusText("No results");
			}
		} catch (Throwable th) {
			response.setStatusOK(false);
			response.setStatusText("Error=>" + th.getMessage());
		}
		return response;

	}

	/**
	 * Creates a ToolCallback for the Bing search functionality.
	 * This method wraps the search functionality in a callback that can be used
	 * by AI frameworks.
	 * 
	 * @return A ToolCallback instance for the Bing search
	 */
	ToolCallback createSimplebingSearch() {
		BiFunction<SimpleWebSearchRequest, ToolContext, SimpleWebSearchResponse> thisFunction = (
				SimpleWebSearchRequest r, ToolContext context) -> {

			return runSimpleBingSearch(r, context);
		};
		return ToolCallbackDeclarationUtil.declare(thisFunction, SIMPLE_WEB_SEARCH_WITH_BING,
				RUNNING_A_SIMPLE_BING_SEARCH, SimpleWebSearchRequest.class, SimpleWebSearchResponse.class);
	}

	/** Base URL for Bing search queries */
	private static final String BING_QUERY = "https://www.bing.com/search?q=";

	/**
	 * Main method for testing the Bing search functionality.
	 * Performs a sample search for "donald trump" and prints the results.
	 * 
	 * @param args Command line arguments (not used)
	 * @throws MalformedURLException If the URL is malformed
	 * @throws URISyntaxException If the URI syntax is invalid
	 */
	public static void main(String[] args) throws MalformedURLException, URISyntaxException {
		SimpleWebSearchRequest request = new SimpleWebSearchRequest();
		request.setSearchTextQParam("donald trump");
		SimpleWebSearchResponse response = new SimpleBingSearchApi().runSimpleBingSearch(request,
				new ToolContext(new HashMap<>()));
		for (WebReferenceItem item : response.getWebReferences()) {
			System.out.println("Link:" + item.hyperLink + " description:" + item.description);
		}
	}
}