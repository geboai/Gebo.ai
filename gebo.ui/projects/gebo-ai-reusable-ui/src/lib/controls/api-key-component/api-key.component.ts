import { Component, forwardRef, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { AbstractControl, ControlValueAccessor, FormControl, FormGroup, NG_VALUE_ACCESSOR, ValidationErrors, ValidatorFn, Validators } from "@angular/forms";
import { GeboAwsConnectionCredentials, GeboGoogleJsonSecretContent, GUserMessage, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
import { Observable, of } from "rxjs";
import { IOperationStatus } from "../base-entity-editing-component/operation-status";
import { ToastMessageOptions } from "primeng/api";
import { GeboActionType, GeboUIActionRequest } from "../../architecture/actions.model";
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
    googleJsonContent?: string,
    googleDelegatedUser?: string
}
/** The kinds of secret whose new credentials are typed in place, in this order of preference. */
const IN_PLACE_KINDS: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.TOKEN, SecretInfo.SecretTypeEnum.AWSCONNECTION, SecretInfo.SecretTypeEnum.GOOGLECLOUDJSONCREDENTIALS];
/** The content of a Google service account key must be json. */
const validJson: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
    if (!control.value) return null;
    try {
        JSON.parse(control.value);
        return null;
    } catch (e) {
        return { "googleJsonContent": "Not valid json" };
    }
};
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
     * The kinds of secret the provider takes; an api key (TOKEN) when not given. The
     * existing secrets of those kinds are offered; new ones are typed in place, as the
     * first kind that can be (an api key, an AWS connection - GeboAwsConnectionCredentials -
     * or a Google service account key), with the fields and checks of the secrets editor.
     */
    @Input() secretTypes?: SecretInfo.SecretTypeEnum[];
    /** The kind new credentials are typed in place as; undefined when none can be (e.g. OAuth2). */
    protected get newKind(): SecretInfo.SecretTypeEnum | undefined {
        const kinds = this.secretTypes?.length ? this.secretTypes : [SecretInfo.SecretTypeEnum.TOKEN];
        return kinds.find(kind => IN_PLACE_KINDS.includes(kind));
    }
    protected readonly kinds = SecretInfo.SecretTypeEnum;
    protected awsRegions: { label: string, value: string }[] = Object.values(GeboAwsConnectionCredentials.RegionEnum)
        .map(region => ({ label: region as string, value: region as string }));
    /**
     * Opens the secrets editor on a new secret of the provider's kinds, only for a
     * provider whose credentials cannot be typed in place (an OAuth2 grant needs its sign in).
     */
    protected newSecretAction?: GeboUIActionRequest;
    private buildNewSecretAction(): void {
        if (this.newKind || !this.secretTypes?.length || !this.contextCode) {
            this.newSecretAction = undefined;
            return;
        }
        const types = this.secretTypes as SecretInfo.SecretTypeEnum[];
        this.newSecretAction = {
            actionType: GeboActionType.NEW,
            contextType: "GeboAIApiKeyComponent",
            context: {},
            targetType: "Secret",
            // the secrets editor's SecretWrapper: the kind and context of the new secret
            target: { secretType: types[0], contextCode: this.contextCode, description: this.apiKeyDescription } as GeboUIActionRequest["target"],
            targetFormInputs: { allowedTypes: types }
        };
    }
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
        googleJsonContent: new FormControl(),
        googleDelegatedUser: new FormControl()
    });
    private static readonly NEW_CONTROLS = ["newApiSecret", "newUserName", "newSecretDescription", "awsAccessKeyId", "awsSecretAccessKey", "awsRegion", "googleJsonContent", "googleDelegatedUser"];
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
                    this.newCredentialsControls().forEach(ctrl => this.secretFormGroup.controls[ctrl].enable());
                } else {
                    this.secretFormGroup.controls["useExistingOrNew"].disable();
                    this.secretFormGroup.controls["selectedSecret"].disable();
                    GeboAIApiKeyComponent.NEW_CONTROLS.forEach(ctrl => this.secretFormGroup.controls[ctrl].disable());
                }
                this.secretFormGroup.updateValueAndValidity();
            }
        });
        this.secretFormGroup.controls["useExistingOrNew"].setValidators(Validators.required);
        this.secretFormGroup.controls["useExistingOrNew"].valueChanges.subscribe({
            next: (value) => {
                if (this.enabled!==true) return;
                const allCtrls = ["selectedSecret", ...GeboAIApiKeyComponent.NEW_CONTROLS];
                this.switchControlsRequired(allCtrls, false);
                this.useExistingOrNew = value;
                if (value === "EXISTING") {
                    this.switchControlsRequired(["selectedSecret"], true);
                    if (this.onChange) this.onChange(this.secretFormGroup.controls["selectedSecret"].value);
                }
                if (value === "NEW") {
                    this.switchControlsRequired(this.newCredentialsControls(), true);
                    if (this.newKind === SecretInfo.SecretTypeEnum.GOOGLECLOUDJSONCREDENTIALS) {
                        this.secretFormGroup.controls["googleJsonContent"].addValidators(validJson);
                    }
                    if (this.newKind === SecretInfo.SecretTypeEnum.AWSCONNECTION) {
                        this.secretFormGroup.controls["awsAccessKeyId"].addValidators(Validators.maxLength(128));
                        this.secretFormGroup.controls["awsSecretAccessKey"].addValidators(Validators.maxLength(128));
                    }
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
                this.saveDisabled = !(value?.useExistingOrNew === "NEW" && this.secretFormGroup.valid && this.newCredentialsComplete(value));
            }
        });
    }
    /**
     * The controls new credentials of the kind typed in place need - the secrets editor's
     * fields of that kind, all required as the backend validates them (an AWS connection's
     * key id, secret key and region; a service account key's json and delegated user).
     */
    private newCredentialsControls(): string[] {
        switch (this.newKind) {
            case SecretInfo.SecretTypeEnum.AWSCONNECTION: return ["awsAccessKeyId", "awsSecretAccessKey", "awsRegion", "newSecretDescription"];
            case SecretInfo.SecretTypeEnum.GOOGLECLOUDJSONCREDENTIALS: return ["googleJsonContent", "googleDelegatedUser", "newSecretDescription"];
            case SecretInfo.SecretTypeEnum.TOKEN: return ["newApiSecret", "newUserName", "newSecretDescription"];
            default: return [];
        }
    }
    private newCredentialsComplete(value: ApiKeyInternalFG): boolean {
        switch (this.newKind) {
            case SecretInfo.SecretTypeEnum.AWSCONNECTION: return !!(value?.awsAccessKeyId && value?.awsSecretAccessKey && value?.awsRegion);
            case SecretInfo.SecretTypeEnum.GOOGLECLOUDJSONCREDENTIALS: return !!(value?.googleJsonContent && value?.googleDelegatedUser);
            case SecretInfo.SecretTypeEnum.TOKEN: return !!(value?.newUserName && value?.newApiSecret);
            default: return false;
        }
    }
    /** Stores the new credentials as the kind typed in place. */
    private storeNewCredentials(value: ApiKeyInternalFG): Observable<SecretInfo> | undefined {
        if (!this.contextCode || !this.apiKeyDescription || !this.newCredentialsComplete(value)) return undefined;
        const description = value.newSecretDescription || this.apiKeyDescription;
        switch (this.newKind) {
            case SecretInfo.SecretTypeEnum.AWSCONNECTION:
                return this.secretController.createAWSConnectionSecret({
                    contextCode: this.contextCode,
                    description: description,
                    secretContent: {
                        accessKeyId: value.awsAccessKeyId as string,
                        secretAccessKey: value.awsSecretAccessKey as string,
                        region: value.awsRegion as GeboAwsConnectionCredentials.RegionEnum
                    }
                });
            case SecretInfo.SecretTypeEnum.GOOGLECLOUDJSONCREDENTIALS:
                return this.secretController.createGoogleJsonCredentialsSecret({
                    contextCode: this.contextCode,
                    description: description,
                    secretContent: { jsonContent: value.googleJsonContent, delegatedUser: value.googleDelegatedUser } as GeboGoogleJsonSecretContent
                });
            case SecretInfo.SecretTypeEnum.TOKEN:
                return this.secretController.createTokenSecret({
                    contextCode: this.contextCode,
                    description: this.apiKeyDescription,
                    secretContent: {
                        token: value.newApiSecret as string,
                        user: value.newUserName as string
                    }
                });
            default:
                return undefined;
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
                                    awsRegion: undefined,
                                    googleJsonContent: undefined,
                                    googleDelegatedUser: undefined,
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
        if (changes["contextCode"] || changes["secretTypes"]) {
            this.buildNewSecretAction();
            const resetValue: ApiKeyInternalFG = {
                requireApiKeyAniway: this.enabled,
                newApiSecret: undefined,
                newUserName: undefined,
                selectedSecret: undefined,
                newSecretDescription: undefined,
                useExistingOrNew: !this.newKind ? "EXISTING" : "NEW"
            };
            this.secretFormGroup.patchValue(resetValue);
            if (this.onChange) {
                this.onChange(undefined);
            }
            if (this.contextCode) {
                console.log("Current api key context:" + this.contextCode);
                this.loadingBackend = true;
                this.secrets = [];
                this.secretFormGroup.controls["useExistingOrNew"].setValue(!this.newKind ? "EXISTING" : "NEW");
                this.secretController.getSecretsByContextCode(this.contextCode).subscribe({
                    next: (secrets) => {
                        this.loadingBackend = false;
                        // the secrets of the kinds the provider takes
                        const kinds = this.secretTypes?.length ? this.secretTypes : [SecretInfo.SecretTypeEnum.TOKEN];
                        this.secrets = (secrets ?? []).filter(x => !x.secretType || kinds.includes(x.secretType));

                        let secretsOrNew: "EXISTING" | "NEW" = "NEW";
                        this.existingOrNewShow = true;
                        if (this.secrets?.length > 0 || !this.newKind) {
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
        if (obj || !this.newKind) {
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