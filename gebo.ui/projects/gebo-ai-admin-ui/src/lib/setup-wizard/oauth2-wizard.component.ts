import { Component, Injectable } from "@angular/core";
import { Oauth2ClientAuthorizativeInfo, OAuth2ProvidersControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, GeboUIActionRoutingService, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
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
    public newOauth2(): void { }
} 