/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.document.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * AI generated comments
 * Represents a fragment of a text document that can be streamed as an InputStream.
 * This class is a final implementation of AbstractGeboDocumentFragment,
 * specifically for text content.
 */
public final class GeboTextDocumentFragment extends AbstractGeboDocumentFragment {

    /**
     * Initializes a new instance of GeboTextDocumentFragment with default settings
     * for a text document fragment, setting encoding, content type, and character encoding.
     */
    public GeboTextDocumentFragment() {
        super(DocumentFragmentType.TEXT);
        this.encoding = null;
        this.contentType = "text/plain";
        this.characterEncoding = "UTF-8";
    }

    /**
     * Streams the content of the text document fragment as an InputStream.
     * Uses character encoding specified to convert the content to bytes.
     * If no content is available, returns a null InputStream.
     *
     * @return An InputStream representing the content of the document fragment.
     * @throws IOException If there is a problem creating the InputStream.
     */
    @Override
    public InputStream streamContent() throws IOException {
        // Convert the content to a byte stream using the specified character encoding
        return content != null ? new ByteArrayInputStream(content.getBytes(this.characterEncoding))
                : InputStream.nullInputStream();
    }

    /**
     * Creates a GeboTextDocumentFragment from the provided plain text.
     *
     * @param text The plain text content to be stored in the document fragment.
     * @return A GeboTextDocumentFragment initialized with the specified text content.
     */
    public static GeboTextDocumentFragment plainText(String text) {
        GeboTextDocumentFragment fragment = new GeboTextDocumentFragment();
        fragment.setContent(text);
        return fragment;
    }
}