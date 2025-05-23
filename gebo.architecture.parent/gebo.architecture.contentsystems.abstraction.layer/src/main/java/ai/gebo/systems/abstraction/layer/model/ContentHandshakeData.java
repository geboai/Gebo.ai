/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.core.messages.GContentEmbeddingHandshakePayload;

/**
 * Gebo.ai comment agent
 * Represents the data structure for content handshake in a MongoDB document. 
 * Implements Serializable interface for object serialization.
 */
@Document
public class ContentHandshakeData implements Serializable {
	
	/** Unique identifier for the document */
	@Id
	private String id = null;

	/** Code related to the content, indexed for quick lookup by hash */
	@HashIndexed
	private String contentCode = null;

	/** The date when the content was last modified, ordered */
	@Order
	private Date modificationDate = null;

	/** The date when the content was created */
	private Date creationdDate = null;

	/** Hash value representing the content */
	private String hash = null;

	/** Flag indicating whether the content has been processed */
	private Boolean processed = null;

	/** 
	 * Default constructor 
	 * Initializes a new instance of ContentHandshakeData.
	 */
	public ContentHandshakeData() {

	}

	/** 
	 * Constructs a ContentHandshakeData with specified payload data.
	 * @param h The payload containing initialization data.
	 */
	public ContentHandshakeData(GContentEmbeddingHandshakePayload h) {
		id = h.getPayloadId();
		contentCode = h.getContentCode();
		modificationDate = h.getModificationDate();
		creationdDate = h.getCreationdDate();
		hash = h.getHash();
		processed = h.getProcessed();
	}

	/**
	 * Gets the unique identifier.
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the unique identifier.
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the content code.
	 * @return the content code
	 */
	public String getContentCode() {
		return contentCode;
	}

	/**
	 * Sets the content code.
	 * @param contentCode the new content code
	 */
	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}

	/**
	 * Gets the modification date.
	 * @return the modification date
	 */
	public Date getModificationDate() {
		return modificationDate;
	}

	/**
	 * Sets the modification date.
	 * @param modificationDate the new modification date
	 */
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	/**
	 * Gets the creation date.
	 * @return the creation date
	 */
	public Date getCreationdDate() {
		return creationdDate;
	}

	/**
	 * Sets the creation date.
	 * @param creationdDate the new creation date
	 */
	public void setCreationdDate(Date creationdDate) {
		this.creationdDate = creationdDate;
	}

	/**
	 * Gets the hash value.
	 * @return the hash value
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * Sets the hash value.
	 * @param hash the new hash value
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}

	/**
	 * Gets the processed flag.
	 * @return the processed flag
	 */
	public Boolean getProcessed() {
		return processed;
	}

	/**
	 * Sets the processed flag.
	 * @param processed the new processed flag
	 */
	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	/**
	 * Factory method to create an instance of ContentHandshakeData from a payload.
	 * @param hs The payload containing initialization data.
	 * @return a new instance of ContentHandshakeData
	 */
	public static ContentHandshakeData of(GContentEmbeddingHandshakePayload hs) {
		return new ContentHandshakeData(hs);
	}

}