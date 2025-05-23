/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.uploads.content.handler;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.model.base.GBaseObject;

/**
 * AI generated comments
 * 
 * This class represents a temporary container for uploaded content.
 * It extends GBaseObject to inherit base functionality and maintains
 * a list of uploaded content identifiers as strings.
 */
public class TmpUploadedContents extends GBaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3192262258718814119L;
	
	/**
	 * List that stores the identifiers or paths of uploaded contents
	 */
	private List<String> uploadedContents = new ArrayList<String>();
	
	/**
	 * Default constructor that initializes an empty TmpUploadedContents object
	 */
	public TmpUploadedContents() {

	}
	
	/**
	 * Retrieves the list of uploaded contents
	 * 
	 * @return List containing uploaded content identifiers
	 */
	public List<String> getUploadedContents() {
		return uploadedContents;
	}
	
	/**
	 * Sets the list of uploaded contents
	 * 
	 * @param uploadedContents The list of uploaded content identifiers to set
	 */
	public void setUploadedContents(List<String> uploadedContents) {
		this.uploadedContents = uploadedContents;
	}

}