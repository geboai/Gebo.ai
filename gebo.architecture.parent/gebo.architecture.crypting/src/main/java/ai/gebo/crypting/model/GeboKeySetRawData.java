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
 * GeboKeySetRawData is a data model class used to store and manage
 * a pair of public and private cryptographic keys in byte array format.
 * 
 * Gebo.ai comment agent
 */
public class GeboKeySetRawData {

    // Fields to store the public and private keys as byte arrays
    private final byte[] pub, priv;

    /**
     * Constructs a GeboKeySetRawData instance with specified public and
     * private key byte arrays.
     * 
     * @param pub  the byte array representing the public key
     * @param priv the byte array representing the private key
     */
    public GeboKeySetRawData(byte[] pub, byte[] priv) {
        this.priv = priv;
        this.pub = pub;
    }

    /**
     * Returns the public key as a byte array.
     * 
     * @return the byte array representing the public key
     */
    public byte[] getPub() {
        return pub;
    }

    /**
     * Returns the private key as a byte array.
     * 
     * @return the byte array representing the private key
     */
    public byte[] getPriv() {
        return priv;
    }
}