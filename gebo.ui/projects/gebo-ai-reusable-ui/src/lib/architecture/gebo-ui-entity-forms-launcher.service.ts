/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * @fileoverview This file defines the base entity forms launcher service for Gebo UI.
 * AI generated comments
 */

import { Injectable } from "@angular/core";
import { GeboUIEntityFormConfig } from "./gebo-ui-entity-form-config";

/**
 * Injectable service that provides functionality for launching entity forms in the Gebo UI.
 * This is an abstract class that must be implemented by concrete services.
 * The service is provided at the root level so it's available throughout the application.
 */
@Injectable({
    providedIn: "root"
})
export abstract class GeboUIEntityFormsLauncherService {

    /**
     * Gets the current entity form configurations available in the application.
     * Implementations should return all active form configurations that can be used.
     * 
     * @returns An array of GeboUIEntityFormConfig objects representing available form configurations
     */
    public abstract getCurrentConfigurations(): GeboUIEntityFormConfig[];
}