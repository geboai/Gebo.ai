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
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * AbstractGeboDocumentFragment represents a fragment of a document within the Gebo system.
 * This abstract class provides common properties and methods for various types of document fragments,
 * such as text, image, audio, video, and media.
 * AI generated comments
 */
public abstract class AbstractGeboDocumentFragment implements Serializable {

    /**
     * Enum representing possible types of document fragments.
     */
    public static enum DocumentFragmentType {
        TEXT, IMAGE, AUDIO, VIDEO, MEDIA
    }

    // Metadata for custom properties relevant to the document fragment
    private Map<String, Object> customMetaData = new HashMap<>();

    // Specifies the type of the document fragment
    protected final DocumentFragmentType fragmentType;

    // Content type of the document fragment (e.g., "text/plain")
    protected String contentType = null;

    // Character encoding used for the document fragment
    protected String characterEncoding = null;

    // Encoding format of the document fragment
    protected String encoding = null;

    // Content of the document fragment as a string
    protected String content = null;

    // Name associated with the document fragment
    protected String name = null;

    // Unique code identifying the document fragment
    protected String uniqueCode = null;

    /**
     * Constructor initializing the document fragment with the specified type.
     * 
     * @param fragmentType the type of the document fragment
     */
    protected AbstractGeboDocumentFragment(DocumentFragmentType fragmentType) {
        this.fragmentType = fragmentType;
    }

    /**
     * Constructor initializing the document fragment with the specified details.
     * 
     * @param fragmentType       the type of the document fragment
     * @param contentType        the MIME type of the content
     * @param encoding           the encoding format
     * @param characterEncoding  the character encoding set
     * @param name               the name of the fragment
     * @param content            the actual content of the fragment
     */
    protected AbstractGeboDocumentFragment(DocumentFragmentType fragmentType, String contentType, String encoding,
            String characterEncoding, String name, String content) {
        this.fragmentType = fragmentType;
        this.encoding = encoding;
        this.characterEncoding = characterEncoding;
        this.content = content;
        this.contentType = contentType;
        this.name = name;
    }

    /**
     * Retrieves the content type of the document fragment.
     * 
     * @return the MIME type of the content
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the content type of the document fragment.
     * 
     * @param contentType the new MIME type to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Retrieves the encoding of the document fragment.
     * 
     * @return the encoding format
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Sets the encoding of the document fragment.
     * 
     * @param encoding the new encoding format to set
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * Retrieves the content of the document fragment.
     * 
     * @return the content as a string
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the document fragment.
     * 
     * @param content the new content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Abstract method to stream the content of the document fragment as an InputStream.
     * 
     * @return InputStream of the content
     * @throws IOException if streaming fails
     */
    public abstract InputStream streamContent() throws IOException;

    /**
     * Retrieves the character encoding of the document fragment.
     * 
     * @return the character encoding
     */
    public String getCharacterEncoding() {
        return characterEncoding;
    }

    /**
     * Sets the character encoding of the document fragment.
     * 
     * @param characterEncoding the new character encoding to set
     */
    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    /**
     * Retrieves the type of the document fragment.
     * 
     * @return the fragment type
     */
    public DocumentFragmentType getFragmentType() {
        return fragmentType;
    }

    /**
     * Retrieves the name of the document fragment.
     * 
     * @return the name of the fragment
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the document fragment.
     * 
     * @param name the new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the unique code of the document fragment.
     * 
     * @return the unique code
     */
    public String getUniqueCode() {
        return uniqueCode;
    }

    /**
     * Sets the unique code of the document fragment.
     * 
     * @param uniqueCode the new unique code to set
     */
    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    /**
     * Retrieves custom metadata for the document fragment.
     * 
     * @return a map of custom metadata
     */
    public Map<String, Object> getCustomMetaData() {
        return customMetaData;
    }

    /**
     * Sets custom metadata for the document fragment.
     * 
     * @param customMetaData the new map of custom metadata to set
     */
    public void setCustomMetaData(Map<String, Object> customMetaData) {
        this.customMetaData = customMetaData;
    }
}