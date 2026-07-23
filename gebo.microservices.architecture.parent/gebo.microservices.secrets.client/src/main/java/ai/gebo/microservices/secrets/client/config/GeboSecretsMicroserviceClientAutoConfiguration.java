/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.secrets.client.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.cluster.cache.GeboTtlCache;
import ai.gebo.microservices.cluster.config.GeboClusterCommonsAutoConfiguration;
import ai.gebo.microservices.secrets.client.GeboSecretsAccessServiceRestClient;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.microservices.topology.config.GeboMicroservicesTopologyAutoConfiguration;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import tools.jackson.databind.ObjectMapper;

/**
 * Wires the secrets microservice REST client.
 *
 * <p>
 * The {@link IGeboSecretsAccessService} bean is
 * {@link ConditionalOnMissingBean @ConditionalOnMissingBean}: the security
 * microservice, which owns the real {@code GeboSecretsAccessServiceImpl}, keeps
 * its local in-process implementation, while every other service that has this
 * jar on its classpath transparently gets the remote client behind the same
 * interface. Being an {@link AutoConfiguration} - evaluated only once the
 * application's own beans are known - is what makes that condition reliable.
 * </p>
 *
 * <p>
 * The {@link WebClient} carries no base url: the target is resolved per call from
 * the topology (see {@link GeboSecretsAccessServiceRestClient}), so this
 * auto-configuration must run after the {@link GeboMicroserviceUrlResolver} bean
 * exists.
 * </p>
 *
 * Gebo.ai comment agent
 */
@AutoConfiguration(after = { GeboMicroservicesTopologyAutoConfiguration.class,
		GeboClusterCommonsAutoConfiguration.class })
@EnableConfigurationProperties(GeboSecretsClientProperties.class)
public class GeboSecretsMicroserviceClientAutoConfiguration {

	static final String WEB_CLIENT_BEAN = "geboSecretsClientWebClient";

	/**
	 * Dedicated {@link WebClient} for the secrets calls. The bearer token is not set
	 * here but per request, on the calling thread, so the caller's identity is the
	 * one that travels.
	 */
	@Bean(name = WEB_CLIENT_BEAN)
	@ConditionalOnMissingBean(name = WEB_CLIENT_BEAN)
	public WebClient geboSecretsClientWebClient(GeboSecretsClientProperties properties) {
		WebClient.Builder builder = WebClient.builder()
				.codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(properties.getMaxInMemorySizeBytes()));
		if (properties.getHeaders() != null) {
			properties.getHeaders().forEach(builder::defaultHeader);
		}
		return builder.build();
	}

	// The IGeboCallerTokenPropagator comes from GeboClusterCommonsAutoConfiguration:
	// forwarding the caller's token (and minting a system one when there is none) is
	// how EVERY cluster client authenticates, not something specific to secrets.

	@Bean
	@ConditionalOnMissingBean(IGeboSecretsAccessService.class)
	public IGeboSecretsAccessService geboSecretsAccessServiceRestClient(
			@Qualifier(WEB_CLIENT_BEAN) WebClient geboSecretsClientWebClient,
			GeboMicroserviceUrlResolver urlResolver, IGeboCallerTokenPropagator tokenPropagator,
			ObjectMapper objectMapper, IGeboCryptingService cryptService, GeboSecretsClientProperties properties) {
		return new GeboSecretsAccessServiceRestClient(geboSecretsClientWebClient, urlResolver, tokenPropagator,
				objectMapper, cryptService, properties.getMicroserviceId(), properties.getBasePath(),
				new GeboTtlCache(properties.getCacheTtl(), properties.getCacheMaxEntries()));
	}
}
