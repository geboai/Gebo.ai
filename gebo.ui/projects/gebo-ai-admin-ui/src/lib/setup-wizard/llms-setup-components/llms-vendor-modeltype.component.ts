import { Component, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { LLMSSetupConfiguration, SecretInfo, SecretsControllerService, GeboFastLlmsSetupControllerService, LLMModelPresetChoice, GBaseModelChoice, LLMCredentials } from "@Gebo.ai/gebo-ai-rest-api";
import { IOperationStatus } from "@Gebo.ai/reusable-ui";
import { ToastMessageOptions } from "primeng/api";
import { forkJoin, Observable } from "rxjs";

@Component({
    selector: "gebo-ai-llms-vendor-model-type-config",
    templateUrl: "llms-vendor-modeltype.component.html",
    standalone: false
})
export class GeboAILlmsVendorModelTypeConfig implements OnInit, OnChanges {
    @Input() vendorConfiguration?: LLMSSetupConfiguration;
    protected userMessages: ToastMessageOptions[] = [];
    protected loading: boolean = false;
    protected secrets: SecretInfo[] = [];
    protected secretFormGroup: FormGroup = new FormGroup({
        useExistingOrNew: new FormControl(),
        selectedSecret: new FormControl(),
        newApiSecret: new FormControl(),
        newUserName: new FormControl(),
        baseUrl: new FormControl()
    });
    protected chatModelPresetsFormGroup:FormGroup=new FormGroup({
        choosedModel:new FormControl()
    });
    protected embeddingModelPresetsFormGroup:FormGroup=new FormGroup({
        choosedModel:new FormControl()
    });
    protected chatModelAdvancedFormGroup:FormGroup=new FormGroup({
        choosedModel:new FormControl()
    });
    protected embeddingModelAdvancedFormGroup:FormGroup=new FormGroup({
        choosedModel:new FormControl()
    });
    protected useExistingOrNewOptions: { label: string, value: string }[] = [{ label: "Existing credentials", value: "EXISTING" }, { label: "New credentials", value: "NEW" }];
    protected chatPresets: LLMModelPresetChoice[] = [];
    protected embeddingPresets: LLMModelPresetChoice[] = [];
    protected lookedUpChatModels: GBaseModelChoice[] = [];
    protected lookedUpEmbeddingModels: GBaseModelChoice[] = [];
    protected existingOrNewShow: boolean = false;
    private oldCredentialId?: string;
    private oldBaseUrl?: string;
    constructor(private secretController: SecretsControllerService, private geboFastLLMSSetupService: GeboFastLlmsSetupControllerService) {
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
                    if (secretId !== this.oldCredentialId || baseUrl !== this.oldCredentialId) {

                        this.loadModels(secretId, baseUrl);
                        this.oldCredentialId = secretId;
                        this.oldBaseUrl = baseUrl;
                    }
                }
            }
        });
    }
    private loadModels(secretId?: string, baseUrl?: string) {

        if (!secretId && this.vendorConfiguration?.parentModel.requiresApiKey === true) {
            this.lookedUpChatModels = [];
            this.lookedUpEmbeddingModels = [];
        } else {
            const observables: Observable<IOperationStatus<GBaseModelChoice[]>>[] = [];
            this.vendorConfiguration?.libraryModel.forEach(x => {
                const credentials: any = {
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
                    let toastMessages: ToastMessageOptions[] = [];
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
                },
                complete: () => {
                    this.loading = false;
                }
            })
        }

    }
    ngOnInit(): void {

    }
    protected createCredentials(): void {
        const credentials = this.secretFormGroup.value;
        if (this.vendorConfiguration?.parentModel.apiKeySecretContext && this.vendorConfiguration.libraryModel[0].serviceHandler && this.vendorConfiguration.libraryModel[0].type) {
            this.loading = true;
            this.geboFastLLMSSetupService.createLLMCredentials({
                apiKeySecretContext: this.vendorConfiguration?.parentModel.apiKeySecretContext,
                newApiSecret: credentials.newApiSecret,
                newUserName: credentials.newApiSecret.newUserName,
                serviceHandler: this.vendorConfiguration.libraryModel[0].serviceHandler,
                type: this.vendorConfiguration.libraryModel[0].type,
                baseUrl: credentials.baseUrl,
                doModelsLookup: this.vendorConfiguration.libraryModel[0].doModelsLookup

            }).subscribe({
                next: (value) => {
                    this.userMessages = value.messages as ToastMessageOptions[];
                    if (value.hasErrorMessages !== true && value.result) {
                        this.secrets = [...this.secrets, value.result];
                        this.secretFormGroup.controls["useExistingOrNew"].setValue("EXISTING");
                        this.secretFormGroup.controls["selectedSecret"].setValue(value.result.code);
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
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["vendorConfiguration"] && this.vendorConfiguration) {
            this.vendorConfiguration.libraryModel.forEach(x => {
                if (x.type)
                    switch (x.type) {
                        case "CHAT": {
                            this.chatPresets = x.choices ? x.choices : [];
                        } break;
                        case "EMBEDDING": {
                            this.embeddingPresets = x.choices ? x.choices : [];
                        } break;
                    }
            });
            this.reloadSecrets();
        }
    }

}