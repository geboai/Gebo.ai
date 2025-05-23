/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { Injectable } from "@angular/core";
import { GeboModuleInfo, GeboModulesConfigControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { forkJoin, Observable } from "rxjs";

/**
 * AI generated comments
 * Constants representing the module IDs used throughout the system.
 * These constants are used to uniquely identify different modules when
 * checking if they are enabled in the configuration.
 */
export const SHARED_FILESYSTEM_MODULE = "shared-filesystem-module";
export const UPLOADS_MODULE = "uploads-module";
export const GIT_MODULE = "git-module";
export const GOOGLEDRIVE_MODULE = "google-drive-module";
export const CONFLUENCE_MODULE = "confluence-module";
export const JIRA_MODULE = "jira-module";
export const SHAREPOINT_MODULE = "sharepoint-module";

/**
 * Interface representing the enabled/disabled state of all Gebo.ai modules.
 * Each property corresponds to a module and indicates whether it is enabled (true) or disabled (false).
 */
export interface GeboAIModules {
    filesystemModuleEnabled?: boolean;
    uploadsModuleEnabled?: boolean;
    gitModuleEnabled?: boolean;
    googleDriveModuleEnabled?: boolean;
    confluenceModuleEnabled?: boolean;
    jiraModuleEnabled?: boolean;
    sharepointModuleEnabled?: boolean;    
};

/**
 * Service responsible for managing the enabled/disabled state of all Gebo.ai modules.
 * It initializes the module configuration on service instantiation and provides methods
 * to access the configuration.
 */
@Injectable({
    providedIn: "root"
})
export class GeboAIModulesService {
    /**
     * Static configuration object that stores the enabled state of all modules.
     * Using static ensures the configuration is shared across all instances of the service.
     */
    private static config?: GeboAIModules;

    /**
     * Creates an instance of GeboAIModulesService.
     * @param geboModulesConfigService Service to fetch module configuration from the backend
     */
    constructor(private geboModulesConfigService: GeboModulesConfigControllerService) {
        this.initialize();
    }

    /**
     * Returns the current configuration of all modules.
     * @returns The current GeboAIModules configuration or undefined if not yet initialized
     */
    public getConfig(): GeboAIModules | undefined {
        return GeboAIModulesService.config;
    }

    /**
     * Checks if a specific module is enabled based on its ID.
     * @param moduleId The ID of the module to check
     * @param mods Array of module information objects retrieved from the backend
     * @returns true if the module is enabled, false otherwise
     */
    private isEnabled(moduleId: string, mods: GeboModuleInfo[]): boolean {
        return mods?.find(x => x?.messagingModuleId === moduleId)?.enabled === true;
    }

    /**
     * Initializes the module configuration by fetching data from the backend.
     * This method is called during service instantiation and populates the static config.
     * It only runs if the config hasn't been initialized yet.
     */
    private initialize(): void {
        if (!GeboAIModulesService.config) {
            this.geboModulesConfigService.getAllModules().subscribe({
                next: (modulesInfos) => {
                    const config: GeboAIModules = {};

                    config.filesystemModuleEnabled = this.isEnabled(SHARED_FILESYSTEM_MODULE, modulesInfos);
                    config.jiraModuleEnabled = this.isEnabled(JIRA_MODULE, modulesInfos);
                    config.uploadsModuleEnabled = this.isEnabled(UPLOADS_MODULE, modulesInfos);
                    config.gitModuleEnabled = this.isEnabled(GIT_MODULE, modulesInfos);
                    config.googleDriveModuleEnabled = this.isEnabled(GOOGLEDRIVE_MODULE, modulesInfos);
                    config.confluenceModuleEnabled = this.isEnabled(CONFLUENCE_MODULE, modulesInfos);
                    config.sharepointModuleEnabled = this.isEnabled(SHARED_FILESYSTEM_MODULE, modulesInfos);
                    GeboAIModulesService.config = config;
                }
            });
        }
    }
}