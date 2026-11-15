/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.globaltopology.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.webclient.WebClientCustomizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.cluster.config.GeboClusterCommonsAutoConfiguration;
import ai.gebo.microservices.globaltopology.client.InternalTopologyPollClient;
import ai.gebo.microservices.globaltopology.service.IGGlobalInternalTopologyService;
import ai.gebo.microservices.globaltopology.service.impl.GGlobalInternalTopologyServiceImpl;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;
import ai.gebo.microservices.topology.config.GeboMicroservicesTopologyAutoConfiguration;

/**
 * Wires the global internal-topology coordinator (deployed on tyr): a load-balanced
 * WebClient, the per-microservice poll client, the scheduled/on-demand service, and
 * the admin controller. {@link EnableScheduling} guarantees the service's
 * {@code @Scheduled} poll runs even on a host that has not otherwise enabled it.
 */
@AutoConfiguration(after = { GeboMicroservicesTopologyAutoConfiguration.class,
		GeboClusterCommonsAutoConfiguration.class })
@EnableConfigurationProperties(GeboGlobalInternalTopologyProperties.class)
@EnableScheduling
public class GeboGlobalInternalTopologyAutoConfiguration {

	static final String WEB_CLIENT_BUILDER_BEAN = "geboGlobalTopologyLbWebClientBuilder";
	static final String WEB_CLIENT_BEAN = "geboGlobalTopologyWebClient";

	@Bean(name = WEB_CLIENT_BUILDER_BEAN)
	@ConditionalOnMissingBean(name = WEB_CLIENT_BUILDER_BEAN)
	@LoadBalanced
	public WebClient.Builder geboGlobalTopologyLbWebClientBuilder(ObjectProvider<WebClientCustomizer> customizers) {
		// Built as Spring Boot builds its own WebClient.Builder: with the application's
		// customizers applied. With tracing, that is the observation filter that gives
		// each call a span and sends the current trace (the traceparent header) to the
		// service called. Spring Boot's builder bean itself cannot be injected here: it
		// is a WebClient.Builder like this one, and this one would be a candidate for
		// its own injection.
		WebClient.Builder builder = WebClient.builder();
		customizers.orderedStream().forEach(customizer -> customizer.customize(builder));
		return builder;
	}

	@Bean(name = WEB_CLIENT_BEAN)
	@ConditionalOnMissingBean(name = WEB_CLIENT_BEAN)
	public WebClient geboGlobalTopologyWebClient(@Qualifier(WEB_CLIENT_BUILDER_BEAN) WebClient.Builder builder,
			GeboGlobalInternalTopologyProperties properties) {
		return builder
				.codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(properties.getMaxInMemorySizeBytes())).build();
	}

	@Bean
	@ConditionalOnMissingBean(InternalTopologyPollClient.class)
	public InternalTopologyPollClient internalTopologyPollClient(@Qualifier(WEB_CLIENT_BEAN) WebClient webClient,
			GeboMicroserviceUrlResolver urlResolver, IGeboCallerTokenPropagator tokenPropagator,
			GeboGlobalInternalTopologyProperties properties) {
		return new InternalTopologyPollClient(webClient, urlResolver, tokenPropagator, properties.getPollBasePath());
	}

	@Bean
	@ConditionalOnMissingBean(IGGlobalInternalTopologyService.class)
	public GGlobalInternalTopologyServiceImpl globalInternalTopologyService(GeboMicroservicesTopology topology,
			InternalTopologyPollClient pollClient) {
		return new GGlobalInternalTopologyServiceImpl(topology, pollClient);
	}

	// GlobalInternalTopologyController is a @RestController picked up by tyr's
	// @ComponentScan("ai.gebo") — not declared here, to avoid a duplicate handler.
}
