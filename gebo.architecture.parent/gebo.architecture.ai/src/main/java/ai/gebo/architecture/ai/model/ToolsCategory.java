/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.ai.model;

/**
 * Represents a category of tools, specifying code, description, and whether
 * it's related to a knowledge base.
 * 
 * Gebo.ai comment agent
 */
public final class ToolsCategory {

	// Predefined instance for software artifacts searches
	public static final ToolsCategory SOFTWARE_ARTIFACTS_SEARCHES = new ToolsCategory(
			"SOFTWARE_ARTIFACTS_SEARCHES", "Software artifacts meta informations search", true);

	// Predefined instance for knowledge base searches
	public static final ToolsCategory KNOWLEDGE_BASE_VARIOUS_SEARCHES = new ToolsCategory(
			"KNOWLEDGE_BASE_VARIUOUS_SEARCHES", "Knowledge base various searches", true);

	// Predefined instance for general internet browsing
	public static final ToolsCategory INTERNET_BROWSING = new ToolsCategory("INTERNET_BROWSING",
			"Internet searching and browsing", false);

	// Predefined instance for searching Gebo.ai user information
	public static final ToolsCategory GEBO_USERS_SEARCH = new ToolsCategory("GEBO_USERS_INFOS",
			"Gebo.ai users informations search", false);

	// Indicates if the category is related to a knowledge base
	private Boolean knowledgeBaseRelative = null;

	// The unique code identifier for the category
	private String code = null;

	// The description of the category
	private String description = null;

	/**
	 * Default constructor for ToolsCategory.
	 */
	public ToolsCategory() {

	}

	/**
	 * Constructs a ToolsCategory with specified code, description, and knowledge
	 * base relation.
	 * 
	 * @param code                 the unique code identifier
	 * @param description          the description of the category
	 * @param kbr                  indicates if it's knowledge base related
	 */
	public ToolsCategory(String code, String description, boolean kbr) {
		this.code = code;
		this.description = description;
		this.knowledgeBaseRelative = kbr;
	}

	/**
	 * Retrieves the code of the category.
	 * 
	 * @return the code of the category
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code of the category.
	 * 
	 * @param code the new code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Retrieves the description of the category.
	 * 
	 * @return the description of the category
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the category.
	 * 
	 * @param description the new description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Determines if the category is related to a knowledge base.
	 * 
	 * @return true if knowledge base related, false otherwise
	 */
	public Boolean getKnowledgeBaseRelative() {
		return knowledgeBaseRelative;
	}

	/**
	 * Sets whether the category is related to a knowledge base.
	 * 
	 * @param knowledgeBaseRelative the new status to set
	 */
	public void setKnowledgeBaseRelative(Boolean knowledgeBaseRelative) {
		this.knowledgeBaseRelative = knowledgeBaseRelative;
	}
}