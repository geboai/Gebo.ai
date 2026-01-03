import { Component, forwardRef, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { ControlValueAccessor, FormControl, FormGroup, NG_VALUE_ACCESSOR, Validators } from "@angular/forms";
import { GUserMessage, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
import { Observable, of } from "rxjs";
import { IOperationStatus } from "../base-entity-editing-component/operation-status";

interface ApiKeyInternalFG {
    requireApiKeyAniway?: boolean,
    newSecretDescription?: string,
    useExistingOrNew?: "EXISTING" | "NEW",
    selectedSecret?: string,
    newApiSecret?: string,
    newUserName?: string
}
@Component({
    selector: "gebo-ai-api-key-component",
    templateUrl: "api-key.component.html",
    standalone: false,
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboAIApiKeyComponent),
            multi: true
        }, { provide: GEBO_AI_MODULE, useValue: "GeboAIApiKeyModule", multi: false }, { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIApiKeyComponent") }
    ],
})
export class GeboAIApiKeyComponent implements OnInit, OnChanges, ControlValueAccessor {
    @Input({ required: true }) contextCode?: string;
    @Input({ required: true }) apiKeyDescription?: string;
    @Input() backendValidationOnChange: boolean = true;
    @Input() backendValidation: (credentials: SecretInfo) => Observable<IOperationStatus<any>> = (credentials: SecretInfo) => {
        const data: IOperationStatus<any> = {
            hasErrorMessages: false,
            hasWarnMessages: false,
            messages: []
        }
        return of(data);
    }
    protected secretFormGroup: FormGroup = new FormGroup({
        requireApiKeyAniway: new FormControl(),
        useExistingOrNew: new FormControl(),
        selectedSecret: new FormControl(),
        newSecretDescription: new FormControl(),
        newApiSecret: new FormControl(),
        newUserName: new FormControl(),
        baseUrl: new FormControl()
    });
    protected messages?: GUserMessage[] = [];
    protected view: "COMPACT" | "FULL" = "COMPACT";
    protected existingOrNewShow: boolean = true;
    protected saveDisabled: boolean = false;
    protected get loading(): boolean {
        return this.loadingBackend || this.validating || this.deleting;
    }
    protected loadingBackend: boolean = false;
    protected validating: boolean = false;
    protected deleting: boolean = false;
    protected secrets: SecretInfo[] = [];
    protected secretId?: string;
    protected useExistingOrNew: "EXISTING" | "NEW" = "NEW";
    protected useExistingOrNewOptions: { label: string, value: string }[] = [{ label: "Existing credentials", value: "EXISTING" }, { label: "New credentials", value: "NEW" }];
    constructor(private secretController: SecretsControllerService) {
        this.secretFormGroup.controls["requireApiKeyAniway"].valueChanges.subscribe({
            next: (value) => {

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
                const allCtrls = ["selectedSecret", "newApiSecret", "newUserName", "newSecretDescription"];
                this.switchControlsRequired(allCtrls, false);
                this.useExistingOrNew = value;
                if (value === "EXISTING") {
                    this.switchControlsRequired(["selectedSecret"], true);
                    if (this.onChange) this.onChange(this.secretFormGroup.controls["selectedSecret"].value);
                }
                if (value === "NEW") {
                    this.switchControlsRequired(["newApiSecret", "newUserName", "newSecretDescription"], true);
                    if (this.onChange) this.onChange(undefined);
                }
            }
        });
        this.secretFormGroup.controls["selectedSecret"].valueChanges.subscribe({
            next: (secretId) => {
                if (this.secretId != secretId) {
                    if (this.backendValidationOnChange === true && secretId) {
                        const credential = this.secrets.find(x => x.code === secretId);
                        if (credential) {
                            this.validating = true;
                            this.backendValidation(credential).subscribe({
                                next: (value) => {
                                    this.validating = false;
                                    if (value?.hasErrorMessages === true) {
                                        this.messages = value.messages;
                                    } else {
                                        if (this.onChange) {
                                            this.secretId = secretId;
                                            this.onChange(secretId);
                                        }
                                    }
                                },
                                error: (err) => {
                                    this.validating = false;
                                },
                                complete: () => {
                                    this.validating = false;
                                }
                            })
                        }
                    } else {
                        if (this.onChange) {
                            this.secretId = secretId;
                            this.onChange(secretId);
                        }
                    }
                }
            }
        });
        this.secretFormGroup.valueChanges.subscribe({
            next: (value: ApiKeyInternalFG) => {
                this.saveDisabled = !(value?.useExistingOrNew === "NEW" && value?.newUserName && value?.newApiSecret);
            }
        });
    }
    private switchControlsRequired(ctrls: string[], required: boolean) {
        ctrls.forEach(ctrName => {
            this.secretFormGroup.controls[ctrName].clearValidators();
            this.secretFormGroup.controls[ctrName].disable();
            if (required === true) {
                this.secretFormGroup.controls[ctrName].enable();
                this.secretFormGroup.controls[ctrName].addValidators(Validators.required);
            }
        });
    }
    protected createCredentials(): void {
        const value: ApiKeyInternalFG = this.secretFormGroup.value;
        if (value?.newApiSecret && value?.newUserName && this.contextCode && this.apiKeyDescription) {
            this.loadingBackend = true;
            this.secretController.createTokenSecret({
                contextCode: this.contextCode,
                description: this.apiKeyDescription,
                secretContent: {
                    token: value.newApiSecret,
                    user: value.newUserName
                }
            }).subscribe({
                next: (secret: SecretInfo) => {
                    this.validating = true;
                    this.backendValidation(secret).subscribe({
                        next: (value: IOperationStatus<any>) => {
                            if (value.hasErrorMessages !== true) {
                                this.secrets = [secret, ...this.secrets];
                                this.existingOrNewShow = true;
                                const newValue: ApiKeyInternalFG = {
                                    requireApiKeyAniway: true,
                                    newApiSecret: undefined,
                                    newUserName: undefined,
                                    selectedSecret: secret?.code,
                                    useExistingOrNew: "EXISTING"
                                };
                                this.secretFormGroup.patchValue(newValue);
                                this.secretId = secret?.code;
                                if (this.onChange)
                                    this.onChange(this.secretId);
                            } else {
                                this.messages = value.messages;
                                this.deleting = true;
                                this.secretController.deleteSecret(secret).subscribe({
                                    next: () => {

                                    },
                                    complete: () => {
                                        this.deleting = false;
                                    }
                                });
                            }


                        },
                        complete: () => {
                            this.validating = false;
                        }
                    })

                },
                complete: () => {
                    this.loadingBackend = false;
                }
            })
        }
    }
    ngOnInit(): void {

    }
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["contextCode"]) {
            const resetValue: ApiKeyInternalFG = {
                requireApiKeyAniway: true,
                newApiSecret: undefined,
                newUserName: undefined,
                selectedSecret: undefined,
                newSecretDescription: undefined,
                useExistingOrNew: "NEW"
            };
            this.secretFormGroup.patchValue(resetValue);
            if (this.onChange) {
                this.onChange(undefined);
            }
            if (this.contextCode) {
                console.log("Current api key context:" + this.contextCode);
                this.loadingBackend = true;
                this.secrets = [];
                this.secretFormGroup.controls["useExistingOrNew"].setValue("NEW");
                this.secretController.getSecretsByContextCode(this.contextCode).subscribe({
                    next: (secrets) => {
                        this.loadingBackend = false;
                        this.secrets = secrets;

                        let secretsOrNew: "EXISTING" | "NEW" = "NEW";
                        this.existingOrNewShow = true;
                        if (this.secrets?.length > 0) {
                            secretsOrNew = "EXISTING";
                        } else {
                            this.existingOrNewShow = false;
                        }
                        this.secretId = secrets && secrets.length ? secrets[0].code : undefined;
                        const newValue: ApiKeyInternalFG = {
                            requireApiKeyAniway: true,
                            newApiSecret: undefined,
                            newUserName: undefined,
                            selectedSecret: this.secretId,
                            newSecretDescription: this.apiKeyDescription,
                            useExistingOrNew: secretsOrNew
                        };
                        this.secretFormGroup.patchValue(newValue);
                        if (this.onChange) {
                            this.onChange(this.secretId);
                        }
                    },
                    complete: () => {
                        this.loadingBackend = false;
                    }
                });
            }
        }
    }
    writeValue(obj: any): void {
        this.secretId = obj;
        let secretsOrNew: "EXISTING" | "NEW" = "NEW";
        if (obj) {
            secretsOrNew = "EXISTING";
        }
        this.secretFormGroup.controls["useExistingOrNew"].setValue(secretsOrNew);
        this.secretFormGroup.controls["selectedSecret"].setValue(obj);
    }
    private onChange?: (fn: any) => void;
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }
    private onTouch?: (fn: any) => void;
    registerOnTouched(fn: any): void {
        this.onTouch = fn;
    }
    setDisabledState?(isDisabled: boolean): void {
        this.secretFormGroup.controls["requireApiKeyAniway"].setValue(!isDisabled);
    }
}