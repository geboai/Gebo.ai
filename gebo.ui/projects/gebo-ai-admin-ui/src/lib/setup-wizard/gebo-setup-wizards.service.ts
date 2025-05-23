/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * @fileoverview Service for managing setup wizard status in a Gebo.ai application
 * AI generated comments
 */

import { Injectable } from "@angular/core";
import { SetupStatus, SetupWizardService } from "@Gebo.ai/reusable-ui";
import { Observable } from "rxjs";

/**
 * Injectable service that wraps the SetupWizardService to provide
 * application-specific setup wizard functionality. This service acts as
 * a facade for the underlying SetupWizardService, providing a more
 * specific interface for Gebo.ai applications.
 */
@Injectable({providedIn:"root"})
export class GeboSetupWizardService {
    /**
     * Creates an instance of GeboSetupWizardService.
     * @param service - The underlying SetupWizardService to be wrapped
     */
    constructor(private service:SetupWizardService) {

    }
    
    /**
     * Retrieves the global setup status of the application.
     * This method delegates to the underlying SetupWizardService.
     * @returns An Observable that emits the current SetupStatus
     */
    public getGlobalSetupStatus():Observable<SetupStatus> {
        return this.service.getSetupStatus();
    }
}