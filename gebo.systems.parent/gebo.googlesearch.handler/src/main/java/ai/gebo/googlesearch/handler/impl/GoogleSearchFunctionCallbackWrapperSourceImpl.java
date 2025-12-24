/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googlesearch.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.IGToolCallbackSource;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.googlesearch.handler.model.GoogleSearchConfig;

/**
 * AI generated comments
 * 
 * Implementation of IGToolCallbackSource that provides tool callbacks for Google Search
 * and Bing Search functionality. This service serves as a wrapper to expose search APIs
 * to the AI system.
 */
@Service
public class GoogleSearchFunctionCallbackWrapperSourceImpl implements IGToolCallbackSource {
	// Logger for this class
	static final Logger LOGGER = LoggerFactory.getLogger(GoogleSearchFunctionCallbackWrapperSourceImpl.class);
	
	// Data access object for Google Search configurations
	@Autowired
	GoogleSearchConfigDaoImpl dao;
	
	// Google Search API implementation
	@Autowired
	GoogleSearchApi googleApi;
	
	// Simple Bing Search API implementation
	@Autowired
	SimpleBingSearchApi bingSearchApi;

	/**
	 * Default constructor for GoogleSearchFunctionCallbackWrapperSourceImpl
	 */
	public GoogleSearchFunctionCallbackWrapperSourceImpl() {

	}

	/**
	 * Returns the unique identifier for this tool callback source
	 * 
	 * @return the fully qualified class name as the ID
	 */
	@Override
	public String getId() {

		return this.getClass().getName();
	}

	/**
	 * Provides the list of tool callbacks for search functionality
	 * 
	 * @return a list containing Bing Search and Google Search tool callbacks (if configured)
	 */
	@Override
	public List<ToolCallback> getToolCallbacks() {
		List<ToolCallback> out = new ArrayList<ToolCallback>();
		List<GoogleSearchConfig> configurations = dao.getConfigurations();
		out.add(bingSearchApi.createSimplebingSearch());
		// Add Google Search only if there are configurations available
		if (!configurations.isEmpty()) {
			out.add(googleApi.create(configurations.get(0)));
		}
		return out;
	}

	/**
	 * Specifies the category for these tools
	 * 
	 * @return the INTERNET_BROWSING tool category
	 */
	@Override
	public ToolsCategory getToolCategory() {

		return ToolsCategory.INTERNET_BROWSING;
	}

	/**
	 * Returns a list of full tool references
	 * 
	 * @return an empty list as this implementation doesn't provide full tool references
	 */
	@Override
	public List<ToolReference> getFullToolReferences() {

		return List.of();
	}
}