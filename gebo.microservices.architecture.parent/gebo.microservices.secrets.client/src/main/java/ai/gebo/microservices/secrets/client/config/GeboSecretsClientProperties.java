/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.secrets.client.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration of the secrets microservice client, bound from
 * {@code ai.gebo.secrets.client.*}:
 *
 * <pre>
 * ai.gebo.secrets.client:
 *   microservice-id: heimdall_gebo_ai
 *   base-path: api/cluster/SecretsController
 *   headers:
 *     X-Tenant: acme
 * </pre>
 *
 * <p>
 * Note there is no {@code base-url}: the address of the secrets microservice is
 * resolved from the topology by the {@code GeboMicroserviceUrlResolver}, so it
 * follows the deployment's addressing strategy instead of being pinned here. To
 * pin it anyway (a test, a monolith), give it a
 * {@code gebo.microservices.topology.url.direct} entry.
 * </p>
 */
@ConfigurationProperties(prefix = "ai.gebo.secrets.client")
public class GeboSecretsClientProperties {

	/**
	 * Id of the microservice hosting the secrets implementation - the security
	 * service. Resolved through the topology, hence the canonical underscore form.
	 */
	private String microserviceId = "heimdall_gebo_ai";

	/**
	 * Base path the cluster endpoints are served under. Must match
	 * {@code ai.gebo.secrets.cluster.base-path} on the secrets microservice.
	 */
	private String basePath = "api/cluster/SecretsController";

	/*
	 * There is deliberately NO service-token here. A call made off a request thread
	 * does not fall back to a shared static credential - which would be one long-lived
	 * bearer, in shared config, unlocking every secret in the platform. It falls back
	 * to a freshly minted, short-lived LOCAL_JWT for the system identity
	 * (IGeboSystemUserService), configured under ai.gebo.security.system-user.
	 */

	/** Arbitrary extra headers added to every request (tenant, correlation, ...). */
	private Map<String, String> headers = new LinkedHashMap<>();

	/**
	 * Maximum in-memory buffer (bytes) for a single response body. Secret contents
	 * are small - a Google service-account JSON or an ssh key is the large end - so
	 * WebClient's 256 KB default is merely raised to 1 MB.
	 */
	private int maxInMemorySizeBytes = 1024 * 1024;

	public String getMicroserviceId() {
		return microserviceId;
	}

	public void setMicroserviceId(String microserviceId) {
		this.microserviceId = microserviceId;
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

	public int getMaxInMemorySizeBytes() {
		return maxInMemorySizeBytes;
	}

	public void setMaxInMemorySizeBytes(int maxInMemorySizeBytes) {
		this.maxInMemorySizeBytes = maxInMemorySizeBytes;
	}
}
