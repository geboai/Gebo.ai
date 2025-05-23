/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knlowledgebase.model.licence;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * Represents a Licence entity within the Gebo knowledge base system.
 * This entity is mapped to a document in a MongoDB database.
 */
@Document
public class GeboLicence {

    /**
     * Enum representing the different types of Gebo Licences available.
     */
    public static enum GeboLicenceType {
        LIMITED_TRIAL, COMMON, ENTERPRISE
    }

    /**
     * Unique identifier for the Gebo Licence.
     */
    @Id
    @NotNull
    private String code = null;

    /**
     * The agreed license text or terms.
     */
    @NotNull
    private String agreedLicence = null;

    /**
     * The type of the Gebo Licence.
     */
    @NotNull
    private GeboLicenceType licenceType = null;

    /**
     * Date when the licence was agreed upon or issued.
     */
    @NotNull
    private Date date = null;

    /**
     * The username of the user who signed the licence.
     */
    @NotNull
    private String signerUser = null;

    /**
     * Default constructor for GeboLicence.
     */
    public GeboLicence() {
    }

    /**
     * Retrieves the agreed licence text.
     * 
     * @return the agreed licence text.
     */
    public String getAgreedLicence() {
        return agreedLicence;
    }

    /**
     * Sets the agreed licence text.
     * 
     * @param agreedLicence the agreed licence text to be set.
     */
    public void setAgreedLicence(String agreedLicence) {
        this.agreedLicence = agreedLicence;
    }

    /**
     * Retrieves the unique code of the Gebo Licence.
     * 
     * @return the unique code of the licence.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the unique code for the Gebo Licence.
     * 
     * @param code the unique code to be set.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Retrieves the type of the Gebo Licence.
     * 
     * @return the type of the Gebo Licence.
     */
    public GeboLicenceType getLicenceType() {
        return licenceType;
    }

    /**
     * Sets the type for the Gebo Licence.
     * 
     * @param licenceType the type of licence to be set.
     */
    public void setLicenceType(GeboLicenceType licenceType) {
        this.licenceType = licenceType;
    }

    /**
     * Retrieves the date when the licence was agreed or issued.
     * 
     * @return the date of the licence.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date for the Gebo Licence.
     * 
     * @param date the date to be set.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Retrieves the username of the signer of the licence.
     * 
     * @return the username of the licence signer.
     */
    public String getSignerUser() {
        return signerUser;
    }

    /**
     * Sets the username of the user who signed the licence.
     * 
     * @param signerUser the username of the signer to be set.
     */
    public void setSignerUser(String signerUser) {
        this.signerUser = signerUser;
    }
}