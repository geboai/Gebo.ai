import { Component, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { LLMSModelsPresets, LLMSSetupConfiguration, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
@Component({
    selector: "gebo-ai-llms-vendor-model-type-config",
    templateUrl: "llms-vendor-modeltype.component.html",
    standalone: false
})
export class GeboAILlmsVendorModelTypeConfig implements OnInit, OnChanges {
    @Input() vendorConfiguration?: LLMSSetupConfiguration;
    protected loading: boolean = false;
    protected secrets: SecretInfo[] = [];
    protected secretFormGroup: FormGroup = new FormGroup({
        useExistingOrNew: new FormControl(),
        selectedSecret: new FormControl(),
        newApiSecret: new FormControl(),
        newUserName: new FormControl()
    });
    protected useExistingOrNewOptions: { label: string, value: string }[] = [{ label: "Existing credentials", value: "EXISTING" }, { label: "New credentials", value: "NEW" }];
    protected modelChoiceFormGroup: FormGroup = new FormGroup({
        choosedModel: new FormControl()
    });
    protected existingOrNewShow: boolean = false;
    constructor(private secretController: SecretsControllerService) {
        this.secretFormGroup.controls["useExistingOrNew"].setValidators(Validators.required);
        this.secretFormGroup.controls["useExistingOrNew"].valueChanges.subscribe({
            next: (value) => {
                const allCtrls = ["selectedSecret", "newApiSecret", "newUserName"];
                this.switchControlsRequired(allCtrls,false);

                if (value === "EXISTING") {
                    this.switchControlsRequired(["selectedSecret"],true);
                }
                if (value === "NEW") {
                    this.switchControlsRequired(["newApiSecret", "newUserName"],true);
                }
            }
        })
    }
    ngOnInit(): void {

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