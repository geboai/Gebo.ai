/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.document.model;

import java.io.IOException;
import java.io.InputStream;

/**
 * AI generated comments
 * Represents a fragment of a media document in the Gebo system.
 * This class is a specific type of GeboBinaryDocumentFragment for handling media content.
 */
public final class GeboMediaDocumentFragment extends GeboBinaryDocumentFragment {

    /**
     * Constructs a GeboMediaDocumentFragment with the default document fragment type MEDIA.
     */
    public GeboMediaDocumentFragment() {
        super(DocumentFragmentType.MEDIA);
    }

    /**
     * Constructs a GeboMediaDocumentFragment with specified content type, name, and input stream.
     *
     * @param contentType the MIME type of the media content
     * @param name the name of the media fragment
     * @param is the input stream containing the media content
     * @throws IOException if an I/O error occurs
     */
    public GeboMediaDocumentFragment(String contentType, String name, InputStream is) throws IOException {
        super(DocumentFragmentType.MEDIA, name, contentType, is);
    }
    
}