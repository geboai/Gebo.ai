/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { Injector } from "@angular/core";

import { Observable } from "rxjs";
import { BaseEntityEditingComponent } from "./base-entity-editing.component";
import { GeboUIActionRequest } from "../../architecture/actions.model";
/**
 * AI generated comments
 * This file defines interfaces and utilities for managing and navigating through wizard-style interfaces
 * in a Gebo.ai application. It provides configuration structures for multi-step wizards and helper functions
 * to manipulate wizard steps.
 */

/**
 * Interface that defines the configuration for a single step in a wizard flow.
 * Each step contains information about its identity, description, navigation options,
 * and callbacks to handle transitions between steps.
 */
export interface GeboAIEntitiesSettingWizardConfiguration {
    /** Unique identifier for the wizard step */
    id: string;
    /** Description text explaining the purpose of this wizard step */
    description: string;
    /** Optional label text for the next step button */
    nextStepLabel?: string;
    /** Optional icon name/path for the next step button */
    nextStepIcon?: string;
    /** Optional ID referencing the configuration for the next step */
    nextStepConfigurationId?: string;
    /** 
     * Optional callback function that handles navigation to the next step.
     * Takes the current component, injector, and optional data as parameters
     * and returns an Observable of GeboUIActionRequest.
     */
    nextStepNavigation?: (actualComponent: BaseEntityEditingComponent<any>,injector:Injector, actualData?: any) => Observable<GeboUIActionRequest>;
    /** Optional label text for the previous step button */
    previusStepLabel?: string;
    /** Optional icon name/path for the previous step button */
    previusStepIcon?: string;
    /** Optional ID referencing the configuration for the previous step */
    previusStepConfigurationId?: string;
    /** 
     * Optional callback function that handles navigation to the previous step.
     * Takes the current component, injector, and optional data as parameters
     * and returns an Observable of GeboUIActionRequest.
     */
    previusStepNavigation?: (actualComponent: BaseEntityEditingComponent<any>,injector:Injector, actualData?: any) => Observable<GeboUIActionRequest>;
}

/**
 * Interface that defines the data structure for the navigation buttons bar in a wizard.
 * Contains state information about available steps and actions for the navigation buttons.
 */
export interface WizardButtonsBarData {
    /** Flag indicating whether there's a next step available */
    hasNextStep: boolean;
    /** Flag indicating whether there's a previous step available */
    hasPreviusStep: boolean;
    /** Icon name/path for the next step button */
    nextStepIcon: string;
    /** Icon name/path for the previous step button */
    previusStepIcon: string;
    /** Label text for the next step button */
    nextStepLabel: string;
    /** Label text for the previous step button */
    previusStepLabel: string;
    /** Function to call when the next step button is clicked */
    nextStepAction: () => void;
    /** Function to call when the previous step button is clicked */
    previusStepAction: () => void;
    /** Optional array containing all wizard step configurations */
    wizardStepsConfigurations?: GeboAIEntitiesSettingWizardConfiguration[];
    /** Optional ID of the current wizard step configuration */
    actualWizardStepConfigrationId?: string;
}

/**
 * Creates a new wizard configuration array by slicing an existing wizard from a specified starting index.
 * The function creates a deep copy of the sliced wizard steps and removes previous step navigation
 * information from the first step of the new wizard (since it's now the first step).
 * 
 * @param startIndex - The index from which to start slicing the wizard
 * @param wizard - The original wizard configuration array
 * @returns A new wizard configuration array starting from the specified index
 */
export function sliceWizard(startIndex: number, wizard: GeboAIEntitiesSettingWizardConfiguration[]): GeboAIEntitiesSettingWizardConfiguration[] {
    const newWizard: GeboAIEntitiesSettingWizardConfiguration[] = [];
    const toCopy = wizard.slice(startIndex);
    toCopy.forEach(x => {
        newWizard.push({ ...x });
    });
    if (newWizard && newWizard.length) {
        // Clear previous step navigation for the first step in the new wizard
        newWizard[0].previusStepNavigation = undefined;
        newWizard[0].previusStepConfigurationId = undefined;
        newWizard[0].previusStepLabel = undefined;
        newWizard[0].previusStepIcon = undefined;
    }
    return newWizard;
}