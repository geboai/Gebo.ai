/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
import { afterNextRender, Component, DestroyRef, ElementRef, forwardRef, Injector, OnInit, runInInjectionContext, ViewChild } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { AgenticChatDefaultNetworksAdminControllerService, GAgentsNetwork, GeboAgentAdminControllerService, GeboAgentsNetworkAdminControllerService, AgentNetworkParticipant, GAgentConfig, AgentMountedTools, AgentServiceDescriptor, NetworkServiceFactoryDescriptor, AgentTypeCompatibility, GUserMessage } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionType, GeboActionPerformedType, GeboAITranslationService, ExtendedConfirmation } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { forkJoin, map, Observable, of, take } from "rxjs";
import { initializeModel, NgDiagramNodeTemplateMap, NgDiagramConfig, provideNgDiagram, NgDiagramViewportService, EdgeDrawEndedEvent, SelectionChangedEvent } from "ng-diagram";
import { AgentNodeComponent } from "./agent-node.component";

/** The verdict on an edge the admin wants to draw, with the reason the rules give. */
export interface EdgeVerdict {
    ok: boolean;
    reason: string;
}

/** A group of the palette: the services of one kind. */
interface PaletteGroup {
    id: string;
    kind: AgentServiceDescriptor.KindEnum;
    label: string;
    services: AgentServiceDescriptor[];
}

/**
 * The composer of a network of agents: the factory the network runs on fixes its
 * contract (the input its input nodes must accept, the output its output nodes
 * must produce), a palette offers the agent services grouped by what they do in
 * a network, the graph shows the participants with their typed ports and the
 * edges of their communication lists, the inspector edits the selected
 * participant and the checks rail shows what the backend validation answers on
 * every change, so that a network is saved only when it would be accepted.
 */
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
        suggestedPurpose: new FormControl(),
        agentsNetworkServiceFactoryId: new FormControl(null, Validators.required),
        agents: new FormControl(),
        readOnly: new FormControl(),
        choosableForPipelineTypes: new FormControl(),
        accessibleToAll: new FormControl(),
        accessibleUsers: new FormControl(),
        accessibleGroups: new FormControl(),
        aclAliases: new FormControl()
    });

    /** Every agent configuration, whole: the participants bind to them. */
    protected configs: GAgentConfig[] = [];
    /** The agent services that can take part in a network, reactive ones included. */
    protected services: AgentServiceDescriptor[] = [];
    protected factories: NetworkServiceFactoryDescriptor[] = [];
    /** For each message type, the types assignable to it, as the backend has them. */
    private assignable = new Map<string, Set<string>>();
    protected paletteGroups: PaletteGroup[] = [];
    protected agentsList: AgentNetworkParticipant[] = [];
    /** The tools each agent configuration mounts, by configuration code. */
    private mountedTools = new Map<string, AgentMountedTools>();
    /**
     * The colour of each tool category in the graph. Only calm tones: red, orange
     * and yellow are kept for errors and warnings.
     */
    private static readonly TOOL_CATEGORY_CLASSES = [
        "bg-blue-50 text-blue-700", "bg-teal-50 text-teal-700", "bg-purple-50 text-purple-700",
        "bg-cyan-50 text-cyan-700", "bg-indigo-50 text-indigo-700", "bg-bluegray-50 text-bluegray-700"
    ];
    /** The tool categories of the graph, in order: each takes the next colour. */
    private toolCategories: string[] = [];

    public readonly: boolean = false;
    protected activeTab: number = 0;
    protected diagramModel: any;
    private lastLayoutNodes: { id: string; position: { x: number; y: number } }[] = [];
    /** The positions the admin gave the nodes by dragging them, kept across redraws. */
    private nodePositions = new Map<string, { x: number; y: number }>();
    /** The rendered height of each node, measured after it is drawn: the nodes of a level are stacked on it. */
    private measuredNodeHeights = new Map<string, number>();
    /** The heights the last layout stacked the nodes with, to tell when a new measure asks for another one. */
    private layoutNodeHeights = new Map<string, number>();
    /** The layouts redone in a row for new measures, bounded so a node that keeps resizing cannot loop. */
    private relayoutsInARow = 0;
    private static readonly DEFAULT_NODE_HEIGHT = 160;
    private static readonly NODE_VERTICAL_GAP = 40;
    @ViewChild("diagramHost", { read: ElementRef }) private diagramHostRef?: ElementRef<HTMLElement>;
    private observedDiagramHost?: HTMLElement;
    private diagramResizeObserver?: ResizeObserver;
    private resizeFitTimer?: ReturnType<typeof setTimeout>;
    protected nodeTemplateMap = new NgDiagramNodeTemplateMap([
        ["agent", AgentNodeComponent]
    ]);
    protected diagramConfig: NgDiagramConfig = {
        viewportPanningEnabled: true,
        edgeRouting: { defaultRouting: "bezier" },
        // The graph tab is hidden while the diagram is initialized. Letting
        // ng-diagram fit at that point caches a zero-size viewport and can leave
        // every node outside the visible area. Fit from rendered DOM dimensions
        // after the tab becomes visible, as the compliance data-flow graph does.
        zoom: {
            max: 3
        },
        linking: {
            // the ports are small: a link snaps to a port from further away than the
            // library's default 10 units
            portSnapDistance: 30,
            // an edge is drawn from the output port of a participant to the input port
            // of another, and only when the rules let the first talk to the second
            validateConnection: (source: { id?: string } | null, sourcePort: { type?: string } | null, target: { id?: string } | null, targetPort: { type?: string } | null) => {
                const verdict = this.linkVerdict(source?.id, sourcePort?.type, target?.id, targetPort?.type);
                console.debug("[agents-network] link " + source?.id + " -> " + target?.id + ": " + (verdict.ok ? "allowed" : "refused") + ", " + verdict.reason);
                return verdict.ok;
            }
        }
    };

    // The inspector: the selected participant, edited in place
    protected selected: AgentNetworkParticipant | null = null;
    protected selectedEdge: { source: string; target: string } | null = null;
    protected inspectorTab: "participant" | "configuration" = "participant";
    private applyingSelection: boolean = false;

    protected policyOptions = [
        { id: "PolicyAllowAll", label: "Allow all", value: "ALLOW_ALL" },
        { id: "PolicyDenyAll", label: "Deny all", value: "DENY_ALL" },
        { id: "PolicyAllowList", label: "Allow list", value: "ALLOW_LIST" },
        { id: "PolicyDenyList", label: "Deny list", value: "DENY_LIST" }
    ];

    /** The groups of the palette, by the kind of the services; the labels are translated on init. */
    private static readonly PALETTE_GROUPS: { id: string, kind: AgentServiceDescriptor.KindEnum, label: string }[] = [
        { id: "PaletteForwarders", kind: "FORWARDER", label: "Input adapters" },
        { id: "PaletteRouters", kind: "ROUTER", label: "Routers" },
        { id: "PaletteResponders", kind: "RESPONDER", label: "Workers" },
        { id: "PaletteWriters", kind: "WRITER", label: "Writers, answer the user" },
        { id: "PaletteNetworkAdapters", kind: "NETWORK_ADAPTER", label: "Networks as agents" }
    ];
    private paletteGroupLabels: { id: string, label: string }[] = GeboAIAgentsNetworkAdminComponent.PALETTE_GROUPS.map(g => ({ id: g.id, label: g.label }));

    /** Whether the agents are enabled: the chat choice fields exist only then. */
    protected agenticChatNetworksEnabled: boolean = false;

    /** The kinds of chat a network can be chosen for; the labels are translated on init. */
    protected pipelineTypeOptions: { id: string, label: string, value: string }[] = [
        { id: "RagPipelineOption", label: "Agentic chat with knowledge bases", value: "RAG_PIPELINE" },
        { id: "PureChatPipelineOption", label: "Free chat", value: "PURE_CHAT_PIPELINE" }
    ];

    /** The validation messages of the network as it is now, from the backend. */
    protected checks: GUserMessage[] = [];
    protected validationErrors: boolean = false;
    private validateTimer?: ReturnType<typeof setTimeout>;
    private validating: boolean = false;
    private validateAgain: boolean = false;

    protected participantFormGroup = new FormGroup({
        agentConfigCode: new FormControl<string>("", Validators.required),
        agentContextualName: new FormControl<string>(""),
        inputNode: new FormControl<boolean>(false),
        outputNode: new FormControl<boolean>(false),
        allowedToNotifyUser: new FormControl<boolean>(true),
        communicationPolicy: new FormControl<string>("ALLOW_LIST"),
        controller: new FormControl<boolean>(false),
        canCallTools: new FormControl<boolean>(true),
        canCallOtherAgents: new FormControl<boolean>(false)
    });
    protected addTargetControl = new FormControl<string | null>(null);

    constructor(
        injector: Injector,
        geboFormGroupsService: GeboFormGroupsService,
        protected myConfirmationService: ConfirmationService,
        protected actionsRouter: GeboUIActionRoutingService,
        outputForwardingService: GeboUIOutputForwardingService,
        private service: GeboAgentsNetworkAdminControllerService,
        private agentsService: GeboAgentAdminControllerService,
        private viewportService: NgDiagramViewportService,
        private chatNetworksService: AgenticChatDefaultNetworksAdminControllerService,
        destroyRef: DestroyRef
    ) {
        super(injector, geboFormGroupsService, myConfirmationService, actionsRouter, outputForwardingService);
        this.myInjector = injector;
        this.manageOperationStatus = true;
        this.formGroup.valueChanges.subscribe((value) => {
            const isReadOnly = value?.readOnly === true;
            if (this.readonly !== isReadOnly) {
                this.applyReadOnly(isReadOnly);
                this.rebuildChart();
            }
        });
        // the contract changes with the factory: every check runs again
        this.formGroup.controls["agentsNetworkServiceFactoryId"].valueChanges.subscribe(() => {
            this.buildPalette();
            this.scheduleValidation();
        });
        this.participantFormGroup.valueChanges.subscribe(() => this.applyInspector());
        this.addTargetControl.valueChanges.subscribe(target => {
            if (target) {
                this.addTarget(target);
                // the select is a picker: it goes back to its placeholder once the
                // choice is taken, after the select has settled its own model
                setTimeout(() => this.addTargetControl.setValue(null, { emitEvent: false }));
            }
        });
        destroyRef.onDestroy(() => {
            this.diagramResizeObserver?.disconnect();
            if (this.resizeFitTimer) {
                clearTimeout(this.resizeFitTimer);
            }
            if (this.validateTimer) {
                clearTimeout(this.validateTimer);
            }
        });
    }

    override ngOnInit(): void {
        super.ngOnInit();
        this.chatNetworksService.isAgenticChatNetworksEnabled()
            .subscribe(enabled => this.agenticChatNetworksEnabled = enabled === true);
        const translations = this.myInjector.get(GeboAITranslationService);
        translations.translateMenuItems("GeboAIAgentsNetworkAdminModule", "GAgentsNetwork", this.pipelineTypeOptions)
            .subscribe(items => this.pipelineTypeOptions = [...items] as { id: string, label: string, value: string }[]);
        translations.translateMenuItems("GeboAIAgentsNetworkAdminModule", "GAgentsNetwork", this.policyOptions)
            .subscribe(items => this.policyOptions = [...items] as { id: string, label: string, value: string }[]);
        translations.translateMenuItems("GeboAIAgentsNetworkAdminModule", "GAgentsNetwork", this.paletteGroupLabels)
            .subscribe(items => {
                this.paletteGroupLabels = [...items] as { id: string, label: string }[];
                this.buildPalette();
            });
        this.loadComposerData();
    }

    /**
     * Loads what the composer needs to apply the rules: the services with their
     * types, the factories with their contracts, the assignability of the types and
     * every agent configuration.
     */
    protected loadComposerData(selectCode?: string): void {
        this.loadingRelatedBackend = true;
        const observables: [Observable<AgentServiceDescriptor[]>, Observable<NetworkServiceFactoryDescriptor[]>, Observable<AgentTypeCompatibility[]>, Observable<GAgentConfig[]>] =
            [this.service.getAgentServices(), this.service.getNetworkServiceFactories(), this.service.getAgentTypesCompatibility(), this.service.getAgentConfigs()];
        forkJoin(observables).subscribe({
            next: ([services, factories, compatibility, configs]) => {
                this.services = services || [];
                this.factories = factories || [];
                this.assignable = new Map((compatibility || []).map(c => [c.type || "", new Set(c.assignableFrom || [])]));
                this.configs = configs || [];
                console.debug("[agents-network] composer data: " + this.services.length + " service(s), " + this.factories.length
                    + " factory(ies), " + this.assignable.size + " type(s), " + this.configs.length + " configuration(s)");
                this.buildPalette();
                if (selectCode) {
                    this.participantFormGroup.controls.agentConfigCode.setValue(selectCode);
                }
                this.rebuildChart();
                this.scheduleValidation();
            },
            error: (error) => console.error("Cannot load the data of the composer", error),
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    override onLoadedPersistentData(actualValue: GAgentsNetwork): void {
        this.applyReadOnly(actualValue?.readOnly === true);
        this.agentsList = actualValue.agents ? [...actualValue.agents] : [];
        this.nodePositions.clear();
        this.select(null);
        this.rebuildChart();
        this.loadMountedTools();
        this.scheduleValidation();
    }

    override onNewData(actualValue: GAgentsNetwork): void {
        this.applyReadOnly(actualValue?.readOnly === true);
        this.agentsList = actualValue.agents ? [...actualValue.agents] : [];
        this.nodePositions.clear();
        this.select(null);
        // a new network runs on the first factory unless told otherwise
        if (!this.formGroup.controls["agentsNetworkServiceFactoryId"].value && this.factories.length > 0) {
            this.formGroup.controls["agentsNetworkServiceFactoryId"].setValue(this.factories[0].factoryId);
        }
        this.rebuildChart();
        this.loadMountedTools();
        this.scheduleValidation();
    }

    private applyReadOnly(isReadOnly: boolean): void {
        this.readonly = isReadOnly;
        const controls = ["maxLoopIteration", "choosableForPipelineTypes", "agentsNetworkServiceFactoryId"];
        if (this.readonly) {
            this.participantFormGroup.disable({ emitEvent: false });
            this.addTargetControl.disable({ emitEvent: false });
            controls.forEach(c => this.formGroup.get(c)?.disable({ emitEvent: false }));
        } else {
            this.participantFormGroup.enable({ emitEvent: false });
            this.addTargetControl.enable({ emitEvent: false });
            controls.forEach(c => this.formGroup.get(c)?.enable({ emitEvent: false }));
        }
    }

    // ---------------------------------------------------------------------
    // The rules, as the backend validation applies them
    // ---------------------------------------------------------------------

    /** The network agent name of a participant, as the backend computes it. */
    public participantName(participant: AgentNetworkParticipant | null | undefined): string {
        if (!participant) {
            return "";
        }
        return (participant.agentConfigCode || "") + (participant.agentContextualName ? "-" + participant.agentContextualName : "");
    }

    public configOf(participant: AgentNetworkParticipant | null | undefined): GAgentConfig | undefined {
        return participant?.agentConfigCode ? this.configs.find(c => c.code === participant.agentConfigCode) : undefined;
    }

    public serviceOfConfig(config: GAgentConfig | undefined): AgentServiceDescriptor | undefined {
        return config?.agentServiceId ? this.services.find(s => s.serviceId === config.agentServiceId) : undefined;
    }

    public serviceOf(participant: AgentNetworkParticipant | null | undefined): AgentServiceDescriptor | undefined {
        return this.serviceOfConfig(this.configOf(participant));
    }

    protected factory(): NetworkServiceFactoryDescriptor | undefined {
        const id = this.formGroup.controls["agentsNetworkServiceFactoryId"].value;
        return id ? this.factories.find(f => f.factoryId === id) : undefined;
    }

    /** Whether a message of the source type can be given to an agent accepting the target type. */
    public isAssignable(target: string | undefined, source: string | undefined): boolean {
        if (!target || !source) {
            return false;
        }
        const sources = this.assignable.get(target);
        return sources ? sources.has(source) : target === source;
    }

    /** The simple name of a type, what the nodes and the chips show. */
    public shortType(type: string | undefined): string {
        if (!type) {
            return "?";
        }
        const simple = type.substring(type.lastIndexOf(".") + 1);
        return simple === "List" ? "List<Document>" : simple;
    }

    public isRouter(service: AgentServiceDescriptor | undefined): boolean {
        return service?.kind === "ROUTER";
    }

    /** Whether the service delivers its output to the agents it talks to (a forwarder). */
    public isForwarder(service: AgentServiceDescriptor | undefined): boolean {
        return service?.kind === "FORWARDER";
    }

    /** Whether the service answers the agent that asked (its list is informational). */
    public isResponder(service: AgentServiceDescriptor | undefined): boolean {
        return !!service && service.messageSemantic === "RESPONSE";
    }

    /**
     * Whether, by the rules, the source participant can be told to talk to the
     * target one, with the reason.
     */
    public edgeVerdict(source: AgentNetworkParticipant, target: AgentNetworkParticipant): EdgeVerdict {
        if (source === target) {
            return { ok: false, reason: "an agent does not talk to itself" };
        }
        const sourceService = this.serviceOf(source), targetService = this.serviceOf(target);
        if (!sourceService || !targetService) {
            return { ok: false, reason: "unknown agent service" };
        }
        if ((source.communicationList || []).includes(this.participantName(target))) {
            return { ok: false, reason: "already listed" };
        }
        if (this.isRouter(sourceService)) {
            return { ok: true, reason: "a router sends commands typed on " + this.shortType(targetService.inputType) };
        }
        if (this.isResponder(sourceService)) {
            return { ok: true, reason: "a responder's list tells the model whom it may address" };
        }
        return this.isAssignable(targetService.inputType, sourceService.outputType)
            ? { ok: true, reason: this.shortType(sourceService.outputType) + " is accepted" }
            : { ok: false, reason: "it delivers " + this.shortType(sourceService.outputType) + ", the target accepts " + this.shortType(targetService.inputType) };
    }

    /** The verdict on a link drawn on the graph, from a node and port to a node and port. */
    private linkVerdict(sourceId: string | undefined, sourcePortType: string | undefined, targetId: string | undefined, targetPortType: string | undefined): EdgeVerdict {
        if (this.readonly) {
            return { ok: false, reason: "the network is read only" };
        }
        if (sourcePortType && sourcePortType !== "source") {
            return { ok: false, reason: "a link starts from an output port" };
        }
        if (targetPortType && targetPortType !== "target") {
            return { ok: false, reason: "a link ends on an input port" };
        }
        const source = this.agentsList.find(p => this.participantName(p) === sourceId);
        const target = this.agentsList.find(p => this.participantName(p) === targetId);
        if (!source || !target) {
            return { ok: false, reason: "unknown participant" };
        }
        return this.edgeVerdict(source, target);
    }

    /** Whether the service can be added to the network under its contract, with the reason when not. */
    public serviceBlockReason(service: AgentServiceDescriptor): string | null {
        const factory = this.factory();
        if (service.reactive && !service.adaptable) {
            return "no adapter makes this agent a network agent";
        }
        if (service.reactive && factory && !factory.reactiveOutput) {
            return "this factory runs network agents only";
        }
        if (service.reactive && this.agentsList.some(p => this.serviceOf(p)?.reactive)) {
            return "this network already streams one writer";
        }
        return null;
    }

    /** Whether a participant of the service can be the input node of the network under its contract. */
    public canBeInputNode(service: AgentServiceDescriptor | undefined): boolean {
        const factory = this.factory();
        return !!service && !!factory && this.isAssignable(service.inputType, factory.inputType);
    }

    /**
     * Whether the participant can be the controller of the network, the one its loop
     * is counted on: a router (its coordination cycles) or an output node (the cycles
     * of a writer, the single pass of a plain output node).
     */
    public canBeController(service: AgentServiceDescriptor | undefined, participant?: AgentNetworkParticipant): boolean {
        if (!service) {
            return false;
        }
        return this.isRouter(service) || service.reactive === true || participant?.outputNode === true;
    }

    /** Whether a participant of the service produces the output of the network. */
    public producesNetworkOutput(service: AgentServiceDescriptor | undefined): boolean {
        const factory = this.factory();
        return !!service && !!factory && this.isAssignable(factory.outputType, service.outputType);
    }

    /** The participants the selected one could be told to talk to, each with the verdict. */
    protected targetCandidates(): { name: string, verdict: EdgeVerdict, disabled: boolean }[] {
        if (!this.selected) {
            return [];
        }
        const source = this.selected;
        return this.agentsList.filter(p => p !== source)
            .map(p => {
                const verdict = this.edgeVerdict(source, p);
                return { name: this.participantName(p), verdict, disabled: !verdict.ok };
            });
    }

    /** How many checks of the severity the validation answered. */
    protected checksCount(severity: string): number {
        return this.checks.filter(m => m.severity === severity).length;
    }

    /** Whether the name of the participant is used by another participant too. */
    public isDuplicateName(participant: AgentNetworkParticipant): boolean {
        const name = this.participantName(participant);
        return this.agentsList.filter(p => this.participantName(p) === name).length > 1;
    }

    /** The configurations of the service the selected participant runs. */
    protected configsOfSelected(): GAgentConfig[] {
        const config = this.configOf(this.selected);
        return config ? this.configs.filter(c => c.agentServiceId === config.agentServiceId) : [];
    }

    // ---------------------------------------------------------------------
    // The checks: the backend validation on every change
    // ---------------------------------------------------------------------

    /** Runs the backend validation once the admin pauses. */
    protected scheduleValidation(): void {
        if (this.validateTimer) {
            clearTimeout(this.validateTimer);
        }
        this.validateTimer = setTimeout(() => this.runValidation(), 400);
    }

    protected runValidation(): void {
        if (this.validating) {
            this.validateAgain = true;
            return;
        }
        const value: GAgentsNetwork = { ...(this.entity || {}), ...this.formGroup.getRawValue(), agents: this.agentsList } as GAgentsNetwork;
        if (!value.code && !value.description) {
            // nothing loaded yet
            return;
        }
        this.validating = true;
        this.service.validateAgentsNetwork(value).subscribe({
            next: (status) => {
                this.checks = (status?.messages || []).filter(m => m.severity !== "success");
                this.validationErrors = this.checks.some(m => m.severity === "error");
                console.debug("[agents-network] validation of " + (value.code || "the new network") + ": "
                    + this.checks.filter(m => m.severity === "error").length + " error(s), "
                    + this.checks.filter(m => m.severity === "warn").length + " warning(s)");
                this.refreshNodeErrors();
            },
            error: (error) => {
                console.error("Cannot validate the network of agents", error);
                this.validating = false;
            },
            complete: () => {
                this.validating = false;
                if (this.validateAgain) {
                    this.validateAgain = false;
                    this.scheduleValidation();
                }
            }
        });
    }

    /** The names of the participants the error messages point at. */
    private nodeErrors = new Set<string>();

    private refreshNodeErrors(): void {
        // the validation names the participant it talks about in quotes
        this.nodeErrors = new Set(this.agentsList.map(p => this.participantName(p))
            .filter(name => this.checks.some(m => m.severity === "error" && (m.detail || "").includes("'" + name + "'"))));
    }

    public nodeHasError(name: string | undefined): boolean {
        return !!name && this.nodeErrors.has(name);
    }

    /** The participant a check points at, if any: clicking the check selects it. */
    protected selectFromCheck(message: GUserMessage): void {
        const participant = this.agentsList.find(p => (message.detail || "").includes("'" + this.participantName(p) + "'"));
        if (participant) {
            this.select(participant);
            this.activeTab = 1;
        }
    }

    // ---------------------------------------------------------------------
    // The palette
    // ---------------------------------------------------------------------

    private buildPalette(): void {
        this.paletteGroups = GeboAIAgentsNetworkAdminComponent.PALETTE_GROUPS.map(group => ({
            id: group.id,
            kind: group.kind,
            label: this.paletteGroupLabels.find(l => l.id === group.id)?.label || group.label,
            services: this.services.filter(s => s.kind === group.kind)
        })).filter(group => group.services.length > 0);
    }

    public configsCount(service: AgentServiceDescriptor): number {
        return this.configs.filter(c => c.agentServiceId === service.serviceId).length;
    }

    /**
     * Adds a participant running the service: with its first configuration, or with
     * a new configuration when the service has none.
     */
    protected addService(service: AgentServiceDescriptor): void {
        if (this.readonly || this.serviceBlockReason(service)) {
            return;
        }
        const configs = this.configs.filter(c => c.agentServiceId === service.serviceId);
        if (configs.length === 0) {
            this.addAgentConfig(service.serviceId);
            return;
        }
        this.addParticipant(configs[0]);
    }

    private addParticipant(config: GAgentConfig): void {
        const service = this.serviceOfConfig(config);
        const participant: AgentNetworkParticipant = {
            agentConfigCode: config.code!,
            agentContextualName: undefined,
            inputNode: false,
            outputNode: service?.reactive === true,
            allowedToNotifyUser: true,
            communicationPolicy: "ALLOW_LIST",
            communicationList: [],
            // the first participant that can be the controller takes the role
            controller: this.canBeController(service) && !this.agentsList.some(p => p.controller),
            canCallTools: true,
            canCallOtherAgents: false
        };
        // the same configuration twice needs a contextual name to tell them apart
        let suffix = 2;
        while (this.agentsList.some(p => this.participantName(p) === this.participantName(participant))) {
            participant.agentContextualName = String(suffix++);
        }
        participant.networkAgentName = this.participantName(participant);
        this.agentsList.push(participant);
        console.debug("[agents-network] added participant " + participant.networkAgentName + " running " + (service?.serviceId || "?"));
        this.updateFormGroup();
        this.select(participant);
    }

    // ---------------------------------------------------------------------
    // The inspector
    // ---------------------------------------------------------------------

    public select(participant: AgentNetworkParticipant | null): void {
        this.selected = participant;
        this.selectedEdge = null;
        this.inspectorTab = "participant";
        this.applyingSelection = true;
        try {
            if (participant) {
                this.participantFormGroup.patchValue({
                    agentConfigCode: participant.agentConfigCode || "",
                    agentContextualName: participant.agentContextualName || "",
                    inputNode: participant.inputNode === true,
                    outputNode: participant.outputNode === true,
                    allowedToNotifyUser: participant.allowedToNotifyUser !== false,
                    communicationPolicy: participant.communicationPolicy || "ALLOW_LIST",
                    controller: participant.controller === true,
                    canCallTools: participant.canCallTools !== false,
                    canCallOtherAgents: participant.canCallOtherAgents === true
                }, { emitEvent: false });
                // the constraints of the contract are on the controls themselves: a
                // participant whose input is not the contract input cannot be the input
                // node, a writer is always the output node
                if (!this.readonly) {
                    const service = this.serviceOf(participant);
                    this.setControlEnabled(this.participantFormGroup.controls.inputNode, this.canBeInputNode(service));
                    this.setControlEnabled(this.participantFormGroup.controls.outputNode, service?.reactive !== true);
                    this.setControlEnabled(this.participantFormGroup.controls.controller, this.canBeController(service, participant));
                }
            }
        } finally {
            this.applyingSelection = false;
        }
        this.refreshNodeSelection();
    }

    private setControlEnabled(control: FormControl<any>, enabled: boolean): void {
        if (enabled && control.disabled) {
            control.enable({ emitEvent: false });
        } else if (!enabled && control.enabled) {
            control.disable({ emitEvent: false });
        }
    }

    public isSelected(participant: AgentNetworkParticipant): boolean {
        return this.selected === participant;
    }

    /** Writes the inspector into the selected participant, renaming its edges when its name changes. */
    private applyInspector(): void {
        if (this.applyingSelection || !this.selected || this.readonly) {
            return;
        }
        const participant = this.selected;
        const value = this.participantFormGroup.getRawValue();
        const oldName = this.participantName(participant);
        participant.agentConfigCode = value.agentConfigCode || participant.agentConfigCode;
        participant.agentContextualName = value.agentContextualName?.trim() ? value.agentContextualName.trim() : undefined;
        participant.inputNode = value.inputNode === true;
        participant.outputNode = value.outputNode === true;
        participant.allowedToNotifyUser = value.allowedToNotifyUser !== false;
        participant.communicationPolicy = (value.communicationPolicy || "ALLOW_LIST") as AgentNetworkParticipant.CommunicationPolicyEnum;
        const controller = value.controller === true;
        if (controller && !participant.controller) {
            // one controller: the flag moves to this participant
            this.agentsList.forEach(p => p.controller = p === participant);
            console.debug("[agents-network] " + this.participantName(participant) + " is the controller of the network");
        } else {
            participant.controller = controller;
        }
        participant.canCallTools = value.canCallTools !== false;
        participant.canCallOtherAgents = value.canCallOtherAgents === true;
        const service = this.serviceOf(participant);
        if (service?.reactive && !participant.outputNode) {
            // a writer is always an output node
            participant.outputNode = true;
            this.participantFormGroup.controls.outputNode.setValue(true, { emitEvent: false });
        }
        this.setControlEnabled(this.participantFormGroup.controls.controller, this.canBeController(service, participant));
        const newName = this.participantName(participant);
        participant.networkAgentName = newName;
        if (oldName !== newName) {
            this.agentsList.forEach(p => {
                if (p.communicationList) {
                    p.communicationList = p.communicationList.map(n => n === oldName ? newName : n);
                }
            });
            const position = this.nodePositions.get(oldName);
            if (position) {
                this.nodePositions.delete(oldName);
                this.nodePositions.set(newName, position);
            }
            console.debug("[agents-network] participant " + oldName + " renamed " + newName + ", its edges renamed with it");
        }
        this.updateFormGroup();
    }

    protected addTarget(target: string): void {
        if (!this.selected || this.readonly) {
            return;
        }
        const targetParticipant = this.agentsList.find(p => this.participantName(p) === target);
        if (!targetParticipant) {
            return;
        }
        const verdict = this.edgeVerdict(this.selected, targetParticipant);
        if (!verdict.ok) {
            console.debug("[agents-network] " + this.participantName(this.selected) + " cannot talk to " + target + ": " + verdict.reason);
            return;
        }
        this.selected.communicationList = [...(this.selected.communicationList || []), target];
        console.debug("[agents-network] " + this.participantName(this.selected) + " talks to " + target + ": " + verdict.reason);
        this.updateFormGroup();
    }

    protected removeTarget(target: string): void {
        if (!this.selected || this.readonly) {
            return;
        }
        this.selected.communicationList = (this.selected.communicationList || []).filter(n => n !== target);
        console.debug("[agents-network] " + this.participantName(this.selected) + " no longer talks to " + target);
        this.updateFormGroup();
    }

    protected removeSelectedEdge(): void {
        if (!this.selectedEdge || this.readonly) {
            return;
        }
        const edge = this.selectedEdge;
        const source = this.agentsList.find(p => this.participantName(p) === edge.source);
        if (source) {
            source.communicationList = (source.communicationList || []).filter(n => n !== edge.target);
            console.debug("[agents-network] edge " + edge.source + " -> " + edge.target + " removed");
        }
        this.selectedEdge = null;
        this.updateFormGroup();
    }

    /** Opens the editor of the configuration the selected participant runs. */
    protected editSelectedConfig(): void {
        this.editAgentConfig(this.selected?.agentConfigCode);
    }

    /** Makes a new configuration of the service the selected participant runs, for the participant. */
    protected newConfigForSelected(): void {
        const service = this.serviceOf(this.selected);
        if (service) {
            this.addAgentConfig(service.serviceId, this.selected || undefined);
        }
    }

    // ---------------------------------------------------------------------
    // The graph
    // ---------------------------------------------------------------------

    /**
     * The colour classes of a tool category: the categories of the graph take the
     * colours in turn, so they share one only past the number of colours.
     */
    public getToolCategoryClass(categoryCode: string | undefined): string {
        const index = categoryCode ? this.toolCategories.indexOf(categoryCode) : -1;
        const classes = GeboAIAgentsNetworkAdminComponent.TOOL_CATEGORY_CLASSES;
        return index >= 0 ? classes[index % classes.length] : "surface-100 text-color";
    }

    private refreshToolCategories(): void {
        const codes = new Set<string>();
        this.mountedTools.forEach(mounted => [...(mounted.tools || []), ...(mounted.excludedTools || [])]
            .forEach(tool => tool.categoryCode && codes.add(tool.categoryCode)));
        this.toolCategories = [...codes].sort();
    }

    /** The tools the given agent configuration mounts, once loaded. */
    public getMountedTools(code: string | undefined): AgentMountedTools | undefined {
        return code ? this.mountedTools.get(code) : undefined;
    }

    /**
     * Loads the tools mounted by the configurations of the network agents that are
     * not known yet, or by the given configuration again after it was edited.
     */
    protected loadMountedTools(reloadCode?: string): void {
        if (reloadCode) {
            this.mountedTools.delete(reloadCode);
        }
        const codes = [...new Set(this.agentsList.map(a => a.agentConfigCode)
            .filter((code): code is string => !!code && !this.mountedTools.has(code)))];
        if (codes.length === 0) {
            return;
        }
        this.service.getAgentsMountedTools(codes).subscribe({
            next: (result) => {
                (result || []).forEach(mounted => {
                    if (mounted.agentConfigCode) {
                        this.mountedTools.set(mounted.agentConfigCode, mounted);
                    }
                });
                this.refreshToolCategories();
                // the nodes show their tools on the next change detection, taller:
                // fit again without moving the nodes the user placed
                if (this.diagramModel && this.lastLayoutNodes.length > 0) {
                    this.fitDiagramToViewport();
                }
            },
            error: (error) => console.error("Cannot load the tools of the network agents", error)
        });
    }

    public getAgentDescription(code: string | undefined): string {
        const found = code ? this.configs.find(a => a.code === code) : undefined;
        return found ? found.description || code || "" : code || "";
    }

    protected rebuildChart(): void {
        if (!this.myInjector) {
            return;
        }
        runInInjectionContext(this.myInjector, () => {
            const list = this.agentsList || [];
            if (list.length === 0) {
                this.diagramModel = initializeModel({ nodes: [], edges: [] });
                this.lastLayoutNodes = [];
                return;
            }
            const nameOf = (p: AgentNetworkParticipant) => this.participantName(p);

            // Collect all target communication names to identify root nodes
            const targetNames = new Set<string>();
            list.forEach(agent => (agent.communicationList || []).forEach(name => name && targetNames.add(name)));

            // Roots are agents whose name is not referenced in any communicationList
            let roots = list.filter(agent => !targetNames.has(nameOf(agent)));

            // If there is a cycle and no clear root, fallback to inputNode or first node
            if (roots.length === 0 && list.length > 0) {
                const inputNode = list.find(a => a.inputNode);
                roots = inputNode ? [inputNode] : [list[0]];
            }

            // BFS to assign hierarchy levels
            const levelsMap = new Map<string, number>();
            const visited = new Set<string>();
            const queue: { agent: AgentNetworkParticipant; level: number }[] = roots.map(r => ({ agent: r, level: 0 }));
            while (queue.length > 0) {
                const { agent, level } = queue.shift()!;
                const name = nameOf(agent);
                if (visited.has(name)) {
                    continue;
                }
                visited.add(name);
                levelsMap.set(name, level);
                (agent.communicationList || []).forEach(childName => {
                    const childAgent = list.find(a => nameOf(a) === childName);
                    if (childAgent && childName && !visited.has(childName)) {
                        queue.push({ agent: childAgent, level: level + 1 });
                    }
                });
            }

            // Default all unreached/isolated agents to level 0
            list.forEach(a => {
                if (!levelsMap.has(nameOf(a))) {
                    levelsMap.set(nameOf(a), 0);
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

            // The nodes of a level are stacked on their rendered heights (they grow with
            // the row of their tools), a gap apart and centred on the same line: before a
            // node is measured it counts as DEFAULT_NODE_HEIGHT.
            const heightOf = (name: string) => this.measuredNodeHeights.get(name) || GeboAIAgentsNetworkAdminComponent.DEFAULT_NODE_HEIGHT;
            const gap = GeboAIAgentsNetworkAdminComponent.NODE_VERTICAL_GAP;
            const stackedY = new Map<string, number>();
            this.layoutNodeHeights = new Map<string, number>();
            levelGroups.forEach(names => {
                const total = names.reduce((sum, n) => sum + heightOf(n), 0) + gap * Math.max(names.length - 1, 0);
                let top = 300 - total / 2;
                names.forEach(n => {
                    stackedY.set(n, top);
                    this.layoutNodeHeights.set(n, heightOf(n));
                    top += heightOf(n) + gap;
                });
            });

            const nodes: any[] = [];
            const edges: any[] = [];
            list.forEach(agent => {
                const name = nameOf(agent);
                const L = levelsMap.get(name) || 0;

                // The levels go left to right, 360px apart: the nodes are at most 300px
                // wide. A node the admin dragged keeps its place.
                const placed = this.nodePositions.get(name);
                const x = placed ? placed.x : L * 360 + 40;
                const y = placed ? placed.y : (stackedY.get(name) ?? 300);
                nodes.push({
                    id: name,
                    position: { x, y },
                    draggable: !this.readonly,
                    selected: this.selected === agent,
                    type: "agent",
                    data: agent
                });
                (agent.communicationList || []).forEach(childName => {
                    if (childName) {
                        edges.push({
                            id: `${name}->${childName}`,
                            source: name,
                            sourcePort: "port-right",
                            target: childName,
                            targetPort: "port-left",
                            data: { source: name, target: childName }
                        });
                    }
                });
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

    /** The graph shows the selection without a rebuild: the nodes read it from the parent. */
    private refreshNodeSelection(): void {
        // nothing to do beyond change detection: the node template asks isSelected()
    }

    /** The admin drew an edge: the target joins the communication list of the source. */
    protected onEdgeDrawEnded(event: EdgeDrawEndedEvent): void {
        if (!event.success || !event.source || !event.target) {
            console.debug("[agents-network] edge draw ended without a link: " + (event.reason || "cancelled"));
            return;
        }
        const source = this.agentsList.find(p => this.participantName(p) === event.source.id);
        const target = this.agentsList.find(p => this.participantName(p) === event.target!.id);
        if (source && target) {
            const verdict = this.edgeVerdict(source, target);
            if (verdict.ok) {
                source.communicationList = [...(source.communicationList || []), this.participantName(target)];
                console.debug("[agents-network] edge drawn " + event.source.id + " -> " + event.target.id + ": " + verdict.reason);
            } else {
                console.debug("[agents-network] edge drawn " + event.source.id + " -> " + event.target.id + " refused: " + verdict.reason);
            }
        }
        // the graph is rebuilt from the lists: the edge the library added is replaced by ours
        this.updateFormGroup();
    }

    /** A click on an edge selects it: the inspector offers to remove it. */
    protected onSelectionChanged(event: SelectionChangedEvent): void {
        const edge = event.selectedEdges && event.selectedEdges.length > 0 ? event.selectedEdges[0] : null;
        if (edge && edge.source && edge.target) {
            this.selectedEdge = { source: edge.source, target: edge.target };
            console.debug("[agents-network] edge selected " + edge.source + " -> " + edge.target);
        } else if (this.selectedEdge) {
            this.selectedEdge = null;
        }
    }

    /** A dragged node keeps the place the admin gave it across redraws. */
    protected onNodeDragEnded(event: any): void {
        const nodes: any[] = event?.nodes || (event?.node ? [event.node] : []);
        nodes.forEach(node => {
            if (node?.id && node.position) {
                this.nodePositions.set(node.id, { x: node.position.x, y: node.position.y });
            }
        });
    }

    protected onTabChange(value: string | number | undefined): void {
        this.activeTab = Number(value);
        if (this.activeTab === 1 && this.diagramModel) {
            afterNextRender(() => {
                this.fitDiagramToViewport();
            }, { injector: this.myInjector });
        }
    }

    private fitDiagramToViewport(): void {
        // Node templates and their containing tab settle asynchronously. Repeating
        // the idempotent fit guarantees that the final pass uses measured node
        // sizes and the real visible viewport rather than placeholder dimensions.
        for (const delay of [120, 350, 700]) {
            setTimeout(() => this.applyFit(), delay);
        }
    }

    private applyFit(): void {
        const hostEl = this.diagramHostRef?.nativeElement;
        if (!hostEl) {
            return;
        }
        this.observeDiagramHost(hostEl);
        if (hostEl.clientWidth <= 0 || hostEl.clientHeight <= 0 || this.lastLayoutNodes.length === 0) {
            return;
        }
        // the nodes drawn taller or shorter than the last layout stacked them are laid out
        // again on their measured heights, so the nodes of a level never overlap
        let heightsChanged = false;
        for (const node of this.lastLayoutNodes) {
            const nodeEl = hostEl.querySelector<HTMLElement>(`[data-node-id="${node.id}"]`);
            const measured = nodeEl?.offsetHeight || 0;
            if (measured > 0) {
                this.measuredNodeHeights.set(node.id, measured);
                const used = this.layoutNodeHeights.get(node.id);
                if (!this.nodePositions.has(node.id) && (used === undefined || Math.abs(used - measured) > 8)) {
                    heightsChanged = true;
                }
            }
        }
        if (heightsChanged && this.relayoutsInARow < 3) {
            this.relayoutsInARow++;
            console.debug("[agents-network] node heights changed: layout " + this.relayoutsInARow + " on the measured heights");
            this.rebuildChart();
            return;
        }
        this.relayoutsInARow = 0;
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
        if (boundsWidth <= 0 || boundsHeight <= 0) {
            return;
        }
        const padding = 60;
        const scale = Math.max(Math.min(
            (hostEl.clientWidth - 2 * padding) / boundsWidth,
            (hostEl.clientHeight - 2 * padding) / boundsHeight,
            1.5
        ), 0.1);
        const centreX = (minX + maxX) / 2;
        const centreY = (minY + maxY) / 2;
        this.viewportService.setViewport(
            hostEl.clientWidth / 2 - centreX * scale,
            -hostEl.clientHeight / 2 - centreY * scale,
            scale
        );
    }

    private observeDiagramHost(hostEl: HTMLElement): void {
        if (this.observedDiagramHost === hostEl || typeof ResizeObserver === "undefined") {
            return;
        }
        this.diagramResizeObserver?.disconnect();
        this.observedDiagramHost = hostEl;
        this.diagramResizeObserver = new ResizeObserver(() => {
            if (this.resizeFitTimer) {
                clearTimeout(this.resizeFitTimer);
            }
            this.resizeFitTimer = setTimeout(() => this.applyFit(), 100);
        });
        this.diagramResizeObserver.observe(hostEl);
    }

    // ---------------------------------------------------------------------
    // The agent configurations
    // ---------------------------------------------------------------------

    public editAgentConfig(code: string | null | undefined): void {
        if (!code) return;
        this.actionsRouter.routeEvent({
            actionType: GeboActionType.OPEN,
            context: {},
            contextType: "GeboAIAgentsNetworkAdminComponent",
            targetType: "GAgentConfig",
            target: { code: code },
            onActionPerformed: (event) => {
                // an agent copied from the participant's context becomes the participant's agent
                const copiedCode = event?.actionType === GeboActionPerformedType.INSERTED ? event.target?.code : undefined;
                if (copiedCode && this.selected && this.selected.agentConfigCode === code) {
                    this.participantFormGroup.controls.agentConfigCode.setValue(copiedCode, { emitEvent: false });
                    this.applyInspectorConfig(copiedCode);
                }
                // the editor tells when it saved or inserted, then when it closes: the
                // composer reloads on the change, not on the closing
                if (copiedCode || event?.actionType === GeboActionPerformedType.SAVED) {
                    this.loadComposerData();
                    // the edited configuration may mount other tools now
                    this.loadMountedTools(copiedCode ?? code);
                }
            }
        });
    }

    /** The selected participant runs another configuration, by code. */
    private applyInspectorConfig(code: string): void {
        if (!this.selected) {
            return;
        }
        const oldName = this.participantName(this.selected);
        this.selected.agentConfigCode = code;
        const newName = this.participantName(this.selected);
        this.selected.networkAgentName = newName;
        this.agentsList.forEach(p => {
            if (p.communicationList) {
                p.communicationList = p.communicationList.map(n => n === oldName ? newName : n);
            }
        });
        this.updateFormGroup();
    }

    /**
     * Opens the editor on a new configuration, of the given service when known: once
     * saved, it joins the network as a participant (or replaces the configuration of
     * the given participant).
     */
    public addAgentConfig(serviceId?: string, forParticipant?: AgentNetworkParticipant): void {
        this.actionsRouter.routeEvent({
            actionType: GeboActionType.NEW,
            context: {},
            contextType: "GeboAIAgentsNetworkAdminComponent",
            targetType: "GAgentConfig",
            target: {
                agentServiceId: serviceId,
                accessibleToAll: true,
                enabledFunctions: [],
                subscribeAllTools: true,
                useDefaultChatModel: true,
                defaultConfiguration: false,
                description: "New Agent Configuration"
            } as any,
            onActionPerformed: (event) => {
                const created = event?.target as GAgentConfig | undefined;
                if (event?.actionType === GeboActionPerformedType.INSERTED && created?.code) {
                    this.loadingRelatedBackend = true;
                    this.service.getAgentConfigs().subscribe({
                        next: (configs) => {
                            this.configs = configs || [];
                            this.buildPalette();
                            if (forParticipant) {
                                this.select(forParticipant);
                                this.applyInspectorConfig(created.code!);
                            } else if (!this.readonly) {
                                this.addParticipant(created);
                            }
                        },
                        complete: () => this.loadingRelatedBackend = false
                    });
                } else if (event?.actionType === GeboActionPerformedType.SAVED) {
                    this.loadComposerData();
                }
            }
        });
    }

    /**
     * Copies the network together with its agents and goes on editing the copy in
     * place of the original: this is also how a read-only system network becomes a
     * network of agents that can be changed.
     */
    protected copyNetwork(): void {
        const code = this.entity?.code;
        if (!code) return;
        if (!this.formGroup.dirty) {
            this.doCopyNetwork(code);
            return;
        }
        const confirmation: ExtendedConfirmation = {
            id: "CopyConfirmation",
            message: "The changes not saved are not copied and will be lost: copy the saved network of agents?",
            header: "Copy the network of agents",
            icon: "pi pi-exclamation-triangle",
            accept: () => this.doCopyNetwork(code)
        };
        this.myInjector.get(GeboAITranslationService).translateConfirmation("GeboAIAgentsNetworkAdminModule", "GAgentsNetwork", confirmation)
            .pipe(take(1)).subscribe(translated => this.myConfirmationService.confirm(translated ?? confirmation));
    }

    private doCopyNetwork(code: string): void {
        this.loadingRelatedBackend = true;
        this.service.copyAgentsNetwork(code).subscribe({
            next: (status) => {
                this.assignBackendMessages(status?.messages);
                const copy = status?.result;
                if (copy?.code) {
                    // the copy takes the place of the original in this editor, and whoever
                    // opened the editor (the networks lists) learns a network was created
                    this.entity = copy;
                    this.formGroup.markAsPristine();
                    this.doReloadByCode();
                    this.loadComposerData();
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
                    detail: "Server error copying the network of agents: " + (error?.message ?? error)
                }];
            }
        });
    }

    /** The participants changed: the form, the graph, the tools and the checks follow. */
    protected updateFormGroup(): void {
        this.agentsList.forEach(p => p.networkAgentName = this.participantName(p));
        this.formGroup.controls["agents"].setValue(this.agentsList);
        this.formGroup.controls["agents"].markAsDirty();
        this.rebuildChart();
        this.loadMountedTools();
        this.scheduleValidation();
    }

    public deleteParticipant(participant: AgentNetworkParticipant): void {
        const name = this.participantName(participant);
        const confirmation: ExtendedConfirmation = {
            id: "RemoveParticipantConfirmation",
            message: `Remove "${name}" from the network? The agents talking to it will stop.`,
            header: "Remove the participant",
            icon: "pi pi-exclamation-triangle",
            accept: () => {
                this.agentsList = this.agentsList.filter(a => a !== participant);
                this.agentsList.forEach(a => {
                    if (a.communicationList) {
                        a.communicationList = a.communicationList.filter(childName => childName !== name);
                    }
                });
                this.nodePositions.delete(name);
                if (this.selected === participant) {
                    this.select(null);
                }
                console.debug("[agents-network] participant " + name + " removed with the edges pointing to it");
                this.updateFormGroup();
            }
        };
        this.myInjector.get(GeboAITranslationService).translateConfirmation("GeboAIAgentsNetworkAdminModule", "GAgentsNetwork", confirmation)
            .pipe(take(1)).subscribe(translated => this.myConfirmationService.confirm(translated ?? confirmation));
    }

    protected clearNetwork(): void {
        const confirmation: ExtendedConfirmation = {
            id: "ClearNetworkConfirmation",
            message: "Remove every agent from the network?",
            header: "Clear the network",
            icon: "pi pi-exclamation-triangle",
            accept: () => {
                this.agentsList = [];
                this.nodePositions.clear();
                this.select(null);
                this.updateFormGroup();
            }
        };
        this.myInjector.get(GeboAITranslationService).translateConfirmation("GeboAIAgentsNetworkAdminModule", "GAgentsNetwork", confirmation)
            .pipe(take(1)).subscribe(translated => this.myConfirmationService.confirm(translated ?? confirmation));
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
        // only the system networks, which are read-only, cannot be deleted
        return of({
            "canBeDeleted": value?.readOnly !== true,
            "message": "This is a system network of agents: it cannot be changed nor deleted, copy it to have one of your own"
        });
    }
}
