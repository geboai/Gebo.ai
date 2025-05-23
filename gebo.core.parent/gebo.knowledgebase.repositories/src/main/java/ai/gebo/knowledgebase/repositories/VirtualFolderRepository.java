/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knowledgebase.repositories;

import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;

/**
 * AI generated comments
 * Repository interface for managing {@link GVirtualFolder} entities in the application's virtual filesystem.
 * Extends the {@link IGAbstractVirtualFilesystemObjectRepository} to inherit common filesystem operations.
 */
public interface VirtualFolderRepository extends IGAbstractVirtualFilesystemObjectRepository<GVirtualFolder> {

    /**
     * Provides the managed type this repository handles.
     * 
     * @return the class type of {@link GVirtualFolder}
     */
    @Override
    default Class<GVirtualFolder> getManagedType() {
        return GVirtualFolder.class; // Returns the class type for GVirtualFolder
    }
}