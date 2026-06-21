/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.abstraction.layer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.model.OperationStatus;

/**
 * Gebo.ai comment agent
 * 
 * Controller class for managing the configuration of chat models. This class
 * extends the base CRUD operations for chat model configurations and adds
 * specific functionality to handle chat model choices.
 * 
 * @param <ChatModelConfigType> the type of chat model configuration
 * @param <ModelChoice>         the type of model choice
 * @param <HandlerIfaceType>    the type of handler interface for chat model
 *                              configuration support
 */
public class BaseChatModelsConfigurationController<ChatModelConfigType extends GBaseChatModelConfig, ModelChoice extends GBaseChatModelChoice, HandlerIfaceType extends IGChatModelConfigurationSupportService<ModelChoice, ChatModelConfigType>>
		extends AbstractBaseChatModelsConfigurationCRUDController<ChatModelConfigType, ModelChoice> {
	protected final HandlerIfaceType ifaceType;
	public BaseChatModelsConfigurationController(IGPersistentObjectManager persistentObjectManager,
			IGChatModelRuntimeConfigurationDao modelRuntimeConfigurationDao, Class<ChatModelConfigType> type, HandlerIfaceType ifaceType) {
		super(persistentObjectManager, modelRuntimeConfigurationDao, type);
		this.ifaceType = ifaceType;

	}

	// Interface for handling chat model configuration support services.
	
	

	/**
	 * Retrieves a list of model choices based on the specified chat model
	 * configuration type. This method interfaces with the underlying service to
	 * obtain model choices.
	 *
	 * @param type the chat model configuration type
	 * @return the operation status containing a list of model choices
	 */
	@Override
	protected OperationStatus<List<ModelChoice>> getModelChoices(ChatModelConfigType type) {
		return this.ifaceType.getModelChoices(type);
	}
}