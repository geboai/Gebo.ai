/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.abstraction.layer.services;

import java.util.List;

import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.model.base.GObjectRef;

/**
 * Gebo.ai comment agent
 * 
 * This interface extends the IGRuntimeConfigurationDao to provide additional
 * methods for handling runtime model configurations.
 * 
 * @param <IFacetype> the type of model configuration managed by this DAO
 */
public interface IGRuntimeModelConfigurationDao<IFacetype extends IGConfigurableModel>
		extends IGRuntimeConfigurationDao<IFacetype> {

	/**
	 * Adds a new model configuration to the data store.
	 * 
	 * @param element the model configuration to be added
	 */
	public void add(IFacetype element);

	/**
	 * Deletes a model configuration identified by its code.
	 * 
	 * @param code the unique code of the model configuration to be deleted
	 * @throws LLMConfigException if the deletion fails
	 */
	public void deleteByCode(String code) throws LLMConfigException;

	/**
	 * Provides a default implementation for retrieving the default model
	 * configuration.
	 * 
	 * @return the default model configuration, or null if none is found
	 */
	public default IFacetype defaultHandler() {
		// Retrieve and iterate over all configurations to find the default one
		List<IFacetype> configurations = getConfigurations();
		IFacetype defaultConfig = null;
		for (IFacetype iFacetype : configurations) {
			// Check if the current configuration is marked as default
			if (iFacetype.getConfig() != null && iFacetype.getConfig().getDefaultModel() != null
					&& iFacetype.getConfig().getDefaultModel()) {
				defaultConfig = iFacetype;
				break;
			}
		}
		return defaultConfig;
	}

	public default IFacetype findByModelReference(GObjectRef<? extends GBaseModelConfig> modelReference) {
		if (modelReference == null)
			return null;
		return findByPredicate(
				x -> modelReference.getCode() != null && modelReference.getClassName() != null && x.getConfig() != null
						&& x.getConfig().getCode() != null && x.getConfig().getCode().equals(modelReference.getCode())
						&& x.getConfig().getClass().getName().equals(modelReference.getClassName()));
	}
}