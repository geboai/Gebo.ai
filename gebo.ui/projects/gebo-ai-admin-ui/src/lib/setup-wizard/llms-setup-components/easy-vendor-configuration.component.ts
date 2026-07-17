import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup, ValidationErrors, ValidatorFn } from "@angular/forms";
import { GBaseModelChoice, GeboFastLlmsSetupControllerService, GUserMessage, LLMAutoconfigureCreationData, LLMModelPresetChoice, LLMSModelsPresets, LLMSSetupConfiguration, LLMSSetupConfigurationData, LLMSVendorInfo, SecretInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, IOperationStatus } from "@Gebo.ai/reusable-ui";
import { Observable, of } from "rxjs";
/**
 * An entry of a model combo: the code is what gets submitted, the description is what the
 * user reads. fromPreset marks the models the vendor library suggests, so that they are
 * listed before the rest of the provider catalogue.
 */
interface ModelChoiceOption {
    code?: string;
    description?: string;
    fromPreset?: boolean;
}
interface PresetSummary {
    type: LLMSModelsPresets.TypeEnum;
    mainUse?: LLMModelPresetChoice.UsesEnum;
    presetChoices?: LLMModelPresetChoice[];
    choice?: string;
    // The form control this summary drives, so that the preselection can be revoked when
    // the provider does not offer the preselected model anymore.
    controlName: string;
    // Everything the vendor library declares for this model kind: it gives the models the
    // provider lists back the readable description of the library (a plain models listing
    // only carries identifiers).
    libraryChoices?: LLMModelPresetChoice[];
    serviceHandler?: string;
    doModelsLookup?: boolean;
    // What the combo shows: the models looked up on the provider once the api key is
    // known, the library presets until then (or when the lookup is not available).
    modelChoices?: ModelChoiceOption[];
    // True when the lookup succeeded and the preselected library model is not part of the
    // provider catalogue anymore: the combo is then unset and flagged to the user.
    presetUnavailable?: boolean;
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
        embeddingModel: new FormControl(),
        rankerModel: new FormControl(),
        transcriptModel: new FormControl(),
        ttsModel: new FormControl(),
        imagesModel: new FormControl()
    });
    protected loading: boolean = false;
    // Number of model-kind catalogues still downloading from the provider: the panel stays
    // blocked until every combo holds the models the provider really offers.
    protected modelsLookupsRunning: number = 0;
    // When false (default) the per-model choice combos are hidden and the setup
    // runs with the preselected preset defaults; when true the user can review
    // and change each model choice. It is forced open when a preselected model is
    // not offered by the provider anymore and has to be replaced by hand.
    protected showLlmsDetail: boolean = false;
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
                                    mainUse: "CHAT",
                                    controlName: "defaultChatModel",
                                    libraryChoices: models.choices,
                                    serviceHandler: models.serviceHandler,
                                    doModelsLookup: models.doModelsLookup
                                });
                                this.formGroup.controls["defaultChatModel"].setValue(defaultChoice);
                            }

                            const internalServicesChoices = models.choices?.filter(x => x.uses && x.uses.find(y => y === "INTERNAL_SERVICES"));
                            if (internalServicesChoices && internalServicesChoices.length > 0 && this.actualProvidersConfiguration?.internalServicesChatModelExists !== true) {
                                presets.push({
                                    type: "CHAT",
                                    choice: internalServicesChoices[0]?.code,
                                    presetChoices: internalServicesChoices,
                                    mainUse: "INTERNAL_SERVICES",
                                    controlName: "internalServicesModel",
                                    libraryChoices: models.choices,
                                    serviceHandler: models.serviceHandler,
                                    doModelsLookup: models.doModelsLookup
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
                                    choice: defaultEmbeddingPreset?.code,
                                    controlName: "embeddingModel",
                                    libraryChoices: embeddingModels[0].choices,
                                    serviceHandler: embeddingModels[0].serviceHandler,
                                    doModelsLookup: embeddingModels[0].doModelsLookup
                                });
                                this.formGroup.controls["embeddingModel"].setValue(defaultEmbeddingPreset?.code);
                            }
                        }
                        const rankingModels = this.vendorSetupMetaInfos.libraryModel.filter(x => x.type === "RANKING");
                        if (rankingModels && rankingModels.length && this.actualProvidersConfiguration?.rankerModelExists !== true) {
                            const defaultRankingPreset = rankingModels[0].choices?.find(x => x.defaultChoice === true);
                            presets.push({
                                type: "RANKING", presetChoices: rankingModels[0].choices, choice: defaultRankingPreset?.code,
                                controlName: "rankerModel", libraryChoices: rankingModels[0].choices,
                                serviceHandler: rankingModels[0].serviceHandler, doModelsLookup: rankingModels[0].doModelsLookup
                            });
                            this.formGroup.controls["rankerModel"].setValue(defaultRankingPreset?.code);
                        }
                        const ttsModels = this.vendorSetupMetaInfos.libraryModel.filter(x => x.type === "TTS");
                        if (ttsModels && ttsModels.length && this.actualProvidersConfiguration?.ttsModelExists !== true) {
                            const defaultTtsPreset = ttsModels[0].choices?.find(x => x.defaultChoice === true);
                            presets.push({
                                type: "TTS", presetChoices: ttsModels[0].choices, choice: defaultTtsPreset?.code,
                                controlName: "ttsModel", libraryChoices: ttsModels[0].choices,
                                serviceHandler: ttsModels[0].serviceHandler, doModelsLookup: ttsModels[0].doModelsLookup
                            });
                            this.formGroup.controls["ttsModel"].setValue(defaultTtsPreset?.code);
                        }
                        const transcriptModels = this.vendorSetupMetaInfos.libraryModel.filter(x => x.type === "TRANSCRIPT");
                        if (transcriptModels && transcriptModels.length && this.actualProvidersConfiguration?.transcriptModelExists !== true) {
                            const defaultTranscriptPreset = transcriptModels[0].choices?.find(x => x.defaultChoice === true);
                            presets.push({
                                type: "TRANSCRIPT", presetChoices: transcriptModels[0].choices, choice: defaultTranscriptPreset?.code,
                                controlName: "transcriptModel", libraryChoices: transcriptModels[0].choices,
                                serviceHandler: transcriptModels[0].serviceHandler, doModelsLookup: transcriptModels[0].doModelsLookup
                            });
                            this.formGroup.controls["transcriptModel"].setValue(defaultTranscriptPreset?.code);
                        }
                        const imagesModels = this.vendorSetupMetaInfos.libraryModel.filter(x => x.type === "IMAGESGEN");
                        if (imagesModels && imagesModels.length && this.actualProvidersConfiguration?.imagesModelExists !== true) {
                            const defaultImagesPreset = imagesModels[0].choices?.find(x => x.defaultChoice === true);
                            presets.push({
                                type: "IMAGESGEN", presetChoices: imagesModels[0].choices, choice: defaultImagesPreset?.code,
                                controlName: "imagesModel", libraryChoices: imagesModels[0].choices,
                                serviceHandler: imagesModels[0].serviceHandler, doModelsLookup: imagesModels[0].doModelsLookup
                            });
                            this.formGroup.controls["imagesModel"].setValue(defaultImagesPreset?.code);
                        }
                        // Until the provider is queried the combos offer what the vendor library
                        // declares, exactly as before the api key is known. Every vendor owns its
                        // own api key context, so changing vendor makes the api key control reload
                        // and re-emit its secret: the lookup of the new vendor is driven from there.
                        presets.forEach(preset => this.resetToLibraryChoices(preset));
                        this.presetsSummary = presets;
                    }


                }
                if (this.vendorId)
                    this.formGroup.controls["secretId"].enable();
                else
                    this.formGroup.controls["secretId"].disable();
            }
        });
        this.formGroup.controls["secretId"].valueChanges.subscribe({
            next: (secretId) => {
                this.lookupModels(secretId);
            }
        });
    }

    /**
     * Fills the combos of every model kind with what the provider really offers, as soon as
     * an api key is known. Each model kind is looked up on its own handler and the result is
     * shared by all the combos of that kind (the two chat slots use the same catalogue).
     * Kinds the vendor library excludes from the lookup, and lookups the provider refuses,
     * keep the library presets: a catalogue we could not read proves nothing about a model.
     */
    private lookupModels(secretId?: string): void {
        if (!secretId) {
            this.presetsSummary.forEach(preset => this.resetToLibraryChoices(preset));
            return;
        }
        const lookedUpTypes: LLMSModelsPresets.TypeEnum[] = [];
        this.presetsSummary.filter(x => x.doModelsLookup === true && x.serviceHandler).forEach(preset => {
            if (lookedUpTypes.indexOf(preset.type) >= 0) {
                return;
            }
            lookedUpTypes.push(preset.type);
            const type = preset.type;
            const requestedVendorId = this.vendorId;
            this.modelsLookupsRunning++;
            this.geboFastLLMSSetupService.verifyCredentialsAndDownloadModels({
                type: type,
                serviceHandler: preset.serviceHandler as string,
                secretId: secretId,
                baseUrl: undefined
            }).subscribe({
                next: (operationStatus) => {
                    // The vendor may have been switched while the catalogue was downloading:
                    // a late answer must not land on the combos of another vendor.
                    if (requestedVendorId !== this.vendorId) {
                        return;
                    }
                    if (operationStatus.hasErrorMessages !== true && operationStatus.result) {
                        this.applyLookedUpModels(type, operationStatus.result);
                    }
                },
                error: () => {
                    this.modelsLookupsRunning--;
                },
                complete: () => {
                    this.modelsLookupsRunning--;
                }
            });
        });
    }

    /**
     * Hands the models the provider offers to every combo of that kind, keeping the library
     * preselection when the provider still offers it and revoking it when it does not: a
     * model the provider dropped cannot be created anymore, so the user has to pick another
     * one and the combos are opened to let them do it.
     */
    private applyLookedUpModels(type: LLMSModelsPresets.TypeEnum, availableModels: GBaseModelChoice[]): void {
        this.presetsSummary.filter(x => x.type === type).forEach(preset => {
            preset.modelChoices = this.describeModels(availableModels, preset.libraryChoices);
            if (!preset.choice) {
                // Nothing was preconfigured for this kind: there is no preselection to lose.
                preset.presetUnavailable = false;
                return;
            }
            // Matched the way the creation service matches it, so that what the combo accepts
            // is exactly what the backend will resolve.
            const stillOffered = availableModels.some(model => model.code
                && model.code.toLowerCase() === (preset.choice as string).toLowerCase());
            preset.presetUnavailable = !stillOffered;
            if (!stillOffered) {
                this.formGroup.controls[preset.controlName].setValue(undefined);
                this.showLlmsDetail = true;
            }
        });
    }

    /**
     * Merges the catalogue of the provider with the vendor library: the library entries keep
     * their readable description and lead the list, the rest of the catalogue follows.
     */
    private describeModels(availableModels: GBaseModelChoice[], libraryChoices?: LLMModelPresetChoice[]): ModelChoiceOption[] {
        const library = libraryChoices || [];
        const described: ModelChoiceOption[] = availableModels.map(model => {
            const libraryChoice = library.find(x => x.code && model.code
                && x.code.toLowerCase() === (model.code as string).toLowerCase());
            return {
                code: model.code,
                description: libraryChoice?.description || model.description || model.code,
                fromPreset: libraryChoice ? true : false
            };
        });
        return [...described.filter(x => x.fromPreset === true), ...described.filter(x => x.fromPreset !== true)];
    }

    private resetToLibraryChoices(preset: PresetSummary): void {
        preset.modelChoices = (preset.presetChoices || []).map(choice => {
            return { code: choice.code, description: choice.description || choice.code, fromPreset: true };
        });
        preset.presetUnavailable = false;
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
                if (!defaultVendorId) {
                    defaultVendorId = this.autoSettingsConfigurations.length ? this.autoSettingsConfigurations[0].parentModel.vendorId : undefined;
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