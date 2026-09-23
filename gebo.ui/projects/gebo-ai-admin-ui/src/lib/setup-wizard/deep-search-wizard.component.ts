import { Component } from "@angular/core";
import { DeepSearchConfig, GeboDeepSearchAdminControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, GeboActionType, GeboUIActionRoutingService, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";

@Component({
    selector: "gebo-ai-deep-search-wizard-component",
    templateUrl: "deep-search-wizard.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIGoogleSearchWizardComponent") }]
})
export class GeboAIDeepSearchWizardComponent extends BaseWizardSectionComponent {

    protected deepSearchConfig: DeepSearchConfig[] = [];

    constructor(setupWizardComunicationService: SetupWizardComunicationService,
        private deepSearchAdminService: GeboDeepSearchAdminControllerService,
        private actionRouter: GeboUIActionRoutingService) {
        super(setupWizardComunicationService)
    }
    public override reloadData(): void {
        this.loading = true;
        this.deepSearchAdminService.getDeepSeachConfigs().subscribe({
            next: (data) => {
                this.deepSearchConfig = data;
            }, complete: () => {
                this.loading = false;
            }
        })
    }
    protected editDeepSearchConfig(data: DeepSearchConfig) {
        this.actionRouter.routeEvent({
            actionType: GeboActionType.OPEN,
            context: {},
            contextType: "DeepSearchSetup",
            target: data,
            targetType: "DeepSearchConfig",
            onActionPerformed: (data) => {
                this.reloadData();
            }
        });
    }
    protected createDeepSearchConfig(): void {
        const data: DeepSearchConfig = {
            searchType: "MULTI_HOP",
            defaultConfig: true,
            accessibleToAll: true,
            perDataSourceConfigured: false
        };
        this.actionRouter.routeEvent({
            actionType: GeboActionType.NEW,
            context: {},
            contextType: "DeepSearchSetup",
            target: data,
            targetType: "DeepSearchConfig",
            onActionPerformed: (data) => {
                this.reloadData();
            }
        });
    }
}