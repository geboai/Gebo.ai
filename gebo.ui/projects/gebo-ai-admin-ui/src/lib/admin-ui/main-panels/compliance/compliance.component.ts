/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { afterNextRender, Component, ElementRef, Injector, OnInit, runInInjectionContext, ViewChild } from "@angular/core";
import { DataFlowMetaInfoControllerService, GDataFlowReport } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";
import { initializeModel, NgDiagramNodeTemplateMap, NgDiagramConfig, provideNgDiagram, NgDiagramViewportService } from "ng-diagram";
import { AncestorPanelComponent } from "../ancestor-panel/ancestor-admin-panel.component";
import { DataEndpointNodeComponent } from "./data-endpoint-node.component";
import { DataTransformationNodeComponent } from "./data-transformation-node.component";
import { DataFlowEndpointNode, DataFlowSummary, DataFlowTransformationNode } from "./compliance-data-flow.model";

/**
 * The Compliance admin panel.
 *
 * Surfaces the data-flow register collected from every messaging component's
 * {@code getDataFlowMetaInfos()} and published by the backend's
 * {@code DataFlowMetaInfoController}: which sources feed the installation, which
 * stores retain the result, which engines and third parties see the content in
 * between, and what is able to erase it.
 *
 * It reports what is actually running - the backend builds the register from
 * live beans through the message broker, not from a declared topology - so it
 * answers a GDPR Art. 30 records-of-processing question about this deployment as
 * it stands.
 */
@Component({
    selector: "gebo-ai-compliance-component",
    templateUrl: "compliance.component.html",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "ComplianceModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("ComplianceComponent") },
        provideNgDiagram()
    ]
})
export class ComplianceComponent extends AncestorPanelComponent implements OnInit {

    protected loading: boolean = false;
    protected report?: GDataFlowReport;
    protected loadError: boolean = false;

    /** Modal visibility for the data-flow register. */
    protected showDataFlowDialog: boolean = false;

    protected endpoints: DataFlowEndpointNode[] = [];
    protected transformations: DataFlowTransformationNode[] = [];
    protected summary: DataFlowSummary = {
        endpoints: 0, transformations: 0, components: 0,
        externalEndpoints: 0, personalDataEndpoints: 0, retainingWithoutErasure: 0
    };

    protected diagramModel: any;
    private lastLayoutNodes: { id: string; position: { x: number; y: number } }[] = [];
    @ViewChild("dataFlowDiagramHost", { read: ElementRef }) private diagramHostRef?: ElementRef<HTMLElement>;

    protected nodeTemplateMap = new NgDiagramNodeTemplateMap([
        ["dataEndpoint", DataEndpointNodeComponent],
        ["dataTransformation", DataTransformationNodeComponent]
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

    constructor(
        private injector: Injector,
        private dataFlowService: DataFlowMetaInfoControllerService,
        private viewportService: NgDiagramViewportService
    ) {
        super();
    }

    ngOnInit(): void {
        this.reloadViewedData();
    }

    public override reloadViewedData(): void {
        this.loading = true;
        this.loadError = false;
        this.dataFlowService.getLocalDataFlow().subscribe({
            next: (value) => {
                this.report = value;
                this.flattenReport(value);
                this.rebuildChart();
            },
            error: () => {
                this.loadError = true;
                this.report = undefined;
                this.flattenReport(undefined);
                this.rebuildChart();
                this.loading = false;
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

    /**
     * Flattens report -> module -> component -> flow into the two render-ready
     * lists, qualifying every endpoint id with its owning component so ids stay
     * unique once several components' reports sit in one graph.
     */
    private flattenReport(report?: GDataFlowReport): void {
        const endpoints: DataFlowEndpointNode[] = [];
        const transformations: DataFlowTransformationNode[] = [];
        const components = new Set<string>();

        for (const module of report?.modules || []) {
            for (const component of module.components || []) {
                const flow = component.dataFlowMetaInfos;
                if (!flow) {
                    continue;
                }
                const ownerComponent = (module.messagingModuleId || "") + "." + (component.messagingSystemId || "");
                components.add(ownerComponent);

                for (const endpoint of flow.dataEndpoints || []) {
                    endpoints.push({
                        qualifiedId: this.qualify(ownerComponent, endpoint.id),
                        localId: endpoint.id,
                        description: endpoint.description || endpoint.id,
                        product: endpoint.product || "",
                        endpoint: endpoint.endpoint || "",
                        input: endpoint.input === true,
                        output: endpoint.output === true,
                        types: (endpoint.types || []) as string[],
                        locality: endpoint.locality as any,
                        secretReference: endpoint.secretReference,
                        personalData: endpoint.personalData === true,
                        disposer: endpoint.disposer
                            ? (endpoint.disposer.messagingModuleId || "") + "." + (endpoint.disposer.messagingComponentId || "")
                            : undefined,
                        ownerComponent: ownerComponent,
                        nodeId: report?.nodeId
                    });
                }

                for (const transformation of flow.transformations || []) {
                    transformations.push({
                        qualifiedId: this.qualify(ownerComponent, transformation.id),
                        description: transformation.description || transformation.id,
                        engineDescription: transformation.transformationInfo?.description || "",
                        transformFrom: (transformation.transformationInfo?.transformFrom || []) as string[],
                        transformInto: (transformation.transformationInfo?.transformInto || []) as string[],
                        // Already qualified by the reporting component - these can
                        // point at an endpoint owned by a different component.
                        sourceId: transformation.dataSourceId,
                        destinationId: transformation.dataDestinationId,
                        ownerComponent: ownerComponent
                    });
                }
            }
        }

        this.endpoints = endpoints;
        this.transformations = transformations;
        this.summary = {
            endpoints: endpoints.length,
            transformations: transformations.length,
            components: components.size,
            externalEndpoints: endpoints.filter(e => e.locality === "EXTERNAL_PROVIDER").length,
            personalDataEndpoints: endpoints.filter(e => e.personalData).length,
            retainingWithoutErasure: endpoints.filter(e => e.output && !e.disposer).length
        };
    }

    /** Mirrors the backend's GDataFlowMetaInfos.qualifiedId(...) convention. */
    private qualify(ownerComponent: string, localId: string): string {
        return ownerComponent + "<->" + localId;
    }

    /**
     * Lays the register out as source -> engine -> destination.
     *
     * Endpoints and transformations form one bipartite graph, so levels are
     * assigned by BFS from the nodes nothing points at (the true sources), the
     * same approach the agents-network diagram uses. Endpoints not touched by any
     * transformation still get a node - an unconnected store is exactly the kind
     * of thing an audit needs to see.
     */
    protected rebuildChart(): void {
        runInInjectionContext(this.injector, () => {
            if (this.endpoints.length === 0 && this.transformations.length === 0) {
                this.diagramModel = initializeModel({ nodes: [], edges: [] });
                this.lastLayoutNodes = [];
                return;
            }

            const edgesRaw: { source: string; target: string }[] = [];
            const knownEndpoints = new Set(this.endpoints.map(e => e.qualifiedId));

            for (const transformation of this.transformations) {
                if (knownEndpoints.has(transformation.sourceId)) {
                    edgesRaw.push({ source: transformation.sourceId, target: transformation.qualifiedId });
                }
                if (knownEndpoints.has(transformation.destinationId)) {
                    edgesRaw.push({ source: transformation.qualifiedId, target: transformation.destinationId });
                }
            }

            const allIds = [
                ...this.endpoints.map(e => e.qualifiedId),
                ...this.transformations.map(t => t.qualifiedId)
            ];
            const targets = new Set(edgesRaw.map(e => e.target));
            const levels = new Map<string, number>();
            const queue: { id: string; level: number }[] = allIds
                .filter(id => !targets.has(id))
                .map(id => ({ id, level: 0 }));

            // Everything is a target (a cycle): fall back to laying every node at 0.
            if (queue.length === 0) {
                allIds.forEach(id => levels.set(id, 0));
            }

            const visited = new Set<string>();
            while (queue.length > 0) {
                const { id, level } = queue.shift()!;
                if (visited.has(id)) {
                    continue;
                }
                visited.add(id);
                levels.set(id, Math.max(levels.get(id) ?? 0, level));
                edgesRaw.filter(e => e.source === id).forEach(e => {
                    if (!visited.has(e.target)) {
                        queue.push({ id: e.target, level: level + 1 });
                    }
                });
            }
            allIds.forEach(id => {
                if (!levels.has(id)) {
                    levels.set(id, 0);
                }
            });

            const levelGroups = new Map<number, string[]>();
            levels.forEach((level, id) => {
                if (!levelGroups.has(level)) {
                    levelGroups.set(level, []);
                }
                levelGroups.get(level)!.push(id);
            });

            const nodes: any[] = [];
            const positionOf = (id: string) => {
                const level = levels.get(id) || 0;
                const group = levelGroups.get(level) || [];
                const index = group.indexOf(id);
                // Columns per level (left to right), rows within a level.
                return { x: level * 420 + 60, y: (index - (group.length - 1) / 2) * 260 + 400 };
            };

            for (const endpoint of this.endpoints) {
                nodes.push({
                    id: endpoint.qualifiedId,
                    position: positionOf(endpoint.qualifiedId),
                    draggable: true,
                    type: "dataEndpoint",
                    data: endpoint
                });
            }
            for (const transformation of this.transformations) {
                nodes.push({
                    id: transformation.qualifiedId,
                    position: positionOf(transformation.qualifiedId),
                    draggable: true,
                    type: "dataTransformation",
                    data: transformation
                });
            }

            // ng-diagram's Edge requires a data payload even when the edge carries
            // no information of its own beyond the two endpoints it joins.
            const edges = edgesRaw.map(e => ({
                id: `${e.source}==>${e.target}`,
                source: e.source,
                target: e.target,
                data: {}
            }));

            this.diagramModel = initializeModel({ nodes, edges });
            this.lastLayoutNodes = nodes;
        });
    }

    protected openDataFlowDialog(): void {
        this.showDataFlowDialog = true;
        // The dialog content does not exist until it is shown, so <ng-diagram>
        // would otherwise initialize against a zero-size viewport and keep
        // computing zoomToFit() against that stale size - the same library quirk
        // the agents-network diagram works around inside a hidden tab panel.
        // Fit from real DOM measurements once the dialog has actually rendered.
        afterNextRender(() => {
            this.fitDiagramToViewport();
        }, { injector: this.injector });
    }

    protected onDialogShow(): void {
        this.fitDiagramToViewport();
    }

    protected refreshFromDialog(): void {
        this.reloadViewedData();
        afterNextRender(() => {
            this.fitDiagramToViewport();
        }, { injector: this.injector });
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
            const width = nodeEl?.offsetWidth || 260;
            const height = nodeEl?.offsetHeight || 200;
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
        // ng-diagram-canvas sits one full viewport-height below the host element,
        // so the Y translate compensates for that fixed offset; X does not.
        const y = (viewportHeight - boundsHeight * scale) / 2 - minY * scale - viewportHeight;
        this.viewportService.setViewport(x, y, scale);
    }
}
