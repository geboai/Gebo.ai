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
 *
 * ChatProfilesComponent manages the display and editing of RAG chat profiles with
 * pagination. Prompt templates live in their own admin panel (PromptsPanelComponent).
 */
import { Component, OnInit } from "@angular/core";
import { DataPage, GChatProfileConfiguration, GeboAdminChatProfilesConfigurationControllerService, PagedModelGChatProfileConfiguration } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionType, GeboUIActionRoutingService } from "@Gebo.ai/reusable-ui";
import { PaginatorState } from "primeng/paginator";
import { AncestorPanelComponent } from "../ancestor-panel/ancestor-admin-panel.component";

/**
 * ChatProfilesComponent manages the display and manipulation of RAG chat profiles
 * with pagination support.
 */
@Component({
    selector: "chat-profiles-component",
    templateUrl: "chat-profiles.component.html",
    standalone: false,
    providers:[{ provide: GEBO_AI_MODULE, useValue: "ChatProfilesPanelModule", multi: false },{ provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("ChatProfilesComponent") }]
})
export class ChatProfilesComponent extends AncestorPanelComponent implements OnInit {
    /**
     * Reloads the chat profiles data.
     */
    public override reloadViewedData(): void {
        this.loadChatProfiles();
    }

    /**
     * Pagination configuration for chat profiles list.
     */
    profilesPage: DataPage = {
        page: 0,
        pageSize: 20
    };

    /**
     * Container for chat profiles data retrieved from the server.
     */
    chatprofiles: PagedModelGChatProfileConfiguration = {
        content: []
    };

    /**
     * Flag indicating whether chat profiles are currently being loaded.
     */
    public loadingChatProfiles: boolean = false;

    constructor(
        private geboChatProfilesAdminService: GeboAdminChatProfilesConfigurationControllerService,
        private geboUIActionEventService: GeboUIActionRoutingService) {
        super();
    }

    /**
     * Initialize the component by loading chat profiles data.
     */
    ngOnInit(): void {
        this.loadChatProfiles();
    }

    /**
     * Opens the edit interface for a specific chat profile configuration.
     * Refreshes chat profiles list after the edit operation completes.
     *
     * @param v The chat profile configuration to edit
     */
    editChatProfile(v: GChatProfileConfiguration) {
        this.geboUIActionEventService.routeEvent({
            actionType: GeboActionType.OPEN,
            context: {},
            contextType: "chatList",
            target: v,
            targetType: "GChatProfileConfiguration",
            onActionPerformed: (evt) => {
                this.loadChatProfiles();
            }
        });
    }

    /**
     * Creates a new chat profile with default settings and opens the edit interface.
     * Refreshes chat profiles list after the creation operation completes.
     */
    newChatProfile() {
        const chatProfile:GChatProfileConfiguration={
            accessibleToAll: true,
            userChoosesKnowledgeBases:false
        };
        this.geboUIActionEventService.routeEvent({
            actionType: GeboActionType.NEW,
            context: {},
            contextType: "chatList",
            target: chatProfile,
            targetType: "GChatProfileConfiguration",
            onActionPerformed: (evt) => {
                this.loadChatProfiles();
            }
        });
    }

    /**
     * Handles pagination changes for chat profiles list and reloads the data accordingly.
     *
     * @param p The new paginator state
     */
    onChatProfilePageChange(p: PaginatorState) {
        this.profilesPage.page = p.page;
        this.profilesPage.pageSize = p.rows;
        this.loadChatProfiles();
    }

    /**
     * Loads chat profile configurations from the server using the configured pagination.
     * Sets the loading flag during the operation.
     */
    private loadChatProfiles() {
        this.loadingChatProfiles = true;
        this.geboChatProfilesAdminService.getAllChatProfileConfiguration(this.profilesPage).subscribe({
            next: (data) => {
                this.chatprofiles = data;
            },
            complete: () => {
                this.loadingChatProfiles = false;
            }
        });
    }
}
