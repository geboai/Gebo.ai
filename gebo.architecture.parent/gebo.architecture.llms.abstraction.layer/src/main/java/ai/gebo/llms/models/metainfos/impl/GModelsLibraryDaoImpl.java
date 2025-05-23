/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.models.metainfos.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.llms.models.metainfos.IGModelsLibraryDao;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.models.metainfos.config.ModelsLibraryBundleConfig;
import ai.gebo.llms.models.metainfos.config.ModelsLibraryConfig;

/**
 * AI generated comments Implementation of the IGModelsLibraryDao interface.
 * Provides functionality to search model metadata by model ID or provider and
 * model ID.
 */
@Service
@Scope("singleton")
public class GModelsLibraryDaoImpl implements IGModelsLibraryDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(GModelsLibraryDaoImpl.class);
	// Configuration holding information about the models.
	ModelsLibraryConfig config = null;

	// Configuration holding bundled information about the models.
	ModelsLibraryBundleConfig bundle = null;

	/**
	 * Constructor for GModelsLibraryDaoImpl.
	 * 
	 * @param config Configuration for model library.
	 * @param bundle Bundled configuration for model library.
	 */
	public GModelsLibraryDaoImpl(ModelsLibraryConfig config, ModelsLibraryBundleConfig bundle) {
		this.config = config;
		this.bundle = bundle;
		
	}

	

	/**
	 * Finds a ModelMetaInfo by model ID.
	 * 
	 * @param modelId The ID of the model.
	 * @return The ModelMetaInfo object if found, otherwise null.
	 */
	@Override
	public ModelMetaInfo findByModelId(String modelId) {
		// Check if configurations are properly initialized and not null.
		if (config == null || config.getModelsInfo() == null || bundle == null || bundle.getModelsInfo() == null)
			return null;
		Optional<ModelMetaInfo> optional = null;

		// Search in config's model information.
		if (config != null && config.getModelsInfo() != null) {
			optional = config.getModelsInfo().stream().filter(x -> {
				return x.getModelId() != null && x.getModelId().equals(modelId);
			}).findFirst();
		}

		// If not found in config, search in bundle's model information.
		if ((optional == null || optional.isEmpty()) && (bundle != null && bundle.getModelsInfo() != null)) {
			optional = bundle.getModelsInfo().stream().filter(x -> {
				return x.getModelId() != null && x.getModelId().equals(modelId);
			}).findFirst();
		}

		// Return the found model meta information or null if not found.
		return optional.isPresent() ? optional.get() : null;
	}

	/**
	 * Finds a ModelMetaInfo by provider ID and model ID.
	 * 
	 * @param providerId The ID of the provider.
	 * @param modelId    The ID of the model.
	 * @return The ModelMetaInfo object if found, otherwise null.
	 */
	@Override
	public ModelMetaInfo findByProviderIdAndModelId(String providerId, String modelId) {
		// Check if configurations are properly initialized and not null.
		if (config == null || config.getModelsInfo() == null || bundle == null || bundle.getModelsInfo() == null)
			return null;
		Optional<ModelMetaInfo> optional = null;

		// Search in config's model information.
		if (config != null && config.getModelsInfo() != null) {
			optional = config.getModelsInfo().stream().filter(x -> {
				return x.getProviderId() != null && x.getProviderId().equals(providerId) && x.getModelId() != null
						&& x.getModelId().equals(modelId);
			}).findFirst();
		}

		// If not found in config, search in bundle's model information.
		if ((optional == null || optional.isEmpty()) && (bundle != null && bundle.getModelsInfo() != null)) {
			optional = bundle.getModelsInfo().stream().filter(x -> {
				return x.getProviderId() != null && x.getProviderId().equals(providerId) && x.getModelId() != null
						&& x.getModelId().equals(modelId);
			}).findFirst();
		}

		// Return the found model meta information or null if not found.
		return optional.isPresent() ? optional.get() : null;
	}

}