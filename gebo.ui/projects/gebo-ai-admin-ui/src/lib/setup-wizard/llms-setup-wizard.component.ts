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
 * This file implements a wizard component for setting up Large Language Models (LLMs) in the Gebo.ai application.
 * It facilitates configuration of chat and embedding models from different providers like OpenAI and Nvidia.
 */

import { Component } from "@angular/core";
import { UserControllerService, UserInfo } from '@Gebo.ai/heimdall';
import { LLMSSetupConfiguration, LLMSSetupConfigurationData } from '@Gebo.ai/brain';
import { BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { LLMSetupWizardService } from "./llms-setup-wizard.service";
import { forkJoin, Observable } from "rxjs";
import { ToastMessageOptions } from "primeng/api";

/**
 * Interface defining the structure of LLM entries displayed in the component.
 * Contains configuration details for both chat and embedding models.
 */
interface LLMSEntry {
    configurationCode?: string; provider?: string; modelType?: string; modelCode?: string; description?: string, defaultModel?: boolean;
}

/**
 * Component responsible for the LLM setup wizard.
 * Extends BaseWizardSectionComponent from the reusable UI library.
 * Provides UI and functionality for configuring LLMs in the system.
 */
@Component({
    selector: "gebo-llms-wizard-component",
    templateUrl: "llms-setup-wizard.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("LLMSetupWizardComponent") }]
})
export class LLMSetupWizardComponent extends BaseWizardSectionComponent {



    /**
     * Array to store configured LLM models fetched from the backend.
     */

    protected actualProvidersConfiguration?: LLMSSetupConfigurationData;
    protected currentIndex:number=0;
    protected autoSettingsConfigurations?:LLMSSetupConfiguration[]=[];
    // Messages produced by the easy setup, carried across the reload it triggers.
    private pendingUserMessages: ToastMessageOptions[] = [];
    /**
     * Constructor initializes services required for LLM setup functionality.
     */
    constructor(setupWizardComunicationService: SetupWizardComunicationService,
        private userService: UserControllerService,
        private llmsSetupWizardService: LLMSetupWizardService) {
        super(setupWizardComunicationService);
    }

    /**
     * Reloads data for the wizard component by fetching current LLM configurations.
     * Retrieves:
     * - Setup completion status
     * - Configured chat models
     * - Configured embedding models
     * - Current user info
     * Updates the component state based on the retrieved data.
     */
    public override reloadData(): void {
        this.loading = true;
        const observables: [Observable<boolean>, Observable<UserInfo>, Observable<LLMSSetupConfigurationData>] = [this.llmsSetupWizardService.getBooleanStatus(), this.userService.getCurrentUser(), this.llmsSetupWizardService.getActualLLMSConfiguration()];
        forkJoin(observables).subscribe({
            next: (value) => {
                this.isSetupCompleted = value[0] === true;
                if (this.isSetupCompleted === true) {
                    this.userMessages = [{ severity: "success", summary: "Large language models setup OK!", detail: "There are at least a default chat bot model and a default embedding model both configured" }];
                } else {
                    this.userMessages = [{ severity: "error", summary: "Large language models setup not yet done", detail: "At least a default chat bot model and a default embedding model both correctly configured are required" }];
                }
                // What the easy setup reported about its own run (a model the provider refused,
                // a kind that could not be created) is kept next to the status banner instead of
                // being overwritten by the reload that follows it.
                if (this.pendingUserMessages.length) {
                    this.userMessages = [...this.userMessages, ...this.pendingUserMessages];
                    this.pendingUserMessages = [];
                }
                this.actualProvidersConfiguration = value[2];
                this.autoSettingsConfigurations=this.actualProvidersConfiguration?.canRunAutoconfigure===true?this.actualProvidersConfiguration.configurations?.filter(x=>x.parentModel.supportsAutoconfig===true):[];
            },
            complete: () => {
                this.loading = false;
            }
        });
    }
    onVendorConfigurationChanged(changedFlag:boolean, index: number) {
       this.reloadData();
    }
    onAutomaticSetupDone():void {
        this.closeWizard();
    }
    /**
     * The easy setup ran but did not fully succeed: keep the wizard open, show what the
     * backend reported and reload, so the kinds that were created disappear from the form
     * and only the ones still missing are offered for a retry.
     */
    onAutomaticSetupMessages(messages: ToastMessageOptions[]): void {
        this.pendingUserMessages = messages ?? [];
        this.reloadData();
    }
    /**
     * Submits the LLM setup form data to the backend service.
     * Creates new LLM configurations based on user input.
     * On successful setup, closes the wizard.
     */
    setupLLMS() {

        this.loading = true;

    }

}