/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.security.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * The security chain of the installation-id path alone, ahead of the platform's
 * one: no bearer processing (a microservice asking for the id has no platform
 * token yet, and its {@code GeboAuthorization} token is not one), no session, no
 * CSRF. {@link InstallationClusterController} authenticates the request itself.
 * Every other path keeps the platform's chain.
 */
@Configuration
@ConditionalOnProperty(prefix = "ai.gebo.security.cluster", name = "enabled", havingValue = "true",
		matchIfMissing = true)
public class InstallationClusterSecurityConfig {

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain installationClusterSecurityFilterChain(HttpSecurity http,
			@Value("${" + InstallationClusterController.BASE_PATH_PROPERTY + ":"
					+ InstallationClusterController.DEFAULT_BASE_PATH + "}") String basePath)
			throws Exception {
		String path = "/" + basePath.replaceAll("^/+", "").replaceAll("/+$", "") + "/**";
		return http.securityMatcher(path).csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll()).build();
	}
}
