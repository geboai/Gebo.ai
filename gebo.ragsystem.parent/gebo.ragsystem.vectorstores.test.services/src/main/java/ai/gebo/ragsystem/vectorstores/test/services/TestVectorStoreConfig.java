/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.test.services;

import ai.gebo.llms.abstraction.layer.vectorstores.model.GBaseVectorStoreConfig;

/**
 * AI generated comments
 * 
 * Test configuration class for vector store implementation.
 * Extends the base vector store configuration to add test-specific
 * configuration properties.
 */
public class TestVectorStoreConfig extends GBaseVectorStoreConfig {
	/**
	 * The response time in milliseconds to simulate during testing.
	 * Can be null if no specific delay is needed.
	 */
	private Integer millisecondResponseTime=null;

	/**
	 * Gets the configured response time in milliseconds.
	 * 
	 * @return The simulated response time in milliseconds, or null if not set
	 */
	public Integer getMillisecondResponseTime() {
		return millisecondResponseTime;
	}

	/**
	 * Sets the simulated response time in milliseconds.
	 * 
	 * @param millisecondResponseTime The response time delay to simulate
	 */
	public void setMillisecondResponseTime(Integer millisecondResponseTime) {
		this.millisecondResponseTime = millisecondResponseTime;
	}
}