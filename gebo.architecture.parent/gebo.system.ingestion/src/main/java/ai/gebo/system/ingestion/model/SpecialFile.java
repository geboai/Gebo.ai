/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.model;

/**
 * Represents a special file in the ingestion system.
 * This class serves as a simple data model to store information about files that 
 * require special handling during ingestion.
 * 
 * AI generated comments
 */
public class SpecialFile {
	/** Stores the name of the file */
	private String fileName = null;
	
	/** Stores the description of the file */
	private String description = null;

	/**
	 * Default constructor that initializes a new SpecialFile instance
	 * with null values for fileName and description.
	 */
	public SpecialFile() {

	}

	/**
	 * Gets the file name.
	 * 
	 * @return the name of the file
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name.
	 * 
	 * @param fileName the name of the file to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Gets the description of the file.
	 * 
	 * @return the description of the file
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the file.
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}