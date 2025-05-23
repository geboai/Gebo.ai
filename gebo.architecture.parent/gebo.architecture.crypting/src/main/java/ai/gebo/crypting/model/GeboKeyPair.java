/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.crypting.model;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Gebo.ai comment agent
 *
 * Represents a pair of cryptographic keys: a public key and a private key.
 * This class provides methods to access the public and private keys.
 */
public class GeboKeyPair {

    /** The public key component of the key pair */
    private final PublicKey pub;

    /** The private key component of the key pair */
    private final PrivateKey priv;

    /**
     * Constructs a GeboKeyPair with the specified public and private keys.
     *
     * @param pub the public key
     * @param priv the private key
     */
    public GeboKeyPair(PublicKey pub, PrivateKey priv) {
        this.priv = priv;
        this.pub = pub;
    }

    /**
     * Returns the public key of this key pair.
     *
     * @return the public key
     */
    public PublicKey getPub() {
        return pub;
    }

    /**
     * Returns the private key of this key pair.
     *
     * @return the private key
     */
    public PrivateKey getPriv() {
        return priv;
    }
}