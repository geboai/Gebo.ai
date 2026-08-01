/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.knowledgebase.client.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.json.JacksonJsonDecoder;
import org.springframework.http.codec.json.JacksonJsonEncoder;
import org.springframework.web.reactive.function.client.WebClient;

import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.cluster.cache.GeboTtlCache;
import ai.gebo.microservices.cluster.config.GeboClusterCommonsAutoConfiguration;
import ai.gebo.microservices.knowledgebase.client.RestKnowledgeBaseHierarchyLookupService;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.microservices.topology.config.GeboMicroservicesTopologyAutoConfiguration;
import ai.gebo.systems.abstraction.layer.IGKnowledgeBaseHierarchyLookupService;
import tools.jackson.databind.json.JsonMapper;

/**
 * Wires the remote, locally-cached GProject/GKnowledgeBase lookup.
 *
 * <p>
 * {@link ConditionalOnMissingBean @ConditionalOnMissingBean(IGKnowledgeBaseHierarchyLookupService.class)}:
 * the owner, which packages {@code gebo.knowledgebase.hierarchy.local}, keeps its
 * local, Mongo-backed lookup and this backs off. The two modules are never on one
 * classpath, so the choice is made in the pom.
 * </p>
 *
 * Gebo.ai comment agent
 */
@AutoConfiguration(after = { GeboMicroservicesTopologyAutoConfiguration.class,
		GeboClusterCommonsAutoConfiguration.class })
@EnableConfigurationProperties(GeboKnowledgeBaseClientProperties.class)
public class GeboKnowledgeBaseMicroserviceClientAutoConfiguration {

	static final String WEB_CLIENT_BUILDER_BEAN = "geboKnowledgeBaseClientLbWebClientBuilder";
	static final String WEB_CLIENT_BEAN = "geboKnowledgeBaseClientWebClient";

	/**
	 * A dedicated {@link LoadBalanced @LoadBalanced} builder so calls to brain's
	 * admin surface go through Spring Cloud LoadBalancer (resolving the topology's
	 * discovery service-id, e.g. {@code brain-gebo-ai}, to a live instance) instead
	 * of a literal DNS lookup, which fails: that hostname is a Eureka service-id,
	 * not a real one. Mirrors the same fix already applied in
	 * {@code GeboSecretsMicroserviceClientAutoConfiguration}.
	 */
	@Bean(name = WEB_CLIENT_BUILDER_BEAN)
	@ConditionalOnMissingBean(name = WEB_CLIENT_BUILDER_BEAN)
	@LoadBalanced
	public WebClient.Builder geboKnowledgeBaseClientLbWebClientBuilder() {
		return WebClient.builder();
	}

	/**
	 * Explicitly wires the app's own {@code ai.gebo.webconfig.JacksonConfig}
	 * {@link JsonMapper} into this WebClient's JSON codecs. {@code WebClient.builder()}
	 * on its own falls back to a default-settings mapper that expects a bare
	 * ISO-8601 date, not the multiple formats (including the cluster's
	 * {@code yyyy-MM-dd HH:mm:ss}) {@code MultiFormatDateDeserializer} accepts, so a
	 * plain field like GProject's {@code dateCreated} - valid, cluster-wide - fails
	 * to decode here otherwise.
	 */
	@Bean(name = WEB_CLIENT_BEAN)
	@ConditionalOnMissingBean(name = WEB_CLIENT_BEAN)
	public WebClient geboKnowledgeBaseClientWebClient(@Qualifier(WEB_CLIENT_BUILDER_BEAN) WebClient.Builder builder,
			JsonMapper objectMapper) {
		return builder.codecs(configurer -> {
			configurer.defaultCodecs().jacksonJsonEncoder(new JacksonJsonEncoder(objectMapper));
			configurer.defaultCodecs().jacksonJsonDecoder(new JacksonJsonDecoder(objectMapper));
		}).build();
	}

	@Bean
	@ConditionalOnMissingBean(IGKnowledgeBaseHierarchyLookupService.class)
	public IGKnowledgeBaseHierarchyLookupService restKnowledgeBaseHierarchyLookupService(
			@Qualifier(WEB_CLIENT_BEAN) WebClient geboKnowledgeBaseClientWebClient,
			GeboMicroserviceUrlResolver urlResolver, IGeboCallerTokenPropagator tokenPropagator,
			GeboKnowledgeBaseClientProperties properties) {
		return new RestKnowledgeBaseHierarchyLookupService(geboKnowledgeBaseClientWebClient, urlResolver,
				tokenPropagator, properties.getMicroserviceId(), properties.getProjectsBasePath(),
				properties.getKnowledgeBaseBasePath(),
				new GeboTtlCache(properties.getCacheTtl(), properties.getCacheMaxEntries()));
	}
}
