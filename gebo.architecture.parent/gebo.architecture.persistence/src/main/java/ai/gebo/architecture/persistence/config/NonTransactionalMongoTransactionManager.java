/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.persistence.config;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

/**
 * Transaction manager that accepts {@code @Transactional} without starting any
 * transaction, used for the Mongo side when the deployment cannot do them.
 *
 * MongoDB only supports multi-document transactions on a replica set or a
 * sharded cluster: a standalone {@code mongod} rejects them outright, and every
 * Gebo.ai compose file runs a standalone {@code mongod}. A real
 * {@code MongoTransactionManager} there would turn each annotated call into a
 * startup or runtime failure.
 *
 * So the annotated Mongo operations bind to THIS manager instead when
 * transactions are unavailable. They keep working exactly as they did before any
 * transaction manager was configured - which is what they have always done in
 * practice, since Gebo.ai declared no transaction manager of its own and the
 * annotations were silently landing on the auto-configured Neo4j one. Every
 * operation annotated on the Mongo side is a single document write, and MongoDB
 * makes those atomic on their own.
 *
 * It is deliberately NOT a general purpose "no-op transactions" facility: it is
 * selected only by {@link GeboMongoTransactionConfig}, only for the Mongo
 * manager, and only after the server has said it cannot do transactions.
 */
public class NonTransactionalMongoTransactionManager extends AbstractPlatformTransactionManager {

	private static final long serialVersionUID = 1L;

	/** The single transaction object handed out; it carries no state. */
	private static final Object TRANSACTION = new Object();

	/**
	 * @return a constant placeholder, since there is nothing to bind
	 */
	@Override
	protected Object doGetTransaction() throws TransactionException {
		return TRANSACTION;
	}

	/**
	 * Starts nothing.
	 *
	 * @param transaction the placeholder from {@link #doGetTransaction()}
	 * @param definition  the requested transaction semantics, ignored
	 */
	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition) throws TransactionException {
		// Intentionally empty: no session, no transaction, nothing to suspend.
	}

	/**
	 * Commits nothing.
	 *
	 * @param status the transaction status, ignored
	 */
	@Override
	protected void doCommit(DefaultTransactionStatus status) throws TransactionException {
		// Intentionally empty.
	}

	/**
	 * Rolls back nothing.
	 *
	 * Note what this means for a caller: without a real transaction there is no
	 * atomic rollback, so a failure halfway through a multi-step write leaves the
	 * earlier steps applied. That is the pre-existing behaviour of these code
	 * paths, not a regression introduced here.
	 *
	 * @param status the transaction status, ignored
	 */
	@Override
	protected void doRollback(DefaultTransactionStatus status) throws TransactionException {
		// Intentionally empty.
	}
}
