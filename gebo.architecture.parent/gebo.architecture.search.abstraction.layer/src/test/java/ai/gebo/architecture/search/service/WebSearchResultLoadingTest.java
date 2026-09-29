/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.search.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.io.Closeable;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.entity.BasicHttpEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;

import com.sun.net.httpserver.HttpServer;

import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.model.base.TypedInputStream;

/**
 * Pins how a web search result's page is downloaded: some sites answer without a
 * Content-Type header, which used to fail the download with a
 * NullPointerException and drop the result from the evidence.
 */
class WebSearchResultLoadingTest {
	private HttpServer server;
	private String base;

	@BeforeEach
	void start() throws Exception {
		server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
		// The JDK server adds no Content-Type unless the handler sets one.
		server.createContext("/no-content-type", exchange -> {
			byte[] body = "<html><body>no declared type</body></html>".getBytes(StandardCharsets.UTF_8);
			exchange.sendResponseHeaders(200, body.length);
			try (OutputStream out = exchange.getResponseBody()) {
				out.write(body);
			}
		});
		server.createContext("/document.pdf", exchange -> {
			byte[] body = "%PDF-1.4".getBytes(StandardCharsets.UTF_8);
			exchange.sendResponseHeaders(200, body.length);
			try (OutputStream out = exchange.getResponseBody()) {
				out.write(body);
			}
		});
		server.createContext("/typed", exchange -> {
			byte[] body = "{}".getBytes(StandardCharsets.UTF_8);
			exchange.getResponseHeaders().add("content-TYPE", "Application/JSON; charset=ISO-8859-1");
			exchange.sendResponseHeaders(200, body.length);
			try (OutputStream out = exchange.getResponseBody()) {
				out.write(body);
			}
		});
		server.createContext("/no-content", exchange -> {
			exchange.sendResponseHeaders(204, -1);
			exchange.close();
		});
		server.start();
		base = "http://127.0.0.1:" + server.getAddress().getPort();
	}

	@AfterEach
	void stop() {
		server.stop(0);
	}

	@SuppressWarnings("rawtypes")
	private static AbstractWebSearchServiceImpl service() {
		return mock(AbstractWebSearchServiceImpl.class,
				withSettings().useConstructor().defaultAnswer(Answers.CALLS_REAL_METHODS));
	}

	private static SearchResult result(String uri) {
		SearchResult result = new SearchResult();
		SearchResultReference reference = new SearchResultReference();
		reference.setUri(uri);
		result.setResultReference(reference);
		return result;
	}

	private static String read(TypedInputStream typed) throws Exception {
		try (InputStream in = typed.getInputStream()) {
			return new String(in.readAllBytes(), StandardCharsets.UTF_8);
		}
	}

	@Test
	void aPageWithoutContentTypeIsLoadedAsHtml() throws Exception {
		TypedInputStream page = service().loadSearchResult(result(base + "/no-content-type"));

		assertEquals("text/html", page.getContentType());
		assertTrue(read(page).contains("no declared type"));
	}

	@Test
	void withoutContentTypeTheTypeIsGuessedFromTheLink() throws Exception {
		TypedInputStream page = service().loadSearchResult(result(base + "/document.pdf"));

		assertEquals("application/pdf", page.getContentType());
		assertEquals("%PDF-1.4", read(page));
	}

	@Test
	void aDeclaredContentTypeIsUsedWithoutItsParametersWhateverItsCasing() throws Exception {
		TypedInputStream page = service().loadSearchResult(result(base + "/typed"));

		assertEquals("application/json", page.getContentType());
		assertEquals("{}", read(page));
	}

	@Test
	void anAnswerWithoutBodyIsAnEmptyPage() throws Exception {
		TypedInputStream page = service().loadSearchResult(result(base + "/no-content"));

		assertEquals("text/html", page.getContentType());
		assertEquals("", read(page));
	}

	@Test
	void theContentTypeFallsBackWhenNotDeclared() {
		BasicHttpEntity entity = new BasicHttpEntity();
		assertEquals("text/plain", AbstractWebSearchServiceImpl.contentTypeOf(entity, "text/plain"));
		assertEquals("text/html", AbstractWebSearchServiceImpl.contentTypeOf(entity, null));
		entity.setContentType(" ; charset=utf-8");
		assertEquals("text/plain", AbstractWebSearchServiceImpl.contentTypeOf(entity, "text/plain"));
	}

	@Test
	void closingThePageReleasesTheConnection() throws Exception {
		AtomicInteger closed = new AtomicInteger();
		Closeable response = closed::incrementAndGet;
		Closeable client = closed::incrementAndGet;

		new AbstractWebSearchServiceImpl.ConnectionReleasingInputStream(new ByteArrayInputStream(new byte[0]),
				response, client).close();

		assertEquals(2, closed.get(), "both the response and the client are closed");
	}
}
