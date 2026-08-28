/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging.model;

/**
 * Builds the sanitized locator stored in {@link DataEndpoint#getEndpoint()}.
 *
 * <p>
 * A data-flow report is an ADMIN-visible map of every store this installation
 * talks to, so it must name each endpoint without ever carrying the credential
 * that guards it. The configuration objects the reporting components read from
 * do carry them inline:
 * </p>
 *
 * <ul>
 * <li>{@code ai.gebo.mongodb.connectionString} is
 * {@code mongodb://mongoroot:mongopwd@mongo:27017/?authSource=admin} - user and
 * password in the userinfo;</li>
 * <li>{@code QdrantConfig} carries an {@code apiKey}, {@code OpenSearchConfig} a
 * {@code username}/{@code password}, {@code spring.neo4j.authentication} a
 * {@code password};</li>
 * <li>a query string can carry a key just as easily
 * ({@code ...?apiKey=...}).</li>
 * </ul>
 *
 * <p>
 * So this keeps only what an auditor needs - scheme, host, port and path (the
 * database / index / collection name) - and drops userinfo, query and fragment
 * outright. It is deliberately string surgery rather than {@code java.net.URI}
 * parsing: connection strings are not always RFC-conformant URIs (a MongoDB
 * replica-set string carries a comma-separated host list) and a report must
 * never fail to render because a configuration value could not be parsed. The
 * credential-bearing part is removed on every path, including the fallbacks.
 * </p>
 *
 * <p>
 * Callers do not normally invoke this directly:
 * {@link DataEndpoint#setEndpoint(String)} applies it, so a raw connection
 * string cannot be stored in the model even by mistake. It is idempotent, so
 * re-applying it on the receiving side of the topology hop is harmless.
 * </p>
 */
public final class DataEndpointLocator {

	/** The placeholder used when a value cannot be reduced to a safe locator. */
	public static final String REDACTED = "[redacted]";

	private DataEndpointLocator() {
	}

	/**
	 * Reduces a raw connection string or URI to a credential-free locator.
	 *
	 * @param rawUri the configured value, possibly carrying userinfo and a query
	 * @return the sanitized locator, or null when there was nothing to sanitize
	 */
	public static String sanitize(String rawUri) {
		if (rawUri == null) {
			return null;
		}
		String value = rawUri.trim();
		if (value.isEmpty()) {
			return null;
		}

		// Fragment and query can both carry secrets, and neither identifies the
		// endpoint - drop them before anything else looks at the string.
		value = cutAt(value, '#');
		value = cutAt(value, '?');

		String scheme = null;
		String remainder = value;
		int schemeEnd = value.indexOf("://");
		if (schemeEnd > 0) {
			scheme = value.substring(0, schemeEnd);
			remainder = value.substring(schemeEnd + 3);
		}

		// Everything up to the first '/' is the authority; the rest is the path
		// (database, index or collection name), which is audit-relevant and kept.
		String authority = remainder;
		String path = "";
		int pathStart = remainder.indexOf('/');
		if (pathStart >= 0) {
			authority = remainder.substring(0, pathStart);
			path = remainder.substring(pathStart);
		}

		// Userinfo is delimited by the LAST '@' - a password may itself contain
		// an encoded or literal '@', so splitting on the first one would leak
		// the tail of it into the host.
		int userInfoEnd = authority.lastIndexOf('@');
		if (userInfoEnd >= 0) {
			authority = authority.substring(userInfoEnd + 1);
		}

		if (authority.isEmpty()) {
			// Nothing recognizable survived; never fall back to the raw input.
			return REDACTED;
		}

		// A trailing '/' adds nothing and makes otherwise identical endpoints
		// compare unequal in the screen.
		if (path.equals("/")) {
			path = "";
		}

		return (scheme != null ? scheme + "://" : "") + authority + path;
	}

	/**
	 * Builds a locator from already-separate configuration fields, which is how
	 * most of the stores are configured ({@code OpenSearchConfig} and
	 * {@code QdrantConfig} both hold host and port apart, never a URI).
	 *
	 * @param scheme the protocol, e.g. {@code https} or {@code bolt}; may be null
	 * @param host   the hostname or address
	 * @param port   the port; omitted when null
	 * @param path   the database / index / collection name; may be null
	 * @return the sanitized locator, or null when no host was given
	 */
	public static String of(String scheme, String host, Integer port, String path) {
		if (host == null || host.trim().isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		if (scheme != null && !scheme.trim().isEmpty()) {
			sb.append(scheme.trim()).append("://");
		}
		sb.append(host.trim());
		if (port != null) {
			sb.append(':').append(port);
		}
		if (path != null && !path.trim().isEmpty()) {
			String tail = path.trim();
			if (!tail.startsWith("/")) {
				sb.append('/');
			}
			sb.append(tail);
		}
		// Run the result back through sanitize() so a host field that itself
		// contains userinfo (nothing stops a misconfiguration) is still cleaned.
		return sanitize(sb.toString());
	}

	private static String cutAt(String value, char delimiter) {
		int index = value.indexOf(delimiter);
		return index >= 0 ? value.substring(0, index) : value;
	}
}
