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
 * This module defines the GeboAIPromptAdminComponent which is responsible for managing AI prompt configurations.
 * It extends BaseEntityEditingComponent to provide CRUD functionality for GPromptConfig entities.
 * The component handles form creation, validation, and interactions with backend services for prompt administration.
 */
import { Component, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { ChatModelsControllerService, EmbeddingModelsControllersService, GeboAdminChatProfilesConfigurationControllerService, GeboAdminPromptsControllerService, GPromptConfig } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { Observable, of } from "rxjs";

/**
 * Component for managing AI prompt configurations in the admin interface.
 * Provides a UI for creating, editing, viewing and deleting prompt configurations.
 * Uses Angular's reactive forms for data management and validation.
 */
@Component({
    selector: "gebo-ai-prompt-admin-component",
    templateUrl: "gebo-ai-prompt-admin.component.html",
    standalone: false
})
export class GeboAIPromptAdminComponent extends BaseEntityEditingComponent<GPromptConfig> {
    /**
     * Name of the entity being managed by this component
     */
    protected override entityName: string = "GPromptConfig";
    
    /**
     * Form group that contains all the controls for editing a prompt configuration
     * Includes fields for code, description, prompt text, model information, and category
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        prompt: new FormControl(),
        modelProvider: new FormControl(),
        modelName: new FormControl(),
        promptCategory: new FormControl(),
        modelConfigurationReference: new FormControl()
    });
    
    /**
     * Array of available prompt categories to be displayed in the category selector
     */
    public promptCategories: string[] = [];
    
    /**
     * Component constructor that injects required services
     * @param injector Angular injector for dependency injection
     * @param geboFormGroupsService Service for managing form groups
     * @param geboPromptConfigurationService Service for CRUD operations on prompt configurations
     * @param geboChatModels Service for accessing available chat models
     * @param embeddingModels Service for accessing available embedding models
     * @param confirmService Service for showing confirmation dialogs
     * @param geboUIActionRoutingService Service for handling UI actions and routing
     * @param outputForwardingService Optional service for forwarding output to other components
     */
    constructor(injector:Injector,geboFormGroupsService: GeboFormGroupsService,
        private geboPromptConfigurationService: GeboAdminPromptsControllerService,
        private geboChatModels: ChatModelsControllerService,
        private embeddingModels: EmbeddingModelsControllersService,
        confirmService:ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector,geboFormGroupsService, confirmService,geboUIActionRoutingService,outputForwardingService);
    }
    
    /**
     * Lifecycle hook that initializes the component
     * Calls the parent initialization and loads the prompt categories
     */
    override ngOnInit(): void {
        super.ngOnInit();
        this.geboPromptConfigurationService.getPromptCategories().subscribe({
            next: (value) => {
                this.promptCategories = value;
            }
        });
    }
    
    /**
     * Finds a prompt configuration by its code
     * @param code The unique code identifier for the prompt configuration
     * @returns An Observable containing the found GPromptConfig or null if not found
     */
    override findByCode(code: string): Observable<GPromptConfig | null> {
        return this.geboPromptConfigurationService.findPromptConfigByCode(code);
    }
    
    /**
     * Saves (updates) an existing prompt configuration
     * @param value The GPromptConfig object to update
     * @returns An Observable containing the updated GPromptConfig
     */
    override save(value: GPromptConfig): Observable<GPromptConfig> {
        return this.geboPromptConfigurationService.updatePromptConfig(value);
    }
    
    /**
     * Inserts a new prompt configuration
     * @param value The GPromptConfig object to insert
     * @returns An Observable containing the inserted GPromptConfig
     */
    override insert(value: GPromptConfig): Observable<GPromptConfig> {
        return this.geboPromptConfigurationService.insertPromptConfig(value);
    }
    
    /**
     * Deletes a prompt configuration
     * @param value The GPromptConfig object to delete
     * @returns An Observable containing a boolean indicating success
     */
    override delete(value: GPromptConfig): Observable<boolean> {
        return this.geboPromptConfigurationService.deletePromptConfig(value);
    }
    
    /**
     * Checks if a prompt configuration can be deleted
     * Currently always returns true as there are no restrictions on deletion
     * @param value The GPromptConfig to check
     * @returns An Observable containing an object with canBeDeleted flag and message
     */
    override canBeDeleted(value: GPromptConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "can delete" });
    }
}