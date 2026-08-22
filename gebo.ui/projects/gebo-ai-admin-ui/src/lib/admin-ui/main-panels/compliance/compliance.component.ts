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
        // onInit zoomToFit is deliberately off: it fires on every model re-init
        // (against not-yet-measured nodes) and would override applyFit(), which
        // centres the graph from real DOM measurements a few ticks later.
        zoom: {
            max: 3
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
     * Downloads the register as a CSV record of processing activities - one row
     * per store/source/interface, the shape an administrator files for a GDPR
     * Art. 30 / NIS2 Art. 21 review. It is built from what is already on screen
     * (the backend has already resolved the authoritative personal-data scope),
     * so the file matches the register exactly, credentials and all excluded.
     */
    protected exportCsv(): void {
        const headers = [
            "Node", "Collected at", "Owner component", "Endpoint", "Local id", "Product",
            "Locator", "Input", "Output", "Types", "Locality", "Personal data",
            "Retained store", "Erasure component", "Secret reference"
        ];
        const collectedAt = this.report?.collectedAt ? new Date(this.report.collectedAt).toISOString() : "";
        const rows = this.endpoints.map(e => {
            const retained = this.isRetainingStore(e);
            return [
                e.nodeId || this.report?.nodeId || "",
                collectedAt,
                e.ownerComponent,
                e.description,
                e.localId,
                e.product,
                e.endpoint,
                e.input ? "yes" : "no",
                e.output ? "yes" : "no",
                (e.types || []).join("; "),
                e.locality || "",
                e.personalData ? "yes" : "no",
                retained ? "yes" : "no",
                e.disposer || (retained ? "NONE" : ""),
                e.secretReference || ""
            ];
        });
        const csv = [headers, ...rows]
            .map(row => row.map(v => this.csvCell(v)).join(","))
            .join("\r\n");

        // Prepend a UTF-8 BOM so Excel opens accented text correctly.
        const blob = new Blob(["﻿" + csv], { type: "text/csv;charset=utf-8" });
        const url = URL.createObjectURL(blob);
        const stamp = new Date().toISOString().replace(/[:T]/g, "-").slice(0, 16);
        const node = (this.report?.nodeId || "node").replace(/[^a-zA-Z0-9._-]/g, "_");
        const link = document.createElement("a");
        link.href = url;
        link.download = `gebo-data-flow-register-${node}-${stamp}.csv`;
        link.click();
        URL.revokeObjectURL(url);
    }

    /** Quotes a CSV cell, doubling any embedded quotes (RFC 4180). */
    private csvCell(value: string): string {
        const text = value == null ? "" : String(value);
        return /[",\r\n]/.test(text) ? `"${text.replace(/"/g, '""')}"` : text;
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

        this.propagatePersonalData(endpoints, transformations);

        this.endpoints = endpoints;
        this.transformations = transformations;
        this.summary = {
            endpoints: endpoints.length,
            transformations: transformations.length,
            components: components.size,
            externalEndpoints: endpoints.filter(e => e.locality === "EXTERNAL_PROVIDER").length,
            personalDataEndpoints: endpoints.filter(e => e.personalData).length,
            // Only endpoints that actually RETAIN data (stores) count towards the
            // erasure gap; model/web endpoints process but retain nothing, and their
            // configs are admin-managed, so they are not an Art. 17 concern.
            retainingWithoutErasure: endpoints.filter(e => this.isRetainingStore(e) && !e.disposer).length
        };
    }

    /**
     * A flow carries personal data only when it originates from one or more data
     * sources the controller has classified as holding personal data
     * (GProjectEndpoint.personalData). The backend seeds that flag on the source
     * endpoints only; here it is propagated to everything in the same connected
     * data-flow component - the stores fed by a personal source, and the query
     * paths that read those stores - so the determination follows the actual
     * wiring rather than a blanket assumption. Traversal is undirected: a store is
     * personal because a personal source wrote to it, and a chat/agent flow is
     * personal because it reads a store that a personal source wrote to.
     */
    private propagatePersonalData(endpoints: DataFlowEndpointNode[], transformations: DataFlowTransformationNode[]): void {
        const seeds = endpoints.filter(e => e.personalData).map(e => e.qualifiedId);
        if (seeds.length === 0) {
            return;
        }
        const adjacency = new Map<string, string[]>();
        const link = (a: string, b: string) => {
            if (!adjacency.has(a)) { adjacency.set(a, []); }
            if (!adjacency.has(b)) { adjacency.set(b, []); }
            adjacency.get(a)!.push(b);
            adjacency.get(b)!.push(a);
        };
        for (const t of transformations) {
            if (t.sourceId) { link(t.sourceId, t.qualifiedId); }
            if (t.destinationId) { link(t.qualifiedId, t.destinationId); }
        }
        const reached = new Set<string>(seeds);
        const queue = [...seeds];
        while (queue.length > 0) {
            const id = queue.shift()!;
            for (const next of adjacency.get(id) || []) {
                if (!reached.has(next)) {
                    reached.add(next);
                    queue.push(next);
                }
            }
        }
        for (const endpoint of endpoints) {
            endpoint.personalData = reached.has(endpoint.qualifiedId);
        }
    }

    /** Mirrors the backend's GDataFlowMetaInfos.qualifiedId(...) convention. */
    private qualify(ownerComponent: string, localId: string): string {
        return ownerComponent + "<->" + localId;
    }

    /** Endpoint kinds that actually retain data - the ones erasure applies to. */
    private static readonly STORE_TYPES = new Set<string>([
        "DATABASE", "VECTORIAL_DATABASE", "GRAPH_DATABASE", "FULLTEXT_INDEX",
        "CHUNK", "OBJECT_STORAGE", "LOCAL_FILESYSTEM", "CHAT_SESSION"
    ]);

    private isRetainingStore(endpoint: DataFlowEndpointNode): boolean {
        return endpoint.output && (endpoint.types || []).some(t => ComplianceComponent.STORE_TYPES.has(t));
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

            const knownEndpoints = new Set(this.endpoints.map(e => e.qualifiedId));

            // A processing step is shown when it is REACHABLE - its source endpoint
            // exists in the running configuration. The backend only emits a step
            // that is enabled in the workflow AND whose component is live in the
            // broker, so what appears is exactly the enabled pipeline for this
            // deployment: switching ai.gebo.opensearch.enabled / ai.gebo.neo4j.enabled
            // off, or a step the workflow disables per data source, removes the step
            // at the source. The step's target STORE is a separate node drawn only
            // when that store is actually configured (its endpoint was reported): an
            // enabled step whose store is not yet set up still appears as part of the
            // workflow, but the graph never invents a store that does not exist.
            const renderableTransformations = this.transformations.filter(t => knownEndpoints.has(t.sourceId));

            const edgesRaw: { source: string; target: string }[] = [];
            for (const transformation of renderableTransformations) {
                edgesRaw.push({ source: transformation.sourceId, target: transformation.qualifiedId });
                if (knownEndpoints.has(transformation.destinationId)) {
                    edgesRaw.push({ source: transformation.qualifiedId, target: transformation.destinationId });
                }
            }

            const allIds = [
                ...this.endpoints.map(e => e.qualifiedId),
                ...renderableTransformations.map(t => t.qualifiedId)
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

            // Group nodes into connected components (treating edges as
            // undirected) so each distinct data-flow tree is laid out in its own
            // horizontal band instead of being interleaved into one centred
            // block. A component flows left-to-right by level; separate
            // components are stacked top-to-bottom with a blank gap between them.
            const undirected = new Map<string, Set<string>>();
            allIds.forEach(id => undirected.set(id, new Set<string>()));
            edgesRaw.forEach(e => {
                undirected.get(e.source)?.add(e.target);
                undirected.get(e.target)?.add(e.source);
            });
            const componentOf = new Map<string, number>();
            let componentCount = 0;
            for (const startId of allIds) {
                if (componentOf.has(startId)) {
                    continue;
                }
                const stack = [startId];
                componentOf.set(startId, componentCount);
                while (stack.length > 0) {
                    const cur = stack.pop()!;
                    undirected.get(cur)!.forEach(n => {
                        if (!componentOf.has(n)) {
                            componentOf.set(n, componentCount);
                            stack.push(n);
                        }
                    });
                }
                componentCount++;
            }

            const COL_WIDTH = 420;
            const ROW_HEIGHT = 260;
            const COMPONENT_GAP = 240; // blank band separating distinct trees

            // Rows are assigned per (component, level) pair so nodes never
            // overlap; a component's height is that of its tallest level.
            const componentLevelGroups = new Map<string, string[]>();
            allIds.forEach(id => {
                const key = `${componentOf.get(id) || 0}:${levels.get(id) || 0}`;
                if (!componentLevelGroups.has(key)) {
                    componentLevelGroups.set(key, []);
                }
                componentLevelGroups.get(key)!.push(id);
            });
            const componentRows = new Array(componentCount).fill(1);
            componentLevelGroups.forEach((ids, key) => {
                const comp = Number(key.split(":")[0]);
                componentRows[comp] = Math.max(componentRows[comp], ids.length);
            });
            // Top of each component band = running sum of previous band heights
            // plus one gap each, so the trees sit clearly apart.
            const componentTop = new Array(componentCount).fill(0);
            let bandOffset = 0;
            for (let c = 0; c < componentCount; c++) {
                componentTop[c] = bandOffset;
                bandOffset += (componentRows[c] - 1) * ROW_HEIGHT + COMPONENT_GAP;
            }

            const nodes: any[] = [];
            const positionOf = (id: string) => {
                const comp = componentOf.get(id) || 0;
                const level = levels.get(id) || 0;
                const group = componentLevelGroups.get(`${comp}:${level}`) || [];
                const index = group.indexOf(id);
                // Centre each level's rows within its component band so shorter
                // levels sit mid-height against the tallest one.
                const bandHeight = (componentRows[comp] - 1) * ROW_HEIGHT;
                const levelHeight = (group.length - 1) * ROW_HEIGHT;
                const y = componentTop[comp] + (bandHeight - levelHeight) / 2 + index * ROW_HEIGHT;
                return { x: level * COL_WIDTH + 60, y };
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
            for (const transformation of renderableTransformations) {
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
        // The dialog content does not exist until it is shown, so the diagram must
        // be fit only once it has a real size. onDialogShow() fires when Primeng
        // has rendered the dialog; this afterNextRender is the fallback for the
        // already-open case.
        afterNextRender(() => this.fitDiagramToViewport(), { injector: this.injector });
    }

    protected onDialogShow(): void {
        this.fitDiagramToViewport();
    }

    protected refreshFromDialog(): void {
        this.reloadViewedData();
        afterNextRender(() => this.fitDiagramToViewport(), { injector: this.injector });
    }

    /**
     * Fits and centers the graph. ng-diagram's viewport {x,y} is the flow
     * coordinate shown at the CENTRE of the viewport (not a translate), so
     * centering is simply: point it at the middle of the node bounding box, at a
     * scale that makes the box fit. Node sizes come from the rendered DOM, which
     * is only measured a tick or two after the dialog opens, so the fit is fired
     * at a few increasing delays; the last, post-measurement attempt lands it. It
     * is idempotent - each call just re-centers.
     */
    private fitDiagramToViewport(): void {
        for (const delay of [120, 350, 700]) {
            setTimeout(() => this.applyFit(), delay);
        }
    }

    private applyFit(): void {
        const hostEl = this.diagramHostRef?.nativeElement;
        if (!hostEl || hostEl.clientWidth <= 0 || hostEl.clientHeight <= 0 || this.lastLayoutNodes.length === 0) {
            return;
        }
        let minX = Infinity, minY = Infinity, maxX = -Infinity, maxY = -Infinity;
        for (const node of this.lastLayoutNodes) {
            const el = hostEl.querySelector<HTMLElement>(`[data-node-id="${node.id}"]`);
            const w = el?.offsetWidth || 260;
            const h = el?.offsetHeight || 200;
            minX = Math.min(minX, node.position.x);
            minY = Math.min(minY, node.position.y);
            maxX = Math.max(maxX, node.position.x + w);
            maxY = Math.max(maxY, node.position.y + h);
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
        // setViewport(x,y) sets the canvas translate. A node at flow (fx,fy) lands
        // at screen x = x + fx*scale (X has no extra offset), but the canvas's Y
        // origin is shifted down by exactly one host-height, so the Y translate has
        // to compensate for that fixed offset (measured: the flow origin sits one
        // clientHeight below the host top). Solving hostCentre = translate +
        // centreFlow*scale (+hostHeight for Y) gives the two expressions below.
        const centreX = (minX + maxX) / 2;
        const centreY = (minY + maxY) / 2;
        this.viewportService.setViewport(
            hostEl.clientWidth / 2 - centreX * scale,
            -hostEl.clientHeight / 2 - centreY * scale,
            scale
        );
    }
}
