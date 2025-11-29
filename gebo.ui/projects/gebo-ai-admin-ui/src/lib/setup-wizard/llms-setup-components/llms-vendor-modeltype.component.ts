import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { LLMSSetupConfiguration, SecretInfo, SecretsControllerService, GeboFastLlmsSetupControllerService, LLMModelPresetChoice, GBaseModelChoice, LLMCreateModelData, ComponentLLMSStatus, GUserMessage, LLMModelsLookupParameter } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboAITranslationService, GeboAIValidators, IOperationStatus } from "@Gebo.ai/reusable-ui";
import { ToastMessageOptions } from "primeng/api";
import { forkJoin, Observable, Subscription } from "rxjs";
interface IModelChoice {
    enableAllFunctions?:boolean;
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
        useExistingOrNew: new FormControl(),
        selectedSecret: new FormControl(),
        newApiSecret: new FormControl(),
        newUserName: new FormControl(),
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
    protected useExistingOrNewOptions: { label: string, value: string }[] = [{ label: "Existing credentials", value: "EXISTING" }, { label: "New credentials", value: "NEW" }];
    protected chatPresets: LLMModelPresetChoice[] = [];
    protected embeddingPresets: LLMModelPresetChoice[] = [];
    protected lookedUpChatModels: GBaseModelChoice[] = [];
    protected lookedUpEmbeddingModels: GBaseModelChoice[] = [];
    protected existingOrNewShow: boolean = false;
    private oldCredentialId?: string;
    private oldBaseUrl?: string;
    protected llmsStatus!: ComponentLLMSStatus;
    protected subscription?: Subscription;
    constructor(private secretController: SecretsControllerService,
        private geboFastLLMSSetupService: GeboFastLlmsSetupControllerService,
        private geboAITranslationService: GeboAITranslationService) {
        this.secretFormGroup.controls["requireApiKeyAniway"].valueChanges.subscribe({
            next: (value) => {
                if (this.vendorConfiguration?.parentModel?.requiresApiKey === true) return;
                if (value === true) {
                    this.secretFormGroup.controls["useExistingOrNew"].enable();
                    this.secretFormGroup.controls["selectedSecret"].enable();
                    this.secretFormGroup.controls["newApiSecret"].enable();
                    this.secretFormGroup.controls["newUserName"].enable();
                } else {
                    this.secretFormGroup.controls["useExistingOrNew"].disable();
                    this.secretFormGroup.controls["selectedSecret"].disable();
                    this.secretFormGroup.controls["newApiSecret"].disable();
                    this.secretFormGroup.controls["newUserName"].disable();
                }
                this.secretFormGroup.updateValueAndValidity();
            }
        });
        this.secretFormGroup.controls["useExistingOrNew"].setValidators(Validators.required);
        this.secretFormGroup.controls["useExistingOrNew"].valueChanges.subscribe({
            next: (value) => {
                const allCtrls = ["selectedSecret", "newApiSecret", "newUserName"];
                this.switchControlsRequired(allCtrls, false);

                if (value === "EXISTING") {
                    this.switchControlsRequired(["selectedSecret"], true);
                }
                if (value === "NEW") {
                    this.switchControlsRequired(["newApiSecret", "newUserName"], true);
                }
            }
        });
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
            this.subscription = this.geboAITranslationService.translateBackendMessages(withoutDuplicates).subscribe({
                next: (msgs) => {
                    if (msgs)
                        this.userMessages = msgs;
                }
            });
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
    protected createCredentials(): void {
        const credentials = this.secretFormGroup.value;
        if (this.vendorConfiguration?.parentModel.apiKeySecretContext && this.vendorConfiguration.libraryModel[0].serviceHandler && this.vendorConfiguration.libraryModel[0].type) {
            this.loading = true;
            this.geboFastLLMSSetupService.createLLMCredentials({
                apiKeySecretContext: this.vendorConfiguration?.parentModel.apiKeySecretContext,
                newApiSecret: credentials.newApiSecret,
                newUserName: credentials.newUserName,
                serviceHandler: this.vendorConfiguration.libraryModel[0].serviceHandler,
                type: this.vendorConfiguration.libraryModel[0].type,
                baseUrl: credentials.baseUrl,
                doModelsLookup: this.vendorConfiguration.libraryModel[0].doModelsLookup

            }).subscribe({
                next: (value) => {
                    //showing eventual fealures or successes
                    this.assignBackendMessages(value?.messages);
                    if (value.hasErrorMessages !== true && value.result) {
                        //the created secret is automatically selected and the option of using it or creating another
                        //new secret is shown.
                        this.secrets = [...this.secrets, value.result];
                        this.secretFormGroup.controls["useExistingOrNew"].setValue("EXISTING");
                        this.secretFormGroup.controls["selectedSecret"].setValue(value.result.code);
                        this.existingOrNewShow=true;
                    }
                },
                complete: () => {
                    this.loading = false;
                }
            });
        }
    }
    private switchControlsRequired(ctrls: string[], required: boolean) {
        ctrls.forEach(ctrName => {
            this.secretFormGroup.controls[ctrName].clearValidators();
            if (required === true) {
                this.secretFormGroup.controls[ctrName].addValidators(Validators.required);
            }
        });
    }
    reloadSecrets(): void {
        if (this.vendorConfiguration?.parentModel?.apiKeySecretContext) {
            this.loading = true;
            this.secretController.getSecretsByContextCode(this.vendorConfiguration?.parentModel?.apiKeySecretContext).subscribe({
                next: (infos) => {
                    if (infos) {
                        this.secrets = infos;
                        if (infos.length) {
                            this.secretFormGroup.controls["selectedSecret"].setValue(infos[0].code);
                            this.existingOrNewShow = true;
                            this.secretFormGroup.controls["useExistingOrNew"].setValue("EXISTING");

                        }

                    }
                    if (!infos || infos.length === 0) {
                        this.secretFormGroup.controls["useExistingOrNew"].setValue("NEW");
                        this.existingOrNewShow = false;
                    }
                },
                complete: () => {
                    this.loading = false;
                }
            });
        }
    }
    protected get saveDisabled(): boolean {
        //|| this.secretFormGroup.controls["newApiSecret"].disabled || this.secretFormGroup.controls["newUserName"].disabled
        const ctrlsValid = this.secretFormGroup.controls["newApiSecret"].valid && this.secretFormGroup.controls["newUserName"].valid;
        return !(ctrlsValid && (this.vendorConfiguration?.parentModel?.requiresApiKey === true || this.secretFormGroup.controls["requireApiKeyAniway"].value === true));

    }
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["vendorConfiguration"] && this.vendorConfiguration) {
            if (this.vendorConfiguration?.parentModel.requiresCustomUrl === true) {
                this.secretFormGroup.controls["baseUrl"].setValidators(GeboAIValidators.baseUrl(true));
            }
            if (!this.vendorConfiguration?.parentModel?.requiresApiKey === true) {
                this.secretFormGroup.controls["useExistingOrNew"].disable();
                this.secretFormGroup.controls["selectedSecret"].disable();
                this.secretFormGroup.controls["newApiSecret"].disable();
                this.secretFormGroup.controls["newUserName"].disable();
                this.secretFormGroup.updateValueAndValidity();
            }
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
            this.reloadSecrets();
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
                    enableAllFunctions: modelChoice.enableAllFunctions===true
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