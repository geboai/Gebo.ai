/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.security.client.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.util.ReflectionTestUtils;

import ai.gebo.microservices.cluster.config.GeboClusterCommonsAutoConfiguration;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.microservices.topology.config.GeboMicroservicesTopologyAutoConfiguration;
import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.services.IGInstallationIdProvider;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityDirectory;
import ai.gebo.security.services.IGUsersAdminService;

/**
 * Where the security client reaches heimdall: through the topology, with the
 * load-balanced WebClient, or - with the topology switched off - at its
 * {@code base-url}, with a plain one.
 */
class GeboSecurityMicroserviceClientAutoConfigurationTest {

	private static final String HEIMDALL = "heimdall_gebo_ai";

	private final ApplicationContextRunner runner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(GeboMicroservicesTopologyAutoConfiguration.class,
					GeboClusterCommonsAutoConfiguration.class, GeboSecurityMicroserviceClientAutoConfiguration.class))
			.withBean(GeboSecurityConfig.class, () -> mock(GeboSecurityConfig.class))
			.withBean(IGSecurityAuditLoggerService.class, () -> mock(IGSecurityAuditLoggerService.class));

	private static GeboMicroserviceUrlResolver urlResolverOf(Object client) {
		return (GeboMicroserviceUrlResolver) ReflectionTestUtils.getField(client, "urlResolver");
	}

	@Test
	void withTheTopologyOnHeimdallIsReachedThroughIt() {
		runner.run(context -> {
			assertThat(context).hasNotFailed()
					.hasBean(GeboSecurityMicroserviceClientAutoConfiguration.WEB_CLIENT_BUILDER_BEAN)
					.hasBean(GeboSecurityMicroserviceClientAutoConfiguration.WEB_CLIENT_BEAN)
					.hasSingleBean(IGSecurityDirectory.class).hasSingleBean(IGUsersAdminService.class);
			assertThat(context.getBeanFactory()
					.getBeanDefinition(GeboSecurityMicroserviceClientAutoConfiguration.WEB_CLIENT_BEAN)
					.getFactoryMethodName()).isEqualTo("geboSecurityClientWebClient");
			GeboMicroserviceUrlResolver topology = context.getBean(GeboMicroserviceUrlResolver.class);
			assertThat(urlResolverOf(context.getBean(IGSecurityDirectory.class))).isSameAs(topology);
			assertThat(urlResolverOf(context.getBean(IGUsersAdminService.class))).isSameAs(topology);
		});
	}

	@Test
	void withTheTopologyOffHeimdallIsReachedAtTheBaseUrlWithAPlainWebClient() {
		runner.withPropertyValues("gebo.microservices.topology.enabled=false",
				"ai.gebo.security.client.base-url=http://gebo-ai:12999").run(context -> {
					assertThat(context).hasNotFailed()
							.doesNotHaveBean(GeboSecurityMicroserviceClientAutoConfiguration.WEB_CLIENT_BUILDER_BEAN)
							.hasBean(GeboSecurityMicroserviceClientAutoConfiguration.WEB_CLIENT_BEAN)
							.doesNotHaveBean(GeboMicroserviceUrlResolver.class);
					assertThat(context.getBeanFactory()
							.getBeanDefinition(GeboSecurityMicroserviceClientAutoConfiguration.WEB_CLIENT_BEAN)
							.getFactoryMethodName()).isEqualTo("geboSecurityClientDirectWebClient");
					for (Object client : new Object[] { context.getBean(IGSecurityDirectory.class),
							context.getBean(IGUsersAdminService.class),
							context.getBean(IGInstallationIdProvider.class) }) {
						assertThat(urlResolverOf(client).baseUrlForMicroserviceId(HEIMDALL))
								.contains("http://gebo-ai:12999");
					}
				});
	}

	@Test
	void withTheTopologyOffAndNoBaseUrlTheServiceDoesNotStart() {
		runner.withPropertyValues("gebo.microservices.topology.enabled=false")
				.run(context -> assertThat(context).hasFailed().getFailure().rootCause()
						.hasMessageContaining("ai.gebo.security.client.base-url"));
	}
}
