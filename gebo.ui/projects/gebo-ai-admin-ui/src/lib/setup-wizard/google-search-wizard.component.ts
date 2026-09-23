import { Component, Injectable } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GGoogleSearchApiCredentials, GoogleSearchConfig, GoogleSearchConfigurationControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { map, Observable } from "rxjs";

@Injectable()
export class GoogleSearcStatusService extends AbstractStatusService {
    constructor(private geboGoogleSearchService: GoogleSearchConfigurationControllerService) {
        super();
    }

    /**
     * Returns an Observable of boolean indicating whether any Confluence systems are configured.
     * Returns true if at least one Confluence system exists, false otherwise.
     * @returns Observable<boolean> - Status of Confluence systems
     */
    public override getBooleanStatus(): Observable<boolean> {
        return this.geboGoogleSearchService.getGoogleSearchStatus().pipe(map(data => data?.isSetup === true));
    }
}

@Component({
    selector: "gebo-ai-google-search-wizard-component",
    templateUrl: "google-search-wizard.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIGoogleSearchWizardComponent") }]
})
export class GeboAIGoogleSearchWizardComponent extends BaseWizardSectionComponent {
    protected googleSearchConfig?: GoogleSearchConfig;
    protected googleSearchCredentials: GGoogleSearchApiCredentials[] = [];
    protected formGroup: FormGroup = new FormGroup({
        apiKey: new FormControl(),
        customSearchEngineId: new FormControl(),
        enabled: new FormControl()
    });
    constructor(setupWizardComunicationService: SetupWizardComunicationService, private geboGoogleSearchService: GoogleSearchConfigurationControllerService) {
        super(setupWizardComunicationService);
    }
    public override reloadData(): void {
        this.loading = true;
        this.geboGoogleSearchService.getGoogleSearchApiCredentials().subscribe({
            next: (values) => {
                this.googleSearchCredentials = values;
            },
            complete: () => {
                this.loading = false;
            }
        })
    }
    protected createGoogleSearchCredentials() {
        this.googleSearchConfig = {
            enabled: true
        };
        this.formGroup.setValue(this.googleSearchConfig);
    }
    protected saveGoogleSearchCredentials() {
        const googleSearchConfig=this.formGroup.value;
        this.loading=true;
        this.geboGoogleSearchService.fastInsertGoogleSearchApiCredentials(googleSearchConfig).subscribe({
            next:(credentials)=>{
                this.googleSearchConfig=undefined;
                this.googleSearchCredentials=[credentials];
            },
            complete:()=>{
                this.loading=false;
            }
        });
    }
    protected deleteCredentials(credenntials: GGoogleSearchApiCredentials) {
        this.loading=true;
        this.geboGoogleSearchService.deleteGGoogleSearchApiCredentials(credenntials).subscribe({
            next:()=>{
                this.reloadData();
            },
            complete:()=>{
                this.loading=false;
            }
        })
    }   
    protected doCancel():void{
        this.googleSearchConfig=undefined;
        this.formGroup.patchValue({});
    }

}