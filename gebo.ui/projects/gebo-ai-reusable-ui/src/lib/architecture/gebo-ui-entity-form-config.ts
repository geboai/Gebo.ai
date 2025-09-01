/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { InjectionToken, Type } from "@angular/core";
import { BaseEntityEditingComponent } from "../controls/base-entity-editing-component/base-entity-editing.component";
/**
 * @fileoverview This file defines interfaces and tokens for entity form configuration in Gebo.ai UI.
 * AI generated comments
 */

/**
 * Interface defining the configuration for entity forms in the Gebo UI system.
 * Provides a structure to associate entity names with their corresponding UI component types.
 */
export interface GeboUIEntityFormConfig {
    /**
     * The name of the entity being configured
     */
     entityName:string;
    /**
     * The component type that provides editing functionality for this entity.
     * Must extend BaseEntityEditingComponent for the specific entity type.
     */
     entityUI:Type<BaseEntityEditingComponent<any>>;
};

/**
 * Injection token used to provide entity form configurations in the Angular dependency injection system.
 * This token allows services and components to inject entity form configurations.
 */
export const GEBO_UI_ENTITY_FORM_TOKEN=new InjectionToken("GeboUIEntityFormConfig");