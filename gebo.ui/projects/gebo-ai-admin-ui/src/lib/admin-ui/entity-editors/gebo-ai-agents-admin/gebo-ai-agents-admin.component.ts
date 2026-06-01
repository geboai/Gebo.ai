import { Component, forwardRef, inject, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { ChatModelsControllerService, ConfigurationEntry, GAgentConfig, GBaseObject, GeboAgentAdminControllerService, GPromptTemplateConfig } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { forkJoin, map, Observable, of } from "rxjs";
@Component({
    selector: "gebo-ai-agent-admin-component",
    templateUrl: "gebo-ai-agents-admin.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAIAgentsAdminModule", multi: false },
    {
        provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIAgentsAdminComponent),
        multi: false
    }]
})
export class GeboAIAgentsAdminComponent extends BaseEntityEditingComponent<GAgentConfig> {
    protected override entityName: string = "GAgentConfig";
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        userModified: new FormControl(),
        userCreated: new FormControl(),
        dateModified: new FormControl(),
        dateCreated: new FormControl(),
        agentServiceId: new FormControl(),
        mainLoopPromptUseCode: new FormControl(),
        customLoopPrompt: new FormControl(),
        completeEvaluationPromptUseCode: new FormControl(),
        completeEvaluationPrompt: new FormControl(),
        subscribeAllTools: new FormControl(),
        choosedModel: new FormControl(),
        maxLoopIterations: new FormControl(),
        topP: new FormControl(),
        accessibleGroups: new FormControl(),
        accessibleUsers: new FormControl(),
        accessibleToAll: new FormControl(),
        enabledFunctions: new FormControl()
    });
    protected chatModelsData: ConfigurationEntry[] = [];
    protected agentTypes: GBaseObject[] = [];
    protected promptLoadingObservable: Observable<GPromptTemplateConfig[]>=of([]);
    public constructor(injector: Injector,
        geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService: GeboUIOutputForwardingService,
        private agentsAdminService: GeboAgentAdminControllerService,
        private geboChatModelsService: ChatModelsControllerService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
        this.formGroup.controls["subscribeAllTools"].valueChanges.subscribe({
            next: (received) => {
                if (received === true)
                    this.formGroup.controls["enabledFunctions"].disable();
                else
                    this.formGroup.controls["enabledFunctions"].enable();
            }
        });
        this.formGroup.controls["agentServiceId"].valueChanges.subscribe(agentServiceId=>{
            if (agentServiceId) {
                this.promptLoadingObservable=this.agentsAdminService.getPromptTemplatesByAgentId(agentServiceId).pipe(map(x=>{return x?.map(y=>{y.code=y.promptUse; return y;})}));
             }
        });
    }
    override ngOnInit(): void {
        super.ngOnInit();
        this.loadingRelatedBackend = true;
        const observables: [Observable<ConfigurationEntry[]>, Observable<GBaseObject[]>] = [this.geboChatModelsService.getRuntimeConfiguredChatModels(), this.agentsAdminService.getAgentsChoices()];
        forkJoin(observables).subscribe({
            next: (values) => {
                this.chatModelsData = values[0];
                this.agentTypes = values[1];
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }

        });
    }
    override findByCode(code: string): Observable<GAgentConfig | null> {
        return this.agentsAdminService.getAgentByCode(code);
    }
    override save(value: GAgentConfig): Observable<GAgentConfig> {
        return this.agentsAdminService.updateAgent(value);
    }
    override insert(value: GAgentConfig): Observable<GAgentConfig> {
        return this.agentsAdminService.insertAgent(value);
    }
    override delete(value: GAgentConfig): Observable<boolean> {
        return this.agentsAdminService.deleteAgent(value).pipe(map(x => true));
    }
    override canBeDeleted(value: GAgentConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: false, message: "" });
    }

}