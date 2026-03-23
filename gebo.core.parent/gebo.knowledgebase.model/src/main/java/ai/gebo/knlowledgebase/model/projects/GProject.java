/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.knlowledgebase.model.projects;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.acl.GAclEntry;
import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.contents.ObjectSpaceType;
import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.annotations.EntityDescription;
import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.model.base.GBaseVersionableObject;
import lombok.Data;

/**
 * AI generated comments Represents a project or item entity documented for use
 * with MongoDB. This class extends GBaseVersionableObject and implements
 * IGObjectWithSecurity.
 */
@Document
@EntityDescription(description = "Project/item")
@Data
public class GProject extends GBaseVersionableObject implements IGObjectWithSecurity, IAclGrantedResource {

	// List of group IDs or names that have access to this project.
	private List<String> accessibleGroups = null;

	// List of user IDs or usernames that have access to this project.
	private List<String> accessibleUsers = null;

	// Indicates whether the project is accessible to all users.
	private Boolean accessibleToAll = null;

	// Reference to the root knowledge base associated with this project.
	@GObjectReference(referencedType = GKnowledgeBase.class)
	private String rootKnowledgeBaseCode = null;

	// Type of object space associated with this project.
	private ObjectSpaceType objectSpaceType = null;
	
	private List<Integer> aclAliases = null;
	/**
	 * Serialization identifier for ensuring class consistency during serialization.
	 */
	private static final long serialVersionUID = 5109012601323753322L;

	// Code of the parent project, if any.
	private String parentProjectCode = null;

}