/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.acl.client.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.util.ReflectionTestUtils;

import ai.gebo.acl.IAclAliasesDao;
import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.cluster.config.GeboClusterCommonsAutoConfiguration;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.microservices.topology.config.GeboMicroservicesTopologyAutoConfiguration;

/**
 * Where the ACL client reaches the ACL owner: through the topology, with the
 * load-balanced WebClient, or - with the topology switched off - at its
 * {@code base-url}, with a plain one.
 */
class GeboAclMicroserviceClientAutoConfigurationTest {

	private final ApplicationContextRunner runner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(GeboMicroservicesTopologyAutoConfiguration.class,
					GeboClusterCommonsAutoConfiguration.class, GeboAclMicroserviceClientAutoConfiguration.class))
			// the security implementation is optional in the cluster commons and not on
			// this module's classpath, so its propagator is replaced, as an app may do
			.withBean(IGeboCallerTokenPropagator.class, () -> mock(IGeboCallerTokenPropagator.class));

	private static GeboMicroserviceUrlResolver urlResolverOf(Object client) {
		return (GeboMicroserviceUrlResolver) ReflectionTestUtils.getField(client, "urlResolver");
	}

	@Test
	void withTheTopologyOnTheOwnerIsReachedThroughIt() {
		runner.run(context -> {
			assertThat(context).hasNotFailed()
					.hasBean(GeboAclMicroserviceClientAutoConfiguration.WEB_CLIENT_BUILDER_BEAN)
					.hasSingleBean(IAclAliasesDao.class);
			assertThat(context.getBeanFactory()
					.getBeanDefinition(GeboAclMicroserviceClientAutoConfiguration.WEB_CLIENT_BEAN)
					.getFactoryMethodName()).isEqualTo("geboAclClientWebClient");
			assertThat(urlResolverOf(context.getBean(IAclAliasesDao.class)))
					.isSameAs(context.getBean(GeboMicroserviceUrlResolver.class));
		});
	}

	@Test
	void withTheTopologyOffTheOwnerIsReachedAtTheBaseUrlWithAPlainWebClient() {
		runner.withPropertyValues("gebo.microservices.topology.enabled=false",
				"ai.gebo.acl.client.base-url=http://gebo-ai:12999").run(context -> {
					assertThat(context).hasNotFailed()
							.doesNotHaveBean(GeboAclMicroserviceClientAutoConfiguration.WEB_CLIENT_BUILDER_BEAN)
							.doesNotHaveBean(GeboMicroserviceUrlResolver.class);
					assertThat(context.getBeanFactory()
							.getBeanDefinition(GeboAclMicroserviceClientAutoConfiguration.WEB_CLIENT_BEAN)
							.getFactoryMethodName()).isEqualTo("geboAclClientDirectWebClient");
					assertThat(urlResolverOf(context.getBean(IAclAliasesDao.class))
							.baseUrlForMicroserviceId("heimdall_gebo_ai")).contains("http://gebo-ai:12999");
				});
	}

	@Test
	void withTheTopologyOffAndNoBaseUrlTheServiceDoesNotStart() {
		runner.withPropertyValues("gebo.microservices.topology.enabled=false")
				.run(context -> assertThat(context).hasFailed().getFailure().rootCause()
						.hasMessageContaining("ai.gebo.acl.client.base-url"));
	}
}
