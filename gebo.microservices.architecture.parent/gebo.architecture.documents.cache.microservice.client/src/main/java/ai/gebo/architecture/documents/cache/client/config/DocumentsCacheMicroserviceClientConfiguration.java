/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.documents.cache.client.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import ai.gebo.architecture.documents.cache.client.DocumentsCacheServiceRestClient;
import ai.gebo.architecture.documents.cache.client.DocumentsChunkServiceRestClient;
import ai.gebo.architecture.documents.cache.service.IDocumentsCacheService;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;

/**
 * Wires the documents-cache microservice REST client.
 *
 * <p>
 * Builds a dedicated {@link WebClient} pointed at the configured chunker base
 * URL and pre-loaded with the api-key header and any extra headers, then
 * publishes {@link IDocumentsCacheService} and {@link IDocumentsChunkService}
 * beans backed by it. Both service beans are
 * {@link ConditionalOnMissingBean @ConditionalOnMissingBean}, so a service that
 * already hosts the real implementation (e.g. the chunker microservice itself)
 * keeps its local beans while a consumer that does not (e.g. brain.gebo.ai)
 * transparently gets the remote client.
 *
 * <p>
 * Picked up by the standard {@code ai.gebo} component scan of the hosting app.
 */
@Configuration
@EnableConfigurationProperties(DocumentsCacheClientProperties.class)
public class DocumentsCacheMicroserviceClientConfiguration {

	static final String WEB_CLIENT_BEAN = "documentsCacheClientWebClient";

	/**
	 * Dedicated {@link WebClient} carrying the base URL, api-key header and extra
	 * headers for every call to the documents-cache controllers.
	 */
	@Bean(name = WEB_CLIENT_BEAN)
	@ConditionalOnMissingBean(name = WEB_CLIENT_BEAN)
	public WebClient documentsCacheClientWebClient(DocumentsCacheClientProperties properties) {
		WebClient.Builder builder = WebClient.builder().baseUrl(properties.getBaseUrl())
				.codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(properties.getMaxInMemorySizeBytes()));
		if (StringUtils.hasText(properties.getApiKey())) {
			builder.defaultHeader(properties.getApiKeyHeader(), properties.getApiKey());
		}
		if (properties.getHeaders() != null) {
			properties.getHeaders().forEach(builder::defaultHeader);
		}
		return builder.build();
	}

	@Bean
	@ConditionalOnMissingBean(IDocumentsCacheService.class)
	public IDocumentsCacheService documentsCacheServiceRestClient(
			@Qualifier(WEB_CLIENT_BEAN) WebClient documentsCacheClientWebClient) {
		return new DocumentsCacheServiceRestClient(documentsCacheClientWebClient);
	}

	@Bean
	@ConditionalOnMissingBean(IDocumentsChunkService.class)
	public IDocumentsChunkService documentsChunkServiceRestClient(
			@Qualifier(WEB_CLIENT_BEAN) WebClient documentsCacheClientWebClient) {
		return new DocumentsChunkServiceRestClient(documentsCacheClientWebClient);
	}
}
