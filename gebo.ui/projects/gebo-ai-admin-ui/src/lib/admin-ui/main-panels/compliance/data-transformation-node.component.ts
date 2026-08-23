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
import { DataFlowTransformationNode } from "./compliance-data-flow.model";

/**
 * An engine applied between two endpoints - a chunker, an embedding model, a
 * graph extractor, a full-text indexer.
 *
 * Rendered as a distinct, narrower shape from the endpoint nodes so the graph
 * reads as "store -> what changes the data -> store" at a glance.
 */
@Component({
    selector: "gebo-ai-data-transformation-node-component",
    standalone: true,
    imports: [NgDiagramNodeSelectedDirective, NgDiagramPortComponent],
    hostDirectives: [{ directive: NgDiagramNodeSelectedDirective, inputs: ["node"] }],
    template: `
        <div class="node-card p-3 border-round shadow-2 flex flex-column gap-2 text-left relative"
          style="min-width: 200px; max-width: 240px; border: 2px dashed var(--primary-color);
                 background-color: var(--surface-50, #f8fafc);">

          <ng-diagram-port [side]="'left'" [type]="'target'" [id]="'port-left'"
            [style.pointer-events]="'none'"></ng-diagram-port>
          <ng-diagram-port [side]="'right'" [type]="'source'" [id]="'port-right'"
            [style.pointer-events]="'none'"></ng-diagram-port>

          <div class="flex align-items-center gap-2">
            <i class="pi pi-cog text-primary"></i>
            <span class="font-bold text-primary" [title]="node().data.description">
              {{ node().data.description }}
            </span>
          </div>

          @if (node().data.engineDescription) {
            <div class="text-xs text-muted-color" [title]="node().data.engineDescription">
              {{ node().data.engineDescription }}
            </div>
          }

          <div class="text-xs flex align-items-center gap-1 flex-wrap">
            <span class="font-semibold text-muted-color">Transforms</span>
            @for (from of node().data.transformFrom; track from) {
              <span class="p-1 border-round surface-200">{{ from }}</span>
            }
            <i class="pi pi-arrow-right text-xs"></i>
            @for (into of node().data.transformInto; track into) {
              <span class="p-1 border-round surface-200">{{ into }}</span>
            }
          </div>

          <div class="text-xs text-muted-color border-top-1 surface-border pt-2" [title]="node().data.ownerComponent">
            <i class="pi pi-box"></i>
            <span class="font-semibold"> Reported by: </span>{{ node().data.ownerComponent }}
          </div>
        </div>
        `
})
export class DataTransformationNodeComponent implements NgDiagramNodeTemplate<DataFlowTransformationNode, SimpleNode<DataFlowTransformationNode>> {
    node = input.required<SimpleNode<DataFlowTransformationNode>>();
}
