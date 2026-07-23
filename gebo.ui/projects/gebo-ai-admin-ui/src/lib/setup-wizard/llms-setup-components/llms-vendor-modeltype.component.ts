import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { FormControl, FormControlStatus, FormGroup, Validators } from "@angular/forms";
import { LLMSSetupConfiguration, SecretInfo, GeboFastLlmsSetupControllerService, LLMModelPresetChoice, GBaseModelChoice, LLMCreateModelData, ComponentLLMSStatus, GUserMessage, LLMModelsLookupParameter, LLMCredentialsVerificationData, LLMUnresolvedModel } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboAIValidators, IOperationStatus } from "@Gebo.ai/reusable-ui";
import { ToastMessageOptions } from "primeng/api";
import { forkJoin, map, Observable, of, Subscription } from "rxjs";
interface IModelChoice {
    enableAllFunctions?: boolean;
    setAsDefault?: boolean;
    choosedModel?: string;
};
interface IProviderAccess {
    requireApiKeyAniway?: boolean;
    selectedSecret?: string;
    baseUrl?: string;
}
// A chosen model that the provider no longer offers, plus the live choices the
// user can pick a replacement from and the original request to rebuild.
interface IModelResolution {
    label: string;
    requestedModelCode?: string;
    availableChoices: GBaseModelChoice[];
    control: FormControl;
    original?: LLMCreateModelData;
}
@Component({
    selector: "gebo-ai-llms-vendor-model-type-config",
    templateUrl: "llms-vendor-modeltype.component.html",
    standalone: false
})
export class GeboAILlmsVendorModelTypeConfig implements OnInit, OnChanges {
    @Input() vendorConfiguration?: LLMSSetupConfiguration;
    @Output() vendorConfigurationChanged: EventEmitter<boolean> = new EventEmitter();
    protected userMessages: ToastMessageOptions[] = [];
    protected loading: boolean = false;
    protected secrets: SecretInfo[] = [];
    protected secretFormGroup: FormGroup = new FormGroup({
        requireApiKeyAniway: new FormControl(),
        selectedSecret: new FormControl(),
        baseUrl: new FormControl()
    });
    protected chatModelPresetsFormGroup: FormGroup = new FormGroup({
        enableAllFunctions: new FormControl(),
        setAsDefault: new FormControl(),
        choosedModel: new FormControl()
    });
    // Internal-services chat slot: always created as a non-default model with
    // uses = INTERNAL_SERVICES, so it has no "set as default" control.
    protected serviceChatModelPresetsFormGroup: FormGroup = new FormGroup({
        choosedModel: new FormControl()
    });
    protected serviceChatModelAdvancedFormGroup: FormGroup = new FormGroup({
        choosedModel: new FormControl()
    });
    protected embeddingModelPresetsFormGroup: FormGroup = new FormGroup({
        setAsDefault: new FormControl(),
        choosedModel: new FormControl()
    });
    protected rankerModelPresetsFormGroup: FormGroup = new FormGroup({
        setAsDefault: new FormControl(),
        choosedModel: new FormControl()
    });
    protected imagesModelPresetsFormGroup: FormGroup = new FormGroup({
        setAsDefault: new FormControl(),
        choosedModel: new FormControl()
    });
    protected transcriptModelPresetsFormGroup: FormGroup = new FormGroup({
        setAsDefault: new FormControl(),
        choosedModel: new FormControl()
    });
    protected ttsModelPresetsFormGroup: FormGroup = new FormGroup({
        setAsDefault: new FormControl(),
        choosedModel: new FormControl()
    });
    protected chatModelAdvancedFormGroup: FormGroup = new FormGroup({
        enableAllFunctions: new FormControl(),
        setAsDefault: new FormControl(),
        choosedModel: new FormControl()
    });

    protected embeddingModelAdvancedFormGroup: FormGroup = new FormGroup({
        setAsDefault: new FormControl(),
        choosedModel: new FormControl()
    });
    protected rankerModelAdvancedFormGroup: FormGroup = new FormGroup({
        setAsDefault: new FormControl(),
        choosedModel: new FormControl()
    });
    protected imagesModelAdvancedFormGroup: FormGroup = new FormGroup({
        setAsDefault: new FormControl(),
        choosedModel: new FormControl()
    });
    protected transcriptModelAdvancedFormGroup: FormGroup = new FormGroup({
        setAsDefault: new FormControl(),
        choosedModel: new FormControl()
    });
    protected ttsModelAdvancedFormGroup: FormGroup = new FormGroup({
        setAsDefault: new FormControl(),
        choosedModel: new FormControl()
    });
    protected chatPresets: LLMModelPresetChoice[] = [];
    protected embeddingPresets: LLMModelPresetChoice[] = [];
    protected imagePresets: LLMModelPresetChoice[] = [];
    protected rankingPresets: LLMModelPresetChoice[] = [];
    protected transcriptPresets: LLMModelPresetChoice[] = [];;
    protected ttsPresets: LLMModelPresetChoice[] = [];;
    protected lookedUpChatModels: GBaseModelChoice[] = [];
    protected lookedUpEmbeddingModels: GBaseModelChoice[] = [];
    protected lookedUpRankingModels: GBaseModelChoice[] = [];
    protected lookedUpImagesModels: GBaseModelChoice[] = [];
    protected lookedUpTtsModels: GBaseModelChoice[] = [];
    protected lookedUpTranscriptModels: GBaseModelChoice[] = [];
    private oldCredentialId?: string;
    private oldBaseUrl?: string;
    protected llmsStatus!: ComponentLLMSStatus;
    protected resolutions: IModelResolution[] = [];
    protected subscription?: Subscription;
    protected doBackendCredentialsValidation: (credentials: SecretInfo) => Observable<IOperationStatus<any>> = (credentials: SecretInfo) => {
        const baseUrl = this.secretFormGroup.controls["baseUrl"].value?.baseUrl;
        if (this.vendorConfiguration && credentials.code) {
            const data: LLMCredentialsVerificationData = {
                baseUrl: baseUrl,
                secretId: credentials.code as string,
                vendorId: this.vendorConfiguration?.parentModel.vendorId
            }
            return this.geboFastLLMSSetupService.verifyVendorCredentialsAndDownloadModels(data);
        } else {
            const rv: IOperationStatus<any> = {
                hasErrorMessages: true,
                messages: [{ id: "INVALID_VENDOR", detail: "Invalid credentials or baseUrl", jobId: "", severity: "error", summary: "Invalid data", timestamp: 0 }]
            }
            return of(rv);
        }
    };



    constructor(
        private geboFastLLMSSetupService: GeboFastLlmsSetupControllerService
    ) {

        this.secretFormGroup.controls["selectedSecret"].valueChanges.subscribe({
            next: (secretId) => {
                if (secretId) {
                    const baseUrl = this.secretFormGroup.controls["baseUrl"].value?.baseUrl;
                    if (secretId !== this.oldCredentialId || baseUrl !== this.oldBaseUrl) {

                        this.loadModels(secretId, baseUrl);
                        this.oldCredentialId = secretId;
                        this.oldBaseUrl = baseUrl;
                    }
                }
            }
        });
        this.secretFormGroup.controls["baseUrl"].valueChanges.subscribe({
            next: (baseUrl) => {
                if (this.secretFormGroup.controls["baseUrl"].invalid) return;
                if (baseUrl) {
                    const secretId = this.secretFormGroup.controls["selectedSecret"].value;
                    if (secretId !== this.oldCredentialId || baseUrl !== this.oldBaseUrl) {

                        this.loadModels(secretId, baseUrl);
                        this.oldCredentialId = secretId;
                        this.oldBaseUrl = baseUrl;
                    }
                }
            }
        });
        this.secretFormGroup.controls["baseUrl"].statusChanges.subscribe({
            next: (baseUrlValueStatus) => {
                this.handleSelectedSecretEnabled(baseUrlValueStatus, this.secretFormGroup.controls["baseUrl"].value, this.secretFormGroup.controls["requireApiKeyAniway"].value === true);
            }
        });
        this.secretFormGroup.controls["requireApiKeyAniway"].valueChanges.subscribe({
            next: (requireApiKeyAniway: boolean) => {
                this.handleSelectedSecretEnabled(this.secretFormGroup.controls["baseUrl"].status, this.secretFormGroup.controls["baseUrl"].value, requireApiKeyAniway === true);
            }
        })

    }
    private handleSelectedSecretEnabled(baseUrlValueStatus: FormControlStatus, baseUrl: string | null, requireApiKeyAniway: boolean) {
        let usableBaseUrl: string | undefined = undefined;
        let usableSecretId: string | undefined;
        let doLookupModels: boolean = false;
        const requiresApiKey = this.vendorConfiguration?.parentModel.requiresApiKey === true || requireApiKeyAniway === true;
        let knownUrl: boolean = this.vendorConfiguration?.parentModel.requiresCustomUrl !== true;
        let apiKeyOk: boolean = false;
        if (this.vendorConfiguration?.parentModel.requiresCustomUrl === true) {
            knownUrl = baseUrlValueStatus === "VALID" && (baseUrl !== null && baseUrl.length > 0);
            if (knownUrl) {
                usableBaseUrl = baseUrl ? baseUrl : undefined;
            }
        }
        const selectedSecretEnabled: boolean = requiresApiKey && knownUrl;
        this.secretFormGroup.controls["selectedSecret"].clearValidators();
        if (selectedSecretEnabled) {
            this.secretFormGroup.controls["selectedSecret"].enable();
            this.secretFormGroup.controls["selectedSecret"].setValidators(Validators.required);
            this.secretFormGroup.controls["selectedSecret"].updateValueAndValidity();
        } else {
            this.secretFormGroup.controls["selectedSecret"].disable();
        }
        const actualParams: IProviderAccess = this.secretFormGroup.value;
        if (requiresApiKey) {
            usableSecretId = actualParams?.selectedSecret;
            apiKeyOk = usableSecretId ? true : false;
        } else {
            apiKeyOk = true;
        }
        doLookupModels = apiKeyOk && knownUrl;
        if (doLookupModels === true) {
            this.loadModels(usableSecretId, usableBaseUrl);
        }

    }

    private assignBackendMessages(messages?: GUserMessage[]) {
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
        if (messages && messages.length) {
            const withoutDuplicates: GUserMessage[] = [];
            messages.forEach(msg => {
                if (!withoutDuplicates.find(x => x.id === msg.id)) {
                    withoutDuplicates.push(msg);
                }
            });

            this.userMessages = withoutDuplicates;


        } else {
            this.userMessages = [];
        }
    }
    private loadModels(secretId?: string, baseUrl?: string) {
        this.resolutions = [];
        if (!secretId && this.vendorConfiguration?.parentModel.requiresApiKey === true) {
            this.lookedUpChatModels = [];
            this.lookedUpEmbeddingModels = [];
        } else {
            const observables: Observable<IOperationStatus<GBaseModelChoice[]>>[] = [];
            this.vendorConfiguration?.libraryModel.forEach(x => {
                const credentials: LLMModelsLookupParameter = {
                    secretId: secretId,
                    serviceHandler: x.serviceHandler,
                    type: x.type,
                    baseUrl: baseUrl
                };
                const observable = this.geboFastLLMSSetupService.verifyCredentialsAndDownloadModels(credentials);
                observables.push(observable);
            });
            this.loading = true;
            forkJoin(observables).subscribe({
                next: (operationStatusArray) => {
                    let toastMessages: GUserMessage[] = [];
                    this.vendorConfiguration?.libraryModel.forEach((x, index) => {
                        let current = operationStatusArray[index];
                        if (current.messages) {
                            toastMessages = [...toastMessages, ...current.messages];
                        }
                        if (x.type === "CHAT") {
                            this.lookedUpChatModels = current.result ? current.result : [];
                        }
                        if (x.type === "EMBEDDING") {
                            this.lookedUpEmbeddingModels = current.result ? current.result : [];
                        }
                        if (x.type === "RANKING") {
                            this.lookedUpRankingModels = current.result ? current.result : [];
                        }
                        if (x.type === "IMAGESGEN") {
                            this.lookedUpImagesModels = current.result ? current.result : [];
                        }
                        if (x.type === "TTS") {
                            this.lookedUpTtsModels = current.result ? current.result : [];
                        }
                        if (x.type === "TRANSCRIPT") {
                            this.lookedUpTranscriptModels = current.result ? current.result : [];
                        }
                    });
                    this.assignBackendMessages(toastMessages);
                },
                complete: () => {
                    this.loading = false;
                }
            })
        }

    }
    ngOnInit(): void {
        this.loadLLMSStatus();
    }
    private loadLLMSStatus(): void {
        this.loading = true;
        this.geboFastLLMSSetupService.getLLMSSetupStatus().subscribe({
            next: (value) => {
                this.llmsStatus = value;
                // Advanced tab: pre-check "set as default" only when no default of that kind
                // exists yet. An expert can still tick it to override an existing default.
                this.chatModelAdvancedFormGroup.controls["setAsDefault"].setValue(value?.chatModelSetup !== true);
                this.embeddingModelAdvancedFormGroup.controls["setAsDefault"].setValue(value?.embeddedModelSetup !== true);
                this.rankerModelAdvancedFormGroup.controls["setAsDefault"].setValue(value?.rankingModelSetup !== true);
                this.imagesModelAdvancedFormGroup.controls["setAsDefault"].setValue(value?.imagesModelSetup !== true);
                this.transcriptModelAdvancedFormGroup.controls["setAsDefault"].setValue(value?.transcriptModelSetup !== true);
                this.ttsModelAdvancedFormGroup.controls["setAsDefault"].setValue(value?.ttsModelSetup !== true);
            },
            complete: () => {
                this.loading = false;
            }
        })
    }

    private switchControlsRequired(ctrls: string[], required: boolean) {
        ctrls.forEach(ctrName => {
            this.secretFormGroup.controls[ctrName].clearValidators();
            if (required === true) {
                this.secretFormGroup.controls[ctrName].addValidators(Validators.required);
            }
        });
    }


    ngOnChanges(changes: SimpleChanges): void {
        if (changes["vendorConfiguration"] && this.vendorConfiguration) {
            if (this.vendorConfiguration?.parentModel.requiresCustomUrl === true) {
                this.secretFormGroup.controls["baseUrl"].setValidators(GeboAIValidators.baseUrl(true));
                //set a value from existing configs or from default url value
                let defaultBaseUrl = this.vendorConfiguration.parentModel.defaultCustomUrl;
                if (this.vendorConfiguration.runtimeConfigs && this.vendorConfiguration.runtimeConfigs.length) {
                    const firstNonNull = this.vendorConfiguration.runtimeConfigs.find(x => x.baseUrl ? true : false);
                    if (firstNonNull?.baseUrl) {
                        defaultBaseUrl = firstNonNull.baseUrl;
                    }
                }
                if (defaultBaseUrl) {
                    this.secretFormGroup.controls["baseUrl"].setValue(defaultBaseUrl);
                }
            }
            this.secretFormGroup.controls["selectedSecret"].clearValidators();
            if (this.vendorConfiguration?.parentModel?.requiresApiKey === true) {
                this.secretFormGroup.controls["selectedSecret"].enable();
                this.secretFormGroup.controls["selectedSecret"].setValidators(Validators.required);
            } else {
                this.secretFormGroup.controls["selectedSecret"].disable();

            }
            this.secretFormGroup.updateValueAndValidity();
            this.vendorConfiguration.libraryModel.forEach(x => {
                if (x.type)
                    switch (x.type) {
                        case "CHAT": {
                            this.chatPresets = x.choices ? x.choices : [];
                            const defaultPreset = x.choices?.find(y => y.defaultChoice === true);
                            if (defaultPreset?.code) {
                                this.chatModelPresetsFormGroup.patchValue({ choosedModel: defaultPreset.code })
                            }
                            // Preselect a sensible internal-services model: prefer a choice
                            // explicitly declared for INTERNAL_SERVICES, otherwise the default one.
                            const serviceChoice = x.choices?.find(y => y.uses && y.uses.find(u => u === "INTERNAL_SERVICES")) ?? defaultPreset;
                            if (serviceChoice?.code) {
                                this.serviceChatModelPresetsFormGroup.patchValue({ choosedModel: serviceChoice.code })
                            }
                        } break;
                        case "EMBEDDING": {
                            this.embeddingPresets = x.choices ? x.choices : [];
                            const defaultPreset = x.choices?.find(y => y.defaultChoice === true);
                            if (defaultPreset?.code) {
                                this.embeddingModelPresetsFormGroup.patchValue({ choosedModel: defaultPreset.code })
                            }
                        } break;
                        case "IMAGESGEN": {
                            this.imagePresets = x.choices ? x.choices : [];
                            const defaultPreset = x.choices?.find(y => y.defaultChoice === true);
                            if (defaultPreset?.code) {
                                this.imagesModelPresetsFormGroup.patchValue({ choosedModel: defaultPreset.code })
                            }
                        } break;
                        case "RANKING": {
                            this.rankingPresets = x.choices ? x.choices : [];
                            const defaultPreset = x.choices?.find(y => y.defaultChoice === true);
                            if (defaultPreset?.code) {
                                this.rankerModelPresetsFormGroup.patchValue({ choosedModel: defaultPreset.code })
                            }
                        } break;
                        case "TRANSCRIPT": {
                            this.transcriptPresets = x.choices ? x.choices : [];
                            const defaultPreset = x.choices?.find(y => y.defaultChoice === true);
                            if (defaultPreset?.code) {
                                this.transcriptModelPresetsFormGroup.patchValue({ choosedModel: defaultPreset.code })
                            }
                        } break;
                        case "TTS": {
                            this.ttsPresets = x.choices ? x.choices : [];
                            const defaultPreset = x.choices?.find(y => y.defaultChoice === true);
                            if (defaultPreset?.code) {
                                this.ttsModelPresetsFormGroup.patchValue({ choosedModel: defaultPreset.code })
                            }
                        } break;
                    }
            });

        }
    }
    // Per-kind visibility: a kind is offered only while its default (for chat, the
    // matching uses-slot) is not yet configured. Once configured, further models of
    // that kind are added from the LLMs admin per-provider / model-type screens.
    protected get showDefaultChat(): boolean { return this.llmsStatus?.chatModelSetup !== true; }
    protected get showServiceChat(): boolean { return this.llmsStatus?.internalServicesChatModelSetup !== true; }
    protected get showEmbedding(): boolean { return this.llmsStatus?.embeddedModelSetup !== true; }
    protected get showRanker(): boolean { return this.llmsStatus?.rankingModelSetup !== true; }
    protected get showImages(): boolean { return this.llmsStatus?.imagesModelSetup !== true; }
    protected get showTts(): boolean { return this.llmsStatus?.ttsModelSetup !== true; }
    protected get showTranscript(): boolean { return this.llmsStatus?.transcriptModelSetup !== true; }
    protected get allKindsConfigured(): boolean {
        return !this.showDefaultChat && !this.showServiceChat && !this.showEmbedding
            && !this.showRanker && !this.showImages && !this.showTts && !this.showTranscript;
    }
    protected get allConfiguredNotice(): ToastMessageOptions[] {
        return this.allKindsConfigured ? [{
            severity: "info", summary: "All model kinds already configured",
            detail: "Every default model kind is already set up. To add or change models of an existing kind use the LLMs admin per-provider / model-type screens."
        }] : [];
    }

    protected get presetCreateBtnEnabled(): boolean {
        const modelChoices: { choosedModel?: string }[] = [
            this.chatModelPresetsFormGroup.value,
            this.serviceChatModelPresetsFormGroup.value,
            this.embeddingModelPresetsFormGroup.value,
            this.rankerModelPresetsFormGroup.value,
            this.imagesModelPresetsFormGroup.value,
            this.transcriptModelPresetsFormGroup.value,
            this.ttsModelPresetsFormGroup.value
        ];

        return modelChoices.filter(x => x.choosedModel ? true : false)?.length > 0;

    }
    protected get advancedCreateBtnEnabled(): boolean {
        const modelChoices: { choosedModel?: string }[] = [
            this.chatModelAdvancedFormGroup.value,
            this.serviceChatModelAdvancedFormGroup.value,
            this.embeddingModelAdvancedFormGroup.value,
            this.rankerModelAdvancedFormGroup.value,
            this.imagesModelAdvancedFormGroup.value,
            this.transcriptModelAdvancedFormGroup.value,
            this.ttsModelAdvancedFormGroup.value
        ];

        return modelChoices.filter(x => x.choosedModel ? true : false)?.length > 0;

    }
    private addModelData(fg: FormGroup, array: LLMCreateModelData[], type: LLMCreateModelData.TypeEnum, show: boolean, uses?: Array<LLMCreateModelData.UsesEnum>, honorDefaultFlag: boolean = false) {
        const model: IModelChoice = fg.value;
        if (show && model.choosedModel) {
            const value = this.buildCreateModelData(model, type, uses, honorDefaultFlag);
            if (value)
                array.push(value);
        }
    }
    protected createPresetLLMS() {
        const modelDataCreationArray: Array<LLMCreateModelData> = [];
        this.addModelData(this.chatModelPresetsFormGroup, modelDataCreationArray, "CHAT", this.showDefaultChat, ["CHAT"]);
        this.addModelData(this.serviceChatModelPresetsFormGroup, modelDataCreationArray, "CHAT", this.showServiceChat, ["INTERNAL_SERVICES"]);
        this.addModelData(this.embeddingModelPresetsFormGroup, modelDataCreationArray, "EMBEDDING", this.showEmbedding);
        this.addModelData(this.rankerModelPresetsFormGroup, modelDataCreationArray, "RANKING", this.showRanker);
        this.addModelData(this.imagesModelPresetsFormGroup, modelDataCreationArray, "IMAGESGEN", this.showImages);
        this.addModelData(this.transcriptModelPresetsFormGroup, modelDataCreationArray, "TRANSCRIPT", this.showTranscript);
        this.addModelData(this.ttsModelPresetsFormGroup, modelDataCreationArray, "TTS", this.showTts);

        this.createLLMS(modelDataCreationArray);
    }
    protected createAdvancedLLMS() {
        // Advanced (expert) tab: every kind is available regardless of what already
        // exists, and the "set as default" choice is honoured so an expert can add
        // extra models and override the current default.
        const modelDataCreationArray: Array<LLMCreateModelData> = [];
        this.addModelData(this.chatModelAdvancedFormGroup, modelDataCreationArray, "CHAT", true, ["CHAT"], true);
        this.addModelData(this.serviceChatModelAdvancedFormGroup, modelDataCreationArray, "CHAT", true, ["INTERNAL_SERVICES"], true);
        this.addModelData(this.embeddingModelAdvancedFormGroup, modelDataCreationArray, "EMBEDDING", true, undefined, true);
        this.addModelData(this.rankerModelAdvancedFormGroup, modelDataCreationArray, "RANKING", true, undefined, true);
        this.addModelData(this.imagesModelAdvancedFormGroup, modelDataCreationArray, "IMAGESGEN", true, undefined, true);
        this.addModelData(this.transcriptModelAdvancedFormGroup, modelDataCreationArray, "TRANSCRIPT", true, undefined, true);
        this.addModelData(this.ttsModelAdvancedFormGroup, modelDataCreationArray, "TTS", true, undefined, true);
        this.createLLMS(modelDataCreationArray);
    }
    private buildCreateModelData(modelChoice: IModelChoice, type: LLMCreateModelData.TypeEnum, uses?: Array<LLMCreateModelData.UsesEnum>, honorDefaultFlag: boolean = false): LLMCreateModelData | undefined {
        if (this.vendorConfiguration?.libraryModel) {
            const preset = this.vendorConfiguration.libraryModel.find(providerPreset => providerPreset.type === type);
            const providerAccess: IProviderAccess = this.secretFormGroup.value;
            if (modelChoice?.choosedModel && preset) {
                // Internal-services chat is always non-default. For every other kind the
                // guided flows force the default (honorDefaultFlag=false), while the expert
                // Advanced tab honours the per-row "set as default" checkbox.
                const isInternalServices = uses ? uses.indexOf("INTERNAL_SERVICES") >= 0 : false;
                const out: LLMCreateModelData = {
                    modelCode: modelChoice.choosedModel,
                    setAsDefaultModel: isInternalServices ? false : (honorDefaultFlag ? (modelChoice.setAsDefault === true) : true),
                    serviceHandler: preset.serviceHandler,
                    type: type,
                    baseUrl: providerAccess.baseUrl,
                    doModelsLookup: preset.doModelsLookup === true,
                    secretId: providerAccess.selectedSecret,
                    enableAllFunctions: modelChoice.enableAllFunctions === true,
                    uses: uses
                };
                return out;
            }

        }
        return undefined;
    }

    private createLLMS(modelDataCreationArray: Array<LLMCreateModelData>) {
        if (modelDataCreationArray?.length) {
            this.loading = true;
            this.geboFastLLMSSetupService.createLLMS(modelDataCreationArray).subscribe({
                next: (operationStatus) => {
                    this.assignBackendMessages(operationStatus?.messages);
                    const unresolved = operationStatus?.result?.unresolved ?? [];
                    const created = operationStatus?.result?.created ?? [];
                    // Some chosen models are no longer offered by the provider: present the
                    // live choices so the user can pick a replacement and resubmit.
                    this.buildResolutions(unresolved, modelDataCreationArray);
                    // Anything actually created should refresh the parent status (hidden kinds).
                    if (created.length > 0) {
                        this.vendorConfigurationChanged.emit(true);
                    }
                }, complete: () => {
                    this.loading = false;
                }
            });
        }
    }

    private resolutionLabel(type?: LLMUnresolvedModel.TypeEnum, isService?: boolean): string {
        if (type === "CHAT") return isService ? "Internal services chat model" : "Default chat model";
        switch (type) {
            case "EMBEDDING": return "Embedding model";
            case "RANKING": return "Ranker model";
            case "IMAGESGEN": return "Image generation model";
            case "TTS": return "Text to speech model";
            case "TRANSCRIPT": return "Transcript model";
        }
        return type ? type : "Model";
    }

    private buildResolutions(unresolved: LLMUnresolvedModel[], requests: LLMCreateModelData[]) {
        this.resolutions = unresolved.map(u => {
            const isService = (u.uses ?? []).indexOf("INTERNAL_SERVICES") >= 0;
            const original = requests.find(r => r.type === u.type
                && (((r.uses ?? []).indexOf("INTERNAL_SERVICES") >= 0) === isService));
            return {
                label: this.resolutionLabel(u.type, isService),
                requestedModelCode: u.requestedModelCode,
                availableChoices: u.availableChoices ?? [],
                control: new FormControl(),
                original: original
            } as IModelResolution;
        });
    }

    protected get resolveCreateBtnEnabled(): boolean {
        return this.resolutions.length > 0 && this.resolutions.some(r => r.control.value ? true : false);
    }

    protected resolveAndCreate() {
        const modelDataCreationArray: Array<LLMCreateModelData> = [];
        this.resolutions.forEach(r => {
            const chosen: string | undefined = r.control.value;
            if (chosen && r.original) {
                modelDataCreationArray.push({ ...r.original, modelCode: chosen });
            }
        });
        this.resolutions = [];
        this.createLLMS(modelDataCreationArray);
    }

}