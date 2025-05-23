/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.onpremise.model;

import java.util.HashMap;
import lombok.Data;

/**
 * Represents metadata for an attachment in an on-premise Confluence environment.
 * This class holds information about the media type of the attachment and
 * associated labels as key-value pairs.
 * <p>
 * The class uses Lombok's @Data annotation to automatically generate 
 * boilerplate code such as getters, setters, and toString methods.
 * </p>
 * 
 * AI generated comments
 */
@Data
public class OnPremiseConfluenceAttachmentMetaData {

    /**
     * The media type of the attachment, which defines the format of the content.
     * Default value is null.
     */
    String mediaType = null;

    /**
     * A collection of labels associated with the attachment. These labels are 
     * stored as key-value pairs where the key is a string and the value can 
     * be any object.
     */
    HashMap<String, Object> labels = new HashMap<String, Object>();
}