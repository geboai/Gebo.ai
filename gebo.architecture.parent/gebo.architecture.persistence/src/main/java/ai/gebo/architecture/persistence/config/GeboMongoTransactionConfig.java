/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.persistence.config;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Registers the transaction manager the MongoDB side of Gebo.ai uses.
 *
 * <h2>The problem this fixes</h2> Gebo.ai declared NO transaction manager of its
 * own. The only one in the monolith's context came from Spring Boot's Neo4j
 * auto-configuration, which registers a {@code Neo4jTransactionManager} under the
 * conventional name {@code transactionManager} as soon as the driver is on the
 * classpath. So every bare {@code @Transactional} on a MONGO component was
 * opening a NEO4J session:
 *
 * <ul>
 * <li>with the graph stack running it silently wrapped a Mongo write in a Neo4j
 * transaction that guaranteed nothing;</li>
 * <li>with {@code ai.gebo.neo4j.enabled=false} - the single-dependency
 * installation - it failed outright, and
 * {@code GeboSystemUserAclInitializer} could not allocate the ACL aliases of the
 * platform's own system identity at startup.</li>
 * </ul>
 *
 * <h2>Why the bean is not a default autowire candidate</h2> The Neo4j manager is
 * named {@code transactionManager} and the graph DAOs rely on a bare
 * {@code @Transactional} resolving to it. Adding a second
 * {@link PlatformTransactionManager} as an ordinary bean would make that
 * resolution ambiguous and break the whole graph stack with a
 * {@code NoUniqueBeanDefinitionException}.
 *
 * {@code autowireCandidate = false} prevents that: it removes the bean from
 * by-TYPE resolution on every path, including the {@code getBean(TransactionManager.class)}
 * that Spring's transaction interceptor uses for an unqualified
 * {@code @Transactional}, while a by-NAME lookup still finds it. So
 * {@code @Transactional("mongoTransactionManager")} resolves here and everything
 * unqualified keeps resolving to Neo4j's manager exactly as before.
 * ({@code defaultCandidate = false} is the weaker neighbour of that flag - it
 * still leaves the bean visible to by-type lookups - so it is not enough here.)
 *
 * <h2>Why the manager is chosen at runtime</h2> MongoDB supports multi-document
 * transactions only on a replica set or a sharded cluster. Every Gebo.ai compose
 * file runs a STANDALONE {@code mongod}, which rejects them - while the
 * Testcontainers {@code MongoDBContainer} used by the integration tests boots a
 * single-node REPLICA SET, which accepts them. Hard-wiring a real
 * {@code MongoTransactionManager} would therefore have passed every test and
 * broken every real installation. So the deployment is asked what it can do, and
 * gets real transactions when they are available and
 * {@link NonTransactionalMongoTransactionManager} when they are not.
 */
@ConditionalOnProperty(prefix = "ai.gebo.mongodb", name = "enabled", havingValue = "true")
@Configuration
public class GeboMongoTransactionConfig {

	private final static Logger LOGGER = LoggerFactory.getLogger(GeboMongoTransactionConfig.class);

	/** Bean name every Mongo side {@code @Transactional} must qualify against. */
	public static final String MONGO_TRANSACTION_MANAGER = "mongoTransactionManager";

	/**
	 * Forces the decision instead of asking the server.
	 *
	 * Left unset (the default) the deployment is probed. Set it to true to demand
	 * real transactions - the context then fails fast if the server cannot do them,
	 * which is what a replica-set deployment wants rather than silently degrading.
	 * Set it to false to never use them.
	 */
	@Value("${ai.gebo.mongodb.transactionsEnabled:#{null}}")
	Boolean transactionsEnabled;

	/**
	 * Builds the Mongo transaction manager appropriate to this deployment.
	 *
	 * @param databaseFactory factory the real manager would bind sessions to
	 * @param mongoTemplate   used to ask the server about its topology
	 * @return a real {@link MongoTransactionManager}, or the non-transactional
	 *         stand-in when the deployment cannot do transactions
	 */
	@Bean(name = MONGO_TRANSACTION_MANAGER, autowireCandidate = false)
	public PlatformTransactionManager mongoTransactionManager(MongoDatabaseFactory databaseFactory,
			MongoTemplate mongoTemplate) {
		boolean supported;
		if (transactionsEnabled != null) {
			supported = transactionsEnabled.booleanValue();
			LOGGER.info("MongoDB transactions forced to {} by ai.gebo.mongodb.transactionsEnabled", supported);
		} else {
			supported = deploymentSupportsTransactions(mongoTemplate);
		}
		if (supported) {
			LOGGER.info("MongoDB transactions are ENABLED: '{}' is a real MongoTransactionManager",
					MONGO_TRANSACTION_MANAGER);
			return new MongoTransactionManager(databaseFactory);
		}
		LOGGER.info("MongoDB transactions are NOT available on this deployment (a standalone mongod cannot do them). "
				+ "'{}' will accept @Transactional without starting a transaction; the annotated Mongo operations "
				+ "are single-document writes, which MongoDB makes atomic on their own. Run MongoDB as a replica set "
				+ "to get real transactions.", MONGO_TRANSACTION_MANAGER);
		return new NonTransactionalMongoTransactionManager();
	}

	/**
	 * Asks the server whether it is a replica set or a mongos.
	 *
	 * A failure to answer is treated as "no", because assuming transactions and
	 * being wrong breaks writes, while assuming none and being wrong only forgoes
	 * an atomicity guarantee these single-document operations do not need.
	 *
	 * @param mongoTemplate template used to run the command
	 * @return true when multi-document transactions can be started
	 */
	private boolean deploymentSupportsTransactions(MongoTemplate mongoTemplate) {
		try {
			Document hello = mongoTemplate.executeCommand(new Document("hello", 1));
			// A replica set member reports the set it belongs to; a mongos router
			// identifies itself through msg. A standalone mongod reports neither.
			boolean replicaSet = hello.get("setName") != null;
			boolean sharded = "isdbgrid".equals(hello.get("msg"));
			LOGGER.info("MongoDB topology probe: setName={} msg={}", hello.get("setName"), hello.get("msg"));
			return replicaSet || sharded;
		} catch (Throwable probeFailed) {
			LOGGER.warn("Cannot determine whether MongoDB supports transactions; assuming it does not. Reason: "
					+ probeFailed.getMessage());
			return false;
		}
	}
}
