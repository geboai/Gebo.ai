/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { ChatModelsControllerService, ConfigurationEntry, EmbeddingModelsControllersService, GBaseChatModelConfig, GChatModelType, GEmbeddingModelType } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GeboActionPerformedEvent, GeboActionType, GeboUIActionRequest, GeboUIActionRoutingService } from "@Gebo.ai/reusable-ui";
import { forkJoin } from "rxjs";
import { AncestorPanelComponent } from "../ancestor-panel/ancestor-admin-panel.component";

/**
 * AI generated comments
 * LlmsSystemsComponent handles the management of Language Learning Models (LLMs) systems
 * in the application. It provides functionality to view, create, and edit chat and embedding
 * model configurations. The component extends AncestorPanelComponent and uses Angular reactive forms
 * to manage user inputs for model selection.
 */
@Component({
    selector: "llms-systems-component",
    templateUrl: "llms-systems.component.html",
    standalone: false,
    providers: [{
        provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("LlmsSystemsComponent"),
        multi: true
    }]
})
export class LlmsSystemsComponent extends AncestorPanelComponent implements OnInit {
    chatModels: ConfigurationEntry[] = [];
    embeddingModels: ConfigurationEntry[] = [];
    chatModelsTypes: GChatModelType[] = [];
    embeddingModelsTypes: GEmbeddingModelType[] = [];

    /**
     * Overrides the parent method to reload data when the panel needs to refresh
     */
    public override reloadViewedData(): void {
        this.loadData();
    }
    public loading: boolean = false;
    //chatModels: [] = [];
    //embeddingModels: ConfigurationEntryGBaseEmbeddingModelConfigGBaseEmbeddingModelChoice[] = [];
    //chatModelsTypes: GChatModelType[] = [];
    //embeddingModelsTypes: GEmbeddingModelType[] = [];

    /**
     * Form group to manage the user's model type selections for chat and embedding models
     */
    createChoices: FormGroup = new FormGroup({
        chat: new FormControl(),
        embed: new FormControl()
    });

    /**
     * Initializes the component with required services
     * 
     * @param chatModelsControllerService Service for chat model operations
     * @param embeddingModelsControllerService Service for embedding model operations
     * @param geboUIActionRouter Service for routing UI actions
     */
    constructor(private chatModelsControllerService: ChatModelsControllerService,
        private embeddingModelsControllerService: EmbeddingModelsControllersService,
        private geboUIActionRouter: GeboUIActionRoutingService) {
        super()
    }

    /**
     * Configures a new chat model based on the user's selection
     * Creates a new model configuration and routes a NEW action request
     */
    public configureChatModel() {
        const formValue = this.createChoices.value;
        const choosedType = this.chatModelsTypes.find(x => x.code === formValue.chat);
        const chatModelConfig: GBaseChatModelConfig = {
            accessibleToAll: true,
            modelTypeCode: choosedType?.code
        };
        if (choosedType) {
            const entityName = choosedType.modelConfigurationClass;
            const action: GeboUIActionRequest = {
                actionType: GeboActionType.NEW,
                context: {},
                contextType: "LLMPage",
                target: chatModelConfig,
                targetType: entityName ? entityName : "",
                onActionPerformed: (event: GeboActionPerformedEvent) => {
                    this.loadData();
                }
            };
            this.geboUIActionRouter.routeEvent(action);
        }
    }

    /**
     * Configures a new embedding model based on the user's selection
     * Creates a new model configuration and routes a NEW action request
     */
    public configureEmbeddingModel() {
        const formValue = this.createChoices.value;
        const choosedType = this.embeddingModelsTypes.find(x => x.code === formValue.embed);
        const modelConfig: GBaseChatModelConfig = {
            modelTypeCode: choosedType?.code
        };
        if (choosedType) {
            const entityName = choosedType.modelConfigurationClass;
            const action: GeboUIActionRequest = {
                actionType: GeboActionType.NEW,
                context: {},
                contextType: "LLMPage",
                target: modelConfig,
                targetType: entityName ? entityName : "",
                onActionPerformed: (event: GeboActionPerformedEvent) => {
                    this.loadData();
                }
            };
            this.geboUIActionRouter.routeEvent(action);
        }
    }

    /**
     * Opens the edit interface for a chat model configuration
     * 
     * @param model The model configuration entry to edit
     */
    public editModel(model: ConfigurationEntry) {
        if (model.configuration && model.objectReference) {
            const entityName = model.objectReference?.className;
            const action: GeboUIActionRequest = {
                actionType: GeboActionType.OPEN,
                context: {},
                contextType: "LLMPage",
                target: model.configuration,
                targetType: entityName ? entityName : "",
                onActionPerformed: (event: GeboActionPerformedEvent) => {
                    this.loadData();
                }
            };
            this.geboUIActionRouter.routeEvent(action);
        }
    }

    /**
     * Opens the edit interface for an embedding model configuration
     * 
     * @param model The model configuration entry to edit
     */
    public editEmbeddingModel(model: ConfigurationEntry) {
        if (model.configuration && model.objectReference) {
            const entityName = model.objectReference?.className;
            const action: GeboUIActionRequest = {
                actionType: GeboActionType.OPEN,
                context: {},
                contextType: "LLMPage",
                target: model.configuration,
                targetType: entityName ? entityName : "",
                onActionPerformed: (event: GeboActionPerformedEvent) => {
                    this.loadData();
                }
            };
            this.geboUIActionRouter.routeEvent(action);
        }
    }

    /**
     * Loads all required data from the API services using forkJoin to combine multiple requests
     * Updates the component's state with the fetched data upon completion
     */
    private loadData(): void {
        this.loading = true;
        forkJoin([this.chatModelsControllerService.getRuntimeConfiguredChatModels(), this.embeddingModelsControllerService.getRuntimeConfiguredEmbeddingModels(), this.chatModelsControllerService.getChatModelTypes(), this.embeddingModelsControllerService.getEmbeddingModelTypes()]).subscribe({
            next: (cfgs) => {
                this.chatModels = cfgs[0];
                this.embeddingModels = cfgs[1];
                this.chatModelsTypes = cfgs[2];
                this.embeddingModelsTypes = cfgs[3];
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

    /**
     * Angular lifecycle hook that initializes the component by loading data
     */
    ngOnInit(): void {
        this.loadData();
    }

}