/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.fastsetup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.fastsetup.model.ComponentVectorStoreStatus;
import ai.gebo.fastsetup.model.FastVectorStoreSetupData;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreProduct;
import ai.gebo.model.OperationStatus;
import ai.gebo.ragsystem.vectorstores.lucene.model.LuceneConfig;
import ai.gebo.ragsystem.vectorstores.model.GeboMongoVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.services.GeboVectorStoreConfigurationService;

/**
 * Service class responsible for setting up and managing the configuration of a fast vector store.
 * AI generated comments
 */
@Service
public class GeboFastVectorStoreSetupService {

	/**
	 * Service for configuration and management of vector stores.
	 */
	@Autowired
	GeboVectorStoreConfigurationService vectorStoreConfig;

	/**
	 * Default constructor.
	 */
	public GeboFastVectorStoreSetupService() {

	};

	/**
	 * Retrieves the current status of the vector store.
	 * @return the status of the vector store as a ComponentVectorStoreStatus object.
	 * @throws LLMConfigException if there is an error in retrieving the configuration.
	 */
	public ComponentVectorStoreStatus getVectorStoreStatus() throws LLMConfigException {
		GeboMongoVectorStoreConfig actualConfiguration = this.vectorStoreConfig.getActualConfiguration();
		ComponentVectorStoreStatus status = new ComponentVectorStoreStatus();
		status.product = actualConfiguration.getProduct();
		status.qdrantConfig = actualConfiguration.getQdrantConfig();
		// Determine if the setup is complete based on the product and its configuration.
		status.isSetup = status.product != null && (status.product != VectorStoreProduct.QDRANT
				|| (status.product == VectorStoreProduct.QDRANT && status.qdrantConfig != null));
		return status;
	}

	/**
	 * Creates and tests a new vector store configuration based on provided setup data.
	 * @param data the setup data for the vector store.
	 * @return an OperationStatus object containing the status of the operation.
	 * @throws LLMConfigException if there is an error in setting the configuration.
	 */
	public OperationStatus<ComponentVectorStoreStatus> createVectorStoreConfiguration(FastVectorStoreSetupData data)
			throws LLMConfigException {

		GeboMongoVectorStoreConfig actualConfiguration = this.vectorStoreConfig.getActualConfiguration();
		actualConfiguration.setProduct(data.getProduct());
		// Configure vector store based on product type
		switch (data.getProduct()) {
		case LUCENE: {
			actualConfiguration.setLuceneConfig(new LuceneConfig());
		}
			break;
		case MONGO: {
		}
			break;
		case QDRANT: {
			actualConfiguration.setQdrantConfig(data.getQdrantConfig());
		}
			break;
		case REDIS: {
			actualConfiguration.setRedisConfig(data.getRedisConfig());
		}
			break;

		}
		OperationStatus<GeboMongoVectorStoreConfig> status = this.vectorStoreConfig
				.validateAndTestConfiguration(actualConfiguration);
		// Save configuration only if no error messages are present
		if (!status.isHasErrorMessages()) {
			vectorStoreConfig.save(actualConfiguration);
		}
		ComponentVectorStoreStatus statusdata = new ComponentVectorStoreStatus();
		statusdata.product = data.getProduct();
		statusdata.qdrantConfig = data.getQdrantConfig();
		statusdata.redisConfig = data.getRedisConfig();
		statusdata.isSetup = !status.isHasErrorMessages();
		OperationStatus<ComponentVectorStoreStatus> out = OperationStatus.of(statusdata);
		// Clear existing messages and set new ones from status
		out.getMessages().clear();
		out.setMessages(status.getMessages());
		return out;
	}
}