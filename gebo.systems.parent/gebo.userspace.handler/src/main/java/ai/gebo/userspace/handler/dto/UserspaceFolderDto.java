/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.userspace.handler.dto;

import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * 
 * Data Transfer Object (DTO) representing a folder within a user's workspace.
 * This class is used to transfer folder data between different layers of the application.
 */
public class UserspaceFolderDto {
	/** The unique code identifier for the folder */
	public String code = null;
	
	/** The description of the folder. This field cannot be null */
	@NotNull
	public String description = null;
	
	/** The code of the parent knowledgebase that contains this folder. This field cannot be null */
	@NotNull
	public String parentUserspaceKnowledgebaseCode = null;
	
	/** The upload code associated with this folder, if applicable */
	public String uploadCode = null;
	
	/** Flag indicating whether the current user is the owner of this folder */
	public boolean owner=false;

}