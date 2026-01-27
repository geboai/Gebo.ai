/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.CalledFunction;
import ai.gebo.llms.chat.abstraction.layer.model.ChatModelRequestContextWindowStats;
import ai.gebo.llms.chat.abstraction.layer.model.GeboWorkingMemoryWindowOccupation;
import ai.gebo.model.GUserMessage;
import lombok.Data;

/**
 * AI generated comments GeboTemplatedChatResponse is a class representing the
 * response of a chat-based interaction. It holds various details about the chat
 * interaction such as the context, chat model information, user messages,
 * document references, and other related metadata.
 *
 * @param <ResponseType> The type of the query response.
 */
@Data
public class GeboTemplatedChatResponse<ResponseType> implements Serializable {
	private String id = null;
	private String userChatContextCode = null; // Context code for user chat session
	private String usedChatModelCode = null; // Code of the chat model used in the response
	private String usedChatModelProvider = null; // Provider of the chat model used
	private ResponseType queryResponse = null; // The response to the chat query
	private GeboWorkingMemoryWindowOccupation windowOccupation = null; // Occupation details of the working memory
																			// window
	private String query = null; // The query string for which this response is generated
	private List<String> thinkingOutputs = null;
	private List<GUserMessage> backendMessages = new ArrayList<GUserMessage>(); // List of backend messages related to
																					// the query
	private List<GResponseDocumentRef> forcedDocumentsRef = new ArrayList<GResponseDocumentRef>(); // Forced document
																										// references
	private List<GResponseDocumentRef> documentsRef = new ArrayList<GResponseDocumentRef>(); // Document references
	// List of functions called during the	 interaction
	private List<CalledFunction> calledFunctions = new ArrayList<LLMtInteractionContextThreadLocal.CalledFunction>(); 
	private ChatModelRequestContextWindowStats contextWindowStats = null; // Statistics related to the context window
	private List<LLMGeneratedResource> generatedResources = new ArrayList<>();

	/**
	 * Default constructor for GeboTemplatedChatResponse.
	 */
	public GeboTemplatedChatResponse() {

	}

	/**
	 * Copy constructor for GeboTemplatedChatResponse. Initializes a new response by
	 * copying properties from an existing response.
	 *
	 * @param r The existing response to copy.
	 */
	public GeboTemplatedChatResponse(GeboTemplatedChatResponse<ResponseType> r) {
		userChatContextCode = r.userChatContextCode;
		usedChatModelCode = r.usedChatModelCode;
		usedChatModelProvider = r.usedChatModelProvider;
		forcedDocumentsRef = r.forcedDocumentsRef;
		queryResponse = r.queryResponse;
		query = r.query;
		backendMessages = r.backendMessages;
		documentsRef = r.documentsRef;
		calledFunctions = r.calledFunctions;
	}


}