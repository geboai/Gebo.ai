/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.acl.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ai.gebo.acl.IAclAliasesDao;
import ai.gebo.acl.mongo.AclAliasesDaoImpl;
import ai.gebo.microservices.acl.controller.AclAliasesClusterController;
import ai.gebo.microservices.cluster.ClusterParticipantsOnlyInterceptor;
import ai.gebo.microservices.cluster.GeboClusterParticipants;
import ai.gebo.microservices.cluster.config.GeboClusterCommonsAutoConfiguration;

/**
 * Publishes the ACL endpoints on the service that <b>owns</b> the ACL store.
 *
 * <p>
 * Conditional on {@link AclAliasesDaoImpl} - the Mongo one - and not merely on
 * {@link IAclAliasesDao}: you may only SERVE the ACL store if you OWN it. A service
 * running the REST client cannot publish these endpoints, or it would proxy the
 * owner back to itself. And no participants bean (no discovered membership) means
 * no endpoints at all, rather than endpoints without a guard.
 * </p>
 *
 * Gebo.ai comment agent
 */
@AutoConfiguration(after = GeboClusterCommonsAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnBean({ AclAliasesDaoImpl.class, GeboClusterParticipants.class })
@ConditionalOnProperty(prefix = "ai.gebo.acl.cluster", name = "enabled", havingValue = "true",
		matchIfMissing = true)
@EnableConfigurationProperties(GeboAclClusterControllerProperties.class)
public class GeboAclClusterControllerAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public AclAliasesClusterController aclAliasesClusterController(IAclAliasesDao aliasesDao) {
		return new AclAliasesClusterController(aliasesDao);
	}

	/** Applies the participants guard to the ACL endpoints' base path only. */
	@Bean
	public WebMvcConfigurer aclClusterParticipantsGuardConfigurer(GeboClusterParticipants participants,
			GeboAclClusterControllerProperties properties) {
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
