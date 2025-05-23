/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knlowledgebase.model.contents;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a software artifact in the knowledge base system.
 * This class extends {@code GAbstractVirtualFilesystemObject} to provide properties and behavior specific to software artifacts.
 * AI generated comments
 */
@Document
public class GSoftwareArtifact extends GAbstractVirtualFilesystemObject {
	/**
	 * Serial version UID for serialization compatibility.
	 */
	private static final long serialVersionUID = -3016949339840684935L;
	
	// Identifier for the module this artifact belongs to.
	private String moduleId = null;
	
	// Identifier for this artifact.
	private String artifactId = null;
	
	// Package manager associated with this artifact.
	private GPackageManager packageManager = null;
	
	// Programming language of the artifact.
	private String language = null;
	
	// ID of the parent deliverable.
	private Long parentDeliverableId = null;
	
	// Code representing the build system used.
	private String buildSystemCode = null;
	
	// Code representing the type of deliverable.
	private String deliverableTypeCode = null;
	
	// Code used for the published content system.
	private String publishedContentSystemCode = null;

	/**
	 * Default constructor for GSoftwareArtifact.
	 */
	public GSoftwareArtifact() {

	}

	/**
	 * Copy constructor for GSoftwareArtifact.
	 * 
	 * @param c The GSoftwareArtifact object to copy from.
	 */
	public GSoftwareArtifact(GSoftwareArtifact c) {
		super(c);
		moduleId = c.moduleId;
		artifactId = c.artifactId;
		buildSystemCode = c.buildSystemCode;
		language = c.language;
		deliverableTypeCode = c.deliverableTypeCode;
		packageManager = c.packageManager;
		parentDeliverableId = c.parentDeliverableId;
		publishedContentSystemCode = c.publishedContentSystemCode;
	}

	/**
	 * Gets the artifact ID.
	 * 
	 * @return The artifact ID as a String.
	 */
	public String getArtifactId() {
		return artifactId;
	}

	/**
	 * Sets the artifact ID.
	 * 
	 * @param artifactId The artifact ID to set.
	 */
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	/**
	 * Gets the parent deliverable ID.
	 * 
	 * @return The parent deliverable ID as a Long.
	 */
	public Long getParentDeliverableId() {
		return parentDeliverableId;
	}

	/**
	 * Sets the parent deliverable ID.
	 * 
	 * @param parentDeliverableId The parent deliverable ID to set.
	 */
	public void setParentDeliverableId(Long parentDeliverableId) {
		this.parentDeliverableId = parentDeliverableId;
	}

	/**
	 * Gets the deliverable type code.
	 * 
	 * @return The deliverable type code as a String.
	 */
	public String getDeliverableTypeCode() {
		return deliverableTypeCode;
	}

	/**
	 * Sets the deliverable type code.
	 * 
	 * @param deliverableTypeCode The deliverable type code to set.
	 */
	public void setDeliverableTypeCode(String deliverableTypeCode) {
		this.deliverableTypeCode = deliverableTypeCode;
	}

	/**
	 * Gets the build system code.
	 * 
	 * @return The build system code as a String.
	 */
	public String getBuildSystemCode() {
		return buildSystemCode;
	}

	/**
	 * Sets the build system code.
	 * 
	 * @param buildSystemCode The build system code to set.
	 */
	public void setBuildSystemCode(String buildSystemCode) {
		this.buildSystemCode = buildSystemCode;
	}

	/**
	 * Gets the published content system code.
	 * 
	 * @return The published content system code as a String.
	 */
	public String getPublishedContentSystemCode() {
		return publishedContentSystemCode;
	}

	/**
	 * Sets the published content system code.
	 * 
	 * @param publishedContentSystemCode The published content system code to set.
	 */
	public void setPublishedContentSystemCode(String publishedContentSystemCode) {
		this.publishedContentSystemCode = publishedContentSystemCode;
	}

	/**
	 * Gets the programming language of the artifact.
	 * 
	 * @return The programming language as a String.
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets the programming language of the artifact.
	 * 
	 * @param language The programming language to set.
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Gets the module ID of the artifact.
	 * 
	 * @return The module ID as a String.
	 */
	public String getModuleId() {
		return moduleId;
	}

	/**
	 * Sets the module ID of the artifact.
	 * 
	 * @param moduleId The module ID to set.
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * Determines if this artifact is the same as another artifact based on 
	 * module ID and artifact ID.
	 * 
	 * @param deliverable The GSoftwareArtifact to compare with.
	 * @return {@code true} if both module and artifact IDs match, {@code false} otherwise.
	 */
	public boolean isSameArtifact(GSoftwareArtifact deliverable) {
		boolean sameModule = true;
		if (moduleId != null && deliverable.moduleId != null) {
			sameModule = moduleId.equals(deliverable.moduleId);
		} else if ((moduleId != null && deliverable.moduleId == null)
				|| (moduleId == null && deliverable.moduleId != null)) {
			sameModule = false;
		}
		if (artifactId != null && deliverable.artifactId != null) {
			return sameModule && (artifactId.equals(deliverable.artifactId));
		}
		return false;
	}

	/**
	 * Provides a description of the software artifact, combining the superclass 
	 * description with specific details of this artifact.
	 * 
	 * @return A String describing the software artifact.
	 */
	@Override
	public String getDescription() {
		String description = super.getDescription();
		if (description != null)
			return description;
		return "package manager:" + packageManager + " module:" + moduleId + " artifact:" + artifactId;
	}

	/**
	 * Generates a code representing this software artifact's location and version in the system.
	 * 
	 * @return A String representing the artifact's code.
	 */
	@Override
	public String getCode() {
		return "kb:" + getRootKnowledgebaseCode() + "::pj:" + getParentProjectCode() + "::" + packageManager + "::"
				+ moduleId + "::" + artifactId + "::" + getVersion();
	}

	/**
	 * Gets the package manager associated with this artifact.
	 * 
	 * @return The package manager as a GPackageManager object.
	 */
	public GPackageManager getPackageManager() {
		return packageManager;
	}

	/**
	 * Sets the package manager for this artifact.
	 * 
	 * @param packageManager The package manager to set.
	 */
	public void setPackageManager(GPackageManager packageManager) {
		this.packageManager = packageManager;
	}
}