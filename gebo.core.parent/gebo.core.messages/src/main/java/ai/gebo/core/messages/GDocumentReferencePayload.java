/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.messages;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * AI generated comments
 * Represents a payload containing a reference to a document within a message fragment.
 * This class serves as a data structure for holding such a document reference.
 */
@Getter
@Setter
public class GDocumentReferencePayload extends GAbstractContentMessageFragmentPayload {

    /**
     * What the document was when it was last ingested (the indexing step's
     * acknowledgement kept by its content handler), null when it never was: the
     * hash of its content, its modification date and its size. The chunker reads
     * the document again only if its date or size changed, and sends it on only if
     * the hash of what it read changed.
     */
    private String lastIngestedHash = null;
    private Date lastIngestedModificationDate = null;
    private Long lastIngestedFileSize = null;

    /**
     * Default constructor for creating an instance of GDocumentReferencePayload.
     * Initializes a new instance without setting any initial properties or values.
     */
    public GDocumentReferencePayload() {
        // No specific initialization required for this constructor
    }

}