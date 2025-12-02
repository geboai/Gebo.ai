/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;
import lombok.Data;

/**
 * AI generated comments Represents a user chat context within the chat
 * abstraction layer model. Stores details about chat interactions and
 * configurations for a user.
 */
@Document
@Data
public class GUserChatContext extends GBaseObject {
	

	private Date chatCreationDateTime = null; // Timestamp for chat creation
	@HashIndexed
	private String username = null; // Username for the chat context
	@GObjectReference(referencedType = GChatProfileConfiguration.class)
	private String chatProfileCode = null; // Chat profile configuration code
	private GObjectRef<GBaseChatModelConfig> modelReference = null; // Reference to the chat model configuration
	private Boolean ragChat = null; // Indicates if the chat supports Retrieval-Augmented Generation
	private String chatMemoryId = null; // Identifier for chat memory
	private List<ChatInteractions> interactions = null; // List of chat interactions
	private String chatModelCode = null; // Code for the chat model used
	private List<String> choosedKnowledgeBases = null; // List of chosen knowledge bases for the chat

	private GUserChatConsolidationData consolidation = null;

	/** Default constructor for GUserChatContext */
	public GUserChatContext() {

	}

}