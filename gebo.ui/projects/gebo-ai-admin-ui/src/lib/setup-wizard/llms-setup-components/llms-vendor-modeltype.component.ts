import { Component, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { LLMSModelsPresets, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
@Component({
    selector: "gebo-ai-llms-vendor-model-type-config",
    templateUrl: "llms-vendor-modeltype.component.html",
    standalone: false
})
export class GeboAILlmsVendorModelTypeConfig implements OnInit, OnChanges {
    @Input() presets?: LLMSModelsPresets;
    @Input() requiresApiKey: boolean = false;
    loading: boolean = false;
    secrets: SecretInfo[] = [];
    secretFormGroup: FormGroup = new FormGroup({
        useExistingOrNew: new FormControl(),
        selectedSecret: new FormControl(),
        newApiSecret: new FormControl(),
        newUserName: new FormControl()
    });
    useExistingOrNewOptions: { label: string, value: string }[] = [{ label: "Existing credentials", value: "EXISTING" },{ label: "New credentials", value: "NEW" }];
    modelChoiceFormGroup: FormGroup = new FormGroup({
        choosedModel: new FormControl()
    });
    existingOrNewReadOnly: boolean = false;
    constructor(private secretController: SecretsControllerService) {

    }
    ngOnInit(): void {

    }
    reloadSecrets(): void {
        if (this.presets?.apiKeySecretContext) {
            this.loading = true;
            this.secretController.getSecretsByContextCode(this.presets.apiKeySecretContext).subscribe({
                next: (infos) => {
                    if (infos) {
                        this.secrets = infos;
                        if (infos.length) {
                            this.secretFormGroup.controls["selectedSecret"].setValue(infos[0].code);
                            this.existingOrNewReadOnly = false;
                            this.secretFormGroup.controls["useExistingOrNew"].setValue("EXISTING");

                        }

                    }
                    if (!infos || infos.length === 0) {
                        this.secretFormGroup.controls["useExistingOrNew"].setValue("NEW");
                        this.existingOrNewReadOnly = true;
                    }
                },
                complete: () => {
                    this.loading = false;
                }
            });
        }
    }
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["presets"] && this.presets) {
            this.reloadSecrets();
        }
    }

}