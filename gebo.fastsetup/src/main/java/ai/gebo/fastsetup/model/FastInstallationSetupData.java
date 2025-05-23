/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.fastsetup.model;

import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * Represents the setup data required for a fast installation process,
 * including user credentials and licence agreement acceptance.
 */
public class FastInstallationSetupData {

    // Username for the setup process, must not be null
    @NotNull
    private String username = null;

    // Password for the setup process, must not be null
    @NotNull
    private String password = null;

    // Confirmation password, can be null
    private String passwordC = null;

    // Licence agreement acceptance, must not be null
    @NotNull
    private String licenceAgreement = null;

    /**
     * Default constructor for the FastInstallationSetupData class.
     */
    public FastInstallationSetupData() {

    }

    /**
     * Gets the username required for setup.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username required for setup.
     * 
     * @param email the username to set
     */
    public void setUsername(String email) {
        this.username = email;
    }

    /**
     * Gets the password required for setup.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password required for setup.
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the confirmation password.
     * 
     * @return the confirmation password
     */
    public String getPasswordC() {
        return passwordC;
    }

    /**
     * Sets the confirmation password.
     * 
     * @param passwordC the confirmation password to set
     */
    public void setPasswordC(String passwordC) {
        this.passwordC = passwordC;
    }

    /**
     * Gets the licence agreement acceptance.
     * 
     * @return the licence agreement
     */
    public String getLicenceAgreement() {
        return licenceAgreement;
    }

    /**
     * Sets the licence agreement acceptance.
     * 
     * @param licenceAgreement the licence agreement to set
     */
    public void setLicenceAgreement(String licenceAgreement) {
        this.licenceAgreement = licenceAgreement;
    }

}