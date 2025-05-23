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
import java.util.UUID;

/**
 * GeboTemplatedChatRequest is a generic class for managing chat requests 
 * with type parameter <RequestType> representing the type of query.
 * It provides various configurations required for processing chat requests.
 * 
 * AI generated comments
 * 
 * @param <RequestType> The type of the query used in the chat request.
 */
public class GeboTemplatedChatRequest<RequestType> implements Serializable {

	/** Unique identifier for each chat request instance */
	protected String id = UUID.randomUUID().toString();
	
	/** Code representing the user's chat context */
	protected String userChatContextCode = null;
	
	/** Code representing the chat profile used for the request */
	protected String chatProfileCode = null;
	
	/** Code representing the chat model being used */
	protected String chatModelCode = null;
	
	/** Flag to determine whether responses should be streamed */
	protected boolean streamResponse = false;
	
	/** The chat query, parameterized by RequestType */
	protected RequestType query = null;
	
	/** Custom configuration for RAG (Retrieval-Augmented Generation) requests */
	protected GeboRagRequestCustomConfig customRagConfig = null;
	
	/** List of knowledge bases selected for the request */
	private List<String> choosedKnowledgeBases = null;
	
	/** List of documents that are forcibly requested */
	protected List<String> forcedRequestDocuments = new ArrayList<String>();

	/**
	 * Default constructor for GeboTemplatedChatRequest.
	 */
	public GeboTemplatedChatRequest() {

	}

	/**
	 * @return the user's chat context code.
	 */
	public String getUserChatContextCode() {
		return userChatContextCode;
	}

	/**
	 * Sets the user chat context code.
	 * 
	 * @param userChatContextCode The code to set.
	 */
	public void setUserChatContextCode(String userChatContextCode) {
		this.userChatContextCode = userChatContextCode;
	}

	/**
	 * @return the chat profile code.
	 */
	public String getChatProfileCode() {
		return chatProfileCode;
	}

	/**
	 * Sets the chat profile code.
	 * 
	 * @param chatProfileCode The code to set.
	 */
	public void setChatProfileCode(String chatProfileCode) {
		this.chatProfileCode = chatProfileCode;
	}

	/**
	 * @return the chat model code.
	 */
	public String getChatModelCode() {
		return chatModelCode;
	}

	/**
	 * Sets the chat model code.
	 * 
	 * @param chatModelCode The code to set.
	 */
	public void setChatModelCode(String chatModelCode) {
		this.chatModelCode = chatModelCode;
	}

	/**
	 * @return the query for the chat request.
	 */
	public RequestType getQuery() {
		return query;
	}

	/**
	 * Sets the query for the chat request.
	 * 
	 * @param query The query to set.
	 */
	public void setQuery(RequestType query) {
		this.query = query;
	}

	/**
	 * @return the list of forced request documents.
	 */
	public List<String> getForcedRequestDocuments() {
		return forcedRequestDocuments;
	}

	/**
	 * Sets the list of forced request documents.
	 * 
	 * @param forcedRequestDocuments The documents to set.
	 */
	public void setForcedRequestDocuments(List<String> forcedRequestDocuments) {
		this.forcedRequestDocuments = forcedRequestDocuments;
	}

	/**
	 * @return the custom RAG configuration.
	 */
	public GeboRagRequestCustomConfig getCustomRagConfig() {
		return customRagConfig;
	}

	/**
	 * Sets the custom RAG configuration.
	 * 
	 * @param customRagConfig The configuration to set.
	 */
	public void setCustomRagConfig(GeboRagRequestCustomConfig customRagConfig) {
		this.customRagConfig = customRagConfig;
	}

	/**
	 * @return the list of chosen knowledge bases.
	 */
	public List<String> getChoosedKnowledgeBases() {
		return choosedKnowledgeBases;
	}

	/**
	 * Sets the list of chosen knowledge bases.
	 * 
	 * @param choosedKnowledgeBases The knowledge bases to set.
	 */
	public void setChoosedKnowledgeBases(List<String> choosedKnowledgeBases) {
		this.choosedKnowledgeBases = choosedKnowledgeBases;
	}

	/**
	 * @return true if the response should be streamed, false otherwise.
	 */
	public boolean isStreamResponse() {
		return streamResponse;
	}

	/**
	 * Sets whether the response should be streamed.
	 * 
	 * @param streamResponse The streaming flag to set.
	 */
	public void setStreamResponse(boolean streamResponse) {
		this.streamResponse = streamResponse;
	}

	/**
	 * @return the unique identifier for the chat request.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the unique identifier for the chat request.
	 * 
	 * @param id The unique identifier to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

}