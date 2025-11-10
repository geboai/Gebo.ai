import { Component, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { LLMSSetupConfiguration, SecretInfo, SecretsControllerService, GeboFastLlmsSetupControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { ToastMessageOptions } from "primeng/api";

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
    protected useExistingOrNewOptions: { label: string, value: string }[] = [{ label: "Existing credentials", value: "EXISTING" }, { label: "New credentials", value: "NEW" }];
    protected modelChoiceFormGroup: FormGroup = new FormGroup({
        choosedModel: new FormControl()
    });
    protected existingOrNewShow: boolean = false;
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
        this.secretFormGroup.controls["selectedSecret"].valueChanges.subscribe ({
            next:(secretId)=>{
                if (secretId) {
                    
                }
            }
        });
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
                    this.userMessages=value.messages as ToastMessageOptions[];
                    if (value.hasErrorMessages!==true && value.result) {
                        this.secrets=[...this.secrets,value.result];
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
            this.reloadSecrets();
        }
    }

}