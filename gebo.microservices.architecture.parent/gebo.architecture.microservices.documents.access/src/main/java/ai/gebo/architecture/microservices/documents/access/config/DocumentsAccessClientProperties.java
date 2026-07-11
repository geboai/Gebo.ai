/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.microservices.documents.access.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration for the microservices document-content-streamer client.
 *
 * <p>
 * There is no base-url here: the target content-handler microservice is resolved
 * per document from its {@code messagingModuleId} through the
 * {@link ai.gebo.microservices.topology.GeboMicroserviceUrlResolver}. Only the
 * outbound authentication is configured, under {@code ai.gebo.documents.access.client.*}:
 *
 * <pre>
 * ai.gebo.documents.access.client:
 *   api-key: &lt;secret&gt;
 *   api-key-header: X-API-Key
 *   headers:
 *     X-Tenant: acme
 * </pre>
 */
@ConfigurationProperties(prefix = "ai.gebo.documents.access.client")
public class DocumentsAccessClientProperties {

	/**
	 * Optional API key sent on every request under {@link #apiKeyHeader}. When
	 * {@code null}/blank no api-key header is added.
	 */
	private String apiKey;

	/** Header name carrying {@link #apiKey}. */
	private String apiKeyHeader = "X-API-Key";

	/**
	 * Arbitrary extra headers added to every request (e.g. an application
	 * authorization token, tenant or correlation headers). Applied on top of
	 * {@link #apiKeyHeader}.
	 */
	private Map<String, String> headers = new LinkedHashMap<>();

	/**
	 * Maximum in-memory buffer (bytes) for a single streamed document. Documents
	 * can be large, so this is raised well above WebClient's 256 KB default.
	 * Defaults to 256 MB.
	 */
	private int maxInMemorySizeBytes = 256 * 1024 * 1024;

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
