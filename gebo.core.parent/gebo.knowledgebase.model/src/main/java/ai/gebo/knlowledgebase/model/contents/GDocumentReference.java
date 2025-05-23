/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knlowledgebase.model.contents;

import java.util.Map;

import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * AI generated comments
 * Represents a document reference within the virtual filesystem.
 * This includes properties such as extension, content type, file size, etc.
 * The document is stored and indexed in a MongoDB database.
 */
@Document
public class GDocumentReference extends GAbstractVirtualFilesystemObject {

	/**
	 * Enumeration for defining the type of reference - either a FILE or WEB.
	 */
	public static enum ReferenceType {
		FILE, WEB
	}

	/**
	 * Serial version UID for serialization.
	 */
	private static final long serialVersionUID = 3645646023522604382L;
	
	/**
	 * Unique identifier for synchronization purposes.
	 */
	private String synchronizationUUID = null;
	
	/**
	 * File extension (e.g., 'pdf', 'docx').
	 * Indexed with a hash for fast lookup.
	 */
	@HashIndexed
	private String extension = null;
	
	/**
	 * Content type of the file (e.g., 'text/plain', 'application/pdf').
	 * Indexed with a hash for fast lookup.
	 */
	@HashIndexed
	private String contentType = null;
	
	/**
	 * Identifier for the archetype of the file.
	 * Indexed with a hash for fast lookup.
	 */
	@HashIndexed
	private String geboFileArchetypeId = null;
	
	/**
	 * Size of the file in bytes.
	 */
	private Long fileSize = null;
	
	/**
	 * Flag indicating whether the content type is unmanaged.
	 */
	private Boolean unmanagedContentType = null;
	
	/**
	 * Type of the reference (FILE or WEB).
	 */
	private ReferenceType referenceType = null;
	
	/**
	 * Flag indicating whether vectorization of the content was skipped.
	 */
	private Boolean skippedVectorizationContent = null;
	
	/**
	 * Artificially generated content of the document.
	 */
	private String artificiallyGeneratedContent = null;

	/**
	 * Retrieves the artificially generated content.
	 * 
	 * @return artificially generated content as a String.
	 */
	public String getArtificiallyGeneratedContent() {
		return artificiallyGeneratedContent;
	}
	
	/**
	 * Retrieves the file extension.
	 * 
	 * @return the file extension as a String.
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * Sets the file extension.
	 * 
	 * @param extension the file extension to set.
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * Sets the artificially generated content.
	 * 
	 * @param artificiallyGeneratedContent the content to set.
	 */
	public void setArtificiallyGeneratedContent(String artificiallyGeneratedContent) {
		this.artificiallyGeneratedContent = artificiallyGeneratedContent;
	}

	/**
	 * Retrieves the synchronization UUID.
	 * 
	 * @return the synchronization UUID as a String.
	 */
	public String getSynchronizationUUID() {
		return synchronizationUUID;
	}

	/**
	 * Sets the synchronization UUID.
	 * 
	 * @param synchronizationUUID the UUID to set.
	 */
	public void setSynchronizationUUID(String synchronizationUUID) {
		this.synchronizationUUID = synchronizationUUID;
	}

	/**
	 * Retrieves the content type of the file.
	 * 
	 * @return the content type as a String.
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * Sets the content type of the file.
	 * 
	 * @param contentType the content type to set.
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * Retrieves the file size.
	 * 
	 * @return the file size as a Long.
	 */
	public Long getFileSize() {
		return fileSize;
	}

	/**
	 * Sets the file size.
	 * 
	 * @param fileSize the file size to set.
	 */
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * Retrieves the gebo file archetype ID.
	 * 
	 * @return the gebo file archetype ID as a String.
	 */
	public String getGeboFileArchetypeId() {
		return geboFileArchetypeId;
	}

	/**
	 * Sets the gebo file archetype ID.
	 * 
	 * @param geboFileArchetypeId the archetype ID to set.
	 */
	public void setGeboFileArchetypeId(String geboFileArchetypeId) {
		this.geboFileArchetypeId = geboFileArchetypeId;
	}

	/**
	 * Retrieves whether the content type is unmanaged.
	 * 
	 * @return true if unmanaged, false otherwise.
	 */
	public Boolean getUnmanagedContentType() {
		return unmanagedContentType;
	}

	/**
	 * Sets whether the content type is unmanaged.
	 * 
	 * @param unmanagedContentType the flag to set.
	 */
	public void setUnmanagedContentType(Boolean unmanagedContentType) {
		this.unmanagedContentType = unmanagedContentType;
	}

	/**
	 * Retrieves the reference type.
	 * 
	 * @return the reference type as a ReferenceType.
	 */
	public ReferenceType getReferenceType() {
		return referenceType;
	}

	/**
	 * Sets the reference type.
	 * 
	 * @param referenceType the reference type to set.
	 */
	public void setReferenceType(ReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	/**
	 * Retrieves whether vectorization of content was skipped.
	 * 
	 * @return true if skipped, false otherwise.
	 */
	public Boolean getSkippedVectorizationContent() {
		return skippedVectorizationContent;
	}

	/**
	 * Sets whether vectorization of content was skipped.
	 * 
	 * @param skippedContent the flag to set.
	 */
	public void setSkippedVectorizationContent(Boolean skippedContent) {
		this.skippedVectorizationContent = skippedContent;
	}

}