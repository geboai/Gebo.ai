/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.analisys.model;

import ai.gebo.model.base.GBaseObject;

/**
 * AI generated comments
 * The {@code GeboInstallation} class represents a specific installation instance of Gebo.ai.
 * It extends from {@code GBaseObject}, inheriting base object properties.
 */
public final class GeboInstallation extends GBaseObject {

    /**
     * Private constructor to prevent external instantiation.
     * Initializes the installation object with predefined code and description.
     */
    private GeboInstallation() {
        // Sets a specific code for this installation.
        setCode("GEBO-INSTALLATION");
        // Sets a description indicating the nature of this installation.
        setDescription("Gebo.ai installation");
    }

    /**
     * A singleton instance of the {@code GeboInstallation} class.
     * This ensures a single, shared installation instance.
     */
    public static final GeboInstallation instance = new GeboInstallation();
}