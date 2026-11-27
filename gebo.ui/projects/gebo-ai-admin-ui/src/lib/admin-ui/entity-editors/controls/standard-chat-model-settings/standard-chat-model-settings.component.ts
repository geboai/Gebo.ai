/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { ChangeDetectorRef, Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges, ChangeDetectionStrategy } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { ChatModelsControllerService, GBaseChatModelConfig, GModelCarriedMediaTypes, ModelMetaInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAITranslationService } from "@Gebo.ai/reusable-ui";
import { ChatModelParametersControls, ThinkingOptionItem } from "./chat-model-parameters-controls";
/**
 * AI generated comments
 * This file defines a component for configuring standard chat model settings
 * in a Gebo.ai application. It handles the UI for adjusting temperature, 
 * top-p sampling, and context length parameters for language models.
 */

// Standard default context length for language models when not otherwise specified
const standardDefaultContextLength: number = 4096;

/** Whether two lists of modalities hold the same ones, whatever their order. */
function sameModalities(a?: string[] | null, b?: string[] | null): boolean {
    const x = [...(a ?? [])].sort();
    const y = [...(b ?? [])].sort();
    return x.length === y.length && x.every((v, i) => v === y[i]);
}

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
    changeDetection: ChangeDetectionStrategy.Eager,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIStandardChatModelModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIStandardChatModelSettings") }
    ]
})
export class GeboAIStandardChatModelSettings implements OnInit, OnChanges, OnDestroy {
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
    // Whether the provider takes the sampling parameters (temperature, top-p)
    @Input() samplingSettings: boolean = true;
    @Input() thinking: boolean = true;
    @Input() maxGeneratedTokens: boolean = true;
    // Shows what the model accepts as input
    @Input() inputModalities: boolean = true;

    /** What a model may accept as input, besides text. */
    protected inputModalityOptions: { id: string, label: string, value: string }[] = [
        { id: "TextInputOption", label: "Text", value: "TEXT" },
        { id: "ImageInputOption", label: "Images", value: "IMAGE" },
        { id: "AudioInputOption", label: "Audio", value: "AUDIO" },
        { id: "VideoInputOption", label: "Video", value: "VIDEO" },
        { id: "DocumentInputOption", label: "Documents (PDF)", value: "DOCUMENT" }];

    /** What the chosen model's metadata says it accepts as input, when it says it. */
    protected modelInputModalities?: string[];

    /**
     * The metadata's modalities last filled into the form as its default, while nobody
     * changed them: another chosen model replaces them with its own.
     */
    private filledInputModalities?: string[];

    /** Whether the form holds the modalities the chosen model's metadata filled in. */
    protected get inputModalitiesFromMetadata(): boolean {
        const control = this.formGroup?.controls["inputModalities"];
        return !!this.filledInputModalities && !!control && sameModalities(control.value, this.filledInputModalities);
    }

    /** Whether the form holds other modalities than the chosen model's metadata tells. */
    protected get inputModalitiesDifferFromMetadata(): boolean {
        const control = this.formGroup?.controls["inputModalities"];
        return !!this.modelInputModalities?.length && !!control
            && !sameModalities(control.value, this.modelInputModalities);
    }

    /**
     * Fills what the chosen model accepts as input into the form, as its default: when the
     * form holds none yet, or still holds what a previous model's metadata filled in and
     * nobody changed. A value saved or changed by hand is left as it is ("use these"
     * applies the metadata then).
     */
    private fillInputModalitiesFromMetadata(): void {
        const control = this.formGroup?.controls["inputModalities"];
        if (!control || control.disabled || !this.modelInputModalities?.length) return;
        const current: string[] = control.value ?? [];
        const untouched = control.pristine
            && (current.length === 0 || sameModalities(current, this.filledInputModalities));
        if (untouched && !sameModalities(current, this.modelInputModalities)) {
            control.setValue([...this.modelInputModalities]);
            this.filledInputModalities = [...this.modelInputModalities];
        } else if (untouched && current.length > 0) {
            this.filledInputModalities = [...current];
        }
    }

    // The default context length for the selected model
    protected defaultContextLength?: number;

    // Flag indicating if the model has its own context window settings
    protected modelHaveNativeContextWindowSettings: boolean = false;

    protected thinkingOptions: { code: GBaseChatModelConfig.ThinkingEnum, description: string }[] = [{ code: "NO_THINKING", description: "Disabled" }, { code: "AUTO", description: "Automatic" }, { code: "LOW_THINKING", description: "Low" }, { code: "MEDIUM_THINKING", description: "Medium" }, { code: "HIGH_THINKING", description: "High" }];

    /**
     * The temperature, top_p and thinking controls as the chosen model's integration
     * tells (enabled, ranges, the thinking choices reaching the model).
     */
    protected parameters?: ChatModelParametersControls;

    /** The thinking choices offered for the chosen model. */
    protected offeredThinkingOptions: ThinkingOptionItem[] = this.thinkingOptions;

    /** The least temperature the chosen model takes. */
    protected get temperatureMin(): number { return this.parameters ? this.parameters.min("temperature", this.minTemperature) : this.minTemperature; }
    /** The greatest temperature the chosen model takes. */
    protected get temperatureMax(): number { return this.parameters ? this.parameters.max("temperature", this.maxTemperature) : this.maxTemperature; }
    /** The least top_p the chosen model takes. */
    protected get topPMin(): number { return this.parameters ? this.parameters.min("topP", this.minTopP) : this.minTopP; }
    /** The greatest top_p the chosen model takes. */
    protected get topPMax(): number { return this.parameters ? this.parameters.max("topP", this.maxTopP) : this.maxTopP; }

    /**
     * Lifecycle hook that is called after data-bound properties are initialized
     */
    constructor(private translations: GeboAITranslationService,
        private chatModelsController: ChatModelsControllerService,
        private changeDetector: ChangeDetectorRef) {
    }

    /**
     * What the provider's integration can deliver to the chosen model besides text: the
     * MIME types of the files a request may carry, by modality.
     */
    protected carriedMediaTypes?: GModelCarriedMediaTypes;
    private carriedMediaTypesOf?: string;

    /** The file types Gebo sends to the model: the carried ones of the modalities the form declares. */
    protected get sentFileTypes(): string[] {
        const declared: string[] = this.formGroup?.controls["inputModalities"]?.value ?? [];
        const carried = this.carriedMediaTypes;
        if (!carried) return [];
        const sent = (kind: string) => declared.includes(kind) && this.kindsSentToModels.includes(kind);
        return [
            ...(sent("IMAGE") ? carried.image ?? [] : []),
            ...(sent("AUDIO") ? carried.audio ?? [] : []),
            ...(sent("VIDEO") ? carried.video ?? [] : []),
            ...(sent("DOCUMENT") ? carried.document ?? [] : [])];
    }

    /** The kinds of file the installation sends to its chat models (its settings): a file of another kind reaches no model. */
    protected kindsSentToModels: string[] = [];

    /** The labels of the declared modalities the integration carries but the installation does not send. */
    protected get declaredNotSentText(): string {
        const declared: string[] = this.formGroup?.controls["inputModalities"]?.value ?? [];
        const carried = this.carriedMediaTypes;
        if (!carried) return "";
        const of: { [key: string]: string[] | undefined } = { IMAGE: carried.image, AUDIO: carried.audio, VIDEO: carried.video, DOCUMENT: carried.document };
        return this.inputModalityOptions.filter(o => o.value !== "TEXT" && declared.includes(o.value) && !!(of[o.value]?.length)
            && !this.kindsSentToModels.includes(o.value))
            .map(o => o.label).join(", ");
    }

    /** The labels of the declared modalities this provider's integration carries nothing for. */
    protected get declaredNotCarriedText(): string {
        const declared: string[] = this.formGroup?.controls["inputModalities"]?.value ?? [];
        const carried = this.carriedMediaTypes;
        if (!carried) return "";
        const of: { [key: string]: string[] | undefined } = { IMAGE: carried.image, AUDIO: carried.audio, VIDEO: carried.video, DOCUMENT: carried.document };
        return this.inputModalityOptions.filter(o => o.value !== "TEXT" && declared.includes(o.value) && !(of[o.value]?.length))
            .map(o => o.label).join(", ");
    }

    private loadCarriedMediaTypes(modelCode?: string): void {
        const modelTypeCode: string | undefined = this.formGroup?.controls["modelTypeCode"]?.value;
        const key = modelTypeCode + "|" + modelCode;
        if (!modelTypeCode || key === this.carriedMediaTypesOf) return;
        this.carriedMediaTypesOf = key;
        this.chatModelsController.getCarriedMediaTypes(modelTypeCode, modelCode).subscribe({
            next: (carried) => {
                this.carriedMediaTypes = carried;
                this.changeDetector.markForCheck();
            },
            error: () => {
                this.carriedMediaTypes = undefined;
                this.carriedMediaTypesOf = undefined;
            }
        });
    }

    ngOnDestroy(): void {
        this.parameters?.destroy();
    }

    ngOnInit(): void {
        this.translations.translateMenuItems("GeboAIStandardChatModelModule", "GeboAIStandardChatModelSettings",
            this.inputModalityOptions as any[])
            .subscribe(items => this.inputModalityOptions = [...items] as { id: string, label: string, value: string }[]);
        this.chatModelsController.getFileKindsSentToModels().subscribe({
            next: (kinds) => {
                this.kindsSentToModels = [...(kinds ?? [])];
                this.changeDetector.markForCheck();
            },
            error: () => { this.kindsSentToModels = []; }
        });
    }

    /** The labels of what the model's metadata says it accepts, in the options' order. */
    protected get modelInputModalitiesText(): string {
        return this.inputModalityOptions.filter(o => this.modelInputModalities?.includes(o.value)).map(o => o.label).join(", ");
    }

    /**
     * Sets what the model accepts as input to what its metadata says, over a value saved
     * or changed by hand (an empty one takes the metadata by itself, see
     * fillInputModalitiesFromMetadata).
     */
    protected useModelInputModalities(event: Event): void {
        event.preventDefault();
        const control = this.formGroup?.controls["inputModalities"];
        if (control && this.modelInputModalities) {
            control.setValue([...this.modelInputModalities]);
            control.markAsDirty();
        }
    }

    /**
     * Updates the component state based on the selected model's capabilities
     * Sets appropriate default context length and determines if the model
     * has native context window settings
     * 
     * @param choosed - The selected model object containing context length information
     */
    private notifyChoosedModelChange(choosed?: { code?: string, contextLength?: number, metaInfos?: ModelMetaInfo, modelDetails?: { [key: string]: any } }) {
        this.loadCarriedMediaTypes(choosed?.code);
        this.parameters?.chosen(this.formGroup?.controls["modelTypeCode"]?.value, choosed);
        this.defaultContextLength = standardDefaultContextLength;
        this.modelHaveNativeContextWindowSettings = false;
        const meta: any = choosed?.metaInfos;
        this.modelInputModalities = meta?.inputModalities?.length ? [...meta.inputModalities]
            : meta?.supportsVision === true ? ["TEXT", "IMAGE"] : meta?.supportsVision === false ? ["TEXT"] : undefined;
        // after the patch the chosen model came with: a form loading a configuration sets
        // its other controls, the modalities among them, in the same patch
        queueMicrotask(() => {
            this.fillInputModalitiesFromMetadata();
            this.changeDetector.markForCheck();
        });
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
            this.parameters?.destroy();
            this.parameters = new ChatModelParametersControls(this.formGroup, this.chatModelsController, () => {
                this.offeredThinkingOptions = this.parameters ? this.parameters.thinkingOptions(this.thinkingOptions) : this.thinkingOptions;
                this.changeDetector.markForCheck();
            });
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