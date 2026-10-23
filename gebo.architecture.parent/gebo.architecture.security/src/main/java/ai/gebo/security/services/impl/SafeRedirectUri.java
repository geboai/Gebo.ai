/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.services.impl;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Where a login may send the browser when it is over.
 *
 * <p>
 * The address comes from the page that started the login, travels through the
 * provider and comes back, so by the time it is used it is simply a parameter of a
 * request: anyone can hand out a link that starts a login here and ends anywhere.
 * It used to be redirected to as it stood, which turns the installation into a
 * convenient way to send someone to another site from a trusted address, and in one
 * case it was also written into the page that carries the session token.
 * </p>
 *
 * <p>
 * Only somewhere on this installation is allowed: a path of its own, or an absolute
 * address whose host is the one the request arrived on. Anything else falls back to
 * the page given by the caller.
 * </p>
 */
public final class SafeRedirectUri {

	private static final Logger LOGGER = LoggerFactory.getLogger(SafeRedirectUri.class);

	private SafeRedirectUri() {
	}

	/**
	 * @param candidate the address asked for, may be null
	 * @param request   the request it arrived on, may be null
	 * @param fallback  where to go when the address is not one of ours
	 * @return the address to send the browser to, never null when fallback is not
	 */
	public static String resolve(String candidate, HttpServletRequest request, String fallback) {
		if (candidate == null || candidate.isBlank()) {
			return fallback;
		}
		String trimmed = candidate.trim();
		// "//host/path" is a complete address in disguise, not a path of ours, and
		// "/\" is read as one by some browsers
		if (trimmed.startsWith("/") && !trimmed.startsWith("//") && !trimmed.startsWith("/\\")) {
			return trimmed;
		}
		try {
			URI uri = new URI(trimmed);
			if (uri.getHost() != null) {
				// it names a host, with or without a scheme: "//host/path" is a complete
				// address to a browser even though it is not an absolute uri
				if (request != null && uri.getHost().equalsIgnoreCase(request.getServerName())) {
					return trimmed;
				}
			} else if (!uri.isAbsolute()) {
				// a bare relative address, resolved against this application anyway
				return trimmed;
			}
		} catch (URISyntaxException notAnAddress) {
			LOGGER.warn("A login asked to end at something that is not an address, ignored");
			return fallback;
		}
		LOGGER.warn("A login asked to end at another host, ignored");
		return fallback;
	}

	/**
	 * A json literal, safe to write inside a {@code <script>} of a page. Quotes and
	 * backslashes are left alone, because json has already escaped them and the
	 * literal has to stay valid; what is escaped is what would end the script from
	 * inside a string, which in json can only be a string's contents.
	 */
	public static String forScriptJson(String json) {
		return json.replace("<", "\\u003c").replace(">", "\\u003e").replace("&", "\\u0026")
				.replace("\u2028", "\\u2028").replace("\u2029", "\\u2029");
	}

	/**
	 * The same text, safe to write between quotes inside a {@code <script>} of a
	 * page: a quote would end the string it sits in, and {@code </script>} ends the
	 * script itself whatever it sits in.
	 */
	public static String forScriptString(String value) {
		StringBuilder out = new StringBuilder(value.length() + 16);
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			switch (c) {
			case '<' -> out.append("\\u003c");
			case '>' -> out.append("\\u003e");
			case '&' -> out.append("\\u0026");
			case '\'' -> out.append("\\u0027");
			case '"' -> out.append("\\u0022");
			case '\\' -> out.append("\\\\");
			case '\r' -> out.append("\\r");
			case '\n' -> out.append("\\n");
			case ' ' -> out.append("\\u2028");
			case ' ' -> out.append("\\u2029");
			default -> out.append(c);
			}
		}
		return out.toString();
	}
}
