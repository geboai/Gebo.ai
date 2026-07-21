/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.globaltopology.remoteclient.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.cluster.config.GeboClusterCommonsAutoConfiguration;
import ai.gebo.microservices.globaltopology.remoteclient.GlobalInternalTopologyClient;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.microservices.topology.config.GeboMicroservicesTopologyAutoConfiguration;

/**
 * Publishes the {@link GlobalInternalTopologyClient} (topology-aware, token-forwarding)
 * for any service that wants to read the network-wide internal topology from the
 * coordinator. Not consumed yet — provided for future dynamic broker-binding work.
 */
@AutoConfiguration(after = { GeboMicroservicesTopologyAutoConfiguration.class,
		GeboClusterCommonsAutoConfiguration.class })
@EnableConfigurationProperties(GeboGlobalInternalTopologyClientProperties.class)
public class GeboGlobalInternalTopologyClientAutoConfiguration {

	static final String WEB_CLIENT_BUILDER_BEAN = "geboGlobalTopologyClientLbWebClientBuilder";
	static final String WEB_CLIENT_BEAN = "geboGlobalTopologyClientWebClient";

	@Bean(name = WEB_CLIENT_BUILDER_BEAN)
	@ConditionalOnMissingBean(name = WEB_CLIENT_BUILDER_BEAN)
	@LoadBalanced
	public WebClient.Builder geboGlobalTopologyClientLbWebClientBuilder() {
		return WebClient.builder();
	}

	@Bean(name = WEB_CLIENT_BEAN)
	@ConditionalOnMissingBean(name = WEB_CLIENT_BEAN)
	public WebClient geboGlobalTopologyClientWebClient(@Qualifier(WEB_CLIENT_BUILDER_BEAN) WebClient.Builder builder,
			GeboGlobalInternalTopologyClientProperties properties) {
		return builder
				.codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(properties.getMaxInMemorySizeBytes())).build();
	}

	@Bean
	@ConditionalOnMissingBean(GlobalInternalTopologyClient.class)
	public GlobalInternalTopologyClient globalInternalTopologyClient(@Qualifier(WEB_CLIENT_BEAN) WebClient webClient,
			GeboMicroserviceUrlResolver urlResolver, IGeboCallerTokenPropagator tokenPropagator,
			GeboGlobalInternalTopologyClientProperties properties) {
		return new GlobalInternalTopologyClient(webClient, urlResolver, tokenPropagator,
				properties.getCoordinatorMicroserviceId(), properties.getBasePath());
	}
}
