import { Component, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { GeboFastLlmsSetupControllerService, LLMModelPresetChoice, LLMSModelsPresets, LLMSSetupConfiguration, LLMSVendorInfo, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboAITranslationService } from "../../../../../gebo-ai-reusable-ui/src/lib/controls/field-translation-container/gebo-translation.service";
import { FormControl, FormGroup } from "@angular/forms";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../../../../../gebo-ai-reusable-ui/src/lib/controls/field-host-component-iface/field-host-component-iface";
interface PresetSummary {
    type: LLMSModelsPresets.TypeEnum;
    mainUse?: LLMModelPresetChoice.UsesEnum;
    presetChoices?: LLMModelPresetChoice[];
    choice?: string;
}
@Component({
    selector: "gebo-ai-llms-easy-vendor-configuration-component",
    templateUrl: "easy-vendor-configuration.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboSetupWizardsModule", multi: false }, { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIEasyVendorConfigurationComponent"), multi: false }]
})
export class GeboAIEasyVendorConfigurationComponent implements OnInit, OnChanges {
    @Input() autoSettingsConfigurations?: LLMSSetupConfiguration[] = [];
    protected choosableVendors: LLMSVendorInfo[] = [];
    protected vendorSetupMetaInfos?: LLMSSetupConfiguration;
    protected choosableIdentities: SecretInfo[] = [];
    protected presetsSummary: PresetSummary[] = [];
    protected formGroup: FormGroup = new FormGroup({
        vendorId: new FormControl(),
        secretId: new FormControl(),
        defaultChatModel: new FormControl(),
        internalServicesModel: new FormControl(),
        embeddingModel: new FormControl()
    });
    constructor(private secretController: SecretsControllerService,
        private geboFastLLMSSetupService: GeboFastLlmsSetupControllerService,
        private geboAITranslationService: GeboAITranslationService) {
        this.formGroup.controls["vendorId"].valueChanges.subscribe({
            next: (vendorId) => {
                this.vendorSetupMetaInfos = undefined;
                if (vendorId) {
                    this.vendorSetupMetaInfos = this.autoSettingsConfigurations?.find(x => x.parentModel.vendorId === vendorId);
                    const secrets: SecretInfo[] = [];
                    if (this.vendorSetupMetaInfos) {
                        const presets: PresetSummary[] = [];
                        const chatModels = this.vendorSetupMetaInfos.libraryModel.filter(x => x.type === "CHAT");
                        if (chatModels.length) {
                            const models = chatModels[0];
                            const defaultPreset = models.choices?.find(x => x.defaultChoice === true);
                            const chatpresets = models.choices?.filter(x => x.uses && ('CHAT' in x.uses));
                            const defaultChoice: string | undefined = (defaultPreset ? (defaultPreset.code as string) : undefined);
                            if (defaultPreset)
                                presets.push({
                                    type: "CHAT",
                                    choice: defaultChoice,
                                    presetChoices: chatpresets,
                                    mainUse: "CHAT"
                                });
                            this.formGroup.controls["defaultChatModel"].setValue(defaultChoice);
                            const internalServicesChoices = models.choices?.filter(x => x.uses && x.uses.find(y => y === "INTERNAL_SERVICES"));
                            if (internalServicesChoices && internalServicesChoices.length > 0) {
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
                            if (defaultEmbeddingPreset) {
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
                        this.choosableIdentities = secrets;
                        if (this.choosableIdentities && this.choosableIdentities.length > 0) {
                            this.formGroup.controls["secretId"].setValue(this.choosableIdentities[0].code);
                        }
                    }
                }
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

    }
}