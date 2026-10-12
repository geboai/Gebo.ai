/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.search.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.search.config.WebResultSizeProbeConfig;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultReference;

/**
 * Fills the size of web search results, best effort: one HEAD request per distinct
 * result URL, in parallel, with a low timeout. A result gets the size its server
 * declares as Content-Length on a 2xx answer, for the content itself (an
 * uncompressed answer is asked for, a compressed length is not a size); anything
 * else (no length, a 0 length, an error status, a HEAD refused, a timeout, a
 * failure) leaves the size unknown. Never throws and never removes a result: the
 * search goes on whatever the probe gives.
 */
public class WebResultSizeProbe {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebResultSizeProbe.class);
	/** The same referer as the download of the results (see AbstractWebSearchServiceImpl). */
	static final String REFERER = "google.com";

	private final boolean enabled;
	private final int timeoutMs;
	private final int parallelism;

	public WebResultSizeProbe(WebResultSizeProbeConfig config) {
		final WebResultSizeProbeConfig used = config != null ? config : new WebResultSizeProbeConfig();
		this.enabled = used.isEnabled();
		this.timeoutMs = used.getTimeoutMs() > 0 ? used.getTimeoutMs() : WebResultSizeProbeConfig.DEFAULT_TIMEOUT_MS;
		this.parallelism = used.getParallelism() > 0 ? used.getParallelism()
				: WebResultSizeProbeConfig.DEFAULT_PARALLELISM;
	}

	/**
	 * Fills the unknown sizes of the results, probing each distinct http(s) URL once.
	 * Waits at most for a request's connect and answer timeouts: the requests still
	 * running then are aborted and their results keep an unknown size.
	 */
	public void fillSizes(List<SearchResult> results) {
		if (!enabled || results == null || results.isEmpty()) {
			return;
		}
		final long start = System.currentTimeMillis();
		// distinct URL -> the results pointing to it, their size unknown
		final Map<String, List<SearchResultReference>> byUrl = new LinkedHashMap<>();
		for (SearchResult result : results) {
			final SearchResultReference reference = result != null ? result.getResultReference() : null;
			final String uri = reference != null ? reference.getUri() : null;
			if (uri == null || reference.getSize() != null || !isHttp(uri)) {
				continue;
			}
			byUrl.computeIfAbsent(uri.trim(), k -> new ArrayList<>()).add(reference);
		}
		if (byUrl.isEmpty()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("fillSizes(...) no http(s) result of " + results.size() + " without a size to probe");
			}
			return;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin fillSizes(...) " + results.size() + " result(s), " + byUrl.size()
					+ " distinct URL(s) to probe, timeout:" + timeoutMs + " ms parallelism:" + parallelism);
		}
		final AtomicInteger filled = new AtomicInteger(0);
		int unfinished = 0;
		final RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeoutMs)
				.setSocketTimeout(timeoutMs).setConnectionRequestTimeout(timeoutMs).build();
		final ExecutorService executor = Executors.newFixedThreadPool(Math.min(parallelism, byUrl.size()),
				runnable -> {
					final Thread thread = new Thread(runnable, "web-result-size-probe");
					thread.setDaemon(true);
					return thread;
				});
		try (CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(requestConfig)
				.setMaxConnTotal(parallelism).setMaxConnPerRoute(parallelism).disableContentCompression()
				.disableCookieManagement().build()) {
			final List<HttpHead> requests = new ArrayList<>();
			final List<Future<?>> probes = new ArrayList<>();
			for (Map.Entry<String, List<SearchResultReference>> entry : byUrl.entrySet()) {
				final HttpHead head;
				try {
					head = new HttpHead(entry.getKey());
				} catch (RuntimeException e) {
					if (LOGGER.isTraceEnabled()) {
						LOGGER.trace("Size probe skipped an invalid URL: " + entry.getKey() + " " + e);
					}
					continue;
				}
				requests.add(head);
				probes.add(executor.submit(() -> {
					final Long size = probe(client, head);
					if (size != null) {
						for (SearchResultReference reference : entry.getValue()) {
							reference.setSize(size);
						}
						filled.incrementAndGet();
					}
				}));
			}
			// a request may take its connect timeout and then its answer timeout
			final long deadline = start + 2L * timeoutMs + 250L;
			for (Future<?> probe : probes) {
				final long left = deadline - System.currentTimeMillis();
				try {
					probe.get(Math.max(1L, left), TimeUnit.MILLISECONDS);
				} catch (Exception e) {
					unfinished++;
				}
			}
			if (unfinished > 0) {
				for (HttpHead request : requests) {
					request.abort();
				}
			}
		} catch (Throwable th) {
			// best effort: the search goes on with the sizes found so far
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("fillSizes(...) stopped by " + th);
			}
		} finally {
			executor.shutdownNow();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End fillSizes(...) " + filled.get() + " of " + byUrl.size() + " URL(s) with a size, "
					+ unfinished + " not answered in time, in " + (System.currentTimeMillis() - start) + " ms");
		}
	}

	/** The size the server declares for the URL, null when it gives none usable. */
	Long probe(CloseableHttpClient client, HttpHead head) {
		head.setHeader("Referer", REFERER);
		// a compressed answer would declare the compressed length, not the content's
		head.setHeader("Accept-Encoding", "identity");
		try (CloseableHttpResponse response = client.execute(head)) {
			final int status = response.getStatusLine().getStatusCode();
			final Header encoding = response.getFirstHeader("Content-Encoding");
			final Header length = response.getFirstHeader("Content-Length");
			final Long size = sizeOf(status, encoding != null ? encoding.getValue() : null,
					length != null ? length.getValue() : null);
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("Size probe " + head.getURI() + " status:" + status + " Content-Length:"
						+ (length != null ? length.getValue() : null) + " Content-Encoding:"
						+ (encoding != null ? encoding.getValue() : null) + " -> size:" + size);
			}
			return size;
		} catch (IOException | RuntimeException e) {
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("Size probe " + head.getURI() + " gave no size: " + e);
			}
			return null;
		}
	}

	/**
	 * The size declared by an answer: a positive Content-Length of a 2xx answer that
	 * is not compressed, null otherwise.
	 */
	static Long sizeOf(int status, String contentEncoding, String contentLength) {
		if (status < 200 || status >= 300 || contentLength == null) {
			return null;
		}
		if (contentEncoding != null && !contentEncoding.isBlank()
				&& !"identity".equals(contentEncoding.trim().toLowerCase(Locale.ROOT))) {
			return null;
		}
		try {
			final long size = Long.parseLong(contentLength.trim());
			return size > 0 ? size : null;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	static boolean isHttp(String uri) {
		final String lower = uri.trim().toLowerCase(Locale.ROOT);
		return lower.startsWith("http://") || lower.startsWith("https://");
	}
}
