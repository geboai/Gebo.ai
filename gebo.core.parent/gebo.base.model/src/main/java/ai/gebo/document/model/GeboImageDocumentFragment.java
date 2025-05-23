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
 * Represents a fragment of a document specifically for handling images.
 * Extends the capabilities of {@link GeboBinaryDocumentFragment} to support image-specific functionality.
 */
public final class GeboImageDocumentFragment extends GeboBinaryDocumentFragment {
    
    /**
     * Default constructor that initializes the image document fragment
     * with the type set to IMAGE.
     */
    public GeboImageDocumentFragment() {
        super(DocumentFragmentType.IMAGE);
    }

    /**
     * Constructor to create an image document fragment with specific
     * content type, name, and input stream. This can throw an IOException
     * if there is a problem reading from the input stream.
     *
     * @param contentType the MIME type of the content
     * @param name the name of the document fragment
     * @param is the input stream from which to read the image content
     * @throws IOException if an I/O error occurs while reading the input stream
     */
    public GeboImageDocumentFragment(String contentType, String name, InputStream is) throws IOException {
        super(DocumentFragmentType.IMAGE, name, contentType, is);
    }

}