/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knlowledgebase.model.contents;

/**
 * AI generated comments
 * 
 * GVirtualFolder represents a virtual folder in a virtual filesystem.
 * It extends the GAbstractVirtualFilesystemObject to inherit common filesystem object properties.
 */
public class GVirtualFolder extends GAbstractVirtualFilesystemObject {

    /**
     * Serial version UID for serialization and deserialization compatibility.
     */
    private static final long serialVersionUID = -3512368048642319405L;

    /**
     * Default constructor for creating an empty GVirtualFolder instance.
     */
    public GVirtualFolder() {
    }

    /**
     * Constructs a GVirtualFolder by copying properties from the given
     * GAbstractVirtualFilesystemObject.
     *
     * @param o the GAbstractVirtualFilesystemObject whose properties are to be copied.
     */
    public GVirtualFolder(GAbstractVirtualFilesystemObject o) {
        super(o);
    }
}