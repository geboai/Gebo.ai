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
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ai.gebo.architecture.environment.EnvironmentHolder;
import ai.gebo.architecture.fulltext.service.IGFullTextSearchService;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.architecture.rag.support.layer.services.IGFullTextSearchDocumentsCachedDao;
import ai.gebo.microservices.acl.client.RestAclAliasesDao;
import ai.gebo.microservices.security.client.RestSecurityDirectory;
import ai.gebo.microservices.secrets.client.GeboSecretsAccessServiceRestClient;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.microservices.models.replication.DiscoveryClientClusterTopologyProvider;
import ai.gebo.architecture.hazelcast.IGModelsReplicationClusterTopologyProvider;
import ai.gebo.microservices.searchservices.client.JiraSearchServiceRestClient;
import ai.gebo.ragsystem.vectorstores.config.GeboAIVectorStoreConfig;
import ai.gebo.acl.IAclAliasesDao;
import ai.gebo.security.services.IGSecurityDirectory;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

/**
 * brain in the LEAN deployment topology: Mongo + Qdrant only, GraphRAG off.
 *
 * <h2>The topology this test pins</h2>
 * <p>
 * brain does not own a store of its own - it shares each one with the service
 * that writes into it:
 * </p>
 * <ul>
 * <li><b>Mongo</b> - database {@code brain-gebo}, shared with vectorizator and
 * graphicator: one knowledge base, one store (see the databaseName note in
 * brain's {@code application.yml}). Every OTHER service has its own
 * {@code <service>-gebo} database, fulltextor included.</li>
 * <li><b>Qdrant</b> - shared with vectorizator, which writes the embeddings
 * brain retrieves. Not optional: it is the vector store the RAG pipeline reads.</li>
 * <li><b>Neo4j</b> - shared with graphicator, which writes the graph brain
 * queries. OPTIONAL: gated on {@code ai.gebo.neo4j.enabled}.</li>
 * <li><b>OpenSearch</b> - shared with fulltextor, which indexes the chunks
 * brain's lexical retrieval leg searches. Both hit the same {@code kb_chunks}
 * index on the same host. OPTIONAL: gated on {@code ai.gebo.opensearch.enabled}.</li>
 * </ul>
 * <p>
 * This class covers the <b>lean</b> topology - brain + vectorizator over Mongo
 * and Qdrant, with BOTH optional subsystems (GraphRAG and full-text) off - which
 * is a supported deployment, so a Mongo container is genuinely all brain needs
 * to start.
 * {@link BrainFullTopologyContextTest} covers the FULL configuration, where
 * GraphRAG is on and the shared Neo4j is live.
 * </p>
 * <p>
 * Setting {@code ai.gebo.neo4j.enabled=false} here is the point of the test, not
 * a convenience: brain's own {@code application.yml} ships it {@code true}, and
 * with it true {@code Neo4jDdlRunner} runs its DDL as an {@code ApplicationRunner}
 * at startup, so the context FAILS TO LOAD unless a real Neo4j answers on bolt.
 * Turning it off is what makes this the lean topology rather than a full-topology
 * test with a missing dependency.
 * </p>
 *
 * <h2>Why the wiring assertions exist</h2>
 * <p>
 * Like every non-heimdall microservice, {@code BrainApplication}'s
 * {@code @ComponentScan(basePackages = "ai.gebo")} drops
 * {@code @SpringBootApplication}'s default exclude filters unless explicitly
 * reinstated, which would silently defeat {@code @AutoConfigureAfter} ordering.
 * brain is the clearest case: its own
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
@SpringBootTest(classes = { BrainApplication.class, BrainContextTest.DiscoveryStubConfig.class })
class BrainContextTest {

	@Container
	static MongoDBContainer mongo = new MongoDBContainer("mongo:7.0").withExposedPorts(27017);

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
		// THE lean-topology switches - see the class javadoc. neo4j overrides the
		// `true` baked into brain's application.yml; opensearch is already false by
		// default (OpenSearchConfig) but is stated here so BOTH optional subsystems
		// are switched off explicitly and this test cannot start depending on a
		// default that moves.
		registry.add("ai.gebo.neo4j.enabled", () -> false);
		registry.add("ai.gebo.opensearch.enabled", () -> false);
	}

	@Autowired
	ApplicationContext context;

	/**
	 * The remote directory/store clients: brain owns none of secrets, the security
	 * directory or the ACL store, so all three must resolve to their REST client
	 * implementation, never a local one it has no business creating.
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
	 * brain is a default models-replication participant, and a
	 * {@link DiscoveryClient} is available (a stub here, Eureka's in production),
	 * so the topology provider must be the discovery-backed one - never the
	 * static-name fallback that only exists for a deployment with no discovery.
	 */
	@Test
	void seedsModelsReplicationFromLiveDiscovery() {
		assertThat(context.getBean(IGModelsReplicationClusterTopologyProvider.class))
				.isInstanceOf(DiscoveryClientClusterTopologyProvider.class);
	}

	/** The search-service clients brain's agents reach out to. */
	@Test
	void publishesSearchServiceClients() {
		assertThat(context.getBean(JiraSearchServiceRestClient.class)).isNotNull();
	}

	/**
	 * The SHARED document store. brain, vectorizator and graphicator are one
	 * workflow group over one database: the vectorizator writes embeddings and the
	 * graphicator writes graph data against the knowledge base brain owns, so a
	 * rename here silently splits a document from the vectors derived from it.
	 * Asserted on the live {@link MongoTemplate} rather than on the property, so it
	 * is the database brain actually TALKS to that is pinned.
	 */
	@Test
	void sharesTheBrainMongoDatabaseWithVectorizatorAndGraphicator() {
		assertThat(context.getBean(MongoTemplate.class).getDb().getName()).isEqualTo("brain-gebo");
	}

	/**
	 * The SHARED vector store: vectorizator writes the embeddings, brain retrieves
	 * them, and both resolve the same Qdrant from the shared config overlay. Qdrant
	 * is not one of the optional subsystems - it is the store the RAG pipeline reads.
	 */
	@Test
	void usesTheSharedQdrantVectorStore() {
		assertThat(context.getBean(GeboAIVectorStoreConfig.class).getUse()).isEqualTo("QDRANT");
	}

	/**
	 * GraphRAG OFF is a supported deployment, not a degraded one: with
	 * {@code ai.gebo.neo4j.enabled=false} the whole graph bean set must simply not
	 * exist - no failed bean, no half-wired service. The counterpart assertion, that
	 * it IS published when the switch is on, lives in
	 * {@link BrainFullTopologyContextTest}.
	 */
	@Test
	void hasNoGraphBeansWhenGraphRagIsDisabled() {
		assertThat(context.getBeanNamesForType(IKnowledgeGraphSearchService.class)).isEmpty();
	}

	/**
	 * Full-text search OFF is a supported deployment, and this is what "off"
	 * concretely means end to end.
	 * <p>
	 * With {@code ai.gebo.opensearch.enabled=false} no {@link IGFullTextSearchService}
	 * is published, so {@code FullTextSearchDocumentsCachedDaoImpl} - which is
	 * {@code @ConditionalOnBean(IGFullTextSearchService.class)} - is not registered
	 * either. {@code GeboRagSearchConfig} accepts that: it takes the DAO as
	 * {@code @Autowired(required = false)} and stores null, and
	 * {@code GDocumentsSearchServiceImpl} then computes
	 * {@code lexicalLegAvailable = fullTextSearch != null} as false and runs
	 * semantic-only. Asserting BOTH beans are absent is what pins that chain: the
	 * DAO is the bean the retrieval path actually reads, and it is the one that
	 * used to be missing for the wrong reason - brain had no OpenSearch client at
	 * all, so the lexical leg was dead even with the subsystem enabled.
	 * </p>
	 */
	@Test
	void hasNoFullTextSearchWhenOpenSearchIsDisabled() {
		assertThat(context.getBeanNamesForType(IGFullTextSearchService.class)).isEmpty();
		assertThat(context.getBeanNamesForType(IGFullTextSearchDocumentsCachedDao.class)).isEmpty();
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
