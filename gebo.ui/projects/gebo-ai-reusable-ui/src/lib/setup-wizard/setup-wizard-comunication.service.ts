/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { Injectable } from "@angular/core";
import { SetupWizardPanelComponent } from "./setup-wizard-panel.component";

/**
 * AI generated comments
 * 
 * Service responsible for facilitating communication with the SetupWizardPanel component.
 * This injectable service acts as a bridge between different components that need to 
 * interact with the setup wizard panel, allowing them to control the wizard without
 * direct reference to the panel component.
 */
@Injectable()
export class SetupWizardComunicationService {
    /**
     * Reference to the SetupWizardPanelComponent instance.
     * This is set via the setWizardPanel method and used to control the wizard.
     */
    private setupWizardPanelComponent?:SetupWizardPanelComponent;
    
    /**
     * Initializes a new instance of the SetupWizardComunicationService.
     */
    constructor() {

    }
    
    /**
     * Sets the reference to the SetupWizardPanelComponent.
     * This method should be called by the panel component itself during initialization
     * to register with the communication service.
     * 
     * @param setupWizardPanelComponent The panel component instance to be controlled
     */
    public setWizardPanel(setupWizardPanelComponent:SetupWizardPanelComponent):void{
        this.setupWizardPanelComponent=setupWizardPanelComponent;
    }
    
    /**
     * Closes the wizard panel if a reference to it exists.
     * This method can be called by any component that has access to this service
     * without needing direct access to the panel component.
     */
    public closeWizard():void {
        if (this.setupWizardPanelComponent) {
            this.setupWizardPanelComponent.closeWizard();
        }
    }

}