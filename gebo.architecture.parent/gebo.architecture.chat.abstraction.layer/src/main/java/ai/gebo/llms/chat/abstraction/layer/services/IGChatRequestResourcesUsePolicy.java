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

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.model.ChatModelLimitedRequest;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatRequest;

/*****************************************************************
 * Strategical business logic that balances between documents, history, query
 * and free space for tools in an interaction with a large language model to
 * fulfill the capability of it to manage data without exceeding the total
 * number of handled tokens.
 * 
 * Gebo.ai comment agent
 */
public interface IGChatRequestResourcesUsePolicy {

	/**
	 * Manages a chat request by considering profile configurations, user context,
	 * the request itself, and handles for embedding and chat models, along with
	 * visible knowledge bases.
	 *
	 * @param chatProfile              The profile configuration affecting chat behavior.
	 * @param userContext              The user-specific context for the chat.
	 * @param request                  The chat request details.
	 * @param embeddingHandler         The handler for embedding model configuration.
	 * @param chatHandler              The handler for chat model configuration.
	 * @param visibleKnowledgeBaseCodes List of codes representing visible knowledge bases.
	 * @return                         A limited request ready for processing by the chat model.
	 * @throws LLMConfigException      Thrown when there is a configuration issue with LLM.
	 */
	public ChatModelLimitedRequest manageRequest(GChatProfileConfiguration chatProfile, GUserChatContext userContext,
			GeboChatRequest request, IGConfigurableEmbeddingModel  embeddingHandler, IGConfigurableChatModel chatHandler,
			List<String> visibleKnowledgeBaseCodes) throws LLMConfigException;
}