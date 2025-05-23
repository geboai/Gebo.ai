/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { Type } from "@angular/core";
/**
 * @fileoverview Gebo UI Actions Framework - Provides types and interfaces for handling UI actions in Gebo applications.
 * AI generated comments
 */

/**
 * Enumeration defining the types of actions that can be performed in the UI
 * - NEW: Create a new entity
 * - OPEN: Open an existing entity
 * - OPEN_OR_NEW: Either open if exists or create new if not
 */
export enum GeboActionType {
    NEW,
    OPEN,
    OPEN_OR_NEW
};

/**
 * Enumeration defining the navigation actions available in a wizard interface
 * - GO_FORWARD: Navigate to the next step in a wizard
 * - GO_BACWKARD: Navigate to the previous step in a wizard (note: typo in enum value)
 */
export enum GeboWizardActionType {
    GO_FORWARD,GO_BACWKARD
};

/**
 * Enumeration defining the types of actions that have been performed
 * - SAVED: Entity was saved
 * - DELETED: Entity was deleted
 * - INSERTED: Entity was inserted
 * - CLOSING_WINDOW: Window is being closed
 */
export enum GeboActionPerformedType {
    SAVED,DELETED,INSERTED,CLOSING_WINDOW
}

/**
 * Interface representing an event triggered after an action has been performed
 * Contains the type of action and the target entity information
 */
export interface GeboActionPerformedEvent {
    actionType:GeboActionPerformedType
    target:{code?:string,description?:string};
};

/**
 * Interface representing an event triggered after a wizard action has been performed
 * Contains information about the wizard navigation, entity, and step identifiers
 */
export interface GeboWizardActionPerformedEvent {
        wizardActionType:GeboWizardActionType;
        entityName:string;
        target?:{code?:string,description?:string};
        actualWizardStepId?:string;
        targetWizardStepId?:string;
};

/**
 * Type definition for a callback function that handles action performed events
 */
export type GeboActionPerformedCallback=(event:GeboActionPerformedEvent)=>void;

/**
 * Type definition for a callback function that handles wizard action performed events
 */
export type GeboWizardActionPerformedCallback=(event:GeboWizardActionPerformedEvent)=>void;

/**
 * Interface representing a request to perform a UI action
 * Contains context information, target information, action type, and optional callbacks
 * for handling the response after the action is performed
 */
export interface GeboUIActionRequest {
    contextType: string;
    context: { code?: string, description?: string };
    actionType: GeboActionType;
    targetType: string;
    target: { code?: string, description?: string };
    targetFormInputs?:{[key:string]:any};
    onActionPerformed?:GeboActionPerformedCallback;
    onWizardActionPerformed?:GeboWizardActionPerformedCallback;
};

/**
 * Interface for components that can listen to and handle UI action requests
 * Implementing components can optionally specify which target types they handle
 * and must implement the handleAction method to process action requests
 */
export interface GeboUIActionRequestListener {
    targetType?: string
    handleAction(action: GeboUIActionRequest): boolean;
}