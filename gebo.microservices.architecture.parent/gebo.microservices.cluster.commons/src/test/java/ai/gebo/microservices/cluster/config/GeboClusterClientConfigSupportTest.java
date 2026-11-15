/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.cluster.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;

/**
 * Where a cluster client's owner is reached: through the topology when there is
 * one, at the client's {@code base-url} when the topology is switched off.
 */
class GeboClusterClientConfigSupportTest {

	private static final String HEIMDALL = "heimdall_gebo_ai";
	private static final String PROPERTY = "ai.gebo.security.client.base-url";

	private static ObjectProvider<GeboMicroserviceUrlResolver> topologyResolver(GeboMicroserviceUrlResolver resolver) {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		if (resolver != null) {
			beanFactory.registerSingleton("geboMicroserviceUrlResolver", resolver);
		}
		return beanFactory.getBeanProvider(GeboMicroserviceUrlResolver.class);
	}

	@Test
	void withTheTopologyOnTheOwnerIsReachedThroughItAndTheBaseUrlIsIgnored() {
		GeboMicroserviceUrlResolver fromTopology = GeboMicroserviceUrlResolver
				.loadBalanced(GeboMicroservicesTopology.defaults());

		GeboMicroserviceUrlResolver resolver = GeboClusterClientConfigSupport
				.ownerUrlResolver(topologyResolver(fromTopology), HEIMDALL, "http://ignored:1", PROPERTY);

		assertThat(resolver).isSameAs(fromTopology);
		assertThat(resolver.baseUrlForMicroserviceId(HEIMDALL)).contains("http://heimdall-gebo-ai/heimdall");
	}

	@Test
	void withTheTopologyOffTheOwnerIsReachedAtTheBaseUrl() {
		GeboMicroserviceUrlResolver resolver = GeboClusterClientConfigSupport
				.ownerUrlResolver(topologyResolver(null), HEIMDALL, " http://gebo-ai:12999/ ", PROPERTY);

		assertThat(resolver.baseUrlForMicroserviceId(HEIMDALL)).contains("http://gebo-ai:12999");
		// the dotted form names the same service
		assertThat(resolver.baseUrlForMicroserviceId("heimdall.gebo.ai")).contains("http://gebo-ai:12999");
		// and nothing else is addressable: there is no topology behind it
		assertThat(resolver.baseUrlForMicroserviceId("brain_gebo_ai")).isEmpty();
	}

	@Test
	void withTheTopologyOffAndNoBaseUrlTheClientRefusesToStart() {
		assertThatThrownBy(
				() -> GeboClusterClientConfigSupport.ownerUrlResolver(topologyResolver(null), HEIMDALL, "  ", PROPERTY))
				.isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("gebo.microservices.topology.enabled=false").hasMessageContaining(PROPERTY)
				.hasMessageContaining(HEIMDALL);
	}
}
