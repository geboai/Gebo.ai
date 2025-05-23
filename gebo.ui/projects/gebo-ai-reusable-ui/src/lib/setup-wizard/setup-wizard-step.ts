/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * AI generated comments
 * This file implements a wizard framework for setup or configuration processes.
 * It provides abstract services, components, and interfaces for creating wizard
 * sections with status tracking capabilities.
 */

import { Component, EventEmitter, Injectable, InjectionToken, Input, Output, Type } from '@angular/core';
import { Observable, of } from 'rxjs';
import { BaseWizardSectionComponent } from './base-wizard-section.component';

/**
 * Abstract service that defines the contract for retrieving boolean status information.
 * Used to determine if a wizard section is enabled or completed.
 */
export abstract class AbstractStatusService {
    /**
     * Returns an Observable that emits a boolean value indicating the status.
     * Implementing classes define the specific logic for determining this status.
     */
    public abstract getBooleanStatus(): Observable<boolean>;
}

/**
 * Abstract service that defines the contract for checking if a module is installed.
 * Used to determine if features dependent on specific modules should be available.
 */
export abstract class AbstractInstalledModuleService {
    /**
     * Returns an Observable that emits a boolean value indicating
     * whether the module is installed or not.
     */
    public abstract getInstalledModule(): Observable<boolean>;
}

/**
 * A concrete implementation of AbstractStatusService that always returns true.
 * Useful as a default or fallback service when a section should always be enabled/completed.
 * Provided at the root level for application-wide availability.
 */
@Injectable({ providedIn: "root" })
export class AlwaysTrueStatusService extends AbstractStatusService {
    /**
     * Always returns an Observable that emits true.
     * Guarantees that the status check will always pass.
     */
    public override getBooleanStatus(): Observable<boolean> {
        return of(true);
    }
}

/**
 * A wizard section component that doesn't render any UI elements.
 * Can be used for wizard sections that perform background operations or
 * serve as placeholders in the wizard flow.
 */
@Component({
    selector: "gebo-wizard-section-with-no-ui", template: "",
    standalone: false
})
export class WizardSectionWithNoUI extends BaseWizardSectionComponent {
    /**
     * Empty implementation of the required reloadData method from the base component.
     * No data reloading is necessary for a UI-less component.
     */
    public override reloadData(): void {
        // Intentionally empty
    }
}

/**
 * Interface defining the structure of a wizard section configuration.
 * Contains all metadata needed to display, manage, and validate a wizard section.
 */
export interface SetupWizardsSection {
    /** Determines the display order of this section in the wizard */
    orderEntry: number;
    /** Unique identifier for the wizard section */
    wizardSectionId: string;
    /** IDs of steps that must be completed before this section can be accessed */
    requredStepsIds?: string[];
    /** Display name for the section */
    label: string;
    /** Longer text explaining the purpose of this section */
    description: string;
    /** Service type that determines if this section is enabled */
    enabledService: Type<AbstractStatusService>;
    /** Instance of the enabled service after initialization */
    enabledServiceInstance?: AbstractStatusService;
    /** Service type that determines if setup for this section is completed */
    setupCompletedService: Type<AbstractStatusService>;
    /** Instance of the setup completed service after initialization */
    setupCompletedServiceInstance?: AbstractStatusService;
    /** Component to render for this wizard section */
    wizardComponent: Type<BaseWizardSectionComponent>;
    /** Optional service to check if required module is installed */
    installedModule?: Type<AbstractInstalledModuleService>;
    /** Instance of installed module service after initialization */
    installedModuleInstance?:AbstractInstalledModuleService;
    /** Whether this section must be completed to finish the wizard */
    mandatory?: boolean;
}

/**
 * Interface representing a processed wizard section item ready for display in the UI.
 * Contains runtime state information derived from the configuration and services.
 */
export interface SetupWizardItem {
    /** Determines the display order of this item in the wizard */
    orderEntry: number;
    /** IDs of steps that must be completed before this item can be accessed */
    requredStepsIds?: string[],
    /** Unique identifier for the wizard section */
    wizardSectionId: string;
    /** Display name for the item */
    label: string,
    /** Longer text explaining the purpose of this item */
    description: string;
    /** Whether this section is currently enabled */
    enabled: boolean;
    /** Whether this section has already been completed */
    alreadyCompleted: boolean;
    /** Component to render for this wizard item */
    wizardComponent: Type<BaseWizardSectionComponent>;
    /** Whether this item must be completed to finish the wizard */
    mandatory?: boolean;
}

/**
 * Injection token for providing the wizard section configurations to components
 * that need to render or process the wizard.
 */
export const WIZARD_SECTION = new InjectionToken<SetupWizardsSection[]>('WIZARD_SECTION');