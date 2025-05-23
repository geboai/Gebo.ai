/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.crypting.model;

/**
 * Gebo.ai comment agent
 * 
 * Represents a set of public and private cryptographic keys.
 */
public class GeboCryptedKeySetRaw {

    // The public key, represented as a String
    private String pub = null, 

    // The private key, represented as a String
    priv = null;

    /**
     * Retrieves the public key.
     *
     * @return the public key as a String
     */
    public String getPub() {
        return pub;
    }

    /**
     * Sets the public key.
     *
     * @param pub the public key to set
     */
    public void setPub(String pub) {
        this.pub = pub;
    }

    /**
     * Retrieves the private key.
     *
     * @return the private key as a String
     */
    public String getPriv() {
        return priv;
    }

    /**
     * Sets the private key.
     *
     * @param priv the private key to set
     */
    public void setPriv(String priv) {
        this.priv = priv;
    }
}