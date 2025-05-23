/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.vectorstores;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GBaseVectorStoreConfig;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreProduct;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * A factory builder interface for creating vector store factories with different configurations.
 */
public interface IGVectorStoreFactoryBuilder {

	/**
	 * Retrieves the product associated with the vector store.
	 * 
	 * @return the vector store product.
	 */
	VectorStoreProduct getProduct();

	/**
	 * Extracts and parses the configuration from a given YAML configuration object.
	 * 
	 * @param <T> the type of the vector store configuration that extends 
	 *            GBaseVectorStoreConfig.
	 * @param <O> the type of the object representing YAML configuration.
	 * @param ymlConfiguration the YAML configuration object.
	 * @return the extracted configuration.
	 * @throws LLMConfigException if the extraction fails.
	 */
	public <T extends GBaseVectorStoreConfig, O> T extractConfiguration(O ymlConfiguration) throws LLMConfigException;

	/**
	 * Converts a given YAML configuration to a MongoDB configuration object.
	 * 
	 * @param <T> the type of the configuration to be converted.
	 * @param <O> the type of the resulting MongoDB configuration object.
	 * @param config the original configuration object.
	 * @return the MongoDB configuration object.
	 * @throws LLMConfigException if the conversion fails.
	 */
	public <T, O> T yml2mongoConfig(O config) throws LLMConfigException;

	/**
	 * Converts a MongoDB configuration to a YAML configuration object.
	 * 
	 * @param <T> the type of the MongoDB configuration to be converted.
	 * @param <O> the type of the resulting YAML configuration object.
	 * @param config the MongoDB configuration object.
	 * @return the resulting YAML configuration object.
	 * @throws LLMConfigException if the conversion fails.
	 */
	public <T, O> O mongo2ymlConfig(T config) throws LLMConfigException;

	/**
	 * Builds the vector store factory using the given configuration.
	 * 
	 * @param <T> the type of the vector store configuration.
	 * @param config the configuration for building the factory.
	 * @return an instance of IGVectorStoreFactory based on the provided configuration.
	 * @throws LLMConfigException if the factory construction fails.
	 */
	public <T extends GBaseVectorStoreConfig> IGVectorStoreFactory<T> build(T config) throws LLMConfigException;

	/**
	 * Indicates whether tests are required for the factory, defaulting to false.
	 * 
	 * @return false indicating tests are required by default.
	 */
	public default boolean noTestsRequired() {
		return false;
	}

	/**
	 * Determines if the configuration is testable.
	 * 
	 * @return true indicating the configuration can be tested by default.
	 */
	public default boolean isTestable() {
		return true;
	}

	/**
	 * Tests the vector store factory with the given configuration.
	 * If the build fails, the operation status will encapsulate the throwable error.
	 * 
	 * @param <T> the type of the vector store configuration.
	 * @param cpnfig the configuration for testing the factory.
	 * @return the operation status indicating the result of the test.
	 */
	public default <T extends GBaseVectorStoreConfig> OperationStatus<IGVectorStoreFactory<T>> test(T cpnfig) {
		OperationStatus<IGVectorStoreFactory<T>> status = null;
		try {
			// Attempt to build the factory and encapsulate the result in the operation status
			status = OperationStatus.of(build(cpnfig));
		} catch (Throwable th) {
			// Capture any throwable errors in the operation status
			status = OperationStatus.of(th);
		}
		return status;
	}

}