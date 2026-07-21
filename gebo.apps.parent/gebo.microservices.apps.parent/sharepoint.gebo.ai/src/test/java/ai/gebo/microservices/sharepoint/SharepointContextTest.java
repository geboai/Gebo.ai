/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.sharepoint;

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
import ai.gebo.acl.IAclAliasesDao;
import ai.gebo.security.services.IGSecurityDirectory;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;

/**
 * sharepoint actually starts, and it resolves the secrets/security/ACL
 * directories through the REMOTE (heimdall) clients rather than any local
 * implementation - this service owns none of the three stores.
 *
 * <h2>Why this test exists</h2>
 * <p>
 * Like every non-heimdall microservice, SharepointApplication's {@code Application} class
 * restates {@code @ComponentScan(basePackages = "ai.gebo")} directly, which drops
 * {@code @SpringBootApplication}'s default exclude filters unless explicitly
 * reinstated. Without {@code AutoConfigurationExcludeFilter}, component-scanning
 * would pick up every {@code @AutoConfiguration} class under {@code ai.gebo} as
 * an ordinary bean, in classpath order, defeating {@code @AutoConfigureAfter}
 * ordering for the client auto-configurations this service depends on
 * (secrets/security/acl.client, both requiring {@code GeboMicroserviceUrlResolver}
 * from the topology auto-configuration).
 * </p>
 *
 * Gebo.ai comment agent
 */
@Testcontainers
@SpringBootTest(classes = { SharepointApplication.class, SharepointContextTest.DiscoveryStubConfig.class })
class SharepointContextTest {

	@Container
	static MongoDBContainer mongo = new MongoDBContainer("mongo:7.0").withExposedPorts(27017);

	static {
		try {
			Path home = Files.createTempDirectory("sharepoint-home");
			Path work = Files.createTempDirectory("sharepoint-work");
			System.setProperty(EnvironmentHolder.GEBO_HOME, home.toAbsolutePath().toString());
			System.setProperty(EnvironmentHolder.GEBO_WORK_DIRECTORY, work.toAbsolutePath().toString());
		} catch (Exception e) {
			throw new IllegalStateException("Cannot prepare the sharepoint test home", e);
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
	 * The remote directory/store clients: sharepoint owns none of secrets, the
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

	/** sharepoint's own content-handler surface still comes up alongside all of the above. */
	@Test
	void publishesItsOwnContentHandler() {
		assertThat(context.getBeansOfType(IGContentManagementSystemHandler.class)).isNotEmpty();
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
