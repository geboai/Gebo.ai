/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, input } from "@angular/core";

import { SimpleNode, NgDiagramNodeTemplate, NgDiagramNodeSelectedDirective, NgDiagramPortComponent } from "ng-diagram";
import { DataFlowEndpointNode } from "./compliance-data-flow.model";

/**
 * A store or interface in the compliance data-flow graph.
 *
 * The border colour encodes locality, which is the datum a GDPR Art. 44 review
 * starts from: red for an endpoint outside the organization, amber for one
 * inside the network but outside this deployment, green for one that ships with
 * the installation. Grey means the reporting component did not determine it -
 * shown as undetermined rather than quietly assumed to be local.
 */
@Component({
    selector: "gebo-ai-data-endpoint-node-component",
    standalone: true,
    imports: [NgDiagramNodeSelectedDirective, NgDiagramPortComponent],
    hostDirectives: [{ directive: NgDiagramNodeSelectedDirective, inputs: ["node"] }],
    template: `
        <div class="node-card p-3 border-round shadow-2 flex flex-column gap-2 text-left relative"
          style="min-width: 240px; max-width: 280px; background-color: var(--surface-card, #ffffff);"
          [style.border-top]="'4px solid ' + localityColor()">

          <ng-diagram-port [side]="'left'" [type]="'target'" [id]="'port-left'"
            [style.pointer-events]="'none'"></ng-diagram-port>
          <ng-diagram-port [side]="'right'" [type]="'source'" [id]="'port-right'"
            [style.pointer-events]="'none'"></ng-diagram-port>

          <div class="flex justify-content-between align-items-start gap-2">
            <span class="font-bold text-primary" [title]="node().data.description">
              {{ node().data.description }}
            </span>
            <div class="flex gap-1 flex-shrink-0">
              @if (node().data.input) {
                <span class="p-1 border-round bg-green-100 text-green-700 text-xs font-semibold" title="Data is read from here">IN</span>
              }
              @if (node().data.output) {
                <span class="p-1 border-round bg-blue-100 text-blue-700 text-xs font-semibold" title="Data is written here">OUT</span>
              }
            </div>
          </div>

          <div class="text-xs text-muted-color">
            <span class="font-semibold">{{ productLabel() }}: </span>{{ node().data.product }}
          </div>

          <div class="text-xs text-overflow-ellipsis overflow-hidden" [title]="node().data.endpoint">
            <span class="font-semibold text-muted-color">Endpoint: </span>
            <code>{{ node().data.endpoint }}</code>
          </div>

          @if (node().data.types?.length) {
            <div class="flex flex-wrap gap-1">
              @for (type of node().data.types; track type) {
                <span class="p-1 border-round surface-200 text-xs">{{ type }}</span>
              }
            </div>
          }

          <div class="flex flex-wrap gap-1 mt-1">
            <span class="p-1 border-round text-xs font-semibold"
              [style.background-color]="localityBadgeBg()" [style.color]="localityColor()"
              [title]="localityTitle()">
              {{ localityLabel() }}
            </span>
            @if (node().data.personalData) {
              <span class="p-1 border-round bg-orange-100 text-orange-700 text-xs font-semibold"
                [title]="personalDataTitle()">
                <i class="pi pi-user"></i> {{ personalDataLabel() }}
              </span>
            }
          </div>

          <div class="text-xs text-muted-color border-top-1 surface-border pt-2">
            @if (node().data.disposer) {
              <div [title]="'Erasure component: ' + node().data.disposer">
                <i class="pi pi-trash text-green-600"></i>
                <span class="font-semibold"> Erasable by: </span>{{ node().data.disposer }}
              </div>
            } @else if (isRetainingStore()) {
              <div title="This store retains data but no disposer component is wired for it - GDPR Art. 17 erasure cannot be demonstrated">
                <i class="pi pi-exclamation-triangle text-orange-600"></i>
                <span class="font-semibold"> No erasure component wired</span>
              </div>
            } @else if (isManagedConfig()) {
              <div title="A model / provider endpoint does not retain data; its configuration is created, changed and deleted by an administrator through the provider's model controller">
                <i class="pi pi-cog text-muted-color"></i>
                <span class="font-semibold"> Config managed by admin</span> (no data retained)
              </div>
            }
            @if (node().data.secretReference) {
              <div class="mt-1" [title]="'Secret code: ' + node().data.secretReference">
                <i class="pi pi-key"></i>
                <span class="font-semibold"> Credential: </span>{{ node().data.secretReference }}
              </div>
            }
            <div class="mt-1" [title]="node().data.ownerComponent">
              <i class="pi pi-box"></i>
              <span class="font-semibold"> Reported by: </span>{{ node().data.ownerComponent }}
            </div>
          </div>
        </div>
        `
})
export class DataEndpointNodeComponent implements NgDiagramNodeTemplate<DataFlowEndpointNode, SimpleNode<DataFlowEndpointNode>> {
    node = input.required<SimpleNode<DataFlowEndpointNode>>();

    /** The endpoint kinds that actually RETAIN data - the ones GDPR Art. 17 erasure applies to. */
    private static readonly STORE_TYPES = new Set<string>([
        "DATABASE", "VECTORIAL_DATABASE", "GRAPH_DATABASE", "FULLTEXT_INDEX",
        "CHUNK", "OBJECT_STORAGE", "LOCAL_FILESYSTEM", "CHAT_SESSION"
    ]);

    /** For a model / web-search endpoint the "product" is really its provider. */
    protected productLabel(): string {
        return this.isModelOrSearch() ? "Provider" : "Product";
    }

    /** A retaining store that data comes to rest in - the erasure warning belongs here. */
    protected isRetainingStore(): boolean {
        const types = this.node().data.types || [];
        return this.node().data.output === true && types.some(t => DataEndpointNodeComponent.STORE_TYPES.has(t));
    }

    /**
     * A model or external-search endpoint: it processes/transfers data but retains
     * none, and its configuration is inserted/modified/deleted by an admin through
     * the provider's model controller - so "no erasure component" does not apply.
     */
    protected isManagedConfig(): boolean {
        return this.isModelOrSearch() && !this.isRetainingStore();
    }

    private isModelOrSearch(): boolean {
        const types = this.node().data.types || [];
        return types.includes("LLM_ENDPOINT") || types.includes("WEB_SEARCH");
    }

    /**
     * The personal-data marker is a SCOPE indicator, never an automated
     * determination: the platform cannot tell from configuration whether ingested
     * documents or queries hold personal data or purely business/company data -
     * that is the data controller's assessment. The wording is therefore honest
     * about what is actually known, and varies by endpoint kind.
     */
    protected personalDataLabel(): string {
        const types = this.node().data.types || [];
        if (types.includes("CHAT_SESSION")) {
            // A user's query/session is that user's own activity - personal by
            // construction, whatever its subject matter.
            return "Personal data (user activity)";
        }
        if (this.isModelOrSearch()) {
            return "May process personal data";
        }
        return "May contain personal data";
    }

    protected personalDataTitle(): string {
        const types = this.node().data.types || [];
        if (types.includes("CHAT_SESSION")) {
            return "A user's query and session are that user's own activity - personal data by construction, "
                + "independent of subject matter.";
        }
        const verb = this.isModelOrSearch() ? "processes" : "may retain";
        return "This endpoint " + verb + " ingested content or query text. Whether that content actually contains "
            + "personal data or is purely business/company data cannot be inferred from configuration - it is a "
            + "determination for the data controller (DPO). Flagged here only to bring the flow into scope for that "
            + "assessment.";
    }

    protected localityColor(): string {
        switch (this.node().data.locality) {
            case "EXTERNAL_PROVIDER": return "var(--red-500, #ef4444)";
            case "SAME_NETWORK": return "var(--orange-500, #f59e0b)";
            case "LOCAL_DEPLOYMENT": return "var(--green-500, #22c55e)";
            default: return "var(--surface-400, #9ca3af)";
        }
    }

    protected localityBadgeBg(): string {
        switch (this.node().data.locality) {
            case "EXTERNAL_PROVIDER": return "var(--red-100, #fee2e2)";
            case "SAME_NETWORK": return "var(--orange-100, #ffedd5)";
            case "LOCAL_DEPLOYMENT": return "var(--green-100, #dcfce7)";
            default: return "var(--surface-200, #e5e7eb)";
        }
    }

    protected localityLabel(): string {
        switch (this.node().data.locality) {
            case "EXTERNAL_PROVIDER": return "External provider";
            case "SAME_NETWORK": return "Same network";
            case "LOCAL_DEPLOYMENT": return "Local deployment";
            default: return "Locality undetermined";
        }
    }

    protected localityTitle(): string {
        switch (this.node().data.locality) {
            case "EXTERNAL_PROVIDER":
                return "A third party outside the organization. Content sent here is a transfer to an external processor (GDPR Art. 44 / Art. 46).";
            case "SAME_NETWORK":
                return "Inside the organization's network but outside this deployment.";
            case "LOCAL_DEPLOYMENT":
                return "Ships and runs with this installation.";
            default:
                return "The reporting component did not determine the locality of this endpoint.";
        }
    }
}
