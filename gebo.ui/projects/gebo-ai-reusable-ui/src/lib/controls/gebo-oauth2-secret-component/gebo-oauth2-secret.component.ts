import { Component, forwardRef, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { AbstractControl, ControlValueAccessor, FormControl, FormGroup, NG_VALIDATORS, NG_VALUE_ACCESSOR, ValidationErrors, Validator, Validators } from "@angular/forms";
import { AuthProviderDto, GeboOauth2SecretContent, OAuth2AdminControllerService, Oauth2CustomAttribute, OAuth2ProvidersControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { Subscription } from "rxjs";
@Component({
    templateUrl: "gebo-oauth2-secret.component.html",
    selector: "gebo-ui-oauth2-secret-component",
    standalone: false,
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboOauth2SecretComponent),
            multi: true
        },
        {
            provide: NG_VALIDATORS,
            useExisting: forwardRef(() => GeboOauth2SecretComponent),
            multi: true
        }
    ]
})
export class GeboOauth2SecretComponent implements OnInit, OnChanges, ControlValueAccessor, Validator {

    protected formGroup: FormGroup = new FormGroup({
        providerName: new FormControl(null, Validators.required),
        clientId: new FormControl(null, Validators.required),
        secret: new FormControl(null, Validators.required),
        scopes: new FormControl(),
        customAttributes: new FormGroup({})
    });
    @Input() public hideProviderChoice: boolean = false;
    @Input() public forcedProviderName?: string;
    @Input() oauth2ChoosableScopes: { code: string, description: string }[] = [];
    @Input() oauth2MandatoryScopes: { code: string, description: string }[] = [];
    private oldProviderName?: string;
    private value?: GeboOauth2SecretContent;
    protected loading: boolean = false;
    protected providers: AuthProviderDto[] = [];
    protected providersData: { code: string, description: string }[] = [];
    protected actualEditableCustomAttributes: Oauth2CustomAttribute[] = [];
    private formGroupChangeValueSubscription?: Subscription;
    protected actualProvider?: AuthProviderDto;
    constructor(private oAuth2AdminControllerService: OAuth2AdminControllerService) {

        this.formGroup.controls["providerName"].valueChanges.subscribe({
            next: (providerName) => {
                this.restructureFormGroup(providerName);
            }
        });
    }
    validate(control: AbstractControl): ValidationErrors | null {
        // Not ready yet → parent gets "notValidated"
        if (this.loading) return { notValidated: true };

        if (!this.actualProvider) {
            // If providerName is required but not chosen or not found in catalog yet
            const pn = this.formGroup.get('providerName')?.value;
            if (!pn) return { required: { field: 'providerName' } };
            return { notValidated: true };
        }

        // Inner form invalid → bubble up a meaningful error
        if (this.formGroup.invalid) {
            // Option A (simple): report a generic required error
            // return { required: true };

            // Option B (explicit): list missing fields (including dynamic ones)
            const missing: string[] = [];
            const addIfReq = (name: string) => {
                const c = this.formGroup.get(name);
                if (c && c.hasError('required')) missing.push(name);
            };
            addIfReq('providerName');
            addIfReq('clientId');
            addIfReq('secret');

            const cattrs = this.formGroup.get('customAttributes') as FormGroup;
            Object.keys(cattrs.controls).forEach(k => {
                if (cattrs.get(k)?.hasError('required')) missing.push(`customAttributes.${k}`);
            });

            return missing.length ? { requiredFields: missing } : { invalid: true };
        }

        // All good → no error on parent control
        return null;
    }


    ngOnInit(): void {
        this.loading = true;
        this.oAuth2AdminControllerService.getProviders().subscribe({
            next: (providers) => {
                this.providers = providers;
                this.providersData = providers.map(x => {
                    const d: { code: string, description: string } = {
                        code: x.provider, description: x.description
                    };
                    return d;
                });
                if (this.forcedProviderName) {
                    this.restructureFormGroup(this.forcedProviderName);
                } else {
                    if (this.value?.providerName)
                        this.restructureFormGroup(this.value?.providerName);
                }
            },
            complete: () => {
                this.loading = false;
                this.triggerRevalidate();
            }
        });
    }
    private validatorChange?: () => void;
    registerOnValidatorChange(fn: () => void): void {
        this.validatorChange = fn;
    }
    private triggerRevalidate() {
        this.validatorChange?.();
    }
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["forcedProviderName"] && this.forcedProviderName) {
            this.restructureFormGroup(this.forcedProviderName);
            this.triggerRevalidate();
        }
    }
    writeValue(obj: any): void {
        this.value = obj;
        if (this.value?.providerName) {
            this.restructureFormGroup(this.value?.providerName);
        }
        this.triggerRevalidate();
    }
    restructureFormGroup(providerName: string) {
        if (this.oldProviderName !== providerName && this.providers && this.providers.length) {
            if (this.formGroupChangeValueSubscription) {
                this.formGroupChangeValueSubscription.unsubscribe();
                this.formGroupChangeValueSubscription = undefined;
            }
            const customAttributes: FormGroup = this.formGroup.controls["customAttributes"] as FormGroup;
            const keys = [...Object.keys(customAttributes.controls)];
            keys.forEach(fld => {
                customAttributes.removeControl(fld);
            });
            customAttributes.updateValueAndValidity({ emitEvent: false });
            this.actualProvider = this.providers.find(x => x.provider === providerName);
            const currentCustomAttributes = this.actualProvider?.customAttributes;
            this.actualEditableCustomAttributes = currentCustomAttributes ? currentCustomAttributes : [];
            this.actualEditableCustomAttributes.forEach(customAttribute => {
                const fc = new FormControl();
                fc.setValidators(Validators.required);
                if (customAttribute.attributeName)
                    customAttributes.addControl(customAttribute.attributeName, fc);
            });
            customAttributes.updateValueAndValidity({ emitEvent: false });
            this.formGroupChangeValueSubscription = this.formGroup.valueChanges.subscribe({
                next: (value) => {
                    if (this.formGroup.valid) {
                        this.value = value;
                        this.onChange(value);
                        this.triggerRevalidate();
                    }

                }
            });
            let boundValue: GeboOauth2SecretContent = { providerName: this.forcedProviderName } as GeboOauth2SecretContent;
            if (this.value) {
                if (this.forcedProviderName) {
                    boundValue = {
                        ...this.value,
                        providerName: this.forcedProviderName
                    };
                } else {
                    boundValue = this.value;
                }
            }
            this.oldProviderName = providerName;
            try {
                this.formGroup.patchValue(boundValue);
            } catch (e) { }
            this.formGroup.updateValueAndValidity();

        }
    }
    onChange: (v: any) => void = (v) => { };
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }
    onTouched: (v: any) => void = (v) => { };
    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }
    setDisabledState?(isDisabled: boolean): void {
        isDisabled ? this.formGroup.disable({ emitEvent: false })
            : this.formGroup.enable({ emitEvent: false });
        this.triggerRevalidate();
    }
    protected customAttribute(attr?: string): FormControl<any> {
        if (attr) {
            const cattrs = this.formGroup.controls["customAttributes"] as FormGroup;
            return cattrs.controls[attr] as FormControl;
        } else throw Error("This execution path is not possible");

    }





}