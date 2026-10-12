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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.architecture.search.config.WebResultSizeProbeConfig;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultReference;

/**
 * Pins the best-effort size probe of the web results: a declared, uncompressed,
 * positive Content-Length of a 2xx HEAD answer is the size; anything else leaves it
 * unknown, one request per distinct URL, never a failure nor a long wait.
 */
class WebResultSizeProbeTest {

	private ServerSocket server;
	private Thread serving;
	private final Map<String, AtomicInteger> requestsByPath = new ConcurrentHashMap<>();
	private final List<String> requestLines = new CopyOnWriteArrayList<>();

	/** The canned answers, by path. */
	private static String answerFor(String path) {
		switch (path) {
		case "/doc.pdf":
			return "HTTP/1.1 200 OK\r\nContent-Type: application/pdf\r\nContent-Length: 12345\r\n";
		case "/gzip.html":
			return "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Encoding: gzip\r\nContent-Length: 999\r\n";
		case "/zero.html":
			return "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: 0\r\n";
		case "/chunked.html":
			return "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nTransfer-Encoding: chunked\r\n";
		case "/nohead.html":
			return "HTTP/1.1 405 Method Not Allowed\r\nContent-Length: 0\r\n";
		case "/forbidden.html":
			return "HTTP/1.1 403 Forbidden\r\nContent-Length: 20\r\n";
		default:
			return "HTTP/1.1 404 Not Found\r\nContent-Length: 0\r\n";
		}
	}

	@BeforeEach
	void startServer() throws Exception {
		server = new ServerSocket(0);
		serving = new Thread(() -> {
			while (!server.isClosed()) {
				try {
					final Socket socket = server.accept();
					new Thread(() -> answer(socket)).start();
				} catch (Exception e) {
					return;
				}
			}
		});
		serving.setDaemon(true);
		serving.start();
	}

	private void answer(Socket socket) {
		try (socket) {
			final BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), StandardCharsets.ISO_8859_1));
			final String requestLine = in.readLine();
			String header;
			final StringBuilder headers = new StringBuilder();
			while ((header = in.readLine()) != null && !header.isEmpty()) {
				headers.append(header).append('\n');
			}
			requestLines.add(requestLine + "\n" + headers);
			final String path = requestLine.split(" ")[1];
			requestsByPath.computeIfAbsent(path, k -> new AtomicInteger()).incrementAndGet();
			if (path.equals("/slow.pdf")) {
				Thread.sleep(3000);
			}
			final OutputStream out = socket.getOutputStream();
			out.write((answerFor(path) + "Connection: close\r\n\r\n").getBytes(StandardCharsets.ISO_8859_1));
			out.flush();
		} catch (Exception e) {
			// the client went away
		}
	}

	@AfterEach
	void stopServer() throws Exception {
		server.close();
	}

	private String url(String path) {
		return "http://127.0.0.1:" + server.getLocalPort() + path;
	}

	private static SearchResult result(String uri) {
		SearchResult result = new SearchResult();
		result.setResultReference(new SearchResultReference());
		result.getResultReference().setUri(uri);
		return result;
	}

	private static WebResultSizeProbe probe(int timeoutMs) {
		WebResultSizeProbeConfig config = new WebResultSizeProbeConfig();
		config.setTimeoutMs(timeoutMs);
		return new WebResultSizeProbe(config);
	}

	@Test
	void onlyADeclaredUncompressedPositiveLengthOfA2xxIsTheSize() {
		SearchResult pdf = result(url("/doc.pdf"));
		SearchResult gzip = result(url("/gzip.html"));
		SearchResult zero = result(url("/zero.html"));
		SearchResult chunked = result(url("/chunked.html"));
		SearchResult noHead = result(url("/nohead.html"));
		SearchResult forbidden = result(url("/forbidden.html"));

		probe(1500).fillSizes(List.of(pdf, gzip, zero, chunked, noHead, forbidden));

		assertEquals(12345L, pdf.getResultReference().getSize());
		assertNull(gzip.getResultReference().getSize());
		assertNull(zero.getResultReference().getSize());
		assertNull(chunked.getResultReference().getSize());
		assertNull(noHead.getResultReference().getSize());
		assertNull(forbidden.getResultReference().getSize());
		String request = requestLines.stream().filter(x -> x.startsWith("HEAD /doc.pdf")).findFirst().orElseThrow();
		assertTrue(request.toLowerCase().contains("accept-encoding: identity"), request);
		assertTrue(request.toLowerCase().contains("referer: google.com"), request);
	}

	@Test
	void aUrlIsProbedOnceAndAKnownSizeIsKept() {
		SearchResult first = result(url("/doc.pdf"));
		SearchResult duplicate = result(url("/doc.pdf"));
		SearchResult known = result(url("/gzip.html"));
		known.getResultReference().setSize(77L);

		probe(1500).fillSizes(new ArrayList<>(List.of(first, duplicate, known)));

		assertEquals(12345L, first.getResultReference().getSize());
		assertEquals(12345L, duplicate.getResultReference().getSize());
		assertEquals(77L, known.getResultReference().getSize());
		assertEquals(1, requestsByPath.get("/doc.pdf").get());
		assertNull(requestsByPath.get("/gzip.html"));
	}

	@Test
	void aSlowServerIsNotWaitedForAndNothingFails() {
		SearchResult slow = result(url("/slow.pdf"));
		SearchResult pdf = result(url("/doc.pdf"));
		SearchResult notHttp = result("ftp://example.org/file.pdf");
		SearchResult invalid = result("http://bad host/with spaces");
		SearchResult noReference = new SearchResult();
		List<SearchResult> results = new ArrayList<>(List.of(slow, pdf, notHttp, invalid, noReference));
		results.add(null);

		long start = System.currentTimeMillis();
		probe(400).fillSizes(results);
		long elapsed = System.currentTimeMillis() - start;

		assertTrue(elapsed < 2000, "waited " + elapsed + " ms");
		assertNull(slow.getResultReference().getSize());
		assertEquals(12345L, pdf.getResultReference().getSize());
		assertNull(notHttp.getResultReference().getSize());
		assertNull(invalid.getResultReference().getSize());
		assertEquals(6, results.size());
	}

	@Test
	void aDisabledProbeSendsNothing() {
		WebResultSizeProbeConfig config = new WebResultSizeProbeConfig();
		config.setEnabled(false);
		SearchResult pdf = result(url("/doc.pdf"));

		new WebResultSizeProbe(config).fillSizes(List.of(pdf));

		assertNull(pdf.getResultReference().getSize());
		assertTrue(requestsByPath.isEmpty());
	}

	@Test
	void sizeOfTheAnswers() {
		assertEquals(10L, WebResultSizeProbe.sizeOf(200, null, "10"));
		assertEquals(10L, WebResultSizeProbe.sizeOf(206, "identity", " 10 "));
		assertNull(WebResultSizeProbe.sizeOf(200, "br", "10"));
		assertNull(WebResultSizeProbe.sizeOf(301, null, "10"));
		assertNull(WebResultSizeProbe.sizeOf(200, null, "abc"));
		assertNull(WebResultSizeProbe.sizeOf(200, null, "-1"));
		assertNull(WebResultSizeProbe.sizeOf(200, null, null));
	}
}
