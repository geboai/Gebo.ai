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
import org.springframework.ai.util.json.JsonParser;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.ai.service.ToolsTokenBudget;
import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;

/**
 * AI generated comments Service class that provides web crawling functionality
 * as a tool callback for LLM interactions. This class allows the LLM to fetch
 * and read content from specified URLs.
 */
@Service
public class CrawlFunctionCallbackWrapperSource implements IGToolCallbackSource {
	static Logger LOGGER = LoggerFactory.getLogger(CrawlFunctionCallbackWrapperSource.class);
	/** Maximum allowed length for content to be fetched (32KB) */
	static long LENGTH_LIMIT = 1024 * 32;
	/** Length at which content will be cut off (4KB) */
	static long LENGTH_CUT = 1024 * 4;

	/**
	 * Request object for URL crawling operations. Contains the URL to be crawled.
	 */
	public static class UrlCrawlRequest {
		private String url = null;

		/**
		 * Gets the URL to crawl.
		 * 
		 * @return the URL string
		 */
		public String getUrl() {
			return url;
		}

		/**
		 * Sets the URL to crawl.
		 * 
		 * @param url the URL string to set
		 */
		public void setUrl(String url) {
			this.url = url;
		}
	}

	/**
	 * Response object for URL crawling operations. Contains the content fetched
	 * from the URL.
	 */
	public static class UrlCrawlResponse {
		public String content = null;

		/**
		 * Gets the content fetched from the URL.
		 * 
		 * @return the content string
		 */
		public String getContent() {
			return content;
		}

		/**
		 * Sets the content fetched from the URL.
		 * 
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
	 * 
	 * @return a ToolCallback that handles URL crawling
	 */
	static UrlCrawlResponse EMPTYRESPONSE=new UrlCrawlResponse();
	static {
		EMPTYRESPONSE.content="Invalid url or request gonna wrong";
	}
	ToolCallback create() {
		BiFunction<UrlCrawlRequest, ToolContext, UrlCrawlResponse> thisFunction = (UrlCrawlRequest request,
				ToolContext toolContext) -> {
			String url = request != null ? request.getUrl() : null;
			if (url == null || url.trim().length() == 0)
				return EMPTYRESPONSE;
			if (!url.toLowerCase().startsWith("http")) {
				return EMPTYRESPONSE;
			}
			// a page read is not worth it when its model call has no room left for it
			if (ToolsTokenBudget.noUsefulRoom(toolContext)) {
				LOGGER.debug("readUrl not run: too little room left in its model call's context");
				UrlCrawlResponse noRoom = new UrlCrawlResponse();
				noRoom.setContent("No room is left in the context for more contents: answer with the contents already found.");
				return noRoom;
			}
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
				final UrlCrawlResponse crespone = new UrlCrawlResponse();
				final ToolsTokenBudget budget = ToolsTokenBudget.from(toolContext);
				if (budget == null) {
					// no room set by the model call: the page is cut to its own limit
					if (content.length() > LENGTH_CUT) {
						LOGGER.warn("Content of " + url + " cut from " + content.length() + " to " + LENGTH_CUT
								+ " characters");
						content = content.substring(0, (int) LENGTH_CUT)
								+ " <!-- content has been cut because is too big -->";
					}
					crespone.setContent(content);
				} else {
					fitInRoom(crespone, content, budget.left());
				}
				if (LOGGER.isTraceEnabled()) {
					LOGGER.trace("<READ_URL_CONTENT url=" + url + ">");
					LOGGER.trace(crespone.getContent());
					LOGGER.trace("</READ_URL_CONTENT>");
				}
				LOGGER.info("End llm reading content:" + url);
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
	 * Puts the page content in the response, cut so that the response as the model
	 * reads it (its JSON) fits the room its model call has left: the content is fitted,
	 * then cut again by what the JSON framing and escaping add.
	 */
	static void fitInRoom(UrlCrawlResponse response, String content, int room) {
		int contentRoom = room;
		response.setContent(ToolsTokenBudget.fitText(content, contentRoom));
		for (int attempt = 0; attempt < 3; attempt++) {
			final int overshoot = ITokensCountable.stringsTokensSize(JsonParser.toJson(response)) - room;
			if (overshoot <= 0) {
				break;
			}
			contentRoom -= overshoot;
			response.setContent(ToolsTokenBudget.fitText(content, Math.max(0, contentRoom)));
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("fitInRoom(...) " + content.length() + " character(s) of content in a room of " + room
					+ " (tok), " + response.getContent().length() + " kept");
		}
	}

	/**
	 * Gets the unique identifier for this tool callback source.
	 * 
	 * @return the class name as the ID
	 */
	@Override
	public String getId() {

		return this.getClass().getName();
	}

	/**
	 * Specifies the category of this tool.
	 * 
	 * @return INTERNET_BROWSING tool category
	 */
	@Override
	public ToolsCategory getToolCategory() {
		return ToolsCategory.INTERNET_BROWSING;
	}

	/**
	 * Gets the list of tool callbacks provided by this source.
	 * 
	 * @return a list containing the URL crawling tool callback
	 */
	@Override
	public List<ToolCallback> getToolCallbacks() {
		return List.of(create());
	}

	/**
	 * Gets the full list of tool references.
	 * 
	 * @return an empty list as this implementation doesn't provide any tool
	 *         references
	 */
	@Override
	public List<ToolReference> getFullToolReferences() {

		return List.of();
	}
}