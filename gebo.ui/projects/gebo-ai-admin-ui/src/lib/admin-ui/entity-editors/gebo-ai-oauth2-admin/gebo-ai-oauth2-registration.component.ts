import { Component, forwardRef, Inject, inject, Injector, Input } from "@angular/core";
import { AbstractControl, FormControl, FormGroup, Validators } from "@angular/forms";
import { AuthProviderDto, BASE_PATH, OAuth2AdminControllerService, Oauth2ProviderModifiableData } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAITranslationService, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
const frontendRedirectRelativeUris: string[] = ["/ui/oauth2-land", "/ui/chat", "/"];
const frontendRefererUris: string[] = ["/ui/login"];
const backendRedirectRelativeUris: string[] = ["/login/oauth2/code/{registrationId}"];
const backendRefererUris: string[] = ["/oauth2/authorization/{registrationId}"];

@Component({
    selector: "gebo-ai-oauth2-registration-component",
    templateUrl: "gebo-ai-oauth2-registration.component.html",
    standalone: false, providers: [ 
        { provide: GEBO_AI_MODULE, useValue: "GeboAIOauth2RegistrationModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIOauth2RegistrationComponent),
        multi: false  }
    ]
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
            issuerUri: new FormControl(),
            jwkSetUri: new FormControl()
        }),
        oauth2ClientContent: new FormControl(),
        authClientMethod: new FormControl(),
        authGrantType: new FormControl(),
        configurationType: new FormControl(),
        resourceServerAudiences: new FormControl(),
        description: new FormControl(),
        readOnly: new FormControl()
    });
    protected redirectUrls: string[] = [];
    protected refererUrls: string[] = [];
    protected currentProvider?: AuthProviderDto.ProviderEnum;
    protected currentConfigurationType?: Oauth2ProviderModifiableData.ConfigurationTypeEnum;
    /** The accepted audiences as typed, one line of comma separated values. */
    protected audiencesText: string = "";
    @Input() oauth2ChoosableScopes: { code: string, description: string }[] = [];
    @Input() oauth2MandatoryScopes: { code: string, description: string }[] = [];
    protected providers: {
        code?: AuthProviderDto.ProviderEnum,
        description?: string
    }[] = [];
    protected authClientMethodData: { code: Oauth2ProviderModifiableData.AuthClientMethodEnum, description: string }[] = [];
    protected authGrantTypeData: { code: Oauth2ProviderModifiableData.AuthGrantTypeEnum, description: string }[] = [];
    private translationService = inject(GeboAITranslationService);
    private moduleId = inject(GEBO_AI_MODULE, { optional: true });
    /** The configuration purposes, built here rather than in the template, translated as the template's texts are. */
    private purposeTexts: { [id: string]: string } = {
        AuthenticationPurposeOption: "Authentication",
        IntegrationPurposeOption: "Integration client"
    };
    protected configurationTypesData: { code: Oauth2ProviderModifiableData.ConfigurationTypeEnum, description: string }[] = this.buildConfigurationTypesData();
    protected readonly: boolean = false;
    // 'facebook' | 'google' | 'github' | 'microsoft' | 'linkedin' | 'amazon' | 'twitter' | 'slack' | 'x' | 'apple' | 'oauth2_generic' 

    public constructor(
        @Inject(BASE_PATH) private basePath: string,
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
            // audiences are checked only when a token is presented to the API, which is
            // what an authentication configuration is for
            this.currentConfigurationType = value.configurationType;
        });
    }
    protected override onNewData(actualValue: Oauth2ProviderModifiableData): void {
        this.userMessages = [{
            summary: "Security warning",
            detail: "Oauth2 client registration data will be crypted inside the database, pay attention on handling it, a misconfiguration can leave your system on being unusable from some users",
            severity: "warning"
        }];
        this.audiencesText = "";
        actualValue.readOnly = false;
        this.formGroup.controls["readOnly"].setValue(false);
    }
    /** Keeps the control and the line the administrator types in step. */
    protected audiencesChanged(text: string): void {
        this.audiencesText = text;
        const audiences = (text ?? "").split(",").map(x => x.trim()).filter(x => x.length > 0);
        this.formGroup.controls["resourceServerAudiences"].setValue(audiences.length ? audiences : null);
    }

    protected cat(base: string, relative: string, id?: string): string {
        return base + (id ? relative.replaceAll("{registrationId}", id) : relative);
    }
    protected override onLoadedPersistentData(actualValue: Oauth2ProviderModifiableData): void {
        this.audiencesText = (actualValue.resourceServerAudiences ?? []).join(", ");
        const registrationId = actualValue.code;
        const baseUIPath = window.document.location.origin;
        this.redirectUrls = [...frontendRedirectRelativeUris.map(x => this.cat(baseUIPath, x, registrationId)), ...backendRedirectRelativeUris.map(x => this.cat(this.basePath, x, registrationId))];
        this.refererUrls = [...frontendRefererUris.map(x => this.cat(baseUIPath, x, registrationId)), ...backendRefererUris.map(x => this.cat(this.basePath, x, registrationId))];
        this.readonly = actualValue?.readOnly === true;
        this.userMessages = [{
            summary: "Security warning",
            detail: "Oauth2 client registration data will be crypted inside the database, pay attention on handling it, a misconfiguration can leave your system on being unusable from some users",
            severity: "warning"
        }];
        if (this.readonly === true) {
            this.userMessages = [{
                summary: "Security warning",
                detail: "These oauth2 configurations are inserted in the configuration files of this software and not completely visible and manageable here for security reasons",
                severity: "warning"
            }];
        }
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
    private buildConfigurationTypesData(): { code: Oauth2ProviderModifiableData.ConfigurationTypeEnum, description: string }[] {
        return [{ code: "AUTHENTICATION", description: this.purposeTexts["AuthenticationPurposeOption"] },
        { code: "INTEGRATION", description: this.purposeTexts["IntegrationPurposeOption"] }];
    }

    /** Translates the configuration purposes, then the options showing them. */
    private translatePurposeTexts(): void {
        const moduleId = Array.isArray(this.moduleId) ? this.moduleId[0] : this.moduleId;
        if (!moduleId) return;
        const items = Object.entries(this.purposeTexts).map(([id, label]) => ({ id, label }));
        this.translationService.translateMenuItems(moduleId, this.entityName, items).subscribe(translated => {
            const texts = { ...this.purposeTexts };
            translated.forEach(x => {
                if (x.id && x.label) texts[x.id] = x.label;
            });
            this.purposeTexts = texts;
            this.configurationTypesData = this.buildConfigurationTypesData();
        });
    }

    override ngOnInit(): void {
        super.ngOnInit();
        this.translatePurposeTexts();
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
        if (value?.authProvider !== "oauth2_generic") {
            value.providerConfiguration = undefined;
        }
        if (!value.readOnly) {
            value.readOnly = false;
        }
        return this.oauth2ControllerService.updateOauth2ProviderRegistration(value);
    }
    override insert(value: Oauth2ProviderModifiableData): Observable<Oauth2ProviderModifiableData> {
        if (value?.authProvider !== "oauth2_generic") {
            value.providerConfiguration = undefined;
        }
        if (!value.readOnly) {
            value.readOnly = false;
        }
        return this.oauth2ControllerService.insertOauth2ProviderRegistration(value);
    }
    override delete(value: Oauth2ProviderModifiableData): Observable<boolean> {
        if (value?.authProvider !== "oauth2_generic") {
            value.providerConfiguration = undefined;
        }
        if (!value.readOnly) {
            value.readOnly = false;
        }
        return this.oauth2ControllerService.deleteOauth2ProviderRegistration(value).pipe(map(x => true));
    }
    override canBeDeleted(value: Oauth2ProviderModifiableData): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" })
    }
}


