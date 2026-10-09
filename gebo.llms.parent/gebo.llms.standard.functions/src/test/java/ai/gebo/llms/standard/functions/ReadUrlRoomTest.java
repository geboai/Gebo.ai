/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.standard.functions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.util.json.JsonParser;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.ToolsTokenBudget;
import ai.gebo.llms.standard.functions.CrawlFunctionCallbackWrapperSource.UrlCrawlRequest;
import ai.gebo.llms.standard.functions.CrawlFunctionCallbackWrapperSource.UrlCrawlResponse;

/**
 * Pins readUrl in the room its model call leaves: the page, as the model reads it
 * (its JSON, quotes and line ends escaped), fits the room; no page is read when the
 * room is too small to be worth it.
 */
class ReadUrlRoomTest {

	@Test
	void thePageAsTheModelReadsItFitsTheRoom() {
		StringBuilder page = new StringBuilder();
		for (int i = 0; i < 4000; i++) {
			page.append("line \"").append(i).append("\" of the page\r\n");
		}
		UrlCrawlResponse response = new UrlCrawlResponse();

		CrawlFunctionCallbackWrapperSource.fitInRoom(response, page.toString(), 700);

		int asRead = ITokensCountable.stringsTokensSize(JsonParser.toJson(response));
		assertTrue(asRead <= 700, "read as " + asRead + " tokens");
		assertTrue(asRead > 500, "the room is used: " + asRead);
		assertTrue(response.getContent().contains("truncated: about"), "the cut is marked");
	}

	@Test
	void aPageThatFitsIsReturnedWhole() {
		UrlCrawlResponse response = new UrlCrawlResponse();

		CrawlFunctionCallbackWrapperSource.fitInRoom(response, "A short page.", 700);

		assertEquals("A short page.", response.getContent());
	}

	@Test
	void noPageIsReadWithoutUsefulRoom() {
		CrawlFunctionCallbackWrapperSource source = new CrawlFunctionCallbackWrapperSource();
		UrlCrawlRequest request = new UrlCrawlRequest();
		// never reached: the room is checked before the page is fetched
		request.setUrl("https://example.invalid/page");
		ToolContext full = new ToolContext(
				Map.of(ToolsTokenBudget.TOOLS_CONTEXT_KEY, new ToolsTokenBudget(ToolsTokenBudget.MIN_USEFUL_TOKENS - 1)));

		String answer = source.create().call(JsonParser.toJson(request), full);

		assertTrue(answer.contains("No room is left in the context"), answer);
	}
}
