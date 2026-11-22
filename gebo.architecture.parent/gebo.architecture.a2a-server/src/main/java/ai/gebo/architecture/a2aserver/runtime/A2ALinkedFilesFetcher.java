/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.a2aserver.runtime;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Fetches the files an A2A caller names by a link instead of sending their
 * bytes. A link has this installation call whatever host it names, so only the
 * hosts whoever runs it listed are called
 * ({@code ai.gebo.a2a.server.linked-files-hosts}, none listed at first: no link is
 * fetched). The call is a plain GET without any credential of this installation,
 * redirects are not followed and a file larger than {@link #MAX_FILE_BYTES} is
 * refused.
 */
@Service
public class A2ALinkedFilesFetcher {
	private static final Logger LOGGER = LoggerFactory.getLogger(A2ALinkedFilesFetcher.class);
	static final long MAX_FILE_BYTES = 20L * 1024 * 1024;
	private final List<String> hosts;

	public A2ALinkedFilesFetcher(@Value("${ai.gebo.a2a.server.linked-files-hosts:}") List<String> hosts) {
		this.hosts = hosts == null ? List.of()
				: hosts.stream().filter(h -> h != null && !h.isBlank()).map(h -> h.trim().toLowerCase(Locale.ROOT))
						.toList();
	}

	/** A file fetched: its bytes and the type its server told, or null. */
	public record Fetched(byte[] bytes, String contentType) {
	}

	/** Whether the link is http(s) and names one of the listed hosts. */
	public boolean fetches(String link) {
		try {
			final URI uri = URI.create(link);
			final String scheme = uri.getScheme() != null ? uri.getScheme().toLowerCase(Locale.ROOT) : "";
			return ("http".equals(scheme) || "https".equals(scheme)) && uri.getHost() != null
					&& hosts.contains(uri.getHost().toLowerCase(Locale.ROOT));
		} catch (RuntimeException notALink) {
			return false;
		}
	}

	/**
	 * The file at the link; null when its host is not listed.
	 *
	 * @throws Exception when a listed host does not give the file, or it is too large
	 */
	public Fetched fetch(String link) throws Exception {
		if (!fetches(link)) {
			return null;
		}
		final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
				.followRedirects(HttpClient.Redirect.NEVER).connectTimeout(Duration.ofSeconds(20)).build();
		final HttpResponse<java.io.InputStream> response = client.send(
				HttpRequest.newBuilder().uri(URI.create(link)).timeout(Duration.ofSeconds(60)).GET().build(),
				HttpResponse.BodyHandlers.ofInputStream());
		try (java.io.InputStream body = response.body()) {
			if (response.statusCode() / 100 != 2) {
				throw new IllegalStateException("HTTP " + response.statusCode());
			}
			final byte[] bytes = body.readNBytes((int) MAX_FILE_BYTES + 1);
			if (bytes.length > MAX_FILE_BYTES) {
				throw new IllegalStateException("more than " + MAX_FILE_BYTES + " bytes");
			}
			String type = response.headers().firstValue("Content-Type").orElse(null);
			if (type != null && type.contains(";")) {
				type = type.substring(0, type.indexOf(';')).trim();
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("A2A attachment fetched from " + link + ": " + bytes.length + " byte(s), type " + type);
			}
			return new Fetched(bytes, type);
		}
	}
}
