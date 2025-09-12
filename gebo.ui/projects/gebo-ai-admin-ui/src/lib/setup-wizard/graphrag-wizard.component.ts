import { Component, Injectable } from "@angular/core";
import { GraphRagConfigurationControllerService, GraphRagExtractionConfig } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, GeboActionType, GeboUIActionRoutingService, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { map, Observable } from "rxjs";

@Injectable()
export class GraphRagStatusService extends AbstractStatusService {
    constructor(private graphRagConfigService: GraphRagConfigurationControllerService) {
        super();
    }

    /**
     * Returns an Observable boolean indicating whether any default graph rag config exists.
     * @returns Observable<boolean> - True if at least one default graph rag configuration exists
     */
    public override getBooleanStatus(): Observable<boolean> {
        return this.graphRagConfigService.getDefaultGraphRagExtractionConfig().pipe(map(c => ((c && c.length > 0) ? true : false)));
    }
}

@Component({
    templateUrl: "graphrag-wizard.component.html",
    selector: "gebo-ai-graphrag-setup-component",
    standalone:false
})
export class GraphRagWizardComponent extends BaseWizardSectionComponent {

    protected defaultConfigurations: GraphRagExtractionConfig[] = [];
    constructor(setupWizardComunicationService: SetupWizardComunicationService, private graphRagConfigService: GraphRagConfigurationControllerService, private actionRouter: GeboUIActionRoutingService) {
        super(setupWizardComunicationService);
    }
    public override reloadData(): void {
        this.loading = true;
        this.graphRagConfigService.getDefaultGraphRagExtractionConfig().subscribe({
            next: (data) => {
                this.defaultConfigurations = data;
            },
            complete: () => {
                this.loading = false;
            }
        });
    }
    protected createConfig() {
        const data:GraphRagExtractionConfig= {
            defaultConfiguration: true,
            description:"Graph rag default configuration"
        };
        this.actionRouter.routeEvent({
            actionType:GeboActionType.NEW,
            context:{},
            contextType:"",
            target:data,
            targetType:"GraphRagExtractionConfig",
            onActionPerformed:(ev)=>{
                this.reloadData();
            }
        });
    }
    protected editConfig(data: GraphRagExtractionConfig) {
        this.actionRouter.routeEvent({
            actionType:GeboActionType.OPEN,
            context:{},
            contextType:"",
            target:data,
            targetType:"GraphRagExtractionConfig",
            onActionPerformed:(ev)=>{
                this.reloadData();
            }
        });
    }
}