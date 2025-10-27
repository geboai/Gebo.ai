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
 * This file provides a component for managing Gebo AI chat profiles in the admin interface. 
 * It includes functionality for creating, editing, and deleting chat profile configurations,
 * with support for configuring chat and embedding models, knowledge bases, and other settings.
 */

import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ChatModelsControllerService, EmbeddingModelsControllersService, GChatProfileConfiguration, GeboAdminChatProfilesConfigurationControllerService, GeboAdminPromptsControllerService, GKnowledgeBase, GPromptConfig, KnowledgeBaseControllerService, PromptTemplatesControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { forkJoin, map, Observable, of } from "rxjs";

/**
 * Interface that extends GChatProfileConfiguration with additional properties
 * to handle UI-specific model selection options for chat and embedding models
 */
interface ExtendedGChatProfileConfiguration extends GChatProfileConfiguration {
    useDefaultChatModel: string;
    useDefaultEmbeddingModel: string;
}

/**
 * Converts a backend chat profile configuration to the frontend extended model
 * by adding UI-specific properties for model selection
 * 
 * @param model The backend chat profile configuration
 * @returns Extended configuration with UI-specific properties
 */
function backend2frontend(model: GChatProfileConfiguration): ExtendedGChatProfileConfiguration {
    const rv: ExtendedGChatProfileConfiguration = {
        ...model,
        useDefaultChatModel: model?.chatModelReference ? "CHOOSEMODEL" : "DEFAULT",
        useDefaultEmbeddingModel: model?.embeddingModelReference ? "CHOOSEMODEL" : "DEFAULT",
    };
    return rv;
}

/**
 * Converts a frontend extended chat profile configuration back to the backend model
 * by removing UI-specific properties
 * 
 * @param model The extended frontend chat profile configuration
 * @returns Standard backend chat profile configuration
 */
function frontend2backend(model: ExtendedGChatProfileConfiguration): GChatProfileConfiguration {
    const c: any = {
        ...model
    };
    c.useDefaultChatModel = undefined;
    c.useDefaultEmbeddingModel = undefined;
    return c;
}

/**
 * Component for administering Gebo AI chat profiles
 * Provides a UI for creating, editing, and managing chat profiles with various
 * configuration options including models, knowledge bases, and access controls
 */
@Component({
    selector: "gebo-ai-chat-profile-admin-component",
    templateUrl: "gebo-ai-chat-profile-admin.component.html",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIChatProfileModule", multi: false },
        {
            provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIChatProfileAdminComponent),
            multi: false
        }]
})
export class GeboAIChatProfileAdminComponent extends BaseEntityEditingComponent<ExtendedGChatProfileConfiguration> {
    /** The entity name used for internal identification */
    protected override entityName: string = "GChatProfileConfiguration";

    /** Form group containing all editable fields for the chat profile */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        prompt: new FormControl(),
        embeddingModelReference: new FormControl(),
        chatModelReference: new FormControl(),
        topK: new FormControl(),
        knowledgeBaseCodes: new FormControl(),
        accessibleGroups: new FormControl(),
        accessibleUsers: new FormControl(),
        accessibleToAll: new FormControl(),
        enabledFunctions: new FormControl(),
        useDefaultChatModel: new FormControl(),
        useDefaultEmbeddingModel: new FormControl(),
        userChoosesKnowledgeBases: new FormControl(),
        similaritySearchThreshold: new FormControl(),
        disableMultiHopRag: new FormControl(),
        otherSearchSimilarityThreshold: new FormControl()
    });

    /** Available chat models to choose from */
    chatModelsData: { code?: string, description?: string, className?: string }[] = [];

    /** The default chat model configuration */
    defaultChatModel: any = {};

    /** Available embedding models to choose from */
    embeddingModelsData: { code?: string, description?: string, className?: string }[] = [];

    /** The default embedding model configuration */
    defaultEmbeddingModel: any = {};

    /** All available knowledge bases */
    allKnowledgeBaseData: GKnowledgeBase[] = [];

    /** Flag indicating if custom chat model selection is enabled */
    public isChooseChatModel: boolean = false;

    /** Flag indicating if custom embedding model selection is enabled */
    public isChooseEmbeddingModel: boolean = false;

    /**
     * Constructor initializing the component and setting up form control behaviors
     */
    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private geboChatProfileAdminService: GeboAdminChatProfilesConfigurationControllerService,
        private geboChatModels: ChatModelsControllerService,
        private embeddingModels: EmbeddingModelsControllersService,
        private knowledgeBaseController: KnowledgeBaseControllerService,
        private promptTemplatesControllerService: PromptTemplatesControllerService,
        confirmService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmService, geboUIActionRoutingService, outputForwardingService);

        // Disable otherSearchSimilarityThreshold when disableMultiHopRag is true
        this.formGroup.controls["disableMultiHopRag"].valueChanges.subscribe((value?: boolean) => {
            if (value === true) {
                this.formGroup.controls["otherSearchSimilarityThreshold"].disable();
            } else {
                this.formGroup.controls["otherSearchSimilarityThreshold"].enable();
            }
        });

        // Clear and disable knowledgeBaseCodes when userChoosesKnowledgeBases is true
        this.formGroup.controls["userChoosesKnowledgeBases"].valueChanges.subscribe((value: boolean) => {
            const userChooses: boolean = value === true;
            if (userChooses === true) {
                this.formGroup.controls["knowledgeBaseCodes"].setValue(undefined);
            }
            this.setControlEnabledAndRequired("knowledgeBaseCodes", userChooses === false);

        });

        // Enable/disable chat model selection based on useDefaultChatModel value
        this.formGroup.controls["useDefaultChatModel"].valueChanges.subscribe(
            x => {
                if (x) {
                    this.isChooseChatModel = x === "CHOOSEMODEL";
                    this.setControlEnabledAndRequired("chatModelReference", this.isChooseChatModel);
                }
            }
        );

        // Enable/disable embedding model selection based on useDefaultEmbeddingModel value
        this.formGroup.controls["useDefaultEmbeddingModel"].valueChanges.subscribe(
            x => {
                if (x) {
                    this.isChooseEmbeddingModel = x === "CHOOSEMODEL";
                    this.setControlEnabledAndRequired("embeddingModelReference", this.isChooseEmbeddingModel);
                }
            }
        );
    }

    /**
     * Sets a control value if it differs from the current value
     * 
     * @param ctrlName Name of the form control
     * @param value New value to set
     */
    private setIfDifferent(ctrlName: string, value: string) {
        const actualValue = this.formGroup.controls[ctrlName].value;
        if (actualValue === null || actualValue === undefined || actualValue !== value) {
            this.formGroup.controls[ctrlName].setValue(value);
        }
    }

    /**
     * Adds or removes the required validator for a form control
     * 
     * @param ctrlName Name of the form control
     * @param required Whether the control should be required
     */
    private setControlRequired(ctrlName: string, required: boolean) {
        const ctrl = this.formGroup.controls[ctrlName];
        if (required) {
            if (ctrl.hasValidator(Validators.required)) {
                ctrl.clearValidators();
                this.formGroup.updateValueAndValidity();
            }
        } else {
            if (!ctrl.hasValidator(Validators.required)) {
                ctrl.setValidators(Validators.required);
                this.formGroup.updateValueAndValidity();
            }
        }
    }

    /**
     * Enables or disables a form control and sets its required status accordingly
     * 
     * @param ctrlName Name of the form control
     * @param enabled Whether the control should be enabled
     */
    private setControlEnabledAndRequired(ctrlName: string, enabled: boolean) {
        const ctrl = this.formGroup.controls[ctrlName];
        if (ctrl.enabled !== enabled) {
            if (enabled === true) {
                ctrl.enable();
                this.setControlRequired(ctrlName, true);
            } else {
                ctrl.disable();
                this.setControlRequired(ctrlName, false);
            }
        }
    }

    /**
     * Handles new data when a chat profile is loaded
     * Converts backend data to frontend format and loads default prompt if needed
     * 
     * @param actualValue The chat profile configuration data
     */
    protected override onNewData(actualValue: ExtendedGChatProfileConfiguration): void {
        actualValue = backend2frontend(actualValue);
        this.formGroup.patchValue(actualValue);
        super.onNewData(actualValue);
        if (actualValue && !actualValue.prompt) {
            this.loadingRelatedBackend = true;
            this.promptTemplatesControllerService.getDefaultPrompt(true).subscribe({
                next: (value: GPromptConfig) => {
                    if (value && value.prompt) {
                        this.formGroup.controls["prompt"].setValue(value.prompt);
                    }
                },
                complete: () => {
                    this.loadingRelatedBackend = false;
                }
            });
        }
    }

    /**
     * Initializes the component and loads related data from the backend
     * Retrieves available chat models, embedding models, and knowledge bases
     */
    override ngOnInit(): void {
        super.ngOnInit();
        this.loadingRelatedBackend = true;
        forkJoin(this.geboChatModels.getRuntimeConfiguredChatModels(), this.embeddingModels.getRuntimeConfiguredEmbeddingModels(), this.knowledgeBaseController.getKnowledgeBases()).subscribe({
            next: (v) => {
                const chatModelsData: { code?: string, description?: string, className?: string }[] = [];
                if (v[0]) {
                    v[0].forEach(entry => {
                        chatModelsData.push({
                            ...entry.objectReference,
                            description: entry.configuration?.description
                        });
                        if (entry.configuration?.defaultModel === true) {
                            this.defaultChatModel = entry.configuration;
                        }
                    });
                }
                this.chatModelsData = chatModelsData;
                const embeddingModelsData: { code?: string, description?: string, className?: string }[] = [];
                if (v[1]) {
                    v[1].forEach(entry => {
                        embeddingModelsData.push({
                            ...entry.objectReference,
                            description: entry.configuration?.description
                        });
                        if (entry.configuration?.defaultModel === true) {
                            this.defaultEmbeddingModel = entry.configuration;
                        }
                    });
                }
                this.embeddingModelsData = embeddingModelsData;
                this.allKnowledgeBaseData = v[2];
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        })
    }

    /**
     * Finds a chat profile by its code
     * 
     * @param code The unique code identifying the chat profile
     * @returns Observable of the found chat profile or null
     */
    override findByCode(code: string): Observable<ExtendedGChatProfileConfiguration | null> {
        return this.geboChatProfileAdminService.findChatProfileConfigurationByCode(code).pipe(map(backend2frontend));
    }

    /**
     * Saves changes to an existing chat profile
     * 
     * @param value The updated chat profile configuration
     * @returns Observable of the saved chat profile
     */
    override save(value: ExtendedGChatProfileConfiguration): Observable<ExtendedGChatProfileConfiguration> {
        return this.geboChatProfileAdminService.updateChatProfile(frontend2backend(value)).pipe(map(backend2frontend));
    }

    /**
     * Creates a new chat profile
     * 
     * @param value The new chat profile configuration
     * @returns Observable of the created chat profile
     */
    override insert(value: ExtendedGChatProfileConfiguration): Observable<ExtendedGChatProfileConfiguration> {
        return this.geboChatProfileAdminService.insertChatProfile(frontend2backend(value)).pipe(map(backend2frontend));
    }

    /**
     * Deletes a chat profile
     * 
     * @param value The chat profile to delete
     * @returns Observable indicating deletion success
     */
    override delete(value: GChatProfileConfiguration): Observable<boolean> {
        return this.geboChatProfileAdminService.deleteChatProfile(value);
    }

    /**
     * Checks if a chat profile can be deleted
     * Currently always returns true
     * 
     * @param value The chat profile to check
     * @returns Observable with deletion status and message
     */
    override canBeDeleted(value: GChatProfileConfiguration): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "can delete" });
    }
}