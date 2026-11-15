/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.security.client.config;

import ai.gebo.security.services.IGInstallationIdProvider;

import ai.gebo.security.config.GeboSecurityConfig;

import ai.gebo.microservices.security.client.RestInstallationIdProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.webclient.WebClientCustomizer;
import org.springframework.context.annotation.Primary;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.cluster.cache.GeboTtlCache;
import ai.gebo.microservices.cluster.config.GeboClusterClientConfigSupport;
import ai.gebo.microservices.cluster.config.GeboClusterCommonsAutoConfiguration;
import ai.gebo.microservices.security.client.RestSecurityDirectory;
import ai.gebo.microservices.security.client.RestUsersAdminService;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.microservices.topology.config.GeboMicroservicesTopologyAutoConfiguration;
import ai.gebo.microservices.topology.config.GeboMicroservicesTopologyProperties;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityDirectory;
import ai.gebo.security.services.IGUsersAdminService;

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
 * <p>
 * Heimdall is reached through the topology, with a load-balanced WebClient, unless
 * the topology is switched off ({@code gebo.microservices.topology.enabled=false}):
 * then at {@code ai.gebo.security.client.base-url}, with a plain one (see
 * {@link GeboClusterClientConfigSupport}).
 * </p>
 *
 * Gebo.ai comment agent
 */
@AutoConfiguration(after = { GeboMicroservicesTopologyAutoConfiguration.class,
		GeboClusterCommonsAutoConfiguration.class })
@EnableConfigurationProperties(GeboSecurityClientProperties.class)
public class GeboSecurityMicroserviceClientAutoConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeboSecurityMicroserviceClientAutoConfiguration.class);

	static final String WEB_CLIENT_BUILDER_BEAN = "geboSecurityClientLbWebClientBuilder";
	static final String WEB_CLIENT_BEAN = "geboSecurityClientWebClient";
	/** The property heimdall's address is bound from when the topology is off. */
	static final String BASE_URL_PROPERTY = GeboSecurityClientProperties.PREFIX + ".base-url";

	/**
	 * Heimdall's url resolver, chosen once for every client bean below (and logged
	 * once): this configuration is a singleton, its bean methods run on startup.
	 */
	private GeboMicroserviceUrlResolver heimdallUrlResolver;

	/**
	 * A dedicated {@link LoadBalanced @LoadBalanced} builder so calls to heimdall's
	 * cluster surface go through Spring Cloud LoadBalancer (resolving the topology's
	 * discovery service-id, e.g. {@code heimdall-gebo-ai}, to a live instance)
	 * instead of a literal DNS lookup, which fails: that hostname is a Eureka
	 * service-id, not a real one. Mirrors the same fix already applied in
	 * {@code GeboSearchServicesClientsAutoConfiguration}. Only with the topology on:
	 * without it there are no service-ids to resolve.
	 */
	@Bean(name = WEB_CLIENT_BUILDER_BEAN)
	@ConditionalOnMissingBean(name = WEB_CLIENT_BUILDER_BEAN)
	@ConditionalOnProperty(prefix = GeboMicroservicesTopologyProperties.PREFIX, name = GeboMicroservicesTopologyProperties.ENABLED, havingValue = "true", matchIfMissing = true)
	@LoadBalanced
	public WebClient.Builder geboSecurityClientLbWebClientBuilder(ObjectProvider<WebClientCustomizer> customizers) {
		return GeboClusterClientConfigSupport.customizedWebClientBuilder(customizers);
	}

	@Bean(name = WEB_CLIENT_BEAN)
	@ConditionalOnMissingBean(name = WEB_CLIENT_BEAN)
	@ConditionalOnProperty(prefix = GeboMicroservicesTopologyProperties.PREFIX, name = GeboMicroservicesTopologyProperties.ENABLED, havingValue = "true", matchIfMissing = true)
	public WebClient geboSecurityClientWebClient(@Qualifier(WEB_CLIENT_BUILDER_BEAN) WebClient.Builder builder,
			GeboSecurityClientProperties properties) {
		return withHeaders(builder, properties).build();
	}

	/**
	 * With the topology off, heimdall's {@code base-url} is a real address, called
	 * with a plain WebClient: a load-balanced one would look its host up as a
	 * discovery service-id, and find none.
	 */
	@Bean(name = WEB_CLIENT_BEAN)
	@ConditionalOnMissingBean(name = WEB_CLIENT_BEAN)
	@ConditionalOnProperty(prefix = GeboMicroservicesTopologyProperties.PREFIX, name = GeboMicroservicesTopologyProperties.ENABLED, havingValue = "false")
	public WebClient geboSecurityClientDirectWebClient(ObjectProvider<WebClientCustomizer> customizers,
			GeboSecurityClientProperties properties) {
		LOGGER.debug("Security client: a plain WebClient to heimdall, the topology being switched off");
		return withHeaders(GeboClusterClientConfigSupport.customizedWebClientBuilder(customizers), properties).build();
	}

	private static WebClient.Builder withHeaders(WebClient.Builder builder, GeboSecurityClientProperties properties) {
		if (properties.getHeaders() != null) {
			properties.getHeaders().forEach(builder::defaultHeader);
		}
		return builder;
	}

	private synchronized GeboMicroserviceUrlResolver heimdallUrlResolver(
			ObjectProvider<GeboMicroserviceUrlResolver> topologyResolver, GeboSecurityClientProperties properties) {
		if (heimdallUrlResolver == null) {
			heimdallUrlResolver = GeboClusterClientConfigSupport.ownerUrlResolver(topologyResolver,
					properties.getMicroserviceId(), properties.getBaseUrl(), BASE_URL_PROPERTY);
		}
		return heimdallUrlResolver;
	}

	@Bean
	@ConditionalOnMissingBean(IGSecurityDirectory.class)
	public IGSecurityDirectory restSecurityDirectory(@Qualifier(WEB_CLIENT_BEAN) WebClient geboSecurityClientWebClient,
			ObjectProvider<GeboMicroserviceUrlResolver> topologyResolver, IGeboCallerTokenPropagator tokenPropagator,
			GeboSecurityClientProperties properties, IGSecurityAuditLoggerService securityAuditLoggerService) {
		GeboMicroserviceUrlResolver urlResolver = heimdallUrlResolver(topologyResolver, properties);
		return new RestSecurityDirectory(geboSecurityClientWebClient, urlResolver, tokenPropagator,
				properties.getMicroserviceId(), properties.getBasePath(),
				new GeboTtlCache(properties.getCacheTtl(), properties.getCacheMaxEntries()),
				securityAuditLoggerService);
	}

	/**
	 * Same mechanism as {@link #restSecurityDirectory}: a service that packages this
	 * module (does not own the user store) gets the remote, topology-aware
	 * {@link IGUsersAdminService}; a service that packages {@code
	 * gebo.security.directory.mongo} instead gets its local, Mongo-backed one and this
	 * bean backs off.
	 */
	/**
	 * A microservice that does not own the installation record asks heimdall's
	 * installation controller for it: this provider takes precedence over the
	 * database-backed one the security module carries. Heimdall, which owns the
	 * record, does not include this module.
	 */
	@Bean
	@Primary
	public IGInstallationIdProvider restInstallationIdProvider(
			@Qualifier(WEB_CLIENT_BEAN) WebClient geboSecurityClientWebClient,
			ObjectProvider<GeboMicroserviceUrlResolver> topologyResolver, GeboSecurityConfig securityConfig,
			GeboSecurityClientProperties properties,
			@Value("${spring.application.name:gebo-microservice}") String applicationName) {
		GeboMicroserviceUrlResolver urlResolver = heimdallUrlResolver(topologyResolver, properties);
		return new RestInstallationIdProvider(geboSecurityClientWebClient, urlResolver, securityConfig,
				properties.getMicroserviceId(), properties.getInstallationBasePath(), applicationName);
	}

	@Bean
	@ConditionalOnMissingBean(IGUsersAdminService.class)
	public IGUsersAdminService restUsersAdminService(@Qualifier(WEB_CLIENT_BEAN) WebClient geboSecurityClientWebClient,
			ObjectProvider<GeboMicroserviceUrlResolver> topologyResolver, IGeboCallerTokenPropagator tokenPropagator,
			GeboSecurityClientProperties properties, IGSecurityAuditLoggerService securityAuditLoggerService) {
		GeboMicroserviceUrlResolver urlResolver = heimdallUrlResolver(topologyResolver, properties);
		return new RestUsersAdminService(geboSecurityClientWebClient, urlResolver, tokenPropagator,
				properties.getMicroserviceId(), properties.getBasePath(), securityAuditLoggerService);
	}
}
