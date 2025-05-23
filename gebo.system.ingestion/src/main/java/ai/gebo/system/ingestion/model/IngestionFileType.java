/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.model;

import java.util.ArrayList;
import java.util.List;

/**
 * AI generated comments
 *
 * Represents a file type configuration for the ingestion system.
 * This class defines the properties of files being processed, such as file extensions,
 * content types, and how they should be treated during ingestion.
 */
public class IngestionFileType {
	/** Unique identifier for the file type */
	String fileTypeId = null;
	/** Human-readable description of the file type */
	String description = null;
	/** Indicates how the file should be processed or categorized */
	String treatAs = null;
	/** Flag indicating if this file type can be viewed in the UI */
	Boolean uiViewable = false;
	/** Programming language associated with this file type, if applicable */
	String programmingLanguage = null;
	/** Start delimiter for comments in this file type */
	String commentEscapeBegin=null;
	/** End delimiter for comments in this file type */
	String commentEscapeEnd=null;
	/** Single-line comment prefix for this file type */
	String commentRowEscape=null;
	/** Flag indicating whether to enrich with catalog information */
	Boolean enrichWithCatalogInfos=true;
	/** List of special files of this type */
	List<SpecialFile> specialfiles=new ArrayList<SpecialFile>();
	/** File extensions associated with this file type */
	List<String> extensions = new ArrayList<String>();
	/** MIME content types associated with this file type */
	List<String> contentTypes = new ArrayList<String>();

	/**
	 * Default constructor for IngestionFileType.
	 */
	public IngestionFileType() {

	}

	/**
	 * Gets the description of this file type.
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of this file type.
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the list of file extensions associated with this file type.
	 * @return the list of extensions
	 */
	public List<String> getExtensions() {
		return extensions;
	}

	/**
	 * Sets the list of file extensions associated with this file type.
	 * @param extensions the list of extensions to set
	 */
	public void setExtensions(List<String> extensions) {
		this.extensions = extensions;
	}

	/**
	 * Gets the unique identifier for this file type.
	 * @return the file type ID
	 */
	public String getFileTypeId() {
		return fileTypeId;
	}

	/**
	 * Sets the unique identifier for this file type.
	 * @param fileTypeId the file type ID to set
	 */
	public void setFileTypeId(String fileTypeId) {
		this.fileTypeId = fileTypeId;
	}

	/**
	 * Gets how this file type should be processed or categorized.
	 * @return the treatment specification
	 */
	public String getTreatAs() {
		return treatAs;
	}

	/**
	 * Sets how this file type should be processed or categorized.
	 * @param treatAs the treatment specification to set
	 */
	public void setTreatAs(String treatAs) {
		this.treatAs = treatAs;
	}

	/**
	 * Gets the MIME content types associated with this file type.
	 * @return the list of content types
	 */
	public List<String> getContentTypes() {
		return contentTypes;
	}

	/**
	 * Sets the MIME content types associated with this file type.
	 * @param contentTypes the list of content types to set
	 */
	public void setContentTypes(List<String> contentTypes) {
		this.contentTypes = contentTypes;
	}

	/**
	 * Gets the programming language associated with this file type.
	 * @return the programming language
	 */
	public String getProgrammingLanguage() {
		return programmingLanguage;
	}

	/**
	 * Sets the programming language associated with this file type.
	 * @param programmingLanguage the programming language to set
	 */
	public void setProgrammingLanguage(String programmingLanguage) {
		this.programmingLanguage = programmingLanguage;
	}

	/**
	 * Gets whether this file type can be viewed in the UI.
	 * @return true if viewable in UI, false otherwise
	 */
	public Boolean getUiViewable() {
		return uiViewable;
	}

	/**
	 * Sets whether this file type can be viewed in the UI.
	 * @param uiViewable true if viewable in UI, false otherwise
	 */
	public void setUiViewable(Boolean uiViewable) {
		this.uiViewable = uiViewable;
	}

	/**
	 * Gets the list of special files associated with this file type.
	 * @return the list of special files
	 */
	public List<SpecialFile> getSpecialfiles() {
		return specialfiles;
	}

	/**
	 * Sets the list of special files associated with this file type.
	 * @param specialfiles the list of special files to set
	 */
	public void setSpecialfiles(List<SpecialFile> specialfiles) {
		this.specialfiles = specialfiles;
	}

	/**
	 * Gets the start delimiter for comments in this file type.
	 * @return the comment begin delimiter
	 */
	public String getCommentEscapeBegin() {
		return commentEscapeBegin;
	}

	/**
	 * Sets the start delimiter for comments in this file type.
	 * @param commentEscapeBegin the comment begin delimiter to set
	 */
	public void setCommentEscapeBegin(String commentEscapeBegin) {
		this.commentEscapeBegin = commentEscapeBegin;
	}

	/**
	 * Gets the end delimiter for comments in this file type.
	 * @return the comment end delimiter
	 */
	public String getCommentEscapeEnd() {
		return commentEscapeEnd;
	}

	/**
	 * Sets the end delimiter for comments in this file type.
	 * @param commentEscapeEnd the comment end delimiter to set
	 */
	public void setCommentEscapeEnd(String commentEscapeEnd) {
		this.commentEscapeEnd = commentEscapeEnd;
	}

	/**
	 * Gets the single-line comment prefix for this file type.
	 * @return the single-line comment prefix
	 */
	public String getCommentRowEscape() {
		return commentRowEscape;
	}

	/**
	 * Sets the single-line comment prefix for this file type.
	 * @param commentRowEscape the single-line comment prefix to set
	 */
	public void setCommentRowEscape(String commentRowEscape) {
		this.commentRowEscape = commentRowEscape;
	}

	/**
	 * Gets whether this file type should be enriched with catalog information.
	 * @return true if enrichment should be applied, false otherwise
	 */
	public Boolean getEnrichWithCatalogInfos() {
		return enrichWithCatalogInfos;
	}

	/**
	 * Sets whether this file type should be enriched with catalog information.
	 * @param enrichWithCatalogInfos true if enrichment should be applied, false otherwise
	 */
	public void setEnrichWithCatalogInfos(Boolean enrichWithCatalogInfos) {
		this.enrichWithCatalogInfos = enrichWithCatalogInfos;
	}

	

}