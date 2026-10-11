import { Component, forwardRef, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { ControlValueAccessor, FormControl, FormGroup, NG_VALUE_ACCESSOR, Validators } from "@angular/forms";
import { GeboAwsConnectionCredentials, GeboGoogleJsonSecretContent, GUserMessage, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
import { Observable, of } from "rxjs";
import { IOperationStatus } from "../base-entity-editing-component/operation-status";
import { ToastMessageOptions } from "primeng/api";
const credentialCreatedOk:ToastMessageOptions={id:"APIKEY_CREATED_OK",severity:"success",detail:"Credentials successfully created",summary:"Credentials created"};
interface ApiKeyInternalFG {
    requireApiKeyAniway?: boolean,
    newSecretDescription?: string,
    useExistingOrNew?: "EXISTING" | "NEW",
    selectedSecret?: string,
    newApiSecret?: string,
    newUserName?: string,
    awsAccessKeyId?: string,
    awsSecretAccessKey?: string,
    awsRegion?: string,
    googleJsonContent?: string
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
    /**
     * The kind of credentials the provider takes: an api key (TOKEN, the default), an AWS
     * access key with its region (AWS_CONNECTION) or a Google service account key
     * (GOOGLE_CLOUD_JSON_CREDENTIALS). Only the existing credentials of that kind are
     * offered, and new ones are created of that kind.
     */
    @Input() secretType?: SecretInfo.SecretTypeEnum;
    protected get actualSecretType(): SecretInfo.SecretTypeEnum {
        return this.secretType ?? SecretInfo.SecretTypeEnum.TOKEN;
    }
    protected awsRegions: { label: string, value: string }[] = Object.values(GeboAwsConnectionCredentials.RegionEnum)
        .map(region => ({ label: region as string, value: region as string }));
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
        awsAccessKeyId: new FormControl(),
        awsSecretAccessKey: new FormControl(),
        awsRegion: new FormControl(),
        googleJsonContent: new FormControl()
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
    protected enabled:boolean=true;
    constructor(private secretController: SecretsControllerService) {
        this.secretFormGroup.controls["requireApiKeyAniway"].valueChanges.subscribe({
            next: (value) => {
                if (value === true) {
                    this.secretFormGroup.controls["useExistingOrNew"].enable();
                    this.secretFormGroup.controls["selectedSecret"].enable();
                    this.secretFormGroup.controls["newApiSecret"].enable();
                    this.secretFormGroup.controls["newUserName"].enable();
                    this.secretFormGroup.controls["newSecretDescription"].enable();
                } else {
                    this.secretFormGroup.controls["useExistingOrNew"].disable();
                    this.secretFormGroup.controls["selectedSecret"].disable();
                    this.secretFormGroup.controls["newApiSecret"].disable();
                    this.secretFormGroup.controls["newUserName"].disable();
                    this.secretFormGroup.controls["newSecretDescription"].disable();
                }
                this.secretFormGroup.updateValueAndValidity();
            }
        });
        this.secretFormGroup.controls["useExistingOrNew"].setValidators(Validators.required);
        this.secretFormGroup.controls["useExistingOrNew"].valueChanges.subscribe({
            next: (value) => {
                if (this.enabled!==true) return;
                const allCtrls = ["selectedSecret", "newApiSecret", "newUserName", "newSecretDescription", "awsAccessKeyId", "awsSecretAccessKey", "awsRegion", "googleJsonContent"];
                this.switchControlsRequired(allCtrls, false);
                this.useExistingOrNew = value;
                if (value === "EXISTING") {
                    this.switchControlsRequired(["selectedSecret"], true);
                    if (this.onChange) this.onChange(this.secretFormGroup.controls["selectedSecret"].value);
                }
                if (value === "NEW") {
                    this.switchControlsRequired(this.newCredentialsControls(), true);
                    if (this.onChange) this.onChange(undefined);
                }
            }
        });
        this.secretFormGroup.controls["selectedSecret"].valueChanges.subscribe({
            next: (secretId) => {
                if (this.enabled!==true) return;
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
                this.saveDisabled = !(value?.useExistingOrNew === "NEW" && this.newCredentialsComplete(value));
            }
        });
    }
    /** The controls a new credential of the actual kind needs. */
    private newCredentialsControls(): string[] {
        switch (this.actualSecretType) {
            case SecretInfo.SecretTypeEnum.AWSCONNECTION: return ["awsAccessKeyId", "awsSecretAccessKey", "awsRegion", "newSecretDescription"];
            case SecretInfo.SecretTypeEnum.GOOGLECLOUDJSONCREDENTIALS: return ["googleJsonContent", "newSecretDescription"];
            default: return ["newApiSecret", "newUserName", "newSecretDescription"];
        }
    }
    private newCredentialsComplete(value: ApiKeyInternalFG): boolean {
        switch (this.actualSecretType) {
            case SecretInfo.SecretTypeEnum.AWSCONNECTION: return !!(value?.awsAccessKeyId && value?.awsSecretAccessKey && value?.awsRegion);
            case SecretInfo.SecretTypeEnum.GOOGLECLOUDJSONCREDENTIALS: return !!value?.googleJsonContent;
            default: return !!(value?.newUserName && value?.newApiSecret);
        }
    }
    /** Stores the new credential as the kind the provider takes. */
    private storeNewCredentials(value: ApiKeyInternalFG): Observable<SecretInfo> | undefined {
        if (!this.contextCode || !this.apiKeyDescription) return undefined;
        const description = value?.newSecretDescription || this.apiKeyDescription;
        switch (this.actualSecretType) {
            case SecretInfo.SecretTypeEnum.AWSCONNECTION:
                if (!value?.awsAccessKeyId || !value?.awsSecretAccessKey || !value?.awsRegion) return undefined;
                return this.secretController.createAWSConnectionSecret({
                    contextCode: this.contextCode,
                    description: description,
                    secretContent: {
                        accessKeyId: value.awsAccessKeyId,
                        secretAccessKey: value.awsSecretAccessKey,
                        region: value.awsRegion as GeboAwsConnectionCredentials.RegionEnum
                    }
                });
            case SecretInfo.SecretTypeEnum.GOOGLECLOUDJSONCREDENTIALS:
                if (!value?.googleJsonContent) return undefined;
                return this.secretController.createGoogleJsonCredentialsSecret({
                    contextCode: this.contextCode,
                    description: description,
                    secretContent: { jsonContent: value.googleJsonContent } as GeboGoogleJsonSecretContent
                });
            default:
                if (!value?.newApiSecret || !value?.newUserName) return undefined;
                return this.secretController.createTokenSecret({
                    contextCode: this.contextCode,
                    description: this.apiKeyDescription,
                    secretContent: {
                        token: value.newApiSecret,
                        user: value.newUserName
                    }
                });
        }
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
        const storing = this.storeNewCredentials(value);
        if (storing) {
            this.loadingBackend = true;
            storing.subscribe({
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
                                    awsAccessKeyId: undefined,
                                    awsSecretAccessKey: undefined,
                                    googleJsonContent: undefined,
                                    selectedSecret: secret?.code,
                                    useExistingOrNew: "EXISTING"
                                };
                                this.secretFormGroup.patchValue(newValue);
                                this.secretId = secret?.code;
                                if (this.onChange)
                                    this.onChange(this.secretId);
                                this.messages=[credentialCreatedOk as GUserMessage];
                            } else {
                                this.messages = value.messages;
                                this.deleting = true;
                                this.secretController.deleteSecret(secret).subscribe({
                                    next: () => {

                                    },
                                    error: () => {
                                        this.deleting = false;
                                    },
                                    complete: () => {
                                        this.deleting = false;
                                    }
                                });
                            }


                        },
                        error: () => {
                            this.validating = false;
                        },
                        complete: () => {
                            this.validating = false;
                        }
                    })

                },
                error: () => {
                    this.loadingBackend = false;
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
        if (changes["contextCode"] || changes["secretType"]) {
            const resetValue: ApiKeyInternalFG = {
                requireApiKeyAniway: this.enabled,
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
                        // only the credentials of the kind the provider takes
                        this.secrets = (secrets ?? []).filter(x => !x.secretType || x.secretType === this.actualSecretType);

                        let secretsOrNew: "EXISTING" | "NEW" = "NEW";
                        this.existingOrNewShow = true;
                        if (this.secrets?.length > 0) {
                            secretsOrNew = "EXISTING";
                        } else {
                            this.existingOrNewShow = false;
                        }
                        this.secretId = this.secrets.length ? this.secrets[0].code : undefined;
                        const newValue: ApiKeyInternalFG = {
                            requireApiKeyAniway: this.enabled,
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
                    error: () => {
                        this.loadingBackend = false;
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
        this.enabled=!isDisabled;
        this.secretFormGroup.controls["requireApiKeyAniway"].setValue(!isDisabled);
        if (this.enabled) {
            const secretsOrNew=this.secretFormGroup.controls["useExistingOrNew"].value;
            this.secretFormGroup.controls["useExistingOrNew"].setValue(secretsOrNew);
        }
    }
}