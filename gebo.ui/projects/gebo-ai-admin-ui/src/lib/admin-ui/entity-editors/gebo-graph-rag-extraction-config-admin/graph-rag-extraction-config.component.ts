import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ChatModelsControllerService, CompanySystemsControllerService, ConfigurationEntry, GKnowledgeBase, GObjectRef, GObjectRefGBaseModelConfig, GProject, GraphRagConfigurationControllerService, GraphRagExtractionConfig, KnowledgeBaseControllerService, ProjectsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { forkJoin, map, Observable, of } from "rxjs";
@Component({
    templateUrl: "graph-rag-extraction-config.component.html",
    selector: "gebo-ai-graph-rag-extraction-config-component",
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAIGraphRagExtractionModule", multi: false }, {
        provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIGraphRagExtractionConfigComponent),
        multi: false
    }]
})
export class GeboAIGraphRagExtractionConfigComponent extends BaseEntityEditingComponent<GraphRagExtractionConfig> {
    protected override entityName: string = "GraphRagExtractionConfig";
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        userModified: new FormControl(),
        userCreated: new FormControl(),
        dateModified: new FormControl(),
        dateCreated: new FormControl(),
        knowledgeBaseCode: new FormControl(),
        projectCode: new FormControl(),
        defaultConfiguration: new FormControl(),
        endpoint: new FormControl(),
        extractionPrompt: new FormControl(),
        customEntityTypes: new FormControl(),
        customEventTypes: new FormControl(),
        customRelationTypes: new FormControl(),
        usedModelConfiguration: new FormControl(),
        graphRagAllSources: new FormControl(),
        contentSelectionFilter: new FormControl(),
        processEveryDocument: new FormControl(),
        extractionFormat: new FormControl()
    });
    protected systemConfiguration?: GraphRagExtractionConfig;
    /** Available chat models to choose from */
    protected chatModelsData?: GObjectRefGBaseModelConfig[] = [];

    protected knowledgeBase?: GKnowledgeBase;
    protected project?: GProject;
    protected dataSource?: GObjectRef;
    protected defaultConfiguration: boolean = false;
    protected graphRagAllSources: boolean = false;
    protected processEveryDocument: boolean | undefined;
    /** The default chat model configuration */
    protected defaultChatModel?: { code?: string, description?: string };
    protected configurationLabel?: string;
    protected extractionFormats: { code: GraphRagExtractionConfig.ExtractionFormatEnum, description: string }[] = [{ code: "CSV", description: "CSV format" }, { code: "JSON", description: "JSON format" }];
    public constructor(injector: Injector,
        geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService: GeboUIOutputForwardingService,
        private geboChatModels: ChatModelsControllerService,
        private graphRagExtractionConfigService: GraphRagConfigurationControllerService,
        private knowledgeBaseService: KnowledgeBaseControllerService,
        private projectsService: ProjectsControllerService,
        private systemsService: CompanySystemsControllerService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
        this.formGroup.controls["defaultConfiguration"].valueChanges.subscribe({
            next: (value) => {
                this.defaultConfiguration = value === true;
                if (this.defaultConfiguration === true) {
                    this.formGroup.controls["graphRagAllSources"].enable();
                } else {
                    this.formGroup.controls["graphRagAllSources"].disable();
                }
            }
        });
        this.formGroup.controls["graphRagAllSources"].valueChanges.subscribe({
            next: (value) => {
                const actualValue: boolean = value === true
                if (this.formGroup.controls["defaultConfiguration"].value === true) {
                    if (actualValue !== true) {
                        this.formGroup.controls["processEveryDocument"].setValue(false);
                        this.formGroup.controls["contentSelectionFilter"].setValue(undefined);
                        this.formGroup.controls["processEveryDocument"].disable();
                        this.formGroup.controls["contentSelectionFilter"].disable();
                    } else {
                        this.formGroup.controls["processEveryDocument"].enable();
                        this.formGroup.controls["contentSelectionFilter"].enable();
                    }
                }
                this.graphRagAllSources = actualValue;
                this.formGroup.updateValueAndValidity();
            }
        });
        this.formGroup.controls["processEveryDocument"].valueChanges.subscribe({
            next: (processEveryDocument: boolean | undefined) => {
                this.processEveryDocument = processEveryDocument;
                if (processEveryDocument === true) {
                    //this.formGroup.controls["contentSelectionFilter"].clearValidators();
                    this.formGroup.controls["contentSelectionFilter"].setValue(null);
                    this.formGroup.controls["contentSelectionFilter"].disable();
                } else {
                    this.formGroup.controls["contentSelectionFilter"].enable();
                    this.formGroup.controls["contentSelectionFilter"].addValidators(Validators.required);
                    //this.formGroup.controls["contentSelectionFilter"].clearValidators();
                    //this.formGroup.controls["contentSelectionFilter"].addValidators(Validators.required);
                }
                this.formGroup.updateValueAndValidity();
            }
        });
        this.formGroup.controls["extractionFormat"].valueChanges.subscribe({
            next: (value) => {
                if (this.mode == "NEW" && value) {
                    this.loadingRelatedBackend = true;
                    this.graphRagExtractionConfigService.getSystemGraphRagExtractionConfig(value).subscribe({
                        next: (systemConfig) => {
                            this.systemConfiguration = systemConfig;
                            this.formGroup.controls["extractionPrompt"].setValue(this.systemConfiguration?.extractionPrompt);
                        },
                        complete: () => {
                            this.loadingRelatedBackend = false;
                        }
                    })

                }
            }
        });
        this.userMessages = [{ summary: "Important disclaimer", detail: "Graph-rag processing improves chat responses quality, but increases data processing pipeline costs.", severity: "warn" }];
    }

    override ngOnInit(): void {
        super.ngOnInit();
        this.loadingRelatedBackend = true;
        this.geboChatModels.getRuntimeConfiguredChatModels().subscribe({
            next: (data) => {
                this.chatModelsData = data?.map(x => x.objectReference).filter(x => x ? true : false) as GObjectRefGBaseModelConfig[];
                const chatModels = data;
                if (chatModels) {
                    let usedDefault: ConfigurationEntry;
                    chatModels.forEach(m => {
                        if (m.configuration?.defaultModel === true) {
                            usedDefault = m;
                            this.defaultChatModel = m.configuration;
                        }
                    });

                }


            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        })
    }
    private loadLookups(actualValue: GraphRagExtractionConfig): void {
        const observables: Observable<any>[] = [];
        this.knowledgeBase = undefined;
        this.project = undefined;
        this.dataSource = undefined;
        if (actualValue?.knowledgeBaseCode) {
            observables.push(this.knowledgeBaseService.findKnowledgeBaseByCode(actualValue.knowledgeBaseCode));
        }
        if (actualValue?.projectCode) {
            observables.push(this.projectsService.findProjectByCode(actualValue.projectCode));
        }
        if (actualValue?.endpoint) {
            observables.push(this.systemsService.getProjectEndpointByObjectRef(actualValue?.endpoint));
        }
        if (observables && observables.length) {
            this.loadingRelatedBackend = true;
            forkJoin(observables).subscribe({
                next: (data) => {
                    this.knowledgeBase = data[0];
                    if (observables.length > 1) {
                        this.project = data[1];
                    }
                    if (observables.length > 2) {
                        this.dataSource = data[2];
                    }
                    let label: string = "Configuration for ";
                    if (this.knowledgeBase) label += " Knowledge base: " + this.knowledgeBase.description;
                    if (this.project) label += " Project: " + this.project.description;
                    if (this.dataSource) label += " Data source: " + this.dataSource.description;
                    this.configurationLabel = label;
                    if (!this.formGroup.controls["description"].value) {
                        this.formGroup.controls["description"].setValue(this.configurationLabel);
                    }
                },
                complete: () => {
                    this.loadingRelatedBackend = false;
                }
            });
        }
    }
    protected override onLoadedPersistentData(actualValue: GraphRagExtractionConfig): void {
        this.loadLookups(actualValue);
    }
    protected override onNewData(actualValue: GraphRagExtractionConfig): void {

        this.loadLookups(actualValue);
       
    }
    override findByCode(code: string): Observable<GraphRagExtractionConfig | null> {
        return this.graphRagExtractionConfigService.findGraphRagExtractionConfigByCode(code);
    }
    override save(value: GraphRagExtractionConfig): Observable<GraphRagExtractionConfig> {
        return this.graphRagExtractionConfigService.saveGraphRagExtractionConfig(value);
    }
    override insert(value: GraphRagExtractionConfig): Observable<GraphRagExtractionConfig> {
        return this.graphRagExtractionConfigService.instertGraphRagExtractionConfig(value);
    }
    override delete(value: GraphRagExtractionConfig): Observable<boolean> {
        return this.graphRagExtractionConfigService.deleteGraphRagExtractionConfig(value).pipe(map(x => true));
    }
    override canBeDeleted(value: GraphRagExtractionConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

}