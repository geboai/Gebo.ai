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
 * This file contains the ChatProfilesComponent which is responsible for managing chat profiles
 * and prompts in the Gebo.ai system. The component provides functionality to view, create,
 * and edit both chat profiles and prompts with pagination support.
 */
import { Component, OnInit } from "@angular/core";
import { DataPage, GChatProfileConfiguration, GeboAdminChatProfilesConfigurationControllerService, GeboAdminPromptsControllerService, GPromptTemplateLightView, PagedModelGChatProfileConfiguration } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionType, GeboUIActionRoutingService } from "@Gebo.ai/reusable-ui";
import { PaginatorState } from "primeng/paginator";
import { AncestorPanelComponent } from "../ancestor-panel/ancestor-admin-panel.component";

/**
 * ChatProfilesComponent manages the display and manipulation of chat profiles and prompts.
 * It extends the AncestorPanelComponent and implements OnInit interface for initialization.
 * This component handles loading, displaying, creating, and editing of chat profiles and prompts
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
     * Overrides the parent's reloadViewedData method to refresh both prompts and chat profiles data.
     */
    public override reloadViewedData(): void {
        this.loadPrompts();
        this.loadChatProfiles();
    }

    /**
     * Client-side pagination state for the prompts list (index of the first row).
     */
    public promptsFirst: number = 0;

    /** Page size for the prompts list. */
    public promptsPageSize: number = 20;

    /**
     * Full light list of prompt templates (loaded once, paged client-side).
     */
    public prompts: GPromptTemplateLightView[] = [];

    /**
     * Pagination configuration for chat profiles list.
     */
    profilesPage: DataPage = {
        page: 0,
        pageSize: 20
    };
    
   
    
    /**
     * Flag indicating whether prompts are currently being loaded.
     */
    public loadingPrompts: boolean = false;
    
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
    
    /**
     * Constructor initializes services needed for managing chat profiles and prompts.
     * 
     * @param geboPromptAdminService Service for handling prompt configurations
     * @param geboChatProfilesAdminService Service for handling chat profile configurations
     * @param geboUIActionEventService Service for routing UI actions
     */
    constructor(
        private geboPromptAdminService: GeboAdminPromptsControllerService,
        private geboChatProfilesAdminService: GeboAdminChatProfilesConfigurationControllerService,
        private geboUIActionEventService: GeboUIActionRoutingService) {
        super();
    }
    
    /**
     * Initialize the component by loading both prompts and chat profiles data.
     */
    ngOnInit(): void {
        this.loadPrompts();
        this.loadChatProfiles();
    }
    
    /**
     * Loads the lightweight prompt templates list (use code, language, description)
     * from the server. The list is paged client-side, so it is fetched once here.
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
     * Handles pagination changes for prompts list and reloads the data accordingly.
     * 
     * @param p The new paginator state
     */
    onPromptPageChange(p: PaginatorState) {
        this.promptsFirst = p.first ?? 0;
        this.promptsPageSize = p.rows ?? this.promptsPageSize;
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