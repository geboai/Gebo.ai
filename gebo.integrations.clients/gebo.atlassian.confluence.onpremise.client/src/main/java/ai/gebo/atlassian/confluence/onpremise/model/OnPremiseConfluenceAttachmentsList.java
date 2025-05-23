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
 * The OnPremiseConfluenceAttachmentsList class represents a list of attachment items 
 * for an on-premise Confluence instance.
 * <p>
 * This class uses Lombok's @Data annotation to automatically generate 
 * boilerplate code such as getters, setters, equals, hash, and toString methods.
 * </p>
 * <p>
 * AI generated comments
 * </p>
 */
@Data
public class OnPremiseConfluenceAttachmentsList {

    /**
     * A list containing OnPremiseConfluenceAttachmentItem objects representing the 
     * individual attachments in the Confluence instance.
     */
    List<OnPremiseConfluenceAttachmentItem> results = new ArrayList<OnPremiseConfluenceAttachmentItem>();
}