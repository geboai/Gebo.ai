/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.knlowledgebase.model.contents;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.acl.GAclEntry;
import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;
import lombok.Data;

/**
 * GKnowledgeBase class represents a knowledge base document in a MongoDB
 * database. It extends GBaseObject and implements IGObjectWithSecurity for
 * security features. AI generated comments
 */
@Document
@Data
public class GKnowledgeBase extends GBaseObject implements IGObjectWithSecurity, IAclGrantedResource {

	/** Serial version UID for serialization. */
	private static final long serialVersionUID = 2198630339276800089L;

	/** List of groups that have access to this knowledge base. */
	private List<String> accessibleGroups = null;

	/** List of users that have access to this knowledge base. */
	private List<String> accessibleUsers = null;

	/** Indicates if this knowledge base is accessible to all users. */
	private Boolean accessibleToAll = null;

	/** References to other knowledge bases. */
	private List<String> knowledgeBaseReferences = new ArrayList<String>();

	/** References to related projects. */
	private List<String> projectsReferences = new ArrayList<String>();

	/** References to embedding models associated with this knowledge base. */
	private List<GObjectRef> embeddingModelReferences = new ArrayList<GObjectRef>();

	/** Username associated with this knowledge base. */
	private String username = null;

	/** Code of the parent knowledge base, if any. */
	private String parentKnowledgebaseCode = null;

	/** Type of the object space. */
	private ObjectSpaceType objectSpaceType = null;
	// Acl implementation
	private List<GAclEntry> acl = null;

	private List<Integer> aclAliases = null;

}