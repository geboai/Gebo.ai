/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { GeboModulesConfigControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractInstalledModuleService } from "@Gebo.ai/reusable-ui";
import { map, Observable } from "rxjs";
// AI generated comments
/**
 * Abstract class extending AbstractInstalledModuleService to provide functionality
 * for checking whether a Gebo module is installed and enabled.
 * This service provides a common implementation for all root-level Gebo modules
 * to determine their installation status through the Gebo API.
 */
export abstract class GeboRootInstalledModuleService extends AbstractInstalledModuleService {
    /**
     * The unique code/identifier for the module that this service represents.
     * Must be implemented by child classes.
     */
    protected abstract moduleCode: string;

    /**
     * Creates an instance of GeboRootInstalledModuleService.
     * @param geboModulesConfigService - Service for accessing module configuration information
     */
    public constructor(private geboModulesConfigService: GeboModulesConfigControllerService) {
        super();
    }

    /**
     * Checks if the module is installed and enabled.
     * Overrides the parent class method to provide specific implementation.
     * @returns An Observable that emits a boolean indicating whether the module is installed and enabled
     */
    public override getInstalledModule(): Observable<boolean> {
        return this.geboModulesConfigService.getModuleInfo(this.moduleCode).pipe(map(modulteInfo => modulteInfo && modulteInfo.enabled === true));
    }
}