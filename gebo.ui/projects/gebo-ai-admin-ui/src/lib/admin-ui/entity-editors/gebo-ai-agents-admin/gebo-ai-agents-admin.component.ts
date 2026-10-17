import { Component, forwardRef, inject, Injector } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { AgentServiceDescriptor, ChatModelsControllerService, ConfigurationEntryGBaseChatModelConfig, GAgentConfig, GAgentRole, GAgentsNetwork, GBaseChatModelConfig, GBaseObject, GeboAgentAdminControllerService, GeboAgentsNetworkAdminControllerService, GPromptTemplateConfig } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, ExtendedConfirmation, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAITranslationService, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from "primeng/api";
import { forkJoin, map, Observable, of, take } from "rxjs";
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
        agentRoleCode: new FormControl(null, Validators.required),
        agentType: new FormControl(),
        adaptedAgentNetworkCode: new FormControl(),
        agentNetworkServiceCode: new FormControl(),
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
    /** The roles an agent can take; the role is mandatory. */
    protected roles: GAgentRole[] = [];
    /** The services with their types and kinds, by service id. */
    protected serviceDescriptors: AgentServiceDescriptor[] = [];
    /** The networks an adapter service can run as one agent. */
    protected networks: GBaseObject[] = [];
    protected promptLoadingObservable: Observable<GPromptTemplateConfig[]> = of([]);
    protected defaultChatModel?: ConfigurationEntryGBaseChatModelConfig;
    protected isChooseChatModel: boolean = false;
    protected thinkingOptions: { code: GBaseChatModelConfig.ThinkingEnum, description: string }[] = [{ code: "NO_THINKING", description: "Disabled" }, { code: "AUTO", description: "Automatic" }, { code: "LOW_THINKING", description: "Low" }, { code: "MEDIUM_THINKING", description: "Medium" }, { code: "HIGH_THINKING", description: "High" }];
    public constructor(injector: Injector,
        geboFormGroupsService: GeboFormGroupsService,
        protected myConfirmationService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService: GeboUIOutputForwardingService,
        private agentsAdminService: GeboAgentAdminControllerService,
        private geboChatModelsService: ChatModelsControllerService,
        private networksService: GeboAgentsNetworkAdminControllerService) {
        super(injector, geboFormGroupsService, myConfirmationService, geboUIActionRoutingService, outputForwardingService);
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
                // a prompt template is chosen by its use; the ones without a description are listed by it
                this.promptLoadingObservable = this.agentsAdminService.getPromptTemplatesByAgentId(agentServiceId).pipe(map(x => { return x?.map(y => { y.code = y.promptUse; y.description = y.description || y.promptUse; return y; }) }));
            }
            // a network adapter service runs a whole network as one agent: the
            // configuration says which network, and the agent type says so
            const adapter = this.isNetworkAdapterService(agentServiceId);
            const agentType = adapter ? "AGENTS_NETWORK" : "AGENT";
            if (this.formGroup.controls["agentType"].value !== agentType) {
                console.debug("[agents-admin] service " + agentServiceId + " makes the agent type " + agentType);
                this.formGroup.controls["agentType"].setValue(agentType);
            }
            if (!adapter && (this.formGroup.controls["adaptedAgentNetworkCode"].value || this.formGroup.controls["agentNetworkServiceCode"].value)) {
                this.formGroup.controls["adaptedAgentNetworkCode"].setValue(null);
                this.formGroup.controls["agentNetworkServiceCode"].setValue(null);
            }
        });
        // the adapted network tells the network service (its factory) that runs it
        this.formGroup.controls["adaptedAgentNetworkCode"].valueChanges.subscribe(code => {
            if (!code) {
                if (this.formGroup.controls["agentNetworkServiceCode"].value) {
                    this.formGroup.controls["agentNetworkServiceCode"].setValue(null);
                }
                return;
            }
            this.networksService.getAgentsNetworkByCode(code).subscribe({
                next: (network: GAgentsNetwork | null) => {
                    const factoryId = network?.agentsNetworkServiceFactoryId || null;
                    console.debug("[agents-admin] adapted network " + code + " runs on " + factoryId);
                    if (this.formGroup.controls["agentNetworkServiceCode"].value !== factoryId) {
                        this.formGroup.controls["agentNetworkServiceCode"].setValue(factoryId);
                    }
                },
                error: (error) => console.error("Cannot read the adapted network " + code, error)
            });
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
        const observables: [Observable<ConfigurationEntryGBaseChatModelConfig[]>, Observable<GBaseObject[]>, Observable<GAgentRole[]>, Observable<AgentServiceDescriptor[]>, Observable<GBaseObject[]>] =
            [this.geboChatModelsService.getRuntimeConfiguredChatModels(), this.agentsAdminService.getAgentsChoices(), this.agentsAdminService.getAgentRoles(), this.networksService.getAgentServices(), this.networksService.getAgentsNetwork()];
        forkJoin(observables).subscribe({
            next: (values) => {
                this.chatModelsData = values[0];
                if (values[0]) {
                    this.defaultChatModel = values[0].find(x => x.configuration?.defaultModel === true);
                }
                this.agentTypes = values[1];
                this.roles = values[2] || [];
                this.serviceDescriptors = values[3] || [];
                this.networks = values[4] || [];
                console.debug("[agents-admin] " + this.agentTypes.length + " service(s), " + this.roles.length + " role(s), "
                    + this.serviceDescriptors.length + " descriptor(s), " + this.networks.length + " network(s)");
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }

        });
    }
    /**
     * Copies the agent and goes on editing the copy in place of the original: this is
     * also how a read-only system agent becomes an agent that can be changed. Opened
     * from a network participant, the participant takes the copy.
     */
    protected copyAgent(): void {
        const code = this.entity?.code;
        if (!code) return;
        if (!this.formGroup.dirty) {
            this.doCopyAgent(code);
            return;
        }
        const confirmation: ExtendedConfirmation = {
            id: "CopyConfirmation",
            message: "The changes not saved are not copied and will be lost: copy the saved agent?",
            header: "Copy the agent",
            icon: "pi pi-exclamation-triangle",
            accept: () => this.doCopyAgent(code)
        };
        this.injector.get(GeboAITranslationService).translateConfirmation("GeboAIAgentsAdminModule", "GAgentConfig", confirmation)
            .pipe(take(1)).subscribe(translated => this.myConfirmationService.confirm(translated ?? confirmation));
    }

    private doCopyAgent(code: string): void {
        this.loadingRelatedBackend = true;
        this.agentsAdminService.copyAgent(code).subscribe({
            next: (status) => {
                this.assignBackendMessages(status?.messages);
                const copy = status?.result;
                if (copy?.code) {
                    // the copy takes the place of the original in this editor, and whoever
                    // opened the editor (a network participant) learns an agent was created
                    this.entity = copy;
                    this.formGroup.markAsPristine();
                    this.doReloadByCode();
                    this.createdNew.emit(copy);
                } else {
                    this.loadingRelatedBackend = false;
                }
            },
            error: (error) => {
                this.loadingRelatedBackend = false;
                this.userMessages = [{
                    id: "COPY-ERROR",
                    severity: "error",
                    summary: "Copy failed",
                    detail: "Server error copying the agent: " + (error?.message ?? error)
                }];
            }
        });
    }

    /** The descriptor of the service chosen in the form, with its types and kind. */
    protected selectedServiceDescriptor(): AgentServiceDescriptor | undefined {
        const serviceId = this.formGroup.controls["agentServiceId"].value;
        return serviceId ? this.serviceDescriptors.find(s => s.serviceId === serviceId) : undefined;
    }

    protected isNetworkAdapterService(serviceId: string | null | undefined): boolean {
        return !!serviceId && this.serviceDescriptors.some(s => s.serviceId === serviceId && s.networkAdapter === true);
    }

    /** The simple name of a type, what the hints show. */
    protected shortType(type: string | undefined): string {
        if (!type) {
            return "?";
        }
        const simple = type.substring(type.lastIndexOf(".") + 1);
        return simple === "List" ? "List<Document>" : simple;
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