import { Component, Injectable } from "@angular/core";
import { Oauth2ClientAuthorizativeInfo, Oauth2ModuleStatusControllerService, OAuth2ProvidersControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, GeboActionType, GeboUIActionRoutingService, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { map, Observable } from "rxjs";
@Injectable({ providedIn: "root" })
export class Oauth2SetupWizardService extends AbstractStatusService {
    constructor(private oauth2ProvidersService: OAuth2ProvidersControllerService) {
        super();
    }
    public override getBooleanStatus(): Observable<boolean> {
        return this.oauth2ProvidersService.listAvailableProviders().pipe(map(data => data && data.length > 0));
    }
}
@Injectable({ providedIn: "root" })
export class Oauth2SetupEnabledService extends AbstractStatusService {
    constructor(private geboOauth2ClientService:Oauth2ModuleStatusControllerService) {
        super();
    }
    public override getBooleanStatus(): Observable<boolean> {
        return this.geboOauth2ClientService.getStatus().pipe(map(status=>status.oauth2Enabled===true && status.oauth2UISetupEnabled===true));
    }
    
}
@Component({
    selector: "oauth2-wizard-component",
    templateUrl: "oauth2-wizard.component.html",
    standalone: false
})
export class Oauth2WizardComponent extends BaseWizardSectionComponent {
    public providers: Oauth2ClientAuthorizativeInfo[] = [];
    constructor(setupWizardComunicationService: SetupWizardComunicationService,
        private oauth2ProvidersService: OAuth2ProvidersControllerService,
        private geboUIActionRoutingService: GeboUIActionRoutingService) {
        super(setupWizardComunicationService);
    }
    public override reloadData(): void {
        this.loading = true;
        this.oauth2ProvidersService.listAvailableProviders().subscribe({
            next: (providers) => {
                this.providers = providers;
            },
            complete: () => {
                this.loading = false;
            }
        });
    }
    public newOauth2(): void {
        const data = {

        };
        this.geboUIActionRoutingService.routeEvent({
            actionType: GeboActionType.NEW,
            context: {},
            contextType: "Oauth2Wizard",
            target: data,
            targetType: "Oauth2ProviderRegistration",
            onActionPerformed: (data) => {
                this.reloadData();
            }
        });
    }
    public editOauth2(info: Oauth2ClientAuthorizativeInfo): void {
        const data = {
            code: info.registrationId
        };
        this.geboUIActionRoutingService.routeEvent({
            actionType: GeboActionType.OPEN,
            context: {},
            contextType: "Oauth2Wizard",
            target: data,
            targetType: "Oauth2ProviderRegistration",
            onActionPerformed: (data) => {
                this.reloadData();
            }
        });
    }
} 