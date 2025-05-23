/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.models.metainfos.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.llms.models.metainfos.ModelMetaInfo;

/**
 * Gebo.ai comment agent
 * 
 * Configuration class for managing the library of model metadata.
 * It utilizes Spring Boot ConfigurationProperties to map the properties 
 * prefixed with "ai.gebo.models.library" from configuration files.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.models.library")
public class ModelsLibraryConfig {
	
	/** 
	 * List to store metadata information about models.
	 */
	private List<ModelMetaInfo> modelsInfo = new ArrayList<ModelMetaInfo>();

	/**
	 * Default constructor for ModelsLibraryConfig.
	 */
	public ModelsLibraryConfig() {

	}

	/**
	 * Retrieves the list of model metadata information.
	 * @return a list of ModelMetaInfo objects.
	 */
	public List<ModelMetaInfo> getModelsInfo() {
		return modelsInfo;
	}

	/**
	 * Sets the list of model metadata information.
	 * @param modelsInfo a list of ModelMetaInfo objects to set.
	 */
	public void setModelsInfo(List<ModelMetaInfo> modelsInfo) {
		this.modelsInfo = modelsInfo;
	}

}