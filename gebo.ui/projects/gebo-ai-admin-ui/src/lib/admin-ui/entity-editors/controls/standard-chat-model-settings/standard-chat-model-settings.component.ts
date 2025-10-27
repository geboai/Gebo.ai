/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { Component, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { ModelMetaInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";
/**
 * AI generated comments
 * This file defines a component for configuring standard chat model settings
 * in a Gebo.ai application. It handles the UI for adjusting temperature, 
 * top-p sampling, and context length parameters for language models.
 */

// Standard default context length for language models when not otherwise specified
const standardDefaultContextLength: number = 4096;

/**
 * @Component GeboAIStandardChatModelSettings
 * 
 * A component that provides UI controls for configuring standard chat model settings.
 * It handles temperature, top-p, and context length parameters that are common across
 * many language models. The component responds to model changes and updates settings
 * based on the selected model's capabilities and constraints.
 */
@Component({
    selector: "gebo-ai-standard-chat-model-settings-component",
    templateUrl: "standard-chat-model-settings.component.html",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIStandardChatModelModule", multi: false }, 
        { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIStandardChatModelSettings") }
    ]
})
export class GeboAIStandardChatModelSettings implements OnInit, OnChanges {
    // Form group to bind settings controls to
    @Input() formGroup?: FormGroup;

    // Input bounds for temperature parameter
    @Input() minTemperature: number = 0;
    @Input() maxTemperature: number = 1.0;
    @Input() maxTemperatureDigits: number = 2;

    // Input bounds for top-p sampling parameter
    @Input() minTopP: number = 0;
    @Input() maxTopP: number = 1.0;
    @Input() maxTopPDigits: number = 2;

    // Input bounds for context length parameter
    @Input() minContextLength: number = 1024;
    @Input() maxContextLength: number = 2000000;

    // The default context length for the selected model
    public defaultContextLength?: number;

    // Flag indicating if the model has its own context window settings
    public modelHaveNativeContextWindowSettings: boolean = false;

    /**
     * Lifecycle hook that is called after data-bound properties are initialized
     */
    ngOnInit(): void {

    }

    /**
     * Updates the component state based on the selected model's capabilities
     * Sets appropriate default context length and determines if the model
     * has native context window settings
     * 
     * @param choosed - The selected model object containing context length information
     */
    private notifyChoosedModelChange(choosed?: { contextLength?: number, metaInfos?: ModelMetaInfo }) {
        this.defaultContextLength = standardDefaultContextLength;
        this.modelHaveNativeContextWindowSettings = false;
        if (choosed) {
            if (choosed.contextLength) {
                this.modelHaveNativeContextWindowSettings = true;
                this.defaultContextLength = choosed.contextLength;
            } else if (choosed?.metaInfos?.contextLength) {
                this.defaultContextLength = choosed.metaInfos.contextLength;
                this.modelHaveNativeContextWindowSettings = true;
            }
        }
    }

    /**
     * Lifecycle hook that is called when any data-bound property of the directive changes
     * Handles changes to the form group and subscribes to model selection changes
     * 
     * @param changes - Object containing all changed properties with current and previous values
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.formGroup && changes["formGroup"]) {
            if (this.formGroup.controls["choosedModel"]) {
                const currentChoosedModel = this.formGroup.controls["choosedModel"].value;
                this.notifyChoosedModelChange(currentChoosedModel);
                this.formGroup.controls["choosedModel"].valueChanges.subscribe((currentChoosedModel) => {
                    this.notifyChoosedModelChange(currentChoosedModel);
                });
            }
        }
    }


}