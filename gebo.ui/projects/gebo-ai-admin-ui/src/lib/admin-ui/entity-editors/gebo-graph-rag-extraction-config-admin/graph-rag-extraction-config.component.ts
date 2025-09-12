import { Component, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { ChatModelsControllerService, GraphRagConfigurationControllerService, GraphRagExtractionConfig } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfigurationEntry, GObjectRefGBaseModelConfig } from "gebo-ai-rest-api";
import { ConfirmationService } from "primeng/api";
import { forkJoin, map, Observable, of } from "rxjs";
@Component({
    templateUrl: "graph-rag-extraction-config.component.html",
    selector: "gebo-ai-graph-rag-extraction-config-component",
    standalone: false
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
        usedModelConfiguration: new FormControl()
    });
    protected systemConfiguration?: GraphRagExtractionConfig;
    /** Available chat models to choose from */
    protected chatModelsData?: GObjectRefGBaseModelConfig[] = [];

    /** The default chat model configuration */
    protected defaultChatModel?: {code?:string,description?:string};
    public constructor(injector: Injector,
        geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService: GeboUIOutputForwardingService,
        private geboChatModels: ChatModelsControllerService,
        private graphRagExtractionConfigService: GraphRagConfigurationControllerService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
    }
    override ngOnInit(): void {
        super.ngOnInit();
        this.loadingRelatedBackend = true;
        const observables: [Observable<ConfigurationEntry[]>, Observable<GraphRagExtractionConfig>] = [this.geboChatModels.getRuntimeConfiguredChatModels(), this.graphRagExtractionConfigService.getSystemGraphRagExtractionConfign()];
        forkJoin(observables)
            .subscribe({
                next: (data) => {
                    this.chatModelsData = data[0]?.map(x => x.objectReference).filter(x => x ? true : false) as GObjectRefGBaseModelConfig[];
                    const chatModels=data[0];
                    if (chatModels) {
                        let usedDefault:ConfigurationEntry;
                        chatModels.forEach(m=>{
                            if (m.configuration?.defaultModel===true) {
                                usedDefault=m;
                                this.defaultChatModel=m.configuration;
                            }
                        });
                        
                    }
                    this.systemConfiguration = data[1];
                    if (this.mode == "NEW") {
                        this.formGroup.controls["extractionPrompt"].setValue(this.systemConfiguration?.extractionPrompt);
                    }
                },
                complete: () => {
                    this.loadingRelatedBackend = false;
                }
            })
    }
    protected override onLoadedPersistentData(actualValue: GraphRagExtractionConfig): void {

    }
    protected override onNewData(actualValue: GraphRagExtractionConfig): void {
        this.loadingRelatedBackend = true;
        this.graphRagExtractionConfigService.getSystemGraphRagExtractionConfign().subscribe({
            next: (systemConfiguration) => {
                this.systemConfiguration = systemConfiguration;
                if (this.mode == "NEW") {
                    this.formGroup.controls["extractionPrompt"].setValue(this.systemConfiguration?.extractionPrompt);
                }
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
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
        return of({ canBeDeleted: this.entity?.defaultConfiguration !== true, message: "" });
    }

}