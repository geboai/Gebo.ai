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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.springframework.util.FileCopyUtils;

/**
 * A class representing a binary document fragment within the Gebo system.
 * This fragment is stored with BASE64 encoding to handle binary data.
 *
 * @AI generated comments
 */
public class GeboBinaryDocumentFragment extends AbstractGeboDocumentFragment {

    /**
     * Constructs a GeboBinaryDocumentFragment with the specified document type.
     * Initializes the encoding to BASE64 and character encoding to ISO-8859-1.
     *
     * @param docType the type of the document fragment.
     */
    protected GeboBinaryDocumentFragment(DocumentFragmentType docType) {
        super(docType);
        this.encoding = "BASE64";
        this.characterEncoding = "ISO-8859-1";
    }

    /**
     * Constructs a GeboBinaryDocumentFragment with the specified document type, name,
     * content type, and an InputStream for the content. The content is encoded in BASE64.
     *
     * @param docType the type of the document fragment.
     * @param name the name of the document fragment.
     * @param contentType the content type of the document fragment.
     * @param is an InputStream providing the content of the document fragment.
     * @throws IOException if an I/O error occurs during reading the InputStream.
     */
    protected GeboBinaryDocumentFragment(DocumentFragmentType docType, String name, String contentType, InputStream is)
            throws IOException {
        this(docType);
        this.name = name;
        Base64.Encoder encoder = Base64.getEncoder();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // Copy content from InputStream to ByteArrayOutputStream
        FileCopyUtils.copy(is, bos);
        bos.flush(); // Ensure all data is written out
        // Encode the content to a BASE64 string
        content = encoder.encodeToString(bos.toByteArray());
    }

    /**
     * Returns an InputStream of the content of the document fragment.
     * The content is decoded from BASE64 encoding.
     *
     * @return an InputStream of the decoded document content.
     * @throws IOException if the content is not available or decoding fails.
     */
    @Override
    public InputStream streamContent() throws IOException {
        if (content == null || content.trim().length() == 0)
            return InputStream.nullInputStream(); // Return a null InputStream if content is empty
        Base64.Decoder decoder = Base64.getDecoder();
        final byte buffer[] = decoder.decode(content); // Decode the BASE64 content
        return new ByteArrayInputStream(buffer); // Return the content as a ByteArrayInputStream
    }

}