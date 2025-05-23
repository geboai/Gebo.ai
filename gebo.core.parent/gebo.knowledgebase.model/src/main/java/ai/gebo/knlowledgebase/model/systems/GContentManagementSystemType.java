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

import ai.gebo.model.base.GBaseObject;

/**
 * AI generated comments
 * Represents a content management system type with associated capabilities.
 * Extends the base functionality provided by GBaseObject.
 */
public class GContentManagementSystemType extends GBaseObject {

	// Unique identifier for serialization purposes.
	private static final long serialVersionUID = 2269273123418214783L;
	
	// List of system roles that define the capabilities of this content management system type.
	private List<GSystemRole> capabilities = new ArrayList<GSystemRole>();

	/**
	 * Retrieves the list of capabilities for the content management system.
	 * 
	 * @return List of GSystemRole representing the capabilities.
	 */
	public List<GSystemRole> getCapabilities() {
		return capabilities;
	}

	/**
	 * Sets the list of capabilities for the content management system.
	 * 
	 * @param capabilities List of GSystemRole to define the system capabilities.
	 */
	public void setCapabilities(List<GSystemRole> capabilities) {
		this.capabilities = capabilities;
	}

}