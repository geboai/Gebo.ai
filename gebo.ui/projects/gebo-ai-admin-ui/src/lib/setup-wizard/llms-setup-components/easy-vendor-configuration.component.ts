import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup, ValidationErrors, ValidatorFn } from "@angular/forms";
import { GeboFastLlmsSetupControllerService, GUserMessage, LLMAutoconfigureCreationData, LLMModelPresetChoice, LLMSModelsPresets, LLMSSetupConfiguration, LLMSSetupConfigurationData, LLMSVendorInfo, SecretInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, IOperationStatus } from "@Gebo.ai/reusable-ui";
import { Observable, of } from "rxjs";
interface PresetSummary {
    type: LLMSModelsPresets.TypeEnum;
    mainUse?: LLMModelPresetChoice.UsesEnum;
    presetChoices?: LLMModelPresetChoice[];
    choice?: string;
}
interface IFormGroupData {
    vendorId?: string,
    secretId?: string,
    defaultChatModel?: string,
    internalServicesModel?: string,
    embeddingModel?: string,
}
function validateVendorSetup(fg: FormGroup): ValidationErrors | null {
    const value: IFormGroupData = fg.value;
    const ok: boolean = (value?.vendorId ? true : false) && (value?.secretId ? true : false) && ((value?.embeddingModel ? true : false) || (value?.internalServicesModel ? true : false) || (value?.defaultChatModel ? true : false));
    if (ok) return null;
    else return { erroneus: "true" };
}
const formValidator: ValidatorFn = (control) => {
    return validateVendorSetup(control as FormGroup);
};
@Component({
    selector: "gebo-ai-llms-easy-vendor-configuration-component",
    templateUrl: "easy-vendor-configuration.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboSetupWizardsModule", multi: false }, { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIEasyVendorConfigurationComponent"), multi: false }]
})
export class GeboAIEasyVendorConfigurationComponent implements OnInit, OnChanges {
    @Input() autoSettingsConfigurations?: LLMSSetupConfiguration[] = [];
    @Input() actualProvidersConfiguration?: LLMSSetupConfigurationData;
    @Output() llmsAutoSettingSuccessfull: EventEmitter<boolean> = new EventEmitter();
    @Output() llmsAutoSettingErrors: EventEmitter<GUserMessage[]> = new EventEmitter();
    protected choosableVendors: LLMSVendorInfo[] = [];
    protected vendorSetupMetaInfos?: LLMSSetupConfiguration;

    protected presetsSummary: PresetSummary[] = [];
    protected formGroup: FormGroup = new FormGroup({
        vendorId: new FormControl(),
        secretId: new FormControl(),
        defaultChatModel: new FormControl(),
        internalServicesModel: new FormControl(),
        embeddingModel: new FormControl()
    });
    protected loading: boolean = false;
    protected vendorId?: string;
    protected secretContext?: string;
    protected secretDescription?: string;
    protected validateCredentials: (credentials: SecretInfo) => Observable<IOperationStatus<any>> = (credentials: SecretInfo) => {

        return this.geboFastLLMSSetupService.verifyVendorCredentialsAndDownloadModels({
            secretId: credentials.code as string,
            vendorId: this.vendorId as string,
            baseUrl: undefined
        });
    };
    constructor(
        private geboFastLLMSSetupService: GeboFastLlmsSetupControllerService) {
        this.formGroup.addValidators(formValidator);
        this.formGroup.controls["vendorId"].valueChanges.subscribe({
            next: (vendorId) => {
                this.vendorSetupMetaInfos = undefined;
                this.vendorId = vendorId;
                this.secretContext = "llm-vendor";
                this.secretDescription = "llm credentials";
                if (vendorId) {
                    this.vendorSetupMetaInfos = this.autoSettingsConfigurations?.find(x => x.parentModel.vendorId === vendorId);
                    this.secretDescription = vendorId ? vendorId + " credentials" : "credentials";
                    this.secretContext = this.vendorSetupMetaInfos?.parentModel?.apiKeySecretContext;
                    const secrets: SecretInfo[] = [];
                    if (this.vendorSetupMetaInfos) {
                        const presets: PresetSummary[] = [];
                        const chatModels = this.vendorSetupMetaInfos.libraryModel.filter(x => x.type === "CHAT");
                        const models = chatModels[0];

                        if (chatModels.length) {
                            const defaultPreset = models.choices?.find(x => x.defaultChoice === true);
                            const chatpresets = models.choices?.filter(x => x.uses && x.uses.find(y => y === "CHAT"));
                            const defaultChoice: string | undefined = (defaultPreset ? (defaultPreset.code as string) : undefined);
                            if (defaultPreset && this.actualProvidersConfiguration?.defaultChatModelExists !== true) {
                                presets.push({
                                    type: "CHAT",
                                    choice: defaultChoice,
                                    presetChoices: chatpresets,
                                    mainUse: "CHAT"
                                });
                                this.formGroup.controls["defaultChatModel"].setValue(defaultChoice);
                            }

                            const internalServicesChoices = models.choices?.filter(x => x.uses && x.uses.find(y => y === "INTERNAL_SERVICES"));
                            if (internalServicesChoices && internalServicesChoices.length > 0 && this.actualProvidersConfiguration?.internalServicesChatModelExists !== true) {
                                presets.push({
                                    type: "CHAT",
                                    choice: internalServicesChoices[0]?.code,
                                    presetChoices: internalServicesChoices,
                                    mainUse: "INTERNAL_SERVICES"
                                });
                                this.formGroup.controls["internalServicesModel"].setValue(internalServicesChoices[0]?.code);
                            }

                        }
                        const embeddingModels = this.vendorSetupMetaInfos.libraryModel.filter(x => x.type === "EMBEDDING");
                        if (embeddingModels && embeddingModels.length) {
                            const defaultEmbeddingPreset = embeddingModels[0].choices?.find(x => x.defaultChoice === true);
                            if (defaultEmbeddingPreset && this.actualProvidersConfiguration?.embeddingModelExists !== true) {
                                presets.push({
                                    type: "EMBEDDING",
                                    presetChoices: embeddingModels[0].choices,
                                    choice: defaultEmbeddingPreset?.code
                                });
                                this.formGroup.controls["embeddingModel"].setValue(defaultEmbeddingPreset?.code);
                            }
                        }
                        this.presetsSummary = presets;
                    }
                    if (this.vendorSetupMetaInfos?.runtimeConfigs) {
                        this.vendorSetupMetaInfos.runtimeConfigs.forEach(x => {
                            if (x.secretInfo) {
                                if (!secrets.find(y => y.code === x.secretInfo?.code)) {
                                    secrets.push(x.secretInfo);
                                }
                            }
                        });

                    }
                }
                if (this.vendorId)
                    this.formGroup.controls["secretId"].enable();
                else
                    this.formGroup.controls["secretId"].disable();
            }
        });
    }
    ngOnInit(): void {

    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes["autoSettingsConfigurations"]) {
            let defaultVendorId: string | undefined;
            this.choosableVendors = this.autoSettingsConfigurations && this.autoSettingsConfigurations.length ? this.autoSettingsConfigurations.map(x => x.parentModel) : [];

            if (this.autoSettingsConfigurations) {
                for (let i: number = 0; i < this.autoSettingsConfigurations.length; i++) {
                    this.autoSettingsConfigurations[i].runtimeConfigs?.forEach(x => {
                        if (x.secretInfo) {
                            if (this.autoSettingsConfigurations)
                                defaultVendorId = this.autoSettingsConfigurations[i].parentModel?.vendorId;
                        }
                    });
                }
            }
            this.formGroup.controls["vendorId"].setValue(defaultVendorId);
        }
    }
    protected doAutoConfigure() {
        const creationData: LLMAutoconfigureCreationData = this.formGroup.value;
        this.loading = true;
        this.geboFastLLMSSetupService.createLLMByAutoconfigure(creationData).subscribe({
            next: (operationStatus) => {
                if (operationStatus.hasErrorMessages) {
                    this.llmsAutoSettingErrors.emit(operationStatus.messages);
                } else {
                    this.llmsAutoSettingSuccessfull.emit(true);
                }
            }, complete: () => {
                this.loading = false;
            }
        });
    }
}