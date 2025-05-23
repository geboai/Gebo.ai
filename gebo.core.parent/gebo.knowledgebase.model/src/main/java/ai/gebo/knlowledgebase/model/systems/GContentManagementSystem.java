/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knlowledgebase.model.systems;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.model.base.GBaseVersionableObject;

/**
 * AI generated comments
 * Represents a content management system within the application.
 * Extends the GBaseVersionableObject to inherit version control functionality.
 */
public class GContentManagementSystem extends GBaseVersionableObject {

	/**
	 * Unique identifier for serialization purposes.
	 */
	private static final long serialVersionUID = -5907700191466579092L;

	/**
	 * Type of the content management system.
	 */
	private String contentManagementSystemType = null;

	/**
	 * Indicates if the content management system is in read-only mode.
	 */
	private Boolean readonly = false;

	/**
	 * Base URI of the content management system.
	 */
	private String baseUri = null;

	/**
	 * List of capabilities used by the system, represented by GSystemRole.
	 */
	private List<GSystemRole> usedCapabilities = new ArrayList<GSystemRole>();

	/**
	 * Retrieves the type of the content management system.
	 * 
	 * @return the content management system type as a String.
	 */
	public String getContentManagementSystemType() {
		return contentManagementSystemType;
	}

	/**
	 * Sets the type of the content management system.
	 * 
	 * @param contentManagementSystemType the type to set for the content management system.
	 */
	public void setContentManagementSystemType(String contentManagementSystemType) {
		this.contentManagementSystemType = contentManagementSystemType;
	}

	/**
	 * Retrieves the base URI of the content management system.
	 * 
	 * @return the base URI as a String.
	 */
	public String getBaseUri() {
		return baseUri;
	}

	/**
	 * Sets the base URI of the content management system.
	 * 
	 * @param baseUri the base URI to set for the content management system.
	 */
	public void setBaseUri(String baseUri) {
		this.baseUri = baseUri;
	}

	/**
	 * Retrieves the list of capabilities used by the system.
	 * 
	 * @return a List of GSystemRole representing the used capabilities.
	 */
	public List<GSystemRole> getUsedCapabilities() {
		return usedCapabilities;
	}

	/**
	 * Sets the list of capabilities used by the system.
	 * 
	 * @param usedCapabilities the list of capabilities to set.
	 */
	public void setUsedCapabilities(List<GSystemRole> usedCapabilities) {
		this.usedCapabilities = usedCapabilities;
	}

	/**
	 * Checks if the content management system is read-only.
	 * 
	 * @return a Boolean indicating if the system is read-only.
	 */
	public Boolean getReadonly() {
		return readonly;
	}

	/**
	 * Sets the read-only status of the content management system.
	 * 
	 * @param readonly the Boolean status to set for read-only mode.
	 */
	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
	}

}