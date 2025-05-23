/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.cloud.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Represents a list of attachments associated with a Confluence cloud instance.
 * Utilizes Lombok's @Data annotation to generate boilerplate code like getters, setters, toString, etc.
 * AI generated comments
 */
@Data
public class CloudConfluenceAttachmentsList {

    /**
     * A list containing the attachment items associated with a Confluence page or entity.
     * Initialized as an empty ArrayList to facilitate addition of attachment items.
     */
	private List<CloudConfluenceAttachmentItem> results = new ArrayList<CloudConfluenceAttachmentItem>();
}