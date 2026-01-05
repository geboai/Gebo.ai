import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { FormControl, FormControlStatus, FormGroup, Validators } from "@angular/forms";
import { LLMSSetupConfiguration, SecretInfo, GeboFastLlmsSetupControllerService, LLMModelPresetChoice, GBaseModelChoice, LLMCreateModelData, ComponentLLMSStatus, GUserMessage, LLMModelsLookupParameter, LLMCredentialsVerificationData } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboAIValidators, IOperationStatus } from "@Gebo.ai/reusable-ui";
import { ToastMessageOptions } from "primeng/api";
import { forkJoin, map, Observable, of, Subscription } from "rxjs";
interface IModelChoice {
    enableAllFunctions?: boolean;
    setAsDefault?: boolean;
    choosedModel?: string;
};
interface IProviderAccess {
    selectedSecret?: string;
    baseUrl?: string;
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
    protected embeddingModelPresetsFormGroup: FormGroup = new FormGroup({
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

    protected chatPresets: LLMModelPresetChoice[] = [];
    protected embeddingPresets: LLMModelPresetChoice[] = [];
    protected lookedUpChatModels: GBaseModelChoice[] = [];
    protected lookedUpEmbeddingModels: GBaseModelChoice[] = [];
    private oldCredentialId?: string;
    private oldBaseUrl?: string;
    protected llmsStatus!: ComponentLLMSStatus;
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
            next:(baseUrlValueStatus)=>{
                this.handleSelectedSecretEnabled(baseUrlValueStatus,this.secretFormGroup.controls["baseUrl"].value,this.secretFormGroup.controls["requireApiKeyAniway"].value===true);
            }
        });
        this.secretFormGroup.controls["requireApiKeyAniway"].valueChanges.subscribe({
            next:(requireApiKeyAniway: boolean)=>{
                this.handleSelectedSecretEnabled(this.secretFormGroup.controls["baseUrl"].status,this.secretFormGroup.controls["baseUrl"].value,requireApiKeyAniway===true);
            }
        })
        
    }
    private handleSelectedSecretEnabled(baseUrlValueStatus: FormControlStatus, baseUrl: string | null, requireApiKeyAniway: boolean) {
        let requiresApiKey = this.vendorConfiguration?.parentModel.requiresApiKey === true || requireApiKeyAniway === true;
        let knownUrl: boolean = this.vendorConfiguration?.parentModel.requiresCustomUrl !== true;
        if (this.vendorConfiguration?.parentModel.requiresCustomUrl === true) {
            knownUrl = baseUrlValueStatus === "VALID" && (baseUrl!==null && baseUrl.length>0);
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
                this.chatModelPresetsFormGroup.controls["setAsDefault"].setValue(this.llmsStatus?.chatModelSetup !== true);
                this.embeddingModelPresetsFormGroup.controls["setAsDefault"].setValue(this.llmsStatus?.embeddedModelSetup !== true);
                this.chatModelAdvancedFormGroup.controls["setAsDefault"].setValue(this.llmsStatus?.chatModelSetup !== true);
                this.embeddingModelAdvancedFormGroup.controls["setAsDefault"].setValue(this.llmsStatus?.embeddedModelSetup !== true);
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
                        } break;
                        case "EMBEDDING": {
                            this.embeddingPresets = x.choices ? x.choices : [];
                            const defaultPreset = x.choices?.find(y => y.defaultChoice === true);
                            if (defaultPreset?.code) {
                                this.embeddingModelPresetsFormGroup.patchValue({ choosedModel: defaultPreset.code })
                            }
                        } break;
                    }
            });

        }
    }
    protected get presetCreateBtnEnabled(): boolean {
        const modelChoices: { choosedModel?: string }[] = [this.chatModelPresetsFormGroup.value, this.embeddingModelPresetsFormGroup.value];

        return modelChoices.filter(x => x.choosedModel ? true : false)?.length > 0;

    }
    protected get advancedCreateBtnEnabled(): boolean {
        const modelChoices: { choosedModel?: string }[] = [this.chatModelAdvancedFormGroup.value, this.embeddingModelAdvancedFormGroup.value];

        return modelChoices.filter(x => x.choosedModel ? true : false)?.length > 0;

    }

    protected createPresetLLMS() {
        const modelDataCreationArray: Array<LLMCreateModelData> = [];
        const chatModelChoice: IModelChoice = this.chatModelPresetsFormGroup.value;
        const embeddingModelChoice: IModelChoice = this.embeddingModelPresetsFormGroup.value;
        if (chatModelChoice?.choosedModel) {
            const value = this.buildCreateModelData(chatModelChoice, "CHAT");
            if (value) modelDataCreationArray.push(value);
        }
        if (embeddingModelChoice?.choosedModel) {
            const value = this.buildCreateModelData(embeddingModelChoice, "EMBEDDING");
            if (value) modelDataCreationArray.push(value);
        }
        this.createLLMS(modelDataCreationArray);
    }
    protected createAdvancedLLMS() {
        const modelDataCreationArray: Array<LLMCreateModelData> = [];
        const chatModelChoice: IModelChoice = this.chatModelAdvancedFormGroup.value;
        const embeddingModelChoice: IModelChoice = this.embeddingModelAdvancedFormGroup.value;
        if (chatModelChoice?.choosedModel) {
            const value = this.buildCreateModelData(chatModelChoice, "CHAT");
            if (value) modelDataCreationArray.push(value);
        }
        if (embeddingModelChoice?.choosedModel) {
            const value = this.buildCreateModelData(embeddingModelChoice, "EMBEDDING");
            if (value) modelDataCreationArray.push(value);
        }
        this.createLLMS(modelDataCreationArray);
    }
    private buildCreateModelData(modelChoice: IModelChoice, type: LLMCreateModelData.TypeEnum): LLMCreateModelData | undefined {
        if (this.vendorConfiguration?.libraryModel) {
            const preset = this.vendorConfiguration.libraryModel.find(providerPreset => providerPreset.type === type);
            const providerAccess: IProviderAccess = this.secretFormGroup.value;
            if (modelChoice?.choosedModel && preset) {
                const out: LLMCreateModelData = {
                    modelCode: modelChoice.choosedModel,
                    setAsDefaultModel: modelChoice.setAsDefault === true,
                    serviceHandler: preset.serviceHandler,
                    type: type,
                    baseUrl: providerAccess.baseUrl,
                    doModelsLookup: preset.doModelsLookup === true,
                    secretId: providerAccess.selectedSecret,
                    enableAllFunctions: modelChoice.enableAllFunctions === true
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
                next: (operationStatusList) => {
                    this.assignBackendMessages(operationStatusList?.messages);
                    if (operationStatusList && operationStatusList.hasErrorMessages !== true) {
                        this.vendorConfigurationChanged.emit(true);
                    }

                }, complete: () => {
                    this.loading = false;
                }
            });
        }
    }

}