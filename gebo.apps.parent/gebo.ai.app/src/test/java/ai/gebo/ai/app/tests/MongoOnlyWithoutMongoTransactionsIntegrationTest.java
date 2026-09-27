/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 


package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.PlatformTransactionManager;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.GAclEntry;
import ai.gebo.acl.IAclAliasesDao;
import ai.gebo.architecture.persistence.config.NonTransactionalMongoTransactionManager;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;

/**
 * The single-dependency installation on a STANDALONE {@code mongod} - the
 * topology a Windows install actually runs, and the one no other test covers.
 *
 * <h2>Why this test exists</h2> MongoDB supports multi-document transactions only
 * on a replica set or a sharded cluster. The Testcontainers
 * {@code MongoDBContainer} every other integration test uses boots a single-node
 * REPLICA SET, so it always gets real transactions - while
 * {@code dockers/docker-compose-deploy/docker-compose.yml} and its Windows copy
 * run {@code mongod} with no {@code --replSet} at all. Without this test the
 * production topology would be the one topology never exercised, and a
 * transaction manager that only works on a replica set would pass the whole
 * suite and fail every real installation.
 *
 * It forces {@code ai.gebo.mongodb.transactionsEnabled=false} rather than running
 * a second kind of container: that switch is exactly what
 * {@code GeboMongoTransactionConfig} would decide by itself when it probes a
 * standalone {@code mongod}, so the code under test is the same.
 *
 * <h2>What it proves</h2> That the Mongo side of the platform works with no
 * transaction support at all: the context starts, the ACL aliases of the
 * platform's own system identity get allocated - the operation that used to fail
 * outright - and the annotated Mongo write paths still work.
 */
public class MongoOnlyWithoutMongoTransactionsIntegrationTest extends AbstractMongoOnlyBaseIntegrationTest {

	/** Allocates ACL aliases; the DAO whose {@code @Transactional} was the bug. */
	@Autowired
	IAclAliasesDao aclAliasesDao;

	/** Used to read the transaction manager bean back by name. */
	@Autowired
	ApplicationContext applicationContext;

	/**
	 * Pretends the deployment is a standalone {@code mongod}.
	 *
	 * @param registry registry the property is added to
	 */
	@DynamicPropertySource
	public static void withoutMongoTransactions(DynamicPropertyRegistry registry) {
		registry.add("ai.gebo.mongodb.transactionsEnabled", () -> false);
	}

	/**
	 * The Mongo manager must be the non-transactional stand-in, and it must NOT
	 * have displaced the manager that unqualified {@code @Transactional} resolves
	 * to.
	 */
	@Test
	public void testMongoTransactionManagerFallsBackWhenTransactionsAreUnavailable() {
		Object manager = applicationContext.getBean("mongoTransactionManager");
		assertNotNull(manager, "The Mongo transaction manager must always be registered");
		assertTrue(manager instanceof NonTransactionalMongoTransactionManager,
				"With transactions unavailable the Mongo manager must be the non-transactional stand-in, found: "
						+ manager.getClass().getName());

		// The bean is registered with autowireCandidate = false precisely so that it
		// stays out of by-TYPE resolution. That is what keeps every bare
		// @Transactional in the graph stack working: the transaction interceptor
		// resolves those through getBean(TransactionManager.class), which would throw
		// NoUniqueBeanDefinitionException the moment a second candidate appeared.
		// Asserted behaviourally rather than by inspecting the bean definition, since
		// the behaviour is the thing that must hold.
		PlatformTransactionManager byType = applicationContext.getBean(PlatformTransactionManager.class);
		assertNotNull(byType, "An unqualified PlatformTransactionManager must still resolve");
		assertFalse(byType instanceof NonTransactionalMongoTransactionManager,
				"By-type resolution must NOT return the Mongo stand-in, otherwise unqualified @Transactional in the "
						+ "graph stack would silently stop using Neo4j; got: " + byType.getClass().getName());
	}

	/**
	 * The exact operation that failed before: allocating an ACL alias.
	 *
	 * {@code AclAliasesDaoImpl.addAcl} is {@code @Transactional}, and it used to
	 * resolve to the auto-configured Neo4j manager, so with the graph stack
	 * disabled it threw and {@code GeboSystemUserAclInitializer} could not
	 * allocate the aliases of the platform's own system identity.
	 */
	@Test
	public void testAclAliasAllocationWorksWithoutTransactions() {
		GAclEntry entry = new GAclEntry("user:mongo-only-transactions-test@gebo.ai", AclGrantType.READ);
		int alias = aclAliasesDao.addAcl(entry);
		assertTrue(alias > 0, "Allocating an ACL alias must return a positive alias, got " + alias);

		GAclEntry readBack = aclAliasesDao.findAcl(alias);
		assertNotNull(readBack, "The allocated ACL alias must be readable back");
		assertEquals(entry.getAclGrantedUniqueId(), readBack.getAclGrantedUniqueId(),
				"The ACL alias must round-trip its granted identity");
		assertEquals(AclGrantType.READ, readBack.getGrant(), "The ACL alias must round-trip its grant type");

		assertEquals(Integer.valueOf(alias), aclAliasesDao.findAlias(entry),
				"Looking the entry up must find the alias just allocated");

		// removeAcl is @Transactional too, so it exercises the same binding.
		aclAliasesDao.removeAcl(alias);
		assertNotNull(aclAliasesDao.findAliasesByAclGrantedUniqueId(entry.getAclGrantedUniqueId()),
				"Querying aliases after a removal must not fail");
	}

	/**
	 * The opt-in transactional API of the persistence manager must still work.
	 *
	 * These methods are named {@code transactional*} and are annotated, so they are
	 * the other place the qualified manager is used. Without transaction support
	 * they simply run without one - the write is a single document, which MongoDB
	 * makes atomic on its own.
	 *
	 * @throws Exception if the write or the read back fails
	 */
	@Test
	public void testTransactionalPersistenceApiWorksWithoutTransactions() throws Exception {
		GKnowledgeBase kb = new GKnowledgeBase();
		kb.setDescription("KB written through the transactional API without Mongo transactions");
		GKnowledgeBase inserted = persistentObjectManager.transactionalInsert(kb);
		assertNotNull(inserted, "transactionalInsert must return the stored object");
		assertNotNull(inserted.getCode(), "The stored object must have been assigned a code");

		GKnowledgeBase found = persistentObjectManager.transactionalFindById(GKnowledgeBase.class,
				inserted.getCode());
		assertNotNull(found, "transactionalFindById must read the object back");
		assertEquals(inserted.getCode(), found.getCode(), "The object read back must be the one written");

		found.setDescription("updated without Mongo transactions");
		GKnowledgeBase updated = persistentObjectManager.transactionalUpdate(found);
		assertEquals("updated without Mongo transactions", updated.getDescription(),
				"transactionalUpdate must persist the change");

		persistentObjectManager.transactionalDelete(updated);
		assertNull(persistentObjectManager.findById(GKnowledgeBase.class, updated.getCode()),
				"transactionalDelete must remove the object");
	}

	/**
	 * Local null assertion helper, kept so the imports stay minimal.
	 *
	 * @param value   value that must be null
	 * @param message failure message
	 */
	private static void assertNull(Object value, String message) {
		org.junit.jupiter.api.Assertions.assertNull(value, message);
	}
}
