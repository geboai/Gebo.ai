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
 * 
 * This component implements a wizard for generating AI-assisted prompt templates.
 * It allows users to select a model and provide a query to generate an appropriate
 * prompt template that can then be applied to a parent form.
 * 
 * The component handles loading available chat models, configuring the wizard,
 * and managing the dialog for prompt generation and selection.
 */
import { Component, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { ChatModelsLookupControllerService, GLookupEntry, OperationStatusPromptTemplateResponse, PromptTemplateWizardConfigs, PromptTemplateWizardControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST } from "@Gebo.ai/reusable-ui";
import { ToastMessageOptions } from "primeng/api";
import { forkJoin, Observable } from "rxjs";

@Component({
    selector: "gebo-ai-prompt-wizard-component",
    templateUrl: "prompt-wizard.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIPromptWizardComponent") }]
})
export class GeboAIPromptWizardComponent implements OnInit, OnChanges {
    /**
     * Name of the control in the parent form group where the generated prompt will be delivered
     */
    @Input() deliveryPromptControlName?: string;
    
    /**
     * Parent form group that will receive the generated prompt
     */
    @Input() deliveryFormGroup?: FormGroup;
    
    /**
     * Optional placeholder text for the prompt template input field
     */
    @Input() promptTemplatePlaceholderText?: string;
    
    /**
     * Flag to determine if the wizard operates in RAG (Retrieval Augmented Generation) mode
     */
    @Input() ragWizardMode:boolean=true;
    
    /**
     * Default placeholder text for prompt template when none is provided
     */
    public defaultTemplatePlaceHolderText?: string;
    
    /**
     * Controls the visibility of the prompt wizard dialog
     */
    public dialogVisible: boolean = false;
    
    /**
     * Messages to display to the user in the UI
     */
    public userMessages: ToastMessageOptions[] = [{ severity: "info", summary: "Ask AI to write a prompt template", detail: "With this screen AI will assist you on writing the correct template for your objectives" }];
    
    /**
     * Flag to indicate when async operations are in progress
     */
    public loading: boolean = false;
    
    /**
     * Form group for collecting user input including model selection and query
     */
    formGroup: FormGroup = new FormGroup({
        modelCode: new FormControl(),
        query: new FormControl()
    });
    
    /**
     * Form group for the generated prompt response
     */
    responseFormGroup: FormGroup = new FormGroup({
        response: new FormControl()
    });
    
    /**
     * Stores the most recent response from the prompt generation service
     */
    lastResponse?: OperationStatusPromptTemplateResponse;
    
    /**
     * Available chat models for prompt generation
     */
    chatModels?: GLookupEntry[];
    
    /**
     * Configuration options for the prompt template wizard
     */
    wizardConfigs?: PromptTemplateWizardConfigs;

    /**
     * Initializes the component with required services for prompt generation and model lookup
     * 
     * @param promptWizardControllerService Service for generating prompt templates
     * @param modelsLookupService Service for retrieving available chat models
     */
    constructor(
        private promptWizardControllerService: PromptTemplateWizardControllerService,
        private modelsLookupService: ChatModelsLookupControllerService) {
    }

    /**
     * Handler for closing the modal dialog
     * 
     * @param a Event parameter (unused)
     */
    closeModal(a: any) { }

    /**
     * Initializes the component by loading available chat models and wizard configurations
     * Sets default prompt if available from configuration
     */
    ngOnInit(): void {
        this.loading = true;
        const observables: [Observable<GLookupEntry[]>, Observable<PromptTemplateWizardConfigs>] = [this.modelsLookupService.getRuntimeConfiguredChatModelsLookup(), this.promptWizardControllerService.getTemplateWizardConfigs()];
        forkJoin(observables).subscribe({
            next: (value) => {
                this.chatModels = value[0];
                this.wizardConfigs = value[1];
                if (!this.promptTemplatePlaceholderText) {
                    const prompt = this.wizardConfigs?.defaultPromptTemplateWizardConfig?.prompt;
                    if (prompt) {
                        this.formGroup.controls["query"].setValue(prompt);
                    }
                }
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

    /**
     * Responds to changes in input properties, particularly updating the query field
     * when the promptTemplatePlaceholderText input changes
     * 
     * @param changes Simple changes object containing information about changed properties
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["promptTemplatePlaceholderText"] && this.promptTemplatePlaceholderText) {
            this.formGroup.controls["query"].setValue(this.promptTemplatePlaceholderText);
        }
    }

    /**
     * Calls the prompt template generation service with the current form values
     * Updates the response form group with the generated template
     */
    doGeneratePrompt(): void {
        this.loading = true;
        this.promptWizardControllerService.generatePromptTemplate(this.formGroup.value).subscribe(
            {
                next: (response) => {
                    this.lastResponse = response;
                    if (this.lastResponse.result) {
                        this.responseFormGroup.patchValue(this.lastResponse.result);
                    }
                    this.userMessages = this.lastResponse.messages as ToastMessageOptions[];
                },
                complete: () => {
                    this.loading = false;
                }
            }
        );
    }

    /**
     * Handles cancellation of the prompt generation dialog
     * Closes the dialog without applying the generated prompt
     */
    doCancelAction(): void {
        this.dialogVisible = false;
    }

    /**
     * Applies the generated prompt to the parent form and closes the dialog
     * Only functions if all required inputs (deliveryFormGroup, deliveryPromptControlName)
     * are provided and the response form is valid
     */
    doAcceptPrompt(): void {
        if (this.deliveryFormGroup && this.deliveryPromptControlName && this.responseFormGroup.valid) {
            const response: string = this.responseFormGroup.controls["response"].value;
            this.deliveryFormGroup.controls[this.deliveryPromptControlName].setValue(response);
            this.dialogVisible = false;
        }
    }
}