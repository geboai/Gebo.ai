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

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.patterns.IGRuntimeModuleComponent;
import ai.gebo.architecture.patterns.model.GModuleUseInfo;
import ai.gebo.architecture.patterns.model.GModuleUseInfo.MInfoType;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.model.OperationStatus;

/**
 * Gebo.ai comment agent This interface defines the model configuration support
 * services. It provides methods to fetch model types, retrieve model choices
 * based on configuration, create base configurations for models, and get module
 * use information.
 *
 * @param <ModelType>   The type of the model extending GModelType
 * @param <ModelChoice> The choice of the model extending GBaseModelChoice
 * @param <ModelConfig> The configuration of the model extending
 *                      GBaseModelConfig
 */
public interface IGModelConfigurationSupportService<ModelType extends GModelType, ModelChoice extends GBaseModelChoice, ModelConfig extends GBaseModelConfig>
		extends IGRuntimeModuleComponent {

	/**
	 * Returns the type of the model.
	 *
	 * @return the model type
	 */
	public ModelType getType();

	/**
	 * Get available choices for the model based on the provided configuration.
	 *
	 * @param config the model configuration
	 * @return an OperationStatus containing a list of model choices
	 */
	public OperationStatus<List<ModelChoice>> getModelChoices(ModelConfig config);

	/**
	 * Create a base configuration for the model using the provided preset model
	 * identifier.
	 *
	 * @param presetModel the identifier of the preset model
	 * @return the base model configuration
	 */
	public ModelConfig createBaseConfiguration(String presetModel);

	/**
	 * Provides module use information for existing instances. It sets the module
	 * ID, handler ID, and information type for the module.
	 *
	 * @return a list of module use information instances
	 */
	@Override
	public default List<GModuleUseInfo> getModuleUseInfo() {
		GModuleUseInfo mod = new GModuleUseInfo();
		mod.setModuleId(GStandardModulesConstraints.CORE_MODULE);
		mod.setHandlerId(getType().getCode()); // Set the handler ID with the code of the model type
		mod.setInfoType(MInfoType.EXISTENCE); // Set the information type to EXISTENCE
		return List.of(mod); // Return a list containing the module use information
	}

	public OperationStatus<ModelConfig> insertAndConfigure(ModelConfig config) throws GeboPersistenceException, LLMConfigException;

}