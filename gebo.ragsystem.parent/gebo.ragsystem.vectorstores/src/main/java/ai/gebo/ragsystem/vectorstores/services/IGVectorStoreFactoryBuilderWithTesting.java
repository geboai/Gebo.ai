/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.services;

import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactory;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryBuilder;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GBaseVectorStoreConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.ragsystem.vectorstores.utils.VectorStoreFactoryBuilderTester;

/**
 * AI generated comments
 * 
 * Interface that extends the base IGVectorStoreFactoryBuilder with built-in testing capabilities.
 * This interface provides default implementations for testing-related methods, making it easier
 * to test vector store factory builders.
 */
public interface IGVectorStoreFactoryBuilderWithTesting extends IGVectorStoreFactoryBuilder {
	/**
	 * Indicates that implementations of this interface are testable.
	 * 
	 * @return Always returns true to signify that this interface supports testing
	 */
	@Override
	default boolean isTestable() {
		
		return true;
	}
	
	/**
	 * Tests the vector store factory builder with the provided configuration.
	 * Delegates the actual testing to VectorStoreFactoryBuilderTester utility.
	 * 
	 * @param <T> The type of vector store configuration
	 * @param cpnfig The configuration to test with
	 * @return An OperationStatus containing either the created factory on success or error information on failure
	 */
	@Override
	default <T extends GBaseVectorStoreConfig> OperationStatus<IGVectorStoreFactory<T>> test(T cpnfig) {
		
		return VectorStoreFactoryBuilderTester.test(cpnfig, this);
	}
}