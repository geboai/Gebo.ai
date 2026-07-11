/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.documents.cache.client.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration for the documents-cache microservice REST client.
 *
 * <p>
 * All values are bound from the {@code ai.gebo.documents.cache.client.*} prefix,
 * e.g.
 *
 * <pre>
 * ai.gebo.documents.cache.client:
 *   base-url: http://chunker_gebo_ai:8080   # or lb://chunker_gebo_ai behind the gateway
 *   api-key: &lt;secret&gt;
 *   api-key-header: X-API-Key
 *   headers:
 *     X-Tenant: acme
 * </pre>
 */
@ConfigurationProperties(prefix = "ai.gebo.documents.cache.client")
public class DocumentsCacheClientProperties {

	/**
	 * Base URL of the chunker microservice that hosts the documents-cache
	 * controllers. Defaults to the chunker's canonical local port.
	 */
	private String baseUrl = "http://localhost:8084";

	/**
	 * Optional API key sent on every request under {@link #apiKeyHeader}. When
	 * {@code null}/blank no api-key header is added.
	 */
	private String apiKey;

	/** Header name carrying {@link #apiKey}. */
	private String apiKeyHeader = "X-API-Key";

	/**
	 * Arbitrary extra headers added to every request (e.g. tenant, correlation or
	 * a custom authorization header). Applied on top of {@link #apiKeyHeader}.
	 */
	private Map<String, String> headers = new LinkedHashMap<>();

	/**
	 * Maximum in-memory buffer (bytes) for a single response body. Chunk sets and
	 * streamed documents can be large, so this is raised well above WebClient's
	 * 256 KB default. Defaults to 256 MB.
	 */
	private int maxInMemorySizeBytes = 256 * 1024 * 1024;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiKeyHeader() {
		return apiKeyHeader;
	}

	public void setApiKeyHeader(String apiKeyHeader) {
		this.apiKeyHeader = apiKeyHeader;
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
