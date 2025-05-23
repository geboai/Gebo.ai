/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.userspace.handler.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * Data Transfer Object (DTO) representing a userspace knowledgebase.
 * This class holds information about a knowledgebase within a user's workspace,
 * including its identification, description, ownership status, parent relationships,
 * and access control information.
 */
public class UserspaceKnowledgebaseDto {
	/** Unique identifier code for the knowledgebase */
	public String code = null;
	
	/** Required description of the knowledgebase */
	public @NotNull String description = null;
	
	/** Flag indicating whether the current user owns this knowledgebase */
	public boolean owned = false;
	
	/** Code reference to a parent knowledgebase if this is a child/sub-knowledgebase */
	public String parentKnowledgebaseCode = null;
	
	/** List of group identifiers that have access to this knowledgebase */
	public List<String> accessibleGroups = null;
}