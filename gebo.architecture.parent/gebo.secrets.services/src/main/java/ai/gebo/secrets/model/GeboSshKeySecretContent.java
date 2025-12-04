/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.secrets.model;

import jakarta.validation.constraints.NotNull;

/**
 * Gebo.ai comment agent
 *
 * A class representing the content of a Gebo SSH key secret. 
 * It contains SSH key-related information, such as email, private key, public key, and passphrase.
 */
public class GeboSshKeySecretContent extends AbstractGeboSecretContent {

    // Email associated with the SSH key
	@NotNull
    private String email = null;

    // Private SSH key
	@NotNull
    private String key = null;

    // Public SSH key
	@NotNull
    private String pub = null;

    // Passphrase for the SSH key
	@NotNull
    private String passphrase = null;

    /**
     * Gets the email associated with this SSH key.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email associated with this SSH key.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the private SSH key.
     *
     * @return the private SSH key
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the private SSH key.
     *
     * @param key the private SSH key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets the public SSH key.
     *
     * @return the public SSH key
     */
    public String getPub() {
        return pub;
    }

    /**
     * Sets the public SSH key.
     *
     * @param pub the public SSH key to set
     */
    public void setPub(String pub) {
        this.pub = pub;
    }

    /**
     * Gets the passphrase for the SSH key.
     *
     * @return the passphrase
     */
    public String getPassphrase() {
        return passphrase;
    }

    /**
     * Sets the passphrase for the SSH key.
     *
     * @param passphrase the passphrase to set
     */
    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    /**
     * Returns the type of the secret, which is SSH_KEY.
     *
     * @return the secret type
     */
    @Override
    public GeboSecretType type() {
        return GeboSecretType.SSH_KEY;
    }
}