import { Component, input, Inject, forwardRef } from "@angular/core";

import { ButtonModule } from "primeng/button";
import { PopoverModule } from "primeng/popover";
import { TooltipModule } from "primeng/tooltip";
import { SimpleNode, NgDiagramNodeTemplate, NgDiagramNodeSelectedDirective, NgDiagramPortComponent } from "ng-diagram";
import { GeboAIAgentsNetworkAdminComponent } from "./gebo-ai-agents-network-admin.component";
import { AgentMountedTools, AgentNetworkParticipant, MountedTool } from "@Gebo.ai/gebo-ai-rest-api";

@Component({
    selector: "gebo-ai-agent-node-component",
    standalone: true,
    imports: [ButtonModule, PopoverModule, TooltipModule, NgDiagramNodeSelectedDirective, NgDiagramPortComponent],
    hostDirectives: [{ directive: NgDiagramNodeSelectedDirective, inputs: ["node"] }],
    template: `
        <div class="node-card p-3 border-round shadow-2 bg-paper flex flex-column gap-2 text-left relative cursor-pointer"
          style="min-width: 220px; max-width: 300px; border-top: 4px solid var(--primary-color); background-color: var(--surface-card, #ffffff);"
          [title]="parent.readonly ? 'Click to view this agent' : 'Click to edit this agent'"
          (pointerdown)="onPointerDown($event)"
          (pointerup)="onPointerUp($event)">
        
          <!-- Left Input Port -->
          <ng-diagram-port [side]="'left'" [type]="'target'" [id]="'port-left'"
          [style.pointer-events]="parent.readonly ? 'none' : 'auto'"></ng-diagram-port>
        
          <!-- Right Output Port -->
          <ng-diagram-port [side]="'right'" [type]="'source'" [id]="'port-right'"
          [style.pointer-events]="parent.readonly ? 'none' : 'auto'"></ng-diagram-port>
        
          <div class="flex justify-content-between align-items-center gap-1">
            <span class="font-bold text-lg text-primary white-space-nowrap overflow-hidden text-overflow-ellipsis"
              style="min-width: 0;" [title]="node().data.networkAgentName">{{node().data.networkAgentName}}</span>
            <div class="flex gap-1 flex-shrink-0">
              @if (node().data.inputNode) {
                <span class="p-1 border-round bg-green-100 text-green-700 text-xs font-semibold flex align-items-center gap-1" title="Input Node">
                  <i class="pi pi-sign-in"></i> IN
                </span>
              }
              @if (node().data.outputNode) {
                <span class="p-1 border-round bg-blue-100 text-blue-700 text-xs font-semibold flex align-items-center gap-1" title="Output Node">
                  <i class="pi pi-sign-out"></i> OUT
                </span>
              }
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
        
          @if (node().data.agentContextualName) {
            <div class="text-xs text-muted-color">
              <span class="font-semibold">Context: </span>
              <span>{{node().data.agentContextualName}}</span>
            </div>
          }
        
          @if (node().data.communicationPolicy) {
            <div class="text-xs text-muted-color">
              <span class="font-semibold">Policy: </span>
              <span>{{node().data.communicationPolicy}}</span>
            </div>
          }
        
          @if (parent.getMountedTools(node().data.agentConfigCode); as mounted) {
            <div class="flex flex-column gap-1 border-top-1 surface-border pt-2" style="max-width: 280px;"
              [class.opacity-50]="node().data.canCallTools === false"
              [pTooltip]="node().data.canCallTools === false ? 'These tools are mounted, but this participant is told not to call them (Can Call Tools is off)' : undefined"
              tooltipPosition="top" appendTo="body">
              <div class="flex align-items-center gap-1 text-xs">
                <i class="pi pi-wrench text-muted-color"></i>
                <span class="font-semibold text-muted-color">Tools ({{mounted.tools?.length || 0}})</span>
                @if (mounted.mountMode === 'AUTO') {
                  <span class="px-1 border-round bg-primary-50 text-primary-700 font-semibold"
                    [pTooltip]="'Auto mounting: every registered tool' + (mounted.excludedTools?.length ? ', but ' + mounted.excludedTools!.length + ' excluded' : '')"
                    tooltipPosition="top" appendTo="body">AUTO</span>
                } @else {
                  <span class="px-1 border-round surface-200 font-semibold"
                    pTooltip="The tools selected in the agent configuration" tooltipPosition="top" appendTo="body">SELECTED</span>
                }
              </div>
              @if (mounted.tools?.length) {
                <div class="flex flex-wrap gap-1">
                  @for (tool of visibleTools(mounted); track tool.name) {
                    <span class="px-1 border-round text-xs flex align-items-center gap-1 white-space-nowrap overflow-hidden text-overflow-ellipsis"
                      style="max-width: 130px;" [class]="categoryClass(tool)"
                      [pTooltip]="toolTooltip(tool)" tooltipPosition="top" appendTo="body">
                      <i class="pi pi-wrench" style="font-size: 0.6rem"></i>{{tool.name}}
                    </span>
                  }
                  @if (hiddenToolsCount(mounted) > 0 || mounted.excludedTools?.length) {
                    <button type="button" class="px-1 border-round text-xs border-1 surface-border surface-card cursor-pointer"
                      [title]="'Show all the tools of ' + node().data.networkAgentName"
                      (click)="$event.stopPropagation(); toolsPopover.toggle($event)">
                      {{hiddenToolsCount(mounted) > 0 ? '+' + hiddenToolsCount(mounted) : '…'}}
                    </button>
                  }
                </div>
              } @else {
                <span class="text-xs text-muted-color">No tools mounted</span>
              }
              <p-popover #toolsPopover appendTo="body">
                <div class="flex flex-column gap-2 text-sm" style="max-width: 420px; max-height: 360px; overflow: auto;">
                  <span class="font-semibold">Tools mounted by {{node().data.networkAgentName}} ({{mounted.mountMode === 'AUTO' ? 'auto mounting' : 'selected'}})</span>
                  @for (tool of sortedTools(mounted.tools); track tool.name) {
                    <div class="flex flex-column">
                      <span class="flex align-items-center gap-1">
                        <span class="px-1 border-round text-xs" [class]="categoryClass(tool)">{{tool.categoryDescription || tool.categoryCode || 'Tool'}}</span>
                        <span class="font-semibold">{{tool.name}}</span>
                      </span>
                      @if (tool.description) {
                        <span class="text-xs text-muted-color">{{tool.description}}</span>
                      }
                    </div>
                  }
                  @if (mounted.excludedTools?.length) {
                    <span class="font-semibold border-top-1 surface-border pt-2">Excluded from auto mounting ({{mounted.excludedTools!.length}})</span>
                    @for (tool of sortedTools(mounted.excludedTools); track tool.name) {
                      <span class="text-xs text-muted-color line-through" [title]="tool.description || ''">{{tool.name}}</span>
                    }
                  }
                </div>
              </p-popover>
            </div>
          }

          @if (!parent.readonly) {
            <div class="flex justify-content-end gap-1 mt-2 border-top-1 surface-border pt-2">
              <button pButton icon="pi pi-plus" class="p-button-rounded p-button-text p-button-success p-button-sm p-0 w-2rem h-2rem"
              title="Add Child / Communicates With" (click)="$event.stopPropagation(); parent.openAddChild(node().data)"></button>
              <button pButton icon="pi pi-pencil" class="p-button-rounded p-button-text p-button-secondary p-button-sm p-0 w-2rem h-2rem"
              title="Edit Participant" (click)="$event.stopPropagation(); parent.openEditParticipant(node().data)"></button>
              <button pButton icon="pi pi-trash" class="p-button-rounded p-button-text p-button-danger p-button-sm p-0 w-2rem h-2rem"
              title="Delete Participant" (click)="$event.stopPropagation(); parent.deleteParticipant(node().data)"></button>
            </div>
          }
        </div>
        `
})
export class AgentNodeComponent implements NgDiagramNodeTemplate<AgentNetworkParticipant, SimpleNode<AgentNetworkParticipant>> {
    node = input.required<SimpleNode<AgentNetworkParticipant>>();

    private pointerDownPos: { x: number; y: number } | null = null;

    /** The chips shown on the node; the rest open in the popover. */
    private static readonly VISIBLE_TOOLS = 6;

    constructor(@Inject(forwardRef(() => GeboAIAgentsNetworkAdminComponent)) protected parent: GeboAIAgentsNetworkAdminComponent) {}

    protected onPointerDown(event: PointerEvent): void {
        this.pointerDownPos = this.isInteractiveTarget(event.target) ? null : { x: event.clientX, y: event.clientY };
    }

    // Open the participant editor only on a genuine click, not at the end of a
    // drag that repositions the node (nodes are draggable while editable) and
    // not when the interaction started on an inner button or connection port.
    protected onPointerUp(event: PointerEvent): void {
        const start = this.pointerDownPos;
        this.pointerDownPos = null;
        if (!start || this.isInteractiveTarget(event.target)) {
            return;
        }
        const moved = Math.abs(event.clientX - start.x) + Math.abs(event.clientY - start.y);
        if (moved <= 4) {
            this.parent.openEditParticipant(this.node().data);
        }
    }

    protected sortedTools(tools: MountedTool[] | undefined): MountedTool[] {
        return [...(tools || [])].sort((a, b) =>
            (a.categoryCode || "").localeCompare(b.categoryCode || "") || (a.name || "").localeCompare(b.name || ""));
    }

    protected visibleTools(mounted: AgentMountedTools): MountedTool[] {
        return this.sortedTools(mounted.tools).slice(0, AgentNodeComponent.VISIBLE_TOOLS);
    }

    protected hiddenToolsCount(mounted: AgentMountedTools): number {
        return Math.max((mounted.tools?.length || 0) - AgentNodeComponent.VISIBLE_TOOLS, 0);
    }

    protected categoryClass(tool: MountedTool): string {
        return this.parent.getToolCategoryClass(tool.categoryCode);
    }

    protected toolTooltip(tool: MountedTool): string {
        return [tool.name, tool.categoryDescription || tool.categoryCode, tool.description].filter(x => !!x).join(" - ");
    }

    private isInteractiveTarget(target: EventTarget | null): boolean {
        return target instanceof Element && !!target.closest("button, ng-diagram-port");
    }
}
