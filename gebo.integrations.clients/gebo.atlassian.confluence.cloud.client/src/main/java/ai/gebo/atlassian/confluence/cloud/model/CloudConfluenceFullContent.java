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
 * Represents the full content structure within Confluence Cloud,
 * including root content, comments, child pages, comment contents, and attachments.
 * Utilizes Lombok to generate boilerplate code such as getters and setters.
 * 
 * AI generated comments
 */
@Data
public class CloudConfluenceFullContent {
	
    /** 
     * The root content of the Confluence page or space.
     */
    private CloudConfluenceContent rootContent = null;

    /**
     * A list of comments associated with the root content.
     */
    private CloudConfluenceCommentList commentsList = null;

    /**
     * A list of child pages under the root content.
     */
    private CloudConfluenceContentsList childPagesList = null;

    /**
     * A collection of content objects representing individual comments.
     */
    private List<CloudConfluenceContent> commentContents = new ArrayList<CloudConfluenceContent>();

    /**
     * A list of attachments associated with the root content.
     */
    private CloudConfluenceAttachmentsList attachmentsList = null;
}