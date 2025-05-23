/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.onpremise.model;

import lombok.Data;

/**
 * Represents a version of a Confluence page stored on-premise.
 * Contains information about the user who made the update, 
 * the timestamp of the update, and the version number.
 * 
 * AI generated comments
 */
@Data
public class OnPremiseConfluenceVersion {
	 
	 /** The user who made the update to this version */
	 OnPremiseConfluenceByUser by = null;
	 
	 /** The timestamp of when this version was created */
	 String when = null;
	 
	 /** The version number of this update */
	 Integer number = null;
}