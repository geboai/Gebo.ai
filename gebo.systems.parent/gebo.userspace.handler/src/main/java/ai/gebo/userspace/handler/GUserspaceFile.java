/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.userspace.handler;

import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * 
 * This class represents a file in a user's workspace within the Gebo system.
 * It extends GBaseObject which likely provides base functionality for all
 * Gebo system objects such as ID management and persistence.
 */
public class GUserspaceFile extends GBaseObject {
	/**
	 * The name of the file in the userspace. Cannot be null.
	 */
	@NotNull
	private String name = null;
	
	/**
	 * The code identifying the userspace endpoint where this file is located. Cannot be null.
	 */
	@NotNull
	private String userspaceEndpointCode = null;
	
	/**
	 * Flag indicating whether the file has been deleted.
	 * Null value likely indicates the file is not deleted.
	 */
	private Boolean deleted = null;

	/**
	 * Default constructor for creating a new GUserspaceFile instance.
	 */
	public GUserspaceFile() {

	}

	/**
	 * Gets the name of the file.
	 * 
	 * @return the file name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the file.
	 * 
	 * @param name the file name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the userspace endpoint code where this file is located.
	 * 
	 * @return the userspace endpoint code
	 */
	public String getUserspaceEndpointCode() {
		return userspaceEndpointCode;
	}

	/**
	 * Sets the userspace endpoint code for this file.
	 * 
	 * @param userspaceEndpointCode the userspace endpoint code to set
	 */
	public void setUserspaceEndpointCode(String userspaceEndpointCode) {
		this.userspaceEndpointCode = userspaceEndpointCode;
	}

	/**
	 * Gets the deleted status of the file.
	 * 
	 * @return the deleted status, true if the file is deleted
	 */
	public Boolean getDeleted() {
		return deleted;
	}

	/**
	 * Sets the deleted status of the file.
	 * 
	 * @param deleted the deleted status to set
	 */
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}