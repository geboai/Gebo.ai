import { Component, forwardRef, Injector, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { GAgentsNetwork, GeboAgentAdminControllerService, GeboAgentsNetworkAdminControllerService, AgentNetworkParticipant, GBaseObject } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, TreeNode } from "primeng/api";
import { map, Observable, of } from "rxjs";

@Component({
    selector: "gebo-ai-agents-network-admin-component",
    templateUrl: "gebo-ai-agents-network-admin.component.html",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIAgentsNetworkAdminModule", multi: false },
        {
            provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIAgentsNetworkAdminComponent),
            multi: false
        }
    ]
})
export class GeboAIAgentsNetworkAdminComponent extends BaseEntityEditingComponent<GAgentsNetwork> implements OnInit {
    protected override entityName: string = "GAgentsNetwork";
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
        defaultUserInteractionNetwork: new FormControl()
    });

    protected availableAgents: GBaseObject[] = [];
    protected agentsList: AgentNetworkParticipant[] = [];
    protected chartData: TreeNode[] = [];

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
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService: GeboUIOutputForwardingService,
        private service: GeboAgentsNetworkAdminControllerService,
        private agentsService: GeboAgentAdminControllerService
    ) {
        super(injector, geboFormGroupsService, myConfirmationService, geboUIActionRoutingService, outputForwardingService);
        this.manageOperationStatus = true;
    }

    override ngOnInit(): void {
        super.ngOnInit();
        this.loadingRelatedBackend = true;
        this.agentsService.getAgents().subscribe({
            next: (agents) => {
                this.availableAgents = agents || [];
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    override onLoadedPersistentData(actualValue: GAgentsNetwork): void {
        this.agentsList = actualValue.agents ? [...actualValue.agents] : [];
        this.rebuildChart();
    }

    override onNewData(actualValue: GAgentsNetwork): void {
        this.agentsList = actualValue.agents ? [...actualValue.agents] : [];
        this.rebuildChart();
    }

    protected getAgentDescription(code: string): string {
        const found = this.availableAgents.find(a => a.code === code);
        return found ? found.description || code : code;
    }

    protected rebuildChart(): void {
        const list = this.agentsList || [];
        if (list.length === 0) {
            this.chartData = [];
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

        const visited = new Set<string>();
        this.chartData = roots.map(root => this.buildTreeNode(root, visited, list));
    }

    private buildTreeNode(
        agent: AgentNetworkParticipant,
        visited: Set<string>,
        allAgents: AgentNetworkParticipant[]
    ): TreeNode {
        const agentName = agent.networkAgentName || "";

        if (visited.has(agentName)) {
            return {
                label: agentName,
                data: agent,
                type: "default",
                styleClass: "node-cycle border-2 border-red-500 bg-red-50",
                children: []
            };
        }

        const localVisited = new Set(visited);
        if (agentName) {
            localVisited.add(agentName);
        }

        const children: TreeNode[] = [];
        if (agent.communicationList) {
            agent.communicationList.forEach(childName => {
                const childAgent = allAgents.find(a => a.networkAgentName === childName);
                if (childAgent) {
                    children.push(this.buildTreeNode(childAgent, localVisited, allAgents));
                }
            });
        }

        return {
            label: agentName || agent.agentConfigCode,
            data: agent,
            type: "default",
            expanded: true,
            children: children
        };
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

    protected openAddChild(parent: AgentNetworkParticipant): void {
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

    protected openEditParticipant(participant: AgentNetworkParticipant): void {
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

    protected deleteParticipant(participant: AgentNetworkParticipant): void {
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