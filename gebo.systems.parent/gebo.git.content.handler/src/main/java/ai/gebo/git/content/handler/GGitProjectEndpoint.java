/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.git.content.handler;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.annotations.EntityDescription;
import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.model.annotations.SchedulableObject;

/**
 * AI generated comments
 * 
 * This class represents a Git repository endpoint for projects.
 * It extends the GProjectEndpoint class and provides specific functionality
 * to interact with Git repositories as a source of documents and code.
 * The class is mapped to a MongoDB document and can be scheduled for operations.
 */
@Document
@EntityDescription(description = "Git repository documents/code source", entityCategory = GProjectEndpoint.class)
@SchedulableObject
public class GGitProjectEndpoint extends GProjectEndpoint {
	/**
	 * URI pointing to the Git repository. References a GGitContentManagementSystem object.
	 */
	@GObjectReference(referencedType = GGitContentManagementSystem.class)
	private String repositoryUri = null;

	/**
	 * The branch of the Git repository to use.
	 */
	private String branch = null;
	
	/**
	 * Identity code used for authentication with the Git repository.
	 */
	private String identityCode = null;
	
	/**
	 * Flag indicating whether to always clone the repository instead of pulling updates.
	 */
	private Boolean alwaysClone = null;
	
	/**
	 * Flag indicating whether the repository has public access.
	 */
	private Boolean publicAccess = null;

	/**
	 * The content management system identifier.
	 */
	private String contentManagementSystem = null;

	/**
	 * Default constructor for GGitProjectEndpoint.
	 */
	public GGitProjectEndpoint() {

	}

	/**
	 * Gets the content management system identifier.
	 * 
	 * @return The content management system identifier
	 */
	public String getContentManagementSystem() {
		return contentManagementSystem;
	}

	/**
	 * Sets the content management system identifier.
	 * 
	 * @param contentManagementSystem The content management system identifier to set
	 */
	public void setContentManagementSystem(String contentManagementSystem) {
		this.contentManagementSystem = contentManagementSystem;
	}

	/**
	 * Gets the repository URI.
	 * 
	 * @return The repository URI
	 */
	public String getRepositoryUri() {
		return repositoryUri;
	}

	/**
	 * Sets the repository URI.
	 * 
	 * @param repositoryUri The repository URI to set
	 */
	public void setRepositoryUri(String repositoryUri) {
		this.repositoryUri = repositoryUri;
	}

	/**
	 * Gets the branch name.
	 * 
	 * @return The branch name
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * Sets the branch name.
	 * 
	 * @param branch The branch name to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * Gets the identity code used for authentication.
	 * 
	 * @return The identity code
	 */
	public String getIdentityCode() {
		return identityCode;
	}

	/**
	 * Sets the identity code used for authentication.
	 * 
	 * @param identityCode The identity code to set
	 */
	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	/**
	 * Gets the flag indicating whether to always clone the repository.
	 * 
	 * @return Boolean indicating whether to always clone
	 */
	public Boolean getAlwaysClone() {
		return alwaysClone;
	}

	/**
	 * Sets the flag for always cloning the repository.
	 * 
	 * @param alwaysClone Boolean value to set
	 */
	public void setAlwaysClone(Boolean alwaysClone) {
		this.alwaysClone = alwaysClone;
	}

	/**
	 * Gets the flag indicating whether the repository has public access.
	 * 
	 * @return Boolean indicating public access status
	 */
	public Boolean getPublicAccess() {
		return publicAccess;
	}

	/**
	 * Sets the flag for public access to the repository.
	 * 
	 * @param publicAccess Boolean value to set
	 */
	public void setPublicAccess(Boolean publicAccess) {
		this.publicAccess = publicAccess;
	}

}