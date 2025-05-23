/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.model;

import java.util.ArrayList;
import java.util.List;

/**
 * AI generated comments
 * 
 * Configuration class for ingestion handlers in the system.
 * This class stores the identifier for a handler and the list of file types
 * that the handler can process during ingestion.
 */
public class IngestionHandlerConfig {
	/** The unique identifier for this ingestion handler configuration */
	String id=null;
	
	/** List of file types that can be processed by this ingestion handler */
	List<IngestionFileType> fileTypes=new ArrayList<IngestionFileType>();
	
	/**
	 * Default constructor that initializes an empty ingestion handler configuration.
	 */
	public IngestionHandlerConfig() {
		
	}
	
	/**
	 * Gets the identifier of this ingestion handler configuration.
	 * 
	 * @return the identifier string
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the identifier for this ingestion handler configuration.
	 * 
	 * @param id the identifier to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the list of file types that can be processed by this handler.
	 * 
	 * @return list of ingestion file types
	 */
	public List<IngestionFileType> getFileTypes() {
		return fileTypes;
	}
	
	/**
	 * Sets the list of file types that can be processed by this handler.
	 * 
	 * @param fileTypes the list of file types to set
	 */
	public void setFileTypes(List<IngestionFileType> fileTypes) {
		this.fileTypes = fileTypes;
	}

}