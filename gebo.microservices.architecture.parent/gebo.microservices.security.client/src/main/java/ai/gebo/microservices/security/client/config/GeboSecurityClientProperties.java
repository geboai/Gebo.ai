/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.security.client.config;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration of the security directory client, bound from
 * {@code ai.gebo.security.client.*}:
 *
 * <pre>
 * ai.gebo.security.client:
 *   microservice-id: heimdall_gebo_ai
 *   base-path: api/cluster/SecurityController
 * </pre>
 *
 * <p>
 * Nothing here selects the remote directory: <b>depending on this module does</b>. A
 * service packages either {@code gebo.microservices.security.client} (remote) or
 * {@code gebo.security.directory.mongo} (local), never both.
 * </p>
 *
 * <p>
 * Heimdall's address is resolved from the topology by the
 * {@code GeboMicroserviceUrlResolver}, so it follows the deployment's addressing
 * strategy. Only a service whose topology is switched off
 * ({@code gebo.microservices.topology.enabled=false}) names it, with
 * {@link #getBaseUrl() base-url}. There is no token - the caller's own is forwarded,
 * falling back to the system identity's (see {@code IGeboCallerTokenPropagator}).
 * </p>
 */
@ConfigurationProperties(prefix = GeboSecurityClientProperties.PREFIX)
public class GeboSecurityClientProperties {

	/** Prefix of the security client configuration. */
	public static final String PREFIX = "ai.gebo.security.client";

	/** Id of the microservice that owns the user store. */
	private String microserviceId = "heimdall_gebo_ai";

	/**
	 * Heimdall's base url, context path included (e.g. {@code http://gebo-ai:12999}
	 * for a monolith, which serves the cluster surface at its root), called with a
	 * plain WebClient - no discovery.
	 *
	 * <p>
	 * Read only with the topology switched off, and then required: the service does
	 * not start without it. With the topology on, heimdall's address comes from the
	 * topology and this is ignored, with a warning.
	 * </p>
	 */
	private String baseUrl;

	/**
	 * Base path of the directory endpoints. Must match
	 * {@code ai.gebo.security.cluster.base-path} on heimdall.
	 */
	private String basePath = "api/cluster/SecurityController";

	/**
	 * How long an identity lookup (user, groups) is cached, keyed by the presented
	 * credential.
	 *
	 * <p>
	 * This is the staleness dial. A role or group change made on heimdall is visible
	 * here within this window - including a <i>revocation</i>. Without it, a busy service
	 * asks heimdall who the caller is on every single request. A password check is never
	 * cached, at any TTL.
	 * </p>
	 */
	private Duration cacheTtl = Duration.ofSeconds(60);

	/** Hard bound on cached identities; past it the cache is emptied rather than grown. */
	private int cacheMaxEntries = 10000;

	/** Arbitrary extra headers added to every request (tenant, correlation, ...). */
	private Map<String, String> headers = new LinkedHashMap<>();
	/** Heimdall's installation controller, under its /public/ path. */
	private String installationBasePath = "public/InstallationController";

	public String getInstallationBasePath() {
		return installationBasePath;
	}

	public void setInstallationBasePath(String installationBasePath) {
		this.installationBasePath = installationBasePath;
	}

	public Duration getCacheTtl() {
		return cacheTtl;
	}

	public void setCacheTtl(Duration cacheTtl) {
		this.cacheTtl = cacheTtl;
	}

	public int getCacheMaxEntries() {
		return cacheMaxEntries;
	}

	public void setCacheMaxEntries(int cacheMaxEntries) {
		this.cacheMaxEntries = cacheMaxEntries;
	}

	public String getMicroserviceId() {
		return microserviceId;
	}

	public void setMicroserviceId(String microserviceId) {
		this.microserviceId = microserviceId;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
}
