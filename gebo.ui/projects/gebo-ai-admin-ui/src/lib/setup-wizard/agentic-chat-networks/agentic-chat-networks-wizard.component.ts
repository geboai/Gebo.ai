/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
import { Component } from "@angular/core";
import {
    AgenticChatDefaultNetworkInfo, AgenticChatDefaultNetworksAdminControllerService, GAgentsNetwork, GUserMessage
} from "@Gebo.ai/gebo-ai-rest-api";
import { BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAITranslationService, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { forkJoin, Observable } from "rxjs";

type PipelineType = AgenticChatDefaultNetworkInfo.PipelineTypeEnum;

/** What the defaults operations answer: the updated defaults, or messages explaining why not. */
interface DefaultsOperationStatus {
    result?: AgenticChatDefaultNetworkInfo;
    messages?: Array<GUserMessage>;
}

/** One network the pipeline type can be handed to, with what it is and when to choose it. */
interface NetworkOption {
    value: string;
    label: string;
    description?: string;
    suggestedPurpose?: string;
}

/** One pipeline type, as the section shows it: its title and description are in the template. */
interface PipelineDefault {
    pipelineType: PipelineType;
    info?: AgenticChatDefaultNetworkInfo;
    options: NetworkOption[];
    /** The network being chosen. */
    chosen?: string;
}

/**
 * Setup section choosing, for each chat pipeline type, the system default network
 * of agents the chats are handed to, overriding the one of the application
 * configuration. A chat profile can still choose its own network for its chats.
 */
@Component({
    selector: "gebo-ai-agentic-chat-networks-wizard-component",
    templateUrl: "agentic-chat-networks-wizard.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "AgenticChatNetworksWizardModule", multi: false }, {
        provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("AgenticChatNetworksWizardComponent")
    }]
})
export class AgenticChatNetworksWizardComponent extends BaseWizardSectionComponent {
    protected messages: GUserMessage[] = [];
    protected pipelines: PipelineDefault[] = (["RAG_PIPELINE", "PURE_CHAT_PIPELINE"] as PipelineType[])
        .map(pipelineType => ({ pipelineType, options: [] }));

    constructor(setupWizardComunicationService: SetupWizardComunicationService,
        private defaultsService: AgenticChatDefaultNetworksAdminControllerService, private translationService: GeboAITranslationService) {
        super(setupWizardComunicationService);
    }

    /** Loads the defaults of every pipeline type and the networks each can be handed to. */
    public override reloadData(): void {
        this.loading = true;
        forkJoin([this.defaultsService.getAgenticChatDefaultNetworks(),
        ...this.pipelines.map(x => this.defaultsService.getChoosableChatNetworksOfAgents(x.pipelineType))]).subscribe({
            next: ([infos, ...choosable]) => {
                this.pipelines.forEach((pipeline, index) => {
                    pipeline.info = (infos ?? []).find(x => x.pipelineType === pipeline.pipelineType);
                    pipeline.options = ((choosable[index] ?? []) as GAgentsNetwork[]).map(x => ({
                        value: x.code ?? "",
                        label: x.description ? x.description + " (" + x.code + ")" : (x.code ?? ""),
                        description: x.description,
                        suggestedPurpose: x.suggestedPurpose
                    }));
                    pipeline.chosen = pipeline.info?.defaultChatNetworkOfAgents ?? pipeline.info?.effectiveChatNetworkOfAgents;
                });
                this.loading = false;
            },
            error: () => this.loading = false
        });
    }

    /** The label of a network code, its description when known. */
    protected networkLabel(pipeline: PipelineDefault, code?: string): string {
        if (!code) return "-";
        return pipeline.options.find(x => x.value === code)?.label ?? code;
    }

    /** Makes the chosen network the system default of the pipeline type. */
    protected save(pipeline: PipelineDefault): void {
        if (!pipeline.chosen) return;
        this.run(this.defaultsService.setAgenticChatDefaultNetwork({
            pipelineType: pipeline.pipelineType, defaultChatNetworkOfAgents: pipeline.chosen
        }));
    }

    /** Removes the system default: the one of the application configuration applies again. */
    protected reset(pipeline: PipelineDefault): void {
        this.run(this.defaultsService.resetAgenticChatDefaultNetwork({ pipelineType: pipeline.pipelineType }));
    }

    private run(operation: Observable<DefaultsOperationStatus>): void {
        this.loading = true;
        operation.subscribe({
            next: (status) => {
                this.showMessages(status.messages ?? []);
                this.reloadData();
            },
            error: () => this.loading = false
        });
    }

    /** Shows the backend messages in the current language, the original ones while not translated. */
    private showMessages(messages: GUserMessage[]): void {
        this.messages = messages;
        if (messages.length) {
            this.translationService.translateBackendMessages(messages).subscribe({
                next: (translated) => {
                    if (translated) this.messages = translated;
                }
            });
        }
    }
}
