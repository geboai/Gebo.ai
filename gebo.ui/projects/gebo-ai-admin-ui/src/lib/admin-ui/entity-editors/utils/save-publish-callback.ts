/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { BaseEntityEditingComponent } from "@Gebo.ai/reusable-ui";
/**
 * AI generated comments
 * 
 * This module provides a utility function for saving and publishing components that extend BaseEntityEditingComponent.
 * It helps standardize the save-and-publish workflow in the application by providing a reusable function.
 */

/**
 * Executes a save operation on a component and then triggers a publish operation if available.
 * 
 * @param component - The component instance that extends BaseEntityEditingComponent with optional code and description properties
 * @template ComponentType - A generic type that extends BaseEntityEditingComponent with the specified properties
 * 
 * This function first calls the doSave method on the provided component, and then
 * checks if the component has a doPublish method. If it does, it calls that method
 * after the save operation completes. This creates a sequential save-then-publish workflow.
 */
export function doSaveAndPublishCall<ComponentType extends BaseEntityEditingComponent<{ code?: string, description?: string }>>(component: ComponentType) {
    component.doSave((d: any) => {
        // Check if the component has a doPublish method and call it after save completes
        if ((component as any).doPublish) {
            (component as any).doPublish();
        }
    });
}