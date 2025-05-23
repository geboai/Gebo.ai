/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.multilanguage.support.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Gebo.ai comment agent
 * 
 * Represents a collection of language resources, where each resource is a type 
 * of LanguageResource. This class implements the LanguageResource interface itself.
 * 
 * @param <T> the type of LanguageResource contained within this collection
 */
public class LanguageCollection<T extends LanguageResource> implements LanguageResource{
	
	/** The language code representing the language of the resources */
	private String langCode = null;

	/** The identifier for this collection of resources */
	private String id = null;

	/** A list of language resources in this collection */
	private List<T> resources = new ArrayList<T>();

	/**
	 * Retrieves the list of language resources in this collection.
	 * 
	 * @return the list of resources
	 */
	public List<T> getResources() {
		return resources;
	}

	/**
	 * Sets the list of language resources for this collection.
	 * 
	 * @param resources the new list of resources to be set
	 */
	public void setResources(List<T> resources) {
		this.resources = resources;
	}

	/**
	 * Gets the language code for this collection of resources.
	 * 
	 * @return the language code
	 */
	public String getLangCode() {
		return langCode;
	}

	/**
	 * Sets the language code for this collection of resources.
	 * 
	 * @param langCode the language code to be set
	 */
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	/**
	 * Gets the identifier for this collection.
	 * 
	 * @return the collection id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the identifier for this collection of resources.
	 * 
	 * @param id the id to be set for this collection
	 */
	public void setId(String id) {
		this.id = id;
	}

}