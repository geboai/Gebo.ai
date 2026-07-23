/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.security.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ai.gebo.microservices.cluster.ClusterParticipantsOnlyInterceptor;
import ai.gebo.microservices.cluster.GeboClusterParticipants;
import ai.gebo.microservices.cluster.config.GeboClusterCommonsAutoConfiguration;
import ai.gebo.microservices.security.controller.SecurityDirectoryClusterController;
import ai.gebo.security.directory.mongo.MongoSecurityDirectory;
import ai.gebo.security.directory.mongo.config.GeboMongoSecurityDirectoryAutoConfiguration;
import ai.gebo.security.services.IGSecurityDirectory;

/**
 * Publishes the security directory endpoints on the service that <b>owns</b> the
 * user store (heimdall.gebo.ai).
 *
 * <p>
 * Guarded twice over, on purpose:
 * </p>
 * <ul>
 * <li>{@link ConditionalOnBean @ConditionalOnBean(GeboClusterParticipants.class)} -
 * no discovered membership, no endpoints. They are never published unguarded.</li>
 * <li>{@link ConditionalOnBean @ConditionalOnBean(MongoSecurityDirectory.class)} -
 * you may only SERVE the directory if you OWN it. A service running the remote
 * directory cannot publish these endpoints, or it would proxy heimdall back to
 * itself.</li>
 * </ul>
 *
 * <p>
 * The controller is registered here as a {@code @Bean} and is not a
 * {@code @Component}, so the {@code ai.gebo} component scan every Gebo app performs
 * cannot publish it on its own - i.e. cannot publish it <i>without</i> the guard
 * this class installs alongside it.
 * </p>
 *
 * <p>
 * {@code after} names both producers this class is {@code @ConditionalOnBean} on:
 * {@link MongoSecurityDirectory} comes from
 * {@link GeboMongoSecurityDirectoryAutoConfiguration}, a separate auto-configuration
 * (not a plain {@code @Component}), so without this ordering the
 * {@code AutoConfigurationSorter} has no constraint between the two and may run this
 * class first - the condition would then find no bean and never look again.
 * </p>
 *
 * Gebo.ai comment agent
 */
@AutoConfiguration(
		after = { GeboClusterCommonsAutoConfiguration.class, GeboMongoSecurityDirectoryAutoConfiguration.class })
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnBean({ MongoSecurityDirectory.class, GeboClusterParticipants.class })
@ConditionalOnProperty(prefix = "ai.gebo.security.cluster", name = "enabled", havingValue = "true",
		matchIfMissing = true)
@EnableConfigurationProperties(GeboSecurityClusterControllerProperties.class)
public class GeboSecurityClusterControllerAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public SecurityDirectoryClusterController securityDirectoryClusterController(IGSecurityDirectory directory) {
		return new SecurityDirectoryClusterController(directory);
	}

	/** Applies the participants guard to the directory endpoints' base path only. */
	@Bean
	public WebMvcConfigurer securityClusterParticipantsGuardConfigurer(GeboClusterParticipants participants,
			GeboSecurityClusterControllerProperties properties) {
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
