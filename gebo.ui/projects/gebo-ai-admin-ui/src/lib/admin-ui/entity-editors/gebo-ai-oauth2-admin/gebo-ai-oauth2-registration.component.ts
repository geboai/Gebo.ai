import { Component, Inject, Injector, Input } from "@angular/core";
import { AbstractControl, FormControl, FormGroup, Validators } from "@angular/forms";
import { AuthProviderDto, BASE_PATH, OAuth2AdminControllerService, Oauth2ProviderModifiableData } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
const frontendRedirectRelativeUris: string[] = ["/ui/oauth2-land", "/ui/chat", "/"];
const frontendRefererUris: string[] = ["/ui/login"];
const backendRedirectRelativeUris: string[] = ["/login/oauth2/code/{registrationId}"];
const backendRefererUris: string[] = ["/oauth2/authorization/{registrationId}"];

@Component({
    selector: "gebo-ai-oauth2-registration-component",
    templateUrl: "gebo-ai-oauth2-registration.component.html",
    standalone: false
})
export class GeboAIOauth2RegistrationComponent extends BaseEntityEditingComponent<Oauth2ProviderModifiableData> {
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
    protected redirectUrls:string[]=[];
    protected refererUrls:string[]=[];
    public currentProvider?: AuthProviderDto.ProviderEnum;
    @Input() oauth2ChoosableScopes: { code: string, description: string }[] = [];
    @Input() oauth2MandatoryScopes: { code: string, description: string }[] = [];
    public providers: {
        code?: AuthProviderDto.ProviderEnum,
        description?: string
    }[] = [];
    public authClientMethodData: { code: Oauth2ProviderModifiableData.AuthClientMethodEnum, description: string }[] = [];
    public authGrantTypeData: { code: Oauth2ProviderModifiableData.AuthGrantTypeEnum, description: string }[] = [];
    public configurationTypesData: { code: Oauth2ProviderModifiableData.ConfigurationTypesEnum, description: string }[] = [{ code: "AUTHENTICATION", description: "Authentication" }, { code: "INTEGRATION", description: "Integration client" }];
    public readonly: boolean = false;
    // 'facebook' | 'google' | 'github' | 'microsoft' | 'linkedin' | 'amazon' | 'twitter' | 'slack' | 'x' | 'apple' | 'oauth2_generic' 

    public constructor(
        @Inject(BASE_PATH) private  basePath:string,
        injector: Injector,
        geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService: GeboUIOutputForwardingService,
        private oauth2ControllerService: OAuth2AdminControllerService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
        this.formGroup.valueChanges.subscribe((value: Oauth2ProviderModifiableData) => {
            this.readonly = (value as any)["readOnly"] === true;
            if (this.currentProvider !== value.authProvider) {
                this.currentProvider = value.authProvider;
                this.modifyValidation(value, this.mode);


            }
        });
    }
    protected override onNewData(actualValue: Oauth2ProviderModifiableData): void {
        this.userMessages = [{
            summary: "Security warning",
            detail: "Oauth2 client registration data will be not readable using the application after insertion for security reason, you can only delete them in the future",
            severity: "warning"
        }];
    }
    protected cat(base:string,relative:string,id?:string):string {
        return base+(id?relative.replaceAll("{registrationId}",id):relative);
    }
    protected override onLoadedPersistentData(actualValue: Oauth2ProviderModifiableData): void {
        const registrationId=actualValue.code;
        const baseUIPath=window.document.location.origin;
        this.redirectUrls=[...frontendRedirectRelativeUris.map(x=>this.cat(baseUIPath,x,registrationId)),...backendRedirectRelativeUris.map(x=>this.cat(this.basePath,x,registrationId))];
        this.refererUrls=[...frontendRefererUris.map(x=>this.cat(baseUIPath,x,registrationId)),...backendRefererUris.map(x=>this.cat(this.basePath,x,registrationId))]

    }
    private modifyValidation(value: Oauth2ProviderModifiableData, mode: "NEW" | "EDIT" | "EDIT_OR_NEW") {
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
    override findByCode(code: string): Observable<Oauth2ProviderModifiableData | null> {
        return this.oauth2ControllerService.findOauth2ProviderRegistrationByRegistrationId(code);
    }
    override save(value: Oauth2ProviderModifiableData): Observable<Oauth2ProviderModifiableData> {
        return this.oauth2ControllerService.updateOauth2ProviderRegistration(value);
    }
    override insert(value: Oauth2ProviderModifiableData): Observable<Oauth2ProviderModifiableData> {
        return this.oauth2ControllerService.insertOauth2ProviderRegistration(value);
    }
    override delete(value: Oauth2ProviderModifiableData): Observable<boolean> {
        return this.oauth2ControllerService.deleteOauth2ProviderRegistration(value).pipe(map(x => true));
    }
    override canBeDeleted(value: Oauth2ProviderModifiableData): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" })
    }
}


