/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { Injectable } from "@angular/core";
import { ComponentLLMSStatus, FastLLMSSetupData, GeboAdvancedSetupStatusControllerService, GeboFastLlmsSetupControllerService, OperationStatusBoolean } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService } from "@Gebo.ai/reusable-ui";
import { map, Observable } from "rxjs";

/**
 * AI generated comments
 * 
 * Service that manages the setup and status of Large Language Models (LLMs) in the application.
 * This service extends AbstractStatusService to provide status information about LLM setup
 * and handles communication with the backend API to configure LLMs.
 */
@Injectable({ providedIn: "root" })
export class LLMSetupWizardService extends AbstractStatusService {
    /**
     * Initializes a new instance of the LLMSetupWizardService.
     * @param statusService The service used to interact with the LLMS setup API endpoints
     */
    constructor(private statusService: GeboFastLlmsSetupControllerService) {
        super();
    }

    /**
     * Overrides the getBooleanStatus method from AbstractStatusService.
     * Gets a boolean observable indicating whether LLMS is set up successfully.
     * @returns An Observable<boolean> that resolves to true if LLMS is set up
     */
    public override getBooleanStatus(): Observable<boolean> {
        return this.statusService.getLLMSSetupStatus().pipe(map(x => x.isSetup === true));
    }

    /**
     * Gets the detailed status of the LLMS setup.
     * @returns An Observable of ComponentLLMSStatus containing detailed information about the LLMS setup state
     */
    public getLLMSSetupStatus(): Observable<ComponentLLMSStatus> {
        return this.statusService.getLLMSSetupStatus();
    }
    
    /**
     * Creates a new LLMS setup using the provided configuration data.
     * @param data The configuration data needed to set up LLMS
     * @returns An Observable of OperationStatusBoolean that indicates whether the operation was successful
     */
    public createLLMSSetup(data:FastLLMSSetupData):Observable<OperationStatusBoolean>{
        return this.statusService.createLLMSSetup(data);
    }
}