/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * Represents the configuration and state for opened modal dialogs in the application.
 * Used to control the display mode and pass data to modal components.
 * AI generated comments
 */
export interface OpenedModals {
    /**
     * Defines the operation mode of the modal:
     * - "NEW": Creating a new entity
     * - "EDIT": Editing an existing entity
     * - "EDIT_OR_NEW": Modal can handle both creation and editing
     */
    mode: "NEW" | "EDIT"|"EDIT_OR_NEW";
    
    /**
     * Optional entity data to be displayed or edited in the modal
     * Contains common entity properties like code and description
     */
    entity?: { code?: string; description?: string; };
    
    /**
     * Optional map of additional input parameters that can be passed to the modal
     * Allows for flexible data passing between components
     */
    composedInputs?: { [key: string]: any } ;
}