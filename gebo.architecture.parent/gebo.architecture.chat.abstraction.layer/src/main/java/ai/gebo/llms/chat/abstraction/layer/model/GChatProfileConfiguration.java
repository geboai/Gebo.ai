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
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;

/**
 * AI generated comments
 * Represents the configuration for a chat profile, extending the base object class
 * and implementing security-related functionality through IGObjectWithSecurity.
 */
@Document
public class GChatProfileConfiguration extends GBaseObject implements IGObjectWithSecurity {
	
	/**
	 * Constant for the default chat profile code.
	 */
	public static final String DEFAULT_CHAT_PROFILE_CODE = "default-rag-chat-profile";
	
	/**
	 * The prompt used in the chat profile.
	 */
	private String prompt = null;

	/**
	 * Reference to the embedding model configuration.
	 */
	private GObjectRef<GBaseEmbeddingModelConfig> embeddingModelReference = null;

	/**
	 * Reference to the chat model configuration.
	 */
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

	/**
	 * Default constructor for GChatProfileConfiguration.
	 */
	public GChatProfileConfiguration() {

	}

	/**
	 * Gets the prompt for the chat profile.
	 * 
	 * @return the current prompt.
	 */
	public String getPrompt() {
		return prompt;
	}

	/**
	 * Sets the prompt for the chat profile.
	 * 
	 * @param prompt - the prompt to set.
	 */
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	/**
	 * Gets the reference to the embedding model configuration.
	 * 
	 * @return the embedding model reference.
	 */
	public GObjectRef<GBaseEmbeddingModelConfig> getEmbeddingModelReference() {
		return embeddingModelReference;
	}

	/**
	 * Sets the reference to the embedding model configuration.
	 * 
	 * @param embeddingModelReference - the embedding model reference to set.
	 */
	public void setEmbeddingModelReference(GObjectRef<GBaseEmbeddingModelConfig> embeddingModelReference) {
		this.embeddingModelReference = embeddingModelReference;
	}

	/**
	 * Gets the reference to the chat model configuration.
	 * 
	 * @return the chat model reference.
	 */
	public GObjectRef<GBaseChatModelConfig> getChatModelReference() {
		return chatModelReference;
	}

	/**
	 * Sets the reference to the chat model configuration.
	 * 
	 * @param chatModelReference - the chat model reference to set.
	 */
	public void setChatModelReference(GObjectRef<GBaseChatModelConfig> chatModelReference) {
		this.chatModelReference = chatModelReference;
	}

	/**
	 * Gets the list of knowledge base codes.
	 * 
	 * @return the list of knowledge base codes.
	 */
	public List<String> getKnowledgeBaseCodes() {
		return knowledgeBaseCodes;
	}

	/**
	 * Sets the list of knowledge base codes.
	 * 
	 * @param knowledgeBaseCodes - the knowledge base codes to set.
	 */
	public void setKnowledgeBaseCodes(List<String> knowledgeBaseCodes) {
		this.knowledgeBaseCodes = knowledgeBaseCodes;
	}

	/**
	 * Gets the number of top results to retrieve.
	 * 
	 * @return the topK value.
	 */
	public Integer getTopK() {
		return topK;
	}

	/**
	 * Sets the number of top results to retrieve.
	 * 
	 * @param topK - the topK value to set.
	 */
	public void setTopK(Integer topK) {
		this.topK = topK;
	}

	/**
	 * Gets the list of accessible groups.
	 * 
	 * @return the accessible groups list.
	 */
	public List<String> getAccessibleGroups() {
		return accessibleGroups;
	}

	/**
	 * Sets the list of accessible groups.
	 * 
	 * @param accessibleGroups - the accessible groups list to set.
	 */
	public void setAccessibleGroups(List<String> accessibleGroups) {
		this.accessibleGroups = accessibleGroups;
	}

	/**
	 * Gets the list of accessible users.
	 * 
	 * @return the accessible users list.
	 */
	public List<String> getAccessibleUsers() {
		return accessibleUsers;
	}

	/**
	 * Sets the list of accessible users.
	 * 
	 * @param accessibleUsers - the accessible users list to set.
	 */
	public void setAccessibleUsers(List<String> accessibleUsers) {
		this.accessibleUsers = accessibleUsers;
	}

	/**
	 * Checks if the chat profile is accessible to all.
	 * 
	 * @return true if accessible to all, otherwise false.
	 */
	public Boolean getAccessibleToAll() {
		return accessibleToAll;
	}

	/**
	 * Sets the accessibility of the chat profile to all.
	 * 
	 * @param accessibleToAll - true to make it accessible to all, otherwise false.
	 */
	public void setAccessibleToAll(Boolean accessibleToAll) {
		this.accessibleToAll = accessibleToAll;
	}

	/**
	 * Gets the list of enabled functions.
	 * 
	 * @return the enabled functions list.
	 */
	public List<String> getEnabledFunctions() {
		return enabledFunctions;
	}

	/**
	 * Sets the list of enabled functions for the chat profile.
	 * 
	 * @param enabledFunctionsList - the enabled functions list to set.
	 */
	public void setEnabledFunctions(List<String> enabledFunctionsList) {
		this.enabledFunctions = enabledFunctionsList;
	}

	/**
	 * Checks if the user can choose knowledge bases.
	 * 
	 * @return true if user can choose, otherwise false.
	 */
	public Boolean getUserChoosesKnowledgeBases() {
		return userChoosesKnowledgeBases;
	}

	/**
	 * Sets the option for user to choose knowledge bases.
	 * 
	 * @param userChoosesKnowledgeBases - true if user can choose, otherwise false.
	 */
	public void setUserChoosesKnowledgeBases(Boolean userChoosesKnowledgeBases) {
		this.userChoosesKnowledgeBases = userChoosesKnowledgeBases;
	}

	/**
	 * Gets the list of forced request documents.
	 * 
	 * @return the forced request documents list.
	 */
	public List<String> getForcedRequestDocuments() {
		return forcedRequestDocuments;
	}

	/**
	 * Sets the list of forced request documents.
	 * 
	 * @param forcedRequestDocuments - the forced request documents list to set.
	 */
	public void setForcedRequestDocuments(List<String> forcedRequestDocuments) {
		this.forcedRequestDocuments = forcedRequestDocuments;
	}

	/**
	 * Checks if the forced request documents are read-only.
	 * 
	 * @return true if read-only, otherwise false.
	 */
	public Boolean getForcedRequestDocumentsReadonly() {
		return forcedRequestDocumentsReadonly;
	}

	/**
	 * Sets the read-only status of forced request documents.
	 * 
	 * @param forcedRequestDocumentsReadonly - true to make read-only, otherwise false.
	 */
	public void setForcedRequestDocumentsReadonly(Boolean forcedRequestDocumentsReadonly) {
		this.forcedRequestDocumentsReadonly = forcedRequestDocumentsReadonly;
	}

	/**
	 * Gets the similarity search threshold.
	 * 
	 * @return the similarity search threshold.
	 */
	public Double getSimilaritySearchThreshold() {
		return similaritySearchThreshold;
	}

	/**
	 * Sets the similarity search threshold.
	 * 
	 * @param similaritySearchThreshold - the threshold to set.
	 */
	public void setSimilaritySearchThreshold(Double similaritySearchThreshold) {
		this.similaritySearchThreshold = similaritySearchThreshold;
	}

	/**
	 * Checks if multi-hop RAG is disabled.
	 * 
	 * @return true if disabled, otherwise false.
	 */
	public Boolean getDisableMultiHopRag() {
		return disableMultiHopRag;
	}

	/**
	 * Sets the option to disable multi-hop RAG.
	 * 
	 * @param disableMultiHopRag - true to disable, otherwise false.
	 */
	public void setDisableMultiHopRag(Boolean disableMultiHopRag) {
		this.disableMultiHopRag = disableMultiHopRag;
	}

	/**
	 * Gets the threshold for other search similarity.
	 * 
	 * @return the other search similarity threshold.
	 */
	public Double getOtherSearchSimilarityThreshold() {
		return otherSearchSimilarityThreshold;
	}

	/**
	 * Sets the threshold for other search similarity.
	 * 
	 * @param otherSearchSimilarityThreshold - the threshold to set.
	 */
	public void setOtherSearchSimilarityThreshold(Double otherSearchSimilarityThreshold) {
		this.otherSearchSimilarityThreshold = otherSearchSimilarityThreshold;
	}

}