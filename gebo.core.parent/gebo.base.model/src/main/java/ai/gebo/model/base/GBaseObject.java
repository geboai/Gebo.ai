/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.model.base;

import java.io.Serializable;
import java.util.Date;

import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.Id;

/**
 * AI generated comments Represents a base object model containing common fields
 * and methods for entity classes. Implements Serializable interface for object
 * serialization.
 */
public class GBaseObject implements Serializable {
	/**
	 * Serial version UID for serialization compatibility.
	 */
	private static final long serialVersionUID = 7582729487023618048L;

	@Id
	private String code = null; // Unique identifier for the object.
	@Order
	private String description = null; // Description of the object.
	private String userModified = null; // Username of the last user who modified the object.
	private String userCreated = null; // Username of the user who created the object.
	private Date dateModified = null; // Date when the object was last modified.
	private Date dateCreated = null; // Date when the object was created.

	public GBaseObject() {

	}

	public GBaseObject(GBaseObject o) {
		code = o.code;
		description = o.description;
		dateCreated = o.dateCreated;
		dateModified = o.dateModified;
		userModified = o.userModified;
		userCreated = o.userCreated;		
	}

	/**
	 * Retrieves the owner of the object.
	 *
	 * @return the username of the user who created the object.
	 */
	public String owner() {
		return this.userCreated;
	}

	/**
	 * Gets the code of the object.
	 *
	 * @return the unique code identifier of the object.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code for the object.
	 *
	 * @param code the unique code identifier to be set.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the description of the object.
	 *
	 * @return the description of the object.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description for the object.
	 *
	 * @param description the descriptive text to be set for the object.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the username of the last user who modified the object.
	 *
	 * @return the username of the user who last modified the object.
	 */
	public String getUserModified() {
		return userModified;
	}

	/**
	 * Sets the username of the last user who modified the object.
	 *
	 * @param userModified the username to be recorded as last modifier.
	 */
	public void setUserModified(String userModified) {
		this.userModified = userModified;
	}

	/**
	 * Gets the username of the user who created the object.
	 *
	 * @return the username of the user who created the object.
	 */
	public String getUserCreated() {
		return userCreated;
	}

	/**
	 * Sets the username of the user who created the object.
	 *
	 * @param userCreated the username to be set as the creator.
	 */
	public void setUserCreated(String userCreated) {
		this.userCreated = userCreated;
	}

	/**
	 * Gets the date when the object was last modified.
	 *
	 * @return the date of the last modification.
	 */
	public Date getDateModified() {
		return dateModified;
	}

	/**
	 * Sets the date when the object was last modified.
	 *
	 * @param dateModified the date to be set for last modification.
	 */
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	/**
	 * Gets the date when the object was created.
	 *
	 * @return the creation date of the object.
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * Sets the creation date for the object.
	 *
	 * @param dateCreated the creation date to be set.
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

}