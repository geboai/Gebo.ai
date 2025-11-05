/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { Component, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { ChatModelsLookupControllerService, GLookupEntry, OperationStatusPromptTemplateResponse, PromptTemplateWizardConfigs, PromptTemplateWizardControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { ToastMessageOptions } from "primeng/api";
import { forkJoin, Observable } from "rxjs";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
/**
 * AI generated comments
 * 
 * Component that provides a wizard interface for generating AI prompt templates.
 * It allows users to select a model and enter a query to get AI-assisted prompt generation.
 * The component can be integrated with parent forms to deliver the generated prompt
 * back to a specified form control.
 */
@Component({
    selector: "gebo-ai-prompt-wizard-control",
    templateUrl: "prompt-wizard.component.html",
    standalone: false,
    providers: [
        {
            provide: GEBO_AI_MODULE, useValue: "PromptEditingModule", multi: false
        },
        {
            provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIPromptWizardComponent")
        }]
})
export class GeboAIPromptWizardComponent implements OnInit, OnChanges {
    /** Name of the form control in the parent form group where the prompt template will be delivered */
    @Input() deliveryPromptControlName?: string;
    /** Parent form group that will receive the generated prompt template */
    @Input() deliveryFormGroup?: FormGroup;
    /** Initial placeholder text for the prompt template input */
    @Input() promptTemplatePlaceholderText?: string;
    /** Flag to determine if the wizard is operating in RAG mode */
    @Input() ragWizardMode: boolean = true;
    /** Default placeholder text for the prompt template */
    public defaultTemplatePlaceHolderText?: string;
    /** Controls visibility of the prompt wizard dialog */
    public dialogVisible: boolean = false;
    /** Messages to display to the user in the UI */
    public userMessages: ToastMessageOptions[] = [{ severity: "info", summary: "Ask AI to write a prompt template", detail: "With this screen AI will assist you on writing the correct template for your objectives" }];
    /** Indicates if any operation is in progress */
    public loading: boolean = false;
    /** Form group for user input containing model selection and query text */
    formGroup: FormGroup = new FormGroup({
        modelCode: new FormControl(),
        query: new FormControl()

    });
    /** Form group for the response from the AI prompt generation */
    responseFormGroup: FormGroup = new FormGroup({
        response: new FormControl()
    });
    /** The last response received from the prompt template generation service */
    lastResponse?: OperationStatusPromptTemplateResponse;
    /** Available chat models for selection */
    chatModels?: GLookupEntry[];
    /** Configuration settings for the prompt template wizard */
    wizardConfigs?: PromptTemplateWizardConfigs;

    /**
     * Constructor for the GeboAIPromptWizardComponent
     * @param promptWizardControllerService Service for prompt template generation
     * @param modelsLookupService Service for retrieving available chat models
     */
    constructor(
        private promptWizardControllerService: PromptTemplateWizardControllerService,
        private modelsLookupService: ChatModelsLookupControllerService) {

    }

    /**
     * Method to close the modal dialog
     * @param a Event object from the modal close action
     */
    closeModal(a: any) { }

    /**
     * Initializes the component by loading chat models and wizard configurations.
     * Sets the default prompt template if a placeholder text isn't provided.
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
     * Responds to changes in component inputs.
     * Updates the query form control when promptTemplatePlaceholderText changes.
     * @param changes SimpleChanges object containing changed properties
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["promptTemplatePlaceholderText"] && this.promptTemplatePlaceholderText) {
            this.formGroup.controls["query"].setValue(this.promptTemplatePlaceholderText);
        }
    }

    /**
     * Triggers the prompt template generation using the selected model and query.
     * Updates the response form group and user messages when the operation completes.
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
     * Cancels the prompt generation operation and closes the dialog
     */
    doCancelAction(): void {
        this.dialogVisible = false;
    }

    /**
     * Accepts the generated prompt template and sends it to the parent form control.
     * Closes the dialog after delivering the prompt.
     */
    doAcceptPrompt(): void {
        if (this.deliveryFormGroup && this.deliveryPromptControlName && this.responseFormGroup.valid) {
            const response: string = this.responseFormGroup.controls["response"].value;
            this.deliveryFormGroup.controls[this.deliveryPromptControlName].setValue(response);
            this.dialogVisible = false;
        }
    }

}