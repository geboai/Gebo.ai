/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;
import lombok.Data;

/**
 * AI generated comments Represents the configuration for a chat profile,
 * extending the base object class and implementing security-related
 * functionality through IGObjectWithSecurity.
 */
@Document
@Data
public class GChatProfileConfiguration extends GBaseObject implements IGObjectWithSecurity {

	/**
	 * Constant for the default chat profile code.
	 */
	public static final String DEFAULT_CHAT_PROFILE_CODE = "default-rag-chat-profile";

	/**
	 * Reference to the embedding model configuration.
	 */
	@GObjectReference(referencedType = GBaseEmbeddingModelConfig.class, referencesExtensions = true)
	private GObjectRef<GBaseEmbeddingModelConfig> embeddingModelReference = null;

	/**
	 * Reference to the chat model configuration.
	 */
	@GObjectReference(referencedType = GBaseChatModelConfig.class, referencesExtensions = true)
	private GObjectRef<GBaseChatModelConfig> chatModelReference = null;

	/**
	 * List of enabled functions for this chat profile.
	 */
	private List<String> enabledFunctions = null;

	/**
	 * List of groups that have access to this chat profile.
	 */
	private List<String> accessibleGroups = null;

	/**
	 * List of users that have access to this chat profile.
	 */
	private List<String> accessibleUsers = null;

	/**
	 * Determines if the chat profile is accessible to all.
	 */
	private Boolean accessibleToAll = null;

	/**
	 * Determines if the user can choose knowledge bases.
	 */
	private Boolean userChoosesKnowledgeBases = null;

	/**
	 * Number of top results to retrieve.
	 */
	private Integer topK = null;

	/**
	 * Threshold for similarity search.
	 */
	private Double similaritySearchThreshold = null;

	/**
	 * List of knowledge base codes associated with this chat profile.
	 */
	private List<String> knowledgeBaseCodes = null;

	/**
	 * List of documents that are forced in the request.
	 */
	private List<String> forcedRequestDocuments = null;

	/**
	 * Indicates if the forced request documents are readonly.
	 */
	private Boolean forcedRequestDocumentsReadonly = null;

	/**
	 * Determines if multi-hop RAG is disabled.
	 */
	private Boolean disableMultiHopRag = null;

	/**
	 * Threshold for other search similarity.
	 */
	private Double otherSearchSimilarityThreshold = null;

	private Boolean manualThreasholdsConfiguration = null;

	private Boolean useAlsoKeywordSearch = null;

}