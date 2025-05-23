/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.onpremise.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * AI generated comments
 * Represents the full content structure on an on-premise instance of Confluence.
 * This includes the root content, comments, child pages, and attachments.
 */
@Data
public class OnPremiseFullContent {
    /** The root content of the Confluence page. */
    OnPremiseConfluenceContent rootContent = null;

    /** The list of comments associated with the content. */
    OnPremiseConfluenceCommentList commentsList = null;
    
    /** The list of child pages associated with the root content. */
    OnPremiseConfluenceContentsList childPagesList = null;
    
    /** A list of individual comment content entries. */
    List<OnPremiseConfluenceContent> commentContents = new ArrayList<OnPremiseConfluenceContent>();
    
    /** The list of attachments associated with the content. */
    OnPremiseConfluenceAttachmentsList attachmentsList = null;
}