import { Component, input, Inject, forwardRef } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ButtonModule } from "primeng/button";
import { SimpleNode, NgDiagramNodeTemplate, NgDiagramNodeSelectedDirective, NgDiagramPortComponent } from "ng-diagram";
import { GeboAIAgentsNetworkAdminComponent } from "./gebo-ai-agents-network-admin.component";
import { AgentNetworkParticipant } from "@Gebo.ai/gebo-ai-rest-api";

@Component({
    selector: "gebo-ai-agent-node-component",
    standalone: true,
    imports: [CommonModule, ButtonModule, NgDiagramNodeSelectedDirective, NgDiagramPortComponent],
    hostDirectives: [{ directive: NgDiagramNodeSelectedDirective, inputs: ["node"] }],
    template: `
        <div class="node-card p-3 border-round shadow-2 bg-paper flex flex-column gap-2 text-left relative"
             style="min-width: 220px; border-top: 4px solid var(--primary-color); background-color: var(--surface-card, #ffffff);">
            
            <!-- Left Input Port -->
            <ng-diagram-port [side]="'left'" [type]="'target'" [id]="'port-left'" 
                             [style.pointer-events]="parent.readonly ? 'none' : 'auto'"></ng-diagram-port>

            <!-- Right Output Port -->
            <ng-diagram-port [side]="'right'" [type]="'source'" [id]="'port-right'" 
                             [style.pointer-events]="parent.readonly ? 'none' : 'auto'"></ng-diagram-port>

            <div class="flex justify-content-between align-items-center">
                <span class="font-bold text-lg text-primary">{{node().data.networkAgentName}}</span>
                <div class="flex gap-1">
                    <span *ngIf="node().data.inputNode" class="p-1 border-round bg-green-100 text-green-700 text-xs font-semibold flex align-items-center gap-1" title="Input Node">
                        <i class="pi pi-sign-in"></i> IN
                    </span>
                    <span *ngIf="node().data.outputNode" class="p-1 border-round bg-blue-100 text-blue-700 text-xs font-semibold flex align-items-center gap-1" title="Output Node">
                        <i class="pi pi-sign-out"></i> OUT
                    </span>
                </div>
            </div>
            
            <div class="text-sm flex align-items-center justify-content-between gap-1">
                <div class="text-overflow-ellipsis overflow-hidden" style="max-width: 150px;">
                    <span class="text-muted-color font-semibold">Config: </span>
                    <span [title]="parent.getAgentDescription(node().data.agentConfigCode)">
                        {{parent.getAgentDescription(node().data.agentConfigCode)}}
                    </span>
                </div>
                <button pButton icon="pi pi-cog" class="p-button-rounded p-button-text p-button-sm p-0 w-2rem h-2rem"
                        [disabled]="parent.readonly"
                        (click)="$event.stopPropagation(); parent.editAgentConfig(node().data.agentConfigCode)"
                        title="Edit Agent Configuration"></button>
            </div>
            
            <div class="text-xs text-muted-color" *ngIf="node().data.agentContextualName">
                <span class="font-semibold">Context: </span>
                <span>{{node().data.agentContextualName}}</span>
            </div>
            
            <div class="text-xs text-muted-color" *ngIf="node().data.communicationPolicy">
                <span class="font-semibold">Policy: </span>
                <span>{{node().data.communicationPolicy}}</span>
            </div>

            <div class="flex justify-content-end gap-1 mt-2 border-top-1 surface-border pt-2" *ngIf="!parent.readonly">
                <button pButton icon="pi pi-plus" class="p-button-rounded p-button-text p-button-success p-button-sm p-0 w-2rem h-2rem" 
                        title="Add Child / Communicates With" (click)="$event.stopPropagation(); parent.openAddChild(node().data)"></button>
                <button pButton icon="pi pi-pencil" class="p-button-rounded p-button-text p-button-secondary p-button-sm p-0 w-2rem h-2rem" 
                        title="Edit Participant" (click)="$event.stopPropagation(); parent.openEditParticipant(node().data)"></button>
                <button pButton icon="pi pi-trash" class="p-button-rounded p-button-text p-button-danger p-button-sm p-0 w-2rem h-2rem" 
                        title="Delete Participant" (click)="$event.stopPropagation(); parent.deleteParticipant(node().data)"></button>
            </div>
        </div>
    `
})
export class AgentNodeComponent implements NgDiagramNodeTemplate<AgentNetworkParticipant, SimpleNode<AgentNetworkParticipant>> {
    node = input.required<SimpleNode<AgentNetworkParticipant>>();

    constructor(@Inject(forwardRef(() => GeboAIAgentsNetworkAdminComponent)) protected parent: GeboAIAgentsNetworkAdminComponent) {}
}
