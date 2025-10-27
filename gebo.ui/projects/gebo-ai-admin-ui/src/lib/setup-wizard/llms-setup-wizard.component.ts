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
import { ChatModelsControllerService, ConfigurationEntry, EmbeddingModelsControllersService, FastLLMSSetupData, OperationStatusBoolean, UserControllerService, UserInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { LLMSetupWizardService } from "./llms-setup-wizard.service";
import { forkJoin, Observable } from "rxjs";
import { FormControl, FormGroup } from "@angular/forms";
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
     * List of available LLM providers that can be configured in the system.
     * Currently supports OpenAI and Nvidia, with Xai (Grok) commented out.
     */
    public providersList: any[] = [
        { code: "openai", description: "openai (ChatGpt) provider" },
        { code: "nvidia", description: "Nvidia nim provider" } /*,
        {code:"xai",description:"Xai (Grok) provider"}*/];

    /**
     * Form group for capturing user input during LLM setup.
     * Contains fields for user, provider, and API key.
     */
    formGroup: FormGroup = new FormGroup({
        user: new FormControl(),
        provider: new FormControl(),
        apiKey: new FormControl()
    });

    /**
     * Array to store configured LLM models fetched from the backend.
     */
    public modelsList: LLMSEntry[] = [];

    /**
     * Constructor initializes services required for LLM setup functionality.
     */
    constructor(setupWizardComunicationService: SetupWizardComunicationService,
        private userService: UserControllerService,
        private llmsSetupWizardService: LLMSetupWizardService,
        private chatModelsService: ChatModelsControllerService,
        private embeddingModelsService: EmbeddingModelsControllersService) {
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

        const observables: [Observable<boolean>, Observable<Array<ConfigurationEntry>>, Observable<Array<ConfigurationEntry>>, Observable<UserInfo>] = [this.llmsSetupWizardService.getBooleanStatus(), this.chatModelsService.getRuntimeConfiguredChatModels(), this.embeddingModelsService.getRuntimeConfiguredEmbeddingModels(), this.userService.getCurrentUser()];
        forkJoin(observables).subscribe({
            next: (value) => {
                this.isSetupCompleted = value[0] === true;
                if (this.isSetupCompleted === true) {
                    this.userMessages = [{ severity: "success", summary: "Large language models setup OK!", detail: "There are at least a default chat bot model and a default embedding model both configured" }];
                } else {
                    this.userMessages = [{ severity: "error", summary: "Large language models setup not yet done", detail: "At least a default chat bot model and a default embedding model both correctly configured are required" }];
                }
                const modelsList: LLMSEntry[] = [];
                if (value[1]) {
                    const chatModels = value[1];
                    if (chatModels && chatModels.length) {
                        chatModels.forEach(x => {
                            const newEntry: LLMSEntry = {
                                configurationCode: x.configuration?.code,
                                modelCode: x.configuration?.choosedModel?.code,
                                modelType: "chat model",
                                provider: x.configuration?.modelTypeCode,
                                defaultModel: x.configuration?.defaultModel,
                                description: x.configuration?.description
                            };
                            modelsList.push(newEntry);
                        });
                    }
                }
                if (value[2]) {
                    const embeddingModels = value[2];
                    if (embeddingModels && embeddingModels.length) {
                        embeddingModels.forEach(x => {
                            const newEntry: LLMSEntry = {
                                configurationCode: x.configuration?.code,
                                modelCode: x.configuration?.choosedModel?.code,
                                modelType: "embedding model",
                                provider: x.configuration?.modelTypeCode,
                                defaultModel: x.configuration?.defaultModel,
                                description: x.configuration?.description
                            };
                            modelsList.push(newEntry);
                        });
                    }
                }
                this.modelsList = modelsList;
                if (value[3]) {
                    if (!this.isSetupCompleted && !this.formGroup.controls["user"].value) {
                        this.formGroup.controls["user"].setValue(value[3].username);
                    }
                }
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

    /**
     * Submits the LLM setup form data to the backend service.
     * Creates new LLM configurations based on user input.
     * On successful setup, closes the wizard.
     */
    setupLLMS() {
        const data: FastLLMSSetupData = this.formGroup.value;
        this.loading = true;
        this.llmsSetupWizardService.createLLMSSetup(data).subscribe({
            next: (opstatus: OperationStatusBoolean) => {
                this.isSetupCompleted = opstatus.result === true;
                this.userMessages = opstatus.messages as ToastMessageOptions[];
                if (this.isSetupCompleted === true) {
                    this.closeWizard();
                }
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

}