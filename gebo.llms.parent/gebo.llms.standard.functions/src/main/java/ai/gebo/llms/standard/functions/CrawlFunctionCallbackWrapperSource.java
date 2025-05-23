/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.standard.functions;

import java.io.InputStream;
import java.util.List;
import java.util.function.BiFunction;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.IGToolCallbackSource;
import ai.gebo.architecture.ai.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;

/**
 * AI generated comments
 * Service class that provides web crawling functionality as a tool callback for LLM interactions.
 * This class allows the LLM to fetch and read content from specified URLs.
 */
@Service
public class CrawlFunctionCallbackWrapperSource implements IGToolCallbackSource {
	static Logger LOGGER = LoggerFactory.getLogger(CrawlFunctionCallbackWrapperSource.class);
	/** Maximum allowed length for content to be fetched (32KB) */
	static long LENGTH_LIMIT = 1024 * 32;
	/** Length at which content will be cut off (4KB) */
	static long LENGTH_CUT = 1024 * 4;

	/**
	 * Request object for URL crawling operations.
	 * Contains the URL to be crawled.
	 */
	public static class UrlCrawlRequest {
		private String url = null;

		/**
		 * Gets the URL to crawl.
		 * @return the URL string
		 */
		public String getUrl() {
			return url;
		}

		/**
		 * Sets the URL to crawl.
		 * @param url the URL string to set
		 */
		public void setUrl(String url) {
			this.url = url;
		}
	}

	/**
	 * Response object for URL crawling operations.
	 * Contains the content fetched from the URL.
	 */
	public static class UrlCrawlResponse {
		public String content = null;

		/**
		 * Gets the content fetched from the URL.
		 * @return the content string
		 */
		public String getContent() {
			return content;
		}

		/**
		 * Sets the content fetched from the URL.
		 * @param content the content string to set
		 */
		public void setContent(String content) {
			this.content = content;
		}
	}

	/**
	 * Default constructor.
	 */
	public CrawlFunctionCallbackWrapperSource() {

	}

	/**
	 * Creates a ToolCallback for URL crawling functionality.
	 * @return a ToolCallback that handles URL crawling
	 */
	ToolCallback create() {
		BiFunction<UrlCrawlRequest,ToolContext, UrlCrawlResponse> thisFunction = (UrlCrawlRequest request,ToolContext toolContext) -> {
			String url = request != null ? request.getUrl() : null;
			LOGGER.info("Begin llm reading content:" + url);
			try {
				String content = "No content can be returned";
				HttpGet get = new HttpGet(url);
				CloseableHttpClient client = HttpClientBuilder.create().build();
				CloseableHttpResponse response = client.execute(get);
				if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300) {
					if (response.getEntity() != null) {
						if (response.getEntity().getContentLength() <= LENGTH_LIMIT) {
							String encoding = "UTF-8";
							InputStream is = response.getEntity().getContent();
							Document document = Jsoup.parse(is, encoding, url);
							content = document.body().text();

						} else {
							content = "Content too big";
						}
					}
				}
				if (content.length() > LENGTH_CUT) {
					LOGGER.warn("Content cut: " + content);
					content = content.substring(0, (int) LENGTH_CUT)
							+ " <!-- content has been cut because is too big -->";

				}
				LOGGER.info("End llm reading content:" + url);
				UrlCrawlResponse crespone = new UrlCrawlResponse();
				crespone.setContent(content);
				KBContext context = LLMtInteractionContextThreadLocal.Context.get();
				LLMtInteractionContextThreadLocal.CalledFunction calledFunction = new LLMtInteractionContextThreadLocal.CalledFunction();
				calledFunction.setFunctionName("readUrl");
				calledFunction.setFunctionDescription("Read web content from its url");
				if (request.getUrl() != null) {
					calledFunction.setParamsDescription(List.of(request.getUrl()));
				}
				if (context != null) {
					
					context.getCalledFunctions().add(calledFunction);
				}
				ToolCallbackDeclarationUtil.addCallToContext(toolContext, calledFunction);
				return crespone;
			} catch (Throwable th) {
				LOGGER.info("Error reading content:" + url, th);
				return null;
			}
		};

		return ToolCallbackDeclarationUtil.declare(thisFunction, "readUrl", "Read web content from its url",
				UrlCrawlRequest.class, UrlCrawlResponse.class);
	}

	/**
	 * Gets the unique identifier for this tool callback source.
	 * @return the class name as the ID
	 */
	@Override
	public String getId() {

		return this.getClass().getName();
	}

	/**
	 * Specifies the category of this tool.
	 * @return INTERNET_BROWSING tool category
	 */
	@Override
	public ToolsCategory getToolCategory() {
		return ToolsCategory.INTERNET_BROWSING;
	}

	/**
	 * Gets the list of tool callbacks provided by this source.
	 * @return a list containing the URL crawling tool callback
	 */
	@Override
	public List<ToolCallback> getToolCallbacks() {
		return List.of(create());
	}

	/**
	 * Gets the full list of tool references.
	 * @return an empty list as this implementation doesn't provide any tool references
	 */
	@Override
	public List<ToolReference> getFullToolReferences() {

		return List.of();
	}
}