/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.secrets.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ai.gebo.microservices.cluster.ClusterParticipantsOnlyInterceptor;
import ai.gebo.microservices.cluster.GeboClusterParticipants;
import ai.gebo.microservices.cluster.config.GeboClusterCommonsAutoConfiguration;
import ai.gebo.microservices.secrets.controller.SecretsClusterController;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import tools.jackson.databind.ObjectMapper;

/**
 * Publishes the cluster-only secrets endpoints on the service that <b>hosts</b>
 * the secrets implementation (heimdall.gebo.ai).
 *
 * <p>
 * The whole configuration hangs off
 * {@link ConditionalOnBean @ConditionalOnBean(IGeboSecretsAccessService.class)}:
 * a service that merely has this jar on its classpath but does not own the
 * secrets implementation publishes nothing, so the endpoints can never appear -
 * empty-handed or worse - on the wrong microservice. Being an
 * {@link AutoConfiguration} (and not a component-scanned {@code @Configuration})
 * is what makes that condition sound: auto-configurations are evaluated after the
 * application's own beans are known.
 * </p>
 *
 * <p>
 * The guard is registered as an interceptor scoped to the controller's base path,
 * so nothing else in the hosting service is affected by it.
 * </p>
 *
 * Gebo.ai comment agent
 */
@AutoConfiguration(after = GeboClusterCommonsAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(DiscoveryClient.class)
@ConditionalOnBean({ IGeboSecretsAccessService.class, GeboClusterParticipants.class })
@ConditionalOnProperty(prefix = "ai.gebo.secrets.cluster", name = "enabled", havingValue = "true",
		matchIfMissing = true)
@EnableConfigurationProperties(GeboSecretsClusterControllerProperties.class)
public class GeboSecretsClusterControllerAutoConfiguration {

	// GeboClusterParticipants (the live membership these endpoints are restricted to)
	// comes from GeboClusterCommonsAutoConfiguration - it is shared with every other
	// cluster-internal controller, and @ConditionalOnBean above means that if discovery
	// is absent and no participants bean exists, these endpoints are not published at
	// all rather than published unguarded.

	@Bean
	@ConditionalOnMissingBean
	public SecretsClusterController secretsClusterController(IGeboSecretsAccessService secretsService,
			ObjectMapper objectMapper) {
		return new SecretsClusterController(secretsService, objectMapper);
	}

	/**
	 * Applies {@link ClusterParticipantsOnlyInterceptor} to the controller's base
	 * path only.
	 */
	@Bean
	public WebMvcConfigurer secretsClusterParticipantsGuardConfigurer(GeboClusterParticipants participants,
			GeboSecretsClusterControllerProperties properties) {
		String pathPattern = "/" + trimSlashes(properties.getBasePath()) + "/**";
		return new WebMvcConfigurer() {
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(new ClusterParticipantsOnlyInterceptor(participants))
						.addPathPatterns(pathPattern);
			}
		};
	}

	private static String trimSlashes(String path) {
		String trimmed = path == null ? "" : path.trim();
		while (trimmed.startsWith("/")) {
			trimmed = trimmed.substring(1);
		}
		while (trimmed.endsWith("/")) {
			trimmed = trimmed.substring(0, trimmed.length() - 1);
		}
		return trimmed;
	}
}
