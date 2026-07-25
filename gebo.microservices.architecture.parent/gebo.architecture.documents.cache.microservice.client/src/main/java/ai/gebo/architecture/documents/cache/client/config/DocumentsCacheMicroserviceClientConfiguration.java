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
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.JacksonJsonDecoder;
import org.springframework.http.codec.json.JacksonJsonEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import ai.gebo.architecture.documents.access.IGDocumentContentStreamer;
import ai.gebo.architecture.documents.cache.client.DocumentContentStreamerWithCacheRestClient;
import ai.gebo.architecture.documents.cache.client.DocumentsCacheServiceRestClient;
import ai.gebo.architecture.documents.cache.client.DocumentsChunkServiceRestClient;
import ai.gebo.architecture.documents.cache.service.IDocumentsCacheService;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import tools.jackson.databind.json.JsonMapper;

/**
 * Wires the documents-cache microservice REST client.
 *
 * <p>
 * Builds a dedicated {@link WebClient} pointed at the chunker resolved by
 * {@link GeboMicroserviceUrlResolver} (from
 * {@link DocumentsCacheClientProperties#getMicroserviceId()}) and pre-loaded
 * with the api-key header and any extra headers, then publishes
 * {@link IDocumentsCacheService}, {@link IDocumentsChunkService} and
 * {@link IGDocumentContentStreamer} beans backed by it. This whole
 * configuration only ever activates where chunker is a REMOTE microservice
 * (every bean here is {@link ConditionalOnMissingBean @ConditionalOnMissingBean}
 * and backs off wherever chunker's own local implementation is already
 * present, e.g. on the chunker microservice itself or the monolith), so
 * resolving through the topology - not a statically configured
 * {@link DocumentsCacheClientProperties#getBaseUrl() base-url} - is always the
 * right address: a fixed {@code http://localhost:13004} default (this
 * property's own fallback) is unreachable from another container.
 *
 * <p>
 * Picked up by the standard {@code ai.gebo} component scan of the hosting app.
 */
@Configuration
@EnableConfigurationProperties(DocumentsCacheClientProperties.class)
public class DocumentsCacheMicroserviceClientConfiguration {

	static final String WEB_CLIENT_BUILDER_BEAN = "documentsCacheClientLbWebClientBuilder";
	static final String WEB_CLIENT_BEAN = "documentsCacheClientWebClient";

	/**
	 * A dedicated {@link LoadBalanced @LoadBalanced} builder so calls resolved by
	 * {@link GeboMicroserviceUrlResolver} (host = chunker's Eureka discovery
	 * service-id, e.g. {@code chunker-gebo-ai}) go through Spring Cloud
	 * LoadBalancer instead of a literal DNS lookup, which fails: that hostname is
	 * a Eureka service-id, not a real one. Mirrors the same fix already applied in
	 * {@code MicroserviceDocumentsAccessClientConfiguration} and others.
	 */
	@Bean(name = WEB_CLIENT_BUILDER_BEAN)
	@ConditionalOnMissingBean(name = WEB_CLIENT_BUILDER_BEAN)
	@LoadBalanced
	public WebClient.Builder documentsCacheClientLbWebClientBuilder() {
		return WebClient.builder();
	}

	/**
	 * Dedicated {@link WebClient} carrying the api-key header and extra headers for
	 * every call to the documents-cache controllers.
	 *
	 * <p>
	 * Explicitly wires the app's own {@code ai.gebo.webconfig.JacksonConfig}
	 * {@link JsonMapper} into this WebClient's JSON codecs, same reasoning (and
	 * same fix) as {@code MicroserviceDocumentsAccessClientConfiguration}: request
	 * and response bodies here carry {@code java.util.Date} fields (e.g.
	 * {@code GDocumentReference}), and a bare {@code WebClient.builder()}'s
	 * default-settings mapper does not produce or accept the exact shape the
	 * cluster's {@code MultiFormatDateDeserializer} does.
	 */
	@Bean(name = WEB_CLIENT_BEAN)
	@ConditionalOnMissingBean(name = WEB_CLIENT_BEAN)
	public WebClient documentsCacheClientWebClient(@Qualifier(WEB_CLIENT_BUILDER_BEAN) WebClient.Builder lbBuilder,
			GeboMicroserviceUrlResolver urlResolver, DocumentsCacheClientProperties properties,
			JsonMapper objectMapper) {
		String baseUrl = urlResolver.baseUrlForMicroserviceId(properties.getMicroserviceId())
				.orElse(properties.getBaseUrl());
		WebClient.Builder builder = lbBuilder.baseUrl(baseUrl).codecs(codecs -> {
			codecs.defaultCodecs().maxInMemorySize(properties.getMaxInMemorySizeBytes());
			codecs.defaultCodecs().jacksonJsonEncoder(new JacksonJsonEncoder(objectMapper));
			codecs.defaultCodecs().jacksonJsonDecoder(new JacksonJsonDecoder(objectMapper));
		});
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

	/**
	 * {@link IGDocumentContentStreamer} served by the chunker's document cache: the
	 * content is fetched from its owning content handler once by the chunker, cached
	 * there and streamed back from that local copy afterwards. The chunker is
	 * addressed through the {@link GeboMicroserviceUrlResolver} (from
	 * {@link DocumentsCacheClientProperties#getMicroserviceId()}), not through the
	 * client's {@code base-url}, so the call honours the deployment's addressing
	 * strategy.
	 */
	@Bean
	@ConditionalOnMissingBean(IGDocumentContentStreamer.class)
	public IGDocumentContentStreamer documentContentStreamerWithCacheRestClient(
			@Qualifier(WEB_CLIENT_BEAN) WebClient documentsCacheClientWebClient,
			GeboMicroserviceUrlResolver urlResolver, DocumentsCacheClientProperties properties) {
		return new DocumentContentStreamerWithCacheRestClient(documentsCacheClientWebClient, urlResolver,
				properties.getMicroserviceId());
	}
}
