/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.web.util.matcher.RequestMatcher;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Matches the requests a metrics scraper or a health probe makes to the actuator's
 * {@code prometheus} and {@code health} endpoints, only when they arrive on the
 * separate management port.
 *
 * <p>
 * Prometheus scrapes without a user, while every {@code /actuator/**} call on the
 * application port needs an authenticated one. Opening the endpoints on the
 * application port would publish them wherever the application is published (the
 * monolith's 12999, the gateway). The management port instead is an opt-in
 * ({@code management.server.port}, e.g. the {@code MANAGEMENT_SERVER_PORT}
 * environment variable) that the observability compose files and the Helm chart set
 * and never publish, so only the monitoring network reaches it.
 * </p>
 *
 * <p>
 * Nothing matches when no management port is configured, or when it is the same as
 * the application port: the actuator then stays behind authentication as before.
 * The ports are read at each request, because a port configured as 0 (random, in the
 * tests) is known only once the server has started ({@code local.management.port}).
 * Every other actuator endpoint ({@code metrics} included) keeps requiring
 * authentication on both ports.
 * </p>
 */
public class ManagementPortScrapeRequestMatcher implements RequestMatcher {
	private static final Logger LOGGER = LoggerFactory.getLogger(ManagementPortScrapeRequestMatcher.class);

	private final Environment environment;

	public ManagementPortScrapeRequestMatcher(Environment environment) {
		this.environment = environment;
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		Integer managementPort = port("local.management.port", "management.server.port");
		if (managementPort == null || managementPort <= 0) {
			return false;
		}
		Integer serverPort = port("local.server.port", "server.port");
		if (managementPort.equals(serverPort) || request.getLocalPort() != managementPort) {
			return false;
		}
		String uri = request.getRequestURI();
		// No dot segments or path parameters: the firewall already rejects them, and a
		// "health/../env" must never be read as a health probe here either.
		if (uri == null || uri.contains("..") || uri.contains(";") || uri.contains("//")) {
			return false;
		}
		String base = basePath();
		boolean scrape = uri.equals(base + "/prometheus") || uri.equals(base + "/health")
				|| uri.startsWith(base + "/health/");
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("matches(...) uri:" + uri + " on management port:" + managementPort + " scrape:" + scrape);
		}
		return scrape;
	}

	/**
	 * The actuator's base path on the management server: the management server's own
	 * base path (the servlet context path does not apply to it) plus the endpoints'
	 * base path.
	 */
	String basePath() {
		String serverBase = trimSlash(environment.getProperty("management.server.base-path", ""));
		String endpointsBase = trimSlash(environment.getProperty("management.endpoints.web.base-path", "/actuator"));
		return serverBase + endpointsBase;
	}

	private Integer port(String actual, String configured) {
		Integer port = environment.getProperty(actual, Integer.class);
		if (port == null || port <= 0) {
			port = environment.getProperty(configured, Integer.class);
		}
		return port;
	}

	private static String trimSlash(String path) {
		if (path == null || path.isBlank() || path.equals("/")) {
			return "";
		}
		String p = path.startsWith("/") ? path : "/" + path;
		return p.endsWith("/") ? p.substring(0, p.length() - 1) : p;
	}
}
