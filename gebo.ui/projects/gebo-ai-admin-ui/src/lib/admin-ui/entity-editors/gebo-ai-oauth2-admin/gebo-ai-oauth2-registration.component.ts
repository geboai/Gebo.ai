import { Component, Injector, Input } from "@angular/core";
import { AbstractControl, FormControl, FormGroup, Validators } from "@angular/forms";
import { AuthProviderDto, OAuth2AdminControllerService, Oauth2ProviderModifiableData, Oauth2ProviderRegistrationInsertData } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
interface ExtendedOauth2ProviderRegistrationInsertData extends Oauth2ProviderRegistrationInsertData {
    code?: string;
};
@Component({
    selector: "gebo-ai-oauth2-registration-component",
    templateUrl: "gebo-ai-oauth2-registration.component.html",
    standalone: false
})
export class GeboAIOauth2RegistrationComponent extends BaseEntityEditingComponent<ExtendedOauth2ProviderRegistrationInsertData> {
    protected override entityName: string = "Oauth2ProviderRegistration";
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        authProvider: new FormControl(),
        providerConfiguration: new FormGroup({
            provider: new FormControl(),
            authorizationUri: new FormControl(),
            tokenUri: new FormControl(),
            userInfoUri: new FormControl(),
            userNameAttribute: new FormControl(),
            introspectionUri: new FormControl(),
            issuerUri: new FormControl()
        }),
        oauth2ClientContent: new FormGroup({
            tenantId: new FormControl(),
            clientId: new FormControl(),
            secret: new FormControl(),
            scopes: new FormControl()
        }),
        authClientMethod: new FormControl(),
        authGrantType: new FormControl(),
        configurationTypes: new FormControl(),
        description: new FormControl(),
        readOnly: new FormControl()
    });
    public currentProvider?: AuthProviderDto.ProviderEnum;
    @Input() oauth2ChoosableScopes: { code: string, description: string }[] = [];
    @Input() oauth2MandatoryScopes: { code: string, description: string }[] = [];
    public providers: {
        code?: AuthProviderDto.ProviderEnum,
        description?: string
    }[] = [];
    public authClientMethodData: { code: Oauth2ProviderRegistrationInsertData.AuthClientMethodEnum, description: string }[] = [];
    public authGrantTypeData: { code: Oauth2ProviderRegistrationInsertData.AuthGrantTypeEnum, description: string }[] = [];
    public configurationTypesData: { code: Oauth2ProviderRegistrationInsertData.ConfigurationTypesEnum, description: string }[] = [{ code: "AUTHENTICATION", description: "Authentication" }, { code: "INTEGRATION", description: "Integration client" }];
    public readonly: boolean = false;
    // 'facebook' | 'google' | 'github' | 'microsoft' | 'linkedin' | 'amazon' | 'twitter' | 'slack' | 'x' | 'apple' | 'oauth2_generic' 

    public constructor(injector: Injector,
        geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService: GeboUIOutputForwardingService,
        private oauth2ControllerService: OAuth2AdminControllerService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
        this.formGroup.valueChanges.subscribe((value: ExtendedOauth2ProviderRegistrationInsertData) => {
            this.readonly = (value as any)["readOnly"] === true;
            if (this.currentProvider !== value.authProvider) {
                this.currentProvider = value.authProvider;
                this.modifyValidation(value, this.mode);


            }
        });
    }
    protected override onNewData(actualValue: ExtendedOauth2ProviderRegistrationInsertData): void {
        this.userMessages = [{
            summary: "Security warning",
            detail: "Oauth2 client registration data will be not readable using the application after insertion for security reason, you can only delete them in the future",
            severity: "warning"
        }];
    }
    protected override onLoadedPersistentData(actualValue: ExtendedOauth2ProviderRegistrationInsertData): void {

    }
    private modifyValidation(value: ExtendedOauth2ProviderRegistrationInsertData, mode: "NEW" | "EDIT" | "EDIT_OR_NEW") {
        if (mode === "NEW") {
            if ((value.authProvider === 'oauth2_generic')) {
                this.formGroup.controls["providerConfiguration"].enable();
                this.switchMandatory(this.formGroup.controls["providerConfiguration"], true);
                const providerConfiguration = this.formGroup.controls["providerConfiguration"] as FormGroup;
                providerConfiguration.controls["provider"].setValue(value.authProvider);
            } else {
                this.formGroup.controls["providerConfiguration"].disable();
                this.switchMandatory(this.formGroup.controls["providerConfiguration"], false);
            }
        } else {

        }
    }
    switchMandatory(ctrl: AbstractControl<any, any>, required: boolean) {
        ctrl.clearValidators();
        if (required === true) {
            ctrl.setValidators(Validators.required);
        }
        if (ctrl instanceof FormGroup) {
            const fg = ctrl as FormGroup;
            Object.values(fg.controls).forEach(_ctrl => {
                this.switchMandatory(_ctrl, required);
            });
        }
    }
    override ngOnInit(): void {
        super.ngOnInit();
        this.loadingRelatedBackend = true;
        this.oauth2ControllerService.getProviders().subscribe({
            next: (providers) => {
                this.providers = providers.map(x => {
                    return { code: x.provider, description: x.description };
                });
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        })
    }
    override findByCode(code: string): Observable<Oauth2ProviderRegistrationInsertData | null> {
        return this.oauth2ControllerService.findOauth2ProviderRegistrationByRegistrationId(code).pipe(map(convertReadable))
    }
    override save(value: Oauth2ProviderRegistrationInsertData): Observable<Oauth2ProviderRegistrationInsertData> {
        return this.oauth2ControllerService.updateOauth2ProviderRegistration(convertUpdatable(value)).pipe(map(convertReadable));
    }
    override insert(value: Oauth2ProviderRegistrationInsertData): Observable<Oauth2ProviderRegistrationInsertData> {
        return this.oauth2ControllerService.insertOauth2ProviderRegistration(value).pipe(map(convertReadable));
    }
    override delete(value: Oauth2ProviderRegistrationInsertData): Observable<boolean> {
        return this.oauth2ControllerService.deleteOauth2ProviderRegistration(convertUpdatable(value)).pipe(map(x => true));
    }
    override canBeDeleted(value: Oauth2ProviderRegistrationInsertData): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" })
    }
}

function convertReadable(value: Oauth2ProviderModifiableData): ExtendedOauth2ProviderRegistrationInsertData {
    const data: ExtendedOauth2ProviderRegistrationInsertData = {
        code: value.registrationId,
        description: value.description ? value.description : "",
        authProvider: value.authProvider,
        configurationTypes: value.configurationTypes,
        authClientMethod: value.authClientMethod,
        authGrantType: value.authGrantType,
        oauth2ClientContent: {
            clientId: "XXXXXXXXXXXXXX",
            secret: "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
            tenantId: "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
            scopes: value.scopes
        }
    };
    return data;
}
function convertUpdatable(value: ExtendedOauth2ProviderRegistrationInsertData): Oauth2ProviderModifiableData {
    const data:Oauth2ProviderModifiableData= {
        authProvider:value.authProvider,
        authClientMethod:value.authClientMethod,
        authGrantType:value.authGrantType,
        configurationTypes:value.configurationTypes,
        description:value.description,
        registrationId: value.code?value.code:"NOCODE",
        scopes: value.oauth2ClientContent.scopes        
    };
    return data;
}

