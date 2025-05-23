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

import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;

/**
 * GKnowledgeBase class represents a knowledge base document in a MongoDB database.
 * It extends GBaseObject and implements IGObjectWithSecurity for security features.
 * AI generated comments
 */
@Document
public class GKnowledgeBase extends GBaseObject implements IGObjectWithSecurity {
	
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
	
	/** Default constructor */
	public GKnowledgeBase() {
		// No initialization required in default constructor
	}

	/**
	 * Gets the list of knowledge base references.
	 * @return List of knowledge base references.
	 */
	public List<String> getKnowledgeBaseReferences() {
		return knowledgeBaseReferences;
	}

	/**
	 * Sets the list of knowledge base references.
	 * @param knowledgeBaseReferences List of knowledge base references.
	 */
	public void setKnowledgeBaseReferences(List<String> knowledgeBaseReferences) {
		this.knowledgeBaseReferences = knowledgeBaseReferences;
	}

	/**
	 * Gets the list of project references.
	 * @return List of project references.
	 */
	public List<String> getProjectsReferences() {
		return projectsReferences;
	}

	/**
	 * Sets the list of project references.
	 * @param projectsReferences List of project references.
	 */
	public void setProjectsReferences(List<String> projectsReferences) {
		this.projectsReferences = projectsReferences;
	}

	/**
	 * Gets the list of embedding model references.
	 * @return List of GObjectRef for embedding models.
	 */
	public List<GObjectRef> getEmbeddingModelReferences() {
		return embeddingModelReferences;
	}

	/**
	 * Sets the list of embedding model references.
	 * @param embeddingModelReferences List of GObjectRef for embedding models.
	 */
	public void setEmbeddingModelReferences(List<GObjectRef> embeddingModelReferences) {
		this.embeddingModelReferences = embeddingModelReferences;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getAccessibleGroups() {
		return accessibleGroups;
	}

	/**
	 * Sets the list of groups that have access to this knowledge base.
	 * @param accessibleGroups List of group IDs.
	 */
	public void setAccessibleGroups(List<String> accessibleGroups) {
		this.accessibleGroups = accessibleGroups;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getAccessibleUsers() {
		return accessibleUsers;
	}

	/**
	 * Sets the list of users that have access to this knowledge base.
	 * @param accessibleUsers List of user IDs.
	 */
	public void setAccessibleUsers(List<String> accessibleUsers) {
		this.accessibleUsers = accessibleUsers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean getAccessibleToAll() {
		return accessibleToAll;
	}

	/**
	 * Sets whether this knowledge base is accessible to all.
	 * @param accessibleToAll True if accessible to all users, false otherwise.
	 */
	public void setAccessibleToAll(Boolean accessibleToAll) {
		this.accessibleToAll = accessibleToAll;
	}

	/**
	 * Gets the username associated with this knowledge base.
	 * @return Username as a String.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username for this knowledge base.
	 * @param username Username as a String.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the parent knowledge base code.
	 * @return The parent knowledge base code as a String.
	 */
	public String getParentKnowledgebaseCode() {
		return parentKnowledgebaseCode;
	}

	/**
	 * Sets the parent knowledge base code.
	 * @param parentKnowledgebaseCode The code of the parent knowledge base.
	 */
	public void setParentKnowledgebaseCode(String parentKnowledgebaseCode) {
		this.parentKnowledgebaseCode = parentKnowledgebaseCode;
	}

	/**
	 * Gets the object space type.
	 * @return The ObjectSpaceType.
	 */
	public ObjectSpaceType getObjectSpaceType() {
		return objectSpaceType;
	}

	/**
	 * Sets the object space type.
	 * @param kbType The type to set.
	 */
	public void setObjectSpaceType(ObjectSpaceType kbType) {
		this.objectSpaceType = kbType;
	}

}