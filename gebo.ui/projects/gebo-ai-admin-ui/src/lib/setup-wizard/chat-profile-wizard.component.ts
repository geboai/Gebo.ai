/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { Component, Injectable } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { DataPage,  FunctionsLookupControllerService, GChatProfileConfiguration, GeboAdminChatProfilesConfigurationControllerService, GeboFastChatProfileStatusControllerService, GKnowledgeBase, GPromptConfig, KnowledgeBaseControllerService, PageGChatProfileConfiguration, PromptTemplatesControllerService, ToolCategoriesTree, ToolReference } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, GeboActionType, GeboUIActionRequest, GeboUIActionRoutingService, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { PaginatorState } from "primeng/paginator";
import { forkJoin, map, Observable } from "rxjs";
/**
 * AI generated comments
 * 
 * This module provides functionality for managing chat profiles in the Gebo.ai application.
 * It includes a service for checking chat profile setup status and a component for a chat profile setup wizard.
 */

/**
 * Service that extends AbstractStatusService to check if chat profiles are set up
 * in the system. This is used to determine if this step in the setup wizard is complete.
 */
@Injectable()
export class ChatProfileStatusService extends AbstractStatusService {
    constructor(private chatProfileStatusService: GeboFastChatProfileStatusControllerService) {
        super()
    }

    /**
     * Overrides the base method to fetch the chat profile setup status
     * from the backend service and convert it to a boolean value.
     * 
     * @returns An Observable<boolean> indicating whether chat profiles are set up
     */
    public override getBooleanStatus(): Observable<boolean> {
        return this.chatProfileStatusService.getChatProfilesSetupStatus().pipe(map(r => r.isSetup === true));
    }
}

/**
 * Component that implements a wizard interface for creating and managing chat profiles.
 * Extends BaseWizardSectionComponent to integrate with the overall setup wizard flow.
 * Allows users to view existing chat profiles, create new ones, and modify existing ones.
 */
@Component({
    selector: "gebo-ai-chat-profile-wizard-component",
    templateUrl: "chat-profile-wizard.component.html",
    standalone: false
})
export class ChatProfileWizardComponent extends BaseWizardSectionComponent {
    /**
     * Pagination configuration for chat profiles list
     */
    public page: DataPage = {
        page: 0,
        pageSize: 20
    };
    
    /**
     * Contains the paginated chat profiles data from the backend
     */
    public chatProfiles?: PageGChatProfileConfiguration;
    
    /**
     * Stores the available functions that can be enabled for chat profiles
     */
    private functions: string[] = [];
    
    /**
     * Stores the default prompt configuration to use for new chat profiles
     */
    private defaultPrompt?: GPromptConfig;
    
    /**
     * Getter that returns the chat profile content from the paginated response,
     * or an empty array if no content is available
     */
    public get rows(): GChatProfileConfiguration[] {
        return this.chatProfiles?.content ? this.chatProfiles?.content : [];
    }
    
    /**
     * List of knowledge bases available in the system for selection
     */
    public knowledgeBases: GKnowledgeBase[] = [];
    
    /**
     * Form group for the chat profile creation form
     */
    public formGroup: FormGroup = new FormGroup({
        knowledgeBaseCode: new FormControl()
    })
    
    /**
     * Initializes the component with required services
     */
    constructor(setupWizardComunicationService: SetupWizardComunicationService,
        private chatProfilesService: GeboAdminChatProfilesConfigurationControllerService,
        private knowledgeBaseService: KnowledgeBaseControllerService,
        private functionsLookupService: FunctionsLookupControllerService,
        private promptTemplatesControllerService: PromptTemplatesControllerService,
        private geboUIActionRoutingService: GeboUIActionRoutingService) {
        super(setupWizardComunicationService);
    }
    
    /**
     * Initializes the component by loading knowledge bases, function trees, and default prompts
     * Overrides the parent class method to add additional initialization logic
     */
    override ngOnInit(): void {
        super.ngOnInit();
        const observables: [Observable<Array<GKnowledgeBase>>, Observable<Array<ToolCategoriesTree>>, Observable<GPromptConfig>] = [this.knowledgeBaseService.getKnowledgeBases(), this.functionsLookupService.getAllFunctionsTree(true), this.promptTemplatesControllerService.getDefaultPrompt(true)];

        forkJoin(observables).subscribe({
            next: (data) => {
                this.knowledgeBases = data[0];
                const functions = this.serializeFunctions(data[1]);
                this.defaultPrompt = data[2];
                if (functions) {
                    this.functions = [];
                    functions.forEach(x => {
                        if (x.name) {
                            this.functions.push(x.name);
                        }
                    });
                }
            },
            complete: () => {

            }
        }

        );
    }
    
    /**
     * Helper method to flatten the tree structure of functions into a simple array
     * 
     * @param data Array of ToolCategoriesTree objects representing the function hierarchy
     * @returns Array of ToolReference objects representing all available functions
     */
    private serializeFunctions(data: ToolCategoriesTree[]): ToolReference[] {
        const out: ToolReference[] = [];
        data.forEach(x => {
            if (x.toolsReference) {
                x.toolsReference.forEach(y => {
                    out.push(y);
                });

            }
        });
        return out;
    }
    
    /**
     * Loads chat profile data from the backend service
     * Overrides the parent class method
     */
    public override reloadData(): void {
        this.loading = true;
        this.chatProfilesService.getAllChatProfileConfiguration(this.page).subscribe({
            next: (chats) => {
                this.chatProfiles = chats;
                this.isSetupCompleted = this.chatProfiles?.content?.length ? true : false;
            },
            complete: () => {
                this.loading = false;
            }
        });
    }
    
    /**
     * Handles pagination events from the PrimeNG paginator
     * 
     * @param evt The paginator state containing page number and page size
     */
    public onPageChange(evt: PaginatorState) {
        this.page.page = evt.page;
        this.page.pageSize = evt.rows;
        this.reloadData();
    }
    
    /**
     * Opens the edit dialog for an existing chat profile
     * 
     * @param data The chat profile configuration to edit
     */
    public editChatProfile(data: GChatProfileConfiguration) {
        const event: GeboUIActionRequest = {
            actionType: GeboActionType.OPEN,
            context: {},
            contextType: "ChatProfileWizardComponent",
            target: data,
            targetType: "GChatProfileConfiguration",
            onActionPerformed: (ev) => {
                this.reloadData();
            }
        };
        this.geboUIActionRoutingService.routeEvent(event);
    }
    
    /**
     * Creates a new chat profile based on the selected knowledge base
     * and routes to the appropriate handler via the action routing service
     */
    public newChatProfile() {
        const data: { knowledgeBaseCode: string } = this.formGroup.value;
        const kbase: GKnowledgeBase | undefined = this.knowledgeBases.find(x => x.code === data.knowledgeBaseCode);
        if (kbase) {
            const newChatProfileData: GChatProfileConfiguration = {
                accessibleToAll: true,
                userChoosesKnowledgeBases:false,
                knowledgeBaseCodes:[data.knowledgeBaseCode],
                description: "Chat with knowledge base: " + kbase.description,
                enabledFunctions: this.functions,
                prompt: this.defaultPrompt?.prompt
            };
            const event: GeboUIActionRequest = {
                actionType: GeboActionType.NEW,
                context: {},
                contextType: "ChatProfileWizardComponent",
                target: newChatProfileData,
                targetType: "GChatProfileConfiguration",
                onActionPerformed: (ev) => {
                    this.reloadData();
                    this.closeWizard();
                }
            };
            this.geboUIActionRoutingService.routeEvent(event);
        }
    }
}