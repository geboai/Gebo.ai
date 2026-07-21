/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.graphicator;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ai.gebo.architecture.environment.EnvironmentHolder;
import ai.gebo.microservices.acl.client.RestAclAliasesDao;
import ai.gebo.microservices.security.client.RestSecurityDirectory;
import ai.gebo.microservices.secrets.client.GeboSecretsAccessServiceRestClient;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.microservices.models.replication.DiscoveryClientClusterTopologyProvider;
import ai.gebo.architecture.hazelcast.IGModelsReplicationClusterTopologyProvider;
import ai.gebo.acl.IAclAliasesDao;
import ai.gebo.security.services.IGSecurityDirectory;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

/**
 * graphicator actually starts, and it resolves the secrets/security/ACL
 * directories through the REMOTE (heimdall) clients rather than any local
 * implementation, and correctly seeds its models-replication cache membership
 * from LIVE discovery rather than the static-name fallback.
 *
 * <h2>Why this test exists</h2>
 * <p>
 * Like every non-heimdall microservice, GraphicatorApplication's
 * {@code @ComponentScan(basePackages = "ai.gebo")} drops
 * {@code @SpringBootApplication}'s default exclude filters unless explicitly
 * reinstated, which would silently defeat {@code @AutoConfigureAfter} ordering.
 * graphicator is the clearest case: its own
 * {@code ModelsReplicationClusterAutoConfiguration} publishes the
 * discovery-backed {@code IGModelsReplicationClusterTopologyProvider} only
 * {@code @ConditionalOnBean(DiscoveryClient.class)} - exactly the pattern that
 * silently produced the WRONG (name-based fallback) provider in heimdall before
 * the fix, with no error, just a less-accurate cluster view.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Testcontainers
@SpringBootTest(classes = { GraphicatorApplication.class, GraphicatorContextTest.DiscoveryStubConfig.class })
class GraphicatorContextTest {

	@Container
	static MongoDBContainer mongo = new MongoDBContainer("mongo:7.0").withExposedPorts(27017);

	static {
		try {
			Path home = Files.createTempDirectory("graphicator-home");
			Path work = Files.createTempDirectory("graphicator-work");
			System.setProperty(EnvironmentHolder.GEBO_HOME, home.toAbsolutePath().toString());
			System.setProperty(EnvironmentHolder.GEBO_WORK_DIRECTORY, work.toAbsolutePath().toString());
		} catch (Exception e) {
			throw new IllegalStateException("Cannot prepare the graphicator test home", e);
		}
	}

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.host", mongo::getHost);
		registry.add("spring.data.mongodb.port", mongo::getFirstMappedPort);
		registry.add("ai.gebo.mongodb.enabled", () -> true);
		registry.add("ai.gebo.mongodb.connectionString", mongo::getConnectionString);
		registry.add("eureka.client.enabled", () -> false);
		registry.add("eureka.client.register-with-eureka", () -> false);
		registry.add("eureka.client.fetch-registry", () -> false);
	}

	@Autowired
	ApplicationContext context;

	/**
	 * The remote directory/store clients: graphicator owns none of secrets, the
	 * security directory or the ACL store, so all three must resolve to their REST
	 * client implementation, never a local one it has no business creating.
	 */
	@Test
	void resolvesRemoteClusterClients() {
		assertThat(context.getBean(IGeboSecretsAccessService.class))
				.isInstanceOf(GeboSecretsAccessServiceRestClient.class);
		assertThat(context.getBean(IGSecurityDirectory.class)).isInstanceOf(RestSecurityDirectory.class);
		assertThat(context.getBean(IAclAliasesDao.class)).isInstanceOf(RestAclAliasesDao.class);
	}

	/** The topology url resolver these clients depend on for ordering. */
	@Test
	void publishesTheUrlResolver() {
		assertThat(context.getBean(GeboMicroserviceUrlResolver.class)).isNotNull();
	}

	/**
	 * graphicator is a default models-replication participant, and a
	 * {@link DiscoveryClient} is available (a stub here, Eureka's in production),
	 * so the topology provider must be the discovery-backed one - never the
	 * static-name fallback that only exists for a deployment with no discovery.
	 */
	@Test
	void seedsModelsReplicationFromLiveDiscovery() {
		assertThat(context.getBean(IGModelsReplicationClusterTopologyProvider.class))
				.isInstanceOf(DiscoveryClientClusterTopologyProvider.class);
	}

	@TestConfiguration
	static class DiscoveryStubConfig {

		@Bean
		DiscoveryClient discoveryClient() {
			return new DiscoveryClient() {

				@Override
				public String description() {
					return "test";
				}

				@Override
				public List<String> getServices() {
					return List.of();
				}

				@Override
				public List<ServiceInstance> getInstances(String serviceId) {
					return List.of();
				}
			};
		}
	}
}
