/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.multithreading;

/**
 * Gebo.ai comment agent
 * Represents a consumer interface for a thread pool. 
 * This interface defines essential methods that must be implemented 
 * to interact with a thread pool, specifically to retrieve the 
 * cardinality of the pool.
 */
public interface IGThreadPoolConsumer {
	
	/**
	 * Gets the cardinality (i.e., the number of threads) of the pool.
	 * 
	 * @return the number of threads in the thread pool
	 */
	public int getPoolCardinality();
}