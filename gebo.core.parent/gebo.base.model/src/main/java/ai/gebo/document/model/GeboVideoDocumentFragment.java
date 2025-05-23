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
 * Represents a fragment of a video document in the Gebo document model.
 * This class extends {@code GeboBinaryDocumentFragment} and is used specifically for handling video document fragments.
 * 
 * AI generated comments
 */
public final class GeboVideoDocumentFragment extends GeboBinaryDocumentFragment {

    /**
     * Constructs a new {@code GeboVideoDocumentFragment} with the specified document fragment type.
     * The fragment type is set to VIDEO.
     *
     * @param docType the type of the document fragment, expected to be VIDEO.
     */
    public GeboVideoDocumentFragment(DocumentFragmentType docType) {
        super(DocumentFragmentType.VIDEO);
    }

    /**
     * Constructs a new {@code GeboVideoDocumentFragment} with the specified content type, name, and input stream.
     * 
     * @param contentType the MIME type of the video content.
     * @param name the name of the video document fragment.
     * @param is the input stream from which the video content is read.
     * @throws IOException if an I/O error occurs during reading from the provided input stream.
     */
    public GeboVideoDocumentFragment(String contentType, String name, InputStream is) throws IOException {
        super(DocumentFragmentType.VIDEO, name, contentType, is);
    }

}