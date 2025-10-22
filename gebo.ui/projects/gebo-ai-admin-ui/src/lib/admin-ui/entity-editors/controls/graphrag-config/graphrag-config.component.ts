import { Component, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { GeboNeo4jModuleSetupControllerService, GObjectRef, GraphRagConfigurationControllerService, GraphRagExtractionConfig } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GeboActionType, GeboUIActionRoutingService } from "@Gebo.ai/reusable-ui";

@Component({
    templateUrl: "graphrag-config.component.html",
    selector: "gebo-ai-graphrag-component",
    standalone: false,
    providers: [{ provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIGraphragConfigComponent") }]
})
export class GeboAIGraphragConfigComponent implements OnInit, OnChanges {
    @Input() context?: { knowledgeBaseCode?: string, projectCode?: string, reference?: GObjectRef };
    protected graphRagConfig?: GraphRagExtractionConfig;
    protected neo4jEnabled: boolean = false;
    protected loading: boolean = false;
    protected graphRagIsSetup: boolean = false;
    constructor(private geboUIRouter: GeboUIActionRoutingService, private graphRagConfigService: GraphRagConfigurationControllerService, private graphRagModuleSetupService: GeboNeo4jModuleSetupControllerService) {

    }
    private reload(): void {
        this.graphRagModuleSetupService.getNeo4jModuleSetupConfig().subscribe({
            next: (setupStatus) => {
                this.neo4jEnabled = setupStatus?.enabled === true;
                if (this.neo4jEnabled === true) {
                    if (this.context?.reference) {
                        this.loading = true;
                        this.graphRagConfigService.findGraphRagExtractionConfigByProjectEndpointGObjectRef(this.context.reference).subscribe({
                            next: (data) => {
                                let _data: GraphRagExtractionConfig | undefined = data && data.length ? data[0] : undefined;
                                this.graphRagConfig = _data;
                            },
                            complete: () => {
                                this.loading = false;
                            }
                        });
                    } else if (this.context?.projectCode && this.context?.knowledgeBaseCode) {
                        this.loading = true;
                        this.graphRagConfigService.findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode(this.context.knowledgeBaseCode, this.context.projectCode).subscribe({
                            next: (data) => {
                                let _data: GraphRagExtractionConfig | undefined = data && data.length ? data[0] : undefined;
                                this.graphRagConfig = _data;
                            },
                            complete: () => {
                                this.loading = false;
                            }
                        });
                    } else if (this.context?.knowledgeBaseCode) {
                        this.loading = true;
                        this.graphRagConfigService.findGraphRagExtractionConfigByKnowledgeBase(this.context?.knowledgeBaseCode).subscribe({
                            next: (data) => {
                                let _data: GraphRagExtractionConfig | undefined = data.find(x => (!(x.endpoint || x.projectCode)));
                                this.graphRagConfig = _data;
                            },
                            complete: () => {
                                this.loading = false;
                            }
                        });
                    }
                }
            }
        });

    }
    ngOnInit(): void {
        this.graphRagModuleSetupService.getNeo4jModuleSetupConfig().subscribe({
            next: (setupStatus) => {
                this.neo4jEnabled = setupStatus?.enabled === true;
                if (setupStatus.enabled === true) {
                    this.graphRagConfigService.getDefaultGraphRagExtractionConfig().subscribe({
                        next: (value) => {
                            this.graphRagIsSetup = (value && value.length) ? true : false;
                        }
                    })
                } else {
                    this.graphRagIsSetup = false;
                }
            }
        });

    }
    ngOnChanges(changes: SimpleChanges): void {
        if (this.context && changes["context"]) {
            this.reload();

        }
    }
    newConfiguration(): void {
        const config: GraphRagExtractionConfig = {
            endpoint: this.context?.reference,
            knowledgeBaseCode: this.context?.knowledgeBaseCode,
            projectCode: this.context?.projectCode,
            defaultConfiguration: false,
            contentSelectionFilter: undefined,
            processEveryDocument: false
        };
        this.geboUIRouter.routeEvent({
            actionType: GeboActionType.NEW,
            context: {},
            contextType: "List",
            target: config,
            targetType: "GraphRagExtractionConfig",
            onActionPerformed: (evt) => {
                this.reload();
            }
        })
    }
    editConfiguration(): void {
        if (this.graphRagConfig) {
            this.geboUIRouter.routeEvent({
                actionType: GeboActionType.OPEN,
                context: {},
                contextType: "List",
                target: this.graphRagConfig,
                targetType: "GraphRagExtractionConfig",
                onActionPerformed: (evt) => {
                    this.reload();
                }
            });
        }
    }
}