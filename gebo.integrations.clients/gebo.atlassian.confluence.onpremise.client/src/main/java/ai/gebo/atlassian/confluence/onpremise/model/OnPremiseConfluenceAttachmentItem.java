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

@Data
public class OnPremiseConfluenceAttachmentItem extends OnPremiseConfluenceListItem {

    // Holds metadata associated with the attachment.
    OnPremiseConfluenceAttachmentMetaData metadata = null;

    // Holds extension-specific metadata for the attachment.
    OnPremiseConfluenceExtensionsMetaData extensions = null;    
}

/**
 * Represents an item that corresponds to an attachment in a Confluence instance hosted on-premise.
 * Extends OnPremiseConfluenceListItem to include additional attachment-specific metadata.
 *
 * The @Data annotation from Lombok is used to automatically generate
 * getters, setters, and other utility methods.
 *
 * @author Gebo.ai Commentor
 * AI generated comments
 */