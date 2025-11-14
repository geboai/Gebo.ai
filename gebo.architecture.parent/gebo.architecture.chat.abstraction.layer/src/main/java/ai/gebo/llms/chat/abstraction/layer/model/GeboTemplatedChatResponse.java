/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.CalledFunction;
import ai.gebo.model.GUserMessage;

/**
 * AI generated comments
 * GeboTemplatedChatResponse is a class representing the response of a chat-based interaction.
 * It holds various details about the chat interaction such as the context, chat model information,
 * user messages, document references, and other related metadata.
 *
 * @param <ResponseType> The type of the query response.
 */
public class GeboTemplatedChatResponse<ResponseType> implements Serializable {
	protected String id=null;
	protected String userChatContextCode = null; // Context code for user chat session
	protected String usedChatModelCode = null; // Code of the chat model used in the response
	protected String usedChatModelProvider = null; // Provider of the chat model used
	protected ResponseType queryResponse = null; // The response to the chat query
	protected GeboWorkingMemoryWindowOccupation windowOccupation = null; // Occupation details of the working memory window
	protected String query = null; // The query string for which this response is generated
	protected List<GUserMessage> backendMessages = new ArrayList<GUserMessage>(); // List of backend messages related to the query
	protected List<GResponseDocumentRef> forcedDocumentsRef = new ArrayList<GResponseDocumentRef>(); // Forced document references
	protected List<GResponseDocumentRef> documentsRef = new ArrayList<GResponseDocumentRef>(); // Document references
	protected List<CalledFunction> calledFunctions = new ArrayList<LLMtInteractionContextThreadLocal.CalledFunction>(); // List of functions called during the interaction
	protected ChatModelRequestContextWindowStats contextWindowStats = null; // Statistics related to the context window

	/**
	 * Default constructor for GeboTemplatedChatResponse.
	 */
	public GeboTemplatedChatResponse() {

	}
	
	/**
	 * Copy constructor for GeboTemplatedChatResponse.
	 * Initializes a new response by copying properties from an existing response.
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

	/**
	 * Gets the user chat context code.
	 *
	 * @return The user chat context code.
	 */
	public String getUserChatContextCode() {
		return userChatContextCode;
	}

	/**
	 * Sets the user chat context code.
	 *
	 * @param userChatContextCode The new user chat context code.
	 */
	public void setUserChatContextCode(String userChatContextCode) {
		this.userChatContextCode = userChatContextCode;
	}

	/**
	 * Gets the used chat model code.
	 *
	 * @return The used chat model code.
	 */
	public String getUsedChatModelCode() {
		return usedChatModelCode;
	}

	/**
	 * Sets the used chat model code.
	 *
	 * @param usedChatModelCode The new chat model code.
	 */
	public void setUsedChatModelCode(String usedChatModelCode) {
		this.usedChatModelCode = usedChatModelCode;
	}

	/**
	 * Gets the used chat model provider.
	 *
	 * @return The used chat model provider.
	 */
	public String getUsedChatModelProvider() {
		return usedChatModelProvider;
	}

	/**
	 * Sets the used chat model provider.
	 *
	 * @param usedChatModelProvider The new chat model provider.
	 */
	public void setUsedChatModelProvider(String usedChatModelProvider) {
		this.usedChatModelProvider = usedChatModelProvider;
	}

	/**
	 * Gets the query response.
	 *
	 * @return The query response of type ResponseType.
	 */
	public ResponseType getQueryResponse() {
		return queryResponse;
	}

	/**
	 * Sets the query response.
	 *
	 * @param queryResponse The new query response.
	 */
	public void setQueryResponse(ResponseType queryResponse) {
		this.queryResponse = queryResponse;
	}

	/**
	 * Gets the query string.
	 *
	 * @return The query string.
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * Sets the query string.
	 *
	 * @param query The new query string.
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * Gets the list of backend messages.
	 *
	 * @return The list of backend messages.
	 */
	public List<GUserMessage> getBackendMessages() {
		return backendMessages;
	}

	/**
	 * Sets the list of backend messages.
	 *
	 * @param backendMessages The new list of backend messages.
	 */
	public void setBackendMessages(List<GUserMessage> backendMessages) {
		this.backendMessages = backendMessages;
	}

	/**
	 * Gets the list of document references.
	 *
	 * @return The list of document references.
	 */
	public List<GResponseDocumentRef> getDocumentsRef() {
		return documentsRef;
	}

	/**
	 * Sets the list of document references.
	 *
	 * @param documentsRef The new list of document references.
	 */
	public void setDocumentsRef(List<GResponseDocumentRef> documentsRef) {
		this.documentsRef = documentsRef;
	}

	/**
	 * Gets the list of called functions.
	 *
	 * @return The list of called functions.
	 */
	public List<CalledFunction> getCalledFunctions() {
		return calledFunctions;
	}

	/**
	 * Sets the list of called functions.
	 *
	 * @param calledFunctions The new list of called functions.
	 */
	public void setCalledFunctions(List<CalledFunction> calledFunctions) {
		this.calledFunctions = calledFunctions;
	}

	/**
	 * Gets the list of forced document references.
	 *
	 * @return The list of forced document references.
	 */
	public List<GResponseDocumentRef> getForcedDocumentsRef() {
		return forcedDocumentsRef;
	}

	/**
	 * Sets the list of forced document references.
	 *
	 * @param forcedDocumentsRef The new list of forced document references.
	 */
	public void setForcedDocumentsRef(List<GResponseDocumentRef> forcedDocumentsRef) {
		this.forcedDocumentsRef = forcedDocumentsRef;
	}

	/**
	 * Gets the window occupation details.
	 *
	 * @return The window occupation details.
	 */
	public GeboWorkingMemoryWindowOccupation getWindowOccupation() {
		return windowOccupation;
	}

	/**
	 * Sets the window occupation details.
	 *
	 * @param windowOccupation The new window occupation details.
	 */
	public void setWindowOccupation(GeboWorkingMemoryWindowOccupation windowOccupation) {
		this.windowOccupation = windowOccupation;
	}

	/**
	 * Gets the context window statistics.
	 *
	 * @return The context window statistics.
	 */
	public ChatModelRequestContextWindowStats getContextWindowStats() {
		return contextWindowStats;
	}

	/**
	 * Sets the context window statistics.
	 *
	 * @param contextWindowStats The new context window statistics.
	 */
	public void setContextWindowStats(ChatModelRequestContextWindowStats contextWindowStats) {
		this.contextWindowStats = contextWindowStats;
	}

	/**
	 * Gets the unique identifier of the response.
	 *
	 * @return The unique identifier of the response.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the unique identifier of the response.
	 *
	 * @param id The new unique identifier of the response.
	 */
	public void setId(String id) {
		this.id = id;
	}

}