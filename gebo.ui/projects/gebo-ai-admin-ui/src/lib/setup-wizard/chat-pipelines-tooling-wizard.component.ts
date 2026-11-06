/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GeboAdminChatPipelinesToolingControllerService, PipelineStepToolingInfo, PipelineToolingInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { TreeNode } from "primeng/api";

/** A step in the tree: the pipeline running it matters, a shared step has tools under each. */
interface StepNodeData {
    pipelineCode: string;
    step: PipelineStepToolingInfo;
}

/**
 * The tools the two models of each step of the chat pipelines can call: the pipelines
 * with their steps on one side, the step chosen on the other.
 */
@Component({
    selector: "gebo-ai-chat-pipelines-tooling-wizard-component",
    templateUrl: "chat-pipelines-tooling-wizard.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIChatPipelinesToolingWizardComponent") }]
})
export class GeboAIChatPipelinesToolingWizardComponent extends BaseWizardSectionComponent {
    protected nodes: TreeNode[] = [];
    protected selectedNode?: TreeNode;
    protected formGroup = new FormGroup({
        chatModelFunctions: new FormControl<string[]>([]),
        serviceModelFunctions: new FormControl<string[]>([])
    });

    constructor(setupWizardComunicationService: SetupWizardComunicationService,
        private toolingService: GeboAdminChatPipelinesToolingControllerService) {
        super(setupWizardComunicationService);
    }

    protected get selected(): StepNodeData | undefined {
        return this.selectedNode?.data;
    }

    public override reloadData(): void {
        this.loading = true;
        this.toolingService.getPipelinesTooling().subscribe({
            next: (pipelines) => {
                this.nodes = (pipelines || []).map(pipeline => this.pipelineNode(pipeline));
            },
            error: () => {
                this.loading = false;
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

    private pipelineNode(pipeline: PipelineToolingInfo): TreeNode {
        return {
            label: pipeline.description || pipeline.pipelineCode,
            key: pipeline.pipelineCode,
            selectable: false,
            expanded: true,
            children: (pipeline.steps || []).map(step => this.stepNode(pipeline.pipelineCode as string, step))
        };
    }

    private stepNode(pipelineCode: string, step: PipelineStepToolingInfo): TreeNode {
        const data: StepNodeData = { pipelineCode, step };
        return { label: step.stepId, key: pipelineCode + "/" + step.stepId, data, icon: this.stepIcon(step) };
    }

    /** A step with tools stands out in the tree. */
    private stepIcon(step: PipelineStepToolingInfo): string {
        return (step.chatModelFunctions?.length || step.serviceModelFunctions?.length) ? "pi pi-wrench" : "pi pi-minus";
    }

    protected onStepSelected(): void {
        this.userMessages = [];
        this.formGroup.reset({
            chatModelFunctions: this.selected?.step.chatModelFunctions ?? [],
            serviceModelFunctions: this.selected?.step.serviceModelFunctions ?? []
        });
    }

    protected save(): void {
        const selected = this.selected;
        if (!selected) {
            return;
        }
        this.loading = true;
        this.toolingService.saveStepTooling({
            pipelineCode: selected.pipelineCode,
            stepId: selected.step.stepId as string,
            chatModelFunctions: this.formGroup.controls.chatModelFunctions.value ?? [],
            serviceModelFunctions: this.formGroup.controls.serviceModelFunctions.value ?? []
        }).subscribe({
            next: (step) => {
                selected.step = step;
                if (this.selectedNode) {
                    this.selectedNode.icon = this.stepIcon(step);
                }
                this.onStepSelected();
                this.userMessages = [{ severity: "success", summary: "Saved", detail: "The tools of this step are saved" }];
            },
            error: () => {
                this.loading = false;
            },
            complete: () => {
                this.loading = false;
            }
        });
    }
}
