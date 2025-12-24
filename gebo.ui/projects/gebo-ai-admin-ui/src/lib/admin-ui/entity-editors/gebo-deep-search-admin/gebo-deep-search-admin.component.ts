import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { ChatModelsControllerService, ConfigurationEntry, DeepSearchConfig, GBaseChatModelConfig, GeboDeepSearchAdminControllerService, GObjectRefGBaseModelConfig } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, MessageService } from "primeng/api";
import { map, Observable, of } from "rxjs";
@Component({
    selector: "gebo-ai-deep-search-admin-component",
    templateUrl: "gebo-deep-search-admin.component.html",
    standalone: false,
    providers: [MessageService,
        { provide: GEBO_AI_MODULE, useValue: "GeboAIDeepSearchConfigAdminModule", multi: false },
        {
            provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIDeepSearchConfigAdminComponent), multi: true
        }]
})
export class GeboAIDeepSearchConfigAdminComponent extends BaseEntityEditingComponent<DeepSearchConfig> {
    protected override entityName: string = "DeepSearchConfig";
    protected minThreashold: number = 0.5;
    protected maxThreashold: number = 1.0;
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        searchType: new FormControl(),
        userModified: new FormControl(),
        userCreated: new FormControl(),
        dateModified: new FormControl(),
        dateCreated: new FormControl(),
        consolidationPrompt: new FormControl(),
        analisysPrompt: new FormControl(),
        ragQueryOptions: new FormGroup({
            topK: new FormControl(),
            similarityThreashold: new FormControl(),
            maxTokens: new FormControl(),
            completeness: new FormControl()
        }),
        firstHopSimilarityThreashold: new FormControl(),
        secondHopSimilarityThreashold: new FormControl(),
        graphRagTopN: new FormControl(),
        tokensLimit: new FormControl(),
        chatModelConfiguration: new FormControl(),
        defaultConfig: new FormControl(),
        chatProfileCode: new FormControl()
    });
    protected defaultConfiguration?: DeepSearchConfig;
    protected searchType?: DeepSearchConfig.SearchTypeEnum = "MULTI_HOP";
    protected searchTypeOptions: { code: DeepSearchConfig.SearchTypeEnum, description: string }[] = [{ code: "MULTI_HOP", description: "Multi hop search" }, { code: "SINGLE_HOP", description: "Single semantic search" }];
    protected chatModelsData: GObjectRefGBaseModelConfig[] = [];
    protected defaultChatModel?: GBaseChatModelConfig;
    constructor(injector: Injector,
        geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService: GeboUIOutputForwardingService,
        private geboChatModels: ChatModelsControllerService,
        private deepSearchConfigService: GeboDeepSearchAdminControllerService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
        this.formGroup.controls["searchType"].valueChanges.subscribe({
            next: (value) => {
                this.searchType = value;
            }
        })
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
        }
        );
    }
    protected getform(frm: string): FormGroup {
        return this.formGroup.controls[frm] as FormGroup;
    }
    protected override onLoadedPersistentData(actualValue: DeepSearchConfig): void {

    }
    protected override onNewData(actualValue: DeepSearchConfig): void {
        this.loadingRelatedBackend = true;
        this.deepSearchConfigService.getDeepSearchSystemConfig().subscribe({
            next: (defaultCfg) => {
                this.defaultConfiguration = defaultCfg;
                const newValue: DeepSearchConfig = {
                    ...defaultCfg,
                    defaultConfig: actualValue.defaultConfig === true
                };
                this.formGroup.patchValue(newValue);
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        })
    }
    override findByCode(code: string): Observable<DeepSearchConfig | null> {
        return this.deepSearchConfigService.findDeepSearchDefaultConfigByCode(code);
    }
    override save(value: DeepSearchConfig): Observable<DeepSearchConfig> {
        return this.deepSearchConfigService.updateDeepSearchConfig(value);
    }
    override insert(value: DeepSearchConfig): Observable<DeepSearchConfig> {
        return this.deepSearchConfigService.insertDeepSearchConfig(value);
    }
    override delete(value: DeepSearchConfig): Observable<boolean> {
        return this.deepSearchConfigService.deleteDeepSearchConfig(value).pipe(map(val => true));
    }
    override canBeDeleted(value: DeepSearchConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

}