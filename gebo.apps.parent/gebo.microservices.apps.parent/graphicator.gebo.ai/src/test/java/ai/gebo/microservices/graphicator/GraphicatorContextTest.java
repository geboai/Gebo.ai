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
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ai.gebo.architecture.environment.EnvironmentHolder;
import ai.gebo.architecture.graphrag.services.impl.Neo4jDdlRunner;
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
 * <h2>The stores, and why Neo4j is a CONTAINER here and not a switch</h2>
 * <p>
 * graphicator shares both of its stores, owning neither:
 * </p>
 * <ul>
 * <li><b>Mongo</b> - database {@code brain-gebo}, shared with brain and
 * vectorizator: it writes graph data against the knowledge base brain owns.</li>
 * <li><b>Neo4j</b> - shared with brain, which READS the graph graphicator
 * writes.</li>
 * </ul>
 * <p>
 * GraphRAG is optional deployment-wide ({@code ai.gebo.neo4j.enabled}), and
 * {@link ai.gebo.microservices.brain.BrainContextTest} covers brain with it OFF -
 * because brain has a whole job left without a graph. graphicator does not:
 * turning GraphRAG off does not make graphicator leaner, it makes graphicator
 * pointless, so the lean topology is the one where this service is simply NOT
 * DEPLOYED. There is therefore only one topology worth testing here, the full
 * one, and it needs a real graph store - so this test brings one up rather than
 * switching the subsystem off.
 * </p>
 * <p>
 * It genuinely has to be live: with GraphRAG on, {@link Neo4jDdlRunner} executes
 * the knowledge-model DDL as an {@code ApplicationRunner} DURING STARTUP, so an
 * unreachable Neo4j does not degrade graphicator, it stops the context loading.
 * That is also why graphicator's compose entry is one of only two that add
 * {@code neo4j} to {@code depends_on}.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Testcontainers
@SpringBootTest(classes = { GraphicatorApplication.class, GraphicatorContextTest.DiscoveryStubConfig.class })
class GraphicatorContextTest {

	private static final String NEO4J_PASSWORD = "neo4jmaster";

	@Container
	static MongoDBContainer mongo = new MongoDBContainer("mongo:7.0").withExposedPorts(27017);

	/**
	 * The shared graph store - same image the compose stack runs, same admin
	 * password graphicator's {@code application.yml} defaults to, so the connection
	 * under test is the one a deployment actually uses.
	 */
	@Container
	static Neo4jContainer<?> neo4j = new Neo4jContainer<>("neo4j:5").withAdminPassword(NEO4J_PASSWORD);

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
		// GraphRAG on (graphicator's shipped default, restated so this test does not
		// silently change meaning if the default moves) pointed at the container
		// instead of the localhost:7687 the application.yml falls back to.
		registry.add("ai.gebo.neo4j.enabled", () -> true);
		registry.add("spring.neo4j.uri", neo4j::getBoltUrl);
		registry.add("spring.neo4j.authentication.username", () -> "neo4j");
		registry.add("spring.neo4j.authentication.password", () -> NEO4J_PASSWORD);
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

	/**
	 * Reaching this assertion at all is the point: the runner already executed the
	 * knowledge-model DDL against the container during startup, because the context
	 * could not have loaded otherwise. This is the write side of the graph brain
	 * reads.
	 */
	@Test
	void runsTheGraphDdlAtStartup() {
		assertThat(context.getBean(Neo4jDdlRunner.class)).isNotNull();
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
