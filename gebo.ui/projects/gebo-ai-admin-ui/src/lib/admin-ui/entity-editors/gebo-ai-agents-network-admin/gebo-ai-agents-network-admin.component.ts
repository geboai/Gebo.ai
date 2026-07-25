import { afterNextRender, Component, ElementRef, forwardRef, Injector, OnInit, runInInjectionContext, ViewChild } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { GAgentsNetwork, GeboAgentAdminControllerService, GeboAgentsNetworkAdminControllerService, AgentNetworkParticipant, GBaseObject, GAgentConfig } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionType } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { initializeModel, NgDiagramNodeTemplateMap, NgDiagramConfig, provideNgDiagram, NgDiagramViewportService } from "ng-diagram";
import { AgentNodeComponent } from "./agent-node.component";

@Component({
    selector: "gebo-ai-agents-network-admin-component",
    templateUrl: "gebo-ai-agents-network-admin.component.html",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIAgentsNetworkAdminModule", multi: false },
        {
            provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIAgentsNetworkAdminComponent),
            multi: false
        },
        provideNgDiagram()
    ]
})
export class GeboAIAgentsNetworkAdminComponent extends BaseEntityEditingComponent<GAgentsNetwork> implements OnInit {
    protected override entityName: string = "GAgentsNetwork";
    private myInjector!: Injector;
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        userModified: new FormControl(),
        userCreated: new FormControl(),
        dateModified: new FormControl(),
        dateCreated: new FormControl(),
        maxLoopIteration: new FormControl(),
        scenarioDescription: new FormControl(),
        agents: new FormControl(),
        readOnly: new FormControl(),
        defaultUserInteractionNetwork: new FormControl(),
        accessibleToAll: new FormControl(),
        accessibleUsers: new FormControl(),
        accessibleGroups: new FormControl(),
        aclAliases: new FormControl()
    });

    protected availableAgents: GBaseObject[] = [];
    protected agentsList: AgentNetworkParticipant[] = [];

    public readonly: boolean = false;
    protected diagramModel: any;
    private lastLayoutNodes: { id: string; position: { x: number; y: number } }[] = [];
    @ViewChild("diagramHost", { read: ElementRef }) private diagramHostRef?: ElementRef<HTMLElement>;
    protected nodeTemplateMap = new NgDiagramNodeTemplateMap([
        ["agent", AgentNodeComponent]
    ]);
    protected diagramConfig: NgDiagramConfig = {
        viewportPanningEnabled: true,
        edgeRouting: { defaultRouting: "bezier" },
        zoom: {
            max: 3,
            zoomToFit: {
                onInit: true,
                padding: 40
            }
        }
    };

    // Participant modal configuration
    protected showParticipantDialog: boolean = false;
    protected isNewParticipant: boolean = true;
    protected dialogHeader: string = "";
    protected editingParticipant: AgentNetworkParticipant | null = null;
    protected parentParticipant: AgentNetworkParticipant | null = null;

    protected policyOptions = [
        { label: "Allow All", value: "ALLOW_ALL" },
        { label: "Deny All", value: "DENY_ALL" },
        { label: "Allow List", value: "ALLOW_LIST" },
        { label: "Deny List", value: "DENY_LIST" }
    ];

    protected participantFormGroup = new FormGroup({
        networkAgentName: new FormControl("", Validators.required),
        agentConfigCode: new FormControl("", Validators.required),
        agentContextualName: new FormControl(""),
        inputNode: new FormControl(false),
        outputNode: new FormControl(false),
        communicationPolicy: new FormControl("ALLOW_ALL", Validators.required),
        communicationList: new FormControl<string[]>([]),
        maxInvocations: new FormControl<number | null>(null),
        maxConsecutiveInvocations: new FormControl<number | null>(null),
        canCallTools: new FormControl(true),
        canCallOtherAgents: new FormControl(true)
    });

    constructor(
        injector: Injector,
        geboFormGroupsService: GeboFormGroupsService,
        protected myConfirmationService: ConfirmationService,
        protected actionsRouter: GeboUIActionRoutingService,
        outputForwardingService: GeboUIOutputForwardingService,
        private service: GeboAgentsNetworkAdminControllerService,
        private agentsService: GeboAgentAdminControllerService,
        private viewportService: NgDiagramViewportService
    ) {
        super(injector, geboFormGroupsService, myConfirmationService, actionsRouter, outputForwardingService);
        this.myInjector = injector;
        this.manageOperationStatus = true;
        this.formGroup.valueChanges.subscribe((value) => {
            const isReadOnly = value?.readOnly === true;
            if (this.readonly !== isReadOnly) {
                this.readonly = isReadOnly;
                if (this.readonly) {
                    this.participantFormGroup.disable();
                    this.formGroup.get("maxLoopIteration")?.disable({ emitEvent: false });
                    this.formGroup.get("defaultUserInteractionNetwork")?.disable({ emitEvent: false });
                } else {
                    this.participantFormGroup.enable();
                    this.formGroup.get("maxLoopIteration")?.enable({ emitEvent: false });
                    this.formGroup.get("defaultUserInteractionNetwork")?.enable({ emitEvent: false });
                }
                this.rebuildChart();
            }
        });
    }

    override ngOnInit(): void {
        super.ngOnInit();
        this.loadAvailableAgents();
    }

    protected loadAvailableAgents(selectCode?: string): void {
        this.loadingRelatedBackend = true;
        this.agentsService.getAgents().subscribe({
            next: (agents) => {
                this.availableAgents = agents || [];
                if (selectCode) {
                    this.participantFormGroup.controls.agentConfigCode.setValue(selectCode);
                }
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    override onLoadedPersistentData(actualValue: GAgentsNetwork): void {
        this.readonly = actualValue?.readOnly === true;
        this.agentsList = actualValue.agents ? [...actualValue.agents] : [];
        if (this.readonly) {
            this.participantFormGroup.disable();
            this.formGroup.get("maxLoopIteration")?.disable({ emitEvent: false });
            this.formGroup.get("defaultUserInteractionNetwork")?.disable({ emitEvent: false });
        } else {
            this.participantFormGroup.enable();
            this.formGroup.get("maxLoopIteration")?.enable({ emitEvent: false });
            this.formGroup.get("defaultUserInteractionNetwork")?.enable({ emitEvent: false });
        }
        this.rebuildChart();
    }

    override onNewData(actualValue: GAgentsNetwork): void {
        this.readonly = actualValue?.readOnly === true;
        this.agentsList = actualValue.agents ? [...actualValue.agents] : [];
        if (this.readonly) {
            this.participantFormGroup.disable();
            this.formGroup.get("maxLoopIteration")?.disable({ emitEvent: false });
            this.formGroup.get("defaultUserInteractionNetwork")?.disable({ emitEvent: false });
        } else {
            this.participantFormGroup.enable();
            this.formGroup.get("maxLoopIteration")?.enable({ emitEvent: false });
            this.formGroup.get("defaultUserInteractionNetwork")?.enable({ emitEvent: false });
        }
        this.rebuildChart();
    }

    public getAgentDescription(code: string): string {
        const found = this.availableAgents.find(a => a.code === code);
        return found ? found.description || code : code;
    }

    protected rebuildChart(): void {
        if (!this.myInjector) {
            return;
        }
        runInInjectionContext(this.myInjector, () => {
            const list = this.agentsList || [];
            if (list.length === 0) {
                this.diagramModel = initializeModel({ nodes: [], edges: [] });
                return;
            }

            // Collect all target communication names to identify root nodes
            const targetNames = new Set<string>();
            list.forEach(agent => {
                if (agent.communicationList) {
                    agent.communicationList.forEach(name => {
                        if (name) {
                            targetNames.add(name);
                        }
                    });
                }
            });

            // Roots are agents whose networkAgentName is not referenced in any communicationList
            let roots = list.filter(agent => agent.networkAgentName && !targetNames.has(agent.networkAgentName));

            // If there is a cycle and no clear root, fallback to inputNode or first node
            if (roots.length === 0 && list.length > 0) {
                const inputNode = list.find(a => a.inputNode);
                roots = inputNode ? [inputNode] : [list[0]];
            }

            // BFS to assign hierarchy levels
            const levelsMap = new Map<string, number>();
            const visited = new Set<string>();
            const queue: { agent: AgentNetworkParticipant; level: number }[] = [];

            roots.forEach(r => {
                if (r.networkAgentName) {
                    queue.push({ agent: r, level: 0 });
                }
            });

            while (queue.length > 0) {
                const { agent, level } = queue.shift()!;
                const name = agent.networkAgentName || "";
                if (visited.has(name)) {
                    continue;
                }
                visited.add(name);
                levelsMap.set(name, level);

                if (agent.communicationList) {
                    agent.communicationList.forEach(childName => {
                        const childAgent = list.find(a => a.networkAgentName === childName);
                        if (childAgent && childName && !visited.has(childName)) {
                            queue.push({ agent: childAgent, level: level + 1 });
                        }
                    });
                }
            }

            // Default all unreached/isolated agents to level 0
            list.forEach(a => {
                if (a.networkAgentName && !levelsMap.has(a.networkAgentName)) {
                    levelsMap.set(a.networkAgentName, 0);
                }
            });

            // Group nodes by level to compute layout positioning
            const levelGroups = new Map<number, string[]>();
            levelsMap.forEach((level, name) => {
                if (!levelGroups.has(level)) {
                    levelGroups.set(level, []);
                }
                levelGroups.get(level)!.push(name);
            });

            const nodes: any[] = [];
            const edges: any[] = [];

            list.forEach(agent => {
                const name = agent.networkAgentName || "";
                const L = levelsMap.get(name) || 0;
                const idxInLevel = levelGroups.get(L)?.indexOf(name) || 0;
                const totalInLevel = levelGroups.get(L)?.length || 1;

                // X centered around 350px, Y spaced by 200px per level
                const x = (idxInLevel - (totalInLevel - 1) / 2) * 280 + 350;
                const y = L * 200 + 50;

                nodes.push({
                    id: name,
                    position: { x, y },
                    draggable: !this.readonly,
                    type: "agent",
                    data: agent
                });

                if (agent.communicationList) {
                    agent.communicationList.forEach(childName => {
                        if (childName) {
                            edges.push({
                                id: `${name}-${childName}`,
                                source: name,
                                target: childName
                            });
                        }
                    });
                }
            });

            this.diagramModel = initializeModel({ nodes, edges });
            this.lastLayoutNodes = nodes;

            // The Graph tab panel is display:none until selected, so <ng-diagram>
            // initializes against a zero-size viewport and its internal viewport
            // metadata cache never recovers once real data arrives (a measured
            // library quirk, not just a render-timing race: zoomToFit() keeps
            // computing against that stale zero/near-zero size even long after
            // the container is visible). Compute the fit ourselves from real DOM
            // measurements instead of trusting the library's cached viewport size.
            afterNextRender(() => {
                this.fitDiagramToViewport();
            }, { injector: this.myInjector });
        });
    }

    protected onTabChange(value: string | number | undefined): void {
        if (value === 1 && this.diagramModel) {
            afterNextRender(() => {
                this.fitDiagramToViewport();
            }, { injector: this.myInjector });
        }
    }

    private fitDiagramToViewport(): void {
        const hostEl = this.diagramHostRef?.nativeElement;
        if (!hostEl || this.lastLayoutNodes.length === 0) {
            return;
        }
        const padding = 40;
        let minX = Infinity, minY = Infinity, maxX = -Infinity, maxY = -Infinity;
        for (const node of this.lastLayoutNodes) {
            const nodeEl = hostEl.querySelector<HTMLElement>(`[data-node-id="${node.id}"]`);
            const width = nodeEl?.offsetWidth || 220;
            const height = nodeEl?.offsetHeight || 100;
            minX = Math.min(minX, node.position.x);
            minY = Math.min(minY, node.position.y);
            maxX = Math.max(maxX, node.position.x + width);
            maxY = Math.max(maxY, node.position.y + height);
        }
        const boundsWidth = maxX - minX;
        const boundsHeight = maxY - minY;
        const viewportWidth = hostEl.clientWidth;
        const viewportHeight = hostEl.clientHeight;
        if (boundsWidth <= 0 || boundsHeight <= 0 || viewportWidth <= 0 || viewportHeight <= 0) {
            return;
        }
        const scale = Math.min(
            (viewportWidth - 2 * padding) / boundsWidth,
            (viewportHeight - 2 * padding) / boundsHeight,
            1
        );
        const x = (viewportWidth - boundsWidth * scale) / 2 - minX * scale;
        // ng-diagram-canvas is positioned with its own vertical origin one full
        // viewport-height below the host element (verified empirically), so the
        // Y translate has to compensate for that fixed offset; X has no such offset.
        const y = (viewportHeight - boundsHeight * scale) / 2 - minY * scale - viewportHeight;
        this.viewportService.setViewport(x, y, scale);
    }

    public editAgentConfig(code: string | null | undefined): void {
        if (!code) return;
        this.actionsRouter.routeEvent({
            actionType: GeboActionType.OPEN,
            context: {},
            contextType: "GeboAIAgentsNetworkAdminComponent",
            targetType: "GAgentConfig",
            target: { code: code },
            onActionPerformed: (event) => {
                this.loadAvailableAgents(code);
            }
        });
    }

    public addAgentConfig(): void {
        this.actionsRouter.routeEvent({
            actionType: GeboActionType.NEW,
            context: {},
            contextType: "GeboAIAgentsNetworkAdminComponent",
            targetType: "GAgentConfig",
            target: {
                accessibleToAll: true,
                enabledFunctions: [],
                maxLoopIterations: 5,
                subscribeAllTools: true,
                useDefaultChatModel: true,
                defaultConfiguration: true,
                description: "New Agent Configuration"
            } as any,
            onActionPerformed: (event) => {
                this.loadAvailableAgents(event.target?.code);
            }
        });
    }

    protected updateFormGroup(): void {
        this.formGroup.controls["agents"].setValue(this.agentsList);
        this.formGroup.controls["agents"].markAsDirty();
        this.rebuildChart();
    }

    protected openAddParticipant(): void {
        this.isNewParticipant = true;
        this.dialogHeader = "Add Agent Participant";
        this.editingParticipant = null;
        this.parentParticipant = null;
        this.participantFormGroup.reset({
            networkAgentName: "",
            agentConfigCode: "",
            agentContextualName: "",
            inputNode: false,
            outputNode: false,
            communicationPolicy: "ALLOW_ALL",
            communicationList: [],
            maxInvocations: null,
            maxConsecutiveInvocations: null,
            canCallTools: true,
            canCallOtherAgents: true
        });
        this.showParticipantDialog = true;
    }

    public openAddChild(parent: AgentNetworkParticipant): void {
        this.isNewParticipant = true;
        this.dialogHeader = `Add Child Agent to ${parent.networkAgentName}`;
        this.editingParticipant = null;
        this.parentParticipant = parent;
        this.participantFormGroup.reset({
            networkAgentName: "",
            agentConfigCode: "",
            agentContextualName: "",
            inputNode: false,
            outputNode: false,
            communicationPolicy: "ALLOW_ALL",
            communicationList: [],
            maxInvocations: null,
            maxConsecutiveInvocations: null,
            canCallTools: true,
            canCallOtherAgents: true
        });
        this.showParticipantDialog = true;
    }

    public openEditParticipant(participant: AgentNetworkParticipant): void {
        this.isNewParticipant = false;
        this.dialogHeader = `Edit Agent Participant: ${participant.networkAgentName}`;
        this.editingParticipant = participant;
        this.parentParticipant = null;
        this.participantFormGroup.patchValue({
            networkAgentName: participant.networkAgentName || "",
            agentConfigCode: participant.agentConfigCode || "",
            agentContextualName: participant.agentContextualName || "",
            inputNode: participant.inputNode || false,
            outputNode: participant.outputNode || false,
            communicationPolicy: participant.communicationPolicy || "ALLOW_ALL",
            communicationList: participant.communicationList || [],
            maxInvocations: participant.maxInvocations || null,
            maxConsecutiveInvocations: participant.maxConsecutiveInvocations || null,
            canCallTools: participant.canCallTools !== false,
            canCallOtherAgents: participant.canCallOtherAgents !== false
        });
        this.showParticipantDialog = true;
    }

    protected saveParticipant(): void {
        if (this.participantFormGroup.invalid) {
            return;
        }

        const value = this.participantFormGroup.value;
        const participant: AgentNetworkParticipant = {
            networkAgentName: value.networkAgentName!,
            agentConfigCode: value.agentConfigCode!,
            agentContextualName: value.agentContextualName || undefined,
            inputNode: value.inputNode || undefined,
            outputNode: value.outputNode || undefined,
            communicationPolicy: value.communicationPolicy as AgentNetworkParticipant.CommunicationPolicyEnum,
            communicationList: (value.communicationList && value.communicationList.length > 0) ? value.communicationList : undefined,
            maxInvocations: value.maxInvocations !== null && value.maxInvocations !== undefined ? Number(value.maxInvocations) : undefined,
            maxConsecutiveInvocations: value.maxConsecutiveInvocations !== null && value.maxConsecutiveInvocations !== undefined ? Number(value.maxConsecutiveInvocations) : undefined,
            canCallTools: value.canCallTools ?? true,
            canCallOtherAgents: value.canCallOtherAgents ?? true
        };

        const existingWithSameName = this.agentsList.find(a => a.networkAgentName === participant.networkAgentName);

        if (this.isNewParticipant) {
            if (existingWithSameName) {
                this.userMessages = [{
                    severity: "error",
                    summary: "Duplicate Name",
                    detail: `An agent with the name "${participant.networkAgentName}" already exists in this network.`
                }];
                return;
            }

            this.agentsList.push(participant);

            if (this.parentParticipant) {
                const parentNode = this.agentsList.find(a => a.networkAgentName === this.parentParticipant!.networkAgentName);
                if (parentNode) {
                    if (!parentNode.communicationList) {
                        parentNode.communicationList = [];
                    }
                    parentNode.communicationList.push(participant.networkAgentName!);
                }
            }
        } else {
            if (existingWithSameName && existingWithSameName !== this.editingParticipant) {
                this.userMessages = [{
                    severity: "error",
                    summary: "Duplicate Name",
                    detail: `An agent with the name "${participant.networkAgentName}" already exists in this network.`
                }];
                return;
            }

            const oldName = this.editingParticipant!.networkAgentName;
            const newName = participant.networkAgentName;

            const idx = this.agentsList.findIndex(a => a.networkAgentName === oldName);
            if (idx !== -1) {
                this.agentsList[idx] = participant;
            }

            if (oldName && newName && oldName !== newName) {
                this.agentsList.forEach(a => {
                    if (a.communicationList) {
                        a.communicationList = a.communicationList.map(name => name === oldName ? newName : name);
                    }
                });
            }
        }

        if (participant.inputNode) {
            this.agentsList.forEach(a => {
                if (a.networkAgentName !== participant.networkAgentName) {
                    a.inputNode = false;
                }
            });
        }

        this.showParticipantDialog = false;
        this.updateFormGroup();
    }

    public deleteParticipant(participant: AgentNetworkParticipant): void {
        this.myConfirmationService.confirm({
            message: `Are you sure you want to remove "${participant.networkAgentName}" from the network? All communication links pointing to this agent will be removed.`,
            header: "Confirm Participant Removal",
            icon: "pi pi-exclamation-triangle",
            accept: () => {
                const name = participant.networkAgentName;
                this.agentsList = this.agentsList.filter(a => a.networkAgentName !== name);

                if (name) {
                    this.agentsList.forEach(a => {
                        if (a.communicationList) {
                            a.communicationList = a.communicationList.filter(childName => childName !== name);
                        }
                    });
                }

                this.updateFormGroup();
            }
        });
    }

    protected clearNetwork(): void {
        this.myConfirmationService.confirm({
            message: "Are you sure you want to clear all agents from the network?",
            header: "Confirm Clear Network",
            icon: "pi pi-exclamation-triangle",
            accept: () => {
                this.agentsList = [];
                this.updateFormGroup();
            }
        });
    }

    // Returns potential communication targets (all other network agents except the current one)
    protected getPotentialTargets(currentName?: string): string[] {
        return this.agentsList
            .map(a => a.networkAgentName || "")
            .filter(name => name !== "" && name !== currentName);
    }

    override findByCode(code: string): Observable<GAgentsNetwork | null> {
        return this.service.getAgentsNetworkByCode(code);
    }

    override save(value: GAgentsNetwork): Observable<GAgentsNetwork> {
        return this.service.updateAgentsNetwork(value).pipe(map(_value => {
            this.assignBackendMessages(_value.messages);
            return _value.result ? _value.result : value;
        }));
    }

    override insert(value: GAgentsNetwork): Observable<GAgentsNetwork> {
        return this.service.insertAgentsNetwork(value).pipe(map(_value => {
            this.assignBackendMessages(_value.messages);
            return _value.result ? _value.result : value;
        }));
    }

    override delete(value: GAgentsNetwork): Observable<boolean> {
        return this.service.deleteAgentsNetwork(value).pipe(map(_value => {
            this.assignBackendMessages(_value.messages);
            return true;
        }));
    }

    override canBeDeleted(value: GAgentsNetwork): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ "canBeDeleted": false, "message": "" });
    }
}