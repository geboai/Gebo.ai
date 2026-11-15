/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.acl.client.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration of the ACL client ({@code ai.gebo.acl.client.*}).
 *
 * <pre>
 * ai.gebo.acl.client:
 *   microservice-id: heimdall_gebo_ai
 *   base-path: api/cluster/AclController
 *   cache-ttl: 60s
 * </pre>
 *
 * <p>
 * Nothing here selects the remote store: <b>depending on this module does</b>. A
 * service packages either {@code gebo.microservices.acl.client} or
 * {@code gebo.acl.mongo}, never both.
 * </p>
 *
 * <p>
 * The owner's address is resolved from the topology, unless the topology is switched
 * off ({@code gebo.microservices.topology.enabled=false}): then it is
 * {@link #getBaseUrl() base-url}.
 * </p>
 */
@ConfigurationProperties(prefix = GeboAclClientProperties.PREFIX)
public class GeboAclClientProperties {

	/** Prefix of the ACL client configuration. */
	public static final String PREFIX = "ai.gebo.acl.client";

	/** Id of the microservice that owns the ACL store. */
	private String microserviceId = "heimdall_gebo_ai";

	/**
	 * The owner's base url, context path included (e.g. {@code http://gebo-ai:12999}
	 * for a monolith), called with a plain WebClient - no discovery.
	 *
	 * <p>
	 * Read only with the topology switched off, and then required: the service does
	 * not start without it. With the topology on, the owner's address comes from the
	 * topology and this is ignored, with a warning.
	 * </p>
	 */
	private String baseUrl;

	/** Base path of the ACL endpoints; must match {@code ai.gebo.acl.cluster.base-path}. */
	private String basePath = "api/cluster/AclController";

	/**
	 * How long a read is cached.
	 *
	 * <p>
	 * This is the staleness dial. An ACL change made <b>elsewhere</b> becomes visible
	 * here within this window - including a <i>revocation</i>. Lower it if a
	 * deployment cannot tolerate that; the cost is more calls to the owner on a data
	 * set that is read constantly. A write made through this client invalidates its
	 * own cache immediately, so this window never applies to your own grants.
	 * </p>
	 */
	private Duration cacheTtl = Duration.ofSeconds(60);

	/** Hard bound on cached alias lookups; past it the cache is emptied rather than grown. */
	private int cacheMaxEntries = 10000;

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

	public Duration getCacheTtl() {
		return cacheTtl;
	}

	public void setCacheTtl(Duration cacheTtl) {
		this.cacheTtl = cacheTtl;
	}
}
