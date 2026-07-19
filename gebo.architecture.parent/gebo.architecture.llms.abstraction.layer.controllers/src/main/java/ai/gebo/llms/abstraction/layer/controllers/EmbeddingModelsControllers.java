/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.llms.abstraction.layer.controllers.model.ConfigurationEntry;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.model.GEmbeddingModelType;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;

/**
 * AI generated comments
 * EmbeddingModelsControllers is a REST controller that manages embeddings model configurations.
 * It is secured with role-based access control, allowing only users with the "ADMIN" role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/EmbeddingModelsControllers")
public class EmbeddingModelsControllers {
	
	// Repository containing information on embedding model types
	@Autowired
	IGEmbeddingModelConfigurationSupportServiceRepositoryPattern embeddingModelsTypesRepo;
	
	// DAO for accessing runtime configuration information for embedding models
	@Autowired
	IGEmbeddingModelRuntimeConfigurationDao runtimeDao;
	
	// Default constructor
	public EmbeddingModelsControllers() {

	}

	/**
	 * Endpoint to retrieve available embedding model types.
	 * 
	 * @return List of GEmbeddingModelType
	 */
	@GetMapping(value = "getEmbeddingModelTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GEmbeddingModelType> getEmbeddingModelTypes() {
		// Mapping model types from the repository to a list
		List<GModelType> list = embeddingModelsTypesRepo.map((x) -> {
			return x.getType();
		});
		return new ArrayList(list);
	}

	/**
	 * Endpoint to retrieve runtime-configured embedding models based on an optional model type code.
	 * 
	 * @param modelTypeCode Optional parameter to filter models by type code
	 * @return List of ConfigurationEntry for GBaseEmbeddingModelConfig
	 */
	@GetMapping(value = "getRuntimeConfiguredEmbeddingModels", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ConfigurationEntry<GBaseEmbeddingModelConfig>> getRuntimeConfiguredEmbeddingModels(
			@RequestParam(name = "modelTypeCode", required = false) String modelTypeCode) {
		if (modelTypeCode != null) {
			// List to store configuration entries
			List<ConfigurationEntry<GBaseEmbeddingModelConfig>> configs = new ArrayList<ConfigurationEntry<GBaseEmbeddingModelConfig>>();
			// Finds and processes models by the given type code
			List<IGConfigurableEmbeddingModel> elements = this.runtimeDao.findListByPredicate(x -> {
				return x.getType().getCode().equals(modelTypeCode);
			});
			if (elements != null) {
				// Mapping elements to configuration entries
				configs = new ArrayList(elements.stream().map(x -> {
					return new ConfigurationEntry(x.getConfig());
				}).toList());
			}
			return configs;
		} else {
			// Returns a list of configurations without any filtering
			return new ArrayList(this.runtimeDao.getConfigurations().stream().map(x -> {
				return new ConfigurationEntry(x.getConfig());
			}).toList());
		}
	}
}