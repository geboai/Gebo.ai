/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * @file GeboAIPluggableModulesConfigService.ts
 * @description Defines the core services and interfaces for Gebo.ai's pluggable modules architecture.
 * AI generated comments
 */

import { Inject, Injectable, InjectionToken, Injector, Type } from "@angular/core";
import { GeboModuleInfo, GeboModulesConfigControllerService, GProject } from "@Gebo.ai/gebo-ai-rest-api";

import { forkJoin, map, Observable } from "rxjs";
import { GeboAIEntitiesSettingWizardConfiguration } from "../controls/base-entity-editing-component/entities-modification-wizard";
import { GeboActionPerformedCallback, GeboActionPerformedEvent, GeboUIActionRequest, GeboWizardActionPerformedCallback, GeboWizardActionPerformedEvent } from "../architecture/actions.model";

/**
 * Injection token for providing pluggable module UI configuration.
 * Used to inject module configurations into services.
 */
export const GEBO_AI_PLUGGABLE_MODULE_UI_CONFIG = new InjectionToken("PLUGGABLE_MODULE_UI_CONFIG");

/**
 * Interface representing a map of enabled modules where the key is the module ID
 * and the value indicates whether the module is enabled.
 */
export interface GeboAIEnabledModulesMap {
    [key: string]: boolean;
}

/**
 * Interface defining the service contract for pluggable project endpoint modules.
 * Provides methods for finding endpoints by project and creating actions.
 */
export interface GeboAIPluggableProjectEndpointModuleService {
    /**
     * Finds endpoints associated with a specific project by its code
     * @param code The project code to search for
     * @returns Observable of endpoint details
     */
    findByProjectEndpoints(code: string): Observable<{ code?: string; description?: string; parentProjectCode?: string; }[]>;
    
    /**
     * Creates an action request for a specific project
     * @param project The project to create an action for
     * @param wizardStepsConfigurations Optional wizard configurations
     * @param actualWizardStepConfigrationId Optional current wizard step ID
     * @returns Action request configuration
     */
    byProjectCreateAction(project: GProject, wizardStepsConfigurations?: GeboAIEntitiesSettingWizardConfiguration[], actualWizardStepConfigrationId?: string): GeboUIActionRequest;
};

/**
 * Interface defining the structure of a pluggable project endpoint module.
 * Contains metadata about the module and a reference to its service implementation.
 */
export interface GeboAIPluggableProjectEndpointModule {
    moduleId: string;
    projecteEndpointClassName: string;
    addProjectEndpointicon: string;
    addProjectEndpointLabel: string;
    addProjectEndpointTitle: string;
    service: Type<GeboAIPluggableProjectEndpointModuleService>;
};

/*****************************************************************************************************
 * Global Gebo.ai UI configuration info with pluggable modules/knowledge base components dynamic infos
 */
export interface GeboAIEnabledModulesConfig {
    //list of installed modules (loaded from installation backend)
    installedModulesList: GeboModuleInfo[];
    //UI modules custom hooks
    pluggableProjecteEndpointsModules: GeboAIPluggableProjectEndpointModule[];
    configMap: GeboAIEnabledModulesMap;
}

/**
 * Interface for UI project endpoint options displayed in the knowledge base editor.
 * Provides metadata and actions for each endpoint type.
 */
export interface GeboAIUIProjectEndpointOption {
    projecteEndpointClassName: string;
    addProjectEndpointicon?: string;
    addProjectEndpointLabel: string;
    addProjectEndpointTitle: string;
    actionRequestByProject: (project: GProject, wizardStepsConfigurations?: GeboAIEntitiesSettingWizardConfiguration[], actualWizardStepConfigrationId?: string) => GeboUIActionRequest;
}

/****
 * Service to expose list of installed modules (standard/custom or other nature)
 * their hooks to UI knowledge base browser
 */
@Injectable({
    providedIn: "platform"
})
export class GeboAIPluggableModulesConfigService {
    private static modules?: GeboAIEnabledModulesConfig;
    
    /**
     * Initializes the pluggable modules configuration service.
     * Fetches all available modules and filters for enabled ones.
     * 
     * @param modulesConfiguration Array of pluggable project endpoint module configurations
     * @param geboModulesConfigService Service for accessing module configurations from the backend
     */
    constructor(
        @Inject(GEBO_AI_PLUGGABLE_MODULE_UI_CONFIG) private modulesConfiguration: GeboAIPluggableProjectEndpointModule[],
        private geboModulesConfigService: GeboModulesConfigControllerService) {
        geboModulesConfigService.getAllModules().subscribe({
            next: (config) => {
                if (config) {
                    const modules: GeboAIEnabledModulesConfig = {
                        configMap: {},
                        installedModulesList: (config.filter(x => x.enabled === true) as GeboModuleInfo[]),
                        pluggableProjecteEndpointsModules: []
                    };
                    modules.installedModulesList.forEach(x => {
                        if (x.messagingModuleId) {
                            modules.configMap[x.messagingModuleId] = true;
                            const foundEndpointModule = this.modulesConfiguration.find(y => y.moduleId === x.messagingModuleId);
                            if (foundEndpointModule) {
                                modules.pluggableProjecteEndpointsModules.push(foundEndpointModule);
                            }
                        }
                    });

                    GeboAIPluggableModulesConfigService.modules = modules;
                }
            }
        });
    }

    /**
     * Retrieves the current modules configuration.
     * @returns The current enabled modules configuration or undefined if not loaded
     */
    public getConfig(): GeboAIEnabledModulesConfig | undefined {
        return GeboAIPluggableModulesConfigService.modules;
    }
}

/**
 * Service that handles the integration of pluggable project endpoints.
 * Manages the loading, discovery, and configuration of project endpoints from various modules.
 */
@Injectable({
    providedIn: "platform"
})
export class GeboAIPluggableProjectEndpointsService {
    private pluggableUIProjectEndpointServices: GeboAIPluggableProjectEndpointModuleService[] = [];
    private classNames: string[] = [];
    private modules: GeboAIPluggableProjectEndpointModule[] = [];
    
    /**
     * Initializes the pluggable project endpoints service.
     * 
     * @param injector Angular injector for dynamically resolving service instances
     * @param configService Service providing module configuration
     */
    constructor(private injector: Injector, private configService: GeboAIPluggableModulesConfigService) {

    }

    /**
     * Initializes the service structures if they haven't been loaded yet.
     * Loads available modules and their services using the injector.
     */
    private preloadStructures():void {
        if (!this.pluggableUIProjectEndpointServices.length) {
            const config = this.configService.getConfig();
            if (config && config.pluggableProjecteEndpointsModules) {
                config.pluggableProjecteEndpointsModules.forEach(x => {
                    this.classNames.push(x.projecteEndpointClassName);
                    this.pluggableUIProjectEndpointServices.push(this.injector.get(x.service));
                    this.modules.push(x);
                });
            }
        }
        
    }
    
    /***
     * Given a project returns the list of data sources exposed by Gebo.ai software modules (standard or custom)
     * 
     * @param projectCode The code of the project to find endpoints for
     * @returns Observable of endpoint data with class name and icon information
     */
    public findByProjectEndpoints(projectCode: string): Observable<{ code?: string; description?: string; parentProjectCode?: string; className: string; icon: string }[]> {
        this.preloadStructures();
        const observables: Observable<{ code?: string; description?: string; parentProjectCode?: string; className: string; icon: string; }[]>[] = [];
        this.classNames.forEach((className, index) => {
            observables.push(this.pluggableUIProjectEndpointServices[index].findByProjectEndpoints(projectCode).pipe(map(data => {
                return data.map(x => {
                    const out: { code?: string; description?: string; parentProjectCode?: string; className: string; icon: string; } = { className: className, icon: this.modules[index].addProjectEndpointicon, ...x };
                    return out;
                });
            })));
        });
        return forkJoin(observables).pipe(map(data => {
            const outData: { code?: string; description?: string; parentProjectCode?: string; className: string, icon: string }[] = [];
            if (data) {
                data.forEach(x => {
                    x.forEach(y => {
                        outData.push(y);
                    });
                })
            }

            return outData;
        }));

    }
    
    /***
     * exports the set of addable data sources in knowledge base editing UI 
     * 
     * @param project The project to get endpoint options for
     * @param onActionPerformed Callback for when an action is performed
     * @param onWizardActionPerformed Callback for when a wizard action is performed
     * @param wizardStepsConfigurations Optional wizard configurations
     * @param actualWizardStepConfigrationId Optional current wizard step ID
     * @returns Array of UI project endpoint options
     */
    public getUIProjectEndpointAddOptions(project: GProject, onActionPerformed?: GeboActionPerformedCallback, onWizardActionPerformed?: GeboWizardActionPerformedCallback, wizardStepsConfigurations?: GeboAIEntitiesSettingWizardConfiguration[], actualWizardStepConfigrationId?: string): GeboAIUIProjectEndpointOption[] {
        this.preloadStructures();
        const out: GeboAIUIProjectEndpointOption[] = [];
        if (this.modules && this.modules.length) {
            this.modules.forEach((module, index) => {
                out.push({
                    addProjectEndpointicon: module.addProjectEndpointicon,
                    addProjectEndpointLabel: module.addProjectEndpointLabel,
                    addProjectEndpointTitle: module.addProjectEndpointTitle,
                    projecteEndpointClassName: module.projecteEndpointClassName,
                    actionRequestByProject: (project: GProject, wizardStepsConfigurations, actualWizardStepConfigrationId) => {
                        //calls the specific module UI action request callback, overrides editing UI events callbacks 
                        //calling the module UI specific callback first and action the one eventually passed as hook here
                        const action: GeboUIActionRequest = this.pluggableUIProjectEndpointServices[index].byProjectCreateAction(project, wizardStepsConfigurations, actualWizardStepConfigrationId);
                        const oldCallBack = action.onActionPerformed;
                        const oldWizardCallBack = action.onWizardActionPerformed;
                        action.onActionPerformed = (event: GeboActionPerformedEvent) => {
                            if (oldCallBack) oldCallBack(event);
                            if (onActionPerformed) onActionPerformed(event);
                        };
                        action.onWizardActionPerformed = (event: GeboWizardActionPerformedEvent) => {
                            if (oldWizardCallBack) oldWizardCallBack(event);
                            if (onWizardActionPerformed) onWizardActionPerformed(event);
                        };
                        return action;
                    }
                })
            });
        }
        return out;
    }
}