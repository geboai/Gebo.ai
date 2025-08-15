import { Component, forwardRef, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { ControlValueAccessor, FormControl, FormGroup, NG_VALUE_ACCESSOR, Validators } from "@angular/forms";
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
        }
    ]
})
export class GeboOauth2SecretComponent implements OnInit, OnChanges, ControlValueAccessor {

    protected formGroup: FormGroup = new FormGroup({
        providerName: new FormControl(null, Validators.required),
        clientId: new FormControl(null, Validators.required),
        secret: new FormControl(null, Validators.required),
        scopes: new FormControl(),
        customAttributes: new FormGroup({})
    });
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
            }
        });
    }
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["forcedProviderName"] && this.forcedProviderName) {
            this.restructureFormGroup(this.forcedProviderName);
        }
    }
    writeValue(obj: any): void {
        this.value = obj;
        if (this.value?.providerName) {
            this.restructureFormGroup(this.value?.providerName);
        }
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
            this.actualProvider = this.providers.find(x => x.provider === providerName);
            const currentCustomAttributes = this.actualProvider?.customAttributes;
            this.actualEditableCustomAttributes = currentCustomAttributes ? currentCustomAttributes : [];
            this.actualEditableCustomAttributes.forEach(customAttribute => {
                const fc = new FormControl();
                fc.setValidators(Validators.required);
                if (customAttribute.attributeName)
                    customAttributes.addControl(customAttribute.attributeName, fc);
            });
            this.formGroupChangeValueSubscription = this.formGroup.statusChanges.subscribe({
                next: (value) => {
                    if (value === "VALID") {
                        const value = this.formGroup.value;
                        this.value = value;
                        this.onChange(value);
                    }
                }
            });
            this.formGroup.updateValueAndValidity();
            this.oldProviderName = providerName;
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

    }
    protected customAttribute(attr?: string): FormControl<any> {
        if (attr) {
            const cattrs = this.formGroup.controls["customAttributes"] as FormGroup;
            return cattrs.controls[attr] as FormControl;
        } else throw Error("This execution path is not possible");

    }





}