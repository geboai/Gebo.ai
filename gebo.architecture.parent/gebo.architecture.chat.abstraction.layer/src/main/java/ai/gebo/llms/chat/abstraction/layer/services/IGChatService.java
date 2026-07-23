/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.services;

import java.util.List;

import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.model.GShortModelInfo;

/**
 * Gebo.ai comment agent
 * 
 * The IGChatService interface defines the operations for managing chat models.
 * It extends the IGGenericalChatService interface, enriching it with specific
 * methods for chat model configurations. Speech and transcript
 * functionalities have been moved to dedicated {@link IGTextToSpeechService}
 * and {@link IGTranscriptService} interfaces.
 */
public interface IGChatService extends IGGenericalChatService {

	/**
	 * Retrieves a list of short model information containing configuration details
	 * for available chat models.
	 *
	 * @return a List of GShortModelInfo objects representing model configurations.
	 */
	public List<GShortModelInfo> getModelsConfigurationList();

	/**
	 * Provides metadata information for a specific chat model based on the given
	 * model code.
	 *
	 * @param modelCode a unique code identifying the chat model.
	 * @return a GBaseChatModelChoice object containing meta information about the
	 *         chat model.
	 */
	public GBaseChatModelChoice getChatModelMetaInfo(String modelCode);

	/**
	 * Retrieves the provider-specific capabilities or features for a given chat
	 * model.
	 *
	 * @param modelCode a unique code identifying the chat model.
	 * @return a ModelProviderCapabilities object that contains the capabilities of
	 *         the model provider.
	 */
	public ModelProviderCapabilities getProviderCapabilities(String modelCode);

	public List<GKnowledgeBase> getVisibleKnowledgeBases();
}