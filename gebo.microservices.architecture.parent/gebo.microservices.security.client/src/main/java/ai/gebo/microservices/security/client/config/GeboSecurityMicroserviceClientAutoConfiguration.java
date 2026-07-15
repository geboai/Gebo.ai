/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.security.client.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.cluster.cache.GeboTtlCache;
import ai.gebo.microservices.cluster.config.GeboClusterCommonsAutoConfiguration;
import ai.gebo.microservices.security.client.RestSecurityDirectory;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.microservices.topology.config.GeboMicroservicesTopologyAutoConfiguration;
import ai.gebo.security.services.IGSecurityDirectory;

/**
 * Wires the remote security directory.
 *
 * <p>
 * <b>No property selects this.</b> A service uses the remote directory by depending
 * on this module and not on {@code gebo.security.directory.mongo} - the two are
 * never on one classpath, so the choice is stated in the pom and cannot be set
 * wrong at runtime. If both were somehow present the owner's Mongo directory wins
 * (it is ordered first) and this backs off.
 * </p>
 *
 * <p>
 * Note what is <b>not</b> here: no {@code IGSecurityService}, no
 * {@code IAclGrantedAccessorService}. Those keep their real implementations from
 * {@code gebo.architecture.security}, running the authorization algorithm locally
 * over local objects; swapping the directory underneath them is the entire proxy.
 * </p>
 *
 * Gebo.ai comment agent
 */
@AutoConfiguration(after = { GeboMicroservicesTopologyAutoConfiguration.class,
		GeboClusterCommonsAutoConfiguration.class })
@EnableConfigurationProperties(GeboSecurityClientProperties.class)
public class GeboSecurityMicroserviceClientAutoConfiguration {

	static final String WEB_CLIENT_BEAN = "geboSecurityClientWebClient";

	@Bean(name = WEB_CLIENT_BEAN)
	@ConditionalOnMissingBean(name = WEB_CLIENT_BEAN)
	public WebClient geboSecurityClientWebClient(GeboSecurityClientProperties properties) {
		WebClient.Builder builder = WebClient.builder();
		if (properties.getHeaders() != null) {
			properties.getHeaders().forEach(builder::defaultHeader);
		}
		return builder.build();
	}

	@Bean
	@ConditionalOnMissingBean(IGSecurityDirectory.class)
	public IGSecurityDirectory restSecurityDirectory(@Qualifier(WEB_CLIENT_BEAN) WebClient geboSecurityClientWebClient,
			GeboMicroserviceUrlResolver urlResolver, IGeboCallerTokenPropagator tokenPropagator,
			GeboSecurityClientProperties properties) {
		return new RestSecurityDirectory(geboSecurityClientWebClient, urlResolver, tokenPropagator,
				properties.getMicroserviceId(), properties.getBasePath(),
				new GeboTtlCache(properties.getCacheTtl(), properties.getCacheMaxEntries()));
	}
}
