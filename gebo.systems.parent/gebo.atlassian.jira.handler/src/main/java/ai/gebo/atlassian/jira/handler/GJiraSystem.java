/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.jira.handler;

import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import lombok.Data;

/**
 * AI generated comments
 * 
 * GJiraSystem is a specialized content management system class for Jira.
 * This class extends the base GContentManagementSystem and provides Jira-specific functionality.
 * The @Data annotation from Lombok automatically generates getters, setters, equals, hashCode, and toString methods.
 */
@Data
public class GJiraSystem extends GContentManagementSystem {
	/**
	 * A secret code used for authentication or identification purposes within the Jira system.
	 * This field is initially set to null and can be modified using the generated setter methods.
	 */
	private String secretCode = null;
	

}