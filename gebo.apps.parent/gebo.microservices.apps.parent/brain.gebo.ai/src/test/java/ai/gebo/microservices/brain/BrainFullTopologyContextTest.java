/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.brain;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.opensearch.testcontainers.OpenSearchContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ai.gebo.architecture.environment.EnvironmentHolder;
import ai.gebo.architecture.fulltext.service.IGFullTextSearchService;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.architecture.opensearch.service.impl.GFullTextSearchServiceImpl;
import ai.gebo.architecture.rag.support.layer.services.IGFullTextSearchDocumentsCachedDao;
import ai.gebo.architecture.graphrag.services.impl.KnowledgeGraphSearchServiceImpl;
import ai.gebo.architecture.graphrag.services.impl.Neo4jDdlRunner;

/**
 * brain in the FULL configuration: BOTH optional subsystems ON - GraphRAG against
 * a live Neo4j (the store brain shares with graphicator) and full-text search
 * against a live OpenSearch (the store brain shares with fulltextor).
 *
 * <h2>Why this is a separate class from {@link BrainContextTest}</h2>
 * <p>
 * The two are not variants of one test, they are the two supported topologies,
 * and each has to be able to fail on its own:
 * </p>
 * <ul>
 * <li>{@link BrainContextTest} - LEAN: {@code ai.gebo.neo4j.enabled=false},
 * Mongo + Qdrant only. Proves brain starts and wires correctly with the optional
 * subsystems off, and that the graph beans are then simply ABSENT.</li>
 * <li>this class - FULL: {@code ai.gebo.neo4j.enabled=true} (brain's shipped
 * default) and {@code ai.gebo.opensearch.enabled=true} (what the shared config
 * overlay sets cluster-wide) against a real Neo4j and a real OpenSearch. Proves
 * both bean sets are actually published, that {@code Neo4jDdlRunner} can run its
 * DDL end to end, and that the lexical retrieval leg is genuinely wired.</li>
 * </ul>
 * <p>
 * A {@code @SpringBootTest} pins one property set per context, so covering both
 * from one class would only ever exercise one of them. Splitting also keeps the
 * fast path fast: the lean test needs one container, this one needs two.
 * </p>
 *
 * <h2>The DDL runner is the real subject here</h2>
 * <p>
 * With GraphRAG on, {@code Neo4jDdlRunner} is an {@code ApplicationRunner}: it
 * executes {@code neo4j-graphrag-ddl/knowledge-model.ddl} against the shared
 * graph DURING STARTUP, so an unreachable or incompatible Neo4j does not degrade
 * brain, it stops it booting at all. That makes the full configuration's Neo4j a
 * hard, boot-time dependency - unlike Qdrant, whose client connects lazily - and
 * this test is what keeps that path honest. It is also why brain and graphicator
 * are the only two services whose compose entry adds {@code neo4j} to
 * {@code depends_on}.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Testcontainers
@SpringBootTest(classes = { BrainApplication.class, BrainFullTopologyContextTest.DiscoveryStubConfig.class })
class BrainFullTopologyContextTest {

	private static final String NEO4J_PASSWORD = "neo4jmaster";

	@Container
	static MongoDBContainer mongo = new MongoDBContainer("mongo:7.0").withExposedPorts(27017);

	/**
	 * The shared graph store. Same image the compose stack runs, and the same admin
	 * password brain's {@code application.yml} defaults to, so the connection under
	 * test is the one a deployment actually uses.
	 */
	@Container
	static Neo4jContainer<?> neo4j = new Neo4jContainer<>("neo4j:5").withAdminPassword(NEO4J_PASSWORD);

	/**
	 * The shared full-text store, with security ON - the same shape as the compose
	 * stack (https + admin credentials + the bundled demo certificate).
	 * <p>
	 * Security is not optional here even though the assertions are about wiring:
	 * {@code OpenSearchInitialization#openSearchTransport} calls
	 * {@code config.getPassword().toCharArray()} unconditionally, so an
	 * unauthenticated OpenSearch makes the transport bean throw an NPE rather than
	 * connect anonymously. The demo certificate's SAN does not match the container
	 * host, which is exactly what {@code ai.gebo.opensearch.noopHostnameVerifier}
	 * (default true) exists for.
	 * </p>
	 */
	@Container
	static OpenSearchContainer<?> opensearch = new OpenSearchContainer<>("opensearchproject/opensearch:2.18.0")
			.withSecurityEnabled();

	static {
		try {
			Path home = Files.createTempDirectory("brain-home");
			Path work = Files.createTempDirectory("brain-work");
			System.setProperty(EnvironmentHolder.GEBO_HOME, home.toAbsolutePath().toString());
			System.setProperty(EnvironmentHolder.GEBO_WORK_DIRECTORY, work.toAbsolutePath().toString());
		} catch (Exception e) {
			throw new IllegalStateException("Cannot prepare the brain test home", e);
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
		// The full configuration: GraphRAG on (brain's shipped default, restated
		// here so the test does not silently change meaning if the default moves)
		// pointed at the container instead of localhost:7687.
		registry.add("ai.gebo.neo4j.enabled", () -> true);
		registry.add("spring.neo4j.uri", neo4j::getBoltUrl);
		registry.add("spring.neo4j.authentication.username", () -> "neo4j");
		registry.add("spring.neo4j.authentication.password", () -> NEO4J_PASSWORD);
		// Full-text ON, pointed at the container. ai.gebo.opensearch.host/port are
		// separate properties, so the container's host:port address is split.
		registry.add("ai.gebo.opensearch.enabled", () -> true);
		registry.add("ai.gebo.opensearch.protocol", () -> "https");
		registry.add("ai.gebo.opensearch.host", () -> opensearch.getHost());
		registry.add("ai.gebo.opensearch.port", () -> opensearch.getFirstMappedPort());
		registry.add("ai.gebo.opensearch.username", opensearch::getUsername);
		registry.add("ai.gebo.opensearch.password", opensearch::getPassword);
	}

	@Autowired
	ApplicationContext context;

	/**
	 * The store sharing does not change between topologies: turning GraphRAG on
	 * must not move brain off the {@code brain-gebo} database it shares with
	 * vectorizator and graphicator.
	 */
	@Test
	void stillSharesTheBrainMongoDatabase() {
		assertThat(context.getBean(MongoTemplate.class).getDb().getName()).isEqualTo("brain-gebo");
	}

	/**
	 * The graph bean set the lean topology asserts is ABSENT must be present here -
	 * brain reads the graph graphicator writes, via
	 * {@link KnowledgeGraphSearchServiceImpl}. 27 beans are gated on the single
	 * {@code ai.gebo.neo4j.enabled} switch; this is the one brain's retrieval path
	 * actually calls.
	 */
	@Test
	void publishesGraphSearchWhenGraphRagIsEnabled() {
		assertThat(context.getBean(IKnowledgeGraphSearchService.class))
				.isInstanceOf(KnowledgeGraphSearchServiceImpl.class);
	}

	/**
	 * Reaching this assertion at all is the point: the runner already executed the
	 * knowledge-model DDL against the container during startup, because the context
	 * could not have loaded otherwise.
	 */
	@Test
	void runsTheGraphDdlAtStartup() {
		assertThat(context.getBean(Neo4jDdlRunner.class)).isNotNull();
	}

	/**
	 * The lexical retrieval leg, end to end - the assertion this whole class exists
	 * for.
	 * <p>
	 * The DAO is the bean that matters, not just the search service: it is
	 * {@code @ConditionalOnBean(IGFullTextSearchService.class)} on a plain
	 * {@code @Service}, so its registration depends on the OpenSearch client being
	 * seen first. Asserting the service alone would pass while the DAO silently
	 * failed to register, which is exactly the shape of the bug this dependency
	 * fixed: {@code GeboRagSearchConfig} takes the DAO as
	 * {@code @Autowired(required = false)}, so a missing one is not an error - it
	 * is a null that turns {@code GDocumentsSearchServiceImpl}'s
	 * {@code lexicalLegAvailable} permanently false and degrades hybrid retrieval
	 * to semantic-only with nothing logged.
	 * </p>
	 */
	@Test
	void publishesTheLexicalSearchLegWhenOpenSearchIsEnabled() {
		assertThat(context.getBean(IGFullTextSearchService.class)).isInstanceOf(GFullTextSearchServiceImpl.class);
		assertThat(context.getBeanNamesForType(IGFullTextSearchDocumentsCachedDao.class)).isNotEmpty();
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

				/**
				 * Returns one instance for every queried service, instead of an
				 * empty list.
				 * <p>
				 * This is what keeps the test fast. An empty list can never satisfy
				 * DiscoveryClientClusterTopologyProvider's "non-empty AND unchanged
				 * since the previous poll" condition, so the provider used to
				 * exhaust its whole retry budget - 24 polls, 10s apart - on the main
				 * thread before every one of these contexts could finish starting,
				 * putting ~240s on each of the four participant services' tests and
				 * most of the runtime of the whole microservices suite. One instance
				 * satisfies it on the second poll instead.
				 * <p>
				 * It is also the more faithful stub: in a deployment discovery does
				 * resolve the participants. Nothing here asserts on the member list -
				 * seedsModelsReplicationFromLiveDiscovery asserts the provider TYPE,
				 * which is what the wiring bug this test exists for would break.
				 */
				@Override
				public List<ServiceInstance> getInstances(String serviceId) {
					return List.of(new DefaultServiceInstance(serviceId + "-1", serviceId, "127.0.0.1", 0, false));
				}
			};
		}
	}
}
