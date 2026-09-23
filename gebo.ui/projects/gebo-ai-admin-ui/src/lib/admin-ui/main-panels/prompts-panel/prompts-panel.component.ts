/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */




/**
 * AI generated comments
 * Admin panel listing the prompt templates (use code, language, description) with
 * client-side paging, and opening the full editor for a selected one. Split out of
 * the chat-profiles panel so prompts have their own admin tab.
 */
import { Component, OnInit } from "@angular/core";
import { GeboAdminPromptsControllerService, GPromptTemplateLightView } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionType, GeboUIActionRoutingService } from "@Gebo.ai/reusable-ui";
import { PaginatorState } from "primeng/paginator";
import { AncestorPanelComponent } from "../ancestor-panel/ancestor-admin-panel.component";

/**
 * Prompt templates admin panel.
 */
@Component({
    selector: "prompts-panel-component",
    templateUrl: "prompts-panel.component.html",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "PromptsPanelModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("PromptsPanelComponent") }
    ]
})
export class PromptsPanelComponent extends AncestorPanelComponent implements OnInit {

    /** Full light list of prompt templates (loaded once, paged client-side). */
    public prompts: GPromptTemplateLightView[] = [];

    /** Index of the first row shown (client-side paging). */
    public promptsFirst: number = 0;

    /** Page size for the prompts list. */
    public promptsPageSize: number = 20;

    /** Whether the prompts list is loading. */
    public loadingPrompts: boolean = false;

    constructor(
        private geboPromptAdminService: GeboAdminPromptsControllerService,
        private geboUIActionEventService: GeboUIActionRoutingService) {
        super();
    }

    /**
     * Reloads the prompt templates list when the panel/tab is (re)activated.
     */
    public override reloadViewedData(): void {
        this.loadPrompts();
    }

    ngOnInit(): void {
        this.loadPrompts();
    }

    /**
     * Loads the lightweight prompt templates list (use code, language, description).
     */
    private loadPrompts() {
        this.loadingPrompts = true;
        this.geboPromptAdminService.getAllPromptConfigsLightList().subscribe({
            next: (data) => {
                this.prompts = data ?? [];
            },
            complete: () => {
                this.loadingPrompts = false;
            }
        });
    }

    /**
     * Opens the prompt template editor for a given light view (loaded fully by code),
     * refreshing the list after the edit completes.
     *
     * @param prompt the light view of the prompt template to edit
     */
    editPrompt(prompt: GPromptTemplateLightView) {
        this.geboUIActionEventService.routeEvent({
            actionType: GeboActionType.OPEN,
            context: {},
            contextType: "chatList",
            target: { code: prompt.code, description: prompt.description },
            targetType: "GPromptConfig",
            onActionPerformed: (evt) => {
                this.loadPrompts();
            }
        });
    }

    /**
     * Handles client-side paging changes for the prompts list.
     *
     * @param p the new paginator state
     */
    onPromptPageChange(p: PaginatorState) {
        this.promptsFirst = p.first ?? 0;
        this.promptsPageSize = p.rows ?? this.promptsPageSize;
    }
}
