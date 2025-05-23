/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.fastsetup.model;

import ai.gebo.model.virtualfs.VFilesystemReference;
import jakarta.validation.constraints.NotNull;

/**
 * This class represents the setup data for a work directory in a fast setup process.
 * It holds a reference to the work directory in the form of a VFilesystemReference object.
 * 
 * <p>
 * AI generated comments
 * </p>
 */
public class FastWorkDirectorySetupData {

    /** 
     * Reference to the work directory, must not be null as per validation constraints.
     */
    @NotNull
    private VFilesystemReference workDirectory = null;

    /**
     * Default constructor for FastWorkDirectorySetupData.
     */
    public FastWorkDirectorySetupData() {
        // An empty constructor for initializing FastWorkDirectorySetupData objects
    }

    /**
     * Retrieves the work directory reference.
     * 
     * @return the current VFilesystemReference for the work directory.
     */
    public VFilesystemReference getWorkDirectory() {
        return workDirectory;
    }

    /**
     * Sets the work directory reference.
     * 
     * @param workDirectory a VFilesystemReference object representing the new work directory. 
     */
    public void setWorkDirectory(VFilesystemReference workDirectory) {
        this.workDirectory = workDirectory;
    }
}
