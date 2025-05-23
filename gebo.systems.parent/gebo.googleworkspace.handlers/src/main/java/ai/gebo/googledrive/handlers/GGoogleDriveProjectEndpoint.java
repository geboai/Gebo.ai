/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GVirtualFilesystemProjectEndpoint;
import ai.gebo.model.annotations.EntityDescription;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * 
 * A project endpoint that connects to Google Drive/Workspace documents.
 * This class extends the virtual filesystem project endpoint to support
 * operations on Google Drive documents as a source for projects.
 * The class is annotated as a MongoDB document for persistence.
 */
@EntityDescription(description = "Google drive/workspace documents source", entityCategory = GProjectEndpoint.class)
@Document
public class GGoogleDriveProjectEndpoint extends GVirtualFilesystemProjectEndpoint {
	/**
	 * The unique code that identifies the Google Drive system to connect to.
	 * This field is required and cannot be null.
	 */
	@NotNull
	private String driveSystemCode=null;
	
	/**
	 * Default constructor for creating a new Google Drive project endpoint.
	 */
	public GGoogleDriveProjectEndpoint() {
		
	}
	
	/**
	 * Gets the system code for the Google Drive.
	 * 
	 * @return the drive system code
	 */
	public String getDriveSystemCode() {
		return driveSystemCode;
	}
	
	/**
	 * Sets the system code for the Google Drive.
	 * 
	 * @param driveSystemCode the drive system code to set
	 */
	public void setDriveSystemCode(String driveSystemCode) {
		this.driveSystemCode = driveSystemCode;
	}
		

}