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
 * GeboAudioDocumentFragment represents a specific type of document fragment
 * that handles audio content. It extends the GeboBinaryDocumentFragment class
 * to inherit basic binary fragment functionalities.
 * 
 * AI generated comments
 */
public final class GeboAudioDocumentFragment extends GeboBinaryDocumentFragment {

    /**
     * Default constructor that initializes the fragment type to AUDIO.
     */
    public GeboAudioDocumentFragment() {
        super(DocumentFragmentType.AUDIO);
    }

    /**
     * Constructs a GeboAudioDocumentFragment with specified content type, name,
     * and input stream. This constructor throws an IOException if an error
     * occurs during input stream handling.
     * 
     * @param contentType The MIME type of the audio content.
     * @param name The name of the audio document fragment.
     * @param is An InputStream of the audio content.
     * @throws IOException If an error occurs while handling the input stream.
     */
    public GeboAudioDocumentFragment(String contentType, String name, InputStream is) throws IOException {
        super(DocumentFragmentType.AUDIO, name, contentType, is);
    }

}