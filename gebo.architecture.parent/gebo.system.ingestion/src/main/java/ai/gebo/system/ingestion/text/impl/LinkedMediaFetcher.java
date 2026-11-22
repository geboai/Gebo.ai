/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.system.ingestion.text.impl;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fetches a media a web page names by an address (the picture of an img, the file of
 * an audio or of a video element). It is resilient by contract: an address that does
 * not answer, answers late, answers with an error or with more bytes than asked gives
 * null and the page is read without that media. A plain GET with no credential of
 * the installation.
 */
final class LinkedMediaFetcher {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinkedMediaFetcher.class);
	static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(5);
	static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(20);

	/** What an address answered: its bytes and the type the server declared, parameters dropped. */
	record Fetched(byte[] data, String contentType) {
	}

	private LinkedMediaFetcher() {
	}

	/**
	 * @param address  an absolute http or https address
	 * @param maxBytes the bytes read at most; a larger answer gives null
	 * @return what the address answered, or null when it is not reachable or not fit
	 */
	static Fetched fetch(String address, long maxBytes) {
		try {
			final URI uri = URI.create(address);
			final String scheme = uri.getScheme() != null ? uri.getScheme().toLowerCase() : "";
			if (!(scheme.equals("http") || scheme.equals("https")) || uri.getHost() == null || maxBytes <= 0) {
				return null;
			}
			final HttpClient client = HttpClient.newBuilder().connectTimeout(CONNECT_TIMEOUT)
					.followRedirects(HttpClient.Redirect.NORMAL).version(HttpClient.Version.HTTP_1_1).build();
			final HttpRequest request = HttpRequest.newBuilder(uri).timeout(REQUEST_TIMEOUT)
					.header("User-Agent", "Gebo.ai").GET().build();
			final HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
			try (InputStream body = response.body()) {
				if (response.statusCode() / 100 != 2) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Linked media " + address + " answered " + response.statusCode() + ": omitted");
					}
					return null;
				}
				final long declared = response.headers().firstValueAsLong("Content-Length").orElse(-1);
				if (declared > maxBytes) {
					return null;
				}
				final byte[] data = body.readNBytes((int) Math.min(Integer.MAX_VALUE - 8, maxBytes + 1));
				if (data.length == 0 || data.length > maxBytes) {
					return null;
				}
				String type = response.headers().firstValue("Content-Type").orElse(null);
				if (type != null && type.indexOf(';') > 0) {
					type = type.substring(0, type.indexOf(';'));
				}
				return new Fetched(data, type != null ? type.trim().toLowerCase() : null);
			}
		} catch (InterruptedException interrupted) {
			Thread.currentThread().interrupt();
			return null;
		} catch (Exception unreachable) {
			// the ingestion is resilient: an element that is not reachable is omitted
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Linked media " + address + " is not reachable: " + unreachable + ": omitted");
			}
			return null;
		}
	}
}
