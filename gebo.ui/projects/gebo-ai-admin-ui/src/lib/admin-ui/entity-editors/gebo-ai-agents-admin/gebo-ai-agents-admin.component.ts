import { Component, forwardRef, inject, Injector } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ChatModelsControllerService, ConfigurationEntryGBaseChatModelConfig, GAgentConfig, GBaseChatModelConfig, GBaseObject, GeboAgentAdminControllerService, GPromptTemplateConfig } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from "primeng/api";
import { forkJoin, map, Observable, of } from "rxjs";
const systemAgentService:ToastMessageOptions={id:"systemAgentService",summary:"Default system agent",detail:"This is a default system generated agent, you cannot change its configuration",severity:"warn",life:5000};
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
        chatModelReference: new FormControl(),
        useDefaultChatModel: new FormControl(),
        maxLoopIterations: new FormControl(),
        topP: new FormControl(),
        accessibleGroups: new FormControl(),
        accessibleUsers: new FormControl(),
        accessibleToAll: new FormControl(),
        enabledFunctions: new FormControl(),
        aclAliases: new FormControl(),
        defaultConfiguration: new FormControl(),
        temperature: new FormControl(),
        thinking: new FormControl(),
        readOnly: new FormControl()

    });
    protected chatModelsData: ConfigurationEntryGBaseChatModelConfig[] = [];
    protected agentTypes: GBaseObject[] = [];
    protected promptLoadingObservable: Observable<GPromptTemplateConfig[]> = of([]);
    protected defaultChatModel?: ConfigurationEntryGBaseChatModelConfig;
    protected isChooseChatModel: boolean = false;
    protected thinkingOptions: { code: GBaseChatModelConfig.ThinkingEnum, description: string }[] = [{ code: "NO_THINKING", description: "Disabled" }, { code: "AUTO", description: "Automatic" }, { code: "LOW_THINKING", description: "Low" }, { code: "MEDIUM_THINKING", description: "Medium" }, { code: "HIGH_THINKING", description: "High" }];
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
        this.formGroup.controls["agentServiceId"].valueChanges.subscribe(agentServiceId => {
            if (agentServiceId) {
                this.promptLoadingObservable = this.agentsAdminService.getPromptTemplatesByAgentId(agentServiceId).pipe(map(x => { return x?.map(y => { y.code = y.promptUse; return y; }) }));
            }
        });
        // Enable/disable chat model selection based on useDefaultChatModel value
        this.formGroup.controls["useDefaultChatModel"].valueChanges.subscribe(
            x => {

                this.isChooseChatModel = x === true;
                this.setControlEnabledAndRequired("chatModelReference", !this.isChooseChatModel);

            }
        );
    }
    protected override onLoadedPersistentData(actualValue: GAgentConfig): void {
        if (actualValue?.readOnly===true) {
            this.userMessages=[systemAgentService];
        }
        super.onLoadedPersistentData(actualValue);
    }
    /**
     * Adds or removes the required validator for a form control
     * 
     * @param ctrlName Name of the form control
     * @param required Whether the control should be required
     */
    private setControlRequired(ctrlName: string, required: boolean) {
        const ctrl = this.formGroup.controls[ctrlName];
        if (required) {
            if (ctrl.hasValidator(Validators.required)) {
                ctrl.clearValidators();
                this.formGroup.updateValueAndValidity();
            }
        } else {
            if (!ctrl.hasValidator(Validators.required)) {
                ctrl.setValidators(Validators.required);
                this.formGroup.updateValueAndValidity();
            }
        }
    }
    /**
   * Enables or disables a form control and sets its required status accordingly
   * 
   * @param ctrlName Name of the form control
   * @param enabled Whether the control should be enabled
   */
    private setControlEnabledAndRequired(ctrlName: string, enabled: boolean) {
        const ctrl = this.formGroup.controls[ctrlName];
        if (ctrl.enabled !== enabled) {
            if (enabled === true) {
                ctrl.enable();
                this.setControlRequired(ctrlName, true);
            } else {
                ctrl.disable();
                this.setControlRequired(ctrlName, false);
            }
        }
        this.formGroup.updateValueAndValidity();
    }
    override ngOnInit(): void {
        super.ngOnInit();
        this.loadingRelatedBackend = true;
        const observables: [Observable<ConfigurationEntryGBaseChatModelConfig[]>, Observable<GBaseObject[]>] = [this.geboChatModelsService.getRuntimeConfiguredChatModels(), this.agentsAdminService.getAgentsChoices()];
        forkJoin(observables).subscribe({
            next: (values) => {
                this.chatModelsData = values[0];
                if (values[0]) {
                    this.defaultChatModel = values[0].find(x => x.configuration?.defaultModel === true);
                }
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
        return of({ canBeDeleted: true, message: "" });
    }

}