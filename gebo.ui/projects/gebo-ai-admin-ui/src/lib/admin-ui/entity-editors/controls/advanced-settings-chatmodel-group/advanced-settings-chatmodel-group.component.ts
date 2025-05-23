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
 * This component manages advanced settings for chat models in the Gebo.ai application.
 * It provides functionality for displaying and configuring advanced options for chat models,
 * such as functions lookup and default prompt templates.
 */
import { Component, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { FunctionsLookupControllerService, GLookupEntry, GPromptConfig, PromptTemplatesControllerService } from "@Gebo.ai/gebo-ai-rest-api";

@Component({
    selector: "gebo-advanced-chatmodel-group-component",
    templateUrl: "advanced-settings-chatmodel-group.component.html",
    standalone: false
})
export class GeboAIAdvancedChatModelGroupComponent implements OnInit, OnChanges {
    /** Form group for managing the advanced chat model settings */
    @Input() formGroup?: FormGroup;
    
    /** 
     * Determines the mode of operation for the component
     * - NEW: Creating a new chat model
     * - EDIT: Editing an existing chat model
     * - EDIT_OR_NEW: Flexible mode supporting both operations
     */
    @Input() mode?: "NEW" | "EDIT" | "EDIT_OR_NEW";
    
    /** Flag indicating whether the entity data has been loaded */
    @Input() entityDataLoaded: boolean = false;
    
    /** List of available functions that can be used in the chat model */
    public functionsList: GLookupEntry[] = [];
    
    /** Loading state indicator for asynchronous operations */
    public loading: boolean = false;
    
    /**
     * Constructor initializes the component with required services
     * @param functionsLookupControllerService Service for retrieving available functions
     * @param promptTemplatesControllerService Service for managing prompt templates
     */
    constructor(private functionsLookupControllerService: FunctionsLookupControllerService,
        private promptTemplatesControllerService: PromptTemplatesControllerService) {

    }
    
    /**
     * Lifecycle hook that initializes the component
     * Fetches the list of available functions when the component is initialized
     */
    ngOnInit(): void {
        this.loading = true;
        this.functionsLookupControllerService.getAllFunctions().subscribe({
            next: (value) => {
                this.functionsList = value;
            },
            complete: () => {
                this.loading = false;
            }
        });
    }
    
    /**
     * Lifecycle hook that responds to changes in component inputs
     * Fetches and sets the default prompt when entity data is loaded and form is available
     * @param changes SimpleChanges object containing current and previous values of inputs
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.entityDataLoaded === true && this.formGroup && (changes["entityDataLoaded"] || changes["formGroup"]) && (!this.formGroup?.controls["defaultModelPrompt"].value)) {
            this.loading = true;
            this.promptTemplatesControllerService.getDefaultPrompt(false).subscribe({
                next: (promptValue: GPromptConfig) => {
                    if (promptValue && promptValue.prompt) {
                        this.formGroup?.controls["defaultModelPrompt"].setValue(promptValue.prompt);
                    }
                },
                complete: () => {
                    this.loading = false;
                }
            });
        }

    }
}