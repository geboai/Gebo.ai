/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.microservices.documents.access.config;

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
import ai.gebo.architecture.microservices.documents.access.GMicroserviceDocumentContentStreamerClient;
import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import tools.jackson.databind.json.JsonMapper;

/**
 * Wires the microservices document-content-streamer client.
 *
 * <p>
 * Builds a dedicated {@link WebClient} (no base url — targets are absolute,
 * resolved per document) carrying the api-key header and any extra headers, then
 * publishes an {@link IGDocumentContentStreamer} bean backed by it and the
 * {@link GeboMicroserviceUrlResolver}. The streamer bean is
 * {@link ConditionalOnMissingBean @ConditionalOnMissingBean}, so a deployment
 * that already provides a streamer (e.g. the monolith's in-process
 * implementation) keeps it while a content-less microservice (e.g. the chunker,
 * which caches document content it does not itself hold) gets the remote client.
 *
 * <p>
 * Picked up by the standard {@code ai.gebo} component scan of the hosting app.
 */
@Configuration
@EnableConfigurationProperties(DocumentsAccessClientProperties.class)
public class MicroserviceDocumentsAccessClientConfiguration {

	static final String WEB_CLIENT_BUILDER_BEAN = "documentsAccessClientLbWebClientBuilder";
	static final String WEB_CLIENT_BEAN = "documentsAccessClientWebClient";

	/**
	 * A dedicated {@link LoadBalanced @LoadBalanced} builder so calls resolved by
	 * {@link GeboMicroserviceUrlResolver} (host = the target's Eureka discovery
	 * service-id, e.g. {@code filesystem-gebo-ai}) go through Spring Cloud
	 * LoadBalancer instead of a literal DNS lookup, which fails: that hostname is
	 * a Eureka service-id, not a real one. Mirrors the same fix already applied in
	 * {@code GeboSecretsMicroserviceClientAutoConfiguration} and
	 * {@code GeboKnowledgeBaseMicroserviceClientAutoConfiguration}.
	 */
	@Bean(name = WEB_CLIENT_BUILDER_BEAN)
	@ConditionalOnMissingBean(name = WEB_CLIENT_BUILDER_BEAN)
	@LoadBalanced
	public WebClient.Builder documentsAccessClientLbWebClientBuilder() {
		return WebClient.builder();
	}

	/**
	 * Dedicated {@link WebClient} carrying the api-key header and extra headers for
	 * every call to a content-handler's {@code DocumentContentStreamerController}.
	 *
	 * <p>
	 * Explicitly wires the app's own {@code ai.gebo.webconfig.JacksonConfig}
	 * {@link JsonMapper} into this WebClient's JSON codecs. A bare
	 * {@code WebClient.builder()} falls back to a default-settings mapper that
	 * serializes {@code java.util.Date} as strict ISO-8601 with milliseconds (e.g.
	 * {@code 2026-07-25T22:28:21.000Z}) - the request body's
	 * {@code GDocumentReference} carries such fields - but the target's own request
	 * deserialization uses the cluster's {@code MultiFormatDateDeserializer}, which
	 * doesn't accept that exact shape and rejects the call with 400 Bad Request.
	 * Mirrors the same fix already applied in
	 * {@code GeboKnowledgeBaseMicroserviceClientAutoConfiguration}.
	 */
	@Bean(name = WEB_CLIENT_BEAN)
	@ConditionalOnMissingBean(name = WEB_CLIENT_BEAN)
	public WebClient documentsAccessClientWebClient(@Qualifier(WEB_CLIENT_BUILDER_BEAN) WebClient.Builder lbBuilder,
			DocumentsAccessClientProperties properties, JsonMapper objectMapper) {
		WebClient.Builder builder = lbBuilder.codecs(codecs -> {
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
	@ConditionalOnMissingBean(IGDocumentContentStreamer.class)
	public IGDocumentContentStreamer microserviceDocumentContentStreamer(
			@Qualifier(WEB_CLIENT_BEAN) WebClient documentsAccessClientWebClient,
			GeboMicroserviceUrlResolver urlResolver, IGeboCallerTokenPropagator tokenPropagator) {
		return new GMicroserviceDocumentContentStreamerClient(documentsAccessClientWebClient, urlResolver,
				tokenPropagator);
	}
}
