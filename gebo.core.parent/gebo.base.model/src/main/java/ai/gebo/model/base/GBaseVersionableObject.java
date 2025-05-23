/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.model.base;

import java.util.Date;

/**
 * AI generated comments
 * The GBaseVersionableObject class extends GBaseObject and provides additional
 * functionality for handling versioning and timestamps related to the creation and
 * modification of objects.
 */
public abstract class GBaseVersionableObject extends GBaseObject {
	
	/**
	 * Serial version UID for serialization.
	 */
	private static final long serialVersionUID = 6963092431767805333L;

	// Date of creation of the object
	private Date creationDate = null;

	// Date of last modification made to the object
	private Date modificationDate = null;

	// Version identifier of the object
	private String version = null;
	
	/**
	 * Gets the version of the object.
	 * @return the version as a String
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version of the object.
	 * @param version a String representing the version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the creation date of the object.
	 * @return the creation date
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date of the object.
	 * @param creationDate the date when the object was created
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Gets the modification date of the object.
	 * @return the modification date
	 */
	public Date getModificationDate() {
		return modificationDate;
	}

	/**
	 * Sets the modification date of the object.
	 * @param modificationDate the date when the object was last modified
	 */
	public void setModificationDate(Date modificatoinDate) {
		this.modificationDate = modificatoinDate;
	}
	
}