/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * AI generated comments
 * Service class responsible for managing vector store configurations within the Gebo AI system.
 * Handles validation, testing, conversion, and persistence of vector store configurations.
 */
package ai.gebo.ragsystem.vectorstores.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactory;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryBuilder;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryBuilderRepositoryPattern;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GBaseVectorStoreConfig;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreRuntimeConfiguration;
import ai.gebo.model.OperationStatus;
import ai.gebo.ragsystem.vectorstores.config.GeboAIVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.model.GeboMongoVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.repository.GeboMongoVectorStoreConfigRepository;

@Service
public class GeboVectorStoreConfigurationService {
	@Autowired
	GeboAIVectorStoreConfig config;
	@Autowired
	GeboMongoVectorStoreConfigRepository mongVSConfigRepo;
	@Autowired
	IGRuntimeBinder runtimeBinder;

	/**
	 * Validates and tests a vector store configuration.
	 * Extracts the configuration, creates a runtime configuration,
	 * and runs tests if the builder supports testing.
	 *
	 * @param configuration The MongoDB vector store configuration to validate and test
	 * @return Operation status containing result and any messages
	 * @throws LLMConfigException If configuration validation fails
	 */
	public OperationStatus<GeboMongoVectorStoreConfig> validateAndTestConfiguration(
			GeboMongoVectorStoreConfig configuration) throws LLMConfigException {
		IGVectorStoreFactoryBuilderRepositoryPattern vectorStoreFactoryBuildersRepo = runtimeBinder
				.getImplementationOf(IGVectorStoreFactoryBuilderRepositoryPattern.class);
		IGVectorStoreFactoryBuilder builder = vectorStoreFactoryBuildersRepo
				.findByCode(configuration.getProduct().name());
		Object ymlConfig = builder.mongo2ymlConfig(configuration);
		GBaseVectorStoreConfig vConfig = builder.extractConfiguration(ymlConfig);
		if (builder.noTestsRequired()) {
		} else {
			VectorStoreRuntimeConfiguration runtimeConfig = new VectorStoreRuntimeConfiguration(
					configuration.getProduct(), vConfig);
			if (builder.isTestable()) {
				OperationStatus<IGVectorStoreFactory<GBaseVectorStoreConfig>> test = builder
						.test(runtimeConfig.getConfiguration());

				OperationStatus<GeboMongoVectorStoreConfig> status = OperationStatus.of(configuration);
				status.getMessages().clear();
				status.setMessages(test.getMessages());
				return status;
			}

		}
		return OperationStatus.of(configuration);
	}

	/**
	 * Saves a vector store configuration to the MongoDB repository.
	 *
	 * @param configuration The configuration to save
	 */
	public void save(GeboMongoVectorStoreConfig configuration) {
		mongVSConfigRepo.save(configuration);
	}

	/**
	 * Notifies system about vector store configuration changes.
	 * Currently a placeholder method.
	 */
	public void notifyVectorStoreConfigurationChanges() {

	}

	/**
	 * Converts a MongoDB vector store configuration to a YAML configuration.
	 *
	 * @param config The MongoDB configuration to convert
	 * @return The equivalent YAML configuration
	 * @throws LLMConfigException If the configuration is invalid or conversion fails
	 */
	public GeboAIVectorStoreConfig mongo2YmlConfig(GeboMongoVectorStoreConfig config) throws LLMConfigException {
		if (config.getProduct() == null)
			throw new LLMConfigException("product is null in mongo gebo vector store configuration");
		IGVectorStoreFactoryBuilderRepositoryPattern vectorStoreFactoryBuildersRepo = runtimeBinder
				.getImplementationOf(IGVectorStoreFactoryBuilderRepositoryPattern.class);
		IGVectorStoreFactoryBuilder builder = vectorStoreFactoryBuildersRepo.findByCode(config.getProduct().name());
		return builder.mongo2ymlConfig(config);
	}

	/**
	 * Converts a YAML vector store configuration to a MongoDB configuration.
	 *
	 * @param config The YAML configuration to convert
	 * @return The equivalent MongoDB configuration
	 * @throws LLMConfigException If the configuration is invalid or conversion fails
	 */
	public GeboMongoVectorStoreConfig yml2MongoConfig(GeboAIVectorStoreConfig config) throws LLMConfigException {
		if (config.getUse() == null || config.getUse().trim().length() == 0)
			throw new LLMConfigException("use is null in yml gebo vector store configuration");
		IGVectorStoreFactoryBuilderRepositoryPattern vectorStoreFactoryBuildersRepo = runtimeBinder
				.getImplementationOf(IGVectorStoreFactoryBuilderRepositoryPattern.class);
		IGVectorStoreFactoryBuilder builder = vectorStoreFactoryBuildersRepo.findByCode(config.getUse());
		return builder.yml2mongoConfig(config);
	}

	/**
	 * Retrieves the current active vector store configuration.
	 * Fetches from MongoDB if available, otherwise converts from YAML config.
	 *
	 * @return The current vector store configuration
	 * @throws LLMConfigException If configuration retrieval or conversion fails
	 */
	public GeboMongoVectorStoreConfig getActualConfiguration() throws LLMConfigException {
		Optional<GeboMongoVectorStoreConfig> mongoConfig = mongVSConfigRepo
				.findById(GeboMongoVectorStoreConfig.VECTORSTORECONFIG);
		if (mongoConfig.isPresent()) {
			return mongoConfig.get();
		} else {

			return yml2MongoConfig(config);
		}

	}

	/**
	 * Converts a MongoDB vector store configuration to a standard runtime configuration.
	 *
	 * @param config The MongoDB configuration to convert
	 * @return A runtime configuration ready for use by the vector store system
	 * @throws LLMConfigException If the configuration is invalid or conversion fails
	 */
	public VectorStoreRuntimeConfiguration mongo2Standart(GeboMongoVectorStoreConfig config) throws LLMConfigException {
		if (config.getProduct() == null)
			throw new LLMConfigException("product is null in mongo gebo vector store configuration");
		IGVectorStoreFactoryBuilderRepositoryPattern vectorStoreFactoryBuildersRepo = runtimeBinder
				.getImplementationOf(IGVectorStoreFactoryBuilderRepositoryPattern.class);
		IGVectorStoreFactoryBuilder builder = vectorStoreFactoryBuildersRepo.findByCode(config.getProduct().name());
		GeboAIVectorStoreConfig ymlConfig = mongo2YmlConfig(config);
		return new VectorStoreRuntimeConfiguration(config.getProduct(), builder.extractConfiguration(ymlConfig));
	}
}