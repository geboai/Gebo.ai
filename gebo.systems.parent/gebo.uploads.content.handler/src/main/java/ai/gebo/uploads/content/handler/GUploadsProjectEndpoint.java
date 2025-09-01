/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.uploads.content.handler;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.annotations.EntityDescription;

/**
 * AI generated comments
 * 
 * Represents a project endpoint for uploaded documents in the Gebo system.
 * This class extends GProjectEndpoint to provide specialized functionality
 * for managing uploaded document content.
 * 
 * The class is annotated with @Document for MongoDB persistence and
 * annotation EntityDescription to provide metadata about the entity type.
 */
@Document
@EntityDescription(description = "Uploaded documents", entityCategory = GProjectEndpoint.class)

public class GUploadsProjectEndpoint extends GProjectEndpoint {
	
	
	/**
	 * Code used for handshaking during the upload process.
	 * This helps validate and secure the upload operation.
	 */
	private String uploadHandshakeCode = null;
	
	/**
	 * List of identifiers for contents that have been uploaded through this endpoint.
	 * These may reference documents, files, or other content objects in the system.
	 */
	private List<String> uploadedContents = null;

	/**
	 * Default constructor for creating a new uploads project endpoint.
	 */
	public GUploadsProjectEndpoint() {

	}

	
	

	/**
	 * Retrieves the handshake code used for upload validation.
	 * 
	 * @return The handshake code as a String
	 */
	public String getUploadHandshakeCode() {
		return uploadHandshakeCode;
	}

	/**
	 * Sets the handshake code used for upload validation.
	 * 
	 * @param uploadHandshakeCode The handshake code to set
	 */
	public void setUploadHandshakeCode(String uploadHandshakeCode) {
		this.uploadHandshakeCode = uploadHandshakeCode;
	}

	/**
	 * Retrieves the list of uploaded content identifiers.
	 * 
	 * @return A List of Strings representing the uploaded content identifiers
	 */
	public List<String> getUploadedContents() {
		return uploadedContents;
	}

	/**
	 * Sets the list of uploaded content identifiers.
	 * 
	 * @param uploadedContents The List of content identifiers to set
	 */
	public void setUploadedContents(List<String> uploadedContents) {
		this.uploadedContents = uploadedContents;
	}

}