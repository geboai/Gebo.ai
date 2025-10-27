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
 * This file defines the MistralAI Embedding Model administration component in the Gebo.ai application.
 * It provides UI and logic for managing MistralAI embedding model configurations including 
 * creating, updating, deleting and retrieving model configurations.
 */

import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GBaseChatModelChoice, GMistralEmbeddingModelConfig, MistralAiEmbeddingModelsConfigurationControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";
import { isValidUrl } from "../utils/url-ok";

/**
 * Component responsible for administering MistralAI embedding models.
 * Extends BaseEntityEditingComponent to manage CRUD operations for GMistralEmbeddingModelConfig entities.
 * Provides UI for configuring MistralAI embedding models, managing API secrets, and selecting models.
 */
@Component({
    selector: "gebo-ai-mistral-ai-embed-model-admin-component",
    templateUrl: "gebo-ai-mistralai-embedmodel-admin.component.html",
    standalone: false, providers: [ 
        { provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false }, 
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIMistralAIEmbedModelAdminComponent),
        multi: false
    }]
})
export class GeboAIMistralAIEmbedModelAdminComponent extends BaseEntityEditingComponent<GMistralEmbeddingModelConfig> {
    /**
     * The name of the entity being managed by this component
     */
    protected override entityName: string = "GMistralEmbeddingModelConfig";
    
    /**
     * Types of secrets that are allowed for MistralAI embedding models
     */
    allowedTypes: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.TOKEN];
    
    /**
     * Form group for managing the MistralAI embedding model configuration
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        modelTypeCode: new FormControl(),
        modelCode: new FormControl(),
        defaultModel:new FormControl(),
        choosedModel: new FormControl(),
        apiSecretCode: new FormControl()
    });
    
    /**
     * Tracks the old form values to detect changes
     */
    private oldValue: any = {};
    
    /**
     * Available model choices for MistralAI embedding models
     */
    modelChoicesData: GBaseChatModelChoice[] = [];
    
    /**
     * Observable for retrieving secrets related to MistralAI
     */
    identitiesObservable = this.secretControllerService.getSecretsByContextCode("mistralai");
    
    /**
     * Action request for creating a new secret for MistralAI
     */
    public newSecretAction= newSecretActionRequest("mistralai",this.entityName,this.entity);

    /**
     * Constructor initializes the component with required services and sets up form change subscriptions
     * to reload models when relevant form fields change
     */
    constructor(injector:Injector,geboFormGroupsService: GeboFormGroupsService,
        private MIstralEmbedModelConfigService: MistralAiEmbeddingModelsConfigurationControllerService,
        private secretControllerService: SecretsControllerService,
        confirmService:ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector,geboFormGroupsService,confirmService,geboUIActionRoutingService, outputForwardingService);
        this.manageOperationStatus=true;
        this.formGroup.valueChanges.subscribe(newValue => {
            if (!newValue.baseUrl && !newValue.apiSecretCode) {
                this.modelChoicesData = [];
            } else if ((newValue.baseUrl!==this.oldValue.baseUrl && isValidUrl(newValue.baseUrl))  || newValue.apiSecretCode !== this.oldValue.apiSecretCode) {
                this.loadModels(newValue);
            }
            this.oldValue = newValue;

        });
        this.manageOperationStatus=true;
    }

    /**
     * Loads available MistralAI embedding models based on the provided baseUrl and apiSecretCode
     * Updates the modelChoicesData with the retrieved models
     */
    private loadModels(newValue: { baseUrl?: string, apiSecretCode?: string }) {
        this.loadingRelatedBackend = true;
        this.MIstralEmbedModelConfigService.getMistralAIEmbeddingModels(newValue).subscribe({
            next: (r) => {
                this.updateLastOperationStatus(r as any);
                this.modelChoicesData = r.result ? r.result : []
            },
            complete:()=>{
                this.loadingRelatedBackend=false;
            }
        });
    }

    /**
     * Handles initialization after persistent data is loaded
     * Sets the model type code and loads models based on the configuration
     */
    protected override onLoadedPersistentData(actualValue: GMistralEmbeddingModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("embedding-mistral");
        this.loadModels(actualValue);
    }

    /**
     * Handles initialization for new data
     * Sets the default model type code for new configurations
     */
    protected override onNewData(actualValue: GMistralEmbeddingModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("embedding-mistral");
    }

    /**
     * Retrieves a specific MistralAI embedding model configuration by its code
     */
    override findByCode(code: string): Observable<GMistralEmbeddingModelConfig | null> {
        return this.MIstralEmbedModelConfigService.findMistralAIEmbeddingModelConfigByCode(code)
    }
    
    /**
     * Saves changes to an existing MistralAI embedding model configuration
     */
    override save(value: GMistralEmbeddingModelConfig): Observable<GMistralEmbeddingModelConfig> {
        
        return this.MIstralEmbedModelConfigService.updateMistralAIEmbeddingModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    /**
     * Creates a new MistralAI embedding model configuration
     */
    override insert(value: GMistralEmbeddingModelConfig): Observable<GMistralEmbeddingModelConfig> {
        
        return this.MIstralEmbedModelConfigService.insertMistralAIEmbeddingModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    /**
     * Deletes a MistralAI embedding model configuration
     */
    override delete(value: GMistralEmbeddingModelConfig): Observable<boolean> {
        return this.MIstralEmbedModelConfigService.deleteMistralAIEmbeddingModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }))
    }

    /**
     * Determines if a MistralAI embedding model configuration can be deleted
     * Currently returns true for all configurations
     */
    override canBeDeleted(value: GMistralEmbeddingModelConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

}