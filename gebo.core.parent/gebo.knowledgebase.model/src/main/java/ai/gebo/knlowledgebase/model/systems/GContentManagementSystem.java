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

import ai.gebo.acl.GAclEntry;
import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.model.base.GBaseVersionableObject;
import lombok.Data;

/**
 * AI generated comments Represents a content management system within the
 * application. Extends the GBaseVersionableObject to inherit version control
 * functionality.
 */
@Data
public class GContentManagementSystem extends GBaseVersionableObject implements IAclGrantedResource {

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
	
	private List<Integer> aclAliases = null;
}