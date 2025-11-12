/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.tests;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.testing.AbstractTestingBusinessLogic;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;
import ai.gebo.llms.abstraction.layer.model.GEmbeddingModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryProvider;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * This service provides a test implementation of the embedding model configuration support service.
 * It's primarily used for testing the embedding model functionality without requiring actual embedding models.
 */
@Service
public class TestEmbeddingModelSupportServiceImpl extends AbstractTestingBusinessLogic implements
		IGEmbeddingModelConfigurationSupportService<GBaseEmbeddingModelChoice, TestEmbeddingModelConfiguration> {
	/** Constant for the test embedding model version 001 */
	public static final String TEST_EMBEDDING_MODEL_001 = "TEST-EMBEDDING-MODEL-001";
	/** Constant for the base test embedding model */
	public static final String TEST_EMBEDDING_MODEL = "TEST-EMBEDDING-MODEL";
	/** Static embedding model type instance for testing */
	static final GEmbeddingModelType type = new GEmbeddingModelType();
	/** Static model choice instance for testing */
	static final GBaseEmbeddingModelChoice model = new GBaseEmbeddingModelChoice();
	/** Provider for vector store factories */
	@Autowired
	IGVectorStoreFactoryProvider factoryProvider;
	
	/**
	 * Static initializer to set up the test model types and codes
	 */
	static {
		type.setCode(TEST_EMBEDDING_MODEL);
		model.setCode(TEST_EMBEDDING_MODEL_001);
		
	}

	/**
	 * Inner class that implements a configurable embedding model for testing purposes.
	 * Extends the abstract embedding model with test-specific configuration.
	 */
	static class TestConfigurableEmbeddingModel
			extends GAbstractConfigurableEmbeddingModel<TestEmbeddingModelConfiguration, TestEmbeddingModel> {

		/**
		 * Constructor that initializes the model with a vector store factory provider
		 * 
		 * @param storeFactoryProvider The provider for vector store factories
		 */
		public TestConfigurableEmbeddingModel(IGVectorStoreFactoryProvider storeFactoryProvider) {
			super(storeFactoryProvider);

		}

		/**
		 * Configures a test embedding model with the provided configuration
		 * 
		 * @param config The configuration to apply to the model
		 * @param type The embedding model type
		 * @return A configured TestEmbeddingModel instance
		 * @throws LLMConfigException If configuration fails
		 */
		@Override
		protected TestEmbeddingModel configureModel(TestEmbeddingModelConfiguration config, GEmbeddingModelType type)
				throws LLMConfigException {
			TestEmbeddingModel e = new TestEmbeddingModel();
			e.setConfiguration(config);
			return e;
		}

	}

	/**
	 * Default constructor
	 */
	public TestEmbeddingModelSupportServiceImpl() {

	}

	/**
	 * Returns the type of embedding model supported by this service
	 * 
	 * @return The embedding model type
	 */
	@Override
	public GEmbeddingModelType getType() {

		return type;
	}

	/**
	 * Returns available model choices for the given configuration
	 * 
	 * @param config The configuration to use when determining model choices
	 * @return Operation status containing a list of available model choices
	 */
	@Override
	public OperationStatus<List<GBaseEmbeddingModelChoice>> getModelChoices(TestEmbeddingModelConfiguration config) {
		return OperationStatus.of(List.of(model));
	}

	/**
	 * Creates a base configuration for the specified preset model
	 * 
	 * @param presetModel The model name to create a configuration for
	 * @return A new configuration instance (returns null in this implementation)
	 */
	@Override
	public TestEmbeddingModelConfiguration createBaseConfiguration(String presetModel) {
		return null;
	}

	/**
	 * Creates and initializes a configurable embedding model with the provided configuration
	 * 
	 * @param config The configuration to use
	 * @return A configured embedding model instance
	 * @throws LLMConfigException If configuration fails
	 */
	@Override
	public IGConfigurableEmbeddingModel<TestEmbeddingModelConfiguration> create(TestEmbeddingModelConfiguration config)
			throws LLMConfigException {
		TestConfigurableEmbeddingModel em = new TestConfigurableEmbeddingModel(factoryProvider);
		em.initialize(config, type);
		return em;
	}

	@Override
	public OperationStatus<TestEmbeddingModelConfiguration> insertAndConfigure(TestEmbeddingModelConfiguration config)
			throws GeboPersistenceException, LLMConfigException {
		// TODO Auto-generated method stub
		return null;
	}

}