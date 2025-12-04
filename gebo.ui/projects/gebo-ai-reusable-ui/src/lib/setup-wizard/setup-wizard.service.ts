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
 * This file implements a service for managing setup wizard configurations in an Angular application. 
 * It provides functionality to track the status of various setup steps, determine if mandatory configurations 
 * are completed, and organize wizard sections in priority order.
 */

import { Inject, Injectable, Injector } from "@angular/core";
import { SetupWizardItem, SetupWizardsSection, WIZARD_SECTION } from "./setup-wizard-step";
import { forkJoin, map, mergeMap, Observable, of } from "rxjs";

/**
 * Helper function that filters elements from the first array that are not present in the second array.
 * Used for finding elements that haven't been processed yet.
 * 
 * @param v1 Source array
 * @param v2 Array containing elements to exclude
 * @returns Array of elements from v1 that are not in v2
 */
function filterNotContained<T>(v1: T[], v2: T[]): T[] {
    return v1?.filter(c => (v2.find(x => x === c) ? false : true));
}

/**
 * Represents the overall status of the setup process.
 * - 'incomplete': Mandatory setups are missing
 * - 'complete': All mandatory setups are completed, but some optional setups are missing
 * - 'full': All setups are completed (mandatory and optional)
 */
export type SetupStatus = "incomplete" | "complete" | "full";

/**
 * Interface representing a setup wizard item with its installation status.
 * Used internally to track both the item definition and whether it's installed.
 */
interface InstalledSetupWizardItem {
    installed: boolean;
    wizardItem?: SetupWizardItem;
}

/**
 * Injectable service that manages setup wizard configurations and their statuses.
 * This service is responsible for:
 * - Ordering wizard sections by priority
 * - Initializing required service instances
 * - Determining the overall setup status
 * - Tracking completion of mandatory and optional setup steps
 */
@Injectable({ providedIn: "root" })
export class SetupWizardService {
    private _configurations: SetupWizardsSection[] = [];

    /**
     * Initializes the SetupWizardService by organizing configurations in priority order
     * and resolving all required service instances.
     * 
     * @param injector Angular injector to resolve service instances
     * @param configs Optional array of wizard section configurations
     */
    constructor(private injector: Injector, @Inject(WIZARD_SECTION) configs?: SetupWizardsSection[]) {

        if (configs && configs.length) {
            const cfgs: SetupWizardsSection[] = [];
            do {
                const notContained = filterNotContained(configs, cfgs);
                if (notContained && notContained.length) {
                    let minPriority: number = 1000000000;
                    let minValue: SetupWizardsSection | undefined;
                    notContained.forEach(e => {
                        if (minPriority > e.orderEntry) {
                            minPriority = e.orderEntry;
                            minValue = e;
                        }
                    });
                    if (minValue)
                        cfgs.push(minValue);
                }
            } while (cfgs.length < configs.length);
            this._configurations = cfgs;
        }
        if (this._configurations && this._configurations.length) {

            this._configurations.forEach(c => {
                if (!c.enabledServiceInstance) {
                    c.enabledServiceInstance = this.injector.get(c.enabledService);
                }
                if (!c.setupCompletedServiceInstance) {
                    c.setupCompletedServiceInstance = this.injector.get(c.setupCompletedService);
                }
                if (!c.installedModuleInstance && c.installedModule) {
                    c.installedModuleInstance = this.injector.get(c.installedModule);
                }
            });
        }
    }

    /**
     * Gets the ordered list of configuration sections.
     * 
     * @returns Array of setup wizard sections in priority order
     */
    public get configurations(): SetupWizardsSection[] {
        return this._configurations;
    }

    /**
     * Calculates the overall setup status based on the provided list of setup items.
     * 
     * @param items Array of setup wizard items to evaluate
     * @returns Status of the setup process as 'incomplete', 'complete', or 'full'
     */
    public calculateSetupStatus(items: SetupWizardItem[]): SetupStatus {
        let status: SetupStatus;
        let missingSetups: boolean = false;
        let missingMandatorySetups: boolean = false;
        status = "complete";
        items?.forEach(x => {
            if (x.enabled === true) {
                if (x.mandatory === true) {
                    if (x.alreadyCompleted !== true) {
                        missingMandatorySetups = true;
                    }
                } else {
                    if (x.alreadyCompleted !== true) {
                        missingSetups = true;
                    }
                }
                status = (missingMandatorySetups === true) ? "incomplete" : (missingSetups === true) ? "complete" : "full";
            }
        });
        return status;
    }

    /**
     * Retrieves the current setup status by getting the actual status of all items
     * and then calculating the overall status.
     * 
     * @returns Observable that emits the current setup status
     */
    public getSetupStatus(): Observable<SetupStatus> {
        return this.getActualStatus().pipe(map(items => {
            return this.calculateSetupStatus(items);
        }));
    }

    /**
     * Retrieves the current status of all wizard items by checking if they are installed,
     * enabled, and completed.
     * 
     * @returns Observable that emits an array of setup wizard items with their current status
     */
    public getActualStatus(): Observable<SetupWizardItem[]> {
        const setupItemObservables: Observable<InstalledSetupWizardItem>[] = [];
        this.configurations.forEach(c => {
            if (c.enabledServiceInstance) {
                const installedModuleObservable = c.installedModuleInstance ? c.installedModuleInstance.getInstalledModule() : of(true);
                const enabled: Observable<boolean> = c.enabledServiceInstance.getBooleanStatus();
                const observableItem: Observable<SetupWizardItem> = enabled.pipe(mergeMap((enabledValue: boolean) => {
                    const item: SetupWizardItem = {
                        orderEntry: c.orderEntry,
                        alreadyCompleted: false,
                        enabled: false,
                        description: c.description,
                        label: c.label,
                        wizardComponent: c.wizardComponent,
                        wizardSectionId: c.wizardSectionId,
                        mandatory: c.mandatory === true,
                        experimental: c.experimental===true,
                        requredStepsIds: c.requredStepsIds
                    };
                    item.enabled = enabledValue;
                    if (enabledValue === true && c.setupCompletedServiceInstance) {
                        return c.setupCompletedServiceInstance.getBooleanStatus().pipe(map((isSetup: boolean) => {
                            item.alreadyCompleted = isSetup === true;
                            return item;
                        }))
                    } else {
                        item.alreadyCompleted = false;
                        return of(item);
                    }
                }));
                const installedcheckObservable: Observable<InstalledSetupWizardItem> = installedModuleObservable.pipe(mergeMap((installedModule: boolean) => {
                    return observableItem.pipe(map((wizardItem: SetupWizardItem) => {
                        const data: InstalledSetupWizardItem = {
                            installed: installedModule === true,
                            wizardItem: wizardItem

                        };
                        return data;
                    }));
                }))
                setupItemObservables.push(installedcheckObservable);
            }
        });
        return forkJoin(setupItemObservables).pipe(map((modulesList:InstalledSetupWizardItem[])=>{
            const outModules:SetupWizardItem[]=[];
            modulesList?.forEach(x=>{
                if (x.installed===true && (x.wizardItem)) {
                    outModules.push(x.wizardItem);
                }
            });
            return outModules;
        }));
    }

    /**
     * Checks if all mandatory configurations are properly set up.
     * A mandatory configuration is considered properly set if it is enabled and completed.
     * 
     * @returns Observable that emits true if all mandatory configurations are set, false otherwise
     */
    public getMandatoryConfigurationsAreSet(): Observable<boolean> {
        return this.getActualStatus().pipe(map(s => {
            let ok: boolean = true;
            if (s && s.length) {
                s.forEach(e => {
                    ok = ok && ((e.mandatory === true && e.enabled == true && e.alreadyCompleted === true) || e.mandatory !== true || e.enabled !== true);
                });
            }
            return ok;
        }));
    }
}