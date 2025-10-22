import { Component, Injectable } from "@angular/core";
import { GeboNeo4jModuleSetupControllerService, GraphRagConfigurationControllerService, GraphRagExtractionConfig } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractInstalledModuleService, AbstractStatusService, BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, GeboActionType, GeboUIActionRoutingService, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { concatMap, map, Observable, of } from "rxjs";
@Injectable()
export class Neo4jModuleEnabledService extends AbstractInstalledModuleService {
    public override getInstalledModule(): Observable<boolean> {
        return this.geboNeo4jModuleService.getNeo4jModuleSetupConfig().pipe(map(data => data?.enabled === true));
    }
    constructor(private geboNeo4jModuleService: GeboNeo4jModuleSetupControllerService) {
        super()
    }
}
@Injectable()
export class GraphRagStatusService extends AbstractStatusService {
    constructor(private geboNeo4jModuleService: GeboNeo4jModuleSetupControllerService, private graphRagConfigService: GraphRagConfigurationControllerService) {
        super();
    }

    /**
     * Returns an Observable boolean indicating whether any default graph rag config exists.
     * @returns Observable<boolean> - True if at least one default graph rag configuration exists
     */
    public override getBooleanStatus(): Observable<boolean> {
        return this.geboNeo4jModuleService.getNeo4jModuleSetupConfig().pipe(concatMap(moduleDef => {
            if (moduleDef?.enabled === true) {
                return this.graphRagConfigService.getDefaultGraphRagExtractionConfig().pipe(map(c => ((c && c.length > 0) ? true : false)));
            } else return of(false);
        }))

    }
}

@Component({
    templateUrl: "graphrag-wizard.component.html",
    selector: "gebo-ai-graphrag-setup-component",
    standalone: false,
    providers: [{ provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GraphRagWizardComponent") }]
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
        const data: GraphRagExtractionConfig = {
            defaultConfiguration: true,
            description: "Graph rag default configuration"
        };
        this.actionRouter.routeEvent({
            actionType: GeboActionType.NEW,
            context: {},
            contextType: "",
            target: data,
            targetType: "GraphRagExtractionConfig",
            onActionPerformed: (ev) => {
                this.reloadData();
            }
        });
    }
    protected editConfig(data: GraphRagExtractionConfig) {
        this.actionRouter.routeEvent({
            actionType: GeboActionType.OPEN,
            context: {},
            contextType: "",
            target: data,
            targetType: "GraphRagExtractionConfig",
            onActionPerformed: (ev) => {
                this.reloadData();
            }
        });
    }
}