/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.handler;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GVirtualFilesystemProjectEndpoint;
import ai.gebo.model.annotations.EntityDescription;

/**
 * AI generated comments
 * 
 * This class represents an endpoint configuration for connecting to Atlassian Confluence.
 * It extends the virtual filesystem project endpoint to provide Confluence-specific functionality,
 * allowing for extraction and management of Confluence content.
 */
@EntityDescription(description = "Atlassian Confluence  documents source", entityCategory = GProjectEndpoint.class)
@Document
public class GConfluenceProjectEndpoint extends GVirtualFilesystemProjectEndpoint {
	/**
	 * Defines the available formats for extracting content from Confluence
	 */
	public static enum ExtractedType {
		HTML,WORD,PDF
	}
	
	/**
	 * The identifier code for the Confluence system to connect to
	 */
	private String confluenceSystemCode = null;
	
	/**
	 * The format in which to extract Confluence content
	 */
	private ExtractedType extractedFormat=null;
	
	/**
	 * Flag indicating whether to extract and save the Confluence contents
	 */
	private Boolean extractAndSaveContents=null;
	
	/**
	 * The version of Confluence being connected to
	 */
	private ConfluenceVersion confluenceVersion=null;
	
	/**
	 * Default constructor for GConfluenceProjectEndpoint
	 */
	public GConfluenceProjectEndpoint() {

	}

	/**
	 * Gets the Confluence system code identifier
	 * 
	 * @return the Confluence system code
	 */
	public String getConfluenceSystemCode() {
		return confluenceSystemCode;
	}

	/**
	 * Sets the Confluence system code identifier
	 * 
	 * @param confluenceOnpremiseSystemCode the system code to set
	 */
	public void setConfluenceSystemCode(String confluenceOnpremiseSystemCode) {
		this.confluenceSystemCode = confluenceOnpremiseSystemCode;
	}

	/**
	 * Gets the format used for extracting Confluence content
	 * 
	 * @return the extraction format type
	 */
	public ExtractedType getExtractedFormat() {
		return extractedFormat;
	}

	/**
	 * Sets the format used for extracting Confluence content
	 * 
	 * @param extractedFormat the extraction format to use
	 */
	public void setExtractedFormat(ExtractedType extractedFormat) {
		this.extractedFormat = extractedFormat;
	}

	/**
	 * Gets whether Confluence contents should be extracted and saved
	 * 
	 * @return true if contents should be extracted and saved, false otherwise
	 */
	public Boolean getExtractAndSaveContents() {
		return extractAndSaveContents;
	}

	/**
	 * Sets whether Confluence contents should be extracted and saved
	 * 
	 * @param extractAndSaveContents true to extract and save contents, false otherwise
	 */
	public void setExtractAndSaveContents(Boolean extractAndSaveContents) {
		this.extractAndSaveContents = extractAndSaveContents;
	}

	/**
	 * Gets the Confluence version being used
	 * 
	 * @return the Confluence version
	 */
	public ConfluenceVersion getConfluenceVersion() {
		return confluenceVersion;
	}

	/**
	 * Sets the Confluence version to use
	 * 
	 * @param confluenceVersion the Confluence version
	 */
	public void setConfluenceVersion(ConfluenceVersion confluenceVersion) {
		this.confluenceVersion = confluenceVersion;
	}
}