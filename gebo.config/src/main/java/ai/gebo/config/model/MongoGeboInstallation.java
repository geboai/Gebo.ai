/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.config.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.model.virtualfs.VFilesystemReference;

/**
 * Represents a MongoDB document for storing Gebo installation details.
 * This includes paths and setup status for a Gebo instance.
 * 
 * Document annotation marks this class as a MongoDB document.
 * AI generated comments
 */
@Document
public class MongoGeboInstallation {

    // Constant for default entry
    public static final String ENTRY = "default";

    // Unique identifier for the MongoDB document
    @Id
    private String id = null;

    // Path to the Gebo work directory
    private String geboWorkdirectoryPath = null;

    // Internal reference to the virtual filesystem work directory
    private VFilesystemReference workDirectoryInternalValue = null;

    // Flag to check if the first setup has been completed
    private Boolean firstSetupDone = null;

    /**
     * Default constructor.
     */
    public MongoGeboInstallation() {

    }

    /**
     * Gets the path to the Gebo work directory.
     *
     * @return the gebo work directory path
     */
    public String getGeboWorkdirectoryPath() {
        return geboWorkdirectoryPath;
    }

    /**
     * Sets the path to the Gebo work directory.
     *
     * @param geboWorkdirectoryPath the new path for the Gebo work directory
     */
    public void setGeboWorkdirectoryPath(String geboWorkdirectoryPath) {
        this.geboWorkdirectoryPath = geboWorkdirectoryPath;
    }

    /**
     * Gets the unique identifier for this MongoDB document.
     *
     * @return the document ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this MongoDB document.
     *
     * @param id the new document ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Checks if the first setup is done.
     *
     * @return true if the first setup is done, false otherwise
     */
    public Boolean getFirstSetupDone() {
        return firstSetupDone;
    }

    /**
     * Sets the status of the first setup.
     *
     * @param firstSetupDone the new status of the first setup
     */
    public void setFirstSetupDone(Boolean firstSetupDone) {
        this.firstSetupDone = firstSetupDone;
    }

    /**
     * Gets the internal value of the work directory reference in the virtual filesystem.
     *
     * @return the internal value of the work directory reference
     */
    public VFilesystemReference getWorkDirectoryInternalValue() {
        return workDirectoryInternalValue;
    }

    /**
     * Sets the internal value of the work directory reference in the virtual filesystem.
     *
     * @param workDirectoryInternalValue the new internal value of the work directory reference
     */
    public void setWorkDirectoryInternalValue(VFilesystemReference workDirectoryInternalValue) {
        this.workDirectoryInternalValue = workDirectoryInternalValue;
    }
}