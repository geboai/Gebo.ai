/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.userspace.handler.dto;

import java.util.Date;

/**
 * AI generated comments
 * 
 * Data Transfer Object (DTO) representing a file in the user's workspace.
 * This class is used to transfer file information between different layers of the application.
 */
public class UserspaceFileDto {
	// Unique identifier for the file
	public String code = null;
	// Name of the file without extension
	public String name = null;
	// File extension (e.g., "pdf", "docx")
	public String extension = null;
	// Reference to the parent upload that contains this file
	public String parentUserspaceUploadCode = null;
	// Flag indicating if the file has been processed
	public boolean processed = false;
	// Time when the file was last modified
	public Date modificationTime = null;
	// Size of the file in bytes
	public Long size = null;
	// Flag indicating if the current user is the owner of this file
	public boolean owner = false;
}