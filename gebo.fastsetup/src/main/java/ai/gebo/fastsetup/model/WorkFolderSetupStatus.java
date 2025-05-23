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

/**
 * Represents the setup status of a work folder component.
 * This includes the work directory reference used for virtual file system operations.
 * 
 * AI generated comments
 */
public class WorkFolderSetupStatus extends ComponentSetupStatus {
    
    /** 
     * A reference to the work directory within the virtual file system.
     */
    public VFilesystemReference workDirectory = null;
}